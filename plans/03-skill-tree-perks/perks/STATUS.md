# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0020** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. Este índice descreve o estado após as correções e auditorias retroativas; `IMPLEMENTAÇÃO CONFIRMADA` só é usada quando existe merge/CI correspondente já comprovado.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO após reauditoria | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; retroauditoria provider→árvore concluída | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; retroauditoria provider→árvore concluída | nenhuma |
| A0003 | Precisão com Espadas | APROVADO + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Notion endurecido para Backlash/companions | nenhuma; preservar provenance direta/root action |
| A0004 | Ritmo do Duelista | APROVADO + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Notion endurecido para Backlash/companions | nenhuma; preservar autoria direta de Ímpeto |
| A0005 | Abertura de Guarda | APROVADO após correção + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Arcane Danger/Shroud não qualificam defesa física | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Backlash/companions não armam/consomem Riposta | nenhuma |
| A0007 | Treino com Machados I | APROVADO após reauditoria | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; retroauditoria provider→árvore concluída | nenhuma |
| A0008 | Treino com Machados II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; retroauditoria provider→árvore concluída | nenhuma |
| A0009 | Precisão com Machados | APROVADO + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Notion endurecido para Backlash/companions | nenhuma; preservar provenance direta/root action |
| A0010 | Pressão do Carrasco | APROVADO + boundary retroativo | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; Backlash/companions nunca geram Fúria/Mastery | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO após correção + boundary retroativo | Presente; Arcane Danger/Shroud não qualificam defesa física; companion não gasta Fúria do dono | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO após correção + boundary retroativo | Presente; transação PRE CORE→exhaustion→benefício; Volcanoes compõe somente via Cold Sweat | nenhuma de design; preservar fail-closed do bridge CORE |
| A0013 | Treino com Lanças I | APROVADO após reauditoria | Presente; classificação provider-native/fail-closed; retroauditoria concluída | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | Presente via `ModifyAttackSpeedEvent`; retroauditoria concluída | nenhuma |
| A0015 | Precisão com Lanças | APROVADO + boundary retroativo | Presente; crítico canônico direto; Backlash/companions inelegíveis | nenhuma |
| A0016 | Distância Ideal | APROVADO + boundary retroativo | Presente; cargas somente por ação direta do jogador; Backlash/companions inelegíveis | nenhuma |
| A0017 | Interceptação | APROVADO + boundary retroativo | Presente em fallback canônico de janela + impacto/pressão | redução de deslocamento segue deliberadamente omitida sem receipt ofensivo provider-native |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO + boundary retroativo | Presente; crossing/janela/consumo exigem causalidade direta; Backlash/companions inelegíveis | nenhuma |
| A0019 | Treino com Adagas I | APROVADO após reauditoria | Presente; classificação provider-native/fail-closed; retroauditoria concluída | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | Presente via `ModifyAttackSpeedEvent`; retroauditoria concluída | nenhuma |

## Correções sistêmicas da reauditoria

- **Critérios versionados:** cópia integral dos critérios canônicos adicionada à pasta dos dossiês.
- **Provider-native first:** os fallbacks fictícios `rpgskilltree:swords`, `rpgskilltree:axes`, `rpgskilltree:spears` e `rpgskilltree:daggers` foram removidos do design canônico. Ausência de classificação segura agora é `FAIL-CLOSED`.
- **A0004/A0016:** `EpicFightEventHooks.Entity.ON_STUNNED` fornece o receipt server-side; apenas `LONG`, `KNOCKDOWN` e `NEUTRALIZE` são aceitos como stagger forte, com fonte hostil validada.
- **A0005/A0011:** guarda/postura observável é rota principal; quando não observável, somente defesa física server-side comprovável autoriza penetração-only. Arcane Danger, Shroud/Exposure/Madness não são defesa física.
- **A0010:** tentativa de ataque, animação, proc secundário ou autoria indireta não geram Fúria.
- **A0012:** +1,5 `CORE` no Cold Sweat é pré-condição no mesmo `DELIVER_DAMAGE_PRE`; exhaustion, bônus e gasto de pico só ocorrem depois do sucesso. Calor Volcanoes pode compor apenas pelo bridge Volcanoes→Cold Sweat, sem double-charge pela perk.
- **A0015/A0016/A0017/A0018:** `ARCANE_BACKLASH` e ataques de allies/bodyguards Mobstein não podem ser reclassificados como ações diretas/root actions do jogador.
- **Mastery Epic Fight:** hits repetidos não concedem Mastery; categorias usam milestones/descobertas persistentes conforme policy canônica.

## Evidência comum

