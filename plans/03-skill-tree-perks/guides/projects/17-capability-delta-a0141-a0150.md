# 17 — Capability Delta — A0141–A0150

Data de reconciliação: 2026-09-01.

Este documento executa o gate obrigatório **provider → árvore** e a contraprova **perk → provider** para o lote exato A0141–A0150. A descoberta de capability não altera a regra de dez perks; gaps externos são classificados e ficam para ciclo posterior quando necessário.

## Heads frescos auditados

| Projeto | Baseline anterior | Head fresco | Delta relevante para este lote |
|---|---|---|---|
| RPG Skill Tree | `f055a65e73faf24ae5484780fc1ee4c2db0ef532` | `452e8b23e374179c1f616f9beedce6e3dea66ef5` | avanço de hardening/docs/CI e política de origem de Mastery do Iron's; nenhuma nova authority de mana/cast/cooldown para A0144–A0150 |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | **SEM DELTA** |
| Enshrouded | `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2` | `e00e6037d7265eb6ab6b3b877428ddfbc4eaec81` | Stage 08.01 fechou bridge com Ars Zero para manifestação do Lich; não cria mana/potência/cast-time/cooldown do jogador |
| Black Arcana | `d069190fedea1f7cb788a2c67e517eed6a9b3729` | `e573a0edfcb69d09e423b60ad75ab71b9d8e70c5` | QA/fixture/artifact/presentation de Stage 05/05A; sem nova capability de gameplay para o lote |

## RPG Skill Tree

O delta desde `f055a65e…` não cria segundo recurso ARCANE nem nova API pública que substitua os providers. `IronMasterySourcePolicy` endurece somente a elegibilidade de origem de **Mastery**: não concede authority sobre mana, spell power, cast-time, cooldown ou interrupção.

Classificação provider → árvore:

- política de origem de Mastery do Iron's: **PROGRESSÃO NATIVA AUTORITATIVA / COBERTA PELO SISTEMA DE MASTERY**, sem nova perk neste lote;
- hardening do Volcanoes dentro do RPG: **COBERTO POR SISTEMA UNIVERSAL**, sem novo node ARCANE/SURVIVAL;
- CI/docs/catalog: **NÃO DEVE SER INTEGRADO** como gameplay.

## Volcanoes

Head inalterado. Atmosfera, pressão, gases, calor e proteção continuam autoridades próprias. Nenhuma delas é MANA, NUTRITION, cast-time, spell power ou cooldown.

Para A0141–A0143, Volcanoes não substitui Cold Sweat/Nutritional Balance/Epic Fight. Para A0144–A0150, é **NÃO APLICÁVEL** salvo futura bridge explicitamente provada.

## Enshrouded

O delta fecha a integração 08.01 com Ars Zero. `ars_zero:lich` é ator preferencial de primeira manifestação quando disponível, enquanto Enshrouded preserva story/reward authority e possui fallback quando o provider não está carregado.

Classificação: **BRIDGE** já coberta pela progressão/story nativa do Enshrouded. Não há nova perk exigida neste lote porque a integração não altera recurso do jogador, mana, spell power, cast-time ou cooldown. Não converter a presença temática de Ars Zero em copropriedade ARCANE.

## Black Arcana

O avanço é de reproducibilidade/presentation para Stage 05/05A. Arcane Resistance, Corruption Resistance, Strain e Backlash permanecem estados próprios e não são MANA. Nenhuma nova capability do delta autoriza A0144–A0150 a escrever nesses estados.

Classificação: **PROGRESSÃO/HAZARD NATIVO AUTORITATIVO** para os estados de perigo já existentes; o delta de QA/presentation é **NÃO DEVE SER INTEGRADO** como perk.

## Providers externos auditados no lote

### Nutritional Balance 7.0.3

Snapshot upstream: `dannydjdk/Nutritional-Balance@fce213e966b395b16ae30a801a19a37f6a73da50`.

`INutritionalBalancePlayer` expõe leitura dinâmica de nutrientes/status e `processSaturationChange(float)`. A implementação calcula o decremento nutricional dentro de `DefaultNutritionalBalancePlayer.processSaturationChange(...)` e chama `IPlayerNutrient.changeValue(...)` sem evento/reducer público com contexto do jogador. Consequência: A0142 é all-or-nothing e permanece `UNAVAILABLE_NODE`; A0143 herda o bloqueio.

### Iron's Spells 'n Spellbooks 3.16.3

Snapshot upstream: `iron431/irons-spells-n-spellbooks@e4056af90302d37eb1739f5ff05020b020e6e252`.

Capabilities comprovadas:

