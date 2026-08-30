# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0040** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. `IMPLEMENTAÇÃO CONFIRMADA` só é definitiva após contrato implementado, testes pertinentes, PR verde e merge em `main`.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #237 | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #237 | nenhuma |
| A0003 | Precisão com Espadas | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/crítico VALIDADA EM CI na PR #237 | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de autoria/recursos VALIDADA EM CI na PR #237 | nenhuma |
| A0005 | Abertura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de defesa física VALIDADA EM CI na PR #237 | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/defesa VALIDADA EM CI na PR #237 | nenhuma |
| A0007 | Treino com Machados I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #237 | nenhuma |
| A0008 | Treino com Machados II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore VALIDADA EM CI na PR #237 | nenhuma |
| A0009 | Precisão com Machados | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de provenance/crítico VALIDADA EM CI na PR #237 | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação de Fúria/autoria VALIDADA EM CI na PR #237 | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; confirmação definitiva após merge | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; Cold Sweat 2.4.2 exato + diagnóstico bounded | nenhuma |
| A0013 | Treino com Lanças I | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; classificação provider-native/fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237 via attack-speed provider-native | nenhuma |
| A0015 | Precisão com Lanças | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; crítico canônico direto | nenhuma |
| A0016 | Distância Ideal | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; cargas direct-player | nenhuma |
| A0017 | Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI NO FALLBACK CANÔNICO na PR #237 | nenhuma bloqueante; `P-A0017-01` permanece fail-closed correto |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; crossing/janela/consumo causais | nenhuma |
| A0019 | Treino com Adagas I | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237; classificação provider-native/fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #237 via attack-speed provider-native | nenhuma |
| A0021 | Precisão com Adagas | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242; confirmação definitiva após merge | nenhuma |
| A0022 | Ritmo das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242; stagger/geometria/idle decay resolvidos | nenhuma |
| A0023 | Ataque ao Ponto Cego | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242; orientação server-side preservada | nenhuma |
| A0024 | Maestria de Adagas — Dança das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI NO FALLBACK CANÔNICO na PR #242 | nenhuma bloqueante; stamina é omitida somente quando não há receipt exato |
| A0025 | Treino com Martelos I | APROVADO após correção | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242; HAMMER provider-native + DiscoveryProgress anti-farm | nenhuma |
| A0026 | Treino com Martelos II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242 via attack-speed provider-native | nenhuma |
| A0027 | Precisão com Martelos | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #242; crítico canônico provider-native | nenhuma |
| A0028 | Abalo Crescente | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL / FAIL-CLOSED VALIDADO EM CI na PR #242 | `P-A0028-01`: guard pressure receipt causal ausente no Epic Fight 21.17.3.1 |
| A0029 | Quebra de Postura | APROVADO + boundary | NÃO CONFIRMADA / FAIL-CLOSED VALIDADO EM CI na PR #242 | `P-A0029-01`: heavy receipt inequívoco ausente no Epic Fight 21.17.3.1 |
| A0030 | Maestria de Martelos — Golpe Demolidor | APROVADO + boundary | NÃO CONFIRMADA / FAIL-CLOSED VALIDADO EM CI na PR #242 | `P-A0030-01`: guard-break causal + heavy receipt ausentes |
| A0031 | Treino com Maças I | APROVADO após correção | NÃO CONFIRMADA | `P-A0031-01`: remover tag MACE; `P-A0031-02`: Mastery anti-farm |
| A0032 | Treino com Maças II | APROVADO | Presente via attack-speed | depende de `P-A0031-01` |
| A0033 | Precisão com Maças | APROVADO + boundary | Presente no crítico canônico | depende de `P-A0031-01` |
| A0034 | Trauma Contundente | APROVADO + boundary | Presente no fallback Armor física | rotas extras guard/posture permanecem fail-closed sem receipt |
| A0035 | Armadura Fendida | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL | `P-A0035-01`: boss Witherstein; `P-A0035-02`: commit de Trauma/Sunder somente após hit confirmado |
| A0036 | Maestria de Maças — Quebra-Ossos | APROVADO após correção | NÃO CONFIRMADA | `P-A0036-01`: heavy receipt; `P-A0036-02`: aplicar Descompasso; `P-A0036-03`: Sunder deve preexistir ao root; depende de Mastery anti-farm |
| A0037 | Treino com Foices I | APROVADO após correção | NÃO CONFIRMADA | `P-A0037-01`: remover tag SCYTHE; `P-A0037-02`: Mastery anti-farm |
| A0038 | Treino com Foices II | APROVADO | Presente via attack-speed | depende de `P-A0037-01` |
| A0039 | Precisão com Foices | APROVADO + boundary | Presente no crítico canônico | depende de `P-A0037-01` |
| A0040 | Marca da Ceifa | APROVADO | IMPLEMENTAÇÃO PARCIAL | `P-A0040-01`: cleanup de marca em target unload/despawn; depende de família SCYTHE segura |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Versões auditadas A0001–A0030:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance A0001–A0030:** hits indiretos ou não hostis não podem ser promovidos a ataques MARTIAL diretos.
- **HAMMER A0025–A0030:** somente categoria Epic Fight explícita; `rpgskilltree:hammers` foi removida do runtime e do datapack no Chat 2 PR #242.
- **Maça vanilla:** `minecraft:mace` usa identidade exata; externos precisam de capability/mapping MACE seguro.
- **SCYTHE:** somente capability/mapping provider-native; não inferir por enxada/nome/aparência.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. HAMMER usa `DiscoveryProgress` +10 uma vez por tipo hostil inédito; MACE/SCYTHE permanecem pendentes para seu lote Chat 2.
- **HAMMER:** `epicfight:heavy` gate70 = 7 tipos; terminal A0030 gate80 = 8 tipos.
- **MACE:** `combat:mace` gate60 = 6 tipos; terminal A0036 gate80 = 8 tipos.
- **SCYTHE:** `combat:scythe` gate60 = 6 tipos.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real deve ocorrer no commit pós-hit confirmado; cancelamento/dano zero não pode deixar estado fantasma.
- **Sequencing:** Quebra-Ossos exige Armadura Fendida pré-existente; o mesmo root action não pode criar Sunder e simultaneamente satisfazer A0036.
- **Lifecycle:** estados por alvo precisam bounded cleanup também quando o alvo some sem morte/evento terminal equivalente.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **Black Arcana:** `ARCANE_BACKLASH` permanece terminal e não crita/proca/concede Mastery/Trauma/Marca.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story não fornecem Armor, heavy ou weapon receipt.
- **Volcanoes:** hazards/geologia permanecem fora do pipeline MARTIAL; A0012 não reaplica/deduz calor ambiental Volcanoes.
- **Mobstein 5.4.4:** companion damage é provider-owned; Witherstein boss-half requer classificação versionada comprovada.
- **Stage 11.01 itemização:** authority própria de identidade/rolls; projeções de efeitos ainda não existem, portanto `SEM HOOK SEGURO` para integração direta com A0031–A0040.
- **NeoVitae:** removido/ausente.

