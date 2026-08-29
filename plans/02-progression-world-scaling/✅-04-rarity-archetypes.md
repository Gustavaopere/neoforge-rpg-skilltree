# World Scaling Plan — Rarity and Archetypes

**Goal:** criar variedade de encontro sem depender apenas de multiplicadores lineares.

- [x] Definir quando raridade/arquetipagem é rolada.
- [x] Persistir seleção para impedir reroll em reload.
- [x] Aplicar modifiers idempotentes.
- [x] Separar raridade de arquétipo para permitir combinações controladas.
- [x] Definir tratamento específico/fallback para bosses.

## Evidência técnica

- O entry point canônico é `EntityScalingEvents.onEntityJoinLevel`: no servidor, entidades vivas não-player só entram no initializer quando ainda não existe `EntityScalingState` persistido.
- `EntityScalingRuntime.getOrInitialize` dá precedência ao attachment `ENTITY_SCALING` já persistido e não invoca o initializer novamente em reload/re-add, impedindo reroll de rarity.
- `EntityScalingState` persiste `Optional<MobRaritySelection> rarity`, `deterministicSeed`, Effective Stats, affixes e behaviors junto da decisão completa de scaling.
- `DeterministicWeightedMobRarityPolicy` resolve seleção ponderada deterministicamente a partir de `MobRarityContext.deterministicSeed`, filtra regras por floor/archetype, mantém rarity separada de `EntityArchetype` e possui `bossFallback` explícito além do fallback geral.
- `MobRarityRule` mantém a tabela de regras como dados fornecidos pelo caller; o Core não congela chances/tier bonuses de balanceamento.
- `EntityArchetypeRuntimeClassifier` mantém fallback conservador `SPECIAL` para entidades externas/desconhecidas e tratamento explícito para bosses.

## Modifiers idempotentes

A pendência causal foi fechada pelo Stage 02.05:

- `EntityEffectiveStatsRuntime.refresh(...)` usa IDs determinísticos `rpgskilltree:entity_scaling/<stat>`.
- Cada refresh remove primeiro o modifier anterior e então chama `addOrReplacePermanentModifier`, impedindo stacking em load/reload e em reaplicações repetidas.
- O snapshot persistido continua sendo a autoridade; a camada de atributos vanilla é apenas uma projeção reconciliada.
- `EntityEffectiveStatsGameTests.persistedEffectiveStatsReapplyWithoutStacking` cobre primeira aplicação, reaplicação, round-trip persistido e nova reaplicação após save/load.

## Evidência TDD / CI

- `DeterministicWeightedMobRarityPolicyJUnitTest` cobre repetibilidade por seed, fronteiras de peso, filtros, fallbacks, regras inválidas e overflow.
- CI do slice de rarity: RPG Skill Tree CI #1450 (`33264906690`) no head `8fc9932c0c583e4c6917ef872944ac37dbd3fe95`, GREEN em Core, JUnit, GameTests, build, JAR e dedicated-server smoke.
- No acceptance final do Stage 02, RPG Skill Tree CI #1589 (`33271450823`) passou Core, JUnit, todos os 11 NeoForge GameTests, validators, build/JAR e dedicated-server smoke; isso inclui a prova de reaplicação idempotente de Effective Stats.
- A cobertura multiplayer/performance correspondente foi integrada em `main@98c5f33952559ea9eea059169b7f486837fc20f1` sem alterar balanceamento.

**Acceptance: satisfied.** Uma entidade não muda de rarity/archetype ao recarregar e os modifiers derivados são reconciliados idempotentemente a partir do estado persistido.
