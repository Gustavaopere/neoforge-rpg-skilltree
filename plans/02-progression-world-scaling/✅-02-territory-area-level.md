# World Scaling Plan — Territory and Area Level

**Goal:** permitir que regiões do mundo mantenham pressão própria além do level individual do jogador.

- [x] Definir fonte do nível local: exploração, distância, marcos, progressão ou composição dessas fontes.
- [x] Escolher unidade espacial estável para persistência/cache.
- [x] Definir subida/decay e caps.
- [x] Preservar estado após restart/reload quando aplicável.
- [x] Evitar fronteiras abruptas sem intenção de design.

**Acceptance:** uma área resolve nível local determinístico, persistente quando necessário e barato de consultar.

## Contrato implementado

- `TerritoryGrid` permanece a unidade espacial canônica: dimensão + célula X/Z com tamanho configurável e `Math.floorDiv`, inclusive para coordenadas negativas e valores extremos.
- `NativeAreaLevelPlan` + `NativeAreaLevelContribution` continuam compondo a ameaça local por fontes namespaced, ordenadas e auditáveis. Fontes adequadas incluem dimensão, biome, estrutura/contexto, distância/localização, danger tags, ruído determinístico e marcos explicitamente configurados.
- `NativeAreaThreatResolver` mantém composição determinística com aritmética checked, clamp inferior em zero e override explícito.
- `NativeAreaLevelPlanProvider` formaliza a fronteira entre resolução territorial e origem dos dados. Fontes determinísticas/recomputáveis não exigem persistência por célula; fontes genuinamente stateful de exploração/milestone são persistidas pelo provider que as possui e entregam seu snapshot atual por esse contrato.
- `NativeAreaLevelTransitionPolicy` fornece min/max, subida máxima por passo e decay máximo por passo para fontes stateful. Não contém coeficientes de balanceamento hard-coded.
- `TerritoryAreaLevelResolver` fornece suavização opcional de fronteira. `blendRadiusBlocks = 0` preserva fronteira dura intencional; raio positivo utiliza kernel inteiro determinístico.
- O diâmetro do kernel deve caber em uma célula, garantindo no máximo duas células por eixo e **quatro** consultas de `NativeAreaLevelPlan` por resolução. O custo é O(1), independente do número de territórios existentes no mundo.
- `TerritoryAreaLevelResolution`/`TerritoryAreaLevelSample` expõem o território primário, breakdowns, pesos, nível resolvido e peso total para diagnóstico sem expor estado mutável.
- A média ponderada usa `BigInteger` internamente para impedir overflow silencioso em `level * weight`; pesos e contadores usam operações exact.

## Persistência e restart

O Core não cria `SavedData` massivo por território. Isso é deliberado e segue a arquitetura aprovada: uma ameaça derivável de dimensão/bioma/distância/noise/datapack é recomputada a partir da mesma `TerritoryKey`. Quando uma contribuição depende de estado real — por exemplo um marco mundial ou progresso de exploração que precisa sobreviver a restart — o subsistema/provider dono desse estado o persiste e fornece a contribuição atual ao `NativeAreaLevelPlanProvider`.

Assim, restart/reload preserva tudo que realmente precisa persistência sem transformar cada célula visitada em uma entrada obrigatória de save.

## Evidência de testes

- `TerritoryGridTest` cobre determinismo, dimensões, cell sizes configuráveis, floor semantics negativas e limites técnicos de coordenadas.
- `NativeAreaThreatCompositionTest` cobre IDs namespaced, composição independente de ordem, deltas assinados, clamp, override e overflow explícito.
- `TerritoryAreaLevelPolicyTest` cobre min/max, rise/decay, zero-rate, suavização de fronteira, fronteira dura opt-in, corner blend com exatamente quatro amostras, coordenadas negativas, imutabilidade e inputs inválidos.
- `TerritoryAreaLevelPolicyJUnitTest` executa esse contrato no discovery JUnit do pipeline NeoForge.
- TDD RED: `eb1bd8a14f4bbe11d4257241e4656679b65421e1`, `RPG Skill Tree CI` run `33246549953`, falhando no Core exclusivamente porque os novos contratos ainda não existiam.
- GREEN pré-sincronização: `3948756e3e4a5aed356bc683e226d410dbad0704`, `RPG Skill Tree CI` run `33246761354`, com Core, JUnit 5, GameTests, build, JAR e dedicated-server smoke GREEN.

## Limite causal preservado

Este subplano não escolhe tamanho final de território, coeficientes de ameaça, blend radius padrão ou valores finais de rise/decay/caps; esses são balance/config. Também não instala ainda a factory completa de `EntityScalingDecisionRequest`, porque raridade, archetype e reward/performance pertencem aos subplanos seguintes.

**Acceptance: satisfied at the contract level. Merge remains gated on full CI for the synchronized head.**
