# Auditoria de implementação Chat 2 — A0021–A0030

## Escopo

- **Lote exato:** A0021–A0030, 10 perks consecutivas.
- **Responsabilidade:** implementar/revalidar somente o design já aprovado pelo Chat 1.
- **PR final de merge:** #248 — `feat(perks): implement A0021-A0030 Chat 2 contracts`; a #242 foi a PR draft predecessora fechada sem merge por limitação do conector ao mudar `ready for review`.
- **Provider principal:** Epic Fight 21.17.3.1, sob o gate de versão exata já existente.
- **Fora de escopo:** A0031+; MACE/SCYTHE não foram corrigidas antecipadamente.

## Resultado por perk

| Perk | Estado do Chat 2 | Resultado técnico |
|---|---|---|
| A0021 | VALIDADA EM CI | crítico DAGGER canônico único, autoria direta preservada |
| A0022 | VALIDADA EM CI | `P-A0022-01/02/03` resolvidas: stagger forte, fallback geométrico, idle decay sem alvo e hardening contra falso reposicionamento durante knockback |
| A0023 | VALIDADA EM CI | flank/rear server-side, Fluxo/cooldown preservados |
| A0024 | VALIDADA EM CI NO FALLBACK CANÔNICO | rota geométrica de A0022 disponível; stamina continua Epic Fight-only e omitida quando receipt exato faltar |
| A0025 | VALIDADA EM CI | `P-A0025-01/02` resolvidas: HAMMER provider-native only + Mastery anti-farm via `DiscoveryProgress` |
| A0026 | VALIDADA EM CI | attack speed HAMMER somente com família provider-native |
| A0027 | VALIDADA EM CI | crítico HAMMER canônico único, sem tag paralela |
| A0028 | PARCIAL / FAIL-CLOSED | `P-A0028-01` permanece: provider não expõe pressão de guarda causal separada de impact/knockback |
| A0029 | NÃO CONFIRMADA / FAIL-CLOSED | `P-A0029-01` permanece: provider não expõe heavy receipt inequívoco |
| A0030 | NÃO CONFIRMADA / FAIL-CLOSED | `P-A0030-01` permanece: provider não expõe guard-break causal attacker-side e também falta heavy receipt |

## TDD e CI

### RED inicial do lote

- Commit inicial de regressão: `27e4d9a7a0a4e397d080fbc56dbf17117d43ce8c`.
- `RPG Skill Tree CI #2181`: **FAILURE em JUnit 5**, após Core tests passarem, confirmando que os novos contratos A0022/A0025 ainda não eram satisfeitos antes da implementação.

### GREEN técnico inicial

- HEAD técnico após hardening completo de HAMMER: `6938549c3961821d72dc3dcba4c6044a8f09e7d9`.
- `RPG Skill Tree CI #2192`: **SUCCESS**.
- Passaram no mesmo run: Core tests, JUnit 5, NeoForge GameTests, data/client/node/passive/runtime/provider validations, NeoForge build, verificação do JAR e dedicated-server smoke test.
- Os nove workflows auxiliares associados ao mesmo HEAD também concluíram **SUCCESS**: Foundation Diagnostics, Foundation Optional Integrations, Foundation Bootstrap, Compendium Flora, Entities, World, Ecology, Discovery e Editorial.

### Review final — knockback A0022

- O review da PR #248 identificou um P1 real: `LivingKnockBackEvent` apenas invalidava a baseline no instante do evento; o tick seguinte ainda podia abrir uma baseline durante a inércia forçada e, posteriormente, transformar o próprio knockback em falso reposicionamento.
- TDD RED: commit `6f6b2f2af355262c041c5cae5f75530cbf768c64`; `RPG Skill Tree CI #2251` falhou em `compileTestJava` exatamente porque `beginForcedRepositionSuppression`, `fallbackRepositionSuppressed` e `updateForcedRepositionSuppression` ainda não existiam.
- GREEN de código: HEAD `07f9c63f8a861a9fb15ee40cfb2dd47c20a6736e`; JUnit do CI #2254 passou antes do fechamento documental final.
- A implementação inicia supressão no receipt real de knockback, invalida toda baseline/receipt geométrico durante o movimento forçado, reseta o contador se a velocidade horizontal volta a ser relevante e só libera a amostragem após 3 ticks server-side quietos consecutivos.
- Após liberação, a primeira amostra cria uma baseline nova; deslocamento acumulado durante knockback nunca pode qualificar A0022/A0024.
- Velocidade é usada apenas como sinal negativo de exclusão do movimento forçado, nunca como evidência positiva de reposicionamento.

Nenhum teste/build local é usado como evidência deste fechamento; a validação objetiva é GitHub Actions.

## A0022 — pendências resolvidas

### P-A0022-01 — strong stagger

- Novo `A0022RuntimeHooks` registra `EpicFightEventHooks.Entity.ON_STUNNED`.
- Somente `LONG`, `KNOCKDOWN` e `NEUTRALIZE` são aceitos.
- A `EpicFightDamageSource` deve existir e identificar fonte hostil; dano genérico não substitui o receipt.
- O efeito chama `onConfirmedHeavyStagger(...)` e remove exatamente 2 Fluxo.

### P-A0022-02 — fallback geométrico

