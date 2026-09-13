# Status dos Dossiês de Perks

Reauditoria obrigatória do recorte histórico **A0001–A0090** e dos ciclos especiais autorizados, incluindo **A0200–A0299**, contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

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
| A0013 | Treino com Lanças I | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pela PR #237; classificação provider→árvore mergeada pela PR #237 | nenhuma |
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
| A0031 | Treino com Maças I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0032 | Treino com Maças II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0033 | Precisão com Maças | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0034 | Trauma Contundente | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pelo Chat 3 na PR #359 | nenhuma bloqueante; guard/posture extras permanecem fail-closed sem receipt |
| A0035 | Armadura Fendida | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA no contrato genérico pelo Chat 3 na PR #359; `P-A0035-02` resolvida | nenhuma bloqueante; `P-A0035-01` Witherstein específico segue fail-closed sem registry/tag versionado |
| A0036 | Maestria de Maças — Quebra-Ossos | APROVADO após correção | NÃO CONFIRMADA / FAIL-CLOSED CORRETO; consumer Descompasso/sequencing validado pelo Chat 3 na PR #359 | `P-A0036-01`: heavy receipt inequívoco continua ausente no Epic Fight 21.17.3.1 |
| A0037 | Treino com Foices I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0038 | Treino com Foices II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0039 | Precisão com Foices | APROVADO + boundary | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359 | nenhuma |
| A0040 | Marca da Ceifa | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #359; `P-A0040-01` resolvida | nenhuma |
| A0041 | Corte de Ceifa | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #364 | nenhuma |
| A0042 | Maestria de Foices — Colheita de Batalha | APROVADO após correção | NÃO CONFIRMADA / FAIL-CLOSED CORRETO validado pelo Chat 3 na PR #364 | `P-A0042-01/-02`: `eligible_kill` anti-abuso canônico ainda ausente; node permanece `UNAVAILABLE_NODE` |
| A0043 | Treino com Arcos I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #364 | nenhuma |
| A0044 | Treino com Arcos II | APROVADO após review | NÃO CONFIRMADA / FAIL-CLOSED CORRETO validado pelo Chat 3 na PR #364 | provider semântico de draw/preparation speed ainda ausente; node `UNAVAILABLE_NODE` |
| A0045 | Precisão com Arcos | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #364 | nenhuma |
| A0046 | Foco de Mira | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA nos componentes com receipt real pelo Chat 3 na PR #364 | heavy-impact/body scalars permanecem component-wise fail-closed sem receipt/provider real |
| A0047 | Distância Dominada | APROVADO após review | NÃO CONFIRMADA / FAIL-CLOSED CORRETO validado pelo Chat 3 na PR #364 | herda indisponibilidade de A0044; provider de launch speed ainda ausente |
| A0048 | Maestria de Arcos — Tiro Preparado | APROVADO | NÃO CONFIRMADA / FAIL-CLOSED CORRETO validado pelo Chat 3 na PR #364 | herda cadeia A0044→A0047 indisponível; sem bypass |
| A0049 | Treino com Bestas I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #364 | nenhuma |
| A0050 | Treino com Bestas II | APROVADO após review | NÃO CONFIRMADA / FAIL-CLOSED CORRETO validado pelo Chat 3 na PR #364 | provider semântico de reload/preparation speed ainda ausente; node `UNAVAILABLE_NODE` |
| A0051 | Precisão com Bestas | APROVADO após correção/review | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #387 | nenhuma |
| A0052 | Cadência de Recarga | APROVADO após correção/review | NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED ATUAL CONFIRMADO pelo Chat 3 na PR #387; `P-A0052-04` Multishot resolvida | herda A0050 sem binding semântico de reload/preparation speed |
| A0053 | Virote Perfurante | APROVADO após correção/review | NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED ATUAL CONFIRMADO pelo Chat 3 na PR #387 | herda cadeia A0050→A0052; Impact só quando semanticamente seguro |
| A0054 | Maestria de Bestas — Mecanismo Ajustado | APROVADO após correção/review | NÃO CONFIRMADA COMO JOGÁVEL / FAIL-CLOSED ATUAL CONFIRMADO pelo Chat 3 na PR #387 | herda A0050/A0052/A0053; reload-speed extra permanece omitido sem hook semântico |
| A0055 | Treino com Armas de Punho I | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #387 | nenhuma |
| A0056 | Treino com Armas de Punho II | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #387 | nenhuma |
| A0057 | Precisão com Armas de Punho | APROVADO após correção | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #387 | nenhuma |
| A0058 | Sequência Limpa | APROVADO após correção/review | IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO pelo Chat 3 na PR #387 | nenhuma bloqueante; `P-A0058-01` heavy-impact e `P-A0058-02` body modulation continuam provider/config-bound |
| A0059 | Quebra de Ritmo | APROVADO | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #387 | nenhuma bloqueante; `P-A0059-01` heavy/finalizer e `P-A0059-02` guard-break dependem de receipts provider-native |
| A0060 | Maestria de Armas de Punho — Combinação Final | APROVADO após review de lifecycle | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #387 | nenhuma bloqueante; `P-A0060-01` heavy/finalizer e `P-A0060-02` Stamina ledger dependem de receipts provider-native |
| A0061 | Força Aplicada | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 no pipeline físico canônico | nenhuma bloqueante; Simply Swords permanece provider-native |
| A0062 | Golpe Preciso | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 no resolvedor crítico canônico | nenhuma bloqueante; adapters futuros devem convergir no mesmo resolver |
| A0063 | Impacto Crítico | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 após `canonicalCritical=true` | nenhuma |
| A0064 | Ritmo de Combate | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 no binding semântico disponível | nenhuma bloqueante; famílias sem equivalência segura permanecem fail-closed |
| A0065 | Penetração Física | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391; backend/contribuição independente | nenhuma bloqueante; providers sem estágio seguro permanecem fail-closed |
| A0066 | Impacto Marcial | APROVADO | IMPLEMENTAÇÃO CONFIRMADA para melee Epic Fight / FAIL-CLOSED CONFIRMADO sem receipt de Impact pelo Chat 3 na PR #391 | nenhuma bloqueante; não fabricar Impact sintético |
| A0067 | Firmeza Ofensiva | APROVADO após correção de availability | IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #391; `UNAVAILABLE_NODE` + ranks legados mascarados server-side | nenhuma bloqueante; binding ofensivo futuro depende de lifetime provider-native seguro |
| A0068 | Dano contra Feridos | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 com snapshot pré-impacto estrito `<35%` | nenhuma |
| A0069 | Dano contra Íntegros | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 com snapshot pré-impacto estrito `>85%` | nenhuma |
| A0070 | Dano contra Chefes | APROVADO após correção de cobertura | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #391 para vanilla/Cataclysm/Apothic + `enshrouded:shroud_lich` opcional | nenhuma bloqueante; demais bosses externos continuam fail-closed até IDs/adapters exatos |
| A0071 | Dano contra Elites | APROVADO | IMPLEMENTAÇÃO CONFIRMADA NO CLASSIFICADOR CANÔNICO pelo Chat 3 na PR #355 | nenhuma bloqueante; providers externos novos exigem identidade/marker/tag exato e permanecem fail-closed sem mapping |
| A0072 | Retaliação | APROVADO após availability | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #355 | nenhuma bloqueante no estado atual; herda A0067 indisponível e não permite ghost rank |
| A0073 | Janela de Execução | APROVADO após reservation→commit | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #355 com reservation→POST commit e correlação root/projétil | nenhuma bloqueante; refund de Stamina permanece 0 sem receipt pós-consumo causal exato |
| A0074 | Primeiro Sangue | APROVADO após reservation→commit | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #355 com opener/history/finisher commitados no POST | nenhuma |
| A0075 | Ritmo Sustentado | APROVADO EM FAIL-CLOSED | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #355 | nenhuma bloqueante enquanto indisponível; desbloqueio futuro exige Stamina regen + Cold Sweat thermal + exhaustion all-or-nothing |
| A0076 | Postura Agressiva | APROVADO após boundary de ativação | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #355 com keybind/payload de intenção e authority server-side | nenhuma |
| A0077 | Postura Cautelosa | APROVADO após availability | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #355 | nenhuma bloqueante no estado atual; herda A0067 indisponível apesar da infraestrutura de stance validada |
| A0078 | Ataque em Movimento | APROVADO | IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO pelo Chat 3 na PR #355 | nenhuma bloqueante; ParCool/Epic ParCool extras permanecem fail-closed até receipt server-authoritative real |
| A0079 | Ataque Estacionário | APROVADO após hardening | IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS AUDITADOS pelo Chat 3 na PR #355 | nenhuma bloqueante; novos transports exigem adapter explícito e desconhecidos falham fechado |
| A0080 | Golpe de Oportunidade | APROVADO EM FAIL-CLOSED | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #355; consumer reservation→POST commit validado | nenhuma bloqueante enquanto indisponível; producer `avoidedAttackId`/dodge-success provider-native continua ausente |
| A0081 | Recuperação de Combate | APROVADO EM FAIL-CLOSED após availability transitiva | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #372; A0075 unavailable mascara purchase/rank efetivo; recovery/lifecycle testados | nenhuma bloqueante enquanto A0075 estiver indisponível; ativação futura depende da resolução canônica de A0075 |
| A0082 | Vampirismo de Arma | APROVADO após boundary de correlação nativa | IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS FÍSICOS CAUSAIS pelo Chat 3 na PR #372; Epic Fight/vanilla/projectile dedup; Ignitium source-specific fail-closed | nenhuma bloqueante; `EXACT_INTERCEPTED` de Ignitium segue ausente e essa fonte permanece Skill Tree=0 |
| A0083 | Vampirismo Mágico | APROVADO EM FAIL-CLOSED | IMPLEMENTAÇÃO CONFIRMADA PARA IRON'S EXATO `1.21.1-3.16.3` pelo Chat 3 na PR #372; provider/version/API/native-lifesteal drift fail-closed | nenhuma bloqueante para Iron's; Ars Nouveau 5.13.1 segue sem adapter causal aprovado |
| A0084 | Sifão Elemental | APROVADO EM FAIL-CLOSED | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #372 | mapa canônico/versionado school/damage type→elemento ainda ausente; ativação futura retorna ao Chat 1 |
| A0085 | Sifão de Dano Periódico | APROVADO EM FAIL-CLOSED | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #372 | owner + `applicationId` + `pulseId` provider-native ainda ausentes; sem ledger heurístico |
| A0086 | Vampirismo Universal | APROVADO após availability transitiva | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #372 | herda indisponibilidade de A0085; universal não sintetiza classifiers |
| A0087 | Sede de Sangue | APROVADO EM FAIL-CLOSED após BodyProvider/healing boundary | NÃO CONFIRMADA COMO JOGÁVEL / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA pelo Chat 3 na PR #372; nenhum benefício parcial vaza | BodyProvider Cold Sweat+exhaustion e pipeline geral de healing-received continuam ausentes; Thirst causal opcional segue pendente |
| A0088 | Constituição | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #372; `MAX_HEALTH` relativo + `preserveHealthRatio` + modifier idempotente | nenhuma |
| A0089 | Couro Endurecido | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #372; `ARMOR` relativo, zero base→zero bônus, sem STUN_ARMOR/segunda resistência | nenhuma |
| A0090 | Têmpera | APROVADO | IMPLEMENTAÇÃO CONFIRMADA pelo Chat 3 na PR #372; `ARMOR_TOUGHNESS` relativo, A0089≥2 preservada, zero base→zero bônus | nenhuma |

