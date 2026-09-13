# ATUALIZAÇÃO OPERACIONAL — 2026-09-07

> Esta camada de freshness prevalece quando um snapshot histórico abaixo divergir do estado operacional atual.

## Topologia atual dos projetos próprios

**Volcanoes foi consolidado no repositório `Gustavaopere/neoforge-rpg-skilltree` pela PR #308**. Ele não deve mais ser tratado como um segundo repositório operacional para mudanças futuras de runtime. O antigo `Gustavaopere/Volcanoes@eaddc3232dfc600780769f4a5e7e45ff1e50181c` permanece como proveniência do snapshot importado.

Fontes de freshness:

- **RPG Skill Tree:** `Gustavaopere/neoforge-rpg-skilltree/main` + `plans/STATUS.md`;
- **Volcanoes:** a mesma `main`, filtrando as superfícies Volcanoes (`docs/archive/volcanoes/**`, `src/main/java/dev/gustavopere/volcanoes/**`, `src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/**`, recursos `volcanoes:*`, docs/workflows/scripts relacionados) + `docs/archive/volcanoes/STATUS.md`;
- **Enshrouded:** repositório próprio + `plans/STATUS.md` enquanto separado;
- **Black Arcana:** repositório próprio + `plans/STATUS.md` enquanto separado.

A consolidação muda o local do código, **não a authority semântica**: progressão RPG não passa a ser dona de Atmosphere, pressão, tectônica, erupções ou depósitos apenas porque o mesmo JAR hospeda o subsistema.

## Suplementos de delta existentes na `main`

- `13-capability-delta-a0071-a0080.md`;
- `14-capability-delta-a0081-a0090.md`;
- `15-capability-delta-a0200-a0209.md`;
- `16-capability-delta-a0200-a0299.md`.

O Chat 1 deve usar sempre o suplemento mais recente pertinente e ainda fazer fetch fresco antes do lote seguinte. Baseline é checkpoint de comparação, não prova de ausência de mudanças posteriores.

## Invariantes

- `PLANEJADO` não vira hook por existir em `plans/`;
- capability detectada precisa de disposição provider→árvore;
- um subcomponente comprovado não promove o Stage inteiro;
- integração temática não cria bridge;
- sem hook seguro: **fail-closed**;
- detectar lacuna não altera o lote exato de 10.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/README.md -->

# Projetos Próprios do Modpack — Fonte Canônica para Perks

Os quatro projetos/sistemas de primeira classe são RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana, acompanhados pela matriz cruzada e pela matriz de delta.

## Topologia operacional atual

Volcanoes está consolidado no `neoforge-rpg-skilltree` desde a PR #308. O standalone `Volcanoes@eaddc323...` é proveniência histórica do snapshot importado. Freshness de runtime do Volcanoes deve ser procurada na `main` unificada, filtrando a superfície do subsistema e `docs/archive/volcanoes/STATUS.md`.

## Gate obrigatório

Cada lote do Chat 1 executa:
1. fetch fresco das fontes operacionais/status;
2. comparação contra o último baseline pertinente;
3. extração de capabilities jogáveis novas/alteradas;
4. disposição provider→árvore;
5. avanço de baseline somente após toda linha receber decisão/fail-closed.

Integração temática não cria hook; coabitação no mesmo JAR não transfere authority semântica.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/01-rpg-skill-tree.md -->

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


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/02-volcanoes.md -->

# Volcanoes — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db8178b64fe5400b66abc9

**Snapshot auditado:** `Gustavaopere/Volcanoes` — análise extensa em `1d0da7ae...`, reconciliada até `main@602e0188c123ac8531d3413a5630daa22e3d761f`

Stages 00–05 estão fechados no snapshot. Stage 06 possui integrações canônicas e uma frente RNS parcialmente fail-closed. Stage 07 Hardening permanece aberto.

## 1. Identidade e autoridade

Volcanoes é o provider ambiental/geológico do pack. Seu domínio não se limita à geração de vulcões: inclui geologia, depósitos, tectônica, terremotos, ciclo vulcânico, erupções, cinzas/piroclastos, geotermia, Atmosphere, respiração/gases/poluição e pressão.

Perks não devem recriar estados físicos paralelos nem assumir ownership de sistemas que o Volcanoes deliberadamente deixa em providers externos.

## 2. Foundation — IMPLEMENTADO E CANÔNICO

O Stage 00 estabelece contratos base, configuração, segurança de mundo e boundaries usados pelos demais estágios. Consumers devem preferir esses contracts em vez de classes internas.

## 3. Geologia — IMPLEMENTADO E CANÔNICO

O Stage 01 fecha rock profiles, strata e resource discovery.

`GeologicalDeposit` representa depósitos com:

- resource tag;
- posição central;
- raio;
- riqueza;
- origem geológica;
- UUID persistente.

Origens canônicas incluem `MAGMATIC`, `HYDROTHERMAL`, `SEDIMENTARY` e `GENERIC`.

`DepositRegistry` é SavedData por level. Repetir o mesmo UUID com o mesmo conteúdo é idempotente; conflito de conteúdo para a mesma identidade falha fechado.

`GeologicalDepositSource` é a SPI read-only para consumers, com consulta integral e `nearby(BlockPos, radius)` bounded.

O core Volcanoes **não** instala um segundo scanner de jogador. Prospecção visual permanece no provider adequado, especialmente RNS quando a integração é segura.

### Perks legítimas

- leitura/interpretação geológica;
- prospecção baseada em fonte real;
- marcos discretos de descoberta;
- eficiência contextual que não altera o ownership do depósito.

Nunca gerar Mastery por permanecer sobre um depósito ou consultar repetidamente o mesmo UUID.

## 4. Tectônica — IMPLEMENTADO E CANÔNICO

O Stage 02 fecha:

- plate field;
- plate boundaries/stress;
- safe earthquakes.

Volcanoes conserva autoridade de suas semânticas de placa/estresse. `Tectonic` é terrain shaping; sua presença não significa que fornece plate data ao Volcanoes.

### Perks legítimas

- interpretação/detecção tectônica por hook real;
- resposta/mitigação de evento sísmico;
- milestone causal único de descoberta/sobrevivência quando deduplicável.

Não conceder XP/Mastery a cada tick de tremor.

## 5. Vulcanismo — IMPLEMENTADO E CANÔNICO

O Stage 03 fecha:

1. volcano sites;
2. magma lifecycle;
3. lava;
4. eruptions;
5. ash/pyroclastics;
6. geothermal/hot springs.

O ciclo de erupção é server-owned e bounded. Cinzas/piroclastos e calor se integram aos sistemas ambientais em vez de criarem pipelines concorrentes. Geothermal lifecycle alimenta o domínio ambiental por bridges canônicas.

### Perks legítimas

- observação/previsão de atividade quando houver boundary real;
- resistência/mitigação contextual;
- exploração de geotermia;
- coleta/prospecção ligada a eventos discretos.

A perk não possui scheduler de erupção nem cria uma segunda fonte de hazard.

## 6. Atmosphere — IMPLEMENTADO E CANÔNICO

`AtmosphereState` é um vetor físico/químico composável, não um único número de “qualidade do ar”. O snapshot auditado inclui grandezas como:

- pressão total/baseline;
- fração de O₂;
- CO₂;
- SO₂/gases ácidos;
- gases tóxicos genéricos;
- particulados;
- fumaça/smog;
- umidade;
- modificador térmico.

O estado é imutável/normalizado e deriva pressão parcial de oxigênio. Fontes locais são indexadas; sampling é bounded, sem scan global. Difusão/decay usa cadência e budgets explícitos. O snapshot de cliente é comprimido e player-relevant.

### Perks legítimas

Perks podem consultar/alterar resposta do jogador a canais ambientais apenas pelos serviços reais. Não colapsar gases diferentes em um bônus genérico se a identidade da perk depende de um canal específico.

