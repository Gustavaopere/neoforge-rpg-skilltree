# A0040 — Marca da Ceifa

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação necessária.
- **Implementação:** **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**.
- **Notion:** `3c569db9-f0db-81f4-86a9-e0b677dd2996`.

## Contrato canônico

- A0039 ≥2; 2 ranks.
- Primeiro hit direto SCYTHE aplica Marca por 8/10 s; reapply renova a mesma marca jogador→alvo.
- A Marca só amadurece quando, **já marcada**, a vida cruza de ≥50% para <50%.
- Dano periódico, projétil derivado, proc encadeado, reflexão, companion/summon, fake player e callback duplicado não aplicam/duplicam a Marca.
- A0040 é Notable, não terminal; A0041+ fica fora deste lote.

## Evidência runtime após Chat 2

- `A0021A0040CombatPolicy.afterConfirmedHit` aplica A0040 somente em hit direto/hostil/dano real e usa claim por root action.
- `A0021A0040CombatState.applyReapingMark` mantém marca nova abaixo de 50% imatura; somente crossing posterior ≥50→<50 amadurece.
- `A0021A0040EpicFightHooks.onLivingDamagePost` atualiza maturidade para dano real posterior sem transferir autoria para a fonte do dano.
- `clearTarget` continua cobrindo morte e lifecycle explícito.
- **P-A0040-01 foi resolvida no código:** `pruneExpiredReapingMarks(now)` remove marcas expiradas de todos os atores de forma bounded e o server tick executa a varredura a cada 1 s, sem exigir nova consulta ao mesmo UUID.
- A resolução SCYTHE foi endurecida para provider-native; a tag paralela foi removida.

## Provider→árvore

O design exclui companion/summon e procs, cobrindo Mobstein e `ARCANE_BACKLASH`. Volcanoes/Enshrouded podem causar outros danos/estados no alvo, mas não recebem autoria de A0040; apenas uma Marca já aplicada pode observar a vida cair pelo estado canônico do Minecraft.

## Pendências Chat 2

- **P-A0040-01 — RESOLVIDA NO CÓDIGO:** cleanup periódico bounded de marcas expiradas, server-authoritative e independente de reconsulta do alvo.
- Dependência de `P-A0037-01` também foi resolvida pela classificação SCYTHE provider-native.
- A0041 não foi iniciada neste ciclo.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura SCYTHE:** Scythe Simply só participa quando Epic Fight Compat resolve `SCYTHE`; namespace, tooltip e aparência não classificam a arma.
- **Execute provider-owned:** execute Implicit pode alterar vida/matar, mas não aplica/duplica Marca nem cria root SCYTHE em nome do RPG.
- **Maturação preservada:** Marca RPG já existente pode observar crossing server-side de vida causado por dano real sem transferir autoria nem reaplicar a Marca.
- **Derived effects:** Unique ability, gem power, Runic Power, Awakening e traits Cataclysm permanecem provider-owned.

## Handoff Chat 3

Validar aplicação/reapply, crossing de 50%, dedup/root, exclusões de autoria, expiry 8/10 s, prune periódico com alvo ausente/descarregado e ausência de leaks em multiplayer/lifecycle. O Chat 2 não executou a bateria final.
