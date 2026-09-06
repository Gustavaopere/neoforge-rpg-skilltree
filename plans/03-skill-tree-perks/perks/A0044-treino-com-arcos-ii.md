# A0044 — Treino com Arcos II

## Estado

- **Design:** APROVADO após correção de availability/fail-closed no review da PR #243.
- **Implementação:** **NÃO CONFIRMADA / FAIL-CLOSED CORRETO VALIDADO PELO CHAT 3**.
- **Disponibilidade:** `UNAVAILABLE_NODE` enquanto não existir binding server-authoritative semântico de draw/preparation speed.
- **Notion:** `3c569db9-f0db-81d4-9c22-c7fc0ebcd482`; corrigido e re-fetch PASS em 2026-08-30.

## Contrato canônico

- A0043 ≥2 + gateway `epic_bow` + binding server-authoritative válido de draw/preparation speed.
- +2% de ritmo efetivo de preparo/disparo com arcos por rank, até +6%, somente quando provider expuser parâmetro server-authoritative com essa semântica.
- Projectile speed, movimento, stamina, dano, tooltip ou manipulação de animação não são substitutos.
- **Sem binding válido, A0044 é explicitamente INDISPONÍVEL/NÃO COMPRÁVEL:** nenhum ponto pode ser gasto e nenhum rank pode ser adquirido como no-op.
- Enquanto A0044 estiver indisponível, dependências que exigem seus ranks não podem ser satisfeitas; A0047 permanece bloqueada.

## Evidência runtime

- `CombatPerkAvailabilityRuntime` marca A0044 indisponível.
- `NodePurchaseRequestProcessor` consulta availability antes de reservar request-id ou mutar pontos/ranks.
- o caminho trusted de `PlayerProgressionRuntime.purchaseNode(...)` também consulta o mesmo gate.
- `A0041A0060RuntimeState.ranks(...)` mascara ranks persistidos de nodes indisponíveis, evitando efeito por legado/ghost rank.
- nenhum consumer de draw/preparation time foi inventado; projectile speed foi explicitamente removido como substituto.

## Provider→árvore

- Nenhum dos providers retroauditados fornece draw speed seguro para este contrato.
- Stage 11.01 de itemização não possui projeção de efeito que autorize esta cadência.
- Volcanoes/Enshrouded/Black Arcana/Mobstein não são providers de preparação de arco.

## Pendência técnica futura

`P-A0044-01` está resolvida quanto ao **availability gate**, mas a capability que permitiria habilitar o node continua inexistente. Reativação futura exige provider semântico real; não redesenhar com projectile speed, custo zero, rank fantasma ou bypass.

## Pendência Chat 3

- validar compra indisponível sem gasto de ponto e sem consumir request-id;
- validar rank legado mascarado;
- validar que A0047/A0048 permanecem estruturalmente indisponíveis;
- validar projeção cliente/servidor do estado indisponível.

## Testes exigidos

- provider ausente → A0044 não comprável e nenhum ponto gasto;
- provider incompatível → indisponível;
- provider futuro presente → rank 1/2/3 apenas quando efeito real +2/+4/+6% existir;
- A0047 bloqueada enquanto A0044 indisponível;
- nenhum dano/stamina/movement/projectile-speed fallback;
- dedicated server e projeção cliente coerente.

## Fechamento Chat 2 — 2026-09-01

O fail-closed deixou de ser apenas documental e passou a ser imposto na compra e no runtime efetivo. Não houve implementação de efeito alternativo.

## Fechamento Chat 3 — 2026-09-02

Availability, compra sem gasto, masking legado e bloqueio estrutural A0044→A0047/A0048 foram validados. Nenhum fallback sintético foi introduzido. CI #3378 (`33665545963`) GREEN completo. **Estado final: NÃO CONFIRMADA / FAIL-CLOSED CORRETO.**