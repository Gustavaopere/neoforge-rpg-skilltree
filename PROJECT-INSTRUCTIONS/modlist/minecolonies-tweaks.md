# Tweaks addon for MineColonies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81e797a2c12c41da4330
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `MineColonies_Tweaks-1.21.1-3.33.jar`, mod id `minecolonies_tweaks`, runtime `3.33`
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion usa a formulação “modlist.txt física atual” e foi editada em 10/09/2026; a authority física mais recente realmente acessível nesta execução continua sendo o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, `MineColonies_Tweaks-1.21.1-3.33.jar` está presente.
- O source 3.33 foi compilado contra MineColonies `1.1.1368`, Structurize `1.0.832`, Multi-Piston `1.2.51`, BlockUI `1.0.199` e Domum Ornamentum `1.0.223`. O snapshot físico acessível contém MineColonies `1.1.1381`, Structurize `1.0.833`, Multi-Piston `1.2.58`, BlockUI `1.0.211` e Domum Ornamentum `1.0.236`. Isso amplia o regression surface, mas não prova incompatibilidade.
- O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Tweaks addon for MineColonies
- **Arquivo JAR:** `MineColonies_Tweaks-1.21.1-3.33.jar`
- **Versão 1.21.1:** 3.33
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG, QoL
- **Função:** Addon de MineColonies com configuração e extensões operacionais para jobs/cidadãos, research, crops, raiders, requests/recipes, custom tool types, stock/logística, batch repair/upgrade, GUI/networking e outras superfícies internas do sistema colonial.
- **Dependências:** Obrigatórias/baseline da release 3.33: MineColonies 1.1.1368+, Structurize 1.0.832, Multi-Piston 1.2.51, BlockUI 1.0.199 e Domum Ornamentum 1.0.223 em linhas compatíveis. Build também lista JEI/Curios como opcionais; Jade/Mekanism/Configured são runtime-only do ambiente upstream.
- **Sobreposição:** Não é redundante com MineColonies Compatibility ou MineColonies Let's Do. Há, porém, superfícies compartilhadas em jobs, recipes, requests, compatibility management e crafting AI, exigindo teste conjunto.
- **Compatibilidade/Riscos:** 3.33 foi compilado contra MineColonies 1.1.1368; o pack usa 1.1.1381. O loader aceita a versão mais nova, porém Tweaks injeta em GUI, citizen/jobs, AI de crafting/interação/estrutura, raiders, racks, research, requests/recipes, crops e ChunkGenerator. Testar também coexistência com MineColonies Compatibility 3.56 e MineColonies Let's Do 2.1.
- **Observações:** JAR físico `MineColonies_Tweaks-1.21.1-3.33.jar`, mod id `minecolonies_tweaks`, runtime 3.33. O source exato declara MC 1.21.1 e target MineColonies 1.1.1368. O pack roda MineColonies 1.1.1381; diferença aceita pelo range, mas sensível devido aos muitos mixins. Defaults de config documentados não equivalem aos valores efetivamente persistidos no serverconfig.
- **Procedência:** modlist.txt física atual + CurseForge oficial da release 3.33 + repositório oficial pinado no commit `33efa6f49e0a3f660726456aa5e403f219177360`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/minecolonies-tweaks | https://github.com/gisellevonbingen-Minecraft/MineColonies_Tweaks/commit/33efa6f49e0a3f660726456aa5e403f219177360
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — dossiê reconstruído contra o source oficial exato de 3.33 (commit 33efa6f). MineColonies físico 1.1.1381 satisfaz o range mínimo da release, mas permanece risco de regressão por 13 snapshots de drift e ampla superfície de mixins.
- **Histórico da decisão:** 2026-09-10 — ficha reconstruída no padrão Alex's Mobs. Source exato de 3.33 identificado no commit 33efa6f. Mantido, com regressão dirigida obrigatória quando MineColonies/Structurize/BlockUI/Domum/Multi-Piston forem atualizados.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO DESTA FICHA.** A autoridade de presença/versão é a modlist física: `MineColonies_Tweaks-1.21.1-3.33.jar`, mod id `minecolonies_tweaks`, runtime `3.33`. A auditoria de código está ancorada no commit upstream `33efa6f49e0a3f660726456aa5e403f219177360`, cuja alteração é `mod_version=3.33` e cujo `gradle.properties` declara Minecraft 1.21.1 e as versões de dependência usadas pela release. O addon modifica numerosas superfícies internas de MineColonies por mixins; portanto, compatibilidade não deve ser inferida apenas porque o loader aceita uma versão mais nova de MineColonies.

