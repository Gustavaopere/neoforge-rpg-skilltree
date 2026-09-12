# FerriteCore — 7.0.3

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8160a186f0e37c33967d  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** FerriteCore
- **Arquivo JAR:** `ferritecore-7.0.3-neoforge.jar`
- **Versão 1.21.1:** `7.0.3`
- **Categoria:** Performance
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ferritecore
- **Função:** Performance/memory mod que deduplica e compacta estruturas internas de blockstates/models/data components/caches, reduzindo RAM sem alterar ownership do conteúdo.
- **Dependências:** NeoForge 1.21.1. ModernFix está presente e 7.0.3 contém correção para dynamic-resource loading; Create: Aeronautics e Supplementaries também estão no stack e têm reports upstream relevantes para regressão.
- **Compatibilidade/Riscos:** Reports upstream: crash sob memory pressure com Create: Aeronautics e startup extremamente lento com Supplementaries em NeoForge 1.21.1; não são bugs locais confirmados. Riscos adicionais: mixin conflict, stale resource/model data e data-component dedup incorreto.
- **Sobreposição:** FerriteCore mira principalmente memória/representação; não substitui EntityCulling, BetterFpsDist, CreateBetterFps ou FastSuite. ModernFix tem interseção de resources/lifecycle e deve ser testado em conjunto.
- **Observações:** 7.0.3 reduz memória de data component patches e corrige loading slowdown com ModernFix dynamic resources. Issues Aeronautics/Supplementaries ficam como regression gates, não incompatibilidades comprovadas.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `ferritecore-7.0.3-neoforge.jar`, mod id `ferritecore`, versão 7.0.3, mixin configs e SHA-1 9563692efb708b6b568df27a01ec52f6311928ef. CurseForge 7.0.3 e issues oficiais auditados.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — FerriteCore 7.0.3; memory/data-component/model surfaces, ModernFix fix, Aeronautics/Supplementaries regression reports, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `ferritecore-7.0.3-neoforge.jar` · mod id `ferritecore` · versão `7.0.3` · NeoForge 1.21.1. O mod atua transversalmente na redução de uso de memória e estruturas internas; não adiciona gameplay.

## 1. Papel no modpack
FerriteCore é um mod de performance/memória. Ele reduz RAM através de deduplicação, estruturas mais compactas e otimizações de caches/model/blockstate/data-component paths. Em um pack de 595 mods, seu impacto potencial é global, mas correctness deve permanecer idêntica ao vanilla/provider original.

## 2. Authority / ownership
- **Minecraft/mods de conteúdo:** continuam authority de blockstates, models, data components e gameplay data.
- **FerriteCore:** altera representação/cache/deduplicação interna para consumir menos memória.

Ele não deve mudar registry IDs, block properties, item components ou resultado de gameplay.

## 3. Superfícies físicas da build
A modlist registra mixins/configs da 7.0.3 relacionados a `modelsides`, `mrl`, `dedupbakedquad`, `threaddetec`, `datacomponents`, `blockstatecache`, `dedupmultipart`, `accessors`, `fastmap` e `predicates`.

Esses nomes sustentam atuação em models/blockstates/predicates/data components e estruturas compactas; não devem ser extrapolados para gameplay que a build não toca.

## 4. Release 7.0.3
A changelog oficial registra duas mudanças relevantes: redução de memória usada por **data component patches** e correção de slowdown de loading na interação com **ModernFix dynamic resources**. Como ModernFix está instalado, a segunda correção é diretamente relevante ao pack.

## 5. Blockstate/model memory
FerriteCore deduplica/compacta estruturas que se repetem em muitos blocks/models. O benefício cresce em packs com muitos content/decor mods. O invariant é que modelos, predicates e blockstate variants continuem semanticamente iguais depois da otimização.

## 6. Data components
7.0.3 otimiza patches de data components. Items modded com componentes complexos precisam manter igualdade, serialization e network sync. Redução de memória não pode alterar component identity/values observáveis.

## 7. Integração com ModernFix
A própria 7.0.3 corrige um slowdown de loading relacionado a dynamic resources do ModernFix. Portanto a combinação atual é explicitamente suportada por uma correção upstream recente; ainda assim startup time deve ser medido porque o pack tem enorme superfície de models/resources.

## 8. Issues upstream relevantes ao pack
Há relato upstream aberto de crash ao liberar memória sob pressão com **Create: Aeronautics** e outro relato de startup extremamente lento em NeoForge 1.21.1 quando FerriteCore e **Supplementaries** coexistem. Ambos os mods estão no ecossistema local.

Esses reports são **regression gates**, não prova de que o pack atual reproduz o problema.

## 9. Client / Server
FerriteCore possui otimizações que afetam common/server data e client model/resource loading. Dedicated server deve iniciar sem tocar code path exclusivamente client. Cliente precisa completar resource/model bake sem corrupção visual.

## 10. Lifecycle
Validar cold boot, warm restart, resource reload, datapack reload, world load/unload, dimension travel, memory pressure/GC, large chunk traversal e update de content/resource mods.

## 11. Multiplayer
Otimização de representação interna não deve alterar packets/state. Clients com o mesmo modpack precisam enxergar os mesmos items/blockstates; servidor não pode depender de identidade de objeto que FerriteCore deduplica de forma incompatível.

## 12. Relação com outros performance mods
FerriteCore mira principalmente memória/estrutura interna. EntityCulling é render culling; BetterFpsDist atua em distância; FastSuite atua recipe matching; CreateBetterFps mira Create. Eles não são substitutos diretos.

ModernFix tem interseções de lifecycle/resources, justificando regressão combinada.

## 13. Riscos
1. incompatibilidade de mixin com mod que altera mesma estrutura;
2. crash sob memory pressure com Aeronautics reportado upstream;
3. startup/model bake muito lento com Supplementaries reportado upstream;
4. stale resource/model data após reload;
5. data component patch dedup incorreto;
6. mod depender de object identity não prevista;
7. client-only model optimization atingir dedicated server por classloading;
8. update ModernFix mudar dynamic-resource contract;
9. corrupção visual difícil de atribuir por ser otimização transversal;
10. remover FerriteCore sem medir impacto de RAM/startup.

## 14. Matriz de testes
1. Dedicated server cold boot.
2. Client cold boot com medição de tempo e heap.
3. Resource reload repetido.
4. Supplementaries presente: comparar model-load time.
5. Aeronautics sob sessão longa/memory pressure em ambiente de teste.
6. Inventário com items de data components complexos.
7. Grande área com muitos blockstates/models modded.
8. Dimension change e reconnect.
9. Comparativo A/B de memória sem FerriteCore em cópia de teste.
10. Smoke-test após update de ModernFix/Aeronautics/Supplementaries.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica: JAR/mod id/version/hash e mixin configs;
- CurseForge oficial 7.0.3: release NeoForge 1.21.1 e changelog de data-component memory + ModernFix dynamic resources;
- issues oficiais `malte0811/FerriteCore` sobre Create: Aeronautics e Supplementaries, registrados apenas como reports a reproduzir.

> **Boundary canônico:** FerriteCore otimiza a **representação/memória**, nunca deve se tornar authority do conteúdo que está deduplicando ou compactando.