## 7. Respiração e gases vulcânicos — IMPLEMENTADO E CANÔNICO

Respiração, volcanic gases e seus efeitos pertencem ao Stage 04. Proteção/consumo deve passar pelo pipeline canônico de respiração/equipment/protection. Uma perk não deve consumir filtro, ar ou durabilidade pela segunda vez.

## 8. Poluição e chuva ácida — IMPLEMENTADO E CANÔNICO

Volcanoes possui integração com Destroy para o domínio de poluição/acid rain. A bridge preserva a autoridade de cada lado; não se deve inventar feedback agregado ou segundo pollution engine apenas para “conectar” sistemas.

## 9. Pressão — IMPLEMENTADO E CANÔNICO

O Stage 05 fecha:

- atmospheric pressure;
- water pressure;
- sealed/protected volumes;
- equipment.

Pressão atmosférica é uma grandeza física distinta do vetor químico da Atmosphere, embora os sistemas se componham. Pressão hidrostática varia com profundidade. Protected/enclosed environment usa SPI bounded e fail-closed: não presume sala/cabine selada sem prova.

Equipamentos possuem capacidades modulares de proteção e o runtime evita consumo concorrente entre Pressure e Atmosphere.

O contrato de Stage 05 usa Curios `9.5.1+1.21.1` no snapshot auditado.

### Perks legítimas

Tolerância, eficiência ou diagnóstico só devem ser acoplados quando houver extension point real. Não duplicar equipment provider nem conceder proteção grátis se uma bridge falhar.

## 10. Stage 06 — integrações

### Worldgen — IMPLEMENTADO E CANÔNICO

- Terralith volcanic crater/peaks funcionam como hints positivos e opcionais;
- Tectonic continua apenas terrain shaping;
- BWG entra por regras genéricas de suitability;
- nenhum desses mods se torna hard dependency desnecessária.

### Create / Sable / Aeronautics — IMPLEMENTADO E CANÔNICO

Versões verificadas no plano fechado:

- Sable `2.0.5`;
- Aeronautics `1.3.1`.

`SablePressureIntegration` usa API Sable verificada para projetar posições de sublevel no level físico e consultar a pressão canônica.

Aeronautics não expõe, nessa versão, um contrato genérico confiável para “cabine selada/leak/flood”. Por isso Volcanoes deliberadamente **não inventa cabine protegida** e cai para a atmosfera/pressão externa quando não consegue provar proteção.

Create Diving Helmet + Backtank participam da oferta de oxigênio e o ar é consumido pelo adapter uma única vez.

### Cold Sweat — IMPLEMENTADO E CANÔNICO

Cold Sweat `2.4.2` continua autoridade da temperatura corporal. Volcanoes injeta calor ambiental bounded proveniente de fontes como lava, pyroclastics e geothermal. Perks não devem criar segundo body-temperature state.

### Destroy — IMPLEMENTADO E CANÔNICO

Bridge de poluição/acid rain fechada com routing/retry safety. Destroy mantém sua autoridade de domínio.

### MineColonies — IMPLEMENTADO E CANÔNICO

Claims alimentam `ProtectedAreaService` com comportamento fail-closed. Perks, rituais ou world effects não podem contornar protected areas.

### RNS — IMPLEMENTADO PARCIALMENTE / FAIL-CLOSED NO WORLDGEN FÍSICO

A integração resolve identidade hidrotermal de metais apenas quando a causalidade é comprovada:

- shield/fissure → iron;
- stratovolcano → copper;
- caldera → gold;
- tectonic-only → generic.

Volcanoes já demonstrou placement físico bounded/determinístico de Cu/Fe/Au. **RNS continua authority de prospecção e native metal worldgen enquanto o handoff seletivo de ownership não for fechado.** A lifecycle bridge de ownership permanece desabilitada/fail-closed; tin/nickel/zinc/silver permanecem RNS-owned.

### Regra para perks

Não listar Volcanoes como autoridade de worldgen mineral RNS além do que a integração realmente prova.

## 11. Hardening — PLANEJADO / ABERTO

O Stage 07 cobre compatibility matrix, performance, world-upgrade/persistence e release checklist. Não é provider de perk.

## 12. Anti-abuso e deduplicação

1. Nenhum hazard ambiental contínuo gera Mastery por tick.
2. Descobertas usam UUID/milestone persistente ou identidade equivalente.
3. Reentrada/reload/rebuild não pode conceder a mesma progressão novamente.
4. Equipment/filter consumption acontece uma única vez pelo pipeline canônico.
5. Não inferir cabine selada, proteção, concentração de gás ou mineral ownership por aparência/nome.
6. Bridge opcional ausente desativa apenas a parcela dependente e nunca concede proteção gratuita.

## 13. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-geology/`
- `plans/02-tectonics/`
- `plans/03-volcanoes/`
- `plans/04-atmosphere/`
- `plans/05-pressure/`
- `plans/06-integrations/`
- `plans/07-hardening/`


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/03-enshrouded.md -->

# Enshrouded — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db813a91bec39bc08f2e53

**Snapshot auditado:** `Gustavaopere/Enshrouded` — análise extensa em `de145be...`, reconciliada até `main@77552a3d7f089a47908c109f5f8c19aff8a0f97d`

Foundation, Shroud Field, Terrain Corruption, Exposure, Corrupted Ecology e **Flame Progression completa** estão fechados. `06.01 Story State` também é subcomponente canônico; o restante de Lich/Story, Client Experience, Integrations e Hardening não deve ser promovido em bloco.

## 1. Identidade e autoridade

Enshrouded é o provider do **Shroud**: campo espacial persistente, corrupção/materialização de terreno, exposição e consequências do jogador, ecologia de criaturas corrompidas e progressão da Flame.

O Shroud é semanticamente diferente da `Corruption` do Black Arcana e não deve ser convertido automaticamente em recurso/resistência arcana.

## 2. Foundation — IMPLEMENTADO E CANÔNICO

A Foundation congela boundaries consumíveis pelos estágios posteriores, incluindo:

- `ShroudQuery` / severity / sample;
- `MutationAuthority`;
- `ProgressionOwner` e resolver;
- `FlamePassageQuery`;
- `FlameWardQuery`;
- classificação de dano mágico;
- boundary para provider de Lich.

### Regra para perks

Consumir boundaries públicos, não SavedData/classes internas. Quando unknown/failure em gate de passagem/proteção poderia abrir acesso indevido, o comportamento deve ser fail-closed.

## 3. Shroud Field — IMPLEMENTADO E CANÔNICO

O Stage 01 fecha o campo do Shroud:

- estado autoritativo dimension-local persistente;
- core lifecycle explícito/idempotente;
- expansão frontier-based e bounded;
- ausência de world/chunk scan e chunk forcing desnecessário;
- query indexada/determinística;
- client sync compacto/read-only;
- core seeding/discovery determinístico e sparse.

Sanctuary, quando o provider final existir, deve funcionar como overlay de proteção sobre o campo latente; proteção não significa apagar automaticamente a existência lógica do Shroud.

### Perks legítimas

- ler zona/severity via query real;
- reagir a entrada/saída como evento causal quando necessário;
- descoberta de core com identidade deduplicável;
- benefícios de exploração que não mutam o campo por fora da autoridade.

Não conceder Mastery por simplesmente permanecer na névoa.

## 4. Terrain Corruption — IMPLEMENTADO E CANÔNICO

O Stage 02 centraliza mutações de mundo:

- `DefaultMutationAuthority` é a authority de mutação/proteção;
- SAFE/AGGRESSIVE terrain tags;
- tri-state protection;
- safeguards para block entities;
- materialização data-driven, reversível e bounded;
- revalidação de `ShroudQuery` antes da mutação;
- corruption growths determinísticos/limitados;
- regression/purification determinística.

