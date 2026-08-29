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
