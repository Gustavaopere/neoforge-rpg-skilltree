# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0050** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

A fonte canônica de design permanece o Notion. `IMPLEMENTAÇÃO CONFIRMADA` só é definitiva após contrato implementado, testes pertinentes, PR verde e merge em `main`.

| Código | Perk | Design | Estado técnico auditado | Pendências bloqueantes |
|---|---|---|---|---|
| A0001 | Treino com Espadas I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore mergeada pela PR #237 | nenhuma |
| A0002 | Treino com Espadas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore mergeada pela PR #237 | nenhuma |
| A0003 | Precisão com Espadas | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provenance/crítico mergeada pela PR #237 | nenhuma |
| A0004 | Ritmo do Duelista | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação autoria/recursos mergeada pela PR #237 | nenhuma |
| A0005 | Abertura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação defesa física mergeada pela PR #237; `P-A0005-02` corrigida na PR #244 | nenhuma |
| A0006 | Maestria de Espadas — Riposta Perfeita | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provenance/defesa mergeada pela PR #237; `P-A0006-01` corrigida na PR #244 | nenhuma bloqueante; aparo/guarda perfeita extras seguem expansão condicional fail-closed |
| A0007 | Treino com Machados I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore mergeada pela PR #237 | nenhuma |
| A0008 | Treino com Machados II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provider→árvore mergeada pela PR #237 | nenhuma |
| A0009 | Precisão com Machados | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação provenance/crítico mergeada pela PR #237 | nenhuma |
| A0010 | Pressão do Carrasco | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #221; revalidação Fúria/autoria mergeada pela PR #237 | nenhuma |
| A0011 | Ruptura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; `P-A0011-02` corrigida na PR #250 | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; Cold Sweat 2.4.2 exato + diagnóstico bounded; transação PRE preservada conforme contrato | nenhuma |
| A0013 | Treino com Lanças I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; classificação provider-native/fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237 via attack-speed provider-native | nenhuma |
| A0015 | Precisão com Lanças | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; crítico canônico direto | nenhuma |
| A0016 | Distância Ideal | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; cargas direct-player; ordem consumer→gain endurecida na PR #250 | nenhuma |
| A0017 | Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pela PR #237; `P-A0017-02` corrigida na PR #250 | nenhuma bloqueante; `P-A0017-01` permanece aberta/fail-closed correto |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; `P-A0018-01` corrigida na PR #250 com commit causal/lockout POST | nenhuma |
| A0019 | Treino com Adagas I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; classificação provider-native/fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237 via attack-speed provider-native | nenhuma |
| A0021 | Precisão com Adagas | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248; confirmação definitiva após merge | nenhuma |
| A0022 | Ritmo das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248; stagger forte, fallback geométrico, idle decay e supressão integral de knockback corrigidos | nenhuma |
| A0023 | Ataque ao Ponto Cego | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248; orientação server-side preservada | nenhuma |
| A0024 | Maestria de Adagas — Dança das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248; fallback canônico de stamina preservado | nenhuma bloqueante |
| A0025 | Treino com Martelos I | APROVADO após correção | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248; HAMMER provider-native + Mastery anti-farm | nenhuma |
| A0026 | Treino com Martelos II | APROVADO | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248 via attack-speed provider-native | nenhuma |
| A0027 | Precisão com Martelos | APROVADO + boundary | IMPLEMENTAÇÃO VALIDADA EM CI na PR #248 no resolver crítico canônico | nenhuma |
| A0028 | Abalo Crescente | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL VALIDADA FAIL-CLOSED na PR #248 | `P-A0028-01`: Epic Fight 21.17.3.1 sem receipt causal separado de guard pressure |
| A0029 | Quebra de Postura | APROVADO + boundary | NÃO CONFIRMADA; fail-closed validado na PR #248 | `P-A0029-01`: Epic Fight 21.17.3.1 sem heavy receipt inequívoco |
| A0030 | Maestria de Martelos — Golpe Demolidor | APROVADO + boundary | NÃO CONFIRMADA; fail-closed validado na PR #248 | `P-A0030-01`: guard-break causal attacker-side + heavy receipt ausentes |
| A0031 | Treino com Maças I | APROVADO após correção | NÃO CONFIRMADA | `P-A0031-01`: remover tag MACE; `P-A0031-02`: Mastery anti-farm |
| A0032 | Treino com Maças II | APROVADO | CÓDIGO PRESENTE via attack-speed | depende de `P-A0031-01` |
| A0033 | Precisão com Maças | APROVADO + boundary | CÓDIGO PRESENTE no crítico canônico | depende de `P-A0031-01` |
| A0034 | Trauma Contundente | APROVADO + boundary | CÓDIGO PRESENTE no fallback Armor física | rotas extras guard/posture permanecem fail-closed sem receipt |
| A0035 | Armadura Fendida | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL | `P-A0035-01`: boss Witherstein; `P-A0035-02`: commit Trauma/Sunder somente após hit confirmado |
| A0036 | Maestria de Maças — Quebra-Ossos | APROVADO após correção | NÃO CONFIRMADA | `P-A0036-01`: heavy receipt; `P-A0036-02`: aplicar Descompasso; `P-A0036-03`: Sunder deve preexistir ao root; depende de Mastery anti-farm |
| A0037 | Treino com Foices I | APROVADO após correção | NÃO CONFIRMADA | `P-A0037-01`: remover tag SCYTHE; `P-A0037-02`: Mastery anti-farm |
| A0038 | Treino com Foices II | APROVADO | CÓDIGO PRESENTE via attack-speed | depende de `P-A0037-01` |
| A0039 | Precisão com Foices | APROVADO + boundary | CÓDIGO PRESENTE no crítico canônico | depende de `P-A0037-01` |
| A0040 | Marca da Ceifa | APROVADO | IMPLEMENTAÇÃO PARCIAL | `P-A0040-01`: cleanup de marca em target unload/despawn; depende de família SCYTHE segura |
| A0041 | Corte de Ceifa | APROVADO após correção | IMPLEMENTAÇÃO PARCIAL | `P-A0041-01`: reservation→commit da Marca somente após hit confirmado; depende de família SCYTHE segura |
| A0042 | Maestria de Foices — Colheita de Batalha | APROVADO após correção | IMPLEMENTAÇÃO PARCIAL | `P-A0042-01`: `eligible_kill` anti-abuso; `P-A0042-02`: unificar/deduplicar producers de death; teste transversal |
| A0043 | Treino com Arcos I | APROVADO após correção | IMPLEMENTAÇÃO PARCIAL | `P-A0043-01`: Mastery BOW por discovery finita; `P-A0043-02`: reconciliar `combat:bow` vs `epicfight:bow`; teste provider-present |
| A0044 | Treino com Arcos II | APROVADO após review | NÃO CONFORME: availability fail-closed não implementada | `P-A0044-01`: sem draw/preparation binding, nó deve ser indisponível/não comprável e não pode gastar pontos |
| A0045 | Precisão com Arcos | APROVADO | CÓDIGO PRESENTE no crítico canônico | depende de A0043 alcançar Mastery 60 e de prova gameplay transversal |
| A0046 | Foco de Mira | APROVADO após correção | IMPLEMENTAÇÃO PARCIAL | `P-A0046-01`: heavy-impact −25 Focus; `P-A0046-02`: escalares corporais reais; teste provider-present |
| A0047 | Distância Dominada | APROVADO após review | IMPLEMENTAÇÃO PARCIAL | `P-A0047-01`: remover projectile speed fabricado; depende de `P-A0044-01`; A0044 indisponível torna A0047 não comprável |
| A0048 | Maestria de Arcos — Tiro Preparado | APROVADO | CÓDIGO PRESENTE | depende de Mastery BOW 80 e prova gameplay/provider-present |
| A0049 | Treino com Bestas I | APROVADO após correção | IMPLEMENTAÇÃO PARCIAL | `P-A0049-01`: Mastery CROSSBOW por discovery finita; `P-A0049-02`: reconciliar `combat:crossbow` vs `epicfight:crossbow`; teste provider-present |
| A0050 | Treino com Bestas II | APROVADO após review | NÃO CONFORME: availability fail-closed não implementada | `P-A0050-01`: sem reload/preparation binding, nó deve ser indisponível/não comprável e não pode gastar pontos |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Unavailable-node invariant:** provider/binding obrigatório ausente ou incompatível gera estado explícito de nó indisponível/não comprável; nunca silent no-op purchase, rank fantasma ou gasto de pontos sem efeito.
- **Versões auditadas A0001–A0020:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance:** hits indiretos, companions, hazards ou fontes não hostis não podem herdar autoria MARTIAL do jogador.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. Famílias sem producer provider-native comprovado usam discovery/milestones finitos e deduplicados.
- **Mastery BOW/CROSSBOW:** fonte canônica do lote é `epicfight:bow` / `epicfight:crossbow`, conforme Notion + `CombatPerkTreeModel` + projectile runtime. `combat:bow` / `combat:crossbow` em `tree_architecture/combat.json` são divergência runtime/catalog a corrigir, não uma segunda ledger válida.
- **HAMMER/MACE/SCYTHE:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada.
- **BOW/CROSSBOW:** vanilla é classificado por `BowItem`/`CrossbowItem`; externos exigem provider-native/mapping explícito. Mastery 60 = 6 tipos hostis inéditos; Mastery 80 = 8 tipos quando o contrato terminal exigir.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real ocorre no commit pós-hit confirmado; cancelamento/dano zero não deixa estado fantasma.
- **Lifecycle:** estados por alvo precisam cleanup bounded quando alvo morre, é removido, descarrega ou desaparece sem evento terminal equivalente.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não crita/proca/concede Mastery/Focus/Marca/eligible_kill.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story/MagicResistance não classificam arma, projectile root, Focus ou proteção física destas perks.
- **Volcanoes:** hazards/geologia/prospecção permanecem fora do pipeline MARTIAL; A0046 só pode refletir temperatura Volcanoes indiretamente se o provider corporal Cold Sweat já a incorporou.
- **Mobstein 5.4.4:** companion damage/projectiles/kills são provider-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal quando receipt real e anti-abuso forem satisfeitos.
- **Stage 11.01 itemização:** authority própria de identidade/rolls; projeções de efeitos ainda não são contrato destas perks, portanto `SEM HOOK SEGURO` para dano/crítico/Focus/penetration/reload.
- **NeoVitae:** removido/ausente.

