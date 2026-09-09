# AUDITORIA — CHAT 1 — A0310–A0319

Data/freshness: 2026-09-05.

Escopo: **exatamente 10 perks consecutivas, A0310–A0319**.

Responsabilidade: auditoria, correção de design, integração documental e handoff. Nenhum runtime de perk ou bateria final de testes pertence a este Chat 1; Chat 1 não faz merge.

## 1. Determinação do lote

O lote anterior A0300–A0309 já está formalmente fechado pelo Chat 1 na PR #395, branch `docs/chat1-a0300-a0309-audit`, com dez dossiês e estado `DESIGN APROVADO`. Busca por branch/conteúdo não encontrou ciclo A0310 já iniciado. Portanto:

- **INÍCIO: A0310**
- **FIM: A0319**

A0320+ permanece fora do escopo.

## 2. Fontes obrigatórias e modlist

Foram cruzados integralmente os snapshots consolidados obrigatórios do projeto:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- protocolo `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`.

Antes da auditoria, a modlist foi conferida na File Library (`modlist.txt`, 2026-08-30) e no banco Notion da Auditoria Mestre. O delta Mobstein 5.4.4 continua registrado; não possui relação tecnológica nem hook legítimo neste lote.

Reconciliado para A0319: a authority de presença/JAR mostra **ParCool 4.0.0.3** e **Epic ParCool 21.0.0**; parágrafo histórico do guia que ainda cite ParCool 4.0.0.2 não prevalece sobre `CURRENT-MODLIST`/modlist atual.

## 3. Notion

- Fetch inicial das perks: **10/10 PASS**.
- Páginas alteradas: **2/10** — A0318 e A0319.
- Re-fetch pós-escrita das páginas alteradas: **2/2 PASS** em 2026-09-05.

### A0318 — correção

O efeito/escalonamento já dizia +2% por rank, mas Gate/Regra ainda continham +3%/+6%/+9%. Corrigido para **+2/+4/+6/+8/+10%**, `ADD_MULTIPLIED_BASE`, uma única contribuição em vanilla `MOVEMENT_SPEED`.

### A0319 — correção

O efeito/escalonamento dizia STAMINA −3%/rank e METABOLIC −2%/rank, enquanto Hook/Regra mantinham −4% genérico e versões antigas. Corrigido para:

- STAMINA ×0,97/0,94/0,91/0,88;
- METABOLIC ×0,98/0,96/0,94/0,92;
- ParCool 4.0.0.3 / Epic ParCool 21.0.0;
- sem pós-reembolso/polling/injection.

## 4. Dependency closure

### Specialist Natureza — A0310–A0317

A0183 Maestria de Natureza é Gate C e continua `UNAVAILABLE_NODE` transitivamente por A0182. A0310–A0317 pertencem ao Specialist Natureza e possuem dependências locais adicionais. Portanto **A0310–A0317 = `UNAVAILABLE_NODE`** no snapshot atual. Abrir A0183 no futuro não materializa classifiers/receipts locais ausentes.

### AGILITY — A0318

A0318 não depende de Specialist. O runtime `AttributeNodeEffectRuntime` + vanilla `Attributes.MOVEMENT_SPEED` já fornece boundary implementável para o contrato aprovado. **A0318 = DESIGN IMPLEMENTÁVEL**.

### A0319

A0319 depende de A0318, porém permanece `UNAVAILABLE_NODE` por capability: não há receipt/precommit modifier seguro para isolar custo causal de sprint em STAMINA ou FoodData/exhaustion. Node não pode ser comprado como no-op.

Resultado: **10/10 designs aprovados; 9/10 unavailable/fail-closed; 1/10 implementável (A0318).**

## 5. Resultado por perk

