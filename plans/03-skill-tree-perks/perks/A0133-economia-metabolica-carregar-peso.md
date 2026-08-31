# A0133 — Economia Metabólica: Carregar Peso

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
SURVIVAL/ENCUMBRANCE_METABOLIC, 4 ranks, 1 PP/rank. Reduziria 3% por rank somente sobrecusto metabólico positivo causado por encumbrance corporal real, até 12%.

## Boundary
FUTURE_PROVIDER_CONTRACT: provider de encumbrance deve expor carga corporal server-authoritative e `METABOLIC_LOAD` causal. Nenhum provider aprovado existe hoje.

## Exclusões
Slots, inventário, armor points, equipamento visual e massa Create Aeronautics/Sable não são carga corporal por heurística.

## Chat 2
Manter compra desabilitada e PP legado = 0 até provider real existir; nunca criar o sobrecusto que a perk pretende economizar.