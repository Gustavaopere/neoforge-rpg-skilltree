# Create: Filters Anywhere

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814bab17f8c03db94521
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Filters Anywhere
- **Arquivo JAR:** `createfiltersanywhere-1.21.1-2.6.0.jar`
- **Versão 1.21.1:** 2.6.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL, Automação
- **Função:** Leva Attribute/List Filters do Create a interfaces e sistemas de vários outros mods, adicionando atributos e QoL de filtragem cruzada.
- **Dependências:** Create obrigatório. Integrações físicas relevantes no pack: Tom's Simple Storage 2.4.2, Sophisticated Core 1.5.1 e Apotheosis 8.8.0. O JAR contém também mixins opcionais para AE2, LaserIO, Modular Routers, Refined Storage e Oritech.
- **Sobreposição:** Não duplica filtros Create; amplia sua semântica para outros providers. Tom's/Sophisticated/Apotheosis são superfícies ativas confirmadas; adapters para providers ausentes permanecem opcionais.
- **Compatibilidade/Riscos:** Riscos: cache stale para FE/rarity/data components; optional-mixin classloading; Tom's/Sophisticated API drift; client/server filter mismatch; reload invalidation; memory/performance tradeoff; predicate semantics divergentes entre consumers.
- **Observações:** JAR/mod id/runtime 2.6.0 e source matching confirmados. 2.6.0 cacheia filter types por mod set/item e adiciona atributos Apotheosis rarity, FE charge, data component e item identity.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog oficiais 2.6.0 + source oficial iwolfking/CreateFiltersAnywhere matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-filters-anywhere
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.6.0 com cross-mod filter adapters, caches, Apotheosis/FE/data-component/item attributes, TPS boundary, reload e active-provider checks catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🔎 **Identidade física e source matching confirmados:** `createfiltersanywhere-1.21.1-2.6.0.jar`, mod id `createfiltersanywhere`, runtime `2.6.0`. O source oficial declara exatamente 2.6.0 para Minecraft 1.21.1.

## 1. Papel e authority
Create: Filters Anywhere amplia **Attribute Filters** e **List Filters** do Create para outros sistemas/mods e adiciona novos atributos/QoL. Create continua owner da semântica base de filter item; o addon owns adapters, novos predicates/attributes e caches utilizados para resolver esses filtros em providers externos.

## 2. Integrações físicas detectadas no JAR
A modlist física expõe mixins dedicados para **Tom's Storage, AE2, LaserIO, Modular Routers, Create, Refined Storage, Sophisticated Core e Oritech**. Isso prova superfícies compiladas no artefato, mas só providers realmente instalados são tratados como integração runtime ativa.

## 3. Tom's Simple Storage
Tom's Simple Storage **2.4.2** está instalado. Filters Anywhere pode estender a utilização dos filtros Create nesse ecossistema. Filter evaluation precisa respeitar inventory/request semantics do Tom's e não substituir sua authority de armazenamento.

## 4. Sophisticated Core
Sophisticated Core **1.5.1** está instalado. O changelog 2.6.0 destaca ganho de TPS especialmente em cenários como Sophisticated Storage por caching de tipos. Isso torna filtros em inventories Sophisticated um regression/performance gate concreto quando os módulos consumidores estão presentes.

## 5. Apotheosis rarity — novo atributo 2.6.0
A 2.6.0 adiciona **has Apotheosis rarity** quando Apotheosis está carregado. O pack contém **Apotheosis 8.8.0**, portanto esse predicate está concretamente relevante. O addon deve ler a rarity atual sem congelar state antigo em cache.

## 6. Forge Energy attributes
A release adiciona atributos para itens com energia: **is uncharged**, **can be charged** e **is fully charged**. Charge é state mutável do item; o predicate precisa refletir o stack atual e não apenas o item type.

## 7. Data component attribute
A 2.6.0 adiciona **has a X data component**. Isso amplia filtragem para components de mods sem suporte específico. A existência do component deve ser avaliada sobre o stack real; component removido/adicionado não pode ficar invisível por cache inadequado.

## 8. Item identity attribute
Também foi adicionado **is a X item**, útil para construir listas específicas de items. Esse predicate deve comparar identidade de registry corretamente e preservar components apenas quando a regra exigir; identidade de item e igualdade total de stack são conceitos distintos.