- `A0021A0040CombatState.sampleFallbackReposition(...)` usa somente posições server-side.
- Exige deslocamento horizontal ≥1,5 blocos e mudança angular alvo→jogador ≥60°.
- Rotação de câmera não entra no cálculo.
- `EntityTeleportEvent` invalida a rota imediatamente.
- `LivingKnockBackEvent` inicia supressão persistente; nenhuma baseline é aceita enquanto o movimento forçado não tiver cessado por 3 ticks quietos consecutivos.
- Movimento sozinho não concede Fluxo; apenas arma o receipt para o próximo hit direto elegível de DAGGER.

### P-A0022-03 — idle decay

- `tickFlow` não exige mais alvo/lock-on hostil.
- Após 3 s sem movimento horizontal relevante, o estado perde 1 Fluxo/s conforme contrato.

## A0025 — pendências resolvidas

### P-A0025-01 — HAMMER provider-native only

- `rpgskilltree:hammers` foi removida como classificação de HAMMER no adapter de combate e no adapter de Mastery.
- O arquivo `data/rpgskilltree/tags/item/hammers.json` foi removido.
- Mesmo um datapack externo não pode reativar HAMMER pelo antigo resolver de tags.
- HAMMER agora depende da categoria explícita Epic Fight; desconhecido = fail-closed.

### P-A0025-02 — Mastery anti-farm

- HAMMER foi removido do caminho repetível de 3 XP/hit.
- Cada tipo hostil distinto atingido por hit direto provider-native HAMMER concede +10 `epicfight:heavy` uma única vez.
- Chave persistente: `mastery/epicfight:heavy/entity_type/<registry-id>`.
- Mastery e discovery são aplicadas numa única mutação canônica via `PlayerProgressionRuntime.awardMasteryAndDiscoveries(...)`.
- Repetir o mesmo tipo, hit indireto ou família incorreta concede 0 XP.

## Fail-closed obrigatório preservado

### P-A0028-01 — guard pressure

A fonte real do Epic Fight 21.17.3.1 foi inspecionada:

- `GuardSkill` calcula consumo de guarda como `penalty × impact`.
- O mesmo `impact` também influencia knockback do bloqueio.
- Logo, aumentar `impact` para representar guard pressure alteraria impacto/knockback, substituição expressamente proibida pelo contrato de A0028.
- `SkillConsumeEvent` permite modificar stamina, mas não carrega `DamageSource`; não há receipt público que vincule causalmente esse consumo ao mesmo HAMMER/Abalo atacante.
- Correlação por timing/contexto seria heurística frágil.

Conclusão: benefício de pressão permanece inativo; Abalo state continua válido. `P-A0028-01` permanece bloqueante para implementação completa.

### P-A0029-01 — heavy

A fonte real de `PlayerPatch.getDamageSource(...)` mostra que `chargeWeapon` é definido por combo/`ComboAttacks.COMBO`; `ServerPlayerPatch` usa `shouldChargeWeapon()` para carregar a Weapon Innate após o hit.

Conclusão: `shouldChargeWeapon()` não é heavy receipt. Animação, dano, arma lenta, impacto ou tempo estimado também são proibidos. O adapter mantém `heavyConfirmed=false`, portanto A0029 não consome Abalo nem aplica os bônus. `P-A0029-01` permanece bloqueante.

### P-A0030-01 — guard-break + heavy

A fonte real de `GuardSkill` mostra:

- `blockType = canAfford ? GUARD : GUARD_BREAK` é uma variável interna do método de guarda.
- `dealEvent(...)` publica apenas resultado geral `BLOCKED` e chama `onAttackBlocked(...)`; não publica `BlockType.GUARD_BREAK` como receipt causal attacker-side.
- Inferir pelo som, animação, stamina, Armor, dano ou stagger seria heurística proibida.
- Heavy receipt inequívoco também continua ausente.

Conclusão: `onConfirmedGuardBreak(...)` permanece sem caller runtime seguro e A0030 continua fail-closed. `P-A0030-01` permanece bloqueante.

## Regressões específicas do lote

`A0021A0030ImplementationContractJUnitTest` cobre:

1. idle decay de A0022 sem target;
2. remoção de 2 Fluxo por strong stagger normalizado;
3. geometria server-side + invalidação;
4. supressão integral da geometria durante knockback e criação de baseline nova somente após 3 ticks quietos;
5. A0025 +10 XP somente para tipo hostil HAMMER inédito;
6. chave de discovery estável;
7. A0028 não substitui guard pressure por dano/impacto;
8. A0029 não consome Abalo sem heavy receipt;
9. A0030 não ativa apenas por heavy sem janela de guard-break causal.

## Fechamento do Chat 2

- **A0021–A0027:** prontas para `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #248 e confirmação da `main`.
- **A0028:** permanece `IMPLEMENTAÇÃO PARCIAL / FAIL-CLOSED` por `P-A0028-01`.
- **A0029:** permanece `NÃO CONFIRMADA / FAIL-CLOSED` por `P-A0029-01`.
- **A0030:** permanece `NÃO CONFIRMADA / FAIL-CLOSED` por `P-A0030-01` e dependência do heavy receipt.
- Nenhuma dessas três pendências requer redesenho do Chat 1: o design já determina fail-closed quando o provider não fornece o receipt. Só retornam ao Chat 1 se houver intenção de alterar o contrato.
- A0031+ não foi iniciada neste ciclo.
