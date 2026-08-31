# A0094 — Recuperação de Guarda

## Estado

- **Design:** APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE` em 2026-08-31.
- **Notion:** `3c569db9-f0db-81f2-810a-fdfe37dbcdf8`; mutado e re-fetch PASS.
- **Runtime observado:** fórmula pura existe, porém `FAIL_CLOSED_A0094=true`; não há receipt causal de GUARD_BREAK + extension point de recovery.

## Contrato canônico

- A0093 Guarda Econômica ≥2.
- 4 ranks: +3% de velocidade de recuperação após uma quebra de guarda real por rank, máximo +12%.
- Exige dois fatos do mesmo provider: `GUARD_BREAK` causal e um parâmetro real de recuperação modificável.
- Como A0093 está indisponível, A0094 também é transitivamente indisponível.

## Evidência provider

Epic Fight distingue `GUARD` e `GUARD_BREAK` internamente e reproduz animação/som próprios quando stamina não suporta a guarda. Isso não fornece, por si só, um receipt público estável nem um recovery scalar seguro. Inferir recovery por animação, knockback, falta de stamina ou cooldown inventado é proibido.

## Cobertura de providers

- Epic Fight 21.17.3.1 é a authority auditada de guard break.
- Outros sistemas defensivos só podem participar se expuserem break + recovery causal equivalentes.
- Mods de magia, tecnologia e projetos próprios são N/A neste contrato.
- A bridge VITALITY↔MARTIAL segue a provenance/custo do Stage 04.02 e não duplica PP de domínio.

## Pendências para Chat 2

- **P-A0094-01 BLOQUEANTE:** availability transitiva A0093→A0094; node não comprável.
- **P-A0094-02:** futuro adapter deve provar GUARD_BREAK e alterar recovery provider-native, sem refund de stamina nem redução de animação como proxy.
- **P-A0094-03:** regressões provider-present/absent, prerequisite loss, respec/rules reload e bridge PP.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | depende de A0093≥2 e availability. |
| Integração global | PASS | recovery continua provider-owned. |
| Qualidade/identidade | PASS | recuperação pós-break, não custo. |
| Topologia | PASS | VITALITY↔MARTIAL, Camada 2. |
| Especializações | PASS | bridge sem dupla contagem. |
| PT-BR | PASS | distinção break/recovery explícita. |
| Notion | PASS | mutação + re-fetch PASS. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | nenhum hook seguro inventado. |

Os 18 critérios passam no design com indisponibilidade transitiva explícita.