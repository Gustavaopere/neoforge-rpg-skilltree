# RPG Core Plan — Persistence and Sync

**Goal:** preservar progressão entre sessões e sincronizar somente o necessário ao cliente.

- [ ] Definir versão de schema persistido.
- [ ] Implementar/testar round-trip de save/load.
- [ ] Preparar política de migração para versões futuras.
- [ ] Definir tratamento explícito para dados desconhecidos/corrompidos.
- [ ] Sincronizar login, respawn, dimension change, compra e respec.
- [ ] Cliente recebe estado para UI, mas não autoridade para mutação.

**Acceptance:** restart não perde progresso e cliente/servidor convergem após todas as transições relevantes.