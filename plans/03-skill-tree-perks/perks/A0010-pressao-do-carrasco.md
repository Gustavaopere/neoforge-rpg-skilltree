# A0010 — Pressão do Carrasco

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE no receipt server-authoritative do Epic Fight.
- **Implementação:** CONFIRMÁVEL após CI/merge.
- **Notion:** https://app.notion.com/p/3c569db9f0db81fd9b1bcb501b7745ba
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

A0010 é Notable de Fúria para machados. Exige A0009 ≥2. Cada hit corpo a corpo direto, hostil, confirmado, com autoria real e categoria machado gera base 8 de Fúria; rank 1 aplica ×1,10, rank 2 ×1,20; troca legítima de alvo aplica depois ×1,50; cap 100. Uma única concessão por ação.

O fallback genérico anterior foi removido. Sem receipt server-authoritative que prove autoria, dano confirmado e categoria de machado, A0010 fica inativa. Tentativa de ataque, animação, nome/material do item ou dano observado não são autorização.

## Auditoria — 9 eixos

1. **Gates:** PASS — A0009 ≥2 + gateway.
2. **Integração global:** PASS — usa Fúria canônica única.
3. **Identidade:** PASS — recompensa pressão e alternância legítima de alvo.
4. **Topologia:** PASS — Notable camada 3 antes do terminal.
5. **Especializações:** PASS — exterior.
6. **PT-BR:** PASS.
7. **Notion:** PASS após remoção do fallback sem receipt.
8. **NeoVitae:** PASS.
9. **Modlist/integrações:** PASS/FALLBACK — Epic Fight é a rota comprovada; demais rotas ficam fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.axeFuryGain`: ordem base 8 → rank → troca de alvo.
- `NotionCombatPerkState.addFury`: clamp 100.
- `A0001A0020CombatPolicy.afterConfirmedHit`: exige `direct`, `hostile`, `actualDamage`, usa `claimOnce` e registra troca de alvo.
- `A0001A0020EpicFightHooks.onDamagePost`: só encaminha `modifiedDamage > 0` de `ServerPlayer` elegível e alvo hostil.

## Pendências

**Nenhuma bloqueante.** A ausência de adapter genérico não é mais uma promessa de design; é fail-closed deliberado até existir um receipt equivalente.

## Testes

- [x] matemática rank/target switch;
- [x] clamp 100;
- [x] deduplicação por ação;
- [x] dano confirmado e autoria server-side;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** Notion consultado novamente em 2026-08-30; efeito, multiplicadores, gate, autoria, hook, fallback e regra permanecem persistidos sem drift.
- **Mutação no Notion neste ciclo:** não necessária.
- **Receipt comprovado:** Epic Fight 21.17.3.1 `DELIVER_DAMAGE_POST`/pipeline correlacionado confirma dano efetivo e autoria; a família de machado vem da capability provider-native.
- **Anti-abuso/deduplicação:** uma concessão de Fúria por ação; tentativa, animação, proc secundário, fake player, alvo de treino/passivo, dano zero ou callback duplicado não contam.
- **Fail-closed:** qualquer adapter futuro deve provar `direct + hostile + actualDamage + autoria + família machado` e compartilhar a deduplicação canônica. Sem isso, A0010 fica inativa.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010.

## Chat 2 — implementação, testes e merge — PR #221

**Estado:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` após merge da PR #221.

- [x] Receipt server-authoritative de dano efetivo implementado.
- [x] Autoria real, alvo hostil e família de machado exigidos.
- [x] Fórmula base/rank/troca de alvo e cap 100 implementados.
- [x] Deduplicação por `rootActionId` implementada.
- [x] Tentativa/animação/dano zero/família errada permanecem fail-closed.
- [x] Regressão explícita `A0001A0010ImplementationContractJUnitTest` adicionada na PR #221.
- [x] `RPG Skill Tree CI` #1996 verde no HEAD `b99ba35671dc92477c6b767ec4e4c5c22f0c71d0`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Fallback/fail-closed:** somente a rota Epic Fight comprovada está ativa; adapters sem receipt equivalente permanecem inativos. Nenhuma pendência bloqueante.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; Fúria, target-switch state e deduplicação são authority do serviço canônico do RPG. Apenas receipt direto do jogador pode mutá-los.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO`; hazards, tremor, pressão, gases, erupções ou sobrevivência ambiental não geram Fúria.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO`; Shroud/Exposure/Flame/Story não geram Fúria e não substituem receipt de dano direto.
- **Black Arcana:** boundary obrigatório: `ARCANE_BACKLASH` é terminal e nunca concede Fúria, Mastery ou proc ofensivo. Ataque direto do jogador contra entidade Black Arcana pode gerar Fúria apenas se satisfizer integralmente `direct + hostile + actualDamage + AXE + autoria`.
- **Mobstein 5.4.4:** ataque direto do jogador contra mob/boss Mobstein é coberto universalmente; dano de ally/bodyguard ressuscitado permanece Mobstein-owned e nunca constitui receipt de ataque do dono, mesmo se houver ownership/seguimento.
- **Notion:** `Hook`, `Fallback` e `Regra` corrigidos nesta retroauditoria para nomear explicitamente Backlash/companions; re-fetch confirmou persistência.
- **Fail-closed:** proc, Backlash, summon/companion ou adapter sem receipt causal permanece inelegível; não atribuir dano secundário ao jogador apenas por ownership.
- **Estado histórico:** implementação da #221 já mergeada; retroauditoria não altera runtime.

## Reauditoria delta Simply Swords — 2026-08-30

- **Cobertura:** armas Simply só geram Fúria quando Epic Fight Compat resolve `AXE` e o POST confirma `direct + hostile + actualDamage + autoria`.
- **Exclusões:** Bleed, Accursed Rage, Blazing Brand, Mecha Pulse/Smite, lifesteal, stun, regen, ability hit, gem power e outros danos/procs derivados não concedem Fúria separadamente.
- **Authority:** o RPG não altera stacks, cooldowns, healing, stun ou charge dos traits Cataclysm/Simply.
- **Integrated:** cobertura universal por família/material; nenhum node nominal.
- **Simply Tooltips:** `NÃO DEVE SER INTEGRADO`; Simply More Unique não comprovada permanece FAIL-CLOSED.
- **Notion:** provider/hook/fallback/regra atualizados e re-fetch PASS.
- **Runtime:** inalterado; teste provider-present e anti-double-count ficam para Chat 2.
