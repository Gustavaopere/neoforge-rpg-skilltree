# My Nether's Delight

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db815aac9dc535b9bb129b
- **Baseline física pré-update:** `MyNethersDelight-1.21.1-1.10.4.jar`
- **Artefato alvo selecionado no CurseForge:** `MyNethersDelight-1.21.1-1.10.4.1.jar` — file `8854319`, Release, publicado em 11/09/2026
- **Dependência física:** Farmer's Delight `1.3.4`
- **Data da atualização documental GitHub:** 2026-09-14

> **BOUNDARY FÍSICO.** A modlist física ainda confirma 1.10.4. O GitHub registra 1.10.4.1 como alvo de atualização. A página oficial do arquivo **não publica changelog**; portanto nenhuma mudança interna é atribuída à 1.10.4.1 por inferência.

## Propriedades equivalentes do catálogo

- **Mod:** My Nether's Delight
- **Arquivo JAR alvo:** `MyNethersDelight-1.21.1-1.10.4.1.jar`
- **Versão 1.21.1 alvo:** 1.10.4.1
- **Baseline física auditada:** 1.10.4
- **Estado da pesquisa:** Verificado documentalmente; validação física/runtime da 1.10.4.1 pendente
- **Decisão:** Sem decisão
- **Categoria:** Comida
- **Função:** Addon culinário/agro do Nether para Farmer's Delight, com Blazier e heat-based cooking, Resurgent Soil/Farmland, Powdery vegetation/worldgen, Piglin food trades, ingredients, foods, recipes e processing próprio.
- **Dependências:** Farmer's Delight — Required Dependency oficial. Pack físico usa Farmer's Delight 1.3.4.
- **Sobreposição:** Compartilha infraestrutura culinária com Farmer's Delight e outros addons, mas conteúdo Nether, Blazier, soils/plants e bartering são próprios; não é duplicata simples.
- **Compatibilidade/Riscos:** Addon Farmer's Delight Client & Server. Riscos: recipe/tag overlap, Blazier heat-state persistence, Resurgent Soil data-map drift, Piglin bartering economy, Powdery worldgen/pathfinding e version drift 1.10.4→1.10.4.1 sem changelog público.
- **Observações:** Runtime físico baseline 1.10.4. Upstream publicou 1.10.4.1 em 11/09/2026; o arquivo não possui changelog público. Tratar a atualização como version gate e preservar os regression gates da 1.10.4.
- **Procedência:** modlist.txt física baseline + CurseForge oficial 1.10.4.1 file 8854319 + documentação/changelog 1.10.4 já auditados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/my-nethers-delight
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/09/2026 — GitHub promovido para alvo 1.10.4.1; nenhuma mudança interna foi inventada porque o arquivo 8854319 não traz changelog. Baseline física 1.10.4 preservada até re-fetch pós-update.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DE CONTEÚDO.** O corpo funcional abaixo foi validado na 1.10.4. O alvo de atualização é `MyNethersDelight-1.21.1-1.10.4.1.jar`. Como a 1.10.4.1 não possui changelog público, este dossiê não projeta alterações internas e usa a arquitetura/funcionalidade 1.10.4 como baseline de regressão.

## 1. Identidade e papel
- **Mod:** My Nether's Delight.
- **JAR físico baseline:** `MyNethersDelight-1.21.1-1.10.4.jar`.
- **JAR alvo:** `MyNethersDelight-1.21.1-1.10.4.1.jar`.
- **Mod id:** `mynethersdelight` na baseline física.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** soytutta.
- **Ambiente:** Client & Server.
- **Licença publicada:** All Rights Reserved.
- **Papel:** expansão culinária/agro do Nether sobre Farmer's Delight, conectando exploração da dimensão, ingredientes, cooking, agricultura, bartering e worldgen temático.

## 2. Dependência e ownership
A relação oficial exige **Farmer's Delight**. O pack possui Farmer's Delight 1.3.4.

