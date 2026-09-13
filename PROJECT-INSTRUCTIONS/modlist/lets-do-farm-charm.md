# [Let's Do] Farm & Charm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db815d8510c4047f9b979e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** [Let's Do] Farm & Charm
- **Arquivo JAR:** `letsdo-farm_and_charm-neoforge-1.1.23.jar`
- **Versão 1.21.1:** 1.1.23
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Core de farming/food da coleção Let's Do: solos/crops, fertilização/sprinklers, feeding/coops, cooking stations, silos/carts e logística rural.
- **Dependências:** NeoForge 1.21.1 + Architectury. Runtime físico também contém Sable 2.0.5; Candlelight e outros Let's Do consomem Farm & Charm como core conforme seus próprios metadados.
- **Sobreposição:** É o core agrícola/culinário da coleção; Candlelight e outros addons o expandem. Pode cruzar com Farmer's Delight/KubeJS/Create em recipes e automação, exigindo IDs/tags/remainders coordenados.
- **Compatibilidade/Riscos:** 1.1.23 corrige diretamente duplication/storage e mixin incompatível com Sable/Create Aeronautics, além de Cooking Pot/Roaster containers, Chicken Coop tick/AI e cart tracking. Stack físico exige regressão desses pontos.
- **Observações:** Source oficial `Let-s-Do-Collection/FarmAndCharm` branch 1.21.1 declara exatamente 1.1.23. Tags plurais `c:flours`/`c:doughs` adicionadas preservando aliases legados.
- **Procedência:** modlist.txt física atual + source oficial FarmAndCharm branch 1.21.1 + arquivos de build, README e changelog oficiais, cruzados com Sable 2.0.5 fisicamente presente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lets-do-farm-charm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — source 1.1.23 pinado; farming/soil, animal feeding/coops, cooking, carts/silo, tags, Sable/Create Aeronautics compat, lifecycle, risks e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🌾 **ESCOPO CANÔNICO.** Runtime físico: `letsdo-farm_and_charm-neoforge-1.1.23.jar`, mod id `farm_and_charm`, versão `1.1.23`. É o **Farming & Food Core** da coleção Let's Do: agricultura, soil/crops, alimentação animal, processamento, cooking stations e logística rural.

## 1. Identidade e source pin
O source oficial `Let-s-Do-Collection/FarmAndCharm`, branch `1.21.1`, declara exatamente `mod_version=1.1.23`, Minecraft 1.21.1, Architectury 13.0.8 e NeoForge 21.1.x. O JAR físico e o source convergem na versão.

## 2. Papel e ownership
O README oficial define Farm & Charm como core de farming/food da coleção. O mod é authority de seus crops, soils, feeding systems, carts, cooking machines, recipes e states próprios. Addons como Candlelight podem reutilizar seus sistemas, mas não substituem esse ownership.

## 3. Agricultura e solo
O mod adiciona Improved Farmland, Fertilized Soil, fertilizer de múltiplas aplicações, Water Sprinklers, Scarecrows e novos grains/fruits. Crescimento, hidratação, fertilização e drops devem ser determinados pelo state do bloco/crop no servidor. O changelog mostra que o Sprinkler foi ampliado para hidratar blocos que estendem `FarmBlock`.

## 4. Feeding Troughs e animais
Feeding Troughs usam o tag próprio `farm_and_charm:feeding_trough_food`; Water Troughs fornecem água. O source confirma lógica de inserção server-side e consumo da stack. O histórico também mostra correções de AI para chickens/coops e de crashes quando Create Deployers interagiam com animais. Integrações devem respeitar o provider e não alimentar/creditar duas vezes o mesmo evento.

## 5. Chicken Coop e state persistente
Chicken Coop armazena/coordena galinhas e ovos, e versões anteriores tiveram problemas de UUID/entity data e ticks após o bloco ser removido. A 1.1.23 corrige crash de tick quando a BlockEntity deixa de ser válida e descarta targets de coop obsoletos. Isso torna chunk lifecycle, save/restart e remoção/movimentação parte obrigatória do QA.

