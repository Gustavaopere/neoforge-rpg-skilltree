# Status canônico dos planos

Última auditoria de fechamento: **2026-08-30**.

Planejamento do Stage 10 adicionado em **2026-08-28**. Os subplanos `10.01 — Proveniência, referências e licenças`, `10.02 — Inventário do modpack e cobertura de conteúdo`, `10.03 — Modelo de dados, identidade e providers`, `10.04 — Descoberta, progresso e recompensas`, `10.05 — Fauna, criaturas e análise de entidades`, `10.06 — Flora, árvores, fungos e cultivos`, `10.07 — Loot, dieta, reprodução e ecologia` e `10.08 — Biomas, estruturas e dimensões` foram implementados, validados, integrados e auditados.

Planejamento do Stage 11 adicionado em **2026-08-29** pelo PR #188. O estágio possui **15 subplanos**; `11.01 — Domínio, invariantes e autoridade` foi implementado e validado pelo PR #232, restando 14 subplanos abertos. A base global permanece em **90 subplanos**.

Base auditada para os fechamentos históricos anteriores ao Stage 10: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.

Fechamento do Stage 10.01 auditado contra `main@b4d84e9078b27349cc691ec2875574ff67246101`, com CI pós-merge `33187232908` / run #755 GREEN completo.

Fechamento do Stage 10.02 auditado contra `main@259eb2d4310f5c7c84dcf0db1a5c3c5f6522df1d`, com CI pós-merge `33190772894` / run #805 GREEN completo.

Fechamento do Stage 10.03 auditado contra `main@112d9266de9ece584f2f58adff03ffb6c8776ca6`, com CI pós-merge `33195224667` / run #858 GREEN completo.

Fechamento do Stage 01.01 auditado contra `main@5171ec7e099be545663b4a1ac989c36fc68835eb`, após consolidação do attachment canônico, dimension-change sync e query somente-leitura; o último gate funcional foi CI `33198679352` / run #908 GREEN completo.

Fechamento do Stage 01.02 auditado sobre a implementação já integrada por PR #74 e PR #77, com pós-merge RPG CI `33212979768` GREEN completo; a documentação foi reconciliada novamente contra a `main` corrente após os merges subsequentes.

Fechamento do Stage 10.04 auditado contra `main@8fdfff0c518fa40099b9459e279118cdbef1b2fc`, após integração do PR #71. O CI focal pós-merge `33201053431` / Compendium Discovery #38 e o CI completo `33201053442` / RPG Skill Tree #921 fecharam GREEN, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.

Fechamento do Stage 10.05 auditado contra `main@33360ba2a44148ddce2d4f8c825066985eee9fb6`, após integração do PR #76. Os CIs pós-merge `33212930323` / Compendium Entities #38, `33212930426` / Compendium Discovery #115 e `33212930354` / RPG Skill Tree #998 fecharam GREEN; o CI completo incluiu validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final do commit.

Fechamento funcional do Stage 10.06 auditado contra `main@68f694e98c068f3274cd1ecb6bd7588951833fb5`, após integração do PR #88. Os CIs pós-merge `33220942187` / Compendium Flora #35, `33220942179` / Compendium Entities #101, `33220942213` / Compendium Discovery #178 e `33220942238` / RPG Skill Tree #1061 fecharam GREEN; o CI completo incluiu todos os validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final do commit.

Fechamento do Stage 08.01 auditado contra `main@2b8e5d10b70704598c0f175a3a9bf1ad0af5586e`, após integração dos PRs #97 e #98. O CI da fundação quest-facing `33225421326` e o CI de especializações/versionamento `33227098892` fecharam GREEN completos, ambos com NeoForge build, verificação do JAR e dedicated-server smoke; os workflows Compendium associados também fecharam GREEN.

Fechamento funcional do Stage 10.07 auditado contra `main@03403fc3f7934b0e2b2c9a5cd0a9e6606a2ba7d9`, após integração do PR #99. Os CIs pós-merge `33228111273` / Compendium Ecology #111, `33228111253` / Compendium Flora #149, `33228111262` / Compendium Entities #215, `33228111257` / Compendium Discovery #292 e `33228111266` / RPG Skill Tree #1175 fecharam GREEN; o CI completo incluiu Core/tests/validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final do commit.

