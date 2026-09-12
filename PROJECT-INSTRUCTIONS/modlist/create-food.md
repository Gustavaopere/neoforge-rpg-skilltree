# Create: Food

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814b925ef8141120f023
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Food
- **Arquivo JAR:** `createfood-neoforge-1.21.1-2.7.1.jar`
- **Versão 1.21.1:** 2.7.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Automação, Tecnologia
- **Função:** Grande addon culinário do Create, com muitos alimentos, ingredientes e cadeias de produção/automação, além de compatibilidades condicionais.
- **Dependências:** Create + Farmer's Delight são a base prática do conteúdo; pack físico usa Create 6.0.10 e Farmer's Delight 1.3.4. Também há Central Kitchen 2.6.0 e Ratatouille 1.4.0 como integrações/overlaps culinários relevantes.
- **Sobreposição:** Stack culinário denso com Farmer's Delight 1.3.4, Central Kitchen 2.6.0, Ratatouille 1.4.0 e outros. Equivalência deve ser comparada por ingredients, fluids, tags e recipe outputs; não há duplicata global presumida.
- **Compatibilidade/Riscos:** Riscos: common config/registry mismatch; custom content first-launch regression; removal of registered content from worlds; plate/bowl/portion dupe; heat/dipping double-processing; effects/tooltips divergence; recipe overlap with Farmer's Delight/Central Kitchen/Ratatouille; startup/model load volume.
- **Observações:** JAR/mod id/runtime 2.7.1 confirmados. Release 2.7.1 migra todo conteúdo cross-mod para listas de config common/client/server e corrige custom display blocks/fluids e registro no primeiro launch. Source head consultado está em 2.8.0.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + CurseForge oficial Create: Food 2.7.1 + documentação de config/release exata; source AverageAnime/create-food-multiloader tratado como arquitetura posterior por estar em 2.8.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-food
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.7.1 com mais de 1000 foods e mais de 100 fluids, cross-mod config registries, blocks/display/fluid/item lists, tooltips/effects, handcrafting, plates/bowls, heat cooking e food-stack overlaps catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🍲 **Identidade física confirmada:** `createfood-neoforge-1.21.1-2.7.1.jar`, mod id `createfood`, runtime `2.7.1`. A release exata é NeoForge 1.21.1, Client & Server. O branch público atualmente consultado já está em 2.8.0, portanto serve apenas como arquitetura posterior; o delta instalado 2.7.1 é sustentado pela release/documentação exata.

## 1. Papel e authority
Create: Food é uma expansão culinária massiva baseada em Create e Farmer's Delight, com mais de mil itens relacionados a comida e mais de cem fluidos segundo a documentação oficial. O addon owns seus registros, recipes, effects/config e display blocks; os mods de origem continuam owners de seus ingredientes/efeitos quando integrados.

## 2. Dependências e contexto do pack
Create é base técnica e Farmer's Delight é tratado pelo próprio projeto como essencial para grande parte do conteúdo. O pack contém Create 6.0.10 e Farmer's Delight 1.3.4. Também contém Create Central Kitchen 2.6.0, Ratatouille 1.4.0 e outros addons culinários, tornando recipe/tag overlap um gate concreto.

## 3. Conteúdo culinário amplo
A documentação oficial publica **mais de 1000 food-related items e mais de 100 new fluids**. Essa escala torna impraticável e inseguro hardcodar uma lista manual no catálogo; o registry da build 2.7.1 é authority para a enumeração exata.

## 4. Configuração de conteúdo
Itens podem ser ocultados e nutrition/saturation podem ser modificados. O projeto também permite adicionar conteúdo customizado. Config passa a ser parte da definição operacional do pack e precisa ser versionada junto com recipes/datapacks.

## 5. Arquitetura 2.7.1 — cross-mod config
Na 2.7.1, **todo conteúdo de compatibilidade cross-mod passa a ser inteiramente baseado em listas de configuração**. O comportamento pretendido é permanecer equivalente após regenerar essas listas, mas o registry resultante precisa ser validado em startup.

## 6. `createfood-common.toml`
A release 2.7.1 documenta listas common para **block, display_block, fluid e item**. Como essas listas afetam conteúdo comum/registrado, cliente e servidor devem carregar configuração compatível antes de entrar no mundo; alteração de registry não deve ser tratada como simples tooltip reload.

## 7. Block config
A lista `block` aceita categorias como **cake_base, cheese e gyro_meat**. Cakes podem gerar variantes de candle-cake. Cada registro customizado precisa usar IDs válidos e não colidir com outro provider.

## 8. Display blocks e plates
`display_block` aceita `plate_food`, incluindo height fracionário; particles também podem ser configuradas para bowls. A documentação geral permite exibir alimentos em plates/bowls, comer diretamente deles e usar plates como cutting boards.
Visual geometry e interação precisam convergir para o mesmo state do bloco.

## 9. Fluid config
A 2.7.1 permite entrada `fluid` com nome simples para comportamento padrão e corrige custom fluids ausentes da config. Fluid registry, containers e recipes precisam resolver o mesmo ID em cliente/servidor.

## 10. Item config
A lista `item` aceita `plain_cr` e outros tipos definidos pelo provider. Itens custom registrados precisam herdar apenas as propriedades previstas pela categoria, sem assumir effects/nutrition não configurados.

## 11. Tooltips e effects
`createfood-client.toml` contém `custom_tooltips`, inclusive `compat_key` opcional; `createfood-server.toml` contém `effects`. Essa separação é importante: tooltip é presentation client-side, enquanto effects de comida precisam ser decididos no servidor.

