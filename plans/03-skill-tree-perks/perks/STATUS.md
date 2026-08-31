# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0100** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

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
| A0021 | Precisão com Adagas | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #248; reauditoria Chat 3 na PR #315 sem nova pendência | nenhuma |
| A0022 | Ritmo das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #248; reauditoria Chat 3 na PR #315 preserva stagger forte, fallback geométrico, idle decay e supressão de knockback | nenhuma |
| A0023 | Ataque ao Ponto Cego | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #248; `P-A0023-01` corrigida na PR #315 com reservation→POST commit de Fluxo/cooldown | nenhuma |
| A0024 | Maestria de Adagas — Dança das Sombras | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pela PR #248; `P-A0024-01` corrigida na PR #315 com activation/first-hit commit no POST | nenhuma bloqueante |
| A0025 | Treino com Martelos I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pela PR #248; HAMMER provider-native + Mastery anti-farm; reauditoria #315 sem nova pendência | nenhuma |
| A0026 | Treino com Martelos II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #248 via attack-speed provider-native; reauditoria #315 sem nova pendência | nenhuma |
| A0027 | Precisão com Martelos | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pela PR #248 no resolver crítico canônico; reauditoria #315 sem nova pendência | nenhuma |
| A0028 | Abalo Crescente | APROVADO + boundary | IMPLEMENTAÇÃO PARCIAL VALIDADA FAIL-CLOSED pela PR #248 e preservada na reauditoria #315 | `P-A0028-01`: Epic Fight 21.17.3.1 sem receipt causal separado de guard pressure |
| A0029 | Quebra de Postura | APROVADO + boundary | NÃO CONFIRMADA; fail-closed preservado; `P-A0029-02` causal latente corrigida na PR #315 | `P-A0029-01`: Epic Fight 21.17.3.1 sem heavy receipt inequívoco |
| A0030 | Maestria de Martelos — Golpe Demolidor | APROVADO + boundary | NÃO CONFIRMADA; fail-closed preservado; `P-A0030-02` causal latente corrigida na PR #315 | `P-A0030-01`: guard-break causal attacker-side + heavy receipt ausentes |
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
| A0061 | Força Aplicada | APROVADO | CÓDIGO PRESENTE em melee Epic Fight + projectile físico canônico | `P-A0061-01/-02`: validar dedup/root e preservar Simply Swords provider-native |
| A0062 | Golpe Preciso | APROVADO | CÓDIGO PRESENTE no resolvedor crítico canônico | `P-A0062-01/-02`: provar uma única rolagem e convergência de adapters Apothic |
| A0063 | Impacto Crítico | APROVADO | CÓDIGO PRESENTE sobre crítico canônico | `P-A0063-01/-02`: uma aplicação por root; sem double multiplier Apothic/provider |
| A0064 | Ritmo de Combate | APROVADO | CÓDIGO PRESENTE em `ModifyAttackSpeedEvent` | `P-A0064-01/-02`: provider-present; moveset sem binding seguro fica fail-closed |
| A0065 | Penetração Física | APROVADO | CÓDIGO PRESENTE em Epic Fight + projectile | `P-A0065-01/-02`: backend único; não duplicar armor ignore/shred/Apothic |
| A0066 | Impacto Marcial | APROVADO | CÓDIGO PRESENTE para melee Epic Fight; projectile FAIL-CLOSED CORRETO | `P-A0066-01/-02`: validar melee e preservar ausência de Impact sintético em projectile |
| A0067 | Firmeza Ofensiva | APROVADO após correção de availability | FAIL-CLOSED CORRETO no efeito; node ainda exige disponibilidade estrutural | `P-A0067-01` BLOQUEANTE: indisponível/não comprável sem attack-window binding; `P-A0067-02/-03` hook/cleanup/testes |
| A0068 | Dano contra Feridos | APROVADO | CÓDIGO PRESENTE melee + projectile | `P-A0068-01/-02`: snapshot pré-impacto <35%, borda e dedup |
| A0069 | Dano contra Íntegros | APROVADO | CÓDIGO PRESENTE melee + projectile | `P-A0069-01/-02`: snapshot pré-impacto >85%, borda e dedup |
| A0070 | Dano contra Chefes | APROVADO após correção de cobertura | IMPLEMENTAÇÃO PARCIAL: vanilla/Cataclysm tag + Apothic; Enshrouded identity ainda sem adapter | `P-A0070-01`: `enshrouded:shroud_lich`; `P-A0070-02`: demais bosses fail-closed até IDs; `P-A0070-03/-04` dedup/fases |
| A0071 | Dano contra Elites | APROVADO | CÓDIGO PRESENTE via classificador canônico | `P-A0071-01/-02`: Apothic provider-present + BOSS>ELITE; externos somente com identidade exata |
| A0072 | Retaliação | APROVADO após availability | EFEITO PRESENTE, MAS ESTRUTURALMENTE INDISPONÍVEL | `P-A0072-01`: A0067 indisponível → A0072 indisponível/não comprável; testar refresh/dedup |
| A0073 | Janela de Execução | APROVADO após reservation→commit | NÃO CONFORME: PRE arma/consome cedo demais | `P-A0073-01`: POST commit/rollback; `P-A0073-02`: Stamina receipt; lifecycle/dedup |
| A0074 | Primeiro Sangue | APROVADO após reservation→commit | NÃO CONFORME: PRE atualiza/consome cedo demais | `P-A0074-01`: POST commit/rollback; lifecycle e bordas de opener/cooldown |
| A0075 | Ritmo Sustentado | APROVADO EM FAIL-CLOSED | CORE PRESENTE; NODE INDISPONÍVEL | `P-A0075-01`: unavailable; `P-A0075-02`: Cold Sweat metabolic boundary; all-or-nothing provider binding |
| A0076 | Postura Agressiva | APROVADO após boundary de ativação | STATE PURO PRESENTE; SEM INPUT/PAYLOAD | `P-A0076-01`: controle remapeável + payload serverbound + availability; transição/lifecycle |
| A0077 | Postura Cautelosa | APROVADO após availability | ESTRUTURALMENTE INDISPONÍVEL | `P-A0077-01`: herda A0067 e depende do stance binding de A0076 |
| A0078 | Ataque em Movimento | APROVADO | CÓDIGO PRESENTE no sprint vanilla; ParCool extra fail-closed | `P-A0078-01/-03`: forced movement/bridge PP; ParCool só por receipt real |
| A0079 | Ataque Estacionário | APROVADO após hardening | IMPLEMENTAÇÃO PARCIAL: detector presente, forced invalidation incompleta | `P-A0079-01`: teleport/mount/vehicle/contraption/belt/forced receipts; bridge PP/testes |
| A0080 | Golpe de Oportunidade | APROVADO EM FAIL-CLOSED | NODE INDISPONÍVEL; SEM DODGE-SUCCESS RECEIPT | `P-A0080-01`: unavailable; `P-A0080-02`: avoidedAttack receipt/dedup; `P-A0080-03`: POST commit |
| A0081 | Recuperação de Combate | APROVADO EM FAIL-CLOSED após availability transitiva | CORE PRESENTE; NODE DEVE SER ESTRUTURALMENTE INDISPONÍVEL | `P-A0081-01` BLOQUEANTE: herdar availability A0075 no purchase/gate; `P-A0081-02`: lifecycle da reserva |
| A0082 | Vampirismo de Arma | APROVADO após boundary de correlação nativa | CÓDIGO PRESENTE para raízes físicas comprovadas; Ignitium exige fail-closed específico | `P-A0082-01`: correlacionar lifesteal nativo Ignitium no mesmo root; `P-A0082-02`: cap/dedup/provenance |
| A0083 | Vampirismo Mágico | APROVADO EM FAIL-CLOSED | RESOLVER PRESENTE; NODE INDISPONÍVEL SEM PRODUCER `DIRECT_MAGIC` | `P-A0083-01` BLOQUEANTE: availability + adapter causal Iron's/Ars |
| A0084 | Sifão Elemental | APROVADO EM FAIL-CLOSED | RESOLVER PRESENTE; NODE INDISPONÍVEL SEM PRODUCER ELEMENTAL | `P-A0084-01` BLOQUEANTE: availability + element/root adapter provider-native |
| A0085 | Sifão de Dano Periódico | APROVADO EM FAIL-CLOSED | RESOLVER PRESENTE; NODE INDISPONÍVEL SEM LEDGER APPLICATION/PULSE | `P-A0085-01` BLOQUEANTE: owner/application/pulse causal + availability |
| A0086 | Vampirismo Universal | APROVADO após availability transitiva | CORE PRESENTE; ESTRUTURALMENTE INDISPONÍVEL | `P-A0086-01` BLOQUEANTE: A0083/A0085 indisponíveis → A0086 indisponível; não sintetizar classifiers |
| A0087 | Sede de Sangue | APROVADO EM FAIL-CLOSED após BodyProvider/healing boundary | CORE PRESENTE com `BodyProvider(null)`; NODE INDISPONÍVEL | `P-A0087-01` BLOQUEANTE: BodyProvider Cold Sweat+exhaustion + availability A0075/A0081; `P-A0087-02/-03`: healing-received/Thirst |
| A0088 | Constituição | APROVADO | CÓDIGO PRESENTE data-driven + `preserveHealthRatio` ligado ao refresh | `P-A0088-01`: regressões rank/respec/reload/no-free-heal/modifier uniqueness |
| A0089 | Couro Endurecido | APROVADO | CÓDIGO PRESENTE no `Attributes.ARMOR` relativo | `P-A0089-01`: regressões zero-base/modificador relativo/não confundir STUN_ARMOR |
| A0090 | Têmpera | APROVADO | CÓDIGO PRESENTE no `Attributes.ARMOR_TOUGHNESS` relativo | `P-A0090-01`: regressões zero-base/modificador relativo/fontes que ignoram armadura |
| A0091 | Base Firme | APROVADO | CÓDIGO PREPARATÓRIO PRESENTE no modifier data-driven `KNOCKBACK_RESISTANCE` | `P-A0091-01`: validar cap +0,15, idempotência, rank loss/respec/remove e separação de STUN_ARMOR/IMPACT |
| A0092 | Resistência Física | APROVADO após materialização do classifier no design | FÓRMULA/EVENT BRIDGE PRESENTES; TAG FÍSICA AINDA NÃO MATERIALIZADA | `P-A0092-01/-02`: criar `rpgskilltree:physical`, seed conservador, exclusões, ordem/dedup do pipeline |
| A0093 | Guarda Econômica | APROVADO EM FAIL-CLOSED | FÓRMULA PURA PRESENTE; SEM HOOK CAUSAL DE CUSTO; NODE DEVE SER INDISPONÍVEL | `P-A0093-01` BLOQUEANTE: purchase `requirementsSatisfied=false` enquanto binding de guard stamina faltar |
| A0094 | Recuperação de Guarda | APROVADO EM FAIL-CLOSED | FÓRMULA PURA PRESENTE; SEM GUARD_BREAK+RECOVERY RECEIPT; NODE DEVE SER INDISPONÍVEL | `P-A0094-01` BLOQUEANTE: availability transitiva A0093 + recovery hook real; sem heurística |
| A0095 | Tenacidade | APROVADO após correção de dependência/provider | CÓDIGO PREPARATÓRIO DIVERGE: A0094 stale + reducer genérico; binding STUN_ARMOR ausente | `P-A0095-01/-02`: remover A0094 stale; ADD_FLAT `epicfight:stun_armor` +0,25/rank, versionado/idempotente |
| A0096 | Último Fôlego | APROVADO após classifier compartilhado | FÓRMULA PRE-IMPACT PRESENTE; DEPENDE DA TAG A0092 | `P-A0096-01`: reutilizar somente classifier A0092; composição multiplicativa e snapshot pré-impacto |
| A0097 | Primeira Defesa | APROVADO após correção causal | NÃO CONFORME: runtime consome PRE e classifier hostil usa `Enemy || Player` | `P-A0097-01/-02`: reservation→POST commit/rollback + atacante causal `LivingEntity` não aliado |
| A0098 | Defesa em Movimento | APROVADO | CÓDIGO PREPARATÓRIO PRESENTE no sprint vanilla | `P-A0098-01/-02`: forced/passive exclusions, ParCool só por state real, bridge PP sem tocar Stage04.02 |
| A0099 | Defesa Estacionária | APROVADO com dependência transversal | IMPLEMENTAÇÃO PARCIAL: serviço único existe; forced invalidation incompleta | `P-A0099-01/-02`: fechar/reutilizar `P-A0079-02`, classifier hostil compartilhado e bridge PP; detector paralelo proibido |
| A0100 | Anti-Crítico | APROVADO EM FAIL-CLOSED | FÓRMULA PURA PRESENTE; SEM INCOMING CRITICAL DECOMPOSITION; NODE DEVE SER INDISPONÍVEL | `P-A0100-01/-02` BLOQUEANTE: purchase fail-closed e nenhuma heurística; futuro adapter exige crit+base+extra causal |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Unavailable-node invariant:** provider/binding obrigatório ausente ou incompatível gera estado explícito de nó indisponível/não comprável; nunca silent no-op purchase, rank fantasma ou gasto de pontos sem efeito.
- **Versões auditadas A0001–A0020:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance:** hits indiretos, companions, hazards ou fontes não hostis não podem herdar autoria MARTIAL do jogador.
- **Launch provenance CROSSBOW:** `owner + CrossbowItem` não bastam; A0051–A0054 exigem launch receipt CROSSBOW confirmado e projectile/root correlacionado. Projectile derivado/reemitido sem receipt fica fail-closed.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. Famílias sem producer provider-native comprovado usam discovery/milestones finitos e deduplicados.
- **Mastery BOW/CROSSBOW:** fonte canônica do lote é `epicfight:bow` / `epicfight:crossbow`, conforme Notion + `CombatPerkTreeModel` + projectile runtime. `combat:bow` / `combat:crossbow` em `tree_architecture/combat.json` são divergência runtime/catalog a corrigir, não uma segunda ledger válida.
- **Mastery FIST:** fonte canônica do ramo A0055–A0060 é `combat:fist`, +10 por tipo hostil inédito; 6 tipos→60 e 8→80. O producer genérico `epicfight:fist` deve ser reconciliado/suprimido; `tree_architecture/combat.json` precisa publicar `combat_fist` antes de o gate ser considerado alinhado.
- **HAMMER/MACE/SCYTHE:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada.
- **FIST:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada. Mãos vazias só entram em FIST por mapping explícito/versionado.
- **BOW/CROSSBOW:** vanilla é classificado por `BowItem`/`CrossbowItem`; externos exigem provider-native/mapping explícito. Mastery 60 = 6 tipos hostis inéditos; Mastery 80 = 8 tipos quando o contrato terminal exigir.
- **Availability em Bestas:** enquanto A0050 estiver indisponível/não comprável, A0052, A0053 e A0054 ficam estruturalmente indisponíveis; fallback não pode bypassar dependência. A ausência de `P-A0049-01` também bloqueia a alcançabilidade da cadeia por Mastery legítima.
- **Root outcome CROSSBOW:** Multishot compartilha uma única root action; projéteis irmãos produzem no máximo um success/failure e uma perda de Cadência por disparo. Success do root bloqueia failures tardios de irmãos.
- **Reservation→commit em lançamento:** Cadência/janela de A0053/A0054 só são consumidas quando o projectile/root correlacionado é realmente criado; cancelamento tardio ou ausência de spawn faz rollback.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real ocorre no commit pós-hit confirmado; cancelamento/dano zero não deixa estado fantasma. Para ações de lançamento A0053/A0054, o commit específico ocorre somente após criação confirmada do projectile/root correlacionado.
- **Lifecycle:** estados por alvo precisam cleanup bounded quando alvo morre, é removido, descarrega ou desaparece sem evento terminal equivalente. Estados por ator precisam cleanup bounded em logout/dimensão/respawn/shutdown e reconciliação em rank loss, respec e rules reload que invalide perk/pré-requisito.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **A0061–A0070:** dano físico direto, crítico, ritmo, penetração e Impact usam boundaries canônicos e identidades distintas; nenhuma contribuição pode ser aplicada duas vezes por bridges paralelas.
- **A0067:** sem lifetime provider-native seguro da janela ofensiva, o node é indisponível/não comprável; matemática pura não é binding.
- **A0070/A0071:** BOSS > ELITE > HOSTILE; classificações são explícitas e nunca acumulam no mesmo root. Heurística visual/estatística é proibida.
- **A0072/A0077:** availability transitiva é obrigatória; A0067 indisponível torna ambos indisponíveis.
- **A0073/A0074/A0080:** estados consumíveis usam reservation→commit; PRE pode reservar, POST com dano efetivo >0 commita, cancelamento/zero faz rollback.
- **A0075:** Stamina regen, Cold Sweat thermal contribution e exhaustion formam contrato all-or-nothing; ausência de qualquer binding torna o node indisponível/não comprável. Thirst é eixo separado.
- **A0076/A0077:** `MARTIAL_STANCE` é RPG-owned; cliente envia intenção por controle remapeável/payload, servidor valida e efetiva. Sem binding de ativação, node de postura não é comprável.
- **A0078/A0079:** movimento autopropelido e estacionário são estados distintos; mount/vehicle/contraption/belt/knockback/forced movement não podem satisfazer gates por simples delta de posição.
- **A0080:** dodge executado não prova dodge-success; exige receipt de ataque hostil efetivamente evitado e dedup cross-provider.
- **A0081:** availability é transitiva de A0075; a reserva de Recuperação é separada do `SustainResolver` e deve ser invalidada em rank loss/respec/rules reload/perda do pré-requisito.
- **A0082–A0086:** uma única resolução de sustain por root/pulso elegível; teto móvel compartilhado de 3% da vida máxima/20 ticks; maior coeficiente elegível vence; cura nativa exata é contabilizada primeiro; correlação ambígua falha fechado.
- **A0083/A0084/A0085:** fórmula/resolvedor sem producer provider-native não é binding; sem ao menos uma rota causal segura o node é indisponível/não comprável.
- **A0086:** keystone não sintetiza classificadores nem bypassa predecessors indisponíveis; availability é transitiva.
- **A0087:** benefício existe somente com BodyProvider capaz de manter calor metabólico Cold Sweat + exhaustion vanilla na mesma atividade; hidratação é eixo opcional separado por receipt causal; +8% healing received aplica uma única vez no pipeline geral de curas elegíveis, não apenas no sustain.
- **A0088–A0090:** owner canônico é Minecraft/NeoForge; A0088 preserva proporção de vida ao recalcular max health; A0089/A0090 são bônus relativos sobre Armor/Toughness existentes e não criam STUN_ARMOR, resistência física paralela ou proteção quando a base é zero.
- **A0091:** Knockback Resistance é eixo vanilla distinto de Epic Fight Stun Armor/Impact; teto próprio +0,15.
- **A0092/A0096:** classificação física pertence à tag canônica `rpgskilltree:physical`; A0096 reutiliza exatamente a mesma classificação. Composição é multiplicativa `damage × (1−A0092) × (1−A0096)`, uma vez cada.
- **A0093/A0094/A0100:** fórmula pura ou constante `FAIL_CLOSED_*` não basta; enquanto hook obrigatório faltar, purchase deve falhar antes do gasto via requirements server-authoritative.
- **A0095:** Tenacidade é modifier `ADD_FLAT` de `epicfight:stun_armor`, +0,25/rank; não depende de A0094 e não cria reducer linear paralelo de interrupção.
- **A0097:** Primeira Defesa usa reservation→commit: PRE reserva/aplica; somente POST com dano hostil efetivo >0 consome e reinicia 200 ticks. Hostilidade é atacante causal `LivingEntity` não aliado, não `Enemy`-only.
- **A0098/A0099:** PP de node-bridge não é custo de confluência/classe. Stage 04.02/`ProgressionService` conserva authority de provenance/cobrança/refund; bridge PP pode ser whitelisted para no máximo um threshold puro.
- **A0099:** compartilha exclusivamente o `StationaryStateService` de A0079; `P-A0079-02`/forced invalidation é dependência transversal e detector paralelo é proibido.
- **Sustain exclusions:** `ARCANE_BACKLASH`, `BLOOD_MAGIC_COST`, dano ambiental/Volcanoes/Enshrouded, máquina, summon/companion sem autoria direta e efeitos recursivos não são dano ofensivo do jogador para A0082–A0087.
- **Ignitium:** lifesteal nativo permanece provider-owned; sem correlação exata da cura final ao mesmo root, a fonte específica falha fechado e é proibido usar `NativeCorrelation.NONE`; armas comuns comprovadas continuam elegíveis.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não crita/proca/concede Mastery/Focus/Marca/eligible_kill; também não abre Retaliação por self-cost nem vira ação física/mágica ofensiva elegível.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story/MagicResistance não classificam movimento, stance, dodge, Stamina ou sustain ofensivo destas perks; bridge Shroud Lich permanece somente A0070 read-only.
- **Volcanoes:** hazards/geologia/prospecção permanecem fora dos pipelines MARTIAL/sustain; o delta mais recente é release/hardening e não cria capacidade de perk.
- **Mobstein 5.4.4:** companions não geram autoria física do dono; Witherstein não entra em A0070/A0071 por nome/tema sem identity adapter real.
- **Stage 11.01 itemização:** authority própria de identidade/rolls; projeções de efeitos sem hook comprovado continuam `SEM HOOK SEGURO`.
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

