# World Scaling Plan — Entity Level

**Goal:** atribuir nível a entidades no ponto correto do lifecycle e preservar o resultado sem reaplicação indevida.

**Status:** concluído.

- [x] Definir momento canônico de cálculo no spawn/load.
  - `EntityScalingEvents` usa `EntityJoinLevelEvent` somente no servidor, ignora players e respeita estado persistido antes de qualquer inicialização.
- [x] Combinar area level e relevant player level conforme fórmula definida.
  - `WorldEntityScalingService` resolve Native Area + Relevant Player e entrega o contexto a `EntityLevelService`, que aplica floor, variance e rarity bonus de forma determinística/auditável.
- [x] Persistir metadados necessários na entidade.
  - `EntityScalingRuntime` persiste `EntityScalingState` no attachment `ENTITY_SCALING`; o estado inclui território, resolução de nível, variance, rarity, seed, Effective Stats, affixes e behaviors.
- [x] Impedir scaling duplicado após chunk unload/reload.
  - `EntityScalingRuntime.current(...)` e `getOrInitialize(...)` fazem persisted-state-first; `EntityScalingEvents` não chama initializer quando já existe estado.
- [x] Definir fallback para mobs externos e entidades sem categoria conhecida.
  - `EntityArchetypeRuntimeClassifier` aplica precedência explícita `BOSS -> GUARD -> TAMED -> VILLAGER/CIVILIAN -> HOSTILE -> NEUTRAL -> PASSIVE -> SPECIAL`.
  - `rpgskilltree:guards` é uma tag de `entity_type`, permitindo integração data-driven sem hard dependency; entidades não reconhecidas caem conservadoramente em `SPECIAL`.

## Effective Stats e save/load

A pendência causal deste plano foi fechada pelo Stage 02.05:

- `EntityScalingState` persiste `Optional<EntityEffectiveStatsSnapshot>` junto da decisão autoritativa.
- `EntityEffectiveStatsRuntime.refresh(...)` remove primeiro o modifier estável `rpgskilltree:entity_scaling/<stat>` e então usa `addOrReplacePermanentModifier`, tornando reaplicações repetidas idempotentes.
- `EntityScalingEvents` reaplica Effective Stats quando encontra estado persistido e também imediatamente após uma inicialização nova.
- `EntityEffectiveStatsGameTests.persistedEffectiveStatsReapplyWithoutStacking` prova primeira aplicação, reaplicação repetida, round-trip do state e reaplicação após save/load sem stacking.

## Evidência TDD / CI

- RED `f540da433fa3d3cc7bf7199c22dc3d5e489cf2ed`: CI #1428 (`33263712009`) falhou porque `EntityArchetypeRuntimeClassifier` ainda não existia.
- Correção de mappings `221ce72590746025b573b662c23562e82416a07a`: CI #1434 (`33264078329`) GREEN completo, incluindo Core, JUnit, NeoForge GameTests, build, JAR e dedicated-server smoke.
- O fechamento causal de Effective Stats é exercitado por `EntityEffectiveStatsGameTests`; no acceptance final do Stage 02, RPG Skill Tree CI #1589 (`33271450823`) executou todos os 11 NeoForge GameTests com sucesso, além de build/JAR e dedicated-server smoke GREEN.
- `WorldScalingMultiplayerAcceptanceTest` também passou no mesmo CI, provando que um jogador global nível 5000 não contamina o scaling local enquanto party relevante participa explicitamente.

**Acceptance: satisfied.** A entidade possui lifecycle de nível estável, não rerolla/recalcula sua decisão persistida e mantém os atributos canônicos reaplicados idempotentemente após save/load.
