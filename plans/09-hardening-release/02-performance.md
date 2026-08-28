# Hardening Plan — Performance

**Goal:** medir e limitar custos de sistemas que podem escalar com jogadores, mobs ou tamanho do mundo.

- [ ] Medir spawn/world scaling e busca de relevant player.
- [ ] Medir combat/magic hooks sob carga.
- [ ] Medir mastery/event handlers.
- [ ] Medir reload e sync da árvore 512-node.
- [ ] Eliminar scans globais/per-tick desnecessários.
- [ ] Definir budgets de regressão para hot paths prioritários.

**Acceptance:** nenhum hot path crítico depende de crescimento não limitado e regressões mensuráveis bloqueiam release.