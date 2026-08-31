#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

NAMESPACE = "rpgskilltree"
SEMANTIC_COMBAT_TREE_ID = "rpgskilltree:runtime/combat_perks"
SEMANTIC_COMBAT_SNAPSHOT = Path("build/generated-wiki/combat-perks.json")
SEMANTIC_COMBAT_WIKI_DIR = Path("wiki/combat-perks")
SEMANTIC_COMBAT_BATCH_SIZE = 10


class WikiCatalogDriftError(RuntimeError):
    def __init__(self, paths: list[Path]):
        self.paths = tuple(paths)
        super().__init__("generated wiki catalog is stale: " + ", ".join(str(path) for path in paths))


def replace_generated_block(document: str, marker: str, body: str) -> str:
    start = f"<!-- {NAMESPACE}:generated:{marker}:start -->"
    end = f"<!-- {NAMESPACE}:generated:{marker}:end -->"
    if document.count(start) != 1 or document.count(end) != 1:
        raise ValueError(f"expected exactly one generated marker pair for {marker}")
    before, remainder = document.split(start, 1)
    _old_body, after = remainder.split(end, 1)
    normalized = body.rstrip("\n") + "\n"
    return before + start + "\n" + normalized + end + after


def build_catalog_sections(root: Path, locale: str = "pt_br") -> tuple[str, str]:
    root = Path(root)
    rules = _load_rules(root)
    translations = _load_translations(root, locale)
    attributes, behaviors = _load_effects(root)

    attributes_by_node: dict[str, list[dict[str, Any]]] = {}
    for effect in attributes:
        attributes_by_node.setdefault(effect["nodeId"], []).append(effect)
    behaviors_by_node: dict[str, list[dict[str, Any]]] = {}
    for effect in behaviors:
        behaviors_by_node.setdefault(effect["nodeId"], []).append(effect)

    perk_lines = [
        "| Árvore | ID | Nome | Descrição | Ranks | Custo/rank | Requisitos | Efeitos |",
        "| --- | --- | --- | --- | ---: | ---: | --- | --- |",
    ]
    for tree_id, node in rules:
        node_id = node["id"]
        name = translations.get(_translation_key(node_id, "name"), node_id)
        description = translations.get(_translation_key(node_id, "description"), "—")
        effects = [
            _format_attribute_summary(effect)
            for effect in sorted(attributes_by_node.get(node_id, []), key=lambda value: value["effectId"])
        ]
        effects.extend(
            f"handler `{effect['handlerId']}`"
            for effect in sorted(behaviors_by_node.get(node_id, []), key=lambda value: value["effectId"])
        )
        perk_lines.append(
            _format_perk_row(
                tree_id,
                node_id,
                name,
                description,
                node,
                "; ".join(effects) if effects else "—",
            )
        )

    effect_lines = [
        "| Nó | Nome | Effect ID | Alvo/Handler | Operação | Por rank |",
        "| --- | --- | --- | --- | --- | ---: |",
    ]
    for effect in sorted(attributes, key=lambda value: (value["nodeId"], value["effectId"])):
        node_id = effect["nodeId"]
        name = translations.get(_translation_key(node_id, "name"), node_id)
        effect_lines.append(
            "| " + " | ".join(
                [
                    _cell(f"`{node_id}`"),
                    _cell(name),
                    _cell(f"`{effect['effectId']}`"),
                    _cell(f"`{effect['attributeId']}`"),
                    _cell(effect["operation"]),
                    _cell(_format_amount(effect["operation"], effect["amountPerRank"])),
                ]
            ) + " |"
        )
    for effect in sorted(behaviors, key=lambda value: (value["nodeId"], value["effectId"])):
        node_id = effect["nodeId"]
        name = translations.get(_translation_key(node_id, "name"), node_id)
        effect_lines.append(
            "| " + " | ".join(
                [
                    _cell(f"`{node_id}`"),
                    _cell(name),
                    _cell(f"`{effect['effectId']}`"),
                    _cell(f"`{effect['handlerId']}`"),
                    "BEHAVIOR_HANDLER",
                    "—",
                ]
            ) + " |"
        )

    return "\n".join(perk_lines) + "\n", "\n".join(effect_lines) + "\n"


def build_semantic_combat_section(root: Path, *, required: bool = True) -> str:
    rows = _load_semantic_combat_snapshot(Path(root), required=required)
    return _render_semantic_combat_table(rows)