## Regras sistêmicas vigentes

- **Provider-native first:** famílias desconhecidas ficam `FAIL-CLOSED`; tags paralelas não versionadas não são classificação canônica.
- **Unavailable-node invariant:** provider/binding obrigatório ausente ou incompatível gera estado explícito de nó indisponível/não comprável; nunca silent no-op purchase, rank fantasma ou gasto de pontos sem efeito.
- **Versões auditadas A0001–A0020:** Epic Fight MARTIAL é registrado somente para `21.17.3.1`; A0012 aceita Cold Sweat somente em `2.4.2`.
- **Provenance:** hits indiretos, companions, hazards ou fontes não hostis não podem herdar autoria MARTIAL do jogador.
- **Launch provenance CROSSBOW:** `owner + CrossbowItem` não bastam; A0051–A0054 exigem launch receipt CROSSBOW confirmado e projectile/root correlacionado. Projectile derivado/reemitido sem receipt fica fail-closed.
- **Crítico:** uma única resolução/root action; `ARCANE_BACKLASH` e companion-owned damage não entram como ataque direto.
- **Mastery:** não pode vir de spam de dano. Famílias sem producer provider-native comprovado usam discovery/milestones finitos e deduplicados.
- **Mastery BOW/CROSSBOW:** fonte canônica é `epicfight:bow` / `epicfight:crossbow`; Notion, `CombatPerkTreeModel`, projectile runtime e `tree_architecture/combat.json` estão alinhados. Discovery é persistente e deduplicada por tipo hostil; não existe segunda ledger válida.
- **Mastery FIST:** fonte canônica do ramo A0055–A0060 é `combat:fist`, +10 por tipo hostil inédito; 6 tipos→60 e 8→80. `tree_architecture/combat.json`, model e producer estão reconciliados; não existe ledger paralela `epicfight:fist` válida para esta disciplina.
- **HAMMER/MACE/SCYTHE:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada.
- **FIST:** externos exigem capability/mapping provider-native seguro; não inferir por nome, aparência ou tag paralela não governada. Mãos vazias só entram em FIST por mapping explícito/versionado.
- **BOW/CROSSBOW:** vanilla é classificado por `BowItem`/`CrossbowItem`; externos exigem provider-native/mapping explícito. Mastery 60 = 6 tipos hostis inéditos; Mastery 80 = 8 tipos quando o contrato terminal exigir.
- **Availability em Bestas:** enquanto A0050 estiver indisponível/não comprável, A0052, A0053 e A0054 ficam estruturalmente indisponíveis; fallback não pode bypassar dependência. A0049/Mastery CROSSBOW já está resolvida; o bloqueio estrutural remanescente da cadeia é A0050.
- **Root outcome CROSSBOW:** Multishot compartilha uma única root action; siblings são agregados por root; qualquer success confirmado vence failures anteriores e all-fail liquida no máximo uma perda de Cadência. Callback duplicado não liquida novamente.
- **Reservation→commit em lançamento:** Cadência/janela de A0053/A0054 só são consumidas quando o projectile/root correlacionado é realmente criado; cancelamento tardio ou ausência de spawn faz rollback.
- **Commit causal:** consumo irreversível de recurso/estado condicionado a resultado real ocorre no commit pós-hit confirmado; cancelamento/dano zero não deixa estado fantasma. Para ações de lançamento A0053/A0054, o commit específico ocorre somente após criação confirmada do projectile/root correlacionado.
- **Lifecycle:** estados por alvo precisam cleanup bounded quando alvo morre, é removido, descarrega ou desaparece sem evento terminal equivalente. Estados por ator precisam cleanup bounded em logout/dimensão/respawn/shutdown e reconciliação em rank loss, respec e rules reload que invalide perk/pré-requisito.
- **Proteção física:** Armor/guard/posture física não se confunde com Arcane Resistance, MagicResistance, Shroud ou hazards ambientais.
- **A0061–A0070:** dano físico direto, crítico, ritmo, penetração e Impact usam boundaries canônicos e identidades distintas; nenhuma contribuição pode ser aplicada duas vezes por bridges paralelas.
- **A0067:** sem lifetime provider-native seguro da janela ofensiva, o node é indisponível/não comprável; ranks persistidos legados são mascarados para rank efetivo 0 no boundary server-side.
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

