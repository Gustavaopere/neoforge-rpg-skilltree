# Amendments

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81909a5dda628ec13094
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Amendments
- **Arquivo JAR:** `amendments-1.21-2.1.10-neoforge.jar`
- **Versão 1.21.1:** 1.21-2.1.10
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Overhaul vanilla+ altamente configurável de blocos existentes: cauldrons com qualquer líquido/mixing/boiling/stews e recipes data-driven; lecterns com GUI de escrita/fontes/cores e hoppers; lanterns 3D/wall/physics; hanging signs com itens, swing e banner patterns via Supplementaries; jukeboxes visuais para qualquer disc; carpeted slabs/stairs; double cakes/rotação; lilypads suportando blocos; skull piles/candles/wax; brewing stand bottle colors; ceiling banners/pots; tripwire tool display; pixel-consistent signs; fire/snow/slime/dragon charges e UI nova de trades em 2.1.10.
- **Dependências:** Moonlight Lib/Selene conforme release/ecossistema; o pack instala `moonlight-1.21.1-3.6.3-neoforge.jar`. Supplementaries é integração condicional para banner patterns em hanging signs e está instalado (1.21.1-3.9.8).
- **Sobreposição:** O próprio projeto nasceu da fusão de Carpeted Stairs, Better Lilypads, Better Jukeboxes e algumas features antigas de Supplementaries. Sobreposição com Supplementaries é deliberada/parcial; não é duplicata total.
- **Compatibilidade/Riscos:** Toca blocos vanilla muito usados e pode cruzar com Supplementaries, resource packs/model overrides, mods de fluidos/cauldrons, jukeboxes, signs e villagers. Cauldrons aceitam qualquer líquido e recipes data-driven, portanto validar fluids modded, automation e potion effects. Moonlight generated pack/mod assets precisam ter prioridade adequada para evitar cauldron water branca/brewing stand vermelho. A UI de trades foi adicionada em 2.1.10 e deve ser testada com mods que alteram villager trades.
- **Observações:** FAQ upstream esclarece que cauldrons não fazem brewing vanilla; fazem liquid mixing, potion/dye mixing e crafting por interação. Não confundir os dois sistemas. Prioridade do generated resource pack de Moonlight é requisito visual de troubleshooting.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/documentação oficiais Amendments 2.1.10 + Moonlight 3.6.3 + Supplementaries 3.9.8 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/amendments
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — cauldrons, vanilla-block interactions, Moonlight 3.6.3, Supplementaries 3.9.8, villager trade UI 2.1.10 and resource-pack troubleshooting confirmed in global QC #26.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `amendments-1.21-2.1.10-neoforge.jar`. Amendments é um pacote vanilla+ de alterações profundas em blocos existentes. Não resumir como “QoL visual”: várias features alteram inventário, fluidos, dano, recipes, automation e interação.

## 1. Origem e filosofia do mod
Amendments reúne tweaks de blocos vanilla com foco em funcionalidade + apresentação. O projeto nasceu da incorporação de mods/features antes separados, incluindo **Carpeted Stairs**, **Better Lilypads**, **Better Jukeboxes** e algumas funções que historicamente estavam em Supplementaries.

Isso explica por que a sobreposição com Supplementaries é real mas parcial: os dois projetos são do mesmo ecossistema e algumas funções foram deliberadamente reorganizadas, não copiadas acidentalmente.

## 2. Cauldrons — sistema completo
Cauldrons são uma das maiores superfícies funcionais do mod.

### Armazenamento de líquidos
Podem armazenar **qualquer líquido** compatível com o sistema exposto pelo mod, não apenas água/lava/powder snow vanilla. Essa generalização exige teste com fluids modded do pack.

### Potion e dye mixing
Cauldrons podem misturar **potions e dyes**. O FAQ upstream é explícito: isso **não é brewing vanilla dentro do cauldron**. É um sistema próprio de liquid mixing/crafting.

### Crafting por interação
Ao clicar no cauldron, o mod tenta combinar o item segurado com o líquido contido conforme recipes/regras aplicáveis.

### Boiling
Water ou potion podem ferver quando existe heat source adequado abaixo. O estado boiling:
- produz feedback visual/sonoro;
- pode causar **boiling damage**;
- é usado em recipes próprios, inclusive stews.