Stage 02 é o owner da transição lógica `DESTROYED → PURIFIED`; limpar blocos visuais não pode ressuscitar estado lógico ou criar segundo lifecycle.

### Regra para perks

Perk não pode mutar terreno Shroud por fora de `MutationAuthority` nem contornar claims/wards/protected-area decisions.

## 5. Exposure — IMPLEMENTADO E CANÔNICO

O Stage 03 possui estado de exposição versionado e server-authoritative:

- CLEAR recupera reserva;
- SHROUD drena reserva;
- delta de lag é bounded;
- reconnect/save-load não limpa reserva insegura;
- `DeadlyExposurePolicy` é seam estável;
- Madness pertence ao domínio canônico;
- Red Sludge faz parte do Stage 03 fechado.

### Deadly / Red Shroud

`FlameGatedDeadlyExposurePolicy` usa progressão da Flame como gate real:

- requirement padrão de passage level 2;
- fallback Foundation level 1;
- jogador subnivelado entra em emergency window bounded e continua rapid drain;
- `ProgressionOwnerResolver` + `FlamePassageQuery` são as dependências do gate;
- owner/query incerto falha fechado;
- edge-dancing não cria segundo timer/reset gratuito.

### Perks legítimas

Eficiência, recuperação, reserva ou mitigação só podem ser desenhadas se respeitarem a authority do Exposure Service. Uma perk genérica não pode bypassar o gate de Passage Level.

## 6. Corrupted Ecology — IMPLEMENTADO E CANÔNICO

O Stage 04 fecha:

- entity corruption;
- hostility/buffs;
- magic resistance;
- ecology visuals.

`MagicResistanceService` é o **único reducer** de resistência mágica do ecossistema. Adapters de outros mods mágicos, quando existirem, devem fornecer evidência/classificação, nunca aplicar a redução outra vez.

### Perks legítimas

Identificação/combate contra corrompidos e interação com resistência somente através de hooks canônicos. Evitar segundo reducer e double-dipping com o pipeline mágico de origem.

## 7. Flame Progression — IMPLEMENTADO E CANÔNICO

### Flame State — IMPLEMENTADO E CANÔNICO

`FlameProgressionSavedData` persiste por owner:

- Flame Level;
- Passage Level;
- ritual IDs concluídos;
- schema/version.

Novo owner começa em Flame Level 1 / Passage Level 1. `FlamePassageService` implementa o boundary Foundation. Ritual IDs são estáveis; repetição é idempotente/rejeitada.

### Flame Altar — IMPLEMENTADO E CANÔNICO

`05.02` está fechado em `main`. O altar participa da progressão pela authority do Stage 05; não é legítimo detectar “qualquer bloco semelhante” por heurística e conceder avanço.

### Sanctuary / Flame Ward — IMPLEMENTADO E CANÔNICO

`05.03` está fechado na `main` reconciliada. Sanctuary funciona como overlay de proteção sem apagar o campo lógico de Shroud e deve ser consumido pelos boundaries do Enshrouded; perks não podem escrever esse estado diretamente.

### Level 1 Ritual — IMPLEMENTADO E CANÔNICO

`05.04` está fechado. Rituais concluídos usam identidade persistente; perks/rewards não podem ser reaplicados por reconnect ou reexecução do mesmo ritual.

## 8. Lich & Story — IMPLEMENTADO PARCIALMENTE

`06.01 Story State` é canônico em `main@77552a3d...`: estado narrativo server-global versionado, owner-scoped por `ProgressionOwner`, encounter UUID estável, transições one-way e defeat/reward issuance idempotentes. **Isso não promove Stage 06 inteiro**; boss provider, manifestação, Lich Skull/reward path e demais tasks continuam dependentes de fechamento próprio.

Perks futuras podem ser planejadas, porém a implementação deve ficar pending/fail-closed até existir provider real em `main`.

## 9. Client Experience — PLANEJADO

Stage 07 contém planos para HUD, fog rendering, audio/particles e accessibility.

Esses recursos são apresentação. Mesmo após implementação, client visual/input não deve se tornar autoridade de gameplay.

## 10. Integrations — PLANEJADO COMO STAGE 08

O Stage 08 inteiro está aberto no snapshot.

### Ars Nouveau / Iron's

O plano prevê adapters de **classificação de dano**. `MagicResistanceService` continua sendo o único reducer. O plano cita Ars Nouveau 5.13.0 e Iron's Spells 3.16.3 no momento em que foi escrito; implementação futura deve reconciliar a versão real corrente da modlist.

### Goety / Malum / Eidolon

Planejados apenas como flavor/recipe/loot opcional quando houver valor concreto. A progressão necromântica desses providers não deve substituir ou sequestrar Flame Progression.

### Demais integrações planejadas

- Ars Zero;
- combat/claims/teams;
- JourneyMap;
- necromancy flavor.

Nenhuma deve ser listada como bridge operacional apenas porque há plano.

## 11. Hardening — PLANEJADO

Stage 09 não é provider de perk.

## 12. Relação com Black Arcana

Separações obrigatórias:

- Enshrouded Shroud/Exposure ≠ Black Arcana Corruption;
- Enshrouded mob Magic Resistance ≠ Black Arcana Arcane Resistance;
- Flame Passage / Flame Ward ≠ Arcane Resistance;
- Madness ≠ Arcane Strain.

Arcane Resistance e Corruption Resistance do Black Arcana não recebem Shroud automaticamente. Qualquer relação futura precisa de bridge/provider explícito.

## 13. Anti-abuso e deduplicação

1. Sem Mastery por tick de exposição.
2. Sem Mastery apenas por ficar dentro do Shroud.
3. Edge-dancing não pode farmar progresso.
4. Core discovery/ritual progress usam identidade persistente/one-time semantics quando aplicável.
5. Remover visual não apaga estado lógico do Shroud.
6. Não bypassar `MutationAuthority`, `FlamePassageQuery` ou protected-area/ward boundaries.
7. Failure/unknown de passage/protection falha fechado.

## 14. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-shroud-field/`
- `plans/02-terrain-corruption/`
- `plans/03-exposure/`
- `plans/04-corrupted-ecology/`
- `plans/05-flame-progression/`
- `plans/06-lich-story/`
- `plans/07-client-experience/`
- `plans/08-integrations/`
- `plans/09-hardening/`


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/04-black-arcana.md -->

# Black Arcana — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81dc8848c3ab317c2c70

**Snapshot auditado:** `Gustavaopere/Black-Arcana@07263ae9bad12eba6ed500992991faa36ad598b2`

O `plans/STATUS.md` auditado é anterior a commits recentes do Stage 05A. Por isso este documento separa o **status formal do estágio** da evidência de componentes que já chegaram à `main`. Um componente presente não autoriza promover o Stage 05A inteiro a “fechado”.

## 1. Identidade e autoridade

Black Arcana é a plataforma própria de magia sombria/perigosa do pack. O projeto possui:

- casting server-authoritative;
- recursos/custos;
- targeting/effect runtime;
- cooldown/persistência;
- conteúdo data-driven;
- bridges para ecossistemas mágicos;
- segurança de efeitos no mundo;
- Arcane Danger: Danger Profiles, Arcane Resistance, Corruption Resistance, Arcane Strain e Arcane Backlash;
- planejamento downstream para rituals, spell domains e progression/balance.

Perks não devem criar segundo cast pipeline, segundo ledger de dano ou conversões implícitas entre hazards de outros mods e Arcane Danger.

## 2. Foundation e Reference Catalog — IMPLEMENTADO E CANÔNICO

Stages 00 e 01 estão fechados. Eles estabelecem base técnica, catálogo de referência, identidade própria e decisões de balance/risco necessárias antes de importar/reinterpretar mecânicas inspiradas em outros sistemas.