## 1. Identidade, versão e authority
- **Mod:** Tweaks addon for MineColonies / MineColonies Tweaks.
- **JAR físico:** `MineColonies_Tweaks-1.21.1-3.33.jar`.
- **Mod id:** `minecolonies_tweaks`.
- **Runtime instalado:** `3.33`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Licença upstream:** GPL 3.0.
- **Source exato auditado:** commit `33efa6f49e0a3f660726456aa5e403f219177360`, de 08/08/2026, com `mod_version=3.33`.
- **Papel no pack:** addon não oficial que altera comportamento/configuração de MineColonies e acrescenta ferramentas, módulos, GUI, mensagens e extensões para operações coloniais.
- **Ownership:** MineColonies continua dono de colônia, cidadãos, jobs, research, buildings, requests e estruturas. Tweaks injeta regras e extensões nesses sistemas; não substitui a autoridade do provider base.
- **Decisão:** manter, com regressão dirigida a cada atualização do stack MineColonies.

## 2. Contrato de dependências da release 3.33
O `gradle.properties` do commit 3.33 fixa a linha de compilação em:
- **MineColonies:** `1.1.1368-1.21.1-snapshot`.
- **Structurize:** `1.0.832-1.21.1-snapshot`.
- **Multi-Piston:** `1.2.51-1.21.1-snapshot`.
- **BlockUI:** `1.0.199-1.21.1-snapshot`.
- **Domum Ornamentum:** `1.0.223-snapshot`.
- **NeoForge API de build:** `21.1.200`, com range declarado a partir de `21.1.194`.
- O mesmo arquivo separa **JEI** e **Curios** como opcionais e lista **Jade, Mekanism e Configured** como runtime-only do ambiente de desenvolvimento. Eles não devem ser reclassificados automaticamente como hard dependencies do JAR.

### Divergência relevante no pack
O MineColonies físico deste pack é `minecolonies-1.1.1381-1.21.1-snapshot.jar`, enquanto Tweaks 3.33 foi compilado contra 1.1.1368. O contrato permite a versão mais nova, mas há **13 iterações snapshot** de diferença e o addon possui muitos mixins contra classes internas de MineColonies. Isso é um risco concreto de regressão estrutural; não é prova de incompatibilidade.

## 3. Função real: não é apenas “algumas configs”
O README resume duas funções históricas — alterar velocidade de Crafter/Sifter e oferecer custom Tool types orientados a datapacks — porém a árvore de 3.33 é substancialmente maior. Ela contém configurações de jobs, cidadãos, fields, research, raiders e crops; extensões de recipe/request systems; módulos de estoque/pesquisa; ferramentas de operação de buildings; GUI própria; mensagens client/server; e ampla instrumentação por mixin.

### 3.1 Crafter, Sifter e delays de trabalho
`JobConfig` expõe controles diretos sobre:
- `blockMiningDelay` e `blockBuildingDelay`.
- `craftingProgressMultiplier`, `craftingHittingTime` e `craftingDecideDelay`.
- `sifterProgressMultiplier`.
- `menuPerLevel` para Restaurant/Netherworker.
- `disableLeisure` para cidadãos.

A própria configuração documenta as fórmulas de tempo de crafting e sifting. Como essas opções alteram frequência de trabalho, são superfícies de balanceamento e de performance, não simples cosmética.

### 3.2 Dyer
Existe `dyerDisableBleaching`, cujo comentário upstream explica que desabilita bleaching de lã para evitar loop de crafting. O próprio código avisa que a mudança requer `/mc colony requestsystem-reset-all` para surtir efeito. Alterar esta opção sem reconstruir o request system pode produzir diagnóstico falso de “config não funciona”.

### 3.3 Farmer e fields
O addon altera o worker Farmer e a seleção de fields:
- `farmerWorkDelay` e `farmerSkillDivider`, com delay final dependente de stamina.
- `farmerActionsDoneUntilDumping`.
- `farmerPlantAfterHoe`.
- `farmerPlantAfterHarvest`.
- `FieldConfig.newRetrieveMethod`: quando ativo, permite trabalhar o mesmo field sem o wait time do método base e processar a lista de fields sequencialmente.
- Há `EntityAIWorkFarmerMixin`/accessor e `WindowFieldMixin`, portanto o alcance inclui lógica server-side e UI.

### 3.4 Construção e recursos
`JobConfig` inclui:
- `structureLeavesFree`: controla se builders podem tratar leaves como gratuitas ou precisam solicitá-las.
- `blockBuildingDelay`/`blockMiningDelay`.

