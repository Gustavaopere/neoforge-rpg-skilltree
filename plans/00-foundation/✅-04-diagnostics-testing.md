# Foundation Plan — Diagnostics and Test Baseline ✅

**Goal:** tornar falhas reproduzíveis antes de expandir gameplay.

- [x] Padronizar logging de bootstrap, reload e erro de dados com ID/path relevante.
- [x] Evitar spam por tick para falhas persistentes.
- [x] Manter unit tests básicos e validators no CI.
- [x] Executar build NeoForge e verificação do JAR.
- [x] Manter dedicated-server smoke no pipeline.
- [x] Registrar como reproduzir localmente cada gate crítico.

## Contrato implementado

- `RuntimeDiagnostics` define prefixo estável `[rpgskilltree/<category>/<event>]`, event IDs `lower_snake_case` e categorias `bootstrap`, `compat`, `progression`, `effects`, `compendium` e `data`, preservando SLF4J por classe emissora.
- Bootstrap, compatibilidade, falhas pós-mutation, efeitos de atributos e publicação/inventory do Compêndio usam eventos operacionais estáveis e severidades explícitas.
- `ReloadDiagnostics` torna falhas de JSON fail-visible: registra o logical data path, os `ResourceLocation` envolvidos em ordem determinística e a exceção original, depois relança a falha em vez de aceitar estado parcial silencioso.
- `NodeRulesReloader` e `ClassRulesReloader` passam pelo boundary de reload com `node_rules` e `classes`; `CoreProgressionRulesReloader` já inclui o `ResourceLocation` exato nas violações de validação.
- `AttributeEffectDiagnostics.putIfAbsent` mantém warning once-only por condição persistente, impedindo spam por refresh/tick para targets indisponíveis.
- `docs/DIAGNOSTICS.md` documenta categorias, severidades, eventos e política anti-spam/fail-closed.
- `docs/TESTING.md` foi reconciliado com o estado real: Gradle Wrapper, fast core tests, JUnit 5, NeoForge GameTests, validators, build, JAR e dedicated-server smoke, com comandos locais reproduzíveis.
- `scripts/verify-foundation-diagnostics.py` e o workflow `Foundation Diagnostics Contract` impedem regressão da taxonomia, reload diagnostics, anti-spam, documentação e wiring dos gates no CI completo.
- Os contratos anteriores de Foundation e node-effect diagnostics foram atualizados semanticamente para o novo boundary, mantendo ordem de bootstrap, summary de providers e ausência de skips silenciosos.

## Evidência TDD e validação

- RED inicial: GitHub Actions `33231244859` falhou exatamente porque `RuntimeDiagnostics.java` ainda não existia.
- Candidatos intermediários expuseram validators lexicais antigos (`LOGGER.info`/`LOGGER.warn`); eles foram reconciliados para verificar o contrato semântico estruturado em vez da implementação textual antiga.
- A auditoria do Acceptance encontrou ainda reloads de `node_rules`/`classes` sem path/IDs úteis em falhas de parsing; isso foi corrigido antes do fechamento por `ReloadDiagnostics`.
- Head funcional final antes do merge: `4de1fc12310e4749fbf6b6312abe0e92a9b242c2`.
- Pré-merge GREEN no mesmo head:
  - `Foundation Diagnostics Contract` `33244275687`;
  - `Foundation Bootstrap Contract` `33244275700`;
  - `Foundation Optional Integrations` `33244275693`;
  - `RPG Skill Tree CI` `33244275690`, incluindo Core, JUnit 5, NeoForge GameTests, todos os validators, drift, NeoForge build, verificação do JAR e dedicated-server smoke;
  - todos os workflows Compendium associados também GREEN.
- PR #120 foi integrado na `main` como `4a13ac7c8deda8827e755d100223985f07319e8e`.
- Pós-merge em `main@4a13ac7c8deda8827e755d100223985f07319e8e`:
  - `Foundation Diagnostics Contract` `33244389124` GREEN;
  - `Foundation Bootstrap Contract` `33244389122` GREEN;
  - `Foundation Optional Integrations` `33244389143` GREEN;
  - `RPG Skill Tree CI` `33244389119` `completed/success`, incluindo Core, JUnit 5, NeoForge GameTests, validators, drift, build, JAR, dedicated-server smoke, upload do JAR e publicação do status final `success`.

**Acceptance: satisfied.** Falhas estruturais relevantes aparecem cedo com diagnóstico operacional estável; reloads canônicos preservam contexto de path/resource e falham fechados; falhas persistentes não geram spam por tick; os gates críticos possuem reprodução local documentada; CI cobre testes, validators, build, JAR e dedicated-server smoke.