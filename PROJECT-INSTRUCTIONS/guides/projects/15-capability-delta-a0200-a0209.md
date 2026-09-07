# 15 — Capability Delta — A0200–A0209

Data de reconciliação inicial: 2026-08-31. Freshness final deste checkpoint: 2026-09-01.

Este suplemento registra o gate obrigatório dos quatro projetos próprios antes do lote **adiantado** A0200–A0209. O usuário determinou diretamente o início em A0200 mesmo com os demais chats ainda trabalhando na faixa dos 100. Portanto este documento promove apenas evidência de capability; ele não fecha A0091–A0199 nem presume que A0144, A0148–A0155, A0198 ou A0199 estejam prontas.

## Exceção de sequência autorizada para este lote

A regra permanente 26 normalmente impede adicionar grandes lotes de conteúdo enquanto blockers de foundation das Fases 0–4 permanecem abertos. Para **este lote exato**, a ordem específica e posterior do usuário autorizou trabalhar adiantado em A0200–A0209 e registrar as dependências para correção futura.

Esta exceção é deliberadamente estreita:

- entrega somente auditoria, design, dossiês, Notion e documentação;
- não adiciona catálogo/runtime, não habilita compra e não marca implementação;
- mantém A0200–A0209 como `UNAVAILABLE_NODE` enquanto faltar qualquer capability ou dependência obrigatória;
- não fecha, pula ou reclassifica A0091–A0199;
- não inicia A0210 nem autoriza outro lote adiantado;
- preserva integralmente a regra permanente para ciclos futuros sem nova autorização específica.

Assim, o lote não amplia a superfície jogável nem contorna foundation: ele pré-especifica dez contratos fechados e deixa todos os efeitos inertes até o fechamento causal dos blockers.

## Baselines documentais anteriores

| Projeto | Baseline promovido por A0081–A0090 |
|---|---|
| RPG Skill Tree | 6975970d086d32985d83a0018c841cce9d1cbd63 |
| Volcanoes standalone | eaddc3232dfc600780769f4a5e7e45ff1e50181c |
| Enshrouded | 391ea82203d30cb392a3397f92e2a3cbe7fb6128 |
| Black Arcana | 710077da89da5eb4418d3ac676e148849727ff07 |

## Freshness final e distinção da consolidação Volcanoes

| Projeto | Head auditado | Delta por capability | Decisão para A0200–A0209 |
|---|---|---|---|
| RPG Skill Tree | 54b6cdc1de923732c3ec7d99c660f8fdefdb0610 | transações PRE→POST A0023/A0024/A0029/A0030; confluences/bridges; lanes de Mastery; class resolution; consolidação nativa e reconciliação pós-merge do Volcanoes; manutenção de Compêndio/CI/Sonar | todas as capabilities dispostas na matriz abaixo; nenhuma fornece classifier/bucket ELDRITCH/ENDER, HealingResolver específico ou displacement receipt exigido pelo lote |
| Volcanoes standalone | eaddc3232dfc600780769f4a5e7e45ff1e50181c | head standalone sem avanço; seu source canônico foi incorporado ao RPG pela PR #308 | a simulação agora é subsistema nativo do RPG, mas geologia/atmosfera/pressão continuam authority Volcanoes e não classificam ELDRITCH/ENDER |
| Enshrouded | 5a25b03a23ae81c111bbe1d5c23f85d8abd066ec | Stage 07.02 fog e Stage 07.03 audio/particles, seguido de fechamento documental | **NÃO DEVE SER INTEGRADO**; apresentação client-side não vira outcome/classifier |
| Black Arcana | e89df6dc2c204c269d8f1811c6b3f309644c864a | integração RPG de hazard/progression/mastery, hardening e forecast read-only de Arcane Resistance | **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO**; não fornece `BLACK_ARCANA_ELDRITCH_OUTCOME` |

O head standalone do Volcanoes e o estado consolidado não são afirmações contraditórias: `eaddc323...` identifica o último source externo auditado; `f613dac5...`/RPG `main@54b6cdc...` identifica sua incorporação como subsistema nativo no único artefato RPG e a reconciliação documental pós-merge.

## Matriz obrigatória provider → árvore por capability detectada

