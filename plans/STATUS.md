# Status canônico dos planos

Última auditoria de fechamento: **2026-08-28**.

Planejamento do Stage 10 adicionado em **2026-08-28**. Os subplanos `10.01 — Proveniência, referências e licenças`, `10.02 — Inventário do modpack e cobertura de conteúdo`, `10.03 — Modelo de dados, identidade e providers` e `10.04 — Descoberta, progresso e recompensas` foram implementados, validados, integrados e auditados no mesmo dia.

Base auditada para os fechamentos históricos anteriores ao Stage 10: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.

Fechamento do Stage 10.01 auditado contra `main@b4d84e9078b27349cc691ec2875574ff67246101`, com CI pós-merge `33187232908` / run #755 GREEN completo.

Fechamento do Stage 10.02 auditado contra `main@259eb2d4310f5c7c84dcf0db1a5c3c5f6522df1d`, com CI pós-merge `33190772894` / run #805 GREEN completo.

Fechamento do Stage 10.03 auditado contra `main@112d9266de9ece584f2f58adff03ffb6c8776ca6`, com CI pós-merge `33195224667` / run #858 GREEN completo.

Fechamento do Stage 01.01 auditado contra `main@5171ec7e099be545663b4a1ac989c36fc68835eb`, após consolidação do attachment canônico, dimension-change sync e query somente-leitura; o último gate funcional foi CI `33198679352` / run #908 GREEN completo.

Fechamento do Stage 10.04 auditado contra `main@8fdfff0c518fa40099b9459e279118cdbef1b2fc`, após integração do PR #71. O CI focal pós-merge `33201053431` / Compendium Discovery #38 e o CI completo `33201053442` / RPG Skill Tree #921 fecharam GREEN, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.

Fechamento do Stage 01.02 auditado contra `main@10403670fb10f7fdfb6ae9f00ca56405db8bb491`, após integração dos PRs #74 e #77. O CI pós-merge `33212979768` / RPG Skill Tree #999 fechou GREEN completo, incluindo Core tests, Compendium tests, validators, generated-data drift/diff sanity, NeoForge build, verificação do JAR e dedicated-server smoke.

A auditoria considera código, recursos, testes, validators e CI já integrados na `main`. Trabalho existente apenas em PR/branch aberta **não conta como concluído**.

## Resultado

**11 / 75 subplanos concluídos formalmente.**

- `00-foundation/✅-02-client-server-boundaries.md`
- `01-rpg-core/✅-01-player-state.md`
- `01-rpg-core/✅-02-progression-services.md`
- `03-skill-tree-perks/✅-05-respec.md`
- `04-classes-masteries-specializations/✅-06-class-subtrees.md`
- `06-integrations/✅-03-irons-spellbooks.md`
- `06-integrations/✅-05-goety-malum-eidolon.md`
- `10-compendio-natural/✅-01-proveniencia-licencas.md`
- `10-compendio-natural/✅-02-inventario-modpack.md`
- `10-compendio-natural/✅-03-modelo-dados-identidade.md`
- `10-compendio-natural/✅-04-descoberta-progresso.md`

Cada arquivo concluído segue o padrão documental do Volcanoes: checklist `[x]`, contrato efetivamente implementado, evidência de verificação e `Acceptance: satisfied`.

## Progresso por estágio

| Estágio | Concluídos | Total | Estado geral |
| --- | ---: | ---: | --- |
| 00 Foundation | 1 | 4 | EM ANDAMENTO |
| 01 RPG Core | 2 | 5 | EM ANDAMENTO |
| 02 Progression & World Scaling | 0 | 5 | EM ANDAMENTO |
| 03 Skill Tree & Perks | 1 | 6 | EM ANDAMENTO |
| 04 Classes, Masteries & Specializations | 1 | 6 | EM ANDAMENTO |
| 05 Combat & Magic Hooks | 0 | 6 | EM ANDAMENTO |
| 06 Integrations | 2 | 9 | EM ANDAMENTO |
| 07 Data, Network & UI | 0 | 6 | EM ANDAMENTO |
| 08 Quest & Progression Hooks | 0 | 6 | EM ANDAMENTO / implementação paralela |
| 09 Hardening & Release | 0 | 7 | EM ANDAMENTO contínuo |
| 10 Compêndio Natural | 4 | 15 | EM ANDAMENTO |
| **Total** | **11** | **75** | |

## Por que os demais continuam abertos

### 00 — Foundation

`01-environment-bootstrap` ainda inclui validação de configuração e convenções/IDs que não estão formalmente encerradas; o PR #25 existe justamente para corrigir/validar recursos e attribute IDs de 1.21.1. `03-optional-integrations` ainda não possui a matriz de ausência individual de cada provider exigida em `PENDING.md`. `04-diagnostics-testing` tem CI forte, mas logging/diagnóstico padronizado e reprodução local dos gates ainda não estão fechados.

### 01 — RPG Core