### A0031–A0040 — Chat 2 retroativo
- Seleção refeita por ordem do usuário desde A0001: A0001–A0020 já confirmadas; A0021–A0030 já passaram por Chat 2/Chat 3; A0031–A0040 foi o primeiro lote exato elegível.
- Branch substituta: `feat/chat2-a0031-a0040-retro-implementation`, criada da `main@452e8b23e374179c1f616f9beedce6e3dea66ef5` porque os refs históricos Chat 2 não continham uma implementação ativa/usável deste lote.
- A0031/A0037: removidas tags paralelas MACE/SCYTHE e Mastery convertida para discovery finita +10/tipo; `minecraft:mace` é o único fallback vanilla; SCYTHE externa exige provider/mapping explícito.
- A0035: `P-A0035-02` resolvida por reservation→POST commit; Trauma/Sunder não são mais consumidos/criados no PRE.
- A0036: `P-A0036-02/-03` implementadas no consumer/sequencing; Sunder precisa preexistir ao root; Descompasso possui ambos debuffs/cooldown/cleanup. `P-A0036-01` permanece fail-closed porque heavy receipt inequívoco continua ausente.
- A0040: `P-A0040-01` resolvida por prune periódico bounded de marcas expiradas.
- A0032/A0033/A0034/A0038/A0039 preservam os pipelines aprovados e passam a operar sobre classificação de família endurecida.
- Auditoria Chat 2: `audits/AUDITORIA-A0031-A0040-IMPLEMENTACAO-CHAT2.md`.
- Estado de handoff: **CÓDIGO PRESENTE / CHAT 2 CONCLUÍDO / AGUARDANDO VALIDAÇÃO CHAT 3**; A0036 permaneceu intencionalmente `CÓDIGO PRESENTE EM FAIL-CLOSED`.

