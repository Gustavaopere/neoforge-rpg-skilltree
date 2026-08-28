# Hardening Plan — Final Release Gate

**Goal:** estabelecer condições objetivas para considerar um build publicável.

- [ ] CI verde no commit candidato.
- [ ] Build NeoForge e JAR verification verdes.
- [ ] Dedicated-server smoke verde.
- [ ] Nenhum blocker conhecido de crash/save corruption.
- [ ] Matriz de compatibilidade do escopo fechada.
- [ ] Migrações exigidas testadas.
- [ ] Wiki/changelog atualizados.
- [ ] Version/tag/release artifact correspondem ao mesmo commit.

**Acceptance:** existe um único commit candidato reproduzível que satisfaz todos os gates obrigatórios.