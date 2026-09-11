# A0329 — Freio Técnico

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0329` — https://app.notion.com/3c569db9f0db8176a0a7e17ee461baea
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0329 modifica somente a política de perda de cargas de A0328 durante uma reversão brusca de direção `DIRECTION_BREAK_V1` válida.

Quando o hard turn zeraria cargas de Ímpeto, A0329 preserva:

- rank 1: até 1 carga;
- rank 2: até 2 cargas;
- rank 3: até 3 cargas.

Fórmula:

`charge_after = min(charge_before, rank)`

A perk **nunca cria carga**. Exemplo: 5 cargas + rank 2 → 2; 1 carga + rank 3 → 1, não 3.

O hard turn continua reiniciando o arm timer de A0328; A0329 só altera a quantidade imediatamente preservada.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0328 Ímpeto ≥2.

A0328 está `UNAVAILABLE_NODE` no snapshot atual. Portanto A0329 também está indisponível por closure transitiva.

Mesmo após A0328 abrir, A0329 exige:

- `charge_before > 0`;
- `DIRECTION_BREAK_V1` server-authoritative;
- mesmo owner e `space_id`;
- comparação de heading locomotor voluntário antes/depois no frame local;
- threshold configurado e versionado;
- `MOMENTUM_DIRECTION_BREAK_POLICY_V1` claim-once.

Compra deve falhar antes de consumir PP enquanto a closure transitiva ou qualquer contrato local estiver fechado. Allocation legada indisponível vale 0 PP e é refundável/migrável.

## Providers e authority

- RPG Skill Tree: owner de A0328/A0329, cargas, ledger e policy de preservação.
- Minecraft/NeoForge: fornece locomoção base, mas não o conceito RPG de direction break.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0 / Epic Fight 21.17.3.1: podem futuramente fornecer evidência/adapters de manobra quando houver estado server-side coerente; não são owners das cargas.
- Sable 2.0.5 / Create Aeronautics: heading deve ser medido no frame local do sublevel; rotação/translação do parent não conta.
- Volcanoes/Enshrouded/Black Arcana: não fornecem authority para este estado.

## Contrato futuro obrigatório

No hard turn detectado, antes do reset padrão de A0328:

`MOMENTUM_DIRECTION_BREAK_POLICY_V1` recebe algo equivalente a:

`{direction_break_id, owner_uuid, space_id, charge_before, local_heading_before, local_heading_after}`

A0329 faz claim atômico de `direction_break_id` e devolve:

`preserved = min(charge_before, rank)`

O ledger grava exatamente esse valor, reinicia `continuous_since/next_charge_tick` conforme a política de A0328 e reconcilia o único modifier `rpgskilltree:agility_momentum_speed`.

## O que não é direction break

Não ativam A0329:

- girar câmera/corpo sem alterar deslocamento;
- lock-on/facing;
- parar sem reversão brusca válida;
- pular no lugar;
- knockback;
- teleporte;
- montaria/veículo;
- conveyor;
- rotação/translação de contraption/sublevel;
- mudança de facing causada por outra mecânica.

## Fallback / fail-closed

Enquanto A0328 ou `DIRECTION_BREAK_V1` / `MOMENTUM_LEDGER_V1` / `MOMENTUM_DIRECTION_BREAK_POLICY_V1` não estiverem operacionais, A0329 não pode ser comprada nem ativada.

Não substituir por bônus genérico de controle/velocidade, grace maior, stamina refund ou imunidade total à perda de Ímpeto.

Se A0328 se tornar operacional antes de A0329, comportamento padrão continua: hard turn zera as cargas.

## Anti-abuso e deduplicação

- cada `direction_break_id` aplica policy uma única vez;
- múltiplas callbacks/bridges do mesmo giro não preservam repetidamente;
- A0329 nunca aumenta `charge_before`;
- hard turn continua quebrando continuidade e reiniciando geração;
- câmera/facing isolados não contam;
- movimento externo não produz preserve event válido.

## Testes destinados ao Chat 3

1. snapshot atual: indisponível por closure A0328;
2. allocation legada indisponível = 0 PP e refundável/migrável;
3. futuro hard turn com 5 cargas preserva 1/2/3 nos ranks 1/2/3;
4. `charge_before < rank` nunca cria cargas adicionais;
5. arm timer é reiniciado mesmo quando cargas são preservadas;
6. mesmo `direction_break_id` não reaplica por callbacks/bridges duplicadas;
7. câmera/facing/lock-on sem mudança locomotora não ativam;
8. knockback/teleport/vehicle/contraption/sublevel passivo não ativam;
9. ausência/mismatch dos contracts mantém fail-closed;
10. multiplayer/dedicated server com heading em frame local.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não criar detector heurístico de hard turn por yaw/câmera ou delta de posição. A0329 só existe sobre o ledger A0328 e `DIRECTION_BREAK_V1` autoritativo.
