<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 5. Integrações do Iron's com outros sistemas

## Goety Iron — 3.1

`GoetyIron-1.21.1-NeoForge-3.1.jar`
**Goety Iron** integra Goety e Iron's Spells principalmente pela camada de **servants**. Criaturas e spellcasters do ecossistema Iron's podem ser incorporados à lógica de servos comandáveis do Goety, permitindo que entidades mágicas usem seus atributos e capacidades dentro do sistema de necromancia/controle de minions.
A versão `3.1` adiciona e configura atributos de spellcasting aplicáveis aos servants e inclui correções de compatibilidade. A bridge não cria uma terceira barra de magia: ela faz entidades de Iron's obedecerem às regras de summons e servants do Goety enquanto preservam capacidades relevantes de spellcaster.

## Create: Wizardry — 1.21.1-0.5.1-pre1

`create_wizardry-1.21.1-0.5.1-pre1.jar`
**Create: Wizardry** é uma integração de **magitech** entre Create e Iron's Spells. Ela acrescenta processamento, receitas, automação e conteúdo cruzado para que materiais e componentes mágicos possam participar de linhas mecânicas em vez de depender exclusivamente de crafting manual ou loot.
A bridge aproxima a progressão do Iron's da infraestrutura de fábrica: recursos mágicos entram em operações e cadeias Create, enquanto novos componentes conectam visual e funcionalmente os dois ecossistemas. Isso amplia o papel de máquinas e automação na produção de conteúdo de spellcasting.
O runtime instalado é `1.21.1-0.5.1-pre1`. A build é **pre-release**, característica de maturidade da versão, enquanto a identidade e o arquivo da instalação permanecem definidos.

## IronSable — 1.2.0

`ironsable-1.2.0.jar`
**IronSable** é a bridge entre Iron's Spells e a física de **Sable/Create Aeronautics**. Ela faz spells que aplicam força — como vento, empurrão, puxão e outros efeitos cinéticos — reconhecerem objetos físicos e contraptions em vez de atuarem apenas sobre entidades e coordenadas vanilla.
Com isso, o vetor produzido por um feitiço pode ser traduzido para o sistema físico usado por estruturas móveis. A bridge não adiciona novas escolas nem um segundo motor de física; sua função é permitir que os efeitos já existentes do Iron's produzam respostas coerentes em objetos simulados pelo Sable.

## Immersive Portal - Iron's Spells 'n Spellbooks Addon — 1.0.0

`immersive_portal_irons_spells_n_spellbooks_addon-1.0.0.jar`
Esta bridge adapta o **Portal Spell** do Iron's Spells para criar portais contínuos do **Immersive Portals**. O destino continua sendo definido pela lógica do spell, enquanto a passagem resultante utiliza a renderização/travessia espacial do mod de portais; o tamanho criado pode ser ajustado por configuração.
É uma integração de escopo estreito e depende diretamente de Iron's Spells 'n Spellbooks e Immersive Portals. A primeira release NeoForge 1.21.1 instalada é `1.0.0`, publicada em 27/08/2026.

## Epic Fight & Iron's Spellbook Animation Compat — 3.1.0

`efiscompat-3.1.0.jar`
**Epic Fight & Iron's Spellbook Animation Compat** adapta o casting e outras ações do Iron's Spells ao sistema de animações do **Epic Fight**. Em vez de spells interromperem ou ignorarem a postura do Battle Mode, o addon fornece animações e transições específicas para que conjuração e combate corporal usem a mesma linguagem visual.
A integração é voltada à apresentação e à coerência de ação: ela não altera escolas, mana ou dano dos spells, mas muda como o corpo do jogador executa esses casts quando Epic Fight está ativo. O projeto público também é identificado como **Epic Fight x Iron's Spells: Enhanced Animations**.
O arquivo instalado é `efiscompat-3.1.0.jar`; a publicação equivalente pode aparecer como `efiscompat-3.1.0-neoforge.jar`, enquanto o runtime é `3.1.0`. Essas strings são preservadas separadamente.

## Woodwalkers SpellBooks — runtime 0.3.1-BETA

`woodwalkers_spellbooks-0.3.1-BETA.jar`
**Woodwalkers SpellBooks** integra o sistema de transformação do **Woodwalkers** ao spellcasting de **Iron's Spells 'n Spellbooks**. A bridge adiciona o spell Evocation **Shapeshifting** e remove a necessidade de usar o desbloqueio convencional de formas: lançar o feitiço mirando uma entidade registra aquela criatura como uma forma disponível.
Desbloquear uma nova forma consome **níveis de XP** de acordo com o nível do spell. Depois de registrada, lançar Shapeshifting sem alvo transforma o personagem em sua forma secundária sem novo custo de XP. Durante a transformação continuam disponíveis os keybinds do Woodwalkers para retornar ao normal e usar a habilidade especial da forma.
A duração da transformação escala com o nível do spell e pode ser configurada, inclusive para duração infinita. Também é configurável o custo de XP e se o jogador pode utilizar outros spells enquanto está transformado. A build pública NeoForge 1.21.1 corresponde à versão 0.3.1; a modlist carrega o filename `woodwalkers_spellbooks-0.3.1-BETA.jar`, que é preservado como identificação local.

## Iron's Apothic — 2.2.1

`irons_apothic-2.2.1.jar`
**Iron's Apothic** conecta equipamentos e atributos do Iron's Spells à itemização de **Apotheosis/Apothic**. Staffs, armaduras e outros itens mágicos podem participar de sistemas de **affixes, gems, sockets, atributos e reforging**, fazendo o gear de spellcasting entrar na mesma camada de loot procedural e aprimoramento usada pelo Apotheosis.
A bridge também permite que propriedades relevantes de magia sejam reconhecidas por esse sistema de itemização, em vez de tratar equipamento do Iron's como itens externos sem suporte. Ela depende do Iron's Spells e dos módulos Apothic necessários para affixes e atributos; não introduz um sistema de spellcasting ou de loot independente.

## Reliquified Iron's Spells 'n Spellbooks — 0.2.7

`reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`
**Reliquified Iron's Spells 'n Spellbooks** integra o sistema **Relics** ao Iron's Spells por meio de relics desenhados especificamente para spellcasting. Os acessórios podem responder a mana, escolas, casts e efeitos mágicos, usando a infraestrutura de Relics para criar progressão e passivos ligados a personagens conjuradores.
Como são relics reais, esses itens seguem o modelo de uso e evolução do mod Relics, mas seus gatilhos e benefícios são construídos ao redor de mecânicas do Iron's. O resultado é uma camada de acessórios mágicos persistentes, separada de spellbooks, armaduras e curios convencionais do mod-base.
