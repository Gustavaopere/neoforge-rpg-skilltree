# 19.05 — Central Térmica níveis 1–5

## Objetivo

Definir progressão física e funcional que o Stage 14 transformará em VoxelModels/schematics.

## Nível 1 — Casa de caldeira local

- footprint pequeno;
- um Generator Core;
- armazenamento simples de combustível;
- pump/input básico;
- uma saída principal de rede;
- capacidade para núcleo inicial da colônia.

## Nível 2 — Caldeira ampliada

- sala técnica maior;
- armazenamento separado;
- redundância parcial de input/pumps;
- primeiro control point;
- capacidade/carga maior.

## Nível 3 — Usina distrital

- hall industrial com múltiplos módulos visuais/funcionais;
- chaminé/escape arquitetônico;
- tanks/pumps/shafts Create quando certificados;
- múltiplas saídas/substation feeds;
- sala de controle e manutenção.

## Nível 4 — Central metropolitana

- módulos de geração redundantes;
- manifolds/linhas independentes;
- maior buffer/armazenamento;
- automação/monitoramento;
- catwalks e maintenance access reais no blueprint;
- suporte a vários distritos.

## Nível 5 — Central Térmica monumental

- edifício industrial de grande escala, coerente com referência visual aprovada do projeto, mas geometria própria;
- vários módulos de geração e distribuição;
- grandes tanks, shafts, gearboxes, pipes, gauges e componentes Create reais onde suportados;
- control room, manutenção, redundância e múltiplos feeders;
- arquitetura legível como estágio final, não apenas nível 4 com mais blocos.

## Regras de upgrade

Cada nível possui VoxelModel completo, BOM e diff. Anchor, Generator Core identity e network connectors devem migrar determinística e seguramente.

## Capacidade

Números finais são data-driven e definidos após benchmark/balance simulation; a ordem estrita é `L1 < L2 < L3 < L4 < L5` em capacidade/controle, sem exigir crescimento linear.

## Testes

- gerar 5 modelos;
- preview/hash/export parity;
- connectors preservados;
- provider requirements;
- upgrade não apaga fuel inventory sem migração;
- functional graph válido.

## Acceptance

Os cinco níveis são construções distintamente reconhecíveis e operacionalmente progressivas.