## 6. Cooking pipeline
O mod possui Meat Grinder/Mincer, Cooking Pot, Stove, Roaster e Crafting Bowl. A 1.1.23 exige match exato de ingredientes no Stove, evita infinite bottles/bowls no Cooking Pot/Roaster e mantém várias correções anteriores de progress, remainder, stirring e state reset. Recipes custom/KubeJS devem preservar essas semânticas e IDs.

## 7. Carts e transporte
Carts transportam harvests e possuem tracking próprio. A 1.1.23 corrige um server crash quando um cart puxado se desprendia durante tick, por exemplo ao prender em bloco. Movement/attachment state precisa ser authoritative no servidor e robusto a chunk borders/unload.

## 8. Silo e multiblock
O changelog 1.1.20 otimiza Silo multiblock, reduzindo blockstate updates e atualizando conectividade apenas em mudanças estruturais. Automação/observers não devem provocar rebuild de state desnecessário. Conteúdo exibido por Jade é representação, não authority do inventory.

## 9. Sable/Create Aeronautics — compatibilidade concreta
A versão **1.1.23** corrige diretamente um glitch de duplicação em storage blocks usados com **Sable/Create: Aeronautics** e remove um `ItemStack` mixin desnecessário que causava incompatibilidade. O pack físico contém `sable-neoforge-1.21.1-2.0.5.jar`; portanto esse histórico é diretamente relevante ao stack atual. A versão 1.1.22 também menciona compatibilidade Sable/Create Aeronautics.

## 10. Tags e interop
A 1.1.23 adiciona tags plurais `c:flours` e `c:doughs` preservando aliases singulares antigos. Isso melhora interop de recipes, mas scripts do pack devem preferir tags canônicas/atuais e não duplicar recipes para singular + plural sem necessidade.

## 11. GUI e client/server boundary
A 1.1.23 corrige background duplicado em container GUIs e melhora compatibilidade com Blur+. GUI/render são client-side; inventories, crafting, food/effects, animal AI e cart state pertencem ao servidor. Receiver/payload history anterior também reforça a necessidade de dedicated-server smoke tests.

## 12. Lifecycle e persistência
Validar cold boot, `/reload` para recipes/data, save/restart de coops/carts/silos, chunk unload, removal/replacement de BlockEntities, crop state e inventories. Remainders e outputs devem ser produzidos exatamente uma vez mesmo com automação concorrente.

## 13. Riscos técnicos
1. **Storage duplication** em interações móveis/contraptions — corrigido em 1.1.23, mas precisa regressão com Sable físico.
2. **Container duplication** em Cooking Pot/Roaster/KubeJS recipes.
3. **Animal AI stale target** após mover/quebrar coop.
4. **Cart tracking corruption** em detach/chunk edges.
5. **BlockEntity lifecycle** em coop/silo/stations.
6. **Recipe ambiguity** se scripts ignorarem exact ingredient match.
7. **Effect duplication** com Candlelight/other food mods.
8. **Tag overlap** entre vários mods culinários.

## 14. Matriz de testes
- [ ] Dedicated server inicia com Farm & Charm 1.1.23 + stack atual.
- [ ] Feeding Trough consome item uma vez e animal recebe efeito/feeding uma vez.
- [ ] Create Deployer interage com feeding systems sem crash/dupe.
- [ ] Chicken Coop sobrevive a break/move/restart sem stale chickens/UUID duplication.
- [ ] Stove rejeita recipes com ingredientes extras não previstos.
- [ ] Cooking Pot/Roaster consomem bowls/bottles apenas conforme recipe.
- [ ] Silo mantém inventory e não gera update storm.
- [ ] Cart detach/re-attach e chunk crossing não corrompem tracking.
- [ ] Storage em contraption Sable/Create Aeronautics não duplica itens.
- [ ] `/reload` não duplica recipes ou handlers.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- modlist física: Farm & Charm 1.1.23 + Sable 2.0.5 + Architectury 13.0.11 + Cloth Config 15.0.140;
- source oficial branch 1.21.1: versão 1.1.23 e contratos do mod;
- README oficial: farming, feeding, crops, carts e cooking;
- CHANGELOG oficial 1.1.23 e histórico 1.1.x: fixes de Sable, storage, containers, coop, cart, recipes e GUI.
Nenhum comportamento não sustentado por essas fontes foi promovido a contrato.