### Potion cauldrons
Entrar em cauldron contendo splash/lingering potion pode aplicar o efeito da potion ao entity/player conforme a regra do sistema.

### Stews
Ingredientes podem ser jogados em **boiling water** para produzir stew conforme recipe.

### Data-driven recipes
Pack authors podem adicionar **custom cauldron recipes por datapack**. Essa é a superfície preferencial para integração própria; não criar mixin se um recipe data-driven resolver.

### Visual
Cauldrons recebem melhor model, translucency, ambient occlusion, splash/bubble particles e sons faltantes.

## 3. Lecterns
Quando o jogador segura **Book and Quill**, o lectern oferece GUI de edição com controles extras:
- **Ink Well** para alternar font colors disponíveis;
- **Quill** para alternar fonts;
- integração com **hoppers**.

Isso transforma lectern em bloco de escrita/automation mais rico. Qualquer mod que altere block entity/inventory do lectern deve ser testado em conjunto.

## 4. Lanterns
- novo modelo/animação 3D quando segurado;
- funciona em first e third person;
- **qualquer lantern** compatível pode ser pendurado em parede;
- lanterns penduradas usam **pendulum physics**.

Resource packs que mudam modelos de lanternas e mods de render/physics podem afetar apresentação sem serem incompatibilidades formais.

## 5. Hanging Signs
- podem exibir **item em ambas as faces**;
- podem exibir **banner pattern** quando Supplementaries está instalado;
- hanging signs fixadas em parede balançam com physics;
- mostram conexão visual com fences, walls e blocos semelhantes.

Como **Supplementaries 1.21.1-3.9.8 está instalado**, a integração de banner patterns é relevante nesta instância.

## 6. Jukeboxes
- mostram visualmente o disc em reprodução;
- model aprimorado;
- disc gira durante playback;
- funciona com **qualquer disc, vanilla ou modded**.

Isso é importante em pack com muitos music discs: um disc custom deve ser validado visualmente, não apenas por áudio.

## 7. Carpets e moss carpets
Carpets e moss carpets podem ser colocados em:
- slabs;
- stairs.

É uma alteração funcional de placement/model/state e pode cruzar com mods que substituam stairs/slabs ou resource packs CTM.

## 8. Cakes
- dois cakes podem ser empilhados;
- funciona com **qualquer cake** compatível;
- vanilla cakes podem ser rotacionados.

Com addons de food, testar custom cakes; “works with any cake” é intenção upstream, mas custom block implementations podem escapar.

## 9. Lilypads
Lilypads podem sustentar **qualquer block** acima. O mecanismo funciona fazendo o lilypad ocupar/transformar a posição subjacente como block waterlogged apropriado e restaurando quando o bloco superior é removido.

O autor indica possibilidade de compat com lilypads custom; não presumir cobertura para todo modded lilypad sem teste.

## 10. Mob Skulls
- skulls podem ser empilhados em qualquer combinação;
- candles podem ser colocadas em cima;
- candle deixa **wax overlay** no skull.

Isso mexe em placement/state/model e pode colidir visualmente com packs que remodelam skulls.

## 11. Brewing Stands
Bottles no brewing stand passam a ter cor visual correspondente à potion/conteúdo, melhorando leitura imediata do estado.

## 12. Banners e Pots
Banners e flower pots podem ser colocados em **ceilings**, ampliando placement vanilla.

## 13. Tripwire Hook
Shift-click permite usar Tripwire Hook como **tool holder/display**. Isso muda a interação que outros mods podem esperar de sneak-use nesse bloco.

## 14. Pixel Consistent Signs
O mod altera model, texture e hitbox de signs para proporções pixel-consistent. Resource packs que remodelam signs precisam ser validados em conjunto.

## 15. Charges/projectiles
### Dragon Charges
Adiciona **craftable Dragon Charges**.

### Fire Charges
Recebem:
- model aprimorado;
- partículas;
- gravity;
- efeito de colocar entities em fogo em determinada área/raio.

### Snowballs e Slimeballs
Recebem models 3D. Snowballs também podem aplicar **freezing** no impacto.