### A0031–A0040 — Chat 3
- PR oficial continuada: **#359**, branch `feat/chat2-a0031-a0040-retro-implementation`; nenhuma terceira linha paralela foi criada.
- O HEAD antigo `ee600298df6791c13be7a327442ed6be5dbb8d75` foi sincronizado com `main@5213d068a91c95f45b9e119dec0be0636abc426d` pelo merge exato `8cf156294c7dd5922f6138a108a544f3ddeeddea`, promovido por fast-forward.
- Findings antigos do review #359 foram rechecados: testes PRE→POST e Mastery foram atualizados; o consumer A0061–A0080 usa fallback exato `Items.MACE`, sem depender da tag removida.
- `RPG Skill Tree CI` #3361 / run `33657496252` no HEAD sincronizado `8cf156294c7dd5922f6138a108a544f3ddeeddea`: **SUCCESS**, incluindo JUnit 5, NeoForge JUnit adapter tests, GameTests, validações, build, JAR e dedicated-server smoke.
- A0031/A0032/A0033/A0037/A0038/A0039/A0040: **IMPLEMENTAÇÃO CONFIRMADA**.
- A0034: **IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO**; guard/posture extras permanecem fail-closed não bloqueantes.
- A0035: **IMPLEMENTAÇÃO CONFIRMADA no contrato genérico**; `P-A0035-01` Witherstein específico permanece fail-closed não bloqueante sem identidade/tag versionada.
- A0036: **NÃO CONFIRMADA / FAIL-CLOSED CORRETO**; `P-A0036-01` continua aberta porque Epic Fight 21.17.3.1 não oferece heavy receipt inequívoco; o consumer latente de Descompasso foi validado sem inventar detecção heavy.
- Auditoria Chat 3: `audits/AUDITORIA-CHAT3-A0031-A0040-PENDENCIAS-TECNICAS.md`.
- Nenhuma perk A0041+ foi iniciada neste ciclo. O merge só ocorre após CI verde do HEAD documental final e confirmação de base fresca/mergeabilidade.

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
- **Nove eixos / 18 critérios:** design PASS/N/A após review; divergências runtime/provider availability e mastery namespace foram catalogadas para os Chats 2/3.
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

