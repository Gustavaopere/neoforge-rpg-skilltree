# GUIA COMPLETO — Mods de Tecnologia | NeoForge 1.21.1

Create e addons, energia, indústria, logística, automação, transporte, computação e integrações tecnológicas.

**Fonte canônica:** diretório versionado no GitHub.

**Snapshot consolidado deste anexo:** **2026-09-07**, modlist física com **612 entradas top-level incluindo NeoForge**. O recorte Tecnologia permanece em **200 JARs tecnológicos/cross-domain**.

> `CURRENT-MODLIST.md` + delta 07/09 prevalecem sobre versões históricas embutidas nos capítulos. Update físico não autoriza inferir mudança de API/hook.

## Índice operacional

- Reconciliação atual da modlist
- 00–20. Corpo histórico consolidado
- 21. Atualização da modlist tecnológica — 07/09

## Regras de manutenção

- provider-native first;
- não criar FE/SU/heat/fluido/custo paralelo ao provider;
- biblioteca/bridge/UI/compat não vira provider;
- Bits 'n' Bobs 2.3.1 = `Manter`; conflito visual com Sulfuric Resonance segue risco aceito/render-test obrigatório;
- GitHub continua fonte canônica editorial.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/CURRENT-MODLIST.md -->

# Reconciliação atual da modlist — Mods de Tecnologia

> **AUTORIDADE DE PRESENÇA/JAR/VERSÃO — 2026-09-07.** O estado físico atual do pack é o snapshot de 06/09/2026 mais o delta de 07/09. Decisões curatoriais explicitamente revistas em 07/09 prevalecem sobre as de 06/09.

A modlist atual contém **612 entradas top-level**, incluindo NeoForge. O recorte Tecnologia permanece com **200 JARs tecnológicos/cross-domain**: o delta de 07/09 não adicionou nem removeu provider tecnológico do recorte, mas atualizou cinco artefatos já classificados.

### Authority corrente adicional

- **Oritech — `1.2.12`**: `oritech-neoforge-1.21.1-1.2.12.jar`. Esta é a identidade física/runtime atual e prevalece sobre referências históricas `1.2.10/1.2.11` preservadas em snapshots anteriores.

## Updates tecnológicos

- Create: Bits 'n' Bobs: `2.3.0 → 2.3.1`.
- Create: Copycats+: `3.0.8 → 3.0.9`.
- Create: Cyber Goggles: `8.5.1 → 8.5.2`.
- Lychee Tweaker: `6.6.1 → 6.7.0`.
- Petrolpark's Library: `1.5.8 → 1.5.9`.

## Bits 'n' Bobs — decisão revisada em 07/09

`bits_n_bobs-2.3.1.jar` continua fisicamente instalado e a decisão curatorial vigente foi **revisada para `Manter` em 07/09/2026**. A antiga decisão `Tirar` de 06/09 não é mais operacional.

O conflito visual com os Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 permanece documentado e é **risco aceito**, não incompatibilidade resolvida. Manter ambos exige monitorar/render-testar esse caso após updates de qualquer lado.

## Boundaries de novos mods cross-domain

- `CerbonsAPI-NeoForge-1.21-1.3.0.jar` é biblioteca/dependência de Bosses of Mass Destruction; não é provider tecnológico.
- `ironsable-wind-1.0.0.jar` é bridge magia↔Sable; interação com blocos simulados não a transforma em provider tecnológico autônomo.

## Regra operacional

- Este fechamento + o delta físico de 07/09 fixam presença/JAR/runtime atual do recorte tecnológico.
- Update de versão não autoriza inferir API, hook ou compatibilidade sem inspeção.
- Biblioteca, bridge, UI ou compatibilidade não se torna provider mecânico apenas por participar do stack tecnológico.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/00-visao-geral.md -->

<!-- Guia temático canônico versionado no GitHub | referência atual de presença/JAR/versão: modlist.txt reconciliada em 2026-08-30 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **SNAPSHOT HISTÓRICO — 2026-08-30:** presença, JARs e versões do eixo tecnológico foram revalidados naquele snapshot. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) fixa o estado instalado atual e prevalece sobre esta seção. Naquele checkpoint, entre os drifts incorporados estavam Oritech `1.2.11`, Aeronautics Camera Sync `1.4.0`, AE2 Import Export Card `1.6.0`, Create Cobblestone `1.5.0+neoforge-1.21.1-153`, Create Stats `1.4.1`, Cyber Goggles `8.3.15`, KilaGraph `21.1.0.12` e Sound Physics Aeronautics `1.4.0.1`. Sophisticated JEI Index `1.2.2` também entra formalmente na cobertura tecnológica atual.

> Este guia é um **catálogo descritivo dos mods de tecnologia** instalados no pack. O foco é explicar o que cada sistema acrescenta, como sua tecnologia funciona e como os addons se encaixam no ecossistema principal. Bibliotecas, UI e bridges são classificadas pelo papel técnico real e não tratadas como sistemas tecnológicos independentes sem contrato mecânico correspondente.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/01-sistemas-tecnologicos-principais.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 1. Sistemas tecnológicos principais

## Create — 6.0.10

`create-1.21.1-6.0.10.jar`
**Create** é a espinha dorsal mecânica do pack. Em vez de trabalhar principalmente com máquinas fechadas que recebem FE, ele constrói automação através de **rotação, velocidade e stress**. Eixos, engrenagens, correias, caixas de engrenagem, rodas d'água, motores, boilers e outras fontes produzem ou transmitem movimento; máquinas consomem capacidade de stress conforme trabalham.
O sistema de produção é deliberadamente físico. Crushing Wheels esmagam materiais, Mechanical Presses prensam, Mechanical Mixers misturam, Deployers simulam interação de jogador, Mechanical Arms distribuem itens, belts fazem transporte visível e contraptions transformam conjuntos inteiros de blocos em máquinas móveis. Isso faz com que uma fábrica Create seja também uma construção de engenharia, porque layout, transmissão mecânica, logística e sincronização importam.
Outra parte central são as **contraptions**. Blocos podem ser colados e montados em pistons, bearings, trains ou outras estruturas móveis. No seu pack esse conceito se expande drasticamente por Create Aeronautics e Sable, chegando a veículos físicos, aeronaves, submarinos e espaçonaves.

## Applied Energistics 2 — 19.2.17

`appliedenergistics2-19.2.17.jar`
**Applied Energistics 2 (AE2)** é o grande sistema de armazenamento digital e automação de crafting do pack. Ele substitui redes gigantes de baús por uma **ME Network**, na qual itens e fluidos são representados dentro de células de armazenamento e acessados por terminais.
A rede possui energia, cabos, dispositivos, interfaces e uma arquitetura baseada em canais. Storage Cells armazenam recursos; ME Drives concentram células; Terminals dão acesso ao conteúdo; Import/Export Buses movimentam itens; Storage Buses incorporam inventários externos; Pattern Providers e Molecular Assemblers executam **autocrafting sob demanda**.
O autocrafting é uma das funções mais importantes: receitas são gravadas em patterns e o sistema calcula ingredientes, subcomponentes e etapas necessárias. Com integrações do seu pack, AE2 também passa a interagir com processos do Create e até armazenar/transmitir grandezas relacionadas à rotação.
AE2 também possui **Spatial Storage**, capaz de capturar regiões do mundo em células espaciais, além de P2P tunnels, subnetworks e ferramentas de diagnóstico para redes complexas.

## Oritech — 1.2.12

`oritech-neoforge-1.21.1-1.2.12.jar`
**Oritech** é um sistema tecnológico mais tradicional, baseado em **máquinas animadas, energia, processamento avançado e equipamentos**. Ele complementa Create oferecendo máquinas especializadas e uma progressão industrial própria, com processamento de recursos, infraestrutura energética e tecnologia de alto nível.
Sua identidade visual é de maquinaria industrial compacta, mas com animações e componentes visíveis. A progressão leva o jogador de processamento básico para sistemas cada vez mais avançados, incluindo máquinas de fabricação, geração e armazenamento de energia, ferramentas e equipamentos tecnológicos.
No pack, ele funciona como uma segunda linguagem tecnológica: Create resolve problemas por cinética e montagem física; Oritech resolve muitos deles através de máquinas energizadas especializadas.

## Oritech Things — 0.0.46

`oritechthings-0.0.46.jar`
**Oritech Things** amplia Oritech com uma camada de upgrades, controle e utilidades voltada principalmente às máquinas avançadas do mod-base. Ele adiciona **addons tiered de Speed, Efficiency, Processing, Capacitor e Acceptor**, inclusive versões híbridas de Efficient Speed, permitindo escalar capacidade e desempenho de máquinas por vários níveis.
O addon aprofunda especialmente o **Particle Accelerator**: inclui Advanced Target Designator, sensor/controlador de velocidade com modo manual ou automático, Accelerator Linear Motor e Magnetic Field capaz de reduzir significativamente o tamanho do anel ao custo de energia. Também possui **Cross-Dimensional Drone Port**, permitindo definir destinos de drones em outra dimensão.
Outros recursos incluem **Exo JetPack** com voo criativo consumindo energia/turbo fuel, Frame Placer para montar automaticamente a estrutura do Destroyer e conteúdo próprio como Infested Amethyst/Amethyst Fish. Portanto ele não é apenas QoL: adiciona peças funcionais e automação avançada ao estágio alto da progressão Oritech.

## Sable — 2.0.5

`sable-neoforge-1.21.1-2.0.5.jar`
**Sable** é a infraestrutura que adiciona **sublevels** ao Minecraft: regiões móveis formadas por blocos, block entities e entidades que continuam interativas depois de montadas. O jogador pode permanecer dentro dessa região, andar, usar inventários e interagir com máquinas enquanto o conjunto inteiro muda de posição e orientação no mundo principal.
Isso fornece a base técnica para construções que se comportam como objetos físicos completos, em vez de simples animações de contraption. Sistemas como Create Aeronautics utilizam esses sublevels para veículos e estruturas móveis construídas livremente com blocos.
Como o conteúdo passa a existir em espaços coordenados próprios, outros mods precisam reconhecer corretamente transformação de posição, renderização, som, teleporte e interação entre level principal e sublevels; por isso o pack possui várias bridges específicas para Sable.

## Sable Assembly Fix — 1.0.0

`SableAssemblyFix-1.0.0.jar`
**Sable Assembly Fix** é um patch server-side para o ciclo de montagem e desmontagem de estruturas Sable/Create Aeronautics. Ele evita duplicação de itens quando block entities de outros mods possuem inventário, mas não implementam corretamente o contrato `Clearable`: sem o patch, o conteúdo pode ser preservado na estrutura montada e ao mesmo tempo dropado durante a desmontagem.
A correção é genérica e foi feita para cobrir inventários modded como kegs, cabinets, baskets, Item Drains e storages compatíveis, sem adicionar um novo sistema de armazenamento ou de física. Sua função é preservar a integridade de inventários durante `assembly/disassembly` no stack móvel. A build instalada é `1.0.0` para NeoForge 1.21.1 e depende do stack Sable + Create + Create Aeronautics.

## Create Aeronautics — 1.3.1

`create-aeronautics-bundled-1.21.1-1.3.1.jar`
**Create Aeronautics** usa Create e Sable para transformar construções de blocos em **veículos físicos montados pelo jogador**. Não há chassis pré-fabricado obrigatório: corpo, propulsão, controle, armazenamento e máquinas podem ser organizados como parte da própria construção antes da montagem.
O bundle reúne três linhas principais. **Simulated** fornece a base de montagem e interação com contraptions físicas; **Aeronautics** acrescenta voo e sustentação, incluindo propellers e mecanismos de hot air; **Offroad** acrescenta rodas, pneus, direção e componentes para veículos terrestres. A própria página oficial descreve o escopo indo de aviões, drones e balões a carros e caminhões.
As contraptions podem continuar contendo máquinas e logística do Create enquanto se movem. A versão 1.3.1 também registra correções para Docking Connector/fluidos, compatibilidade CC e parâmetros de atrito de pneus, reforçando que movimento, docking e sistemas internos do veículo fazem parte da simulação funcional.

## Create Tracks+ — 1.0.6b

`tracks_plus-1.0.6b.jar`
**Create Tracks+** é um fork/addon de lagartas físicas voltado ao stack **Create Aeronautics + Sable**. Ele adiciona conjuntos de tracks/esteiras para veículos, com geometria retrabalhada, track mounts, suspensão de maior curso e interação física com o terreno, permitindo que veículos transponham obstáculos usando o contato das lagartas em vez de tratar as tracks apenas como rodas visuais.
A build instalada é `1.0.6b`, linha beta para NeoForge 1.21.1. O projeto declara dependência de Create, Create Aeronautics e Sable e também declara **incompatibilidade com o Create:Tracks original**, pois este fork ocupa o mesmo papel funcional. Portanto esta entrada representa o provider de tracks presente no pack, não uma segunda instalação paralela do mod original.

## Immersive Aeronautics — runtime 6.0.7

`Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`
**Immersive Aeronautics** é um rewrite de Immersive Portals voltado especificamente a **Create Aeronautics/Sable**. Seu objetivo é fazer navios e outras contraptions Sable atravessarem portais mantendo a experiência de portal contínuo e a integração visual do sistema, em vez de tratar o veículo como um conjunto incompatível de blocos durante a travessia.
A build 1.1.4 inclui correções para renderização quando há várias contraptions Sable simultâneas e para artefatos com Distant Horizons, além de trabalho específico para tornar o transporte de portais mais fluido. O arquivo publicado é `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`, enquanto o metadata interno continua se identificando como **Immersive Portals 6.0.7**; o guia preserva as duas identidades separadamente.
A release 1.1.4 é beta para NeoForge 1.21.1; essa classificação descreve maturidade da implementação, não ausência de funcionalidade ou incerteza sobre o JAR instalado.

## Create: The Factory Must Grow — 1.2.4b Community

`tfmg-1.21.1-1.2.4b-community.jar`
**Create: The Factory Must Grow (TFMG)** expande Create para **indústria pesada, petróleo e eletricidade**. A progressão adiciona extração e refino de óleo, produção de combustíveis como diesel, gasolina e LPG, além de materiais industriais como aço, enxofre, alumínio e chumbo.
A energia mecânica passa a incluir **motores a combustão** de diferentes combustíveis e configuração de cilindros. O addon também possui um sistema elétrico próprio com **voltagem** e compatibilidade com FE, levando a fábrica de uma rede puramente cinética para linhas elétricas e equipamentos industriais de maior escala.
O conteúdo se estende a infraestrutura de refinaria, componentes químicos, pipes, blocos estruturais como concreto/cinderblocks/suportes e equipamentos associados à indústria pesada. Há ainda armas e produtos industriais, como napalm e flamethrowers, que derivam das mesmas cadeias de materiais e combustíveis.
O JAR do pack é a build comunitária `1.2.4b-community`; a documentação pública do projeto-base expõe atualmente a linha oficial 1.2.x para NeoForge 1.21.1, então o guia mantém a identidade do arquivo local sem substituí-la pela numeração pública.

## Create: Rubberworks — 1.1.4

`rubberworks-neoforge-1.21.1-1.1.4.jar`
**Create: Rubberworks** acrescenta uma cadeia de **resina → borracha** integrada às máquinas do Create. O objetivo declarado do projeto é substituir usos industriais artificiais de dried kelp por uma produção dedicada de borracha, dando a componentes do Create uma matéria-prima própria desse domínio.
O **Sapper** é usado para extrair resina, que depois entra em etapas de processamento mecânico até virar borracha e materiais derivados para receitas. A linha é pequena em comparação com TFMG, mas constitui uma cadeia produtiva real e separada, com coleta de matéria-prima e transformação industrial próprias.

## Create: New Age — 1.2.0+mc1.21.1

`create-new-age-1.2.0+neoforge-mc1.21.1.jar`
**Create: New Age** adiciona uma camada de **eletricidade, magnetismo, calor e nuclear** construída ao redor das mecânicas visuais do Create. Grandes bobinas de cobre girando com magnetos geram eletricidade; diferentes motores fazem a conversão inversa e transformam energia elétrica em rotação.
A rede elétrica usa fios e alimenta máquinas como o **Energiser**, que combina eletricidade e movimento para processos específicos. O addon também possui produção térmica e multiblocks nucleares cujo calor pode ferver água e alimentar steam engines do Create, mantendo ligação entre geração elétrica, calor e cinética.
Na versão 1.2.0 foram adicionados **efeito de radiação e Geiger counter**, além de comportamento de explosão para reatores superaquecidos. A mesma linha inclui Street Lights/Lamp Posts e suporte experimental a CC:Tweaked. Portanto o componente nuclear não é apenas decorativo: temperatura, radiação e falha de reator fazem parte do sistema.

## Create: Power Grid — 0.6.1

`powergrid-mc1.21.1-0.6.1.jar`
**Create: Power Grid** implementa uma rede elétrica inspirada em **engenharia elétrica real**, integrada ao Create. O sistema exige equilibrar geração e demanda para manter tensão estável e trabalha com geração, distribuição, circuitos, medição e conversão em vez de representar eletricidade apenas como uma reserva abstrata.
A linha 0.6 inclui Electric Pump, Energy Meter, FE Inverter, Large Induction Rotor, Integrated Circuits, Modular Displays, Solar Panels, Portable Drill/Saw e outros componentes de geração e controle. A build `0.6.1` corrige persistência de switches, renderização de fios, parsing de expressões e crashes com **TFMG Community Edition** e contraptions Sable em alta velocidade; também adiciona integração CC para battery e redstone converter, além de comportamento de corte de árvores ao Portable Saw.

## Create Nuclear — 1.3.2-beta.3

`createnuclear-1.3.2-beta.3-neoforge.jar`
**Create Nuclear** implementa uma cadeia de **fissão nuclear integrada ao Create**. O worldgen adiciona **Uranium e Thorium**, e o combustível depende de etapas de refino que podem ser automatizadas antes de chegar ao reator.
O reator é uma instalação industrial cujo desempenho depende da montagem e do ciclo de combustível; um projeto otimizado pode produzir milhões de **Stress Units**, convertendo a cadeia nuclear diretamente em capacidade cinética para a fábrica Create. O sistema também incorpora os perigos associados à radiação e ao funcionamento inadequado do reator, de modo que energia nuclear não é representada apenas por um bloco gerador isolado.
O JAR `1.3.2-beta.3` é a última build NeoForge publicada para **Minecraft 1.21.1** e permanece classificado como beta. O projeto já possui uma linha 2.x mais nova para Forge 1.20.1, mas ela é outra plataforma/versão de jogo e não representa a build instalada neste pack.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/02-applied-energistics-2-e-automacao-digital.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 2. Applied Energistics 2 e automação digital

## Advanced AE — 1.6.12

`AdvancedAE-1.6.12-1.21.1.jar`
**AdvancedAE** é uma expansão de conveniência e endgame para redes AE2. Seus **Advanced Pattern Providers** permitem definir por qual face cada input de um processing pattern será enviado, reduzindo a necessidade de pipes e lados dedicados em máquinas com entradas específicas.
O **Quantum Computer** funciona como um sistema de crafting escalável: pode executar múltiplas tarefas simultâneas compartilhando co-processors e storage dentro de um multiblock configurável. O **Quantum Crafter** acessa o inventário ME diretamente, executa muitas operações por tick e suporta crafting recursivo, receitas com ingredientes não consumidos e limites de estoque.
O addon também inclui **Quantum Armor** conectada à rede ME, com utilidades como voo, magnetismo, alimentação automática e resistências; uma **Reaction Chamber** para processamento de alto consumo energético; buses especializados para manter estoque/importar/exportar; e **ME Throughput Monitor** para acompanhar variação de itens, fluidos ou energia na rede.

## ExtendedAE — runtime 1.21-2.2.35-neoforge

