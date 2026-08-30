# RPG Skill Tree — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db810db57bf96c8168071e

**Snapshot reconciliado:** análise extensa iniciada em `Gustavaopere/neoforge-rpg-skilltree@e49a1fa651abecfe096adb03c822482fcf9c3e7b` e reconciliada antes do fechamento com `main@55463a195f8c3a87436399f71db19f29c8e85488`.

O próprio RPG Skill Tree é simultaneamente a plataforma das perks e provider de vários estados. Nunca registrar apenas `RPG Skill Tree` em `Provider/Mods` sem indicar qual serviço, boundary ou estado canônico é usado.

## 1. Identidade e autoridade

O projeto concentra a progressão RPG do pack e possui planos separados para Foundation, RPG Core, world scaling, Skill Tree, classes/masteries/especializações, combate/magia, integrações, data/network/UI, quests, hardening, Compêndio Natural, itemização, corpos/identidades e cartografia.

Nem todos esses estágios possuem o mesmo status. A existência de um plano futuro não transforma sua interface proposta em hook disponível.

## 2. Estado canônico do jogador — IMPLEMENTADO E CANÔNICO

`CanonicalPlayerAttachmentData` é o envelope persistente único do jogador. A seção Core é autoritativa para:

- Character Level;
- RPG XP;
- Core Progression Points;
- atributos fundamentais;
- Perk Budget;
- reward claims.

`ModAttachments.CANONICAL_PLAYER` é a fronteira normal de escrita. Attachments legados permanecem apenas como entrada de migração.

`CanonicalPlayerSnapshot` e `CanonicalPlayerQueryService` fornecem projeção imutável para consumers. Consulta read-only não pode materializar migração, gravar attachment nem sincronizar cliente como efeito colateral.

Mastery/classes/tree ainda podem existir em áreas de compatibilidade dentro do mesmo envelope, mas isso não cria uma segunda persistência normal.

### Implicação para perks

Gates por Level/XP/CPP/atributos consultam o Core canônico. Perks e bridges externas não escrevem storage diretamente.

## 3. Serviços de progressão — IMPLEMENTADO E CANÔNICO

A mutação de progressão passa pelos serviços canônicos:

- grant de RPG XP é não negativo;
- rollback é operação administrativa explícita;
- level-up e CPP derivados são processados pelas regras Core;
- Mastery mutations podem usar replay keys persistentes para rejeitar replay conflitante;
- runtimes/adapters convergem em `CanonicalPlayerAttachmentRuntime.commitMutation(...)`;
- no-op não persiste nem publica evento;
- eventos internos só são publicados após mutação persistida.

### Implicação para perks

Uma ação causal deve produzir uma única mutação. Hooks duplicados de NeoForge/provider não podem conceder o mesmo XP/Mastery duas vezes.

## 4. Atributos e modifiers — IMPLEMENTADO E CANÔNICO

`AttributeNodeEffectRuntime.refresh` recompõe o estado derivado sem stacking ou drift.

Contratos relevantes:

- `effectId` estável identifica a origem do efeito;
- rank muda valor, não identidade;
- operações mantêm semânticas distintas: `ADD_FLAT`, `ADD_PERCENT_BASE`, `MULTIPLY_TOTAL`;
- `OVERRIDE` não é uma operação válida de node attribute effect;
- alvos antigos são removidos para impedir modifier órfão após respec/reload/mudança de target;
- alteração de `max_health` preserva proporção de vida;
- targets opcionais ausentes falham sem criar um segundo atributo substituto.

### Implicação para perks

Bônus numéricos persistentes derivados de perks devem entrar no runtime canônico de node effects, não em um segundo motor de atributos.

## 5. Skill Tree — núcleo IMPLEMENTADO E CANÔNICO

O Stage 03 está fechado para:

1. data schema/loaders;
2. graph/layout validation;
3. purchase/ranks;
4. effects runtime;
5. respec.