## Chat 3 — lote exato A0041–A0050

- **PR:** #364; branch `feat/chat2-a0041-a0050-stacked-handoff`.
- **Auditoria:** `audits/AUDITORIA-CHAT3-A0041-A0050-PENDENCIAS-TECNICAS.md`.
- **A0041/A0043/A0045/A0049:** `IMPLEMENTAÇÃO CONFIRMADA`.
- **A0046:** `IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL`; heavy/body permanecem component-wise fail-closed.
- **A0042/A0044/A0047/A0048/A0050:** `NÃO CONFIRMADA / FAIL-CLOSED CORRETO`, com nodes indisponíveis ou cadeia indisponível quando o provider obrigatório não existe.
- **A0041:** boundary Epic Fight POST coberto em suíte NeoForge-loaded; dano zero faz rollback, dano positivo confirmado consome exatamente uma vez.
- **RPG Skill Tree CI #3467 / run `33986475213`: SUCCESS** — JUnit 5, NeoForge JUnit, GameTests, provider-present tests, build, JAR e dedicated-server smoke verdes.
- **SonarQube Cloud #703 / run `33986475341`: SUCCESS** — Quality Gate recuperado por cobertura comportamental real, sem reduzir gate/excluir código novo.
- **Mastery BOW/CROSSBOW:** `epicfight:bow` / `epicfight:crossbow` alinhados entre architecture/model/runtime; discovery persistente e deduplicada.
- **Availability:** A0042/A0044/A0047/A0048/A0050 falham fechado sem gasto de ponto/rank fantasma; A0047 não fabrica projectile speed.
- **Pendências futuras:** `eligible_kill`, draw/reload/launch speed, heavy-impact/body providers reais. Todas permanecem fail-closed e não exigem redesign imediato.
- **A0051+:** não iniciado por este Chat 3; a PR sucessora do Chat 2 existe separadamente e não faz parte deste ciclo.

