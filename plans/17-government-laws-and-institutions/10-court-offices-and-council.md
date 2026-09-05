# 17.10 — Corte, cargos e conselho

## OfficeDefinition

Cargos são IDs namespaced com:

- eligibility query;
- appointment/election/succession method;
- authority permissions;
- salary account;
- institution/building requirement opcional;
- term/tenure;
- vacancy policy.

## Cargos suportados pelo modelo

O conteúdo data-driven pode materializar funções como ruler, chancellor, treasurer, steward, marshal, spymaster, high priest, magister, chief engineer e representantes da assembleia. A lista não deve ser enum fechado.

## Conselho

`CouncilRecord` define seats e regras de decisão: consultivo, veto limitado ou approval obrigatório por domínio. O jogador não perde controle por um sistema opaco; a UI mostra requisito, voto/posição e consequência.

## Salários

Cargo remunerado usa payroll Stage 16. Office sem funds pode gerar arrears e efeito de legitimidade quando policy declarar.

## Vacância

Death/removal/provider change não deve deixar authority ghost. Resolver successor/acting office pela rule definida e registrar history.

## Testes

- appointment/election;
- qualification;
- vacancy;
- salary;
- council quorum;
- permission enforcement;
- history after regime transition.

## Acceptance

Toda ação política privilegiada pode responder “qual cargo autorizou?”.