A árvore possui mixins em `AbstractEntityAIStructure`, `BuildingStructureHandler`, `CreativeBuildingStructureHandler` e `LoadOnlyStructureHandler`. Isso torna builders/estruturas um ponto obrigatório de regressão.

### 3.5 Warehouse, couriers e maximum stock
- `warehouseCouriersPerLevel` controla couriers por nível.
- `maximumStockKindsPerLevel` controla quantidade de tipos de estoque máximo por nível e declara interação com o research effect `minimumstockmultiplier`.
- Existem `MaximumStockModule`, `MaximumStockModuleWindow` e `MaximumStockUpdateMessage`.
- `CourierAssignmentModuleMixin` e seu equivalente de view mostram que assignment/logística também são tocados.

### 3.6 Undertaker e ressurreição de cidadãos
`JobConfig` parametriza o sistema de ressurreição do Undertaker:
- chance de totem único/múltiplo;
- pesos de building level e mana do worker;
- cap da chance e peso de Mystical Site;
- chance de quebra do totem;
- possibilidade de desativar o cap.

A fórmula é explicitamente baseada em constantes de `UndertakerConstants`; há também `EntityAIWorkUndertakerMixin`. Logo, qualquer sistema do pack que dependa de mortalidade permanente de cidadão deve considerar essa configuração antes de presumir comportamento vanilla de MineColonies.

## 4. Citizens, mourning e interação
`CitizenConfig` confirma duas opções:
- `disableMourn` — desabilitar mourning.
- `disableInteractionDelay` — eliminar o delay de interação; em 3.33 o default do addon é `true`.

A árvore também contém `CitizenDataMixin`, `CitizenMournHandlerMixin`, `CitizenDiseaseHandlerAccessor`, `EntityCitizenMixin` e comandos de cidadão. A release 3.33 ainda traz no changelog a mudança de GUI “Moved up/down all skills button in citizen gui”, implementada sobre a superfície de `MainWindowCitizenMixin`.

## 5. Raiders
`MonsterConfig` possui um grupo `raider` com:
- `disableImmunity` default `true`.
- `disableThorns` default `true`.

A árvore contém `AbstractEntityMinecoloniesRaiderMixin`. Assim, balanceamento e compatibilidade de raid precisam ser testados com os valores efetivamente usados no serverconfig, especialmente quando outros mods alteram dano, IA ou equipamentos.

## 6. Research
`ResearchConfig` expõe:
- `speed` com default 1.0.
- `ignoreConstraints` com default `false`; o comentário descreve permitir múltiplas researches até depth 6 ignorando a limitação de escolher apenas uma.

O alcance de código inclui `GlobalResearchBranchMixin`, `GlobalResearchEffectMixin`, `LocalResearchTreeMixin`, `ResearchEffectCategoryMixin`, `ResearchEffectManagerMixin`, `ResearchListenerMixin`, `ResearchManagerMixin`, `TryResearchMessageMixin`, classes de `ResearchCost`, `ResearchCostResolver`, `ResearchCostSelector`, módulo `ResearchCostResolverBuildingModule`, comandos e `WindowResearchTreeMixin`.

Isso é uma superfície extensa: não tratar o addon apenas como multiplicador de velocidade. Ele também participa de custos, effects, constraints, UI, mensagens e resolução de research.

## 7. Crops e world interaction
`BlockConfig` confirma três controles para crops de MineColonies:
- `cropVanillaFarmland` default `true`: permite plantar crop em farmland vanilla; o próprio source alerta que mudar isso pode quebrar crops já plantadas em item.
- `cropIgnoreBiome` default `true`.
- `cropCanPerformBonemeal` default `true`.

Há `ItemCropMixin`, `MinecoloniesCropBlockMixin` e extensões correspondentes.

A presença de `ChunkGeneratorMixin`, `WorldGenConfig` e `ChrousTree` mostra também superfície de worldgen no addon. Esta ficha não infere regras de geração além do que foi efetivamente exposto pelo source auditado; qualquer detalhe de distribuição deve ser validado em runtime/dados antes de virar regra operacional.

## 8. Custom tools e datapacks
O README confirma custom Tool types para cidadãos orientados a datapacks. A API de 3.33 contém:
- `ConfigToolType`.
- `CustomToolType`.
- `CustomToolTypeRegisterEvent`.
- `OrToolType`.
- `ToolTypeExtension`.
- `ToolTypeTags`.

