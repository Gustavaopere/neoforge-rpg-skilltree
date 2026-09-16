# 08.14 — Social Propagation, Unrest & Consequences

## Goal
Converter conhecimento, instituições, opinião e leis em consequências sociais bounded e persistentes.

## Entregas
- [ ] Event-driven propagation queue; sem O(population) por tick.
- [ ] Regras de rumor/informação com actor/group targets.
- [ ] Public knowledge threshold separado de institutional knowledge.
- [ ] Unrest/crisis state machine: calm, tension, protest, institutional conflict, revolt/coup/civil conflict quando conteúdo justificar.
- [ ] Mitigation/escalation por choices, evidence, authority, fear, favors e provider events.
- [ ] Scheduler persistente para consequências atrasadas.
- [ ] Consequences condicionais que podem ser canceladas, adiantadas ou transformadas por eventos posteriores.
- [ ] Cooldown/dedup para crises recorrentes.
- [ ] Causal source e audit trail em toda consequência.

## Não fazer
- não simular cada conversa de cada cidadão;
- não aplicar penalidade pública por segredo ainda não conhecido;
- não usar RNG não persistido para decisões críticas;
- não transformar unrest em dano arbitrário ou debuff genérico.

## Acceptance exemplar
Na fixture genérica de pesquisador de necromancia, manter a presença do pesquisador em segredo não afeta opinião pública. Uma testemunha pode iniciar cadeia de rumor. Se o clero recebe prova, pode escalar para investigação. Se a população já foi salva pelo pesquisador, o mesmo evento pode produzir polarização em vez de consenso contra ele.

A fixture é sistêmica e identitariamente neutra; não representa nem cria binding para qualquer NPC canônico.
