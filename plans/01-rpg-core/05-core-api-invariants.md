# RPG Core Plan — Internal API and Invariants

**Goal:** oferecer contratos estáveis para todos os estágios posteriores.

- [ ] Separar queries de commands/mutations.
- [ ] Expor snapshots imutáveis para UI/scaling/adapters.
- [ ] Documentar invariantes de level/XP/mastery/unlocks.
- [ ] Impedir dependência do core em classes de UI ou mods opcionais.
- [ ] Cobrir limites de XP, rank, pontos e IDs em testes.

**Acceptance:** consumidores usam API explícita e mudanças internas de storage não vazam para integrações.