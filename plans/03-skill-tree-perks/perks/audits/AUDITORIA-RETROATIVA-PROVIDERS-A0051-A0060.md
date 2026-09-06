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
| RPG Skill Tree | Expansão editorial do Compendium/corpus pt-BR | IMPLEMENTADO E CANÔNICO, não é gameplay MARTIAL | `main@2e6cf57d...`; corpus editorial | Compendium próprio | **NÃO DEVE SER INTEGRADO** | Nenhuma | Conteúdo/editorial não é hook de combate | Não gerar perk/Mastery por presença no corpus |
| Volcanoes | Ownership proof durável de projeções exact-RNS gravado no host record | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`, PR #84; `RnsProjectionOwnerMarker` + mixin | Integração RNS/Volcanoes | **NÃO DEVE SER INTEGRADO** ao lote CROSSBOW/FIST | Nenhuma A0051–A0060 | Boundary é depósito/prospecção hidrotermal, não ação MARTIAL | Sem converter ownership em Mastery/Cadência/Sequência |
| Volcanoes | Persistência do ownership proof através de NBT round-trip/restart | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`; ownership data + GameTests | Persistência RNS/Volcanoes | **NÃO DEVE SER INTEGRADO** | Nenhuma | Estado de depósito é Volcanoes/RNS-owned | Reload/restart não cria evento MARTIAL |
| Volcanoes | Proteção contra foreign same-value replacement | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`; `RnsHostDepositProjectionWriter` | Safety do handoff RNS | **NÃO DEVE SER INTEGRADO** | Nenhuma | Destructive authority depende do owner marker | Identidade estrangeira é preservada; nenhuma perk contorna |
| Volcanoes | Side ledger deixa de ser autoridade destrutiva e pode ser reparada/limpa pelo marker persistido | IMPLEMENTADO E CANÔNICO no delta | `c26e97c...`; writer/ownership data | Safety/recovery RNS | **NÃO DEVE SER INTEGRADO** | Nenhuma | Host marker é authority; side ledger é auxiliar | Ledger stale nunca autoriza adoção/remoção nem progressão |
| Enshrouded | Nenhuma capacidade nova relevante desde baseline | SEM DELTA | `main@f8d4d54...` | N/A | **NÃO DEVE SER INTEGRADO** ao lote | Nenhuma | Shroud/Exposure/Madness/Flame/Story não são receipts CROSSBOW/FIST | Ausência de bridge = contribuição zero |
| Black Arcana | Nenhuma capacidade MARTIAL nova relevante desde baseline | SEM DELTA | `main@73c14ce5...` | `ARCANE_BACKLASH` já possui boundary terminal | **NÃO DEVE SER INTEGRADO** como ação MARTIAL direta | Nenhuma | Backlash não é projectile/hit/root do jogador | Nunca crita/proca/gera Cadência/Sequência/Mastery |

**Disposição do delta:** todas as linhas detectadas possuem decisão, ação e fail-closed explícitos. Assim, `2e6cf57d...` (RPG Skill Tree), `c26e97c...` (Volcanoes), `f8d4d54...` (Enshrouded) e `73c14ce5...` (Black Arcana) são checkpoints reconciliados deste lote.

## Gate provider→árvore

### RPG Skill Tree

- `A0041A0060CombatPolicy`, `A0041A0060CombatState`, projectile bridge e Epic Fight bridge materializam partes relevantes do lote.
- Stage 11.01 itemization continua authority de identidade/ranks/rolls; projeção de efeitos em CROSSBOW/FIST permanece **SEM HOOK SEGURO** neste lote.
- `tree_architecture/combat.json` publica `combat:crossbow`, enquanto Notion/`CombatPerkTreeModel`/projectile runtime usam `epicfight:crossbow`: blocker herdado, sem ledger paralela.
- `P-A0049-01` permanece blocker de aquisição: não existe producer finite-discovery `epicfight:crossbow` para jogador novo alcançar Mastery 60/80. A0051–A0054 herdam esse blocker.
- Review da PR #249 encontrou ainda que owner jogador + metadata `CrossbowItem` não bastam como provenance: projectile derivado/reemitido sem launch receipt CROSSBOW real precisa ficar fail-closed para A0051–A0054.
- Para FIST, Notion/`CombatPerkTreeModel` usam `combat:fist` e gateway `combat_fist`, mas architecture não publica essa árvore e producer genérico Epic Fight geraria `epicfight:fist`. Isso precisa ser reconciliado antes de considerar gate alcançável.
- Recursos transientes do lote precisam ser reconciliados em rank loss/respec/rules reload: Cadência, Sequência, receipts, reservas, janelas e cooldowns não podem sobreviver à invalidação da perk e reaparecer numa recompra.

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
| A0051 | APROVADO após correção/review | PARCIAL | launch provenance + `P-A0049-01/-02` producer/ledger CROSSBOW |
| A0052 | APROVADO após correção/review | PARCIAL | availability + mesma besta hit→reload + Multishot + launch provenance + lifecycle |
| A0053 | APROVADO após correção/review | PARCIAL | availability + reservation→commit + launch provenance + lifecycle |
| A0054 | APROVADO após correção/review | PARCIAL | consumo antecipado + rollback de launch cancel + provenance + lifecycle + ledger |
| A0055 | APROVADO após correção | NÃO CONFIRMADO como adquirível | `combat:fist` vs `epicfight:fist`; architecture `combat_fist` ausente |
| A0056 | APROVADO | attack-speed path presente | depende de A0055 e reconciliação de rank/gateway |
| A0057 | APROVADO após correção | crítico FIST presente | depende de A0055 e reconciliação de rank/gateway |
| A0058 | APROVADO após correção/review | PARCIAL | heavy-impact reset ausente + lifecycle de Sequência; body penalty opcional |
| A0059 | APROVADO | FAIL-CLOSED CORRETO | heavy/finalizer + guard-break movement + lifecycle próprio |
| A0060 | APROVADO após review de lifecycle | FAIL-CLOSED CORRETO | heavy/finalizer + Stamina refund + lifecycle de cooldown/reserva |

## Nove eixos obrigatórios — prova individual 10/10

Cada um dos dez dossiês possui agora uma seção `Nove eixos obrigatórios de aprovação` com resultado e evidência específica por perk, conforme os critérios obrigatórios.

| Perk | Dependências/gates | Integração global | Qualidade/identidade | Topologia | Especializações | PT-BR | Notion completo | NeoVitae | Cobertura providers |
|---|---|---|---|---|---|---|---|---|---|
| A0051 | PASS design | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS |
| A0052 | PASS design | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS |
| A0053 | PASS design | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS |
| A0054 | PASS design | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS |
| A0055 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |
| A0056 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |
| A0057 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |
| A0058 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |
| A0059 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |
| A0060 | PASS design | PASS | PASS | PASS design | PASS | PASS | PASS | PASS | PASS |

`PASS design` não significa runtime confirmado: indica que o contrato possui gate/fail-closed/boundary correto e que o blocker de implementação está explicitamente catalogado. Os 18 critérios técnicos cumulativos foram cruzados em cada dossiê; hooks ausentes não foram substituídos por bônus genéricos.

## Notion

### Fetch fresco inicial

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

### Correções após primeiro review da PR #249

- **A0052:** outcome de Multishot deduplicado por root action; máximo um success/failure e uma perda de Cadência por disparo.
- **A0053:** consumo de 2 Cadências definido como reservation→commit/rollback até criação confirmada do projectile/root.
- **A0054:** janela/Cadência seguem reservation→commit/rollback; cancelamento tardio/ausência de spawn não queimam ativação.

**Re-fetch da primeira rodada de review:** 3/3 PASS.

### Correções após segundo review da PR #249

- **A0051:** launch provenance obrigatória; owner + `CrossbowItem` isolados não tornam projectile derivado elegível.
- **A0052:** launch provenance + limpeza de Cadência/receipts/root outcomes em rank loss/respec/rules reload.
- **A0053:** launch provenance + descarte de reservas em reconciliação de rank/regras.
- **A0054:** launch provenance + limpeza de janela/reservas próprias do capstone; Cadência reconciliada por A0052.
- **A0058:** limpeza de Sequência/janela em rank loss/respec/rules reload.
- **A0060:** limpeza de cooldown/reserva do capstone em rank loss/respec/rules reload; Sequência permanece owner A0058.

**Re-fetch da segunda rodada de review:** 6/6 PASS em 2026-08-30.

### Sem mutação funcional no lote

- **A0056 e A0059:** fetch fresco sem drift; não receberam alteração cosmética.
- Total de páginas distintas mutadas no ciclo: **8/10** — A0051, A0052, A0053, A0054, A0055, A0057, A0058 e A0060.

## Pendências destinadas ao Chat 2

1. **P-A0049-01 (herdada):** producer finite-discovery `epicfight:crossbow`, +10 por tipo hostil inédito; 6→60, 8→80; dedup por player/category/type.
2. **P-A0049-02 (herdada):** reconciliar `combat:crossbow` vs `epicfight:crossbow`; uma ledger apenas.
3. **P-A0051-01:** exigir launch receipt CROSSBOW real antes de aplicar crítico; projectile derivado/reemitido sem correlação fica fail-closed.
4. **P-A0052-01:** propagar availability A0050→A0052.
5. **P-A0052-02:** correlacionar hit e reload pela mesma identidade causal da besta/ItemStack.
6. **P-A0052-03:** regressões de miss/cancel >50%/troca/reload real/estado externo/dedup.
7. **P-A0052-04:** deduplicar outcome Multishot pelo `rootActionId`.
8. **P-A0052-05:** hit receipt somente de projectile correlacionado a launch CROSSBOW confirmado.
9. **P-A0052-06:** limpar Cadência/receipts/root outcomes em rank loss/respec/rules reload que invalide a perk/cadeia.
10. **P-A0053-01:** propagar availability A0052→A0053.
11. **P-A0053-02:** reservation→commit das 2 Cadências até projectile/root confirmado.
12. **P-A0053-03:** exigir launch provenance real para ação especial.
13. **P-A0053-04:** descartar reservas em rank loss/respec/rules reload que invalide A0053/pré-requisitos.
14. **P-A0054-01:** consumir 3 Cadências no disparo que usa Mecanismo Ajustado, não ao armar janela.
15. **P-A0054-02:** propagar availability A0050/A0052/A0053→A0054.
16. **P-A0054-03:** reconciliar `combat:crossbow` vs `epicfight:crossbow`.
17. **P-A0054-04:** reservation→commit/rollback da ativação até projectile/root confirmado.
18. **P-A0054-05:** exigir launch provenance CROSSBOW no disparo consumidor.
19. **P-A0054-06:** limpar janela/reservas do capstone em rank loss/respec/rules reload; Cadência pelo owner A0052.
20. **P-A0055-01:** producer finite-discovery único `combat:fist`, 6→60, 8→80; suprimir/migrar `epicfight:fist` paralelo.
21. **P-A0055-02:** publicar/reconciliar `combat_fist` no architecture catalog.
22. **P-A0055-03:** teste architecture↔model↔Notion↔producer + provider-present/absent FIST.
23. **P-A0058-01:** reset de Sequência por heavy impact recebido somente com receipt provider-native.
24. **P-A0058-02:** body modulation opcional somente por hunger/exhaustion real.
25. **P-A0058-03:** limpar Sequência/janela em rank loss/respec/rules reload que invalide A0058/A0057/gateway.
26. **P-A0059-01:** heavy/finalizer receipt provider-native para Quebra de Ritmo.
27. **P-A0059-02:** guard-break receipt real + −8% movement/2s com lifecycle.
28. **P-A0059-03:** limpar reserva/estado próprio em rank loss/respec/rules reload; Sequência segue A0058.
29. **P-A0060-01:** heavy/finalizer receipt provider-native para Combinação Final.
30. **P-A0060-02:** Stamina refund somente com ledger causal pós-consumo das cinco ações.
31. **P-A0060-03:** gate80 usa exclusivamente `combat:fist`; depende da reconciliação A0055.
32. **P-A0060-04:** limpar cooldown/reserva do capstone em rank loss/respec/rules reload; Sequência segue A0058.
33. **P-A0051-60-TEST-01:** GameTest/harness server-side cobrindo CROSSBOW/FIST, availability, Mastery, same-weapon correlation, projectile derivado sem launch receipt, Multishot root outcome, launch cancellation/rollback, rank loss/respec/rules reload, heavy/finalizer fail-closed, dedup, lifecycle, multiplayer e dedicated server.

## Nove eixos / critérios técnicos do lote

- **1. Dependências e bloqueios:** 10/10 auditadas; PASS no design, blockers runtime explicitados.
- **2. Integrações globais/modlist/corpo/recursos:** 10/10 auditadas; owners de Cadência/Sequência/Stamina preservados.
- **3. Qualidade/identidade:** 10/10 auditadas; ranked passives pequenos permanecem caminhos, Notables/Capstones possuem decisão própria.
- **4. Ramificação/distância/topologia:** 10/10 auditadas; divergências `combat_fist`/CROSSBOW estão catalogadas como blockers runtime, sem atalho aceito.
- **5. Especializações:** 10/10 auditadas; MARTIAL/BESTAS e MARTIAL/ARMAS_DE_PUNHO não viram classes de mod automaticamente.
- **6. PT-BR:** 10/10 auditadas; texto de jogador em PT-BR, IDs/hooks técnicos preservados em inglês quando necessário.
- **7. Notion completo:** 10/10 auditadas; 8 páginas distintas mutadas e todas as escritas re-fetched com persistência confirmada.
- **8. NeoVitae:** 10/10 sem dependência residual.
- **9. Cobertura da modlist/providers:** 10/10 auditadas; provider→árvore e delta de projetos próprios totalmente dispostos.

Provider-native first, authority/causalidade/dedup, Mastery anti-farm, fallback, recursos, lifecycle, multiplayer e testabilidade também foram cruzados pelos 18 critérios técnicos. Gaps de runtime permanecem explícitos e fail-closed.

## Fechamento de design

**A0051–A0060 — LOTE FECHADO NO DESIGN; NOVE EIXOS 10/10 REGISTRADOS; BLOCKERS RUNTIME CATALOGADOS.**

Chat 2 deve implementar/corrigir somente os contratos acima, sem redesenhar. **A0061+ não foi iniciada.**