### A0021–A0030 — Chat 3
- PR #315 reabre somente a auditoria técnica pós-merge do lote já implementado pela #248; nenhum redesign foi feito e A0031+ permaneceu fora do escopo.
- Pendências causais encontradas: `P-A0023-01`, `P-A0024-01`, `P-A0029-02`, `P-A0030-02`.
- As quatro foram corrigidas por reservation→commit: PRE calcula/reserva; POST direto/hostil/com dano efetiva os consumos irreversíveis; cancelamento/zero damage faz rollback.
- A0023 só debita 2 Fluxo e inicia cooldown por alvo no POST confirmado; commit ocorre antes do ganho A0022.
- A0024 só consome 4 Fluxo, ativa Dança e consome o primeiro hit especial no POST confirmado; o benefício de movimento só existe após ativação commitada; commit ocorre antes do ganho A0022.
- A0029 ficou preparado causalmente para um futuro heavy receipt: 3 Abalo são reservados no PRE e consumidos apenas no POST; `P-A0029-01` permanece aberta e o adapter real continua `heavyConfirmed=false`.
- A0030 ficou preparado causalmente para futuros receipts: Janela Demolidora é reservada no PRE e consumida apenas no POST; `P-A0030-01` permanece aberta.
- `P-A0028-01`, `P-A0029-01` e `P-A0030-01` continuam explicitamente fail-closed; nenhuma heurística provider substituta foi criada.
- TDD RED: commit `53f469c3f8943b1b011a306e8b6a497256d3a778`, RPG Skill Tree CI #2656 / run `33393390999`, com Core verde e JUnit vermelho após introdução dos testes causais.
- GREEN pré-fechamento: HEAD `e32d72bb1280b667c12057bfe1f17cdfbfad0b57`, RPG Skill Tree CI #2691 / run `33399375858` SUCCESS, incluindo JUnit 5, NeoForge GameTests, validações, build, JAR e dedicated-server smoke; auxiliares verdes.
- Auditoria Chat 3: `audits/AUDITORIA-CHAT3-A0021-A0030-PENDENCIAS-TECNICAS.md`.
- O fechamento operacional exige CI verde do HEAD documental/status final, merge da PR #315 e confirmação fresca da `main`; depois disso o Chat 3 deve PARAR.

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

