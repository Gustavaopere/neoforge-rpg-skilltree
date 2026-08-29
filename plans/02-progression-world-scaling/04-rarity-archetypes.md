# World Scaling Plan — Rarity and Archetypes

**Goal:** criar variedade de encontro sem depender apenas de multiplicadores lineares.

- [x] Definir quando raridade/arquetipagem é rolada.
- [x] Persistir seleção para impedir reroll em reload.
- [ ] Aplicar modifiers idempotentes.
- [x] Separar raridade de arquétipo para permitir combinações controladas.
- [x] Definir tratamento específico/fallback para bosses.

## Evidência técnica

- O entry point canônico continua sendo `EntityScalingEvents.onEntityJoinLevel`: no servidor, entidades vivas não-player só entram no initializer quando ainda não existe `EntityScalingState` persistido.
- `EntityScalingRuntime.getOrInitialize` dá precedência ao attachment `ENTITY_SCALING` já persistido e não invoca o initializer novamente em reload/re-add, impedindo reroll de rarity.
- `EntityScalingState` persiste `Optional<MobRaritySelection> rarity` e `deterministicSeed` junto da decisão completa de scaling.
- `DeterministicWeightedMobRarityPolicy` resolve seleção ponderada deterministicamente a partir de `MobRarityContext.deterministicSeed`, filtra regras por floor/archetype, mantém rarity separada de `EntityArchetype` e possui `bossFallback` explícito além do fallback geral.
- `MobRarityRule` mantém a tabela de regras como dados fornecidos pelo caller; este plano não congela chances/tier bonuses de balanceamento na implementação Core.
- O contrato TDD de `DeterministicWeightedMobRarityPolicyJUnitTest` cobre repetibilidade por seed, fronteiras de peso, filtros, fallbacks, regras inválidas e overflow.
- CI do slice de implementação: `RPG Skill Tree CI` #1450 (`33264906690`) no head `8fc9932c0c583e4c6917ef872944ac37dbd3fe95`, GREEN em Core, JUnit, GameTests, build, verificação do JAR e dedicated-server smoke.

## Pendência causal

`Aplicar modifiers idempotentes` permanece aberto e pertence ao Stage `02.05 — Effective Stats`: rarity/archetype já são decisões persistidas e rastreáveis, mas a reaplicação runtime dos atributos/modifiers derivados após save/load ainda precisa ser fechada naquele estágio. Não marcar este plano como concluído antes disso.

**Acceptance:** a parte "uma entidade não muda de raridade ao recarregar" está coberta pelo lifecycle persistido; a aceitação completa continua aberta até `02.05` garantir aplicação/reaplicação idempotente e rastreável dos atributos/comportamentos derivados de rarity/archetype.