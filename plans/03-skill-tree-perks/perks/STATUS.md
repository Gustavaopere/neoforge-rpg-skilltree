# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0070** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

Para novos ciclos deste chat, o registro operacional canônico é o GitHub: dossiês `Axxxx-*.md`, `STATUS.md` e auditorias em `perks/audits/`. Referências ao Notion abaixo são evidência histórica de ciclos anteriores; por instrução do usuário, este ciclo não faz novas gravações no Notion. `IMPLEMENTAÇÃO CONFIRMADA` só é definitiva após contrato implementado, testes pertinentes, PR verde e merge em `main`.

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
| A0051 | Precisão com Bestas | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL no crítico CROSSBOW | `P-A0051-01`: exigir launch provenance; herda `P-A0049-01/-02` producer/ledger CROSSBOW |
| A0052 | Cadência de Recarga | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL / estruturalmente indisponível | `P-A0052-01/-02/-04`: availability, mesma besta e Multishot; `P-A0052-05/-06`: launch provenance + lifecycle; herda A0049/A0050 |
| A0053 | Virote Perfurante | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL / nó indisponível | `P-A0053-01/-02`: availability + reservation→commit; `P-A0053-03/-04`: launch provenance + lifecycle; herda cadeia CROSSBOW |
| A0054 | Maestria de Bestas — Mecanismo Ajustado | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL / estruturalmente indisponível | `P-A0054-01/-04`: consumo/rollback; `P-A0054-02/-03`: availability/ledger; `P-A0054-05/-06`: launch provenance + lifecycle |
| A0055 | Treino com Armas de Punho I | APROVADO após correção | NÃO CONFIRMADO como adquirível | `P-A0055-01`: producer único `combat:fist`; `P-A0055-02`: architecture `combat_fist`; `P-A0055-03`: regressão cruzada |
| A0056 | Treino com Armas de Punho II | APROVADO | CÓDIGO PRESENTE via attack-speed | depende do fechamento de A0055 e reconciliação de rank/gateway |
| A0057 | Precisão com Armas de Punho | APROVADO após correção | CÓDIGO PRESENTE no crítico FIST | depende do fechamento de A0055 e reconciliação de rank/gateway |
| A0058 | Sequência Limpa | APROVADO após correção/review | IMPLEMENTAÇÃO PARCIAL | `P-A0058-01`: heavy-impact; `P-A0058-02`: body modulation opcional; `P-A0058-03`: lifecycle rank/respec/rules reload; depende de A0055 |
| A0059 | Quebra de Ritmo | APROVADO | FAIL-CLOSED CORRETO | `P-A0059-01`: heavy/finalizer; `P-A0059-02`: guard-break movement; `P-A0059-03`: lifecycle próprio; depende de A0055/A0058 |
| A0060 | Maestria de Armas de Punho — Combinação Final | APROVADO após review de lifecycle | FAIL-CLOSED CORRETO | `P-A0060-01`: heavy/finalizer; `P-A0060-02`: Stamina ledger; `P-A0060-03`: gate80 `combat:fist`; `P-A0060-04`: lifecycle cooldown/reserva |
| A0061 | Força Aplicada | APROVADO + boundary | CÓDIGO PRESENTE / IMPLEMENTAÇÃO PARCIAL | `P-A0061-01`: remover classifiers melee por tags paralelas; capability/mapping versionado only |
| A0062 | Golpe Preciso | APROVADO + boundary | CÓDIGO PRESENTE no resolver crítico canônico | herda `P-A0061-01` para roots melee; sem segunda rolagem |
| A0063 | Impacto Crítico | APROVADO + boundary | CÓDIGO PRESENTE nos adapters críticos | herda `P-A0061-01`; aplicar somente após `canonicalCritical=true` |
| A0064 | Ritmo de Combate | APROVADO + boundary | CÓDIGO PRESENTE via `ModifyAttackSpeedEvent` onde semântico | herda `P-A0061-01`; não fabricar draw/reload/projectile speed |
| A0065 | Penetração Física | APROVADO + boundary | CÓDIGO PRESENTE em melee e projectile físico | herda `P-A0061-01`; provider armor-ignore/sunder permanece provider-owned |
| A0066 | Impacto Marcial | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL: melee Impact presente; projectile corretamente fail-closed | herda `P-A0061-01`; sem equivalência por knockback/stun |
| A0067 | Firmeza Ofensiva | APROVADO EM FAIL-CLOSED | NÃO CONFORME: coeficiente existe, binding/availability não | `P-A0067-01`: safe offensive interruption/stun-armor receipt ou nó indisponível/não comprável |
| A0068 | Dano contra Feridos | APROVADO + boundary | CÓDIGO PRESENTE | herda `P-A0061-01` para melee; pre-impact HP deve ser <35% estrito |
| A0069 | Dano contra Íntegros | APROVADO + boundary | CÓDIGO PRESENTE | herda `P-A0061-01` para melee; pre-impact HP deve ser >85% estrito |
| A0070 | Dano contra Chefes | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL: vanilla/Cataclysm/Apothic cobertos | `P-A0070-01`: `enshrouded:shroud_lich`; `P-A0070-02`: inventário boss provider-present da modlist; herda `P-A0061-01` |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Unavailable-node invariant:** provider/binding obrigatório ausente ou incompatível gera estado explícito de nó indisponível/não comprável; nunca silent no-op purchase, rank fantasma ou gasto de pontos sem efeito.
- **Versões auditadas A0001–A0020:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance:** hits indiretos, companions, hazards ou fontes não hostis não podem herdar autoria MARTIAL do jogador.
- **Launch provenance CROSSBOW:** `owner + CrossbowItem` não bastam; A0051–A0054 exigem launch receipt CROSSBOW confirmado e projectile/root correlacionado. Projectile derivado/reemitido sem receipt fica fail-closed.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. Famílias sem producer provider-native comprovado usam discovery/milestones finitos e deduplicados.
- **Mastery BOW/CROSSBOW:** fonte canônica do lote é `epicfight:bow` / `epicfight:crossbow`, conforme contratos/modelo/projectile runtime. `combat:bow` / `combat:crossbow` divergentes não são segunda ledger válida.
- **Mastery FIST:** fonte canônica do ramo A0055–A0060 é `combat:fist`, +10 por tipo hostil inédito; 6 tipos→60 e 8→80. Producer paralelo deve ser reconciliado/suprimido.
- **HAMMER/MACE/SCYTHE:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada.
- **FIST:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada. Mãos vazias só entram em FIST por mapping explícito/versionado.
- **BOW/CROSSBOW:** vanilla é classificado por `BowItem`/`CrossbowItem`; externos exigem provider-native/mapping explícito. Mastery 60 = 6 tipos hostis inéditos; Mastery 80 = 8 tipos quando o contrato terminal exigir.
- **Availability em Bestas:** enquanto A0050 estiver indisponível/não comprável, A0052, A0053 e A0054 ficam estruturalmente indisponíveis; fallback não pode bypassar dependência.
- **Root outcome CROSSBOW:** Multishot compartilha uma única root action; projéteis irmãos produzem no máximo um success/failure e uma perda de Cadência por disparo.
- **Reservation→commit em lançamento:** Cadência/janela de A0053/A0054 só são consumidas quando o projectile/root correlacionado é realmente criado; cancelamento tardio/ausência de spawn faz rollback.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real ocorre no commit confirmado; cancelamento/dano zero não deixa estado fantasma.
- **Lifecycle:** estados por alvo/ator precisam cleanup bounded em remoção, logout, dimensão, respawn, shutdown, rank loss, respec e rules reload pertinentes.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não recebe dano/crítico/penetração/Impact/anti-boss MARTIAL A0061–A0070.
- **Enshrouded:** Shroud/Exposure/Flame/Story/MagicResistance não classificam ação MARTIAL; `enshrouded:shroud_lich` é uma identidade de boss física explícita para A0070, não uma regra de namespace.
- **Volcanoes:** hazards/geologia/pressão/lava/gases/calor permanecem fora do pipeline MARTIAL; o delta de hardening/RNS não cria root de jogador.
- **Mobstein:** companion damage/projectiles/kills são provider-owned; Witherstein só entra em A0070 após identidade/registry prova explícita, nunca por nome/aparência.
- **Stage 11 itemização:** authority própria de identidade/rolls; A0061–A0070 não leem rolled modifiers para fabricar dano/crítico/cadência/penetração/Impact.
- **Simply Swords:** Implicits, Runic Powers, Awakening, Uniques, sockets/gems e traits continuam provider-owned; derived hits não viram novo root MARTIAL. `P-SIMPLY-A0001-50-01` permanece acceptance provider-present.
- **A0067 availability:** sem binding seguro de janela ofensiva + resistência à interrupção, nó indisponível/não comprável; nenhum rank no-op.
- **A0070 boss taxonomy:** classificação por EntityType/tag/marker provider-native explícito. HP, nome, bossbar, tamanho, aparência e namespace isolado são proibidos como heurística.
- **NeoVitae:** removido/ausente.

