# A0141 — Adaptação Boreal

## Estado
**DESIGN APROVADO — PROVIDER-GATED / NÃO ADQUIRÍVEL NO RUNTIME ATUAL.**

## Contrato
Keystone SURVIVAL/ACCLIMATION_COLD, 1 rank, 2 PP. Lê 0–5 cargas `ENVIRONMENTAL_COLD`; benefícios históricos só podem tocar componente COLD explícito e quantificado do provider.

## Boundary
Cold Sweat fornece exposição ambiental read-only, mas não foi aprovado mapper para penalidade fisiológica COLD nem surcharge METABOLIC/HYDRATION específico de frio.

## Exclusões
Não alterar temperatura/thresholds, não usar BODY/WORLD/FREEZING_POINT/ICE damage como substitutos e não inventar penalidade.

## Chat 2
Manter compra desabilitada e allocation legado = 0 PP até existir pelo menos um componente mecânico COLD versionado.