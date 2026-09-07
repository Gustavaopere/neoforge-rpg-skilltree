# A0150 — Estabilidade de Conjuração

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

No snapshot auditado, não existe `CAST_INTERRUPTION_CONVERTIBLE` versionada com boundary seguro imediatamente antes do cancelamento real de um cast ativo. Compra nova deve falhar antes do gasto; allocation legado vale 0 PP para gates e permanece reembolsável/migrável.

## Contrato

- Domínio ARCANE; ramo Técnica de Conjuração — Estabilidade; camada 3; Notable.
- 1 rank; 2 PP.
- Pré-requisitos: A0148 ≥1 + A0144 ≥2.
- Na primeira interrupção elegível de uma conjuração/canalização real, a perk pode converter o cancelamento uma única vez.
- Conversão: `tempo_restante ×1,20` + débito adicional de mana.
- Débito: `quantize_up(0,08 × mana_paga, unidade_mínima_do_provider)`.
- `mana_paga <= 0` não é elegível; arredondamento nunca pode produzir débito zero quando houve mana paga positiva.
- Exige saldo atual suficiente e ausência de estado fisiológico severo.
- Cooldown interno: 10 s.

## ResourceDebitReceipt

O débito adicional depende de `ResourceDebitReceipt(MANA)` causal da **mesma action_id/cast** e da mana realmente paga, não do custo nominal.

Source, Soul Energy, HP/sangue, reagentes e outros recursos não substituem `mana_paga`.

O receipt só pode ser consumido uma vez pela conversão.

## Iron's Spells 'n Spellbooks 3.16.3 — evidência

Snapshot: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

- `SpellPreCastEvent` é cancelável, mas ocorre antes do início e não representa interrupção ativa;
- `SpellOnCastEvent` ocorre quando a magia é efetivamente disparada;
- o runtime interno de `MagicManager`/casting pode encerrar casts ativos e chegar a `onServerCastComplete(..., true)`, porém não foi comprovado evento público/versionado que exponha, antes do cancelamento efetivo, motivo de interrupção + tempo restante + capacidade de impedir aquele cancelamento.

Consequência: Iron's não satisfaz `CAST_INTERRUPTION_CONVERTIBLE` no snapshot atual.

## Boundary futuro obrigatório

`cast/action_id ativo + interruption reason elegível + tempo restante → verificar ResourceDebitReceipt(MANA) da mesma action → quantizar extra → reservar/debitar atomically → se commit do débito confirmar, impedir somente aquele cancelamento → substituir por tempo_restante×1,20 → marcar conversão usada → iniciar cooldown interno`.

Se o débito falhar ou qualquer gate falhar, o cancelamento original segue normalmente.

## Gates fisiológicos

Fome, sede e temperatura são apenas leituras server-authoritative para bloquear a conversão em condição severa. Eles nunca viram custo nem são alterados por A0150.

Se qualquer leitura fisiológica obrigatória falhar, a perk falha fechado para aquele cast.

## Availability

Enquanto `CAST_INTERRUPTION_CONVERTIBLE`, receipt MANA causal, unidade debitável, débito atômico e gates fisiológicos seguros não estiverem comprovados, A0150 é `UNAVAILABLE_NODE`.

## Exclusões

- não interceptar cancelamento depois do fato;
- não reiniciar cast como aproximação;
- não cobrar custo nominal em vez de mana realmente paga;
- não usar Source/Soul Energy/HP como substituto;
- não ignorar interrupção severa/fisiológica;
- não permitir múltiplas conversões no mesmo cast;
- não refundar mana se o cast já foi cancelado.

## Handoff Chat 2

Manter indisponível no Iron's 3.16.3. Só habilitar após prova de boundary versionado pré-cancelamento e receipt/debit atômico. Qualquer solução que mude a identidade da perk deve voltar ao Chat 1.

## Testes Chat 3

1. purchase fail-before-spend e legacy PP 0 no snapshot atual;
2. SpellPreCastEvent não é aceito como interruption boundary;
3. mana_paga<=0 não qualifica;
4. quantize_up de 8% nunca gera zero com mana positiva;
5. saldo insuficiente deixa cancelamento original ocorrer;
6. débito deve ser atômico e same-action;
7. primeira interrupção no máximo uma vez por cast;
8. gates fisiológicos fail-closed;
9. recursos não-MANA não substituem receipt;
10. provider removal/reload/respec/relog/dimensão/restart;
11. nenhum restart/refund pós-cancelamento como fallback.