# 18 — Capability Delta — A0121–A0130

Data de reconciliação: 2026-09-01.

Este suplemento executa o gate obrigatório **provider → árvore** e a contraprova **perk → provider** para o lote exato A0121–A0130.

## Continuidade documental

A `main@66fcec7b163320cfb0d79943969aae33f3adf862` ainda contém o índice formal até o delta 14. Os fechamentos posteriores de Chat 1 permanecem em PRs abertas e são fontes de design, não runtime:

- A0091–A0100: PR #326 / delta 15.
- A0101–A0110: PR #340 / delta 16.
- A0111–A0120: PR #341 / delta 17.

O delta 17 promovia como checkpoints documentais: RPG `66fcec7...`, Enshrouded `29ae2d9...`, Black Arcana `e89df6d...` e o standalone Volcanoes `eaddc323...` apenas como provenance. A própria `main` atual determina a topologia operacional vigente: desde a PR #308, **Volcanoes runtime é subsistema nativo do repositório RPG Skill Tree**; o antigo repo Volcanoes não é fonte operacional de mudanças novas.

## Heads/fontes frescos auditados

| Projeto/sistema | Fonte operacional fresca | Resultado do delta |
|---|---|---|
| RPG Skill Tree | `main@66fcec7b163320cfb0d79943969aae33f3adf862` + `plans/STATUS.md` | sem mudança de head desde o lote anterior; `BodyCostResolver` ausente |
| Volcanoes nativo | mesma `main@66fcec7...` + `plans/volcanoes/STATUS.md` e superfícies Volcanoes consolidadas | sem capability nova para A0121–A0130 |
| Enshrouded | `main@a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2` + `plans/STATUS.md` | 1 commit de hardening de fixture/reload; **SEM DELTA DE CAPABILITY PARA O LOTE** |
| Black Arcana | `main@e89df6dc2c204c269d8f1811c6b3f309644c864a` + `plans/STATUS.md` | sem mudança; sem capability aplicável |

## Enshrouded — delta 29ae2d9… → a08ff919…

O compare fresco mostra exatamente um avanço após o checkpoint do delta 17: PR #57, `Harden entity corruption two-boot reload fixture`. A mudança substitui espera fixa de 20 ticks por polling bounded em fixture de persistência/reload, preserva fail-closed para duplicate UUID e acrescenta regressão de boundary.

Isso não cria novo hook de custo corporal, HYDRATION, mining, forestry, climbing, melee ou ranged; não muda owner de Shroud/Exposure/Madness/Flame; não oferece provider para este lote.

Classificação: **HARDENING/TESTE; SEM NOVA CAPABILITY JOGÁVEL PARA A0121–A0130**. O baseline Enshrouded é promovido para `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2`.

## RPG Skill Tree / BodyCost

Busca fresca da `main` por `BodyCostResolver` retorna zero código. O resolver descrito por P-0037 nos lotes A0115+ continua contrato de design, não runtime live.

Consequência:

- A0121/A0122 permanecem indisponíveis também pela ausência de `METABOLIC_CLIMB`/`HYDRATION_CLIMB` causal;
- A0123–A0128 foram endurecidas no Notion para `UNAVAILABLE_NODE` enquanto o resolver/provider global obrigatório não existir;
- A0129/A0130 já estavam indisponíveis por ausência de custo corporal ranged real.

Classificação: **BINDING OBRIGATÓRIO AUSENTE / FAIL-CLOSED**.

## Volcanoes nativo

O subsystem consolidado mantém authority ambiental/geológica própria. Nada em Atmosphere, pressão, calor, gases, respiração, depósitos, prospecção ou hazards constitui automaticamente `METABOLIC_*` ou `HYDRATION_*` de uma ação corporal.

- mineração em conteúdo/geologia Volcanoes só entra em A0123/A0124 quando a ação manual concreta produzir o mesmo receipt corporal causal canônico;
- temperatura/pressão não podem ser reclassificadas como custo hídrico de mineração/escalada/melee/ranged;
- o antigo repo `Gustavaopere/Volcanoes@eaddc323...` permanece provenance do snapshot importado, não fonte de delta operacional atual.

Classificação: **NÃO DEVE SER CONVERTIDO EM PROVIDER CORPORAL DESTE LOTE**.

## Black Arcana

Arcane Danger, Arcane/Corruption Resistance, strain, backlash e forecast de resistência continuam pipelines próprios. Mana/custos arcanos não são FoodData nem HYDRATION e não substituem custo corporal ausente.

Classificação: **SEM DELTA / NÃO APLICÁVEL AO LOTE**.

