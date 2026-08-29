# RPG Core Plan — Persistence and Sync

**Goal:** preservar progressão entre sessões e sincronizar somente o necessário ao cliente.

- [x] Definir versão de schema persistido.
- [x] Implementar/testar round-trip de save/load.
- [x] Preparar política de migração para versões futuras.
- [x] Definir tratamento explícito para dados desconhecidos/corrompidos.
- [x] Sincronizar login, respawn, dimension change, compra e respec.
- [x] Cliente recebe estado para UI, mas não autoridade para mutação.

**Acceptance:** restart não perde progresso e cliente/servidor convergem após todas as transições relevantes.

## Evidência de implementação

- `CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION` versiona explicitamente o envelope persistido; `CanonicalPlayerAttachmentMigrations` concentra a cadeia obrigatória de migrações antes de qualquer futura mudança de versão.
- `ModAttachments.CANONICAL_PLAYER` é o destino persistente autoritativo normal, usa `CanonicalPlayerAttachmentSerializer` e `copyOnDeath`; os attachments antigos permanecem apenas como entradas de migração.
- `CanonicalPlayerAttachmentRuntime` materializa o envelope canônico, remove cópias legadas, rejeita mutações stale e publica uma mutação somente depois do write aceito.
- `CanonicalPlayerAttachmentDataTest` cobre round-trip do envelope vazio, inicializado e de um estado rico com XP, pontos, boss, classe, mastery, especialização, ranks de perks e discoveries. Payload truncado, com bytes extras ou versão futura desconhecida falha fechado.
- `PlayerProgressionEvents` reconcilia login/respawn e reenvia snapshots em mudança de dimensão; compras/respecs confirmados passam pela mutation boundary e são sincronizados pela fila de owner sync.
- `ProgressionOwnerSyncRuntime` coalesce mutações no mesmo tick e `ProgressionOwnerSyncEvents` faz flush em `ServerTickEvent.Post`, limpando estado pendente em logout/server stop.
- `verify-persistence-sync-contract.py`, executado por `scripts/test-core.sh`, impede regressões no schema/migration boundary, no destino canônico de persistência, nos pontos de lifecycle sync e na autoridade servidor-cliente. Todos os payloads cliente→servidor são intenções mínimas processadas com `ServerPlayer` e não carregam `ProgressionState`/`CoreProgressionState` autoritativos.

## Interpretação do acceptance

A retenção entre sessões é verificada na fronteira efetivamente serializada pelo NeoForge: encode/decode do `CanonicalPlayerAttachmentData` completo usando o mesmo codec registrado no `AttachmentType`, complementado pelo dedicated-server smoke do CI. Não existe dependência de memória estática para recuperar o estado persistido após carregamento.
