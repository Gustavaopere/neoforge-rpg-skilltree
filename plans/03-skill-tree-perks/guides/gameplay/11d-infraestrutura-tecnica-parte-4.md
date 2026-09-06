# 11D. Infraestrutura técnica — parte 4

[← Índice do capítulo 11](11-infraestrutura-tecnica-interface-visual-e-performance.md)

> Faixa editorial: **Explosive Enhancement: Reforged — 1.1.2** até **InsaneLib — 2.4.31.0**. Cada seção preserva a descrição e a authority do mod correspondente; a divisão é apenas física para manutenção.

## Explosive Enhancement: Reforged — 1.1.2

`explosiveenhancement-neoforge-1.21.1-1.1.2.jar`
**Explosive Enhancement: Reforged** substitui a apresentação visual das explosões por efeitos/partículas mais elaborados sem trocar o cálculo vanilla/modded de dano ou a física central da explosão. Assim, TNT, creepers e explosões produzidas por outros sistemas continuam determinando sua força pelas regras originais, enquanto o feedback visual é retrabalhado.
A build instalada é especificamente o fork **Reforged** `1.1.2`, não o projeto homônimo 1.4.x. A ficha do pack também registra um tempfix dessa release para um crash envolvendo o Creeper Head Projectile de Iron's Spells.

## FerriteCore — 7.0.3

`ferritecore-7.0.3-neoforge.jar`
**FerriteCore** é um conjunto de **otimizações de uso de memória** para Minecraft modded. Em vez de reduzir view distance ou remover conteúdo, ele altera estruturas internas e estratégias de armazenamento para evitar manter representações redundantes em memória; a linha `7.0.3` também reduz memória usada por **data component patches**.
O projeto funciona em cliente e servidor. A build `7.0.3-neoforge` é a release 1.21.1 instalada.

## First-person Model — 2.7.2

`firstperson-neoforge-2.7.2-mc1.21.1.jar`
**First-person Model** substitui a apresentação vanilla de primeira pessoa pelo **modelo de terceira pessoa do próprio jogador**, fazendo torso, pernas e equipamento permanecerem visíveis a partir dos olhos do personagem em vez de mostrar apenas braços separados.
O mod não altera animações nem regras de combate: é puramente visual e client-side, podendo conectar-se a qualquer servidor sem instalação obrigatória no servidor. A build instalada é `2.7.2` para NeoForge 1.21.1.

## Forgified Fabric API — runtime 0.116.15+2.3.5+1.21.1

`forgified-fabric-api-0.116.15+2.3.5+1.21.1.jar`
**Forgified Fabric API (FFAPI)** porta para NeoForge os **hooks, events e APIs essenciais do Fabric API** exigidos por mods Fabric executados através do ecossistema Sinytra Connector. Isso inclui mecanismos de interoperabilidade para registries, partículas, biomas/dimensões, rendering e outras superfícies que muitos mods Fabric assumem como disponíveis.
Ela não é o loader Fabric nem executa mods Fabric sozinha: Connector cuida da camada de compatibilidade de loader e FFAPI fornece as APIs Fabric esperadas pelos consumidores. A build instalada é `0.116.15+2.3.5+1.21.1`.

## Fragmentum — 2.4.4

`fragmentum-neoforge-1.21.1-2.4.4.jar`
**Fragmentum** é o framework leve da **Obscuria Collection**. O projeto concentra ferramentas compartilhadas e uma arquitetura multi-loader para que os mods da coleção mantenham o código de conteúdo separado das diferenças entre plataformas.
O core não adiciona conteúdo quando instalado sozinho; existe porque consumidores dependem de seus serviços comuns. A build NeoForge 1.21.1 instalada é `2.4.4`.

## FTB Library — 2101.1.35

`ftb-library-neoforge-2101.1.35.jar`
**FTB Library** centraliza infraestrutura compartilhada pelos mods FTB, com destaque para **GUI/widgets, utilidades comuns e APIs usadas por FTB Teams, FTB Chunks e outros projetos do ecossistema**. Isso permite que os módulos funcionais compartilhem a mesma linguagem de interface e serviços sem copiar código.
Ela não cria claims ou vein mining sozinha; essas funções pertencem a FTB Chunks e FTB Ultimine. A release top-level instalada é `2101.1.35` para NeoForge 1.21.1.