## Ciclos fechados anteriores

### A0001–A0010
- Chat 2 PR #221 mergeada; merge `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`; implementação confirmada.
- Revalidação provider→árvore posteriormente mergeada pela PR #237.
- Chat 3 PR #244 reabriu somente a auditoria técnica pós-merge desse mesmo lote e encontrou duas pendências causais: `P-A0005-02` e `P-A0006-01`.
- Ambas foram corrigidas sem redesenho: PRE apenas prepara; POST direto/hostil/com dano efetiva os consumos irreversíveis.
- TDD RED no CI #2193: 120 testes, exatamente 2 falhas correspondentes aos defeitos antigos.
- GREEN de código no CI #2203 e GREEN integral do HEAD documental pré-reconciliação no CI #2220, incluindo NeoForge GameTests, build, JAR e dedicated-server smoke.
- A PR #244 foi reconciliada com `main@5e9dd777722014596641cb77d7be5c51df410e4e` pelo merge commit `f110492c68e67efcb4848fbee99dc522f7cf0307`, preservando o fechamento A0041–A0050 da PR #243.
- Auditoria Chat 3: `audits/AUDITORIA-CHAT3-A0001-A0010-PENDENCIAS-TECNICAS.md`.
- Aparo/guarda perfeita adicionais de A0006 continuam expansão condicional não bloqueante e fail-closed sem receipt público causal.