## Matriz perk → provider

| Perk | Pipeline principal | Provider/classificador secundário | Estado atual |
|---|---|---|---|
| A0121 | `BodyCostResolver` METABOLIC | ParCool 4.0.0.3 / Epic ParCool 21.0.0 só classificam CLIMB | `UNAVAILABLE_NODE`: resolver + `METABOLIC_CLIMB` ausentes |
| A0122 | TWR 3.0.4 HYDRATION após METABOLIC | ParCool/Epic ParCool só classificam CLIMB | `UNAVAILABLE_NODE`: predecessors + resolver + `HYDRATION_CLIMB` ausentes |
| A0123 | Minecraft/NeoForge FoodData + BodyCostResolver | classifier MINING explícito | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0124 | TWR 3.0.4 same-action HYDRATION | A0123/MINING | `UNAVAILABLE_NODE`: A0123 + P-0037/TWR adapter |
| A0125 | FoodData + BodyCostResolver | classifier FORESTRY explícito | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0126 | TWR 3.0.4 same-action HYDRATION | A0125/FORESTRY | `UNAVAILABLE_NODE`: A0125 + P-0037/TWR adapter |
| A0127 | FoodData + BodyCostResolver | Epic Fight 21.17.3.1 somente root/classificação melee | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0128 | TWR 3.0.4 same-root HYDRATION | Epic Fight somente root/classificação | `UNAVAILABLE_NODE`: A0127 + P-0037/TWR adapter |
| A0129 | futuro METABOLIC_RANGED real | Minecraft/Epic Fight classificam launch | `UNAVAILABLE_NODE`: custo corporal ranged ausente |
| A0130 | futuro HYDRATION_RANGED TWR | launch/root ranged | `UNAVAILABLE_NODE`: A0129 + custo hídrico causal ausente |

## Pipeline corporal canônico do lote

As lanes não podem aplicar reduções independentes diretamente nos providers. O contrato comum é:

1. identificar uma `action_id` server-side válida;
2. provider corporal expõe **quote/receipt positivo e causal** da ação;
3. agregar todas as eficiências METABOLIC elegíveis;
4. aplicar teto METABOLIC compartilhado de 30%;
5. resolver a parcela METABOLIC efetiva;
6. quando semanticamente suportado, adapter TWR correlaciona HYDRATION à **mesma action_id**, depois do resultado METABOLIC;
7. agregar eficiências HYDRATION sob teto próprio de 30%;
8. provider faz um único commit final de cada lane.

Sem seam para quote/alteração antes do commit, não substituir por refund posterior, polling, direct thirst write ou resource paralelo.

## Notion — hardening A0121–A0130

Fetch fresco: **10/10**.

Páginas distintas alteradas: **8/10**.

- A0121/A0122: versão ParCool corrigida de 4.0.0.2 para **4.0.0.3** conforme `CURRENT-MODLIST.md`.
- A0123–A0128: availability endurecida. Binding global ausente => `UNAVAILABLE_NODE`, purchase fail-before-spend e allocation legado 0 PP/reembolsável. Binding global presente + receipt ausente num evento => somente aquele proc é omitido.
- A0129/A0130 já estavam corretamente indisponíveis sem `METABOLIC_RANGED`/`HYDRATION_RANGED`; sem mutação.

Re-fetch pós-escrita das oito páginas: **8/8 PASS** em 2026-09-01.

## Gate de implementação

O design pode ser fechado pelo Chat 1, mas **Chat 2 A0121–A0130 NÃO deve iniciar runtime ainda**. Antes:

1. a `main` deve comprovar os requisitos mínimos de `AGENTS.md` em `Before expanding content` por código/testes/CI;
2. PR #326 (A0091–A0100), #340 (A0101–A0110) e #341 (A0111–A0120) devem atravessar Chat 2 + Chat 3 e chegar à `main`;
3. a PR deste lote deve ser reconciliada com essa `main`;
4. só então implementar os contratos, preservando `UNAVAILABLE_NODE` para qualquer capability ainda ausente.

## Baselines para o próximo gate

- RPG Skill Tree / Volcanoes nativo: `66fcec7b163320cfb0d79943969aae33f3adf862`.
- Volcanoes standalone provenance: `eaddc3232dfc600780769f4a5e7e45ff1e50181c` — provenance apenas.
- Enshrouded: `a08ff919463cb6ce3ea2a8eda59d74feffa6b6b2`.
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`.

Em todo lote seguinte, fontes operacionais frescas + `plans/STATUS.md` prevalecem novamente.