`ExtendedAE-1.21-2.2.35-neoforge.jar`
**ExtendedAE** amplia storage, I/O e autocrafting do AE2 com componentes de alta capacidade. Pattern Provider e Interface passam a ter **36 slots**; Import/Export Buses possuem versões até **8× mais rápidas**; e terminais de acesso a patterns recebem versões maiores, inclusive wireless.
O addon inclui **Infinity Cells** para água/cobblestone, Pattern Modifier para editar e clonar patterns em lote, buses filtrados por tag ou mod ID, Precise Export Bus, Active Formation Plane e versões paralelas de Inscriber/Charger/Molecular Assembler. O **Assembler Matrix** fornece um multiblock escalável de crafting de altíssimo throughput.
Há ainda ME Canner, Crystal Assembler/Restorer e **ME Wireless Connector** para ligar trechos de rede à distância. A release pública é 2.2.35; o metadata runtime instalado declara `1.21-2.2.35-neoforge`, e as duas identificações permanecem preservadas.

## AE2: Crafting Tree — 1.1.1

`ae2ct-1.21.1-1.1.1.jar`
**AE2: Crafting Tree** adiciona uma visualização explícita do plano de autocrafting do AE2. Um botão de **Show Crafting Plan** abre a árvore do craft, exibindo a relação entre produto final, componentes intermediários e dependências recursivas.
A função é diagnóstica: em receitas grandes, permite seguir visualmente quais subcrafts compõem a solicitação em vez de analisar apenas a lista plana de ingredientes do terminal.

## AE2 Import Export Card — 1.5.0

`ae2importexportcard-1.21.1-1.5.0.jar`
**AE2 Import Export Card** leva automação de inventário diretamente aos **wireless terminals**. A Import Card observa slots selecionados do inventário do jogador e envia automaticamente os itens correspondentes para a ME Network; a Export Card faz o caminho inverso e mantém quantidades configuradas no inventário a partir do armazenamento ME.
Ambas suportam filtros. A integração reconhece **Fuzzy Card** e **Inverter Card**; a Export Card pode usar **Acceleration Card** para aumentar transferência até stacks inteiros e **Crafting Card** para solicitar autocrafting quando o item configurado não existe em estoque. Assim, o inventário pessoal pode ser tratado como uma extensão automatizada da rede enquanto o terminal wireless está em uso.

## AE2 Network Analyzer — 2.1.5

`AE2NetworkAnalyzer-1.21-2.1.5-neoforge.jar`
**AE2 Network Analyzer** é uma ferramenta de inspeção visual para ME Networks. Ele representa a rede e seus devices de forma gráfica, permitindo examinar como componentes estão conectados e localizar segmentos ou dispositivos dentro de topologias grandes.
Seu papel é observabilidade e diagnóstico, não armazenamento ou crafting: transforma a estrutura lógica da rede em uma visualização navegável para entender conexões e organização.

## AE2 JEI Integration — 1.2.1

`ae2jeiintegration-1.2.1.jar`
**AE2 JEI Integration** restaura no Minecraft 1.21 a camada JEI que deixou de vir embutida no AE2. Ela registra no JEI as **receitas dos blocos-máquina** do ecossistema AE2 e permite preencher ingredientes diretamente a partir da visualização de receita.
A integração suporta **ingredient autofill**, click-and-drag de **Ghost Ingredients** para slots de configuração e opções para sincronizar a busca do JEI com a busca dos Terminals AE2. A versão 1.2.1 também registra correção de compatibilidade com ME Requester.

## AE2WTLib — 19.5.1

`ae2wtlib-19.5.1.jar`
**Applied Energistics 2 Wireless Terminals / AE2WTLib** adiciona versões wireless de vários terminais AE2 — incluindo Crafting, Pattern, Interface e Fluid Terminal — e fornece a biblioteca usada por integrações que operam sobre esses dispositivos.
O addon também possui um **Wireless Universal Terminal**, reunindo múltiplas interfaces em um único item. Com **Quantum Bridge Card**, os terminais podem permanecer ligados à ME Network a qualquer distância e inclusive entre dimensões, usando a infraestrutura de quantum linking em vez do alcance wireless convencional.

## Schematic Energistics — 1.5.4a

`schematicenergistics-1.21.1-1.5.4a.jar`
**Schematic Energistics** conecta diretamente o **Schematicannon do Create** à rede AE2 por meio do **Cannon Interface**. Colocado ao lado do cannon e conectado à ME Network, o bloco permite que o Schematicannon solicite automaticamente os materiais da construção ao armazenamento digital.
Se um item exigido pelo schematic não estiver armazenado, a integração pode acionar o **autocrafting do AE2** para produzi-lo. Também reabastece gunpowder automaticamente. Containers convencionais próximos continuam tendo prioridade, e a rede ME entra como fonte adicional quando esses inventários não fornecem o material necessário.

## Polymorphic Energistics — 0.4.1

`polyeng-0.4.1.jar`
**Polymorphic Energistics** leva a resolução de conflitos de receita do Polymorph para interfaces de crafting do AE2. Quando a mesma disposição de ingredientes pode gerar resultados diferentes, o jogador recebe o seletor correspondente dentro do fluxo de crafting do sistema ME em vez de ficar preso à receita escolhida automaticamente.
A versão 0.4.1 também possui integração com AE2WTLib para reposicionar o widget de seleção em wireless crafting terminals e evitar sobreposição com os controles de retorno de itens. No pack, a implementação Polymorph+ fornece a camada compatível usada por esse tipo de addon.

## Not Enough Patterns — 0.5.1

`nep-1.21.1-0.5.1.jar`
**Not Enough Patterns** estende o sistema de processing patterns do AE2 para máquinas e tipos de receita de outros mods. Em vez de obrigar toda integração externa a ser representada como um pattern genérico, o addon cria suporte específico para máquinas/receitas compatíveis e melhora a codificação desses processos dentro do ecossistema ME.
As versões públicas recentes também adicionaram compatibilidade com providers expandidos e corrigiram casos em que receitas transferidas por viewers externos podiam ser codificadas no tipo de pattern errado. O objetivo é fazer autocrafting intermod funcionar de maneira mais fiel à máquina que executará o processo.
A fonte de identidade instalada permanece a modlist: **`nep-1.21.1-0.5.1.jar`**. Índices públicos consultados exibem builds anteriores 0.3.x/0.5.0 em momentos diferentes, portanto o guia não rebaixa o JAR local para essas versões.

## Applied Create — 1.1.7

`appliedcreate-1.21.1-1.1.7.jar`
**Applied Create** é uma bridge estrutural entre AE2 e Create. Ela permite representar e transportar **capacidade cinética/stress** pela infraestrutura ME, conectando uma rede digital a máquinas que dependem de rotação.
O addon inclui **Kinetic Energy Acceptor**, armazenamento próprio de grandezas cinéticas e **Stress P2P Tunnel**, que leva capacidade mecânica por túneis P2P. O **ME Gearbox** faz conversão bidirecional entre a rede e componentes rotacionais, permitindo que uma extremidade digital participe de transmissão cinética sem substituir os conceitos de speed/stress do Create.
A integração também alcança autocrafting: Pattern Providers podem acionar **Mechanical Crafting** e outros processos Create registrados, fazendo uma solicitação ME atravessar etapas digitais e físicas dentro da mesma cadeia de produção.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/03-industria-energia-e-processamento-do-create.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 3. Indústria, energia e processamento do Create

## Create: Fluid — 2.1.5

`fluid-2.1.5.jar`
**Create: Fluid** amplia a logística de fluidos do Create com dispositivos de medição, distribuição, envase e controle. A **Mechanical Pipette** manipula quantidades pequenas de líquido; componentes como **Centrifugal Pump**, Copper Tap, valves e Communicating Vessel acrescentam novas formas de mover, interromper ou equilibrar fluxos.
O addon também possui **Can Filler/Cans** para transportar fluidos em recipientes, Fluid Manifest para identificação, **Fluid Atomizer** para transformar líquidos em efeitos/processos próprios e componentes de drenagem como Gutter Outlet. Há controles de redstone e válvulas múltiplas para tornar redes hidráulicas mais programáveis.
A integração com Mechanical Arms e fluid handlers permite incluir esses componentes em linhas automatizadas. Ele continua usando a lógica de fluidos do Create como base; não cria uma rede universal separada de pipes.

## Create Crafts & Additions — 1.7.0

`createaddition-1.7.0.jar`
**Create Crafts & Additions** conecta rotação Create ao ecossistema **FE**. O **Alternator** converte potência cinética em energia; Electric/Servo Motors realizam o caminho inverso; fios e connectors distribuem FE; e o **Accumulator** oferece armazenamento elétrico integrado à estética do Create.
O addon inclui ainda **Rolling Mill** para processamento, Portable Energy Interface para transferência durante contraptions, Redstone Relay, Tesla Coil e Digital Adapter. Algumas linhas produtivas próprias trabalham com biomassa e líquidos, incluindo straw usado na alimentação de Blaze Burners.
Na build `1.7.0`, o Servo Motor e ajustes de compatibilidade com Sable fazem parte da linha NeoForge 1.21.1 instalada. O resultado é uma infraestrutura onde energia elétrica e stress rotacional podem ser convertidos conforme a máquina ou sistema conectado exige.

## Create Diesel Generators — 1.21.1-1.3.15

`createdieselgenerators-1.21.1-1.3.15.jar`
**Create Diesel Generators** adiciona uma cadeia de **petróleo bruto, refino e motores a combustível** integrada ao Create. Pumpjacks e infraestrutura de extração obtêm crude oil; etapas de processamento produzem combustíveis utilizáveis por engines e burners, transformando líquidos industriais em potência cinética.
O conteúdo também inclui diferentes motores/upgrades, Bulk Fermenters, armazenamento e transporte em oil barrels/canisters, além de materiais/processos como compression molding, casting, asphalt e cement. A linha 1.3.x também possui dispositivos industriais adicionais, como sprayers/turrets que trabalham com fluidos compatíveis.
A build instalada é exatamente `createdieselgenerators-1.21.1-1.3.15.jar`, release NeoForge publicada para 1.21.1. A string completa `1.21.1-1.3.15` continua sendo o runtime registrado no catálogo.

## Destroy — 0.4.1

`destroy-1.21.1-0.4.1.jar`
**Destroy** leva Create para **química industrial e engenharia química**. O sistema trabalha com misturas e reações, vats, destilação e separação de substâncias, fazendo composição química e condições de processo participarem diretamente da automação em vez de cada fluido ser apenas uma receita estática.
O port 1.21.1 também inclui infraestrutura como pumpjack/seismograph para depósitos de óleo, ageing barrel, centrifuge, cooler, distillation tower, dynamo/arc furnace, extrusion, glassblowing, sieve, coleta de latex e etapas de circuit lithography. A linha "Chemistry and Carnage" acrescenta explosivos, fogos, álcool e outros produtos derivados dessas cadeias.
Poluição e riscos químicos fazem parte do domínio do mod. Portanto combustíveis e petróleo são apenas uma parte do sistema; o núcleo é a transformação de substâncias por operações químicas automatizáveis.

### Petrolpark's Library — 1.5.6

`petrolpark-1.21.1-1.5.6.jar`
**Petrolpark's Library** é o framework compartilhado usado por Destroy e projetos relacionados. Centraliza infraestrutura técnica para receitas, registries, componentes e sistemas reutilizados pelo ecossistema, sem representar uma progressão tecnológica independente.
A build `1.5.6` é particularmente relevante ao ambiente atual porque inclui correções de **multipart blocks em sublevels Sable**, permitindo que estruturas dos mods consumidores se comportem corretamente quando usadas em espaços físicos móveis.

## Create: Vintage Improvements — SSW Edition — 0.0.0.7

`vintageimprovements-1.21.1-0.0.0.7.jar`
**Vintage Improvements — SSW Edition** é o port NeoForge 1.21.1/Create 6 do Vintage Improvements. Ele adiciona máquinas cinéticas e recipe types voltados à fabricação industrial de **wires, rods, sheets, springs** e outras formas intermediárias de materiais.
Entre os equipamentos estão **Compressor**, com tanque interno e diferentes modos de processo; **Vibrating Table**, inclusive com recipes de unpacking; **Centrifuge**, que trabalha com Basins; **Curving Press** com heads específicos; além de outros processos mecânicos herdados do projeto original. A edição SSW também inclui correções de overflow, resultados incorretos em servidor, laser e curving press na migração para Create 6.

## Create: Blaze Burner Fuels — 1.0.2

`create_blaze_burner_fuels-1.0.2-neoforge-1.21.1.jar`
**Create: Blaze Burner Fuels** amplia a economia térmica do Create com novas fontes para **Blaze Burners**, inclusive alternativas renováveis ao uso contínuo de Blaze Cakes. O addon adiciona itens e receitas próprias de combustível, como pellets/briquetes e outras rotas de materiais processados.
Esses combustíveis servem para estados de heating/superheating usados por mixers, basins e Steam Boiler setups. O mod, portanto, altera a forma de abastecer calor industrial sem adicionar um motor cinético ou uma rede energética paralela.

## Create Metallurgy — 1.0.3

`createmetallurgy-1.0.3-1.21.1.jar`
**Create Metallurgy** acrescenta fundição e ligas ao fluxo do Create. O **Industrial Crucible** derrete materiais em fluidos metálicos; faucets e componentes associados transferem o metal líquido para etapas de casting, permitindo produzir formas metálicas através de uma linha física de fusão e vazamento.
O sistema também aceita combinações de metais para ligas e integra crushed ores e materiais especiais às receitas de fusão. Na linha 1.0.3, correções do Industrial Crucible/Faucet e receita de crushed tungsten fazem parte da build NeoForge 1.21.1 instalada.

## Productive Metalworks — runtime 1.21.1-1.15.1

`productivemetalworks-1.21.1-1.15.1.jar`
**Productive Metalworks** é um sistema de **foundry multiblock** voltado a smelting e casting. Minérios e itens metálicos podem ser derretidos em metais líquidos, armazenados/processados dentro da fundição e depois vazados em moldes para gerar formas e produtos metálicos.
A estrutura multiblock concentra capacidade térmica, armazenamento de fluidos e operações de casting em uma instalação industrial própria. Por isso sua lógica é distinta de receitas de basin do Create: o objeto central é a foundry e o ciclo metal líquido → molde → item.

## Create: Metalwork — 2.0.0

`createmetalwork-2.0.0.jar`
**Create: Metalwork** adiciona novos **crushed ores e fluidos metálicos** para aumentar o rendimento obtido de minérios usando processamento Create. A proposta é inserir etapas adicionais entre minério bruto e ingot, aproveitando moagem, fluidos e receitas mecânicas para extrair mais metal de cada unidade coletada.
É uma expansão de ore processing, não um multiblock de foundry. A build `2.0.0` é a release NeoForge 1.21.1 instalada e inclui correções específicas de compatibilidade da linha atual.

## Create: More Automation — 0.5.2

`create_more_automation-0.5.2-neoforge-1.21.1.jar`
**Create: More Automation** amplia o número de recursos vanilla e Create que podem ser produzidos por linhas mecânicas, acrescentando receitas pensadas para deployers, basins, crushing, mixing e outros processos já existentes no Create.
A build NeoForge 1.21.1 é uma reestruturação significativa em relação à antiga linha 1.20.1: o próprio projeto informa que várias receitas foram alteradas e continuam em desenvolvimento. A versão `0.5.2`, por exemplo, reajustou receitas de Ice e Moss. Seu conteúdo é majoritariamente **recipe-driven**: a expansão vem de novas rotas de fabricação, não de uma segunda família de máquinas.

## Create: Ultimate Factory — 2.2.4

`create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`
**Create: Ultimate Factory** adiciona um conjunto curado de aproximadamente **30 receitas de automação** para recursos que normalmente exigiriam coleta, exploração ou etapas manuais. As receitas utilizam máquinas e sequências do Create para transformar esses recursos em produtos de fábrica de forma repetível.
A proposta é manter essas rotas relativamente balanceadas em custo e complexidade, fazendo a expansão ocorrer pelo desenho de linhas produtivas. Assim como More Automation, seu foco não é uma nova infraestrutura energética ou logística, mas aumentar o catálogo de coisas que uma fábrica Create consegue produzir autonomamente.

## Create: Dreams n' Desires — 2.3a-BETA

`DnDesires-1.21.1-2.3a-BETA.jar`
**Create: Dreams n' Desires** é uma expansão ampla e deliberadamente heterogênea do Create. O projeto adiciona **blocos, mecanismos, ferramentas/equipamentos, worldgen, decoração, utilidades e novas possibilidades de automação**, em vez de se concentrar em uma única cadeia como petróleo ou metalurgia.
Seu catálogo funciona como uma coleção de extensões que procuram manter a linguagem visual e funcional do Create: novos componentes podem participar de contraptions e fábricas, enquanto itens e blocos adicionais ampliam construção e exploração. Por essa natureza, o escopo é melhor entendido como uma expansão geral do ecossistema.
A publicação NeoForge 1.21.1 é classificada como Release no CurseForge, mas o próprio identificador do arquivo continua **`2.3a-BETA`**; o guia preserva essa string sem interpretar a etiqueta externa como uma mudança de versão.

## Create: Factory — 0.7b-1.21.1

`create_factory-0.7b-1.21.1.jar`
**Create: Factory** é uma expansão de **alimentos e produção culinária industrializada**. Ela adiciona ingredientes, comidas e receitas desenhadas para serem processadas com máquinas Create, transformando preparo de doces e outros alimentos em sequências de mixing, filling e demais operações mecânicas.
O foco é conteúdo consumível e suas cadeias de fabricação, não novas fontes de energia. A versão atual `0.7b-1.21.1` é release NeoForge 1.21.1 e inclui correções específicas da linha atual, como o comportamento de potions armazenadas em jars.

## Create: Integrated Farming — 1.3.3b

`create-integrated-farming-1.3.3b.jar`
**Create: Integrated Farming** acrescenta máquinas e blocos voltados à **produção agropecuária automatizada** dentro do Create. A proposta é tratar criação animal, pesca e manejo rural como processos industriais integráveis a belts, spouts e logística mecânica, não apenas automatizar receitas finais de comida.
Entre os sistemas estão **Chicken Roosts**, componentes de alimentação/processamento e mecanismos que aceitam fluidos agrícolas por tags, permitindo consumir óleos vegetais produzidos por outras cadeias industriais compatíveis. A linha atual também possui Fishing Nets e interação com mecanismos montados em sublevels Sable.
A build `1.3.3b` corrige especificamente a coleta de saída de Fishing Nets por **Simulated Auger Shafts** quando a entrada está em painéis coplanares conectados dentro de sublevels Sable. Quando Nether Depths Upgrade estiver presente, esta linha exige versão 3.2 ou posterior desse mod.

## Ratatouille — 1.4.0

`create_ratatouille-1.21.1-1.4.0.jar`
**Ratatouille** expande Create com máquinas próprias de **agricultura e processamento de alimentos**. O **Oven** é um multiblock voltado a cozinhar grandes lotes com aquecimento uniforme; o **Thresher** processa grãos como wheat e pode trabalhar com culturas compatíveis como rice e corn.
No campo, a **Irrigation Tower** mantém áreas amplas de farmland hidratadas, enquanto o **Spreader** acelera o amadurecimento de culturas em área e também pode induzir breeding em animais próximos. O projeto ainda apresenta infraestrutura como Compost Tower associada ao ciclo agrícola.
A ficha individual no Notion chama o mod simplesmente de **Ratatouille**; o guia passa a usar esse mesmo nome, preservando o JAR `create_ratatouille-1.21.1-1.4.0.jar`.

## Create: Food — 2.7.1

