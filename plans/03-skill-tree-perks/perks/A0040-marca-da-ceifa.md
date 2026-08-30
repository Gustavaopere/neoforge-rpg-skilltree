# A0040 — Marca da Ceifa

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação necessária.
- **Implementação:** PRESENTE, condicionada à família SCYTHE segura.
- **Notion:** `3c569db9-f0db-81f4-86a9-e0b677dd2996`.

## Contrato canônico

- A0039 ≥2; 2 ranks.
- Primeiro hit direto SCYTHE aplica Marca por 8/10 s; reapply renova a mesma marca jogador→alvo.
- A Marca só amadurece quando, **já marcada**, a vida cruza de ≥50% para <50%.
- Dano periódico, projétil derivado, proc encadeado, reflexão, companion/summon, fake player e callback duplicado não aplicam/duplicam a Marca.
- A0040 é Notable, não terminal; A0041+ fica fora deste lote.

## Evidência runtime

- `A0021A0040CombatPolicy.afterConfirmedHit` aplica A0040 somente em hit direto/hostil/dano real e usa claim por root action.
- `A0021A0040CombatState.applyReapingMark` inicia marcas abaixo de 50% como imaturas; somente crossing posterior ≥50→<50 amadurece.
- `A0021A0040EpicFightHooks.onLivingDamagePost` chama `updateReapingMaturityForTarget` para todo dano real, permitindo que dano externo ao golpe de foice amadureça uma marca já existente.
- Lifecycle remove estado por alvo/ator conforme contrato transitório.

## Provider→árvore

O design já exclui explicitamente companion/summon e procs, cobrindo Mobstein e `ARCANE_BACKLASH` sem mutação adicional. Volcanoes/Enshrouded podem causar outros danos/estados no alvo, mas não recebem autoria de A0040; apenas uma Marca já aplicada pode observar a vida cair pelo estado canônico do Minecraft.

## Pendência Chat 2

Nenhuma exclusiva de A0040 além de `P-A0037-01` para classificação SCYTHE segura. Não iniciar A0041 neste ciclo.