def build_semantic_combat_documents(root: Path) -> list[tuple[Path, str]]:
    rows = _load_semantic_combat_snapshot(Path(root), required=True)
    if len(rows) != 100:
        raise ValueError(f"semantic combat snapshot must contain exactly 100 nodes, got {len(rows)}")

    documents: list[tuple[Path, str]] = []
    for offset in range(0, len(rows), SEMANTIC_COMBAT_BATCH_SIZE):
        batch = rows[offset : offset + SEMANTIC_COMBAT_BATCH_SIZE]
        first_code = batch[0][1]["code"]
        last_code = batch[-1][1]["code"]
        path = SEMANTIC_COMBAT_WIKI_DIR / f"{first_code}-{last_code}.md"
        content = (
            f"# Perks de combate {first_code}–{last_code}\n\n"
            "> Gerado a partir das autoridades canônicas do runtime; não editar manualmente. "
            "Descrições ausentes permanecem `—` e efeitos não são inferidos de policies Java.\n\n"
            + _render_semantic_combat_table(batch)
        )
        documents.append((path, content))
    return documents


def build_content_coverage(
    root: Path,
    locale: str = "pt_br",
    tree_id: str | None = None,
) -> dict[str, Any]:
    root = Path(root)
    rules = _load_rules(root)
    translations = _load_translations(root, locale)
    attributes, behaviors = _load_effects(root)

    if tree_id is not None:
        rules = [entry for entry in rules if entry[0] == tree_id]
        if not rules:
            raise ValueError(f"tree not found in node rules: {tree_id}")

    attribute_counts: dict[str, int] = {}
    behavior_counts: dict[str, int] = {}
    for effect in attributes:
        node_id = str(effect["nodeId"])
        attribute_counts[node_id] = attribute_counts.get(node_id, 0) + 1
    for effect in behaviors:
        node_id = str(effect["nodeId"])
        behavior_counts[node_id] = behavior_counts.get(node_id, 0) + 1

    nodes: list[dict[str, Any]] = []
    for current_tree_id, node in rules:
        node_id = str(node["id"])
        has_name = _has_nonblank_translation(translations, _translation_key(node_id, "name"))
        has_description = _has_nonblank_translation(translations, _translation_key(node_id, "description"))
        attribute_effects = attribute_counts.get(node_id, 0)
        behavior_effects = behavior_counts.get(node_id, 0)
        nodes.append(
            {
                "tree_id": current_tree_id,
                "id": node_id,
                "has_name": has_name,
                "has_description": has_description,
                "attribute_effects": attribute_effects,
                "behavior_effects": behavior_effects,
                "has_declarative_effect": (attribute_effects + behavior_effects) > 0,
            }
        )

    nodes.sort(key=lambda value: (value["tree_id"], value["id"]))
    total_nodes = len(nodes)
    localized_names = sum(1 for node in nodes if node["has_name"])
    localized_descriptions = sum(1 for node in nodes if node["has_description"])
    nodes_with_declarative_effects = sum(1 for node in nodes if node["has_declarative_effect"])

    return {
        "locale": locale,
        "tree_id": tree_id,
        "summary": {
            "total_nodes": total_nodes,
            "localized_names": localized_names,
            "localized_descriptions": localized_descriptions,
            "nodes_missing_names": total_nodes - localized_names,
            "nodes_missing_descriptions": total_nodes - localized_descriptions,
            "nodes_with_declarative_effects": nodes_with_declarative_effects,
            "nodes_without_declarative_effects": total_nodes - nodes_with_declarative_effects,
        },
        "nodes": nodes,
    }


def update_catalog_documents(root: Path, locale: str = "pt_br", check: bool = False) -> list[Path]:
    root = Path(root)
    perk_section, effect_section = build_catalog_sections(root, locale=locale)
    generated_documents = build_semantic_combat_documents(root)
    targets = (
        (root / "wiki/PERK_CATALOG.md", "perk-catalog", perk_section),
        (root / "wiki/EFFECT_CATALOG.md", "effect-catalog", effect_section),
    )
    changed: list[Path] = []
    updates: list[tuple[Path, str]] = []

    for path, marker, section in targets:
        original = path.read_text(encoding="utf-8")
        updated = replace_generated_block(original, marker, section)
        if updated != original:
            changed.append(path)
            updates.append((path, updated))

    for relative_path, expected in generated_documents:
        path = root / relative_path
        actual = path.read_text(encoding="utf-8") if path.is_file() else None
        if actual != expected:
            changed.append(path)
            updates.append((path, expected))

    if check and changed:
        raise WikiCatalogDriftError(changed)
    if not check:
        for path, updated in updates:
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_text(updated, encoding="utf-8")
    return changed


