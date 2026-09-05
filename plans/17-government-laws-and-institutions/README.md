# 17 — Governo, Leis, Regimes e Instituições

## Objetivo

Criar uma camada política server-authoritative que permita governar a sociedade simulada sem reduzir ideologias/regimes a bônus cosméticos.

A decisão fundamental é separar:

```text
GovernmentForm != EconomicRegime != LawSet
```

Uma monarquia pode operar economia capitalista ou feudal; uma teocracia pode adotar propriedade privada ou comunal; uma tecnocracia/magocracia pode coexistir com diferentes regras econômicas. O sistema descreve instituições e contratos, não rótulos decorativos.

## Singleplayer-first

O jogador é o principal agente político. NPCs/cidadãos fornecem legitimidade, representação, oposição e consequências. Não projetar o sistema em torno de PvP, eleições entre jogadores ou griefing político.

## Law resolver

Precedência aprovada:

```text
hard invariants/constitution constraints
→ lei geral de realm/colônia
→ política distrital
→ decreto específico/temporário
```

A camada específica pode substituir apenas cláusulas delegáveis. Todo resultado guarda provenance/revision para explicar “por que esta regra vale aqui”.

## Regimes discutidos

- capitalismo;
- economia comunal/comunista;
- teocracia;
- tecnocracia;
- magocracia;
- feudalismo;
- servidão;
- escravidão como status legal/econômico de coerção fictícia, sem vínculo a grupo real e com consequências sociais/políticas sistêmicas.

## Instituições

- assembleia;
- eleições/sufrágio;
- corte/conselho/cargos;
- treasury administration;
- instituições religiosas, técnicas e arcanas;
- oposição/legitimidade;
- transição de regime.

## Ordem

1. `01-government-economic-regime-separation.md`
2. `02-laws-decrees-precedence-and-enforcement.md`
3. `03-assembly-elections-and-suffrage.md`
4. `04-capitalism.md`
5. `05-communal-communist-economy.md`
6. `06-theocracy-faith-and-doctrines.md`
7. `07-technocracy.md`
8. `08-magocracy.md`
9. `09-feudalism-serfdom-and-slavery.md`
10. `10-court-offices-and-council.md`
11. `11-transition-opposition-and-legitimacy.md`
12. `12-tests-balance-and-migration.md`

## Definition of Done

Trocar de regime altera ownership, contratos, tributação, elegibilidade política e instituições de forma auditável; laws resolvem por território/decreto; eleições usam eleitorado calculado; transições preservam ledger/propriedade histórica e geram consequências sem apagar estado incompatível.