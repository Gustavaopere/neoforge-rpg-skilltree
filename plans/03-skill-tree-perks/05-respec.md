# Skill Tree Plan — Respec

**Goal:** permitir desfazer investimento sem criar árvore inválida ou deixar bônus órfãos.

- [ ] Detectar nós dependentes antes de remover um requisito.
- [ ] Definir política de cascata versus bloqueio de respec.
- [ ] Remover modifiers, gateways e permissões derivados.
- [ ] Recalcular classes/especializações afetadas.
- [ ] Definir custo, reembolso e limites de uso.
- [ ] Sincronizar estado final em uma única atualização coerente.

**Acceptance:** após respec, árvore, atributos, classes e pontos ficam equivalentes ao estado que teria sido obtido sem comprar o nó removido.