| Projeto | Capacidade | Estado real | SHA/evidência | Cobertura atual | Decisão | Perk(s)/ação | Hook/boundary | Fail-closed |
|---|---|---|---|---|---|---|---|---|
| RPG Skill Tree | A0023: reserva de 2 Fluxo no PRE e commit do custo/cooldown somente após dano direto, hostil e positivo no POST | IMPLEMENTADO E CANÔNICO | PR #315; `A0021A0040CombatPolicy`, `A0021A0040CombatState`, `A0021A0040EpicFightHooks`; main `66fcec7...` | completa para o contrato A0023 | **COBERTA POR PERK EXISTENTE** | preservar A0023; nenhuma A0200+ nova | `rootActionId` + `DealDamageEvent.Pre/Post`; commit consumidor antes do ganho A0022 | POST cancelado, hostilidade perdida ou dano `<= 0` descarta reserva sem gasto/cooldown; roots concorrentes não reutilizam Fluxo reservado |
| RPG Skill Tree | A0024: ativação da Dança, custo de 4 Fluxo e consumo do primeiro hit migrados de PRE para transação causal | IMPLEMENTADO E CANÔNICO | PR #315; mesmos State/Policy/Hooks; main `66fcec7...` | completa para o contrato A0024 | **COBERTA POR PERK EXISTENTE** | preservar A0024; nenhuma A0200+ nova | reserva por `rootActionId`; modifiers de cálculo no PRE e estado/consumo no POST | POST inválido preserva Fluxo, janela e primeiro-hit enquanto seus prazos originais forem válidos; nenhum benefício fantasma |
| RPG Skill Tree | A0029: reserva de 3 Abalo e commit POST para Quebra de Postura | CORE CANÔNICO; ATIVAÇÃO PROVIDER PARCIAL | PR #315; `P-A0029-02` resolvida; `P-A0029-01` aberta; main `66fcec7...` | parcial por falta de heavy receipt inequívoco | **COBERTA POR PERK EXISTENTE** | preservar A0029 e blocker `P-A0029-01`; nenhuma A0200+ nova | transação root PRE→POST; futuro receipt Epic Fight provider-native | sem heavy receipt seguro, `heavyConfirmed=false`; nada reserva/consome/aplica. POST inválido faz rollback |
| RPG Skill Tree | A0030: reserva/commit POST da Janela Demolidora por root e alvo | CORE CANÔNICO; ATIVAÇÃO PROVIDER PARCIAL | PR #315; `P-A0030-02` resolvida; `P-A0030-01` aberta; main `66fcec7...` | parcial por falta de guard-break attacker-side e heavy receipt | **COBERTA POR PERK EXISTENTE** | preservar A0030 e blocker `P-A0030-01`; nenhuma A0200+ nova | reserva por `rootActionId` + target; commit no POST confirmado | sem os dois receipts seguros, não ativa; POST inválido descarta reserva e conserva a janela original ainda válida |
| RPG Skill Tree | confluences/bridges pagos com proveniência persistida e reconciliação automática após respec/class reload | IMPLEMENTADO E CANÔNICO | PR #312 / `5098e38...`; `ClassProgressionState`, `ProgressionService`, `ProgressionStateCodec` | completa pelo sistema de classes | **COBERTO POR SISTEMA UNIVERSAL** | nenhuma perk A0200+; manter authority de classes | `PaidClassReconcileResult` e pipeline canônico de progressão | requisito/provider ausente revoga classe derivada; estado incompleto não fabrica unlock nem bridge |
| RPG Skill Tree | catálogo central de lanes canônicas de Mastery | IMPLEMENTADO PARA IDS CANÔNICOS; PARCIAL PARA ADDONS IRON'S | PR #317 / `19f6fa7...`; `MasteryLaneCatalog`, `MasteryPolicies` | universal para lanes aceitas; ausente para `namespace/path` de school addon | **COBERTO POR SISTEMA UNIVERSAL** + **SEM HOOK SEGURO** para A0202/A0203/A0204/A0206 | usar ledgers canônicas; abrir `P-A0200-09-01`; manter quatro nodes indisponíveis | `MasteryLaneCatalog`/producer Iron's | school ID que o catálogo não aceite contribui zero; não remover namespace, agregar temas ou criar alias colidente |
| RPG Skill Tree | reconciliação de resolução de classes após datapack sync/rules reload | IMPLEMENTADO E CANÔNICO | PR #324 / `80df3a2...`; PR #328 / `2e1c5b6...`; `ProgressionDatapackEvents` | completa pelo sistema de classes | **COBERTO POR SISTEMA UNIVERSAL** | nenhuma A0200+; exigir cleanup/reconcile no lifecycle futuro | evento server-side pós-sync → resolver canônico | regras ausentes/incompatíveis não mantêm classe/node derivado inválido; cliente não é authority |
| RPG Skill Tree | dossiês A0081–A0090 e avanços editoriais de Compêndio natural | DOCUMENTAL/EDITORIAL | PRs #310, #314, #318, #323; main `66fcec7...` | N/A para capabilities Eldritch/Ender | **NÃO DEVE SER INTEGRADO** | nenhuma ação A0200+ | documentação/corpus, sem gameplay boundary | não usar texto editorial como prova de ação, classifier ou provider |
| RPG Skill Tree | workflows JaCoCo/Sonar, baseline, diagnóstico e dispatch manual | INFRAESTRUTURA | PRs #320, #325, #327, #329, #330, #332, #339; main `66fcec7...` | N/A gameplay | **NÃO DEVE SER INTEGRADO** | nenhuma perk | CI/quality gate | nenhum estado de CI cria capability jogável |
| RPG + Volcanoes nativo | lifecycle único e facade read-only de serviços Volcanoes dentro do artefato RPG | IMPLEMENTADO E CANÔNICO NO DESTINO | PR #308 / `f613dac...`; `RpgSkillTreeMod` chama `VolcanoesMod.initialize`; `NativeVolcanoesServices` | completa como bridge arquitetural read-only | **BRIDGE** + **COBERTO POR SISTEMA UNIVERSAL** | consumidores RPG futuros leem a facade; A0200–A0209 não ganham classificação temática | `NativeVolcanoesServices` para depósitos, regiões, tectônica, atmosfera e pressão | facade não duplica estado nem inventa classificação; input inválido falha; ausência de classifier ELDRITCH/ENDER mantém contribuição zero |
| RPG + Volcanoes nativo | reconciliação documental pós-merge da consolidação, authority e source cleanup não autorizado | DOCUMENTAL/CONTRATO DE CI; RUNTIME INALTERADO | PR #337 / `54b6cdc...`; `plans/00-project/volcanoes-consolidation.md`, guia de projetos e workflow de consolidação | completa como confirmação do estado já mergeado | **COBERTO POR SISTEMA UNIVERSAL** | avançar apenas o baseline documental; nenhuma A0200+ | documentação canônica + consolidation contract | texto/CI não cria classifier; source standalone permanece intacto e nenhuma limpeza destrutiva é inferida |
| Volcanoes nativo | geologia, estratos, depósitos, tectônica, terremotos, vulcanismo e geotermia | IMPLEMENTADO E CANÔNICO | source `eaddc323...` consolidado pela PR #308; `docs/archive/volcanoes/STATUS.md`; services nativos | completa pela progressão/simulação Volcanoes | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma A0200+; perks geológicas futuras apenas por query read-only comprovada | `GeologicalDepositSource`, `TectonicService`, `VolcanicRegionService` | árvore não produz minério, não escreve worldgen/ownership e não converte geologia em ELDRITCH/ENDER |
| Volcanoes nativo | atmosfera, respiração, O2, gases, fumaça/particulados, poluição e chuva ácida | IMPLEMENTADO E CANÔNICO | source `eaddc323...` + PR #308; `AtmosphereRuntime`, `RespirationModel`, pollution adapters | completa pelo provider ambiental | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma A0200+; não equiparar hazard a dano Eldritch/Ender | `AtmosphereState`/providers ambientais e adapters específicos | ausência/mismatch de proteção ou host produz resultado ambiental seguro/zero conforme contrato; nenhum outcome ELDRITCH/ENDER é sintetizado |
| Volcanoes nativo | pressão atmosférica/hidrostática, volumes selados e proteção/equipamento | IMPLEMENTADO E CANÔNICO | source `eaddc323...` + PR #308; `AtmosphericPressureRuntime`; Stage 05 | completa pelo provider ambiental | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma A0200+ | pressure services + protection transaction nativa | SPI/host incompatível falha fechado; pressão/dimensão não prova dano ENDER nem deslocamento próprio |
| Volcanoes nativo | integrações opcionais Create, Sable, Cold Sweat, Destroy, RNS, MineColonies e Curios | IMPLEMENTADAS SOB BOUNDARIES EXATOS | source `eaddc323...` + PR #308; Stage 06; `OptionalIntegrationBootstrap` | completa dentro da authority Volcanoes/hosts | **BRIDGE** | não conectar a A0200–A0209; preservar ownership de cada host | gates de versão/adapter específicos e serviços protegidos | host ausente/incompatível não carrega classes nem produz efeito; não usar teleporte/veículo/pressão/temperatura como receipt ENDER |
| Volcanoes nativo | hardening, performance, world-upgrade, proveniência e release gates | IMPLEMENTADO E CANÔNICO | source `eaddc323...` + PR #308; Stage 07 e workflows consolidados | completa como infraestrutura do subsistema | **NÃO DEVE SER INTEGRADO** | nenhuma perk | testes/admin/CI, sem outcome de combate | metadado, diagnóstico ou workflow não autoriza efeito de perk |
| Enshrouded | fog server-seeded e áudio/partículas do Shroud | APRESENTAÇÃO CANÔNICA CLIENT-SIDE | delta `391ea822...`→`5a25b03...`; Stages 07.02/07.03 | completa como experiência visual/sonora | **NÃO DEVE SER INTEGRADO** | nenhuma A0200+ | sync de apresentação; Shroud/Exposure permanecem provider-owned | VFX, som, fog, seed ou proximidade não viram outcome/classifier ELDRITCH/ENDER |
| Black Arcana | bridge RPG para hazard resistance, progression requirements e Mastery | IMPLEMENTADO E CANÔNICO NO DOMÍNIO BLACK ARCANA | delta `710077d...`→`e89df6d...`; adapters/progression/hardening do projeto | completa para os contratos Black Arcana | **PROGRESSÃO NATIVA AUTORITATIVA** | nenhuma equivalência automática A0200–A0204 | APIs Black Arcana-owned de Arcane/Corruption/Strain/Backlash | ausência de `BLACK_ARCANA_ELDRITCH_OUTCOME` mantém contribuição ELDRITCH zero; Backlash nunca dá offensive credit |
| Black Arcana | forecast server-authored de Arcane Resistance para HUD | IMPLEMENTADO READ-ONLY | `e89df6d...`; forecast de resistência | completa como apresentação/diagnóstico | **NÃO DEVE SER INTEGRADO** como authority | nenhuma perk | forecast → HUD, nunca cast/outcome authority | ausência/mismatch omite forecast; cliente não concede resistência, ação ou crédito |

