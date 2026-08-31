# A0034 — Trauma Contundente

## Estado

- **Design:** APROVADO + boundary retroativo.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO para fechamento pela PR #252; rotas extras guard/posture/physical-reduction permanecem fail-closed sem receipt próprio.
- **Notion:** `3c569db9-f0db-81d8-a04d-cdb2c11aba4b`.

## Contrato canônico

- A0033 ≥2; 2 ranks.
- Hit direto MACE contra proteção física comprovada gera Trauma, cap 3; duração 6/8 s.
- Qualificação: Armor >0, guarda/postura provider-native ou redução física explícita >0.
- Fallback seguro: somente `Attributes.ARMOR > 0`.
- Arcane Resistance, Corruption Resistance, Arcane Strain, Shroud/Exposure/Madness, STUN_ARMOR/poise e hazards ambientais não qualificam.
- Companion-owned damage Mobstein não cria Trauma do dono.

## Evidência runtime

- `A0021A0040CombatPolicy.afterConfirmedHit` adiciona Trauma por ator→alvo somente em hit direto/hostil com dano real, deduplicado por `rootActionId`.
- O adapter define `protectedTarget = target.getArmorValue() > 0`, implementando exatamente o fallback Armor aprovado.
- A PR #252 preserva a ordem consumer→same-hit-gain: quando A0035 consome três Trauma no POST, o mesmo hit protegido pode iniciar uma nova carga A0034 somente depois do commit.
- Não foi comprovado receipt adicional de guard/posture para MACE nesse adapter; essas rotas permanecem inativas em vez de serem inferidas.

## Provider→árvore

- Black Arcana/Enshrouded/Volcanoes: estados próprios não são proteção física da perk.
- Mobstein: ataque direto do jogador contra mob/boss é universalmente coberto quando o receipt MACE é válido; companion não transfere autoria.

## Pendência Chat 2 / resolução Chat 3

Nenhuma blocker permanece para o fallback Armor. Qualquer rota de guard/posture/redução física adicional continua condicionada a receipt provider-native seguro e não altera o fechamento do contrato canônico disponível.

## Validação Chat 3 — PR #252

- Regressões de A0035 verificam que Trauma não é consumido no PRE e que rollback de hit cancelado/dano zero preserva as cargas.
- `RPG Skill Tree CI` #2806: JUnit 5, NeoForge GameTests, build e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: fallback canônico A0034 validado; apta ao merge da PR #252.
