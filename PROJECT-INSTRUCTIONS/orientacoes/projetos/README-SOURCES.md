# Fontes de auditoria

Os dossiês desta pasta foram produzidos a partir de `plans/`, `plans/STATUS.md` e, quando necessário para distinguir plano de runtime, código/testes/CI da `main` dos quatro projetos. README raiz não é usado como prova suficiente de disponibilidade de hook.

## Política para projetos próprios em evolução

Cada lote do Chat 1 deve consultar `main` e `plans/STATUS.md` frescos dos quatro projetos e comparar os SHAs com o baseline de [`06-snapshot-reconciliation.md`](referencias/06-snapshot-reconciliation.md).

Quando houver avanço, usar a menor superfície técnica suficiente para provar o delta:

1. `plans/STATUS.md`;
2. task/plano alterado;
3. diff/commit/PR relevante;
4. contrato/código público;
5. testes/CI quando necessários para distinguir intenção de comportamento canônico.

O resultado deve alimentar [`12-capability-delta-coverage.md`](referencias/12-capability-delta-coverage.md), incluindo capacidades novas ou semanticamente alteradas ainda não citadas por nenhuma perk.

## Novos mods externos

Para mods externos adicionados à modlist, `PROJECT-INSTRUCTIONS/modlist/modlist.md` deve registrar presença/JAR/versão e o dossier individual atual deve registrar o contrato técnico. Se o dossier ainda não existir ou não sustentar a mecânica necessária, a perk permanece fail-closed até a reauditoria do provider. Os antigos guias temáticos não são mais uma etapa de manutenção.
