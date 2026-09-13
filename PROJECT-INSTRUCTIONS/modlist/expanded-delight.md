# Expanded Delight — 0.1.4

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81a4b118e7d4f2651d79  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Expanded Delight
- **Arquivo JAR:** `expandeddelight-0.1.4.jar`
- **Versão 1.21.1:** `0.1.4`
- **Categoria:** Comida
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/expanded-delight
- **Função:** Addon de Farmer's Delight que expande crops, ingredientes, alimentos, worldgen e processing/workstations; o feature tracker inclui vegetables, peanut/cinnamon/salt, cheese, juices/jellies e várias refeições, sujeito ao registry exato da build.
- **Dependências:** Farmer's Delight 1.3.4 está fisicamente presente. O pack também possui Farmer's Delight: Extended 0.2.2 e vários outros addons culinários, exigindo auditoria de recipes/tags por ID e não por tema.
- **Compatibilidade/Riscos:** Issue upstream #118 aberto reporta que Expanded Delight em 1.21.1 NeoForge remove/substitui a receita original de dough do Farmer's Delight; precisa ser reproduzido no pack. Outros riscos: recipe/tag overlap, worldgen duplicado, workstation overlap, food-balance drift e feature tracker conter conteúdo ainda não lançado.
- **Sobreposição:** Sobreposição temática com Farmer's Delight: Extended e outros food addons não implica duplicação. Comparar recipe IDs, tags, crops, food stats e workstations. O conflito de dough #118 é um overlap técnico específico a validar.
- **Observações:** Tracker oficial do projeto lista várias famílias de crops/foods/resources/workstations, mas avisa que acompanha pushes e pode conter conteúdo ainda não disponível na release. Por isso o registry/JEI da 0.1.4 é authority para inventário final. Issue #118 foi registrado como regression gate, não como bug confirmado localmente.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `expandeddelight-0.1.4.jar`, mod id `expandeddelight`, versão 0.1.4 e SHA-1 cf26fe18c3a9deec525fe0f0e7c2d3b9f4e9f484; Farmer's Delight 1.3.4 e Farmer's Delight: Extended 0.2.2 presentes.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Expanded Delight 0.1.4; food/worldgen/workstation scope, FD 1.3.4 ownership, feature-tracker boundary, dough issue #118, lifecycle, multiplayer, risks and tests cataloged.
- **Data da última decisão:** 2026-08-26

> **Runtime físico confirmado:** `expandeddelight-0.1.4.jar` · mod id `expandeddelight` · versão `0.1.4` · NeoForge 1.21.1 · Client & Server. Base física: **Farmer's Delight 1.3.4**; também existe **Farmer's Delight: Extended 0.2.2** no pack.

## 1. Papel no modpack
Expanded Delight é um addon de Farmer's Delight voltado a ampliar crops, ingredientes, alimentos, geração e workstations. O projeto está em desenvolvimento; conteúdo físico/recipes deve ser validado no JAR/JEI antes de assumir que todo item do feature tracker do repositório pertence exatamente à build 0.1.4.

## 2. Authority / ownership
- **Farmer's Delight:** cooking framework/base recipes, cooking pot/cutting board e food ecosystem principal.
- **Expanded Delight:** crops, ingredients, foods, workstations e recipes que registra.
- **Worldgen:** features do addon para wild crops/recursos quando realmente presentes.

Outros addons culinários não devem reescrever recipe IDs sem decisão explícita de pack.

## 3. Feature tracker do projeto
O tracker oficial lista famílias de conteúdo como:
- vegetables/crops: asparagus, sweet potato, chili pepper e peanut;
- recursos: cinnamon e salt;
- cheese/dairy;
- sandwiches, cookies, salads, soups e meals;
- fruit juices/jellies;
- wild crops, cinnamon tree e salt ore;
- functional blocks/tools como cask, juicer e crushing/grinding tooling conforme loader/version.

O próprio tracker avisa que acompanha pushes do repositório e pode conter conteúdo ainda não presente na release; portanto o registry 0.1.4 é a autoridade final.