`createfood-neoforge-1.21.1-2.7.1.jar`
**Create: Food** é uma expansão culinária de grande porte baseada em Create, com **mais de mil itens relacionados a comida e mais de cem fluidos** no catálogo atual. Ingredientes, alimentos, bebidas e etapas intermediárias são estruturados para participar de processos mecânicos e cadeias automatizadas.
A versão 2.7.1 tornou o conteúdo de compatibilidade cross-mod amplamente **config-driven**: blocos, display blocks, fluidos, itens, efeitos e tooltips podem ser controlados por listas de configuração. Farmer's Delight e outros addons culinários ampliam o conteúdo disponível, enquanto Create permanece a base tecnológica obrigatória.

## Create: Garnished — 2.1.9.2

`garnished-2.1.9.2+1.21.1-neoforged.jar`
**Create: Garnished** adiciona uma cadeia culinária própria centrada principalmente em **nuts/nozes**, novos ingredientes e alimentos processáveis com o maquinário Create. As matérias-primas entram em receitas mecânicas e podem compor linhas automatizadas de produção alimentar.
O addon também detecta integrações com outros mods quando presentes. A build `2.1.9.2` é a release NeoForge 1.21.1 instalada.

## Create: Central Kitchen — 2.6.0

`create-central-kitchen-2.6.0.jar`
**Create: Central Kitchen** é uma camada de **automação culinária e compatibilidade** para Farmer's Delight e seus addons. Ele adapta ingredientes, recipientes e processos de cozinha para que possam ser executados ou movimentados por máquinas do Create, conectando cooking recipes ao mesmo fluxo de mixing, filling e logística usado por outras linhas de fábrica.
O addon é extensível e inclui integrações condicionais com outros mods culinários detectados. Por isso atua como uma central de compatibilidade: cada integração traduz conteúdo externo para operações Create, em vez de adicionar uma culinária independente.
A versão `2.6.0` foi publicada especificamente para **Create 1.21.1-6.0.10**, que é exatamente a versão do Create presente no pack.

## Create: Arm-made Cuisine — 1.0.0

`create_cuisine-1.0.0-mc1.21.1-neoforge.jar`
**Create: Arm-made Cuisine** integra o Mechanical Arm à **Cuisine Skillet** do Cuisine Delight. Um braço pode colocar alimentos ou pacotes contendo ingredientes na skillet aquecida, fazendo a etapa de abastecimento da receita funcionar como parte de uma linha Create.
Quando segura uma **spatula**, o Mechanical Arm executa continuamente o stir-fry enquanto houver alimento na skillet. Com um **plate**, ele consegue retirar a preparação pronta e entregá-la ao próximo ponto configurado, automatizando também o empratamento/saída.
O addon inclui um Ponder específico para explicar a interação com a Cuisine Skillet. Assim, sua função é bastante delimitada: automatiza ações que normalmente exigiriam interação manual do jogador dentro do sistema do Cuisine Delight.

## Create: Fishery Industry — 5.1.1

`createfisheryindustry-5.1.1.jar`
**Create: Fishery Industry** transforma atividades aquáticas em um conjunto de **pesca automática, mergulho, captura de criaturas, processamento de alimentos e logística** no estilo Create. Equipamentos próprios permitem obter recursos da água sem depender exclusivamente da fishing rod manual e encaminhá-los para processamento mecânico.
O escopo inclui exploração subaquática e captura, além da transformação e transporte dos produtos obtidos. Dessa forma, a cadeia começa na aquisição automatizada de recursos aquáticos e termina em armazenamento/processamento dentro da fábrica Create.
A build `5.1.1` é a release NeoForge 1.21.1 atual da linha 5.x instalada.

## Create Aquatic Ambitions — 2.0.4

`create_aquatic_ambitions-1.21.1-2.0.4.jar`
**Create Aquatic Ambitions** adiciona **bulk processing** voltado a recursos aquáticos e costeiros. A proposta é fazer prismarine, coral, cobre e materiais relacionados entrarem em cadeias mecânicas reproduzíveis, reduzindo dependência de coleta manual ou farms específicas para cada recurso.
As receitas utilizam a infraestrutura de processamento do Create em escala, convertendo materiais e condições aquáticas em rotas de produção contínua. O addon também inclui itens/equipamentos associados ao tema oceânico, mas sua função central é ampliar o conjunto de recursos renováveis ou processáveis industrialmente.

## Create: Deep Dark — 3.0.2

`create_deep_dark-3.0.2-neoforge-1.21.1.jar`
**Create: Deep Dark** adiciona uma camada de **endgame baseada no Deep Dark**. A exploração de Ancient Cities e território do Warden fornece recursos raros que entram em receitas e equipamentos processados pelas mecânicas do Create.
O addon acrescenta materiais, itens e gear de alto nível cuja fabricação utiliza máquinas/etapas Create, ligando exploração perigosa a uma progressão tecnológica posterior. Assim, o Deep Dark deixa de ser apenas uma fonte de loot vanilla e passa a alimentar novas cadeias de crafting industrial.

## Create: Recycle Everything Continued — runtime 1.1

`create_recycle_everything-2.1.0.jar`
**Create Recycle Everything (Continued)** cria uma extensa tabela de **receitas de desmontagem/reciclagem**, principalmente usando Crushing Wheels, para devolver matérias-primas a partir de itens já fabricados. A versão 2.1.0 acrescenta reciclagem de várias peças de armadura e amplia cobertura de itens vanilla, Create e integrações condicionais.
O fluxo é deliberadamente parcial: um item processado retorna componentes ou recursos aproveitáveis, permitindo reinserir gear e blocos obsoletos na fábrica em vez de simplesmente descartá-los. Algumas receitas são habilitadas apenas quando mods integrados estão presentes.
A identidade continua dupla: o filename/publicação é `create_recycle_everything-2.1.0.jar`, mas o metadata runtime local declara `1.1`; o guia preserva ambos.

## Create: Rock & Stone — 1.3.1-1.21.1-6

`create_rns-1.3.1-1.21.1-6.jar`
**Create: Rock & Stone** adiciona **depósitos subterrâneos próprios** que representam reservas minerais exploráveis por maquinário Create. O **Deposit Scanner** localiza essas reservas; **Miner Bearing** e **Mine Head** formam a infraestrutura usada para extrair recursos continuamente do depósito.
Os depósitos podem ser finitos ou configurados para comportamento diferente conforme o pack, e sua composição pode incluir materiais vanilla ou de outros mods. A definição é data-driven, com suporte a datapacks/KubeJS, permitindo criar novos tipos de depósito sem adicionar outro sistema de mineração.
Integrações com Jade e JourneyMap ajudam a identificar e acompanhar as reservas detectadas, tornando prospecção e extração partes distintas da mesma cadeia.

## Create Cobblestone — 1.4.12+neoforge-1.21.1-144

`createcobblestone-1.4.12+neoforge-1.21.1-144.jar`
**Create Cobblestone** substitui geradores vanilla de água/lava por um **bloco gerador movido a Stress Units**. A produção de cobblestone passa a depender da rede cinética, podendo ser ligada, escalada e posicionada como outra máquina Create sem manter fluidos atualizando blocos continuamente.
A proposta também é reduzir problemas de desempenho e construção causados por geradores convencionais dentro de contraptions ou fábricas densas. O recurso gerado pode alimentar automaticamente crushing, washing e cadeias posteriores de materiais de construção.
O runtime é preservado integralmente como `1.4.12+neoforge-1.21.1-144`.

## Create: Mechanical Spawner — runtime 1.3.1-6.0.10

`create_mechanical_spawner-1.21.1-1.3.2-6.0.10.jar`
**Create: Mechanical Spawner** adiciona um spawner acionado por **potência rotacional**. Em vez de depender de um cage vanilla encontrado no mundo, o bloco utiliza stress/speed e recursos configurados para produzir mobs dentro de uma linha Create.
O sistema suporta **Random Spawn Fluid**, cuja seleção pode depender do bioma, e fluidos específicos associados a criaturas. Configurações controlam consumo de stress, velocidade, alcance e condições de spawn, permitindo que custo e produtividade façam parte da engenharia da fábrica.
A publicação/filename da build é `1.3.2-6.0.10`, enquanto o metadata local declara `1.3.1-6.0.10`; essa divergência permanece registrada sem alterar o JAR.

## Create Confectionery — runtime 1.1.3.

`create-confectionery1.21.1_v1.1.3b.jar`
**Create Confectionery** transforma confeitaria em uma cadeia produtiva ligada ao Create. A matéria-prima de cacau passa por etapas próprias para gerar **Crushed Cocoa, Cocoa Butter e Cocoa Powder**, que alimentam variedades como chocolate branco, preto e ruby, além de caramel e produtos derivados.
O catálogo inclui gingerbread, marshmallows, chocolate candies, honey candy, hot chocolate e Full Chocolate Bars, além de versões glaceadas de alimentos. Alguns produtos possuem efeitos próprios: a documentação cita **Stimulation**, Rest, Saturation e Speed, enquanto Hot Chocolate concede regeneração. A linha 1.21.1 também inclui Candy Cane Tools e receitas reversíveis para converter determinados blocos/barras de chocolate novamente em formas utilizáveis.
A build instalada é `create-confectionery1.21.1_v1.1.3b.jar`; o metadata runtime local declara literalmente `1.1.3.`. A release `v1.1.3b` é a atual para NeoForge 1.21.1 e inclui hotfix da receita de chocolate-glazed marshmallow.

## Create Slice & Dice — 4.3.3

`sliceanddice-4.3.3-neoforge.jar`
**Create Slice & Dice** integra principalmente Farmer's Delight ao Create por automação de preparação e cozinha. O **Slicer** registra automaticamente receitas de Cutting Board e executa o corte usando a ferramenta instalada na própria máquina; knives e axes são permitidos por padrão, e a tag `sliceanddice:allowed_tools` permite ampliar esse conjunto.
Receitas do **Cooking Pot** do Farmer's Delight são traduzidas para **heated mixing**, permitindo executar preparação culinária em linhas mecânicas. O addon também possui **Sprinkler**, alimentado por fluidos via pipes: água simula chuva na área, lava causa dano de fogo, poções aplicam seus efeitos e Liquid Fertilizer produz efeito de bonemeal sobre blocos/culturas.
A build `4.3.3` é a release NeoForge 1.21.1 instalada; ela corrige o comportamento de potion sprinkler e expõe `SprinklerProvider` para integrações.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/04-logistica-e-fabricas-conectadas.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 4. Logística e fábricas conectadas

## Create Contraption Terminals — 1.4.0

`createcontraptionterminals-1.21-1.4.0.jar`
**Create Contraption Terminals** faz os terminais do **Tom's Simple Storage** continuarem funcionais depois que a estrutura é montada como contraption Create. Um terminal colocado na construção consegue acessar o **inventário inteiro da contraption assemblada**, preservando a experiência de storage centralizado durante movimento.
Isso permite usar trens e outras estruturas móveis como depósitos acessíveis por terminal em vez de abrir cada container individualmente. Contraptions montadas antes da instalação do addon precisam ser remontadas para que os terminais sejam registrados corretamente.

## Create: Ender Transmission — 2.1.1-1.21.1

`createendertransmission-2.1.1-1.21.1.jar`
**Create: Ender Transmission** adiciona links de longa distância para **itens, fluidos e energia**, incluindo conexões entre dimensões. Os endpoints formam pares/canais que transferem recursos sem exigir uma linha física contínua de belts, pipes ou cabos atravessando o trajeto.
O addon também possui **chunk loader alimentado por potência cinética**, permitindo manter pontos de uma infraestrutura remota ativos como parte da rede Create. Assim, seu escopo combina teletransporte de recursos e manutenção operacional de instalações distantes.
A publicação usa `v2.1.1`, enquanto o runtime local registra `2.1.1-1.21.1`; o guia mantém a string completa instalada.

## Create: Mobile Packages — 0.7.7

`create_mobile_packages-1.21.1-0.7.7.jar`
**Create: Mobile Packages** cria uma rede logística de entregas baseada em **Bee Ports e Robo Bees**. Ports são vinculados a uma Logistics Network e enviam packages para outros ports ou diretamente para jogadores pertencentes à mesma rede; os Robo Bees funcionam como couriers físicos que carregam as encomendas.
O **Portable Stock Ticker** permite consultar remotamente um Stock Network, pedir itens, pesquisar com JEI e enviar itens do próprio inventário para um endereço. O **Mobile Packager** cria ou edita packages de até nove stacks fora de uma instalação fixa.
Bee Ports também podem puxar packages de inventários adjacentes ou, com redstone, descarregar para eles. O resultado é uma camada de entrega móvel sobre o sistema de packages/stock do Create, com destinatários, redes e couriers persistentes.

## Create Stock Bridge — 0.2.0

`createstockbridge-1.21.1-0.2.0.jar`
**Create Stock Bridge** liga o **Create Stock Network** ao Applied Energistics 2 por um AE Stock Bridge. Itens presentes no estoque Create são expostos ao AE2 como itens solicitáveis/craftable, permitindo que a interface ME enxergue a disponibilidade logística do lado Create.
No sentido inverso, um **Packager conectado ao bridge** pode solicitar itens armazenados na ME Network para atender pedidos do sistema Create. A versão 0.2.0 também corrige cenários de envio parcial, Create Promises, Re-Packager e prioridade controlada por redstone.
Essa integração trabalha especificamente com stock requests/packages; ela não substitui Applied Create, que conecta AE2 a stress e processamento cinético.

## Create: Transmission! — runtime 1.2.2+neoforge-create6-1.21.1

`createtransmission-1.2.2+neoforge-create6-1.21.1.jar`
**Create: Transmission!** adiciona um único componente mecânico principal: a **Transmission Chain**. Ela transmite rotação ao longo de uma linha e pode alimentar belts de forma compacta, evitando montar Encased Chain Drives apenas para levar potência a trechos logísticos.
O bloco existe como alternativa de layout: mantém a lógica cinética do Create, mas oferece outra geometria para distribuição de rotação em fábricas onde espaço e leitura visual importam. A build instalada preserva o runtime completo `1.2.2+neoforge-create6-1.21.1`.

## Create: Filters Anywhere — 2.6.0

`createfiltersanywhere-1.21.1-2.6.0.jar`
**Create: Filters Anywhere** leva **List Filters e Attribute Filters** do Create para inventários, slots de filtro e máquinas de outros mods. Em vez de cada sistema externo depender apenas de sua própria sintaxe de whitelist/blacklist, interfaces compatíveis passam a aceitar filtros Create e os atributos que eles representam.
A build do pack possui integrações específicas com **AE2, Tom's Simple Storage, Sophisticated Core, Refined Storage, Oritech, Modular Routers e LaserIO**, entre outras. O addon também adiciona atributos e QoL aos próprios filtros, tornando-os uma linguagem de seleção compartilhada entre diferentes sistemas logísticos.

## Create: Pattern Schematics — 2.0.10

`create_pattern_schematics-2.0.10.jar`
**Create: Pattern Schematics** adiciona schematics pensados para **repetição automática de um padrão**. Em vez de copiar manualmente a mesma seção várias vezes, o padrão pode ser aplicado ao longo de contraptions, trains, gantries e outros eixos de movimento para construir sequências repetitivas.
Isso transforma módulos como trechos de ponte, parede, via ou estrutura industrial em unidades replicáveis. A lógica é diferente do Schematicannon comum: o foco não é apenas imprimir uma área salva, mas definir como a unidade se repete ao longo de um percurso.

## Create: Fast Schematic Cannon — runtime 1.4.1-neoforge

`CreateFastSchematicCannon-1.4.1-neoforge-1.21.1.jar`
**Create: Fast SchematicCannon** torna a velocidade de impressão do cannon configurável. O parâmetro de **prints per tick** permite executar múltiplas colocações em cada tick de trabalho, multiplicando a taxa de construção sobre o delay normal do Create.
O addon também permite configurar uma lista de **blocos que o Schematicannon não pode imprimir/quebrar**, protegendo componentes específicos durante construção automática e evitando casos como o chamado void-boiler issue. Portanto ele atua tanto em throughput quanto em regras de segurança de impressão.
O runtime local é `1.4.1-neoforge`; a publicação externa abrevia a release para `v1.4.1`.

## Create: Schematic Checker — runtime 2.27.45-6.0

`createschematicchecker-2.27.45-6.0-neoforge-1.21.1.jar`
**Create: Schematic Checker** é uma camada server-side de **proteção e sanitização de schematics**. Ao receber um schematic enviado pelo cliente, ele examina NBT e outros dados antes da impressão, bloqueando ou corrigindo padrões conhecidos que poderiam causar crashes, lag, duplicação ou inserir conteúdo malicioso no mundo.
O mod também possui reparos específicos para dados de blocos e addons usados em schematics. A release `2.27.45` para Create 6.0 inclui ajustes recentes envolvendo Copycats e Quark, além da manutenção contínua das regras de validação.
`TorqueAPI 1.2.2` vem incorporada dentro desse JAR como dependência interna e não corresponde a uma entrada top-level separada da modlist.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/05-fisica-veiculos-e-create-aeronautics.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 5. Física, veículos e Create Aeronautics

## Create: Aeroworks — 1.5.0

`aeroworks-1.5.0.jar`
**Create: Aeroworks** adiciona uma camada de **aviônica e controle** sobre contraptions físicas do Aeronautics. Joysticks, levers, pedals, yokes, throttle quadrants, steering wheels e desks fornecem input do piloto; **gyroscopes** ajudam a medir e estabilizar orientação; e servos convertem sinais de controle em saídas rotacionais capazes de acionar partes móveis do veículo.
O sistema modular permite construir cockpits por canais de entrada e saída, em vez de depender de um bloco único de direção. A versão `1.5.0` amplia essa linguagem com **Control Stand e Copycat Control Stand**, variantes de cobre dos módulos, steering wheels de cobre e nas 16 cores, um terceiro socket de pedal e compatibilidade **Drive-By-Sable**. Assim, pitch, yaw, roll, direção e superfícies móveis podem ser organizados como uma rede física de comandos dentro do veículo.

## Create: Radars — 0.4.9.4-1.21.1

`create_radar-0.4.9.4-1.21.1.jar`
**Create: Radars** fornece infraestrutura de **surveillance, detection, tracking e weapon control**. Blocos de radar detectam alvos no espaço ao redor, mantêm informações de posição/movimento e disponibilizam esses dados para displays e sistemas de controle compatíveis.
A informação de rastreamento pode alimentar mecanismos de mira/armamento em integrações externas. Assim, o mod-base trata de detectar e representar alvos; addons como Create Aero Radar usam esses dados dentro de veículos físicos e sistemas de armas.

## Create: Radiologistics — 1.1.1 beta

`CreateRadiologistics-1.1.1.jar`
**Create: Radiologistics** adiciona comunicação **wireless** e um sistema de **lógica programável por nós** para máquinas e contraptions Create. Sensores coletam estados, nós processam/encaminham informações e dispositivos de saída transformam esses sinais em ações mecânicas.
Entre os componentes da linha atual estão sensores, servo motor, ferramentas de configuração e nós usados para construir circuitos de controle distribuídos sem passar redstone física por toda a estrutura. Isso é especialmente útil em contraptions móveis, onde controle e telemetria podem viajar por rádio.
A build `1.1.1` permanece beta NeoForge 1.21.1; a classificação descreve maturidade do projeto, enquanto o JAR/runtime estão confirmados.

## Create Aero Radar — 0.1.1-1.21.1

`create_aero_radar-0.1.1-1.21.1.jar`
**Create Aero Radar** conecta **Create Aeronautics, Create: Radars e Create Big Cannons**. Ele traduz dados de detecção/rastreamento do radar para o espaço físico da contraption, permitindo que armamentos montados em aeronaves ou outros veículos utilizem informação coerente de alvo enquanto toda a estrutura se move e gira.
A integração cobre orientação, mira e convergência de armas sobre contraptions físicas. O radar continua sendo fornecido pelo mod Create: Radars e o armamento por Big Cannons; este addon é a ponte espacial/veicular entre esses sistemas.

