# 17 — Capability Delta — A0111–A0120

Data de reconciliação: 2026-08-31.

Este suplemento executa o gate obrigatório **provider → árvore** e a contraprova **perk → provider** para o lote exato A0111–A0120.

## Checkpoint anterior promovido por A0101–A0110

| Projeto | Checkpoint |
|---|---|
| RPG Skill Tree — inclui Volcanoes nativo | `66fcec7b163320cfb0d79943969aae33f3adf862` |
| Volcanoes — source/provenance | `eaddc3232dfc600780769f4a5e7e45ff1e50181c` |
| Enshrouded | `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3` |
| Black Arcana | `e89df6dc2c204c269d8f1811c6b3f309644c864a` |

## Heads frescos auditados

Os quatro `main` + `plans/STATUS.md` foram re-fetched antes do fechamento deste lote e permanecem **exatamente nos mesmos SHAs** acima. Resultado global: **SEM DELTA DE CAPABILITY** entre o checkpoint A0101–A0110 e A0111–A0120.

Nenhum estado preparatório/branch foi promovido por nome ou intenção.

## Disposição provider → árvore

### RPG Skill Tree

- ProgressionService/Stage 04 continua authority de gateways, purchase, predecessor, respec e PP válido.
- A0110/P-0036 permanece o blocker de conservação de durabilidade; a `main` não contém seam global pós-prevenção/pré-decremento.
- Busca da `main` não encontrou `BodyCostResolver`, `P-0037`, `AttunementSocket`, `tool_instance_id`, `tool_lease_nonce` nem `ToolIdentityLedger` como runtime live.
- “Attunement Socket” aparece em documentação alpha/change history, não como infraestrutura live canônica.
- A `main` já contém superfícies de fundação como `ProgressionRulesSnapshot`, mutation core e verificadores de sync coalescing, mas `AGENTS.md` define um gate mínimo de fundações antes de expansão de conteúdo; este lote não promove essas fundações nem presume seu fechamento completo.
- Portanto nenhuma capability atual habilita A0111–A0120 e a implementação deve permanecer diferida até revalidação explícita do gate global.

Classificação: **SEM DELTA; BINDINGS OBRIGATÓRIOS AINDA AUSENTES; IMPLEMENTAÇÃO DIFERIDA POR GATE GLOBAL**.

### Volcanoes

Source/provenance permanece `eaddc323...`; runtime canônico está consolidado no mesmo JAR `rpgskilltree` pela PR #308.

- pressão, respiração, equipamento e recursos de Volcanoes permanecem provider-owned;
- FE/fuel/pressure não são durabilidade A0111;
- pressão/temperatura/hazards não são METABOLIC/HYDRATION action costs A0115–A0120;
- água/pressão não geram hydration receipt.

Classificação: **NÃO DEVE SER CONVERTIDO EM PROVIDER DESTAS PERKS**.

### Enshrouded

Head permanece `29ae2d9...`. Client Experience não cria gameplay authority; Shroud/Exposure/Madness/Flame continuam sistemas próprios.

Classificação: **SEM DELTA / NÃO APLICÁVEL AO LOTE**.

### Black Arcana

Head permanece `e89df6d...`; Arcane Resistance forecast continua read-only e Arcane Danger/resource costs permanecem provider-owned.

Classificação: **SEM DELTA / NÃO APLICÁVEL AO LOTE**.

## Matriz perk → provider

| Perk | Provider/pipeline principal | Secundários permitidos | Estado/fail-closed atual |
|---|---|---|---|
| A0111 | Minecraft/NeoForge durability + adapter tecnológico | Oritech 1.2.11 / Protection Pixel 2.2.1 somente por item durável comprovado | `UNAVAILABLE_NODE`: A0110/P-0036 |
| A0112 | RPG scheduler/selection + repair transaction provider-native | Oritech/outros somente por adapter de posição+custo+reparo | `UNAVAILABLE_NODE`: A0111→A0110/P-0036 |
| A0113 | futuro RPG `tool_instance_id` + `tool_lease_nonce` + `ToolIdentityLedger` + harvest ledger + repair provider-native | famílias de ferramentas explicitamente mapeadas | `UNAVAILABLE_NODE`: A0110/P-0036; components/ledger/producer ausentes |
| A0114 | futuro Attunement Socket + provider repair | Relics/Artifacts/Reliquified somente por binding real | `UNAVAILABLE_NODE`: cadeia A0112→A0110 + Attunement ausente |
| A0115 | Minecraft FoodData + futuro BodyCostResolver METABOLIC | nenhum necessário | `UNAVAILABLE_NODE`: P-0037 ausente |
| A0116 | TWR 3.0.4 + futuro BodyCostResolver HYDRATION | Thirst Was Fixed apenas compat | `UNAVAILABLE_NODE`: P-0037 + TWR adapter ausentes |
| A0117 | BodyCostResolver METABOLIC | ParCool/Epic ParCool só classificam ação com custo real | `UNAVAILABLE_NODE`: A0115/P-0037 |
| A0118 | TWR + BodyCostResolver HYDRATION | ParCool/Epic ParCool só com receipt real | `UNAVAILABLE_NODE`: A0116/P-0037/TWR |
| A0119 | BodyCostResolver METABOLIC | nenhum necessário | `UNAVAILABLE_NODE`: A0115/P-0037 |
| A0120 | TWR + BodyCostResolver HYDRATION | nenhum necessário | `UNAVAILABLE_NODE`: A0116/P-0037/TWR |