## 12. Fixes de registro 2.7.1
A release corrige custom display blocks/fluids ausentes da config e conteúdo custom que não registrava no primeiro launch. O primeiro boot de uma config nova é regression gate: conteúdo não pode aparecer só após reiniciar uma segunda vez.

## 13. Handcrafting
O projeto inclui um **handcrafting system** para recipes simples. Handcrafting não deve criar uma rota paralela que ignora ingredients/conditions de recipe quando a integração é configurada; Recipe Manager/provider continua authority.

## 14. Plates, bowls e cutting-board behavior
Qualquer alimento pode ser exibido em plates/bowls segundo a documentação, e plates podem funcionar como cutting boards. Item↔display-block conversion deve preservar quantidade, recipe state e efeitos; break/interaction não pode duplicar a comida exibida.

## 15. Cloth filters
Há **cloth filters** utilizáveis manualmente ou em recipes. Eles pertencem ao domínio culinário/processing do mod e não são equivalentes aos Attribute Filters do Create Filters Anywhere.

## 16. Heat-adjacent cooking
Ingredientes podem ser cozidos ao serem mantidos próximos a campfire ou outra heat source suportada. Proximity processing precisa usar uma única state machine e não aplicar o mesmo cook transition várias vezes por múltiplas fontes próximas.

## 17. Small display bowls e dipping
Small display bowls podem armazenar fluido para mergulhar alimentos. Fluid amount e item transformation precisam ser conservativos, especialmente com automation/manual interaction concorrentes.

## 18. Storage blocks
A documentação lista **cloth sack** e **ration box** como storage blocks. Inventory capability, break/reload e automation precisam conservar stacks; não assumir integração com Tom's ou Sophisticated sem suporte explícito.

## 19. Placeable food blocks
Pizzas, cakes, pies e outros alimentos podem existir como blocks colocáveis. Portion/eating/break state precisa sobreviver a chunk reload e não produzir item completo novamente após consumo parcial, salvo semântica explícita do provider.

## 20. Effects compat
O projeto implementa effects de Farmer's Delight, Let's Do, Kaleidoscope Cookery e outros quando configurados/providers existem. No pack atual, Farmer's Delight 1.3.4 é integração concreta; providers ausentes não devem gerar classes/itens fantasma.

## 21. Create Central Kitchen e Ratatouille
Create Central Kitchen 2.6.0 e Ratatouille 1.4.0 estão instalados. Eles podem automatizar/expandir culinária, mas não são substitutos globais de Create: Food. O overlap precisa ser auditado por ingredients, fluids, tags e recipe outputs para evitar duas rotas equivalentes com custos muito diferentes.

## 22. Config/registry lifecycle
Mudanças em common config que adicionam/removem registros podem afetar mundos existentes. Remover conteúdo usado em inventories/blocks pode gerar missing registry entries; aplicar mudança exige backup/smoke-test, não apenas `/reload`.

## 23. Client/server e multiplayer
Nutrition, saturation, effects, recipe consumption, inventory e display-block state são server-authoritative. Tooltips/particles/textures são client-facing. Clientes com config divergente não podem registrar conjunto incompatível de conteúdo sem erro explícito.

## 24. Riscos
1. Common config diverge cliente↔servidor e altera registries.
2. Conteúdo custom não registra no primeiro launch, regressão do fix 2.7.1.
3. Fluid/display block configurado desaparece e corrompe mundo existente.
4. Plate/bowl conversion duplica alimento.
5. Portion block reseta após reload.
6. Heat cooking processa item duas vezes.
7. Dipping consome fluido incorretamente.
8. Farmer's Delight/Central Kitchen/Ratatouille criam recipes duplicadas com custos divergentes.
9. Nutrition/effects client tooltip divergem do efeito server-side.
10. Compat entry referencia provider ausente.
11. Mais de mil registros aumentam custo de startup/model/recipe loading.
12. Config regeneration altera IDs ou ordem semanticamente relevante.

## 25. Matriz de testes
- [ ] Dedicated server inicia com Create Food 2.7.1 + Create 6.0.10 + Farmer's Delight 1.3.4.
- [ ] Primeiro launch com config gerada registra custom content conforme fix 2.7.1.
- [ ] Common/client/server configs permanecem consistentes entre lados.
- [ ] Plates/bowls preservam alimento e portions em restart.
- [ ] Cutting-board interaction consome/gera exatamente uma vez.
- [ ] Heat-adjacent cooking não duplica processamento com múltiplas fontes.
- [ ] Dipping conserva item e fluid amount.
- [ ] Cloth sack/ration box persistem inventory.
- [ ] Custom fluids/display blocks aparecem quando configurados e somem apenas com migration consciente.
- [ ] Farmer's Delight effects são aplicados no servidor conforme config.
- [ ] Central Kitchen 2.6.0/Ratatouille 1.4.0 não criam loops ou recipe duplicates perigosos.
- [ ] Cliente com config incompatível falha de forma explícita, não com silent desync.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 26. Evidências e limites
A modlist física confirma runtime 2.7.1 e o stack culinário instalado. A documentação oficial confirma escala de conteúdo, configurabilidade, handcrafting, plates/bowls, cloth filters, heat cooking, dipping, storage e placeable foods. A release 2.7.1 confirma a arquitetura de compat por config e fixes de registro inicial. O source head consultado está em 2.8.0; internals que podem ter mudado após 2.7.1 não são retroprojetados.

> 🔒 **Boundary canônico:** em Create: Food 2.7.1, configuração common participa do próprio catálogo de conteúdo. Registry/config e recipe state devem ser tratados como dados de mundo, não como mero ajuste visual.
