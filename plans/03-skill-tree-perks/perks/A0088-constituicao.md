# A0088 — Constituição

## Estado

- **Design:** APROVADO sem mutação funcional no Notion em 2026-08-31.
- **Notion:** `3c569db9-f0db-8138-aa1b-f856663414a8`; fetch fresco PASS.
- **Estado Chat 2:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.

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