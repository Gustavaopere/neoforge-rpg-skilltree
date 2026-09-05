# 17.01 — Separação entre governo, regime econômico e leis

## Domínios

`GovernmentForm`: como autoridade política é composta/substituída.

`EconomicRegime`: regras estruturais de propriedade, trabalho, distribuição e iniciativa.

`LawSet`: parâmetros concretos em vigor.

`InstitutionSet`: cargos/órgãos existentes.

Todos usam IDs namespaced data-driven e revisions; não enums fechados para conteúdo extensível.

## Exemplos válidos

- monarquia + capitalismo + assembleia consultiva;
- monarquia feudal;
- teocracia + propriedade privada;
- teocracia comunal;
- tecnocracia + economia de mercado;
- magocracia + feudalismo arcano.

Combinações podem ter constraints explícitos, mas o código não deve pressupor que “teocracia = economia X”.

## State

`GovernanceState` referencia IDs ativos e history entries. Mudança política registra provenance, effectiveAt e migration plan de contratos afetados.

## Authority

Stage 17 não move item/dinheiro diretamente: emite `EconomicPolicySnapshot` consumido pelo Stage 16 e `GovernanceQuery` para outros sistemas.

## Testes

- combinações permitidas;
- constraint inválida;
- reload de definitions;
- state history;
- troca de regime sem apagar laws desconhecidas;
- client recebe view sanitizada/revision.

## Acceptance

Nenhum subsistema decide comportamento econômico apenas lendo um nome de governo.