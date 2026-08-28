# RPG Core Plan — Progression Services

**Goal:** fazer toda mutação de progressão passar por serviços canônicos.

- [x] Uma rota para conceder/remover XP.
- [x] Uma rota para level-up e recompensas associadas.
- [x] Uma rota para mastery/progresso especializado.
- [x] Impedir adapters/quests de escrever storage diretamente.
- [x] Definir idempotência para ações que podem emitir múltiplos eventos auxiliares.
- [x] Emitir eventos internos somente após mutação confirmada.

## Contrato implementado

- RPG XP normal usa grants não-negativos; correções administrativas usam `rollbackXp(...)` explícito, exato e fail-closed.
- `CharacterProgressionService` resolve grant e rollback por thresholds da curva, incluindo transições de múltiplos níveis sem loop proporcional à quantidade de levels atravessados.
- Level-up e Core Progression Points derivados são aplicados atomicamente pelas regras Core.
- Rollback de XP não revoga implicitamente Core Progression Points históricos; eventual rollback econômico é uma operação administrativa separada.
- Mastery usa replay keys persistentes e rejeita reutilização conflitante da mesma identidade de replay.
- Runtimes e adapters convergem em `CanonicalPlayerAttachmentRuntime.commitMutation(...)`; writes diretos fora do boundary são bloqueados pelos validators.
- Mutações no-op não persistem nem publicam eventos internos.
- `ProgressionMutationEvent` é publicado somente depois da persistência canônica confirmada.
- `CorePlayerProgressionRuntime.rollbackXp(...)` é uma rota trusted-server/admin; não existe packet de gameplay para o cliente solicitar rollback de XP.
- Adapters Eidolon de ritual/alquimia solicitam mutações canônicas e não gravam storage de progressão diretamente.

## Verificação

- PR #74 → merge `e09240f3ea2889070f57f72845e5a84aaefdf14d`.
  - head RPG CI `33210112181` GREEN;
  - head Compendium Discovery CI `33210112188` GREEN;
  - pós-merge RPG CI `33211988985` GREEN, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.
- PR #77 → merge `10403670fb10f7fdfb6ae9f00ca56405db8bb491`.
  - RED `237b0cecacef6986fad4a7a1f3db4e7840ca3b64` confirmado no Core tests;
  - GREEN head `ef370994c817bb03b56913876603f48af7c0be19`;
  - head RPG CI `33212716291` GREEN;
  - head Compendium Discovery CI `33212716265` GREEN;
  - pós-merge RPG CI `33212979768` GREEN completo: Core tests, Compendium tests, validators, generated-data drift/diff sanity, NeoForge build, JAR, dedicated-server smoke e final build status.

**Acceptance: satisfied.** O mesmo evento auxiliar/replay protegido não duplica mastery/recompensas, XP possui grant e rollback canônicos, e adapters não contornam o core para persistir progressão.
