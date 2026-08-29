#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DATA = ROOT / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/data'


def require(path: Path, needle: str) -> None:
    text = path.read_text(encoding='utf-8')
    if needle not in text:
        raise SystemExit(f'Skill-tree reload contract: FAIL {path}: missing {needle}')


def forbid(path: Path, needle: str) -> None:
    text = path.read_text(encoding='utf-8')
    if needle in text:
        raise SystemExit(f'Skill-tree reload contract: FAIL {path}: forbidden direct publication {needle}')


def main() -> None:
    node_rules = DATA / 'NodeRulesReloader.java'
    architecture = DATA / 'TreeArchitectureReloader.java'
    effects = DATA / 'NodeEffectsReloader.java'
    transaction = DATA / 'SkillTreeDataReloadTransaction.java'

    require(node_rules, 'SkillTreeDataReloadTransaction.begin()')
    require(node_rules, 'SkillTreeDataReloadTransaction.stageNodeRules(')
    forbid(node_rules, 'TreeRuleCatalog.replace(')

    require(architecture, 'SkillTreeDataReloadTransaction.stageTrees(')
    forbid(architecture, 'TreeArchitectureCatalog.replace(')

    require(effects, 'SkillTreeDataReloadTransaction.stageEffects(')
    require(effects, 'SkillTreeDataReloadTransaction.commit()')
    forbid(effects, 'NodeEffectCatalog.replace(')

    require(transaction, 'TreeArchitectureCatalog.PreparedSnapshot architecture')
    require(transaction, 'TreeRuleCatalog.PreparedSnapshot rules')
    require(transaction, 'NodeEffectCatalog.PreparedSnapshot effects')
    prepare = transaction.read_text(encoding='utf-8').index('NodeEffectCatalog.PreparedSnapshot effects')
    first_publish = transaction.read_text(encoding='utf-8').index('TreeArchitectureCatalog.publish(architecture)')
    if prepare > first_publish:
        raise SystemExit('Skill-tree reload contract: FAIL publication begins before every catalog is prepared')

    print('Skill-tree reload contract: PASS (staged validation + prepare-before-publish wiring)')


if __name__ == '__main__':
    main()