`06-content-wiki-generation.md` permanece aberto como plano integral. A `main@55463a195f8c3a87436399f71db19f29c8e85488` já contém, porém, um subcomponente real e canônico desse Stage: o gate de drift do catálogo/wiki em CI, implementado e registrado pelos PRs #222/#223. Somente esse gate específico pode ser tratado como disponível; o Stage 03.06 inteiro não foi promovido a concluído.

`NodeEffectRuntime` é a fronteira única de efeitos derivados:

`ProgressionState → NodeEffectRuntime.refresh → AttributeNodeEffectRuntime + BehaviorNodeEffectRuntime`

Regras importantes:

- `bonuses` inline é apresentação/exportação, não segunda autoridade de gameplay;
- behavior handlers são registrados explicitamente;
- refresh idêntico é idempotente;
- mudança de rank remove estado anterior antes de aplicar o novo;
- reload inválido preserva revisão anterior;
- reload válido reconcilia jogadores online.

## 6. World Scaling — IMPLEMENTADO E CANÔNICO

O Stage 02 está fechado e cobre:

- relevant player level;
- territory/area level;
- entity level;
- rarity/archetypes;
- scaling/rewards/performance.

`RelevantPlayerLevelResolver` usa candidatos espaciais/party-aware, bounded. Um jogador distante ou global não contamina encontro local.

`EntityScalingState` persiste a decisão por entidade e impede reroll após chunk unload/reload. O estado preserva contexto como território, resolução de nível, variance, rarity, seed, Effective Stats, affixes e behaviors.

Effective Stats são reaplicados idempotentemente por modifiers estáveis.

### Implicação para perks

Perks podem consumir contexto de scaling quando houver boundary adequado, mas não podem recalcular, rerrolar ou substituir a autoridade do World Scaling.

## 7. Classes, Masteries e especializações — ESTADO MISTO

`plans/04-classes-masteries-specializations/✅-06-class-subtrees.md` está fechado para quatro subtrees:

- Technomancer;
- Warlock;
- Druid;
- Metamorph.

Technomancer possui gateways de **Create Kinetics**, **AE2 Networks** e **Oritech Power**, além do capstone `triune_core`. Warlock possui pactos mutuamente limitados; Druid/Metamorph possuem permissões/blacklists de forma.

Os planos gerais do Stage 04 para class resolution, confluences/bridges, masteries, provider identities e specialization gateways continuam abertos como estágio formal.

### Regra

Não declarar “sistema inteiro de classes/masteries finalizado” apenas porque subtrees específicas já são canônicas. A perk deve provar o boundary concreto que usa.

## 8. Combat & Magic Hooks — PLANEJADO COMO STAGE 05

Os planos de:

- combat context;
- melee/Epic Fight;
- projectiles;
- magic pipeline;
- healing/support;
- summons/ownership

não possuem fechamento formal do Stage 05 no snapshot auditado.

Perks existentes podem ter hooks específicos já implementados por seus próprios serviços/adapters. Nesses casos vale o hook real do dossiê/código, não a mera existência do plano Stage 05.

## 9. Integrações Stage 06 — ESTADO MISTO

### IMPLEMENTADO E CANÔNICO

- Iron's Spellbooks (`✅-03-irons-spellbooks.md`);
- Goety / Malum / Eidolon (`✅-05-goety-malum-eidolon.md`).

### PLANEJADO / ABERTO COMO STAGE 06

- adapter contract geral;
- Epic Fight;
- Ars Nouveau;
- identity morphs;
- Apothic Attributes;
- Create / AE2 / Oritech;
- integration test matrix.

Uma perk pode possuir integração específica já comprovada fora do fechamento geral; nesse caso o dossiê deve citar essa evidência específica.

## 10. Data, Network e UI — PLANEJADO COMO STAGE 07

O Stage 07 contém planos para schemas de datapack, reload snapshots, network protocol, player sync, skill-tree UI e localização/acessibilidade. Não usar a presença desses planos como prova de um novo hook de gameplay.

