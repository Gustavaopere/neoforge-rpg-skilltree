# AUDITORIA — CHAT 1 — A0091–A0100

Data: 2026-08-31  
Escopo: **exatamente 10 perks consecutivas, A0091–A0100**.  
Responsabilidade: auditoria/design/documentação. **Nenhum runtime foi alterado neste Chat 1.**

## 1. Fontes e regra operacional

Aplicados os critérios de `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, o protocolo do Chat 1, os guias consolidados de Gameplay/Sistemas, Magia, Tecnologia e Projetos Próprios.

A cobertura foi feita nos dois sentidos obrigatórios:

1. `perk → provider`: identificar owner, hook, versão, gates e fallback de cada perk;
2. `provider → árvore`: verificar se capacidades reais dos guias/projetos próprios exigiam integração no lote.

Provider pertinente sem receipt/hook seguro fica `FAIL-CLOSED`; presença do mod, namespace, animação ou efeito visual nunca é prova suficiente.

## 2. Gate de delta dos projetos próprios

Baselines promovidos pelo lote A0081–A0090:

- RPG Skill Tree: `6975970d086d32985d83a0018c841cce9d1cbd63`.
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`.
- Enshrouded: `391ea82203d30cb392a3397f92e2a3cbe7fb6128`.
- Black Arcana: `710077da89da5eb4418d3ac676e148849727ff07`.

Freshness de abertura:

