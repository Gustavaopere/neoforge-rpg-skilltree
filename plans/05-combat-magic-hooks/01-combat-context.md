# Combat Plan — Canonical Action Context

**Goal:** representar uma ação de combate de forma suficiente para todos os hooks posteriores.

- [ ] Modelar atacante/source/owner/alvo e tipo de ação.
- [ ] Preservar origem de projectile e summon.
- [ ] Separar dano base, bônus aditivo, multiplicadores e dano final.
- [ ] Carregar flags necessários para idempotência/once-per-hit.
- [ ] Evitar dependência do contexto comum em API de mod opcional.

**Acceptance:** melee, projectile, spell e summon podem ser descritos sem reconstruir autoria de forma ambígua.