## 9. Cache por mods carregados
A 2.6.0 implementa caching dos tipos de filtro dependentes do conjunto de mods carregados. O cache pode ser construído no startup porque o mod set normalmente é estável durante a sessão, mas não deve registrar adapters de provider ausente nem causar classloading opcional.

## 10. Cache por item
A release também cacheia o tipo de filtro associado a cada item. Isso melhora performance, mas exige separar **classificação estática do item** de **state dinâmico do stack**. Energy, rarity e data components não podem ser tratados como valores imutáveis só porque o tipo foi cacheado.

## 11. TPS e avaliação em massa
O upstream afirma grande melhoria de TPS. Isso é expectativa de otimização, não benchmark garantido do pack. Profiling deve medir MSPT com filtros intensivos e comparar 2.6.0 em workloads reais de storage/logistics.

## 12. AE2 e Refined Storage
O artefato contém mixins para AE2 e Refined Storage, mas esses providers não foram encontrados como JARs top-level na modlist atual. Essas superfícies permanecem opcionais/inertes no pack atual e não justificam dependências extras.

## 13. LaserIO e Modular Routers
Mixins compilados também existem para LaserIO e Modular Routers. Se os providers estiverem ausentes, nenhum adapter deve classloadar suas classes. Se forem adicionados futuramente, precisam de revalidação por versão antes de considerar a compat funcional.

## 14. Oritech
O JAR contém integração Oritech; a presença/runtime do provider deve ser confirmada quando esse caminho for testado. A existência do mixin por si só não prova que todos os inventories ou filters do Oritech sejam cobertos.

## 15. Client/server
Predicate evaluation que governa inserção/extração/roteamento deve convergir no servidor. UI de filter configuration e tooltips podem ser client-facing. Cliente não pode fazer um stack “passar” num filtro que o servidor rejeita.

## 16. Reload/config lifecycle
Datapack/resource changes podem alterar tags/components/registries utilizados por predicates. Caches derivados precisam ser invalidados/reconstruídos quando o provider exige; cache stale é o principal risco introduzido pela otimização 2.6.0.

## 17. Multiplayer
Dois jogadores podem editar filtros enquanto logística opera. Mudança de regra precisa sincronizar para o servidor e consumidores, sem uma janela em que caches diferentes aceitem o mesmo stack de forma contraditória.

## 18. Riscos
1. Cache por item congela FE charge state.
2. Apotheosis rarity muda e predicate permanece stale.
3. Data component é alterado sem invalidar avaliação.
4. Provider ausente causa optional-mixin classloading crash.
5. Tom's/Sophisticated API drift quebra adapter.
6. Filter UI diverge da decisão server-side.
7. `is a X item` é confundido com igualdade completa de stack.
8. Cache não é reconstruído após reload relevante.
9. Ganho de TPS não aparece ou vira memory overhead excessivo.
10. Dois consumers interpretam o mesmo Attribute Filter de forma diferente.

## 19. Matriz de testes
- [ ] Dedicated server inicia com Filters Anywhere 2.6.0 + Create 6.0.10.
- [ ] Tom's 2.4.2 aceita Attribute/List Filters no fluxo suportado.
- [ ] Sophisticated Core path não produz class/mixin error.
- [ ] Apotheosis rarity predicate funciona com Apotheosis 8.8.0.
- [ ] Uncharged/can charge/fully charged acompanham FE state mutável.
- [ ] Data-component predicate muda junto com o stack.
- [ ] Item identity predicate não perde components semanticamente relevantes.
- [ ] Ausência de AE2/Refined Storage/LaserIO/Modular Routers não causa crash.
- [ ] `/reload` não deixa filter semantics stale.
- [ ] Multiplayer edit + logistics converge para uma única regra server-side.
- [ ] Profiling compara MSPT de carga de filtros antes/depois da cache.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física confirma 2.6.0 e os arquivos de mixin opcionais. O source matching confirma mod id/versão. O changelog oficial 2.6.0 confirma os dois caches, novos atributos de Apotheosis/FE/data component/item identity e objetivo de TPS. Providers ativos foram cruzados com a modlist atual; cobertura funcional detalhada por cada adapter não foi inventada.

> 🔒 **Boundary canônico:** caching pode acelerar a descoberta do tipo de filtro, mas predicates dependentes do stack continuam obrigatoriamente derivados do state atual. Performance não pode trocar correção por stale filtering.
