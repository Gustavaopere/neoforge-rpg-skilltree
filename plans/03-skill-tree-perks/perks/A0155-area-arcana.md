# A0155 — Área Arcana

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

Além de herdar a disponibilidade de A0153, o contrato exige radius semântico, contagem causal de alvos e cobrança extra de MANA no mesmo cast. O conjunto não está completo hoje.

## Contrato

- ARCANE; camada 4; Notable; 1 rank; 2 PP.
- Pré-requisitos: A0153 ≥2 + A0144 ≥2 + Gateway ARCANE.
- Uma resolução AoE elegível que atinja ≥3 alvos válidos distintos no mesmo `action_id` arma `Geometria Expandida` por 120 ticks, uma vez por ação.
- CD interno: 160 ticks.
- Próxima spell AoE elegível de `spell_key` diferente pode usar radius ×1,12 se pagar MANA extra.
- `extra = quantize_up(0,10 × mana_normal_final, provider_min_unit)`.
- O extra não recebe desconto de A0145.

## Authority e disponibilidade

Exige conjuntamente `AOE_RADIUS_ADAPTER_V1`, `AOE_TARGET_RECEIPT_V1` e `MANA_DEBIT_RECEIPT_V1` com unidade mínima do provider, além de A0153 capability-eligible.

Ars possui effects/AoE específicos, mas AoE não significa radius universal; Flare, por exemplo, aumenta número de cinders. Iron's também não forneceu radius genérico auditado.

## Transação

A expansão é candidate. Só commita quando radius aprovado + cobrança extra MANA + cast/outcome são confirmados. MANA insuficiente mantém a magia normal e a janela intacta. Cancelamento faz rollback.

## Exclusões

- Source, Soul Energy, HP, reagentes ou energia tecnológica como MANA;
- range→radius;
- contagem repetida do mesmo alvo;
- mesma spell para opener/consumer;
- cobrar antes de o cast poder ocorrer;
- descontar o custo extra via A0145.

## Handoff Chat 2

Manter indisponível até o conjunto completo existir. Não implementar somente o buff de radius ou somente a cobrança.

## Testes Chat 3

1. availability transitiva A0153→A0155;
2. ≥3 alvos distintos no mesmo action; duplicatas não contam;
3. janela 120/CD160 e spell diferente;
4. quantização do extra após custo normal;
5. mana insuficiente não consome janela;
6. commit/rollback, lifecycle, provider absence e dedicated server.