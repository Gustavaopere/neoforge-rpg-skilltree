# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0060** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

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
| A0011 | Ruptura de Guarda | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237 | nenhuma |
| A0012 | Maestria de Machados — Frenesi do Saqueador | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; Cold Sweat 2.4.2 exato + diagnóstico bounded | nenhuma |
| A0013 | Treino com Lanças I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; classificação provider-native/fail-closed | nenhuma |
| A0014 | Treino com Lanças II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237 via attack-speed provider-native | nenhuma |
| A0015 | Precisão com Lanças | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; crítico canônico direto | nenhuma |
| A0016 | Distância Ideal | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; cargas direct-player | nenhuma |
| A0017 | Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pela PR #237 | nenhuma bloqueante; `P-A0017-01` permanece fail-closed correto |
| A0018 | Maestria de Lanças — Linha de Interceptação | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; crossing/janela/consumo causais | nenhuma |
| A0019 | Treino com Adagas I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; classificação provider-native/fail-closed | nenhuma |
| A0020 | Treino com Adagas II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237 via attack-speed provider-native | nenhuma |
| A0021 | Precisão com Adagas | APROVADO + boundary | CÓDIGO PRESENTE; crítico canônico direto | nenhuma exclusiva |
| A0022 | Ritmo das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL | `P-A0022-01`: stagger; `P-A0022-02`: fallback geométrico; `P-A0022-03`: idle decay sem alvo |
| A0023 | Ataque ao Ponto Cego | APROVADO + boundary | CÓDIGO PRESENTE; orientação server-side | nenhuma exclusiva |
| A0024 | Maestria de Adagas — Dança das Sombras | APROVADO + boundary | CÓDIGO PRESENTE com fallback canônico de stamina | depende de A0022 não assumir rota geométrica ausente |
| A0025 | Treino com Martelos I | APROVADO após correção | NÃO CONFIRMADA | `P-A0025-01`: remover tag HAMMER; `P-A0025-02`: Mastery anti-farm |
| A0026 | Treino com Martelos II | APROVADO | CÓDIGO PRESENTE via attack-speed | depende de `P-A0025-01` |
| A0027 | Precisão com Martelos | APROVADO + boundary | CÓDIGO PRESENTE no resolver crítico | depende de `P-A0025-01` |
| A0028 | Abalo Crescente | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL | `P-A0028-01`: guard pressure receipt |
| A0029 | Quebra de Postura | APROVADO + boundary | NÃO CONFIRMADA | `P-A0029-01`: heavy receipt |
| A0030 | Maestria de Martelos — Golpe Demolidor | APROVADO + boundary | NÃO CONFIRMADA | `P-A0030-01`: guard-break + heavy receipt |
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
| A0051 | Precisão com Bestas | APROVADO após correção | CÓDIGO PRESENTE no crítico físico CROSSBOW | herda `P-A0049-01/-02`: sem producer finite-discovery e ledger única, A0049/A0051+ não são alcançáveis; classificação externa sem mapping fica fail-closed |
| A0052 | Cadência de Recarga | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL / estruturalmente indisponível | `P-A0052-01`: availability; `P-A0052-02`: mesma besta hit→reload; `P-A0052-04`: Multishot/root outcome; herda A0049/A0050 |
| A0053 | Virote Perfurante | APROVADO após correção/review | caminho penetration presente, mas nó indisponível | `P-A0053-01`: availability; `P-A0053-02`: reservation→commit/rollback até projectile spawn; herda cadeia CROSSBOW |
| A0054 | Maestria de Bestas — Mecanismo Ajustado | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL / estruturalmente indisponível | `P-A0054-01`: consumo no disparo; `P-A0054-02`: availability; `P-A0054-03`: ledger; `P-A0054-04`: launch rollback |
| A0055 | Treino com Armas de Punho I | APROVADO após correção | NÃO CONFIRMADO como adquirível | `P-A0055-01`: producer único `combat:fist`; `P-A0055-02`: architecture `combat_fist`; `P-A0055-03`: regressão cruzada |
| A0056 | Treino com Armas de Punho II | APROVADO | CÓDIGO PRESENTE via attack-speed | depende do fechamento de A0055 |
| A0057 | Precisão com Armas de Punho | APROVADO após correção | CÓDIGO PRESENTE no crítico FIST | depende do fechamento de A0055 |
| A0058 | Sequência Limpa | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL | `P-A0058-01`: reset por heavy-impact recebido; `P-A0058-02`: body modulation opcional; depende de A0055 |
| A0059 | Quebra de Ritmo | APROVADO | FAIL-CLOSED CORRETO | `P-A0059-01`: heavy/finalizer receipt; `P-A0059-02`: guard-break movement; depende de A0055/A0058 |
| A0060 | Maestria de Armas de Punho — Combinação Final | APROVADO | FAIL-CLOSED CORRETO | `P-A0060-01`: heavy/finalizer; `P-A0060-02`: Stamina ledger; `P-A0060-03`: gate80 `combat:fist` |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Unavailable-node invariant:** provider/binding obrigatório ausente ou incompatível gera estado explícito de nó indisponível/não comprável; nunca silent no-op purchase, rank fantasma ou gasto de pontos sem efeito.
- **Versões auditadas A0001–A0020:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance:** hits indiretos, companions, hazards ou fontes não hostis não podem herdar autoria MARTIAL do jogador.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. Famílias sem producer provider-native comprovado usam discovery/milestones finitos e deduplicados.
- **Mastery BOW/CROSSBOW:** fonte canônica é `epicfight:bow` / `epicfight:crossbow`, conforme Notion + `CombatPerkTreeModel` + projectile runtime. `combat:bow` / `combat:crossbow` em `tree_architecture/combat.json` são divergência runtime/catalog a corrigir, não segunda ledger válida.
- **Mastery FIST:** fonte canônica do ramo A0055–A0060 é `combat:fist`, +10 por tipo hostil inédito; 6 tipos→60 e 8→80. O producer genérico `epicfight:fist` deve ser reconciliado/suprimido; `tree_architecture/combat.json` precisa publicar `combat_fist` antes de o gate ser considerado alinhado.
- **HAMMER/MACE/SCYTHE/FIST:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada. Mãos vazias só entram em FIST por mapping explícito/versionado.
- **BOW/CROSSBOW:** vanilla é classificado por `BowItem`/`CrossbowItem`; externos exigem provider-native/mapping explícito. Mastery 60 = 6 tipos hostis inéditos; Mastery 80 = 8 tipos quando o contrato terminal exigir.
- **Availability em Bestas:** enquanto A0050 estiver indisponível/não comprável, A0052, A0053 e A0054 também ficam estruturalmente indisponíveis; fallback não pode bypassar dependência. A ausência de `P-A0049-01` também bloqueia a alcançabilidade da cadeia por Mastery legítima.
- **Root outcome CROSSBOW:** Multishot compartilha uma única root action; projéteis irmãos produzem no máximo um success/failure e uma perda de Cadência por disparo. Success do root bloqueia failures tardios de irmãos.
- **Reservation→commit em lançamento:** Cadência/janela de A0053/A0054 só são consumidas quando o projectile/root é realmente criado; cancelamento tardio ou ausência de spawn faz rollback.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real ocorre no commit previsto pelo contrato; cancelamento/dano zero não deixa estado fantasma.
- **Lifecycle:** estados por alvo/ator precisam cleanup bounded em morte, remoção, unload/despawn, logout/dimensão/respawn e shutdown conforme owner.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não crita/proca/concede Mastery/Focus/Marca/Cadência/Sequência nem heavy/finalizer receipt.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story/MagicResistance não classificam arma, projectile root, Cadência, Sequência ou heavy/finalizer.
- **Volcanoes:** hazards/geologia/prospecção permanecem fora do pipeline MARTIAL; o delta RNS hidrotermal `7839db6...→c26e97c...` foi decomposto por capacidade na auditoria do lote e todas as linhas receberam `NÃO DEVE SER INTEGRADO` para A0051–A0060.
- **Mobstein 5.4.4:** companion damage/projectiles/kills são provider-owned; ataque direto do jogador contra entidade Mobstein continua cobertura universal quando o receipt real for satisfeito.
- **Stage 11.01 itemização:** authority própria de identidade/rolls; projeções de efeitos ainda não são contrato destas perks, portanto `SEM HOOK SEGURO` para dano/crítico/Cadência/Sequência/reload.
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

