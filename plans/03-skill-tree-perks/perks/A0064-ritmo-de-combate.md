# A0064 — Ritmo de Combate

## Estado de design

**APROVADA COM BOUNDARY.** Fundação MARTIAL de cadência; terceiro ponto inicial alternativo de `martial_core`.

## Contrato final

- **Ranks:** 4; **custo:** 1/rank.
- **Gate:** gateway MARTIAL; sem dependência de perk.
- **Efeito:** +2% de cadência/attack speed efetivo por rank, máximo +8%, somente quando o provider expõe um mecanismo server-authoritative de cadência compatível.
- O bônus compõe uma única vez com A0002/A0008/etc.; não cria segundo modifier paralelo.
- **Não significa** draw speed de arco, reload de besta, velocidade de projétil, velocidade de animação, movimento ou stamina. Para famílias sem surface segura, a parcela correspondente fica inativa.

## Authority / hooks

Epic Fight `ModifyAttackSpeedEvent` 21.17.3.1 é o hook preferencial. Fallback vanilla só pode existir se provar a mesma semântica e não quebrar o moveset.

## Simply Swords

Chakram/Twinblade attack-speed Implicit e Uniques do Simply More permanecem provider-owned. A0064 modifica apenas o valor efetivo no boundary de cadência; não retriggera proc, não reaplica Awakening e não recalcula o Implicit.

## Fail-closed

Sem hook de cadência semanticamente equivalente para uma família, não fabricar benefício. Herda `P-A0061-01` para classificação melee.

## Testes obrigatórios

1. +2/4/6/8% uma vez no `ModifyAttackSpeedEvent`.
2. Com perk de cadência de arma, soma-se uma vez antes da aplicação provider-native.
3. Bow draw/Crossbow reload/projectile speed permanecem inalterados.
4. Proc de attack speed Simply não dispara segunda aplicação.

## Nove eixos

1. Gates: PASS.
2. Integração: PASS com cadência provider-native.
3. Qualidade: PASS — corredor de velocidade concorrente a força/crítico, sem duplicar draw/reload.
4. Topologia: PASS — ponto inicial.
5. Especialização: PASS — universal apenas onde há cadence hook.
6. PT-BR: PASS.
7. Registro: GitHub.
8. NeoVitae: PASS.
9. Providers: PASS; optional families fail-closed.