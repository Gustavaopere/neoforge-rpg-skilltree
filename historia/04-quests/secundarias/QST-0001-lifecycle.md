# Lifecycle editorial de QST-0001 — Ecos Sombrios

## Estado editorial
RASCUNHO

## Escopo
Contrato auxiliar de autoria para o lifecycle de `QST-0001`. Não adiciona solução, culpado ou outcome preferencial e não substitui o dossiê principal da quest.

## Autoridade
Este arquivo descreve como o conteúdo editorial de `QST-0001` mapeia para o contrato de `plans/08-quests-progression-hooks/25-opportunity-discovery-lifecycle.md`. O Stage 08 continua autoridade sobre enums, persistence, idempotency e runtime.

## Princípio
`QST-0001` não possui um único status. Availability, discovery, engagement e resolution evoluem de maneira independente. O mundo pode resolver ou transformar a situação enquanto o jogador continua `UNKNOWN`.

## Availability

### LOCKED
Oportunidade ainda não pode existir de forma significativa porque requisitos de mundo/progressão não foram satisfeitos.

Regras:
- não aparece em journal;
- `NPC-0003` não comenta o caso como se ele existisse;
- rumores específicos da oportunidade não são injetados artificialmente;
- requisitos concretos permanecem data-driven e só podem usar providers/mecânicas reais.

### ELIGIBLE
O mundo já permite que a situação exista e avance.

`ELIGIBLE` não significa:
- que o jogador sabe de algo;
- que `NPC-0003` já possui evidência;
- que alguém fez uma oferta;
- que `NPC-0001` foi localizado ou identificado.

### INVALIDATED
Fato posterior torna esta oportunidade específica impossível sem necessariamente representar fracasso. Exemplos editoriais possíveis: contexto que originava a investigação deixa de existir ou a situação se transforma em outro conteúdo.

### EXPIRED
Só usar se for criado um prazo ficcional explícito, causal e observável. Não existe expiry apenas porque tempo real passou ou porque o jogador fez outras coisas.

## Discovery

### UNKNOWN
Estado inicial legítimo mesmo quando availability já é `ELIGIBLE`.

### RUMORED
Pode ser alcançado por `EVD-0001` ou outro rumor legítimo. O jogador sabe que há relatos, não que existe uma quest ou uma explicação correta.

### DISCOVERED
Pode ser alcançado por rotas como:
- análise suficiente de `EVD-0002`;
- descoberta/inspeção de `EVD-0003`;
- investigação ativa independente;
- encontro acidental com situação relevante antes de qualquer oferta;
- comunicação legítima de `NPC-0003` quando ele próprio possuir knowledge suficiente.

### CONFIRMED
Confirma a existência de um fato/ocorrência investigável por fonte suficiente. Não equivale a confirmar culpa, identidade, motivação ou provider.

## Engagement

### NOT_OFFERED
Estado válido mesmo com `DISCOVERED`/`CONFIRMED`. O jogador pode investigar por conta própria.

### OFFERED
Offering actor no desenho atual: `NPC-0003`.

`NPC-0003` pode oferecer envolvimento somente se:
- ele possui knowledge/proveniência suficiente para justificar a abordagem;
- sua agenda/estado permite a ação;
- a oportunidade continua semanticamente relevante;
- nenhuma condição de mundo já reconciliou a situação como pre-resolved/obsoleta/transformada.

A presença de um quest marker não cria a oferta.

### ACCEPTED
O jogador aceita responsabilidade/objetivo, mas o mundo continua autônomo. Aceitação não congela NPCs, local ou terceiros.

### DECLINED
Recusa explícita. Não equivale a nunca receber oferta e não exige punição automática.

### ABANDONED
Só existe se houver ação explícita de abandono suportada pelo design. Parar de jogar ou fazer outra atividade não é automaticamente abandono.

`ABANDONED` pertence ao eixo Engagement. Resolution permanece independente.

## Resolution

### UNRESOLVED
Situação ainda aberta independentemente de discovery/engagement.

### PRE_RESOLVED
Usar quando uma ação válida do jogador ocorre antes da oferta/quest formal. Não respawnar, resetar ou repetir artificialmente o mundo para caber no roteiro.

### RESOLVED_BY_OTHERS
Terceiros resolvem a situação por causa explícita. O jogador pode continuar `UNKNOWN` e descobrir o resultado apenas depois.

### OBSOLETE
A oportunidade deixa de fazer sentido sem converter desconhecimento/recusa em `FAILURE` fictício.

### TRANSFORMED
A situação muda de natureza e deve continuar em outro beat/quest/evento, preservando a cadeia histórica.

### Demais outcomes
`SUCCESS`, `SUCCESS_WITH_COST`, `PARTIAL_SUCCESS`, `FAILURE`, `PRODUCTIVE_FAILURE` só devem ser aplicados quando ações e critérios concretos forem escritos; este arquivo não escolhe outcome preferencial.

## Scope multiplayer do rascunho atual

- availability scope: `WORLD` enquanto depender apenas de fatos de mundo globais;
- discovery scope: `PLAYER` por padrão;
- engagement scope: `PLAYER` por padrão para oferta/aceitação individual de `NPC-0003`;
- resolution scope: `WORLD` para o estado objetivo da situação;
- knowledge sharing: nunca automático; `TEAM` só entra por regra/canal explícito de compartilhamento;
- recompensa/ledger pessoal, quando existir, não deve ser confundido com resolution world-scoped.

