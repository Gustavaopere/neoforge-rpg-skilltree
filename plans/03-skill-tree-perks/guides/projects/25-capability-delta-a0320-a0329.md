# Capability Delta — Chat 1 — A0320–A0329

Data da revalidação: 2026-09-05.

Escopo: exatamente A0320–A0329. Este arquivo satisfaz o gate bidirecional `perk → provider` e `provider → árvore`. Estado `PLANEJADO`, documentação, apresentação ou similaridade temática não viram runtime/bridge automaticamente.

## 1. Checkpoints

Baseline reconciliado usado pelo lote A0310–A0319:

- RPG Skill Tree: `8e33da13a9fe0347987d43addf888885d05be24b`;
- Volcanoes standalone: `298352973e941c2034c97465929dc67f6a0400e2` — tombstone/provenance histórico;
- Enshrouded: `67f4ab9095e69a922f265ffc477381f84c30ec69`;
- Black Arcana: `8c7ea474e17b4a0c80c6377482f08c8ebce1c58b`.

Fresh heads/disposição auditados para o fechamento deste lote:

- RPG Skill Tree `main`: `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`;
- Volcanoes standalone: repositório não mais resolvível/acessível (404); runtime authority permanece consolidada no RPG Skill Tree;
- Enshrouded: `67f4ab9095e69a922f265ffc477381f84c30ec69` — **SEM DELTA RELEVANTE**;
- Black Arcana: `d1388127435e9da902f4baf4814bd52550265a40` — 07.01/07.02 canônicos e avanço documental da sequência Stage 07; nenhuma capability runtime nova pertinente ao lote.

O RPG avançou concorrentemente durante o fechamento do Chat 1. Por isso o delta foi reaberto e reconciliado contra `main@d4422e3e...` antes do handoff.

## 2. RPG Skill Tree — delta material

Entre o baseline anterior e `main@d4422e3e...`, o projeto incorporou conteúdo editorial do Compêndio, planejamento MineColonies Economy e, materialmente, **runtime de MineColonies Battle Mages × Iron's Spellbooks**.

### 2.1 Compêndio BWG

Novos batches editoriais PT-BR e testes de conteúdo ampliam cobertura do Compêndio.

Disposição provider→árvore: **COBERTO POR SISTEMA UNIVERSAL**.

Essas entradas não provam movement context, climb, dodge, stamina debit, perfect-dodge window, momentum ledger ou ranged movement receipt. Nenhuma perk A0320–A0329 é aberta por conteúdo editorial do Compêndio.

### 2.2 MineColonies Economy

`plans/06-integrations/11-minecolonies-economy.md` continua um plano de arquitetura econômica. O documento permanece **PLANEJADO** e exige auditoria técnica antes de qualquer mutação transacional segura.

Capabilities planejadas incluem moeda por colônia, tesouro, oferta monetária, inflação/deflação, impostos e custo econômico de construção/upgrade, com MineColonies permanecendo authority da colônia/material pipeline.

Disposição:

- estado atual: **PLANEJADO / NÃO RUNTIME** para a economia;
- provider→árvore: **SEM HOOK SEGURO** para as mutações econômicas planejadas até implementação específica;
- não criar perk A0320–A0329 por similaridade;
- não tratar `ColonyEconomyLedger` planejado como ledger disponível para AGILITY/momentum;
- não reutilizar economia/tesouro como stamina, metabolic, movement ou action identity.

### 2.3 MineColonies Battle Mages × Iron's Spellbooks — runtime novo

`main@d4422e3e...` materializou a bridge Battle Mage com runtime, registries, profiles, GameTests/JUnit e gates de versão.

Authorities confirmadas no código:

- **MineColonies** continua owner do cidadão, job, vínculo de colônia, inventário real, target/guard relations, lifecycle e contexto operacional;
- **Iron's Spells 'n Spellbooks** continua owner de `SpellData`, spellbook real, `MagicData`, mana, mana regen, cooldown, cast time, pre-cast, spell execution e callbacks;
- **RPG Skill Tree** atua como bridge/orquestrador: reconhece o Battle Mage, resolve o spellbook real, escolhe profiles suportados, aplica safety/friendly-fire policy, inicia/ticka/cancela o cast provider-native e mantém somente contexto mínimo de bridge/deduplicação.