- `NotionCombatPerkRules` — coeficientes, thresholds e durações.
- `A0001A0020CombatPolicy` — política provider-independent, consumo de recursos e deduplicação.
- `NotionCombatPerkState` — Ímpeto, Fúria, Controle de Distância, janelas, lockouts e Queda de Ritmo.
- `A0001A0020CriticalService` — resolução crítica única.
- `A0001A0020EpicFightHooks` — hits PRE/POST, transação corporal A0012, attack speed, dodge, miss, stagger, alcance e tick server-side.
- `ColdSweatFrenzyBridge` — integração fail-closed com Cold Sweat 2.4.2 `Temperature.Trait.CORE`.
- `EpicFightProgressionHooks` + `MasteryPolicies` — Mastery baseada em milestones persistentes.
- `A0001A0020CombatPolicyTest` e `EpicFightDepthPolicyTest` — regressões dos contratos corrigidos.

A matriz histórica detalhada dos nove eixos está em `AUDITORIA-A0001-A0020.md`. As retroauditorias focadas estão em `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md` e `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`.

## Chat 1 V3 — fechamento do ciclo exato A0001–A0010

**Estado:** `LOTE FECHADO NO DESIGN`.

- **INÍCIO:** A0001.
- **FIM:** A0010.
- **Quantidade:** 10 perks consecutivas.
- **Re-fetch Notion:** A0001–A0010 = 10/10 PASS em 2026-08-30.
- **Nove eixos:** 10/10 PASS.
- **18 critérios técnicos:** PASS/N/A justificado; nenhum bloqueio de design.
- **Provider principal:** Epic Fight `21.17.3.1`.

## Chat 2 — implementação, testes e merge — A0001–A0010

- **PR:** #221 — `test(perks): confirm A0001-A0010 implementation contracts` — **MERGEADA** em 2026-08-30.
- **Merge commit:** `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`.
- **Estado definitivo:** A0001–A0010 = `IMPLEMENTAÇÃO CONFIRMADA`.

## Auditoria retroativa de integração — A0001–A0010 — projetos próprios + Mobstein 5.4.4

**Estado:** `LOTE RETROATIVO FECHADO NO DESIGN`; será fechado operacionalmente junto à PR documental combinada A0001–A0020 autorizada pelo usuário.

- **Escopo exclusivo:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4.
- **Arquivo:** `AUDITORIA-RETROATIVA-PROVIDERS-A0001-A0010.md`.
- **Notion alterado:** A0003, A0004, A0005, A0006, A0009 e A0010 — `Hook`, `Fallback` e `Regra`; re-fetch 6/6 PASS.
- **A0001/A0002/A0007/A0008:** re-fetch sem drift; nenhuma mutação artificial.
- **Boundary principal:** Backlash terminal e companions Mobstein não herdam autoria marcial do dono; Arcane/Shroud states não viram defesa física.

## Auditoria retroativa de integração — A0011–A0020 — projetos próprios + Mobstein 5.4.4

**Estado:** `LOTE RETROATIVO FECHADO NO DESIGN`; pronto para PR/CI/merge combinado com o lote anterior.

- **INÍCIO:** A0011.
- **FIM:** A0020.
- **Quantidade:** 10 perks consecutivas.
- **Arquivo:** `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`.
- **Notion alterado:** A0011, A0012, A0015, A0016, A0017 e A0018 — `Hook`, `Fallback` e `Regra`; re-fetch 6/6 PASS.
- **A0013/A0014/A0019/A0020:** re-fetch sem drift; nenhuma mutação cosmética.
- **Volcanoes delta:** `main@7839db6d9b718e1e2becfe8b88e9b3d24282e2ef` trouxe coexistência segura de prospecção hidrotermal RNS. Disposição: `PERK PRÓPRIA / CICLO FUTURO DE GEOLOGIA-PROSPECÇÃO`; não integrar às perks MARTIAL deste lote.
- **A0012:** Volcanoes pode afetar o mesmo Cold Sweat pela bridge ambiental canônica, mas A0012 não chama Volcanoes nem reaplica/deduz o calor ambiental; Cold Sweat permanece authority corporal.
- **A0017:** `P-A0017-01` permanece `FAIL-CLOSED CORRETO`; redução de deslocamento ofensivo continua omitida até receipt provider-native seguro.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story permanecem authorities separadas, sem conversão para guarda, CORE ou receipt MARTIAL.
- **Black Arcana:** `ARCANE_BACKLASH` terminal não crita/proca/gera recursos/janelas/Mastery neste lote.
- **Mobstein 5.4.4:** combate direto contra seus mobs/bosses é coberto universalmente; allies/bodyguards ressuscitados são Mobstein-owned e não herdam autoria do dono.
- **RPG Stage 12 Bodies ↔ Mobstein:** `SEM HOOK SEGURO` até runtime/boundary canônico existir.

## Estado combinado desta retroauditoria

- **A0001–A0020:** 20/20 perks retroauditadas em **dois lotes exatos de 10**, sem iniciar A0021.
- **Notion:** 12 páginas receberam correções reais; todas re-fetched com persistência confirmada.
- **GitHub:** 20 dossiês + duas auditorias retroativas + este STATUS atualizados; nenhum runtime alterado.
- **Próxima etapa:** PR → reviews → CI GREEN → merge → confirmação da `main` → PARAR.