## Create Aeronautics: Automated Logistics — 0.6.2

`create_aeronautics_automated_logistics-0.6.2.jar`
**Create Aeronautics: Automated Logistics** permite gravar e repetir **rotas de airships**, transformando veículos Aeronautics em transportadores autônomos. O jogador constrói uma rede de estações, registra o percurso e configura pontos de docking; depois o veículo pode repetir esse trajeto sem pilotagem manual contínua.
A automação é pensada para continuar funcionando mesmo quando ninguém está próximo, permitindo linhas logísticas persistentes entre bases. O projeto também mantém compatibilidade experimental com trens Simurail, mas o núcleo da build `0.6.2` são rotas, estações e docking automatizado de airships Aeronautics.

## Create Aeronautics: FTB Chunks — 1.1.1

`create_aeronautics_ftb_chunks-1.21.1-NeoForge-1.1.1.jar`
**Create Aeronautics: FTB Chunks** faz ships/sublevels Sable participarem das regras territoriais do **FTB Chunks e FTB Teams**. Um **Contraption Claim Block** colocado no veículo abre uma interface própria para associar a contraption ao time do jogador.
Pela interface é possível **claim/unclaim os chunks ocupados pelo ship** e ativar force-loading para mantê-los carregados quando jogadores estiverem distantes. Assim, proteção, permissões de equipe e persistência de carregamento acompanham a estrutura física em vez de só existir no terreno estático do level principal.

## Create Aeronautics: Transmission & Linkage — 0.2.7

`create_aeronautics_transmission_linkage-0.2.7.jar`
**Create Aeronautics: Transmission & Linkage** adiciona componentes para transmitir **movimento cinético e deslocamento mecânico entre partes físicas articuladas**. Universal joints permitem conectar eixos que não permanecem perfeitamente alinhados; kinetic converters adaptam transferência entre contextos diferentes; e hydraulic rods introduzem extensão/retração controlada.
Essas peças tornam possível construir mecanismos articulados dentro ou entre physics contraptions, como juntas, braços, superfícies móveis e acoplamentos cuja geometria muda durante o funcionamento. A versão 0.2.7 é a release NeoForge 1.21.1 atual dessa linha.

## Create Aeronautics: Toolgun — 0.3.6

`create_aeronautics_toolgun-0.3.6.jar`
**Create Aeronautics: Toolgun** introduz um workflow de engenharia inspirado em toolguns para manipular contraptions físicas. O sistema inclui **blueprints** para salvar e imprimir crafts físicos e ferramentas para apagar, selecionar ou modificar estruturas sem desmontá-las manualmente bloco a bloco.
A linha atual também possui **magnetic field guns** voltadas a mover/manipular objetos físicos. O objetivo é fornecer ferramentas de edição adequadas à escala de ships e veículos Aeronautics, onde operações de construção convencionais se tornam pouco práticas.

## Create Aeronautics: Throwable Rope Connector — 0.4.3

`create_aeronautics_throwable_rope_connector-0.4.3.jar`
**Throwable Rope Connector** transforma rope connectors do Aeronautics em uma ferramenta prática de **docking e conexão à distância**. O jogador pode lançar a conexão em vez de precisar posicionar os dois pontos manualmente lado a lado, permitindo prender ship↔dock ou ship↔ship durante manobras.
A versão `0.4.3` inclui correção específica para a mira do **Mounted Rope Launcher em contraptions Sable**, especialmente em disparos de um veículo físico contra outro. O addon atua sobre vínculos/docking; não é o mesmo sistema de escalada de Climbable Ropes.

## Create Aeronautics: Copycat Wing — 1.0.2

`CreateAeronauticsCopycatWing-1.21.1-1.0.2.jar`
**Create Aeronautics: Copycat Wing** registra blocos do **Copycats+** como partes válidas de asa para o modelo aerodinâmico do Aeronautics. Isso permite construir superfícies de lift com formatos e materiais visuais de Copycat sem perder a função física de wing.
A função é especificamente aerodinâmica. `aerocopycats`, presente separadamente no stack, trata propriedades físicas/massa de blocos Copycat em Sable; Copycat Wing trata reconhecimento desses blocos como superfícies que geram sustentação.

## Create Aeronautics x Curios API Compat — runtime 2.0

`createaeronauticscurios-neoforge-1.21.1-2.2.jar`
**Create Aeronautics x Curios API Compat** faz equipamentos utilitários do Aeronautics funcionarem quando equipados em slots **Curios**, em vez de exigir ocupação dos slots vanilla correspondentes. Os principais casos documentados são **Aviator Goggles** e **Linked Typewriter**.
A integração preserva o comportamento dos goggles enquanto equipados e permite ativação/uso remoto do Linked Typewriter pelo slot Curios. O filename/publicação é `2.2`, mas o metadata runtime carregado declara `2.0`; o catálogo mantém as duas strings.

## Create Aeronautics: Harness — 1.0.1

`CreateAeronauticsHarness-1.21.1-1.0.1.jar`
**Create Aeronautics: Harness** permite anexar e **carregar objetos físicos/contraptions Aeronautics nas costas do jogador**. O objeto transportado permanece associado ao harness, criando uma forma portátil de movimentar componentes físicos pequenos sem pilotá-los ou desmontá-los.
Há integração com **Create Big Cannons**, permitindo configurações transportáveis envolvendo canhões/objetos compatíveis. A função é transporte de physics objects pelo jogador; não é um sistema de cinto de segurança ou fixação do jogador ao veículo.

## Create Deep Seas — 2.2.4

`create_submarine-2.2.4.jar`
**Create Deep Seas** estende o modelo de veículos construídos com blocos do Aeronautics/Sable para **barcos e submarinos**. A embarcação é tratada como uma contraption física na qual o jogador pode instalar mecanismos e navegar, em vez de uma entidade de barco com inventário fixo.
O domínio acrescentado é o movimento e a operação **sobre e sob a água**, permitindo projetos submersíveis construídos na mesma lógica modular dos airships. O JAR `2.2.4` é a release NeoForge 1.21.1 instalada e publicada para a linha atual.

## Create: Deep Seas - Lava Fix — 1.0.1

`submarinefix-1.0.1.jar`
**Create: Deep Seas - Lava Fix** corrige um caso específico do sistema de submarinos: mesmo após um compartimento selado remover corretamente a lava de seu interior, o jogador ainda podia receber dano de lava/fogo e manter o overlay de chamas porque as verificações vanilla não reconheciam o estado do interior do submarino.
O patch suprime esses danos e efeitos quando o jogador está realmente protegido pela estrutura selada. Ele cobre situações dentro do compartimento, em **hatches/doorways**, sentado em Create Seats, usando ladders e durante entrada/saída na superfície da lava; também corrige fire ticks persistentes depois de um rompimento do casco seguido de nova selagem.
O mod não adiciona submarinos nem altera a física principal de Deep Seas. Sem o mod-alvo ele simplesmente não executa sua correção. O JAR `submarinefix-1.0.1.jar` é a release NeoForge 1.21.1 instalada.

## Create Aeronautics: Portable Engine Liquid Fuel — 2.0.0

`portable_engine_liquid_fuel-2.0.0-neoforge-1.21.1.jar`
**Create Aeronautics: Portable Engine Liquid Fuel** adapta Portable Engines do ecossistema Aeronautics para consumir **combustíveis líquidos**. Em vez de limitar a propulsão portátil aos recursos originais do componente, engines podem ser abastecidos a partir de fluidos de combustível fornecidos por cadeias industriais compatíveis.
Isso conecta diretamente veículos às infraestruturas de tanks, pumps e refino do pack: o combustível pode ser produzido em fábrica, armazenado como fluido e então utilizado a bordo. O addon é uma bridge de fuel handling, não um motor físico separado.

## Create Propulsion: Simulated — 1.1.5

`createpropulsion-1.1.5.jar`
**Create Propulsion: Simulated** adiciona thrusters que aplicam **força e torque reais em pontos da contraption Sable/Aeronautics**. A linha inclui thrusters convencionais a combustível, **Ion Thrusters alimentados por FE**, Creative Thrusters e, na versão 1.1.5, **Solid Fuel Thruster** com combustíveis definidos por dados.
**Tilt Adapters** e a versão avançada permitem alterar direção/orientação do empuxo, tornando possível construir mecanismos de vectoring em vez de manter todos os motores fixos. A propulsão interage diretamente com massa e orientação do physics object, de modo que posição do thruster influencia o comportamento do veículo.
A 1.1.5 também inclui otimizações de sincronização/renderização e amplia a configuração data-driven de combustíveis.

## Create Aeronautics: Gadgets & Gizmos — 1.1.3

`createthrusters-bundled-V1.1.3.jar`
**Create Aeronautics: Gadgets & Gizmos** é uma expansão ampla de componentes para contraptions físicas. A linha inclui vários tipos de **thrusters**, Contraption Controller, Industrial Alternator/Motor, Smart Gearbox, Variable Transmission, servo/Vector/Aileron Bearings, analogue joystick, Bi-Directional Gearshift e componentes de movimentação como Claw, Physics Gantry e Powered Zipline.
O addon também acrescenta infraestrutura de bordo: Advanced Data Link, cabos, Fuel Oxidizer, Entity Launcher, Shipping Manifest e ferramentas como Physics Staff/Goggles, Network Linker e Configuration Clipboard. Há upgrades específicos para propulsion, smelting, smoking e haunting.
Vários dispositivos expõem integração com **CC:Tweaked** como peripherals, permitindo ler ou comandar thrusters, bearings, gearboxes, joysticks e controllers por computador. O metadata runtime usa `Create Gadgets & Gizmos`, enquanto o projeto público mantém o nome `Create Aeronautics: Gadgets & Gizmos`; o JAR atual é o bundle `1.1.3`.

## AeroStar — 1.0.1

`AeroStar-1.0.1.jar`
**AeroStar** é a compatibilidade entre **Create Aeronautics e Create: Northstar Redux** voltada a viagens espaciais com physics ships. O **Dimensional Drive** permite transferir uma contraption Aeronautics entre planetas/órbitas, preservando a estrutura física e o estado necessário para continuar operando depois da mudança de dimensão.
A integração procura manter componentes, assentos e dados do ship durante a transferência em vez de reconstruir a nave como uma entidade nova. Também acrescenta recursos voltados a **space stations**, fazendo uma nave construída com blocos participar do sistema planetário/orbital do Northstar.
O projeto é continuação corrigida e expandida de uma compatibilidade anterior entre Northstar e Aeronautics; a build `1.0.1` é a release instalada no pack.

## Climbable Ropes for Create Aeronautics — 2.1.1

`climbable_ropes-2.1.1.jar`
**Climbable Ropes for Create Aeronautics** torna cordas verticais do stack Aeronautics/Simulated realmente escaláveis pelo jogador, inclusive as cordas usadas pelo **Plunger Launcher**. Com as mãos vazias, o jogador pode agarrar a corda, usar W/S para subir ou descer, Space para saltar para fora e Sneak para soltá-la.
O movimento inclui animações próprias de escalada e comportamento de mantle/salto associado à corda. A versão `2.1.1` corrige casos em que a animação podia permanecer incorreta depois de respawn ou troca de dimensão e ajusta poses/renderização durante cancelamento do estado de climbing.
O JAR também possui dependências internas usadas pela animação, mas isso não altera a presença dos JARs top-level atuais do pack.

## Aeronautics Camera Sync — 1.3.6

`aero_cam_sync-1.3.6.jar`
**Aeronautics Camera Sync** sincroniza a orientação da câmera do jogador com a **rotação dinâmica da contraption física** onde ele está. Sem essa camada, o veículo pode inclinar, rolar ou girar enquanto a câmera continua orientada principalmente pelo frame do mundo, produzindo sensação visual desconectada do movimento.
O addon aplica a transformação do ship/sublevel à visão do jogador para que cockpit, interior e horizonte respondam de forma coerente durante pitch, yaw e roll. Sua função é estritamente de apresentação/câmera sobre a física existente; ele não altera forças ou controles do veículo.

## Create Aeronautics: Weight — 1.2.0

`weight-1.2.0.jar`
**Create Aeronautics: Weight** acrescenta massa dinâmica a elementos que normalmente não contribuem integralmente para a física de uma contraption. Jogadores, mobs, inventários, containers, fluid tanks, armaduras e carga podem aumentar o peso da estrutura Sable conforme configurações e mappings próprios.
Saltos e aterrissagens também aplicam força ao deck proporcionalmente à intensidade. As fontes de massa de inventário, conteúdo de containers, fluidos, armaduras e mob gear são configuráveis separadamente e os mappings podem ser recarregados por JSON/comandos. Não é um sistema de encumbrance: o peso é aplicado à **simulação física da contraption**.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/06-sable-e-infraestrutura-de-sublevels.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6. Sable e infraestrutura de sublevels

## Dimensional Sable — 1.0.5

`dimensional_sable-1.0.5.jar`
**Dimensional Sable** fornece a infraestrutura para **teleportar physics objects do Sable entre dimensões**. Em vez de mover apenas jogadores ou entidades vanilla, a operação transfere o objeto físico/sublevel correspondente, permitindo que contraptions compatíveis continuem existindo após a mudança dimensional.
Create Aeronautics é um dos principais consumidores desse comportamento. A versão `1.0.5` foi atualizada para a linha Sable 2.0.3+ e funciona como API/ferramenta de transporte interdimensional de objetos físicos, não como um teleporter geral de gameplay para qualquer bloco.

## Sable Beyond — 0.5.0

`sablebeyond-neoforge-1.21.1-v0.5.0.jar`
**Sable Beyond** amplia a quantidade de mecânicas vanilla/Create que continuam funcionando de forma física entre o mundo principal e sublevels. **Mechanical Arms** podem interagir entre espaços; Encased Fans podem empurrar/puxar sublevels; e Water Wheels podem gerar força de propulsão em situações compatíveis.
O addon também introduz **massa dinâmica**: containers, tanks, spouts, drains e basins podem alterar a massa do objeto físico conforme o conteúdo armazenado. Fórmulas e fatores de massa de entidades são configuráveis.
Fogo pode se propagar entre sublevel e mundo principal, lava pode causar ignição, fluidos correntes podem aplicar força à estrutura e basins podem interagir com líquidos externos. Várias dessas extensões são configuráveis e algumas ficam desabilitadas por padrão, permitindo modular quanto da simulação adicional é aplicada.

## Sable: Physics Compat — 1.3.0

`sablephysicscompat-1.3.0.jar`
**Sable: Physics Compat** fornece tags e parâmetros físicos para **blocos adicionados por outros mods**, fazendo-os participar corretamente de massa, flutuação, fricção, elasticidade, airtightness e outras propriedades usadas pelo Sable.
A versão `1.3.0` amplia cobertura para Architect's Palette e diversas famílias Macaw's, além de Storage Drawers. Também expande propriedades para blocos de mods como Supplementaries, Aether, Quark, Ice and Fire e Cataclysm, entre outros.
Sem essa camada, muitos blocos modded ainda poderiam ser montados, mas tenderiam a usar defaults genéricos ou propriedades incompletas. O addon funciona como uma base de dados física de compatibilidade, não como um novo solver.

## Sable: True Impact — 0.5.7-delta

`true_impact-0.5.7-delta.jar`
**Sable: True Impact** transforma colisões de objetos físicos e veículos Sable/Create Aeronautics em **eventos de impacto com dano e destruição**. A energia da colisão pode ser convertida em dano a entidades e estruturas, além de provocar cracking/fratura e destruição de terreno conforme a intensidade e as regras configuradas.
Isso acrescenta consequências físicas às contraptions: massa, velocidade e impacto deixam de ser apenas movimento visual e passam a interferir no mundo. A build instalada `0.5.7-delta` é uma beta NeoForge 1.21.1; o caráter experimental e destrutivo descreve a mecânica/maturidade, não incerteza sobre o JAR atual.

## Sable Create Addition Compat — 0.1.13

`sable_createaddition_compat-0.1.13.jar`
**Sable Create Addition Compat** adapta componentes de **Create Crafts & Additions** aos sublevels físicos. Fios podem conectar mundo principal↔sublevel ou sublevel↔sublevel, com renderização e transformação de endpoints ajustadas ao movimento; conexões também podem romper quando o deslocamento excede a condição suportada.
A compatibilidade inclui renderização e comportamento de block entities como **Accumulator, Electric/Servo Motor, Portable Energy Interface, Rolling Mill e Liquid Blaze Burner**, para que continuem aparecendo e operando corretamente quando a estrutura se move.
A versão `0.1.13` removeu um pin rígido de versão e atualizou suporte a Sable mais recente. A release é beta, mas o escopo funcional da bridge está definido.

## Sable: Stuff&Additions Compatibility — 1.0.3

`SableStuffAdditionsCompat v1.0.3-1.21.1.jar`
**Sable: Stuff&Additions Compatibility** adapta o conteúdo de **Create: Stuff & Additions** para funcionar dentro de Sable/Simulated/Aeronautics. A bridge corrige comportamentos de itens, componentes e block entities que originalmente assumem coordenadas e contexto do level principal.
Seu objetivo é permitir que o equipamento tecnológico de Stuff & Additions continue funcional em physics contraptions sem criar versões alternativas desses itens. A build `1.0.3` é a release NeoForge 1.21.1 instalada.

## VS / Sable Hose Connectors — 0.1.8

`VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`
**VS / Sable Hose Connectors** fornece conexões de transporte entre estruturas físicas e o mundo, com suporte tanto a Valkyrien Skies quanto a **Aeronautics/Sable**. No ramo Sable, hose connectors mantêm fluxos através da fronteira entre um sublevel móvel e a infraestrutura externa.
A versão 0.1.8 também permite rotacionar Hose Connectors e Electric Wire Connectors. A bridge resolve transferência entre veículos/ships e instalações fixas; não cria um sistema de armazenamento independente.

## Sable Dynamic Lights — 2.0.1

`sable-dynamic-lights-1.21.1-2.0.1.jar`
**Sable Dynamic Lights** é um fork/integração de iluminação dinâmica voltado a **Create contraptions e sublevels Sable**. Fontes de luz transportadas por estruturas móveis continuam iluminando o ambiente a partir de sua posição física real, em vez de ficarem visualmente presas às coordenadas originais do level principal.
Isso cobre contraptions, veículos e outros espaços móveis que normalmente não são compreendidos por sistemas de dynamic lighting convencionais. O mod atua na apresentação luminosa e não altera a iluminação permanente dos blocos nem a física da estrutura. A build `2.0.1` é a beta NeoForge 1.21.1 instalada.

## SableMassView — 1.0.0

`sablemassview-1.0.0.jar`
**SableMassView** é uma ferramenta client-side de inspeção. Com **tooltips avançados (****`F3+H`****)** ativados, ela mostra a **massa física atribuída pelo Sable a cada bloco** diretamente na tooltip do item/bloco.
O addon não recalcula nem modifica a massa; apenas expõe um valor já utilizado pelo physics engine. Isso torna visível uma propriedade que influencia peso total e comportamento de contraptions, permitindo consultar materiais sem precisar inferir seus parâmetros físicos.

## Jade Sable Compat — 1.3.0

`sablejade-1.3.0.jar`
**Jade Sable Compat** corrige o ray tracing/seleção de blocos do Jade quando o alvo pertence a um **sublevel ou construção móvel Sable**. Em vez de o overlay identificar por engano o bloco situado nas mesmas coordenadas do level principal, a integração refaz a seleção usando a transformação do sublevel.
Com isso, título, ícone e dados exibidos pelo Jade correspondem ao bloco realmente sob a mira dentro da contraption. A versão `1.3.0` é client-side e é a release NeoForge 1.21.1 atual.

