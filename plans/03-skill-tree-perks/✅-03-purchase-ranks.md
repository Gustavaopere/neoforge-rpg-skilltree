# Skill Tree Plan — Purchase and Ranks

**Status:** ✅ concluído em 2026-08-29.

**Goal:** tornar compra de nó uma operação server-authoritative e atômica.

- [x] Servidor valida pontos disponíveis, requisitos e rank atual.
- [x] Cobrar ponto somente após validação completa.
- [x] Impedir rank acima de `maxRank` e compra duplicada.
- [x] Aplicar mudança de estado e efeitos como uma transação coerente.
- [x] Sincronizar cliente após confirmação, não antes.
- [x] Retornar motivo legível quando a compra for negada.

## Contrato implementado

- O pacote client → server carrega somente `nodeId + requestId`; custo, `maxRank`, requisitos, conectividade e rank atual são resolvidos exclusivamente no servidor a partir do estado persistido e do `TreeRuleCatalog` vigente.
- `NodePurchaseMutationService` executa todas as validações antes de gastar pontos e retorna `NodePurchaseResult` estruturado sem mutar o estado original em rejeições.
- Uma compra aceita gasta exatamente `costPerRank`, aumenta exatamente um rank e preserva o bookkeeping de final triads antes da reconciliação derivada e do commit canônico.
- `NodePurchaseRequestProcessor` combina a mutação atômica com uma janela bounded de 256 `requestId` por jogador: replay exato retorna `DUPLICATE_REQUEST`; reutilização do mesmo id para outro nó retorna `REQUEST_ID_CONFLICT` e falha fechado.
- O construtor client-side de `PurchaseNodePayload` gera um UUID-backed `requestId` novo por intenção; o wire format valida charset/tamanho e o protocolo de rede foi promovido de `3` para `4`.
- O cache de replay é descartado no logout e no server stop.
- Compra rejeitada retorna mensagem legível via `fallbackMessage()`; não há cobrança, rank novo nem owner sync de mutação rejeitada.
- Compra confirmada passa pela reconciliação derivada e pelo boundary canônico de persistência; o owner sync continua sendo emitido a partir de mutação confirmada, não antecipadamente.
- O overload histórico `purchaseNode(player, nodeId)` permanece disponível apenas para callers server-side confiáveis e conserva o contrato legado de `ProgressionService.purchaseNode`; o handler de rede usa exclusivamente o overload idempotente com `requestId`.

## Evidência

- TDD RED `33279144632` / run #1708: falha exclusivamente pela ausência de `NodePurchaseMutationService`, `NodePurchaseResult` e `NodePurchaseRequestTracker`, mantendo o Core anterior GREEN.
- TDD RED `33279348706` / run #1712: cinco erros de compilação provaram que `PurchaseNodePayload` ainda não carregava o contrato `requestId`.
- TDD RED `33279541427` / run #1718: falha exclusivamente pela ausência de `NodePurchaseRequestProcessor`, provando a composição replay + mutação antes do GREEN.
- TDD RED `33279652249` / run #1721: falha exclusivamente pela ausência de `fallbackMessage()`, cobrindo a exigência de rejeição legível.
- O CI intermediário #1724 confirmou Core, JUnit e NeoForge GameTests; revelou um validator textual legado que ainda exigia a chamada histórica diretamente no caminho alterado. O overload server-side confiável foi preservado sem reabrir autoridade ao cliente.
- O CI intermediário #1726 passou o runtime scaffold e revelou outro validator textual congelado em `NETWORK_VERSION = "3"`; o validator foi atualizado para o wire contract real `NETWORK_VERSION = "4"`.
- Head funcional `3b1d01a8829d3c99dc8b242e8ab538e46046eceb`: RPG Skill Tree CI `33280107469` / run #1728 GREEN completo, incluindo Core, JUnit 5, todos os 11 NeoForge GameTests, Compendium, data/client-tree/node-effect/passive-export/runtime/attribute/provider/diagnostics/drift validators, NeoForge build, verificação do JAR e dedicated-server smoke.
- Os workflows Foundation Bootstrap, Foundation Diagnostics, Foundation Optional Integrations e todos os workflows Compendium associados ao mesmo head também fecharam GREEN.

**Acceptance:** satisfied — spam/replay dentro da janela autoritativa não duplica compra, `requestId` conflitante falha fechado, o cliente não consegue fornecer custo/requisitos/rank/topologia e nenhum caminho client → server consegue forçar rank inválido ou cobrança antes da validação completa.