# Stage 10.10 — Providers permanentemente excluídos

## Regra normativa

Por decisão explícita do projeto em 2026-09-01, TerraFirmaCraft não faz parte do escopo futuro do Compêndio Natural e não deve voltar a ser adicionado.

Namespaces permanentemente excluídos:

- `tfc`
- `terrafirmacraft`

A exclusão vale independentemente de:

- snapshots históricos da modlist;
- branches ou PRs antigas;
- documentação ou guias anteriores;
- eventual presença acidental do provider em um runtime de teste;
- disponibilidade futura de uma versão compatível.

## Consequências operacionais

- não criar novos lotes editoriais para esses namespaces;
- não manter corpus `OPTIONAL` ou `LEGACY` desses namespaces;
- não criar referências editoriais para entradas desses namespaces;
- não usar esses providers como exemplo de prioridade ou provider-alvo;
- qualquer reaparecimento no corpus deve ser tratado como regressão de escopo e bloquear CI;
- a seleção de novos lotes não-vanilla continua obrigatoriamente baseada na modlist canônica atual e na Auditoria Mestre da Modlist, além desta lista de exclusões permanentes.

## Histórico

Os lotes TFC 11–20 foram produzidos com base em estado histórico desatualizado e são removidos pela PR corretiva correspondente. A PR do lote 21 foi encerrada sem merge. Este documento impede que o mesmo provider seja reintroduzido em ciclos futuros.
