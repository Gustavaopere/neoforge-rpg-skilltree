# A0160 — Imbuimento de Fogo

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / UNAVAILABLE_NODE.**

A perk herda A0156 e exige um pipeline de componente derivado no **mesmo** outcome melee. O runtime atual não publica `DERIVED_DAMAGE_COMPONENT_V1`; criar um segundo DamageSource/event quebraria o contrato.

## Contrato

- Ponte ARCANE ↔ MARTIAL; camada 5; Ramo; 3 ranks; 1 PP/rank.
- Pré-requisitos: A0156 ≥2 + Gateway ARCANE + uma família melee elegível com Mastery ≥20.
- Após cast DIRECT_MAGIC+FIRE válido, abrir janela de imbuimento por 120 ticks.
- Próximo direct melee outcome elegível recebe no mesmo outcome um componente FIRE:
  `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements × (0,04 × rank)`.
- No máximo um componente/outcome.
- O componente herda a decisão crítica do parent; não rola crítico novamente.

## Famílias elegíveis

Swords `epic_sword`, Axes `epic_axe`, Spears apenas melee `epic_spear`, Daggers `epic_dagger`, Hammers `epic_hammer`, Maces `combat_mace`, Scythes `combat_scythe`; `combat_fist` somente quando a lane/especialização estiver realmente implementada e capability-eligible. Mão vazia nunca é prova suficiente.

## Availability

Exige A0156 capability-eligible + melee root/outcome canônico + `DERIVED_DAMAGE_COMPONENT_V1` que preserve a base pré-target/pré-critical e componha o elemento antes da mitigação apropriada. Sem o pipeline: `UNAVAILABLE_NODE`.

## Causalidade

O FIRE derivado compartilha actor/root/outcome do melee parent. Não existe segundo hit, DamageSource, event, RNG ou `eligible_kill` independente. A janela só é consumida quando o melee outcome elegível commita.

## Exclusões

- ranged/projectile, thrown spear e sweep derivado;
- DoT, summon, automation/fake player;
- segundo DamageSource/event/critical roll;
- conceder Mastery, rearm de A0156/A0157, sustain ou ignite por este componente sem contrato separado;
- empty-hand como FIST implícito.

## Handoff Chat 2

Não implementar enquanto o component pipeline não existir. Se o pipeline for criado, ele deve ser infraestrutura compartilhada de outcome e não serviço privado de A0160.

## Testes Chat 3

1. unavailable e availability transitiva de A0156;
2. janela 120 ticks e consumo somente no melee commit;
3. fórmula por rank usando base pré-target/pré-critical;
4. herança de critical sem segunda rolagem;
5. uma componente/outcome e nenhum segundo DamageSource;
6. families/mastery positivas e projectile/thrown/sweep/empty-hand negativas;
7. sem Mastery/sustain/rearm/ignite derivado; lifecycle e multiplayer.