- `AttributeRegistry.SPELL_POWER`, `MAX_MANA`, `MANA_REGEN`, `CAST_TIME_REDUCTION`;
- `SpellOnCastEvent` com custo mutável, porém publicado somente após o gate de mana suficiente do `canBeCastedBy()`;
- `SpellCooldownAddedEvent.Pre/Post` antes/depois do commit de cooldown;
- `SpellPreCastEvent` não expõe boundary de interrupção ativa convertível.

Disposição:

- A0144: provider direto via `SPELL_POWER`;
- A0145: **SEM HOOK COMPLETO** no Iron's; desconto apenas no debit seria semanticamente incompleto;
- A0146: provider direto via `MAX_MANA`;
- A0147: provider direto via `MANA_REGEN`;
- A0148: provider direto via `CAST_TIME_REDUCTION`, preservando semântica LONG/CONTINUOUS/INSTANT;
- A0149: provider direto via `SpellOnCastEvent` + `SpellCooldownAddedEvent.Pre/Post`;
- A0150: **SEM HOOK SEGURO**, `UNAVAILABLE_NODE`.

### Ars Nouveau 5.13.1

Snapshot upstream: `baileyholl/Ars-Nouveau@112920ff774831f204031da75b4c4e73d3765157`.

Capabilities comprovadas:

- `SpellDamageEvent.Pre` mutável;
- `SpellCostCalcEvent.Post` mutável;
- `MaxManaCalcEvent` mutável;
- `ManaRegenCalcEvent` mutável;
- `IManaCap`/ManaCap para estado do reservatório.

Disposição:

- A0144: canal de dano de spell;
- A0145: provider direto de custo MANA;
- A0146: provider direto de mana máxima;
- A0147: provider direto de regen;
- A0148/A0149/A0150: fail-closed enquanto não houver seam equivalente versionado por spell/canal.

## Matriz perk → provider

| Perk | Authority principal | Estado do contrato |
|---|---|---|
| A0141 | Cold Sweat + futuro AcclimationLedger/mapper COLD | `UNAVAILABLE_NODE` |
| A0142 | Nutritional Balance + FoodData | `UNAVAILABLE_NODE` all-or-nothing |
| A0143 | Nutritional Balance + futuros seams de regen natural | `UNAVAILABLE_NODE` transitivo |
| A0144 | Iron's SPELL_POWER; Ars SpellDamageEvent | implementável nos canais comprovados |
| A0145 | Ars SpellCostCalcEvent | implementável no Ars; Iron's fail-closed |
| A0146 | Iron's MAX_MANA; Ars MaxManaCalcEvent | implementável |
| A0147 | Iron's MANA_REGEN; Ars ManaRegenCalcEvent | implementável |
| A0148 | Iron's CAST_TIME_REDUCTION | implementável Iron's; demais fail-closed |
| A0149 | Iron's cast + cooldown Pre/Post | implementável Iron's |
| A0150 | future interruption conversion boundary | `UNAVAILABLE_NODE` |

## Matriz provider → árvore

| Capability/provider | Cobertura | Decisão |
|---|---|---|
| Nutritional Balance dynamic nutrients/status | A0142/A0143 | query segura; mutation de decay sem seam ⇒ fail-closed |
| Iron's SPELL_POWER | A0144 | coberta por perk existente |
| Ars SpellDamageEvent.Pre | A0144 | coberta por perk existente, dano apenas |
| Ars SpellCostCalcEvent.Post | A0145 | coberta por perk existente |
| Iron's mana debit event pós-admission | A0145 | **SEM HOOK COMPLETO**; não integrar parcialmente |
| Iron's/Ars max mana | A0146 | coberta por perk existente |
| Iron's/Ars mana regen | A0147 | coberta por perk existente |
| Iron's CAST_TIME_REDUCTION | A0148 | coberta por perk existente |
| Iron's cooldown Pre/Post | A0149 | coberta por perk existente |
| Enshrouded ↔ Ars Zero Lich manifestation | fora do efeito das dez perks | **BRIDGE**, já coberta por progressão/story nativa |
| Black Arcana QA/presentation delta | nenhuma | **NÃO DEVE SER INTEGRADO** |
| RPG Iron Mastery source policy | nenhuma nova | **PROGRESSÃO NATIVA AUTORITATIVA** |

## Baseline promovido para o próximo gate

- RPG Skill Tree: `452e8b23e374179c1f616f9beedce6e3dea66ef5`;
- Volcanoes: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`;
- Enshrouded: `e00e6037d7265eb6ab6b3b877428ddfbc4eaec81`;
- Black Arcana: `e573a0edfcb69d09e423b60ad75ab71b9d8e70c5`.

Nenhum baseline foi avançado com capability detectada sem disposição explícita.