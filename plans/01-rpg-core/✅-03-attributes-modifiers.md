# RPG Core Plan — Attributes and Modifiers

**Goal:** aplicar efeitos de perks/classes de forma determinística, removível e recomputável.

- [x] Definir IDs estáveis de modifiers por origem/nó/rank.
- [x] Separar flat, percent-base e multiplicative-total.
- [x] Impedir duplicação após login, reload ou reaplicação.
- [x] Remover modifiers órfãos após respec/unlock perdido.
- [x] Recomputar estado derivado sem acumular drift.

**Acceptance:** aplicar/remover/reaplicar a mesma build produz exatamente os mesmos atributos.

## Evidência de implementação

- `NodeAttributeEffect.effectId` é a identidade persistente da origem do efeito; alterações de rank modificam somente o valor resolvido, sem criar uma segunda identidade.
- `AttributeNodeEffectRuntime.refresh` remove previamente todos os pares históricos `(effectId, attributeId)` conhecidos e reaplica somente o conjunto resolvido atual com `addOrUpdateTransientModifier`.
- `ModifierResolver` recompõe o resultado a partir do valor-base canônico e mantém semânticas distintas para `ADD_FLAT`, `ADD_PERCENT_BASE` e `MULTIPLY_TOTAL`, sem acumulação incremental.
- `NodeEffectCatalog` rejeita `effectId` duplicado e preserva alvos históricos necessários para limpar modifiers órfãos após reload, remoção ou mudança de atributo-alvo.
- `AttributeModifierContractJUnitTest` cobre composição das operações, recomputação idempotente, estabilidade de identidade entre ranks, limpeza de alvos órfãos e rejeição de IDs duplicados.

## Verificação de fechamento

- Implementação integrada pelo PR #117.
- Merge canônico: `c37f335ed5e95d044044a767fb8dd15de6149851`.
- O head final validado `ed242f571edd4cf3ee71dcefca5290cce683bbbd` passou no `RPG Skill Tree CI` run `33231346803`, incluindo Core tests, JUnit 5, NeoForge GameTests, data/attribute validation, build, verificação do JAR e dedicated-server smoke.

**Acceptance: satisfied.**
