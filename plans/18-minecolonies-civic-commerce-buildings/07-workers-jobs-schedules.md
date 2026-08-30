# 18.07 — Workers, jobs e schedules

## Objetivo

Adicionar profissões necessárias aos novos serviços usando o lifecycle MineColonies quando a API suportar.

## Jobs planejáveis

IDs extensíveis podem representar:

- merchant;
- banker/financial clerk;
- tax officer;
- magistrate/administrator;
- priest/clergy;
- healer/doctor;
- researcher;
- engineer;
- heat-plant operator;
- bureaucratic clerk.

A lista é data-driven e pode crescer sem enum fechado.

## Separar job de office

`Job` = trabalho cotidiano/produtivo. `Office` do Stage 17 = autoridade política. Um treasurer pode ser office político com staff clerk como job; não fundir conceitos.

## Schedule

Usar schedule/AI hooks MineColonies comprovados. Atividades econômicas são eventos semânticos (atendeu venda, processou folha, operou serviço), não pagamento por cada AI tick.

## Ausência/falha

Se provider API não permitir job custom seguro para uma função, capability pode operar com interação do jogador/serviço abstrato até uma integração comprovada; não mixinar internals sem plano/licença/teste.

## Testes

- assignment/unassignment;
- citizen unload;
- job salary contract;
- schedule resume after restart;
- no duplicate economic events;
- provider absence.

## Acceptance

Profissões novas têm identidade e lifecycle estáveis e não duplicam salários/transações por AI callbacks repetidos.