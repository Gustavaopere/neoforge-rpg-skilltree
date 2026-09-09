# Farmer's Delight — 1.3.4

> **Runtime físico confirmado:** `FarmersDelight-1.21.1-1.3.4.jar` · mod id `farmersdelight` · versão `1.3.4` · NeoForge 1.21.1 · Client & Server. Esta ficha trata Farmer's Delight como provider-base do grande stack culinário instalado.

## 1. Papel no modpack
Farmer's Delight é a base culinária/agropecuária de dezenas de addons do pack. Ele amplia cultivo, ingredientes, preparação e apresentação de refeições, além de fornecer workstations e contracts que bridges como Farmer's Spell, Extended, Expanded Delight, Ender's Delight e vários outros consomem.

## 2. Authority / ownership
- **Farmer's Delight:** workstations base, recipes próprios, crops/ingredients, foods/feasts, cabinets/baskets e regras dos seus blocos/itens.
- **Addons FD:** apenas conteúdo e recipes adicionais que registram.
- **Create/bridges:** automação/integração das recipes, sem assumir ownership do recipe result ou food state original.

Evitar substituir IDs/recipes base sem decisão explícita de pack.

## 3. Workstations source-confirmed
O registry 1.21 inclui `stove`, `cooking_pot`, `skillet`, `cutting_board`, `wooden_basket` e `bamboo_basket`. Cada estação tem semantics próprias de interação/processamento. A Cooking Pot e o Cutting Board são superfícies de integração particularmente usadas por addons e automação.

## 4. Crops e ingredientes centrais
O conteúdo público da linha 1.21.1 inclui os crops/ingredientes característicos **rice, cabbage, tomato e onion**, além de straw e alimentos derivados. A ausência de uma crop em chunks antigos não deve ser confundida com falha de recipe; worldgen/loot/obtenção precisa ser avaliado separadamente.

## 5. Storage e building content
O source registra crates de carrot, potato, beetroot, cabbage, tomato e onion; `rice_bale`, `rice_bag`, `straw_bale`; rope, safety net, rope fence/gate; cabinets de múltiplas madeiras; canvas/tatami e sinais de canvas. Isso mostra que Farmer's Delight não é apenas um recipe pack: também possui storage/decor/utility blocks com state próprio.

## 6. Cooking Pot
O Cooking Pot mantém inventory/process state e produz refeições através de recipes. Em multiplayer, output e container items precisam ser server-authoritative. Automação Create ou pipes deve interagir com o handler real, sem duplicar output nem retirar input duas vezes.

## 7. Cutting Board
O Cutting Board é uma superfície de processamento manual/data-driven consumida por muitos addons. Recipe matching, tool/ingredient tags e output devem ser validados após `/reload`; uma bridge não deve duplicar a mesma cutting recipe sob outro ID sem necessidade.

## 8. Skillet e Stove
Skillet e Stove têm lifecycle de uso/render próprios. O Stove também funciona como bloco emissivo quando aceso. Rendering/animation são client-facing; cook/use state e item consequences pertencem ao servidor.

## 9. Feasts e servings
Farmer's Delight usa food/feast blocks com múltiplas porções. Esses blocos exigem single-settlement por serving, persistência após chunk reload e comportamento correto sob piston quando suportado. A release 1.3.4 corrigiu um comportamento quebrado ao empurrar Feasts com pistons.

## 10. Release 1.3.4 — correções relevantes
A changelog oficial 1.3.4 registra:
- correção de comportamento quebrado ao empurrar Feasts com pistons;
- em 1.21.1+, correção do config `generateFDChestLoot`, que não desabilitava corretamente a geração adicional de loot em baús.

Esses dois pontos entram explicitamente no QA local.

## 11. Loot e configuração
Farmer's Delight pode injetar loot adicional em baús quando a configuração correspondente permite. O valor efetivo de `generateFDChestLoot` deve ser lido do config da instância; não assumir default como decisão do pack. Loot tables/tags/recipes precisam ser tratados como datapack data e reavaliados após reload/update.

## 12. Addons fisicamente relevantes
O pack contém, entre outros, Farmer's Delight: Extended, Expanded Delight, Ender's Delight, Alex's Delight, Cuisine Delight, Farmer's Spell 'n Spellbooks e Ars Nouveau's Flavors & Delight. Essas relações aumentam a superfície de recipe/tag overlap.

O critério de conflito deve ser **ID, recipe type, ingredient tag, workstation e output**, não apenas tema culinário.

## 13. Relação com Expanded Delight
Há issue upstream em Expanded Delight 1.21.1 reportando substituição/remoção da recipe original de dough do Farmer's Delight. Isso não prova bug local no Farmer's Delight; deve ser testado como interação entre os dois addons e recipe reload order.

## 14. Relação com Create
Create pode automatizar processing através de bridges/addons, mas Farmer's Delight continua authority das recipes FD e state de suas workstations. Qualquer integração deve evitar double-processing, container duplication e tag broadening inesperado.

## 15. Client / Server
**Servidor:** crops, growth, inventory, recipes, food/effects, feast servings, block entity state, loot e processing.

**Cliente:** screens, models, particles, item/block rendering e recipe-viewer presentation.

Dedicated server precisa carregar toda lógica de food/workstation sem classes client-only.

## 16. Lifecycle
Validar crop grow/harvest/replant, cooking/cutting start-finish, block entity unload/reload, feast serving, piston movement, cabinet/basket save, death/relog com effects, `/reload`, server restart, new chunks vs existing chunks e update de addons.

## 17. Multiplayer
Dois jogadores na mesma estação não podem consumir/produzir duas vezes. Containers devem sincronizar slots corretamente. Harvest/feast interactions precisam ter uma única authority server-side; clients não podem criar ghost output.

## 18. Riscos
1. duplicate recipe IDs entre addons;
2. ingredient tags excessivamente amplas;
3. double-processing via Create/compat;
4. dupe de feast/serving em piston ou concorrência;
5. `generateFDChestLoot` não respeitado após config migration;
6. worldgen/loot density inflada pelo stack;
7. stale BlockEntity inventory após unload;
8. food/effect balance overlap;
9. cabinet/storage handler incompatível com automação;
10. update 1.3.x alterar contract usado por dezenas de addons;
11. recipe-viewer mostrar recipe que server datapack removeu;
12. confundir addon bug com bug do mod-base.

## 19. Matriz de testes
1. Dedicated server boot com stack FD atual.
2. Cultivo/harvest de rice, cabbage, tomato e onion.
3. Cooking Pot manual e automatizado.
4. Cutting Board com recipes base e de addons.
5. Skillet/Stove lifecycle.
6. Feast serving + piston regression 1.3.4.
7. `generateFDChestLoot` on/off em world de teste.
8. Cabinet/basket save/reload e automação.
9. `/reload` com recipes/tags modificadas.
10. Dois jogadores usando mesma workstation.
11. Recipe de dough com Expanded Delight presente.
12. Smoke-test após qualquer update de FD ou bridge principal.

**Esta catalogação não afirma que esses testes foram executados.**

## 20. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- CurseForge oficial: Farmer's Delight 1.3.4 para NeoForge 1.21.1 e changelog da release;
- source oficial `vectorwing/FarmersDelight` branch 1.21: registry de workstations, crates, cabinets e building content;
- páginas dos addons locais apenas para identificar consumers/overlaps concretos.

> **Boundary canônico:** Farmer's Delight é a **base de autoridade culinária** para seus crops, foods, workstations, recipes e storage blocks; addons devem integrar-se a esses contratos sem criar state duplicado.