O runtime usa `CastSource.MOB`, não `ServerPlayer`. `IronsCitizenMagicBridge` usa o `MagicData` real do provider, sem segundo mana/cooldown/spell state. `BattleMageCombatController` aceita somente `EntityCitizen`/`JobBattleMage`, usa relações de ataque do MineColonies, LOS/range e friendly-fire. `BattleMageCastTracker` ancora o cast ao slot/`ItemStack` real e spell ID/level. `BattleMageIntegrationBootstrap` aplica version gate e `FAILED_CLOSED` em erro de linkage/runtime.

Version gates atuais da bridge:

- MineColonies: exatamente `1.1.1375-1.21.1-snapshot`;
- Iron's: `3.16.3` / artifact `1.21.1-3.16.3`.

Disposição provider→árvore:

- **BRIDGE IMPLEMENTADA / PROGRESSÃO NATIVA AUTORITATIVA**: a capability pertence ao cidadão MineColonies e à magia Iron's; o RPG orquestra compatibilidade sem criar player perk equivalente;
- **COBERTA POR SISTEMA DE INTEGRAÇÃO, NÃO POR A0320–A0329**;
- nenhum cast de Battle Mage é `direct player offense` por padrão: owner é o `EntityCitizen` e source é `MOB`;
- Battle Mage **não** abre A0327, não produz player Mastery por mera conjuração, não satisfaz A0326 `RANGED_PHYSICAL`, não é DODGE/STAMINA de A0324 e não é `VOLUNTARY_MOVEMENT_CONTEXT_V1` de A0328/A0329;
- uma futura atribuição de crédito do cidadão ao jogador exigiria contrato explícito de autoria/causalidade e não pode ser inferida por colony owner, proximidade ou equipe.

A presença desse runtime não exige 11ª perk neste ciclo.

## 3. Volcanoes

O standalone anteriormente usado como provenance/tombstone retorna 404 e não está mais acessível como repositório separado. A authority viva permanece consolidada no RPG Skill Tree.

Disposição: **RELOCAÇÃO/CONSOLIDAÇÃO DE AUTHORITY; SEM CAPABILITY NOVA PERTINENTE**.

O2, respiração, gases, pressão, proteção, geologia e volcanismo continuam em pipelines próprios. Não equivalem a SWIM_SPEED, CLIMB_SPEED, DODGE, stamina cost, perfect dodge, voluntary movement ou momentum.

Nenhuma linha A0320–A0329 ganha hook pela consolidação.

## 4. Enshrouded

`67f4ab9095e69a922f265ffc477381f84c30ec69` → mesmo SHA.

`plans/STATUS.md` confirma Stage 08 Integrations 5/5 concluído e Stage 09 ainda não iniciado. **SEM DELTA RELEVANTE** para o lote.

Shroud/Exposure/Flame/Sanctuary/Story/MagicResistance e demais systems do projeto não são authority de movement/stamina/dodge deste lote. Nenhuma integração é aberta por tema.

## 5. Black Arcana

`8c7ea474...` → `d1388127...`.

`plans/STATUS.md` confirma Stage 07.01 Blood & Curses e 07.02 Souls & Death canônicos; 07.03–07.07 permanecem pendentes. O commit fresco corrige a sequência documental, registrando 07.03 Projection & Arsenal e 07.04 Space & Displacement. Não foi detectada capability runtime nova/semanticamente alterada pertinente a A0320–A0329 desde o checkpoint material anterior.

Disposição: **SEM DELTA RUNTIME RELEVANTE / PROGRESSÃO NATIVA AUTORITATIVA preservada**.

Mesmo futuros domínios de Space/Displacement não podem ser usados como `VOLUNTARY_MOVEMENT_CONTEXT_V1` ou `DIRECTION_BREAK_V1` sem bridge semântica concreta, versionada e já implementada.

## 6. Mobstein e demais providers periféricos

Mobstein 5.4.4 continua provider próprio de ressurreição corporal, corpos/órgãos, experimentos, allies/bodyguards, estruturas e boss. Suas perks internas Attack/Health/Speed/Template não são nodes do RPG Skill Tree.

Disposição: **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO** ao lote sem hook específico.

Mods de apresentação/HUD/tooltip permanecem não-authoritative.

## 7. Perk → provider