Isso significa que datapacks podem se apoiar nessa camada para ampliar a semântica de equipamentos aceitos por cidadãos. IDs, formato de JSON e regras específicas devem ser copiados do source/data efetivo, não reconstruídos por memória.

## 9. Recipe system e request system
O addon possui sua própria camada de extensibilidade sobre sistemas de MineColonies:
- `CustomizableRecipeStorage`, registry e interfaces correspondentes.
- `CustomizableRecipeStorageFactory` e `RecipeStorageExtension`.
- `CustomizableDeliverable` e `CustomizableRequestable`.
- registries de requestable/deliverable.
- factories e resolvers de requests customizáveis.
- `PostBoxRequestMessageMixin`, `RecipeStorageMixin` e `StackMixin`.

Essas superfícies são relevantes para qualquer datapack/addon que ensine receitas, altere entregáveis ou reconfigure requests. Interação com #404 MineColonies Compatibility e #405 MineColonies Let’s Do deve ser validada, não presumida.

## 10. Ferramentas e módulos operacionais acrescentados
A árvore exata de 3.33 confirma implementações para:
- **Batch repair de buildings** (`BatchRepairBuildingsWindow`, `BatchRepairData` e mensagens load/save).
- **Batch upgrade de buildings** (`BatchUpgradeBuildingsWindow`, `BatchUpgradeData` e mensagens load/save).
- **Maximum stock** com módulo/UI/networking.
- **Study item list** com módulo/UI.
- **University dashboard** e university scroll.
- **Building link scroll**.
- **Copy scroll**.
- **Inventory scroll**.
- **Resource scroll book** com menu/UI/mensagem de abertura.
- `FarmFieldPlotResize2Message` para alteração de field plot.
- `ResearchCostRequestMessage` para consulta de custos.

A existência das classes/mensagens está confirmada no commit 3.33. Esta ficha não inventa recipes, teclas ou permissões de uso que não tenham sido auditados diretamente.

## 11. Registries e extensibilidade
A API inclui helpers de `DeferredRegister`, `JobRegister` e `RecipeTypeRegister`, além de registries internos para recipes e requests customizáveis. Também existe `ModuleRegisterEvent`. Isso reforça que Tweaks serve como pequena plataforma de extensão para comportamentos de MineColonies, não apenas como conjunto de toggles.

## 12. Client/server e networking
Há divisão explícita de código client/common:
- Client: GUI, render helpers, key mappings e mixins de BlockUI/MineColonies/Minecraft.
- Common/server: configs, workers, colony/buildings, requests, researches, items e mixins de lógica.
- `MCTweaksMessagesRegistrar` registra mensagens próprias para operações como batch repair/upgrade, field resize, maximum stock, research cost e resource scroll.

Consequência operacional: servidor dedicado precisa ser testado. Um addon que altera só tela seria menos sensível; 3.33 modifica lógica server-side e sincroniza ações de GUI com mensagens.

## 13. Superfície de mixins — pontos críticos auditados

### 13.1 Client/GUI
Mixins confirmados em BlockUI (`Pane`, `View`), janelas de building, citizen, skeleton, crafting, field, inventory, research tree, Town Hall, scarecrow renderer, tool recipe category e vanilla `Screen`/`AbstractContainerScreen`.

### 13.2 Common/MineColonies
A versão 3.33 injeta em superfícies de:
- `AbstractBuilding`.
- crafting AI e interaction AI.
- structure AI/handlers.
- MineColonies raiders.
- racks.
- Dyer crafting.
- citizen data/disease/mourning/entity.
- colony.
- compatibility manager.
- courier assignment.
- Farmer, Lumberjack, Sifter e Undertaker.
- equipment types.
- global/local research, research effects/listeners/managers.
- crops.
- post box/request system.
- recipe storage.
- registered structures.
- restaurant menu.
- stacks/tree logic.

### 13.3 Minecraft/worldgen
`ChunkGeneratorMixin` existe em 3.33. Portanto world generation faz parte da superfície técnica que pode entrar em conflito/regredir, ainda que a documentação pública do projeto dê muito mais destaque aos tweaks de colônia.

## 14. Compatibilidade e sobreposição no pack

### MineColonies 1.1.1381
É a maior área de risco. 3.33 foi construído com 1.1.1368 e injeta em muitas classes internas. O loader aceitar 1.1.1381 não prova que todos os injection points continuam semanticamente equivalentes.

