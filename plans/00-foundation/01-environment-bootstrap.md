# Foundation Plan — Environment and Bootstrap

**Goal:** congelar um ambiente de build e bootstrap NeoForge previsível.

- [ ] Conferir Minecraft, NeoForge, Java, Gradle e metadata do mod contra o artefato real.
- [ ] Alinhar `gradle.properties`, `build.gradle`, `mods.toml`/metadata equivalente e CI.
- [ ] Definir dependências obrigatórias versus opcionais.
- [ ] Garantir ordem determinística de registries e listeners comuns.
- [ ] Validar ranges/defaults de configuração durante carga.
- [ ] Formalizar convenção de `ResourceLocation` e IDs persistidos.

**Acceptance:** checkout limpo compila no ambiente suportado e inicializa com metadata/dependências coerentes.