#!/usr/bin/env python3
"""Fail-closed third-party provenance validator for Volcanoes Stage 07."""

from __future__ import annotations

import argparse
import hashlib
import json
import re
import subprocess
import sys
import zipfile
from collections import defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
INVENTORY_PATH = ROOT / "docs" / "provenance" / "third-party-inventory.json"
ALLOWED_RELATIONSHIPS = {
    "REFERENCE_ONLY",
    "RUNTIME_DEPENDENCY",
    "BUILD_DEPENDENCY",
    "DERIVED_CODE",
    "DERIVED_ASSET",
    "REVIEW_REQUIRED",
}
TFC_SHA256 = "26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e"
TFC_FILE_ID = "8710292"
FORBIDDEN_SOURCE_PATTERNS = {
    "TFC Volcanoes package": re.compile(r"\btfcvolcanoes(?:\.|/)", re.IGNORECASE),
    "TerraFirmaCraft package": re.compile(r"\bnet\.dries007\.tfc\b"),
    "TFC Registry API": re.compile(r"\btfc.?registry(?:api)?\b", re.IGNORECASE),
    "Verph copyright": re.compile(r"copyright[^\n]{0,80}\bVerph\b", re.IGNORECASE),
}
PENDING_AUDIT_PHRASES = (
    "must still be executed before release",
    "exact audited source revision/license not frozen here",
    "`REVIEW_REQUIRED` for any source/assets",
    "`REVIEW_REQUIRED` before any source/asset derivation",
    "`REVIEW_REQUIRED` for derivation",
    "`REVIEW_REQUIRED` still applies",
)


class Audit:
    def __init__(self) -> None:
        self.errors: list[str] = []
        self.warnings: list[str] = []
        self.notes: list[str] = []

    def error(self, message: str) -> None:
        self.errors.append(message)
        print(f"ERROR: {message}")

    def warn(self, message: str) -> None:
        self.warnings.append(message)
        print(f"WARN: {message}")

    def note(self, message: str) -> None:
        self.notes.append(message)
        print(f"OK: {message}")

    def finish(self) -> int:
        print(
            f"PROVENANCE_AUDIT_SUMMARY errors={len(self.errors)} "
            f"warnings={len(self.warnings)} checks={len(self.notes)}"
        )
        return 1 if self.errors else 0


def read_text(path: Path) -> str:
    return path.read_text(encoding="utf-8")


def load_inventory(audit: Audit) -> dict:
    if not INVENTORY_PATH.is_file():
        audit.error(f"missing machine-readable inventory: {INVENTORY_PATH.relative_to(ROOT)}")
        return {}
    try:
        data = json.loads(read_text(INVENTORY_PATH))
    except Exception as exc:
        audit.error(f"inventory is not valid JSON: {exc}")
        return {}

    if data.get("schema_version") != 1:
        audit.error("third-party inventory schema_version must be 1")
    if data.get("default_derivation_status") != "NO_DERIVED_MATERIAL_FOUND":
        audit.error("inventory default_derivation_status must be NO_DERIVED_MATERIAL_FOUND")

    projects = data.get("projects")
    if not isinstance(projects, list) or not projects:
        audit.error("third-party inventory must contain a non-empty projects list")
        return data

    ids: set[str] = set()
    for idx, project in enumerate(projects):
        prefix = f"projects[{idx}]"
        project_id = project.get("id")
        if not isinstance(project_id, str) or not project_id:
            audit.error(f"{prefix} has no stable id")
            continue
        if project_id in ids:
            audit.error(f"duplicate project id: {project_id}")
        ids.add(project_id)

        relationships = project.get("relationships")
        if not isinstance(relationships, list) or not relationships:
            audit.error(f"{project_id}: relationships must be a non-empty list")
            continue
        unknown = set(relationships) - ALLOWED_RELATIONSHIPS
        if unknown:
            audit.error(f"{project_id}: unknown relationship(s): {sorted(unknown)}")
        if "REVIEW_REQUIRED" in relationships:
            audit.error(f"{project_id}: REVIEW_REQUIRED blocks release")
        if project.get("redistributed_in_volcanoes_jar") is not False:
            audit.error(
                f"{project_id}: external project is marked redistributed in the Volcanoes JAR; "
                "record explicit derived/bundled compliance before release"
            )

        derivation = project.get("derivation") or {}
        derived = {"DERIVED_CODE", "DERIVED_ASSET"} & set(relationships)
        if derived:
            required = {
                "status": derivation.get("status"),
                "local_paths": derivation.get("local_paths"),
                "upstream_paths": derivation.get("upstream_paths"),
                "permission_evidence": derivation.get("permission_evidence"),
                "required_notice": derivation.get("required_notice"),
            }
            missing = [key for key, value in required.items() if not value]
            if missing:
                audit.error(f"{project_id}: derived material missing {', '.join(missing)}")
            if not project.get("source") or not project.get("version_or_revision"):
                audit.error(f"{project_id}: derived material lacks exact upstream source/revision")
            license_info = project.get("license") or {}
            if not license_info.get("spdx") or not license_info.get("evidence"):
                audit.error(f"{project_id}: derived material lacks license/permission evidence")
        elif derivation and derivation.get("status") != "NO_DERIVED_MATERIAL_FOUND":
            audit.error(
                f"{project_id}: non-derived relationship may only override derivation status "
                "with NO_DERIVED_MATERIAL_FOUND"
            )

        tokens = project.get("evidence_tokens")
        if not isinstance(tokens, list) or not tokens:
            audit.error(f"{project_id}: evidence_tokens must be non-empty")

    audit.note(f"inventory schema and {len(projects)} project records validated")
    return data