def _render_semantic_combat_table(rows: list[tuple[str, dict[str, Any]]]) -> str:
    lines = [
        "| Árvore | ID | Código | Nome | Descrição | Ranks | Custo/rank | Requisitos | Efeitos |",
        "| --- | --- | --- | --- | --- | ---: | ---: | --- | --- |",
    ]
    for tree_id, node in rows:
        lines.append(
            "| " + " | ".join(
                [
                    _cell(f"`{tree_id}`"),
                    _cell(f"`{node['id']}`"),
                    _cell(node["code"]),
                    _cell(node["name"]),
                    _cell(node["description"] or "—"),
                    str(node["maxRank"]),
                    str(node["costPerRank"]),
                    _cell(_format_requirements(node)),
                    "—",
                ]
            ) + " |"
        )
    return "\n".join(lines) + "\n"


def _load_rules(root: Path) -> list[tuple[str, dict[str, Any]]]:
    rules_dir = root / "src/main/resources/data/rpgskilltree/node_rules"
    result: list[tuple[str, dict[str, Any]]] = []
    for path in sorted(rules_dir.glob("*.json")):
        payload = _read_json(path)
        tree_id = payload["treeId"]
        for node in payload["nodes"]:
            result.append((tree_id, node))
    result.sort(key=lambda value: (value[0], value[1]["id"]))
    return result


def _load_semantic_combat_snapshot(
    root: Path,
    required: bool,
) -> list[tuple[str, dict[str, Any]]]:
    path = root / SEMANTIC_COMBAT_SNAPSHOT
    if not path.is_file():
        if required:
            raise FileNotFoundError(f"missing derived semantic combat wiki snapshot: {path}")
        return []

    payload = _read_json(path)
    if not isinstance(payload, dict):
        raise ValueError(f"semantic combat snapshot root must be an object: {path}")
    if payload.get("schema") != 1:
        raise ValueError(f"semantic combat snapshot requires schema 1: {path}")
    tree_id = payload.get("treeId")
    if tree_id != SEMANTIC_COMBAT_TREE_ID:
        raise ValueError(
            f"semantic combat snapshot requires treeId {SEMANTIC_COMBAT_TREE_ID}: {path}"
        )
    nodes = payload.get("nodes")
    if not isinstance(nodes, list):
        raise ValueError(f"semantic combat snapshot requires nodes array: {path}")

    result: list[tuple[str, dict[str, Any]]] = []
    seen_ids: set[str] = set()
    seen_codes: set[str] = set()
    for index, node in enumerate(nodes):
        if not isinstance(node, dict):
            raise ValueError(f"semantic combat snapshot node {index} must be an object: {path}")
        for field in ("id", "code", "name", "description", "maxRank", "costPerRank"):
            if field not in node:
                raise ValueError(f"semantic combat snapshot node {index} missing {field}: {path}")

        node_id = node["id"]
        code = node["code"]
        name = node["name"]
        description = node["description"]
        if not isinstance(node_id, str) or not node_id:
            raise ValueError(f"semantic combat snapshot node {index} has invalid id: {path}")
        if not isinstance(code, str) or not code:
            raise ValueError(f"semantic combat snapshot node {index} has invalid code: {path}")
        if not isinstance(name, str) or not name.strip():
            raise ValueError(f"semantic combat snapshot node {node_id} has blank name: {path}")
        if description is not None and not isinstance(description, str):
            raise ValueError(f"semantic combat snapshot node {node_id} has invalid description: {path}")
        if not isinstance(node["maxRank"], int) or node["maxRank"] < 1:
            raise ValueError(f"semantic combat snapshot node {node_id} has invalid maxRank: {path}")
        if not isinstance(node["costPerRank"], int) or node["costPerRank"] < 0:
            raise ValueError(f"semantic combat snapshot node {node_id} has invalid costPerRank: {path}")
        for field in ("requiredMastery", "requiredNodeRanks"):
            value = node.get(field, {})
            if not isinstance(value, dict):
                raise ValueError(f"semantic combat snapshot node {node_id} has invalid {field}: {path}")

        if node_id in seen_ids:
            raise ValueError(f"semantic combat snapshot has duplicate node id {node_id}: {path}")
        if code in seen_codes:
            raise ValueError(f"semantic combat snapshot has duplicate code {code}: {path}")
        seen_ids.add(node_id)
        seen_codes.add(code)
        result.append((tree_id, node))

    result.sort(key=lambda value: (value[0], value[1]["code"], value[1]["id"]))
    return result


