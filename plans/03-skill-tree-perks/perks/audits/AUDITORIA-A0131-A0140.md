# Auditoria Chat 1 — A0131–A0140

**Intervalo:** A0131–A0140, exatamente 10 perks consecutivas.  
**Data:** 2026-09-01.  
**Base de gameplay/runtime auditada:** `main@eed066e418a9968bcfbbd61df32dcfbf2683ca37`.  
**Freshness final reconciliada:** `main@f055a65e73faf24ae5484780fc1ee4c2db0ef532`; `eed066e…→c89bc8d8…` contém apenas CI/Sonar e `c89bc8d8…→f055a65e…` contém CodeQL + corpus/teste editorial TFC do Compêndio, sem capability nova para este lote.  
**Responsabilidade:** auditoria, design, integração e documentação. Nenhum runtime é implementado pelo Chat 1 e nenhum merge pertence a este chat.

## Fontes obrigatórias

Foram reconsultados como base operacional:

- `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`;
- `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`;
- `GUIA-COMPLETO-MODS-DE-MAGIA.md`;
- `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`;
- `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`;
- `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`;
- `STATUS.md`, `plans/STATUS.md`, dossiês predecessores e código/API real dos providers quando o contrato exigiu prova.

## Resultado executivo

| Código | Perk | Resultado Chat 1 | Estado runtime esperado hoje |
|---|---|---|---|
| A0131 | Economia Metabólica: Conjurar | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: `METABOLIC_CAST`/BodyCost ausentes |
| A0132 | Conservação Hídrica: Conjurar | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: A0131 + `HYDRATION_CAST` ausentes |
| A0133 | Economia Metabólica: Carregar Peso | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: provider de `player_encumbrance` ausente |
| A0134 | Conservação Hídrica: Carregar Peso | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: A0133 + HYDRATION causal de carga ausentes |
| A0135 | Economia Metabólica: Trabalhar Em Calor | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: BodyCostResolver/AcclimationLedger/ADVERSE_HOT ausentes |
| A0136 | Conservação Hídrica: Trabalhar Em Calor | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: A0135 + TWR-HYDRATION/ledger ausentes |
| A0137 | Economia Metabólica: Trabalhar Em Frio | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: BodyCostResolver/AcclimationLedger/ADVERSE_COLD ausentes |
| A0138 | Conservação Hídrica: Trabalhar Em Frio | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: A0137 + TWR-HYDRATION/ledger ausentes |
| A0139 | Metabolismo Eficiente | DESIGN APROVADO EM FAIL-CLOSED | `UNAVAILABLE_NODE`: natural Stamina regen seam não provada |
| A0140 | Adaptação do Deserto | DESIGN APROVADO APÓS HARDENING | `UNAVAILABLE_NODE`: ledger + environmental state + TWR hot-surcharge seam ausentes |

`UNAVAILABLE_NODE` significa: compra falha antes do gasto; allocation legado conta 0 PP para gates e permanece reembolsável/migrável. Nenhum node recebe efeito substituto.

## Notion

- Fetch fresco: **10/10**.
- Páginas já corretamente provider-gated e sem mutação: A0131, A0132, A0133, A0134, A0139.
- Páginas endurecidas neste ciclo: **A0135, A0136, A0137, A0138, A0140**.
- Re-fetch pós-escrita: **5/5 PASS**.

### Hardening A0135–A0138

A fonte anterior distinguia corretamente estados térmicos e receipts, mas permitia interpretar a ausência dos serviços globais como simples no-op por evento. Isso violaria o invariant de purchase.

Foi congelado:

- BodyCostResolver/AcclimationLedger/adapter térmico obrigatório ausente => node não comprável;
- A0136/A0138 herdam availability dos predecessors e do adapter TWR;
- node predecessor fail-closed não satisfaz requisito de ranks capability-eligible;
- somente depois que os bindings globais existirem, ausência de estado/carga/receipt numa ação específica apenas omite o proc.

### Hardening A0140

O código 1.21.1 do Thirst Was Reclaimed foi auditado diretamente. `PlayerThirst.updateExhaustion()` deriva sede do delta de `FoodData.exhaustion`; `PlayerThirst.addExhaustion(...)` multiplica o valor pelo `ThirstHelper.getExhaustionBiomeModifier(player)` e demais modificadores. Não existe hoje um receipt separado e pronto de `HYDRATION_ENVIRONMENTAL_HOT_SURCHARGE`.

