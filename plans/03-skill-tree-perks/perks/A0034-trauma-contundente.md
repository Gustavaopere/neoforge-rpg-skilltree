# A0034 — Trauma Contundente

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pelo Chat 3 na PR #359**.
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
- Adapter define `protectedTarget = target.getArmorValue() > 0`, implementando exatamente o fallback Armor.
- Não foi comprovado receipt adicional de guard/posture para MACE; nenhum foi inventado.
- A classificação MACE subjacente é provider-native/exata.

## Provider→árvore

- Black Arcana/Enshrouded/Volcanoes: estados próprios não são proteção física da perk.
- Mobstein: ataque direto do jogador contra mob/boss é universalmente coberto; companion não transfere autoria.

## Fechamento Chat 3

Fallback Armor, cap/duração, dedup/root e exclusões foram revalidados. Rotas adicionais de guard/posture/redução física permanecem fail-closed e não bloqueantes. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`.