## Findings de cobertura

1. **Durabilidade e recursos são eixos separados.** Nenhum equipamento FE/fuel/pressure pode ser tratado como “durability-equivalent”.
2. **Repair é provider-native.** A0112/A0113/A0114 nunca inventam material/recurso universal.
3. **A0113 usa linhagem lógica single-writer, não detecção de “original”.** A lease `current_nonce` rotaciona atomicamente após ação aceita; cópias com nonce stale ou owner incompatível são reidentificadas/resetadas. No máximo uma cópia continua o ledger prévio.
4. **Attunement é capability distinta de slot/equipamento.** Curios, Relics ou Artifacts não provam vínculo.
5. **METABOLIC e HYDRATION são lanes tipadas.** Compartilhar `action_id` não autoriza compartilhar valor.
6. **P-0037 é blocker estrutural.** Como o único efeito de A0115–A0120 depende do resolver/receipt, ausência do binding significa node não comprável, não apenas proc omitido.
7. **TWR é authority de hidratação.** Thirst Was Fixed é compat/fix; nenhuma escrita direta/polling é aceita.
8. **Movimento deve ser causal/autopropelido.** ParCool, correntes, veículos e deslocamento externo não criam custo por aparência.
9. **Design fechado não equivale a autorização imediata de runtime.** Antes do Chat 2 deste lote, a `main` deve satisfazer o gate “Before expanding content” de `AGENTS.md` e os lotes A0091–A0110 devem ter atravessado Chat 2/Chat 3 e sido integrados.

## Notion — hardening do lote

Fetch fresco 10/10.

- A0113 foi endurecida inicialmente e, após review PR #341, recebeu V2: `tool_instance_id + tool_lease_nonce` com `ToolIdentityLedger` single-writer; não há promessa de reconhecer o stack físico original.
- A0115–A0120 foram corrigidas para declarar `UNAVAILABLE_NODE`, purchase fail-before-spend e allocation legado 0 PP enquanto P-0037/BodyCostResolver e, nas lanes hídricas, adapter TWR não existirem.

Páginas distintas mutadas: **7/10**. Re-fetch das sete: **7/7 PASS**; A0113 V2 teve re-fetch adicional específico e persistiu corretamente.

## Gate de implementação para Chat 2

O lote A0111–A0120 pode permanecer **DESIGN APROVADO / FECHADO PELO CHAT 1**, mas a implementação fica diferida até:

1. os requisitos mínimos de fundação listados em `AGENTS.md` serem confirmados na `main` corrente por código/testes/CI;
2. A0091–A0100 (PR #326) e A0101–A0110 (PR #340) serem implementados/validados/mergeados pelos chats responsáveis;
3. a branch/PR #341 ser então reconciliada com essa `main` antes de qualquer runtime A0111–A0120.

Chat 1 não faz esses merges e não implementa as fundações neste lote.

## Baselines promovidos para o próximo gate

Como houve **SEM DELTA** dos providers, os checkpoints continuam:

- RPG Skill Tree: `66fcec7b163320cfb0d79943969aae33f3adf862`
- Volcanoes source/provenance: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Enshrouded: `29ae2d9b7a13bbdffd3291d2fe4213e0705eb8e3`
- Black Arcana: `e89df6dc2c204c269d8f1811c6b3f309644c864a`

Esses SHAs são checkpoints de comparação. Em qualquer lote seguinte, `main` + `plans/STATUS.md` frescos prevalecem novamente.
