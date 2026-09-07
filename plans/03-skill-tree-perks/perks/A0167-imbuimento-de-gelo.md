# A0167 — Imbuimento de Gelo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A perk depende de dois contracts globais que ainda não existem na `main`: outcome mágico direto canônico para armar a janela e componente de dano derivado same-outcome para anexar ICE ao ataque melee sem criar um segundo DamageSource/proc.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81ff92e6e81571a24f34`.

## Contrato

- ARCANE ↔ MARTIAL; camada 5; Ponte; 3 ranks; 1 PP/rank.
- Dependências: A0163 ≥2 + Mastery ≥20 na família melee efetivamente usada.
- Famílias elegíveis existentes no design: `epic_sword`, `epic_axe`, `epic_spear` em modo melee, `epic_dagger`, `epic_hammer`, `combat_mace`, `combat_scythe`.
- `combat_fist` só entra após P-0032/lane real; mãos vazias nunca contam.
- Cast ICE direto elegível arma janela de 120 ticks.
- Durante a janela, cada `direct_melee_outcome` elegível recebe um componente ICE igual a 3%/6%/9% do `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements`.
- Se o mesmo outcome já produzir buildup ICE_CONTROL nativo por adapter seguro, multiplicar somente esse buildup ×1,04/×1,08/×1,12.

## Capabilities ausentes

### `DIRECT_MAGIC_OUTCOME_V1`
Necessária para provar que a ação que arma a janela é uma conjuração ICE direta do jogador, com action identity e autoria estáveis.

### `DERIVED_DAMAGE_COMPONENT_V1`
Necessária para anexar ICE ao **mesmo** melee outcome, preservando:

- mesmo `root_action_id/outcome_id`;
- mesma autoria;
- herança da única decisão de crítico do pai;
- ausência de segundo DamageSource/hurt invocation;
- ausência de segunda rolagem de proc;
- exclusão de reentrada como novo direct outcome.

A busca na `main` não encontrou esse contract.

## Pipeline futuro

1. `DIRECT_MAGIC_OUTCOME_V1` ICE do jogador arma `imbuement_window[player]` por 120 ticks.
2. `direct_melee_outcome` resolve arma/lane/mastery canônica.
3. Excluir ranged/projectile/thrown spear, derived sweep, DoT, summon, fake player, automação e mão vazia.
4. Deduplicar por `outcome_id`.
5. Calcular base elegível pré-target/pre-critical/pre-added-elements.
6. `DERIVED_DAMAGE_COMPONENT_V1` anexa um único componente ICE de 3/6/9% ao outcome pai.
7. O componente herda critical do pai; não rola crítico novamente.
8. Buildup ICE_CONTROL só é multiplicado se o próprio outcome possuir receipt provider-native seguro; nunca criar CHILL.

## Mastery e autoria

A Mastery da família é apenas gate. O componente ICE derivado:

- não concede Mastery adicional;
- não rearma a janela;
- não cria sustain;
- não conta como nova ação melee/magic;
- não credita fake player/minion/automação.

## Fail-closed

Enquanto faltar A0163 disponível, `DIRECT_MAGIC_OUTCOME_V1` ou `DERIVED_DAMAGE_COMPONENT_V1`:

- compra falha antes de gastar PP;
- rank legado unavailable vale 0 PP em gates e permanece reembolsável/migrável;
- não simular componente ICE com segundo `hurt`/DamageSource;
- não substituir por enchant, Fire/Frost ticks, potion ou bônus de dano físico;
- não criar CHILL universal.

Se futuramente apenas o adapter ICE_CONTROL estiver ausente, o componente ICE same-outcome pode permanecer, desde que `DERIVED_DAMAGE_COMPONENT_V1` exista; o buildup opcional fica fail-closed.

## Bridge PP

`PP_REGION: ARCANE_MARTIAL_ICE_BRIDGE`. O mesmo PP não conta simultaneamente como gasto puro ARCANE e MARTIAL. Specialist só pode whitelistar a bridge em um threshold semântico explicitamente definido.

## Handoff Chat 2

Implementar somente availability/fail-closed. Não criar `DERIVED_DAMAGE_COMPONENT_V1` localmente dentro da perk nem duplicar a pipeline de dano. Divergência que exija redefinir base damage, crítica ou ownership volta ao Chat 1.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend sem capabilities/A0163;
2. window 120 ticks e lifecycle seguro;
3. famílias melee positivas e ranged/thrown/sweep/empty-hand negativas;
4. gate Mastery da lane realmente usada;
5. fórmula 3/6/9% sobre base canônica;
6. exatamente um componente por `outcome_id` e nenhum segundo DamageSource;
7. herança da única crítica do pai;
8. derived component não concede Mastery/sustain/rearm;
9. buildup ICE_CONTROL opcional não cria estado novo;
10. bridge PP, reload, multiplayer e dedicated server.