O estado de CI/merge da PR de fechamento é confirmado no GitHub; este arquivo registra o design canônico e as pendências técnicas. Após o merge da PR #243, o ciclo A0041–A0050 está operacionalmente encerrado e o próximo lote só pode começar mediante novo comando do usuário.

## Chat 1 — lote exato A0051–A0060

**Estado:** `LOTE FECHADO NO DESIGN; NOVE EIXOS 10/10 REGISTRADOS; BLOCKERS RUNTIME CATALOGADOS`.

- **INÍCIO:** A0051.
- **FIM:** A0060.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura:** RPG Skill Tree `main@5e9dd777722014596641cb77d7be5c51df410e4e`.
- **Base efetiva original da branch:** `main@2e6cf57d5c12630d55280d1c4ff0177f536dce96`; depois a branch foi reconciliada com a `main@1dfcbb3b567e213e6feed4cf254bba2989685454`, preservando integralmente os trabalhos concorrentes de Chat 2/Chat 3 e narrativa.
- **Providers rechecados:** Volcanoes `c26e97c136b543f1fa0ef2ebb12044d10d8af816`; Enshrouded `f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`; Black Arcana `73c14ce55ff918bb8a81daeb99a352607ef11064`; Mobstein 5.4.4 conforme guia obrigatório.
- **Matriz de delta obrigatória:** registrada por capacidade na auditoria; Volcanoes `7839db6...→c26e97c...` decomposto em ownership marker, persistência NBT/restart, foreign replacement safety e side-ledger recovery, todos `NÃO DEVE SER INTEGRADO` ao lote CROSSBOW/FIST.
- **Nove eixos:** registrados individualmente nos 10 dossiês; 10/10 perks possuem resultado/evidência para Dependências/Gates, Integração Global, Qualidade/Identidade, Topologia, Especializações, PT-BR, Notion, NeoVitae e Cobertura de Providers.
- **Notion fetch fresco:** 10/10.
- **Notion alterado no fechamento inicial:** A0051, A0052, A0053, A0054, A0055, A0057, A0058.
- **Re-fetch inicial:** 7/7 PASS.
- **Primeiro review PR #249:** quatro findings válidos incorporados — `P-A0049-01`; reservation→commit A0053/A0054; Multishot/root outcome A0052; matriz per-capability do Volcanoes.
- **Notion alterado no primeiro review:** A0052, A0053, A0054; re-fetch 3/3 PASS.
- **Segundo review PR #249:** três findings válidos incorporados — launch provenance CROSSBOW; lifecycle de rank loss/respec/rules reload; nove eixos individualizados por perk.
- **Notion alterado no segundo review:** A0051, A0052, A0053, A0054, A0058, A0060; re-fetch 6/6 PASS.
- **Total de páginas Notion distintas mutadas no ciclo:** 8/10 — A0051, A0052, A0053, A0054, A0055, A0057, A0058, A0060.
- **Sem mutação funcional:** A0056 e A0059.
- **Arquivo canônico do lote:** `audits/AUDITORIA-RETROATIVA-PROVIDERS-A0051-A0060.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0061+:** não iniciado naquele ciclo.

### Pendências destinadas ao Chat 2

1. `P-A0049-01` — producer finite-discovery `epicfight:crossbow`: +10 por tipo hostil inédito; 6→60, 8→80.
2. `P-A0049-02` — reconciliar `combat:crossbow` vs `epicfight:crossbow`; uma ledger.
3. `P-A0051-01` — launch receipt CROSSBOW real antes do crítico; projectile derivado sem correlação fail-closed.
4. `P-A0052-01` — availability A0050→A0052.
5. `P-A0052-02` — mesma identidade causal de besta entre hit e reload.
6. `P-A0052-03` — regressões de miss/cancel/troca/reload/estado externo/dedup.
7. `P-A0052-04` — Multishot dedup por `rootActionId`.
8. `P-A0052-05` — hit receipt somente de projectile correlacionado a launch CROSSBOW.
9. `P-A0052-06` — limpar Cadência/receipts/root outcomes em rank loss/respec/rules reload.
10. `P-A0053-01` — availability A0052→A0053.
11. `P-A0053-02` — reservation→commit/rollback das 2 Cadências até projectile/root confirmado.
12. `P-A0053-03` — launch provenance real da ação especial.
13. `P-A0053-04` — descartar reservas em rank loss/respec/rules reload.
14. `P-A0054-01` — consumir Cadência no disparo, não ao armar janela.
15. `P-A0054-02` — availability A0050/A0052/A0053→A0054.
16. `P-A0054-03` — uma ledger CROSSBOW `epicfight:crossbow`.
17. `P-A0054-04` — reservation→commit/rollback da janela até projectile/root confirmado.
18. `P-A0054-05` — launch provenance CROSSBOW no disparo consumidor.
19. `P-A0054-06` — limpar janela/reservas do capstone em rank loss/respec/rules reload; Cadência por A0052.
20. `P-A0055-01` — producer `combat:fist` por discovery finita; 6→60, 8→80; suprimir/migrar `epicfight:fist` paralelo.
21. `P-A0055-02` — publicar/reconciliar `combat_fist` no architecture catalog.
22. `P-A0055-03` — teste architecture↔model↔Notion↔producer + provider-present/absent.
23. `P-A0058-01` — reset da Sequência por receipt provider-native de heavy impact.
24. `P-A0058-02` — body modulation opcional somente por hunger/exhaustion real.
25. `P-A0058-03` — limpar Sequência/janela em rank loss/respec/rules reload.
26. `P-A0059-01` — heavy/finalizer receipt para Quebra de Ritmo.
27. `P-A0059-02` — guard-break receipt + −8% movement por 2 s.
28. `P-A0059-03` — limpar reserva/estado próprio em rank loss/respec/rules reload; Sequência por A0058.
29. `P-A0060-01` — heavy/finalizer receipt para Combinação Final.
30. `P-A0060-02` — Stamina refund somente por ledger causal pós-consumo das cinco ações.
31. `P-A0060-03` — gate80 usa exclusivamente `combat:fist`.
32. `P-A0060-04` — limpar cooldown/reserva do capstone em rank loss/respec/rules reload; Sequência por A0058.
33. `P-A0051-60-TEST-01` — GameTest/harness CROSSBOW/FIST, availability, Mastery, same-weapon correlation, projectile derivado sem launch receipt, Multishot root outcome, launch cancellation/rollback, rank loss/respec/rules reload, heavy/finalizer fail-closed, dedup, lifecycle, multiplayer e dedicated server.

O fechamento operacional deste lote exige PR, review, CI GREEN, merge e confirmação da `main`; após isso o ciclo encerra e A0061+ só pode começar mediante novo comando do usuário.

## Chat 1 — lote exato A0061–A0070

**Estado:** `LOTE FECHADO NO DESIGN; PR #298 MERGEADA; CI GREEN; MAIN CONFIRMADA`.

