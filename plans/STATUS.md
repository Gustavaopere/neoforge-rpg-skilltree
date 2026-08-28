# Status canônico dos planos

Última auditoria de fechamento: **2026-08-28**.

Planejamento do Stage 10 adicionado em **2026-08-28**. Os subplanos `10.01 — Proveniência, referências e licenças` e `10.02 — Inventário do modpack e cobertura de conteúdo` foram implementados, validados, integrados e auditados no mesmo dia.

Base auditada para os fechamentos históricos anteriores ao Stage 10: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.

Fechamento do Stage 10.01 auditado contra `main@b4d84e9078b27349cc691ec2875574ff67246101`, com CI pós-merge `33187232908` / run #755 GREEN completo.

Fechamento do Stage 10.02 auditado contra `main@259eb2d4310f5c7c84dcf0db1a5c3c5f6522df1d`, com CI pós-merge `33190772894` / run #805 GREEN completo.

A auditoria considera código, recursos, testes, validators e CI já integrados na `main`. Trabalho existente apenas em PR/branch aberta **não conta como concluído**.

## Resultado

**7 / 75 subplanos concluídos formalmente.**

- `00-foundation/✅-02-client-server-boundaries.md`
- `03-skill-tree-perks/✅-05-respec.md`
- `04-classes-masteries-specializations/✅-06-class-subtrees.md`
- `06-integrations/✅-03-irons-spellbooks.md`
- `06-integrations/✅-05-goety-malum-eidolon.md`
- `10-compendio-natural/✅-01-proveniencia-licencas.md`
- `10-compendio-natural/✅-02-inventario-modpack.md`

Cada arquivo concluído segue o padrão documental do Volcanoes: checklist `[x]`, contrato efetivamente implementado, evidência de verificação e `Acceptance: satisfied`.

## Progresso por estágio

| Estágio | Concluídos | Total | Estado geral |
| --- | ---: | ---: | --- |
| 00 Foundation | 1 | 4 | EM ANDAMENTO |
| 01 RPG Core | 0 | 5 | EM ANDAMENTO |
| 02 Progression & World Scaling | 0 | 5 | EM ANDAMENTO |
| 03 Skill Tree & Perks | 1 | 6 | EM ANDAMENTO |
| 04 Classes, Masteries & Specializations | 1 | 6 | EM ANDAMENTO |
| 05 Combat & Magic Hooks | 0 | 6 | EM ANDAMENTO |
| 06 Integrations | 2 | 9 | EM ANDAMENTO |
| 07 Data, Network & UI | 0 | 6 | EM ANDAMENTO |
| 08 Quest & Progression Hooks | 0 | 6 | EM ANDAMENTO / implementação paralela |
| 09 Hardening & Release | 0 | 7 | EM ANDAMENTO contínuo |
| 10 Compêndio Natural | 2 | 15 | EM ANDAMENTO |
| **Total** | **7** | **75** | |

## Por que os demais continuam abertos

### 00 — Foundation

`01-environment-bootstrap` ainda inclui validação de configuração e convenções/IDs que não estão formalmente encerradas; o PR #25 existe justamente para corrigir/validar recursos e attribute IDs de 1.21.1. `03-optional-integrations` ainda não possui a matriz de ausência individual de cada provider exigida em `PENDING.md`. `04-diagnostics-testing` tem CI forte, mas logging/diagnóstico padronizado e reprodução local dos gates ainda não estão fechados.

### 01 — RPG Core

A `main` ainda contém `ProgressionState`/`PlayerProgressionRuntime` e a fundação nova `CoreProgressionState`/Core runtime em paralelo. Por isso o requisito de uma fonte de verdade única, API única de mutation/query e sync/migração completos ainda não pode ser marcado. Dimension-change sync e a política final de migração também permanecem abertos.

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

Loaders, packets e UI já existem, mas o acceptance final ainda não está satisfeito: reload cross-catalog, versionamento formal de protocolo/schemas, dimension-change sync e resolução completa de efeitos/requisitos/erros na UI ainda possuem trabalho aberto.

### 08 — Quest & Progression Hooks

O core possui peças reutilizáveis de query/reward/idempotency, mas a API quest-facing completa, condições data-driven, adapters e authoring diagnostics ainda não foram formalmente fechados e integrados como estágio concluído.

### 09 — Hardening & Release

Nenhum gate final pode ser fechado enquanto existirem blockers de migração, compatibilidade, performance e release. A suíte atual é forte, mas ainda não substitui profiling/budgets, migrations, matriz completa de optional mods e release gate finais.

### 10 — Compêndio Natural

`✅-01-proveniencia-licencas.md` e `✅-02-inventario-modpack.md` estão fechados. O Stage 10 possui proveniência/licenças auditadas e agora também um pipeline reproduzível de modlist + registries runtime, cobertura explícita (`AUTO/CURATED/ADAPTER/IGNORED/ERROR`), drift/orphans e relatórios por namespace. A materialização do snapshot da instância completa do pack continua como tarefa operacional do gate de conteúdo; o runtime é a autoridade. O próximo passo causal é `03-modelo-dados-identidade.md`.

## Evidência de regressão atual

O CI pós-merge de `main@259eb2d4310f5c7c84dcf0db1a5c3c5f6522df1d` foi `33190772894` / run #805: Core tests, gates do Compêndio, validators, NeoForge build, verificação do JAR, upload do artefato, dedicated-server smoke e status final passaram.

## Convenção

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = implementado, validado, integrado e auditado contra o Acceptance do próprio arquivo.

Se uma regressão ou alteração arquitetural invalidar um Acceptance já fechado, o arquivo deve voltar ao nome sem `✅-`.