Se requisitos futuros tornarem availability ou engagement realmente team/player-scoped de outra forma, o contrato deve ser alterado explicitamente em vez de herdar o scope silenciosamente.

Dois jogadores podem, portanto, observar o mesmo estado de mundo com discovery/engagement diferentes.

## Política de progressão autônoma
Progressão autônoma é permitida, mas nunca por tick ou timer oculto genérico.

Uma transição autônoma precisa declarar:
1. ator/causa;
2. precondição observável/consultável;
3. milestone/evento único no ledger quando aplicável;
4. resultado determinístico para o mesmo estado de entrada;
5. evidências/consequências que possam sobreviver à ausência do jogador quando semanticamente adequado.

Causas editoriais admissíveis incluem:
- um ator decide investigar por conta própria;
- um ator muda de região por agenda própria;
- terceiro interfere;
- uma evidência é removida/destruída por causa concreta;
- fatos posteriores transformam a oportunidade.

Não admitir como causa suficiente:
- "passou tempo demais" sem prazo ficcional;
- jogador não abriu o journal;
- jogador estava em outro mod/sistema;
- scheduler aleatório sem agente/causa narrativa.

## Reconciliação de entradas alternativas

### Encontrar evidência antes do rumor
Registrar discovery pela evidência encontrada. Não exigir `EVD-0001` retroativamente.

### Encontrar situação/alvo antes da oferta
Avaliar `PRE_RESOLVED`, `DISCOVERED`/`CONFIRMED` ou `TRANSFORMED` conforme ação real. `NPC-0003` deve reagir ao histórico, não oferecer a versão antiga da missão.

### Conhecer `NPC-0003` antes de QST-0001
Nenhum problema. Ele continua um NPC autônomo; conhecer Iren não desbloqueia automaticamente a oportunidade.

### `NPC-0003` indisponível
Morte, ausência, mudança de cargo ou indisposição não deve bloquear retroativamente a existência do mundo. A oferta formal pode nunca acontecer; discovery independente e resolução por outros permanecem possíveis quando fizerem sentido.

Se `NPC-0003` retornar, disponibilidade corporal não satisfaz automaticamente gates de memória, knowledge, relação ou identidade; consultar Identity Continuity.

## Descoberta retrospectiva
Se a situação já terminou enquanto o jogador estava `UNKNOWN`, ele pode adquirir knowledge histórico por:
- consequência visível;
- documento posterior;
- testemunha;
- registro institucional;
- investigação retrospectiva.

Nesse caso, não criar mensagem de “missão perdida”. O jogador descobre um fato histórico, não recebe retroativamente uma oportunidade que nunca conheceu.

## Journal
- `UNKNOWN`: invisível;
- `RUMORED`: só exibir se o produto possuir superfície apropriada para rumor; não transformar automaticamente em quest formal;
- `DISCOVERED`/`CONFIRMED`: mostrar apenas informações legitimamente conhecidas;
- `OFFERED`/`ACCEPTED`: superfície de quest pode representar compromisso sem revelar estados secretos;
- resolução desconhecida: continuar invisível até canal retrospectivo legítimo.

## Idempotência / transition keys
O Stage 08 exige keys estáveis por transição/efeito one-shot. O formato concreto permanece **PENDENTE DE CONTRATO DE RUNTIME**; este arquivo não inventa namespace técnico.

Antes de implementação, declarar keys para:
- availability transitions relevantes;
- discovery grants/canais one-shot;
- oferta/engagement;
- resolution;
- reward issuance;
- cleanup/callbacks one-shot.

Replay da mesma transição não pode duplicar journal, evento, recompensa ou consequência.

## Casos de teste editoriais mínimos
1. `ELIGIBLE + UNKNOWN + NOT_OFFERED + UNRESOLVED` persiste sem UI indevida.
2. `EVD-0001` pode gerar `RUMORED` sem oferta.
3. `EVD-0003` pode gerar descoberta antecipada sem `EVD-0001`/`EVD-0002`.
4. `NPC-0003` não oferece investigação sem knowledge suficiente.
5. recusa mantém mundo capaz de avançar sem punição automática.
6. aceitação não congela resolução por terceiros.
7. abandono explícito mantém Resolution independente.
8. encontro prévio reconcilia sem respawn/reset.
9. oportunidade pode ficar obsoleta enquanto `UNKNOWN`.
10. jogador pode descobrir somente consequência histórica.
11. dois jogadores podem manter discovery diferente sobre o mesmo world state.
12. resolution world-scoped não revela automaticamente discovery ao segundo jogador.
13. `NPC-0003` retornado sem memory/knowledge adequado não reabre automaticamente a oferta.
14. replay de transition/reward key não duplica efeitos.

## Relações
- quest: `QST-0001`;
- NPCs: `NPC-0001`, `NPC-0003`;
- evidências: `EVD-0001`, `EVD-0002`, `EVD-0003`.

## QA
- [ ] availability/discovery/engagement/resolution continuam ortogonais;
- [ ] offering actor/conditions são explícitos;
- [ ] `ABANDONED` permanece em Engagement;
- [ ] scopes multiplayer são explícitos no desenho atual;
- [ ] knowledge não é compartilhado automaticamente;
- [ ] transition/idempotency keys serão definidas antes de implementação;
- [ ] entrada antecipada e pre-resolution não exigem reset;
- [ ] retorno de NPC respeita Identity Continuity;
- [ ] nenhum outcome/culpado/solução foi selecionado neste contrato.

## Spoilers internos
Nenhum outcome, culpado ou solução é definido aqui.
