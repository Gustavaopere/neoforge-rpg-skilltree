# Combat Plan — Summons and Ownership

**Goal:** atribuir corretamente perks e mastery de invocações ao dono real.

- [ ] Resolver owner para summons de providers suportados.
- [ ] Definir fallback neutro quando owner não existir.
- [ ] Aplicar `summon_damage` e efeitos derivados no ponto canônico.
- [ ] Preservar owner através de save/load quando o provider permitir.
- [ ] Evitar double-count de kill entre summon e owner events.

**Acceptance:** summon beneficia apenas a build do dono correto e uma kill gera no máximo uma concessão de progressão.