## Ciclos fechados anteriores

### A0001–A0010
- Chat 2 PR #221 mergeada; merge `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`; implementação confirmada.
- Revalidação provider→árvore posterior está na PR #237.

### A0001–A0020 — retroauditoria combinada
- PR #233 mergeada; merge/main `0087ef7e513664454b3d54cb70a9c3f24ec46e84`; CI principal #2105 + auxiliares GREEN.
- Chat 2 PR #237 implementa/revalida os boundaries runtime exigidos pela retroauditoria; substitui a #234, fechada sem merge por bloqueio operacional de draft no conector.
- P-A0017-01 continua não bloqueante e fail-closed correto.

### A0011–A0020 — Chat 2
- Design/retroauditoria fechados em `AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md`.
- Implementação/revalidação técnica documentada nos 10 dossiês e em `AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`.
- Estado pré-merge registrado na PR #237; o próximo lote foi determinado pelo estado técnico real pós-merge, não por esse texto histórico.

### A0021–A0030 — Chat 1
- Chat 1 PR #235 — `docs(perks): close Chat 1 A0021-A0030` — **MERGEADA**.
- Merge commit `15cf3f75959165e0a40f4b0be8263ffae83cb097`.
- CI principal #2129 + oito auxiliares: GREEN.
- Design fechado para o Chat 2.