Consequência: A0140 fica não comprável até existir seam/adapter versionado que isole causalmente somente o surcharge hídrico ambiental quente. O componente histórico `THERMAL_PHYSIOLOGY_HOT` é independente e opcional: depois que o componente HYDRATION principal for implementável, ausência do mapper fisiológico apenas omite essa parcela e não bloqueia a perk inteira.

## Providers e versões relevantes

- Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21.
- Cold Sweat 2.4.2 — authority térmica; leitura, não segundo owner de metabolismo/hidratação.
- Thirst Was Reclaimed 3.0.4 — owner de HYDRATION.
- Epic Fight 21.17.3.1 — owner de Stamina; boundary específico de natural regen para A0139 ainda não provado.
- Iron's Spells 'n Spellbooks 3.16.3 e Ars Nouveau 5.13.1 — classificadores/owners de recursos mágicos, não FoodData.
- RPG Skill Tree — futuro BodyCostResolver/AcclimationLedger; ambos ausentes da `main` auditada.

## Pipeline corporal canônico

```text
action_id legítima
→ owner corporal produz quote/receipt positivo e causal
→ METABOLIC reducers elegíveis
→ cap METABOLIC 30%
→ settlement uma vez
→ se TWR produzir HYDRATION para a mesma action_id
→ HYDRATION reducers elegíveis
→ cap HYDRATION 30%
→ commit uma vez
```

A0135–A0138 acrescentam contexto térmico read-only e carga de aclimatação; não alteram o owner do custo. A0140 exige uma parcela ambiental explícita, não estimada por diferença de barra.

## Decisões por família

### Conjuração — A0131/A0132

Um cast legítimo não implica custo de fome/sede. Iron's/Ars/Black Arcana podem classificar a ação, mas Mana, Source, Soul Energy, HP/sangue, cooldown ou Arcane Strain não viram METABOLIC/HYDRATION.

O delta fresco de Black Arcana adicionou `ArcanaGatePreflight`, forecast e hardening de Casting & UX. `ArcanaGatePreflight` é deliberadamente **read-only e parcial**: `CLEAR` significa somente que nenhum gate previsível de consulta bloqueia naquele momento, não que o cast ocorrerá. Portanto não é receipt pós-cast nem owner corporal.

### Encumbrance — A0133/A0134

Não existe provider aprovado de `player_encumbrance`. Massa de contraption/Create Aeronautics, Sable Weight, inventário, slots, Armor, movement speed e aparência não podem ser usados como heurística.

### Aclimatação corporal — A0135–A0138

`ADVERSE_HOT`/`ADVERSE_COLD` são estados corporais server-authoritative futuros do adapter Cold Sweat. São distintos de `ENVIRONMENTAL_HOT`/`ENVIRONMENTAL_COLD`, WORLD/bioma, FIRE/ICE damage ou thresholds internos do provider.

### Disciplina — A0139

O benefício −12% METABOLIC/HYDRATION é inseparável do tradeoff −8% natural Stamina regen. Sem boundary versionado pré-aplicação da regen natural, a perk inteira é indisponível. Refunds, active gains, mana, movement ou exhaustion não substituem Stamina.

### Deserto — A0140

`ENVIRONMENTAL_HOT` usa 0–5 cargas próprias: +1 após 10 min consecutivos; fora do estado −1 após 20 min; em `ENVIRONMENTAL_COLD` −1 a cada 5 min; sem progresso offline. Cargas não são consumidas pelo proc. O efeito principal só reduz surcharge HYDRATION quente real isolado pelo TWR adapter.

Volcanoes permanece indireto: futura contribuição vulcânica/geotérmica alimenta Cold Sweat; A0140 não lê Volcanoes diretamente e não cria segundo sistema térmico.

## Capability delta dos quatro projetos próprios

Arquivo canônico: `guides/projects/16-capability-delta-a0131-a0140.md`.

