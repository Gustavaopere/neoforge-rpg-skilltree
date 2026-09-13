# Lifecycle editorial de QST-#### — Título

## Estado editorial
RASCUNHO

## Escopo do documento
Contrato auxiliar de autoria para detalhar lifecycle de uma `QST-####`. Não declara uma segunda entidade nem substitui o dossiê principal da quest.

## Autoridade
Mapear este arquivo ao contrato de `plans/08-quests-progression-hooks/25-opportunity-discovery-lifecycle.md`. Runtime/persistence continuam sob autoridade do Stage 08.

## Princípio
Não usar status único. Availability, discovery, engagement e resolution são eixos ortogonais.

## Availability
### LOCKED
- condições:
- UI/knowledge permitido:

### ELIGIBLE
- condições:
- o que NÃO implica:

### INVALIDATED
- causas legítimas:
- reconciliação:

### EXPIRED
Usar apenas quando existir prazo ficcional explícito, causal e observável.

## Discovery
### UNKNOWN

### RUMORED
- channels:
- knowledge concedido:
- provenance/origem do rumor quando relevante:

### DISCOVERED
- conditions:
- channels:
- alternate entry:

### CONFIRMED
Definir exatamente o que foi confirmado; não confundir existência do fato com culpa/identidade/causa.

## Engagement
### NOT_OFFERED
Condição inicial quando nenhuma oferta formal ocorreu.

### OFFERED
- offering actor:
- offer conditions:
- condições de knowledge/agenda/estado do offering actor:
- o que registra a transição como oferta formal:

### ACCEPTED
- o que constitui aceitação/responsabilidade:

### DECLINED
- o que constitui recusa explícita:

### ABANDONED
Só usar quando abandono for ação explícita suportada pelo design.

`ABANDONED` pertence a Engagement. A Resolution continua separada e pode permanecer `UNRESOLVED`, transformar-se ou alcançar outro outcome depois.

## Resolution
- `UNRESOLVED`:
- `PRE_RESOLVED`:
- `RESOLVED_BY_OTHERS`:
- `OBSOLETE`:
- `TRANSFORMED`:
- `SUCCESS`:
- `SUCCESS_WITH_COST`:
- `PARTIAL_SUCCESS`:
- `FAILURE`:
- `PRODUCTIVE_FAILURE`:

## Política de progressão autônoma
Cada transição autônoma deve declarar:
1. ator/causa;
2. precondição observável/consultável;
3. milestone/evento quando aplicável;
4. resultado determinístico para o mesmo estado;
5. evidências/consequências persistentes quando semanticamente adequadas.

Não usar timer oculto genérico, tick ou inatividade do jogador como causalidade narrativa por si só.

## Reconciliação de entradas alternativas
- objetivo/fato encontrado antes da oferta:
- evidência encontrada fora de ordem:
- ator ofertante indisponível:
- situação já resolvida/transformada:
- retorno de ator com continuidade parcial, se aplicável:

## Descoberta retrospectiva
- hooks possíveis:
- knowledge concedido:
- regra para não criar "quest perdida" retroativa:

## Journal/visibilidade
- UNKNOWN:
- RUMORED:
- DISCOVERED/CONFIRMED:
- OFFERED/ACCEPTED:
- resolução desconhecida:

O journal não revela `UNKNOWN` só porque o runtime conhece a oportunidade.

## Multiplayer
Declarar quando relevante:
- availability scope: PLAYER | TEAM | WORLD | outro contrato explícito;
- discovery scope: PLAYER | TEAM | WORLD;
- engagement scope: PLAYER | TEAM | WORLD;
- resolution scope: PLAYER | TEAM | WORLD | outro contrato explícito;
- regra de compartilhamento de knowledge:
- estados que podem divergir entre jogadores/equipes:

Discovery/knowledge não são sincronizados automaticamente apenas porque o world state ou a resolution são globais.

## Idempotência por transição
- availability transition key:
- discovery transition key:
- offer/engagement transition key:
- resolution transition key:
- reward issuance key:
- cleanup key:

Replay da mesma transição não pode duplicar journal entry, evento, recompensa ou consequência one-shot.

## Casos de teste editoriais mínimos
1. elegível e desconhecida sem UI indevida;
2. rumor sem oferta;
3. entrada antecipada;
4. recusa distinta de nunca oferecida;
5. aceitação sem congelar mundo;
6. abandono explícito com Resolution independente;
7. resolução por terceiros;
8. pre-resolution sem respawn/reset;
9. obsolescência desconhecida;
10. descoberta retrospectiva;
11. offering actor indisponível antes da oferta;
12. discovery divergente entre jogadores quando permitido;
13. resolution world-scoped sem vazar discovery para outro jogador;
14. replay de transition key sem duplicar efeitos.

## Relações
- quest:
- NPCs:
- evidências:
- facções:
- assentamentos:
- eventos:

## QA
- [ ] estado editorial usa categoria persistente válida;
- [ ] offering actor e offer conditions estão explícitos quando há oferta;
- [ ] `ABANDONED` permanece em Engagement, não em Resolution;
- [ ] scopes multiplayer foram declarados quando relevantes;
- [ ] knowledge/discovery não são compartilhados automaticamente;
- [ ] idempotency keys cobrem transições/efeitos one-shot quando aplicáveis;
- [ ] alternate entry e pre-resolution não exigem reset artificial;
- [ ] progressão autônoma possui ator/causa.

## Spoilers internos