O merge da PR #364 só é autorizado após CI verde do HEAD documental final, confirmação fresca de mergeabilidade/base e verificação pós-merge da `main`. Depois disso o Chat 3 deve PARAR.

## Chat 3 — lote exato A0051–A0060

- **PR:** #387; branch `feat/chat2-a0051-a0060-stacked-handoff`.
- **Auditoria:** `audits/AUDITORIA-CHAT3-A0051-A0060-PENDENCIAS-TECNICAS.md`.
- **Modlist fresca:** 607 mods; Epic Fight `21.17.3.1`, Apothic Attributes `2.10.1`, Mobstein `5.4.4`; Punchy `2.7e` confirmado também no Notion e continua visual/compat, não authority deste lote.
- **A0051/A0055/A0056/A0057:** `IMPLEMENTAÇÃO CONFIRMADA` após revisão do contrato e bateria funcional.
- **A0058:** `IMPLEMENTAÇÃO CONFIRMADA NOS COMPONENTES COM RECEIPT REAL / FALLBACK CANÔNICO`; heavy-impact/body modulation permanecem provider-bound.
- **A0052/A0053/A0054:** `NÃO CONFIRMADAS COMO JOGÁVEIS / FAIL-CLOSED ATUAL CONFIRMADO`; herdam indisponibilidade A0050 e não permitem bypass/ghost rank.
- **A0059/A0060:** `NÃO CONFIRMADAS COMO JOGÁVEIS / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA`; sem heavy/finalizer/guard-break/Stamina receipts não há ativação ou consumo heurístico.
- **P-A0052-04 RESOLVIDA:** Multishot agrega siblings por `rootActionId`, usa `success-wins`, all-fail liquida no máximo uma perda e callback duplicado não liquida novamente.
- **HEAD funcional/final pré-documentação validado:** `e0fcc0a178b2560ff3476bb9b7cda1690c5436e8`.
- **RPG Skill Tree CI #3720 / run `34009906068`: SUCCESS** — JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, Battle Mage provider-present, validações, build, JAR e dedicated-server smoke verdes.
- **SonarQube Cloud #956 / run `34009906109`: SUCCESS / QUALITY GATE PASSED** — **81.7% Coverage on New Code** (gate ≥80%), **0.0% duplication**, **0 Security Hotspots**; cobertura recuperada por testes comportamentais reais, sem reduzir gate nem excluir novo código de produção.
- **Sonar:** 1 New issue não bloqueante foi reportada, mas o comentário GitHub não expõe o detalhe; `P-SONAR-PR387-01` fica registrada para inspeção em outro chat autorizado a usar navegador externo. Não usar TinyFish neste ciclo.
- **Pendências não bloqueantes:** `P-A0058-01/-02`, `P-A0059-01/-02`, `P-A0060-01/-02`, blockers provider/availability herdados de A0050 para A0052–A0054 e `P-SONAR-PR387-01`.
- **Retorno ao Chat 1:** nenhum; a correção Multishot e os testes não alteraram design/authority/topologia.
- **Gate documental:** auditoria e STATUS reconciliados após o primeiro Quality Gate verde; o novo HEAD documental deve receber CI/Sonar verde antes do merge.
- **A0061+:** não iniciar neste ciclo.