- **PR de fechamento:** #298 (`docs(perks): close Chat 1 audit A0061-A0070`).
- **Merge/main:** `4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a`.
- **CI:** RPG Skill Tree CI #2521 e workflows auxiliares verdes, incluindo NeoForge GameTests, build, JAR e dedicated-server smoke.
- **INÍCIO:** A0061.
- **FIM:** A0070.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura/fresh gameplay:** RPG Skill Tree `main@6ed628864199e74af23e6234d126959829f3c968`.
- **Gate de delta próprio:** Volcanoes `a47bb868de9b4846d8ae9afb94374f9672ab381e`; Enshrouded `391ea82203d30cb392a3397f92e2a3cbe7fb6128`; Black Arcana `526d8196087c863e9df64051d5d39d88c3050856`.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0067 e A0070.
- **Re-fetch pós-escrita:** 2/2 PASS em 2026-08-31.
- **Dossiês criados:** 10/10.
- **Nove eixos / 18 critérios:** PASS no design; fail-closed explícito onde runtime/API não prova o binding.
- **A0067:** sem attack-window lifetime seguro no Epic Fight 21.17.3.1 atual, o node deve ser indisponível/não comprável; matemática sem binding não habilita aquisição.
- **A0070:** `enshrouded:shroud_lich` é bridge read-only comprovada; Mowzie/Legendary Monsters/Born in Chaos/Mobstein permanecem fail-closed até registry ID/adapter exato.
- **Arquivo canônico do lote:** `audits/AUDITORIA-A0061-A0070.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0071+:** não iniciado naquele ciclo.

### Pendências destinadas ao Chat 2

1. `P-A0061-01/-02` — dedup/root da contribuição física universal e Simply Swords provider-native.
2. `P-A0062-01/-02` — uma única resolução crítica; adapters Apothic convergem no mesmo resolver.
3. `P-A0063-01/-02` — uma aplicação de critical damage por root, sem double multiplier.
4. `P-A0064-01/-02` — provider-present attack speed; movesets sem equivalência ficam fail-closed.
5. `P-A0065-01/-02` — backend único de penetração; não duplicar armor ignore/pierce/shred.
6. `P-A0066-01/-02` — Impact melee provider-present; projectile sem receipt continua fail-closed.
7. `P-A0067-01` — **BLOQUEANTE:** unavailable-node invariant no purchase/gate.
8. `P-A0067-02/-03` — lifetime/cleanup futuro e testes negativos de no-op purchase/STUN_ARMOR permanente.
9. `P-A0068-01/-02` — snapshot pré-impacto <35%, borda e dedup.
10. `P-A0069-01/-02` — snapshot pré-impacto >85%, borda e dedup.
11. `P-A0070-01` — exact adapter/tag `enshrouded:shroud_lich`, read-only.
12. `P-A0070-02` — Mowzie/Legendary/Born in Chaos/Mobstein fail-closed até IDs comprovados.
13. `P-A0070-03/-04` — BOSS > ELITE e preservação de fases/imunidades provider-native.

O lote A0061–A0070 está operacionalmente encerrado após a PR #298; A0071–A0080 só foi iniciado após novo comando do usuário.

## Chat 1 — lote exato A0071–A0080

**Estado:** `LOTE FECHADO NO DESIGN; PR #302 MERGEADA; CI GREEN; MAIN CONFIRMADA; BLOCKERS RUNTIME CATALOGADOS`.