Fechamento do Stage 00.01 auditado contra `main@0f008fc3bc1767e74da777fcc02e37fd19acb263`, após integração do PR #108. O `Foundation Bootstrap Contract` pós-merge `33229822213` e o `RPG Skill Tree CI` pós-merge `33229822237` fecharam GREEN; o CI completo incluiu wrapper contract, Core, JUnit 5, NeoForge GameTests, Compendium, validators, drift, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final de sucesso.

Fechamento funcional do Stage 10.08 auditado contra `main@c980f7835a01ef038e34d1ea0fab66d33e8bb03c`, após integração do PR #110. Os CIs pós-merge `33230100328` / Compendium Flora #215, `33230100330` / Compendium Entities #281, `33230100337` / Foundation Bootstrap #19, `33230100355` / Compendium Ecology #254, `33230100371` / Compendium Discovery #358, `33230100386` / Compendium World #16 e `33230100358` / RPG Skill Tree #1241 fecharam GREEN; o CI completo incluiu Core, JUnit 5, NeoForge GameTests, Compendium, validators, drift, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final de sucesso.

Fechamento do Stage 00.03 auditado contra `main@4f48fefa15477023ce2dcb9d56c36b586a6b16ea`, após integração do PR #113. O TDD RED `33230185322` detectou a ausência do registry central; um candidato intermediário revelou ainda `ClassNotFoundException` real do target Identity2 no Mixin, corrigido por isolamento e gate early-startup. Os CIs pós-merge `33230834923` / Foundation Bootstrap #55, `33230834955` / Foundation Optional Integrations #17 e `33230834856` / RPG Skill Tree #1277 fecharam GREEN. O CI completo incluiu Core, JUnit 5, NeoForge GameTests, validators, drift, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e status final de sucesso; o smoke confirmou os sete providers opcionais ausentes e `Classloading errors: none`.

Fechamento do Stage 00.04 auditado contra `main@4a13ac7c8deda8827e755d100223985f07319e8e`, após integração do PR #120. O RED inicial `33231244859` exigiu o boundary de diagnostics; a implementação consolidou taxonomia estruturada, reload fail-visible com path/resource IDs, anti-spam de falhas persistentes e documentação reproduzível dos gates. Os CIs pós-merge `33244389124` / Foundation Diagnostics #22, `33244389122` / Foundation Bootstrap #113, `33244389143` / Foundation Optional Integrations #75 e `33244389119` / RPG Skill Tree #1335 fecharam GREEN. O CI completo cobriu Core, JUnit 5, NeoForge GameTests, Compendium, validators, drift, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final de sucesso.

Fechamento do Stage 01.03 auditado contra `main@398f160f5bec74629331475eff1e60d3cdeb0958`, após integração do PR #135. O RED `33245655826` falhou exatamente porque o GameTest runtime ainda não existia. O head sincronizado final `4bcefe741d641da6b7d14b1b89d4214bce9bc3ff` passou o CI completo `33246262135`, incluindo NeoForge GameTests, `Attribute runtime validation`, build, JAR e dedicated-server smoke. O pós-merge canônico `33246405719` repetiu toda a matriz em GREEN, incluindo upload do JAR e publicação do status final de sucesso.

Fechamento do Stage 01.04 auditado contra `main@461f386179a3c904f52793354998c29ff7979dd7`, após integração do PR #128. O head funcional `a02b76ae004aa48e3fe2efef01c33c31f1fd267f` passou o RPG Skill Tree CI `33244574779` e os workflows Foundation/Compendium associados. O pós-merge funcional `33244700777` / run #1341 fechou GREEN completo, incluindo Core, JUnit 5, NeoForge GameTests, validators, drift, build, JAR, dedicated-server smoke, upload do artefato e status final de sucesso. O fechamento formal foi mergeado como `845335058bbe8cd4f5e6f4c140bf4c8fd9f14063`, cujo CI pós-merge `33262871523` / run #1407 também terminou GREEN completo. Auditoria posterior confirmou que o `AttachmentType` canônico continua usando `CanonicalPlayerAttachmentSerializer`, o mesmo codec exercitado pelos testes de round-trip.

