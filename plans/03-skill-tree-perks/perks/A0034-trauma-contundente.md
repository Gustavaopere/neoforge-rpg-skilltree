# A0034 — Trauma Contundente

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** PRESENTE no fallback canônico por Armor; guard/posture/physical-reduction extras não estão confirmados.
- **Notion:** `3c569db9-f0db-81d8-a04d-cdb2c11aba4b`.

## Contrato canônico

- A0033 ≥2; 2 ranks.
- Hit direto MACE contra proteção física comprovada gera Trauma, cap 3; duração 6/8 s.
- Qualificação: Armor >0, guarda/postura provider-native ou redução física explícita >0.
- Fallback seguro: somente `Attributes.ARMOR > 0`.
- Arcane Resistance, Corruption Resistance, Arcane Strain, Shroud/Exposure/Madness, STUN_ARMOR/poise e hazards ambientais não qualificam.
- Companion-owned damage Mobstein não cria Trauma do dono.

## Evidência runtime

- `A0021A0040CombatPolicy.afterConfirmedHit` adiciona Trauma por ator→alvo, deduplicado por root action.
- Adapter atual define `protectedTarget = target.getArmorValue() > 0`, portanto implementa exatamente o fallback Armor.
- Não foi comprovado receipt adicional de guard/posture para MACE nesse adapter.

## Provider→árvore

- Black Arcana/Enshrouded/Volcanoes: estados próprios não são proteção física da perk.
- Mobstein: ataque direto do jogador contra mob/boss é universalmente coberto; companion não transfere autoria.

## Pendência Chat 2

Nenhuma blocker para o fallback Armor. Qualquer rota de guard/posture/redução física adicional só pode ser ativada com receipt provider-native seguro.
