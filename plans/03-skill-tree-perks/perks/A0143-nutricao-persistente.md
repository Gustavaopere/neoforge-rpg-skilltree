# A0143 — Nutrição Persistente

## Estado
**DESIGN APROVADO — IMPLEMENTÁVEL POR CANAL DISPONÍVEL.**

## Contrato
Keystone SURVIVAL/NUTRITION, 1 rank, 2 PP. Tier dinâmico pelo Nutritional Balance: MALNOURISHED/ENGORGED ×1,00; SAFE com menos da metade ON_TARGET ×1,05; SAFE com pelo menos metade ON_TARGET ×1,10; global ON_TARGET ×1,15. Multiplica somente recuperação natural positiva já emitida.

## Boundary
Consultar `getStatus()` e `getPlayerNutrients()` no evento de recuperação. N é dinâmico. Canais: cura natural Minecraft e regeneração natural de Stamina apenas quando houver adapter seguro.

## Exclusões
Não criar pulsos, não afetar spell heal/poção/lifesteal/comida, não assumir cinco nutrientes e não transformar nutrição em dano recebido.

## Chat 2
Cada canal é independente: hook ausente omite apenas aquele canal. Um evento causal recebe o multiplicador uma vez.