Fechamento do Stage 02.01 e 02.02 integrado em `main@6afc351449bfb04a14f35c44aebb37c77802eec3`. O head sincronizado `62733fa01f4eab936df336af41c7f835ef16a42e` passou o RPG Skill Tree CI `33263146200` / run #1412 e os workflows Foundation/Compendium, incluindo NeoForge build, verificação do JAR e dedicated-server smoke. Os dois planos estão materializados na `main` como `✅-01-relevant-player-level.md` e `✅-02-territory-area-level.md`.

Fechamento do Stage 01.05 auditado sobre a implementação integrada pelo PR #131, head `9cfe75564686192b3c63d55ef4c9865b31aba79d`, mergeada como `fd2879c1c7375ab006cafb022f10bd8700f2da9c`. O CI do head `33244812701` / run #1344 e o CI pós-merge `33244953339` / run #1346 fecharam GREEN completos, incluindo Core, JUnit 5, NeoForge GameTests, validators, drift, NeoForge build, verificação do JAR e dedicated-server smoke. A auditoria direta confirmou a barreira de imports do package `core`, snapshots/read boundaries sem efeitos laterais e ausência de vazamento de attachments/estado persistido para adapters de integração.

Fechamento dos Stages 02.03, 02.04 e 02.05 integrado em `main@7cfa1e988c81619ce8209a39b330a7b181785a88`, após o acceptance funcional de multiplayer/performance já ter sido integrado em `main@98c5f33952559ea9eea059169b7f486837fc20f1`. O PR docs-only #174 passou o RPG Skill Tree CI `33272133374` / run #1605 GREEN completo antes do merge; o CI pós-merge `33272264027` / run #1617 repetiu Core, JUnit 5, todos os NeoForge GameTests, validators, build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final em GREEN.

Reconciliação do Stage 03 confirma que `✅-01-data-schema-loaders.md` e `✅-02-graph-layout-validation.md` já estão materializados na `main`; seus heads funcionais `4a10cc875dc95b04474b85e00b4645fd3340be04` e `f641e99e87882575705b88b21fa68d582a1f9db8` passaram respectivamente os RPG Skill Tree CI `33275095434` / run #1676 e `33278390140` / run #1700 em GREEN completo. O fechamento do Stage 03.03 foi integrado pela PR #189 no merge `efe45b9e5ee15fd37169086ef5e17887e98d4059`; a PR #186 preserva o histórico de implementação/TDD e foi reconhecida pelo GitHub como merged quando o mesmo head entrou na `main`. Após quatro ciclos TDD RED, o head funcional `3b1d01a8829d3c99dc8b242e8ab538e46046eceb` passou o RPG Skill Tree CI `33280107469` / run #1728 GREEN completo; o head final `cde8b5dd0c0e19e91aa528f45bf227fcb0ecc1b3` passou novamente os CIs #1732 e #1737 em GREEN completo, incluindo Core, JUnit 5, todos os 11 NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke; os workflows Foundation/Compendium associados também fecharam GREEN.

Fechamento do Stage 03.04 integrado pela PR #194 em `main@c1523ac5211543d26cdb54387eb2089510822ff3`. O runtime unificado `NodeEffectRuntime` passou a reconciliar efeitos numéricos e comportamentais pela mesma fronteira autoritativa; IDs gerados são estáveis e sensíveis à origem, mudanças de rank não criam identidade persistente nova, reload/login/compra/respec convergem no mesmo refresh e providers opcionais ausentes permanecem fail-soft. O head funcional `5611ebbf6a2052f4ac3a4f0ac41327376ee13463` passou o RPG Skill Tree CI `33287243581` / run #1845 GREEN completo, incluindo a aceitação NeoForge real de `ADD_FLAT`, `ADD_PERCENT_BASE` e `MULTIPLY_TOTAL`; o head documental final `c8950388e1de4281372e79f11e89a8f827ce3f5f` passou o CI `33287464600` / run #1855 GREEN completo, com Core, JUnit 5, NeoForge GameTests, validators, build, JAR e dedicated-server smoke, e todos os workflows Foundation/Compendium associados também fecharam GREEN.

