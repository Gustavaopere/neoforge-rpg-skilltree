#!/usr/bin/env python3
from __future__ import annotations

import json
from pathlib import Path
from typing import Any

NAMESPACE = "rpgskilltree"


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
        requirements = _format_requirements(node)
        effects = [
            _format_attribute_summary(effect)
            for effect in sorted(attributes_by_node.get(node_id, []), key=lambda value: value["effectId"])
        ]
        effects.extend(
            f"handler `{effect['handlerId']}`"
            for effect in sorted(behaviors_by_node.get(node_id, []), key=lambda value: value["effectId"])
        )
        perk_lines.append(
            "| " + " | ".join(
                [
                    _cell(f"`{tree_id}`"),
                    _cell(f"`{node_id}`"),
                    _cell(name),
                    _cell(description),
                    str(node["maxRank"]),
                    str(node["costPerRank"]),
                    _cell(requirements),
                    _cell("; ".join(effects) if effects else "—"),
                ]
            ) + " |"
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

    if check and changed:
        raise WikiCatalogDriftError(changed)
    if not check:
        for path, updated in updates:
            path.write_text(updated, encoding="utf-8")
    return changed


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