### A0021–A0030
- Chat 1 PR #235 mergeada; merge `15cf3f75959165e0a40f4b0be8263ffae83cb097`.
- Design fechado; implementação permanece parcial/não confirmada onde a tabela indica.
- Auditoria: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0021-A0030.md`.

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
- **A0051+:** não iniciado naquele ciclo.

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

O estado de CI/merge da PR de fechamento é confirmado no GitHub; este arquivo registra o design canônico e as pendências técnicas. Após o merge da PR #243, o ciclo A0041–A0050 está operacionalmente encerrado.

## Chat 1 — lote exato A0051–A0060

**Estado:** `LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME CATALOGADOS`.

- **INÍCIO:** A0051.
- **FIM:** A0060.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura:** RPG Skill Tree `main@5e9dd777722014596641cb77d7be5c51df410e4e`.
- **Base efetiva da branch após delta concorrente:** `main@2e6cf57d5c12630d55280d1c4ff0177f536dce96`; mudanças concorrentes do Chat 3 A0005/A0006 e Compendium foram preservadas.
- **Providers rechecados:** Volcanoes `c26e97c136b543f1fa0ef2ebb12044d10d8af816`; Enshrouded `f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`; Black Arcana `73c14ce55ff918bb8a81daeb99a352607ef11064`; Mobstein 5.4.4 conforme guia obrigatório.
- **Matriz de delta obrigatória:** registrada por capacidade em `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`; Volcanoes `7839db6...→c26e97c...` foi decomposto em ownership marker, persistência NBT/restart, foreign replacement safety e side-ledger recovery, todos `NÃO DEVE SER INTEGRADO` ao lote CROSSBOW/FIST.
- **Notion fetch fresco:** 10/10.
- **Notion alterado no fechamento inicial:** A0051, A0052, A0053, A0054, A0055, A0057, A0058.
- **Re-fetch inicial:** 7/7 PASS em 2026-08-30.
- **Review PR #249:** quatro findings válidos incorporados: blocker herdado `P-A0049-01`; reservation→commit de A0053/A0054; Multishot/root-outcome de A0052; matriz per-capability de delta Volcanoes.
- **Notion alterado após review:** A0052, A0053, A0054.
- **Re-fetch pós-review:** 3/3 PASS em 2026-08-30.
- **Sem mutação:** A0056, A0059, A0060; A0051 recebeu somente blocker técnico herdado após review.
- **Arquivo canônico do lote:** `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0061+:** não iniciado.

