# Hardening Plan — Network and Data Robustness

**Goal:** tratar input de cliente e datapacks como dados que precisam de validação e limites.

- [ ] Bounds em packets, listas, IDs e strings.
- [ ] Requests nunca bypassam validação server-side.
- [ ] Datapack inválido não publica snapshot parcial.
- [ ] Logs não expõem volume ilimitado por input hostil.
- [ ] Testar payloads fora de faixa e IDs desconhecidos.

**Acceptance:** inputs inválidos falham de forma bounded e não alteram estado canônico.