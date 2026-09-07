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