def inventory_tokens(inventory: dict) -> list[tuple[str, str]]:
    result: list[tuple[str, str]] = []
    for project in inventory.get("projects", []):
        for token in project.get("evidence_tokens", []):
            if isinstance(token, str) and token:
                result.append((project.get("id", "?"), token))
    return result


def covered(token: str, evidence: list[tuple[str, str]]) -> bool:
    token_lower = token.lower()
    return any(
        marker.lower() in token_lower or token_lower in marker.lower()
        for _, marker in evidence
    )


def versioned_plugins(text: str) -> set[str]:
    return {
        f"{plugin_id}@{version}"
        for plugin_id, version in re.findall(
            r"\bid\s+['\"]([^'\"]+)['\"]\s+version\s+['\"]([^'\"]+)['\"]",
            text,
        )
    }


def validate_direct_dependency_coverage(audit: Audit, inventory: dict) -> None:
    evidence = inventory_tokens(inventory)
    build = read_text(ROOT / "build.gradle")
    settings = read_text(ROOT / "settings.gradle")
    tokens: set[str] = set()

    # Versioned plugins in both project and settings scopes are direct build inputs.
    tokens.update(versioned_plugins(build))
    tokens.update(versioned_plugins(settings))

    dependency_patterns = (
        r"\bimplementation\s*\(?\s*['\"]([^'\"]+)['\"]",
        r"\bcompileOnly\s*\(?\s*['\"]([^'\"]+)['\"]",
        r"\btestImplementation\s+(?:platform\()?\s*['\"]([^'\"]+)['\"]",
    )
    for pattern in dependency_patterns:
        tokens.update(re.findall(pattern, build))

    installer = read_text(ROOT / ".github" / "scripts" / "install_full_pack_acceptance.sh")
    tokens.update(
        re.findall(
            r"^\s*modrinth_version\s+([A-Za-z0-9][A-Za-z0-9._+-]*)",
            installer,
            re.MULTILINE,
        )
    )
    tokens.update(
        re.findall(
            r"^\s*modrinth_project_exact\s+([A-Za-z0-9][A-Za-z0-9._+-]*)",
            installer,
            re.MULTILINE,
        )
    )
    for url in re.findall(r"'(https://[^']+)'", installer):
        if (
            "/curse/maven/" in url
            or "/releases/download/" in url
            or "/dev/latvian/mods/" in url
            or "/com/ldtteam/" in url
            or "maven.createmod.net/" in url
        ):
            tokens.add(url)

    workflow_dir = ROOT / ".github" / "workflows"
    for workflow in workflow_dir.glob("*.yml"):
        text = read_text(workflow)
        tokens.update(re.findall(r"^\s*uses:\s*([^\s#]+)", text, re.MULTILINE))
        tokens.update(
            f"gradle-version: '{version}'"
            for version in re.findall(r"gradle-version:\s*'([^']+)'", text)
        )

    missing = sorted(token for token in tokens if not covered(token, evidence))
    if missing:
        for token in missing:
            audit.error(f"external dependency/tool is not covered by inventory: {token}")
    else:
        audit.note(
            f"all {len(tokens)} direct Gradle/settings/acceptance/CI dependency tokens are inventoried"
        )


