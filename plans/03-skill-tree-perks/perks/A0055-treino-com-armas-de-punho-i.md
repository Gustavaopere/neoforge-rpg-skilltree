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
- A ledger paralela `epicfight:fist` não pode coexistir como segunda progressão da mesma disciplina.

## Evidência runtime

`A0041A0060EpicFightHooks.family(...)` classifica `fist`/`knuckle` provider-native e o pipeline de dano existe. Entretanto `EpicFightProgressionHooks` gera milestones genéricos como `epicfight:<categoria>`, portanto um hit FIST tende a alimentar `epicfight:fist`, enquanto `CombatPerkTreeModel` e o Notion exigem `combat:fist`.

Além disso, `src/main/resources/data/rpgskilltree/tree_architecture/combat.json` não publica uma árvore `rpgskilltree:combat_fist`, embora `CombatPerkTreeModel` use gateway `combat_fist` para A0055–A0060.

## Pendências para Chat 2

- **P-A0055-01:** criar/reconciliar producer de discovery finita para a ledger única `combat:fist`; impedir `epicfight:fist` paralelo para a mesma disciplina.
- **P-A0055-02:** publicar/reconciliar `combat_fist` no architecture catalog com o mesmo gateway, domínio, Mastery e topologia do modelo/Notion.
- **P-A0055-03:** regressão architecture↔model↔Notion↔producer e classificação FIST provider-present/absent.
- Ao reconciliar ranks/rules do ramo, impedir que Mastery/gateway alternativo reative estado de perks descendentes sem pré-requisitos válidos.

## Boundaries

Punchy é visual/compat. `ARCANE_BACKLASH`, summons, procs e allies/bodyguards Mobstein não geram dano/Mastery FIST do jogador.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado individual | Evidência / decisão |
|---|---|---|
| 1. Dependências, bloqueios e gates | **PASS no design** | Nível 8 + `combat:fist` 60 + gateway `combat_fist`; desalinhamento runtime é blocker explícito, não bypass aceito. |
| 2. Integração global | **PASS** | Dano usa pipeline MARTIAL canônico; Mastery é discovery finita e causal; Punchy, magia, hazards e companions não alimentam o ramo. |
| 3. Qualidade e identidade | **PASS** | Node fundacional/ranked de entrada FIST; bônus pequeno é compatível com função estrutural e prepara o ramo sem fingir ser Notable. |
| 4. Ramificação, distância e topologia | **PASS no design** | Entrada do ramo `combat_fist`; architecture ausente foi catalogada em `P-A0055-02` e precisa ser materializada sem teleporte/atalho. |
| 5. Especializações | **PASS** | Armas de Punho permanecem subdisciplina MARTIAL; Epic Fight/Punchy/WoM não viram classes automaticamente. |
| 6. PT-BR | **PASS** | Nome/efeito/gates visíveis em PT-BR; IDs técnicos permanecem em inglês. |
| 7. Notion completo | **PASS** | Gate/Hook/Fallback/Regra completos e re-fetch confirmado; nenhuma mutação posterior necessária nesta rodada. |
| 8. NeoVitae | **PASS** | Ausente. |
| 9. Cobertura modlist/providers | **PASS** | Epic Fight/WoM/Punchy e own-projects/Mobstein foram classificados; classificação externa é provider-native/fail-closed. |

Os 18 critérios técnicos cumulativos passam **no design**; `P-A0055-01/02/03` impedem confirmação runtime até producer, architecture e regressões estarem alinhados.

## Notion

Gate, Hook, Fallback e Regra corrigidos; re-fetch PASS em 2026-08-30.