## FTB Teams — 2101.1.11

`ftb-teams-neoforge-2101.1.11.jar`
**FTB Teams** fornece identidade e persistência de **equipes** para sistemas que compartilham progressão, permissões ou território. Jogadores podem criar teams e configurar propriedades; mods consumidores podem então associar claims, quests, acesso e outros estados à equipe em vez de tratá-los apenas por jogador individual.
No stack atual, FTB Chunks utiliza essa camada para interações e proteção territorial. Os dados das equipes são persistidos no mundo. A build instalada é `2101.1.11` para NeoForge 1.21.1.

## Fusion — 1.3.15

`fusion-1.3.15-neoforge-mc1.21.1.jar`
**Fusion** amplia o formato de resource packs com **connected textures, continuous textures, scrolling textures, overlays e custom entity models**, além de permitir que mods registrem tipos adicionais de textura/modelo. Superfícies podem, por exemplo, conectar bordas entre blocos ou formar uma textura contínua atravessando vários blocos.
Apesar de possuir API para mods, o efeito principal é client-side/renderização de recursos. A build instalada preserva o runtime `1.3.15`.

## Fzzy Config — runtime 0.7.6+1.21+neoforge

`fzzy_config-0.7.6+1.21+neoforge.jar`
**Fzzy Config** é um engine de configuração multiplataforma com **serialização automática, geração de GUI, validação/correção de valores, sincronização servidor↔cliente e atualização versionada de configs**. Mod authors podem definir estruturas de configuração e deixar que a biblioteca construa boa parte da interface e do fluxo de sync automaticamente.
As telas são navegáveis por teclado e possuem suporte a narration; a API também integra ambientes como Catalogue. O runtime instalado é `0.7.6+1.21+neoforge`.

## Gabou's Libs — 1.9

`gaboulibs-neoforge-1.9.jar`
**Gabou's Libs** é a library compartilhada pelos mods de Gaboouu. Ela reúne **sistemas, utilidades e infraestrutura comum**, inclusive serviços usados por projetos ligados a clima, estações e simulação ambiental, para manter comportamento e manipulação de dados consistentes entre consumidores e loaders.
Não adiciona uma mecânica própria quando instalada isoladamente. A build top-level no pack é `1.9` para NeoForge; a linha atual reforça networking/autenticação, corrige registros duplicados de payload e amplia tolerância a respostas atrasadas do desafio de verificação.

## GeckoLib 4 — 4.9.2

`geckolib-neoforge-1.21.1-4.9.2.jar`
**GeckoLib** é uma biblioteca de **modelos e animações 3D** usada por entidades, blocos, itens, armaduras e outros objetos. Ela fornece runtime de animação, controllers, keyframes e integração de modelos para que mods criem movimentos complexos e state-driven sem construir um engine próprio.
Por isso muitos mods de criaturas e bosses dependem dela, mas GeckoLib não adiciona essas criaturas por conta própria. A versão instalada é `4.9.2`, release NeoForge 1.21.1.

## Global Packs — 21.0.6

`globalpacks-neoforge-1.21.1-21.0.6.jar`
**Global Packs** carrega **datapacks e resource packs globais** automaticamente em todos os mundos da instância. Em vez de copiar manualmente um datapack para cada save novo, o pack pode ser colocado nas pastas globais geradas pelo mod e passa a participar do carregamento de cada mundo compatível.
Isso é infraestrutura de distribuição/configuração do modpack; o conteúdo real continua pertencendo aos datapacks/resource packs carregados. A build instalada é `21.0.6` para NeoForge 1.21.1.

## Glodium — runtime 1.21-2.2-neoforge

`Glodium-1.21-2.2-neoforge.jar`
**Glodium** é uma code library do ecossistema GlodBlock voltada a **renderização, networking e registries**. Ela fornece implementações comuns para consumidores que precisam registrar conteúdo, trocar dados e renderizar componentes sem repetir a mesma infraestrutura.
A própria página do projeto ressalta que a library não faz nada útil quando instalada sozinha. O runtime preservado no pack é `1.21-2.2-neoforge`.

