# My Nether's Delight

## Propriedades do registro

- **Mod:** My Nether's Delight
- **Arquivo JAR:** MyNethersDelight-1.21.1-1.10.4.1.jar
- **Versão 1.21.1:** 1.10.4.1
- **Categoria:** Comida
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/my-nethers-delight
- **Função:** Addon culinário/agro do Nether para Farmer's Delight, com Blazier e heat-based cooking, Resurgent Soil/Farmland, Powdery vegetation/worldgen, Piglin food trades, ingredients, foods, recipes e processing próprio.
- **Dependências:** Farmer's Delight — Required Dependency oficial. Pack físico usa Farmer's Delight 1.3.4.
- **Compatibilidade/Riscos:** Addon Farmer's Delight Client & Server. Riscos: recipe/tag overlap, Blazier heat-state persistence, Resurgent Soil data-map drift, Piglin bartering economy, Powdery worldgen/pathfinding e ausência de changelog específico da 1.10.4.1. Regressão do loop culinário/worldgen continua obrigatória.
- **Sobreposição:** Compartilha infraestrutura culinária com Farmer's Delight e outros addons, mas conteúdo Nether, Blazier, soils/plants e bartering são próprios; não é duplicata simples.
- **Observações:** Runtime físico 1.10.4.1, JAR `MyNethersDelight-1.21.1-1.10.4.1.jar`, SHA-1 `9c54439d65e77c7b4b711831138f81f3a849ff35`. CurseForge file 8854319, Release NeoForge 1.21.1 de 11/09/2026. Sem changelog público específico; conteúdo funcional detalhado permanece ancorado na 1.10.4.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial My Nether's Delight file 8854319 (1.10.4.1) + documentação/changelog 1.10.4 já auditados.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — runtime físico atualizado para My Nether's Delight 1.10.4.1. File ID 8854319, NeoForge 1.21.1, 11/09/2026. Como a publicação 1.10.4.1 não traz changelog público, o dossiê preserva a 1.10.4 como baseline funcional documentada sem inferir mudanças internas.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #414: JAR `MyNethersDelight-1.21.1-1.10.4.1.jar`, mod id `mynethersdelight`, runtime `1.10.4.1`, SHA-1 `9c54439d65e77c7b4b711831138f81f3a849ff35`.

<callout icon="🔎" color="orange_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `MyNethersDelight-1.21.1-1.10.4.1.jar`, mod id `mynethersdelight`, versão `1.10.4.1`, NeoForge 1.21.1. O conteúdo funcional abaixo permanece ancorado no changelog documentado da 1.10.4 porque a 1.10.4.1 não publica changelog próprio. É um addon de Farmer's Delight dedicado ao Nether. A release 1.10.4 reworka Blazier, Resurgent Soil, Powdery Cannon/vegetação, Piglin food trades e corrige múltiplas superfícies de crash/recipe. Farmer's Delight continua authority da infraestrutura culinária base.
</callout>
## 1. Identidade e papel
- **Mod:** My Nether's Delight.
- **JAR físico:** `MyNethersDelight-1.21.1-1.10.4.1.jar`.
- **Mod id:** `mynethersdelight`.
- **Runtime:** `1.10.4.1`.
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
Isso torna o estado térmico parte do lifecycle do bloco. Automação deve validar input, heat state, output e comportamento de quebra/recolocação.
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
Também amplia condições/integrações de recipes e adiciona tags ausentes. Essas correções indicam que recipes/servings e estados parciais de food containers são superfícies críticas da build atual.
## 8. Processing e Cutting Board
Mudanças documentadas incluem:
- Powdery Blocks podem virar **2 planks**;
- salvaging via Cutting Board foi ampliado para móveis Powdery;
- existem recipes condicionais e data maps para transformações de plantas.
Isso cria interseção com automação Create, recipe viewers e scripts do pack. Não duplicar outputs via KubeJS sem comparar recipes reais da 1.10.4.
## 9. Configuração
A release expõe configuração para pelo menos:
- Piglin food trades;
- frog/Magma Cake behavior;
- Resurgent Soil propagation/growth/range;
- Blazier cooking speed;
- stone cabinets;
- Powdery Cannon generation.
Defaults e nomes exatos dos arquivos devem ser lidos do config físico antes de alteração automatizada; a existência da opção não prova que o pack usa o default.
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
## 12. Riscos
1. **Recipe overlap/dupe** com outros addons e KubeJS.
2. **Blazier state loss** ao quebrar/recolocar ou automatizar.
3. **Datapack transformation drift** em Resurgent Soil.
4. **Bartering economy** duplicada ou excessivamente generosa.
5. **Worldgen density** da Powdery Cannon em Nether já modificado.
6. **Mob avoidance/pathfinding** ao redor de blocos Powdery.
7. **Feast/container state** após chunk unload/restart.
8. **Cross-version docs:** não importar regras antigas de 1.19/1.20 sem confirmação na 1.10.4.
## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Farmer's Delight 1.3.4 + My Nether's Delight 1.10.4.1.
- [ ] Blazier processa recipe em estados de calor suportados e produz output correto.
- [ ] Silk Touch preserva heat state conforme release; quebra normal acesa produz drops publicados sem duplicação.
- [ ] Resurgent Soil propaga/cresce conforme config real e não crasha com Bamboo.
- [ ] Data map de transformação de planta recarrega com `/reload` sem duplicar regras.
- [ ] Powdery Cannon gera em chunks novos conforme config e não invade densidade indevida.
- [ ] Mobs evitam Powdery hazards de forma estável.
- [ ] Piglin bartering entrega food trade na frequência configurada e exatamente uma vez.
- [ ] Poaching/Striderloaf/Stuffed Hoglin não reproduzem crashes corrigidos.
- [ ] Cutting Board salvaging e Powdery plank recipes têm outputs corretos.
- [ ] Restart/chunk reload conserva containers, crops e estado persistente relevante.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Runtime atual — 1.10.4.1
Em **11/09/2026**, upstream publicou `MyNethersDelight-1.21.1-1.10.4.1.jar` para NeoForge 1.21.1, file ID `8854319`; a modlist física de 25/09/2026 confirma que esta build agora está instalada.
A página oficial do arquivo 1.10.4.1 **não publica changelog**. Portanto, as mudanças funcionais detalhadas neste dossiê continuam ancoradas no changelog documentado da 1.10.4, sem inventar fixes, recipes, configs ou mudanças internas exclusivas da 1.10.4.1. Os gates de Blazier/heat state, Resurgent Soil/data maps, Powdery worldgen, Piglin food trades, recipes/feasts e save/restart permanecem válidos para regressão.
## 15. Evidências e limites
- Modlist física: `MyNethersDelight-1.21.1-1.10.4.1.jar`, mod id `mynethersdelight`, runtime `1.10.4.1`, SHA-1 `9c54439d65e77c7b4b711831138f81f3a849ff35` e `mynethersdelight.mixins.json`.
- CurseForge oficial: release NeoForge 1.21.1 e Farmer's Delight required.
- Changelog oficial/Modrinth 1.10.4: Blazier, Resurgent Soil, Powdery Cannon, Piglin bartering, configs e fixes aqui descritos.
- **Limite:** classes, registry IDs, chances e defaults não explicitados pela release não foram inventados; para automação futura, extrair do JAR/config/data real.
