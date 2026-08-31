# A0040 — Marca da Ceifa

## Estado

- **Design:** APROVADO; re-fetch sem drift, nenhuma mutação necessária.
- **Implementação:** IMPLEMENTAÇÃO CONFIRMADA para fechamento pela PR #252; `P-A0040-01` resolvida com pruning bounded e a dependência da família SCYTHE segura foi encerrada.
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
- `A0021A0040EpicFightHooks.onLivingDamagePost` chama `updateReapingMaturityForTarget` para dano real, permitindo que dano externo amadureça uma marca já existente sem transferir autoria nem reaplicar a Marca.
- `clearTarget` remove marcas em death; ator é limpo em logout/dimension/respawn.
- A PR #252 adiciona `pruneExpiredReapingMarks(now)`: varredura bounded remove marcas expiradas de todos os atores sem consultar novamente o UUID do target. `onServerTickPost` chama esse pruning a cada 1.000 ms de game time.
- SCYTHE agora só é resolvida por capability/categoria Epic Fight ou mapping versionado explícito; tag paralelo removido.

## Provider→árvore

O design exclui companion/summon e procs, cobrindo Mobstein e `ARCANE_BACKLASH` sem mutação adicional. Volcanoes/Enshrouded podem causar outros danos/estados no alvo, mas não recebem autoria de A0040; apenas uma Marca já aplicada pode observar a vida cair pelo estado canônico do Minecraft.

## Pendências Chat 2 / resolução Chat 3

- **P-A0040-01 — RESOLVIDA:** lifecycle de `reapMarks` passou a ter varredura periódica bounded de expirados, server-authoritative e independente de reconsulta do UUID.
- Dependência `P-A0037-01 — RESOLVIDA`: família SCYTHE segura sem classificador paralelo.
- A0041 não foi iniciada neste ciclo.

## Reauditoria delta — Simply Swords stack — 2026-08-31

- **Cobertura SCYTHE:** Scythe Simply só participa quando Epic Fight Compat resolve `SCYTHE`; namespace, tooltip e aparência não classificam a arma.
- **Execute provider-owned:** o execute Implicit da Scythe pode alterar a vida ou matar pelo pipeline Simply, mas não aplica/duplica Marca, não cria novo `rootActionId` SCYTHE e não contorna deduplicação.
- **Maturação preservada:** uma Marca RPG já existente continua podendo observar o crossing server-side de vida causado por qualquer dano real conforme o contrato; isso não transfere autoria do dano externo para A0040 e não reaplica a Marca.
- **Derived effects:** Unique ability, gem power, Runic Power, Awakening e traits Cataclysm permanecem provider-owned e não criam nova Marca.
- **Lifecycle:** pruning bounded da PR #252 encerra `P-A0040-01` sem depender do target voltar a ser consultado.
- **Notion:** boundary Simply registrada em quatro propriedades; re-fetch PASS.

## Validação Chat 3 — PR #252

- `A0031A0040ImplementationContractJUnitTest` prova remoção em 8.000 ms no rank 1, nenhuma remoção antecipada em 7.999 ms e idempotência do pruning após expiração.
- Regressões existentes continuam validando crossing estrito ≥50→<50 e duração rank 2 de 10 s.
- `RPG Skill Tree CI` #2806: JUnit 5, NeoForge GameTests, runtime/data validations, build, JAR e dedicated-server smoke **GREEN**.
- `SonarQube Cloud` #41: **GREEN**.
- Resultado: contrato A0040 validado; apta ao merge da PR #252. Nenhuma perk A0041+ foi iniciada.