Ownership:
- Farmer's Delight continua provider da infraestrutura culinária base;
- My Nether's Delight adiciona conteúdo, recipes, blocos, crops/plants e regras próprias do Nether;
- Nether/vanilla continua authority dos mobs, biomas e bartering base que o addon integra.

Não tratar qualquer recipe/food de Farmer's Delight como pertencente ao addon apenas por interoperar com ele.

## 3. Blazier — sistema de calor/cooking
A release 1.10.4 traz grande revisão do **Blazier**:
- recipes podem mudar conforme o estado de calor;
- tooltips/integração de recipe viewing foram melhorados;
- Blaziers preservam o calor atual quando coletados com **Silk Touch**;
- quando quebrados acesos sem Silk Touch, o drop publicado é Nether Brick + **1–4 Blaze Powder**;
- há configuração para velocidade de cooking do Blazier.

Isso torna o estado térmico parte do lifecycle do bloco. Automação deve validar input, heat state, output e comportamento de quebra/recolocação também após a promoção para 1.10.4.1.

## 4. Resurgent Soil e Resurgent Farmland
A 1.10.4 reworka **Resurgent Soil** e expõe configurações para:
- propagação;
- crescimento;
- alcance/range.

Transformações especiais de plantas passam a ser customizáveis por **datapacks/data maps**. A release também:
- corrige crash envolvendo Bamboo sobre Resurgent Soil;
- restaura o fall damage pretendido de Resurgent Farmland.

Esse sistema cruza agricultura, datapacks e outros crop mods. Não assumir que toda planta modded é válida sem tag/data map efetivo.

## 5. Powdery Cannon e vegetação Powdery
A publicação 1.10.4 altera a geração de **Powdery Cannon** para patches mais naturais e raros, com centro mais alto e flores dispersas. Também ajusta plantas Powdery:
- uso de sons de Bamboo em superfícies documentadas;
- resistência a explosão ajustada;
- mobs passam a evitar Powdery plants/blocos danosos.

Há configuração para geração da Powdery Cannon. Isso é worldgen real e precisa ser testado em chunks novos.

## 6. Piglin food trades
A release integra **food trades** ao sistema vanilla de Piglin bartering e adiciona configuração para essa feature. O autor também tornou os resultados mais raros por padrão.

Consequências:
- altera economia de alimentos/ingredientes do Nether;
- scripts/datapacks que substituam bartering podem remover ou duplicar entradas;
- o resultado deve ser validado server-side em multiplayer.

## 7. Recipes, feasts e cooking fixes
A 1.10.4 corrige crashes/situações envolvendo:
- **Poaching**;
- **Striderloaf**;
- feasts incompletos de **Stuffed Hoglin**;
- custom recipes.

Também amplia condições/integrações de recipes e adiciona tags ausentes. Essas correções permanecem regression gates na 1.10.4.1 até validação física.

## 8. Processing e Cutting Board
Mudanças documentadas na baseline incluem:
- Powdery Blocks podem virar **2 planks**;
- salvaging via Cutting Board foi ampliado para móveis Powdery;
- existem recipes condicionais e data maps para transformações de plantas.

Isso cria interseção com automação Create, recipe viewers e scripts do pack. Não duplicar outputs via KubeJS sem comparar recipes reais da build instalada.

## 9. Configuração
A 1.10.4 expõe configuração para pelo menos:
- Piglin food trades;
- frog/Magma Cake behavior;
- Resurgent Soil propagation/growth/range;
- Blazier cooking speed;
- stone cabinets;
- Powdery Cannon generation.

Defaults e nomes exatos dos arquivos devem ser lidos do config físico antes de alteração automatizada; a existência da opção não prova que o pack usa o default. A 1.10.4.1 não deve ser presumida idêntica em config sem inspeção.

## 10. Client/server e persistência
É conteúdo Client & Server.

