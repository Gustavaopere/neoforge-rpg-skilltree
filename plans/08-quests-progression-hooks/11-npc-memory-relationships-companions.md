# 08.11 — NPC Memory, Relationships & Companions

## Goal
Dar memória social persistente a personagens importantes sem reduzir relação a uma barra única.

## Dimensões mínimas
- affection;
- trust;
- respect;
- fear;
- dependency;
- ideological alignment.

## Entregas
- [ ] `NarrativeActorProfile` separado da entidade física/renderizada.
- [ ] Relações actor→actor com clamps e schema versionado.
- [ ] Grievance ledger limitado e deduplicado.
- [ ] Favor/debt ledger limitado.
- [ ] Memórias importantes referenciando event IDs, não cópias livres de texto.
- [ ] Regras data-driven de reação a choices/events.
- [ ] Estado de disponibilidade: active, hidden, missing, imprisoned, exiled, dead, returned, unknown.
- [ ] NPC death/fallback contract para não soft-lockar arcs.
- [ ] Integração com `26-death-resurrection-identity-continuity.md`: retorno não equivale automaticamente a restaurar memória, personalidade, knowledge, relações ou identidade social anterior.
- [ ] Suporte a companion loyalty sem assumir que Easy NPC fornece esse sistema.
- [ ] Export read-only de variáveis necessárias a diálogos.

## Regra
NPC físico de Easy NPC é presentation/provider de interação. A identidade narrativa deve sobreviver a respawn controlado, troca de entidade ou indisponibilidade do mod, sem duplicar o personagem.

Morte permanece fato histórico mesmo se o ator retornar. Um retorno por Mobstein ou outro provider deve ser reconciliado por `Identity Continuity Record`; não usar simplesmente `dead=false` e restaurar todo o snapshot anterior.

## Acceptance exemplar
Esconder Severin pode reduzir `respect`, aumentar um grievance e manter `trust`; defendê-lo em julgamento pode alterar dimensões diferentes. Nenhuma mudança precisa ser universalmente positiva/negativa.

Se um NPC morto retorna via Mobstein, relações e memórias anteriores só são preservadas conforme as regras de continuidade declaradas; quests que dependem de uma memória perdida continuam podendo permanecer indisponíveis mesmo com o corpo funcional novamente.
