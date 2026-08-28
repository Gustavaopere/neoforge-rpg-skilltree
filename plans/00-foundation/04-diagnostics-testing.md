# Foundation Plan — Diagnostics and Test Baseline

**Goal:** tornar falhas reproduzíveis antes de expandir gameplay.

- [ ] Padronizar logging de bootstrap, reload e erro de dados com ID/path relevante.
- [ ] Evitar spam por tick para falhas persistentes.
- [ ] Manter unit tests básicos e validators no CI.
- [ ] Executar build NeoForge e verificação do JAR.
- [ ] Manter dedicated-server smoke no pipeline.
- [ ] Registrar como reproduzir localmente cada gate crítico.

**Acceptance:** falhas estruturais aparecem cedo, com diagnóstico útil, e CI cobre build + server smoke.