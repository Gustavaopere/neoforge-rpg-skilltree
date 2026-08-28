# Skill Tree Plan — Purchase and Ranks

**Goal:** tornar compra de nó uma operação server-authoritative e atômica.

- [ ] Servidor valida pontos disponíveis, requisitos e rank atual.
- [ ] Cobrar ponto somente após validação completa.
- [ ] Impedir rank acima de `maxRank` e compra duplicada.
- [ ] Aplicar mudança de estado e efeitos como uma transação coerente.
- [ ] Sincronizar cliente após confirmação, não antes.
- [ ] Retornar motivo legível quando a compra for negada.

**Acceptance:** spam/replay de request não duplica compra e nenhum cliente consegue forçar rank inválido.