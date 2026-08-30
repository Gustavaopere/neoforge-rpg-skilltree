<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 3. Sobrevivência e condições ambientais

## Cold Sweat — 2.4.2

`ColdSweat-2.4.2.jar`
**Cold Sweat** adiciona um sistema dinâmico de **temperatura corporal** influenciado tanto pelo mundo quanto pelo estado do jogador. Bioma e temperatura ambiental, clima, altitude, blocos próximos, itens carregados ou usados e outras condições entram no cálculo térmico; por isso atravessar uma montanha, uma região gelada, o Nether ou uma área artificialmente aquecida pode exigir preparação diferente.
A sobrevivência térmica possui várias ferramentas próprias. **Armaduras podem receber insulation**, blocos quentes ou frios irradiam temperatura ao redor, o **Thermometer** permite acompanhar valores com precisão e **Waterskins** servem como alívio imediato ou como packs aquecidos/resfriados. A infraestrutura de base inclui dispositivos como **Hearth, Boiler e Icebox**, que permitem condicionar espaços e formar instalações permanentes de aquecimento ou refrigeração.
O sistema é altamente data-driven e configurável por configs, JSON/datapacks e KubeJS. A build instalada `2.4.2` é a release NeoForge 1.21.1 de 22/06/2026; nessa versão, fontes térmicas em objetos **Sable/Create Aeronautics** passaram a emitir temperatura corretamente e a integração de sede foi atualizada para **Thirst Was Reclaimed**, ambos presentes no stack atual.

## Ecliptic Seasons — 0.14.99-beta-2

`EclipticSeasons-1.21.1-neoforge-0.14.99-beta-2.jar`
**Ecliptic Seasons** transforma o calendário do mundo em um sistema sazonal baseado em **24 termos solares**. O avanço do ano altera clima e precipitação, cores de foliage/ambiente, comportamento de ecossistemas e condições de crescimento das culturas, fazendo agricultura e exploração dependerem do período sazonal em vez de permanecerem idênticas o ano inteiro.
A linha 0.14 também trabalha com renderização sazonal e integrações de mundo distante, incluindo ajustes para **Distant Horizons** e Embeddium. O stack instalado possui ainda **Ecliptic Seasons: MultiMod Patch 0.32.0-beta**, que adiciona compatibilidades em código, e **Ecliptic Seasons: Bundles 0.18.0**, que fornece datapacks/resource packs para conteúdo externo.
Ele não mede a temperatura corporal do jogador: essa função continua pertencendo ao Cold Sweat. A build `0.14.99-beta-2` é a versão NeoForge 1.21.1 instalada; o sufixo beta descreve maturidade da release, não dúvida sobre identidade ou presença.

## Snow! Real Magic! — runtime 12.2.2+neoforge

`SnowRealMagic-1.21.1-NeoForge-12.2.2.jar`
**Snow! Real Magic!** expande o comportamento físico das snow layers. A neve pode cobrir **slabs, stairs, fences, vegetação e outras superfícies não cúbicas**, acumular com maior naturalidade durante nevascas e formar camadas que respeitam melhor a geometria do bloco subjacente.
O mod também adiciona regras configuráveis para acumulação, queda/movimento das camadas e interação com blocos cobertos. Assim, Ecliptic Seasons determina o contexto sazonal/climático em que a neve aparece, enquanto Snow! Real Magic! determina **como a neve depositada ocupa e reage ao mundo**. A build `12.2.2` é a release NeoForge 1.21.1 instalada, cujo runtime declara `12.2.2+neoforge`.

## Nutritional Balance — runtime 1.21.1-7.0.3

`nutritionalbalance-1.21.1-7.0.3.jar`
**Nutritional Balance** adiciona um sistema persistente de dieta no qual alimentos fornecem **Nutritional Units (NUs)** distribuídas entre nutrientes. Tooltips mostram os nutrientes de cada comida e uma GUI própria, aberta por padrão com `N`, exibe o nível atual de cada grupo, faixas-alvo e limites de malnutrition/engorgement.
Manter todos os nutrientes em suas faixas ideais concede buffs configuráveis — como benefícios de vida, velocidade ou eficiência — enquanto permanecer abaixo ou acima dos limites pode aplicar debuffs. **Sugar** funciona como nutriente não essencial, sem penalidade por deficiência, enquanto vegetables possuem comportamento especial que não pune excesso.
O sistema é data-driven: nutrientes usam tags e o mod percorre recipes para inferir o valor de comidas compostas a partir de seus ingredientes, permitindo incorporar alimentos vanilla e modded sem exigir uma tabela manual para cada prato. Buffs, debuffs, thresholds e integrações podem ser ajustados por datapack/config.
A versão `7.0.3` para NeoForge 1.21.1 também reestruturou a construção/traversal de nutrientes para reduzir travamentos em modpacks muito grandes.

