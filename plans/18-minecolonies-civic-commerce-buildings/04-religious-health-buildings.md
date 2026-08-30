# 18.04 — Prédios religiosos e de saúde

## Religião

Building com capability `religion` pode sediar clergy/high-priest office, executar serviços definidos por doctrine e receber tithes/welfare religiosos via Stage 16/17. O sistema usa conteúdo fictício/provider-derived e não pressupõe religião real.

## Saúde

Building `health` representa hospital/clinic/service center. Integração deve usar necessidades/health hooks reais do MineColonies ou provider; não curar cidadãos ilimitadamente só porque o prédio existe.

Pode fornecer:

- capacidade de atendimento;
- prioridade térmica crítica no Stage 19;
- consumo de medicine/supplies via request system;
- worker healer/doctor quando adapter suportar;
- budget público/privado conforme regime.

## Estoque

Medicamentos/suprimentos são goods classificados pelo Stage 16 e requests MineColonies. Falta de item gera shortage; não criar cura grátis.

## Testes

- temple/health capability;
- missing supplies;
- provider absent;
- emergency heat priority;
- tithe transaction;
- worker assignment/lifecycle.

## Acceptance

Religião e saúde possuem prédio, recursos e capacidade mensurável, não aura abstrata sem custo.