### A0001–A0020 — retroauditoria combinada / Chat 2
- PR #233 mergeada; merge/main `0087ef7e513664454b3d54cb70a9c3f24ec46e84`.
- Chat 2 PR #237 mergeada em `8b76a7cef1aa675fdd988bf694c876e751fb839d`; implementação/revalidação A0011–A0020 confirmada na `main`.
- Auditorias: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md` e `audits/AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`.
- `P-A0017-01` continua não bloqueante e fail-closed correto.

### A0011–A0020 — Chat 3
- PR #250 reabre somente a auditoria técnica pós-merge do lote já confirmado pela #237; nenhum redesign foi feito.
- Pendências causais encontradas: `P-A0011-02`, `P-A0017-02`, `P-A0018-01`.
- As três foram corrigidas por reservation→commit: PRE calcula/reserva; POST direto/hostil/com dano efetiva os consumos irreversíveis.
- A0011 reserva 20 Fúria no PRE para manter a avaliação efetiva de A0012, mas só debita no POST; commit ocorre antes do ganho A0010.
- A0017 reserva janela + 1 Controle de Distância; commit ocorre antes do ganho A0016.
- A0018 reserva janela + 3 cargas e só inicia lockout de 8 s no POST válido; commit ocorre antes do ganho A0016.
- A0012 permanece intencionalmente com sua transação PRE, conforme o contrato aprovado; não foi redesenhada.
- `P-A0017-01` permanece aberta, não bloqueante e fail-closed: redução de deslocamento exige receipt ofensivo provider-native causal.
- TDD RED no CI #2256: 123 testes, exatamente 3 falhas correspondentes aos três defeitos antigos.
- GREEN de código no CI #2269, HEAD `1698bdc518f84ae99da6a9f6da1a78ad5b9f3923`: JUnit, NeoForge GameTests, validações, build, JAR e dedicated-server smoke verdes; nove auxiliares verdes.
- Branch reconciliada com `main@c6faec4e889386b338b9205845efbcd8e0e9a747` após a PR #248, preservando A0021–A0030.
- Auditoria Chat 3: `audits/AUDITORIA-CHAT3-A0011-A0020-PENDENCIAS-TECNICAS.md`.
- Estado final do lote depende apenas do CI do HEAD documental/reconciliado e merge da PR #250; depois disso o Chat 3 deve PARAR.

### A0021–A0030 — Chat 2
- Chat 1 PR #235 mergeada; merge `15cf3f75959165e0a40f4b0be8263ffae83cb097`; design fechado.
- Chat 2 PR draft #242 iniciou a implementação; foi fechada sem merge após a limitação do conector para mudar `ready for review`. A PR final não-draft é #248, com a mesma linha de implementação e os hardenings posteriores.
- A0022 resolve `P-A0022-01/02/03`; o review final de #248 também fechou o falso reposicionamento possível durante a inércia de knockback por supressão até 3 ticks quietos.
- A0025 resolve `P-A0025-01/02`.
- A0028, A0029 e A0030 permanecem com pendências técnicas explícitas e comportamento fail-closed, sem heurística substituta.
- Auditorias: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md` e `audits/AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`.
- Estado pré-merge: implementação e regressões validadas em CI na PR #248; confirmação definitiva das perks implementadas após merge e confirmação da `main`.