def validate_build_input_versions(audit: Audit, inventory: dict) -> None:
    props: dict[str, str] = {}
    for raw in read_text(ROOT / "gradle.properties").splitlines():
        line = raw.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, value = line.split("=", 1)
        props[key.strip()] = value.strip()

    projects = {project.get("id"): project for project in inventory.get("projects", [])}
    checks = {
        "neoforge": props.get("neo_version"),
        "parchment": (
            f"Minecraft {props.get('neogradle.subsystems.parchment.minecraftVersion')} / "
            f"mappings {props.get('neogradle.subsystems.parchment.mappingsVersion')}"
        ),
    }
    for project_id, expected in checks.items():
        project = projects.get(project_id)
        if project is None:
            audit.error(f"missing exact build-input inventory record: {project_id}")
            continue
        actual = project.get("version_or_revision")
        if actual != expected:
            audit.error(
                f"{project_id} inventory mismatch: configured={expected}, inventory={actual}"
            )
    if not any("inventory mismatch" in error for error in audit.errors):
        audit.note(
            "exact build inputs match gradle.properties: "
            f"NeoForge={checks['neoforge']}; Parchment={checks['parchment']}"
        )


def validate_legal_documents(audit: Audit) -> None:
    sources = read_text(ROOT / "SOURCES.md")
    notices = read_text(ROOT / "THIRD_PARTY_NOTICES.md")
    build = read_text(ROOT / "build.gradle")
    task = ROOT / "plans" / "07-hardening" / "✅-05-third-party-licenses-provenance.md"

    if "docs/provenance/third-party-inventory.json" not in sources:
        audit.error("SOURCES.md does not link the machine-readable provenance inventory")
    if "docs/provenance/THIRD_PARTY_AUDIT.md" not in sources:
        audit.error("SOURCES.md does not link the human-readable Stage 07 audit")
    if "docs/provenance/third-party-inventory.json" not in notices:
        audit.error("THIRD_PARTY_NOTICES.md does not link the machine-readable provenance inventory")
    if "docs/provenance/THIRD_PARTY_AUDIT.md" not in notices:
        audit.error("THIRD_PARTY_NOTICES.md does not link the human-readable Stage 07 audit")

    for phrase in PENDING_AUDIT_PHRASES:
        if phrase.lower() in sources.lower() or phrase.lower() in notices.lower():
            audit.error(f"legal/source docs still contain unresolved pre-audit phrase: {phrase}")

    if TFC_FILE_ID not in sources or TFC_SHA256 not in sources:
        audit.error("SOURCES.md does not preserve the exact TFC Volcanoes file id + SHA-256")
    if TFC_FILE_ID not in notices or TFC_SHA256 not in notices:
        audit.error("THIRD_PARTY_NOTICES.md does not preserve exact TFC Volcanoes baseline evidence")

    if "from('LICENSE')" not in build or "from('THIRD_PARTY_NOTICES.md')" not in build:
        audit.error("build.gradle does not package LICENSE and THIRD_PARTY_NOTICES.md in the JAR")
    else:
        audit.note("build.gradle packages LICENSE and THIRD_PARTY_NOTICES.md")

    if not task.is_file():
        audit.error("completed Task 05 plan file is not present with ✅ prefix")
    audit.note("legal/source document consistency checks executed")


def scan_current_sources(audit: Audit) -> None:
    roots = [ROOT / "src" / "main" / "java", ROOT / "src" / "main" / "resources"]
    scanned = 0
    for root in roots:
        if not root.exists():
            continue
        for path in root.rglob("*"):
            if not path.is_file():
                continue
            try:
                text = path.read_text(encoding="utf-8")
            except UnicodeDecodeError:
                continue
            scanned += 1
            relative = path.relative_to(ROOT)
            for label, pattern in FORBIDDEN_SOURCE_PATTERNS.items():
                if pattern.search(text):
                    audit.error(f"current release material contains {label}: {relative}")
    audit.note(f"current source/resource signature scan covered {scanned} text files")


