# Create: Central Kitchen

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81c28080e2e1ca401d1b  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Central Kitchen
- **Arquivo JAR:** `create-central-kitchen-2.6.0.jar`
- **Versão 1.21.1:** `2.6.0`
- **Categoria:** Comida; Compat; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-central-kitchen
- **Função:** Bridge de automação culinária entre Create e Farmer's Delight/ecossistema de comida: adapta Packagers, Mechanical Arms e processos Create para Cooking Pots, Cutting Boards, Kegs, Ovens, Casks e outras kitchenwares suportadas.
- **Dependências:** Release 2.6.0 requer Create 6.0.10, Create: Dragons Plus 1.11.4 e Farmer's Delight 1.3.2+. Integrações com Cultural Delights, Rustic Delight, Festive Delight, Hearth and Harvest, Brewin' and Chewin' e Extra Delight são condicionais.
- **Compatibilidade/Riscos:** Riscos em recipe conversion/prioridade, reusable tools, shared cached recipe results, staged feast state, fermentation e Packager/Arm atomicity. 2.6.0 corrige precedence de Create Filling/Emptying sobre Keg fallbacks e mutação indevida de cached results do Extra Delight.
- **Sobreposição:** É bridge de cooking automation. Create controla primitives de automação; Farmer's Delight/addons controlam recipes/kitchenware; Central Kitchen adapta as duas authorities sem se tornar food-content provider geral.
- **Observações:** mod id `create_central_kitchen`; runtime 2.6.0. Desde 2.0, o projeto foca Cooking Automation; farming foi separado para Create: Integrated Farming. 2.6.0 adiciona integrations Cultural/Rustic/Festive/Hearth and Harvest e Ponder/Arm Targets/High Logistics.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_central_kitchen` 2.6.0 + CurseForge/Modrinth oficiais da release 2.6.0 e documentação de features da linha 2.x.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Central Kitchen 2.6.0 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — cooking-automation authority, Packager/Mechanical Arm conversions, heat sources, recipe priority/cache behavior, optional integrations e regressões 2.6.0 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🍳 Versão física confirmada: `create-central-kitchen-2.6.0.jar`, mod id `create_central_kitchen`, runtime `2.6.0`, NeoForge 1.21.1. É uma **bridge de cooking automation**, não um segundo provider de recipes culinárias.

## 1. Papel e authority
Create: Central Kitchen adapta mecanismos do Create para kitchenware e recipes de Farmer's Delight e addons suportados. Create continua authority de Mechanical Arm/Packager/processing; os mods culinários continuam authority de seus recipes, containers, feast states e fermentation.

## 2. Escopo atual da linha 2.x
Desde 2.0, o projeto foi refocado em **Cooking Automation**. Farming automation foi separado para Create: Integrated Farming. Isso evita atribuir ao 2.6.0 features agrícolas históricas que agora pertencem a outro addon.

## 3. Dependências 2.6.0
A release exata requer:
- Create 6.0.10;
- Create: Dragons Plus 1.11.4;
- Farmer's Delight 1.3.2+.

Integrações adicionais só devem ser ativadas/avaliadas quando o mod alvo estiver realmente presente.

## 4. Packager e kitchenware
Packagers podem desempacotar ingredientes para kitchenware suportada. O addon deve distribuir inputs de forma determinística e deixar o container/recipe owner decidir quando o recipe está completo.

Não deve haver item simultaneamente no package e no inventory alvo após commit.

## 5. Mechanical Arm
Mechanical Arms recebem targets para Cooking Pot, Skillet, Stove, Cutting Board e integrações adicionais. O Arm decide transporte; a kitchenware decide validade/estado do recipe.

Em feasts multi-stage, servir/refill precisa ser atômico para impedir dupe ou troca de sabor/variante.

## 6. Cutting Board conversions
Recipes de Cutting Board podem ser convertidos para Sawing/Deploying conforme o tipo. Na 2.6.0, Sawing usa o processing duration correto e reusable tools sem durability damage não são consumidas.

Esse é um regression gate importante para facas/ferramentas modded.

## 7. Brewin' and Chewin' Keg
Keg pouring pode ser adaptado a Filling/Draining. A 2.6.0 corrige a prioridade: recipes nativos de Create Filling/Emptying devem vencer os fallbacks de Keg, inclusive no JEI e após recipe reload.

Não permitir que duas rotas processem o mesmo input.

## 8. Extra Delight
A 2.6.0 corrige conversions que modificavam resultados compartilhados em cache durante fallback search. Shared recipe objects não devem ser mutados por uma consulta temporária.

Packagers também passam a desempacotar ingredientes em Extra Delight Ovens quando suportado.

## 9. Heat sources e boilers
Boiler heaters do Create podem servir como heat sources para cooking, mas a 2.6.0 passa a respeitar remoções da tag `farmersdelight:heat_sources`. Tag/datapack é authority da elegibilidade.

Não hardcodar heat source removido pelo pack.

## 10. Cultural Delights
Mechanical Arms podem servir Eggplant Parmesan Feasts, preservar block state e repor atomicamente a última porção com um feast inteiro compatível. Estado de serving precisa permanecer único.

## 11. Rustic Delight
A integração suporta sabores de pancakes e seus estados não lineares. Refill deve exigir flavor correspondente e remover o block corretamente após a última serving.

## 12. Festive Delight
Festive Chicken possui quatro estágios registrados e leftover final. O Arm deve avançar a sequência sem pular/duplicar serving.

## 13. Hearth and Harvest
Mechanical Arms e Packagers podem abastecer Casks recipe-by-recipe, separar ingredientes repetidos entre slots, aguardar fermentation normal e extrair apenas outputs finalizados. Central Kitchen não deve acelerar artificialmente o timer fora do contract.

## 14. Ponder e High Logistics
2.6.0 adiciona Ponder scenes, Arm Targets e High Logistics entries. Essas superfícies são documentação/integração com Create 6; recipes e inventories continuam server-authoritative.

## 15. Client/server e lifecycle
Recipe conversion, inventories, fermentation e item movement são common/server. Ponder/render de chef/model assets são client-facing.

Validar datapack/recipe reload, server restart, chunk unload e optional-mod presence/absence sem manter conversion caches stale.

## 16. Riscos
1. Dois recipes competirem pelo mesmo input.
2. Packager duplicar item ao unpackar.
3. Reusable tool ser consumida indevidamente.
4. Feast/Cask state avançar duas vezes.
5. Cached recipe result ser mutado globalmente.
6. Heat-source tag ser ignorada.
7. Optional integration classload sem mod alvo.
8. JEI mostrar rota diferente da authority real.

## 17. Matriz de testes
1. Dedicated server boot com requirements 2.6.0.
2. Cooking Pot/Skillet/Stove/Cutting Board com Arm e Packager.
3. Sawing/Deploying conversions e reusable tools.
4. Keg conversion versus native Filling/Emptying antes/depois de recipe reload.
5. Extra Delight Oven e cached fallback behavior.
6. Heat-source tag removendo um boiler heater.
7. Cada integração opcional presente no pack, uma por vez e combinada.
8. Cask fermentation/repeated ingredients/output extraction.
9. Multiplayer com dois Arms/Packagers sobre a mesma kitchenware.

## 18. Evidência
- modlist física 08/09/2026: Central Kitchen 2.6.0;
- release oficial 2.6.0: requirements, integrations e fixes;
- documentação 2.x: foco em cooking automation e conversions Create↔kitchenware.

> 🔒 Boundary canônico: **Central Kitchen adapta automação; não possui os recipes/comida que automatiza**. O provider culinário mantém a semântica do recipe e Create mantém a logística/processamento.
