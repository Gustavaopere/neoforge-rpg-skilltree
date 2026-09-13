# 19. Atualização da modlist — 2026-09-07

[← Índice do guia](README.md)

> Este capítulo incorpora ao guia Gameplay/Sistemas o delta físico reconciliado em 07/09/2026. A autoridade exata de presença/JAR/runtime é [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md).

## Bosses of Mass Destruction — 1.3.3

`BOMD-NeoForge-1.21-1.3.3.jar`

**Bosses of Mass Destruction** adiciona encontros de bosses de grande porte associados a arenas/estruturas, padrões de ataque próprios, fases e loot. No pack ele entra no mesmo domínio de progressão por combate/exploração ocupado por Cataclysm, Mowzie's Mobs e outros sistemas de bosses, mas não é uma duplicação técnica desses providers.

A build instalada é `1.3.3` para NeoForge 1.21.1. O projeto declara **CERBON's API, Cloth Config API e GeckoLib** como dependências na linha atual. Há histórico upstream de ajuste de hitboxes ligado a Epic Fight; como Epic Fight está presente, validar arenas, hitboxes e balanceamento em combate real antes de usar o mod como base causal de perks.

## Bosses'Rise — 2.1.2

`block_factorys_bosses-2.1.2-neo-1.21.1.jar`

**Bosses'Rise** adiciona bosses em estilo Souls-like, com múltiplas fases, arenas próprias, loot e mobs relacionados aos encontros. A identidade funcional é conteúdo de boss/RPG, não biblioteca nem bridge.

A build física é `2.1.2`; GeckoLib é dependência requerida. A coexistência com Cataclysm, Mowzie's Mobs e Bosses of Mass Destruction deve ser julgada por densidade de estruturas, curva de dificuldade e progressão, não por simples semelhança temática.

## CERBON's API — 1.3.0

`CerbonsAPI-NeoForge-1.21-1.3.0.jar`

**CERBON's API** é biblioteca compartilhada do ecossistema CERBON. No pack atual há consumidor confirmado: Bosses of Mass Destruction. Portanto ela é classificada como **Dependência** e não deve ser promovida a provider de perk, habilidade ou progressão.

A build física é `1.3.0`, mod id `cerbons_api`.

## Integrated Mowzie's Mobs — 1.1.0

`IMM v1.1.0-1.21.1.jar`

**Integrated Mowzie's Mobs** retrabalha a colocação das estruturas do Mowzie's Mobs para melhorar encaixe no terreno e compatibilidade com worldgen modded, usando infraestrutura data-driven do stack Integrated.

O mod depende do Mowzie's Mobs e Integrated API e declara integrações com Amendments, Create, Quark e Supplementaries. A modlist física comprova `1.1.0`, mas a auditoria pública não encontrou correspondência exata dessa build na página oficial durante a reconciliação; não substituir a identidade local por uma versão pública inferida. Por alterar placements, validar novos chunks contra os demais overhauls de worldgen e estruturas.

## Simple Inventory Sorter — 24.0.24

`inventorysorter-1.21.1-24.0.24.jar`

**Simple Inventory Sorter** é QoL de inventário. O comportamento principal é ordenar inventários com clique do meio e mover itens individualmente com a roda do mouse. Não cria progressão ou atributo de personagem.

A build instalada é `24.0.24`. O risco principal no pack é conflito de atalhos/gestos com outros mods de inventário; validar keybinds antes de tratar qualquer comportamento anômalo como incompatibilidade de inventário.

## [Let's Do] Furniture — 1.1.4

`letsdo-furniture-neoforge-1.1.4.jar`

**[Let's Do] Furniture** amplia o ecossistema [Let's Do] com móveis e blocos voltados a interiores, construção e ambientação funcional. A release `1.1.4` inclui compatibilidade de madeiras com módulos relacionados, entre eles Meadow, que já faz parte do pack.

A principal sobreposição é de catálogo com outros mods de furniture/decoração; isso não cria provider mecânico de perk.

## MineColonies: Jade crops — 1.1.1300

`mcjadecrops-1.1.1300.jar`

**MineColonies: Jade crops** é um addon client-side de HUD: adiciona ao Jade informações de crescimento e maturidade das plantações do MineColonies. O projeto lista Jade e MineColonies como dependências e a linha `1.1.1300+` do MineColonies como base; o pack agora usa MineColonies `1.1.1377`, acima desse mínimo.

É uma bridge de apresentação/compatibilidade. Não altera crescimento das crops e não deve ser tratada como provider de agricultura ou progressão.

## Remoções físicas confirmadas

A modlist de 07/09 confirma a remoção física de quatro JARs que ainda apareciam no snapshot anterior:

- `alexscaves-2.0.2.jar` — removido; `alexscaves-1.0.9-neoforge+1.21.1.jar` (Alex's Caves Continued) permanece.
- `alexsmobs-1.22.9.jar` — removido; Alex's Mobs Continued permanece e foi atualizado para `2.1.10`.
- `spore_1.21.1_2.2.0j_neo.jar` — removido.
- `Infnexus-2.0.4-1.21.1.jar` — removido.

Com isso, os bloqueios de discovery por mod IDs duplicados `alexscaves` e `alexsmobs` estão fisicamente resolvidos no inventário atual.

## Updates de interesse para Gameplay

O delta canônico também atualiza, entre outros, Amendments `2.1.10`, Alex's Mobs Continued `2.1.10`, Dynamic Trees BetterEnd/BetterNether `2.2.0`, InsaneLib `2.4.32.0`, Lithostitched `1.8.0+beta6`, MineColonies `1.1.1377`, Moonlight `3.6.3`, Photon `2.2.6.a`, Polytone `4.2.0`, Puzzles Lib `21.1.59`, Supplementaries `3.9.8` e Vampirism `1.10.13`.

## Regra para perks

Nenhum mod novo deste capítulo vira provider automaticamente. Bibliotecas, HUD, bridges e worldgen compatibility continuam não-provider por padrão. Bosses of Mass Destruction e Bosses'Rise podem oferecer eventos/entidades causais úteis para perks, mas hooks concretos devem ser auditados antes de qualquer contrato de Chat 1.
