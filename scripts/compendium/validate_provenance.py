#!/usr/bin/env python3
"""Validate Stage 10 Compêndio Natural provenance guardrails.

The validator is intentionally stdlib-only and offline. It validates frozen
upstream records, reuse policy, registered Compendium assets, and provenance
metadata for explicitly imported editorial JSON.
"""

from __future__ import annotations

import argparse
import json
from pathlib import Path
import re
import sys
from typing import Any


MARKERS = {
    "UPSTREAM.md": "<!-- compendium-upstream:v1 -->",
    "PROVENANCE.md": "<!-- compendium-provenance:v1 -->",
    "ASSET_SOURCES.md": "<!-- compendium-assets:v1 -->",
}
ALLOWED_POLICIES = {
    "BEHAVIOR_REFERENCE",
    "PUBLIC_API",
    "CODE_REUSE",
    "ASSET_REUSE",
    "NO_REUSE",
}
ALLOWED_ASSET_ORIGINS = {"PROJECT_ORIGINAL", "EXTERNAL_REUSE", "DERIVED"}
SHA40 = re.compile(r"^[0-9a-f]{40}$")
HTTPS_URL = re.compile(r"^https://")


class ManifestError(ValueError):
    pass


def extract_json_manifest(path: Path, marker: str) -> Any:
    if not path.is_file():
        raise ManifestError(f"missing manifest: {path.as_posix()}")
    text = path.read_text(encoding="utf-8")
    marker_index = text.find(marker)
    if marker_index < 0:
        raise ManifestError(f"missing marker {marker!r} in {path.as_posix()}")
    after = text[marker_index + len(marker):]
    open_fence = after.find("```json")
    if open_fence < 0:
        raise ManifestError(f"missing JSON fence in {path.as_posix()}")
    payload_start = open_fence + len("```json")
    close_fence = after.find("```", payload_start)
    if close_fence < 0:
        raise ManifestError(f"unterminated JSON fence in {path.as_posix()}")
    payload = after[payload_start:close_fence].strip()
    try:
        return json.loads(payload)
    except json.JSONDecodeError as exc:
        raise ManifestError(
            f"invalid JSON in {path.as_posix()}: line {exc.lineno}, column {exc.colno}: {exc.msg}"
        ) from exc


def require_string(record: dict[str, Any], key: str, context: str, errors: list[str]) -> str | None:
    value = record.get(key)
    if not isinstance(value, str) or not value.strip():
        errors.append(f"{context}: {key} must be a non-empty string")
        return None
    return value.strip()


def validate_upstreams(data: Any, errors: list[str]) -> set[str]:
    if not isinstance(data, list):
        errors.append("UPSTREAM.md: manifest must be a JSON array")
        return set()

    ids: set[str] = set()
    for index, raw in enumerate(data):
        context = f"UPSTREAM.md[{index}]"
        if not isinstance(raw, dict):
            errors.append(f"{context}: record must be an object")
            continue
        upstream_id = require_string(raw, "id", context, errors)
        if upstream_id:
            if upstream_id in ids:
                errors.append(f"{context}: duplicate id {upstream_id!r}")
            ids.add(upstream_id)
            context = f"upstream {upstream_id}"

        require_string(raw, "name", context, errors)
        require_string(raw, "observed_version", context, errors)
        require_string(raw, "minecraft_version", context, errors)
        require_string(raw, "loader", context, errors)
        require_string(raw, "code_license", context, errors)
        require_string(raw, "asset_license", context, errors)
        require_string(raw, "observed_at", context, errors)

        sha = require_string(raw, "source_sha", context, errors)
        if sha and not SHA40.fullmatch(sha):
            errors.append(f"{context}: source_sha must be a frozen 40-character lowercase Git SHA, got {sha!r}")

        for url_key in (
            "project_url",
            "source_repository",
            "code_license_evidence",
            "asset_license_evidence",
        ):
            url = require_string(raw, url_key, context, errors)
            if url and not HTTPS_URL.match(url):
                errors.append(f"{context}: {url_key} must use https://")

        for policy_key in ("code_reuse_policy", "asset_reuse_policy"):
            policy = require_string(raw, policy_key, context, errors)
            if policy and policy not in ALLOWED_POLICIES:
                errors.append(f"{context}: {policy_key} has unknown policy {policy!r}")

        if raw.get("code_reuse_policy") == "CODE_REUSE" and not raw.get("reuse_obligations"):
            errors.append(f"{context}: CODE_REUSE requires reuse_obligations")
        if raw.get("asset_reuse_policy") == "ASSET_REUSE" and not raw.get("asset_reuse_obligations"):
            errors.append(f"{context}: ASSET_REUSE requires asset_reuse_obligations")

    return ids


def validate_policy(data: Any, upstream_ids: set[str], errors: list[str]) -> None:
    if not isinstance(data, dict):
        errors.append("PROVENANCE.md: manifest must be a JSON object")
        return
    if data.get("schema") != 1:
        errors.append("PROVENANCE.md: schema must be 1")

    allowed = data.get("allowed_categories")
    if not isinstance(allowed, list) or set(allowed) != ALLOWED_POLICIES:
        errors.append("PROVENANCE.md: allowed_categories must match the canonical policy set")

    default = data.get("default_policy")
    if default not in ALLOWED_POLICIES:
        errors.append(f"PROVENANCE.md: invalid default_policy {default!r}")

    refs = data.get("feature_references")
    if not isinstance(refs, list):
        errors.append("PROVENANCE.md: feature_references must be an array")
        return

    seen_features: set[str] = set()
    for index, raw in enumerate(refs):
        context = f"PROVENANCE.md feature_references[{index}]"
        if not isinstance(raw, dict):
            errors.append(f"{context}: entry must be an object")
            continue
        feature = require_string(raw, "feature", context, errors)
        upstream_id = require_string(raw, "upstream_id", context, errors)
        policy = require_string(raw, "policy", context, errors)
        if feature:
            if feature in seen_features:
                errors.append(f"{context}: duplicate feature {feature!r}")
            seen_features.add(feature)
        if upstream_id and upstream_id not in upstream_ids:
            errors.append(f"{context}: unknown upstream_id {upstream_id!r}")
        if policy and policy not in ALLOWED_POLICIES:
            errors.append(f"{context}: unknown policy {policy!r}")


