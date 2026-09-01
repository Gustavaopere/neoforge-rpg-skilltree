# A0181 — Imbuimento de Natureza

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

Chat 1 não implementa runtime. A identidade da perk foi preservada, mas sua compra deve permanecer indisponível no snapshot atual porque A0177 já está `UNAVAILABLE_NODE` e a `main` ainda não possui os boundaries canônicos necessários para um cast NATURE direto e um componente derivado no mesmo outcome melee.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81438e83dcdb73666bb3`.

## Contrato de gameplay

- ARCANE ↔ MARTIAL/SURVIVAL; camada 5; Ponte; até 3 ranks; 1 PP/rank.
- Pré-requisito: A0177 Dano de Natureza I ≥2 + mastery canônica ≥20 na família melee usada.
- Famílias elegíveis: `epic_sword`, `epic_axe`, `epic_spear` somente em contato melee, `epic_dagger`, `epic_hammer`, `combat_mace`, `combat_scythe`.
- `combat_fist` só entra após a lane canônica prevista em P-0032 existir; mão vazia nunca conta.
- Um cast NATURE direto elegível do próprio jogador abre janela de 120 ticks.
- Durante a janela, cada `direct_melee_outcome` elegível recebe um único componente NATURE:
  - rank 1: 3%;
  - rank 2: 6%;
  - rank 3: 9%;
  - base: `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements`.
- Após o outcome melee pai causar dano direto positivo efetivamente aceito a alvo vivo válido, a perk poderá futuramente solicitar recuperação de vida:
  - 0,25% / 0,50% / 0,75% da vida máxima;
  - no máximo uma solicitação A0181 por 40 ticks;
  - somente por serviço canônico de sustain quando essa capability existir.

## Blockers canônicos

### `DIRECT_MAGIC_OUTCOME_V1`

Ainda ausente na `main`. Deve provar `action_id/outcome_id`, autoria do jogador, NATURE explícito e DIRECT vs derived antes de armar a janela.

### `DERIVED_DAMAGE_COMPONENT_V1`

Ainda ausente. O componente NATURE precisa pertencer ao outcome melee pai sem segundo `hurt`, `DamageSource`, crítico, proc ou Mastery.

### Sustain

Não existe `SustainResolver` runtime na `main` atual. A recuperação é componente secundário. Futuramente, se direct+derived estiverem presentes e apenas o serviço de sustain faltar, omitir somente a recuperação preserva a identidade de Imbuimento. O inverso é proibido: sustain/regen isolado não substitui o componente NATURE.

## Authority e providers

- Iron's Spells 'n Spellbooks 3.16.3, Ars Nouveau 5.13.1 e Ars Elemental 0.7.10.1 podem fornecer identidade NATURE somente por adapters direct/versionados.
- Epic Fight 21.17.3.1 e o classificador canônico de lanes definem as famílias melee elegíveis.
- Weapons of Miracles 2.0.176 só participa quando a arma concreta estiver mapeada de forma inequívoca à lane.
- Hexalia 1.3.5 não qualifica por tema ecológico; precisa de outcome mágico NATURE causal explicitamente adaptado.
- Ranged, projéteis, DoT, summon, automação, fake player e derived component são inelegíveis como outcome pai.

## Pipeline futuro obrigatório

`direct NATURE cast -> DIRECT_MAGIC_OUTCOME_V1 -> arma janela 120t -> direct_melee_outcome elegível -> calcula base_weapon_damage canônico -> DERIVED_DAMAGE_COMPONENT_V1:NATURE 3/6/9% no outcome pai -> confirma dano direto positivo do pai -> opcionalmente solicita sustain canônico com o mesmo action/outcome`.

Deduplicação por `outcome_id`: no máximo um componente A0181 e uma solicitação A0181 elegível por outcome/intervalo.

## Fail-closed / disponibilidade

Enquanto A0177, `DIRECT_MAGIC_OUTCOME_V1` ou `DERIVED_DAMAGE_COMPONENT_V1` estiverem indisponíveis:

- compra falha antes de consumir PP;
- rank legado indisponível contribui 0 PP para gates/thresholds;
- rank legado continua reembolsável/migrável;
- não habilitar somente sustain, regen ou cura;
- não criar segundo dano para simular o componente;
- não inferir NATURE por veneno, planta, fauna, cor, namespace ou visual.

## Bridge PP

`PP_REGION: ARCANE_MARTIAL_SURVIVAL_NATURE_BRIDGE`.

Os mesmos PP não satisfazem simultaneamente thresholds puros ARCANE, MARTIAL e SURVIVAL. Qualquer Specialist pode whitelistar essa ponte em no máximo um threshold semântico explícito.

## Handoff Chat 2

Implementar somente o estado de availability/fail-closed enquanto os blockers acima permanecerem. Não criar producer direct, segundo DamageSource ou sustain paralelo local à perk. Se os boundaries canônicos surgirem antes da implementação desta PR, a mudança de availability deve voltar ao Chat 1 antes de promover o runtime.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend enquanto A0177/direct/derived estiverem fechados;
2. rank legado unavailable = 0 PP e reembolsável/migrável;
3. `combat_fist`/mão vazia/ranged/projéteis negativos;
4. sem componente por poison/planta/fauna/Hexalia apenas temática;
5. quando os boundaries existirem: janela exata 120t e ranks 3/6/9%;
6. base pre-target/pre-critical/pre-added-elements;
7. componente herda crítico pai e não cria segundo DamageSource/proc/Mastery;
8. sustain somente após dano pai positivo, 0,25/0,50/0,75%, intervalo 40t;
9. derived component nunca gera nova solicitação de sustain;
10. logout/reload/dimensão/multiplayer e dedup por outcome.