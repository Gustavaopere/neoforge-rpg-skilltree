# 16 — Capability Delta — A0131–A0140

Data de reconciliação: 2026-09-01.

Este documento executa o gate obrigatório **provider → árvore** e a contraprova **perk → provider** para o lote exato A0131–A0140.

## Heads frescos auditados

| Projeto/sistema | Head fresco | Delta desde o baseline anterior | Classificação para A0131–A0140 |
|---|---|---|---|
| RPG Skill Tree | `f055a65e73faf24ae5484780fc1ee4c2db0ef532` | gameplay auditado em `eed066e418a9968bcfbbd61df32dcfbf2683ca37`; `eed066e…→c89bc8d8…` foi CI/Sonar e `c89bc8d8…→f055a65e…` foi CodeQL + corpus/teste editorial TFC | **SEM DELTA DE CAPABILITY** |
| Volcanoes standalone/provenance | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem delta relevante | não é provider corporal/térmico direto deste lote |
| Enshrouded | `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2` | sem delta relevante | não aplicável a BodyCost/acclimation deste lote |
| Black Arcana | `d069190fedea1f7cb788a2c67e517eed6a9b3729` | 25 commits desde `e89df6d…`; gate preflight/forecast/presentation e hardening | pode fornecer contexto/classificação futura de cast, mas não METABOLIC/HYDRATION nem receipt pós-cast por si só |

## RPG Skill Tree

Na `main` auditada não existe implementação live de `BodyCostResolver` ou `AcclimationLedger`. P-0037/P-0038/P-0054 continuam sem binding runtime canônico para este recorte.

A reconciliação final foi feita sobre `main@f055a65e73faf24ae5484780fc1ee4c2db0ef532`. O avanço `eed066e…→c89bc8d8…` é de CI/Sonar; o avanço `c89bc8d8…→f055a65e…` adiciona CodeQL e conteúdo/teste editorial TFC do Compêndio. Nenhum desses deltas introduz hook corporal, thermal ledger, Stamina natural-regen adapter, encumbrance provider ou receipt de cast.

**Provider → árvore:** nenhuma capability nova a adicionar ao range A0131–A0140.

## Volcanoes

O repositório standalone permanece em `eaddc323…`; o runtime consolidado no RPG continua authority operacional. Geologia, atmosfera, pressão, calor, gases e hazards não são `METABOLIC`, `HYDRATION`, `ADVERSE_HOT/COLD` ou `player_encumbrance` por si só.

A relação futura correta para A0140 é:

`Volcanoes bounded local heat (future Stage 06 integration) -> Cold Sweat authority -> environmental thermal state -> A0140 consumer indireto`

A0140 nunca lê Volcanoes diretamente e não cria um segundo sistema térmico.

## Enshrouded

Head permanece `a08ff919…`; Shroud, Exposure, Flame, MagicResistance, Madness e corrupção de entidades não são BodyCost nem acclimation state deste lote.

**Classificação:** sem delta e sem provider novo.

## Black Arcana

Desde `e89df6d…`, Black Arcana avançou em Casting & UX/Stage 05A: preflight de gates previsíveis, forecast de Arcane Resistance, networking/presentation e testes.

O `ArcanaGatePreflight` é deliberadamente uma projeção **parcial e read-only**. `CLEAR` significa somente que nenhum gate de consulta previsível bloqueia; replay admission, target resolution, world policy e hazard preparation continuam em cast-time. Portanto:

- pode ser contexto futuro para diagnóstico/classificação do pipeline de cast;
- **não prova que o cast foi concluído**;
- não é receipt de `METABOLIC_CAST`;
- não cria `HYDRATION_CAST`;
- custo ARCANE/Mana/HP/strain continua recurso próprio, não FoodData.

A0131/A0132 só podem ativar quando existir receipt pós-cast/root real **e** parcela corporal causal positiva.

## Thirst Was Reclaimed 3.0.4 — prova de boundary A0140

No código 1.21.1 auditado:

1. `PlayerThirst.updateExhaustion(Player)` lê `player.getFoodData().getExhaustionLevel()` e calcula o delta;
2. `PlayerThirst.addExhaustion(Player,float)` aplica o delta ao estado hídrico;
3. nessa aplicação, o valor é multiplicado por `ThirstHelper.getExhaustionBiomeModifier(player)` e modificadores de proteção/resistência ao fogo;
4. `getExhaustionBiomeModifier` pode usar Cold Sweat BODY quando a compat está ativa.

