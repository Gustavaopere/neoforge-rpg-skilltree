# A0318 — Passo Leve

## Estado

- **Chat 1:** DESIGN APROVADO / IMPLEMENTÁVEL.
- **Disponibilidade atual:** não depende de Specialist; respeita o Gateway AGILITY/topologia canônica.
- **Fonte canônica:** Notion `A0318` — https://app.notion.com/3c569db9f0db81dd9fb1e78e2e55c1f5
- **Correção/persistência:** Gate e Regra corrigidos e re-fetched em 2026-09-05.

## Contrato aprovado

Passive ranked de movimento:

- rank 1: +2%;
- rank 2: +4%;
- rank 3: +6%;
- rank 4: +8%;
- rank 5: +10%.

Uma única contribuição `RPG_AGILITY_MOVEMENT_SPEED = 0.02 × rank` sobre `Attributes.MOVEMENT_SPEED`, usando `AttributeModifier.Operation.ADD_MULTIPLIED_BASE` e ID estável. O teto próprio da perk é +10%; não cria cap global oculto.

## Gate e dependências

Não existe dependency de Specialist. Aquisição respeita o Gateway AGILITY principal e as regras normais de rank/custo/topologia. A perk não depende de sprint, stamina, dodge, morph, ParCool ou provider opcional.

## Provider-native e runtime

- RPG Skill Tree `AttributeNodeEffectRuntime`: authority da reconciliação de efeitos derivados.
- Minecraft `Attributes.MOVEMENT_SPEED`: atributo canônico.
- Pufferfish Attributes pode coexistir para atributos próprios, mas **não** cria segunda lane de movement speed para A0318.
- Usar modifier transitório estável; não escrever base value e não registrar listener paralelo.

## Lifecycle e deduplicação

Rank muda valor, não identidade. Refresh idêntico é idempotente. Rank loss/respec/reload/logout e mudança de target removem/reconciliam o estado segundo o lifecycle canônico. A perk não modifica stamina, custo de sprint, distância de dodge/roll, parkour, aceleração ou animação.

## Fallback

Vanilla MOVEMENT_SPEED + runtime canônico tornam o design implementável. Se o runtime não conseguir reconciliar o atributo por erro/mismatch, falhar fechado; não criar implementação ad hoc alternativa.

## Testes obrigatórios para Chat 3

1. ranks 1–5 produzem exatamente +2/+4/+6/+8/+10%;
2. operação é `ADD_MULTIPLIED_BASE`, não `MULTIPLY_TOTAL`;
3. um único modifier ID estável, sem stacking em refresh/relogin;
4. mudança de rank substitui valor anterior;
5. respec/rank loss/reload/logout remove/reconcilia corretamente;
6. Pufferfish ausente não altera o funcionamento;
7. não altera stamina/sprint/dodge/parkour/ParCool;
8. composição com outros modifiers respeita semântica vanilla, sem cap global inventado;
9. datapack/reload mantém idempotência;
10. multiplayer e dedicated server.

## Handoff Chat 2

Esta é a única perk do lote atualmente implementável sem capability nova. Implementar pelo `AttributeNodeEffectRuntime`; não criar segundo motor de movimento.