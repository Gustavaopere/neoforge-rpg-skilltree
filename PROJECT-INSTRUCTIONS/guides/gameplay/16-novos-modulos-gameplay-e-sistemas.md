[← Índice do guia](README.md)

# 16. Novos módulos incorporados na modlist 607

> Este capítulo documenta os **22 módulos novos** presentes na `modlist.txt` de 06/09/2026 que pertencem principalmente a Gameplay/Sistemas ou à infraestrutura geral deste guia. Eles são distintos das 21 lacunas históricas fechadas no capítulo 17.

## AAA Particles — 2.2.3

`aaa_particles-neoforge-1.21.1-2.2.3.jar`

**AAA Particles** é uma infraestrutura de efeitos visuais baseada em **Effekseer**. Ela fornece loader/runtime para efeitos de partículas mais complexos do que o pipeline vanilla e pode ser consumida por conteúdo próprio ou por addons que empacotam efeitos preparados nesse formato. O ponto importante para integração é que o mod pode sustentar telegraphing, partículas de habilidade, ambientação e feedback visual, mas a existência do efeito não prova que exista uma mecânica de gameplay correspondente.

**Authority/integração:** presentation/VFX. Não usar emissão de partículas como prova de dano, buff, cast ou sucesso de ação sem um hook mecânico separado.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/aaa-particles) · [Modrinth — busca](https://modrinth.com/mods?q=AAA+Particles) · [GitHub — busca](https://github.com/search?q=AAA+Particles+minecraft&type=repositories)


## AAA Particles: World — 2.0.0

`aaa_particles_world-neoforge-1.21.1-2.0.0.jar`

**AAA Particles: World** aplica o runtime AAA Particles diretamente a eventos visuais do mundo, substituindo ou enriquecendo VFX de situações como explosões, impactos e critical hits com efeitos Effekseer. A linha 2.0.0 amplia o conjunto de efeitos em mundo e possui opções de configuração para desabilitar efeitos individuais, inclusive os com flashes mais fortes.

**Authority/integração:** client/presentation. O addon reage a eventos; não deve ser tratado como provider do evento que está ilustrando.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/aaa-particles-world) · [Modrinth — busca](https://modrinth.com/mods?q=AAA+Particles%3A+World) · [GitHub — busca](https://github.com/search?q=AAA+Particles%3A+World+minecraft&type=repositories)


## Better Advanced Tooltips — 2101.1.0-build.5

`better-advanced-tooltips-2101.1.0-build.5.jar`

**Better Advanced Tooltips** amplia o modo avançado de tooltips ativado por `F3+H`. Ele expõe informações técnicas adicionais de itens, incluindo tags, components e outros dados úteis para depuração e análise de integração. Para este projeto, ele é especialmente útil para confirmar metadata em runtime durante testes manuais, mas não altera o conteúdo ou as regras do item.

**Authority/integração:** diagnóstico/interface. Nunca é authority de atributo, tag ou component; apenas apresenta dados registrados por outros sistemas.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/better-advanced-tooltips) · [Modrinth — busca](https://modrinth.com/mods?q=Better+Advanced+Tooltips) · [GitHub — busca](https://github.com/search?q=Better+Advanced+Tooltips+minecraft&type=repositories)


## Easy Model Entities — runtime sem versão top-level

`easy_model_entities-neoforge-1.21.1-2.3.0.jar`

**Easy Model Entities (EME)** permite transformar modelos do Blockbench em entidades e block entities por configuração de datapack/resource pack, evitando criar uma classe Java, renderer e pipeline de animação exclusivo para cada modelo. Perfis de servidor descrevem identidade/comportamento e perfis de render definem modelo, textura, escala, bounds e animação. Isso o torna relevante para NPCs ambientais, shrines, mimics, props animados e entidades auxiliares de projetos próprios.

**Authority/integração:** host/render framework. Se uma entidade EME for usada por uma perk ou sistema próprio, a regra mecânica deve vir do perfil/integração correspondente, não do renderer.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=Easy+Model+Entities) · [Modrinth](https://modrinth.com/mod/easy-model-entities) · [GitHub — busca](https://github.com/search?q=Easy+Model+Entities+minecraft&type=repositories)


## Easy NPC — runtime sem versão top-level

`easy_npc-neoforge-1.21.1-7.11.0.jar`

**Easy NPC** fornece o núcleo modular para criação de NPCs customizados com **diálogos, interações, comércio e comportamento configurável**. É adequado ao uso do pack em missões, rumores, tutoriais e personagens de ambientação porque os NPCs podem ser definidos sem escrever um mod Java dedicado para cada personagem. O runtime top-level da modlist não expõe uma versão interna confiável para este JAR; por isso o guia preserva o filename `7.11.0` sem inventar o campo runtime.

**Authority/integração:** provider de NPC/interação quando o NPC em questão é realmente configurado no Easy NPC. Não inferir quest completion só porque houve diálogo; a conclusão deve vir do sistema de quest ou de integração explícita.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-npc) · [Modrinth — busca](https://modrinth.com/mods?q=Easy+NPC) · [GitHub — busca](https://github.com/search?q=Easy+NPC+minecraft&type=repositories)


## Easy NPC: Bundle — 7.11.0

`easy_npc_bundle-neoforge-1.21.1-7.11.0.jar`

**Easy NPC: Bundle** é o pacote de conveniência do ecossistema Easy NPC. Ele agrupa/coordena os módulos necessários para uma instalação funcional e existe para simplificar distribuição, não para criar um segundo sistema de NPC paralelo ao core. No pack, o bundle deve ser lido como embalagem do stack Easy NPC, enquanto a semântica dos NPCs continua pertencendo ao core e aos módulos carregados.

**Authority/integração:** bundle/packaging. Não contar como provider adicional e não duplicar eventos do Easy NPC.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-npc) · [Modrinth — busca](https://modrinth.com/mods?q=Easy+NPC%3A+Bundle) · [GitHub — busca](https://github.com/search?q=Easy+NPC%3A+Bundle+minecraft&type=repositories)


## Easy NPC: Config UI — 7.11.0

`easy_npc_config_ui-neoforge-1.21.1-7.11.0.jar`

**Easy NPC: Config UI** adiciona a interface gráfica usada para configurar NPCs Easy NPC em jogo. Ela expõe parâmetros de NPC, diálogos e opções do core por uma UI mais acessível e é particularmente útil durante autoria e manutenção do conteúdo do modpack. A UI não substitui o core: sem Easy NPC ela não possui mecânica própria.

**Authority/integração:** authoring/UI. Mudanças persistidas pela UI pertencem ao Easy NPC; o clique na interface não deve ser usado como evento causal de gameplay.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/easy-npc-config-ui) · [Modrinth — busca](https://modrinth.com/mods?q=Easy+NPC%3A+Config+UI) · [GitHub — busca](https://github.com/search?q=Easy+NPC%3A+Config+UI+minecraft&type=repositories)


## EMF Compat: Core — 2.0.0

`emf_compat_core_1.21.1_2.0.0.jar`

**EMF Compat: Core** é o framework compartilhado da família EMF Compat. Ele centraliza código utilizado pelos módulos de compatibilidade visual que adaptam entidades/modelos de mods ao pipeline **Entity Model Features**. A própria página do projeto o classifica como framework compartilhado e client-side.

**Authority/integração:** client/presentation only. Não é provider de IA, hitbox, habilidade ou estado de entidade.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/emf-compat-core) · [Modrinth — busca](https://modrinth.com/mods?q=EMF+Compat%3A+Core) · [GitHub — busca](https://github.com/search?q=EMF+Compat%3A+Core+minecraft&type=repositories)


## Fantasy Armor — 1.2.4-1.21.1

`fantasy_armor-neoforge-1.2.4-1.21.1.jar`

**Fantasy Armor (Medieval Series)** adiciona conjuntos de armadura com modelos 3D de estética medieval/fantasy. O papel principal é ampliar equipamento e identidade visual do personagem; os itens continuam participando das regras normais de equipamento/atributos e de integrações que realmente reconheçam seus itens. O guia não atribui habilidades especiais que não estejam documentadas pelo mod.

**Authority/integração:** equipamento/conteúdo. Para perks de defesa, usar atributos efetivos do item/slot em runtime, não apenas o nome ou modelo do set.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/fantasy-armor) · [Modrinth — busca](https://modrinth.com/mods?q=Fantasy+Armor) · [GitHub — busca](https://github.com/search?q=Fantasy+Armor+minecraft&type=repositories)


## FTB Quests — 2101.1.34

`ftb-quests-neoforge-2101.1.34.jar`

**FTB Quests** é o sistema de quests team-based do pack. Ele organiza capítulos, quests, objetivos, dependências, rewards e progressão compartilhável, permitindo representar linhas narrativas e objetivos sem implementar uma engine de quests própria. A versão 2101.1.34 é a release NeoForge 1.21.1 instalada em 06/09/2026. O próprio projeto documenta que integrações com KubeJS, JEI/REI, Game Stages e filtros passam por **FTB XMod Compat**.

**Authority/integração:** provider canônico de estado/conclusão de quests FTB. Para perks baseadas em quests, observar o estado da quest/objetivo resolvido no servidor, não clicks de UI ou simples posse do reward.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ftb-quests-forge) · [Modrinth — busca](https://modrinth.com/mods?q=FTB+Quests) · [Wiki/Docs](https://docs.feed-the-beast.com/docs/mods/quests/) · [Código](https://github.com/FTBTeam/FTB-Quests)


## FTB XMod Compat — 21.1.11

`ftb-xmod-compat-neoforge-21.1.11.jar`

**FTB XMod Compat** reúne integrações oficiais entre os mods FTB e outros ecossistemas. No contexto do FTB Quests, ele é a camada que habilita pontes como KubeJS e recipe viewers sem incorporar essas integrações diretamente no core. Isso reduz acoplamento e deixa a presença de determinadas bridges condicionada aos mods envolvidos.

**Authority/integração:** bridge. A fonte do estado continua sendo o provider de origem (por exemplo FTB Quests); a bridge apenas expõe/transporta integração.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/ftb-xmod-compat) · [Modrinth — busca](https://modrinth.com/mods?q=FTB+XMod+Compat) · [Código](https://github.com/FTBTeam/FTB-XMod-Compat)


## Guide-API-VP — 2.3.0

`Guide-API-VP-1.21.1-2.3.0.jar`

**Guide-API-VP** é um fork moderno do Guide-API voltado à criação de **livros de documentação in-game baseados principalmente em código**. Ele oferece registro e organização de books, categorias e tipos de página, integração de receitas/imagens/texto e possibilidade de conteúdo condicionado à configuração do mod. É infraestrutura útil para mods consumidores — historicamente incluindo Vampirism — e não um sistema de progressão por si só.

**Authority/integração:** documentação/API. Uma página desbloqueada não deve ser confundida automaticamente com quest, advancement ou mastery sem contrato explícito do mod consumidor.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/guide-api-village-and-pillage) · [Modrinth](https://modrinth.com/mod/guide-api) · [Código](https://github.com/maxanier/Guide-API)


## Immersive Furniture — 0.3.3+1.21.1

`immersive_furniture-neoforge-0.3.3+1.21.1.jar`

**Immersive Furniture** permite criar e usar móveis, objetos decorativos e props customizados, inclusive com conteúdo obtido de uma biblioteca online e definição própria. O projeto trabalha em cliente e servidor e é mais flexível que um catálogo fixo de furniture porque funciona como plataforma de conteúdo decorativo customizável. No pack ele é relevante para ambientação de NPCs, casas, colônias e cenários dos projetos próprios.

**Authority/integração:** decoração/objeto. Só tratar um móvel como estação mecânica se o perfil/configuração ou integração realmente definir comportamento além de apresentação.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/immersive-furniture) · [Modrinth](https://modrinth.com/mod/immersive-furniture) · [GitHub — busca](https://github.com/search?q=Immersive+Furniture+minecraft&type=repositories)


## KubeJS — 2101.7.2-build.374

`kubejs-neoforge-2101.7.2-build.374.jar`

**KubeJS** é a principal superfície de scripting do pack. Ele permite editar receitas, criar conteúdo customizado e reagir a eventos do jogo em **JavaScript**, servindo como camada de composição para modpacks. Em integração de perks, KubeJS é importante porque pode fornecer glue code e regras do pack, mas scripts são configuração do pack, não uma API universal do mod provider.

**Authority/integração:** scripting/orchestration. Sempre identificar qual evento/estado do provider o script está consumindo; não substituir causalidade provider-native por polling genérico quando existe hook melhor.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs) · [Modrinth](https://modrinth.com/mod/kubejs/version/2101.7.2-build.374) · [Wiki/Docs](https://kubejs.com/wiki) · [Código](https://github.com/KubeJS-Mods/KubeJS)


## KubeJSCurios — 1.0.4

`kubejs_curios_neoforge_1.21.1-1.0.4.jar`

**KubeJSCurios** expõe o ecossistema **Curios/slots acessórios** ao KubeJS. Sua função é permitir que scripts consultem ou manipulem integrações relacionadas a equipamentos Curios de forma adequada à versão instalada. Ele não cria slots ou itens acessórios por conta própria; esses contratos continuam pertencendo ao Curios e aos mods que registram conteúdo.

**Authority/integração:** bridge KubeJS↔Curios. Para perks de equipamento, a authority primária é o estado do slot/stack no servidor.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=KubeJSCurios) · [Modrinth — busca](https://modrinth.com/mods?q=KubeJSCurios) · [GitHub — busca](https://github.com/search?q=KubeJSCurios+minecraft&type=repositories)


## KubeJS Additions — 1.21.1-6.0.0

`kubejsadditions-neoforge-1.21.1-6.0.0.jar`

**KubeJS Additions** amplia o que scripts KubeJS podem observar e fazer. A documentação destaca, entre outras extensões, capacidade de ouvir eventos adicionais/arbitrários de plataformas suportadas, abrindo superfícies que não existem no KubeJS base. Isso é poderoso para integração, mas exige cautela: um evento de baixo nível exposto pela bridge não é automaticamente o evento canônico de uma mecânica.

**Authority/integração:** extensão de scripting. Preferir eventos de domínio do provider quando existirem e usar eventos genéricos somente com causalidade demonstrável.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=KubeJS+Additions) · [Modrinth](https://modrinth.com/mod/kubejs-additions/version/kubejsadditions-neoforge-6.0.0) · [GitHub — busca](https://github.com/search?q=KubeJS+Additions+minecraft&type=repositories)


## KubeJSDelight — 1.1.6

`kubejsdelight-1.1.6.jar`

**KubeJSDelight** integra Farmer's Delight ao KubeJS, permitindo definir por script conteúdo como **knives, pies e feasts** e trabalhar com receitas de Cutting Board e Cooking Pot. Isso torna o sistema culinário extensível pelo modpack sem substituir Farmer's Delight como provider das operações culinárias.

**Authority/integração:** bridge de scripting Farmer's Delight. Receitas/itens criados por script devem continuar respeitando as operações e tags canônicas do ecossistema alimentar.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/kubejs-delight) · [Modrinth — busca](https://modrinth.com/mods?q=KubeJSDelight) · [GitHub — busca](https://github.com/search?q=KubeJSDelight+minecraft&type=repositories)


## LootJS — 1.21.1-3.7.0

`lootjs-neoforge-1.21.1-3.7.0.jar`

**LootJS** é um addon de KubeJS focado em **modificação global de loot**. Ele fornece APIs de scripting para alterar drops e loot tables de maneira controlada pelo pack, permitindo adicionar, remover ou condicionar resultados sem editar manualmente cada datapack. Em auditorias de reward/drop, sua presença significa que a tabela original do mod pode não ser a única camada que determina o resultado final.

**Authority/integração:** pipeline de loot via scripts. Perks ligadas a drop devem observar o resultado final ou um hook causal adequado após as modificações, evitando duplicar rewards já inseridos por LootJS.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/lootjs) · [Modrinth — busca](https://modrinth.com/mods?q=LootJS) · [GitHub — busca](https://github.com/search?q=LootJS+minecraft&type=repositories)


## DynamicTreesCompat — 2.3

`NeoForge-dynamictreescompat-2.3.jar`

**DynamicTreesCompat** é uma camada de compatibilidade do ecossistema Dynamic Trees. Seu papel é hospedar integrações/compatibilidade que permitem que espécies, blocos ou regras de outros mods funcionem com a representação dinâmica de árvores. Ele deve ser tratado como bridge e não como uma nova flora independente.

**Authority/integração:** compatibility bridge. O provider de espécie/bioma continua sendo o mod de origem; Dynamic Trees continua sendo o sistema de crescimento/árvore.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=DynamicTreesCompat) · [Modrinth — busca](https://modrinth.com/mods?q=DynamicTreesCompat) · [GitHub — busca](https://github.com/search?q=DynamicTreesCompat+minecraft&type=repositories)


## Transmog — 1.6.0

`transmog-neoforge-1.6.0+1.21.1.jar`

**Transmog** separa **aparência** de **comportamento/estatísticas** do item. A Transmogrification Table permite aplicar a aparência de outro item a armas, ferramentas, armaduras e conteúdo modded sem trocar o item funcional. Esse detalhe é crítico para integração: identificação visual não é suficiente para determinar material, atributos, enchantments, perk eligibility ou item real.

**Authority/integração:** aparência separada da identidade mecânica. Toda lógica deve consultar o stack/atributos reais, nunca o modelo/textura resultante do transmog.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/transmog) · [Modrinth — busca](https://modrinth.com/mods?q=Transmog) · [GitHub — busca](https://github.com/search?q=Transmog+minecraft&type=repositories)


## Alex's Caves Continued — 1.0.9

`alexscaves-1.0.9-neoforge+1.21.1.jar`

**Alex's Caves Continued** é um port/continuação que preserva o conteúdo do Alex's Caves original em versões mais novas: os mesmos grandes cave biomes, criaturas, blocos, receitas e progressão são trazidos para NeoForge/Fabric/Forge modernos. No pack atual ele aparece junto de outro JAR que também declara `modid alexscaves`; essa duplicidade é um **bloqueio de startup** e precisa ser resolvida antes do jogo carregar.

**Authority/integração:** provider de conteúdo com **modid duplicado no snapshot atual**. Não existe desambiguação por JAR/registry nem fail-closed de runtime capaz de manter os dois `alexscaves` simultaneamente: a instalação deve reter exatamente uma implementação, ou um repack legítimo com mod ID distinto, antes do startup. Só depois dessa correção a implementação escolhida pode ser tratada como provider válido.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/alexs-caves-continued) · [Modrinth — busca](https://modrinth.com/mods?q=Alex%27s+Caves+Continued) · [GitHub — busca](https://github.com/search?q=Alex%27s+Caves+Continued+minecraft&type=repositories)


## Alex's Mobs — 1.22.9

`alexsmobs-1.22.9.jar`

**Alex's Mobs** é o roster original de fauna/monstros, com dezenas de criaturas que possuem IA, interações, tame/breeding, drops e itens próprios. O JAR `alexsmobs-1.22.9.jar` é um port NeoForge 1.21.1 presente simultaneamente com **Alex's Mobs Continued 2.1.9**, e ambos declaram o mesmo `modid alexsmobs`; esse estado é um **bloqueio de startup**.

**Authority/integração:** provider de fauna com **modid duplicado no snapshot atual**. A instalação deve manter exatamente um dos JARs `alexsmobs` (ou um repack legítimo com ID distinto) antes do startup. `ModList.isLoaded("alexsmobs")`, inspeção de registry/object/JAR ou fail-closed em runtime não podem resolver dois mod IDs idênticos durante discovery.

**Fontes:** [CurseForge — busca](https://www.curseforge.com/minecraft/search?page=1&pageSize=20&sortBy=relevancy&class=mc-mods&search=Alex%27s+Mobs) · [Modrinth](https://modrinth.com/mod/alexs-mobs%281.21.1%29/version/1.22.9) · [GitHub — busca](https://github.com/search?q=Alex%27s+Mobs+minecraft&type=repositories)
