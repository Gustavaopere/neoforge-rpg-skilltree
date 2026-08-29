# World Scaling Plan — Formulas, Rewards and Performance

**Goal:** fechar curvas de dificuldade/recompensa com custo aceitável.

- [x] Separar escalas de HP, dano, defesa, utilidade e recompensa.
- [x] Configurar caps/curvas para evitar one-shot inevitável e HP sponge.
- [x] Fazer XP/loot acompanhar risco sem criar farm exponencial.
- [x] Definir multiplicadores de bosses/raridades especiais.
- [x] Testar solo, party e grande diferença de níveis.
- [x] Benchmarkar spawn, consulta espacial e recomputação.

## Curvas e famílias independentes

- `ScalingCurveFamily` separa explicitamente `HEALTH`, `DAMAGE`, `DEFENSE`, `UTILITY` e `REWARD`.
- `ScalingCurveSet` exige configuração completa das cinco famílias; não existe fallback silencioso de curva ausente.
- `CappedLinearScalingCurve` recebe base, crescimento por level, mínimo e máximo explicitamente e usa caps independentes por família.
- `CanonicalStatScalingFamilyCatalog` mapeia os atributos vanilla sem heurística por nome e exige registro explícito para stats de providers externos.
- `CurveBackedEntityArchetypeStatPolicy` aplica as curvas configuradas ao snapshot canônico sem embutir balanceamento oculto.
- `EntityScalingCurvePolicyTest` cobre famílias independentes, caps, níveis muito altos, providers externos registrados e fail-closed para configuração incompleta/desconhecida.

## Rewards proporcionais ao risco

- `CappedEntityRewardScalingPolicy` resolve reward a partir de `EntityRewardScalingContext`, mantendo level/archetype/rarity como fatores explícitos e bounded.
- O caminho de XP usa somente `EntityScalingState` já persistido; payout não inicializa, não rerolla e não altera a decisão da entidade.
- O caminho de loot usa `RewardRiskLootModifier`, um Global Loot Modifier NeoForge 1.21.x registrado em `GLOBAL_LOOT_MODIFIER_SERIALIZERS` e ativado via data pack.
- XP e loot reutilizam a mesma reward policy canônica; não existem duas fórmulas independentes de recompensa.
- Loot stackable respeita `maxStackSize`, arredondamento fracionário determinístico pelo `deterministicSeed` persistido e `max_extra_stacks_per_input` como limite técnico de expansão.
- Loot não-stackable/único é preservado por cópia e não é genericamente duplicado ou removido.
- Sem uma reward policy explicitamente instalada pelo servidor, XP/loot scaling permanece no-op; o Core não congela balanceamento final.

## Bosses e raridades especiais

- `MobRaritySelection.levelBonus()` participa da decisão persistida de level.
- A reward policy possui multiplicadores/configuração explícitos para archetype e rarity; regras/fatores são fornecidos pelo caller, não hardcoded como balanceamento universal.
- `DeterministicWeightedMobRarityPolicy` mantém `bossFallback` explícito e rarity separada de `EntityArchetype`, permitindo combinação controlada sem reroll.

## Solo, party e grande disparidade

`WorldScalingMultiplayerAcceptanceTest` cobre o cenário integrado:

- solo local nível 20 + jogador global não relevante nível 5000 resolve floor local 20;
- party relevante adiciona membro remoto nível 200 e eleva o floor para 200;
- o jogador global nível 5000 continua excluído, provando que grande disparidade fora do conjunto relevante não contamina o encounter.

A resolução continua limitada ao conjunto fornecido por `RelevantPlayerCandidateRuntime`/spatial index + party adapter, e `RelevantPlayerLevelResolution` proíbe uma policy de inventar valor fora do intervalo dos participantes relevantes.

## Benchmark e budgets algorítmicos

`WorldScalingPerformanceBenchmarkTest` mede três hot paths, mas usa tempo apenas como diagnóstico; o CI não possui threshold de milissegundos dependente da máquina.

No RPG Skill Tree CI #1589 (`33271450823`) o benchmark registrou:

- spawn resolution: `60.515.077 ns` para 2.000 resoluções puras;
- spatial lookup: `77.194.596 ns` para 5.000 consultas;
- persisted resume: `4.056.403 ns` para 10.000 resumes;
- `4096` jogadores indexados;
- máximo de `1` jogador efetivamente examinado por consulta local;
- `49` células visitadas para budget máximo `49`;
- `0` chamadas ao initializer durante os 10.000 resumes com estado persistido.

Os asserts permanentes são algorítmicos:

- `visitedCells <= worstCaseVisitedCells`;
- consulta local não pode degradar para scan global dos jogadores indexados;
- estado persistido deve retornar diretamente sem recomputação/initializer;
- a resolução de spawn deve realmente produzir resultados consumidos pelo benchmark.

## Evidência de integração

- XP reward slice: PR #163, merge `3af8e110198dd2105a56eedbc421ac4c5d7e7dc3`, com CI sincronizado #1559 e pós-merge GREEN.
- Loot reward slice: PR #167, merge `2f60718c8cd6acd818a8ab387901440b1afd6fe7`; CI sincronizado #1585 (`33271051158`) GREEN completo, incluindo Core, JUnit, NeoForge GameTests, build/JAR e dedicated-server smoke.
- Acceptance multiplayer/performance: PR #169, head funcional `34e4d79815ea921d4d89c4d6b44b29983f674c15`; RPG Skill Tree CI #1589 (`33271450823`) GREEN completo, incluindo os novos acceptance tests, todos os 11 GameTests, build/JAR e dedicated-server smoke.
- `EntityEffectiveStatsGameTests.persistedEffectiveStatsReapplyWithoutStacking` fecha também as pendências causais de 02.03/02.04: modifiers são reaplicados após round-trip sem stacking.

**Acceptance: satisfied.** As curvas são separadas e capped, XP/loot acompanham risco pela mesma policy bounded, multiplayer não usa jogador global irrelevante e os hot paths possuem budgets determinísticos sem scan global/per-tick desnecessário.
