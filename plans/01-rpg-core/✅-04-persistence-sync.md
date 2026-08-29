# RPG Core Plan — Persistence and Sync

**Goal:** preservar progressão entre sessões e sincronizar somente o necessário ao cliente.

- [x] Definir versão de schema persistido.
- [x] Implementar/testar round-trip de save/load.
- [x] Preparar política de migração para versões futuras.
- [x] Definir tratamento explícito para dados desconhecidos/corrompidos.
- [x] Sincronizar login, respawn, dimension change, compra e respec.
- [x] Cliente recebe estado para UI, mas não autoridade para mutação.

**Acceptance:** restart não perde progresso e cliente/servidor convergem após todas as transições relevantes.

## Contrato implementado

- `CanonicalPlayerAttachmentDataCodec.CURRENT_VERSION` versiona explicitamente o envelope persistido; `CanonicalPlayerAttachmentMigrations` concentra a cadeia obrigatória de migrações antes de qualquer futura mudança de versão.
- `ModAttachments.CANONICAL_PLAYER` é o destino persistente autoritativo normal, usa `CanonicalPlayerAttachmentSerializer` e `copyOnDeath`; os attachments antigos permanecem apenas como entradas de migração.
- `CanonicalPlayerAttachmentSerializer` delega diretamente para `CanonicalPlayerAttachmentDataCodec.encode/decode`, portanto o round-trip testado é o mesmo codec registrado no `AttachmentType` real do NeoForge.
- `CanonicalPlayerAttachmentRuntime` materializa o envelope canônico, remove cópias legadas, rejeita mutações stale e publica uma mutação somente depois do write aceito.
- `CanonicalPlayerAttachmentDataTest` cobre round-trip do envelope vazio, inicializado e de um estado rico com XP, pontos, boss, classe, mastery, especialização, ranks de perks e discoveries. Payload truncado, com bytes extras ou versão futura desconhecida falha fechado.
- `PlayerProgressionEvents` reconcilia login/respawn e reenvia snapshots em mudança de dimensão; compras/respecs confirmados passam pela mutation boundary e são sincronizados pela fila de owner sync.
- `ProgressionOwnerSyncRuntime` coalesce mutações no mesmo tick e `ProgressionOwnerSyncEvents` faz flush em `ServerTickEvent.Post`, limpando estado pendente em logout/server stop.
- `verify-persistence-sync-contract.py`, executado por `scripts/test-core.sh`, impede regressões no schema/migration boundary, no destino canônico de persistência, nos pontos de lifecycle sync e na autoridade servidor-cliente. Todos os payloads cliente→servidor são intenções mínimas processadas com `ServerPlayer` e não carregam `ProgressionState`/`CoreProgressionState` autoritativos.

## Evidência de integração e verificação

- Implementação funcional consolidada pelo PR #128, head `a02b76ae004aa48e3fe2efef01c33c31f1fd267f`, mergeado na `main` como `461f386179a3c904f52793354998c29ff7979dd7`.
- O head funcional passou o RPG Skill Tree CI `33244574779` GREEN e os workflows Foundation/Compendium associados também fecharam GREEN.
- O pós-merge canônico em `main@461f386179a3c904f52793354998c29ff7979dd7` passou o RPG Skill Tree CI `33244700777` / run #1341 GREEN completo.
- O job `verify` do CI pós-merge confirmou Core tests, JUnit 5, NeoForge GameTests, todos os validators relevantes, generated-data drift, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final de sucesso.
- Auditoria posterior na `main` confirmou que `ModAttachments.CANONICAL_PLAYER` continua registrado com `CanonicalPlayerAttachmentSerializer.INSTANCE` e `copyOnDeath`, enquanto o serializer continua usando diretamente o codec canônico testado.

## Interpretação do acceptance

A retenção entre sessões é verificada na fronteira efetivamente serializada pelo NeoForge: encode/decode do `CanonicalPlayerAttachmentData` completo usando o mesmo codec registrado no `AttachmentType`, complementado pelo dedicated-server smoke do CI. Não existe dependência de memória estática para recuperar o estado persistido após carregamento.

A convergência cliente/servidor é coberta pelos lifecycle hooks de login, respawn e dimension change, pela fila de owner sync para mutações confirmadas como compra/respec e pelo contrato de rede que mantém o cliente sem autoridade de estado.

**Acceptance: satisfied.**
