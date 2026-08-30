# Auditoria retroativa de integração — A0051–A0060

## Escopo

- **Lote exato:** A0051–A0060, 10 perks consecutivas.
- **Bestas:** A0051–A0054.
- **Armas de Punho:** A0055–A0060.
- **Providers retroauditados:** RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana e Mobstein 5.4.4, além dos providers canônicos de cada contrato.
- **Fora de escopo:** A0061+ e implementação runtime neste Chat 1.

## Fontes/checkpoints frescos

- Critérios obrigatórios e guias consolidados anexados ao projeto.
- Notion Catálogo Mestre: fetch fresco individual A0051–A0060.
- RPG Skill Tree `main@5e9dd777722014596641cb77d7be5c51df410e4e` na abertura; antes da branch, `main` avançou para `2e6cf57d5c12630d55280d1c4ff0177f536dce96` por correções Chat 3 em A0005/A0006 e Compendium. A branch deste lote foi criada sobre o HEAD novo.
- Volcanoes `main@c26e97c136b543f1fa0ef2ebb12044d10d8af816`.
- Enshrouded `main@f8d4d54cb5b8f12aa2149568bfaa2e25f00ef5e5`.
- Black Arcana `main@73c14ce55ff918bb8a81daeb99a352607ef11064`.
- Mobstein 5.4.4 conforme guia obrigatório.

## Matriz obrigatória de delta de capacidades — projetos próprios

O baseline só avança depois de cada capacidade semanticamente nova/alterada receber disposição explícita. As classificações abaixo usam a taxonomia obrigatória do guia de Projetos Próprios.