def git(*args: str) -> bytes:
    return subprocess.check_output(["git", *args], cwd=ROOT, stderr=subprocess.DEVNULL)


def historical_blobs(audit: Audit) -> list[tuple[str, str, bytes]]:
    try:
        raw = git(
            "rev-list",
            "--objects",
            "--all",
            "--",
            "src/main/java",
            "src/main/resources",
            "src/generated/resources",
        ).decode("utf-8", errors="replace")
    except Exception as exc:
        audit.error(f"cannot enumerate Git history; checkout must use fetch-depth: 0: {exc}")
        return []

    seen: set[str] = set()
    result: list[tuple[str, str, bytes]] = []
    for line in raw.splitlines():
        parts = line.split(" ", 1)
        if len(parts) != 2:
            continue
        object_id, path = parts
        if object_id in seen:
            continue
        if not (
            path.endswith(".java")
            or path.startswith("src/main/resources/")
            or path.startswith("src/generated/resources/")
        ):
            continue
        try:
            if git("cat-file", "-t", object_id).strip() != b"blob":
                continue
            blob = git("cat-file", "blob", object_id)
        except Exception:
            continue
        seen.add(object_id)
        result.append((object_id, path, blob))

    audit.note(f"retroactive Git audit enumerated {len(result)} unique release-material blobs")
    return result


def strip_java_for_similarity(text: str) -> str:
    text = re.sub(r"/\*.*?\*/", " ", text, flags=re.DOTALL)
    text = re.sub(r"//.*?$", " ", text, flags=re.MULTILINE)
    text = re.sub(r"^\s*(?:package|import)\s+[^;]+;\s*$", " ", text, flags=re.MULTILINE)
    return text


def java_tokens(text: str) -> list[str]:
    return re.findall(
        r"[A-Za-z_$][A-Za-z0-9_$]*|0x[0-9A-Fa-f]+|\d+(?:\.\d+)?|"
        r"==|!=|<=|>=|&&|\|\||::|->|[{}()[\].,;:+\-*/%<>=!?&|^~]",
        strip_java_for_similarity(text),
    )


def build_tfc_shingle_index(upstream_dir: Path, size: int = 64) -> dict[tuple[str, ...], str]:
    index: dict[tuple[str, ...], str] = {}
    for path in upstream_dir.rglob("*.java"):
        tokens = java_tokens(path.read_text(encoding="utf-8", errors="replace"))
        if len(tokens) < size:
            continue
        relative = str(path.relative_to(upstream_dir))
        for idx in range(0, len(tokens) - size + 1):
            index.setdefault(tuple(tokens[idx : idx + size]), relative)
    return index


def scan_history_signatures_and_similarity(
    audit: Audit,
    blobs: list[tuple[str, str, bytes]],
    upstream_dir: Path | None,
) -> None:
    shingle_size = 64
    shingle_index: dict[tuple[str, ...], str] = {}
    if upstream_dir is not None:
        if not upstream_dir.is_dir():
            audit.error(f"TFC decompiled source directory missing: {upstream_dir}")
        else:
            shingle_index = build_tfc_shingle_index(upstream_dir, shingle_size)
            audit.note(
                f"TFC decompiled comparison index contains {len(shingle_index)} "
                f"{shingle_size}-token shingles"
            )

    suspicious: list[str] = []
    similarity_hits: list[str] = []
    java_count = 0
    for object_id, path, blob in blobs:
        if not path.endswith(".java"):
            continue
        java_count += 1
        text = blob.decode("utf-8", errors="replace")
        for label, pattern in FORBIDDEN_SOURCE_PATTERNS.items():
            if pattern.search(text):
                suspicious.append(f"{object_id[:12]} {path}: {label}")

        if shingle_index:
            tokens = java_tokens(text)
            if len(tokens) >= shingle_size:
                matched_upstreams: defaultdict[str, int] = defaultdict(int)
                for idx in range(0, len(tokens) - shingle_size + 1):
                    upstream = shingle_index.get(tuple(tokens[idx : idx + shingle_size]))
                    if upstream is not None:
                        matched_upstreams[upstream] += 1
                if matched_upstreams:
                    upstream, count = max(matched_upstreams.items(), key=lambda item: item[1])
                    if count >= 3:
                        similarity_hits.append(
                            f"{object_id[:12]} {path} vs {upstream}: "
                            f"{count} matching {shingle_size}-token windows"
                        )

    for finding in suspicious:
        audit.error(f"historical high-signal upstream signature: {finding}")
    for finding in similarity_hits:
        audit.error(f"historical TFC source similarity requires DERIVED_CODE review: {finding}")
    if not suspicious and not similarity_hits:
        audit.note(
            "retroactive Java audit found no high-signal TFC carryover across "
            f"{java_count} unique historical Java blobs"
        )