### Interação elemental
Snowballs/fireballs interagem com fogo/água conforme as regras adicionadas pelo mod.

Essas mudanças são gameplay real e podem entrar em perks/projectile systems; não classificá-las como puramente cosméticas.

## 16. Villager Trade UI — 2.1.10
A release instalada **2.1.10**, publicada em 06/09/2026, adiciona **nova UI para villager trades**. Como o pack contém outros mods que alteram villagers/trades, a tela deve ser testada com listas extensas, profissões modded e trades injetados.

## 17. Dependências e integrações locais
### Moonlight Lib
Dependência/base do ecossistema. O pack instala `moonlight-1.21.1-3.6.3-neoforge.jar`.

### Supplementaries
Instalado. Participa explicitamente de banner patterns em hanging signs e compartilha histórico de features com Amendments.

### Resource pack gerado de Moonlight
O FAQ upstream alerta que, se **Moonlight generated pack / Mod Assets** estiver abaixo de vanilla na prioridade de resource packs, podem aparecer sintomas como water de cauldron branca e brewing stand vermelho. Isso é problema de prioridade de assets, não necessariamente bug de gameplay.

## 18. Sobreposição funcional
### Supplementaries
Sobreposição parcial e intencional. Auditar feature por feature; não remover um dos dois por descrição genérica.

### Mods de cauldron/fluid crafting
Podem competir por click handling, fluid storage, recipes ou blockstate. Identificar qual hook ganha por feature.

### Mods de signs/lanterns/skulls
Principal risco é model/render/interaction overlap.

### Mods de villagers
A nova trade UI pode coexistir com novos trades, mas mods que substituem a tela inteira precisam de teste.

## 19. Riscos específicos do pack
1. **Cauldron + fluids modded:** qualquer líquido suportado aumenta superfície de edge cases.
2. **Automation:** hoppers em lecterns e recipes de cauldron podem criar rotas não previstas de produção.
3. **Potion effects:** stepping em cauldron pode aplicar efeitos e interagir com immunity/duration mods.
4. **Heat sources:** blocos de calor modded precisam ser testados se reconhecidos por tag/logic.
5. **Resource packs:** models de signs/skulls/lanterns/carpets podem ser sobrescritos.
6. **Supplementaries:** funções historicamente relacionadas exigem teste de dupla instalação.
7. **Villager UI:** risco de incompatibilidade de tela, não necessariamente de trade data.
8. **Projectile changes:** snowball/freezing/fire charge podem alterar combate/quests.

## 20. Matriz de validação
1. Colocar água, lava e pelo menos três fluids modded em cauldron.
2. Potion mixing e dye mixing.
3. Confirmar que não há brewing vanilla indevido no cauldron.
4. Boiling com diferentes heat sources; dano de boiling.
5. Splash/lingering potion cauldron aplicando efeitos.
6. Craft por right-click e pelo menos um custom datapack cauldron recipe.
7. Stew via boiling cauldron.
8. Lectern GUI: fonts/colors + hopper insert/extract.
9. Wall lantern + first/third person + pendulum.
10. Hanging sign com item dos dois lados e banner pattern via Supplementaries.
11. Modded music disc no jukebox.
12. Carpets/moss carpets em stairs/slabs.
13. Custom cake stacking se houver no pack.
14. Lilypad com block acima e remoção/restauração.
15. Skull stack + candle + wax overlay.
16. Ceiling banner/pot.
17. Tripwire Hook tool holder e redstone normal.
18. Snowball freezing e fire charge area ignition.
19. Villager trade UI com profissões/trades modded.
20. Prioridade Moonlight generated assets; verificar water/stand colors.
21. Dedicated server: cauldron recipes, inventory/hopper sync e projectile effects.

## 21. Fontes e confiança
**Authority física:** modlist de 07/09/2026.

**Upstream:** [CurseForge — Amendments](https://www.curseforge.com/minecraft/mc-mods/amendments), descrição oficial e release 2.1.10.

**Confiança:** alta para features publicadas acima. Compatibilidade com blocks/fluids custom depende da implementação de cada mod e deve ser confirmada runtime quando virar contrato de automação/perk.
