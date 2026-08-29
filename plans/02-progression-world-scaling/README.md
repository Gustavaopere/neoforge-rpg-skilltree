# 02 — Progression & World Scaling

Este estágio faz a ameaça do mundo acompanhar a progressão sem converter todo inimigo em esponja de vida.

Ordem: relevant player level → área/território → nível da entidade → raridade/arquétipo → fórmulas, recompensas e performance.

**Status: CONCLUÍDO.**

Todos os cinco subplanos estão implementados e formalmente fechados:

- `✅-01-relevant-player-level.md`
- `✅-02-territory-area-level.md`
- `✅-03-entity-level.md`
- `✅-04-rarity-archetypes.md`
- `✅-05-scaling-rewards-performance.md`

O fechamento final valida lifecycle persistido de entidades, Effective Stats idempotentes após save/load, rarity/archetype estáveis, curvas independentes e capped, XP/loot pela mesma reward policy bounded, multiplayer local/party sem vazamento de jogador global irrelevante e budgets algorítmicos dos hot paths de spawn/consulta/reuso.
