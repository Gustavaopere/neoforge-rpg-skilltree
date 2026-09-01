# Stage 10.10 — Correção de escopo do corpus em 2026-09-01

## Motivo

Os lotes 11–20 do corpus editorial foram produzidos a partir de um snapshot histórico em que TerraFirmaCraft 4.2.8 estava instalado. Essa premissa estava desatualizada: a modlist canônica mais recente e a Auditoria Mestre da Modlist registram TerraFirmaCraft como removido do pack atual.

A PR #362, que preparava o lote 21 TFC, foi fechada sem merge assim que a divergência foi confirmada.

## Correção aplicada

- removidos do corpus ativo todos os 100 verbetes TFC introduzidos pelos lotes 11–20;
- removidos os 10 testes de lote que exigiam esses verbetes TFC;
- restaurado `10-10-corpus-lotes.md` ao último estado válido anterior ao início da cobertura TFC: 100 verbetes vanilla, 10 lotes e namespace `minecraft`;
- preservada infraestrutura genérica/fail-soft que não depende especificamente do TerraFirmaCraft, incluindo contratos editoriais reutilizáveis por outros providers opcionais;
- Stage 10.10 permanece aberto.

## Regra de escopo daqui em diante

Antes de iniciar qualquer lote não-vanilla, o provider deve ser reconciliado contra a modlist canônica mais recente e contra a Auditoria Mestre da Modlist. Logs antigos, guias históricos, branches anteriores e providers que já estiveram instalados não são evidência suficiente de presença atual.

O próximo lote do Stage 10.10 só pode ser escolhido depois dessa reconciliação. Não reutilizar a sequência TFC removida salvo se uma modlist futura voltar a instalar explicitamente o provider.