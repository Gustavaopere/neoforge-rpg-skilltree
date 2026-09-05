# 16.07 — Obras, manutenção e orçamento público

## Objetivo

Fazer infraestrutura custar recursos financeiros além de materiais físicos quando a política econômica exigir.

## BudgetAccount

Tesouro possui envelopes/centros de custo data-driven: salários, obras, manutenção, pesquisa, welfare, defesa, aquecimento e outros.

Orçamento é autorização/limite; saldo real continua no ledger.

## Construção

Stage 14/18 fornece BOM/progresso. Stage 16 pode registrar:

- custo de materiais comprados externamente;
- salário/contrato da obra;
- taxa pública;
- financiamento;
- capital committed.

Materiais já existentes no warehouse não são “comprados do nada”; seu custo histórico pode entrar em accounting apenas se o modelo escolhido registrar valuation, sem criar transação fictícia.

## Manutenção

Prédios próprios podem possuir `MaintenanceProfile` por período econômico. Falta de verba gera estado de manutenção atrasada, redução de eficiência ou risco somente quando o Stage dono do prédio declara essa consequência.

## Prioridade

Em crise, Stage 17/19 pode reordenar envelopes; o sistema não gasta saldo reservado acima do permitido sem decreto/lei válido.

## Testes

- obra com budget suficiente/insuficiente;
- materials internos vs purchase;
- maintenance arrears;
- cancellation/refund rules;
- save/reload sem cobrar duas vezes;
- district project tagging.

## Acceptance

Obras e serviços públicos competem por recursos reais e o tesouro consegue explicar cada gasto.