### #404 MineColonies Compatibility 3.56
Não é redundância direta. Compatibility amplia integrações/jobs/recipes com outros mods; Tweaks altera comportamento interno, configuração e ferramentas. Porém os dois podem convergir em recipes, requests, jobs e compatibility management, então precisam de smoke tests conjuntos.

### #405 MineColonies Let’s Do 2.1
Let’s Do ensina/integra conteúdo culinário ao MineColonies; Tweaks modifica recipe/request storage e AI de crafting. Não há conflito confirmado, mas há superfície compartilhada suficiente para teste dirigido.

### Curios/JEI e outros opcionais
O build lista Curios/JEI como opcionais e Jade/Mekanism/Configured como runtime-only de desenvolvimento. Não promover esses componentes a dependência obrigatória sem o manifesto/runtime provar isso.

## 15. Riscos técnicos
- **Alto acoplamento ao internals de MineColonies:** ampla matriz de mixins torna snapshots do base um risco real de quebra silenciosa ou de comportamento, não só crash de startup.
- **AI/jobs:** mudanças de delay, farmer, sifter, crafter, lumberjack e undertaker podem alterar throughput e balanceamento.
- **Research:** options de speed/constraints/cost/effects podem mudar progressão de colônia.
- **Requests/recipes:** podem afetar resolução de entregas e receitas ensinadas por addons.
- **GUI/networking:** UI pode abrir corretamente e ainda enviar/interpretar estado errado se BlockUI/MineColonies mudarem.
- **Crops:** mudança de `vanillaFarmland` possui aviso explícito de quebra de crops plantadas.
- **Raiders:** imunidade/thorns podem alterar combate da colony raid.
- **Worldgen:** `ChunkGeneratorMixin` exige validação em geração de chunks novos.
- **Config migration:** defaults do addon não devem ser confundidos com valores efetivos já persistidos no serverconfig do pack.

## 16. Matriz de validação obrigatória
> Estes itens são **testes requeridos**, não resultados já aprovados.

### Boot e sincronização
- Iniciar servidor dedicado com o stack físico completo.
- Entrar com cliente e abrir GUI de citizen/building/research/field/stock.
- Verificar ausência de mixin apply failure, linkage error e packet mismatch.

### Jobs e cidadãos
- Crafter: comparar tempo de crafting com config.
- Sifter: validar progress multiplier.
- Farmer: work delay, plant-after-hoe, plant-after-harvest, dumping e sequência de fields.
- Dyer: bleaching e reset do request system quando aplicável.
- Undertaker: chance/cap/totem sob condições controladas.
- Lumberjack e structure workers: regressão funcional por serem alvo de mixins.
- Mourning e interaction delay de citizens.

### Buildings/logística
- Batch repair e batch upgrade.
- Warehouse/couriers.
- Maximum stock.
- racks e hut inventories.
- resource/inventory/copy/building-link/university scrolls.

### Research
- velocidade normal e alterada.
- constraint padrão e `ignoreConstraints`.
- custos/effects/tree/UI.
- save/reload durante research em andamento.

### Requests/recipes
- requests básicos do MineColonies.
- receita ensinada pelo base.
- receita/integração de #404 e #405.
- post box e resolução de entregas.

### World/crops
- plantar crops em farmland conforme configuração.
- bone meal e biome restriction.
- alterar `vanillaFarmland` apenas em mundo de teste por causa do warning de crops existentes.
- gerar chunks novos e observar mixin de ChunkGenerator sem regressão.

### Combate/multiplayer/persistência
- raid com immunity/thorns configurados.
- dois clientes usando telas/módulos simultaneamente.
- restart completo do servidor e reabertura da colony.
- chunk unload/reload em colony ativa.

## 17. Evidências e limites
- **Autoridade física:** `MineColonies_Tweaks-1.21.1-3.33.jar` na modlist atual.
- **Source oficial:** `gisellevonbingen-Minecraft/MineColonies_Tweaks`.
- **Pin auditado:** `33efa6f49e0a3f660726456aa5e403f219177360`, `mod_version=3.33`.
- **README:** confirma configuração de velocidade de Crafter/Sifter e custom Tool type orientado a datapacks; blockPlacementDelay/blockDestructionDelay é descrito ali como planejamento histórico e não foi promovido a feature apenas por essa frase.
- **Source 3.33:** confirma configs, módulos, APIs, mensagens e a extensa matriz de mixins descrita acima.
- **Limite:** esta auditoria documenta superfícies concretamente presentes no source; não afirma que cada configuração foi runtime-tested no pack. A diferença MineColonies 1.1.1368 → 1.1.1381 permanece como risco de teste, não como incompatibilidade declarada.
