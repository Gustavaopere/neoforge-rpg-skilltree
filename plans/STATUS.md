# Status canônico dos planos

Última auditoria de fechamento: **2026-08-27**.

Base auditada: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.

A auditoria foi feita contra código, recursos, testes, validators e CI já integrados na `main`. Trabalho existente apenas em PR/branch aberta **não conta como concluído**.

## Resultado

**5 / 60 subplanos concluídos formalmente.**

- `00-foundation/✅-02-client-server-boundaries.md`
- `03-skill-tree-perks/✅-05-respec.md`
- `04-classes-masteries-specializations/✅-06-class-subtrees.md`
- `06-integrations/✅-03-irons-spellbooks.md`
- `06-integrations/✅-05-goety-malum-eidolon.md`

Cada arquivo concluído foi convertido para o mesmo padrão documental do Volcanoes: checklist `[x]`, contrato efetivamente implementado, evidência de verificação e `Acceptance: satisfied`.

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
| **Total** | **5** | **60** | |

## Por que os demais continuam abertos

### 00 — Foundation

`01-environment-bootstrap` ainda inclui validação de configuração e convenções/IDs que não estão formalmente encerradas; o PR #25 existe justamente para corrigir/validar recursos e attribute IDs de 1.21.1. `03-optional-integrations` ainda não possui a matriz de ausência individual de cada provider exigida em `PENDING.md`. `04-diagnostics-testing` tem CI forte, mas logging/diagnóstico padronizado e reprodução local dos gates ainda não estão fechados.

### 01 — RPG Core

A `main` ainda contém `ProgressionState`/`PlayerProgressionRuntime` e a fundação nova `CoreProgressionState`/Core runtime em paralelo. Por isso o requisito de uma fonte de verdade única, API única de mutation/query e sync/migração completos ainda não pode ser marcado. Dimension-change sync e a política final de migração também permanecem abertos.

### 02 — Progression & World Scaling

Relevant-player, território, entity level, rarity e stat scaling possuem fundações reais e testadas, mas os próprios PRs mergeados declararam que políticas finais de raio/party, fórmulas, caps, balance e persistência não estavam congeladas. PRs #31 e #32 continuam implementando persistência/lifecycle para impedir reroll depois de unload/reload. Performance/balance final também permanece aberto.

### 03 — Skill Tree & Perks

O respec está fechado. Os demais subplanos ainda têm gaps objetivos: validação atômica de reload/IDs, detecção explícita de cycles/orphans no validator de grafo, motivo legível de rejeição de compra, composição formal entre efeitos inline/packs/behavior handlers e geração automática da `wiki/`. O PR #25 também mostra que a validação atual ainda pode deixar passar ResourceLocation/attribute ID incorreto.

### 04 — Classes, Masteries & Specializations

As quatro subtrees dedicadas estão fechadas. Class resolution geral ainda precisa reconciliar reload; confluences ainda não mostram todos os requisitos faltantes na UI; masteries não possuem curva/cap final; provider identities têm requisitos e testes fortes, mas falta uma política explícita para saves/provider ausente; specializations não verificam presença do provider no resolver atual.

### 05 — Combat & Magic Hooks

O pipeline canônico final por hit/projétil/magia ainda não está na `main`. `CombatAction` não representa todo o contexto de source/owner/target/fases de dano exigido pelo plano, e os PRs #7/#8/#12/#14/#15 continuam carregando trabalho de perks/serviços de combate ainda não integrado. O contrato conjunto Ars + Iron's para stats genéricas também permanece em `PENDING.md`.

### 06 — Integrations

Iron's e o bloco Goety/Malum/Eidolon estão fechados. Epic Fight ainda depende do pipeline canônico de combate; Ars precisa do contrato de coexistência com Iron's; Identity2 tem runtime/mixin funcional, mas a matriz lifecycle solicitada (death/relog/dimension) não está formalmente testada; Apothic ainda precisa de revalidação nominal; Create/AE2/Oritech continuam data-driven sem adapter runtime completo; a matriz de presença/ausência por mod ainda está pendente.

### 07 — Data, Network & UI

Loaders, packets e UI já existem, mas o acceptance final ainda não está satisfeito: reload cross-catalog não é publicado como snapshot atômico único, protocolo/schemas não estão formalmente versionados, dimension-change sync não está fechado, e a UI ainda usa mensagens genéricas/hardcoded em alguns tooltips em vez de efeito/requisito/erro resolvido completo.

### 08 — Quest & Progression Hooks

Ainda não conta como concluído porque o PR #28 permanece draft e fora da `main`. O core possui peças reutilizáveis de query/reward/idempotency, mas a API quest-facing completa, condições data-driven, adapters e authoring diagnostics ainda não foram integrados.

### 09 — Hardening & Release

Nenhum gate final pode ser fechado enquanto existirem blockers de migração, compatibilidade, performance e release. A suíte atual é forte, mas ainda não cobre toda a matriz cliente/multiplayer/optional-mods e não substitui profiling/budgets, migrations e release gate finais.

## Evidência de regressão atual

O último CI completo da `main` materializada antes desta auditoria foi `33132979048` / run #620: Core tests, regeneração/validators, NeoForge build, verificação do JAR e dedicated-server smoke passaram.

## Convenção

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = implementado, validado, integrado e auditado contra o Acceptance do próprio arquivo.

Se uma regressão ou alteração arquitetural invalidar um Acceptance já fechado, o arquivo deve voltar ao nome sem `✅-`.