## Waystones: Sable — 1.0.7

`waystonessable-1.0.7.jar`
**Waystones: Sable** faz Waystones reconhecerem corretamente **SubLevels móveis**. A bridge corrige validação de destino, cálculo de distância, sincronização cliente-servidor e transformação de coordenadas quando uma waystone está dentro de uma contraption ou quando o teleporte aponta para um sublevel.
O objetivo é preservar a semântica normal do Waystones mesmo quando o ponto de viagem não pertence ao grid estático do mundo. A build `1.0.7` é a release NeoForge 1.21.1 atual dessa integração.

## Sound Physics Aeronautics — runtime 1.3.0.2

`sound-physics-aeronautics-1.4.0.jar`
**Sound Physics Aeronautics** é um fork de Sound Physics Remastered adaptado ao espaço transformado de **Sable/Aeronautics**. Ele calcula acústica, oclusão e alcance sonoro levando em conta paredes e geometria dentro de sublevels móveis, em vez de avaliar apenas o level principal.
A integração também cobre efeitos de movimento associados a veículos, incluindo comportamento espacial/Doppler conforme suportado pelo fork, fazendo som de máquinas, passos e ambiente acompanhar a posição física da contraption.
O arquivo instalado/publicado é `1.4.0`, mas o provider carregado continua declarando runtime **`1.3.0.2`**. Não existe um segundo Sound Physics Remastered top-level na modlist atual.

## Presence Footsteps x Sable — 1.0

`pfsable-1.0.jar`
**Presence Footsteps x Sable** é um patch específico para que o Presence Footsteps consulte corretamente a superfície sob o jogador quando ele está sobre uma **contraption Sable/Aeronautics**. Dessa forma, o material real do bloco móvel produz o conjunto de passos correspondente em vez de o sistema consultar erroneamente o bloco do mundo principal.
A bridge reutiliza os sons e regras do Presence Footsteps; não adiciona uma segunda biblioteca de áudio nem novos materiais próprios. O JAR `pfsable-1.0.jar` é a release NeoForge 1.21.1 instalada.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/07-1-extensoes-fisicas-de-ragdoll-do-sable-atualizacao-28-08.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 6.1. Extensões físicas de ragdoll do Sable — atualização 28/08

## Sable Ragdolls — 0.7.5

`sable_player_ragdoll-1.21.1-0.7.5.jar`
**Sable Ragdolls** usa a física do Sable para representar o corpo do jogador como um conjunto de peças físicas simuladas. O estado pode ser acionado manualmente, por itens definidos em datapack ou por addons externos; o mod também fornece dummies, suporte a skin/perfil, despawn configurável e uma API pública para outras integrações.
Ele é a base física do restante do stack de ragdoll: não define sozinho todos os gatilhos, patches ou modelos compatíveis, mas expõe o corpo articulado que essas extensões utilizam.

## Ragdoll Reactions — 0.7.0

`ragdoll_reactions-1.21.1-0.7.0.jar`
**Ragdoll Reactions** conecta eventos cinéticos do mundo ao estado físico do Player Ragdoll. Colisões fortes, atropelamentos, mudanças bruscas de direção, velocidades de lançamento e explosões podem provocar ragdoll, com sensibilidade, thresholds, cooldown de retrigger e ativação geral configuráveis no servidor.
O addon não implementa um segundo motor físico: depende de Sable + Sable Player Ragdoll e converte eventos de movimento/impacto em gatilhos para o sistema já existente.

## Sable Ragdolls Patch — 1.9

`sable_player_ragdoll_patch-1.21.1-1.9.jar`
**Sable Ragdolls Patch** é a camada de correção do stack. Ajusta renderização, colisão e estados envolvendo Player Ragdoll/Ragdoll Corpse, incluindo Curios, second skin/cape, swim pose, carrying/inventory e modelos ou braços com escala incorreta.
A release `1.9` também corrige os braços em primeira pessoa do Punchy durante ragdoll, bloqueia Ender Pearls e Wind Bombs nesse estado e trata um caso em que `/sable remove @e` podia deixar o jogador preso em estado inválido. É patch/compatibilidade, não outro sistema de ragdoll.

## Sable x CPM — 0.3.2+1.21.1

`sable-x-cpm-0.3.2+1.21.1.jar`
**Sable x CPM** integra **Customizable Player Models** aos ragdolls e cadáveres físicos do Sable. Cada peça simulada normalmente usa um PlayerModel vanilla; a bridge usa a plugin API do CPM para associar o modelo customizado correto antes da renderização.
Ela cobre Sable Player Ragdoll e Sable Ragdoll Corpse. Capas e elytra continuam usando o caminho vanilla, e partes CPM muito afastadas do osso pai podem apresentar separação visual nas juntas porque as peças são simuladas individualmente.

## Sable mob ragdoll corpses — 1.1.5

`mob_ragdoll_corpse-1.1.5.jar`
**Sable mob ragdoll corpses** estende a física pós-morte aos mobs. Em vez de desaparecerem imediatamente, criaturas mortas podem permanecer como corpos ragdoll físicos persistentes que participam da apresentação e da interação do mundo.
O projeto documenta usos como carregar presas ou companheiros e enterrar companheiros, transformando a morte de entidades em um estado físico persistente. Sua função é complementar o stack Sable/ragdoll; não altera IA viva nem cria um sistema separado de mobs.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/08-espaco-e-tecnologia-de-alto-nivel.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 7. Espaço e tecnologia de alto nível

## Create: Northstar Redux — 0.6.4+1.21.1

`Northstar-0.6.4+1.21.1.jar`
**Create: Northstar Redux** é um addon de **exploração espacial construído sobre a filosofia de engenharia do Create**. O jogador fabrica componentes, materiais e infraestrutura para montar foguetes/veículos espaciais como construções próprias, em vez de receber uma nave pré-fabricada por interface.
A progressão introduz materiais e processos de nível espacial, incluindo Titanium, sistemas ligados a oxigênio e ambientes planetários, além de diferentes corpos celestes e dimensões exploráveis. Recursos obtidos fora do Overworld retornam às cadeias de produção e permitem avançar o programa espacial.
A linha Redux moderniza Northstar para Create 6 e NeoForge 1.21.1. O JAR `0.6.4+1.21.1` é a release atual dessa linha e funciona como base para integrações como AeroStar.

## Create: Creating Space — 1.7.20

`creatingspace-1.21.1-1.7.20.jar`
**Create: Creating Space** permite projetar **foguetes como contraptions Create** e utilizá-los para viajar a outros planetas. O formato e a disposição da nave são construídos pelo jogador, e a proposta do projeto enfatiza uma fantasia de engenharia inspirada em ciência mais física do que em veículos espaciais prontos.
A progressão espacial depende da infraestrutura da base: peças, combustível e preparação do foguete entram em cadeias Create antes da viagem, enquanto os destinos formam a camada de exploração interplanetária. Desde a linha 1.7.10 o mod requer **Create 6+**.
O JAR `creatingspace-1.21.1-1.7.20.jar` é a release NeoForge 1.21.1 atual dessa linha; releases 1.7.21 posteriores pertencem à linha Forge 1.20.1, não à instância atual.

## Create Aeronautics: Alcubierre — 1.2.6

`alcubierre-1.2.6.jar`
**Create Aeronautics: Alcubierre** adiciona dois blocos de tecnologia de alto nível para physics contraptions. O **Antigravity Drive** conecta-se a uma rede cinética e, enquanto recebe potência, cancela a gravidade aplicada ao ship, permitindo mantê-lo flutuando sem sustentação aerodinâmica convencional.
O **Alcubierre Controller** funciona como cockpit de warp: recebe coordenadas e uma dimensão de destino e, após ativação pelo painel ou redstone, transfere o **ship inteiro** em poucos segundos. A partir da versão 1.2.6 o mod exige **Dimensional Sable**, que fornece a transferência interdimensional do physics object.
Assim, antigravidade e warp são aplicados à própria construção Sable/Aeronautics, preservando a ideia de veículo montado com blocos.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/09-armas-defesa-e-engenharia-militar-do-create.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 8. Armas, defesa e engenharia militar do Create

## Create Big Cannons — 5.11.7

`createbigcannons-5.11.7+mc.1.21.1.jar`
**Create Big Cannons** transforma artilharia em engenharia de blocos integrada ao Create. O jogador fabrica e monta grandes canhões e autocannons a partir de componentes físicos, escolhe materiais de construção e configura mecanismos de carregamento, breeches e mounts em vez de receber uma arma pronta como item.
O sistema inclui famílias próprias de munição, cargas propelentes, projectiles e fuzes, além de processos de fabricação e operação que fazem a artilharia participar da fábrica. A linha 5.11 também possui integração explícita com **Sable**, incluindo tratamento de impacto de projéteis em physics objects; a build 5.11.7 instalada é a release NeoForge 1.21.1 atual desse ramo.

## CBC Advanced Technology — 0.1.4c-1.21.1

`cbc_at_Neoforge_1.21.1_0.1.4c.jar`
**Create Big Cannons: Advanced Technologies** amplia CBC com armamento e mecanismos mais complexos. A ficha atual registra muzzle brakes, fume extractors, silencers, rifled barrels, autocannons avançados, rocket pods/rails, novas munições e foguetes, levando o sistema além dos canhões e autocannons básicos do mod-base.
Ele continua dependente da infraestrutura mecânica e balística de Create Big Cannons; não é uma coleção de armas independente. A release pública é `0.1.4c`, enquanto o metadata runtime da instância declara `0.1.4c-1.21.1`, distinção mantida no catálogo.

## CBCAT Fix — 1.0.0

`cbcatfix-1.21.1-neoforge-1.0.1.jar`
**CBCAT Fix** é um patch top-level dedicado à combinação atual de Create Big Cannons e Advanced Technologies. Sua função é corrigir crashes e comportamentos quebrados do addon em versões recentes do stack CBC, especialmente pontos ligados a armamentos/munições e compatibilidade funcional.
Ele não acrescenta uma segunda árvore de artilharia: existe para estabilizar e complementar CBC:AT no ambiente atual. O JAR instalado/publicado é `cbcatfix-1.21.1-neoforge-1.0.1.jar`, enquanto o metadata runtime declara `1.0.0`; ambas as identificações permanecem registradas.

## Create: Gunsmithing — 1.4.9

`create-gunsmithing-1.21.1-1.4.9.jar`
**Create: Gunsmithing** adiciona armas portáteis steampunk produzidas por mecânicas do Create. O catálogo oficial inclui Flintlock, Revolver, Shotgun, Nailgun, Gatling, Blazegun, Launcher, Pneumatic Hammer e Frag Grenade, com animações próprias e componentes associados.
A identidade do mod está tanto nas armas quanto na fabricação: o arsenal é construído dentro da linguagem industrial do Create, em vez de funcionar como um gun mod desconectado da fábrica. A linha também oferece suporte a gun packs NTGL para adicionar ou modificar armas.

## Create Missiles — 1.0.3

`createmissiles-1.0.3+neoforge-1.21.1.jar`
**Create Missiles** adiciona mísseis fisicamente simulados e modulares ao Create. Cada veículo pode ser montado com mais de **30 peças intercambiáveis**, combinando chassis, thrusters, guidance/navigation e warheads para formar projetos com comportamento próprio.
O lançamento usa uma estrutura multiblock de **Launch Pad** com Control Panel, Assembly Panel e Navigation Panel. O addon também possui drones capazes de voar até coordenadas específicas e mapear a área, integração com JEI/Ponder e receitas para clonar assemblies. A build 1.0.3 instalada é a release NeoForge 1.21.1 para Create 6.0.10 e inclui correção explícita para dedicated server.

## Create Guardian Beam Defense — 1.3.7.1b

`Create-Guardian-Beam-Defense-1.3.7.1b-1.21.1-neoforge.jar`
**Create Guardian Beam Defense** acrescenta unidades estacionárias de defesa inspiradas em Guardians e Ocean Monuments. As torres usam feixes contínuos/em rajadas, fazem **lock automático em mobs configurados** e causam dano progressivamente sem exigir que o jogador opere a arma manualmente.
O sistema possui níveis de defesa — incluindo variantes capazes de lidar com múltiplos alvos — e é integrado ao ecossistema Create como infraestrutura defensiva fixa. A build `1.3.7.1b` instalada continua classificada como beta para NeoForge 1.21.1.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/10-transporte-ferroviario-maritimo-e-pessoal.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 9. Transporte ferroviário, marítimo e pessoal

## Create: Steam 'n' Rails — 0.3.0 beta 2

`railways-0.3.0-beta.2+neoforge-mc1.21.1.jar`
**Create: Steam 'n' Rails** é a grande expansão ferroviária do Create. Ela amplia trens com novos tipos e estilos de **tracks e bogeys**, componentes ferroviários, sinalização e recursos de composição/estação que tornam material rodante e infraestrutura muito mais variados que o conjunto base.
A edição instalada é o **port não oficial NeoForge 1.21.1**, cujo ramo 0.3.0 migrou o conjunto de recursos do upstream moderno. O arquivo atual `railways-0.3.0-beta.2+neoforge-mc1.21.1.jar` é beta; essa classificação descreve a maturidade do port, não dúvida sobre sua presença no pack.

## Create Teleporters Remastered — 2.0.2

`createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`
**Create Teleporters Remastered** reconstrói o antigo Create Teleporters com estética e interfaces alinhadas ao Create atual. O sistema inclui **Entity Teleporters** de tamanhos diferentes, **Item Teleporter**, links de teleporte com alcance configurável e um **Custom Portal multiblock** formado por Custom Portal Base e Quantum Casings.
A versão Remastered simplificou a arquitetura antiga, removeu receivers/gravity stabilizer e refez GUIs, modelos e mecânicas de portal. O JAR instalado usa a build `2.0.2b`, enquanto o metadata runtime declara `2.0.2`; as duas strings são mantidas separadamente.

## Create: Blocks & Bogies — 1.0.8

`create_bb-1.0.8-1.21.1.jar`
**Create: Blocks & Bogies** é uma expansão de customização do material rodante. Ela adiciona uma **interface interativa de configuração de bogies** e muitas famílias de wheelsets/drivers, permitindo ajustar visual, número de eixos e mecanismo aparente do conjunto.
Entre as opções estão Walschaerts, piston-only, pistonless, rodless e Scotch Yoke drivers em vários números de eixos, além de bogies menores/trailing. O mod exige Create 6.0+, mas não depende de Steam 'n' Rails para funcionar.

## Create: Ornithopter Glider — runtime 1.2.0-1.21.1

`createornithopterglider-1.2.0-1.21.1.jar`
**Create: Ornithopter Glider** adiciona um planador mecânico inspirado nas máquinas voadoras de Leonardo da Vinci, com modelo, animações e sons próprios. Durante o voo, a tecla configurada — Space por padrão — carrega um movimento de **flap** que produz um impulso adicional; o boost possui cooldown configurável e a configuração padrão documentada usa 2 segundos.
O glider pode usar o slot traseiro do Curios, mas Curios tornou-se opcional na 1.2.0. A fabricação também participa do ecossistema Create: o Ornithopter é montado através do **Mechanical Crafter**, em vez de uma receita manual comum. A publicação externa usa `1.2.0+1.21.1`, enquanto o arquivo/runtime local preserva `1.2.0-1.21.1`.

## Create: Mechanical Companion — runtime não declarado (publicação 1.9)

`createmechanicalcompanion-1.9-neoforge-1.21.1.jar`
**Create: Mechanical Companion** adiciona o **Mechanical Wolf**, um mob craftável que acompanha o jogador e é invocado ao equipar o Mechanical Wolf Link no slot de cabeça do Curios. Clicar no wolf abre sua interface de módulos; uma wrench pode ser usada para repará-lo.
Os módulos são organizados em quatro categorias. **Defensive** inclui Reinforced/Netherite Plates; **Offensive** inclui Smelting Fangs, Tesla Tail e Mounted Crossbow; **Movement** inclui Booster Rocket e Quantum Drive; **Utility** inclui Mob Radar, Mounted Light e Regenerative Casing. O mod também possui **Illager Workshops**, estruturas onde aparecem Illager Engineers/Supervisors e recursos ligados à criação do wolf.
A release pública instalada é `1.9` para NeoForge 1.21.1, mas a modlist não declara uma versão runtime interna; por isso o catálogo mantém `não declarada (publicação 1.9)` em vez de inferir o valor.

## Create Jetpack — 5.2.1

`create_jetpack-forge-5.2.1.jar`
**Create Jetpack** transforma o conceito do Copper Backtank em equipamento de voo. O jetpack utiliza **ar pressurizado** da infraestrutura Create para permitir mobilidade aérea, fazendo o voo consumir um recurso ligado ao backtank em vez de funcionar como voo criativo gratuito.
A build 5.2.1 é a release NeoForge 1.21.1 atual do projeto. O JAR também embarca Flight Lib como dependência interna, portanto essa biblioteca não aparece como mod top-level separado no catálogo.

## Create: Jetpack Curios — 1.2.0

`create_jetpack_curios-1.2.0-neoforge-1.21.1.jar`
**Create: Jetpack Curios** é a bridge entre Create Jetpack e Curios. Ela permite equipar **Create Jetpack e backtanks compatíveis no slot traseiro do Curios**, preservando seu funcionamento sem ocupar o slot convencional de peitoral.
Seu papel é exclusivamente de equipagem/compatibilidade: o sistema de voo continua pertencendo ao Create Jetpack. A build instalada é `1.2.0` para NeoForge 1.21.1.

## Create SA Curios Jetpacks — runtime 1.2.22

`create_sa_curios_jetpacks-neoforge-1.21.1-1.2.4.jar`
**Create SA Curios Jetpacks** integra os jetpacks e tanks de **Create Stuff 'N Additions** ao Curios. A bridge cobre equipagem em slot dedicado, comportamento funcional dos equipamentos quando usados fora do armor slot, visualização e rotas de reabastecimento/uso compatíveis com o sistema-base.
Ela é distinta de Create: Jetpack Curios porque atende outro mod-base: aqui o equipamento vem de Stuff 'N Additions. O arquivo/publicação instalado identifica `1.2.4`, enquanto o metadata runtime declara `1.2.22`; ambas as strings permanecem registradas.

## Create: Curios Backtank — 1.0.1

`create_curios_backtank-neoforge-1.21.1-1.0.1.jar`
**Create: Curios Backtank** permite usar os **backtanks do Create no slot traseiro do Curios**. O reservatório continua fornecendo sua função pneumática aos equipamentos Create compatíveis, mas deixa de competir diretamente com a armadura equipada no chest slot.
É uma bridge pequena e específica: não adiciona novo combustível, nova mochila ou novo jetpack. Sua função é mudar a forma de equipar o backtank mantendo a lógica original do Create.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/11-armazenamento-e-logistica-geral.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 10. Armazenamento e logística geral

## Sophisticated Backpacks — 3.25.78

`sophisticatedbackpacks-1.21.1-3.25.78.2107.jar`
**Sophisticated Backpacks** é o sistema portátil do ecossistema Sophisticated. As mochilas podem ser carregadas pelo jogador ou colocadas no mundo, possuem tiers de capacidade e aceitam **upgrades funcionais** que mudam seu comportamento em vez de apenas aumentar slots.
Entre os upgrades do sistema estão pickup/magnet para coleta automática, feeding, crafting, smelting, filtros, tanks e battery, além de módulos ligados à organização e automação. Isso permite configurar uma backpack como inventário pessoal simples, estação de crafting, coletor automático, suporte de fluidos/energia ou combinação desses papéis conforme os slots disponíveis.
A build instalada é `sophisticatedbackpacks-1.21.1-3.25.78.2107.jar`, release NeoForge 1.21.1 publicada em 19/08/2026. Ela depende de Sophisticated Core e é o mod-base das integrações Create descritas abaixo.

