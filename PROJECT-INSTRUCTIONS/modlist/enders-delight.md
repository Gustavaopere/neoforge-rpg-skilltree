# Ender's Delight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81988966f22e0795aa71
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Ender's Delight
- **Arquivo JAR:** `endersdelight-1.3.1.jar`
- **Versão 1.21.1:** 1.3.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Addon culinário Client & Server de Farmer's Delight voltado ao End, convertendo chorus fruit, ender pearls e novos drops de Endermen/Shulkers/Endermites em ingredientes, pratos e processing temático.
- **Dependências:** Farmer's Delight é dependência obrigatória e está presente como 1.3.4. Runtime Ender's Delight 1.3.1 NeoForge 1.21.1; file 8570814 publicado em 03/08/2026.
- **Sobreposição:** Sobreposição temática/econômica com outros Farmer's Delight addons, mas os ingredientes e cadeia culinária do End são próprios. Farmer's Delight continua authority do cooking/cutting framework; Ender's Delight registra conteúdo/recipes próprios.
- **Compatibilidade/Riscos:** Riscos de recipe/tag/cutting API drift com Farmer's Delight, food effect/balance overlap com outros culinary addons, loot/drop duplication em mobs do End, Endstone Stove inventory/state, structure/loot generation e cutting recipe result format. 1.3.1 corrige acesso a unbound values, Chorus Pie Slice result format e all cutting recipes.
- **Observações:** Conteúdo oficial atual usa recursos do End como ender pearls e chorus fruits e drops próprios como Ender Sight, Shulker Mollusk e Mite Crust. A rework 1.21.1 introduziu 4 Ender vegetables, 5 foods, uma structure e JEI info para droppable items. 1.3.1 é a release física atual.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `endersdelight-1.3.1.jar`, mod id `endersdelight`, versão 1.3.1 e Farmer's Delight 1.3.4; SHA-1 físico a3a9f7fd621f14461d37357463dbd16c67d2badf corresponde ao file 8570814. CurseForge oficial confirma escopo e changelog 1.3.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enders-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Ender's Delight 1.3.1; End ingredients/drops, food chain, Endstone Stove/rework lineage, Farmer's Delight cutting pipeline, lifecycle, economy overlap, risks and tests cataloged.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `endersdelight-1.3.1.jar` · mod id `endersdelight` · versão `1.3.1` · NeoForge 1.21.1 · Client & Server.

## 1. Papel no modpack
Ender's Delight é um addon de **Farmer's Delight** dedicado à gastronomia do End. Ele transforma recursos existentes e novos drops de criaturas do End em ingredientes, pratos e processing dentro do framework culinário Farmer's Delight.

## 2. Authority / ownership
- **Farmer's Delight:** cooking/cutting framework, recipe types e contracts culinários-base.
- **Ender's Delight:** seus drops, ingredients, foods, blocks/structure e recipes.
- **Minecraft/End mobs:** entidades vanilla e seus lifecycle/loot hooks.

Outros addons culinários podem consumir os mesmos tags, mas não devem reaplicar o recipe/loot do Ender's Delight sem intenção explícita.

## 3. Ingredientes-base do End
A descrição oficial cita uso de:
- **Ender Pearls**;
- **Chorus Fruits**;
- drops de Endermen, Shulkers e Endermites.

Isso ancora a progressão culinária na exploração do End em vez de criar uma cadeia agrícola completamente separada.

## 4. Drops próprios confirmados
A página oficial destaca:
- **Ender Sight:** olho instável de Enderman, descrito como produtor de luz ofuscante;
- **Shulker Mollusk:** parte interna/molusco do Shulker;
- **Mite Crust:** pele crocante/pungente de Endermite.

Esses drops entram na cadeia de receitas e devem ser tratados como conteúdo do addon, não como loot genérico de outro provider.

## 5. Rework 1.21.1
A rework da linha 1.21.1 adicionou oficialmente:
- **4 Ender vegetables**;
- **5 new foods**;
- **1 new structure**;
- JEI info para itens dropáveis.

Também corrigiu tooltips de Ender Paella, Mite Crust, Enderman Sight e Shulker Mollusk, além de iluminação do Endstone Stove.

Esta ficha não inventa nomes/stats dos quatro vegetais ou cinco foods além do que está confirmado na documentação/runtime consultados.

## 6. Endstone Stove
O Endstone Stove é uma superfície de block entity/cooking própria do addon. Ele participa do processing temático e recebeu correções históricas na linha 1.21.1.

Inventory, fuel/process state, drops ao quebrar e save/load precisam permanecer server-authoritative. Automação externa deve usar os handlers/contracts válidos da build atual.

## 7. Farmer's Delight cutting pipeline
A release física **1.3.1** corrige:
- tentativa de acessar **unbound values**;
- formato do resultado do recipe de cutting para **Chorus Pie Slice**;
- **all cutting recipes**.