- **PR de fechamento:** #302 (`docs(perks): close Chat 1 audit A0071-A0080`).
- **Merge/main:** `616a0dd36b943562ea64fa354a1a2fc49b09c77b`.
- **CI:** RPG Skill Tree CI #2543 / run `33363516499` GREEN no head `289e31bae45b4586ce3c0f44b8445d6dd6987ea2`, incluindo Core, JUnit 5, NeoForge GameTests, validators, build, JAR e dedicated-server smoke; workflows auxiliares também concluídos sem falha.
- **INÍCIO:** A0071.
- **FIM:** A0080.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura:** RPG Skill Tree `main@4cde1cf26dc1b4bb374f782b348ec3a2c3c5702a`.
- **Gate de delta:** RPG `4cde1cf...` (somente docs desde baseline); Volcanoes `bbb273d61984e2c9bb84e8f8a56668ae7e315532` (hardening proveniência/licenças); Enshrouded `391ea822...` sem delta; Black Arcana `526d819...` sem delta.
- **Delta canônico do ciclo:** `guides/projects/13-capability-delta-a0071-a0080.md`.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0072, A0073, A0074, A0075, A0076, A0077, A0079, A0080.
- **Re-fetch pós-escrita:** 8/8 PASS em 2026-08-31.
- **Sem mutação funcional:** A0071, A0078.
- **Dossiês criados:** 10/10.
- **Nove eixos / 18 critérios:** PASS no design.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0081+:** não iniciado naquele ciclo.

