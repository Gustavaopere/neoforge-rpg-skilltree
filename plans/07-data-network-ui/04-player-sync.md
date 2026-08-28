# Data/UI Plan — Player RPG Sync

**Goal:** manter a UI fiel ao estado server-authoritative sem tráfego por tick.

- [ ] Definir snapshot mínimo de level, XP, pontos, classes, masteries e unlocks necessários.
- [ ] Enviar em login/respawn/dimension change quando necessário.
- [ ] Atualizar após compra, respec, mastery threshold e mudança de classe.
- [ ] Coalescer mudanças próximas quando seguro.
- [ ] Medir tamanho/frequência em multiplayer.

**Acceptance:** cliente nunca permanece stale após uma ação confirmada e o servidor não envia o estado inteiro a cada tick.