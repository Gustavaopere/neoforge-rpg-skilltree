# 16 — Capability Delta — A0101–A0110

Data de reconciliação: 2026-08-31.

Este suplemento executa o gate obrigatório **provider → árvore** para o lote A0101–A0110. O checkpoint imediatamente anterior está no lote A0091–A0100, PR #326, arquivo `15-capability-delta-a0091-a0100.md`; como essa PR ainda não estava integrada na `main` na abertura deste lote, seus baselines promovidos foram usados como origem de comparação sem duplicar os dez dossiês anteriores.

## Baselines anteriores — lote A0091–A0100

| Projeto | Baseline promovido na PR #326 |
|---|---|
| RPG Skill Tree | `cb95a527fa3b6138d674c74a09dc32d58885d523` |
| Volcanoes | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `6642d4ed14bbae2a771075ca466e6749ac8f7fb8` |
| Black Arcana | `462c5c4af403629a7092129cf7f3070472f03e59` |

## Heads frescos auditados e freshness final

| Projeto | Head auditado | Disposição para A0101–A0110 |
|---|---|---|
| RPG Skill Tree | `66fcec7b163320cfb0d79943969aae33f3adf862` | progressão/gateways continuam authority; PR #308 tornou Volcanoes subsistema nativo do mesmo JAR; avanço final desde `b32a4c8...` alterou apenas `.github/workflows/sonarqube.yml` e não adicionou capability de gameplay |
| Volcanoes — repositório fonte | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` | permanece preservado como fonte/migration baseline; sua implementação canônica foi consolidada no RPG pela PR #308; não executar como segundo pipeline |
| Enshrouded | `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3` | Stages 07.03–07.04: áudio/partículas e perfis de acessibilidade/configuração client-side; `NÃO DEVE SER INTEGRADO` como authority de perk |
| Black Arcana | `e89df6dc2c204c269d8f1811c6b3f309644c864a` | forecast server-authored de Arcane Resistance é read-only; reforça separação de A0102, não cria reducer genérico |

A verificação usou `main` e `plans/STATUS.md` frescos dos quatro projetos. O avanço concorrente da `main` foi reavaliado até os heads acima; estados preparatórios não foram promovidos por nome ou intenção.

## RPG Skill Tree

Desde `cb95a527...`, a `main` consolidou a authority live de progressão/classes/especializações, recebeu trabalho paralelo do Compendium/CI e, durante o fechamento deste lote, mergeou a PR #308 (`feat: consolidate Volcanoes into RPG Skill Tree`).

### PR #308 — consolidação Volcanoes

A PR #308 foi mergeada com merge commit `f613dac5a15b26c7a92e07a9d9cb537c2412ddf2` usando como fonte canônica Volcanoes `eaddc323...`.

O estado pós-merge relevante é:

- `rpgskilltree` permanece o único `@Mod` e o único JAR distribuído;
- `RpgSkillTreeMod` chama `VolcanoesMod.initialize(...)` como bootstrap interno;
- `VolcanoesMod` preserva o namespace `volcanoes:*`, mas não é segundo `@Mod`;
- a simulação de geologia, tectônica, vulcanismo, atmosfera, pressão e integrações foi internalizada preservando suas próprias authorities e anti-double-processing;
- `NativeVolcanoesServices` publica superfícies **read-only** para depósitos, regiões vulcânicas, tectônica, atmosfera e pressão;
- o repositório fonte `Gustavaopere/Volcanoes` permanece em `eaddc323...` enquanto a limpeza separada não ocorre; isso é fonte histórica/migração, não autorização para instalar/executar dois providers simultaneamente.

### Disposição da consolidação no lote

A consolidação é **CAPABILITY CANÔNICA DE WIRING/OWNERSHIP**, mas não cria semântica defensiva nova para A0101–A0110. A implementação internalizada é a mesma fonte Volcanoes já auditada em `eaddc323...`.

- `NativeVolcanoesServices` é classificado como **BRIDGE READ-ONLY**.
- Geologia/tectônica/atmosfera/pressão são **COBERTAS PELO SUBSISTEMA NATIVO VOLCANOES**, não por perks defensivas deste lote.
- Para A0103, esses dados são **NÃO DEVE SER INTEGRADO** como classifier implícito: atmosfera/pressão/localização não provam `DamageType` ambiental allowlisted.
- A0109 não pode converter pressão, massa de veículo, Sable/Create ou equipamento Volcanoes em body encumbrance.
- A0110 não pode tratar equipamentos do subsistema como elegíveis sem o seam global de durabilidade exigido.
- Nenhum pipeline Volcanoes pode ser duplicado pelo Chat 2 apenas porque agora está no mesmo source tree/JAR.

### Outras dispositions RPG

- `ProgressionService`/Stage 04 continua **PROGRESSÃO NATIVA AUTORITATIVA** para gateway, predecessor, compra, respec e derived state. A0101–A0110 não criam segundo ledger de classe/bridge.
- Availability transitiva de A0107/A0108/A0109 deve usar a mesma authority; fórmula local não pode tornar predecessor indisponível comprável.
- `DamageMitigationResolver` é o pipeline canônico a estender para A0101/A0102/A0103/A0106 e, quando adquiríveis, A0108/A0109; não criar cadeia defensiva paralela.
- `gradle.properties` ainda fixa Ars Nouveau `5.13.0`, enquanto modlist/guia e design Notion de A0102 fixam `5.13.1`. Classificação: **DRIFT DE FIXTURE/BUILD**, pendência Chat 2; não rebaixa a versão canônica do design.
- Nenhum avanço fecha A0093/A0100, P-0035 como capability canônica, body encumbrance ou P-0036.
- O avanço `b32a4c8... → 66fcec7...` contém somente `workflow_dispatch` no workflow SonarQube; classificação: **SEM DELTA DE CAPABILITY PARA O LOTE**.

## Volcanoes — repositório fonte

O repositório fonte continua em `eaddc323...`; não houve novo commit após o baseline. A diferença operacional é que essa implementação foi incorporada no RPG Skill Tree pela PR #308.

### Disposição

- o source repo continua referência de provenance/migração até sua limpeza formal;
- a authority executável do modpack passa a estar no subsistema nativo consolidado do `rpgskilltree`;
- pressão, gases, lava, calor e proteção ambiental permanecem semantics/provider-owned de Volcanoes mesmo dentro do mesmo JAR;
- não entram genericamente em A0103 por tema;
- equipamentos de pressão/respiração não são ferramentas manuais elegíveis de A0110 por default;
- massa/veículo não fornece encumbrance corporal de A0109;
- executar a fonte antiga como segundo mod/provider em paralelo é proibido.

Classificação: **MIGRADO PARA SUBSISTEMA NATIVO / SEM NOVA SEMÂNTICA DE PERK**.

## Enshrouded

O delta de `6642d4e...` para `29ae2d9...` fecha Stage 07.03 (áudio/partículas) e Stage 07.04 (perfis de acessibilidade/configuração client-side). O head `29ae2d9...` contém a implementação/validação da quarta tarefa de Client Experience; Stage 08 Integrations continua não implementado.

### Disposição

- áudio, partículas, fog, perfis de acessibilidade e config client-side não são gameplay authority;
- Shroud/Exposure/Madness permanecem sistemas próprios e não entram no allowlist A0103;
- nenhuma configuração de acessibilidade altera a authority server-side dos estados de gameplay;
- nenhum novo provider de incoming physical, encumbrance corporal, impact→Stamina ou durability seam surgiu.

Classificação: **NÃO DEVE SER INTEGRADO AO LOTE**.

## Black Arcana

O delta de `462c5c4...` para `e89df6d...` adiciona um forecast server-authored de Arcane Resistance para apresentação contextual. O próprio status declara que ele espelha somente providers side-effect-free, falha fechado e nunca vira cast authority; Stage 05A continua ativo/parcial.

### Disposição

- Arcane Resistance/Corruption Resistance continuam canais provider-owned;
- o forecast é read-only e **não** alimenta A0102 como resistência mágica genérica;
- `ARCANE_BACKLASH` e `BLOOD_MAGIC_COST` continuam excluídos de A0102/A0104/A0105/A0106;
- não fornece encumbrance corporal ou seam de durabilidade.

Classificação: **PROVIDER NATIVO AUTORITATIVO, SOMENTE LEITURA PARA PRESENTATION; NÃO DEVE SER CONVERTIDO EM REDUCER A0102**.

## Matriz provider → árvore

| Capacidade detectada | Estado | Cobertura na árvore | Decisão |
|---|---|---|---|
| Progression/gateway/class authority RPG | canônico | sistema universal | usar authority existente; nenhuma perk duplica ledger |
| Damage mitigation RPG-owned | infraestrutura canônica | A0092/A0096 e futuras A0101/A0102/A0103/A0106/A0108/A0109 | estender um único resolver, once/root |
| Volcanoes consolidado no mesmo JAR | canônico via PR #308 | subsistema nativo | preservar simulação/authority; não criar segundo provider |
| `NativeVolcanoesServices` | canônico read-only | bridge/query universal | não usar atmosfera/pressão como classifier implícito A0103 |
| Ars fixture 5.13.0 vs design 5.13.1 | drift de build | A0102 | Chat 2 reconcilia fixture/API; fail-closed se incompatível |
| Volcanoes pressure/gas/heat/hazards | canônico | sistemas próprios | não classificar em A0103 por analogia |
| Enshrouded audio/particles/accessibility | canônico client-side | apresentação própria | não integrar como gameplay |
| Enshrouded Shroud/Exposure | canônico | sistema próprio | não classificar em A0103 |
| Black Arcana Arcane Resistance forecast | parcial/canônico read-only | apresentação/hazard próprio | não integrar como reducer A0102 |
| Impact→Stamina P-0035 draft | preparatório/não canônico | A0107 | não habilita node; A0093 continua blocker |
| Player-body encumbrance | AUSENTE | A0109 | `SEM HOOK SEGURO`; node unavailable |
| Durability post-Unbreaking/pre-write seam | AUSENTE | A0110 | P-0036 bloqueante; node unavailable |

## Matriz perk → provider

| Perk | Provider/pipeline principal | Secundários permitidos | Fail-closed principal |
|---|---|---|---|
| A0101 | NeoForge DamageSource + RPG mitigation | Epic Fight adapter causal | consumer/classifier ausente |
| A0102 | `neoforge:is_magic` + RPG mitigation | Iron's/Ars adapters versionados | source unknown/version mismatch |
| A0103 | allowlist vanilla + RPG mitigation | adapter específico por DamageType; Volcanoes native queries são read-only | fora do allowlist; nenhuma inferência por atmosphere/pressure |
| A0104 | NeoForge `LivingDamageEvent.Post` + RPG scheduler/healing | nenhum necessário | scheduler/state ausente |
| A0105 | NeoForge Post + RPG attribute runtime | root receipt provider-native | state/attribute consumer ausente |
| A0106 | NeoForge Pre + RPG mitigation/state | Epic Fight apenas por DamageSource causal | consumer/state ausente |
| A0107 | futuro Epic Fight impact/Stamina adapter | RPG transaction | A0093 + P-0035 não canônico |
| A0108 | RPG physical mitigation + movement attribute | Epic Fight classification | A0100 unavailable |
| A0109 | futuro body-encumbrance + Stamina provider | RPG mitigation/KB | A0108 unavailable + provider ausente |
| A0110 | futuro durability seam provider-native | RPG RNG/purchase | P-0036 sem hook seguro |

## Baselines promovidos para o próximo gate

Após a disposição acima, os próximos checkpoints documentais tornam-se:

- RPG Skill Tree: `66fcec7b163320cfb0d79943969aae33f3adf862`
- Volcanoes — source/migration baseline: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3`
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`

A authority executável do Volcanoes está agora no mesmo `rpgskilltree` JAR; o SHA do repositório fonte permanece registrado apenas para provenance/migração enquanto a limpeza separada não ocorre.

Esses SHAs são checkpoints de comparação, não promoção de conteúdo preparatório. No próximo lote, `main` + `plans/STATUS.md` frescos prevalecem novamente.
