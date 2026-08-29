# 02 — Progression & World Scaling

Este estágio faz a ameaça do mundo acompanhar a progressão sem converter todo inimigo em esponja de vida.

Ordem: relevant player level → área/território → nível da entidade → raridade/arquétipo → fórmulas, recompensas e performance.

**Status: CONCLUÍDO quando este fechamento integrar a `main`.**

Todos os cinco subplanos estão implementados e materializados neste fechamento:

- `✅-01-relevant-player-level.md`
- `✅-02-territory-area-level.md`
- `✅-03-entity-level.md`
- `✅-04-rarity-archetypes.md`
- `✅-05-scaling-rewards-performance.md`

O fechamento valida lifecycle persistido de entidades, Effective Stats idempotentes após save/load, rarity/archetype estáveis, curvas independentes e capped, XP/loot pela mesma reward policy bounded, multiplayer local/party sem vazamento de jogador global irrelevante e budgets algorítmicos dos hot paths de spawn/consulta/reuso.

A contagem canônica de `plans/STATUS.md` só deve subir de 2/5 para 5/5 após este PR estar efetivamente integrado, em conformidade com a regra de que branch/PR aberto não conta como concluído.
