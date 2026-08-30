# A0055 — Treino com Armas de Punho I

## Estado

- **Design:** APROVADO após correção de classificação/Mastery.
- **Notion:** `3c569db9-f0db-8137-9c8f-cd4c54ad59ef`.
- **Runtime:** NÃO CONFIRMADO como progressão adquirível; damage classifier existe, mas gateway/Mastery estão desalinhados.

## Contrato canônico

- Nível 8 + `combat:fist` ≥60 + gateway `combat_fist`.
- +3% dano FIST por rank, máximo +9%.
- Categoria FIST/knuckle somente provider-native ou mapping versionado explícito; mãos vazias não contam por padrão.
- Não usar `rpgskilltree:fist_weapons`, nome, aparência, tooltip, attack speed ou Punchy como classificador.
- Mastery `combat:fist`: +10 uma única vez por tipo hostil inédito; 6 tipos =60; 8 tipos =80 para A0060.

## Evidência runtime

`A0041A0060EpicFightHooks.family(...)` classifica `fist`/`knuckle` provider-native e o pipeline de dano existe. Entretanto `EpicFightProgressionHooks` gera milestones genéricos como `epicfight:<categoria>`, portanto um hit FIST tende a alimentar `epicfight:fist`, enquanto `CombatPerkTreeModel` e o Notion exigem `combat:fist`.

Além disso, `src/main/resources/data/rpgskilltree/tree_architecture/combat.json` não publica uma árvore `rpgskilltree:combat_fist`, embora `CombatPerkTreeModel` use o gateway `combat_fist` para A0055–A0060.

## Pendências para Chat 2

- **P-A0055-01:** criar/reconciliar producer de discovery finita para a ledger única `combat:fist`; impedir `epicfight:fist` paralelo para a mesma disciplina.
- **P-A0055-02:** publicar/reconciliar `combat_fist` no architecture catalog com o mesmo gateway, domínio, Mastery e topologia do modelo/Notion.
- **P-A0055-03:** regressão architecture↔model↔Notion↔producer e classificação FIST provider-present/absent.

## Boundaries

Punchy é visual/compat. `ARCANE_BACKLASH`, summons, procs e allies/bodyguards Mobstein não geram dano/Mastery FIST do jogador.

## Notion

Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS.
