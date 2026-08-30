<!-- Snapshot auditável reorganizado. Fonte canônica: https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03 | referência da modlist: modlist 28.08.26.txt -->

[← Índice do guia](README.md)

# 9. Bibliotecas e infraestrutura mágica

## RunicLib — 5.0.7

`neoforge-runiclib-1.21.1-5.0.7.jar`
**RunicLib** é uma biblioteca multi-loader usada principalmente pelos projetos do ecossistema **Azurune**, mas exposta para outros desenvolvedores. Ela não adiciona uma progressão mágica autônoma; fornece infraestrutura comum para mods que precisam compartilhar código entre loaders e registrar sistemas reutilizáveis.
Entre as funções públicas estão um método comum de registry para projetos MultiLoader, utilidades como tratamento comum de flammability e **conditional recipes**, classes públicas compartilhadas e um conjunto de **effects/effect classes** pré-prontos. A biblioteca também concentra atributos e helpers consumidos por mods dependentes.
A instalação atual é `5.0.7` para NeoForge 1.21.1. Sua presença no guia representa infraestrutura técnica do stack mágico, não uma escola, spellbook ou árvore de progressão jogável.

## Ace's Spell Utils — 1.2.7.1

`aces_spell_utils-1.2.7.1-1.21.1.jar`
**Ace's Spell Utils** é uma API de suporte para addons de Iron's Spells, mas fornece uma quantidade considerável de infraestrutura reutilizável. Ela inclui classes-base para armas com habilidades ativas/passivas, magic swords, magic guns, loot bags, spellbooks passivos e itens com cooldowns próprios.
A biblioteca também registra atributos como **Mana Steal, Mana Rend, Spell Resistance Penetration, Magic Crit Chance/Damage, projectile crits, Life Recovery e Vigor Reap**. No lado de spellcraft oferece escolas utilitárias como Occult, Hydro e Technomancy, animações de casting e classe abstrata para summons; ainda possui helpers para boss music, shaders de pós-processamento e renderização emissiva de armaduras GeckoLib.

## GTBC's SpellLib — runtime 2.0.0-1.21.1

`gtbcs_spell_lib-2.0.0-1.21.1.jar`
**GTBC's SpellLib/API** é a biblioteca comum usada pelos addons de Iron's Spells do GameTechBC. Ela não adiciona uma progressão jogável independente: concentra classes, atributos, helpers e estruturas reutilizáveis para que os addons do autor compartilhem a mesma implementação de spells, itens e mecânicas.
A linha 2.0 amplia essa infraestrutura com recursos como atributo de **Elemental Permeability**, interfaces utilitárias para preservação localizada de inventário/XP/Curios em contextos específicos, framework de paintings customizadas e componentes reutilizáveis como Treasure Pouch. No pack, **GTBC's Geomancy Plus** é um consumidor direto dessa API.

## HazentouveLib — 1.0.9

`hazentouvelib-1.0.9.jar`
**HazentouveLib** é uma biblioteca para os mods e addons de Hazen e não adiciona conteúdo jogável por conta própria. Ela centraliza três escolas reutilizáveis — **Radiance, Shadow e Cosmic** —, classes-base para entidades spellcasters, o efeito **Hexed** e abstrações de itens mágicos, armaduras e maces/staffs.
Também fornece tiers e combinações de armor helpers, raridades ligadas a escolas, cinco keybinds reutilizáveis para habilidades e a abstração `AbstractTaggedSpell`. Essas APIs são consumidas por projetos como **Hazen N Stuff**. A build atual do pack, `1.0.9`, é a release NeoForge 1.21.1 publicada em 16/08/2026.

## FamiliarsLib — runtime 1.21.1-1.7

`familiarslib-1.21.1-1.7.1.jar`
**FamiliarsLib** contém o núcleo técnico extraído de Alshanex's Familiars. A API permite registrar **familiars próprios**, beds/casas de familiars, blocos de armazenamento ligados a essas criaturas e até spells associados à **Sound school**, de modo que addons possam criar companions compatíveis sem copiar a implementação do mod principal.
Também concentra comportamento compartilhado de familiars e infraestrutura usada pelo addon Alshanex's Familiars; atualizações da linha 1.7 incluíram correções de beds/healing e estabilidade de servidor. O filename/publicação é `1.7.1`, enquanto o metadata runtime da instância declara `1.21.1-1.7`; as duas strings permanecem separadas.

## Iron's Lib — runtime 1.21.1-2.1.0

`irons_lib-1.21.1-2.1.0.jar`
**Iron's Lib** reúne frameworks reutilizáveis para os mods do Iron431. A camada de **transmogs** permite aplicar modelos únicos a armaduras, construir interfaces customizáveis de armor e lidar automaticamente com cape physics; o framework de **statues** cobre geração dinâmica de estátuas de jogador, estruturas multibloco e renderização estática.
A biblioteca também implementa atributos RPG leves como **Armor Pierce, Mining Speed, Experience Gained, Arrow Damage, Crit Damage, Dodge Chance e Healing Received**. Um Attribute Remapper permite trocar atributos de itens dinamicamente e possui compatibilidade nativa com Apothic Attributes, com pontos de extensão tanto por código quanto por datapacks. Há ainda uma camada comum de integração Patreon usada pelos projetos do mesmo ecossistema.
