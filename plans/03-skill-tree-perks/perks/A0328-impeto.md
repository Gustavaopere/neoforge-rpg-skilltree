# A0328 — Ímpeto

## Estado

- **Chat 1:** DESIGN APROVADO / FAIL-CLOSED.
- **Disponibilidade atual:** `UNAVAILABLE_NODE`.
- **Fonte canônica:** Notion `A0328` — https://app.notion.com/3c569db9f0db81cfbb4cf17720772eb4
- **Snapshot auditado:** NeoForge 1.21.1 / Java 21 / modlist 2026-08-30.

## Identidade da perk

A0328 cria um recurso transitório RPG-owned, `RPG_MOMENTUM_CHARGE`, somente a partir de locomoção **voluntária, contínua e server-authoritative**.

Regras aprovadas:

- armar após 40 ticks de movimento elegível contínuo;
- depois, +1 carga a cada 15 ticks adicionais;
- máximo 5 cargas;
- cada carga concede +0,6% de `MOVEMENT_SPEED` por rank;
- rank máximo 4;
- máximo absoluto próprio: 5 cargas × 4 ranks × 0,6% = +12%.

A carga pertence ao jogador e não à ação concreta, mas só pode ser gerada por contexto locomotor causalmente válido.

## Gate e dependências

Gate estrutural: Gateway AGILITY + A0318 Passo Leve ≥2.

Gate técnico futuro: `VOLUNTARY_MOVEMENT_CONTEXT_V1=true` continuamente, com owner/space/cause válidos e `MOMENTUM_LEDGER_V1` server-side.

Sprint/caminhada voluntários podem contar. Não contam automaticamente:

- teleporte;
- dodge/roll;
- launch;
- knockback;
- veículo/montaria;
- conveyor;
- corrente;
- movimento passivo do espaço/sublevel.

No snapshot atual faltam o contexto causal e o ledger; portanto compra falha antes de gastar PP. Allocation legada indisponível permanece refundável/migrável e vale 0 PP para gates/thresholds.

## Providers e authority

- Minecraft/NeoForge: authority de `Attributes.MOVEMENT_SPEED`.
- RPG Skill Tree: owner de `RPG_MOMENTUM_CHARGE`, ledger, decay e modifier de projeção.
- ParCool 4.0.0.3 / Epic Fight 21.17.3.1 / Epic ParCool 21.0.0: podem futuramente fornecer evidência de ações concretas, mas não viram owners das cargas.
- Sable 2.0.5 / Create Aeronautics: exigem vetor locomotor no frame/space local; transformação passiva do parent não conta.

A `main` auditada **não contém `AttributeNodeEffectRuntime` nem helper genérico de modifier transitório**. Chat 2 não deve depender desse helper inexistente.

## Contrato futuro — ledger

Criar somente quando existir contexto causal seguro:

`MOMENTUM_LEDGER_V1 {owner_uuid, space_id, continuous_since, last_valid_tick, next_charge_tick, charge_count}`

Regras determinísticas:

1. movimento válido contínuo por 40 ticks arma;
2. +1 carga a cada 15 ticks contínuos depois do arm;
3. cap 5;
4. não conceder crédito retroativo após pausas;
5. estado é server-authoritative.

## Projeção de velocidade

Com ledger válido, reconciliar uma única instância transitória:

`rpgskilltree:agility_momentum_speed`

- atributo: `Attributes.MOVEMENT_SPEED`;
- amount: `0.006 × rank × charge_count`;
- operação: `ADD_MULTIPLIED_BASE`.

O modifier deve refletir exatamente o estado atual do ledger e nunca empilhar IDs por carga/rank.

## Perda e decay

Perda simples de movimento voluntário, sem hard turn:

- grace de 10 ticks;
- depois, remover 1 carga a cada 10 ticks.

Retomar antes do fim da grace preserva cargas e reinicia somente o relógio de geração; não concede crédito retroativo.

Um `DIRECTION_BREAK_V1` válido é quebra dura: por padrão zera cargas e reinicia arm timer. A0329 pode interceptar somente essa política de reset para preservar parte das cargas.

## Fallback / fail-closed

Sem contexto causal/ledger, node indisponível. Não substituir por:

- bônus fixo após 2 s;
- `isSprinting` isolado;
- delta de posição;
- velocidade observada;
- animação;
- movement packet heurístico.

## Anti-abuso e deduplicação

- exatamente um ledger por jogador;
- um modifier de velocidade estável;
- movimento externo não gera tempo nem carga;
- pause/reconnect/reload não concede cargas retroativas;
- mudança de dimension/space invalida continuidade quando o contrato exigir;
- A0328 não gera Mastery;
- A0329 não cria cargas, apenas pode preservar existentes em hard turn válido.

## Testes destinados ao Chat 3

1. snapshot atual: compra fail-before-spend;
2. allocation legada indisponível = 0 PP e migrável/refundável;
3. futuro ledger: 40 ticks para armar, depois +1/15t até 5;
4. ranks/cargas produzem exatamente `0.006 × rank × charge_count`;
5. máximo próprio rank4/5 cargas = +12%;
6. grace 10t e decay −1/10t após perda simples;
7. retomada antes da grace preserva cargas sem crédito retroativo;
8. teleport/knockback/vehicle/contraption/sublevel passivo não geram carga;
9. modifier único em rank change/respec/logout/clone/reload;
10. multiplayer/dedicated server e frame local Sable/Create Aeronautics.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE` até existir `VOLUNTARY_MOVEMENT_CONTEXT_V1` e `MOMENTUM_LEDGER_V1` seguros. Não implementar por polling/delta de posição nem criar helper global inexistente para mascarar a ausência do contrato causal.