## 3. Arcana Core — IMPLEMENTADO E CANÔNICO

Stage 02 fecha:

1. cast request/execution;
2. resource cost provider;
3. targeting/effect runtime;
4. cooldowns/persistence;
5. networking/data-driven content.

Todo cast deve terminar no pipeline canônico do Stage 02. Perks podem fornecer gates/modifiers apenas por extension points reais; elas não criam segunda reserva/commit de custo ou execução paralela.

Client input/presentation nunca se torna authority de gameplay.

## 4. Integration Layer — IMPLEMENTADO E CANÔNICO

Stage 03 fecha bridges com:

- Iron's Spellbooks;
- Ars Nouveau;
- Eidolon;
- Malum;
- RPG Skill Tree;
- fallbacks de dependência opcional.

### Regra para perks

O adapter traduz contexto ao Black Arcana. Ele não deve criar segunda progressão, segundo mana pool ou segundo cast engine para o provider externo.

A existência dessa integração geral com RPG não prova automaticamente que toda API futura de Arcane Danger ↔ RPG já está finalizada; cada subcontrato deve ser auditado pelo seu estágio.

## 5. World Safety — IMPLEMENTADO E CANÔNICO

Stage 04 fecha:

- world effect policy;
- temporary blocks/rollback;
- area budgets/chunk safety;
- PvP/boss protection.

Perks, rituais e spell domains não podem contornar essas políticas para produzir uma versão “mais forte” do mesmo world effect.

## 6. Casting & UX — IMPLEMENTADO PARCIALMENTE

O código de Stage 05 foi mergeado e os gates automatizados ficaram verdes, mas a matriz manual real de cliente/visual/input não estava encerrada no `STATUS.md` auditado.

Áreas do estágio:

- input/loadouts;
- radial wheel;
- contextual HUD;
- accessibility/client config.

Mesmo quando presentes em código, são sobretudo input/apresentação e não devem virar authority de damage/cost/hazard.

## 7. Arcane Danger — IMPLEMENTADO PARCIALMENTE / ESTÁGIO ATIVO

### 7.1 Danger Model — PRESENTE

`ArcaneDangerTier` distingue ao menos:

- `NORMAL`;
- `UNSTABLE`;
- `DANGEROUS`;
- `FORBIDDEN`;
- `CATASTROPHIC`.

`ArcanaCastId` continua sendo a identidade raiz do cast. Subordinate damage recebe IDs próprios. Hazard sessions são bounded e snapshots são imutáveis após ativação.

### 7.2 Arcane Resistance — PRESENTE

É um canal específico para suportar energia arcana perigosa. Não é automaticamente:

- vanilla armor;
- armor toughness;
- generic magic resistance;
- Enshrouded Shroud/Exposure;
- Volcanoes heat/respiration/toxicity/pressure.

Curva canônica inicial documentada:

`residual(R) = K / (K + clamp(R, 0, R_MAX))`

Defaults auditados:

- `K = 40`;
- `R_MAX = 240`;
- `R = 0` produz exatamente `1.0` de residual antes de floors/multipliers do profile.

Contribuições chegam por providers registrados, bounded e read-only. Provider que falha é isolado e contribui zero. O snapshot é capturado no hazard activation boundary e não muda com gear swap posterior.

### 7.3 Corruption Resistance — PRESENTE

Canal separado de Arcane Resistance.

A Corruption do Black Arcana é persistente por jogador e responde à alteração de longo prazo causada por magia proibida. Ela **não é** o Shroud/Exposure do Enshrouded.

Alta Arcane Resistance com baixa Corruption Resistance é uma build válida e vice-versa.

### 7.4 Arcane Strain — PRESENTE

Carga de curto/médio prazo criada por channeling repetido de magia perigosa.

- possui estado bounded;
- recuperação é lazy/event-driven quando possível;
- relog/restart não deve limpá-la de graça;
- não é uma segunda mana pool;
- só influencia preflight/backlash/corruption quando o danger profile declara essa interação.

### 7.5 Arcane Backlash — COMPONENTE IMPLEMENTADO E VERIFICADO

O Backlash usa causalidade própria e dano realmente confirmado:

- a base é health damage elegível confirmado pós-mitigação;
- não usa nominal spell damage, valor pre-armor ou client value;
- ledger bounded por root cast deduplica `ArcanaDamageInstanceId`;
- delayed damage mantém ownership do root cast quando permitido;
- canonical linear DANGEROUS/FORBIDDEN com zero Arcane Resistance produz backlash 1:1 sobre dano elegível confirmado;
- `ARCANE_BACKLASH` é damage family terminal.

Backlash **não** pode:

- recursar;
- contar como dano ofensivo normal;
- critar;
- lifestealar;
- conceder offensive Mastery;
- alimentar proc chains ofensivas;
- gerar sustain credit.

### 7.6 Equipment/Containment — IMPLEMENTADO PARCIALMENTE

