# Skill Tree Plan — Content Completion and Wiki Generation

**Goal:** fechar conteúdo player-facing e impedir drift entre jogo e `wiki/`.

- [ ] Revisar nós que ainda são apenas estruturais e decidir se permanecem assim.
- [ ] Completar nomes/descrições localizadas das perks que entram no escopo da release.
- [ ] Balancear final triads, bridges e keystones.
- [ ] Gerar catálogo de IDs, custos, ranks, requisitos e stats a partir de dados/localização.
- [x] Preservar texto editorial extra da wiki sem sobrescrever seções manuais como trivia.
- [x] Adicionar CI para detectar catálogo desatualizado.

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

## Gate de drift dos catálogos — PR #222

O PR #222 concluiu a parte operacional que faltava para impedir divergência silenciosa entre dados/runtime e os blocos factuais da wiki.

Implementado nesta fatia:

- `alpha2-build.yml` executa `python3 scripts/generate-wiki-catalog.py --check` após os testes do gerador;
- `scripts/test_wiki_catalog.py` contém contrato explícito que impede a remoção acidental desse gate do workflow principal;
- `wiki/PERK_CATALOG.md` e `wiki/EFFECT_CATALOG.md` foram regenerados deterministicamente a partir das fontes factuais autoritativas atuais;
- as alterações geradas permanecem confinadas aos blocos `rpgskilltree:generated:*`; o texto editorial/manual fora dos marcadores foi preservado;
- o workflow final manteve `contents: read`; nenhuma permissão temporária usada durante a regeneração faz parte da `main`.

Evidência TDD desta fatia:

- CI `33302169094`: RED esperado — Core e testes do gerador GREEN, com falha apenas no novo `--check` porque `PERK_CATALOG.md` e `EFFECT_CATALOG.md` estavam desatualizados;
- CI #2018 / `33303114590`: GREEN completo no HEAD final — Core, testes do gerador, drift gate, auditoria de conteúdo, JUnit, NeoForge GameTests, validators, build, verificação do JAR e dedicated-server smoke;
- PR #222 mergeado em `main` como `6c36c5f7cec457984eab0b03a35cd0b5e621e334`.

Esse fechamento é exclusivamente factual/infrastrutural. Ele não autoriza preencher A0021+ por inferência nem transforma runtime Java em fonte editorial para descrições player-facing.

## Visibilidade client-side das perks semânticas — PR #212

A auditoria factual revelou que a malha visual histórica de 512 nós e as perks canônicas A0001–A0100 são contratos diferentes. `CombatPerkNodeBinding` usa IDs persistentes `rpgskilltree:combat/a####` e documenta explicitamente que os antigos IDs `martial_###` não são aliases. O servidor já injeta A0001–A0100 em `rpgskilltree:runtime/combat_perks` por meio de `SkillTreeDataLoader.closedCombatRules()`, mas esses 100 nós não possuíam layout carregável pelo cliente.

O PR #203 concluiu e foi mergeado em `main` como `5878b56eb5890931a3b316b545771baca014460a` durante esta fatia. Posteriormente, o PR #213 (Stage 10.10 editorial corpus) avançou a `main` para `00c072120503e9a7f26cb6fd317f41b9f8db3bcc`; por isso o merge final do #212 exige um novo merge-ref validado contra essa base mais recente.

Implementado nesta fatia:

- `CombatPerkVisualLayout` projeta os 100 nós canônicos sem criar uma segunda fonte de regras de gameplay;
- a topologia visual é derivada exclusivamente dos vizinhos e dependências já presentes em `CombatPerkTreeModel`;
- dependências externas como `martial_000`, `arcane_000`, `occult_000`, `agility_000` e `vitality_000` continuam sendo requisitos reais, mas não são convertidas em aliases nem adicionadas como falsas perks A####;
- como o Catálogo Mestre do Notion não define coordenadas, X/Y são apresentação determinística derivada: componentes conectados, profundidade por `requiredNodeRanks` internos, espaçamento estável e detecção fail-closed de ciclo;
- `ClientTreeLayout.combatPerks()` espelha exatamente os 100 IDs, `maxRank`, custo, starting point, nível mínimo, mastery e `requiredNodeRanks` de `CombatPerkTreeModel`;
- `ClientTreeLayout.availableFor(...)` expõe `rpgskilltree:runtime/combat_perks` sem exigir desbloqueio de uma classe paga não relacionada;
- `CombatPerkClientText` resolve os nomes A#### diretamente de `NotionCombatPerkCatalog`, sem duplicar os 100 nomes em outro catálogo/localização;
- `RpgSkillTreeScreen` usa essa fonte canônica para nomes A#### e mostra a aba como `Perks de Combate`;
- descrições A#### continuam ausentes quando não existe texto player-facing canônico versionado. Nenhuma descrição ou efeito foi sintetizado a partir de políticas Java;
- o zoom inicial da aba semântica usa visão geral apropriada para uma árvore grande;
- após review P2, `CombatPerkVisualLayout.Node` canonicaliza zero assinado (`-0.0` → `+0.0`) e os dois testes de unicidade usam pares numéricos normalizados, impedindo que coordenadas visualmente idênticas escapem da detecção de overlap por diferença textual.

Evidência TDD desta fatia:

- CI #1919 / `33290671268`: RED esperado — `CombatPerkVisualLayout` inexistente;
- CI #1921 / `33290725782`: projeção visual canônica GREEN em Core/JUnit e GameTests observados antes do run ser sucedido;
- CI #1926 / `33290844884`: RED esperado — três erros, todos pela ausência de `ClientTreeLayout.combatPerks()`;
- CI #1930 / `33290934102`: espelhamento servidor→cliente GREEN em Core/JUnit no head correspondente;
- CI #1933 / `33291027327`: RED esperado — seis erros, todos pela ausência de `CombatPerkClientText`;
- CI #1936 / `33291182166`: projeção + árvore client-side + nomes canônicos + Core + JUnit + 16 GameTests + validators + build + JAR + dedicated-server smoke totalmente GREEN no merge-ref contra `main@5878b56eb5890931a3b316b545771baca014460a`;
- CI #1938 / `33291335826`: head documental da fatia totalmente GREEN;
- CI #1941 / `33291649067`: RED de regressão do review — 76 testes executados, exatamente 1 falha em `visualNodeCanonicalizesSignedZeroCoordinates`;
- CI #1946 / `33291770043`: correção do review totalmente GREEN — Core, JUnit, 16 GameTests, validators, build, JAR e dedicated-server smoke. Esse run ainda foi criado sobre `main@5878b56eb5890931a3b316b545771baca014460a`, antes da entrada do #213, portanto não é usado sozinho como gate final de merge.

Esta fatia resolve o blocker de **invisibilidade client-side** de A0001–A0100. Ela não declara encerrado o conteúdo dessas perks: nomes estão disponíveis pelo catálogo versionado, mas descrições/efeitos player-facing ainda precisam vir do snapshot canônico auditado, e A0021+ ainda seguem o processo de auditoria/implementação próprio.

## Bloqueio de conteúdo final

O PR #203 deixou de ser um blocker de concorrência: A0001–A0020 já foram reauditoradas contra os critérios obrigatórios e mergeadas na `main`. O gate de drift e a materialização factual dos catálogos também já estão ativos na `main` pelo PR #222. Entretanto, o Catálogo Mestre e os lotes posteriores ainda precisam convergir para conteúdo player-facing final antes de fechar o Stage 03.06.

Por isso o trabalho remanescente **não pode**:

- decidir quais nós estruturais da malha histórica permanecem estruturais sem a especificação canônica;
- criar descrições finais ou textos de efeito por inferência;
- alterar balanceamento de triads, bridges ou keystones sem o design auditado;
- tratar o catálogo factual gerado como substituto do texto player-facing canônico.

Os blocos factuais da wiki e o `--check` já estão materializados. Os itens restantes devem consumir snapshots de design auditados, em vez de transformar o runtime atual em uma fonte editorial improvisada. O Stage 03.06 permanece aberto até todos os checkboxes e o Acceptance final serem satisfeitos.

**Acceptance:** jogador consegue descobrir o que cada perk final faz e a parte factual da wiki pode ser regenerada a partir do jogo.