## Chat 3 — lote exato A0061–A0070

- **PR:** #391; branch `feat/chat2-a0061-a0070-stacked-handoff`.
- **Auditoria:** `audits/AUDITORIA-CHAT3-A0061-A0070-VALIDACAO.md`.
- **A0061/A0062/A0063/A0064/A0065/A0068/A0069/A0070:** `IMPLEMENTAÇÃO CONFIRMADA` no escopo/provider comprovado.
- **A0066:** `IMPLEMENTAÇÃO CONFIRMADA` no melee Epic Fight e `FAIL-CLOSED CONFIRMADO` onde não existe receipt de Impact.
- **A0067:** `IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA`; `CombatPerkAvailabilityRuntime` marca o node indisponível e `A0061A0080RuntimeState.ranks(...)` mascara rank legado para zero efetivo.
- **A0070:** `enshrouded:shroud_lich` foi adicionado à tag canônica de bosses com `required: false`; nenhum lifecycle/reward/Story/fase do Enshrouded é alterado.
- **Cobertura Chat 3:** `A0061A0070Chat3CoverageJUnitTest` cobre A0067 availability/masking, bordas A0068/A0069, BOSS>ELITE, critical composition A0063 e independência A0061/A0065/A0066.
- **Pendências residuais não bloqueantes:** provider futuro para A0067 e registry IDs/adapters exatos dos bosses externos ainda fail-closed em A0070.
- **Redesign:** nenhum; authority, gates, topologia e identidade aprovados foram preservados.
- **Gate final:** após esta reconciliação documental, o HEAD da PR #391 deve ser revalidado em todos os checks; somente com CI GREEN fresco é autorizado o merge e a confirmação pós-merge da `main`.
- **A0071+:** não iniciar neste ciclo.

## Chat 3 — lote exato A0071–A0080

- **PR:** #355; branch `feat/chat2-a0071-a0080-implementation`.
- **Auditoria:** `audits/AUDITORIA-CHAT3-A0071-A0080-VALIDACAO.md`.
- **Preflight:** modlist fresca de 2026-09-07 e Notion rechecados; Epic Fight `21.17.3.1`, ParCool `4.0.0.3`, Epic ParCool `21.0.0`, Create `6.0.10` e Cold Sweat `2.4.2` permanecem coerentes com os boundaries usados pelo lote.
- **A0071/A0073/A0074/A0076:** `IMPLEMENTAÇÃO CONFIRMADA` no escopo comprovado; A0071 especificamente no classificador canônico.
- **A0078:** `IMPLEMENTAÇÃO CONFIRMADA NO FALLBACK CANÔNICO` via sprint vanilla server-side.
- **A0079:** `IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS AUDITADOS`, incluindo teleport/knockback/passenger e Create/Sable version-gated; desconhecidos falham fechado.
- **A0072/A0075/A0077/A0080:** `NÃO CONFIRMADAS COMO JOGÁVEIS / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA`; availability runtime mascara os nodes sem provider/binding obrigatório e impede ghost ranks.
- **A0073/A0074/A0080:** reservation→POST commit real; cancelamento/dano zero faz rollback. O finding P1 de correlação de projéteis foi resolvido com estado por arrow+target+root.
- **A0076:** `Alternar Postura Marcial` envia somente intenção; servidor é authority de ranks, availability, cooldown e transição.
- **HEAD funcional pré-documentação:** `201f038e2f145806f7111ef7d8376ba22769aad0`.
- **RPG Skill Tree CI #4079 / run `34081344643`: SUCCESS** — JUnit 5, NeoForge JUnit adapter, NeoForge GameTests, Battle Mage provider-present, validações, build, JAR e dedicated-server smoke verdes.
- **SonarQube Cloud / run `34081344663`: QUALITY GATE PASSED** — **80.2% Coverage on New Code**, **0.0% Duplication on New Code**, **0 Security Hotspots**, **0 New issues**. Cobertura recuperada com testes comportamentais reais, sem reduzir threshold/excluir código novo.
- **CodeQL e workflows auxiliares:** SUCCESS no HEAD funcional.
- **Pendências residuais não bloqueantes:** providers futuros de elite/transporte/movimento; refund A0073 permanece 0 sem receipt de Stamina; Cold Sweat metabolic binding para A0075; A0077 aguarda A0067; A0080 aguarda producer causal `avoidedAttackId`.
- **Retorno ao Chat 1:** nenhum; nenhuma correção alterou identidade, effect contract, authority, gate, dependência ou topologia.
- **Gate final:** este HEAD documental deve receber CI/Sonar verde fresco; somente então a PR #355 pode ser mergeada e a `main` confirmada.
- **A0081+:** não iniciar neste ciclo.

