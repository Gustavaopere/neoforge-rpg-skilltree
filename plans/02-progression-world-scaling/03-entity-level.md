# World Scaling Plan — Entity Level

**Goal:** atribuir nível a entidades no ponto correto do lifecycle e preservar o resultado sem reaplicação.

**Status:** checklist funcional de 02.03 implementado. A aceitação final permanece aberta até 02.05 fechar a aplicação/reaplicação de Effective Stats após save/load.

- [x] Definir momento canônico de cálculo no spawn/load.
  - `EntityScalingEvents` usa `EntityJoinLevelEvent` somente no servidor, ignora players e respeita estado persistido antes de qualquer inicialização.
- [x] Combinar area level e relevant player level conforme fórmula definida.
  - `WorldEntityScalingService` resolve Native Area + Relevant Player e entrega o contexto a `EntityLevelService`, que aplica floor, variance e rarity bonus de forma determinística/auditável.
- [x] Persistir metadados necessários na entidade.
  - `EntityScalingRuntime` persiste `EntityScalingState` no attachment `ENTITY_SCALING`; o estado inclui território, resolução de nível, variance, rarity, seed, affixes e behaviors.
- [x] Impedir scaling duplicado após chunk unload/reload.
  - `EntityScalingRuntime.current(...)` e `getOrInitialize(...)` fazem persisted-state-first; `EntityScalingEvents` não chama initializer quando já existe estado.
- [x] Definir fallback para mobs externos e entidades sem categoria conhecida.
  - `EntityArchetypeRuntimeClassifier` aplica precedência explícita `BOSS -> GUARD -> TAMED -> VILLAGER/CIVILIAN -> HOSTILE -> NEUTRAL -> PASSIVE -> SPECIAL`.
  - `rpgskilltree:guards` é uma tag de `entity_type`, permitindo integração data-driven sem hard dependency; entidades não reconhecidas caem conservadoramente em `SPECIAL`.

## Evidência TDD / CI

- RED `f540da433fa3d3cc7bf7199c22dc3d5e489cf2ed`: CI #1428 (`33263712009`) falhou porque `EntityArchetypeRuntimeClassifier` ainda não existia.
- GREEN inicial `2a8950d0426426decb5cdec824a767627afab57f`: revelou apenas imports incompatíveis com os mappings NeoForge 1.21.1; o teste não foi alterado.
- Correção de mappings `221ce72590746025b573b662c23562e82416a07a`: CI #1434 (`33264078329`) GREEN completo, incluindo Core, JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke.
- `EntityArchetypeClassifierGameTests` cobre WITHER/BOSS, IRON_GOLEM/GUARD, ZOMBIE/HOSTILE, BEE/NEUTRAL, PIG/PASSIVE, VILLAGER, WANDERING_TRADER/CIVILIAN, WOLF domesticado/TAMED e ARMOR_STAND/SPECIAL.

## Limite causal para ativação de produção

`EntityScalingInitializerCatalog.installDecisionFactory(...)` continua deliberadamente **não instalado** em produção neste estágio. O request autoritativo completo exige rarity, provider stats, archetype stat policies, affix policy e behavior policy. Ativá-lo antes de 02.04/02.05 poderia persistir uma decisão parcial e torná-la autoritativa após reload.

**Acceptance:**

- [x] A entidade possui lifecycle de nível estável e não rerolla/recalcula sua decisão persistida após save/load.
- [ ] A entidade mantém **atributos aplicados** corretos após save/load. Bloqueado por `05-scaling-rewards-performance.md`, que deve fechar curvas e a aplicação/reaplicação idempotente de Effective Stats antes de ativar o initializer completo.
