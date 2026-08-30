# 17.09 — Feudalismo, servidão e escravidão

## Escopo

Modelar relações coercivas/hierárquicas como **sistemas fictícios de status legal/econômico**, nunca como classificação de raça, etnia, religião ou outro grupo real.

## Feudalismo

`FeudalTitle`/land rights podem associar:

- lord/vassal;
- territory/property rights;
- tribute;
- military/service obligation;
- succession/transfer rules;
- realm authority do Stage 20.

## Servidão

`LaborStatus: SERF` pode limitar mobilidade contratual e vincular obrigações a estate/property conforme law. Produção/salário/tribute passam pelo Stage 16; não alterar AI do cidadão por tick sem integração explícita.

## Escravidão

`LaborStatus: ENSLAVED` representa coerção legal no mundo fictício e pode alterar freedom of employment, compensation rules e property claims conforme regime. Deve produzir custos/consequências sistêmicas configuráveis: descontentamento, resistência/rebelião, legitimidade e relações diplomáticas. Não é um “buff de produção grátis”.

## Abolição/transição

Mudança de law cria records de emancipação e encerra/transforma obligations. Dívidas/ownership relacionadas precisam de migration explícita; não apagar histórico do cidadão.

## Segurança de design

Nenhum critério de status pode usar atributos sensíveis do mundo real. Conteúdo textual permanece ficcional e pt-BR.

## Testes

- title/tribute;
- serf obligation;
- coerced status transition;
- emancipation;
- wage/production accounting;
- discontent integration;
- save migration.

## Acceptance

Hierarquias coercivas têm custo, estado e consequências reais, sem mecânica gratuita nem targeting de grupos reais.