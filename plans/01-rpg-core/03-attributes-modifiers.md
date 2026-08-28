# RPG Core Plan — Attributes and Modifiers

**Goal:** aplicar efeitos de perks/classes de forma determinística, removível e recomputável.

- [ ] Definir IDs estáveis de modifiers por origem/nó/rank.
- [ ] Separar flat, percent-base e multiplicative-total.
- [ ] Impedir duplicação após login, reload ou reaplicação.
- [ ] Remover modifiers órfãos após respec/unlock perdido.
- [ ] Recomputar estado derivado sem acumular drift.

**Acceptance:** aplicar/remover/reaplicar a mesma build produz exatamente os mesmos atributos.