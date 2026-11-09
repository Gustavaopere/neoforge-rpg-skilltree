# A0322 — Escalador

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0322` — https://app.notion.com/3c569db9f0db81008027f7fade5e9bd0
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0322 aumenta **somente a velocidade/progresso de uma escalada já existente**, em +4% por rank:

- rank 1: ×1,04;
- rank 2: ×1,08;
- rank 3: ×1,12;
- rank 4: ×1,16.

A perk nunca concede capacidade nova de escalar e nunca converte o efeito em redução de STAMINA, Jump Boost, Speed, wall-run, teleporte, alcance de grab ou velocity injection.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥2.

Gate técnico futuro: ação explicitamente classificada `CLIMB`, server-authoritative, com parâmetro nativo positivo e mutável `CLIMB_SPEED` ou progresso monotônico equivalente no mesmo pipeline provider-native.

No snapshot atual esse binding não foi comprovado. Compra deve falhar antes de gastar PP; allocation legada permanece refundável/migrável e vale 0 PP para gates/thresholds enquanto indisponível.

Ladders/vines, wall-run, wall jump, cat leap, vault e cliff grab parado não entram automaticamente por semelhança visual.

## Providers e authority

- ParCool 4.0.0.3 possui ações reais de climb/cliff movement, mas o source 1.21.1-v4 auditado mantém o movimento/progresso relevante em fluxo interno/cliente e não expõe API pública server-authoritative segura para modificar `CLIMB_SPEED`.
- Epic ParCool 21.0.0 é bridge e não cria o parâmetro ausente.
- Epic Fight 21.17.3.1 não é provider de climb por presunção.
- RPG Skill Tree é owner da perk, rank, gate e adapter futuro; não é owner da física de escalada do provider.

## Contrato futuro obrigatório

Um adapter versionado deve entregar algo equivalente a:

`{action_id, provider_id, movement_step_id, parameter_id=CLIMB_SPEED, native_candidate}`

O RPG aplica uma única contribuição:

`final = native_candidate × (1 + 0.04 × rank)`

Se o provider usar duração/progresso em vez de velocidade, o adapter pode normalizar um canal monotônico equivalente, desde que o efeito final seja semanticamente "mais rápido na mesma escalada" e não uma física paralela.

Nunca reutilizar o valor já modificado como base no tick seguinte.

## Fallback / fail-closed

Sem parâmetro seguro, node indisponível. Não usar polling, delta de posição, animação, velocidade absoluta, redução de stamina ou buff genérico como aproximação.

Se um provider futuro suportar o contrato e outro não, aplicar somente no provider com binding comprovado.

## Anti-abuso e deduplicação

- uma contribuição por `action_id + movement_step_id + parameter_id`;
- nenhuma multiplicação recursiva sobre resultado já ampliado;
- movimento passivo/forçado não pode ser reclassificado como CLIMB;
- bridges observadoras não viram segundo owner da mesma etapa de movimento;
- a perk não gera Mastery por tick de escalada.

## Testes destinados ao Chat 3

1. snapshot atual: compra fail-before-spend;
2. allocation legada indisponível = 0 PP e refundável/migrável;
3. provider futuro seguro: ranks 1–4 resultam ×1,04/1,08/1,12/1,16 sobre o candidate nativo;
4. valor modificado não vira base do tick seguinte;
5. ação não-CLIMB não recebe efeito;
6. ladder/vine/wall-run/wall-jump/vault não entram sem adapter explícito;
7. ausência/mismatch do provider não cria fallback de Speed ou stamina;
8. múltiplas bridges não duplicam a contribuição;
9. multiplayer e lifecycle de availability;
10. dedicated server com provider presente/ausente.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não implementar listener heurístico, polling ou alteração genérica de movimento para tornar a perk adquirível.
