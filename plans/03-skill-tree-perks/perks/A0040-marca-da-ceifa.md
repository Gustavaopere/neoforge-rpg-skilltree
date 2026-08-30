# A0040 — Marca da Ceifa

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação necessária.
- **Implementação:** PARCIAL; aplicação/maturação existem, mas `P-A0040-01` impede considerar o lifecycle completo. Continua condicionada à família SCYTHE segura.
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
- `clearTarget` limpa a marca em `LivingDeathEvent`, e o ator é limpo em logout/dimension/respawn; porém um alvo que despawna ou tem chunk descarregado sem morte pode deixar `reapMarks` retida no mapa do ator. A expiração só remove a entrada quando o mesmo UUID volta a ser consultado ou quando o ator é limpo.

## Provider→árvore

O design já exclui explicitamente companion/summon e procs, cobrindo Mobstein e `ARCANE_BACKLASH` sem mutação adicional. Volcanoes/Enshrouded podem causar outros danos/estados no alvo, mas não recebem autoria de A0040; apenas uma Marca já aplicada pode observar a vida cair pelo estado canônico do Minecraft.

## Pendências Chat 2

- **P-A0040-01:** completar o lifecycle de `reapMarks` para alvos removidos/despawnados/chunks descarregados sem `LivingDeathEvent`, por hook seguro de remoção ou varredura periódica de expirados. A solução deve ser bounded, server-authoritative e não depender de o mesmo UUID ser consultado novamente.
- Depende também de `P-A0037-01` para classificação SCYTHE segura.
- Não iniciar A0041 neste ciclo.