### A0031–A0040
- Chat 1 PR #239 mergeada; merge `689d0d4f0290686cbed61056e3471a124a01101c`.
- Design fechado; review incorporou `P-A0035-02`, `P-A0036-03` e `P-A0040-01`.
- Auditoria: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`.
- Runtime não foi alterado pelo Chat 1.

## Chat 1 — lote exato A0041–A0050

**Estado:** `LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME CATALOGADOS`.

- **PR de fechamento:** #243 (`docs(perks): close Chat 1 A0041-A0050 and organize audits`).
- **INÍCIO:** A0041.
- **FIM:** A0050.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura:** RPG Skill Tree `main@8b76a7cef1aa675fdd988bf694c876e751fb839d`.
- **Freshness de fechamento:** RPG Skill Tree `main@d1c29b1acca488f14e0741073f90502621a5ed39`; o delta posterior contém somente corpus editorial pt-BR do Compendium + teste e não altera contratos A0041–A0050.
- **Providers rechecados:** Volcanoes `7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`; Enshrouded `f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`; Black Arcana `73c14ce55ff918bb8a81daeb99a352607ef11064`; Mobstein 5.4.4 conforme guia obrigatório.
- **Notion fetch fresco:** 10/10.
- **Notion alterado no fechamento inicial:** A0041, A0042, A0043, A0046, A0049.
- **Correções adicionais após review PR #243:** A0044, A0047, A0050.
- **Total de páginas Notion mutadas:** 8/10.
- **Re-fetch pós-escrita:** 8/8 PASS em 2026-08-30.
- **Sem mutação:** A0045, A0048.
- **Nove eixos / 18 critérios:** design PASS/N/A após review; divergências runtime/provider availability e mastery namespace estão explicitamente catalogadas e impedem confirmação de implementação.
- **Review PR #243:** dois findings confirmados contra código real: silent no-op purchase de A0044/A0050 e divergência `combat:*` vs `epicfight:*` para Mastery BOW/CROSSBOW; ambos incorporados ao Notion/dossiês/auditoria.
- **Organização corrigida:** auditorias movidas para `perks/audits/`; `audits/README.md` é o índice; novos `AUDITORIA-*.md` não ficam mais na raiz.
- **Arquivo canônico do lote:** `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0051+:** não iniciado.