Server authority cobre:
- recipes e outputs;
- block states/heat do Blazier;
- crops/soil/worldgen;
- Piglin bartering;
- loot e interação de mobs.

Client representa models, particles, tooltips e recipe-viewer presentation.

Persistência crítica inclui heat state preservado via Silk Touch e estados de containers/crops. Save/restart/chunk reload devem fazer parte da regressão.

## 11. Integrações no pack
O pack possui vários addons Farmer's Delight e integração Create. Interseções principais:
- recipes/tags/foods;
- Cutting Board;
- automação de cooking;
- crops/plants;
- bartering/loot;
- worldgen Nether.

Sobreposição temática não é redundância. Remoção deve comparar conteúdo único, economia e progressão.

## 12. Delta 1.10.4 → 1.10.4.1
Em **11/09/2026**, upstream publicou `MyNethersDelight-1.21.1-1.10.4.1.jar` para NeoForge 1.21.1, file ID `8854319`, canal Release.

A página oficial desse arquivo **não possui changelog**. Portanto:
- não atribuir bug fix, recipe, config, registry ou comportamento novo sem evidência;
- preservar todos os regression gates da 1.10.4;
- inspecionar metadata/config/data/JAR após instalação se alguma diferença funcional aparecer.

## 13. Riscos
1. **Recipe overlap/dupe** com outros addons e KubeJS.
2. **Blazier state loss** ao quebrar/recolocar ou automatizar.
3. **Datapack transformation drift** em Resurgent Soil.
4. **Bartering economy** duplicada ou excessivamente generosa.
5. **Worldgen density** da Powdery Cannon em Nether já modificado.
6. **Mob avoidance/pathfinding** ao redor de blocos Powdery.
7. **Feast/container state** após chunk unload/restart.
8. **Version opacity:** 1.10.4.1 não publica changelog; diferença interna desconhecida até JAR/runtime inspection.
9. **Cross-version docs:** não importar regras antigas de 1.19/1.20 sem confirmação na linha 1.21.1.

## 14. Matriz de testes
- [ ] Nova modlist física confirma `MyNethersDelight-1.21.1-1.10.4.1.jar`.
- [ ] Dedicated server e cliente iniciam com Farmer's Delight 1.3.4 + My Nether's Delight 1.10.4.1.
- [ ] Blazier processa recipe em estados de calor suportados e produz output correto.
- [ ] Silk Touch preserva heat state; quebra normal acesa não duplica drops.
- [ ] Resurgent Soil propaga/cresce conforme config real e não crasha com Bamboo.
- [ ] Data map de transformação de planta recarrega com `/reload` sem duplicar regras.
- [ ] Powdery Cannon gera em chunks novos conforme config e não invade densidade indevida.
- [ ] Mobs evitam Powdery hazards de forma estável.
- [ ] Piglin bartering entrega food trade na frequência configurada e exatamente uma vez.
- [ ] Poaching/Striderloaf/Stuffed Hoglin não reproduzem crashes da baseline.
- [ ] Cutting Board salvaging e Powdery plank recipes têm outputs corretos.
- [ ] Restart/chunk reload conserva containers, crops e estado persistente relevante.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física baseline: `MyNethersDelight-1.21.1-1.10.4.jar`, id/version e `mynethersdelight.mixins.json`.
- CurseForge oficial: `MyNethersDelight-1.21.1-1.10.4.1.jar`, file 8854319, Release NeoForge 1.21.1, publicado em 11/09/2026.
- Changelog oficial/Modrinth 1.10.4: Blazier, Resurgent Soil, Powdery Cannon, Piglin bartering, configs e fixes aqui descritos.
- **Limite:** o arquivo 1.10.4.1 não publica changelog; classes, registry IDs, chances, defaults ou mudanças internas não foram inventados. A instalação física da 1.10.4.1 ainda requer confirmação pela nova modlist/JAR.