## Thirst Was Reclaimed — runtime 1.21.1-3.0.4

`ThirstWasReclaimed-1.21.1-3.0.4.jar`
**Thirst Was Reclaimed** é o provider de **sede/hidratação** do pack. Ele adiciona uma necessidade separada da fome, com formas próprias de consumir água e regras de qualidade/pureza que fazem fontes de hidratação terem valores diferentes em vez de toda água ser equivalente.
O sistema é client+server e a release pública atual do ramo 1.21.1 é `3.0.4`. O metadata local preserva a string `1.21.1-3.0.4` como runtime. As correções e integrações adicionais ficam no addon Thirst Was Fixed, não no core.

## Thirst Was Fixed — 2.1.5

`thirstwasfixed-2.1.5.jar`
**Thirst Was Fixed** é a camada de correções e integrações do Thirst Was Reclaimed. Ele trata estados de pureza em cauldrons, permite configurar pureza de água de chuva/dripstone, beber de cauldrons e aplicar benefícios quando a barra de sede está cheia.
Também conecta sede a sistemas instalados: **Ars Nouveau Potion Flasks** podem restaurar hidratação, a Urn of Endless Waters do Ars Elemental pode interagir com água/pureza, FTB Ultimine pode exigir sede mínima, ParCool pode alterar regeneração de stamina conforme hidratação e Amendments pode purificar água em cauldrons aquecidos. A build instalada `2.1.5` foi publicada em 24/08/2026.

## Sophisticated Thirst Upgrade — 0.1.8

`sophisticated-thirst-upgrade.jar`
**Sophisticated Thirst Upgrade** leva a hidratação para o sistema de upgrades do Sophisticated Backpacks. O upgrade monitora itens armazenados na mochila capazes de restaurar sede e pode **consumi-los automaticamente**, de maneira análoga aos upgrades automáticos de alimentação do ecossistema Sophisticated.
Ele depende da mochila e do sistema de sede existente; não cria uma barra própria. O runtime instalado é `0.1.8`.

## Ecliptic Seasons: MultiMod Patch — 0.32.0-beta

`Ecliptic-Seasons-MultiMod-Patch-1.21.1-neoforge-0.32.0-beta.jar`
**MultiMod Patch** adiciona integrações em código para que outros sistemas reconheçam corretamente o calendário e as condições sazonais do Ecliptic Seasons. No stack atual, a cobertura pode atingir sistemas como **Cold Sweat, Dynamic Trees, MineColonies, JourneyMap** e outros mods suportados pela versão instalada.
Ele não cria estações novas: traduz o estado sazonal do Ecliptic Seasons para APIs e mecânicas de outros mods. A release instalada é `0.32.0-beta` para NeoForge 1.21.1.

## Ecliptic Seasons: Bundles — 0.18.0

`EclipticSeasons-Bundles-0.18.0.jar`
**Ecliptic Seasons: Bundles** complementa o patch em uma camada de **datapacks e resource packs**. Ele fornece dados sazonais para crops, vegetação, biomas e conteúdo de mods suportados, permitindo que recursos externos recebam regras e representação coerentes com as estações.
Portanto Bundles e MultiMod Patch não são duplicatas: um concentra dados/recursos e o outro integra lógica em código. O runtime atual é `0.18.0`.

## Puddles & Floods — 1.1.5

`puddleflood-1.1.5+1.21.1-neoforge.jar`
**Puddles & Floods** adiciona puddles que surgem e acumulam durante chuva, podem se conectar em formas irregulares e fazem cursos d'água parecerem **transbordar visualmente suas margens**. As poças evaporam conforme as condições e podem usar a água do shader quando configurado.
Quantidade de cobertura, raio de geração ao redor do jogador, velocidade de coleta/evaporação e comportamento de conexão com água são configuráveis. A maior parte da camada visual pode funcionar no cliente; a build `1.1.5` é a release NeoForge 1.21.1 de 08/07/2026.
