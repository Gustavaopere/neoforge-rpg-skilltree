# 19.06 — Subestações, endpoints/radiadores e prioridades

## Substations

Dividem rede principal em setores/distritos e possuem throughput. Podem ser prédios/blocks próprios ou conjuntos validados no Stage 14.

## Endpoints

Cada prédio aquecido precisa de `HeatEndpoint` ligado ao serviço/blueprint. Residences e prédios desconhecidos podem ganhar endpoint via adapter quando geometry exacta não estiver disponível.

## Prioridades

`HeatPriorityPolicy` vem Stage 17/15 e pode ordenar categorias como saúde, habitação, governo, indústria e pesquisa. Não hardcodar que uma categoria sempre vence; baseline/default é data-driven.

## Racionamento

Quando supply < demand:

- alocação respeita prioridades e mínimos configurados;
- UI mostra demanda, recebido e motivo;
- reduzir heat de um setor libera capacity real;
- não existe percentual invisível que exceda throughput.

## Manual override

Jogador pode ajustar priority por distrito/building se office/law permitir. Servidor valida authority.

## Testes

- supply shortage;
- two priority classes;
- minimum allocation;
- substation capacity;
- endpoint disabled;
- district override/decree.

## Acceptance

Crise exige decisão de distribuição quando capacidade é insuficiente e o resultado é explicável.