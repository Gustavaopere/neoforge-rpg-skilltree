# A0088 — Constituição

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8138-aa1b-f856663414a8`; fetch fresco PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Estado Chat 3:** **IMPLEMENTAÇÃO CONFIRMADA / AGUARDANDO MERGE DA PR #372**.

## Contrato canônico

- Gateway VITALITY; 5 ranks, +2% de vida máxima por rank, máximo +10%.
- Owner canônico: Minecraft/NeoForge `Attributes.MAX_HEALTH`.
- Mudança de rank não pode produzir cura gratuita. Ao recalcular max health, preservar `vidaAtual/vidaMáxima` quando possível e clamp seguro à nova vida máxima.
- A perk não concede absorption, cura emergencial, regeneração ou trigger de low-health por alternância de rank.

## Implementação Chat 2 — estado confirmado em código

- binding data-driven existente publica A0088 como `MULTIPLY_TOTAL` +0,02/rank em `minecraft:generic.max_health`;
- `AttributeNodeEffectRuntime.refresh(...)` remove/reaplica modifiers por `effectId` estável e usa `A0081A0100CombatPolicy.preserveHealthRatio(...)` quando a vida máxima muda;
- a perk compõe pela pilha de atributos vanilla e não cria NBT/persistência paralela;
- nenhum adapter de Pufferfish, Apotheosis, Simply, magia ou projetos próprios foi criado como segundo owner.

## Checklist Chat 2

- [x] Binding `MAX_HEALTH` presente
- [x] Escalonamento +2%/rank presente
- [x] `MULTIPLY_TOTAL` canônico presente
- [x] Preservação de razão de vida presente
- [x] Modifier derivado/idempotente por effectId presente
- [x] Código presente
- [ ] **VALIDAÇÃO CHAT 3:** rank up/down/respec sem cura líquida explorável
- [ ] **VALIDAÇÃO CHAT 3:** composição com outros modifiers de max health
- [ ] **VALIDAÇÃO CHAT 3:** relog/respawn/reload/idempotência
- [ ] **VALIDAÇÃO CHAT 3:** testes unitários/GameTests aplicáveis
- [ ] **VALIDAÇÃO CHAT 3:** build NeoForge
- [ ] **VALIDAÇÃO CHAT 3:** dedicated-server smoke
- [ ] **VALIDAÇÃO CHAT 3:** CI GREEN
- [ ] **VALIDAÇÃO CHAT 3:** IMPLEMENTAÇÃO CONFIRMADA

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | Gateway VITALITY. |
| Integração global | PASS | compõe via atributo vanilla, sem owner paralelo. |
| Qualidade/identidade | PASS | fundamento corporal simples e estável. |
| Topologia | PASS | VITALITY/CORE, Camada 1. |
| Especializações | PASS | fundamento só conta quando mapeado; não desbloqueia Specialist sozinho. |
| PT-BR | PASS | Constituição / vida máxima. |
| Notion | PASS sem mudança | fetch fresco. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Minecraft/NeoForge owner; terceiros apenas compõem modifiers. |

Chat 2 não executou a bateria final de testes/build/smoke/CI e não declara `IMPLEMENTAÇÃO CONFIRMADA`.

## Validação Chat 3 — 2026-09-07

- `A0081A0100CombatPolicyTest` confirmou multiplicador 1,10 no rank 5 e `preserveHealthRatio` para rank up, rank down, clamp de full health e health zero sem revive.
- O GameTest canônico de atributos usa `ServerPlayer` real e valida `MULTIPLY_TOTAL` com apply → reaplicação idempotente → remoção → reapply determinístico.
- `verify-attribute-runtime.py` exige o GameTest real e a reconciliação de jogadores online após reload válido, prevenindo modifier órfão/drift.
- A revisão do binding confirmou owner único `minecraft:generic.max_health`, sem NBT/persistência paralela e sem segundo motor de cura.
- Baseline de validação `4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`: RPG Skill Tree CI `34147843664` SUCCESS; Sonar `34147843581` SUCCESS; CodeQL `34147843620` SUCCESS.
- **Resultado:** implementação confirmada no pipeline vanilla/NeoForge de atributos, com preservação de proporção de vida e idempotência.

### Checklist final Chat 3

- [x] Design aprovado e código presente
- [x] Binding `MAX_HEALTH` / +2% por rank confirmado
- [x] `MULTIPLY_TOTAL` e composição canônica confirmados
- [x] Rank up/down sem cura líquida explorável confirmado
- [x] Respec/reload/idempotência de modifier confirmados
- [x] Anti-abuso/Mastery: N/A — perk não concede Mastery nem recurso
- [x] Testes unitários/NeoForge JUnit e GameTests verdes
- [x] Build NeoForge e dedicated-server smoke verdes
- [x] SonarQube e CodeQL verdes no baseline funcional
- [x] **IMPLEMENTAÇÃO CONFIRMADA**

O HEAD documental final deve ser revalidado em CI antes do merge da PR #372.