O plano 05A.06 ainda não estava formalmente fechado, porém `main@07263ae...` contém a implementação mergeada de **equipment set bonuses** (#20):

- contrato bounded de bônus de set;
- registry;
- datapack definitions/reload;
- resolver cumulativo por thresholds;
- publicação por runtime;
- inclusão no standard equipment snapshot/provider;
- contributions diagnósticas de Arcane/Corruption Resistance;
- hot reload e cleanup.

Isso prova a infraestrutura de set bonus. Não prova automaticamente que toda emergency protection/transação, todos os itens de conteúdo ou todo o tuning de Equipment/Containment estejam fechados.

### 7.7 Curios, Profiles, Public API, RPG Hazard Integration, HUD e Hardening — NÃO DECLARAR FECHADOS SEM NOVA EVIDÊNCIA

No status formal auditado, 05A.07–05A.12 permanecem abertos. Commits posteriores podem fechar subpartes específicas; o Chat 1 deve registrar exatamente a subparte comprovada, nunca promover o conjunto inteiro sem evidência.

## 8. RPG Skill Tree integration do Arcane Danger — PLANEJADO NO 05A.10

O contrato proposto exige:

- RPG adapter registra contributions via interfaces públicas Black Arcana;
- Black Arcana não lê attachments internos do RPG;
- contributions são snapshotadas no hazard activation boundary;
- Mastery/attribute gates permanecem separados da resistência;
- satisfazer requisito de cast não implica immunity;
- integração ausente/incompatível contribui zero e não desativa Black Arcana;
- Backlash nunca gera Mastery.

### Regra operacional

Enquanto 05A.10 não estiver realmente fechado em `main`, uma perk que dependa dessa bridge pode ter o design documentado, mas o componente de implementação fica **pending/fail-closed**.

## 9. Rituals — PREPARATÓRIO / NÃO CANÔNICO

Stage 06 possui trabalho avançado preservado em branches/protótipos para:

- ritual contracts;
- Eidolon ritual bridge;
- Malum spirit components;
- grand rituals.

Esse trabalho antecede o freeze final do Stage 05A e precisa ressincronizar. Não usar como hook da `main`.

## 10. Spell Domains — PREPARATÓRIO / PLANEJADO

Domínios planejados incluem:

- blood/curses;
- souls/death;
- projection/arsenal;
- space/displacement;
- black flame;
- forbidden domains;
- familiars/divination.

São identidade futura de conteúdo, não uma lista de spells/hooks canônicos já disponíveis.

## 11. Progression & Balance — PREPARATÓRIO / PLANEJADO

Stage 08 prevê:

- knowledge progression;
- RPG mastery gates;
- balance budget;
- diminishing returns/caps;
- server config presets.

Não usar números/tuning desse estágio como contrato atual de perk.

## 12. Hardening & Release — PLANEJADO

Stage 09 não é provider de perks.

## 13. Relação com Enshrouded e Volcanoes

Separações obrigatórias:

- Black Arcana Corruption ≠ Enshrouded Shroud/Exposure;
- Arcane Resistance ≠ Enshrouded `MagicResistanceService` de mobs corrompidos;
- Volcanoes heat/respiration/toxicity/pressure não contribuem automaticamente para Arcane Resistance;
- Flame Passage/Flame Ward não fornecem Arcane Resistance automaticamente.

Qualquer relação futura exige provider/bridge explícito, directional e bounded.

## 14. Regras obrigatórias para perks

1. Provider-native first: Black Arcana conserva a authority do seu casting e Arcane Danger.
2. Não transformar armor/generic magic resistance/Shroud/pressão/temperatura em Arcane Resistance por inferência.
3. Resistências são snapshotadas no boundary do cast; alteração posterior de gear não retroage.
4. Backlash jamais concede Mastery, lifesteal ou proc ofensivo.
5. Não criar segundo `ArcanaCastId`, ledger ou reflection pipeline.
6. Corruption/Strain não podem ser abusadas por evento contínuo arbitrário se o contrato exige milestones/casts específicos.
7. Componente ainda planejado fica fail-closed/pending no Chat 2.

## 15. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-reference-catalog/`
- `plans/02-arcana-core/`
- `plans/03-integration-layer/`
- `plans/04-world-safety/`
- `plans/05-casting-ux/`
- `plans/05a-arcane-danger/`
- `plans/06-rituals/`
- `plans/07-spell-domains/`
- `plans/08-progression-balance/`
- `plans/09-hardening-release/`
- commit `07263ae9bad12eba6ed500992991faa36ad598b2` para equipment set bonuses.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/05-cross-project-integration-matrix.md -->

# Matriz de Integração Cruzada — Projetos Próprios

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81ddb34bfc4c085fe023

Esta matriz existe para impedir **dupla autoridade**. Uma relação só pode ser tratada como provider operacional quando produtor e consumidor possuem boundary real compatível no estado auditado.

## 1. RPG Skill Tree → Black Arcana

**Objetivo de design:** RPG fornecer requisitos/perks/Mastery e, futuramente, contributions de Arcane/Corruption Resistance.

**Estado auditado:** a Integration Layer geral Black Arcana ↔ RPG é canônica, mas o provider específico de hazard descrito em `Black-Arcana/plans/05a-arcane-danger/10-rpg-skilltree-integration.md` permanece planejado/aberto no snapshot formal.

**Authority:**

- RPG: progressão, perks, atributos e Mastery;
- Black Arcana: Arcane Danger, resistance snapshots, Corruption/Strain/Backlash.

**Regra:** o RPG adapter deve registrar contributions através da API Black Arcana. Black Arcana não deve ler attachment/storage interno do RPG. Ausência/incompatibilidade = contribuição zero/fail-closed para a parcela dependente.

## 2. Black Arcana → RPG Skill Tree

**Objetivo de design:** perks/gates baseados em casts perigosos, danger tiers, domínios ou eventos causais.

**Estado:** Arcana Core e provenance/Backlash possuem contratos reais, mas o Stage 05 genérico do RPG não prova automaticamente um hook de Mastery para todo evento Black Arcana.

**Risco de duplicação:** contar o mesmo cast por:

1. cast request;
2. nominal damage;
3. confirmed damage;
4. Backlash;
5. evento auxiliar de provider.

Somente o evento causal definido pelo dossiê da perk pode conceder progressão. `ARCANE_BACKLASH` concede **zero** Mastery/offensive credit.

## 3. Enshrouded → RPG Skill Tree

**Objetivo:** perks de exploração, resistência, ecologia corrompida e progressão ligadas ao Shroud/Flame.

**Estado:** `ShroudQuery`, Exposure, entity corruption e `FlamePassageQuery` são boundaries canônicos do Enshrouded. Uma bridge RPG dedicada não está fechada no Stage 08 Enshrouded.

**Conduta:** o Chat 1 pode desenhar uma perk que consome query pública real, mas não deve inventar uma persistência/adapter que o provider ainda não oferece. Descobertas de core/ritual precisam identidade deduplicável. Exposição contínua não gera Mastery por tick.

## 4. RPG Skill Tree → Enshrouded

**Objetivo:** perks modificarem capacidades do jogador sem sequestrar a progressão Flame/Shroud.

**Estado:** nenhum contrato auditado concede ao RPG direito genérico de escrever diretamente:

- Flame Level;
- Passage Level;
- Shroud state;
- purification lifecycle.

**Regra:** Flame/Shroud continuam Enshrouded-owned. Qualquer futura resistência/ward vinda do RPG precisa entrar por provider/boundary do Enshrouded. Sem isso, a parcela fica fail-closed.

## 5. Volcanoes → RPG Skill Tree

**Objetivo:** perks de geologia, prospecção, tectônica, vulcanismo, Atmosphere, respiração, gases e pressão.

**Estado:** os sistemas ambientais do Volcanoes são canônicos. O adapter RPG deve ser verificado por perk/boundary antes de implementação.

**Candidatos de design com base real:**

- `GeologicalDepositSource`;
- identidade persistente de depósito;
- estados/eventos ambientais discretos quando expostos;
- `AtmosphereState`/sampling;
- pressure/protection services quando houver extension point consumível.

**Anti-abuso:** sem Mastery por tick, distância viajada, throughput de máquina, permanência em gás/pressão/calor ou rebuild spam.

## 6. RPG Skill Tree → Volcanoes

**Objetivo:** perks alterarem resposta/tolerância/eficiência do jogador.

**Estado:** o RPG não possui direito genérico de escrita direta em:

- Atmosphere;
- tectonic stress;
- eruption lifecycle;
- DepositRegistry;
- protected volumes;
- equipment consumption.

**Regra:** modificador só entra quando Volcanoes expõe provider/extension point compatível. Não criar segunda Atmosphere, segundo body-temperature state, segundo equipment consumer ou segundo eruption scheduler.

## 7. Volcanoes ↔ Enshrouded

**Estado atual:** não existe acoplamento ambiental implícito canônico.

- Shroud não é gás/poluição/`AtmosphereState`;
- Atmosphere não é Shroud severity;
- Shroud exposure não é falta de O₂ ou pressão;
- SO₂/particulado não aumentam Shroud por definição.

Uma futura bridge pode ter valor temático, mas precisa definir direção, authority e boundary explícitos. Até lá, a integração é **NÃO APLICÁVEL** para efeitos automáticos.

## 8. Black Arcana ↔ Enshrouded

**Estado atual:** sistemas semanticamente separados.

- Black Arcana Corruption ≠ Enshrouded Shroud/Exposure;
- Black Arcana Arcane Resistance ≠ Enshrouded mob `MagicResistanceService`;
- Flame Passage/Flame Ward não fornecem Arcane Resistance automaticamente;
- Enshrouded Madness ≠ Arcane Strain.

**Bridge futura:** provider explícito apenas. Sem bridge, contribuição = zero.

## 9. Black Arcana ↔ Volcanoes

**Estado atual:** Black Arcana exclui implicitamente stats ambientais do Volcanoes de Arcane Resistance. Não há conversão automática.

Uma perk híbrida pode exigir simultaneamente uma condição física do Volcanoes e um estado do Black Arcana, mas cada projeto conserva sua autoridade. Pressão, temperatura, O₂, SO₂, toxicidade e tectonic stress não viram Arcane Resistance por inferência.

## 10. RNS ↔ Volcanoes ↔ RPG

**Estado:** parcial/fail-closed para ownership físico de minério.

Volcanoes identifica contexto hidrotermal de iron/copper/gold quando a causalidade vulcânica é comprovada e já possui placement físico bounded/determinístico para Cu/Fe/Au. RNS permanece authority de prospecção/native metal worldgen enquanto o handoff seletivo de ownership não for fechado.

**Perks:** podem usar descoberta/prospecção de forma read-only e deduplicada; não devem, porém, tratar o placement físico Volcanoes como transferência já concluída de authority RNS.

## 11. Enshrouded magic resistance ↔ sistemas mágicos

`MagicResistanceService` é o reducer do Enshrouded para mobs corrompidos. Adapters futuros de Ars/Iron's ou outros providers produzem classificação/evidência e não aplicam uma segunda redução.

Uma perk não deve alterar o mesmo dano em dois reducers independentes.

## 12. Technomancer ↔ Create / AE2 / Oritech

A subtree Technomancer do RPG é canônica e possui gateways específicos para os três ecossistemas. Isso **não** significa que todo o plano genérico `RPG Stage 06 Create/AE2/Oritech` esteja fechado.

Perks de Technomancer podem depender dos gates/subtree realmente materializados. Hooks profundos de máquina/energia/logística ainda precisam da API real do provider correspondente.

## 13. Black Arcana Equipment ↔ RPG Itemization / Curios

Black Arcana já possui infraestrutura parcial de equipment profiles/set bonuses. O RPG Stage 11 de itemização e o Black Arcana 05A.07 Curios permanecem, no snapshot formal, não fechados integralmente.

Não unir os dois sistemas por antecipação. Qualquer future bridge deve declarar quem calcula Item Power/affix e quem calcula Arcane/Corruption Resistance/containment.

## 14. Tabela rápida de decisão

| Relação | Status auditado | Authority | Conduta da perk |
|---|---|---|---|
| RPG → Black Arcana hazard resistance | PLANEJADO no 05A.10 | BA hazard / RPG progress | design futuro; implementação pending/fail-closed |
| BA Backlash → RPG Mastery | PROIBIDO | Black Arcana | nunca conceder Mastery/proc/sustain |
| Enshrouded Shroud/Flame → RPG | boundaries Enshrouded canônicos; bridge RPG dedicada não fechada | Enshrouded | usar query real; dedup milestones; não tick |
| RPG → Flame/Passage/Shroud state | sem authority de escrita genérica | Enshrouded | não escrever diretamente |
| Volcanoes environment → RPG | systems canônicos; adapter por perk a verificar | Volcanoes | consultar hook real; sem Mastery contínua |
| RPG → Volcanoes world state | sem authority genérica | Volcanoes | somente provider explícito |
| BA Corruption ↔ Enshrouded Shroud | SEPARADOS | cada projeto | nenhuma conversão implícita |
| BA Arcane Resistance ← Volcanoes stats | EXCLUÍDO por default | Black Arcana | contribuição zero sem bridge |
| Volcanoes RNS mineral ownership | PARCIAL / handoff fail-closed | RNS prospecting/native worldgen até handoff seletivo | reconhecer placement físico Volcanoes sem declarar transferência de authority concluída |
| Enshrouded magic resistance | CANÔNICO | Enshrouded `MagicResistanceService` | adapter classifica; não reduzir duas vezes |
| Technomancer gateways | CANÔNICO nas subtrees | RPG para gate; provider externo para máquina/recurso | não extrapolar gateway para API inexistente |
| BA equipment set bonus | PARCIAL dentro de 05A ativo | Black Arcana | usar somente contracts já presentes; não declarar Stage completo |

## 15. Contrato obrigatório para perk híbrida

Se uma perk toca dois ou mais projetos próprios, o dossiê deve declarar:

1. **pipeline principal**;
2. provider que conserva a authority de cada recurso/estado;
3. consumers/adapters secundários;
4. hook/evento causal único;
5. identidade de deduplicação;
6. ordem de settlement quando houver dano/custo/reward;
7. fallback permitido;
8. fail-closed quando uma bridge opcional estiver ausente/incompatível;
9. teste de ausência do provider opcional;
10. teste que prove ausência de double-processing.

Integração temática nunca é suficiente para criar uma segunda fonte de verdade.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/06-snapshot-reconciliation.md -->

# Snapshot reconciliation — 2026-08-30

Esta nota registra a janela de auditoria usada para construir os dossiês de projetos próprios.

## Repositórios externos

- Volcanoes: `main@1d0da7ae7f19e06f60390fdeb0835720e2e40f1b`.
- Enshrouded: `main@de145be720f7f500f55e060982693312ed7f7bc3`.
- Black Arcana: `main@07263ae9bad12eba6ed500992991faa36ad598b2`.

## RPG Skill Tree

A auditoria inicial dos dossiês começou em `main@e49a1fa651abecfe096adb03c822482fcf9c3e7b`. Durante a preparação desta documentação a `main` avançou até `55463a195f8c3a87436399f71db19f29c8e85488`.

A reconciliação detectou avanço no Stage 03.06: os PRs #222/#223 implementaram e registraram o gate de drift do catálogo/wiki em CI. O arquivo `06-content-wiki-generation.md` continua aberto como plano integral, portanto somente esse subcomponente comprovado pode ser tratado como canônico; o Stage 03.06 inteiro não foi promovido.

## Regra de uso

O SHA anotado em cada dossiê representa a base usada para a análise extensa daquele projeto. Quando `main` avançar, `plans/STATUS.md`, arquivos de plano fechados/abertos e código/CI mais recentes prevalecem para determinar se um hook continua planejado, parcial ou canônico.

O Chat 1 deve revalidar apenas a superfície pertinente quando houver avanço posterior ao snapshot; não precisa refazer a leitura histórica completa se nenhuma área relacionada à perk mudou.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/07-chat1-provider-listing-checklist.md -->

# Chat 1 — Checklist de listagem de projetos próprios em perks

Use este checklist depois de auditar o efeito da perk e antes de fechar `Provider/Mods`.

Para cada um dos quatro projetos próprios potencialmente relacionado, responder:

1. **Relação:** provider principal, consumer secundário, bridge, gate, recurso, read-only, hazard, equipamento, Mastery/progressão, não aplicável ou fail-closed?
2. **Estado:** o contrato necessário está canônico em `main`, parcial, preparatório, planejado ou bloqueado?
3. **Authority:** qual projeto é dono da decisão/estado?
4. **Boundary:** qual API, serviço, hook, query, event ou provider concreto será usado?
5. **Versão/evidência:** qual versão/SHA/plano fechado comprova esse boundary?
6. **Causalidade:** qual ação discreta do jogador justifica proc/Mastery/reward?
7. **Deduplicação:** qual identidade impede processar a mesma ação mais de uma vez?
8. **Fallback:** a identidade da perk continua preservada sem o provider opcional?
9. **Fail-closed:** o que fica inativo quando o hook não é seguro ou não existe?
10. **Proibição:** qual estado/pipeline do provider a perk explicitamente não deve duplicar ou escrever diretamente?

## Como preencher `Provider/Mods`

Não escrever apenas `RPG Skill Tree`, `Volcanoes`, `Enshrouded` ou `Black Arcana` quando o uso real puder ser especificado.

Preferir formas semanticamente precisas, por exemplo:

- `RPG Skill Tree — NodeEffectRuntime / Core Progression`;
- `Volcanoes — GeologicalDepositSource (read-only)`;
- `Enshrouded — ShroudQuery + FlamePassageQuery`;
- `Black Arcana — Arcane Danger / Arcane Backlash provenance`.

Quando a relação for futura, registrar explicitamente `PLANEJADO — sem hook implementável no snapshot`; não mascarar como provider atual.

A resposta deste checklist deve ser incorporada ao dossiê individual da perk quando qualquer projeto próprio for pertinente.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/08-maintenance-rule.md -->

# Regra de manutenção dos projetos próprios

Os quatro dossiês são snapshots auditáveis, não substitutos para o estado vivo dos repositórios.

Ao detectar avanço posterior ao SHA registrado:

1. consultar `plans/STATUS.md` fresco do projeto;
2. identificar quais planos/subsistemas mudaram;
3. consultar código/testes/CI apenas na superfície alterada quando necessário;
4. atualizar o estado do subcomponente afetado sem promover o Stage inteiro indevidamente;
5. atualizar a matriz cruzada se uma bridge/authority mudou;
6. atualizar o apêndice temático pertinente somente se a mudança afetar Gameplay, Magia ou Tecnologia;
7. preservar separação entre runtime canônico, implementação parcial, trabalho preparatório e planejamento.

O objetivo é permitir atualização incremental precisa sem obrigar o Chat 1 a reauditar historicamente todos os quatro projetos a cada lote.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/09-source-policy.md -->

# Política de fontes

Fonte primária para descrever os projetos próprios: `plans/`, `plans/STATUS.md` e evidência de `main`. README raiz pode ajudar na navegação, mas nunca é prova suficiente de que um hook está implementado ou canônico.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/10-authority-rule.md -->

# Regra de autoridade

Quando dois projetos próprios tocam a mesma perk, a documentação deve indicar explicitamente qual projeto possui o estado/decisão e qual atua apenas como consumer/provider secundário. Similaridade temática não cria copropriedade.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/11-do-not-infer-hooks.md -->

# Não inferir hooks

Um hook de perk só é operacional quando há evidência suficiente em `main` ou contrato público canônico. Nomes de planos, classes propostas, namespaces, semelhança temática ou README não bastam.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/12-capability-delta-coverage.md -->

# Matriz de Cobertura e Delta de Capacidades — Projetos Próprios

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db81ee99a7e465e8333251

Os projetos próprios estão em desenvolvimento contínuo. O Chat 1 não pode limitar a auditoria às capacidades já citadas pelas perks existentes ou pelo snapshot anterior. Todo avanço de `main` deve ser examinado por **delta de capacidades**.

## Gate obrigatório por lote

Antes de fechar cada **lote exato de 10 perks**, o Chat 1 deve:

1. fazer fetch fresco das fontes operacionais dos quatro sistemas próprios — RPG Skill Tree + superfície Volcanoes na `main` unificada, e `main`/status de Enshrouded e Black Arcana enquanto separados;
2. comparar o SHA atual com o último SHA reconciliado;
3. registrar `SEM DELTA` quando não houver avanço relevante;
4. se houver avanço, inspecionar apenas os planos/subsistemas alterados, usando código/testes/CI quando necessário;
5. extrair toda capacidade jogável nova ou semanticamente alterada: recurso, resistência, estado, hazard, ação, equipamento, query, serviço, progressão, diagnóstico, milestone ou boundary público;
6. lançar cada capacidade na matriz de cobertura, **mesmo que nenhuma perk atual a cite**;
7. classificar cada linha antes do fechamento;
8. **não avançar o baseline do projeto enquanto qualquer capacidade detectada naquele delta estiver sem decisão, ação e fail-closed quando aplicável**.

O novo SHA só vira checkpoint depois que todas as linhas do delta tiverem disposição explícita.

A auditoria é bidirecional: **perk → provider** e **provider → árvore**.

Classificações permitidas: **COBERTA POR PERK EXISTENTE**, **PERK PRÓPRIA**, **ESPECIALIZAÇÃO**, **BRIDGE**, **COBERTO POR SISTEMA UNIVERSAL**, **PROGRESSÃO NATIVA AUTORITATIVA**, **SEM HOOK SEGURO**, **NÃO DEVE SER INTEGRADO**.

### Volcanoes — gatilhos de cobertura
Rastrear O₂/pressão parcial, respiração/oferta e consumo de ar, hipóxia, gases/filtros, pressão, protection equipment, geologia/prospecção, tectônica, vulcanismo/geotermia e novas integrações. Detectar O₂ não autoriza inventar `+X% oxigênio`; respeitar hook real.

### Black Arcana — gatilhos de cobertura
Rastrear Arcane Resistance, Corruption Resistance, Strain, Backlash/provenance, Danger profiles, equipment contributions e futuras superfícies públicas. Arcane Resistance não recebe generic magic resistance, Shroud, pressão ou temperatura sem provider explícito.

### Enshrouded — gatilhos de cobertura
Rastrear Shroud, Exposure/reserva/drain/recovery, Madness, Deadly/Red Shroud, Flame/Passage/Sanctuary/Ward/rituais, Corrupted Ecology/MagicResistanceService e Story/Lich conforme cada subcomponente chegar à `main`.

### RPG Skill Tree — gatilhos de cobertura
Rastrear novos atributos/recursos/boundaries, subtrees/classes/masteries/especializações e demais áreas conforme se tornem canônicas.

## Disposição do delta detectado em 2026-08-30

Os baselines de Volcanoes e Enshrouded abaixo só foram avançados **depois** das seguintes disposições explícitas:

| Projeto | Capacidade detectada | Estado real | Decisão principal | Ação / boundary | Fail-closed |
|---|---|---|---|---|---|
| Volcanoes | Produtor físico hidrotermal bounded/determinístico de Cu/Fe/Au | IMPLEMENTADO E CANÔNICO | **NÃO DEVE SER INTEGRADO** como mutação/produção de perk | Perks de geologia podem consumir `GeologicalDepositSource`/boundaries read-only; nenhuma perk deve invocar, reimplementar ou escrever o produtor/worldgen | Sem boundary público seguro, contribuição/mutação da perk = zero; nunca produzir minério |
| Volcanoes | Handoff seletivo de ownership Cu/Fe/Au para RNS + lifecycle bridge | IMPLEMENTADO PARCIALMENTE / aberto | **SEM HOOK SEGURO** | RNS continua authority de prospecção e native metal worldgen até `05-rns.md` fechar | Bridge permanece desabilitada; tin/nickel/zinc/silver continuam RNS-owned |
| Enshrouded | Sanctuary / Flame Ward do Stage 05 | IMPLEMENTADO E CANÔNICO | **PROGRESSÃO NATIVA AUTORITATIVA** | Não duplicar Flame Level/Sanctuary/Ward; futuras perks só consultam/contribuem por `FlameWardQuery`, `FlamePassageQuery` ou boundary explícito | Sem adapter = nenhuma escrita/contribuição; nunca criar Sanctuary paralelo |
| Enshrouded | Story State 06.01: ownership, encounter UUID, lifecycle e reward issuance idempotente | IMPLEMENTADO E CANÔNICO para 06.01; Stage 06 parcial | **PROGRESSÃO NATIVA AUTORITATIVA** | RPG só pode consumir milestones discretos por query/event/adapter público comprovado; não copiar narrativa, encounter state ou reward ledger | Sem boundary cross-mod comprovado, bridge/perk inativa; não inferir derrota/reward por entidade física ou tick |

Nenhuma dessas linhas autoriza inserir uma 11ª perk no lote ativo.

## Baseline reconciliado após as disposições

- RPG Skill Tree: `main@f448aa0b4f9df400011873e9ad26771209876ad4` para o snapshot documental anterior; a documentação deste ciclo foi posteriormente mergeada pela PR #229.
- Volcanoes: `main@602e0188c123ac8531d3413a5630daa22e3d761f` — baseline avançado somente após a classificação acima.
- Enshrouded: `main@77552a3d7f089a47908c109f5f8c19aff8a0f97d` — baseline avançado somente após a classificação acima.
- Black Arcana: `main@07263ae9bad12eba6ed500992991faa36ad598b2`.

### Delta externo — Mobstein
Mobstein `5.4.4` entrou na modlist em 2026-08-30. Gameplay/Magia: provider próprio de ressurreição, corpos/órgãos, experimentos, allies/bodyguards, estruturas e Witherstein. Tecnologia: `NÃO APLICÁVEL` por padrão; Clinical/Surgery Stretch e Subject Assembly Machine não provam FE/SU/Create/AE2/Oritech. `Attack/Health/Speed/Template` são perks internas do Mobstein, não nodes RPG.

A descoberta de uma lacuna não transforma um lote de 10 em 11. Registrar para ciclo posterior e parar conforme o protocolo.


---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/INDEX.md -->

# Índice operacional atual

1. README / governança
2. RPG Skill Tree
3. Volcanoes
4. Enshrouded
5. Black Arcana
6. Matriz de integração cruzada
7. Reconciliação de snapshots
8. Checklist Chat 1
9. Regra de manutenção
10. Política de fontes
11. Regra de autoridade
12. Não inferir hooks
13. Matriz de cobertura e delta
14. Delta A0071–A0080
15. Delta A0081–A0090
16. Delta A0200–A0209
17. Delta especial A0200–A0299

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/README-SOURCES.md -->

# Fontes de auditoria

Os dossiês desta pasta foram produzidos a partir das fontes operacionais e status pertinentes e, quando necessário para distinguir plano de runtime, código/testes/CI dos quatro sistemas. RPG Skill Tree e Volcanoes compartilham atualmente a `main` unificada; README raiz não é usado como prova suficiente de disponibilidade de hook.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/13-capability-delta-a0071-a0080.md -->

# Delta de Capacidades — A0071–A0080

Checkpoint documental do Chat 1. O delta de abertura não introduziu uma 11ª perk: mudanças concorrentes foram classificadas como sem delta jogável ou já cobertas. As dez perks preservaram seus fail-closed onde receipts/bindings não estavam disponíveis. Baselines foram avançados somente depois da disposição explícita de todos os deltas.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/14-capability-delta-a0081-a0090.md -->

# Delta de Capacidades — A0081–A0090

Checkpoint reconciliado em 31/08. RPG teve avanços editoriais/arquiteturais; Volcanoes fechou hardening/release; Enshrouded ficou sem delta; Black Arcana endureceu Stage 05A. Nenhum desses deltas autorizou transformar hazards/custos em sustain ofensivo nem remover fail-closed das perks do lote.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/15-capability-delta-a0200-a0209.md -->

# Delta de Capacidades — A0200–A0209

Lote adiantado autorizado explicitamente pelo usuário; a exceção não fecha A0091–A0199 nem habilita runtime. O checkpoint registra a consolidação nativa do Volcanoes no RPG Skill Tree, mudanças de Enshrouded/Black Arcana e blockers de classifiers/buckets/receipts. Os dossiês permanecem indisponíveis quando faltar capability/upstream real.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/projects/16-capability-delta-a0200-a0299.md -->

# Delta de Capacidades — A0200–A0299

Checkpoint especial reconciliado em 01/09 para a faixa A0200–A0299. Não implementa runtime nem inicia A0300. Registra contratos futuros como `SPECIALIST_GATE_RESOLVER_V1`, `ENDER_MASTERY_LANE_V1`, pipelines elementais/mitigação/receipts e mantém `UNAVAILABLE_NODE`/contribuição zero enquanto API/runtime não estiver comprovado. Volcanoes, Enshrouded e Black Arcana não devem ser convertidos em classifiers elementais por semelhança temática.

---

<!-- ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/MODLIST-DELTA-2026-09-07.md -->

# Delta físico da modlist — 2026-09-07

> **AUTORIDADE OPERACIONAL DO SNAPSHOT:** a modlist física atual possui **612 entradas top-level**, incluindo NeoForge + **611 JARs**. Dependências `jarjar` embutidas não contam como mods top-level.

## Fechamento quantitativo

- baseline físico de 06/09: **607** entradas top-level;
- updates do mesmo mod: **21**;
- adições reais: **9**;
- remoções físicas reais: **4**;
- saldo líquido: **+5**;
- estado físico atual: **612** entradas top-level;
- Notion reconciliado: **612 `Instalado`**, **612 JARs físicos distintos** e **613/613 registros `Verificado`**;
- runtime version audit: **605/605** JARs que declaram `modVersion` batem exatamente com `Versão 1.21.1` no Notion;
- **7/7** JARs sem `modVersion` físico permanecem sem runtime version inferida no Notion.

## 21 updates

- Alex's Mobs Continued: `2.1.9 → 2.1.10`;
- Amendments: `2.1.9 → 2.1.10`;
- Apotheosis: `8.7.0 → 8.8.0`;
- Ars 'n' Spells: `3.2.4 → 3.3.0`;
- Create: Bits 'n' Bobs: `2.3.0 → 2.3.1`;
- Create: Copycats+: `3.0.8 → 3.0.9`;
- Create: Cyber Goggles: `8.5.1 → 8.5.2`;
- Dynamic Trees - BetterEnd: `2.1.0 → 2.2.0`;
- Dynamic Trees - BetterNether: `2.1.0 → 2.2.0`;
- GTBC's SpellLib: `2.1.0 → 2.2.0`;
- InsaneLib: `2.4.31 → 2.4.32`;
- Lithostitched: `1.8.0+beta4 → 1.8.0+beta6`;
- Lychee Tweaker: `6.6.1 → 6.7.0`;
- MineColonies: `1.1.1376 → 1.1.1377`;
- Moonlight Lib: `3.6.1 → 3.6.3`;
- Petrolpark's Library: `1.5.8 → 1.5.9`;
- Photon: `2.2.5 → 2.2.6.a`;
- Polytone: `4.1.0 → 4.2.0`;
- Puzzles Lib: `21.1.56 → 21.1.59`;
- Supplementaries: `3.9.7 → 3.9.8`;
- Vampirism: `1.10.12 → 1.10.13`.

## 9 adições

- `BOMD-NeoForge-1.21-1.3.3.jar` — Bosses of Mass Destruction;
- `block_factorys_bosses-2.1.2-neo-1.21.1.jar` — Bosses'Rise;
- `CerbonsAPI-NeoForge-1.21-1.3.0.jar` — CERBON's API;
- `IMM v1.1.0-1.21.1.jar` — Integrated Mowzie's Mobs;
- `inventorysorter-1.21.1-24.0.24.jar` — Simple Inventory Sorter;
- `ironsable-wind-1.0.0.jar` — Ironsable x Wind's Spellbooks;
- `letsdo-furniture-neoforge-1.1.4.jar` — [Let's Do] Furniture;
- `mcjadecrops-1.1.1300.jar` — MineColonies: Jade crops;
- `morerelics-1.7.7-1.21.1.jar` — More Relics.

## 4 remoções físicas

- `alexscaves-2.0.2.jar`;
- `alexsmobs-1.22.9.jar`;
- `spore_1.21.1_2.2.0j_neo.jar`;
- `Infnexus-2.0.4-1.21.1.jar`.

Os antigos bloqueios de discovery por IDs duplicados `alexscaves` e `alexsmobs` estão fisicamente resolvidos no snapshot atual.

## Curadoria vigente

- **Alex's Caves Continued 1.0.9 — Manter**; é a única implementação `alexscaves` presente.
- **Alex's Mobs Continued 2.1.10 — Manter**; é a única implementação `alexsmobs` presente.
- **Create: Bits 'n' Bobs 2.3.1 — Manter**; o conflito visual com Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 continua sendo risco aceito e precisa de render-test.
- **More Relics 1.7.7 — Manter**; o pack usa Relics 0.12.8 apesar do upstream não declarar esse suporte. Integrações/perks provider-specific permanecem **FAIL-CLOSED** até teste real.
- **Integrated Mowzie's Mobs 1.1.0 — Manter**; o runtime local é autoridade física, mas a publicação pública exata dessa build não deve ser inferida.

## Regra

Presença física, filename e runtime version vêm da modlist auditada. Decisão curatorial é uma camada separada. Update de versão não revalida automaticamente hook/API de perk.
