# A0034 — Trauma Contundente

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** **CÓDIGO PRESENTE NO FALLBACK CANÔNICO / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
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
- Não foi comprovado receipt adicional de guard/posture para MACE e o Chat 2 não inventou um.
- A classificação MACE subjacente foi corrigida para provider-native/exata.

## Provider→árvore

- Black Arcana/Enshrouded/Volcanoes: estados próprios não são proteção física da perk.
- Mobstein: ataque direto do jogador contra mob/boss é universalmente coberto; companion não transfere autoria.

## Fechamento Chat 2

Nenhuma blocker permanece para o fallback Armor aprovado. Rotas adicionais de guard/posture/redução física seguem fail-closed até receipt real. Chat 3 deve validar cap/duração, dedup/root, Armor zero, exclusões mágicas/ambientais e lifecycle. O Chat 2 não executou a bateria final.
