# QST-#### — Título

## Estado editorial
RASCUNHO

## Tipo

## Premissa player-safe

## Participantes

## Availability / eligibility
Quando a oportunidade pode existir/oferecer rotas. Não confundir disponibilidade com discovery.

## Discovery
- estado inicial: UNKNOWN | RUMORED | DISCOVERED | CONFIRMED, quando aplicável;
- canais legítimos:
- knowledge/evidência exigidos:
- descoberta antecipada/tardia:

Conteúdo elegível e desconhecido não deve aparecer automaticamente no journal.

## Engagement
O que conta como envolvimento real. Aceitar uma proposta não deve ser o único tipo possível de participação.

## Preconditions
Condições necessárias para ações/choices específicas dentro da oportunidade.

## Entry points alternativos
Rotas legítimas quando jogador chega por rumor, evidência, local, NPC diferente, consequência anterior ou objetivo já concluído.

## Prior-event reconciliation / ANTES
Como reconhecer fatos/eventos anteriores sem resetar o mundo, respawnar alvo ou repetir discovery artificialmente.

## SIM / NÃO / ANTES / DEPOIS
- SIM — envolvimento/aceitação possível:
- NÃO — recusa, ignorar, abandonar ou escolher outra prioridade:
- ANTES — estado relevante já ocorreu antes do encontro/oferta:
- DEPOIS — consequências/callbacks posteriores:

Nunca registrar “falha retroativa” para oportunidade que o jogador nunca descobriu.

## Choices / decisões relevantes
Para cada decisão que realmente produz estado persistente:
- disponibilidade/explicação da choice;
- knowledge/relação/lei/provider necessários;
- consequência imediata solicitada;
- consequência agendada possível;
- estados que transforma/fecha/abre.

Não preencher choices cosméticas apenas para simular branching.

## Progressão autônoma
- atores que podem agir sem o jogador:
- causas/gatilhos:
- estados/eventos que podem mudar:
- estados que NÃO avançam apenas porque “tempo passou”:

## Invalidation/expiry
Deadline/expiry somente quando design explícito e causalmente justificável. Evitar timers invisíveis indiscriminados.

## Descoberta retrospectiva
Como o jogador pode descobrir depois que a oportunidade foi resolvida por terceiros, transformada ou tornou-se obsoleta, sem receber retroativamente a missão original.

## Journal visibility
Separar eligibility, discovery, engagement e resolution. Declarar quando/por que algo aparece, muda ou some do journal.

## Evidências/knowledge
- evidências `EVD-####` relacionadas:
- fatos/knowledge necessários:
- canais alternativos:
- o que NÃO pode ser conhecido automaticamente:

Evitar single point of failure quando semanticamente possível.

## NPC death / indisponibilidade fallback
Como a oportunidade reage se participante importante morrer, desaparecer, for preso/exilado, retornar diferente ou nunca for conhecido.

Corpo disponível após retorno não satisfaz automaticamente gates de memória, knowledge ou identidade.

## Objective already completed / PRE_RESOLVED
Como reconhecer objetivo/estado atingido antes da descoberta/oferta. Não respawnar artificialmente alvo nem duplicar recompensa.

## Resolution
Usar a taxonomia Stage 08 quando aplicável:
- UNRESOLVED
- SUCCESS
- SUCCESS_WITH_COST
- PARTIAL_SUCCESS
- FAILURE
- PRODUCTIVE_FAILURE
- ABANDONED
- RESOLVED_BY_OTHERS
- PRE_RESOLVED
- OBSOLETE
- TRANSFORMED

Nem toda oportunidade precisa usar todos os estados.

## Failure-forward
O que falha, recusa ou perda legítima pode abrir, transformar, fechar ou tornar mais difícil sem reduzir tudo a game over narrativo.

## Recompensas/consequências
- consequências imediatas:
- consequências agendadas:
- reward issuance key conceitual/one-shot:
- condições que cancelam/transformam consequência futura:

Texto editorial solicita intents; Narrative Core valida/muta estado real.

## Providers
- provider/capability real necessária:
- fonte técnica:
- fallback quando provider for opcional/indisponível:

Lore não cria capability mecânica.

## Idempotência/deduplicação
- cleanup/idempotency key conceitual:
- fatos/eventos que não podem duplicar em replay:
- efeitos one-shot:

## Anti-soft-lock
- fallbacks de informação/ator:
- estados de perda legítima:
- condições que tornam rota impossível de forma deliberada:

Anti-soft-lock não significa garantir que todo conteúdo permaneça sempre acessível.

## Arcos/entidades relacionadas
- arcos:
- NPCs:
- facções:
- assentamentos/locais:
- eventos:
- evidências:

## Invariantes

## QA
- [ ] eligibility, discovery, engagement e resolution estão separados;
- [ ] UNKNOWN não aparece no journal só por ser elegível;
- [ ] SIM/NÃO/ANTES/DEPOIS foram considerados;
- [ ] prior-event reconciliation reconhece objetivos prévios quando aplicável;
- [ ] não participação não virou falha retroativa fictícia;
- [ ] progressão autônoma possui ator/causa;
- [ ] actor-death fallback/identity continuity foram considerados;
- [ ] failure-forward existe quando semanticamente útil;
- [ ] rewards/effects one-shot possuem dedup/idempotência;
- [ ] provider capability foi comprovada ou ficou não vinculada;
- [ ] perda legítima de conteúdo não foi mascarada como soft-lock técnico.

## Spoilers internos