## Sophisticated Storage — 1.5.91.2127

`sophisticatedstorage-1.21.1-1.5.91.2127.jar`
**Sophisticated Storage** é um sistema de armazenamento físico modular baseado em **Barrels, Chests e Shulker Boxes com tiers e upgrades**. Chests/barrels podem subir por copper, iron, gold, diamond e netherite, aumentando capacidade e número de slots de upgrade sem exigir abandonar o inventário existente.
Os upgrades alteram comportamento do armazenamento — filtragem, automação, capacidade por slot, crafting e outras funções — enquanto configurações e conteúdo permanecem associados ao bloco. O mod também possui **Limited Barrels**, com 1–4 slots de grande capacidade, voltados a recursos em massa, além de personalização visual de madeira, cores e materiais de barrel.

## Sophisticated Storage Create Integration — 0.1.21.209

`sophisticatedstoragecreateintegration-1.21.1-0.1.21.209.jar`
**Sophisticated Storage Create Integration** torna os blocos de Sophisticated Storage plenamente funcionais em **contraptions do Create**. Barrels, chests e shulker boxes mantêm conteúdo, configurações de inventário, slots memorizados e a maior parte dos upgrades enquanto a estrutura se move em trains, rotating platforms, flying machines e outras contraptions.
Upgrades dependentes de posição, como Pickup/Magnet, usam a posição dinâmica real do storage na contraption; stack/crafting upgrades continuam funcionais. Tier upgrades podem ser aplicados diretamente ao storage montado, e Storage Tool/Paintbrush mantêm controle de locks, indicadores, cores e materiais. O arquivo instalado é `0.1.21.209`, com runtime `0.1.21`.

## Create: Sophisticated Backpacks Compat — runtime sem versão declarada

`create_sophback_compat-1.0.jar`
**Create: Sophisticated Backpacks Compat** conecta a progressão de crafting do Sophisticated Backpacks às máquinas do Create. Ele adiciona **receitas de processamento Create** para componentes da mochila, permitindo que partes da fabricação/upgrades sejam produzidas em linhas mecânicas em vez de depender apenas do crafting convencional.
Isso é diferente de Sophisticated Backpacks Create Integration: este mod trata **receitas e fabricação**; a outra bridge trata o funcionamento de backpacks em contraptions. O arquivo/publicação é `1.0`, mas o metadata runtime não declara versão e expõe um nome interno incorreto; o catálogo preserva essa anomalia sem inventar uma versão runtime.

## Sophisticated Backpacks Create Integration — 0.1.8.134

`sophisticatedbackpackscreateintegration-1.21.1-0.1.8.134.jar`
**Sophisticated Backpacks Create Integration** faz mochilas Sophisticated funcionarem como armazenamento compatível com **contraptions Create**, mantendo seu papel de inventário móvel quando inseridas na estrutura física/contraption.
A integração é complementar ao mod de receitas `create_sophback_compat`: aqui o foco é **funcionamento da backpack em movimento**, enquanto o outro altera como seus componentes podem ser fabricados. A build instalada é `0.1.8.134`, com metadata runtime `0.1.8`.

## Create: Backpack Pixel — 1.2.0

`backpack_pixel-1.2.0-neoforge-1.21.1.jar`
**Create: Backpack Pixel** adiciona backpacks de estética Create com **componentes funcionais instaláveis**. As mochilas podem ser configuradas para finalidades diferentes combinando componentes, e também aceitam skins para mudar a aparência sem alterar sua função.
A interface pode ser aberta por clique direito ou pela tecla `B` enquanto a backpack está equipada. A linha 1.2.0 para NeoForge 1.21.1 não exige mais a antiga renderer API como dependência obrigatória, e o próprio projeto apresenta Backpack Pixel como complemento visual/funcional de Protection Pixel.

## Create: Protection Pixel — 2.2.1

`protection_pixel-2.2.1-neoforge-1.21.1.jar`
**Create: Protection Pixel** adiciona conjuntos de armadura steampunk em que **cada peça possui função e atributos próprios**, em vez de serem apenas variantes cosméticas. O equipamento é fabricado, processado e fortalecido usando a infraestrutura industrial do Create.
Exemplos documentados incluem **Plague Helmet**, que libera vapor para remover efeitos temporários como blindness, darkness, weakness e slowness; **Lancer Helmet**, que relaciona velocidade do usuário ao dano; e **Hunter Helmet**, voltado a detectar criaturas em movimento e auxiliar exploração/loot. Assim, os conjuntos formam uma camada de equipamentos tecnológicos com especializações diferentes.

## Tom's Simple Storage — 2.4.1

`toms_storage-1.21-2.4.1.jar`
**Tom's Simple Storage Mod** cria uma rede leve sobre **inventários físicos existentes**. Baús e outros containers continuam guardando os itens; cabos/conectores os unem a um terminal que apresenta o conteúdo como um inventário pesquisável e centralizado.
O sistema fornece acesso, organização e crafting sobre a rede sem converter recursos em células digitais como o AE2. Essa arquitetura também explica integrações do pack como Create Contraption Terminals, que levam terminais Tom's para contraptions móveis.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/12-pontes-entre-magia-e-tecnologia.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 11. Pontes entre magia e tecnologia

## Create: Enchantment Industry — 2.5.3

`create-enchantment-industry-2.5.3.jar`
**Create: Enchantment Industry** transforma experiência e encantamento em processos industriais. **Liquid Experience** pode ser armazenada e transportada; o **Disenchanter** remove enchantments convertendo-os em experiência; o **Blaze Enchanter** automatiza encantamento; e o **Printer** replica conteúdos como livros escritos, enchanted books, name tags e train schedules.
O sistema também integra Mending em belts por Spout + Liquid Experience, permite experience nuggets em interações compatíveis de Deployer/Crushing Wheels e possui **hyper-enchanting** para níveis acima do cap convencional. Há compatibilidade específica com Apotheosis/Apothic quando detectado.

## Create Enchantment Industry Plus — 1.1.1

`create_enchantment_industry_plus-1.1.1-1.21.1.jar`
**Create Enchantment Industry Plus** é uma extensão pequena do sistema acima focada em **ink e glow ink**. Acrescenta sacs que podem ser preenchidos para produzir ink sacs, receitas de grinding e conversão de ink sac em glow ink sac; a linha atual também ajusta receitas para usar black dye onde aplicável.

## Create: Enchantable Machinery — 3.6.0

`createenchantablemachinery-3.6.0+mc1.21.1-neoforge.jar`
**Create: Enchantable Machinery** permite aplicar **encantamentos vanilla diretamente a máquinas/blocos Create compatíveis**. Enchanted Spout, Mechanical Mixer, Plough, Mechanical Roller e outros componentes suportados recebem efeitos derivados dos enchantments aplicados, como interações específicas de Silk Touch e outras propriedades.
Isso é mecanicamente diferente do Enchantment Industry: aqui a própria máquina recebe enchantments e modifica seu comportamento; o outro mod industrializa XP e o processo de encantar itens.

## Ars Creo — 5.4.0

`ars_creo-1.21.1-5.4.0.jar`
**Ars Creo** é a integração estrutural entre **Ars Nouveau e Create**. Ela permite que componentes mágicos participem de contraptions e fluxos Create: Source, jars, turrets, Starbuncles e outros sistemas passam a reconhecer movimento e automação mecânica, enquanto displays/fluidos e interações específicas conectam as duas infraestruturas.
O resultado é compatibilidade mecânica real entre source automation e contraptions, não apenas receitas cruzadas. A build instalada é `5.4.0` para NeoForge 1.21.1.

## Ars Technica — 2.7.6

`ars_technica-1.21.1-2.7.6.jar`
**Ars Technica** cria uma camada de technomancy sobre Ars Nouveau/Create. Seus **glyphs** reproduzem operações industriais inspiradas em processos como crushing e pressing, permitindo que spellcraft execute transformações normalmente associadas às máquinas Create.
O addon também possui equipamentos próprios e o **Source Motor**, que converte Source em potência cinética, fazendo energia mágica alimentar diretamente uma rede mecânica. O escopo é maior que compatibilidade de receitas: magia passa a gerar movimento e a executar processos industriais.

## IronSable — 1.2.0

`ironsable-1.2.0.jar`
**IronSable** conecta **Iron's Spells 'n Spellbooks** à física Sable/Aeronautics. Spells que aplicam força deixam de ignorar physics objects: empurrões, puxões e efeitos de vento podem agir sobre contraptions e estruturas físicas móveis.
Na 1.2.0, Tempest's Grasp, Downburst e Maelstrom podem usar a escola **Wind** quando Wind's Spellbooks está instalado, e o addon expõe uma API pública de física para companion mods. Ele não adiciona um segundo sistema de spells ou de física; traduz efeitos mágicos para forças compreendidas pelo Sable.

## Create: Wizardry — 1.21.1-0.5.1-pre1

`create_wizardry-1.21.1-0.5.1-pre1.jar`
**Create: Wizardry** integra **Create e Iron's Spells 'n Spellbooks** por processamento, fluidos e spellcasting automatizado. Materiais/componentes mágicos entram em receitas mecânicas, enquanto mana e recursos arcanos passam a participar de máquinas e linhas produtivas.
Um dos elementos centrais é o **Blaze Caster**, que permite incorporar lançamento de spells à automação em vez de exigir exclusivamente um jogador conjurando manualmente. A build instalada é `1.21.1-0.5.1-pre1`; o sufixo pre-release é mantido como parte da identidade da versão.

## Apokinetics — 1.0.5

`apokinetics-1.0.5.jar`
**Create: Apokinetics** aplica a lógica de gems/affixes de Apotheosis/Apothic às máquinas do Create. Máquinas compatíveis podem receber **sockets** e **Machine Gems** que modificam propriedades industriais, transformando equipamento de loot em componentes de otimização da fábrica.
O addon inclui **Apokinetic Table**, **Kinetic Pylon** e ferramentas de diagnóstico/gerenciamento ligadas ao sistema, criando uma progressão própria de melhoria de máquinas em vez de apenas reconhecer itens Apotheosis em filtros.

## Apotheotic Creation — 2.0.0

`apotheoticcreation-2.0.0.jar`
**Apotheotic Creation** é uma bridge focada em **filtragem e identificação de propriedades Apotheosis/Apothic dentro do Create**. Ela permite que Attribute Filters e componentes relacionados reconheçam informações como raridade e affixes dos itens.
Seu papel é diferente de Apokinetics: Apotheotic Creation expõe metadados de loot ao sistema de filtros/logística; Apokinetics adiciona sockets e Machine Gems às próprias máquinas.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/13-addons-create-de-equipamentos-utilidades-e-construcao.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 12. Addons Create de equipamentos, utilidades e construção

## Create Stuff 'N Additions — runtime 2.1.4.

`create-stuff-additions1.21.1_v2.1.4b.jar`
**Create Stuff 'N Additions** amplia o Create com **equipamentos, ferramentas e gadgets pessoais**. As peças podem aumentar atributos físicos como força/mining speed e fornecer formas de movimento como flutuar, saltar e voar.
Os equipamentos não compartilham um único combustível: conforme a família/material — brass, andesite ou copper — podem operar com **steam**, heat energy ou hydraulic energy, usando água e/ou combustível conforme o sistema. O addon também adiciona rotas Create para produzir recursos vanilla como chainmail armor, coral, netherrack, crying obsidian e magma cream por processos como haunting/filling.
O JAR instalado é `v2.1.4b`; o metadata runtime declara literalmente `2.1.4.` com ponto final, e essa diferença permanece preservada.

## Create: Stuff & Netherite Additions — 1.2

`create_sna-1.2-neoforge-1.21.1.jar`
**Create Stuff & Netherite Additions** estende Stuff 'N Additions para o estágio Netherite. Seu conteúdo central são versões **mais fortes de jetpack e exoskeleton**, mantendo o modelo de equipamento tecnológico do mod-base, mas elevando proteção/desempenho para uma faixa endgame.
Ele depende diretamente de Create Stuff 'N Additions e não substitui sua progressão: adiciona o tier superior de equipamentos sobre a infraestrutura já existente. A build atual do pack é `1.2` para NeoForge 1.21.1.

## Create: Dragons Plus — 1.11.7b

`CreateDragonsPlus-1.11.7b.jar`
**Create: Dragons Plus** reúne utilidades e conteúdo complementar para Create, além de **integrações condicionais** com outros addons. Seu escopo é distribuído: pequenas conveniências, ajustes e recursos são ativados conforme os mods relacionados existem no ambiente, em vez de formar uma única cadeia industrial.
O JAR usa conditional mixins para carregar somente as integrações aplicáveis. A build atual do pack é `1.11.7b`; o componente `conditional-mixin 0.6.4` está embarcado internamente e não é um top-level separado.

## Create: Things and Misc — 4.1.1

`create_things_and_misc-4.1.1-neoforge-1.21.1.jar`
**Create: Misc & Things** é uma coleção ampla de conteúdo utilitário para Create, com **ferramentas, armas, equipamentos pessoais, gadgets, blocos tecnológicos e elementos decorativos**. Em vez de desenvolver uma única cadeia industrial, o addon preenche vários nichos pequenos ao redor da fábrica e do jogador.
A build instalada `4.1.1` é a release NeoForge 1.21.1 atual. O projeto é implementado com MCreator, mas isso descreve sua ferramenta de desenvolvimento e não altera seu papel funcional no catálogo.

## Create: More Features — 0.1.3

`create_mf-0.1.3-neoforge-1.21.1.jar`
**Create: More Features** é uma expansão geral que acrescenta **novos mecanismos, itens utilitários, profissões de villagers, automações/farms e dispositivos decorativos ou funcionais** ao Create. O projeto também recupera ideias ou itens removidos de versões anteriores do ecossistema e os adapta à linha atual.
Seu conteúdo é deliberadamente variado: parte serve à fábrica, parte à interação com villagers e parte à construção. Portanto ele funciona como um pacote de extensões menores, não como uma nova fonte de energia ou sistema logístico independente.

## Create: Connected — 1.3.2-mc1.21.1

`create_connected-1.3.2-mc1.21.1.jar`
**Create: Connected** amplia os componentes de controle, redstone e construção do Create. Entre as adições estão **novos clutches e gearboxes, redstone diodes/transmitters, versões rotacionadas de vaults e tanks** e blocos voltados a layouts mecânicos mais flexíveis.
O addon também melhora sistemas existentes: Sequenced Gearshift e Attribute Filter recebem funções adicionais, vários blocos passam a funcionar de forma mais completa em contraptions e schematics podem ser organizados em subpastas. Grande parte dos recursos pode ser habilitada ou desabilitada individualmente por configuração.
O runtime instalado declara literalmente `1.3.2-mc1.21.1` e exige Create 6.0.7+, requisito atendido pelo Create 6.0.10 do pack.

## Create: Copycats+ — 3.0.8

`copycats-3.0.8+mc.1.21.1-neoforge.jar`
**Create: Copycats+** expande maciçamente o conceito de **Copycat Blocks**: formas estruturais diferentes podem receber a aparência de outros blocos, permitindo reproduzir material, textura e acabamento sem perder a geometria especializada da peça.
O catálogo inclui numerosas variantes além das formas básicas do Create, úteis para revestir mecanismos, criar painéis, molduras, superfícies inclinadas e detalhes arquitetônicos. A build `3.0.8+mc.1.21.1-neoforge` instalada é a release NeoForge 1.21.1 atual publicada em agosto de 2026.

## Create: Extra Copycats — 1.0.2

`extra_copycats-1.0.2.jar`
**Create: Extra Copycats** adiciona formas adicionais de blocos Copycat ao ecossistema Create/Copycats, incluindo peças como **Copycat Collapsible Grid**. Como os demais copycats, as formas podem assumir materiais aplicados pelo jogador para integrar mecanismos e arquitetura sem limitar a paleta visual.
Ele é um addon separado de Copycats+: o JAR `extra_copycats-1.0.2.jar` permanece top-level na modlist atual e adiciona seu próprio subconjunto de formas.

## Create: Bells & Whistles — 0.4.7-1.21.1

`bellsandwhistles-0.4.7-1.21.1.jar`
**Create: Bells & Whistles** amplia principalmente a **estética ferroviária, industrial e de estações** do Create. O addon adiciona adornos, peças utilitárias e componentes de construção pensados para locomotivas, vagões, plataformas, estações e outras estruturas ligadas a trains.
Seu foco é tornar material rodante e infraestrutura ferroviária mais detalhados e variados visualmente, complementando as mecânicas de trem já fornecidas pelo Create e por outros addons ferroviários.

## Create: Bits 'n' Bobs — 2.2.7

`bits_n_bobs-2.2.7.jar`
**Create: Bits 'n' Bobs** combina customização visual com novos componentes mecânicos. Cogwheels podem trocar o tipo de madeira; pipes e tanks aceitam tingimento; e o addon acrescenta peças como **Cogwheel Chain Drives, Flanged Cogwheels e Chain Carriage** para novos layouts de transmissão e movimentação.
O **Flywheel Bearing** dá ao flywheel uma função mecânica própria de armazenamento/uso de energia cinética, fazendo o addon ultrapassar o escopo puramente decorativo. Assim, estética e pequenas extensões de engenharia aparecem no mesmo pacote.

## Create Deco — 2.1.3

`createdeco-2.1.3.jar`
**Create Deco** é um grande pacote de **arquitetura industrial** alinhado à linguagem visual do Create. Ele fornece estruturas metálicas, catwalks/passarelas, grades, placas, suportes e múltiplas variantes de materiais para detalhar fábricas, ferrovias, depósitos e instalações técnicas.
A função é predominantemente construtiva: o addon amplia a paleta de blocos industriais sem criar uma linha de produção ou sistema energético separado. A build instalada é `2.1.3`.

## Create: Design n' Decor — 2.2b

`Design-n-Decor-1.21.1-2.2b.jar`
**Create: Design n' Decor** adiciona **QoL de construção e variantes funcionais/decorativas** ao Create. A linha atual inclui múltiplos tipos de Crushing Wheels, containers retrabalhados, dyed depots, text plates, large fans, boilers, sheet metals, lamps, catwalks e breaker switches, além de diversas variantes tingidas e texturas reestruturadas.
Parte do conteúdo é visual, mas containers, fans, switches e outras peças continuam participando de interação ou infraestrutura Create. A build instalada `2.2b` é a release NeoForge 1.21.1 atual.

## Create: Furnitures — 1.1.2

`create_furnitures-1.1.2-neoforge-1.21.1.jar`
**Create: Furnitures** adiciona mobiliário construído para combinar com a estética industrial do Create. Mesas, cadeiras, bancos e outras peças recebem variantes de materiais e cores para equipar oficinas, fábricas, escritórios, estações e áreas residenciais sem abandonar a linguagem visual do restante da instalação.
É um addon predominantemente decorativo; sua contribuição está na ambientação e composição interna das construções, não em uma nova mecânica de automação.

## Create: Prismatic Shine — 1.2.2

`createprism-1.2.2.jar`
**Create: Prismatic Shine** é uma releitura do antigo Create: Crystal Clear focada em **casings transparentes e iluminados**. Ele adiciona glass casings e illumination casings que mantêm a estética mecânica do Create enquanto permitem estruturas visualmente abertas, luminosas ou translúcidas.
O objetivo é ampliar a construção de máquinas e fábricas com casings que exibem o interior ou funcionam como elementos de iluminação. A versão `1.2.2` é a release NeoForge 1.21.1 instalada.

## Create: Chromatic Return — runtime 1.0.0

