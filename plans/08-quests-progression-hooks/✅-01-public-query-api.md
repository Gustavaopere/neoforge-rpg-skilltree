# Quest Hooks Plan — Public Query API

**Goal:** expor progresso do jogador para quests/addons sem permitir acesso direto ao storage interno.

- [x] Consultar level, XP, pontos, perks, classes, masteries e especializações.
- [x] Retornar snapshots/read-only views.
- [x] Definir semântica para ID inexistente/provider ausente.
- [x] Evitar expor detalhes de implementação do persistence layer.
- [x] Versionar contrato público quando necessário.

## Contrato implementado

- `RpgQuestProgressionApi.query(ServerPlayer)` é a façade pública server-side para quests, NPCs e adapters narrativos.
- `QuestProgressionSnapshot` é imutável e expõe o `CoreProgressionQuerySnapshot` (level, XP, Core Progression Points e attributes), mastery XP, classes desbloqueadas, especializações desbloqueadas e ranks de perks.
- `QuestProgressionSnapshot.CONTRACT_VERSION` e `RpgQuestProgressionApi.CONTRACT_VERSION` fixam a revisão pública inicial em `1`.
- Consultas observam o envelope canônico e o Core por rotas read-only; não materializam migration, não escrevem attachments e não disparam sync como efeito colateral.
- IDs válidos mas inexistentes/indisponíveis resolvem de forma fail-closed: mastery/perk rank retornam `0`, class/specialization retornam `false`; não há dependência de classes de providers para realizar a consulta.
- `QuestProgressionCondition` / `QuestProgressionConditionService` oferecem condições declarativas para level, mastery, class, specialization, perk rank e attribute rank.
- `QuestProgressionHooksFoundationTest` é executado explicitamente em `scripts/test-core.sh`, cobrindo imutabilidade, especializações, IDs ausentes, contract version e valores `long` em escala multi-bilhão.

## Evidência

- PR #97 — `Quest progression hooks foundation` — merge `ace51ae1c147ddec77c4a766ceabbcbe3fa7d208`.
  - RPG Skill Tree CI `33225421326`: GREEN, incluindo NeoForge build, JAR verification e dedicated-server smoke.
  - Compendium Discovery `33225421309`, Flora `33225421305` e Entities `33225421312`: GREEN.
- PR #98 — `Complete quest public API specialization coverage` — merge `2b8e5d10b70704598c0f175a3a9bf1ad0af5586e`.
  - RPG Skill Tree CI `33227098892`: GREEN, incluindo Core tests, validators, NeoForge build, JAR verification e dedicated-server smoke.
  - Compendium Discovery `33227098912`, Flora `33227099162` e Entities `33227098974`: GREEN.

**Acceptance: satisfied.** Addons conseguem verificar requisitos por contrato público imutável/versionado sem depender de classes internas mutáveis ou do persistence layer.