def normalize_repo_path(value: str) -> str:
    return Path(value.replace("\\", "/")).as_posix().lstrip("./")


def validate_asset_record(raw: Any, index: int, errors: list[str]) -> str | None:
    context = f"ASSET_SOURCES.md assets[{index}]"
    if not isinstance(raw, dict):
        errors.append(f"{context}: record must be an object")
        return None
    path_value = require_string(raw, "path", context, errors)
    origin = require_string(raw, "origin", context, errors)
    require_string(raw, "author", context, errors)
    require_string(raw, "license", context, errors)
    if origin and origin not in ALLOWED_ASSET_ORIGINS:
        errors.append(f"{context}: unknown origin {origin!r}")
    if origin in {"EXTERNAL_REUSE", "DERIVED"}:
        require_string(raw, "source", context, errors)
        frozen = raw.get("source_sha") or raw.get("source_version")
        if not isinstance(frozen, str) or not frozen.strip():
            errors.append(f"{context}: {origin} requires source_sha or source_version")
    return normalize_repo_path(path_value) if path_value else None


def validate_assets(root: Path, data: Any, errors: list[str]) -> None:
    if not isinstance(data, dict):
        errors.append("ASSET_SOURCES.md: manifest must be a JSON object")
        return
    if data.get("schema") != 1:
        errors.append("ASSET_SOURCES.md: schema must be 1")
    records = data.get("assets")
    if not isinstance(records, list):
        errors.append("ASSET_SOURCES.md: assets must be an array")
        return

    registered: set[str] = set()
    for index, raw in enumerate(records):
        path = validate_asset_record(raw, index, errors)
        if path:
            if path in registered:
                errors.append(f"ASSET_SOURCES.md: duplicate asset path {path}")
            registered.add(path)
            if not (root / path).is_file():
                errors.append(f"ASSET_SOURCES.md: registered asset does not exist: {path}")

    asset_root = root / "src/main/resources/assets/rpgskilltree/compendium"
    actual: set[str] = set()
    if asset_root.is_dir():
        actual = {
            path.relative_to(root).as_posix()
            for path in asset_root.rglob("*")
            if path.is_file()
        }

    for path in sorted(actual - registered):
        errors.append(f"untracked asset: {path} is not registered in ASSET_SOURCES.md")
    for path in sorted(registered - actual):
        # A more specific missing-file error is already emitted above; keep this
        # branch silent to avoid duplicate diagnostics.
        pass


def imported_editorial_metadata(document: Any) -> dict[str, Any] | None:
    if not isinstance(document, dict):
        return None
    provenance = document.get("provenance")
    if document.get("origin") == "imported":
        return provenance if isinstance(provenance, dict) else document
    if isinstance(provenance, dict) and provenance.get("origin") == "imported":
        return provenance
    return None


def validate_editorial_imports(root: Path, errors: list[str]) -> None:
    editorial_root = root / "src/main/resources/data/rpgskilltree/compendium/editorial"
    if not editorial_root.is_dir():
        return
    for path in sorted(editorial_root.rglob("*.json")):
        rel = path.relative_to(root).as_posix()
        try:
            document = json.loads(path.read_text(encoding="utf-8"))
        except (OSError, json.JSONDecodeError) as exc:
            errors.append(f"editorial JSON unreadable: {rel}: {exc}")
            continue
        metadata = imported_editorial_metadata(document)
        if metadata is None:
            continue
        missing = [
            key
            for key in ("author", "license", "source")
            if not isinstance(metadata.get(key), str) or not metadata[key].strip()
        ]
        if missing:
            errors.append(
                f"imported editorial content lacks provenance in {rel}: missing {', '.join(missing)}"
            )


def validate(root: Path) -> list[str]:
    errors: list[str] = []
    docs = root / "docs/compendium"
    loaded: dict[str, Any] = {}
    for filename, marker in MARKERS.items():
        try:
            loaded[filename] = extract_json_manifest(docs / filename, marker)
        except (OSError, ManifestError) as exc:
            errors.append(str(exc))

    if errors:
        return errors

    upstream_ids = validate_upstreams(loaded["UPSTREAM.md"], errors)
    validate_policy(loaded["PROVENANCE.md"], upstream_ids, errors)
    validate_assets(root, loaded["ASSET_SOURCES.md"], errors)
    validate_editorial_imports(root, errors)
    return errors


def parse_args(argv: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "--root",
        type=Path,
        default=Path(__file__).resolve().parents[2],
        help="repository root to validate (defaults to this checkout)",
    )
    return parser.parse_args(argv)


def main(argv: list[str] | None = None) -> int:
    args = parse_args(sys.argv[1:] if argv is None else argv)
    root = args.root.resolve()
    errors = validate(root)
    if errors:
        print(f"Compendium provenance validation: FAIL ({len(errors)} error(s))")
        for error in errors:
            print(f"- {error}")
        return 1
    print("Compendium provenance validation: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
