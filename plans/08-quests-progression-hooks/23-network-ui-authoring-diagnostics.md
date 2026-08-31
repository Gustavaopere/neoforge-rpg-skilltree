# 08.23 — Network, UI & Authoring Diagnostics

## Goal
Sincronizar apenas o necessário ao cliente e oferecer ferramentas de authoring capazes de explicar por que uma rota aparece ou não aparece.

## Entregas
- [ ] Server-authoritative snapshots compactos.
- [ ] Não sincronizar ledger/segredos integrais ao cliente.
- [ ] Visibility filtering por player/knowledge.
- [ ] Incremental sync/revision IDs para journal/dialogue context.
- [ ] Debug command para consultar fact/event/relationship/faction/settlement/knowledge por ID.
- [ ] Explain-condition: árvore indicando qual predicado bloqueou choice/beat.
- [ ] Explain-route: por que beat está ACTIVE/PRE_RESOLVED/OBSOLETE/etc.
- [ ] Scheduler diagnostics sem revelar segredos ao jogador comum.
- [ ] Admin-only tooling separado de UI player-facing.
- [ ] Graph export para inspeção de branches e rotas inalcançáveis.
- [ ] Logs estruturados, rate-limited e sem spam por tick.
- [ ] Accessibility/localization para qualquer UI própria futura.

## Segurança
Client não recebe secrets de outros atores apenas porque uma tela pode precisar deles no futuro. O servidor decide opções disponíveis e fornece somente presentation-safe context.

## Acceptance
Um autor consegue diagnosticar uma choice ausente com um comando/relatório determinístico. Um cliente modificado não consegue descobrir secrets server-side pelo pacote de sync normal.