Fechamento funcional do Stage 11.01 preparado no PR #232. O TDD RED `33308736024` falhou exclusivamente pela ausência dos tipos do novo domínio. O head funcional corrigido após review `1fc372df5eda3e2beaa4292224cf59a6cf967d90` passou o RPG Skill Tree CI `33309096174` / run #2100 GREEN completo, incluindo JUnit 5, NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke; os workflows Foundation/Compendium associados também fecharam GREEN. O fechamento formal é materializado por `✅-01-domain-invariants.md` no mesmo PR.

A auditoria considera código, recursos, testes, validators e CI já integrados na `main`. Trabalho existente apenas em PR/branch aberta **não conta como concluído**.

## Resultado

**32 / 90 subplanos concluídos formalmente.**

- `00-foundation/✅-01-environment-bootstrap.md`
- `00-foundation/✅-02-client-server-boundaries.md`
- `00-foundation/✅-03-optional-integrations.md`
- `00-foundation/✅-04-diagnostics-testing.md`
- `01-rpg-core/✅-01-player-state.md`
- `01-rpg-core/✅-02-progression-services.md`
- `01-rpg-core/✅-03-attributes-modifiers.md`
- `01-rpg-core/✅-04-persistence-sync.md`
- `01-rpg-core/✅-05-core-api-invariants.md`
- `02-progression-world-scaling/✅-01-relevant-player-level.md`
- `02-progression-world-scaling/✅-02-territory-area-level.md`
- `02-progression-world-scaling/✅-03-entity-level.md`
- `02-progression-world-scaling/✅-04-rarity-archetypes.md`
- `02-progression-world-scaling/✅-05-scaling-rewards-performance.md`
- `03-skill-tree-perks/✅-01-data-schema-loaders.md`
- `03-skill-tree-perks/✅-02-graph-layout-validation.md`
- `03-skill-tree-perks/✅-03-purchase-ranks.md`
- `03-skill-tree-perks/✅-04-effects-runtime.md`
- `03-skill-tree-perks/✅-05-respec.md`
- `04-classes-masteries-specializations/✅-06-class-subtrees.md`
- `06-integrations/✅-03-irons-spellbooks.md`
- `06-integrations/✅-05-goety-malum-eidolon.md`
- `08-quests-progression-hooks/✅-01-public-query-api.md`
- `10-compendio-natural/✅-01-proveniencia-licencas.md`
- `10-compendio-natural/✅-02-inventario-modpack.md`
- `10-compendio-natural/✅-03-modelo-dados-identidade.md`
- `10-compendio-natural/✅-04-descoberta-progresso.md`
- `10-compendio-natural/✅-05-fauna-entidades.md`
- `10-compendio-natural/✅-06-flora-arvores-cultivos.md`
- `10-compendio-natural/✅-07-loot-dieta-reproducao-ecologia.md`
- `10-compendio-natural/✅-08-biomas-estruturas-dimensoes.md`
- `11-itemization-equipment-progression/✅-01-domain-invariants.md`

Cada arquivo concluído segue o padrão documental do Volcanoes: checklist `[x]`, contrato efetivamente implementado, evidência de verificação e `Acceptance: satisfied`.

## Progresso por estágio