| Perk | Provider/authority principal | Boundary exigido | Estado |
|---|---|---|---|
| A0320 | Minecraft FoodData + stamina providers futuros | causal JUMP cost PRECOMMIT por lane | unavailable |
| A0321 | NeoForge/Minecraft + RPG node lifecycle | `SWIM_SPEED` + `isSwimming()` server-side | implementável |
| A0322 | provider de climb, ParCool candidato futuro | `CLIMB_SPEED`/progress nativo mutável server-side | unavailable |
| A0323 | NeoForge/Minecraft | `LivingDamageEvent.Pre` + `DamageTypes.FALL` | implementável |
| A0324 | Epic Fight 21.17.3.1 | `SkillConsumeEvent` DODGE/STAMINA precommit | implementável via Epic Fight |
| A0325 | Epic Fight/futuro dodge provider | `PERFECT_DODGE_WINDOW_V1` mutável | unavailable |
| A0326 | ranged providers + RPG composition | RELEASE/LAUNCH voluntary movement snapshot + native movement penalty/crit lane | unavailable |
| A0327 | future perfect-dodge + direct damage compositor | `PERFECT_DODGE_RECEIPT_V1` + direct player outcome composition | unavailable |
| A0328 | RPG + vanilla `MOVEMENT_SPEED` | `VOLUNTARY_MOVEMENT_CONTEXT_V1` + `MOMENTUM_LEDGER_V1` | unavailable |
| A0329 | RPG momentum policy | `DIRECTION_BREAK_V1` + claim-once hard-turn policy | unavailable |

Battle Mage não aparece como provider positivo de nenhuma das dez linhas porque sua authority é `EntityCitizen` + `CastSource.MOB`, não jogador AGILITY.

## 8. Provider → árvore — cobertura final

- RPG Compêndio BWG → **COBERTO POR SISTEMA UNIVERSAL**.
- RPG MineColonies Economy → **PLANEJADO / SEM HOOK SEGURO**, não runtime econômico e não perk neste lote.
- RPG MineColonies Battle Mages → **BRIDGE IMPLEMENTADA / PROGRESSÃO NATIVA AUTORITATIVA**, coberta por Stage 06 Integration; não é player perk e não produz direct-player credit automaticamente.
- Volcanoes consolidado → **PIPELINE NATIVO AUTORITATIVO**, sem nova capability AGILITY.
- Enshrouded → **SEM DELTA RELEVANTE**.
- Black Arcana → **SEM DELTA RUNTIME RELEVANTE**; progressão nativa preservada.
- Mobstein 5.4.4 → **PROGRESSÃO NATIVA AUTORITATIVA / NÃO DEVE SER INTEGRADO** ao lote.
- Epic Fight DODGE/STAMINA → **COBERTA POR A0324** pela rota provider-native atual.
- NeoForge `SWIM_SPEED` → **COBERTA POR A0321**.
- NeoForge FALL damage PRE → **COBERTA POR A0323**.
- ParCool climb/stamina client/internal surfaces → **SEM HOOK SEGURO** para A0320/A0322/A0324 enquanto não houver precommit server-authoritative.
- perfect-dodge semantic window → **SEM HOOK SEGURO**, preservando A0325/A0327 unavailable.
- voluntary movement/momentum/hard turn → **SEM HOOK SEGURO**, preservando A0326/A0328/A0329 unavailable.

## 9. Correções de infrastructure assumptions

A inspeção fresca da `main` demonstrou que não existem:

- `AttributeNodeEffectRuntime` como helper genérico reutilizável;
- `A0001A0020CriticalService`;
- serviço genérico comprovado de root/outcome claim para estas perks.

Disposição: **NÃO TRATAR NOMES DOCUMENTAIS COMO CAPABILITY EXISTENTE**.

A0321 e futura projeção A0328 devem reconciliar modifiers no lifecycle do próprio sistema/node sem criar engine global paralela. A0326 deve compor com a lane crítica concreta de cada provider quando ela existir, sem criar um serviço apenas para satisfazer nome antigo.

O novo Battle Mage runtime também não pode ser reutilizado como prova desses contratos: seu cast tracker é cidadão/spellbook-specific e não é root-action ledger universal de player offense ou movement.

## 10. Checkpoint

Todas as capabilities novas/alteradas detectadas receberam disposição explícita. Baseline reconciliado para o próximo ciclo, depois que esta cadeia de PRs for serializada:

- RPG Skill Tree `d4422e3ee07e6cfa17cceac0fddd87be81cf78e4`;
- Volcanoes standalone: removido/indisponível como repo; authority viva no RPG Skill Tree;
- Enshrouded `67f4ab9095e69a922f265ffc477381f84c30ec69`;
- Black Arcana `d1388127435e9da902f4baf4814bd52550265a40`.

Nenhuma disposição exige ampliar A0320–A0329 além de dez perks.
