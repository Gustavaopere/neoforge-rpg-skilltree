# A0320 — Salto Econômico

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0320` — https://app.notion.com/3c569db9f0db81378e54efa59992b78e
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0320 reduz somente custo **real, nativo e causal de um salto próprio**. Mantém duas lanes independentes:

- **STAMINA:** −3% por rank → custo ×0,97 / ×0,94 / ×0,91 / ×0,88;
- **METABOLIC / FoodData exhaustion:** −2% por rank → custo ×0,98 / ×0,96 / ×0,94 / ×0,92.

`HYDRATION` não participa. Custo inexistente continua zero; uma lane ausente nunca é sintetizada a partir da outra.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥1. Gate técnico adicional: pelo menos uma lane precisa expor um debit/segmento **PRECOMMIT**, server-authoritative e causalmente ligado a `JUMP`.

No snapshot atual não foi comprovado:

- hook NeoForge/RPG que isole a parcela de `FoodData` exhaustion causada pelo salto antes do commit;
- debit STAMINA de salto seguro e mutável em Epic Fight/ParCool.

Logo a compra deve falhar **antes de gastar PP**. Allocation legada indisponível vale 0 PP para gates/thresholds e permanece refundável/migrável.

## Providers e authority

- Minecraft `FoodData`: authority da lane metabólica vanilla.
- Epic Fight 21.17.3.1: authority de sua stamina, porém nenhuma ação `JUMP` com debit correlacionável foi comprovada neste lote.
- ParCool 4.0.0.3 / Epic ParCool 21.0.0: não promovidos a authority do debit server-side de salto por polling/client state.
- RPG Skill Tree: owner do node, ranks, composição e deduplicação futura; não cria recurso paralelo.

## Contrato futuro obrigatório

METABOLIC: um `JUMP_METABOLIC_PRECOMMIT_V1` deve expor identidade causal e apenas o segmento de exhaustion do salto.

STAMINA: um receipt equivalente a `{debit_id,pool_id,provider_id,action_id,action_type=JUMP,resource_type=STAMINA,native_amount}` deve existir antes do commit.

Se duas integrações observarem a mesma pool/debit, A0320 aplica uma única contribuição.

## Anti-abuso e proibições

Não usar `onGround`, delta Y, animação, polling de barra, refund pós-fato, regen aumentada, food/stamina injection ou wall-jump/vault/dodge como aproximação de salto. Knockback, queda, veículo, montaria e movimento de sublevel não contam.

## Testes destinados ao Chat 3

1. snapshot atual: purchase fail-before-spend mesmo com A0318 válida;
2. allocation legada indisponível = 0 PP e migrável/refundável;
3. futura STAMINA lane: ×0,97/0,94/0,91/0,88 exatos;
4. futura METABOLIC lane: ×0,98/0,96/0,94/0,92 exatos;
5. custo zero permanece zero e HYDRATION nunca muda;
6. debit compartilhado entre bridges recebe A0320 uma vez;
7. ação não-JUMP não recebe redução;
8. falha/version mismatch de uma lane não fabrica a outra;
9. nenhuma restituição pós-commit/polling/injection;
10. server authority, multiplayer e dedicated server.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não criar listener heurístico nem custo sintético para tornar a perk comprável.