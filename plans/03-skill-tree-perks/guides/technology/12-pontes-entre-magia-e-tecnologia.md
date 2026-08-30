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