### Pendências destinadas ao Chat 2

1. `P-A0049-01` — producer finite-discovery `epicfight:crossbow`: +10 por tipo hostil inédito; 6→60, 8→80; dedup por player/category/type.
2. `P-A0049-02` — reconciliar `combat:crossbow` vs `epicfight:crossbow`; uma ledger apenas.
3. `P-A0052-01` — availability A0050→A0052.
4. `P-A0052-02` — mesma identidade causal de besta/ItemStack entre hit e reload; limpar receipt em troca/clone.
5. `P-A0052-03` — regressões de miss/cancel >50%/troca/reload real/estado externo/dedup.
6. `P-A0052-04` — Multishot: deduplicar success/failure por `rootActionId`; no máximo uma perda de Cadência por disparo e success bloqueia failures de irmãos.
7. `P-A0053-01` — availability A0052→A0053 e first-impact/dedup.
8. `P-A0053-02` — reservation→commit/rollback das 2 Cadências até criação confirmada do projectile/root; cancelamento/ausência de spawn não consome.
9. `P-A0054-01` — consumir as 3 Cadências no disparo que usa Mecanismo Ajustado, não ao armar a janela.
10. `P-A0054-02` — availability A0050/A0052/A0053→A0054.
11. `P-A0054-03` — uma única ledger CROSSBOW `epicfight:crossbow`.
12. `P-A0054-04` — reservation→commit/rollback da janela de Mecanismo Ajustado até projectile/root confirmado; cancelamento tardio não queima ativação/cargas.
13. `P-A0055-01` — producer `combat:fist` por discovery finita; 6→60, 8→80; suprimir/migrar `epicfight:fist` paralelo.
14. `P-A0055-02` — publicar/reconciliar `combat_fist` no architecture catalog.
15. `P-A0055-03` — teste architecture↔model↔Notion↔producer + provider-present/absent.
16. `P-A0058-01` — reset da Sequência por receipt provider-native de heavy impact recebido.
17. `P-A0058-02` — body modulation opcional somente por hunger/exhaustion real.
18. `P-A0059-01` — heavy/finalizer receipt para Quebra de Ritmo.
19. `P-A0059-02` — guard-break receipt + −8% movement por 2 s, com lifecycle seguro.
20. `P-A0060-01` — heavy/finalizer receipt para Combinação Final.
21. `P-A0060-02` — Stamina refund apenas por ledger causal pós-consumo das cinco ações; sem receipt, refund 0.
22. `P-A0060-03` — gate80 usa exclusivamente `combat:fist`.
23. `P-A0051-60-TEST-01` — GameTest/harness CROSSBOW/FIST, availability, Mastery, same-weapon correlation, Multishot root outcome, launch cancellation/rollback, heavy/finalizer fail-closed, dedup, lifecycle e multiplayer.

O fechamento operacional deste lote exige PR, review, CI GREEN, merge e confirmação da `main`; após isso o ciclo encerra e A0061+ só pode começar mediante novo comando do usuário.