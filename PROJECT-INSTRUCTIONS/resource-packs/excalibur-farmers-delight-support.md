# Excalibur | Farmer's Delight Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db816b866bd39d98f9f8c1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `FarmersDelightExcaliburSupport_1.21.1.zip`
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `FarmersDelightExcaliburSupport_1.21.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `FarmersDelight-1.21.1-1.3.4.jar`, mod id `farmersdelight`, runtime `1.3.4`.
- O arquivo instalado corresponde à publicação de 27/06/2026. O upstream publicou depois `1.21.1 HOTFIX` em 13/07/2026; esse hotfix não corresponde ao arquivo atualmente registrado no perfil.
- O claim histórico de 411 textures/100% completion é preservado como claim upstream, não auditoria independente nem garantia contra alterações posteriores do Farmer's Delight 1.3.4.

## Propriedades do banco

- **Mod:** Excalibur | Farmer's Delight Support
- **Arquivo JAR:** `FarmersDelightExcaliburSupport_1.21.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** sem versão semântica própria publicada
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Comida
- **Função:** Support pack visual para Farmer's Delight no estilo Excalibur, cobrindo items/blocks e ajustes de models/text colors da linha 1.21.1.
- **Dependências:** Uso visual pretendido: Excalibur + Farmer's Delight. Stack físico atual: Farmer's Delight 1.3.4. O upstream exige prioridade acima do Excalibur original.
- **Sobreposição:** Sobrepõe assets do Farmer's Delight base. Outros retextures/GUI packs podem vencer por prioridade; addons culinários externos não são automaticamente cobertos.
- **Compatibilidade/Riscos:** O arquivo instalado é de 27/06/2026 e existe `1.21.1 HOTFIX` oficial de 13/07/2026. Risco de regressões/assets posteriores, colisão de resource packs e claim histórico de completude não refletir mudanças mais novas.
- **Observações:** Arquivo instalado `FarmersDelightExcaliburSupport_1.21.1.zip`, sem versão semântica publicada no nome; campo de versão mantido vazio. Upstream histórico declara 411 textures/100% completion, mas há hotfix posterior não instalado.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial do arquivo 1.21.1 de 27/06/2026 e do HOTFIX de 13/07/2026.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-farmers-delight-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Farmer's Delight 1.3.4, arquivo 27/06, HOTFIX 13/07 não instalado, load order, regressões, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `FarmersDelightExcaliburSupport_1.21.1.zip`, arquivo publicado em 27/06/2026 para Minecraft 1.21.1. O upstream publicou depois um arquivo **1.21.1 HOTFIX**, em **13/07/2026**; esse hotfix não corresponde ao arquivo atualmente registrado no perfil.

## 1. Papel e authority
Excalibur | Farmer's Delight Support alinha visualmente **Farmer's Delight** ao Excalibur. Farmer's Delight continua authority de crops, foods, cooking, blocks, recipes, nutrition/effects e qualquer gameplay.
O support pack altera somente textures/models/GUI/text colors/assets incluídos em sua distribuição.

## 2. Cobertura declarada
A documentação oficial histórica descreve o projeto como **100% completed** e registra **411 textures** para itens e blocos. Esse número é reproduzido como claim do upstream para o estado documentado do pack, não como auditoria independente do ZIP atual.
A build 1.21.1 instalada recebeu mudanças/fixes específicos e depois um hotfix posterior, portanto a completude deve ser validada contra Farmer's Delight físico 1.3.4.

## 3. Build 1.21.1 instalada
O arquivo do perfil corresponde à publicação de **27/06/2026**. O changelog dessa build registra:
- fix de Cutting Board texture;
- fix de Tomato Plant model;
- fix de text color;
- fix de Bamboo Basket;
- Wooden Basket;
- Old Tomato Plant;
- Netherite Knife atualizado;
- Rice Basket atualizado;
- Rope Fence e Rope Gate;
- Gleaming Salad item/block;
- Onion Soup.
Esses pontos são smoke gates concretos para a build instalada.

## 4. HOTFIX posterior não instalado
O projeto publica um arquivo posterior chamado **`1.21.1 HOTFIX`**, datado de 13/07/2026. O catálogo não substitui automaticamente o arquivo do usuário e não inventa o conteúdo exato do hotfix além do que estiver publicado.
Operationalmente, a existência desse arquivo torna o support pack instalado **não mais a publicação mais recente para 1.21.1** e exige avaliação antes de considerar o visual final atualizado.

## 5. Stack físico atual
A modlist mantém Farmer's Delight `1.3.4`. Como o resource pack é visual, essa versão do mod é contexto de compatibilidade, não uma dependência binária.
Qualquer asset novo/renomeado do Farmer's Delight 1.3.4 deve ser conferido contra a build visual instalada e contra o hotfix disponível.

## 6. Load order
O upstream instrui colocar o support pack **acima do Excalibur original**. Essa prioridade é necessária para seus overrides de Farmer's Delight vencerem o pack base.
Outros retextures de Farmer's Delight acima dele podem substituir assets individualmente.

## 7. Client e resource reload
Ativar/remover/reordenar afeta somente visual do cliente após resource reload. Crop growth, recipes, Cooking Pot, Cutting Board, nutrition e inventory state continuam pertencendo ao Farmer's Delight.
Text color e GUI/model changes são apresentação, não regras de gameplay.

## 8. Sobreposição
Pode colidir com outros Farmer's Delight resource packs ou GUI packs. Também deve ser distinguido de addons culinários que adicionam conteúdo: eles não são automaticamente cobertos por este support pack do mod base.

## 9. Riscos
1. Arquivo instalado anterior ao hotfix de 13/07/2026.
2. Farmer's Delight 1.3.4 possuir asset não coberto pelo arquivo de 27/06.
3. Cutting Board/Tomato/Bamboo/text-color regression reaparecer por prioridade de pack.
4. Outro resource pack sobrescrever baskets, crops, food items ou GUI.
5. Claim histórico de 411 textures não refletir conteúdo novo posterior.
6. Resource reload manter model/texture cache stale.

## 10. Matriz de testes
- [ ] Cutting Board texture correta.
- [ ] Tomato Plant model e Old Tomato Plant.
- [ ] Bamboo Basket, Wooden Basket e Rice Basket.
- [ ] Netherite Knife.
- [ ] Rope Fence e Rope Gate.
- [ ] Gleaming Salad item e block.
- [ ] Onion Soup.
- [ ] Text colors/GUI legíveis com o restante do stack visual.
- [ ] Comparar arquivo instalado com o `1.21.1 HOTFIX` antes de decidir atualização.
- [ ] Resource reload sem missing textures/models.

Nenhum teste foi marcado como aprovado.

## 11. Evidências e limite
- captura CurseForge do perfil: `FarmersDelightExcaliburSupport_1.21.1.zip` instalado;
- modlist física: Farmer's Delight `1.3.4`;
- CurseForge oficial: suporte Excalibur, claim histórico de 411 textures/100% completion, instrução de load order, changelog do arquivo 27/06/2026 e existência de `1.21.1 HOTFIX` de 13/07/2026.
O hotfix não foi instalado nem aplicado nesta auditoria; nenhuma atualização foi feita automaticamente.

> Boundary canônico: **o arquivo instalado é anterior ao HOTFIX oficial de 13/07/2026; manter instalado não equivale a estar na publicação visual mais recente**.