| Código | Perk | Decisão | Estado atual | Principal boundary/blocker |
|---|---|---|---|---|
| A0310 | Marca Selvagem | APROVADA | `UNAVAILABLE_NODE` | A0183 + `NATURAL_FORM_STATE_V1`/mapping e trait `OFFENSIVE_SCALABLE` |
| A0311 | Veneno por Acúmulo | APROVADA | `UNAVAILABLE_NODE` | A0183/A0301 + NATURE action/application ledger POISON |
| A0312 | Espinhos Reativos | APROVADA | `UNAVAILABLE_NODE` | A0183/A0309 ou A0303 + direct hostile melee/derived/boss/PvP |
| A0313 | Aura de Pólen | APROVADA | `UNAVAILABLE_NODE` | A0183/A0304/A0306 + direct NATURE_HEALING/companion/spatial receipt |
| A0314 | Raízes Prensivas | APROVADA | `UNAVAILABLE_NODE` | A0183/A0307 + NATURE action ledger/control commit/boss-elite |
| A0315 | Avatar Selvagem | APROVADA | `UNAVAILABLE_NODE` | A0183/A0310 + explicit primary natural-form category |
| A0316 | Sangue Verde | APROVADA | `UNAVAILABLE_NODE` | A0183/A0303 + shared POISON reducer/actual-prevented ledger |
| A0317 | Bosque Ambulante | APROVADA | `UNAVAILABLE_NODE` | A0183 + `NATURAL_TERRITORY_V1`/same-space/assist ledger |
| A0318 | Passo Leve | APROVADA | IMPLEMENTÁVEL | `AttributeNodeEffectRuntime` + vanilla MOVEMENT_SPEED |
| A0319 | Sprint Econômico | APROVADA | `UNAVAILABLE_NODE` | sprint STAMINA/METABOLIC causal precommit receipt ausente |

## 6. Nove eixos obrigatórios

| Critério | Status do lote |
|---|---|
| 1. Dependências e bloqueios | ✅ closure + local blockers preservados |
| 2. Integrações globais/corpo/recursos | ✅ POISON, healing, morph, stamina e metabolic mantêm authorities distintas |
| 3. Qualidade/identidade | ✅ ranked small coerente em A0318/A0319; Notables/Keystones/Capstone alteram decisões |
| 4. Ramificação/distância/topologia | ✅ Specialist Nature e AGILITY não foram misturados; A0317 fecha o ramo Nature |
| 5. Especializações | ✅ A0310–17 pertencem ao Specialist Nature; A0318–19 pertencem AGILITY |
| 6. PT-BR | ✅ player-facing permanece PT-BR |
| 7. Notion completo | ✅ 10/10 revisadas; 2/2 alterações persistidas |
| 8. NeoVitae removido | ✅ nenhuma dependência ativa/residual usada |
| 9. Cobertura modlist/providers | ✅ modlist File Library+Notion, quatro projetos próprios e providers periféricos pertinentes revisados |

## 7. Provider-native e integrações globais

### Morph / fauna / Nature

Identity2/Woodwalkers e outros morph providers preservam forma, stats e abilities. A árvore só consome uma classificação explicitamente mapeada. `NATURAL`, primary category, companion e territory não podem ser inferidos por entity type, namespace, biome, VFX ou aparência.

### POISON

POISON é uma família causal real; A0311 aplica/renova pelo provider, A0312 pode solicitar proc e A0316 reduz dano subsequente. O mesmo application/damage outcome não pode ser contado por dois adapters. A0316 aplica primeiro a resistência canônica e depois seu segundo reducer, calculando cura apenas do dano efetivamente prevenido pelo segundo estágio.

### Healing

A0313 requer direct `NATURE_HEALING`; derived aura heals não se tornam direct triggers. A0316 heal-derived segue o mesmo princípio. `LivingHealEvent` sozinho não prova category/source.

### Spatial/sublevels

Sable/Aeronautics só fornecem transform/contexto. Same-space e território são resolvidos server-side; parent-Level proximity não serve como aproximação.

### Stamina/metabolic

Epic Fight 21.17.3.1, ParCool 4.0.0.3 e Epic ParCool 21.0.0 podem participar da stamina, mas A0319 não pode deduzir custo por animação/deslocamento. FoodData/exhaustion é recurso distinto. Cada debit causal recebe no máximo uma contribuição.

### Tecnologia

Nenhum mod tecnológico é provider positivo do lote. FE, cinética, veículo/sublevel e machine automation não viram stamina, Nature territory, Poison, heal ou morph semantics.

## 8. Projetos próprios e delta bidirecional

Detalhamento completo: `guides/projects/24-capability-delta-a0310-a0319.md`.

Resumo:

- **RPG Skill Tree:** `5213d068...` → `8e33da13...`; avanços A0031–A0040 são cobertos pelas perks existentes; Compêndio é sistema universal/read-only para este contexto; consolidação Volcanoes relocou authority, não criou semântica Nature/sprint nova. Nenhum delta abre A0310–17/A0319; A0318 usa primitive preexistente.
- **Volcanoes standalone:** `29835297...` → mesmo tombstone. Sem delta no standalone; authority viva agora está consolidada no RPG Skill Tree e continua separada de Nature territory/POISON.
- **Enshrouded:** `03db9404...` → `67f4ab90...`; Stage 08.05 fechou necromancy flavor como **NO-OP intencional**, sem adapter Goety/Malum/Eidolon. Classificação: `NÃO DEVE SER INTEGRADO` ao lote.
- **Black Arcana:** `6b77b5c0...` → `8c7ea474...`; novos Blood & Curses e Souls & Death são capabilities reais. Mantêm `PROGRESSÃO NATIVA AUTORITATIVA`; eventual bridge à família BLOOD do RPG exige adapter semântico versionado. Soul Anchor/Spirit Sight não são duplicados pela árvore. Malum death→spirit e Eidolon player-specific anchor unlock permanecem `SEM HOOK SEGURO`/fail-closed onde o próprio contrato documenta falta de causalidade/identidade.

Todos os deltas receberam disposição explícita; o checkpoint pode avançar sem capability órfã.

## 9. Causalidade, deduplicação e anti-abuso

Regras transversais:

1. uma root action/outcome recebe no máximo uma contribuição da mesma perk;
2. derived outcomes carregam parent/root identity e não reentram como ação direta;
3. ledgers são bounded por owner/target/window e não contam replay;
4. POISON application, pulse e damage são identidades diferentes e não transferem autoria por proximidade;
5. companion/morph/territory exigem identity explícita;
6. DR/root só avança após commit real;
7. healing credit usa cura/dano prevenido efetivos, não requested values;
8. same-space/sublevel é server-authoritative;
9. stamina/metabolic só modificam débito causal precommit;
10. reload/respec/dependency loss limpa/reconcilia estado sem farm.

## 10. Testes destinados ao Chat 3

Além dos testes individuais dos dez dossiês, validar transversalmente:

- purchase fail-before-spend para os nove unavailable nodes;
- A0318 ranks/modifier/lifecycle idempotente;
- allocation legado unavailable = 0 PP em gates e respec/migration segura;
- provider absent/version mismatch e ambiguous classifier;
- direct vs derived provenance;
- dedup owner+target/root/pulse;
- same-space/sublevel;
- boss/PvP/elite special policies;
- POISON reducer order e actual-prevented accounting;
- nenhuma geração gratuita de heal/stamina/resource/Mastery;
- multiplayer e dedicated server.

Chat 1 **não executou** essa bateria; execução/validação pertence ao Chat 3.

## 11. STATUS e concorrência documental

`perks/STATUS.md` possui histórico amplo e há PRs de lotes anteriores ainda abertas. Para não sobrescrever estados concorrentes, este ciclo materializa o estado do lote em `audits/STATUS-A0310-A0319.md`, seguindo o padrão já usado por A0300–A0309. O tracker é a atualização de status do lote nesta branch e deve ser reconciliado no `STATUS.md` agregado quando a cadeia de PRs for serializada.

## 12. Handoff Chat 2

Chat 2 deve continuar **a mesma branch/PR** criada por este ciclo.

- Não redesenhar A0310–A0319.
- Preservar nove nodes unavailable até capabilities reais existirem.
- A0318 deve usar `AttributeNodeEffectRuntime`; não criar listener/motor paralelo.
- Não inferir Nature/morph/territory/companion/boss por heurística.
- Não fabricar sprint-cost receipts nem reembolsar custo pós-commit.
- Divergência real de provider que altere identity/effect/gate/topologia/authority volta ao Chat 1.

## 13. Estado final Chat 1

**A0310–A0319 — DESIGN APROVADO / LOTE FECHADO PELO CHAT 1.**

Chat 1 para aqui: não implementa runtime, não executa bateria final, não declara `IMPLEMENTAÇÃO CONFIRMADA`, não faz merge e não inicia A0320+.