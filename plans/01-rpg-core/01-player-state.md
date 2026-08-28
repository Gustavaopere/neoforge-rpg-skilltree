# RPG Core Plan — Canonical Player State

**Goal:** consolidar todos os dados persistentes de RPG do jogador em um modelo coerente.

- [ ] Definir campos canônicos de level, XP, pontos passivos, masteries, unlocks e identidades.
- [ ] Definir defaults e invariantes por campo.
- [ ] Rejeitar/normalizar estado impossível ou negativo conforme regra.
- [ ] Separar estado persistente de caches derivados.
- [ ] Definir snapshot somente-leitura para consumidores.

**Acceptance:** todos os sistemas consultam o mesmo estado e não mantêm cópias concorrentes de progressão.