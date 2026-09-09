# Fast Suite — 6.0.7

> **Runtime físico confirmado:** `FastSuite-1.21.1-6.0.7.jar` · mod id `fastsuite` · versão `6.0.7` · NeoForge 1.21.1. A linha 6.x usa **Concurrent Recipe Matching**; a arquitetura indexada da 7.x não deve ser projetada sobre esta instalação.

## 1. Papel no modpack
FastSuite é um mod de performance para o sistema de recipes JSON. Na linha instalada 6.0.7, ele acelera lookup/matching através de processamento concorrente controlado, mantendo compatibilidade por classificação de recipe/ingredient thread-safe e fallback para casos não seguros.

## 2. Authority / ownership
- **Minecraft/mod provider:** define recipes, ingredients, outputs e semantics de cada recipe type.
- **FastSuite:** otimiza a forma de procurar/matchear recipes; não altera intencionalmente o resultado correto da recipe.
- **Recipe consumers como Create/FD/KubeJS:** continuam authority de seus recipes e processamento.

Qualquer divergência de resultado é bug/regressão, não comportamento aceitável de performance.

## 3. Arquitetura 4.1→6.x
O changelog oficial registra que a linha 4.1 substituiu o antigo linked-list cache por **Concurrent Recipe Matching**. A concorrência é ativada automaticamente para recipe types com mais de 100 recipes, com blacklist configurável e timeout máximo para evitar deadlock em worker threads.
A 6.0.0 porta essa arquitetura para 1.21.1 e inclui as melhorias thread-safe da 5.1.0.

## 4. Classificação thread-safe
Desde 5.1.0, FastSuite só paraleliza recipes conhecidas como thread-safe. Vanilla recipe classes com vanilla/Forge ingredients são cobertas por padrão; mods podem registrar classes adicionais através de `FastSuite.registerSafeRecipeClass` e `FastSuite.registerSafeIngredientClass`.
Essa allowlist é crítica em pack grande: não assumir que toda recipe modded roda em worker thread.

## 5. Configuração
A linha concorrente possui controles para blacklist de recipe types problemáticos, timeout de match concorrente e opção histórica de lock dos input stacks durante matching. Valores concretos da instância devem ser lidos do config real antes de tuning.

## 6. Correções 6.0.x relevantes
- **6.0.1:** corrigiu crash de startup com KubeJS;
- **6.0.2:** corrigiu debug test que aumentava muito o tempo de world start;
- **6.0.3:** corrigiu race condition em múltiplas shapeless recipes concorrentes;
- **6.0.4:** corrigiu Stack Overflow por chamada incorreta de super em `getRecipeFor`;
- **6.0.6:** corrigiu data race em `Ingredient#stackingIds`;
- **6.0.7:** corrigiu problema severo de performance na combinação FastSuite + ModernFix + AE2JEIIntegration.

A build física está no fim dessa linha de correções.

## 7. Relação com o pack atual
O pack possui **ModernFix**, KubeJS e milhares de recipes de numerosos addons. AE2 foi removido do design atual, mas a correção 6.0.7 continua parte da build. KubeJS e recipes customizadas são surfaces que justificam regressão específica.

## 8. Recipe reload/cache lifecycle
Após `/reload`, datapack change ou mod update, estruturas auxiliares/classificações não podem manter recipes antigas. Matching deve refletir exatamente o `RecipeManager` atual e preservar deterministic result quando múltiplas recipes podem combinar.

## 9. Client / Server
Recipe matching que afeta crafting/processamento é principalmente server/common logic. O cliente pode fazer recipe-viewer/recipe-book lookups, mas não deve decidir output autoritativo. Thread pools/caches precisam encerrar/reconstruir corretamente durante lifecycle.

## 10. Multiplayer e concorrência
FastSuite introduz concorrência interna independentemente de quantos players existem. O principal invariant é: **mesmo input + mesmo recipe state → mesmo resultado**, sem race, deadlock, crash ou mutation simultânea de ingredients/input stacks.

## 11. Sobreposição com outros performance mods
ModernFix pode tocar recipe/resources/cache lifecycle; FerriteCore mira memória e não substitui FastSuite. Polymorph é explicitamente citado no changelog da arquitetura concorrente como caso que deixou de impor o custo do modelo antigo. Sobreposição de objetivo não significa redundância de implementação.

## 12. Riscos
1. recipe modded não thread-safe ser classificada como segura;
2. data race em ingredient custom;
3. shapeless nondeterminism;
4. deadlock/timeout;
5. stale cache após `/reload`;
6. KubeJS recipe object incompatível;
7. recipe type custom precisar blacklist;
8. combinação com ModernFix alterar timing/lifecycle;
9. erro de matching produzir recipe incorreta — severidade maior que perda de performance;
10. usar documentação 7.x/indexed matching como se fosse 6.0.7.

## 13. Matriz de testes
1. Dedicated server boot com KubeJS + ModernFix.
2. `/reload` repetido e craft antes/depois.
3. Crafting grid com recipe shapeless ambígua.
4. Recipe modded de Create, Farmer's Delight e magia.
5. Polymorph/recipe-selection stack, se ativo.
6. Stress com recipe type >100 entries.
7. Dois players crafting simultaneamente.
8. Monitorar exceptions de worker/data race/deadlock.
9. Comparar outputs com FastSuite temporariamente desabilitado em ambiente de teste.
10. Medir lookup/startup sem alterar correctness.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- CurseForge oficial: `FastSuite-1.21.1-6.0.7.jar`, release 1.21.1;
- source/changelog oficial `Shadows-of-Fire/FastSuite` branch 1.21: arquitetura concorrente, thread-safe classifiers e correções 6.0.0–6.0.7.

> **Boundary canônico:** FastSuite pode mudar **como** recipes são encontradas, nunca **qual recipe é válida nem qual output pertence ao provider**.
