# Foundation Plan — Environment and Bootstrap

**Goal:** congelar um ambiente de build e bootstrap NeoForge previsível.

- [x] Conferir Minecraft, NeoForge, Java, Gradle e metadata do mod contra o artefato real.
  - Target congelado e verificado: Minecraft `1.21.1`, NeoForge `21.1.248`, Java `21`, NeoGradle UserDev `7.1.26`, Parchment `2024.11.17` e Gradle Wrapper `8.14`.
  - `scripts/verify-foundation-bootstrap.py` falha se esse contrato divergir entre `gradle.properties`, `build.gradle`, wrapper e CI.
- [x] Alinhar `gradle.properties`, `build.gradle`, `mods.toml`/metadata equivalente e CI.
  - CI usa o wrapper versionado para `test`, `runGameTestServer`, `build` e `runServer`.
  - O metadata usa o modelo de dependências do NeoForge atual em vez do campo legado `mandatory`.
- [x] Definir dependências obrigatórias versus opcionais.
  - `minecraft` e `neoforge` são `type="required"`.
  - Iron's Spellbooks, Ars Nouveau, Epic Fight, Goety, Malum, Eidolon e Identity2 são `type="optional"` no artefato.
  - Faixas dos providers opcionais permanecem vazias de propósito até a matriz de compatibilidade certificar versões suportadas; esta Foundation não inventa compatibilidade nominal.
- [x] Garantir ordem determinística de registries e listeners comuns.
  - O verificador fixa a sequência de bootstrap comum em `RpgSkillTreeMod` e exige que os guards de providers opcionais ocorram somente após o bootstrap comum.
- [x] Validar ranges/defaults de configuração durante carga.
  - No baseline fechado não existe configuração de usuário registrada, portanto não há valores de config atuais cujo range/default precise ser validado em runtime.
  - O contrato de Foundation falha se surgir `registerConfig(...)` sem uma configuração respaldada por `ModConfigSpec`; futuras configs devem declarar e validar seus próprios ranges/defaults no contrato NeoForge correspondente.
- [x] Formalizar convenção de `ResourceLocation` e IDs persistidos.
  - ADR `docs/decisions/015-persisted-resource-id-convention.md` define `namespace:path` como forma canônica para novos IDs persistidos ou addon-facing extensíveis, preserva namespaces de providers e proíbe nome traduzido, ordinal, classe Java ou nome de JAR como identidade de save/rede.
  - IDs legados sem namespace não são reescritos silenciosamente; permanecem sob migração/reconciliação específica do schema.

## Contrato implementado

- O checkout não depende de Gradle instalado no sistema; o wrapper `8.14` é a autoridade reproduzível.
- A suíte canônica inclui testes core, JUnit 5, NeoForge GameTests, validators, verificação de drift, build NeoForge, estrutura do JAR e dedicated-server smoke.
- Metadata separa dependências obrigatórias de integrações opcionais sem transformar providers em hard dependency acidental.
- Bootstrap comum possui sequência verificável e não depende da presença de providers opcionais.
- A convenção de identidade persistida é explícita e compatível com `ResourceLocation` sem executar migração destrutiva de saves históricos.

## Evidência TDD e integração

- RED: head inicial `a3a6d4c745643b9572ad00c4c3df78c8ae1e4ccf`, `Foundation Bootstrap Contract` run `33229595573`, falhou exatamente porque o metadata ainda usava `mandatory=true` e não satisfazia o contrato `required/optional`.
- GREEN pré-merge: head final `4a04e601a4a47618a4ec4dcfc7ed86588b146000`; `RPG Skill Tree CI` run `33229695104` passou core, JUnit 5, NeoForge GameTests, Compendium, validators, drift, build, JAR e dedicated-server smoke.
- Integração: PR #108 → merge `0f008fc3bc1767e74da777fcc02e37fd19acb263` em `main`.
- GREEN pós-merge: `Foundation Bootstrap Contract` run `33229822213` passou no commit integrado; `RPG Skill Tree CI` run `33229822237` passou todos os gates funcionais, incluindo build, JAR e dedicated-server smoke.

**Acceptance: satisfied.** O checkout integrado compila e inicializa no ambiente suportado com metadata/dependências coerentes, bootstrap comum determinístico e contratos de identidade/configuração explicitamente protegidos por CI.
