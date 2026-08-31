# A0093 — Guarda Econômica

## Estado

- **Design:** APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` após auditoria do Epic Fight 21.17.3.1 em 2026-08-31.
- **Notion:** `3c569db9-f0db-8157-9ee9-d1516f495b51`; mutado e re-fetch PASS.
- **Runtime observado:** fórmula pura existe, mas o bridge atual mantém `FAIL_CLOSED_A0093=true`; não existe binding seguro de custo real de guarda.

## Contrato canônico

- Topologia reservada VITALITY ↔ MARTIAL.
- 5 ranks: −2% do custo real de stamina da guarda por rank, máximo −10%.
- A redução deve ocorrer **antes** do débito provider-native, sobre o `consumeAmount` causal que a guarda realmente cobraria.
- Enquanto não houver extension point suportado, o node é indisponível e não pode gastar pontos.

## Evidência provider

O `GuardSkill` do Epic Fight calcula `penalty × impact` e chama `consumeForSkill(... STAMINA, consumeAmount)`. Isso prova que existe custo causal interno, mas não transforma classe interna/mixin em API pública estável. O runtime atual do RPG reconhece essa ausência e não tenta reembolso pós-fato.

## Cobertura de providers

- Epic Fight 21.17.3.1 é a única authority positiva de guarda/stamina auditada para esta perk.
- Shield vanilla ou outros sistemas de block não entram automaticamente: precisam expor custo causal compatível com o contrato.
- Nenhum mod de magia/tecnologia/projeto próprio é owner por analogia.
- Stage 04.02 do RPG governa provenance/custo de confluências; Bridge Node não pode contar duplamente para thresholds de domínio.

## Pendências para Chat 2

- **P-A0093-01 BLOQUEANTE:** implementar availability server-authoritative: sem adapter causal suportado, node indisponível/não comprável.
- **P-A0093-02:** se surgir adapter suportado, modificar o custo antes do débito; proibir refund posterior, estimativa por impacto ou stamina paralela.
- **P-A0093-03:** testes provider-present/absent, bridge PP, respec e no-op purchase.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | VITALITY↔MARTIAL + availability. |
| Integração global | PASS | stamina continua provider-owned. |
| Qualidade/identidade | PASS | economia de guarda, não regen. |
| Topologia | PASS | Bridge VITALITY_MARTIAL. |
| Especializações | PASS | sem dupla contagem de PP. |
| PT-BR | PASS | efeito e indisponibilidade claros. |
| Notion | PASS | mutação + re-fetch PASS. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | Epic Fight classificado; sem hook = unavailable. |

Os 18 critérios passam no design porque a indisponibilidade é explícita e impede rank fantasma.