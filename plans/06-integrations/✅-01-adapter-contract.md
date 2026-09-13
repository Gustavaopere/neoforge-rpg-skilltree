# Integrations Plan — Adapter Contract

**Goal:** estabelecer um contrato semântico comum para adapters opcionais sem acoplar o RPG Core às APIs externas.

- [x] Detectar provider antes de construir/carregar o adapter específico.
- [x] Definir interface/capability interna por ação semântica.
- [x] Manter tipos de APIs externas confinados aos packages de compatibilidade do provider.
- [x] Garantir fallback neutro quando provider/capability não estiver disponível.
- [x] Registrar ownership único por ação semântica e falhar fechado em colisão.
- [x] Padronizar diagnóstico bounded de adapter `absent`, `disabled` e `enabled`.
- [x] Executar o registry no bootstrap de produção antes dos hooks opcionais.
- [x] Gatear os providers opcionais do bootstrap real pelo registry comum, preservando seus gates específicos de versão/fail-closed.

**Acceptance:** o Core pode consultar capabilities por significado sem carregar classes de mods ausentes; duas integrações não podem publicar ownership concorrente para a mesma ação semântica; remover um provider opcional não impede o carregamento do core.

## Implementação

A PR #444 materializou o contrato provider-neutral em `runtime/compat`:

- `SemanticActionId` para IDs canônicos namespaced;
- `IntegrationCapability` como capability provider-neutral;
- `IntegrationAdapter` e `IntegrationAdapterFactory` como boundaries lazy;
- `IntegrationAdapterRegistry` com presença-before-construction, fallback neutro, diagnostics e colisão de ownership fail-closed.

O review do fechamento identificou que o contrato ainda não era executado pelo bootstrap real. A correção de produção é feita pela PR #453, reconstruída sobre a `main` corrente para preservar os merges de Create e do adapter Blacksmith/Productive Metalworks:

- `OptionalIntegrationAdapterRegistry` cria um adapter/source canônico para cada `OptionalIntegrations.Provider` conhecido no runtime atual;
- `RpgSkillTreeMod` constrói esse registry depois do resumo de providers e antes dos hooks específicos;
- Iron's, Ars Nouveau, Goety, Malum, Eidolon, Identity2, MineColonies, Epic Fight, Create e Productive Metalworks passam pelo boundary comum antes da ativação específica;
- Epic Fight, Cold Sweat, Create e Productive Metalworks propagam versão auditada incompatível como `disabled(unsupported_version)` no registry;
- MineColonies permanece ativo no registry somente se pelo menos uma rota auditada suportar a versão instalada: Battle Mage `1.1.1375` ou Economy `1.1.1375/1.1.1376`;
- Create preserva `CreateIntegrationBootstrap` e os estados `ABSENT_PROVIDER`, `UNSUPPORTED_VERSION`, `FAILED_CLOSED` e `ACTIVE` já existentes;
- tipos de APIs externas continuam fora do contrato comum.

## Evidência TDD e validação

O RED de produção foi observado no run `34057383727` da PR #451: `compileTestJava` falhou com 12 `cannot find symbol` para `OptionalIntegrationAdapterRegistry`, antes da classe existir.

Na primeira reconciliação da PR #453, `OptionalIntegrationAdapterRegistryJUnitTest` fechou GREEN e um review P2 detectou corretamente que MineColonies podia ser anunciado como ativo em uma versão sem rota compatível. O gate foi corrigido e `OptionalIntegrationMineColoniesVersionGateJUnitTest` cobre `1.1.1375`, `1.1.1376` e uma versão desconhecida fail-closed.

A branch final foi reconstruída novamente sobre a `main` após a integração concorrente da PR #447, preservando `BlacksmithItems`, `ModConditions` e o adapter Productive Metalworks. O workflow focal temporário de Adapter Wiring não faz parte do candidato final; as regressões permanecem na suíte ordinária. Os IDs finais de CI/head/merge devem ser registrados após a última matriz GREEN e antes/depois do merge.

**Estética:** N/A. Este subplano é infraestrutura server/common sem superfície player-facing; renderers, GeckoLib ou partículas aqui criariam acoplamento cosmético indevido.
