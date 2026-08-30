# 02 — Progression & World Scaling

Este estágio faz a ameaça do mundo acompanhar a progressão sem converter todo ser vivo em esponja de vida.

Ordem histórica concluída: relevant player level → área/território → nível da entidade → raridade/arquétipo → fórmulas, recompensas e performance.

**Status do núcleo 02.01–02.05: CONCLUÍDO.**

Subplanos implementados:

- `✅-01-relevant-player-level.md`
- `✅-02-territory-area-level.md`
- `✅-03-entity-level.md`
- `✅-04-rarity-archetypes.md`
- `✅-05-scaling-rewards-performance.md`

O fechamento histórico valida lifecycle persistido, Effective Stats idempotentes após save/load, rarity/archetype estáveis, curvas capped, reward policy bounded, multiplayer local/party e budgets algorítmicos.

## Complemento aberto — 02.06

A revisão de gameplay posterior identificou uma correção de semântica que **não invalida** o núcleo concluído: nem toda entidade viva deve receber o mesmo tipo de scaling. O subplano `06-entity-scaling-eligibility-minecolonies.md` introduz uma política explícita de elegibilidade:

- `COMBATANT_FULL`: hostis, bosses, guardas, raiders e combatentes reais recebem scaling completo;
- `NONCOMBATANT_DEFENSIVE`: civis importantes, especialmente colonos MineColonies, podem receber apenas sobrevivência defensiva;
- `UNSCALED`: fauna passiva e não combatentes comuns permanecem com atributos vanilla/provider.

Isso garante que uma vaca ou galinha não se torne artificialmente resistente só porque o jogador possui nível alto, sem deixar cidadãos MineColonies indefesos contra ameaças escaladas.

O 02.06 é um trabalho novo e aberto; os arquivos `✅` anteriores continuam representando corretamente o que já foi integrado.