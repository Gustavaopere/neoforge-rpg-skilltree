# RPG Core Plan — Canonical Player State

**Goal:** consolidar todos os dados persistentes de RPG do jogador em um modelo coerente.

- [x] Definir campos canônicos de level, XP, pontos de progressão, masteries, unlocks e identidades.
- [x] Definir defaults e invariantes por campo.
- [x] Rejeitar/normalizar estado impossível ou negativo conforme regra.
- [x] Separar estado persistente de caches/projeções derivados.
- [x] Definir snapshot somente-leitura para consumidores.

## Contrato implementado

- `CanonicalPlayerAttachmentData` é o envelope persistente único do jogador. A seção Core é autoritativa para Character Level, RPG XP, Core Progression Points, atributos fundamentais, Perk Budget e reward claims; a seção de compatibilidade mantém temporariamente domínios ainda não migrados como mastery/classes/tree dentro do mesmo envelope, sem criar uma segunda localização persistente normal.
- `ModAttachments.CANONICAL_PLAYER` é a única fronteira de escrita persistente normal. `PROGRESSION` e `CORE_PROGRESSION` permanecem registrados exclusivamente para leitura/migração de saves anteriores.
- `CanonicalPlayerAttachmentRuntime` centraliza observação, materialização de migração e escrita. Os runtimes de progressão legado/Core não escrevem os attachments antigos.
- `CanonicalPlayerState`, `CoreProgressionState` e os value objects associados impõem defaults, null-safety, não-negatividade, checked arithmetic e validação de regras/fingerprint nas fronteiras relevantes.
- `CanonicalPlayerSnapshot` + `CanonicalPlayerQueryService` expõem uma projeção imutável para consumidores. Level/XP/CPP/atributos vêm exclusivamente de `CoreProgressionQuerySnapshot`; domínios ainda em compatibilidade são projetados individualmente, sem expor `ProgressionState`, `PassivePointLedger`, `totalCharacterXp` ou outro authority legado.
- `CanonicalPlayerQueryRuntime.query(ServerPlayer)` observa o envelope sem persistir migração, escrever attachment ou sincronizar cliente como efeito colateral de uma consulta.
- Dados derivados de UI/query/effective stats não são persistidos como cópias do estado do jogador.

## Evidência

- Consolidação do attachment canônico: PR #43 e validators `verify-canonical-player-runtime.py`.
- Dimension-change sync do envelope canônico: merge `d088375c49141fe4d2ee5fc495a00e0b5e36c5df`, CI `33195966114` GREEN completo.
- Snapshot/query canônico reconciliado: PR #72, head `0e50a2c1c292e906ce9d4c6319f4666371280145`, CI `33198679352` GREEN completo.
- Merge da query canônica em `main`: `5171ec7e099be545663b4a1ac989c36fc68835eb`.
- Auditoria de writes: não há produção escrevendo `ModAttachments.PROGRESSION` ou `ModAttachments.CORE_PROGRESSION`; ambos são somente inputs de migração no `CanonicalPlayerAttachmentRuntime`.

**Acceptance: satisfied.** Todos os sistemas persistem/consultam o mesmo envelope canônico; não existem cópias concorrentes de progressão como fonte normal de verdade. A existência temporária de modelos internos de compatibilidade dentro desse envelope é uma estratégia explícita de migração e não uma segunda persistência.
