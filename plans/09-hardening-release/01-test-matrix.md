# Hardening Plan — Test Matrix

**Goal:** consolidar uma matriz mínima obrigatória para qualquer release.

- [ ] Unit tests do core e serviços.
- [ ] Validators de datapack/árvore/effects.
- [ ] GameTests onde trouxerem valor real.
- [ ] Build NeoForge e verificação do JAR.
- [ ] Dedicated-server smoke.
- [ ] Cliente + servidor multiplayer nos fluxos críticos.
- [ ] Registrar versão/seed/config necessárias para reproduzir falhas.

**Acceptance:** o commit candidato passa uma matriz documentada e repetível, não apenas um build local.