- RPG Skill Tree: `5098e38cbfb0e90d788de0722dd7e2f68753261d` — Stage 04.02 ganhou provenance persistente de bridge paga, cobrança única e refund exato na reconciliação de classes híbridas. É **progressão autoritativa**, não bônus de perk; nodes-bridge A0093/A0098/A0099 não podem duplicar esse ledger.
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c` — sem delta desde o baseline.
- Enshrouded: `6642d4ed14bbae2a771075ca466e6749ac8f7fb8` — delta de fog/render client-side e pequeno hardening de reload; nenhuma nova authority de gameplay para A0091–A0100.
- Black Arcana: `462c5c4af403629a7092129cf7f3070472f03e59` — hardening/testes de Backlash, resistência numérica e exclusão de offensive credit/mastery; reforça que `ARCANE_BACKLASH` é terminal/hazard e não fornece receipt defensivo novo ao lote.

Durante o fechamento, a `main` do RPG avançou primeiro para `5530667f5303c3f628ea9f69dd947dbfed888915` pelo fechamento Chat 3 A0021–A0030. A branch deste lote foi reconciliada explicitamente com esse head antes de editar arquivos compartilhados. Esse avanço não cria capability A0091–A0100.

A `main` avançou depois para `19f6fa749348c6c7dc46887787fa718242f09af0`. Entre `5530667...` e `19f6fa7...`, o compare mostrou dois lotes editoriais TFC do Compêndio e a centralização canônica de IDs de Mastery (`MasteryLaneCatalog` + migração de `MasteryPolicies`, PR #317). A centralização é capability arquitetural real, mas nenhuma perk A0091–A0100 consome Mastery lane/gate; não altera `rpgskilltree:physical`, guard stamina/recovery, `epicfight:stun_armor`, hostile receipt, sprint, `StationaryStateService` ou incoming critical decomposition. Classificação: **DELTA ARQUITETURAL SEM IMPACTO CONTRATUAL NESTE LOTE**. A branch foi reconciliada com esse head no commit `158ba7dcc75449fc3a7bbb8fd129b8997da237b3`.

A freshness final antes da PR é `main@cb95a527fa3b6138d674c74a09dc32d58885d523`. O avanço `19f6fa7... → cb95a52...` contém exclusivamente a integração SonarQube: `.github/workflows/sonarqube.yml` e plugin/configuração em `build.gradle`. Trata-se de infraestrutura CI/análise estática, sem nova capability jogável, provider, hook, gate, atributo, receipt ou alteração semântica de A0091–A0100. Classificação: **SEM DELTA DE GAMEPLAY / INFRAESTRUTURA SOMENTE**. A branch foi reconciliada com esse head no commit `48b0c1d132db8f8dc7b933d8a4646c65dec9bd2a` antes da revisão final.

## 3. Resultado por perk

| Código | Perk | Design | Estado runtime observado | Decisão Chat 1 |
|---|---|---|---|---|
| A0091 | Base Firme | APROVADO | modifier data-driven já existe | usar somente `minecraft:generic.knockback_resistance`; máx. +0,15 |
| A0092 | Resistência Física | APROVADO | fórmula/event bridge existem; tag física ainda não materializada | Chat 2 cria `rpgskilltree:physical`; sem inferência universal |
| A0093 | Guarda Econômica | APROVADO EM FAIL-CLOSED | fórmula pura + `FAIL_CLOSED_A0093`; sem hook causal de custo | `UNAVAILABLE_NODE`; purchase rejeitado sem gasto enquanto binding faltar |
| A0094 | Recuperação de Guarda | APROVADO EM FAIL-CLOSED | fórmula pura + `FAIL_CLOSED_A0094`; sem break/recovery receipt | `UNAVAILABLE_NODE` transitivo e próprio |
| A0095 | Tenacidade | APROVADO | código preparatório diverge do design fresco | remover dependência A0094; ADD_FLAT em `epicfight:stun_armor`, +0,25/rank |
| A0096 | Último Fôlego | APROVADO | fórmula pre-impact já existe | reutilizar exclusivamente classifier físico A0092; composição multiplicativa |
| A0097 | Primeira Defesa | APROVADO COM CORREÇÃO CAUSAL | runtime consome PRE e usa classifier hostil restritivo | reservation PRE → commit POST com dano efetivo; sem `Enemy`-only |
| A0098 | Defesa em Movimento | APROVADO | sprint vanilla server-side já é consumido | forced/passive movement excluído; ParCool somente por state receipt real |
| A0099 | Defesa Estacionária | APROVADO COM PENDÊNCIA TRANSVERSAL | serviço único existe; forced invalidation incompleta | reutilizar A0079/`StationaryStateService`; fechar `P-A0079-02`, sem detector paralelo |
| A0100 | Anti-Crítico | APROVADO EM FAIL-CLOSED | fórmula pura existe; sem incoming decomposition | `UNAVAILABLE_NODE`; exige crit/base/extra do mesmo evento causal |

## 4. Correções persistidas no Notion

Fetch fresco: **10/10**.

Páginas alteradas: **5/10** — A0092, A0096, A0097, A0098 e A0099.

Re-fetch pós-escrita: **5/5 PASS**.

Correções:

- A0092: deixou de presumir que a tag física já existe; seed conservador e exclusões passaram a ser contrato explícito para Chat 2.
- A0096: passou a declarar que reutiliza exclusivamente o classifier físico de A0092 e a composição `damage × (1−A0092) × (1−A0096)`.
- A0097: hostilidade por atacante causal `LivingEntity` não aliado e reservation→commit PRE/POST; cancelamento/dano zero não consome.
- A0098/A0099: nodes bridge não cobram/persistem/reembolsam custo de confluência; Stage 04.02/`ProgressionService` permanece authority exclusiva.
- A0099: dependência transversal `P-A0079-02` materializada; proibido detector alternativo.

A0091/A0093/A0094/A0095/A0100 já estavam semanticamente adequadas no Notion após a revisão fresca, embora A0095 revele drift do código preparatório.

## 5. Pipeline defensivo canônico

A0092, A0096, A0097, A0098, A0099 e futuro A0100 devem compor no mesmo pipeline de incoming damage, sem reducers paralelos.

Invariantes:

- classificação da fonte antes da mitigação específica;
- snapshot pre-impact para A0096;
- contributors percentuais aplicados uma vez por evento/root;
- A0092+A0096 multiplicativos, nunca soma de percentuais;
- A0097 só consome preparação após dano efetivo confirmado;
- ambiente/self/resource-cost/ally não recebem hostilidade por heurística;
- A0100, se um dia disponível, reduz apenas `additionalCriticalDamage` e não o dano base.

`BLOOD_MAGIC_COST`, `ARCANE_BACKLASH`, Shroud/Exposure, lava/gás/pressão e hazards de máquina continuam fora da classificação ofensiva/hostil quando o contrato específico não disser o contrário.

## 6. Provider coverage — Gameplay/Sistemas

### Minecraft / NeoForge

Owner positivo de A0091, A0092, A0096, A0097, sprint vanilla de A0098 e posição/lifecycle de A0099. `LivingIncomingDamageEvent` e `LivingDamageEvent.Post` formam o boundary causal defensivo.

### Epic Fight 21.17.3.1

- A0093/A0094: owner de guarda/stamina e guard-break, mas nenhum extension point público/versionado seguro foi comprovado para modificar o débito real antes do consumo ou a recuperação pós-break. Resultado correto: unavailable.
- A0095: owner real de `epicfight:stun_armor` e da fórmula nativa de stun reduction. A perk apenas injeta atributo estável; não cria interrupção linear própria.
- A0100: não foi comprovado receipt incoming com `critical + base + extra`; não presumir.

### ParCool 4.0.0.3 / Epic ParCool 21.0.0

Pertinentes apenas a A0098 se um adapter provar estado mecânico server-authoritative de movimento autopropelido. Animação, câmera ou velocidade isolada não contam.

### Apothic Attributes 2.10.1 / Pufferfish's Attributes 0.8.3

Podem compor atributos normais pela stack quando pertinente, mas não são promovidos a providers genéricos de incoming critical decomposition. A0100 continua unavailable sem receipt específico.

## 7. Provider coverage — Magia e tecnologia

Nenhum mod mágico/tecnológico auditado cria owner positivo adicional para as dez perks deste lote.

Regras negativas relevantes:

- magia, DoT ou school não transformam dano em físico sem classificação explícita;
- Black Arcana `BLOOD_MAGIC_COST` é custo e `ARCANE_BACKLASH` é hazard terminal;
- Enshrouded Shroud/Exposure/Madness não viram hostile living-attacker receipt;
- Volcanoes lava/calor/gás/pressão não viram ataque físico hostil por analogia;
- Create/TFMG/contraptions/veículos não contam como self-propelled sprint nem como stationarity válida por ausência de deslocamento local aparente;
- fake players, máquinas, summons e indiretos não transferem autoria sem adapter causal explícito.

Conclusão provider→árvore: **NÃO DEVE SER INTEGRADO** onde não há semântica causal comprovada.

## 8. Divergências concretas do código preparatório

1. **A0092:** `PHYSICAL_DAMAGE` é consultada, mas a materialização data/tag ainda precisa existir e ser validada.
2. **A0095:** `NotionCombatPerkCatalog` ainda exige A0094≥1 e `A0081A0100CombatPolicy.interruptionMultiplier` modela reducer genérico; ambos divergem do design fresco. `a0081_a0100.json` ainda não contém node-effect de `epicfight:stun_armor`.
3. **A0097:** `consumeOpeningDefense(...)` ocorre no PRE; deve virar reservation→commit no POST com dano efetivo. O helper hostil atual restringe a `Enemy || Player`, também incorreto.
4. **A0099:** sampler fallback passa `forcedTransition=false`; isso não fecha `P-A0079-02`.
5. **A0093/A0094/A0100:** constantes `FAIL_CLOSED_*` protegem efeito, mas não provam purchase fail-closed. O seam existente `requirementsSatisfied` no `NodePurchaseRequestProcessor`/`NodePurchaseMutationService` deve rejeitar aquisição antes do gasto.

## 9. Pendências destinadas ao Chat 2

- `P-A0091-01`: validar modifier idempotente, cap próprio e respec/remove.
- `P-A0092-01`: materializar `rpgskilltree:physical` com seed conservador e testes de exclusão.
- `P-A0092-02`: consolidar classifier/ordem/dedup do pipeline defensivo.
- `P-A0093-01`: `requirementsSatisfied=false` enquanto hook causal de guard stamina faltar; sem refund heuristic.
- `P-A0094-01`: availability transitiva A0093 + receipt real de guard-break/recovery; sem animação/cooldown inventado.
- `P-A0095-01`: remover dependência stale A0094 e alinhar catálogo/tree/tests ao Notion fresco.
- `P-A0095-02`: adicionar binding ADD_FLAT `epicfight:stun_armor`, +0,25/rank, versão exata, lifecycle idempotente.
- `P-A0096-01`: reutilizar exclusivamente classifier A0092 + snapshot pre-impact e composição multiplicativa.
- `P-A0097-01`: reservation→commit causal, rollback em cancel/zero e dedup por root/evento.
- `P-A0097-02`: remover `Enemy`-only e compartilhar classificação de atacante causal não aliado.
- `P-A0098-01`: sprint vanilla seguro + forced/passive exclusions; ParCool somente com adapter real.
- `P-A0098-02`: bridge PP sem tocar provenance Stage 04.02.
- `P-A0099-01`: fechar/reutilizar `P-A0079-02` no detector único.
- `P-A0099-02`: bridge PP e classifier hostil compartilhados.
- `P-A0100-01`: purchase unavailable sem incoming decomposition real.
- `P-A0100-02`: preservar ausência de heurística e dedup por evento se provider futuro existir.
- `P-A0091-0100-TEST-01`: harness transversal para purchase gates, attributes, damage tags, PRE/POST causalidade, sprint/stationary lifecycle, multiplayer, provider absent/present e dedicated server.

## 10. Testes reservados ao Chat 3

Cada dossiê contém sua matriz específica. O lote exige no mínimo:

- unit tests de fórmulas/gates/dedup;
- NeoForge GameTests de incoming damage real, cancel/zero, lifecycle e atributos;
- provider-present/provider-absent para Epic Fight onde aplicável;
- purchase rejection sem gastar PP em A0093/A0094/A0100;
- regression de A0095 sem A0094 e com Stun Armor real;
- forced movement/teleport/dimension/mount para A0099/A0079;
- multiplayer isolation;
- NeoForge build, JAR verification e dedicated-server smoke.

Chat 1 não executa essa bateria nem declara `IMPLEMENTAÇÃO CONFIRMADA`.

## 11. Resultado dos critérios

Os dez dossiês passam no **design**, inclusive A0093/A0094/A0100 cujo resultado correto é `UNAVAILABLE_NODE / FAIL-CLOSED` até API suficiente existir.

Nenhum no-op comprável é considerado implementação válida. Nenhum bônus genérico pode substituir provider/hook ausente.

## 12. Encerramento Chat 1

**A0091–A0100: DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

O Chat 2 deve continuar nesta mesma branch/PR, implementar exatamente os contratos e registrar divergências reais sem redesign silencioso. Chat 1 não faz merge.