`createchromaticreturn-1.0.4-neoforge-1.21.1.jar`
**Create: Chromatic Return** reintroduz o conceito de **Chromatic Compound** como uma progressão de ligas endgame com propriedades especiais. A linha atual possui materiais como **Multiplite, Anti-Plite, Industrium, Durasteel, Fortunite e Silkstrum**, cada um ligado a efeitos ou usos próprios — produção de componentes, durabilidade extrema, aumento de rendimento de minérios ou comportamento semelhante a Silk Touch.
Essas ligas alimentam ferramentas/armas de altíssimo nível, charms com efeitos permanentes enquanto equipados, livros infundidos e até itens equivalentes a recursos Creative. O sistema também possui enriquecimento de quartz que pode aumentar rendimento de crushed ores/gem ores em processos aquecidos ou superheated.
O arquivo/publicação instalado é `1.0.4`, enquanto o metadata runtime declara `1.0.0`; as duas identidades são preservadas sem normalização.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/14-ferramentas-de-controle-e-interface-create.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 13. Ferramentas de controle e interface Create

## Create Quality of Life — 1.6.3 fix1

`Create Quality of Life-1.21.1-1.6.3-fix1.jar`
**Create: Quality Of Life** reúne pequenas extensões voltadas a reduzir atrito no uso cotidiano do Create. Em vez de introduzir uma grande cadeia tecnológica, acrescenta ferramentas e comportamentos auxiliares para tornar construção, configuração e operação das máquinas mais rápidas ou menos repetitivas.
A build instalada é `1.6.3-fix1`, release NeoForge 1.21.1 atual. O mod também é dependência funcional de Create: Chromatic Return na configuração presente.

## Create Tweaked Controllers — 1.21.1-1.2.7

`create_tweaked_controllers-1.21.1-1.2.7.jar`
**Create: Tweaked Controllers** adiciona um **Advanced Controller** para comandar contraptions Create com teclado, mouse ou gamepad. Isso permite mapear entradas do jogador para controles de veículos e mecanismos de forma mais direta que a interface vanilla de redstone links/contraption controls.
A versão `1.2.7` suporta Create 6 e declara compatibilidade com Create Simulated e com ambientes Valkyrien Skies/Clockwork, refletindo seu foco em contraptions dirigíveis e sistemas móveis.

## Create Ultimine — 1.3.2

`createultimine-1.21.1-neoforge-1.3.2.jar`
**Create Ultimine** integra ações compatíveis do Create ao **FTB Ultimine**. Operações que normalmente seriam executadas bloco a bloco podem participar da seleção em área do Ultimine quando suportadas pelo addon, incluindo interações com ferramentas e wrench do ecossistema Create.
Ele não implementa um segundo sistema de vein mining: reutiliza o mecanismo de seleção/execução do FTB Ultimine e apenas ensina ações Create a participarem desse fluxo.

## Create Utilities J — 0.3.4+1.21.1

`Create-Utilities-J-1.21.1-0.3.4+1.21.1.jar`
**Create Utilities J** é a continuação mantida do antigo Create Utilities. A linha atual porta e preserva os componentes utilitários do projeto, corrige bugs e mantém o addon compatível com as APIs modernas de Create/NeoForge.
Na build `0.3.4+1.21.1`, o projeto migrou networking para a payload API do NeoForge, atualizou saved data/capability registration, restaurou a creative tab e corrigiu problemas como serialização de frequência com ItemStack vazio. Também existem correções específicas de sistemas legados, como o Void Motor. O foco é manutenção funcional e infraestrutura das utilidades já fornecidas pelo addon.

## Create Stats & Numbers — 1.2.81

`create_stats-1.2.81.jar`
**Create Stats & Numbers** amplia Create com **telemetria, eletricidade FE, controle e instrumentação de fábrica**. Stats displays, recorders, alarms, data dials, production detectors, hologramas e mapas de topologia permitem medir produção, eficiência, estado de máquinas e danos em seções de veículos ou instalações.
A parte elétrica forma uma rede própria com **cabos, wire terminals, dynamos, motors e capacitor banks**. O **Power Distribution Unit (PDU)** centraliza a distribuição e permite atribuir prioridades aos dispositivos; em modo automático, cargas de prioridade inferior podem ser desligadas primeiro quando a geração não atende a demanda.
O addon também inclui Factory Logic Controller, Dispatch Board, Load Balancer, Calculator, iluminação industrial, Induction Heater/Range e componentes veiculares como **Flight Control Computer, RPM Governor, cockpit console, Path Scanner e Pathfinder Computer**. A build `1.2.81` é um hotfix para inconsistência entre PDU e Flow Meter e inclui melhorias na interface web local.

## Create Optical — 0.4.2

`create_optical-0.4.2.jar`
**Create Optical** introduz uma camada de **sinais por feixes ópticos** dentro do ecossistema Create. Fontes ópticas dependem de potência/rotação e emitem feixes que podem ser direcionados, detectados ou usados como entrada lógica por componentes compatíveis.
A mecânica oferece uma linguagem de transmissão e controle diferente de eixos, redstone dust ou wireless redstone links, permitindo construir circuitos visuais baseados em direção e presença de luz/feixe.

## Create Cyber Goggles — 8.3.14

`CreateCyberGoggles-1.21.1-8.3.14-NeoForge.jar`
**Create: Cyber Goggles** é uma ferramenta **client-side de inspeção e assistência modular** para o Create. Os goggles ampliam as informações apresentadas ao jogador ao observar máquinas, redes e componentes, tornando mais fácil diagnosticar estados e parâmetros durante construção ou manutenção.
Seu escopo é interface/assistência visual; ele não substitui o equipamento físico do mod Create Goggles (Create Plus), que adiciona Goggle Helmets e Armored Backtanks. A build `8.3.14`, direcionada a Create 6.0.10, corrige um crash client-side quando o centro de uma contraption coincide exatamente com o centro da OBB do dispositivo e também atualiza localização.

## Create Goggles — 6.1.1

`creategoggles-1.21.1-6.1.1-[NEOFORGE].jar`
**Create Goggles (Create Plus)** adiciona **Goggle Helmets e Armored Backtanks**, combinando as Engineer's Goggles com peças de armadura/equipamento. O objetivo é preservar a leitura de informações do Create enquanto o jogador utiliza capacetes ou backtanks mais protegidos/funcionais.
É uma expansão de equipamento físico, não um overlay independente. A build `6.1.1` instalada é a beta NeoForge 1.21.1 atual do projeto.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/15-compatibilidades-e-infraestrutura-tecnica.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 14. Compatibilidades e infraestrutura técnica

## Lychee Tweaker — runtime 6.5.4+neoforge

`Lychee-1.21.1-NeoForge-6.5.4.jar`
**Lychee Tweaker** é um framework **data-driven de interações e receitas in-world**. Datapacks/JSON podem definir processos como item-on-block, interação com fogo ou fluidos, lightning, explosions, crushing, dripstone e outros eventos do mundo sem exigir criar um mod Java específico para cada receita.
Ele funciona como infraestrutura de scripting/configuração para conteúdos que precisam representar transformação física no mundo, especialmente útil quando uma receita não cabe no crafting table ou em uma recipe type convencional. A build instalada é `6.5.4`, com metadata runtime `6.5.4+neoforge`, e permanece classificada como beta oficial para NeoForge 1.21.1.

## Pondus Inventory — 0.15-Beta

`pondus_inventory_fi-0.15-Beta.jar`
**Pondus Inventory** estende a física de **Sable** para o conteúdo armazenado dentro dos ships/sublevels. Itens e blocos em inventários e **fluidos expostos por fluid handlers** passam a contribuir para a massa física total da estrutura, de modo que carga transportada deixe de ser invisível para o modelo de peso.
O sistema permite multiplicadores globais e ajustes por namespace/mod para massas de itens, blocos e fluidos. Assim, sua função é complementar a física/massa do Sable; ele não cria inventários, veículos ou armazenamento próprios. A build instalada é `0.15-Beta`.

## Create: Cold Sweat — 1.1.2

`create_cold_sweat-1.1.2.jar`
**Create: Cold Sweat** é a bridge térmica entre Create e Cold Sweat. Ela adiciona **temperaturas de fluidos**, fórmulas térmicas específicas para objetos Create e interações que não podem ser reproduzidas completamente apenas por datapack ou KubeJS.
Boilers, pipes, tanks e outras estruturas podem atuar como fontes ou sumidouros de temperatura conforme configuração; isso permite desde instalações industriais perigosamente quentes até pisos aquecidos por tubulações. A build 1.1.2 para NeoForge 1.21.1 também inclui correção específica de inicialização em dedicated server.

## Almost Unified — 1.21.1-1.4.2

`almostunified-neoforge-1.21.1-1.4.2.jar`
**Almost Unified** é uma infraestrutura de **unificação de materiais e receitas**. Quando vários mods registram equivalentes do mesmo recurso — por exemplo, diferentes ingots, nuggets, plates ou dusts associados às mesmas tags — o sistema escolhe uma variante preferida e reescreve receitas compatíveis para reduzir a fragmentação de itens equivalentes.
A unificação é orientada por tags e prioridades configuráveis, portanto o mod não simplesmente apaga materiais: ele ajusta quais variantes aparecem como saída/ingrediente canônico nos fluxos de crafting e processamento. Isso é especialmente relevante em um pack com muitas cadeias metalúrgicas e industriais.

## Create: Addon Compatibility — 1.0.0

`createaddoncompatibility-neoforge-1.21.1-1.0.0.jar`
**Create: Addon Compatibility** é uma camada específica de **unificação entre addons do Create**. Ela utiliza regras de compatibilidade para reduzir materiais, fluidos e receitas duplicadas produzidas por extensões industriais que representam os mesmos recursos de formas diferentes.
O mod trabalha em conjunto com **Almost Unified**, mas com conhecimento específico do ecossistema Create. Assim, Almost Unified fornece o mecanismo genérico de unificação e este addon fornece regras/ajustes voltados às extensões Create presentes no pack.

## Create: Dynamic Village — 0.9

`dynamicvillage-0.9-1.21.1.jar`
**Create: Dynamic Village** leva Create para **vilas vanilla** por meio de estruturas, profissões, job sites, trades e loot próprios. Entre as profissões documentadas estão **Mechanical Engineer** e **Hydraulic Engineer**, com economia ligada a componentes e materiais do Create.
A versão `0.9` revisa o interior das 20 construções do addon, corrige salas sem iluminação e chests ausentes e remove o antigo anel de ar ao redor dos prédios. A geração passou a ser ajustada por bioma e recebeu opções de configuração para **Biome Density** e **Village Size**, controlando quantidade de prédios customizados, alcance e densidade dos settlements.

## Create: Pillagers Arise — 132.36

`create_pillagers_arise NeoForge 1.21.1-132.36.jar`
**Create: Pillagers Arise** leva a estética e a engenharia do Create para estruturas hostis geradas no mundo. O projeto adiciona **10 novas estruturas** defendidas por pillagers, incluindo fortalezas e outposts mecanizados com contraptions, defesas automatizadas, armadilhas e loot próprios.
A proposta é transformar encontros com pillagers em exploração de instalações industriais hostis: Create aparece integrado ao próprio layout e às defesas dessas bases, não apenas como decoração aplicada a uma estrutura vanilla. A versão `132.36` é a release NeoForge 1.21.1 atual do projeto.

## CreateColonies — 2.0.6

`createcolonies-2.0.6.jar`
**CreateColonies** integra **Create, Structurize e MineColonies** para que blueprints da colônia consigam construir estruturas que utilizam componentes Create. A bridge adapta regras de placement e configuração de blocos que não podem ser tratados como cubos estáticos comuns.
Entre os elementos atendidos estão rails, belts, bogeys, train stations, deployers e outros componentes cuja orientação, montagem ou estado precisa ser reconstruído corretamente pelo builder da colônia. O objetivo é permitir que infraestrutura Create faça parte de schematics/blueprints MineColonies de maneira funcional.

## Create Aero + Connected Fluid Vessel Compat — 1.0.0

`aeroconnectedfluidvessel-1.0.0.jar`
**Create Aero + Connected FluidVessel Compat** corrige a interação entre **Create Aeronautics/Sable e os FluidVessels do Create: Connected**. Sem a bridge, steam vents do Aeronautics não reconhecem o FluidVessel como boiler válido quando ele está integrado a uma estrutura física.
A integração permite usar FluidVessels como boilers para produzir steam em airships, sincroniza corretamente sinais de redstone entre vents ligados ao mesmo vessel e corrige um caso em que shafts dos steam vents podiam se separar em physics entities independentes. A build instalada é `1.0.0`.

## Aero Copycats — runtime 1.1.0 / arquivo 1.1.1

`aerocopycats-1.1.1.jar`
**Aero Copycats** atribui **massa física (kpg)** apropriada aos blocos do Copycats+ quando usados em Sable/Create Aeronautics. Em vez de todas as formas Copycat receberem um peso genérico, o addon define valores para o catálogo completo e escala a massa em geometrias como Copycat Layers conforme o número de camadas aumenta.
Essa função é física, não aerodinâmica: `Create Aeronautics: Copycat Wing` registra formas Copycat como superfícies que geram lift; Aero Copycats define quanto essas peças pesam no solver Sable. O filename atual é `1.1.1`, enquanto a ficha/runtime local declara `1.1.0`; a divergência permanece explícita.

## Aeronautics Compat — 1.1.3

`aeronauticscompat-1.1.3.jar`
**AeronauticsCompat** é um pacote geral de **patches de compatibilidade para Sable/Create Aeronautics**. Ele adapta mods que originalmente assumem um mundo estático para reconhecer corretamente posição, câmera, interação ou estado de blocos/entidades dentro de physics ships.
A linha atual acumulou correções para diferentes integrações — incluindo suporte de câmera, Storage Drawers e, em versões anteriores, Immersive Paintings, furniture, Sleep Tight e casos de Cobblemon. A build instalada `1.1.3` é a release NeoForge 1.21.1 atual e requer Sable.

## Create: Smart Bounds — 1.0.0

`smart_bounds-1.0.0.jar`
**Create: Smart Bounds** é uma otimização **client-side de render bounds** para block entities do Create. Vários componentes usam bounding boxes de renderização muito maiores que o conteúdo realmente visível; isso pode manter block entities sendo renderizadas mesmo quando estão fora da tela. O addon substitui esses bounds por limites mais adequados em componentes suportados.
A cobertura documentada inclui **Mechanical Arms, Belts, Chain Conveyors, Factory Panels, PSI/Deployers, Rollers e Frog Ports**. O mod também refaz parte do cache dessas bounding boxes: belts, por exemplo, deixam de recalcular o render bound a cada tick e passam a atualizá-lo apenas quando o estado relevante realmente muda.
Ele não altera recipes, stress ou ticking das máquinas. Seu objetivo é reduzir trabalho de renderização, especialmente em fábricas grandes e contraptions densas. A build `1.0.0` é a única release NeoForge 1.21.1 publicada.

## CreateBetterFps — 1.1.4

`createbetterfps-1.21.1-1.1.4.jar`
**CreateBetterFps** é uma otimização **client-side** direcionada ao custo de renderização do Create. O alvo principal são cenas com grande quantidade de componentes, contraptions e efeitos visuais, especialmente quando shaders tornam a renderização do ecossistema mais pesada.
Ele não modifica receitas ou progressão e não substitui Create: Lazy Tick: BetterFps atua principalmente no lado visual/render; Lazy Tick reduz trabalho de atualização lógica de blocos e máquinas.

## Create Lazy Tick — 2.6.25-6.0.10

`CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar`
**Create: Lazy Tick** reduz o custo lógico de fábricas Create diminuindo ou reorganizando atualizações de componentes que não precisam executar trabalho completo a cada tick. Belts, funnels, chutes, depots e outros elementos podem reaproveitar cache ou atualizar em frequência menor quando o estado permite.
A build `2.6.25-6.0.10` é especificamente alinhada ao Create 6.0.10 e inclui correções recentes para **Mechanical Arms, Basins e ammo containers do Create Big Cannons**, além de ajustes de regras/cache. O foco é CPU/tick time de fábricas grandes, distinto da otimização de renderização do CreateBetterFps.

## Create JEI Compat — 1.0.3

`createjeicompat-1.0.3.jar`
**Create JEI Compat** melhora a representação de processos Create no **JEI**, especialmente **Sequenced Assembly** longas. A integração acrescenta paginação e controles de navegação quando uma sequência possui muitas etapas, mantendo ingredientes e fases legíveis e pesquisáveis sem comprimir todo o processo em uma única tela.
A versão `1.0.3` também inclui controles por teclado, atualização in-place do layout e suporte opcional ao stack EMI através de JEmi. Seu papel é de visualização/consulta de receitas, não alteração dos processos executados pelas máquinas.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/16-bibliotecas-tecnologicas.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 15. Bibliotecas tecnológicas

## Azimuth API — 1.4.7

`azimuth-1.4.7.jar`
**Azimuth API** é uma biblioteca de infraestrutura para addons do ecossistema Create. Ela concentra recursos estruturais, de renderização e de integração cinética usados por consumidores em vez de fazer cada addon implementar essas camadas de forma independente.
Um consumidor atual registrado no pack é **Bits 'n' Tracks**. Azimuth não adiciona uma linha de progressão ou máquinas próprias; sua presença representa uma dependência top-level compartilhada.

## KilaGraph — 21.1.0.11

`kilagraph-neoforge-1.21.1-21.1.0.11.jar`
**KilaGraph** é um toolkit de **node graphs programáveis e shader graphs** construído sobre LDLib2. Ele fornece Blueprint Graphs para lógica/data flow, RenderType Graphs para pipelines visuais, Shader Function Graphs reutilizáveis e um editor in-game baseado nas ferramentas do LDLib2.
A biblioteca inclui nodes voltados ao próprio Minecraft — items, blocks, fluids, entities, NBT, queries de mundo, math, listas, maps, strings e operações de shader. É infraestrutura para mods consumidores, não um shaderpack ou sistema visual autônomo. A build 1.21.1 está no canal beta.

## Multi-Piston — 1.2.58-1.21.1

`multipiston-1.2.58-1.21.1.jar`
**Multi-Piston** implementa um sistema de **pistão multidirecional** capaz de movimentar estruturas de formas que o piston vanilla não representa. Além da mecânica própria, ele funciona como componente técnico do ecossistema MineColonies.
Na modlist atual, MineColonies é consumidor confirmado dessa biblioteca/mod, e a versão `1.2.58-1.21.1` supera o mínimo exigido pelo snapshot instalado. Não é equivalente às contraptions Create: sua implementação e seu papel de dependência são separados.

## NukaTeam's Gun Lib — 3.2.0

`ntgl-1.21.1-3.2.0.jar`
**NukaTeam's Gun Lib (NTGL)** é um framework para mods e gun packs implementarem **armas animadas, armas melee, granadas e sistemas de tiro**. A API fornece modos de disparo, munição, dual wielding, tipos de projétil e infraestrutura visual/funcional para conteúdos consumidores.
A biblioteca não representa uma arma específica nem uma progressão própria completa: seu valor está em fornecer o runtime comum para packs e mods de armamento que a utilizam. A build 3.2.0 é a release NeoForge 1.21.1 instalada.

## Potentials — 0.7.1

`potentials-neoforge-1.21-0.7.1.jar`
**Potentials** é uma biblioteca de **capabilities cross-platform** para transferência de energia, fluidos e itens. Ela abstrai diferenças entre loaders/APIs para que mods consumidores possam consultar e movimentar esses recursos através de uma interface comum.
A linha `0.7.1` é a compatível com Minecraft 1.21/1.21.1 presente no pack e permanece beta. Não adiciona máquinas ou redes próprias; fornece a camada de interoperabilidade usada por consumidores específicos.

