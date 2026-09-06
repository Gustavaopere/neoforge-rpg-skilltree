# Integrations Plan — Adapter Contract

**Goal:** estabelecer um contrato semântico comum para adapters opcionais sem acoplar o RPG Core às APIs externas.

- [x] Detectar provider antes de construir/carregar o adapter específico.
- [x] Expor IDs semânticos canônicos namespaced e capabilities provider-neutral.
- [x] Confinar tipos de APIs externas aos packages de compatibilidade do provider.
- [x] Garantir fallback neutro quando o provider/capability não estiver disponível.
- [x] Garantir uma única owner/source por ação semântica e falhar fechado em colisão.
- [x] Expor diagnóstico bounded de adapter `absent`, `disabled` e `enabled`.
- [x] Testar provider ausente, adapter desabilitado, ownership único, colisão e validação de IDs.

**Acceptance:** o Core pode consultar capabilities por significado sem carregar classes de mods ausentes; duas integrações não podem processar a mesma ação semântica em paralelo.

## Implementação confirmada

Implementado pela PR #444, head final `b03ad5d862baa157ed311d1a5181a7423553ebd5`, mergeada na `main` como `f23afcf14adfd5936991487980037f280683fb43` em 2026-09-06.

O contrato materializado em `runtime/compat` contém:

- `SemanticActionId` para identidade semântica canônica namespaced;
- `IntegrationCapability` como capability provider-neutral;
- `IntegrationAdapter` e `IntegrationAdapterFactory` como boundaries de integração;
- `IntegrationAdapterRegistry` com detecção lazy de provider, diagnostics, ownership único e colisão fail-closed.

A construção do adapter só ocorre após o predicate de presença confirmar o provider, evitando classloading prematuro. Adapters carregados porém desabilitados não publicam capabilities, e providers ausentes resultam em fallback neutro e diagnóstico `provider_absent`.

## Evidência de validação

O HEAD final da PR #444 fechou **25/25 workflows de pull request em SUCCESS**. Entre os gates executados estavam `Stage 06.01 Adapter Contract`, `RPG Skill Tree CI`, SonarQube Cloud, CodeQL Security, Foundation Optional Integrations, NeoForge JUnit/GameTests, build NeoForge, verificação do JAR, dedicated-server smoke, Volcanoes Worldgen Compatibility Matrix e Consolidated Release Readiness.

O workflow focal `Stage 06.01 Adapter Contract` executou `IntegrationAdapterContractJUnitTest`, cobrindo provider ausente sem construção do adapter, fallback neutro, adapter habilitado com owner única, colisão de ownership fail-closed, adapter desabilitado e validação de IDs semânticos.

A PR também corrigiu o diagnóstico do Sonar para consultar a própria pull request e fixou `gradle/actions/setup-gradle` por SHA imutável, removendo a vulnerabilidade `githubactions:S7637` que bloqueava o Quality Gate durante a implementação.

**Estética:** N/A. Este subplano é infraestrutura server/common sem superfície player-facing; adicionar renderers, GeckoLib ou partículas aqui criaria acoplamento cosmético indevido.