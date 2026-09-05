# Reconciliação pós-auditoria de critérios — A0001–A0299

Data: 2026-09-02.

## 1. Objetivo e precedência

Esta auditoria é uma **errata normativa e de autoridade** produzida após a varredura read-only de A0001–A0299 contra `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`, o Catálogo Mestre do Notion, os dossiês e as auditorias históricas.

Ela **não redesenha efeitos**, **não implementa runtime**, **não declara `IMPLEMENTAÇÃO CONFIRMADA`** e **não substitui blockers reais de provider/capability**.

Onde houver conflito, este documento tem precedência exclusivamente sobre:

1. referências históricas a `SPECIALIST_GATE_V1`, `SPECIALIST_GATE_RESOLVER_V1` ou `SpecialistGateResolver` como infraestrutura futura/ausente;
2. a interpretação de que a ausência desse resolver paralelo torna uma perk unavailable;
3. os nove campos vazios de `Dependências Obrigatórias` corrigidos diretamente no Notion, listados abaixo.

Todo outro contrato, número, dependency, provider, state receipt, resource modifier, deduplicação, anti-abuso, fallback ou fail-closed dos dossiês originais continua válido até auditoria específica posterior.

## 2. Prova superior — pipeline Specialist já existente

A premissa histórica de um resolver Specialist futuro foi superada pela infraestrutura já mergeada do Stage 04.01.

PR #365 — `Stage 04.01: canonical investment projection` — foi mergeada e materializou/provou:

- `TreeUnlockReloader` → `TreeUnlockCatalog`;
- `TreeUnlockDefinition` para definições data-driven de unlock;
- `TreeUnlockResolver` como resolver canônico de gates de árvores;
- projeção canônica read-only de investimento a partir de `ProgressionState`;
- contribuição de nodes baseada somente em metadata/tags explícitas, sem inferência por ID, posição ou topologia;
- fail-closed por node sem metadata e por revision mismatch.

Merge commit da PR #365: `76785ebc286bfcdb9835416802e72c070ecc4e15`.

Consequência normativa: **não criar `SpecialistGateResolver`, `SPECIALIST_GATE_V1` ou `SPECIALIST_GATE_RESOLVER_V1` paralelo**. Specialist unlock deve reutilizar a pipeline `TreeUnlockResolver` + `TreeUnlockDefinition` + projeção canônica de investimento do Stage 04.01.

## 3. Contrato canônico de Specialist após reconciliação

Para toda Specialist afetada nesta faixa:

- **Gate A:** fundamentos exteriores semanticamente mapeados e legitimamente disponíveis;
- **Gate B:** pelo menos **100 Passive Points válidos** na `SPECIALIST_REGION:<FAMILY>` correspondente;
- **Gate C:** terminal exterior correta possuída;
- `UNAVAILABLE_NODE` conta **0 PP** para Gate B;
- bridge/shared PP não pode satisfazer dois thresholds incompatíveis; qualquer whitelist deve contar para no máximo um lado semântico;
- geometria da UI, posição visual e border hopping nunca substituem gates semânticos;
- respec seguro exige reembolsar perks internas antes de quebrar terminal, fundamentos, dependency closure ou reduzir Gate B abaixo de 100;
- definição/snapshot/revision inválidos continuam fail-closed na pipeline TreeUnlock.

A terminal continua sendo **somente Gate C**. A correção de authority não adiciona dano, resistência, estado, recurso, aura, mobilidade ou outro pacote de poder.

## 4. Perks com referência Specialist desatualizada

### 4.1 A0162 / A0169

- `A0162` — Maestria de Fogo;
- `A0169` — Maestria de Gelo.

Os dossiês da PR #368 e as páginas do Notion foram corrigidos diretamente.

Estado após correção:

- A0162 continua `UNAVAILABLE_NODE` **transitivo por A0161/dependency closure**, não por falta de resolver Specialist;
- A0169 continua `UNAVAILABLE_NODE` **transitivo por A0168/dependency closure**, não por falta de resolver Specialist;
- ambos reutilizam `TreeUnlockResolver`/`TreeUnlockDefinition` + Stage 04.01.

### 4.2 Terminais A0200–A0232

Referências históricas corrigidas no Catálogo Mestre e materializadas nos cinco dossiês desta PR:

- `A0204` — Maestria de Eldritch;
- `A0211` — Maestria de Ender;
- `A0218` — Maestria de Terra;
- `A0225` — Maestria de Água;
- `A0232` — Maestria de Vento.

Disposição:

- A0204 continua sujeita a A0198/A0199, A0203 e lane ELDRITCH exata/dependency closure; a remoção do falso blocker Specialist **não** a torna automaticamente comprável;
- A0211 continua bloqueada enquanto `ENDER_MASTERY_LANE_V1`, A0210 ou sua dependency closure real estiverem ausentes; `SPECIALIST_GATE_RESOLVER_V1` deixa de ser blocker;
- A0218 passa a ter availability decidida apenas por A0217, Earth Mastery, rota profunda e demais requisitos reais; ausência de resolver Specialist separado não é blocker;
- A0225 passa a ter availability decidida apenas por A0224, Water Mastery, rota profunda e demais requisitos reais;
- A0232 passa a ter availability decidida apenas por A0231, Wind Mastery, rota profunda e demais requisitos reais.

Nenhuma das cinco recebe implementação confirmada por esta errata. Verificação pós-escrita no GitHub: **5/5** dossiês com `TreeUnlockResolver` + `TreeUnlockDefinition` + Stage 04.01; **0/5** identificadores legados de resolver Specialist.

### 4.3 Perks internas Specialist A0243–A0299

A auditoria especial A0200–A0299 registrou `SPECIALIST_GATE_RESOLVER_V1` em 61 perks; quatro delas são terminais acima e **57** são perks internas A0243–A0299.

Os **57 dossiês A0243–A0299 foram materializados nesta PR**. Em todos eles, `SPECIALIST_UNLOCK:<FAMILY>` é comprovado pela pipeline canônica `TreeUnlockResolver` + `TreeUnlockDefinition` + projeção de investimento Stage 04.01.

A materialização remove **somente** o falso blocker/contrato duplicado de gate. Todos os contratos específicos de gameplay permanecem exatamente como documentados.

A correção também foi persistida diretamente no Catálogo Mestre do Notion. Verificação pós-escrita de A0243–A0299:

- **57/57** páginas apontam para TreeUnlock canônico;
- **0/57** mantêm o padrão antigo como provider/dependência;
- **0** referências ao identificador legado permanecem em `Provider/Mods`;
- blockers/contracts próprios de cada perk foram preservados;
- a view temporariamente filtrada durante a revisão foi restaurada ao estado original.

Os **57 dossiês individuais A0243–A0299** foram sincronizados no GitHub nesta PR. Verificação automática pós-escrita: **57/57** com authority TreeUnlock canônica, **0/57** com identificador legado e nenhum arquivo fora da faixa alterado pelo passo de materialização. `perks/README.md` e `audits/README.md` continuam indexando esta reconciliação para dar precedência sobre auditorias históricas anteriores.

Exemplos que **continuam blockers reais quando ausentes**:

- `FIRE_IGNITION_RESOLVER_V1`;
- `ELEMENT_SIGNATURE_REGISTRY_V1`;
- `ELEMENTAL_DIVERSITY_LEDGER_V1`;
- `DAMAGE_MITIGATION_RESOLVER_V1` quando o dossiê exigir capability além do resolver já materializado;
- `DERIVED_COMBAT_OUTCOME_PIPELINE_V1` / derived same-outcome equivalentes;
- `FIRE_DERIVED_OUTCOME_PIPELINE_V1`;
- `THERMAL_PARCEL_PIPELINE_V1` / thermal parcel causal;
- `CHILL_STATE_REGISTRY_V1`;
- `CHILL_APPLICATION_RESOLVER_V1`;
- `FREEZE_BUILDUP_ADAPTER_V1`;
- `FULL_FREEZE_STATE_V1` e receipts/modifiers correlatos;
- `WET_STATE_V1`;
- `CHARGED_STATE_LEDGER_V1`;
- `LIGHTNING_CHAIN_QUERY_V1`, `LIGHTNING_CHAIN_CONTEXT_V1` e `LIGHTNING_CHAIN_DAMAGE_V1`;
- resource/stamina/mana regen modifiers;
- transient attribute modifiers;
- dodge/avoid receipts;
- temporary world mutation guards;
- absorption/source ledgers;
- boss classifiers;
- hostile damage receipts;
- dependências upstream A0144–A0199 registradas nos dossiês.