def upstream_resource_hashes(upstream_jar: Path) -> dict[tuple[int, str], str]:
    """Index every eligible upstream resource, including small files."""
    result: dict[tuple[int, str], str] = {}
    with zipfile.ZipFile(upstream_jar) as jar:
        for info in jar.infolist():
            name = info.filename
            if info.is_dir() or name.endswith(".class"):
                continue
            if name.startswith("META-INF/") or name in {"pack.mcmeta", "LICENSE", "LICENSE.txt"}:
                continue
            data = jar.read(info)
            result.setdefault((len(data), hashlib.sha256(data).hexdigest()), name)
    return result


def scan_history_assets(
    audit: Audit,
    blobs: list[tuple[str, str, bytes]],
    upstream_jar: Path | None,
) -> None:
    if upstream_jar is None:
        return
    if not upstream_jar.is_file():
        audit.error(f"pinned TFC upstream JAR missing: {upstream_jar}")
        return

    actual = hashlib.sha256(upstream_jar.read_bytes()).hexdigest()
    if actual != TFC_SHA256:
        audit.error(f"TFC upstream JAR SHA-256 mismatch: expected {TFC_SHA256}, got {actual}")
        return

    hashes = upstream_resource_hashes(upstream_jar)
    hits: list[str] = []
    resource_count = 0
    for object_id, path, blob in blobs:
        if path.endswith(".java"):
            continue
        resource_count += 1
        upstream = hashes.get((len(blob), hashlib.sha256(blob).hexdigest()))
        if upstream is not None:
            hits.append(f"{object_id[:12]} {path} == {upstream}")

    for hit in hits:
        audit.error(f"historical exact TFC resource match requires DERIVED_ASSET review: {hit}")
    if not hits:
        audit.note(
            "retroactive resource audit found no exact TFC asset/resource copies across "
            f"{resource_count} unique historical resource blobs"
        )


def validate_tfc_baseline(audit: Audit, upstream_jar: Path | None) -> None:
    upstream_docs = [
        ROOT / "docs" / "upstream" / "NOTICE.md",
        ROOT / "docs" / "upstream" / "TFC_VOLCANOES.md",
        ROOT / "docs" / "upstream" / "TFC_VOLCANOES_CLASSIFICATION.md",
    ]
    for path in upstream_docs:
        if not path.is_file():
            audit.error(f"missing TFC provenance document: {path.relative_to(ROOT)}")
            continue
        text = read_text(path)
        if TFC_SHA256 not in text and path.name != "TFC_VOLCANOES_CLASSIFICATION.md":
            audit.error(f"{path.relative_to(ROOT)} does not preserve pinned TFC SHA-256")

    if upstream_jar is not None and upstream_jar.is_file():
        actual = hashlib.sha256(upstream_jar.read_bytes()).hexdigest()
        if actual == TFC_SHA256:
            audit.note("pinned TFC Volcanoes 2.2.1 binary SHA-256 verified")
        else:
            audit.error(f"pinned TFC binary changed: {actual}")
    audit.note("TFC baseline document checks executed")


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--history", action="store_true", help="scan historical release-material blobs")
    parser.add_argument("--tfc-upstream-dir", type=Path)
    parser.add_argument("--tfc-upstream-jar", type=Path)
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    audit = Audit()
    inventory = load_inventory(audit)
    if inventory:
        validate_direct_dependency_coverage(audit, inventory)
        validate_build_input_versions(audit, inventory)
        validate_legal_documents(audit)
    scan_current_sources(audit)
    validate_tfc_baseline(audit, args.tfc_upstream_jar)
    if args.history:
        blobs = historical_blobs(audit)
        scan_history_signatures_and_similarity(audit, blobs, args.tfc_upstream_dir)
        scan_history_assets(audit, blobs, args.tfc_upstream_jar)
    return audit.finish()


if __name__ == "__main__":
    sys.exit(main())
