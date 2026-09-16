# Integrations Complete — Ars Nouveau

**Goal:** conectar conjuração Ars, mastery Sorcerer e especializações modulares ao RPG.

- [x] Confirmar evento semântico de cast válido.
- [x] Alimentar `ars:casting` uma vez.
- [x] Manter requisitos de Sorcerer data-driven.
- [x] Mapear Amplification, AoE, Control, Duration, Projectile e Summoning.
- [x] Definir coexistência com atributos genéricos do Iron's sem double-dipping.
- [x] Testar Ars isolado e Ars + Iron's.

## Runtime contract

- Provider auditado: Ars Nouveau **`5.13.1`** para NeoForge 1.21.1. `ArsNouveauVersionContract` aceita somente essa versão exata; qualquer outra versão é desativada no registry opcional com `unsupported_version` antes do registro de `ArsNouveauProgressionEvents`.
- `SpellCastEvent` é a fronteira provider-native usada para armar a causalidade de um cast válido. Cast cancelado, actor que não seja `ServerPlayer`, `FakePlayer`, contexto ausente ou ação sem composição válida falham fechado.
- `SpellResolveEvent.Post` é a fronteira de conclusão. A Mastery só é concedida quando existe uma claim causal one-shot ligada ao UUID do caster original; replay do mesmo contexto, claim por outro jogador, contexto reidratado sem attachment causal e eventos sem autoria não concedem novamente.
- Ars Nouveau continua authority da composição e do custo do spell. A assinatura semântica deriva os glyph IDs reais e o custo vem de `Spell#getCost()`; o RPG Skill Tree não cria segunda mana, Source ou custo paralelo.
- `ArsCompositionClassifier` mapeia as famílias canônicas `amplify`, `aoe`, `control`, `duration`, `projectile` e `summoning` para as lanes Ars correspondentes. Requisitos de Sorcerer permanecem data-driven no RPG Skill Tree.
- A resolução de um cast Ars concede a lane compartilhada `magic:casting` e as lanes Ars pertinentes, mas não concede `irons:casting` apenas porque Iron's está carregado. Cada provider conserva seu próprio cast lifecycle e sua própria autoria.

## Fail-closed e anti-abuso

- Provider ausente: adapter `absent(provider_absent)` e nenhum hook Ars é registrado.
- Versão diferente de `5.13.1`: adapter `disabled(unsupported_version)` e nenhum hook Ars é registrado.
- Cast cancelado, fake player, contexto sem causalidade, caster divergente ou replay: nenhuma Mastery.
- A causalidade é consumida uma única vez por cadeia de `SpellContext`, inclusive quando o contexto é clonado/filho e compartilha o attachment provider-native.
- Não existe recompensa por tick, mana gasta continuamente, spell equipado, turret/automações ou presença do provider.

## Verification

### TDD RED

O workflow dedicado `Stage 06.04 Ars Nouveau` run **`34150393282`** falhou no passo de contrato de versão antes da implementação de `ArsNouveauVersionContract`, comprovando o RED pelo motivo pretendido.

### GREEN focal

No head funcional **`46de2ca0056af516d9ae1ae53a51af409e5b26f6`**, o workflow `Stage 06.04 Ars Nouveau` run **`34151605488`** fechou GREEN:

- contrato unitário: `ArsNouveauVersionContractJUnitTest`, `OptionalIntegrationAdapterRegistryJUnitTest` e `ArsMasteryCausalityJUnitTest` — **SUCCESS**;
- **Ars 5.13.1 isolado** — provider real carregado, adapter Ars `enabled`, Iron's ausente, **71/71 GameTests obrigatórios passaram**;
- **Ars 5.13.1 + Iron's 1.21.1-3.16.3** — ambos os providers reais carregados/adapters ativos, **71/71 GameTests obrigatórios passaram**, incluindo prova de coexistência em que um cast Ars aumenta `magic:casting` e `ars:casting` sem alterar `irons:casting`.

### GREEN completo da PR

O `RPG Skill Tree CI` run **`34151605761`**, executado no merge sintético do mesmo head contra a `main` corrente, fechou **SUCCESS** e incluiu:

- Core tests e wiki drift/coverage;
- JUnit 5;
- NeoForge JUnit adapter tests;
- NeoForge GameTests;
- Battle Mage provider-present GameTests;
- validações de dados/runtime/attributes/bindings/diagnostics;
- NeoForge build;
- verificação do JAR;
- dedicated-server smoke.

A documentação de fechamento é revalidada por CI novamente antes do merge da PR #472.

**Acceptance:** prática Ars válida aumenta a progressão correta uma única vez, Ars isolado permanece funcional, Ars e Iron's coexistem deterministicamente sem double-dipping e versões não auditadas falham fechado.
