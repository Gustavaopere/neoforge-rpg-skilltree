# A0122 — Conservação Hídrica: Escalar

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Ramo SURVIVAL/HYDRATION_MOBILITY, 4 ranks, 1 PP/rank. Reduziria 3% por rank somente a parcela HYDRATION causal de escalada, até 12%, teto de 30% por evento.

## Boundary
Exige a mesma action_id de escalada e receipt `HYDRATION_CLIMB` positivo do adapter Thirst Was Reclaimed 3.0.4. Como não existe `METABOLIC_CLIMB` corporal comprovado, a derivação hídrica também está ausente.

## Exclusões
Não escrever diretamente em thirst, não usar polling/delta da barra e não converter Stamina em hidratação.

## Chat 2
Falhar fechado e desabilitar aquisição enquanto `HYDRATION_CLIMB` estiver ausente. Não habilitar só porque ParCool identifica escalada.