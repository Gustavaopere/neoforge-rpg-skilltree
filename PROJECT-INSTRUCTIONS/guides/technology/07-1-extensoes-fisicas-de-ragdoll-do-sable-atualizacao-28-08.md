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