Portanto: **gate Specialist corrigido ≠ perk automaticamente implementável**.

## 5. Notion — nove dependências formais corrigidas

A auditoria encontrou `Dependências Obrigatórias = null` em nove fundamentos, embora o Gateway/requisito já estivesse descrito em Gate/Pré-requisitos. O Catálogo Mestre foi corrigido diretamente, sem alterar efeito ou balanceamento:

| Código | Correção |
|---|---|
| A0001 | nenhuma dependência de perk; exige Gateway `epic_sword` |
| A0007 | nenhuma dependência de perk; exige Gateway `epic_axe` |
| A0013 | nenhuma dependência de perk; exige Gateway `epic_spear` |
| A0019 | nenhuma dependência de perk; exige Gateway `epic_dagger` |
| A0025 | nenhuma dependência de perk; exige Gateway `epic_hammer` |
| A0031 | nenhuma dependência de perk; exige Gateway `combat_mace` |
| A0037 | nenhuma dependência de perk; exige Gateway `combat_scythe` |
| A0043 | nenhuma dependência de perk; exige Gateway `epic_bow` |
| A0049 | nenhuma dependência de perk; exige Gateway `epic_crossbow` |

Os requisitos de nível/Mastery existentes continuam no Gate canônico; esta correção apenas elimina campo obrigatório vazio.

## 6. Pendências reais de runtime não alteradas

A varredura também confirmou pendências que **não são documentação stale** e, portanto, não foram “corrigidas” por este Chat 1:

- `A0044` — availability fail-closed aprovada ainda precisa ser validada/implementada no runtime; sem draw/preparation binding o node deve permanecer indisponível e não gastar PP;
- `A0050` — availability fail-closed aprovada ainda precisa ser validada/implementada no runtime; sem reload/preparation binding o node deve permanecer indisponível e não gastar PP.

Esses casos pertencem ao fluxo Chat 2 → Chat 3. Esta PR documental não altera `src/` e não mascara a pendência.

## 7. UNAVAILABLE_NODE continua válido

Uma perk que depende de capability realmente inexistente continua correta quando:

1. compra falha antes do gasto;
2. rank legado indisponível vale 0 PP em gates/thresholds e permanece reembolsável/migrável;
3. não existe fallback que troque a identidade da perk;
4. não é criado listener/resource/attribute/resolver paralelo apenas para fazê-la funcionar;
5. provider ausente/version mismatch mantém fail-closed;
6. callback, derived outcome, automation, fake player, summon ou estado temático não substituem a causalidade exigida.

Esta reconciliação não transforma `UNAVAILABLE_NODE` correto em erro de design.

## 8. NeoVitae

A varredura A0001–A0299 não encontrou referência operacional residual a NeoVitae. Nenhuma correção de NeoVitae foi necessária.

## 9. Handoff obrigatório

Chat 2/Chat 3 devem consultar esta reconciliação junto dos dossiês/auditorias históricas.

Regra de precedência:

- os dossiês A0204/A0211/A0218/A0225/A0232 e A0243–A0299 nesta PR já usam TreeUnlock canônico; A0162/A0169 permanecem sob a PR #368; auditorias históricas que ainda citem o resolver Specialist antigo ficam supersedidas **somente nesse ponto**;
- preservar todas as demais dependencies/capabilities do mesmo dossiê;
- não implementar um segundo resolver Specialist;
- não declarar a perk disponível apenas porque o falso blocker Specialist foi removido; reavaliar a dependency closure real.

## 10. Estado desta correção

**AUDITORIA/RECONCILIAÇÃO DOCUMENTAL APROVADA — SEM RUNTIME / SEM MERGE PELO CHAT 1.**

Escopo efetivamente corrigido:

- 9 campos formais do Notion A0001/A0007/A0013/A0019/A0025/A0031/A0037/A0043/A0049;
- authority de Specialist em A0162/A0169;
- authority de Specialist em A0204/A0211/A0218/A0225/A0232, sincronizada em Notion + dossiês GitHub (**5/5**);
- authority de Specialist persistida no Notion e materializada nos dossiês GitHub para **57/57 A0243–A0299**;
- total materializado nesta PR: **62 dossiês** do escopo mergeado, todos reutilizando TreeUnlock canônico; A0162/A0169 permanecem na PR #368;
- A0044/A0050 preservadas como pendências reais de runtime.
