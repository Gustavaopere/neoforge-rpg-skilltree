# Lifecycle editorial de QST-#### — Título

## Estado editorial
RASCUNHO / CONTRATO DE AUTORIA

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

### DISCOVERED
- channels:
- alternate entry:

### CONFIRMED
Definir exatamente o que foi confirmado; não confundir existência do fato com culpa/identidade/causa.

## Engagement
### NOT_OFFERED

### OFFERED
- offering actor:
- condições de knowledge/agenda:

### ACCEPTED

### DECLINED

### ABANDONED
Só usar quando abandono for ação explícita suportada pelo design.

## Resolution
- `UNRESOLVED`:
- `PRE_RESOLVED`:
- `RESOLVED_BY_OTHERS`:
- `OBSOLETE`:
- `TRANSFORMED`:
- demais outcomes aplicáveis (`SUCCESS`, `SUCCESS_WITH_COST`, `PARTIAL_SUCCESS`, `FAILURE`, `PRODUCTIVE_FAILURE`):

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

## Multiplayer
- availability scope:
- discovery scope:
- engagement scope:
- resolution scope:
- regra de compartilhamento de knowledge:

## Casos de teste editoriais mínimos
1. elegível e desconhecida sem UI indevida;
2. rumor sem oferta;
3. entrada antecipada;
4. recusa distinta de nunca oferecida;
5. aceitação sem congelar mundo;
6. resolução por terceiros;
7. pre-resolution sem respawn/reset;
8. obsolescência desconhecida;
9. descoberta retrospectiva;
10. discovery divergente entre jogadores quando permitido.

## Relações
- quest:
- NPCs:
- evidências:
- facções:
- assentamentos:
- eventos:

## Spoilers internos
