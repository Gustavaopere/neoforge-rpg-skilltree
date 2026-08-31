# A0128 — Conservação Hídrica: Lutar Corpo a Corpo

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL COM ADAPTER TWR.**

## Contrato
SURVIVAL/MARTIAL_HYDRATION, 4 ranks, 1 PP/rank. −3% por rank sobre o HYDRATION causal do mesmo ataque melee válido, até −12%; teto de 30%.

## Boundary
Reutilizar action_id/outcome e receipt METABOLIC de A0127; aceitar somente o parcel HYDRATION que o adapter TWR 3.0.4 atribuir à mesma ação.

## Exclusões
Stamina, DoT, reflexão, summons e procs não viram sede. Não escrever diretamente no capability de thirst.

## Chat 2
Uma resolução hídrica por ataque causal; ausência de receipt TWR desativa apenas esse canal.