#!/usr/bin/env python3
from __future__ import annotations
import json, re, sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[3]
ENG = ROOT / "PROJECT-INSTRUCTIONS" / "engineering"

SCHEMA_EXAMPLES = {
    "mod-spec.schema.json": "mod-spec.example.json",
    "dependency-profile.schema.json": "dependency-profile.example.json",
    "asset-handoff.schema.json": "asset-handoff.example.json",
    "compatibility-matrix.schema.json": "compatibility-matrix.example.json",
    "test-manifest.schema.json": "test-manifest.example.json",
}
EVIDENCE_STATES = {
    "CONFIRMED","IMPLEMENTED","MERGED","PASS","PENDING","UNRESOLVED",
    "UNAVAILABLE","DEFERRED","REFERENCE_ONLY","INCOMPATIBLE","BLOCKED","SUPERSEDED"
}

def load_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8"))

def _type_ok(value, expected):
    if expected == "object": return isinstance(value, dict)
    if expected == "array": return isinstance(value, list)
    if expected == "string": return isinstance(value, str)
    if expected == "integer": return isinstance(value, int) and not isinstance(value, bool)
    if expected == "number": return isinstance(value, (int, float)) and not isinstance(value, bool)
    if expected == "boolean": return isinstance(value, bool)
    if expected == "null": return value is None
    return False

def validate_instance(schema, value, path="$"):
    errors = []
    expected = schema.get("type")
    if isinstance(expected, list):
        if not any(_type_ok(value, t) for t in expected):
            return [f"{path}: expected one of {expected}, got {type(value).__name__}"]
    elif expected and not _type_ok(value, expected):
        return [f"{path}: expected {expected}, got {type(value).__name__}"]

    if "const" in schema and value != schema["const"]:
        errors.append(f"{path}: expected const {schema['const']!r}")
    if "enum" in schema and value not in schema["enum"]:
        errors.append(f"{path}: value {value!r} not in enum")
    if isinstance(value, str):
        if "minLength" in schema and len(value) < schema["minLength"]:
            errors.append(f"{path}: shorter than minLength")
        if "pattern" in schema and re.fullmatch(schema["pattern"], value) is None:
            errors.append(f"{path}: does not match pattern {schema['pattern']}")
    if isinstance(value, (int, float)) and not isinstance(value, bool):
        if "minimum" in schema and value < schema["minimum"]:
            errors.append(f"{path}: below minimum")
        if "maximum" in schema and value > schema["maximum"]:
            errors.append(f"{path}: above maximum")
    if isinstance(value, list):
        if "minItems" in schema and len(value) < schema["minItems"]:
            errors.append(f"{path}: fewer than minItems")
        if schema.get("uniqueItems"):
            seen = [json.dumps(v, sort_keys=True, separators=(",", ":")) for v in value]
            if len(seen) != len(set(seen)):
                errors.append(f"{path}: duplicate array items")
        if "items" in schema:
            for i, item in enumerate(value):
                errors += validate_instance(schema["items"], item, f"{path}[{i}]")
    if isinstance(value, dict):
        required = schema.get("required", [])
        for key in required:
            if key not in value:
                errors.append(f"{path}: missing required property {key!r}")
        props = schema.get("properties", {})
        for key, item in value.items():
            if key in props:
                errors += validate_instance(props[key], item, f"{path}.{key}")
            elif schema.get("additionalProperties") is False:
                errors.append(f"{path}: unexpected property {key!r}")
    return errors

def validate_schema_document(path: Path, schema):
    errors = []
    if schema.get("$schema") != "https://json-schema.org/draft/2020-12/schema":
        errors.append(f"{path}: wrong or missing $schema")
    if not isinstance(schema.get("$id"), str) or not schema["$id"].startswith("https://github.com/Gustavaopere/neoforge-rpg-skilltree/"):
        errors.append(f"{path}: $id must be repository-qualified")
    if schema.get("type") != "object":
        errors.append(f"{path}: root type must be object")
    if not isinstance(schema.get("properties"), dict):
        errors.append(f"{path}: root properties must be object")
    if not isinstance(schema.get("required"), list):
        errors.append(f"{path}: root required must be array")
    if schema.get("additionalProperties") is not False:
        errors.append(f"{path}: root must fail closed with additionalProperties=false")
    return errors

def validate_source_registry(path: Path):
    data = load_json(path)
    errors = []
    if data.get("schema_version") != 1:
        errors.append(f"{path}: schema_version must be 1")
    sources = data.get("sources")
    if not isinstance(sources, list) or not sources:
        return errors + [f"{path}: sources must be non-empty array"]
    ids = []
    for i, source in enumerate(sources):
        p = f"{path}:sources[{i}]"
        for key in ("source_id","source_type","authority_scope","state","locator"):
            if key not in source: errors.append(f"{p}: missing {key}")
        sid = source.get("source_id")
        if isinstance(sid, str): ids.append(sid)
        if source.get("state") not in EVIDENCE_STATES:
            errors.append(f"{p}: invalid evidence state")
        if source.get("source_type") not in {"git_repository","physical_snapshot","official_documentation","local_artifact"}:
            errors.append(f"{p}: invalid source_type")
        if not isinstance(source.get("locator"), dict) or not source.get("locator"):
            errors.append(f"{p}: locator must be non-empty object")
    if len(ids) != len(set(ids)):
        errors.append(f"{path}: duplicate source_id")
    return errors

def run_validation(root=ROOT):
    eng = root / "PROJECT-INSTRUCTIONS" / "engineering"
    errors = []
    schemas_dir = eng / "schemas"
    examples_dir = eng / "examples"
    for schema_name, example_name in SCHEMA_EXAMPLES.items():
        schema_path = schemas_dir / schema_name
        example_path = examples_dir / example_name
        if not schema_path.is_file():
            errors.append(f"missing {schema_path.relative_to(root)}")
            continue
        if not example_path.is_file():
            errors.append(f"missing {example_path.relative_to(root)}")
            continue
        try:
            schema = load_json(schema_path)
            example = load_json(example_path)
        except Exception as exc:
            errors.append(f"{schema_name}/{example_name}: JSON parse failed: {exc}")
            continue
        errors += validate_schema_document(schema_path, schema)
        errors += [f"{example_path}: {e}" for e in validate_instance(schema, example)]
    source_registry = eng / "catalog" / "sources" / "SOURCE-REGISTRY.json"
    if not source_registry.is_file():
        errors.append(f"missing {source_registry.relative_to(root)}")
    else:
        try: errors += validate_source_registry(source_registry)
        except Exception as exc: errors.append(f"{source_registry}: validation failed: {exc}")
    contract = eng / "contracts" / "MOD-SPEC-CONTRACT.md"
    if not contract.is_file():
        errors.append(f"missing {contract.relative_to(root)}")
    else:
        text = contract.read_text(encoding="utf-8")
        for token in ("mod-spec.schema.json", "UNRESOLVED", "Repo Textura", "runtime authority"):
            if token not in text:
                errors.append(f"{contract}: missing contract token {token!r}")
    return errors

def main():
    errors = run_validation()
    if errors:
        print("I1 FOUNDATION VALIDATION: FAIL")
        for error in errors: print(f"- {error}")
        return 1
    print("I1 FOUNDATION VALIDATION: PASS")
    print("5 schemas + 5 examples + source registry + mod-spec contract validated")
    return 0

if __name__ == "__main__":
    raise SystemExit(main())
