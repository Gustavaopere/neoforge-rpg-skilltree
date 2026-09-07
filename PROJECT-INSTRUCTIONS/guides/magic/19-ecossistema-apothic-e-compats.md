[← Índice do guia](README.md)

# 19. Ecossistema Apothic — módulos, atributos, spawners e compats

> Snapshot do pack: NeoForge 1.21.1, `Apotheosis 8.7.0`. Em 1.21.1, funcionalidades antes agrupadas em Apotheosis também aparecem como módulos/projetos separados. Para integração, é importante identificar **qual módulo é autoridade** antes de escolher um hook.

## Apothic Attributes — 2.10.1

`ApothicAttributes-1.21.1-2.10.1.jar`

**Apothic Attributes** é biblioteca e runtime de atributos usada pelo ecossistema Apotheosis e por outros mods. Ela fornece atributos adicionais, utilidades de manipulação/depuração de atributos e uma GUI acessível pelo inventário que mostra os atributos anexados ao jogador e seus modificadores. Também altera os cálculos relacionados a armor/protection para suportar atributos como armor/protection pierce e shred.

Para nossos mods integrados, isso torna Apothic Attributes um **provider de atributos**, não um simples HUD. Uma perk que mexa em valores já representados por atributos Apothic deve preferir o atributo registrado pelo mod, respeitar operações/UUIDs/modifier lifecycle e evitar recalcular dano por fora do pipeline. A build 2.10.1 requer Placebo; Curios é opcional segundo a metadata pública da versão.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/apothic-attributes) · [Modrinth](https://modrinth.com/mod/apothic-attributes) · [versão 2.10.1](https://modrinth.com/mod/apothic-attributes/version/1.21.1-2.10.1) · [source](https://github.com/Shadows-of-Fire/Apothic-Attributes).

## Apothic Enchanting — 1.6.2

`ApothicEnchanting-1.21.1-1.6.2.jar`

É o módulo de encantamento do ecossistema. Além da mesa/estatísticas de enchanting e conteúdo associado, ele participa de uma camada que pode ser alterada por outros addons do pack, incluindo Create: Enchantment Industry e bridges de encantamento. Para integração, separar **encantamento vanilla**, **estatísticas da mesa**, **affixes/gems de Apotheosis** e **atributos Apothic**: são pipelines diferentes.

A descrição de gameplay permanece no capítulo 8; aqui a função é registrar a arquitetura modular. **Fonte:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/apothic-enchanting) · [busca Modrinth](https://modrinth.com/mods?q=Apothic%20Enchanting) · source no ecossistema Shadows-of-Fire.

## Apothic Spawners — 1.4.0

`ApothicSpawners-1.21.1-1.4.0.jar`

**Apothic Spawners** é o módulo de spawners extraído de Apotheosis. Spawners podem ser movidos com Silk Touch e modificados por receitas/itens para alterar delay mínimo/máximo, quantidade de mobs, limite de entidades próximas, alcance de ativação, spawn range e propriedades adicionais. O projeto também expõe flags como ignore players/conditions/light, redstone control, no-AI, silent, youthful, burning e echoing, conforme a configuração/modificadores disponíveis.

Esse módulo é altamente relevante para balanceamento e anti-abuso: qualquer perk que acelere farms, altere spawn ou multiplique loot deve distinguir mobs gerados por spawner e respeitar os modificadores do próprio spawner. Não é seguro aplicar bônus cegos “por mob morto” sem causalidade, porque o pack pode produzir entidades em grande escala por automação.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/apothic-spawners) · [busca Modrinth](https://modrinth.com/mods?q=Apothic%20Spawners) · [source](https://github.com/Shadows-of-Fire/Apothic-Spawners).

## Apothic Compat / Apothic Category Compat — 2.0.2

`apothic_compat-2.0.2.jar`

A build instalada 2.0.2 é uma bridge de **loot-category** para Apotheosis 8.x. No NeoForge 1.21.1 ela preenche lacunas de categorização de armas modded usando um **NeoForge data map** de overrides. Isso determina em qual categoria Apothic um item entra e, por consequência, que affixes/gem behavior fazem sentido. A versão instalada cobre itens de mods como Cataclysm, Alex's Caves/Mobs, Born in Chaos, Undergarden e Twilight Forest; o projeto posterior passou a usar o nome editorial *Apothic Category Compat*.

No contexto deste pack, essa bridge é particularmente relevante porque há armas de Cataclysm, Iron's e outros ecossistemas. Ela **não cria affixes** por conta própria; corrige o roteamento/categoria para que Apotheosis aplique o pipeline certo. Portanto, não duplicar categorização por código próprio se o data map já resolve o item.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/apothic-category-compat) · [arquivo 2.0.2 NeoForge](https://www.curseforge.com/minecraft/mc-mods/apothic-category-compat/files/8219980) · [Modrinth](https://modrinth.com/mod/apothic-category-compat) · [source](https://github.com/Nightwielder23/apothic-category-compat).

## Apothic Compats — 0.2.4.2

`apothic_compats-0.2.4.2.jar`

**Apothic Compats** é um pacote de compatibilidade data-driven mais amplo. Ele distribui datapacks e integrações para fazer conteúdo de outros mods participar de affixed loot, gear sets, invaders, gems, Curios e categorias específicas. A versão 0.2.4.2 é a release 1.21.1 instalada e declara compatibilidade com vários mods presentes no pack; entre os exemplos oficiais estão **Ars Nouveau**, **Cataclysm**, **Malum**, **Create**, **Curios** e AE2.

Isso é diferente de `apothic_compat`: o Category Compat corrige **roteamento de categoria** de itens; Apothic Compats injeta **conteúdo/integrações data-driven** mais amplas. Os dois podem coexistir e não devem ser deduplicados só pelo nome parecido.

Para integração própria, a regra é “data first”: antes de adicionar affix, gem, loot entry ou categoria por Java/KubeJS, verificar se Apothic Compats já fornece datapack para o mesmo item/mod. Duplicação aqui pode aumentar loot, criar categorias concorrentes ou aplicar gems duas vezes.

**Fontes:** [CurseForge](https://www.curseforge.com/minecraft/mc-mods/apothic-compats) · [arquivo 0.2.4.2](https://www.curseforge.com/minecraft/mc-mods/apothic-compats/files/8483936) · [source](https://github.com/ianm1647/apothic-compats).

## Relações com outros mods mágicos do pack

- **Iron's Apothic** conecta o gear/spells do Iron's ao ecossistema Apothic; tratar como bridge própria.
- **Malum** recebe integração de affixes/gems/scythes/staves por Apothic Compats.
- **Ars Nouveau** recebe affixed loot, gear sets/gems/Curios conforme os datapacks da versão instalada.
- **Create: Enchantment Industry** e addons de encantamento atuam sobre outro pipeline; não confundir enchanting com affix/gem.

## Checklist para integração

1. Identificar se a mudança pertence a **attribute**, **enchanting**, **spawner**, **loot category**, **affix/gem** ou **datapack compat**.
2. Preferir registry/data map/datapack/API do módulo dono do domínio.
3. Verificar se Apothic Compats já cobre o mod/item antes de criar integração paralela.
4. Em perks de combate, deixar o pipeline de atributos/dano calcular uma vez; não reaplicar multiplicadores depois do resultado final.
5. Em spawners/farms, exigir causalidade e anti-abuso explícitos.
