# A0113 — Reforço de Campo

## Estado
**DESIGN APROVADO — FAIL-CLOSED / NÃO COMPRÁVEL enquanto A0110/P-0036 estiver indisponível.**

## Contrato
Notable SURVIVAL, 3 ranks. Cada ferramenta manual elegível recebe `rpgskilltree:tool_instance_id` server-side. Após 12 coletas legítimas pelo mesmo `player_uuid + tool_instance_id`, abre janela de 600 ticks. O próximo reparo manual compatível da mesma instância, com custo nativo integral pago, recebe +15/+25/+35% sobre a durabilidade realmente restaurada e consome a janela.

## Invariantes
Blocos colocados/automação/AFK não geram progresso. Cópias com instance-id duplicado invalidam o estado até reseed/reconciliação; registry id, nome, slot ou NBT parcial não identificam a mesma ferramenta.

## Hook
Evento causal de coleta + identidade persistente da instância + evento de reparo interceptável. P-0036 continua mandatory.

## Chat 2
Implementar fail-closed transitivo, anti-clone, persistência da janela e material integral antes do bônus.