- RPG Skill Tree: gameplay auditado em `eed066e…`; freshness final reconciliada `f055a65e…`; deltas intermediários somente CI/Sonar, CodeQL e corpus/teste editorial TFC, **SEM DELTA DE CAPABILITY**.
- Volcanoes: `eaddc323…`, sem provider corporal novo para este lote.
- Enshrouded: `a08ff919…`, sem BodyCost/acclimation deste lote.
- Black Arcana: `d069190…`, delta funcional de cast preflight/forecast/presentation; **PODE ALIMENTAR FUTURO CLASSIFIER DE CAST, MAS NÃO DEVE SER TRATADO COMO RECEIPT PÓS-CAST NEM COMO METABOLIC/HYDRATION**.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | availability estrutural/transitiva; 10/10 indisponíveis hoje |
| Integração global | PASS | BodyCost/TWR/Cold Sweat/Epic Fight ownership separado |
| Qualidade/identidade | PASS | cast, encumbrance, adverse thermal e environmental thermal distintos |
| Topologia | PASS | bridge ARCANE, branches SURVIVAL e Keystone preservados |
| Especializações | PASS/N/A | PP inválido não conta; bridge não double-count |
| PT-BR | PASS | identidade/nomenclatura preservadas |
| Notion | PASS | 10/10 fetch; 5 mutadas; 5/5 re-fetch |
| NeoVitae | PASS/N/A | ausente/removido |
| Cobertura providers | PASS | quatro projetos próprios + TWR/Cold Sweat/Epic Fight/magia classificados |

## Checklist técnico — 18 critérios

| # | Critério | Resultado |
|---:|---|---|
| 1 | efeito real | PASS — somente sobre parcelas reais/causais |
| 2 | provider-native first | PASS |
| 3 | sem mecânica inventada | PASS — sem fome/sede/peso/surcharge fabricados |
| 4 | fail-closed | PASS — 10/10 indisponíveis hoje |
| 5 | fallback mantém identidade | PASS |
| 6 | Mastery por feitos | N/A |
| 7 | anti-farm | PASS/N/A — cargas temporais bounded e sem offline |
| 8 | atribuição causal | PASS — `action_id`/receipt explícitos |
| 9 | sem pipelines duplicados | PASS — BodyCost/TWR/thermal owners preservados |
| 10 | custos reais | PASS |
| 11 | sem geração gratuita | PASS |
| 12 | read-only correto | PASS — Cold Sweat/Black Arcana preflight não ganham authority indevida |
| 13 | versionamento | PASS |
| 14 | coerência estrutural | PASS — ranks/custos/prereqs preservados |
| 15 | dependências semânticas | PASS — hydration/keystone transitivos |
| 16 | sem sobreposição indevida | PASS — ADVERSE vs ENVIRONMENTAL, METABOLIC vs HYDRATION |
| 17 | implementável posteriormente | PASS — owners/hooks/order/fallback/testes fechados |
| 18 | verificação pós-escrita | PASS — 5/5 Notion |

## Handoff Chat 2

Não implementar runtime deste lote enquanto os blockers estruturais permanecerem ausentes. Quando os predecessores e serviços globais chegarem à `main`, Chat 2 deve:

1. manter unavailable qualquer node cujo binding real continue ausente;
2. implementar um único BodyCost pipeline e adapters versionados, não reducers paralelos;
3. manter Cold Sweat read-only e TWR como owner de HYDRATION;
4. exigir receipt pós-cast real para A0131/A0132; preflight Black Arcana não basta;
5. não criar provider de encumbrance por heurística;
6. tratar A0139 all-or-nothing com natural Stamina regen;
7. para A0140, interceptar/isolar o surcharge TWR com causalidade antes de reduzir; nunca estimar por polling.

Divergência semântica de API deve voltar ao Chat 1; não redesenhar silenciosamente.

## Testes transversais Chat 3

- purchase fail-before-spend e legacy PP 0 para todos os blockers;
- provider/version present/absent e remoção durante runtime;
- action identity/dedup/rollback;
- caps METABOLIC e HYDRATION de 30% independentes;
- cast cancel/preflight sem sucesso não proca;
- encumbrance heuristics negativas;
- ADVERSE_HOT/COLD distintos de ENVIRONMENTAL_HOT/COLD;
- ledger 0–5, 10/20/5 min, sem offline, lifecycle completo;
- A0139 tradeoff somente natural regen;
- TWR hot surcharge isolado sem direct thirst write;
- physiology A0140 opcional fail-closed;
- multiplayer, reload, respec, logout/dimensão/restart;
- unit/JUnit, NeoForge GameTests, validators, build, JAR e dedicated-server smoke quando houver código.

## Estado final Chat 1

**DESIGN APROVADO / LOTE A0131–A0140 FECHADO PELO CHAT 1 / 10/10 UNAVAILABLE_NODE NO SNAPSHOT ATUAL / AGUARDANDO FUTURO HANDOFF AO CHAT 2 APÓS PREDECESSORES E CAPABILITIES.**

Chat 1 não faz merge. A0141+ não pertence a este ciclo.