### Pendências destinadas ao Chat 2

1. `P-A0071-01/-02` — validar Apothic ELITE e BOSS > ELITE; providers externos somente por identidade exata.
2. `P-A0072-01` — availability transitiva A0067→A0072; refresh/dedup/anti-recursion.
3. `P-A0073-01` — opener/consumer/cooldown por reservation→POST commit; `P-A0073-02` Stamina receipt; lifecycle/dedup.
4. `P-A0074-01` — last-attack/opener/consumer/cooldown por reservation→POST commit; lifecycle/bordas.
5. `P-A0075-01` — unavailable-node invariant; `P-A0075-02` boundary Cold Sweat thermal; contrato all-or-nothing com STAMINA_REGEN/exhaustion.
6. `P-A0076-01` — controle `Alternar Postura Marcial` + payload serverbound + availability; atomic transition/lifecycle.
7. `P-A0077-01` — availability A0067 + stance binding.
8. `P-A0078-01/-03` — sprint/forced movement e bridge PP; ParCool apenas por receipt real.
9. `P-A0079-01` — propagar forced transition/invalidation para teleport, mount/vehicle, contraption/belt e deslocamentos provider-identificados.
10. `P-A0080-01` — unavailable até receipt de ataque hostil efetivamente evitado; `P-A0080-02` dedup Epic Fight/ParCool; `P-A0080-03` consumption reservation→POST commit.
11. `P-A0071-80-TEST-01` — GameTest/harness transversal de classification, availability, root dedup, POST commit/rollback, stance networking, thermal all-or-nothing, movement/stationary, dodge-success, lifecycle, multiplayer e dedicated server.