## Ritchie's Projectile Library — 2.1.2

`ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`
**Ritchie's Projectile Library** fornece infraestrutura para projéteis rápidos, long-range e de grande volume. Entre os recursos estão sincronização de movimento mais precisa, **chunkloading configurável para projéteis**, screen shake e **projectile bursts** capazes de representar shotgun pellets, fragmentação e shrapnel sem criar uma quantidade excessiva de entidades independentes.
É uma biblioteca para mods consumidores, especialmente conteúdos de firearms/artillery, e não uma arma isolada. A build 2.1.2 também inclui correções NeoForge ligadas ao registry de rede e efeitos de cannon shake.

## Sophisticated Core — runtime 1.4.90

`sophisticatedcore-1.21.1-1.4.90.2299.jar`
**Sophisticated Core** é a biblioteca compartilhada do ecossistema Sophisticated. Ela centraliza infraestrutura de **upgrades, inventários, storage, recipes, filtros e comportamento comum** utilizada por Sophisticated Backpacks, Sophisticated Storage e integrações correspondentes.
Não adiciona uma progressão jogável independente: seu conteúdo é consumido pelos mods-base. A build instalada `1.4.90.2299` é a release NeoForge 1.21.1 de 22/08/2026; o metadata runtime declara `1.4.90`. Entre as correções dessa build está o restocking de receitas com múltiplas alternativas de ingrediente.

## Mechanicals Lib — 1.1.6

`mechanicals-1.21.1-1.1.6.jar`
**Mechanicals Lib** é a biblioteca comum utilizada por mods do ecossistema Mechanicals. Ela concentra registries, helpers e código compartilhado para que addons consumidores não precisem embarcar implementações duplicadas.
Não adiciona uma progressão tecnológica própria. Um consumidor confirmado na modlist atual é **Create: Mechanical Spawner**, que depende dessa biblioteca para sua infraestrutura.

## Cupboard — 4.1

`cupboard-1.21.1-4.1.jar`
**Cupboard** é uma biblioteca/framework compartilhado para mods consumidores. Além de helpers e infraestrutura comum, a linha atual fornece **framework de configuração JSON, stacktraces completos em crashes, logging de erros de comandos e de carregamento síncrono de chunks**, além de proteções para falhas comuns durante carregamento de entidades, como rotações inválidas.
Ela não adiciona máquinas, itens de progressão ou gameplay próprio: os recursos visíveis pertencem aos mods que utilizam sua API. A build instalada foi atualizada para `4.1` na modlist de 28/08/2026; o guia anterior ainda registrava `4.0` e foi corrigido para o JAR canônico `cupboard-1.21.1-4.1.jar`.

## Curios API — 9.5.1+1.21.1

`curios-neoforge-9.5.1+1.21.1.jar`
**Curios API** fornece a infraestrutura de **slots de acessórios adicionais** usada por grande parte do pack. Mods podem registrar categorias como back, charm, ring, necklace e outros slots, definir regras de equipagem e consultar os itens equipados sem disputar os slots vanilla de armadura/offhand.
Isso permite que jetpacks, backtanks, goggles, charms e diversos acessórios permaneçam funcionais através de uma API comum. Curios não possui uma progressão própria significativa; é a camada de slots/equipamento consumida por vários mods e bridges.

## Cyclops Core — 1.29.3

`cyclopscore-1.21.1-neoforge-1.29.3.jar`
**Cyclops Core** é a biblioteca central do ecossistema CyclopsMC. Ela fornece APIs, configuração, network helpers, registries e outras estruturas compartilhadas por mods da família, como Integrated Dynamics/EvilCraft quando presentes.
A versão atual da modlist é `1.29.3`. O fato de outras bibliotecas gerais existirem no pack não substitui sua API específica; sua função é exclusivamente infraestrutura para consumidores Cyclops.

## Forgified Fabric API — 0.116.15+2.3.5+1.21.1

`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API** porta módulos da **Fabric API para NeoForge**, fornecendo as interfaces que mods originalmente escritos contra Fabric API esperam encontrar. No pack ele trabalha especialmente em conjunto com **Sinytra Connector**, permitindo que mods Fabric e suas dependências utilizem APIs equivalentes dentro do ambiente NeoForge.
Ele não substitui o Connector: Forgified Fabric API fornece APIs; Connector realiza a camada de compatibilidade/carregamento necessária aos mods Fabric. A build atual é `0.116.15+2.3.5+1.21.1`.

> **Componentes internos não contados como mods top-level:** Advanced AE pode embarcar/usar AE2AddonLib internamente, e o ecossistema AE2WTLib expõe APIs compartilhadas para seus consumidores. Como esses nomes não correspondem a JARs top-level independentes da modlist atual, ficam registrados apenas como contexto técnico e não como entradas de mod do catálogo.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/17-mapa-rapido-dos-ecossistemas.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 16. Mapa rápido dos ecossistemas

| Objetivo | Ecossistema principal | Exemplos instalados |
| --- | --- | --- |
| Automação mecânica visível | Create | Create, More Automation, Ultimate Factory, Metallurgy |
| Armazenamento digital e autocrafting | Applied Energistics 2 | AE2, Advanced AE, Applied Create |
| Máquinas energizadas especializadas | Oritech | Oritech, Oritech Things |
| Eletricidade e conversão cinética | Create energético | New Age, Crafts & Additions, Power Grid |
| Indústria pesada | Create industrial | TFMG, Diesel Generators, Metalwork |
| Energia nuclear | Create nuclear | Create Nuclear, New Age |
| Veículos construídos por blocos | Sable + Aeronautics | Create Aeronautics, Aeroworks, Propulsion |
| Exploração espacial | Create espacial | Northstar Redux, Creating Space, Alcubierre |
| Ferrovias | Create trains | Steam 'n' Rails, Blocks & Bogies |
| Artilharia e defesa | Create militar | Big Cannons, CBC Advanced Technology, Missiles |
| Integração magia-tecnologia | Technomancy | Ars Creo, Ars Technica, Create Wizardry, Apokinetics |
| Armazenamento físico conectado | Storage | Sophisticated Storage, Tom's Storage |


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/18-fontes-principais-de-referencia.md -->

<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 17. Fontes principais de referência

## Projetos e ecossistemas tecnológicos

- [Create Aeronautics — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeronautics)
- [Applied Energistics 2 — CurseForge](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
- [Oritech — CurseForge](https://www.curseforge.com/minecraft/mc-mods/oritech)
- [Create: New Age — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-new-age)
- [Create Nuclear — CurseForge](https://www.curseforge.com/minecraft/mc-mods/createnuclear)
- [Create: The Factory Must Grow — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-industry)
- [Create: Northstar Redux — CurseForge](https://www.curseforge.com/minecraft/mc-mods/northstar-redux)
- [Create: Creating Space — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-creating-space)
- [Applied Create — CurseForge](https://www.curseforge.com/minecraft/mc-mods/applied-create)
- [Create: Aeroworks — CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-aeroworks)

## Guia relacionado — Gameplay e Sistemas

- [Snapshot local](../gameplay/README.md)
- [Fonte canônica no Notion](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6)


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/19-projetos-proprios-do-modpack.md -->

<!-- Snapshot auditável reconciliado em 2026-08-30. -->

# 19. Projetos próprios do modpack — integração tecnológica canônica

A partir de 30/08/2026, os quatro projetos próprios fazem parte da auditoria obrigatória das perks.

**Fonte canônica completa:** [Projetos Próprios do Modpack](../projects/README.md)

Este capítulo é somente o recorte de **Tecnologia, engenharia e automação**. Para decidir `Provider/Mods`, hook, status, autoridade e fail-closed, o Chat 1 deve ler os quatro dossiês completos e a matriz cruzada.

## Volcanoes — ambiente físico como provider para engenharia

[Dossiê completo](../projects/02-volcanoes.md)

Volcanoes é o projeto próprio com maior superfície tecnológica já disponível em runtime.

- **IMPLEMENTADO E CANÔNICO:** geologia/depósitos, tectônica, vulcanismo/geotermia, Atmosphere, respiração, pressão atmosférica e hidrostática, enclosed-environment SPI e equipment/protection.
- Create Diving Helmet + Backtank participam da oferta canônica de oxigênio sem criar segundo recurso ou segundo consumo.
- Sable `2.0.5` e Aeronautics `1.3.1` integram posições/sublevels à pressão física; na ausência de contrato confiável de cabine selada, o sistema deliberadamente usa a atmosfera/pressão externa e não inventa proteção.
- Cold Sweat `2.4.2` continua autoridade de temperatura corporal. Volcanoes fornece calor ambiental bounded de lava, piroclastos e geotermia.
- Destroy continua autoridade de seu domínio de poluição/acid rain; a bridge não autoriza feedback industrial inventado.
- MineColonies alimenta `ProtectedAreaService`; máquinas/perks não podem contornar proteção de área.
- **RNS PARCIAL/FAIL-CLOSED:** Volcanoes já prova placement físico bounded/determinístico de Cu/Fe/Au, mas RNS continua authority de prospecção/native metal worldgen até o handoff seletivo de ownership ser fechado.

**Perks tecnológicas legítimas:** leitura geológica, diagnóstico ambiental, eficiência/proteção respiratória e de pressão, geotermia e engenharia em veículos quando houver boundary real. A perk não deve possuir um segundo scheduler de erupção, atmosphere state, body-temperature state, filtro ou sistema de pressão.

## RPG Skill Tree — progressão tecnológica e itemização futura

[Dossiê completo](../projects/01-rpg-skill-tree.md)

- O RPG é autoridade de Level/XP/CPP/atributos, Skill Tree, node effects e world scaling já fechados.
- A subtree **Technomancer** é **IMPLEMENTADA E CANÔNICA**, com ramos de Create Kinetics, AE2 Networks e Oritech Power. Gates e perda/refund de requisito pertencem ao runtime canônico da subtree.
- Integração geral Create/AE2/Oritech do Stage 06 ainda é **PLANEJADA/ABERTA**; a existência da subtree não autoriza o Chat 1 a inventar qualquer API genérica desses providers.
- Stage 11 de itemização — Rank, Item Power, Prefixos/Sufixos/Infixos, geração universal e bridges com Create/tech/Curios — é **PLANEJADO**, não provider operacional atual.
- Stage 12 planeja **construção tecnológica de corpos**, Body Registry e troca transacional; também não pode ser usado como hook atual enquanto estiver aberto.
- Stage 13 de cartografia/regiões/POI/discovery é planejamento futuro, ainda que possa orientar perks de exploração/engenharia posteriores.

**Regra:** perks tecnológicas já implementáveis usam somente a subtree/boundaries realmente presentes. Sistemas de itemização, corpos e adapters ainda abertos devem permanecer `PENDENTE`/fail-closed no Chat 2 até prova em `main`.

## Enshrouded — sem subsistema tecnológico próprio canônico

[Dossiê completo](../projects/03-enshrouded.md)

Enshrouded possui Shroud, Terrain Corruption, Exposure, Corrupted Ecology e Flame Progression; ele **não é um provider tecnológico genérico**.

- Não converter automaticamente máquinas Create, pressão, energia ou tecnologia em proteção contra Shroud.
- `MutationAuthority`, `ShroudQuery`, `FlamePassageQuery` e demais boundaries continuam Enshrouded-owned.
- Stage 08 Integrations está aberto. Portanto uma futura bridge com sistemas tecnológicos precisa ser nomeada e comprovada antes de entrar em perks.
- Uma máquina/perk não pode purificar terreno, conceder Flame Passage ou criar Sanctuary por inferência temática.

No recorte tecnológico atual, a classificação normal é **NÃO APLICÁVEL**, salvo quando uma perk realmente cruza um boundary público do Enshrouded.

## Black Arcana — magia perigosa não vira tecnologia por associação

[Dossiê completo](../projects/04-black-arcana.md)

Black Arcana possui casting, World Safety e Arcane Danger. Nenhum desses sistemas deve ser convertido automaticamente em FE, stress, pressão, geotermia ou engenharia.

- Arcane Resistance/Corruption Resistance usam providers próprios; equipamentos tecnológicos não contribuem automaticamente.
- Volcanoes heat/respiration/toxicity/pressure são explicitamente excluídos da Arcane Resistance por default.
- A infraestrutura de equipment set bonuses já presente em `main` não transforma Create/AE2/Oritech em provider arcano sem registro explícito.
- Rituals, Spell Domains e Progression & Balance permanecem preparatórios/planejados.

No eixo tecnologia, Black Arcana é normalmente **NÃO APLICÁVEL**. Uma futura perk híbrida Technomancer/arcana precisa declarar dois providers e uma bridge explícita, preservando a autoridade de cada lado.

## Matriz cruzada obrigatória

[Matriz de integração cruzada](../projects/05-cross-project-integration-matrix.md)

Antes de fechar uma perk tecnológica híbrida, registrar:

- qual sistema é autoridade;
- qual provider é apenas consumer/bridge;
- versão/hook realmente disponível;
- recurso consumido e quem o debita;
- identidade de deduplicação;
- fallback/fail-closed;
- comportamento com provider opcional ausente.

Sem esses elementos, a integração não está suficientemente especificada para implementação.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/20-mobstein-boundary.md -->

# 20. Mobstein — boundary: não é provider tecnológico

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

Mobstein possui blocos/dispositivos com apresentação de laboratório — **Clinical Stretch**, **Surgery Stretch** e **Subject Assembly Machine** — mas isso **não o transforma em provider tecnológico** do pack.

A função documentada desses elementos é servir ao sistema de ressurreição corporal, partes/órgãos, experimentos, mannequins e mobs ressuscitados. A versão auditada não prova contrato de FE, SU, rede, automação industrial, processamento Create, AE2 ou Oritech.

### Classificação para perks

No eixo Tecnologia, Mobstein deve ser classificado como **NÃO APLICÁVEL** por padrão ou `SEM HOOK SEGURO` se uma futura perk tentar criar bridge tecnológica sem API comprovada.

É proibido inferir energia, eficiência industrial, throughput, automação ou integração Create/AE2/Oritech apenas porque um bloco possui `Machine` ou `Stretch` no nome ou aparência de laboratório.

Perks sobre ressurreição, corpos/órgãos, experimentos, companions/bodyguards, estruturas e boss devem tratar **Mobstein como provider de Gameplay/Magia**. Uma futura bridge tecnológica só pode existir depois de contrato/API real, authority definida e fail-closed quando o adapter estiver ausente.

**Fonte:** CurseForge oficial, projeto 1193873; release `mobstein-5.4.4-neoforge-1.21.1.jar` de 04/05/2026.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/technology/21-atualizacao-modlist-2026-09-07.md -->

# 21. Atualização da modlist tecnológica — 2026-09-07

## Updates

- Create: Bits 'n' Bobs `2.3.1`;
- Create: Copycats+ `3.0.9`;
- Create: Cyber Goggles `8.5.2`;
- Lychee Tweaker `6.7.0`;
- Petrolpark's Library `1.5.9`.

## Bits 'n' Bobs — decisão curatorial revisada

A decisão vigente é **`Manter`**. A decisão `Tirar` de 06/09 é histórica. O conflito visual com Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 continua real e precisa de render-test; manter ambos não significa que o conflito foi corrigido.

## Boundaries

CERBON's API é dependência de boss content, não provider tecnológico. Ironsable x Wind pertence ao domínio magia↔física/Sable e não é provider tecnológico independente.

## Regra

Update físico não autoriza inferir mudança de API/hook. Para Bits 'n' Bobs, `Manter` é a decisão operacional atual.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/MODLIST-DELTA-2026-09-07.md -->

# Delta físico da modlist — 2026-09-07

> **AUTORIDADE OPERACIONAL DO SNAPSHOT:** a modlist física atual possui **612 entradas top-level**, incluindo NeoForge + **611 JARs**. Dependências `jarjar` embutidas não contam como mods top-level.

## Fechamento quantitativo

- baseline físico de 06/09: **607** entradas top-level;
- updates do mesmo mod: **21**;
- adições reais: **9**;
- remoções físicas reais: **4**;
- saldo líquido: **+5**;
- estado físico atual: **612** entradas top-level;
- Notion reconciliado: **612 `Instalado`**, **612 JARs físicos distintos** e **613/613 registros `Verificado`**;
- runtime version audit: **605/605** JARs que declaram `modVersion` batem exatamente com `Versão 1.21.1` no Notion;
- **7/7** JARs sem `modVersion` físico permanecem sem runtime version inferida no Notion.

## 21 updates

- Alex's Mobs Continued: `2.1.9 → 2.1.10`;
- Amendments: `2.1.9 → 2.1.10`;
- Apotheosis: `8.7.0 → 8.8.0`;
- Ars 'n' Spells: `3.2.4 → 3.3.0`;
- Create: Bits 'n' Bobs: `2.3.0 → 2.3.1`;
- Create: Copycats+: `3.0.8 → 3.0.9`;
- Create: Cyber Goggles: `8.5.1 → 8.5.2`;
- Dynamic Trees - BetterEnd: `2.1.0 → 2.2.0`;
- Dynamic Trees - BetterNether: `2.1.0 → 2.2.0`;
- GTBC's SpellLib: `2.1.0 → 2.2.0`;
- InsaneLib: `2.4.31 → 2.4.32`;
- Lithostitched: `1.8.0+beta4 → 1.8.0+beta6`;
- Lychee Tweaker: `6.6.1 → 6.7.0`;
- MineColonies: `1.1.1376 → 1.1.1377`;
- Moonlight Lib: `3.6.1 → 3.6.3`;
- Petrolpark's Library: `1.5.8 → 1.5.9`;
- Photon: `2.2.5 → 2.2.6.a`;
- Polytone: `4.1.0 → 4.2.0`;
- Puzzles Lib: `21.1.56 → 21.1.59`;
- Supplementaries: `3.9.7 → 3.9.8`;
- Vampirism: `1.10.12 → 1.10.13`.

## 9 adições

- `BOMD-NeoForge-1.21-1.3.3.jar` — Bosses of Mass Destruction;
- `block_factorys_bosses-2.1.2-neo-1.21.1.jar` — Bosses'Rise;
- `CerbonsAPI-NeoForge-1.21-1.3.0.jar` — CERBON's API;
- `IMM v1.1.0-1.21.1.jar` — Integrated Mowzie's Mobs;
- `inventorysorter-1.21.1-24.0.24.jar` — Simple Inventory Sorter;
- `ironsable-wind-1.0.0.jar` — Ironsable x Wind's Spellbooks;
- `letsdo-furniture-neoforge-1.1.4.jar` — [Let's Do] Furniture;
- `mcjadecrops-1.1.1300.jar` — MineColonies: Jade crops;
- `morerelics-1.7.7-1.21.1.jar` — More Relics.

## 4 remoções físicas

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Os antigos bloqueios de discovery por IDs duplicados `alexscaves` e `alexsmobs` estão fisicamente resolvidos no snapshot atual.

## Curadoria vigente

- **Alex's Caves Continued 1.0.9 — Manter**; é a única implementação `alexscaves` presente.
- **Alex's Mobs Continued 2.1.10 — Manter**; é a única implementação `alexsmobs` presente.
- **Create: Bits 'n' Bobs 2.3.1 — Manter**; o conflito visual com Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 continua sendo risco aceito e precisa de render-test.
- **More Relics 1.7.7 — Manter**; o pack usa Relics 0.12.8 apesar do upstream não declarar esse suporte. Integrações/perks provider-specific permanecem **FAIL-CLOSED** até teste real.
- **Integrated Mowzie's Mobs 1.1.0 — Manter**; o runtime local é autoridade física, mas a publicação pública exata dessa build não deve ser inferida.

## Regra

Presença física, filename e runtime version vêm da modlist auditada. Decisão curatorial é uma camada separada. Update de versão não revalida automaticamente hook/API de perk.