## GroovyModLoader — 6.0.2

`gml-6.0.2.jar`
**GroovyModLoader (GML)** é um **language provider** que permite carregar mods escritos em Groovy no ambiente NeoForge. Ele inclui módulos da linguagem Groovy e fornece o `GMLLangProvider`, que reconhece declarações `@GMod` e integra esses projetos ao ciclo de carregamento do modloader.
Não é um mod de scripting de gameplay para o jogador: sua função é permitir que outros mods sejam implementados/carregados em Groovy. A build instalada é `6.0.2`, compatível com 1.21/1.21.1.

## Patchouli — runtime 1.21.1-93-NEOFORGE

`Patchouli-1.21.1-93-NEOFORGE.jar`
**Patchouli** é um framework data-driven para livros e guias in-game. Mods consumidores podem definir capítulos, páginas, categorias e progressão documental sem implementar uma interface de livro própria, tornando a biblioteca parte da infraestrutura de documentação do pack.
A build instalada é a release estável `93` para NeoForge 1.21.1. Alguns livros de consumidores antigos ainda podem usar formatos removidos e serem ignorados, mas isso é um problema do conteúdo desses consumidores, não da identidade ou validade do Patchouli. Sua coexistência com GuideME ou Modonomicon não caracteriza redundância automática, porque cada mod consumidor depende da API para a qual foi construído.

## GuideME — 21.1.17

`guideme-21.1.17.jar`
**GuideME** é um toolkit para criação de **guidebooks in-game**. O conteúdo dos guias pode ser escrito em Markdown e enriquecido com elementos interativos, incluindo **cenas 3D ao vivo**, permitindo que mods e modpacks mantenham documentação integrada à própria interface do jogo.
Ele é particularmente usado no ecossistema AE2, mas a infraestrutura pode ser consumida por outros projetos. A build instalada é `21.1.17` para NeoForge 1.21.1.

## Iceberg — 1.3.2

`Iceberg-1.21.1-neoforge-1.3.2.jar`
**Iceberg** é uma modding library que adiciona **events, helpers e utilities** reutilizáveis para projetos NeoForge/Forge. O objetivo é expor hooks e operações comuns que seriam repetidos em cada mod dependente.
Ela não adiciona conteúdo autônomo ao jogador; recursos visíveis pertencem aos mods consumidores. A build instalada é `1.3.2` para NeoForge 1.21.1.

## Iglee's Library — runtime 1.21.1-1.2.7

`igleelib-1.21.1-1.2.7.jar`
**Iglee's Library** é a dependência compartilhada pelos mods de iglee42, centralizando código reutilizado entre seus projetos. A documentação pública a apresenta explicitamente como a library de toda essa família de mods.
Seu papel é de infraestrutura, sem gameplay próprio quando isolada. O runtime instalado preserva `1.21.1-1.2.7`.

## ImmediatelyFast — runtime 1.6.13+1.21.1

`ImmediatelyFast-NeoForge-1.6.13+1.21.1.jar`
**ImmediatelyFast** otimiza o **immediate mode rendering** do cliente através de buffers e batching mais eficientes. A otimização alcança entidades, block entities, partículas, texto, GUI/HUD e rendering imediato feito por outros mods, reduzindo draw calls/uploads redundantes e principalmente custo de CPU em cenas pesadas.
A versão instalada é `1.6.13+1.21.1`; o projeto é client-side e não altera regras de gameplay. Esta release restaura corretamente o depth-test após flush do DrawContext, corrige exceção ao fechar buffers sem uso e elimina vazamento de file handle da configuração.

## InsaneLib — 2.4.31.0

`insanelib-2.4.31.0.jar`
**InsaneLib** é a biblioteca comum dos mods de Insane96, reunindo **utilitários, configuração e infraestrutura compartilhada** para os consumidores do ecossistema. Ela evita que cada projeto replique serviços técnicos iguais.
Não possui alteração relevante de gameplay instalada sozinha e não deve ser tratada como uma alternativa genérica intercambiável a outras APIs. O runtime atual é `2.4.31.0`.
