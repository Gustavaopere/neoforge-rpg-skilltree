# 18 — Capability Delta — A0121–A0130

Data de reconciliação: 2026-09-01.

Este suplemento executa o gate obrigatório **provider → árvore** e a contraprova **perk → provider** para o lote exato A0121–A0130.

## Continuidade documental

Os lotes predecessores posteriores a A0090 continuam em PRs de design abertas:

- A0091–A0100: PR #326;
- A0101–A0110: PR #340;
- A0111–A0120: PR #341.

Essas PRs são fontes de contrato, não prova de runtime integrado.

A `main` também contém o fechamento documental adiantado A0200–A0209 pela PR #331. Essa exceção registrada não fecha, implementa nem substitui A0091–A0199.

## Heads/fontes frescos auditados

| Projeto/sistema | Fonte operacional fresca | Resultado do delta |
|---|---|---|
| RPG Skill Tree | `main@c1597a34787b602e85139d565b9c1e1eb3481cda` | `BodyCostResolver`, `METABOLIC_CLIMB` e `METABOLIC_RANGED` continuam ausentes; avanço recente é documental A0200–A0209 |
| Volcanoes nativo | runtime consolidado no mesmo JAR `rpgskilltree`; provenance standalone `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | sem capability corporal nova para A0121–A0130 |
| Enshrouded | `main@a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2` | hardening de fixture/reload somente; **SEM DELTA DE CAPABILITY PARA O LOTE** |
| Black Arcana | `main@e89df6dc2c204c269d8f1811c6b3f309644c864a` | sem capability aplicável ao lote |

## RPG Skill Tree / avanço até c1597a34…

O avanço da `main` após `25ade2f…` contém apenas a PR documental #331 de A0200–A0209. O compare adiciona dossiês/auditoria/delta/STATUS do lote adiantado e não modifica `src/`, providers corporais ou hooks deste lote.

A reconciliação anterior do Volcanoes já havia removido o `pull_request.paths` restritivo de `Volcanoes Consolidation Contract`; isso corrige a lacuna de orquestração observada na PR #341, mas não cria gameplay.

Busca fresca continua sem implementação live de `BodyCostResolver`, P-0037, `METABOLIC_CLIMB` ou `METABOLIC_RANGED`.

Classificação: **SEM NOVA CAPABILITY CORPORAL / BINDING OBRIGATÓRIO AINDA AUSENTE**.

## Volcanoes nativo

Atmosphere, pressão, calor, gases, respiração, depósitos, prospecção e hazards permanecem sob authority ambiental/geológica própria. Eles não são automaticamente `METABOLIC_*` nem `HYDRATION_*` de ação corporal.

- mineração em conteúdo Volcanoes só participa de A0123/A0124 quando a quebra manual concreta produzir receipt corporal causal canônico;
- temperatura/pressão não podem ser reclassificadas como custo hídrico de mineração, escalada, melee ou ranged;
- o standalone `Gustavaopere/Volcanoes@eaddc323…` permanece provenance do snapshot consolidado, não runtime paralelo.

Classificação: **NÃO DEVE SER CONVERTIDO EM PROVIDER CORPORAL DESTE LOTE**.

## Enshrouded

O delta `29ae2d9… → a08ff919…` altera somente teste/GameTest de reload de corrupção de entidades, substituindo espera fixa por polling bounded e acrescentando regressão de boundary. Não cria custo corporal, HYDRATION, climbing, mining, forestry, melee ou ranged.

Classificação: **HARDENING/TESTE; SEM NOVA CAPABILITY PARA A0121–A0130**.

## Black Arcana

Arcane Danger, Arcane/Corruption Resistance, strain, backlash e forecast continuam pipelines próprios. Mana/custos arcanos não são FoodData nem HYDRATION.

Classificação: **SEM DELTA / NÃO APLICÁVEL AO LOTE**.

## Modlist/provider externa relevante

A fonte obrigatória `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md` fixa:

- ParCool **4.0.0.2**;
- Epic ParCool **21.0.0**;
- Thirst Was Reclaimed **3.0.4** como owner de HYDRATION;
- Thirst Was Fixed **2.1.5** como compat/fix, não owner paralelo;
- Epic Fight **21.17.3.1**.

Essa fonte prevalece sobre rascunhos concorrentes que citaram ParCool 4.0.0.3.

## Matriz perk → provider

| Perk | Pipeline principal | Provider/classificador secundário | Estado atual |
|---|---|---|---|
| A0121 | futuro `BodyCostResolver` METABOLIC | ParCool 4.0.0.2 / Epic ParCool 21.0.0 só classificam CLIMB | `UNAVAILABLE_NODE`: P-0037 + `METABOLIC_CLIMB` ausentes |
| A0122 | TWR 3.0.4 HYDRATION após METABOLIC | ParCool/Epic ParCool só classificam CLIMB | `UNAVAILABLE_NODE`: predecessors + P-0037 + `HYDRATION_CLIMB` ausentes |
| A0123 | Minecraft/NeoForge FoodData + futuro BodyCostResolver | classifier MINING explícito | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0124 | TWR 3.0.4 same-action HYDRATION | A0123/MINING | `UNAVAILABLE_NODE`: A0123 + P-0037/TWR adapter |
| A0125 | FoodData + futuro BodyCostResolver | classifier FORESTRY explícito | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0126 | TWR 3.0.4 same-action HYDRATION | A0125/FORESTRY | `UNAVAILABLE_NODE`: A0125 + P-0037/TWR adapter |
| A0127 | FoodData + futuro BodyCostResolver | Epic Fight 21.17.3.1 somente root/classificação melee | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0128 | TWR 3.0.4 same-root HYDRATION | Epic Fight somente root/classificação | `UNAVAILABLE_NODE`: A0127 + P-0037/TWR adapter |
| A0129 | futuro `METABOLIC_RANGED` real | Minecraft/Epic Fight classificam launch | `UNAVAILABLE_NODE`: custo corporal ranged ausente |
| A0130 | futuro `HYDRATION_RANGED` TWR | launch/root ranged | `UNAVAILABLE_NODE`: A0129 + custo hídrico causal ausente |

## Pipeline corporal canônico

1. identificar `action_id` server-side válida;
2. provider corporal expõe quote/receipt positivo e causal;
3. agregar reducers METABOLIC elegíveis;
4. aplicar cap METABOLIC compartilhado de 30%;
5. settlement/commit METABOLIC uma vez;
6. quando semanticamente suportado, adapter TWR correlaciona HYDRATION à mesma action depois de METABOLIC;
7. agregar reducers HYDRATION sob cap próprio de 30%;
8. commit HYDRATION uma vez.

Sem seam pré-commit ou contrato provider-native equivalente, não usar refund posterior, polling, direct thirst write nem recurso paralelo.

## Notion — hardening A0121–A0130

Fetch fresco: **10/10**.

Páginas distintas alteradas: **6/10 — A0123–A0128**.

- A0121/A0122 já estavam corretamente fail-closed e registravam ParCool 4.0.0.2; não foram mutadas.
- A0123/A0125/A0127: `UNAVAILABLE_NODE` enquanto P-0037/BodyCostResolver METABOLIC estiver ausente/incompatível.
- A0124/A0126/A0128: availability transitiva do predecessor + P-0037 HYDRATION + adapter causal TWR 3.0.4.
- A0129/A0130 já estavam indisponíveis sem `METABOLIC_RANGED`/`HYDRATION_RANGED`; não foram mutadas.

Re-fetch pós-escrita: **6/6 PASS**.

## Gate de implementação

Chat 2 A0121–A0130 não deve iniciar runtime ainda. Antes:

1. requisitos globais mínimos do projeto comprovados por código/testes/CI;
2. #326, #340 e #341 atravessam Chat 2 + Chat 3 e chegam à `main`;
3. esta branch é reconciliada novamente com a `main` resultante;
4. qualquer capability ainda ausente permanece `UNAVAILABLE_NODE`.

## Baselines para o próximo gate

- RPG Skill Tree / Volcanoes nativo: `c1597a34787b602e85139d565b9c1e1eb3481cda`.
- Volcanoes standalone provenance: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`.
- Enshrouded: `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2`.
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`.

Em todo lote seguinte, fontes operacionais frescas e `plans/STATUS.md` prevalecem novamente.