| Estágio | Concluídos | Total | Estado geral |
| --- | ---: | ---: | --- |
| 00 Foundation | 4 | 4 | CONCLUÍDO |
| 01 RPG Core | 5 | 5 | CONCLUÍDO |
| 02 Progression & World Scaling | 5 | 5 | CONCLUÍDO |
| 03 Skill Tree & Perks | 5 | 6 | EM ANDAMENTO |
| 04 Classes, Masteries & Specializations | 1 | 6 | EM ANDAMENTO |
| 05 Combat & Magic Hooks | 0 | 6 | EM ANDAMENTO |
| 06 Integrations | 2 | 9 | EM ANDAMENTO |
| 07 Data, Network & UI | 0 | 6 | EM ANDAMENTO |
| 08 Quest & Progression Hooks | 1 | 6 | EM ANDAMENTO |
| 09 Hardening & Release | 0 | 7 | EM ANDAMENTO contínuo |
| 10 Compêndio Natural | 8 | 15 | EM ANDAMENTO |
| 11 Itemização & Progressão de Equipamentos | 1 | 15 | EM ANDAMENTO |
| **Total** | **32** | **90** | |

## Por que os demais continuam abertos

### 00 — Foundation

Stage 00 concluído. `✅-01-environment-bootstrap.md` fixa e verifica ambiente, metadata, bootstrap determinístico e convenção de IDs; `✅-02-client-server-boundaries.md` fecha separação de lados e autoridade; `✅-03-optional-integrations.md` centraliza detecção/isola adapters e prova core-only sem falha de classloading; `✅-04-diagnostics-testing.md` fecha diagnostics estruturados, reload fail-visible com contexto de resource, anti-spam, comandos locais reproduzíveis e gates permanentes de testes/build/JAR/dedicated-server smoke. Matrizes de provider **presente** permanecem corretamente nos respectivos estágios de integração.

### 01 — RPG Core

Stage 01 concluído. `✅-01-player-state.md` fecha o envelope persistente canônico e a projeção read-only; `✅-02-progression-services.md` fecha XP grant/rollback, recompensas de level, mastery replay-safe, storage boundary, idempotência e eventos pós-persistência; `✅-03-attributes-modifiers.md` fecha identidade/composição/recomputação de modifiers e a prova NeoForge real sem stacking ou drift; `✅-04-persistence-sync.md` fecha versionamento/migração, round-trip do codec real, lifecycle/mutation sync e autoridade server-side; `✅-05-core-api-invariants.md` fecha queries versus mutations, snapshots imutáveis, invariantes de IDs/limites e a barreira que impede o core de depender de UI, runtime Minecraft/NeoForge ou mods opcionais.

### 02 — Progression & World Scaling

Stage 02 concluído. `✅-01-relevant-player-level.md` fecha seleção bounded/local/party; `✅-02-territory-area-level.md` fecha resolução e transição determinísticas de território; `✅-03-entity-level.md` fecha lifecycle persistido e fallback conservador para entidades externas; `✅-04-rarity-archetypes.md` fecha rarity/archetype persistidos e modifiers idempotentes; `✅-05-scaling-rewards-performance.md` fecha curvas independentes e capped, XP/loot bounded pela mesma reward policy, acceptance multiplayer e budgets algorítmicos dos hot paths.

### 03 — Skill Tree & Perks

Schema/loaders, grafo/layout, compra/ranks, effects runtime e respec estão fechados. O runtime de efeitos agora possui precedência explícita, IDs determinísticos, publicação atômica, cleanup/reconciliação idempotente, suporte a behaviors e proteção fail-soft para providers opcionais. Permanece aberto apenas `06-content-wiki-generation.md`, responsável pela geração automática da `wiki/`.

### 04 — Classes, Masteries & Specializations

As quatro subtrees dedicadas estão fechadas. Class resolution, confluences, curvas/caps de masteries, provider identities e specializations ainda possuem contratos finais pendentes.

### 05 — Combat & Magic Hooks

O pipeline canônico final por hit/projétil/magia ainda não está formalmente fechado. O contrato conjunto Ars + Iron's para stats genéricas também permanece pendente.

### 06 — Integrations

Iron's e o bloco Goety/Malum/Eidolon estão fechados. Epic Fight, Ars, Identity2, Apothic, Create/AE2/Oritech e as matrizes provider-presente ainda possuem trabalho pendente. A segurança core-only/ausência global já está fechada no Stage 00.03.

### 07 — Data, Network & UI

