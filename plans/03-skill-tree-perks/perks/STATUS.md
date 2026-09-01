# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte **A0001–A0090** contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

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
| A0031 | Treino com Maças I | APROVADO após correção | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252; família MACE segura + Mastery por discovery finita; aguardando merge | nenhuma |
| A0032 | Treino com Maças II | APROVADO | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252 via attack-speed provider-native; aguardando merge | nenhuma |
| A0033 | Precisão com Maças | APROVADO + boundary | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252 no crítico canônico/root action; aguardando merge | nenhuma |
| A0034 | Trauma Contundente | APROVADO + boundary | VALIDAÇÃO CHAT 3 CONCLUÍDA NO FALLBACK CANÔNICO de Armor física na PR #252; aguardando merge | nenhuma bloqueante; guard/posture/redução física extras permanecem fail-closed sem receipt |
| A0035 | Armadura Fendida | APROVADO + boundary | VALIDAÇÃO CHAT 3 CONCLUÍDA no contrato genérico na PR #252; `P-A0035-02` corrigida por reservation→POST commit; aguardando merge | `P-A0035-01`: extensão Witherstein sem registry id/tag comprovado permanece fail-closed, não bloqueante para o contrato genérico |
| A0036 | Maestria de Maças — Quebra-Ossos | APROVADO após correção | NÃO CONFIRMADA / FAIL-CLOSED CORRETO; `P-A0036-02/-03` + Mastery anti-farm resolvidas/testadas na PR #252 | `P-A0036-01`: Epic Fight 21.17.3.1 sem heavy receipt inequívoco; bloqueia ativação operacional da perk |
| A0037 | Treino com Foices I | APROVADO após correção | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252; família SCYTHE provider-native/fail-closed + Mastery por discovery finita; aguardando merge | nenhuma |
| A0038 | Treino com Foices II | APROVADO | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252 via attack-speed provider-native; aguardando merge | nenhuma |
| A0039 | Precisão com Foices | APROVADO + boundary | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252 no crítico canônico/root action; aguardando merge | nenhuma |
| A0040 | Marca da Ceifa | APROVADO | VALIDAÇÃO CHAT 3 CONCLUÍDA na PR #252; `P-A0040-01` resolvida por pruning bounded + família SCYTHE segura; aguardando merge | nenhuma |
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
- **Sustain exclusions:** `ARCANE_BACKLASH`, `BLOOD_MAGIC_COST`, dano ambiental/Volcanoes/Enshrouded, máquina, summon/companion sem autoria direta e efeitos recursivos não são dano ofensivo do jogador para A0082–A0087.
- **Ignitium:** lifesteal nativo permanece provider-owned; sem correlação exata da cura final ao mesmo root, a fonte específica falha fechado e é proibido usar `NativeCorrelation.NONE`; armas comuns comprovadas continuam elegíveis.
- **Black Arcana:** `ARCANE_BACKLASH` é terminal e não crita/proca/concede Mastery/Focus/Marca/eligible_kill; também não abre Retaliação por self-cost nem vira ação física/mágica ofensiva elegível.
- **Enshrouded:** Shroud/Exposure/Madness/Flame/Story/MagicResistance não classificam movimento, stance, dodge, Stamina ou sustain ofensivo destas perks; bridge Shroud Lich permanece somente A0070 read-only.
- **Volcanoes:** hazards/geologia/prospecção permanecem fora dos pipelines MARTIAL/sustain; o delta mais recente é release/hardening e não cria capacidade de perk.
- **Mobstein 5.4.4:** companions não geram autoria física do dono; Witherstein não entra em A0070/A0071 por nome/tema sem identity adapter real.
