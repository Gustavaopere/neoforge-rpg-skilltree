# Skill Tree Plan — Data Schema and Loaders

**Goal:** validar completamente os arquivos de árvore antes de publicar um snapshot utilizável.

- [x] Validar ID, tree, custo, max rank, posição, requisitos e payload de efeito.
- [x] Rejeitar IDs duplicados e referências inexistentes.
- [x] Validar ranges e tipos de operações de atributo.
- [x] Separar parse, validação e publicação do snapshot.
- [x] Erros apontam arquivo/resource, ID e campo problemático.

**Acceptance:** datapack inválido falha de forma explícita sem publicar estado parcial.

## Implementação integrada nesta etapa

- `SkillTreeDataLoader` constrói um candidato completo a partir de `node_rules`, `node_effects` e posições server-side em `skills`, preservando também as regras fechadas A0001–A0080 de `CombatPerkTreeModel`.
- IDs de árvore/nó/efeito/atributo que são identidades de resource são validados como namespaced IDs explícitos.
- `maxRank`, custo, nível mínimo, final triad, ranks requeridos, specialization grants, vizinhos e referências de nós são validados antes de qualquer publicação.
- Efeitos rejeitam referências de nó inexistentes, IDs duplicados, operação desconhecida/`OVERRIDE`, quantidade não finita e quantidade zero.
- Nós da árvore canônica `rpgskilltree:main` exigem posição server-side finita; entradas de layout duplicadas ou apontando para nós inexistentes são rejeitadas.
- `SkillTreeDataValidationException` registra `resourceId`, `entryId` e `field`, mantendo o diagnóstico do ponto exato da falha.
- `SkillTreeDataSnapshot` é a leitura imutável e revisionada de regras, requisitos, grafo, efeitos e posições.
- `SkillTreeDataCatalog.publish` somente troca o estado depois que o candidato completo foi preparado e validado; candidato inválido não incrementa revisão nem altera o último snapshot bom.
- `SkillTreeDataReloader` substitui, no bootstrap ativo, os antigos reloaders independentes de node rules/effects. O listener prepara o candidato inteiro no lifecycle de reload NeoForge e só então aplica uma publicação.
- `TreeRuleCatalog` e `NodeEffectCatalog` continuam como projeções de compatibilidade do runtime existente, mas recebem estado já validado pelo boundary canônico. Os antigos `NodeRulesReloader`/`NodeEffectsReloader` permanecem apenas como código legado não registrado.
- Após publicação válida, efeitos dos jogadores online são recompostos a partir do snapshot novo; falha de preparação ocorre antes dessa fase.

## Evidência TDD e regressão

- RED: commit `826efa3a82ed9e9ed72c8a0250e09a105bca6a0a`, RPG Skill Tree CI #1649 / run `33273800835`, falhando em `compileTestJava` exatamente pela ausência de `SkillTreeDataCatalog`, `SkillTreeDataLoader`, `SkillTreeDataSnapshot` e `SkillTreeDataValidationException`, enquanto o Core anterior permaneceu GREEN.
- Durante o GREEN, o bootstrap contract detectou e bloqueou a retirada não documentada dos listeners antigos; o contrato foi atualizado para exigir `SkillTreeDataReloader` e proibir seus dois registros não-atômicos.
- O primeiro JUnit comportamental detectou precedência incorreta na fixture de duplicate source; a fixture foi corrigida sem enfraquecer a ordenação determinística de resources no runtime.
- GREEN funcional: head `4a10cc875dc95b04474b85e00b4645fd3340be04`, RPG Skill Tree CI #1676 / run `33275095434` GREEN completo: Core, 28 JUnit, NeoForge GameTests, data/client-tree/node-effects/passive-export/runtime/attribute/canonical-binding/foundation validators, drift sanity, NeoForge build, verificação do JAR e dedicated-server smoke.
- No mesmo head, Foundation Bootstrap, Foundation Diagnostics, Foundation Optional Integrations e todas as matrizes Compendium executadas em paralelo também fecharam GREEN.

O conteúdo/balance das perks não foi redesenhado nesta etapa; o trabalho foi exclusivamente de schema, validação, lifecycle e atomicidade de publicação.