Loaders, packets e UI já existem, mas o acceptance final ainda exige reload cross-catalog, versionamento formal de protocolo/schemas e resolução completa de efeitos/requisitos/erros na UI.

### 08 — Quest & Progression Hooks

`✅-01-public-query-api.md` está fechado. Os subplanos `02-progression-rewards`, `03-data-driven-conditions`, `04-idempotency-ledger`, `05-ftbquests-npc-adapters` e `06-authoring-diagnostics` continuam abertos e devem ser fechados separadamente.

### 09 — Hardening & Release

Nenhum gate final pode ser fechado enquanto existirem blockers de migração, compatibilidade, performance e release. A suíte atual é forte, mas ainda não substitui profiling/budgets, migrations e matrizes provider-presente.

### 10 — Compêndio Natural

`✅-01-proveniencia-licencas.md` até `✅-08-biomas-estruturas-dimensoes.md` estão fechados. O Stage 10 agora possui proveniência/licenças auditadas, inventário reproduzível, identidade/fatos/relações canônicas, snapshots imutáveis, descoberta server-authoritative, cobertura técnica de entidades e flora, enriquecimento seguro de loot/ecologia e geografia runtime de biomas, estruturas e dimensões.

O 10.07 adicionou alvos tipados de relação (`ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK`, `BLOCK_TAG`) sem criar `ITEM` como kind canônico; análise estrutural de loot sem rolling/efeitos; semânticas separadas de alimento/atração/reprodução; domesticação separando capability de espécie de estado contextual; relações ecológicas source-aware; e snapshot de loot atômico em datapack reload. Integrações externas completas permanecem no 10.11 e save/rede/cache global no 10.13.

O 10.08 adicionou providers de `BIOME`, `STRUCTURE` e `DIMENSION` com identidade por registry ID; coleta server-authoritative de registries e níveis carregados; índices `Dimension ↔ Biome` e `Structure ↔ Biome`, com `Structure ↔ Dimension` somente quando a interseção de biomas fornece evidência; snapshot geográfico imutável e publicação validada por cobertura; reload de datapack; e structure discovery confirmada pelo servidor de forma bounded. Suites opcionais de worldgen permanecem desacopladas e fatos editoriais não deriváveis genericamente continuam reservados aos estágios posteriores.

A materialização do snapshot completo da instância do pack continua como tarefa operacional do gate de conteúdo; o runtime é a autoridade. O próximo passo causal é `09-ui-modelo3d-notas.md`.

### 11 — Itemização e Progressão de Equipamentos

`✅-01-domain-invariants.md` está fechado. O domínio canônico agora fixa identidade (`instanceId`/seed/schema), sete ranks, `ItemPower`, as três famílias Prefix/Suffix/Infix com 1..5 rolls por família, fontes de geração, primeira geração imutável, query versus mutation, política de evolução/cópia e barreira contra imports opcionais/compat. Os 14 subplanos seguintes continuam abertos e devem reutilizar esse contrato sem criar representações concorrentes. O próximo passo causal é `02-equipment-classification.md`.

## Evidência de regressão atual

O fechamento funcional mais recente é Stage 11.01 no PR #232. O TDD RED `33308736024` confirmou a ausência intencional dos tipos; o head funcional corrigido após review `1fc372df5eda3e2beaa4292224cf59a6cf967d90` passou o RPG Skill Tree CI `33309096174` / run #2100 GREEN completo, incluindo Core, JUnit 5, NeoForge GameTests, validators, drift, NeoForge build, verificação do JAR e dedicated-server smoke. Todos os oito workflows Foundation/Compendium associados ao mesmo head também fecharam GREEN. Os fechamentos anteriores, inclusive Stage 03.04 em `main@c1523ac5211543d26cdb54387eb2089510822ff3`, permanecem preservados pela mesma matriz de regressão.

## Convenção

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = implementado, validado, integrado e auditado contra o Acceptance do próprio arquivo.

Se uma regressão ou alteração arquitetural invalidar um Acceptance já fechado, o arquivo deve voltar ao nome sem `✅-`.