# Skill Tree Plan — Content Completion and Wiki Generation

**Goal:** fechar conteúdo player-facing e impedir drift entre jogo e `wiki/`.

- [ ] Revisar nós que ainda são apenas estruturais e decidir se permanecem assim.
- [ ] Completar nomes/descrições localizadas das perks que entram no escopo da release.
- [ ] Balancear final triads, bridges e keystones.
- [ ] Gerar catálogo de IDs, custos, ranks, requisitos e stats a partir de dados/localização.
- [x] Preservar texto editorial extra da wiki sem sobrescrever seções manuais como trivia.
- [ ] Adicionar CI para detectar catálogo desatualizado.

## Estado da infraestrutura — PR #207

A infraestrutura de geração foi separada da revisão de design para não transformar dados técnicos em uma segunda fonte de verdade enquanto o Catálogo Mestre do Notion continua sendo auditado.

Implementado nesta fatia:

- `scripts/wiki_catalog.py` deriva linhas factuais exclusivamente de `node_rules`, `node_effects` e localização existente;
- a convenção de localização é a mesma usada pela tela real: `node.<namespace>.<path com / convertido em .>.name|description`;
- ausência de localização usa o ID técnico como fallback de nome e `—` como descrição; nenhum texto de gameplay é inventado;
- requisitos exibíveis são somente os campos autoritativos aceitos pelo loader: nível, classes, mastery, especializações, class choices, required nodes/ranks e discoveries;
- efeitos de atributo preservam alvo, operação e quantidade por rank; handlers comportamentais são identificados explicitamente como `BEHAVIOR_HANDLER`;
- `replace_generated_block(...)` exige exatamente um par de marcadores e altera somente o trecho delimitado;
- `wiki/PERK_CATALOG.md` e `wiki/EFFECT_CATALOG.md` já possuem os marcadores canônicos; texto fora deles permanece editorial/manual;
- `scripts/generate-wiki-catalog.py` fornece o comando de geração e o modo `--check`;
- `scripts/test_wiki_catalog.py` roda no CI e cobre preservação editorial, fail-closed de marcadores, derivação factual, fallback sem invenção, idempotência e detecção de drift.

Evidência TDD desta fatia:

- CI #1876 / `33288786529`: RED esperado — `wiki_catalog` inexistente;
- CI #1880 / `33288856344`: contrato inicial do gerador GREEN;
- CI #1882 / `33288913145`: RED esperado — updater/drift boundary inexistente;
- CI #1887 / `33289002560`: testes de geração + updater/drift GREEN no head correspondente;
- PR #207 mergeado em `main` como `86be26020c03854a6224b5b673fd02d4d683c758`, validado pelo CI #1895 / `33289157469` totalmente GREEN.

## Auditoria factual da Árvore Principal — PR #209

A segunda fatia adiciona uma auditoria reproduzível de cobertura sem inferir design. `scripts/audit-wiki-coverage.py` mede a Árvore Principal diretamente após a regeneração dos dados e roda no workflow principal.

O CI #1905 / `33289654389` mediu o snapshot validado:

- **512** nós na Árvore Principal;
- **0 / 512** com chave de nome localizada em `pt_br`;
- **0 / 512** com chave de descrição localizada em `pt_br`;
- **66 / 512** com pelo menos um efeito declarativo em `node_effects`;
- **446 / 512** sem efeito declarativo.

A ausência de localização foi conferida contra a implementação real de `RpgSkillTreeScreen`: a tela procura `node.<namespace>.<path>.name` e `.description`; quando a chave não existe, o nome cai para o ID técnico e a descrição fica vazia. O `pt_br.json` contém localizações para subárvores já implementadas, como Tecnomago, Bruxo, Druida e Metamorfo, mas não para os IDs da Árvore Principal (`martial_000`, `arcane_000`, etc.).

**Importante:** `sem efeito declarativo` não equivale a `nó estrutural`. Há perks cujo comportamento é implementado por políticas/hooks Java e integrações provider-native. Portanto os 446 nós são apenas **candidatos de revisão factual**. A decisão “estrutural ou gameplay” continua dependendo da especificação canônica e da auditoria de runtime/Notion.

Evidência TDD desta fatia:

- CI #1901 / `33289517411`: RED esperado — `build_content_coverage` inexistente;
- CI #1902 / `33289583854`: contrato de cobertura GREEN;
- CI #1905 / `33289654389`: auditoria real + Core + JUnit + 16 GameTests + validators + build + JAR + dedicated-server smoke totalmente GREEN no head correspondente.

## Bloqueio de conteúdo final

O PR #203 (`audit/perk-criteria-a0001-a0020`) continua aberto e está reaplicando critérios obrigatórios e design canônico do Notion às perks A0001–A0020. O Catálogo Mestre também continua sendo editado durante essa auditoria.

Por isso estas fatias **não**:

- decidem quais nós estruturais permanecem estruturais;
- criam nomes ou descrições finais por inferência;
- alteram balanceamento de triads, bridges ou keystones;
- materializam o catálogo factual final dentro dos blocos gerados;
- ativam ainda o `--check` contra os arquivos reais da wiki no pipeline de regeneração.

Esses itens devem consumir um snapshot de design auditado, em vez de competir com o trabalho canônico em andamento. O Stage 03.06 permanece aberto até todos os checkboxes e o Acceptance final serem satisfeitos.

**Acceptance:** jogador consegue descobrir o que cada perk final faz e a parte factual da wiki pode ser regenerada a partir do jogo.