O lote A0071–A0080 está operacionalmente encerrado após a PR #302, CI GREEN e confirmação da `main@616a0dd36b943562ea64fa354a1a2fc49b09c77b`. O Chat 1 deve PARAR aqui; A0081–A0090 só pode começar mediante novo comando do usuário.

## Chat 1 — lote exato A0081–A0090

**Estado:** `LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME CATALOGADOS; PR/CI/MERGE EM FECHAMENTO OPERACIONAL`.

- **INÍCIO:** A0081.
- **FIM:** A0090.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura do ciclo:** RPG Skill Tree `main@877120acf4f20a693e971282e8fca35bef72c6e7`.
- **Fresh gameplay/runtime auditado:** `main@d20e7d666b627615f4af26dffb7c794b9a0b0fbd`.
- **Freshness final pré-PR:** RPG Skill Tree `main@bc8b3d571b1a3cc85a21b7b206543a47c9a8eab4`; avanços concorrentes #300 (corpus/teste TFC do Compêndio) e #306 (narrative continuity auditor) foram classificados `SEM DELTA DE CAPABILITY PARA O LOTE`.
- **Gate de delta próprio promovido:** Volcanoes `eaddc3232dfc600780769f4a5e7e45ff1e50181c`; Enshrouded `391ea82203d30cb392a3397f92e2a3cbe7fb6128`; Black Arcana `710077da89da5eb4418d3ac676e148849727ff07`.
- **Delta canônico do ciclo:** `guides/projects/14-capability-delta-a0081-a0090.md`.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0081, A0082, A0083, A0084, A0085, A0086, A0087.
- **Re-fetch pós-escrita:** 7/7 PASS em 2026-08-31.
- **Sem mutação funcional:** A0088, A0089, A0090.
- **Dossiês criados:** 10/10.
- **Nove eixos / 18 critérios:** PASS no design; availability/fail-closed explícitos onde runtime/provider não prova o binding.
- **A0081:** herda indisponibilidade de A0075 e não pode comprar rank enquanto o pré-requisito estiver estruturalmente indisponível.
- **A0082:** caminho físico comum permanece válido; Ignitium e qualquer lifesteal nativo exigem correlação exata da cura provider-native ao mesmo root antes da parcela Skill Tree.
- **A0083/A0084/A0085:** `SustainResolver` puro não torna os nodes implementáveis; cada um exige producer causal próprio e fica indisponível/não comprável enquanto nenhum provider seguro estiver ligado.
- **A0086:** availability transitiva de A0083/A0085; a keystone não cria classificadores faltantes.
- **A0087:** runtime já é fail-closed com `BloodThirstService(null)`; o purchase/gate também deve ser unavailable até BodyProvider Cold Sweat+exhaustion e disponibilidade A0075/A0081. O +8% de healing received pertence ao pipeline geral de curas elegíveis.
- **A0088/A0089/A0090:** bindings vanilla/NeoForge reais presentes; A0088 preserva a razão de vida durante refresh e A0089/A0090 usam modifiers relativos sobre Armor/Toughness existentes.
- **Arquivo canônico do lote:** `audits/AUDITORIA-A0081-A0090.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0091+:** não iniciado.

### Pendências destinadas ao Chat 2

1. `P-A0081-01` — **BLOQUEANTE:** unavailable-node transitivo A0075→A0081; nenhum purchase/rank enquanto A0075 estiver indisponível.
2. `P-A0081-02` — limpar reserve/snapshot/pending/claims em rank loss, respec, rules reload e perda do pré-requisito; testar cura diferida, no-overheal e não recursão no sustain.
3. `P-A0082-01` — interceptar e correlacionar a cura nativa final de Ignitium ao mesmo root (`EXACT_INTERCEPTED` ou equivalente); sem prova, aquele root fica inelegível; nunca `NativeCorrelation.NONE` para fonte nativa conhecida.
4. `P-A0082-02` — regressões melee/projectile físico, overkill clipping, um root/uma cura, cap móvel, projectile derivado, fake player/summon e dedup.
5. `P-A0083-01` — **BLOQUEANTE:** availability + producer `DIRECT_MAGIC`; Iron's `SpellDamageSource` é boundary provider-native candidato e Ars precisa prova causal equivalente.
6. `P-A0084-01` — **BLOQUEANTE:** availability + classificação elemental/root provider-native; nenhum elemento fabricado por heurística.
7. `P-A0085-01` — **BLOQUEANTE:** ledger causal `owner + application + pulse` para DoT direto; lifecycle de alvo/ator/rank/respec/rules reload.
8. `P-A0086-01` — **BLOQUEANTE/transitivo:** availability depende de A0083/A0085 e ranks obrigatórios; universal path não sintetiza producers ausentes.
9. `P-A0087-01` — **BLOQUEANTE:** BodyProvider real para calor metabólico Cold Sweat + exhaustion vanilla na mesma atividade, `maintain/release`, availability herdada A0075/A0081.
10. `P-A0087-02` — pipeline canônico geral de `healing received +8%` exatamente uma vez durante Sede de Sangue; interação com o cap de sustain sem duplicação.
11. `P-A0087-03` — Thirst Was Reclaimed somente por adapter causal da mesma atividade; ausência omite hidratação e nunca a infere de exhaustion.
12. `P-A0088-01` — testes de rank/respec/rules reload, preservação de razão, ausência de cura gratuita e unicidade do modifier.
13. `P-A0089-01` — testes de zero-base, modifier relativo e separação de `STUN_ARMOR`/resistência física.
14. `P-A0090-01` — testes de zero-base, modifier relativo e fontes que ignoram armadura/toughness.
15. `P-A0081-90-TEST-01` — GameTest/harness transversal provider-present/absent para sustain, native heal correlation, magic/element/DoT availability, BodyProvider, attributes, lifecycle, dedup, multiplayer e dedicated server.

O design A0081–A0090 está fechado. O fechamento operacional deste ciclo exige a PR desta auditoria, review resolvido, CI GREEN, merge e confirmação fresca da `main`; após isso o Chat 1 deve **PARAR**. A0091–A0100 só pode começar mediante novo comando do usuário.

## Chat 1 — lote exato A0091–A0100

**Estado:** `DESIGN APROVADO / LOTE FECHADO PELO CHAT 1; AGUARDANDO IMPLEMENTAÇÃO CHAT 2`.

- **INÍCIO:** A0091.
- **FIM:** A0100.
- **Quantidade:** 10 perks consecutivas.
- **Base de abertura:** RPG Skill Tree `main@5098e38cbfb0e90d788de0722dd7e2f68753261d`.
- **Reconciliado antes dos arquivos compartilhados com:** `main@5530667f5303c3f628ea9f69dd947dbfed888915`, preservando o fechamento Chat 3 A0021–A0030.
- **Freshness final pré-PR/reconciliação final:** RPG Skill Tree `main@cb95a527fa3b6138d674c74a09dc32d58885d523`; `5530667... → 19f6fa7...` trouxe corpus TFC do Compêndio + centralização de Mastery IDs (#317), e `19f6fa7... → cb95a52...` trouxe somente SonarQube workflow/plugin Gradle. Ambos foram classificados sem impacto contratual nas dez perks; o último é infraestrutura CI/análise estática.
- **Gate de delta próprio promovido:** RPG `cb95a527fa3b6138d674c74a09dc32d58885d523`; Volcanoes `eaddc3232dfc600780769f4a5e7e45ff1e50181c`; Enshrouded `6642d4ed14bbae2a771075ca466e6749ac8f7fb8`; Black Arcana `462c5c4af403629a7092129cf7f3070472f03e59`.
- **Delta canônico do ciclo:** `guides/projects/15-capability-delta-a0091-a0100.md`.
- **Notion fetch fresco:** 10/10.
- **Notion alterado:** A0092, A0096, A0097, A0098, A0099.
- **Re-fetch pós-escrita:** 5/5 PASS em 2026-08-31.
- **Sem mutação funcional:** A0091, A0093, A0094, A0095, A0100.
- **Dossiês criados:** 10/10.
- **Nove eixos / critérios:** PASS no design, usando `UNAVAILABLE_NODE / FAIL-CLOSED` como resultado correto onde provider/hook não prova binding.
- **A0091:** usar apenas `minecraft:generic.knockback_resistance`, +0,03/rank, teto próprio +0,15.
- **A0092/A0096:** materializar uma única tag/classifier físico; A0096 reutiliza A0092; composição multiplicativa, não aditiva.
- **A0093/A0094/A0100:** indisponíveis/não compráveis enquanto bindings obrigatórios faltarem; fórmula pura e `FAIL_CLOSED_*` no efeito não autorizam gasto de PP.
- **A0095:** design fresco remove dependência A0094 e usa `epicfight:stun_armor` real por `ADD_FLAT +0,25/rank`; catálogo/policy/data preparatórios estão stale.
- **A0097:** PRE somente reserva; POST com dano hostil efetivo commita e reinicia janela. Classifier hostil é `LivingEntity` causal não aliado, não `Enemy || Player`.
- **A0098/A0099:** bridge PP não duplica provenance/custo/refund do Stage 04.02; A0099 reutiliza exclusivamente o detector de A0079.
- **Arquivo canônico do lote:** `audits/AUDITORIA-A0091-A0100.md`.
- **Runtime alterado neste Chat 1:** nenhum.
- **A0101+:** não iniciado.

### Pendências destinadas ao Chat 2

1. `P-A0091-01` — validar modifier idempotente, teto +0,15 e lifecycle.
2. `P-A0092-01/-02` — materializar `rpgskilltree:physical`, seed conservador, exclusões, ordem e dedup.
3. `P-A0093-01` — **BLOQUEANTE:** purchase fail-closed sem guard-stamina hook causal; sem refund pós-débito.
4. `P-A0094-01` — **BLOQUEANTE:** availability A0093 + GUARD_BREAK/recovery hook real; sem simulação.
5. `P-A0095-01/-02` — remover A0094 stale; adicionar binding ADD_FLAT `epicfight:stun_armor` +0,25/rank, versionado/idempotente.
6. `P-A0096-01` — reuse classifier A0092 + pre-impact snapshot + composição multiplicativa.
7. `P-A0097-01/-02` — reservation→commit/rollback causal e classifier `LivingEntity` não aliado compartilhado.
8. `P-A0098-01/-02` — sprint/forced movement/ParCool receipt e bridge PP sem tocar Stage04.02.
9. `P-A0099-01/-02` — fechar/reutilizar `P-A0079-02`, forced invalidation, classifier hostil e bridge PP; detector paralelo proibido.
10. `P-A0100-01/-02` — **BLOQUEANTE:** purchase unavailable sem incoming critical decomposition; nenhuma heurística.
11. `P-A0091-0100-TEST-01` — harness/GameTests transversal de purchase gates, damage tag, PRE/POST causalidade, Stun Armor, sprint/stationary lifecycle, provider absent/present, multiplayer e dedicated server.

O Chat 2 deve continuar nesta mesma branch/PR e implementar exatamente os contratos aprovados. Chat 1 não executa a bateria final, não declara `IMPLEMENTAÇÃO CONFIRMADA` e não faz merge. A0101+ só pode começar em ciclo futuro mediante novo comando do usuário.