## Ciclos fechados anteriores

### A0001–A0010
- Chat 2 PR #221 mergeada; merge `d7aa65bf37bbe284cac5d92818ef0a1a23ffd14b`; implementação confirmada.
- Revalidação provider→árvore posteriormente mergeada pela PR #237.
- Chat 3 PR #244 reabriu somente a auditoria técnica pós-merge desse mesmo lote e encontrou duas pendências causais: `P-A0005-02` e `P-A0006-01`.
- Ambas foram corrigidas sem redesenho: PRE apenas prepara; POST direto/hostil/com dano efetiva os consumos irreversíveis.
- Aparo/guarda perfeita adicionais de A0006 continuam expansão condicional não bloqueante e fail-closed sem receipt público causal.

### A0001–A0020 — retroauditoria combinada / Chat 2
- PR #233 mergeada; merge/main `0087ef7e513664454b3d54cb70a9c3f24ec46e84`.
- Chat 2 PR #237 mergeada em `8b76a7cef1aa675fdd988bf694c876e751fb839d`; implementação/revalidação A0011–A0020 confirmada na `main`.
- Auditorias: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0011-A0020.md` e `audits/AUDITORIA-A0001-A0020-REVALIDACAO-IMPLEMENTACAO-CHAT2.md`.
- `P-A0017-01` continua não bloqueante e fail-closed correto.

### A0011–A0020 — Chat 3
- PR #250 reabriu somente a auditoria técnica pós-merge; nenhum redesign.
- `P-A0011-02`, `P-A0017-02`, `P-A0018-01` foram corrigidas por reservation→commit.
- `P-A0017-01` permanece aberta, não bloqueante e fail-closed.
- Auditoria: `audits/AUDITORIA-CHAT3-A0011-A0020-PENDENCIAS-TECNICAS.md`.

### A0021–A0030 — Chat 2
- Chat 1 PR #235 mergeada; merge `15cf3f75959165e0a40f4b0be8263ffae83cb097`; design fechado.
- Implementação/hardenings registrados em `audits/AUDITORIA-A0021-A0030-IMPLEMENTACAO-CHAT2.md`.
- A0028–A0030 preservam fail-closed onde providers não expõem receipts seguros.

### A0031–A0040
- Chat 1 PR #239 mergeada; merge `689d0d4f0290686cbed61056e3471a124a01101c`.
- Design fechado; review incorporou `P-A0035-02`, `P-A0036-03` e `P-A0040-01`.
- Auditoria: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0031-A0040.md`.
- Runtime não foi alterado pelo Chat 1.