### Resultado da matriz

- Nenhuma capability detectada exige uma 11ª perk neste lote.
- As quatro mudanças A0023/A0024/A0029/A0030 permanecem cobertas por suas perks originais; A0029/A0030 continuam corretamente inertes sem receipts provider-native.
- A consolidação do Volcanoes cria boundaries read-only importantes para futuros consumidores, mas nenhum deles publica semântica ELDRITCH/ENDER ou causalidade de deslocamento dimensional próprio.
- A incompatibilidade de Mastery addon permanece lacuna explícita e bloqueante; não foi escondida pelo avanço do baseline.

## RPG Skill Tree — bindings ainda ausentes para A0200–A0209

Na main auditada não foram encontrados:

- hostile/direct ELDRITCH classifier;
- direct/hostile ENDER classifier;
- `RPG_ELDRITCH_RESISTANCE` ou `RPG_ENDER_RESISTANCE` ligados a um `DamageMitigationResolver`;
- `HealingResolver` geral para penalidades de A0202/A0203;
- own-dimensional-displacement receipt causal;
- catálogo/runtime A0200–A0209.

### Incompatibilidade canônica de Mastery

`IronsSpellbookProgressionEvents.normalizeSchool` produz path para escola base Iron's e `namespace/path` para escola de addon. `MasteryLaneCatalog.ironsDiscipline` delega atualmente a validação que aceita apenas token sem barra. Assim, a forma normalizada de addon não é uma lane canônica válida no código auditado. A0202, A0203, A0204 e A0206 não podem usar aliases genéricos “Eldritch Mastery”/“Ender Mastery”. O Chat 2 deverá:

1. identificar os `SchoolType.getId()` exatos dos addons;
2. escolher uma representação canônica sem colisão;
3. reconciliar producer, catálogo, persistência e migração;
4. testar base Iron's e addon;
5. só então tornar os gates compráveis.

Lanes melee foram corrigidas para as ledgers reais: `epicfight:sword`, `epicfight:axe`, `epicfight:spear`, `epicfight:dagger`, `epicfight:heavy`, `combat:mace`, `combat:scythe` e `combat:fist` condicional. IDs `epic_*` são gateways, não ledgers.

## Impacto por família

| Família | Capability mínima | Estado |
|---|---|---|
| A0200–A0201 | hostile ELDRITCH classifier + mitigation bucket + outcome IDs | ausente; `UNAVAILABLE_NODE` |
| A0202–A0204 | direct ELDRITCH action + school lane exata + healing/categories/Specialist gate | incompleto; incompatibilidade de addon lane; `UNAVAILABLE_NODE` |
| A0205 | direct ENDER outcome + dependencies A0144/A0148–A0155 | ausente/upstream aberto; `UNAVAILABLE_NODE` |
| A0206 | Ender lane + causal self-displacement + action/target correlation | ausente; `UNAVAILABLE_NODE` |
| A0207–A0208 | hostile ENDER classifier + bucket + causal self-displacement | ausente; `UNAVAILABLE_NODE` |
| A0209 | ENDER producer + direct melee component hook + canonical lane | ausente; `UNAVAILABLE_NODE` |

## Baselines promovidos após disposição completa

Após classificar cada capability detectada, os heads abaixo se tornam baseline documental para mudanças posteriores:

- RPG Skill Tree: 54b6cdc1de923732c3ec7d99c660f8fdefdb0610
- Volcanoes standalone/source: eaddc3232dfc600780769f4a5e7e45ff1e50181c
- Enshrouded: 5a25b03a23ae81c111bbe1d5c23f85d8abd066ec
- Black Arcana: e89df6dc2c204c269d8f1811c6b3f309644c864a

Se qualquer main avançar antes do merge, o novo delta deve ser classificado antes do fechamento operacional.
