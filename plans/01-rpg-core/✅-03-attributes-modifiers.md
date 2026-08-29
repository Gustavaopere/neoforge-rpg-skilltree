# RPG Core Plan — Attributes and Modifiers ✅

**Goal:** aplicar efeitos de perks/classes de forma determinística, removível e recomputável.

- [x] Definir IDs estáveis de modifiers por origem/nó/rank.
- [x] Separar flat, percent-base e multiplicative-total.
- [x] Impedir duplicação após login, reload ou reaplicação.
- [x] Remover modifiers órfãos após respec/unlock perdido.
- [x] Recomputar estado derivado sem acumular drift.

## Contrato implementado

- `NodeAttributeEffect.effectId` é a identidade estável da origem do efeito. Alterações de rank alteram o valor resolvido, não a identidade do modifier.
- `AttributeNodeEffectRuntime.refresh` remove previamente todos os pares históricos `(effectId, attributeId)` conhecidos e reaplica apenas o conjunto resolvido da build atual com `addOrUpdateTransientModifier`, impedindo acúmulo em login/reload/reaplicação.
- `ModifierResolver` recompõe o resultado a partir do valor-base canônico e preserva semânticas separadas para `ADD_FLAT`, `ADD_PERCENT_BASE` e `MULTIPLY_TOTAL`.
- `NodeEffectCatalog` rejeita `effectId` duplicado e preserva alvos históricos necessários para remover modifiers órfãos após respec, perda de unlock, reload ou mudança de atributo-alvo.
- `AttributeModifierContractJUnitTest` cobre composição, recomputação idempotente, identidade estável entre ranks, limpeza de alvos órfãos e rejeição de IDs duplicados.
- `AttributeModifierGameTests` fecha o boundary NeoForge real com `ServerPlayer` e `AttributeInstance`: aplica uma build, reaplica a mesma build sem stacking, remove a build retornando exatamente ao baseline e reaplica obtendo exatamente o mesmo valor da primeira aplicação.
- `scripts/verify-attribute-runtime.py` exige permanentemente esse GameTest, o uso de `AttributeNodeEffectRuntime.refresh` e as invariantes estruturais do runtime; o validator faz parte do CI completo.

## Evidência TDD e validação

- RED: commit `36f7b67ff6fa10c8df90f3c9ca525fd17d29ae8f`, RPG Skill Tree CI `33245655826`, falhou exatamente no gate `Attribute runtime validation` porque `AttributeModifierGameTests.java` ainda não existia. Todos os gates anteriores desse run haviam passado.
- O primeiro candidato funcional `b1f0260fd71bdea655553e073886bf41ac4f8c94` passou o CI completo `33246064596`, incluindo o novo GameTest, build, JAR e dedicated-server smoke.
- Como a `main` avançou em paralelo, o PR #135 foi sincronizado sem force-push. O head final `4bcefe741d641da6b7d14b1b89d4214bce9bc3ff` preservou apenas o GameTest e o validator próprios de `01.03` sobre `main@f55e80e9b962559d99784c4f69761f70236cb95d`.
- Pré-merge final: RPG Skill Tree CI `33246262135` GREEN completo no head sincronizado, incluindo Core, JUnit 5, NeoForge GameTests, `Attribute runtime validation`, demais validators, drift, NeoForge build, verificação do JAR e dedicated-server smoke. Os oito workflows auxiliares associados também fecharam GREEN.
- PR #135 foi integrado na `main` como `398f160f5bec74629331475eff1e60d3cdeb0958`.
- Pós-merge canônico: RPG Skill Tree CI `33246405719` `completed/success`, cobrindo novamente Core, JUnit 5, NeoForge GameTests, `Attribute runtime validation`, demais validators, drift, build, JAR, dedicated-server smoke, upload do JAR e publicação do status final `success`.

**Acceptance: satisfied.** Aplicar, reaplicar, remover e reaplicar a mesma build produz exatamente o mesmo estado de atributos no runtime NeoForge real, sem stacking, drift ou modifiers órfãos.