Existe efeito ambiental real sobre a exhaustion hídrica, mas **não existe receipt separado já publicado** de `HYDRATION_ENVIRONMENTAL_HOT_SURCHARGE`. A0140 precisa de interceptação/adapter versionado que isole a parcela adicional dentro do mesmo settlement, antes do commit, sem polling ou refund posterior.

## Cold Sweat 2.4.2

É authority térmica. O Skill Tree pode consultar estados versionados por adapter, mas não escrever traits ou thresholds.

Estados semânticos do lote:

- `ADVERSE_HOT` / `ADVERSE_COLD`: condição corporal usada por A0135–A0138;
- `ENVIRONMENTAL_HOT` / `ENVIRONMENTAL_COLD`: condição de exposição ambiental do ledger A0140.

Eles são distintos e não compartilham cargas automaticamente.

## Epic Fight 21.17.3.1

É owner de Stamina. A0139 exige modulação específica da **regeneração natural**. O boundary versionado pré-aplicação ainda não está provado; por isso a perk inteira permanece indisponível. Active gains/refunds não podem ser reduzidos por aproximação.

## Matriz perk → provider

| Perk | Provider/owner principal | Binding secundário | Estado atual |
|---|---|---|---|
| A0131 | FoodData + futuro BodyCostResolver | cast provider/root pós-cast | unavailable: METABOLIC_CAST ausente |
| A0132 | TWR HYDRATION + BodyCostResolver | A0131 + cast root | unavailable: HYDRATION_CAST ausente |
| A0133 | futuro player_encumbrance provider | BodyCostResolver | unavailable: provider ausente |
| A0134 | TWR HYDRATION | A0133 + encumbrance provider | unavailable |
| A0135 | FoodData/BodyCostResolver | Cold Sweat ADVERSE_HOT + AcclimationLedger | unavailable: serviços ausentes |
| A0136 | TWR HYDRATION | A0135 + ADVERSE_HOT + ledger | unavailable |
| A0137 | FoodData/BodyCostResolver | Cold Sweat ADVERSE_COLD + AcclimationLedger | unavailable |
| A0138 | TWR HYDRATION | A0137 + ADVERSE_COLD + ledger | unavailable |
| A0139 | BodyCost/TWR + Epic Fight Stamina | natural regen seam | unavailable: seam não provada |
| A0140 | TWR environmental surcharge | Cold Sweat ENVIRONMENTAL_HOT + AcclimationLedger | unavailable: surcharge seam/ledger ausentes |

## Matriz provider → árvore

| Provider/capability | Perks alcançadas | Decisão |
|---|---|---|
| FoodData exhaustion causal | A0131/A0135/A0137/A0139 | somente via BodyCostResolver/action_id; não inferir cast ou thermal context |
| TWR HYDRATION same-action | A0132/A0136/A0138/A0139 | owner hídrico; adapter causal obrigatório |
| TWR environmental modifier | A0140 | real, porém sem receipt separado; precisa seam versionada |
| Cold Sweat BODY/environment | A0135–A0138/A0140 | read-only classifier; não mutar traits |
| Epic Fight natural Stamina regen | A0139 | binding obrigatório ainda ausente |
| Iron's/Ars | A0131/A0132 | classificadores de cast/recursos; não body cost |
| Black Arcana preflight | A0131/A0132 | read-only partial gate projection; não receipt pós-cast |
| future player_encumbrance | A0133/A0134 | ausente; não substituir por massa/inventário |
| Volcanoes thermal contribution | A0140 indireta | futuro Cold Sweat adapter; nunca provider direto da perk |
| Enshrouded | nenhuma neste lote | não aplicável |

## Cobertura e gaps

Todos os providers citados por A0131–A0140 estão classificados. Os gaps deliberados são:

- P-0037 / BodyCostResolver;
- P-0038 / AcclimationLedger;
- P-0054 / adapter térmico canônico;
- METABOLIC_CAST/HYDRATION_CAST;
- player_encumbrance + load surcharge receipts;
- Epic Fight natural regen seam;
- TWR environmental-hot surcharge seam.

Ausência de qualquer binding obrigatório mantém o node correspondente não comprável e legacy PP 0.

## Baseline promovido para próximo gate

- RPG Skill Tree: `f055a65e73faf24ae5484780fc1ee4c2db0ef532`.
- Volcanoes standalone/provenance: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`.
- Enshrouded: `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2`.
- Black Arcana: `d069190fedea1f7cb788a2c67e517eed6a9b3729`.

No próximo lote, os heads e `plans/STATUS.md` devem ser lidos novamente; estes SHAs são checkpoint, não autorização para assumir ausência de delta futuro.
