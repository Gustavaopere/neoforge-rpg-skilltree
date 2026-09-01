# A0206 — Dano de Ender II

## Estado

- **Design:** APROVADO EM FAIL-CLOSED em 2026-08-31.
- **Notion:** 3c569db9-f0db-81bc-86d4-e27ad910acf7; mastery exata, receipt causal, availability, transação e lifecycle corrigidos; re-fetch PASS.
- **Runtime observado:** não existe serviço canônico que prove deslocamento dimensional próprio causado por uma ação ENDER. EntityTeleportEvent genérico é insuficiente. A0206 é **UNAVAILABLE_NODE/não comprável**.

## Contrato canônico

- 1 rank; exige A0205 ≥3, ender_mastery_lane_id exata ≥20 e fonte ENDER real.
- Receipt de deslocamento dimensional próprio confirmado abre RPG_ENDER_PRIMER por 80 ticks.
- Primeiro direct_ender_outcome durante o primer aplica RPG_ENDER_RUPTURE ao alvo por 100 ticks e consome o primer.
- Próxima ação ENDER direta, distinta da aplicadora e contra o mesmo alvo, recebe ×1,18 no componente ENDER e consome a Ruptura.
- Estados não acumulam; callbacks e derived components não contam como ações.

## Mastery exata

“Ender Mastery” não é soma de escolas. O gate consulta uma lane exata derivada de SchoolType.getId(), allowlisted e aceita pelo MasteryLaneCatalog. O catálogo atual não aceita a forma namespace/path produzida para escolas de addons; isso precisa ser corrigido antes da aquisição.

## Causalidade do deslocamento

O receipt deve provar:

- jogador que iniciou a ação;
- action/cast id de origem;
- provider e versão;
- commit do deslocamento;
- que não foi teleporte forçado, comando, respawn ou mudança administrativa de dimensão.

Diferença de posição, portal/VFX, destino no End e evento NeoForge isolado não bastam.

## Transações e lifecycle

Primer nasce após commit. Aplicação da Ruptura e consumo do primer usam reserva→commit/rollback. Consumo da Ruptura e ×1,18 também. Cancelamento conserva o estado. Limpar tudo em morte, logout, troca de dimensão, rank/dependency loss, respec e rules reload.

## Pendências para Chat 2

- **P-A0206-01 BLOQUEANTE:** availability transitiva de A0205.
- **P-A0206-02 BLOQUEANTE:** ender mastery lane exata e catálogo de addon.
- **P-A0206-03 BLOQUEANTE:** own-dimensional-displacement receipt causal.
- **P-A0206-04:** action/outcome/target correlation e reservation→commit/rollback.
- **P-A0206-05:** lifecycle e testes forced teleport/command/respawn/cancel/dedup.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS no design | A0205, lane 20 e receipt próprio. |
| Integração global | PASS | primer/ruptura no outcome ledger canônico. |
| Qualidade/identidade | PASS | combo dimensional causal, não teleporte genérico. |
| Topologia | PASS | notable ARCANE/ENDER↔AGILITY. |
| Especializações | PASS | school lane exata, sem agregação. |
| PT-BR | PASS | Primer/Ruptura descritos por comportamento. |
| Notion | PASS após correção | causalidade e transaction gravadas. |
| NeoVitae | PASS | ausente. |
| Providers | PASS no design | Fire's/Somake só por receipt versionado. |

Os 18 critérios passam **no design**; o evento genérico de teleporte é explicitamente rejeitado.
