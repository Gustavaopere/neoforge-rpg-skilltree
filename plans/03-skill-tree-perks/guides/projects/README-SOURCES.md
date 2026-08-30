# Fontes de auditoria

Os dossiês desta pasta foram produzidos a partir de `plans/`, `plans/STATUS.md` e, quando necessário para distinguir plano de runtime, código/testes/CI da `main` dos quatro projetos. README raiz não é usado como prova suficiente de disponibilidade de hook.

## Política para projetos próprios em evolução

Cada lote do Chat 1 deve consultar `main` e `plans/STATUS.md` frescos dos quatro projetos e comparar os SHAs com o baseline de [`06-snapshot-reconciliation.md`](06-snapshot-reconciliation.md).

Quando houver avanço, usar a menor superfície técnica suficiente para provar o delta:

1. `plans/STATUS.md`;
2. task/plano alterado;
3. diff/commit/PR relevante;
4. contrato/código público;
5. testes/CI quando necessários para distinguir intenção de comportamento canônico.

O resultado deve alimentar [`12-capability-delta-coverage.md`](12-capability-delta-coverage.md), incluindo capacidades novas ou semanticamente alteradas ainda não citadas por nenhuma perk.

## Novos mods externos

Para mods adicionados à modlist depois do snapshot dos três guias, usar documentação oficial/arquivo instalado para versão e função e incorporá-los aos guias pertinentes antes do próximo fechamento de lote. O primeiro delta registrado por esta regra é **Mobstein 5.4.4**, verificado na página oficial CurseForge e incorporado aos guias de Gameplay e Magia em 2026-08-30.