## Chat 1 — lote exato A0041–A0050

**Estado:** `LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME CATALOGADOS`.

- PR de fechamento #243; arquivo canônico `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0041-A0050.md`.
- 10 perks consecutivas; runtime não alterado pelo Chat 1.
- Handoffs detalhados do lote permanecem no dossiê/auditoria e nas linhas A0041–A0050 acima.

## Chat 1 — lote exato A0051–A0060

**Estado:** `LOTE FECHADO NO DESIGN; NOVE EIXOS 10/10 REGISTRADOS; BLOCKERS RUNTIME CATALOGADOS`.

- Arquivo canônico: `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`.
- Runtime alterado neste Chat 1: nenhum.
- Handoffs CROSSBOW/FIST detalhados permanecem no dossiê/auditoria e nas linhas A0051–A0060 acima.

## Chat 1 — lote exato A0061–A0070

**Estado:** `LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME/PROVIDER CATALOGADOS`.

- **INÍCIO:** A0061.
- **FIM:** A0070.
- **Quantidade:** 10 perks consecutivas.
- **Base de escrita:** RPG Skill Tree `main@52bd7bd340e21b4020b4465214779f1d6bea072a`.
- **Registro:** GitHub apenas neste ciclo; sem novas gravações no Notion.
- **Providers/deltas auditados:** RPG Skill Tree baseline `f448aa0...→52bd7bd...`; Volcanoes `602e018...→a47bb86...`; Enshrouded `77552a3...→ffc5007...`; Black Arcana `07263ae...→b2bf5e9...`; Simply stack conforme guia atual.
- **Qualidade:** A0061/A0062/A0064 aprovadas como Ranked Passives de fundação porque são starting points concorrentes de corredores distintos; A0063/A0065/A0066/A0068/A0069/A0070 são especializações condicionais. A0067 só é adquirível quando houver effect binding real.
- **Arquivo canônico:** `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0061-A0070.md`.
- **Runtime alterado por este Chat 1:** nenhum.
- **A0071+:** não iniciado.

### Pendências destinadas ao Chat 2

1. `P-A0061-01` — remover `rpgskilltree:hammers/maces/scythes` como fallback classifier em A0061–A0080; capability/classification provider-native ou mapping versionado apenas.
2. `P-A0067-01` — integrar receipt seguro de janela ofensiva + interruption/stun-armor ou manter A0067 indisponível/não comprável sem gasto/rank fantasma.
3. `P-A0070-01` — adicionar/testar `enshrouded:shroud_lich` como boss explícito opcional.
4. `P-A0070-02` — fechar matriz de identidades de bosses dos demais providers pertinentes da modlist; sem heurísticas.
5. `P-SIMPLY-A0001-50-01` — acceptance provider-present continua necessária para provar que derived Simply não cria novo root MARTIAL.
6. Adicionar textos PT-BR A0061–A0070 ao catálogo player-facing durante implementação, sem usar texto como authority de gameplay.

O fechamento operacional deste lote exige PR, review, CI GREEN, merge e confirmação da `main`; após isso o ciclo encerra e A0071+ só pode começar mediante novo comando do usuário.
