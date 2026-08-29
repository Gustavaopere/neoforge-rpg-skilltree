# Status canônico dos planos

Última auditoria de fechamento: **2026-08-28**.

Planejamento do Stage 10 adicionado em **2026-08-28**. Os subplanos `10.01 — Proveniência, referências e licenças`, `10.02 — Inventário do modpack e cobertura de conteúdo`, `10.03 — Modelo de dados, identidade e providers`, `10.04 — Descoberta, progresso e recompensas`, `10.05 — Fauna, criaturas e análise de entidades`, `10.06 — Flora, árvores, fungos e cultivos` e `10.07 — Loot, dieta, reprodução e ecologia` foram implementados, validados, integrados e auditados.

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

A auditoria considera código, recursos, testes, validators e CI já integrados na `main`. Trabalho existente apenas em PR/branch aberta **não conta como concluído**.

## Resultado

**16 / 75 subplanos concluídos formalmente.**

- `00-foundation/✅-01-environment-bootstrap.md`
- `00-foundation/✅-02-client-server-boundaries.md`
- `01-rpg-core/✅-01-player-state.md`
- `01-rpg-core/✅-02-progression-services.md`
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

Cada arquivo concluído segue o padrão documental do Volcanoes: checklist `[x]`, contrato efetivamente implementado, evidência de verificação e `Acceptance: satisfied`.

## Progresso por estágio

| Estágio | Concluídos | Total | Estado geral |
| --- | ---: | ---: | --- |
| 00 Foundation | 2 | 4 | EM ANDAMENTO |
| 01 RPG Core | 2 | 5 | EM ANDAMENTO |
| 02 Progression & World Scaling | 0 | 5 | EM ANDAMENTO |
| 03 Skill Tree & Perks | 1 | 6 | EM ANDAMENTO |
| 04 Classes, Masteries & Specializations | 1 | 6 | EM ANDAMENTO |
| 05 Combat & Magic Hooks | 0 | 6 | EM ANDAMENTO |
| 06 Integrations | 2 | 9 | EM ANDAMENTO |
| 07 Data, Network & UI | 0 | 6 | EM ANDAMENTO |
| 08 Quest & Progression Hooks | 1 | 6 | EM ANDAMENTO |
| 09 Hardening & Release | 0 | 7 | EM ANDAMENTO contínuo |
| 10 Compêndio Natural | 7 | 15 | EM ANDAMENTO |
| **Total** | **16** | **75** | |

## Por que os demais continuam abertos

### 00 — Foundation

`✅-01-environment-bootstrap.md` está fechado: ambiente MC 1.21.1/NeoForge 21.1.248/Java 21, Gradle Wrapper 8.14, metadata required/optional, bootstrap comum determinístico e convenção de IDs persistidos estão protegidos por contrato e CI. O baseline não possui config de usuário registrada; futuras configs são obrigadas pelo contrato a usar `ModConfigSpec`. `03-optional-integrations` continua aberto porque ainda falta a matriz completa de ausência individual de providers e a centralização formal da detecção; `04-diagnostics-testing` tem CI forte, mas logging/diagnóstico padronizado e reprodução local dos gates ainda não estão fechados.

### 01 — RPG Core

`✅-01-player-state.md` está fechado: `CANONICAL_PLAYER` é o envelope persistente de escrita normal e `CanonicalPlayerSnapshot` fornece a projeção read-only. `✅-02-progression-services.md` também está fechado: XP grant/rollback, recompensas de level, mastery replay-safe, storage boundary, idempotência e eventos pós-persistência convergem em serviços canônicos. Os subplanos `03-attributes-modifiers`, `04-persistence-sync` e `05-core-api-invariants` continuam abertos porque ainda exigem fechamento formal de recomputação/modificadores, migração/sync/corrupção e invariantes públicas de API/boundary.

### 02 — Progression & World Scaling

Relevant-player, território, entity level, rarity e stat scaling possuem fundações reais e testadas, mas políticas finais de raio/party, fórmulas, caps, balance, persistência e performance ainda precisam de fechamento formal.

### 03 — Skill Tree & Perks

O respec está fechado. Os demais subplanos ainda têm gaps objetivos em reload/IDs, cycles/orphans, motivos legíveis de rejeição, composição de efeitos e geração automática da `wiki/`.

### 04 — Classes, Masteries & Specializations

As quatro subtrees dedicadas estão fechadas. Class resolution, confluences, curvas/caps de masteries, provider identities e specializations ainda possuem contratos finais pendentes.

### 05 — Combat & Magic Hooks

O pipeline canônico final por hit/projétil/magia ainda não está formalmente fechado. O contrato conjunto Ars + Iron's para stats genéricas também permanece pendente.

### 06 — Integrations

Iron's e o bloco Goety/Malum/Eidolon estão fechados. Epic Fight, Ars, Identity2, Apothic, Create/AE2/Oritech e a matriz global de presença/ausência ainda possuem trabalho pendente.

### 07 — Data, Network & UI

Loaders, packets e UI já existem, mas o acceptance final ainda exige reload cross-catalog, versionamento formal de protocolo/schemas e resolução completa de efeitos/requisitos/erros na UI.

### 08 — Quest & Progression Hooks

`✅-01-public-query-api.md` está fechado. Os subplanos `02-progression-rewards`, `03-data-driven-conditions`, `04-idempotency-ledger`, `05-ftbquests-npc-adapters` e `06-authoring-diagnostics` continuam abertos e devem ser fechados separadamente.

### 09 — Hardening & Release

Nenhum gate final pode ser fechado enquanto existirem blockers de migração, compatibilidade, performance e release. A suíte atual é forte, mas ainda não substitui profiling/budgets, migrations e matriz completa de optional mods.

### 10 — Compêndio Natural

`✅-01-proveniencia-licencas.md` até `✅-07-loot-dieta-reproducao-ecologia.md` estão fechados. O Stage 10 agora possui proveniência/licenças auditadas, inventário reproduzível, identidade/fatos/relações canônicas, snapshots imutáveis, descoberta server-authoritative, cobertura técnica de entidades e flora e enriquecimento seguro de loot/ecologia.

O 10.07 adicionou alvos tipados de relação (`ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK`, `BLOCK_TAG`) sem criar `ITEM` como kind canônico; análise estrutural de loot sem rolling/efeitos; semânticas separadas de alimento/atração/reprodução; domesticação separando capability de espécie de estado contextual; relações ecológicas source-aware; e snapshot de loot atômico em datapack reload. Integrações externas completas permanecem no 10.11 e save/rede/cache global no 10.13.

A materialização do snapshot completo da instância do pack continua como tarefa operacional do gate de conteúdo; o runtime é a autoridade. O próximo passo causal é `08-biomas-estruturas-dimensoes.md`.

## Evidência de regressão atual

O fechamento formal mais recente é Stage 00.01 em `main@0f008fc3bc1767e74da777fcc02e37fd19acb263`. `Foundation Bootstrap Contract` `33229822213` e `RPG Skill Tree CI` `33229822237` passaram no commit integrado; o CI completo cobriu wrapper, Core, JUnit 5, NeoForge GameTests, todos os validators, build, JAR e dedicated-server smoke.

## Convenção

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = implementado, validado, integrado e auditado contra o Acceptance do próprio arquivo.

Se uma regressão ou alteração arquitetural invalidar um Acceptance já fechado, o arquivo deve voltar ao nome sem `✅-`.