## 11. API pública de progressão para quests/addons — IMPLEMENTADO E CANÔNICO

`RpgQuestProgressionApi.query(ServerPlayer)` retorna `QuestProgressionSnapshot` imutável com informações de progresso consumíveis externamente. O contrato público v1 expõe, conforme o snapshot implementado:

- Core progression;
- Mastery XP;
- classes;
- especializações;
- perk ranks;
- attribute ranks.

IDs válidos mas inexistentes retornam valores fail-closed (`0`/`false`). `QuestProgressionConditionService` suporta condições declarativas de level, mastery, class, specialization, perk rank e attribute rank.

Essa API é read-only; não autoriza mutation de progresso.

## 12. Hardening/Release — PLANEJADO COMO STAGE 09

Não é provider de perks.

## 13. Compêndio Natural — IMPLEMENTADO PARCIALMENTE

O Stage 10 possui 01–08 formalmente concluídos no snapshot:

- proveniência/licenças;
- inventário do modpack;
- modelo de dados/identidade;
- descoberta/progresso;
- fauna;
- flora;
- ecologia/loot;
- mundo/biomas/estruturas/dimensões.

09–15 permanecem abertos. O Compêndio é principalmente conhecimento/descoberta; abrir UI ou visualizar entrada não é autoria suficiente para Mastery sem evento causal dedicado.

## 14. Itemização e equipamentos — PLANEJADO COMO STAGE 11

Há planos para:

- domain invariants;
- equipment classification;
- identidade/persistência do item;
- Rank/Item Power;
- prefix/suffix/infix;
- generation pipeline;
- modifier runtime;
- loot/crafting/mobs;
- Apotheosis;
- Iron's/Ars;
- Create/tech/Curios;
- demais integrações e hardening.

Esses planos são relevantes para design futuro de perks, mas ainda não constituem provider operacional simplesmente por estarem documentados.

## 15. Corpos, clones e identidades de progressão — PLANEJADO COMO STAGE 12

O planejamento inclui:

- Body Profiles e ownership;
- schema/persistência;
- state scope;
- roteamento de progresso ao corpo ativo;
- troca atômica;
- refresh de world scaling;
- construção tecnológica de corpos;
- transmigração mística/Vampirism;
- inventário/Curios/itemização;
- morte/respawn/perda de corpo.

Não usar esses hooks no Chat 2 até que nova evidência em `main` demonstre fechamento da parte necessária.

## 16. Cartografia, regiões, POI e descoberta — PLANEJADO COMO STAGE 13

O Stage 13 foi adicionado ao planejamento em 2026-08-30. Pode orientar perks futuras de exploração/cartografia, mas não é runtime automaticamente disponível.

## 17. Regras obrigatórias para perks

1. RPG Skill Tree conserva autoridade da própria progressão.
2. Providers externos devem usar boundary de query/mutation, nunca attachment interno.
3. Uma ação causal = uma mutação canônica.
4. Mastery requer autoria causal e evento discreto.
5. Não dar Mastery por tick, AFK, item equipado, percurso contínuo, throughput autônomo ou rebuild/reload spam.
6. Não usar Stage `PLANEJADO` como hook real.
7. Ao listar `RPG Skill Tree` em `Provider/Mods`, registrar o serviço efetivo: Core progression, NodeEffectRuntime, World Scaling, Quest API, subtree específica etc.

## 18. Fontes principais

- `plans/STATUS.md`
- `plans/01-rpg-core/`
- `plans/02-progression-world-scaling/`
- `plans/03-skill-tree-perks/`
- `plans/04-classes-masteries-specializations/`
- `plans/05-combat-magic-hooks/`
- `plans/06-integrations/`
- `plans/07-data-network-ui/`
- `plans/08-quests-progression-hooks/`
- `plans/09-hardening-release/`
- `plans/10-compendio-natural/`
- `plans/11-itemization-equipment-progression/`
- `plans/12-bodies-clones-progression-identities/`
- `plans/13-cartography-regions-poi-discovery/`
