# 16.09 — Pobreza, dívida, assistência e desigualdade

## Pobreza

Estado derivado de recursos/necessidades, não debuff aplicado por nome. Usar indicadores econômicos agregados: renda disponível, arrears, patrimônio líquido, acesso a bens/serviços essenciais.

## Dívida

`DebtContract` possui creditor, debtor, principal, juros/policy quando aplicável, origem, calendário e status. Juros não rodam a cada tick; avançam em períodos econômicos discretos e bounded.

## Welfare

Políticas do Stage 17 podem criar:

- transferência monetária;
- subsídio de alimento/combustível/aluguel;
- salário público emergencial;
- perdão/reestruturação de dívida permitido.

Cada benefício consome tesouro/estoque real e gera receipt. Sem recursos, benefício pode ficar parcialmente atendido; não cria moeda/mercadoria silenciosamente.

## Desigualdade

Calcular métricas simples e verificáveis sobre snapshots de riqueza/renda; Gini ou outra medida só entra com implementação/testes corretos. Não fazer O(n²) por tick.

## Integração social

Pobreza, wage arrears, desigualdade e falta de heat/services publicam fatores para `DiscontentService` do Stage 20. Stage 16 não inicia rebelião.

## Testes

- poverty threshold data-driven;
- debt repayment;
- arrears;
- welfare budget exhausted;
- inequality snapshot;
- no double count após citizen move/die/reload.

## Acceptance

Crise econômica produz fatos sociais explicáveis e fiscalmente consistentes.