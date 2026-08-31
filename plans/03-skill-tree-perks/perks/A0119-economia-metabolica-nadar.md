# A0119 — Economia Metabólica: Nadar

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL.**

## Contrato
4 ranks; −3%/rank até −12% da exhaustion positiva realmente atribuída à natação autopropelida; cap METABOLIC 30% por evento.

## Hook
BodyCostResolver correlaciona deslocamento/natação server-authoritative à chamada real de FoodData exhaustion. Usar valor observado, não distância×constante reconstituída.

## Exclusões
Correnteza, veículos, knockback, Carry On/contraption e deslocamento passivo não entram. Movimento subaquático sem débito FoodData não recebe benefício.

## Chat 2
Testar água corrente, natação normal, sprint-swim e ausência de receipt.