`✅-01-player-state.md` está fechado: `CANONICAL_PLAYER` é o único envelope persistente de escrita normal, attachments antigos existem somente como inputs de migração, os runtimes convergem pelo `CanonicalPlayerAttachmentRuntime` e `CanonicalPlayerSnapshot` fornece a projeção somente-leitura sem reintroduzir authorities legados.

`✅-02-progression-services.md` também está fechado: grants e rollback privilegiado de RPG XP possuem rotas canônicas separadas; level-up/recompensas associadas passam pelo Core; mastery usa replay keys persistentes; adapters não gravam storage de progressão diretamente; `commitMutation(...)` suprime no-ops e publica `ProgressionMutationEvent` somente após persistência confirmada. O rollback de XP é trusted-server/admin, não usa grant negativo nem packet de gameplay, e preserva Core Progression Points históricos por contrato.

Os subplanos `03-attributes-modifiers`, `04-persistence-sync` e `05-core-api-invariants` continuam abertos porque ainda exigem fechamento formal de recomputação/modificadores, matriz completa de migração/sync/corrupção e invariantes públicas de API/boundary.

### 02 — Progression & World Scaling

Relevant-player, território, entity level, rarity e stat scaling possuem fundações reais e testadas, mas políticas finais de raio/party, fórmulas, caps, balance e persistência continuam sendo fechadas incrementalmente. Performance/balance final também permanece aberto.

### 03 — Skill Tree & Perks

O respec está fechado. Os demais subplanos ainda têm gaps objetivos: validação atômica de reload/IDs, detecção explícita de cycles/orphans no validator de grafo, motivo legível de rejeição de compra, composição formal entre efeitos inline/packs/behavior handlers e geração automática da `wiki/`.

### 04 — Classes, Masteries & Specializations

As quatro subtrees dedicadas estão fechadas. Class resolution geral ainda precisa reconciliar reload; confluences ainda não mostram todos os requisitos faltantes na UI; masteries não possuem curva/cap final; provider identities têm requisitos e testes fortes, mas falta política final para saves/provider ausente; specializations ainda dependem dos contratos finais aplicáveis.

### 05 — Combat & Magic Hooks

O pipeline canônico final por hit/projétil/magia ainda não está formalmente fechado. O contrato conjunto Ars + Iron's para stats genéricas também permanece em `PENDING.md`.

### 06 — Integrations

Iron's e o bloco Goety/Malum/Eidolon estão fechados. Epic Fight ainda depende do pipeline canônico de combate; Ars precisa do contrato de coexistência com Iron's; Identity2 ainda requer a matriz lifecycle formal; Apothic ainda precisa de revalidação nominal; Create/AE2/Oritech continuam com trabalho pendente; a matriz de presença/ausência por mod permanece aberta.

### 07 — Data, Network & UI

Loaders, packets e UI já existem, mas o acceptance final ainda não está satisfeito: reload cross-catalog, versionamento formal de protocolo/schemas e resolução completa de efeitos/requisitos/erros na UI ainda possuem trabalho aberto. O dimension-change sync do RPG Core já foi integrado e não é mais blocker deste estágio.

### 08 — Quest & Progression Hooks

O core possui peças reutilizáveis de query/reward/idempotency, mas a API quest-facing completa, condições data-driven, adapters e authoring diagnostics ainda não foram formalmente fechados e integrados como estágio concluído.

### 09 — Hardening & Release

Nenhum gate final pode ser fechado enquanto existirem blockers de migração, compatibilidade, performance e release. A suíte atual é forte, mas ainda não substitui profiling/budgets, migrations, matriz completa de optional mods e release gate finais.

### 10 — Compêndio Natural

`✅-01-proveniencia-licencas.md`, `✅-02-inventario-modpack.md`, `✅-03-modelo-dados-identidade.md` e `✅-04-descoberta-progresso.md` estão fechados. O Stage 10 possui proveniência/licenças auditadas, inventário reproduzível de modlist + registries runtime, modelo canônico de identidade/fatos/relações, catálogo imutável com publicação atômica e agora uma base persistente e server-authoritative de descoberta com estados monotônicos, rewards idempotentes, preservação de tombstones, conclusão derivada e feeds genéricos NeoForge. A materialização do snapshot da instância completa do pack continua como tarefa operacional do gate de conteúdo; o runtime é a autoridade. O próximo passo causal é `05-fauna-entidades.md`.

## Evidência de regressão atual

O fechamento mais recente do Stage 01.02 foi auditado em `main@10403670fb10f7fdfb6ae9f00ca56405db8bb491`; CI pós-merge `33212979768` / RPG Skill Tree #999 passou Core tests, Compendium tests, validators RPG, generated-data drift/diff sanity, NeoForge build, verificação do JAR, dedicated-server smoke e publicação do status final.

## Convenção

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = implementado, validado, integrado e auditado contra o Acceptance do próprio arquivo.

Se uma regressão ou alteração arquitetural invalidar um Acceptance já fechado, o arquivo deve voltar ao nome sem `✅-`.