## Chat 3 — lote exato A0081–A0090

- **PR:** #372; branch `feat/chat2-a0081-a0090-implementation`.
- **Auditoria:** `audits/AUDITORIA-CHAT3-A0081-A0090-VALIDACAO.md`.
- **Preflight:** modlist física 2026-09-07 = 612 top-level; Notion fresco = 613 registros, 612 `Instalado`, 613 `Verificado`; jarjar não contado como top-level.
- **A0082:** `IMPLEMENTAÇÃO CONFIRMADA NOS BINDINGS FÍSICOS CAUSAIS`; Epic Fight/vanilla/projectile usam roots/receipts causais e Ignitium continua provider-owned/fail-closed quando a cura nativa não é correlacionável.
- **A0083:** `IMPLEMENTAÇÃO CONFIRMADA PARA IRON'S 1.21.1-3.16.3`; absence/version drift/API mismatch/invocation failure/native lifesteal ambíguo falham fechado. Ars Nouveau permanece sem adapter causal aprovado.
- **A0088/A0089/A0090:** `IMPLEMENTAÇÃO CONFIRMADA` no owner canônico Minecraft/NeoForge, com modifiers relativos/idempotentes; A0088 preserva health ratio e A0090 preserva A0089≥2.
- **A0081/A0084/A0085/A0086/A0087:** `NÃO CONFIRMADAS COMO JOGÁVEIS / IMPLEMENTAÇÃO FAIL-CLOSED CONFIRMADA`; availability mascara purchase/rank efetivo e nenhuma heurística/benefício parcial é liberado.
- **Pendência Iron's P2 resolvida:** falha de reflexão faz latch `runtimeInvocationHealthy=false` e torna o runtime contract unavailable; regressão JUnit presente.
- **Review crossbow resolvido:** NeoForge 1.21.1 passa `CrossbowItem.performShooting` por `ArrowLooseEvent`; nenhum segundo hook foi criado.
- **HEAD funcional pré-documentação:** `4502d1d253863f6f25e13e6a7284a0d53a3b0fd5`.
- **RPG Skill Tree CI run `34147843664`: SUCCESS** — JUnit 5, NeoForge JUnit, NeoForge GameTests, provider-present GameTests, validações, build, JAR e dedicated-server smoke verdes.
- **SonarQube run `34147843581`: SUCCESS** — coletas provider-free/Battle Mage/Ars/Create/MineColonies e análise verdes; S2187 removido sem suppress/exclusion.
- **CodeQL run `34147843620`: SUCCESS** — compile + analyze verdes.
- **Reviews:** 2/2 threads resolvidas.
- **Pendências residuais não bloqueantes:** Ignitium `EXACT_INTERCEPTED`; Ars adapter A0083; mapa elemental A0084; provider application/pulse A0085; A0086 transitiva; BodyProvider/healing-received/Thirst A0087. Todas permanecem fail-closed e não autorizam heurística.
- **Retorno ao Chat 1:** nenhum bloqueio atual; retornos somente condicionais à futura ativação quando isso exigir mudança semântica.
- **Gate final:** este HEAD documental deve receber RPG Skill Tree CI + Sonar + CodeQL verdes frescos; somente então a PR #372 pode ser mergeada e a `main` confirmada.
- **A0091+:** não iniciar neste ciclo.