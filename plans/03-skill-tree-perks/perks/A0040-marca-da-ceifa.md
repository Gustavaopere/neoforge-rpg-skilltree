# A0040 — Marca da Ceifa

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação necessária.
- **Implementação:** **IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359**.
- **Notion:** `3c569db9-f0db-81f4-86a9-e0b677dd2996`.

## Contrato canônico

- A0039 ≥2; 2 ranks.
- Primeiro hit direto SCYTHE aplica Marca por 8/10 s; reapply renova a mesma marca jogador→alvo.
- A Marca só amadurece quando, já marcada, a vida cruza de ≥50% para <50%.
- Dano periódico, projétil derivado, proc encadeado, reflexão, companion/summon, fake player e callback duplicado não aplicam/duplicam a Marca.
- A0040 é Notable, não terminal; A0041+ fica fora deste lote.

## Evidência runtime

- `A0021A0040CombatPolicy.afterConfirmedHit` aplica A0040 somente em hit direto/hostil/dano real e usa claim por root action.
- `A0021A0040CombatState.applyReapingMark` mantém marca nova abaixo de 50% imatura; somente crossing posterior ≥50→<50 amadurece.
- `A0021A0040EpicFightHooks.onLivingDamagePost` atualiza maturidade para dano real posterior sem transferir autoria para a fonte do dano.
- `clearTarget` cobre morte e lifecycle explícito.
- `pruneExpiredReapingMarks(now)` remove marcas expiradas de todos os atores de forma bounded; server tick executa a varredura a cada 1 s sem exigir nova consulta ao mesmo UUID.
- A resolução SCYTHE é provider-native; a tag paralela foi removida.

## Pendências resolvidas

- **P-A0040-01 — RESOLVIDA:** cleanup periódico bounded de marcas expiradas, server-authoritative e independente de reconsulta do alvo.
- Dependência de `P-A0037-01` resolvida pela classificação SCYTHE provider-native.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- Scythe Simply só participa quando Epic Fight Compat resolve `SCYTHE`; namespace, tooltip e aparência não classificam a arma.
- Execute provider-owned pode alterar vida/matar, mas não aplica/duplica Marca nem cria root SCYTHE em nome do RPG.
- Marca RPG já existente pode observar crossing server-side de vida causado por dano real sem transferir autoria nem reaplicar a Marca.
- Unique ability, gem power, Runic Power, Awakening e traits Cataclysm permanecem provider-owned.

## Fechamento Chat 3

Aplicação/reapply, crossing, dedup/root, expiry 8/10 s, lifecycle e prune periódico sem reconsulta foram validados. `RPG Skill Tree CI` #3361 / run `33657496252` ficou GREEN no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`. Nenhuma perk A0041+ foi iniciada.
