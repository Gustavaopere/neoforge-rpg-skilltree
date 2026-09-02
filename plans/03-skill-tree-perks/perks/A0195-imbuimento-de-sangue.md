# A0195 — Imbuimento de Sangue

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0195 exige uma ação BLOOD direta efetivamente concluída e composição de dano adicional no mesmo melee outcome. A `main` atual não contém os contratos canônicos necessários para materializar isso sem criar uma segunda pipeline de dano.

Notion revalidado após hardening: `https://app.notion.com/p/3c569db9f0db8101a208dcbd800de424`.

## Contrato

- ARCANE; camada 5; Ponte; até 3 ranks; 1 PP/rank.
- Pré-requisitos:
  - A0191 ≥2;
  - Blood Mastery ≥15;
  - Mastery ≥20 na família melee efetivamente usada.
- Uma ação/conjuração BLOOD direta elegível, concluída com sucesso e com custos nativos processados pelo provider, abre janela de **120 ticks / 6 s**.
- Durante a janela, cada `direct_melee_outcome` elegível recebe um único componente BLOOD derivado no **mesmo outcome**.

Coeficiente sobre `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements`:

| Rank | HP PRE-ataque ≥50% | HP PRE-ataque <50% |
|---|---:|---:|
| 1 | 4% | 5% |
| 2 | 8% | 10% |
| 3 | 12% | 15% |

A faixa de baixa vida substitui a normal; não soma. Exatamente 50% usa a coluna normal.

## Lanes melee elegíveis

- `epic_sword`;
- `epic_axe`;
- `epic_spear` **somente melee**;
- `epic_dagger`;
- `epic_hammer`;
- `combat_mace`;
- `combat_scythe`.

`combat_fist` permanece inelegível até P-0032 materializar uma lane real. Mão vazia nunca é fallback.

Armas externas, incluindo Weapons of Miracles 2.0.176, só entram quando a arma concreta estiver classificada inequivocamente pela pipeline canônica/versionada.

## Contracts obrigatórios

- receipt de commit BLOOD direto / `DIRECT_MAGIC_OUTCOME_V1`;
- `DERIVED_DAMAGE_COMPONENT_V1`.

O receipt de custo do provider serve somente para provar que a ação de origem foi realmente concluída. Custo de HP, mana, blood ou reagente nunca vira dano/trigger corporal sintético.

## Same-outcome composition

O `derived_component:BLOOD`:

- pertence ao outcome melee pai;
- herda exatamente autoria e decisão `criticalHit` do pai;
- não executa nova rolagem crítica;
- não cria segundo `DamageSource`;
- não chama `hurt` novamente;
- não gera Mastery, sustain, lifesteal, proc ou nova janela;
- é deduplicado pelo `outcome_id` pai.

## Exclusões

Inelegíveis:

- bows/crossbows;
- qualquer projectile;
- spear arremessada;
- DoT;
- summons;
- automação/FakePlayer;
- empty hand;
- outro derived component usado como raiz.

Black Arcana Stage 06 atual não publica `BLACK_ARCANA_BLOOD_OUTCOME`; seus rituais não abrem esta janela por associação temática.

## Fail-closed

Sem o receipt DIRECT/commit BLOOD ou sem `DERIVED_DAMAGE_COMPONENT_V1`, o node inteiro fica `UNAVAILABLE_NODE`.

Não existe fallback de movimento, lifesteal, segundo dano separado ou simples enchant-like modifier que mude a identidade do contrato. Purchase falha antes do gasto; rank legado unavailable = 0 PP em gates e permanece reembolsável/migrável.

## Handoff Chat 2

Preservar A0195 unavailable. Não criar segundo DamageSource/evento, não implementar dano BLOOD como hit separado e não abrir janela apenas ao observar school BLOOD sem commit causal.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend;
2. legacy unavailable =0 PP + refund/migration;
3. janela só abre após ação BLOOD concluída/commitada;
4. falha/cancelamento/custo não processado não abre janela;
5. janela dura 120t;
6. ranks usam 4/8/12% em HP PRE ≥50%;
7. ranks usam 5/10/15% em HP PRE <50%, substituindo a coluna normal;
8. exatamente 50% usa coeficiente normal;
9. lane allowlist positiva;
10. spear arremessada, bows, crossbows, projectiles e empty hand negativos;
11. `combat_fist` fail-closed até P-0032;
12. cálculo usa base pre-target/pre-critical/pre-added-elements;
13. derived component herda crítico/autoria do pai;
14. nenhuma segunda chamada de hurt/DamageSource/critical/proc/Mastery;
15. mesmo outcome não recebe componente duas vezes;
16. provider absent/version mismatch falha fechado.