### Pendências destinadas ao Chat 2

1. `P-A0041-01` — reservation→commit da Marca Madura por `rootActionId`; commit somente no POST confirmado com dano >0.
2. `P-A0042-01` — usar receipt `eligible_kill` anti-abuso central em vez de `Enemy || Player` como prova suficiente.
3. `P-A0042-02` — unificar/deduplicar os dois producers de death de Colheita de Batalha.
4. `P-A0043-01` — Mastery BOW +10 por tipo hostil inédito no pós-hit BOW real; 6→60, 8→80; dedup contra producer Epic Fight.
5. `P-A0043-02` — reconciliar `tree_architecture/combat.json` de `combat:bow` para a ledger canônica `epicfight:bow` ou realizar migração formal antes de trocar ID; teste architecture↔model↔contrato.
6. `P-A0044-01` — availability gate server-authoritative: sem draw/preparation binding seguro, A0044 é indisponível/não comprável; nenhum gasto/rank no-op; A0047 fica bloqueada.
7. `P-A0046-01` — integrar receipt hostil pesado seguro e aplicar −25 Focus uma vez por outcome.
8. `P-A0046-02` — integrar somente providers corporais reais; eixo indisponível é omitido; Volcanoes nunca é lido diretamente.
9. `P-A0047-01` — remover `projectileSpeedAvailable=true` incondicional/mutação genérica de `deltaMovement`; speed fica omitido sem provider semântico; respeitar availability de A0044.
10. `P-A0049-01` — Mastery CROSSBOW +10 por tipo hostil inédito; 6→60, 8→80; dedup contra producer Epic Fight.
11. `P-A0049-02` — reconciliar `combat:crossbow` para `epicfight:crossbow` ou migrar formalmente; nunca manter duas ledgers paralelas.
12. `P-A0050-01` — availability gate server-authoritative: sem reload/preparation binding seguro, A0050 é indisponível/não comprável; nenhum gasto/rank no-op.
13. `P-A0041-50-TEST-01` — GameTest/harness server-side provider-present/absent para SCYTHE/BOW/CROSSBOW, Mastery, Focus, availability, dedup, lifecycle e multiplayer.

O estado de CI/merge da PR de fechamento é confirmado no GitHub; este arquivo registra o design canônico e as pendências técnicas. Após o merge da PR #243, o ciclo A0041–A0050 está operacionalmente encerrado e o próximo lote só pode começar mediante novo comando do usuário.