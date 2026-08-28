# Skill Tree Complete — Respec

**Goal:** permitir desfazer investimento sem criar árvore inválida ou deixar bônus órfãos.

- [x] Detectar nós dependentes antes de remover um requisito.
- [x] Definir política de cascata versus bloqueio de respec.
- [x] Remover modifiers, gateways e permissões derivados.
- [x] Recalcular classes/especializações afetadas.
- [x] Definir custo, reembolso e limites de uso.
- [x] Sincronizar estado final em uma única atualização coerente.

## Runtime contract

- Remover um rank acima de 1 reduz apenas aquele rank; remover o último rank recalcula conectividade a partir dos starting points.
- Nós aprendidos que ficam órfãos são removidos em cascata; nós ainda alcançáveis são preservados.
- O reembolso é exatamente `ranks removidos × costPerRank`; não existe taxa/cooldown adicional de respec no contrato atual.
- Final triads são decrementadas junto dos ranks removidos.
- `reconcileDerivedState` remove classes automáticas, especializações e nós que deixaram de satisfazer requisitos até o estado estabilizar.
- Morph permissions são derivadas da progressão viva; modifiers de atributos são reconstruídos por `AttributeNodeEffectRuntime.refresh`.
- `PlayerProgressionRuntime.respecNode` publica o estado reconciliado por uma única chamada final de `set`, que refresca efeitos e sincroniza o owner.

## Verification

- Implementação principal integrada pelo milestone Alpha 2 / PR #4.
- `Alpha2ProgressionTest` cobre cascade apenas de branches órfãs, refund exato, final triads, reconciliação automática de classes, choices e remoção/refund de class subtrees inválidas.
- `SpecializationReconciliationTest` cobre revogação/restauração de especializações node-owned.
- Auditoria de fechamento: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.
- CI `33132979048` / run #620: suíte e dedicated-server smoke GREEN.

**Acceptance:** satisfied. Depois do respec, árvore, pontos e estado derivado são reconstruídos como se os ranks removidos não estivessem presentes.