| Projeto | Capacidade detectada no delta | Estado real | SHA/evidência | Cobertura atual | Decisão | Perk(s)/ação | Hook/boundary | Fail-closed |
|---|---|---|---|---|---|---|---|---|
| RPG Skill Tree | Commit causal de A0005/A0006 endurecido pelo Chat 3 | IMPLEMENTADO E CANÔNICO fora deste lote | `main@2e6cf57d...`; diff desde `5e9dd777...` toca `A0001A0020CombatPolicy`, testes e dossiês A0005/A0006 | Já coberta por A0005/A0006 | **COBERTA POR PERK EXISTENTE** | Nenhuma mudança A0051–A0060 | PRE prepara; POST direto/hostil/com dano efetiva consumo irreversível | Não reutilizar como receipt CROSSBOW/FIST |
| RPG Skill Tree | Expansão editorial do Compendium/corpus pt-BR | IMPLEMENTADO E CANÔNICO, não é gameplay MARTIAL | `main@2e6cf57d...`; `plans/10-compendio-natural/10-10-corpus-lotes.md` + corpus editorial | Compendium próprio | **NÃO DEVE SER INTEGRADO** | Nenhuma | Conteúdo/editorial não é hook de combate | Não gerar perk/Mastery por presença no corpus |
| Volcanoes | Ownership proof durável de projeções exact-RNS gravado no próprio host record | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`, PR #84; `RnsProjectionOwnerMarker` + `CustomServerDepositLocationMixin` | Integração RNS/Volcanoes | **NÃO DEVE SER INTEGRADO** ao lote CROSSBOW/FIST | Nenhuma A0051–A0060 | Boundary é depósito/prospecção hidrotermal, não ação MARTIAL | Sem converter ownership em Mastery/Cadência/Sequência |
| Volcanoes | Persistência do ownership proof através de NBT round-trip/restart | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`; `RnsProjectionOwnershipData`, mixin e GameTests de restart/replacement | Persistência RNS/Volcanoes | **NÃO DEVE SER INTEGRADO** | Nenhuma | Estado de depósito é Volcanoes/RNS-owned | Reload/restart não cria evento MARTIAL |
| Volcanoes | Proteção contra foreign same-value replacement: remoção/adoption só com marker de owner correspondente | IMPLEMENTADO E CANÔNICO no delta | mensagem do commit `c26e97c...`: “fail closed for foreign same-value replacements”; `RnsHostDepositProjectionWriter` | Safety do handoff RNS | **NÃO DEVE SER INTEGRADO** | Nenhuma | Destructive authority depende do owner marker no host record | Identidade estrangeira é preservada; nenhuma perk pode contornar |
| Volcanoes | Side ledger deixa de ser autoridade destrutiva e pode ser reparado/limpo a partir do marker persistido | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`; `RnsHostDepositProjectionWriter`/`RnsProjectionOwnershipData` | Safety/recovery RNS | **NÃO DEVE SER INTEGRADO** | Nenhuma | Host marker é authority; side ledger é auxiliar | Ledger stale nunca autoriza adoção/remoção nem gera progressão |
| Enshrouded | Nenhuma capacidade nova relevante desde o baseline do lote | SEM DELTA | `main@f8d4d54...` | N/A | **NÃO DEVE SER INTEGRADO** ao lote | Nenhuma | Shroud/Exposure/Madness/Flame/Story não são receipts CROSSBOW/FIST | Ausência de bridge = contribuição zero |
| Black Arcana | Nenhuma capacidade MARTIAL nova relevante desde o baseline | SEM DELTA | `main@73c14ce5...` | `ARCANE_BACKLASH` já possui boundary terminal | **NÃO DEVE SER INTEGRADO** como ação MARTIAL direta | Nenhuma | Backlash não é projectile/hit/root do jogador | Nunca crita/proca/gera Cadência/Sequência/Mastery |

**Disposição do delta:** todas as linhas detectadas possuem decisão, ação e fail-closed explícitos. Assim, `2e6cf57d...` (RPG Skill Tree), `c26e97c...` (Volcanoes), `f8d4d54...` (Enshrouded) e `73c14ce5...` (Black Arcana) podem ser usados como checkpoints reconciliados deste lote.

## Gate provider→árvore

### RPG Skill Tree

- `A0041A0060CombatPolicy`, `A0041A0060CombatState`, projectile bridge e Epic Fight bridge já materializam partes relevantes do lote.
- Stage 11.01 itemization continua authority de identidade/ranks/rolls; projeção de efeitos em CROSSBOW/FIST permanece **SEM HOOK SEGURO** neste lote.
- `tree_architecture/combat.json` publica `combat:crossbow`, enquanto Notion/`CombatPerkTreeModel`/projectile runtime usam `epicfight:crossbow`: blocker herdado, sem ledger paralela.
- Além do namespace, `P-A0049-01` permanece blocker de aquisição: não existe producer finite-discovery `epicfight:crossbow` para um jogador novo alcançar Mastery 60/80. A0051–A0054 herdam esse blocker.
- Para FIST, Notion/`CombatPerkTreeModel` usam `combat:fist` e gateway `combat_fist`, mas o architecture catalog não publica essa árvore e o producer genérico Epic Fight geraria `epicfight:fist`. Isso precisa ser reconciliado antes de considerar o gate alcançável.

### Volcanoes

- Delta `7839db6... -> c26e97c...` foi decomposto na matriz obrigatória acima: ownership marker durável, persistência NBT/restart, preservação de foreign replacements e side-ledger recovery.
- Todas as capacidades são do boundary RNS/depósito hidrotermal e recebem **NÃO DEVE SER INTEGRADO** para A0051–A0060.
- RNS/geologia/prospecção não classificam CROSSBOW/FIST, Cadência, Sequência, heavy/finalizer ou crítico.

### Enshrouded

- Main sem delta relevante; Stage 08 integrations ainda não implementado.
- Shroud/Exposure/Madness/Flame/Story não são reload, Cadência, Sequência, FIST, heavy/finalizer ou Stamina receipt.
- **NÃO DEVE SER INTEGRADO** ao lote.

### Black Arcana

- Main sem delta MARTIAL relevante.
- `ARCANE_BACKLASH` continua terminal; não é projectile CROSSBOW, hit FIST, crítico root, Cadência, Sequência, heavy/finalizer ou eligible Stamina receipt.
- **BOUNDARY / NÃO DEVE SER PROCESSADO COMO AÇÃO MARTIAL DIRETA**.

### Mobstein 5.4.4

- Allies/bodyguards/ressuscitados permanecem Mobstein-owned.
- Projectile/hit de companion não herda autoria CROSSBOW/FIST, Mastery, Cadência ou Sequência do dono.
- Ataques diretos do jogador contra entidades Mobstein permanecem `COBERTO POR SISTEMA UNIVERSAL` quando o receipt real da disciplina é satisfeito.
- Progressão interna Attack/Health/Speed/Template permanece `PROGRESSÃO NATIVA AUTORITATIVA`.

## Matriz do lote

| Perk | Design | Runtime auditado | Pendência principal |
|---|---|---|---|
| A0051 | APROVADO após correção | crítico físico presente | `P-A0049-01/-02`: producer + ledger CROSSBOW herdados |
| A0052 | APROVADO após correção | PARCIAL | availability A0050 + mesma besta hit→reload + Multishot/root-outcome dedup |
| A0053 | APROVADO após correção/review | penetration path presente | availability + reservation→commit até projectile spawn |
| A0054 | APROVADO após correção/review | PARCIAL | consumo antecipado no arm/ArrowLoose; availability/ledger + rollback de launch cancel |
| A0055 | APROVADO após correção | NÃO CONFIRMADO como adquirível | `combat:fist` vs `epicfight:fist`; architecture `combat_fist` ausente |
| A0056 | APROVADO | attack-speed path presente | depende de A0055 |
| A0057 | APROVADO após correção | crítico FIST presente | depende de A0055 |
| A0058 | APROVADO após correção/review | PARCIAL | heavy-impact reset ausente; body penalty opcional ausente |
| A0059 | APROVADO | FAIL-CLOSED CORRETO | heavy/finalizer + guard-break movement ainda sem receipt |
| A0060 | APROVADO | FAIL-CLOSED CORRETO | heavy/finalizer ausente; Stamina refund permanece omitido |

## Notion

### Fetch fresco

A0051–A0060 = **10/10**.

### Páginas alteradas no fechamento inicial

- **A0051:** remove tag paralela CROSSBOW; direct-player projectile provenance e boundaries.
- **A0052:** availability herdada de A0050; mesma besta/ItemStack; consumo nativo real; sem bypass.
- **A0053:** availability herdada; first-impact/root projectile; boundaries.
- **A0054:** availability herdada; ledger `epicfight:crossbow`; consumo das 3 cargas no disparo, não ao armar janela.
- **A0055:** remove tag FIST; discovery finita `combat:fist`; proíbe ledger `epicfight:fist` paralela; architecture/gateway precisa alinhar.
- **A0057:** classifier FIST provider-native + crítico/root único.
- **A0058:** heavy-impact recebido somente por receipt provider-native; boundaries de Backlash/Shroud/hazard/companion.

**Re-fetch inicial pós-escrita:** 7/7 PASS.

### Correções após review da PR #249

- **A0052:** outcome de Multishot deduplicado por root action; máximo um success/failure e uma perda de Cadência por disparo.
- **A0053:** consumo de 2 Cadências definido como reservation→commit/rollback até criação confirmada do projectile/root.
- **A0054:** janela/Cadência também seguem reservation→commit/rollback; cancelamento tardio/ausência de spawn não queimam ativação.

**Re-fetch pós-review:** 3/3 PASS em 2026-08-30.

### Sem mutação

- A0056, A0059 e A0060.
- A0051 recebeu blocker técnico herdado no dossiê, sem nova mutação de design após review.

## Pendências destinadas ao Chat 2

1. **P-A0049-01 (herdada):** producer finite-discovery `epicfight:crossbow`, +10 por tipo hostil inédito; 6→60, 8→80; dedup por player/category/type.
2. **P-A0049-02 (herdada):** reconciliar `combat:crossbow` vs `epicfight:crossbow`; uma ledger apenas.
3. **P-A0052-01:** propagar availability A0050→A0052; sem A0050 comprável, A0052 é indisponível/não comprável.
4. **P-A0052-02:** correlacionar hit e reload pela mesma identidade causal da besta/ItemStack; limpar receipt ao trocar/clonar.
5. **P-A0052-03:** testes de miss/cancel >50%/troca/reload real/estado externo/dedup.
6. **P-A0052-04:** deduplicar outcome Multishot pelo `rootActionId`; sucesso de um irmão impede failure dos demais e uma root action remove no máximo 1 Cadência.
7. **P-A0053-01:** propagar availability A0052→A0053.
8. **P-A0053-02:** reservar 2 Cadências no `ArrowLooseEvent`, commit somente após criação confirmada do projectile/root e rollback em cancelamento/ausência de spawn.
9. **P-A0054-01:** não zerar Cadência ao armar Mecanismo Ajustado; consumir 3 no disparo que efetivamente usa a janela.
10. **P-A0054-02:** propagar availability A0050/A0052/A0053→A0054.
11. **P-A0054-03:** reconciliar `combat:crossbow` vs `epicfight:crossbow`; uma ledger apenas.
12. **P-A0054-04:** reservation→commit/rollback da ativação de Mecanismo Ajustado até projectile/root confirmado; cancelamento tardio não queima janela/cargas.
13. **P-A0055-01:** producer finite-discovery para `combat:fist`, +10 por tipo hostil inédito; 6→60, 8→80; suprimir/migrar `epicfight:fist` paralelo.
14. **P-A0055-02:** publicar/reconciliar `combat_fist` no `tree_architecture/combat.json` com modelo/Notion.
15. **P-A0055-03:** teste architecture↔model↔Notion↔producer + provider-present/absent FIST.
16. **P-A0058-01:** reset de Sequência por heavy impact recebido somente com receipt provider-native.
17. **P-A0058-02:** opcional body modulation por hunger/exhaustion real; ausência omite a parcela.
18. **P-A0059-01:** heavy/finalizer receipt provider-native para ativar Quebra de Ritmo.
19. **P-A0059-02:** guard-break receipt real + −8% movement/2s com lifecycle; sem break, omitir.
20. **P-A0060-01:** heavy/finalizer receipt provider-native para ativar Combinação Final.
21. **P-A0060-02:** Stamina refund apenas com ledger causal pós-consumo das cinco ações; sem receipt, 0% refund.
22. **P-A0060-03:** gate80 usa exclusivamente `combat:fist`; depende da reconciliação A0055.
23. **P-A0051-60-TEST-01:** GameTest/harness server-side CROSSBOW/FIST, availability, Mastery, same-weapon correlation, Multishot root outcome, launch cancellation/rollback, heavy/finalizer fail-closed, dedup, lifecycle e multiplayer.

## Nove eixos / critérios técnicos

- Dependências/gates: PASS no design; blockers runtime explicitados.
- Provider-native first: PASS após remoção das tags paralelas em A0051/A0055/A0057.
- Authority/causalidade/dedup: PASS no contrato após review; gaps de implementação catalogados, inclusive Multishot e reservation→commit.
- Mastery anti-farm: PASS no design; CROSSBOW/FIST producer/namespace pendente no runtime.
- Recursos: Cadência/Sequência/Stamina mantêm owners separados; launch cancel não pode consumir recurso.
- Fallback: preserva identidade e availability; ausência de provider não vira compra no-op.
- Lifecycle/multiplayer/testabilidade: requisitos explícitos, prova transversal pendente.
- PT-BR/topologia/especializações: PASS.
- NeoVitae: ausente.

## Fechamento de design

**A0051–A0060 — LOTE FECHADO NO DESIGN; BLOCKERS RUNTIME CATALOGADOS.**

Chat 2 deve implementar/corrigir somente os contratos acima, sem redesenhar. **A0061+ não foi iniciada.**