def _load_effects(root: Path) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    effects_dir = root / "src/main/resources/data/rpgskilltree/node_effects"
    attributes: list[dict[str, Any]] = []
    behaviors: list[dict[str, Any]] = []
    for path in sorted(effects_dir.glob("*.json")):
        payload = _read_json(path)
        attributes.extend(payload.get("attributes", []))
        behaviors.extend(payload.get("behaviors", []))
    return attributes, behaviors


def _load_translations(root: Path, locale: str) -> dict[str, str]:
    path = root / f"src/main/resources/assets/rpgskilltree/lang/{locale}.json"
    if not path.is_file():
        raise ValueError(f"missing locale file: {path}")
    payload = _read_json(path)
    if not isinstance(payload, dict):
        raise ValueError(f"locale root must be an object: {path}")
    return {str(key): str(value) for key, value in payload.items()}


def _has_nonblank_translation(translations: dict[str, str], key: str) -> bool:
    value = translations.get(key)
    return value is not None and bool(value.strip())


def _translation_key(node_id: str, suffix: str) -> str:
    namespace, separator, path = node_id.partition(":")
    if not separator or not namespace or not path:
        raise ValueError(f"invalid namespaced node id: {node_id}")
    return f"node.{namespace}.{path.replace('/', '.')}.{suffix}"


def _format_requirements(node: dict[str, Any]) -> str:
    parts: list[str] = []
    level = int(node.get("minCharacterLevel", 1))
    if level > 1:
        parts.append(f"Nível ≥ {level}")
    for value in sorted(node.get("requiredClasses", [])):
        parts.append(f"Classe: `{value}`")
    for mastery, rank in sorted(node.get("requiredMastery", {}).items()):
        parts.append(f"Mastery `{mastery}` ≥ {rank}")
    for value in sorted(node.get("requiredSpecializations", [])):
        parts.append(f"Especialização: `{value}`")
    for value in sorted(node.get("requiredClassChoices", [])):
        parts.append(f"Escolha: `{value}`")
    for value in sorted(node.get("requiredNodes", [])):
        parts.append(f"Nó: `{value}`")
    for value, rank in sorted(node.get("requiredNodeRanks", {}).items()):
        parts.append(f"Nó `{value}` rank ≥ {rank}")
    for value in sorted(node.get("requiredDiscoveries", [])):
        parts.append(f"Descoberta: `{value}`")
    if node.get("startingPoint", False):
        parts.append("Ponto inicial")
    return "; ".join(parts) if parts else "—"


def _format_perk_row(
    tree_id: str,
    node_id: str,
    name: str,
    description: str,
    node: dict[str, Any],
    effects: str,
) -> str:
    return "| " + " | ".join(
        [
            _cell(f"`{tree_id}`"),
            _cell(f"`{node_id}`"),
            _cell(name),
            _cell(description),
            str(node["maxRank"]),
            str(node["costPerRank"]),
            _cell(_format_requirements(node)),
            _cell(effects),
        ]
    ) + " |"


def _format_attribute_summary(effect: dict[str, Any]) -> str:
    return (
        f"`{effect['attributeId']}` {_format_amount(effect['operation'], effect['amountPerRank'])} "
        f"({effect['operation']})"
    )


def _format_amount(operation: str, amount: Any) -> str:
    value = float(amount)
    if operation in {"ADD_PERCENT_BASE", "MULTIPLY_TOTAL"}:
        return f"{_signed_number(value * 100)}%/rank"
    return f"{_signed_number(value)}/rank"


def _signed_number(value: float) -> str:
    normalized = f"{abs(value):.10f}".rstrip("0").rstrip(".")
    if normalized == "":
        normalized = "0"
    sign = "+" if value >= 0 else "-"
    return sign + normalized


def _cell(value: Any) -> str:
    return str(value).replace("|", "\\|").replace("\n", " ")


def _read_json(path: Path) -> Any:
    return json.loads(path.read_text(encoding="utf-8"))
