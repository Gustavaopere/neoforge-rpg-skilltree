# 08.08 — Narrative Domain, Invariants & Authority

## Goal
Congelar o domínio e as fronteiras do Narrative & Society Core antes de persistence/adapters.

## Entregas
- [ ] Definir scopes canônicos: world, player, settlement, faction, NPC, region, arc/quest e provider-bound.
- [ ] Definir IDs estáveis para actors, settlements, factions, institutions, facts, events, laws, arcs e consequences.
- [ ] Definir authority matrix entre RPG Core, Narrative Core e providers externos.
- [ ] Proibir scoreboards/tags como storage canônico.
- [ ] Proibir FTB Quests/Easy NPC/KubeJS como authority.
- [ ] Definir optional-adapter policy e fail-soft de classloading.
- [ ] Definir tri-state `KNOWN/UNKNOWN/UNAVAILABLE` para queries externas que precisem distinguir ausência de false.
- [ ] Definir causal actor e dedup identity como campos obrigatórios para eventos mutáveis.
- [ ] Definir diferença entre event, fact, knowledge, evidence, relationship change e consequence.
- [ ] Definir política multiplayer: decisões pessoais, team/party, settlement e world-global.
- [ ] Definir security boundary server-authoritative.

## Invariantes
1. Uma causa narrativa produz uma mutação canônica por pipeline.
2. Client/UI nunca autoriza estado.
3. Provider externo continua autoridade de sua mecânica.
4. Ausência de adapter não cria verdade positiva.
5. Reload inválido não destrói snapshot anterior.
6. Nenhuma feature contínua gera ledger por tick.
7. IDs de conteúdo são namespaced e versionáveis.

## Acceptance
Um teste de arquitetura deve demonstrar que o jar base carrega sem Easy NPC, FTB Quests, MineColonies e KubeJS, enquanto o domínio ainda consegue persistir e consultar estado narrativo próprio.