## 4. Crops e worldgen
Wild crops e recursos naturais ampliam a superfície de worldgen. Em mundos existentes, ausência em chunks já gerados não é bug. Biome tags e placement precisam ser testados com o worldgen real do pack.

Não atribuir geração de uma espécie específica à 0.1.4 sem confirmar o registry/datapack da build.

## 5. Processing / workstations
O projeto usa processing culinário adicional para cheese, juices e grinding. Cada recipe type deve manter ownership único; Create/Central Kitchen pode automatizar receitas sem duplicar o recipe result no provider original.

## 6. Farmer's Delight 1.3.4
A base física é FD 1.3.4. Qualquer update do Farmer's Delight pode alterar tags/recipes/interfaces usadas por Expanded Delight. O addon 0.1.4 precisa ser regression-tested contra esta versão, não apenas contra releases antigas.

## 7. Issue aberto #118 — dough recipe
Existe issue upstream aberto específico de **1.21.1 NeoForge** reportando que, com Expanded Delight instalado, a receita original de dough de Farmer's Delight (`wheat + water bucket`, conforme o relato) deixa de aparecer/funcionar e ficam alternativas do Expanded Delight com 3 wheat + egg ou salt/water.

Isso é evidência de um conflito de recipe real reportado, não prova de reprodução neste pack. Deve entrar no QA de recipes.

## 8. Stack alimentar local
O pack possui muitos addons de Farmer's Delight, além de Farmer's Delight: Extended. Sobreposição deve ser julgada por **recipe ID, ingredient tags, food stats e workstation ownership**, não apenas por ambos adicionarem comida.

## 9. Client / Server
**Servidor:** crop growth, item/food state, recipes, worldgen, hunger/effects e processing.

**Cliente:** models/textures, screens/tooltips/JEI presentation.

Recipe existence e food effects são server-authoritative.

## 10. Lifecycle
Validar new world vs existing world, crop grow/harvest/replant, recipe reload, tag reload, workstation processing, chunk generation, server restart, datapack override e update de FD/Expanded Delight.

## 11. Multiplayer
Workstations e inventories precisam manter single server authority. Dois players usando o mesmo processing block não podem duplicar output. Crop harvesting não pode produzir ghost state no cliente.

## 12. Riscos
1. recipe ID substituir receita base de FD;
2. dough recipe #118 reproduzir;
3. ingredient tag conflict com outro food addon;
4. duplicate crop/resource worldgen;
5. workstation recipe type overlap;
6. food stats/buffs desbalancearem hunger progression;
7. worldgen em existing world parecer ausente;
8. Create automation consumir/produzir item errado por tag ampla;
9. feature tracker ser confundido com registry exato 0.1.4;
10. update FD 1.3.4+ quebrar compat.

## 13. Matriz de testes
1. Dedicated server com FD 1.3.4 + Expanded Delight 0.1.4.
2. JEI/recipe dump do registry 0.1.4.
3. Reproduzir dough recipe vanilla-FD e alternativas Expanded Delight.
4. Grow/harvest/replant cada crop efetivamente registrado.
5. Wild generation em chunks novos.
6. Cask/juicer/grinding blocks realmente presentes na build.
7. Tag interoperability com outros food addons.
8. Create/Central Kitchen automation sobre recipes relevantes.
9. Dois jogadores no mesmo workstation.
10. `/reload` + restart.
11. Existing world e new world.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/mod id/version/hash + FD 1.3.4/FD Extended 0.2.2;
- CurseForge/Modrinth oficial: addon Farmer's Delight, NeoForge 1.21.1, Client & Server;
- GitHub oficial issue #1: feature tracker, explicitamente sujeito a conteúdo ainda em development;
- GitHub issue #118: recipe de dough removida reportada em 1.21.1 NeoForge.

> **Boundary canônico:** Expanded Delight é authority apenas dos **crops/foods/recipes/workstations que sua build registra**. Farmer's Delight continua a base culinária.