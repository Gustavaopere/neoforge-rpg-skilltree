# 08.25 — Opportunity & Discovery Lifecycle

## Goal
Formalizar como regra universal que conteúdo narrativo pode existir no mundo sem nunca ser conhecido, oferecido ou vivido pelo jogador. Distinguir claramente oportunidade, descoberta, oferta, engajamento e resolução para evitar o falso pressuposto de que toda quest elegível precisa aparecer no journal.

## Princípio
Não participar também é um estado narrativo legítimo, inclusive quando o jogador nunca descobre que havia algo do qual participar.

O engine não deve confundir:
- conteúdo ainda impossível de ocorrer;
- conteúdo possível, mas desconhecido;
- rumor sem confirmação;
- conteúdo descoberto sem oferta formal;
- oferta recebida e ignorada;
- oferta recusada;
- missão aceita e abandonada;
- objetivo resolvido antes de existir uma quest;
- oportunidade resolvida por terceiros;
- oportunidade invalidada/expirada sem o jogador jamais saber;
- descoberta retrospectiva apenas das consequências.

## Estado ortogonal
Não usar um único enum `quest_status`. Cada beat/oportunidade deve poder expressar e persistir eixos independentes.

### Availability
- `LOCKED` — requisitos para a oportunidade ainda não existem.
- `ELIGIBLE` — o mundo permite que a oportunidade exista/avance.
- `INVALIDATED` — fatos posteriores tornaram a oportunidade impossível.
- `EXPIRED` — janela explícita terminou quando o design realmente possuir prazo.

### Discovery
- `UNKNOWN` — jogador não possui conhecimento da oportunidade.
- `RUMORED` — ouviu indícios, rumores ou informação incompleta.
- `DISCOVERED` — sabe que há algo investigável/interativo.
- `CONFIRMED` — confirmou o fato/ameaça/pessoa/ocorrência por fonte suficiente.

### Engagement
- `NOT_OFFERED` — nenhum ator fez oferta formal.
- `OFFERED` — proposta/quest foi apresentada.
- `ACCEPTED` — jogador aceitou responsabilidade/objetivo.
- `DECLINED` — recusou explicitamente.
- `ABANDONED` — aceitou e depois abandonou quando o conteúdo suporta essa ação.

### Resolution
Usar a taxonomia canônica do Choice & Consequence Engine, incluindo `UNRESOLVED`, `SUCCESS`, `SUCCESS_WITH_COST`, `PARTIAL_SUCCESS`, `FAILURE`, `PRODUCTIVE_FAILURE`, `RESOLVED_BY_OTHERS`, `PRE_RESOLVED`, `OBSOLETE` e `TRANSFORMED`.

Os eixos podem coexistir em combinações que seriam impossíveis num status único, por exemplo:
- `ELIGIBLE + UNKNOWN + NOT_OFFERED + UNRESOLVED`;
- `ELIGIBLE + RUMORED + NOT_OFFERED + UNRESOLVED`;
- `ELIGIBLE + DISCOVERED + OFFERED + DECLINED + UNRESOLVED`;
- `ELIGIBLE + DISCOVERED + ACCEPTED + RESOLVED_BY_OTHERS`;
- `INVALIDATED + UNKNOWN + NOT_OFFERED + OBSOLETE`.

## Discovery channels
Conteúdo pode ser descoberto por canais distintos, todos data-driven:
- diálogo direto;
- rumor de NPC;
- testemunha;
- item/documento/livro;
- estrutura/local descoberto;
- evento observado;
- evidência física;
- provider query legítima;
- consequência visível;
- comunicação/facção;
- investigação ativa;
- encontro acidental antes de qualquer quest.

A origem da descoberta deve ser preservada quando relevante para diálogos e credibilidade.

## Regra de não onisciência
FTB Quests, Easy NPC e demais adapters não devem revelar conteúdo `UNKNOWN` apenas porque o runtime sabe que a oportunidade existe. O journal só apresenta informação que o ator/player legitimamente conhece.

Não mostrar mensagens como “quest perdida” para conteúdo que nunca foi descoberto. Diagnostics/admin podem enxergar esse estado; o jogador não.

## Mundo continua sem o jogador
Oportunidades podem evoluir enquanto continuam `UNKNOWN` para o player quando o design declarar agentes/causas autônomas. Exemplos:
- NPC muda de região;
- outra facção intervém;
- alvo morre;
- investigação é concluída por terceiros;
- ameaça cresce;
- evento deixa evidências;
- oportunidade se transforma em outra;
- o jogador descobre apenas o resultado muito tempo depois.

Não usar progressão autônoma arbitrária em todo conteúdo; ela precisa ser explicitamente declarada e determinística/observável para debugging.

## Exemplo canônico — Severin
Em uma fase avançada, o mago da corte pode sentir energia sombria e criar uma oportunidade de investigação. Rotas válidas incluem:

1. requisitos nunca satisfeitos → `LOCKED + UNKNOWN`;
2. requisitos satisfeitos, mas jogador nunca fala com o mago → `ELIGIBLE + UNKNOWN`;
3. jogador escuta rumor, mas não investiga → `ELIGIBLE + RUMORED`;
4. mago oferece a investigação e jogador recusa → `OFFERED + DECLINED`;
5. jogador aceita e nunca vai → `ACCEPTED + UNRESOLVED`, com consequências apenas se designadas;
6. jogador vai ao local, mas não encontra Severin → descoberta parcial/evidência sem contato;
7. jogador encontra Severin antes da missão → alternate entry / `PRE_RESOLVED` ou transformação apropriada;
8. encontra e ignora;
9. conversa e se alia;
10. torna-se inimigo;
11. mata Severin;
12. terceiros encontram/expulsam/matam/recrutam Severin antes do jogador;
13. Severin parte sozinho;
14. jogador nunca soube da oportunidade e só descobre meses depois que “um necromante foi executado”. Nesse caso ele adquire knowledge do fato histórico, não uma quest retroativa fictícia.

## Requisitos de authoring
Todo beat significativo deve declarar, quando aplicável:
- eligibility conditions;
- discovery conditions e channels;
- offer conditions e offering actor;
- alternate discovery/entry;
- autonomous progression policy;
- invalidation/expiry conditions;
- retrospective discovery hooks;
- visibility policy no journal;
- reconciliation quando objetivo já ocorreu;
- idempotency keys por transição.

## Multiplayer
Discovery/engagement pode ser player-scoped, team-scoped ou world-scoped de forma explícita. Um jogador pode conhecer Severin enquanto outro ainda está `UNKNOWN`. Compartilhamento de knowledge deve passar por regras próprias, não por sincronização automática de toda a quest.

## Acceptance
- Conteúdo `ELIGIBLE + UNKNOWN` não aparece no journal nem em diálogos sem condição de descoberta.
- Recusar uma oferta é distinguível de nunca recebê-la.
- Ignorar uma missão aceita é distinguível de abandonar explicitamente.
- Uma oportunidade pode tornar-se `OBSOLETE` ou `RESOLVED_BY_OTHERS` enquanto permanece desconhecida.
- O jogador pode descobrir somente a consequência posterior sem receber retroativamente a missão perdida.
- Encontrar/resolver o alvo antes da oferta reconcilia via alternate entry/`PRE_RESOLVED` sem respawn artificial.
- Dois jogadores podem ter discovery states diferentes para a mesma oportunidade sem corromper o world state.