### A0021–A0030 — Chat 2
- PR #242 — `feat(perks): implement A0021-A0030 Chat 2 contracts`.
- `P-A0022-01`, `P-A0022-02`, `P-A0022-03`, `P-A0025-01` e `P-A0025-02` resolvidas.
- A0021–A0027: `IMPLEMENTAÇÃO VALIDADA EM CI`; confirmação definitiva após merge e confirmação da `main`.
- A0028: `IMPLEMENTAÇÃO PARCIAL / FAIL-CLOSED`; `P-A0028-01` permanece porque Epic Fight 21.17.3.1 não fornece guard-pressure receipt causal separado de impact/knockback.
- A0029: `NÃO CONFIRMADA / FAIL-CLOSED`; `P-A0029-01` permanece porque `shouldChargeWeapon()` é combo/Weapon Innate, não heavy receipt.
- A0030: `NÃO CONFIRMADA / FAIL-CLOSED`; `P-A0030-01` permanece porque `GuardSkill` mantém `GUARD_BREAK` interno e não publica receipt causal attacker-side, além da ausência de heavy receipt.
- Head técnico `6938549c3961821d72dc3dcba4c6044a8f09e7d9`: RPG Skill Tree CI #2192 GREEN, incluindo JUnit 5, NeoForge GameTests, build, JAR e dedicated-server smoke; nove workflows auxiliares GREEN.
- Auditoria técnica: `AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`.

## Chat 1 — lote exato A0031–A0040

**Estado:** `LOTE FECHADO NO DESIGN`.

- **INÍCIO:** A0031.
- **FIM:** A0040.
- **Quantidade:** 10 perks consecutivas.
- **Base reconciliada inicialmente:** `main@492a4d28ee4b57a7e43645f623c4d07c08ac3361`; deltas concorrentes Stage 11.01 e Stage 10.10/pt-BR não alteraram contratos A0031–A0040.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0031, A0033, A0034, A0035, A0036, A0037, A0039.
- **Re-fetch pós-escrita:** 7/7 PASS em 2026-08-30.
- **Sem mutação:** A0032, A0038, A0040.
- **Nove eixos / 18 critérios:** PASS/N/A justificado no design; gaps runtime catalogados.
- **Review PR #239:** três findings de implementação incorporados como `P-A0035-02`, `P-A0036-03` e `P-A0040-01`; nenhum exige redesenho do Notion.
- **Arquivo canônico:** `AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0041+:** não iniciado.

### Pendências para Chat 2

1. `P-A0031-01` — remover tag MACE; vanilla mace por identidade exata, externos provider-native.
2. `P-A0031-02` — `combat:mace` anti-farm via DiscoveryProgress.
3. `P-A0035-01` — verificar boss classification do Witherstein em Mobstein 5.4.4; mapping só com registry id comprovado.
4. `P-A0035-02` — mover consumo definitivo de 3 Trauma + `markSundered` para commit do mesmo root action em hit confirmado; dano zero/cancelamento não consome/não cria state fantasma.
5. `P-A0036-01` — heavy receipt MACE seguro.
6. `P-A0036-02` — aplicar realmente Descompasso: −8% dano físico causado + −10% movement por 3 s, boss half/cooldown/lifecycle.
7. `P-A0036-03` — A0036 só pode observar Sunder existente antes do root action atual; não permitir que o mesmo golpe aplique A0035 e ative A0036.
8. `P-A0037-01` — remover tag SCYTHE; provider-native only.
9. `P-A0037-02` — `combat:scythe` anti-farm via DiscoveryProgress.
10. `P-A0040-01` — cleanup bounded/server-authoritative de `reapMarks` para alvo removido/despawnado/chunk descarregado sem morte.

**Próxima etapa do ciclo A0021–A0030:** CI/review do HEAD documental → merge da PR #242 → confirmação da `main` → PARAR. A0031–A0040 não começa automaticamente.
