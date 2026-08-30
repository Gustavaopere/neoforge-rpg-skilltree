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
- [ ] Estado de disponibilidade: active, hidden, missing, imprisoned, exiled, dead, unknown.
- [ ] NPC death/fallback contract para não soft-lockar arcs.
- [ ] Suporte a companion loyalty sem assumir que Easy NPC fornece esse sistema.
- [ ] Export read-only de variáveis necessárias a diálogos.

## Regra
NPC físico de Easy NPC é presentation/provider de interação. A identidade narrativa deve sobreviver a respawn controlado, troca de entidade ou indisponibilidade do mod, sem duplicar o personagem.

## Acceptance exemplar
Esconder Severin pode reduzir `respect`, aumentar um grievance e manter `trust`; defendê-lo em julgamento pode alterar dimensões diferentes. Nenhuma mudança precisa ser universalmente positiva/negativa.