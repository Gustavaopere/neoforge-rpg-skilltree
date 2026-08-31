# A0120 — Conservação Hídrica: Nadar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL com TWR.**

## Contrato
4 ranks; −3%/rank até −12% somente da contribuição HYDRATION atribuída à natação; cap hídrico global 30%.

## Hook
Action-id de natação + receipt metabólico real são propagados ao adapter Thirst Was Reclaimed 3.0.4. A0120 modifica apenas o parcel hídrico do provider e nunca a barra diretamente.

## Exclusões
Água/chuva não hidratam nem reduzem custo por inferência; correnteza/movimento passivo não contam. Não duplicar A0119.

## Chat 2
Testar causalidade por action, modificadores TWR e dedupe de ticks de movimento.