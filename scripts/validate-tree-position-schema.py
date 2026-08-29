#!/usr/bin/env python3
import json
import math
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
TREE_DIR = ROOT / 'src/main/resources/assets/rpgskilltree/tree'


def fail(path: Path, node_id: str, field: str, detail: str) -> None:
    raise SystemExit(f'Tree position schema: FAIL {path}: node={node_id} field={field}: {detail}')


def validate_node(path: Path, node: object, index: int) -> None:
    if not isinstance(node, dict):
        fail(path, f'<index:{index}>', 'node', 'must be an object')
    node_id = node.get('id', f'<index:{index}>')
    if not isinstance(node_id, str) or not node_id:
        fail(path, f'<index:{index}>', 'id', 'must be a non-empty string')
    for field in ('x', 'y'):
        if field not in node:
            fail(path, node_id, field, 'field is required')
        value = node[field]
        if isinstance(value, bool) or not isinstance(value, (int, float)):
            fail(path, node_id, field, 'must be numeric')
        if not math.isfinite(float(value)):
            fail(path, node_id, field, 'must be finite')


def main() -> None:
    validated_files = 0
    validated_nodes = 0
    for path in sorted(TREE_DIR.glob('*.json')):
        payload = json.loads(path.read_text(encoding='utf-8'))
        nodes = payload.get('nodes')
        if nodes is None:
            continue
        if not isinstance(nodes, list):
            raise SystemExit(f'Tree position schema: FAIL {path}: nodes must be a list')
        for index, node in enumerate(nodes):
            validate_node(path, node, index)
        validated_files += 1
        validated_nodes += len(nodes)

    if validated_files == 0:
        raise SystemExit('Tree position schema: FAIL no tree assets with nodes found')
    print(f'Tree position schema: PASS ({validated_files} tree assets, {validated_nodes} nodes)')


if __name__ == '__main__':
    main()
