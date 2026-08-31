# A0107 — Conversão de Impacto

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** `3c569db9-f0db-813b-82c4-f57469727e4d`; corrigido e verificado pós-escrita.
- **Runtime:** `UNAVAILABLE_NODE` por predecessor A0093 indisponível e por ausência do P-0035 canônico na `main`.

## Contrato canônico

- Gateway VITALITY + A0093 Guarda Econômica ≥3 + A0095 Tenacidade ≥3.
- Exige provider simultâneo de pressão/interrupção e Stamina nativa.
- Futuramente converte no máximo 35% da pressão elegível para custo nativo de Stamina somente por função versionada `impact_pressure -> stamina_cost`.
- A redução de pressão só commita após débito de Stamina confirmado na mesma transação causal.

## Bloqueio estrutural atual

A0093 foi fechada no lote anterior como `UNAVAILABLE_NODE`. Logo A0107 é transitivamente não alcançável independentemente de qualquer infraestrutura futura. Além disso, `ImpactStaminaBridge` existe apenas como trabalho histórico/isolado da PR #15 e nunca foi conectado à `main`.

## Proibições

- não assumir 1 ponto de impacto = 1 ponto de Stamina;
- não converter por percentual da barra;
- não observar delta/polling de Stamina;
- não usar hunger/exhaustion como fallback;
- não reduzir pressão e depois tentar cobrar/refundar;
- não usar a PR histórica #15 como prova de binding canônico.

## Pendências para Chat 2

- `P-A0107-01` **BLOQUEANTE:** availability transitiva A0093→A0107; nenhum purchase/rank.
- `P-A0107-02`: manter P-0035 desconectado até A0093 estar disponível e existir quote/consume atômico provider-native versionado.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS em fail-closed | A0093 bloqueia corretamente. |
| Integração global | PASS | Stamina provider-native. |
| Qualidade/identidade | PASS | conversão, não imunidade. |
| Topologia | PASS | bridge VITALITY↔MARTIAL. |
| Especializações | PASS | PP anti-double-count. |
| PT-BR | PASS | transação explícita. |
| Notion | PASS | indisponibilidade persistida. |
| NeoVitae | PASS | ausente. |
| Providers | PASS em fail-closed | Epic Fight/P-0035 sem binding canônico. |

Os 18 critérios passam no design porque o node indisponível impede rank fantasma.