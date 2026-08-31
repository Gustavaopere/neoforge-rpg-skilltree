# Classes Plan — Provider Identities

**Goal:** fazer classes dependentes de mods externos nascerem de requisitos data-driven e prática comprovada.

**Estado atual:** FECHADO — Mage e Sorcerer usam os requisitos atuais do datapack, a disponibilidade do provider é derivada do mod carregado e a identidade automática é revogada fail-closed quando o provider exigido está ausente, sem apagar Mastery já conquistada.

- [x] Mage: validar `arcane_000` + threshold de `irons:casting` conforme dados atuais.
  - `mage.json` continua autoritativo para o threshold e declara `required_provider_mods: ["irons_spellbooks"]`.
- [x] Sorcerer: validar `arcane_000` + threshold de `ars:casting` conforme dados atuais.
  - `sorcerer.json` continua autoritativo para o threshold e declara `required_provider_mods: ["ars_nouveau"]`.
- [x] Remover hardcodes que possam divergir dos JSON.
  - O runtime lê `required_nodes`, `minimum_mastery_experience` e `required_provider_mods` no reload; não existe tabela runtime específica de Mage/Sorcerer.
- [x] Definir comportamento quando o provider mod estiver ausente.
  - `ClassRulesReloader` resolve presença com `ModList`, publica o snapshot em `ProviderClassAvailabilityRegistry` e mantém a definição no catálogo.
  - `ClassRequirementPolicy` consulta o snapshot; definição indisponível falha fechada e `ProgressionService.reconcileAutomaticClasses` remove a identidade derivada já persistida.
  - A revogação não remove nós nem Mastery, portanto eventual retorno do provider pode reavaliar a mesma prática já conquistada.
- [x] Testar abaixo/exato/acima do threshold.
  - `ProviderIdentityAvailabilityJUnitTest` lê os thresholds diretamente dos JSON e cobre abaixo, exato e acima para `irons:casting` e `ars:casting`.
  - O mesmo teste exercita `ClassRulesReloader` → `ClassRuleCatalog`/availability snapshot → `ProgressionService` alternando provider presente/ausente, cobrindo unlock, revogação e preservação de Mastery.

## Evidência do fechamento

- TDD RED inicial: RPG Skill Tree CI #2544 falhou em `compileTestJava` porque `ProviderAvailabilityPolicy` ainda não existia.
- Segundo RED: RPG Skill Tree CI #2550 falhou em `compileTestJava` porque a revogação de identidade indisponível ainda não estava integrada ao reconciliador canônico.
- Review P1 exigiu provar o caminho real de loader/class resolution; o teste foi endurecido para usar o próprio `ClassRulesReloader` com snapshots provider-present/provider-absent e o thread foi resolvido somente após JUnit GREEN.
- Head funcional `97cd6539f93b49aa96afc2a6c06737899a1386b2`: todos os 10 workflows GREEN; RPG Skill Tree CI #2558 passou Core, wiki/drift, JUnit 5, NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke.
- Nenhum arquivo de perk/dossiê/Notion foi alterado neste fechamento.

**Acceptance:** FECHADO. Identidades externas só emergem quando os requisitos atuais do datapack são satisfeitos e o provider exigido está realmente carregado; simples posse de spellbook/glyph não substitui `arcane_000` nem a Mastery, e ausência do provider revoga somente o estado derivado da classe.
