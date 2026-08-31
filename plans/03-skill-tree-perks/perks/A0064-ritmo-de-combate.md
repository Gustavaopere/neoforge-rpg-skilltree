# A0064 — Ritmo de Combate

## Estado

- **Design:** APROVADO.
- **Notion:** `3c569db9-f0db-81ff-8f83-ff01614f1db5`; fetch fresco em 2026-08-31 sem drift.
- **Runtime observado:** CÓDIGO PRESENTE em `ModifyAttackSpeedEvent` e regras canônicas; confirmação definitiva pertence ao Chat 2.

## Contrato canônico

- Gateway MARTIAL desbloqueado.
- 4 ranks, 1 ponto por rank.
- +2% de velocidade de ataque efetiva por rank, máximo próprio de +8%.
- O bônus deve respeitar moveset, restrições e semântica do provider.
- Não acelera animações diretamente, não cria hits extras e não encurta guard/dodge windows.

## Provider / authority / boundary

- Para Epic Fight 21.17.3.1, usar o evento provider-native de attack speed (`ModifyAttackSpeedEvent`) e a semântica equivalente a `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/speed bonus.
- Para vanilla/outros providers, `minecraft:generic.attack_speed` só é válido quando representa realmente a cadência da arma.
- Simply Swords 1.70.2/Simply More 1.3.0 ALPHA preservam Awakening e Implicits de velocidade/double strike como provider-native; A0064 não os reexecuta.
- Projectile speed, movement speed e stamina não são substitutes de attack speed.

## Evidência runtime

`A0001A0020EpicFightHooks.onAttackSpeed(...)` lê `NotionCombatPerkRules.rhythmBonus(...)`, que incorpora A0064, e modifica a velocidade uma única vez no evento Epic Fight. A policy pura expõe `attackSpeedMultiplier(...)` para consumidores compatíveis.

## Fallback e fail-closed

Sem contrato semanticamente equivalente de cadência, A0064 fica inativa naquele moveset/provider. Não alterar animações por reflection/mixin frágil e não substituir por outro atributo.

## Anti-abuso, causalidade e deduplicação

- A0064 é atributo/ritmo, não proc por hit.
- Procs provider-native de double strike/attack speed não são disparados novamente.
- Não gera Mastery por ataques, ticks ou tempo equipado.

## Pendências para Chat 2

- **P-A0064-01:** validar provider-present que cada moveset Epic Fight recebe exatamente uma contribuição A0064 no evento de speed.
- **P-A0064-02:** manter fail-closed em famílias sem equivalência segura; não criar fallback de animação.

## Nove eixos obrigatórios de aprovação

| Eixo | Resultado | Decisão |
|---|---|---|
| 1. Dependências/gates | PASS | Gateway MARTIAL adequado. |
| 2. Integração global | PASS | Cadência não é stamina/movimento/projétil. |
| 3. Qualidade/identidade | PASS | Small foundation de ritmo com efeito tático claro. |
| 4. Topologia | PASS | Camada 1, `MARTIAL/CORE_RHYTHM`. |
| 5. Especializações | PASS | Universal, respeitando movesets. |
| 6. PT-BR | PASS | Texto player-facing em PT-BR. |
| 7. Notion completo | PASS | Fetch fresco confirmado. |
| 8. NeoVitae | PASS | Ausente. |
| 9. Cobertura providers | PASS | Epic Fight/vanilla/Simply Swords classificados sem falsa equivalência. |

Os 18 critérios técnicos cumulativos passam **no design**.