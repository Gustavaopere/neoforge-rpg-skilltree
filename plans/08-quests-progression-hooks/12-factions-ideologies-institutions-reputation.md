# 08.12 — Factions, Ideologies, Institutions & Reputation

## Goal
Representar poder político/social e reputação sem depender de um mod externo de facções.

## Entregas
- [ ] Faction definitions data-driven.
- [ ] Player↔faction reputation multidimensional ou com componentes explícitos quando necessário.
- [ ] Faction↔faction relations.
- [ ] Ideology/stance tags para temas como necromancia, vampirismo, Black Arcana, automação, poluição, religião, Shroud research, experimentação corporal e militarização.
- [ ] Institution definitions internas a settlements/factions: clero, militares, academia, mercadores, conselho, hunters, casas vampíricas etc.
- [ ] Influence como poder político relativo, não “opinião”.
- [ ] Knowledge compartilhado por instituição somente após aquisição/propagação legítima.
- [ ] Policy hooks para instituições proporem, bloquear ou pressionar decisões.
- [ ] Relations não devem automaticamente mudar por tick; usar eventos/milestones.
- [ ] APIs read-only para dialogue/journal.

## Regra
Tags temáticas são linguagem narrativa; não criam bridge mecânica entre providers. `NECROMANCY` pode classificar uma controvérsia sem afirmar equivalência entre Goety, Malum, Eidolon, Mobstein ou Black Arcana.

## Acceptance
Uma teocracia com clero de alta influência responde a Severin de forma diferente de uma cidade acadêmica com baixa influência clerical, usando o mesmo content definition e condições derivadas.