Isso torna cutting board/knife integration um regression gate específico da versão instalada.

## 8. Farmer's Delight 1.3.4
O pack usa Farmer's Delight `1.3.4`, acima da linha 1.3.1 citada em alguns históricos de compatibilidade. Como Ender's Delight 1.3.1 é a release atual para 1.21.1, a combinação deve ser testada diretamente; não rebaixar Farmer's Delight por inferência a partir de issues de builds antigas.

## 9. Food effects e balanceamento
Pratos podem fornecer nutrition/saturation/effects definidos pelo addon/FD. O pack possui muitos addons de comida, então risco principal é **economia/nutrition overlap**, não necessariamente conflito técnico.

Balanceamento externo deve alterar recipes/stats deliberadamente e evitar aplicar uma segunda cópia do mesmo food effect.

## 10. Loot de mobs
Novos drops ligados a Endermen/Shulkers/Endermites precisam liquidar uma vez no servidor. Outros loot modifiers do pack podem tocar as mesmas entidades; qualquer duplicação deve ser analisada por loot table/modifier e não presumida só pela coincidência do mob.

## 11. Structure / world content
A rework adicionou uma estrutura própria. Worldgen/loot de estrutura deve ser validado em chunks novos e tratado como parte do save. Atualizar o addon no meio de um mundo pode criar diferenças entre áreas antigas e novas.

## 12. JEI / informação de recipe
A linha 1.21.1 adicionou JEI info para droppable items. JEI está presente no pack, mas é apenas presentation/discovery; o recipe/loot real permanece nos data/resources e no servidor.

## 13. Data layer
Recipes, cutting recipes, loot e tags formam a maior superfície de integração. Datapacks externos podem substituir receitas sem atualizar automaticamente qualquer documentação/tooltip.

Após `/reload`, validar recipes críticos e não usar a presença no JEI como única prova de que o recipe executa corretamente.

## 14. Client / Server
- food consumption, recipes, loot, stove/inventory e structure: server-authoritative;
- models, textures, tooltips, JEI presentation: client-facing;
- client não deve conceder food/drop nem concluir recipe por conta própria.

## 15. Lifecycle
Validar:
- mob loot;
- cooking/cutting;
- Endstone Stove place/break/save/restart;
- chunk unload/reload;
- structure generation em chunk novo;
- food consume/effect expiry;
- datapack reload;
- reconnect multiplayer;
- update de Farmer's Delight.

## 16. Multiplayer / idempotência
Dois jogadores usando a mesma station ou coletando loot não podem duplicar item settlement. Block entity inventory e cutting/cooking output devem ser confirmados uma vez no servidor.

## 17. Sobreposição culinária
Há muitos addons Farmer's Delight no pack. Sobreposição deve ser avaliada por ingredient tags, recipes e nutrition progression. Ender's Delight não é redundante apenas porque outro addon também adiciona comida: seu domínio específico são recursos e criaturas do End.

## 18. Riscos
1. cutting recipe format drift;
2. Chorus Pie Slice não produzir output correto;
3. unbound-value access regressar;
4. Farmer's Delight API mismatch;
5. Endstone Stove inventory/save bug;
6. loot modifier duplicar drops de End mobs;
7. nutrition/effect overlap;
8. structure loot/worldgen inconsistente;
9. JEI mostrar recipe que datapack substituiu;
10. tag de ingredient colidir com outro addon;
11. chunk old/new structure seam;
12. recipe reload deixar cache stale.

## 19. Matriz de testes
1. Dedicated server boot com Farmer's Delight 1.3.4.
2. Obter Ender Sight, Shulker Mollusk e Mite Crust de forma legítima.
3. Verificar JEI/info dos drops.
4. Testar cutting recipes, especialmente Chorus Pie Slice.
5. Testar amostra dos foods/vegetables da rework.
6. Endstone Stove: cook, break/drop, unload/reload, restart.
7. Gerar chunks novos e localizar a estrutura própria.
8. Food effects em dois jogadores.
9. `/reload` e repetir cutting/cooking.
10. Smoke-test após qualquer update de Farmer's Delight.

**Esta catalogação não afirma que esses testes foram executados.**

## 20. Evidências
- modlist física canônica: JAR/mod id/version/hash + Farmer's Delight 1.3.4;
- CurseForge oficial: escopo End-themed Farmer's Delight, Ender Sight/Shulker Mollusk/Mite Crust;
- changelog oficial da rework 1.21.1: vegetables, foods, structure, JEI info, Endstone Stove/tooltips;
- changelog oficial 1.3.1: unbound values, Chorus Pie Slice cutting result e all cutting recipes.

> **Boundary canônico:** Ender's Delight possui seus **ingredientes/foods/recipes/drops**; Farmer's Delight continua sendo authority do framework culinário.
