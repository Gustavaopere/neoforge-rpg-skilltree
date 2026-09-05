# A0188 — Imbuimento de Sagrado

## Estado Chat 1

**DESIGN APROVADO EM FAIL-CLOSED / `UNAVAILABLE_NODE`.**

A0184 está indisponível e a `main` ainda não possui `DIRECT_MAGIC_OUTCOME_V1`, `DERIVED_DAMAGE_COMPONENT_V1` nem um serviço canônico de contribuição de absorção. A identidade composta foi preservada sem fallback de absorção isolada.

Notion revalidado após correção: `https://app.notion.com/p/3c569db9f0db81919f74d9bded8dbb20`.

## Contrato de gameplay

- ARCANE ↔ MARTIAL/HEALING; camada 5; Ponte; até 3 ranks; 1 PP/rank.
- Pré-requisito: A0184 ≥2 + mastery canônica ≥20 na família melee usada.
- Lanes elegíveis: `epic_sword`, `epic_axe`, `epic_spear` melee, `epic_dagger`, `epic_hammer`, `combat_mace`, `combat_scythe`; `combat_fist` somente após P-0032; mãos vazias excluídas.
- Cast HOLY direto elegível abre janela de 120 ticks.
- Outcome melee elegível recebe um único `derived_component:HOLY`:
  - 3% / 6% / 9% de `base_weapon_damage_pre_target_mitigation_pre_critical_pre_added_elements`.
- Após dano direto positivo aceito do ataque pai, no máximo uma vez a cada 60 ticks, a perk poderá conceder contribuição própria de absorção:
  - 0,5% / 1,0% / 1,5% da vida máxima;
  - duração 80 ticks;
  - procs renovam/substituem somente a contribuição A0188, sem empilhar magnitude nem remover absorção externa.

## Blockers

### `DIRECT_MAGIC_OUTCOME_V1`

Necessário para armar a janela por cast HOLY direto com autoria causal.

### `DERIVED_DAMAGE_COMPONENT_V1`

Necessário para anexar HOLY ao mesmo outcome melee sem segundo dano, crítico, proc ou Mastery.

### Absorção canônica

A `main` não contém serviço próprio para contribuição de absorção identificada. Esse componente é secundário. Futuramente, com direct+derived disponíveis, ausência apenas desse serviço pode omitir absorção e preservar o Imbuimento HOLY. Absorção isolada nunca substitui o componente HOLY.

## Authority/providers

- Iron's 3.16.3 é origem HOLY principal por adapter direct futuro.
- Eidolon: Repraised 0.5.0.2 só entra quando cast concreto estiver explicitamente HOLY-mapeado.
- Epic Fight 21.17.3.1/classificador canônico define lanes melee.
- Weapons of Miracles 2.0.176 só entra por classificação inequívoca da arma.
- Ranged, projectile, DoT, summon, automação, fake player e derived component são inelegíveis como pai.

## Pipeline futuro

`direct HOLY cast -> arma 120t -> direct melee outcome -> calcula base canônica -> DERIVED_DAMAGE_COMPONENT_V1:HOLY 3/6/9% -> settlement do pai -> se dano direto positivo e CD60 livre -> atualiza contribuição A0188 de absorção por 80t`.

Uma identidade causal não reentra em proc/Mastery/sustain.

## Fail-closed

Enquanto A0184/direct/derived estiverem fechados:

- compra falha antes do gasto;
- rank legado unavailable =0 PP/refundável;
- não habilitar absorção sozinha;
- não criar segundo DamageSource;
- não qualificar arma por nome/visual;
- `combat_fist` continua fechado até P-0032.

## Bridge PP

`PP_REGION: ARCANE_MARTIAL_HEALING_HOLY_BRIDGE`. Sem double-count entre ARCANE/MARTIAL/HEALING; whitelisting de Specialist no máximo uma vez.

## Handoff Chat 2

Preservar `UNAVAILABLE_NODE`. Não implementar absorção ou derived damage localmente sem os boundaries canônicos. Se direct+derived surgirem, availability volta ao Chat 1 antes da promoção.

## Testes obrigatórios para Chat 3

1. purchase fail-before-spend e legacy=0 PP;
2. A0184 unavailable fecha A0188;
3. lanes/ranged/fist/mão vazia conforme contrato;
4. quando direct+derived existirem: janela 120t, 3/6/9%;
5. componente herda crítico e não cria segundo DamageSource/Mastery/proc;
6. absorção só após dano pai positivo;
7. valores 0,5/1,0/1,5%, 80t, CD60;
8. refresh substitui somente a parcela A0188;
9. absorção externa permanece intacta;
10. dedup/lifecycle multiplayer/reload.