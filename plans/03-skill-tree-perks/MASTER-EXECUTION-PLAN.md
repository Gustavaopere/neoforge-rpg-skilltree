# Skill Tree Catalog Rebuild Implementation Plan

> **For agentic workers:** Use the host's available task-by-task implementation workflow. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** reconstruir o catálogo completo de perks das 11 árvores a partir do pack realmente instalado, com paridade entre árvores, Transmutations curadas por Ability, especializações com Mecânicas Assinatura e implementação técnica posterior sem herdar decisões editoriais do catálogo `A####`.

**Architecture:** o trabalho é dividido em gates sequenciais. Nenhuma fase de conteúdo pode começar enquanto a fonte anterior não estiver fechada. O catálogo editorial usa Markdown individual por perk/Ability, IDs `I/P/T/S`, ownership único por árvore e seleção curada de Abilities. A implementação runtime só começa depois de o catálogo conceitual inteiro estar aprovado e tecnicamente auditado.

**Tech Stack:** Markdown versionado no GitHub, Python 3 para validators/catalog checks, Java 21 + NeoForge 1.21.1 para runtime, Gradle para testes, catálogos em `PROJECT-INSTRUCTIONS` e Black Arcana como fontes de inventário.

## Global Constraints

- As 11 árvores canônicas são `MARTIAL`, `AGILITY`, `VITALITY`, `HEALING`, `ARCANE`, `ENGINEERING`, `MINING`, `SURVIVAL`, `SUMMONING`, `OCCULT` e `LOGISTICS`.
- Todas as árvores devem terminar com o mesmo `N_standard_per_tree`, `N_transmutation_per_tree` e `N_specialization_per_tree`.
- A árvore interna de atributos permanece comum e igual para todos.
- O desenho visual/topológico vem somente depois de catálogo e runtime estarem estáveis.
- Transmutation usa seleção fixa e curada de Abilities; não é requisito cobrir toda skill existente nem aceitar uma Ability arbitrária escolhida pelo jogador.
- O sistema de Transmutation deve cobrir magia e classes/árvores não mágicas com a mesma quota estrutural.
- Cada Ability selecionada recebe exatamente uma raiz `Txxxx` em todo o catálogo.
- Cada raiz possui LEFT `.11-.19`, RIGHT `.21-.29` e METAMORPHOSIS `.31-.39`.
- LEFT: mínimo 2, máximo 9, escolher 1.
- RIGHT: mínimo 2, máximo 9, escolher 1.
- METAMORPHOSIS: mínimo 3, máximo 9, escolher 1.
- Uma configuração pode ter simultaneamente 1 LEFT + 1 RIGHT + 1 METAMORPHOSIS.
- Especialização é uma ramificação temática, não sinônimo de mod/provider.
- Especializações podem ser puras ou híbridas; toda especialização tem exatamente um owner tree para contagem.
- Toda especialização aprovada deve possuir uma Mecânica Assinatura própria e suas `Sxxxx` devem alimentar essa mecânica.
- Diablo IV e outros jogos são referência de padrões de design, nunca authority técnica nem obrigação de copiar números, efeitos ou classes.
- `PROJECT-INSTRUCTIONS/modlist` prevalece para presença/versionamento do pack.
- `PROJECT-INSTRUCTIONS/datapacks` deve ser cruzado para comportamento carregado.
- `Black-Arcana/wiki/modpack-catalog/providers` é fonte obrigatória para capacidades mágicas já catalogadas, mas não substitui presença física da modlist.
- Resource packs e shaders servem como contexto visual, não prova de gameplay/hook.
- Nenhuma perk pode ser fechada tecnicamente antes da auditoria do provider real.
- Nenhum filler é permitido apenas para atingir quota.

---

### Task 1: Fechar a authority física do pack

**Files:**
- Modify: `PROJECT-INSTRUCTIONS/modlist/**`
- Modify: `PROJECT-INSTRUCTIONS/guides/gameplay/CURRENT-MODLIST.md`
- Modify: `PROJECT-INSTRUCTIONS/guides/magic/CURRENT-MODLIST.md`
- Modify: `PROJECT-INSTRUCTIONS/guides/technology/CURRENT-MODLIST.md`
- Read: `PROJECT-INSTRUCTIONS/datapacks/**`
- Read: `PROJECT-INSTRUCTIONS/resource-packs/**`
- Read: `PROJECT-INSTRUCTIONS/shaders/**`

**Interfaces:**
- Consumes: inventário físico/documental atual do pack.
- Produces: uma única authority de presença/versionamento usada por todas as fases posteriores.

- [ ] **Step 1: Inventariar presença real e conflitos documentais**

Comparar a fonte física mais recente com os dossiês de `modlist`. Classificar cada entrada como presente, removida, substituída ou sem prova física atual. Não usar quantidade de arquivos `.md` como proxy de quantidade de JARs.

- [ ] **Step 2: Reconciliar os três CURRENT-MODLIST**

Todos devem apontar para a mesma authority vigente. Conteúdo histórico pode permanecer identificado como histórico, mas não pode ser apresentado como current.

- [ ] **Step 3: Classificar função de cada entrada**

Classificar ao menos: gameplay provider, addon, bridge/compat, API/library, UI/QoL, cosmetic, worldgen/content, datapack-affecting, visual-only.

- [ ] **Step 4: Registrar remoções de forma negativa**

Mods removidos precisam de evidência explícita suficiente para impedir reaparecimento em planos futuros. Catálogos auxiliares não podem ressuscitá-los.

- [ ] **Step 5: Gate de aprovação**

A fase passa somente se uma consulta por nome de mod responder de forma não ambígua: presente/ausente, versão/prova e papel no pack.

**Validation:**
- revisão manual cruzada dos três `CURRENT-MODLIST.md`;
- busca por providers sabidamente removidos deve retornar apenas contexto histórico, nunca status current.

**Expected outcome:** uma única fotografia operacional do pack.

---

### Task 2: Construir a Matriz de Capacidades

**Files:**
- Create: `plans/03-skill-tree-perks/CAPABILITY-MATRIX.md`
- Read: `PROJECT-INSTRUCTIONS/modlist/**`
- Read: `PROJECT-INSTRUCTIONS/datapacks/**`
- Read: `Gustavaopere/Black-Arcana/wiki/modpack-catalog/providers/**`
- Read: projetos próprios e vanilla pertinentes.

**Interfaces:**
- Consumes: authority fechada pela Task 1.
- Produces: inventário funcional de capacidades jogáveis, sem ainda criar perks.

- [ ] **Step 1: Enumerar providers que realmente fornecem gameplay relevante**

Excluir libraries, cosmetic-only, UI-only e bridges sem capacidade jogável própria da lista de providers candidatos a perks.

- [ ] **Step 2: Registrar capacidades por provider**

Cada linha deve declarar: provider, capacidade, tipo de Ability/sistema, progressão nativa, possíveis árvores, potencial para Standard Perks, potencial para Transmutation, potencial para especialização, dependências e observações de authority.

- [ ] **Step 3: Incluir vanilla e projetos próprios**

Vanilla não pode ser ignorado só porque não aparece na modlist. Projetos próprios entram com a mesma disciplina de authority.

- [ ] **Step 4: Deduplicar capacidades equivalentes**

Quando vários mods expuserem a mesma fantasia, manter providers distintos, mas registrar que a capacidade compete pelo mesmo nicho. Não criar uma perk por provider automaticamente.

- [ ] **Step 5: Gate de aprovação**

Cada provider relevante deve terminar em um dos estados: integrar diretamente, alimentar sistema universal, preservar progressão nativa sem perk própria, candidato a especialização, candidato a Transmutation, ou não integrar.

**Validation:** revisão provider → árvore e árvore → provider. Nenhum provider relevante pode ficar sem decisão.

**Expected outcome:** mapa completo do que o jogador realmente pode fazer no pack.

---

### Task 3: Definir identidade mecânica das 11 árvores

**Files:**
- Create: `plans/03-skill-tree-perks/TREE-IDENTITIES.md`
- Modify: `plans/03-skill-tree-perks/02-standard-perks/<tree>/README.md`
- Modify: `plans/03-skill-tree-perks/03-transmutation-perks/<tree>/README.md`

**Interfaces:**
- Consumes: `CAPABILITY-MATRIX.md`.
- Produces: escopo claro de cada árvore e ownership para capacidades.

- [ ] **Step 1: Definir promessa de gameplay de cada árvore**

Cada árvore deve ter uma descrição curta do que fortalece, do que deliberadamente não é seu escopo e de como se diferencia das árvores vizinhas.

- [ ] **Step 2: Atribuir owner principal às capacidades**

Capacidades híbridas podem ter integrações secundárias, mas recebem um owner único para quota/ID.

- [ ] **Step 3: Registrar sobreposições permitidas**

Ex.: mobilidade pode aparecer em AGILITY e como suporte em outras árvores; summons podem interagir com OCCULT, mas SUMMONING continua dono da capacidade quando aplicável.

- [ ] **Step 4: Gate de aprovação**

Nenhuma capacidade candidata a perk pode estar sem owner; nenhuma árvore pode depender de filler para ter identidade própria.

**Validation:** revisar duplicações de ownership e lacunas de cobertura.

**Expected outcome:** 11 domínios com fronteiras compreensíveis e ownership determinístico.

---

### Task 4: Congelar os orçamentos do catálogo

**Files:**
- Create: `plans/03-skill-tree-perks/CATALOG-BUDGETS.md`
- Modify: `plans/03-skill-tree-perks/CATALOG-CONTRACT.md`

**Interfaces:**
- Consumes: cobertura da Task 3.
- Produces: `N_standard_per_tree`, `N_transmutation_per_tree`, `N_specialization_per_tree` e política de distribuição interna.

- [ ] **Step 1: Calcular o menor orçamento sustentável entre as 11 árvores**

A quota é limitada pela árvore mais difícil de preencher com conteúdo de qualidade, não pela árvore com mais providers.

- [ ] **Step 2: Separar paridade obrigatória de simetria opcional**

Obrigatório: mesmo total de Standard, Transmutation e Specialization Perks por árvore.

Decisão de produto nesta fase: se `K_specializations_per_tree` e `M_perks_per_specialization` serão também exatamente iguais ou se apenas o produto total `N_specialization_per_tree` será fixo.

- [ ] **Step 3: Documentar ranges efetivamente usados**

Os blocos `1000–11999` continuam reservas. `CATALOG-BUDGETS.md` deve declarar o range ocupado por família/árvore depois de congelar as quotas.

- [ ] **Step 4: Gate de aprovação**

Não avançar para criação massiva de perks enquanto os três `N_*` não estiverem congelados.

**Validation:** tabela 11×3 com contagens idênticas em cada coluna.

**Expected outcome:** orçamento quantitativo final do catálogo editorial.

---

### Task 5: Selecionar as Abilities de Transmutation

**Files:**
- Create: `plans/03-skill-tree-perks/TRANSMUTATION-SELECTION.md`
- Create/Modify: `plans/03-skill-tree-perks/03-transmutation-perks/<tree>/README.md`

**Interfaces:**
- Consumes: capacidade/ownership + `N_transmutation_per_tree`.
- Produces: lista fechada de Abilities que receberão `Txxxx`.

- [ ] **Step 1: Definir e congelar a rubrica de seleção antes de ranquear**

A rubrica deve avaliar pelo menos: relevância para a árvore, espaço real para LEFT/RIGHT/METAMORPHOSIS, distinção de builds, redundância, progressão nativa, risco de integração, potencial visual e capacidade de produzir escolhas reais em vez de bônus genéricos.

Os pesos exatos da rubrica são decisão desta task e devem ser registrados antes de selecionar qualquer Ability.

- [ ] **Step 2: Ranquerar candidatas por árvore**

Todas as 11 árvores passam pela mesma rubrica. Magia não recebe vantagem quantitativa por possuir mais spells/providers.

- [ ] **Step 3: Fechar exatamente `N_transmutation_per_tree` candidatas por árvore**

Cada Ability aparece uma vez no catálogo. Se duas trees disputarem a mesma Ability, resolver owner antes de atribuir ID.

- [ ] **Step 4: Reservar IDs `Txxxx`**

Atribuir IDs apenas depois da seleção fechar. Não criar filhos `.xx` ainda.

- [ ] **Step 5: Gate de aprovação**

A lista completa de raízes deve ser revisável como conjunto antes de desenhar qualquer LEFT/RIGHT/METAMORPHOSIS.

**Validation:** 11 árvores × mesmo número de raízes; nenhuma Ability duplicada; todas com provider/authority conhecido.

**Expected outcome:** roster definitivo de Abilities transmutáveis.

---

### Task 6: Criar o catálogo de Standard Perks

**Files:**
- Create: `plans/03-skill-tree-perks/02-standard-perks/<tree>/Pxxxx-<slug>.md`
- Read: `plans/03-skill-tree-perks/DOSSIER-CONTRACT.md`

**Interfaces:**
- Consumes: identidade das árvores + `N_standard_per_tree`.
- Produces: exatamente `N_standard_per_tree` perks normais por árvore.

- [ ] **Step 1: Criar propostas por árvore em lote editorial**

Cobrir ofensiva, defesa, utilidade, recursos, sinergias e bridges apenas onde fizer sentido ao domínio.

- [ ] **Step 2: Aplicar ownership e antirrepetição**

Uma Standard Perk não deve ser uma segunda árvore específica para uma Ability que já possui Transmutation.

- [ ] **Step 3: Revisar valor de escolha**

Eliminar perks que são apenas versões cosméticas, duplicações ou números sem identidade quando houver alternativa de design melhor.

- [ ] **Step 4: Fechar exatamente a quota**

Nenhum filler. Se a quota não se sustentar, retornar à Task 4 e reduzir o orçamento globalmente.

- [ ] **Step 5: Gate de aprovação**

As 11 árvores precisam ter catálogo Standard completo antes da auditoria técnica individual.

**Validation:** contagem, IDs únicos, owner válido, dossiê conforme contrato.

**Expected outcome:** catálogo conceitual completo de Standard Perks.

---

### Task 7: Desenhar LEFT/RIGHT/METAMORPHOSIS para cada Txxxx

**Files:**
- Create: `plans/03-skill-tree-perks/03-transmutation-perks/<tree>/Txxxx-<ability>/Txxxx-<ability>.md`
- Create: `.../Txxxx.1x-<slug>.md`
- Create: `.../Txxxx.2x-<slug>.md`
- Create: `.../Txxxx.3x-<slug>.md`
- Read: `plans/03-skill-tree-perks/DOSSIER-CONTRACT.md`

**Interfaces:**
- Consumes: roster fechado da Task 5.
- Produces: personalização conceitual completa de cada Ability selecionada.

- [ ] **Step 1: Definir dois eixos menores distintos**

LEFT e RIGHT precisam responder a perguntas diferentes sobre a Ability. Não fixar universalmente “dano à esquerda / área à direita” quando não fizer sentido.

- [ ] **Step 2: Criar mínimo 2 LEFT e 2 RIGHT**

Cada opção precisa ser mutuamente exclusiva dentro do próprio grupo e justificar uma build diferente.

- [ ] **Step 3: Criar mínimo 3 METAMORPHOSIS**

Metamorphosis deve alterar identidade/comportamento de forma perceptível. Pode existir 4–9 quando houver ideias realmente distintas.

- [ ] **Step 4: Aplicar conversões de Affinity apenas quando conceitualmente completas**

A escolha de afinidade pode existir como uma metamorfose configurável, mas a lista concreta só será aprovada na auditoria técnica.

- [ ] **Step 5: Gate de aprovação**

Cada raiz precisa ter ao menos 2/2/3, nenhum grupo pode conter opções redundantes e a combinação `1 LEFT + 1 RIGHT + 1 METAMORPHOSIS` deve produzir configurações coerentes.

**Validation:** IDs dentro de `.11-.19/.21-.29/.31-.39`, limites 2–9/2–9/3–9, exclusividade intragrupo, uma raiz por Ability.

**Expected outcome:** catálogo conceitual completo de Transmutations.

---

### Task 8: Definir especializações puras e híbridas com Mecânicas Assinatura

**Files:**
- Create: `plans/03-skill-tree-perks/SPECIALIZATION-ROSTER.md`
- Create: `plans/03-skill-tree-perks/04-specialization-perks/<owner-tree>/<slug>/README.md`
- Create: `plans/03-skill-tree-perks/04-specialization-perks/<owner-tree>/<slug>/Sxxxx-<slug>.md`
- Read: `plans/03-skill-tree-perks/SPECIALIZATION-CONTRACT.md`
- Read: `plans/03-skill-tree-perks/DOSSIER-CONTRACT.md`

**Interfaces:**
- Consumes: matriz de capacidades + orçamento de especializações.
- Produces: roster de especializações e perks `Sxxxx`.

- [ ] **Step 1: Propor especializações por fantasia, não por mod**

Cada candidata deve sobreviver mesmo quando suas capacidades vêm de múltiplos providers.

- [ ] **Step 2: Classificar PURE ou HYBRID**

PURE: depende apenas da progressão principal do owner.

HYBRID: exige owner + uma ou mais secondary trees. Mesmo híbrida, conta apenas para o owner tree.

- [ ] **Step 3: Definir uma Mecânica Assinatura principal por especialização**

Exemplos de padrões permitidos: afinidade/patrono, summon roles/sacrifice, captura/selamento, juramentos/posturas/contratos, doutrinas de engenharia, outros sistemas sustentados pelo pack.

- [ ] **Step 4: Criar `Sxxxx` que alimentem a Mecânica Assinatura**

Evitar subárvore de passivos desconectados.

- [ ] **Step 5: Fechar o mesmo `N_specialization_per_tree`**

Se uma árvore não sustentar a quota, voltar à Task 4 em vez de criar filler.

- [ ] **Step 6: Gate de aprovação**

Toda especialização deve declarar owner, required trees, tipo PURE/HYBRID, fantasy, signature mechanic, providers candidatos e quota de `Sxxxx`.

**Validation:** contagem por owner, nenhum S duplicado, híbridos não contam duas vezes, todas possuem Mecânica Assinatura.

**Expected outcome:** catálogo conceitual completo de especializações.

---

### Task 9: Auditar tecnicamente cada perk aprovada

**Files:**
- Modify: todos os dossiês `P/T/S` para adicionar seção de Technical Audit.
- Read: source/API/docs/version do provider instalado.
- Proposed create: `plans/03-skill-tree-perks/TECHNICAL-AUDIT-STATUS.md`

**Interfaces:**
- Consumes: catálogo conceitual fechado.
- Produces: contrato implementável ou fail-closed para cada item.

- [ ] **Step 1: Verificar versão e identity reais**

Registrar mod id, registry id, API/event/boundary e authority server/client.

- [ ] **Step 2: Verificar persistência e causalidade**

Definir quem é fonte de verdade para “Ability conhecida”, seleção de grupo, affinity, summon role e Mecânica Assinatura.

- [ ] **Step 3: Verificar stacking/ownership**

Impedir dupla aplicação com provider nativo, equipamento, mastery ou outro bridge.

- [ ] **Step 4: Classificar cada dossiê**

Estados permitidos: `TECH-AUDITED`, `FAIL-CLOSED`, `REQUIRES-DESIGN-CHANGE`.

`REQUIRES-DESIGN-CHANGE` retorna o item para revisão conceitual; não autoriza implementar um bônus genérico substituto.

- [ ] **Step 5: Gate de aprovação**

Runtime só começa quando todo catálogo estiver TECH-AUDITED ou explicitamente FAIL-CLOSED com impacto de quota resolvido.

**Validation:** nenhum dossiê aprovado sem provider/hook/status documentado.

**Expected outcome:** catálogo tecnicamente executável.

---

### Task 10: Criar validator editorial antes da implementação runtime

**Files:**
- Proposed create: `scripts/validate-perk-catalog.py`
- Proposed create: `scripts/test_validate_perk_catalog.py`

**Interfaces:**
- Consumes: estrutura Markdown do Stage 03.
- Produces: CLI que falha quando contratos editoriais forem violados.

- [ ] **Step 1: Add the focused failing tests**

Casos obrigatórios:
- duplicate `P/T/S` ID;
- `Txxxx.xx` sem raiz;
- grupo fora dos ranges;
- menos de 2 LEFT;
- menos de 2 RIGHT;
- menos de 3 METAMORPHOSIS;
- mais de 9 em qualquer grupo;
- mesma Ability em duas raízes;
- quota desigual entre trees;
- especialização sem owner;
- híbrida contando em duas trees;
- `Sxxxx` sem specialization container.

- [ ] **Step 2: Verify the relevant failure**

Run: `python3 -m unittest scripts/test_validate_perk_catalog.py`

Expected: falha porque o validator ainda não existe/comportamentos ainda não estão implementados.

- [ ] **Step 3: Implement the minimum validator**

CLI proposta:

`python3 scripts/validate-perk-catalog.py plans/03-skill-tree-perks`

Exit 0 apenas quando todos os invariantes editoriais forem satisfeitos. Mensagens devem identificar arquivo e regra quebrada.

- [ ] **Step 4: Verify the focused pass**

Run: `python3 -m unittest scripts/test_validate_perk_catalog.py`

Expected: todos os testes passam.

- [ ] **Step 5: Run the affected integration check**

Run: `python3 scripts/validate-perk-catalog.py plans/03-skill-tree-perks`

Expected: exit 0 no catálogo aprovado.

- [ ] **Step 6: Commit the passing deliverable**

Commit independente do validator antes de iniciar bindings runtime.

**Expected outcome:** o contrato editorial deixa de depender somente de revisão humana.

---

### Task 11: Binding editorial → runtime e implementação

**Files:**
- Modify/Create: paths runtime definidos pela auditoria técnica.
- Modify: `src/main/resources/data/rpgskilltree/**`
- Modify/Create: adapters em `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/**`
- Modify/Create: tests em `src/test/java/dev/gustavopere/rpgskilltree/**`

**Interfaces:**
- Consumes: catálogo validado e TECH-AUDITED.
- Produces: runtime funcional para P/T/S, seleção 1+1+1, Mecânicas Assinatura e persistência.

- [ ] **Step 1: Definir runtime IDs estáveis sem reciclar IDs editoriais publicados**

Binding precisa preservar save/datapack compatibility.

- [ ] **Step 2: Implementar contract tests antes do runtime**

Cobrir compra, requisito de Ability conhecida, exclusividade intragrupo, troca de escolha, persistência, respec, provider ausente e multiplayer.

- [ ] **Step 3: Implementar adapters e handlers mínimos**

Providers opcionais devem permanecer fail-closed e sem hard classloading quando ausentes.

- [ ] **Step 4: Integrar Mecânicas Assinatura**

Cada sistema recebe persistência/authority própria definida na Task 9.

- [ ] **Step 5: Run focused and integration tests**

Run: `./gradlew test`

Expected: JUnit passa.

Run: `bash scripts/test-core.sh`

Expected: core regression suite passa.

Run: `python3 scripts/validate-data.py`

Expected: dados runtime válidos.

- [ ] **Step 6: Gate de aprovação**

Nenhuma topologia final/UX é considerada fechada antes de save/reload, multiplayer, respec e optional-provider behavior passarem.

**Expected outcome:** runtime estável e consistente com o catálogo editorial.

---

### Task 12: Topologia visual, UX e wiki

**Files:**
- Modify: tree layout/resources a definir pela implementação.
- Modify: wiki generation pipeline.
- Modify: `plans/03-skill-tree-perks/06-content-wiki-generation.md`.

**Interfaces:**
- Consumes: catálogo e runtime estáveis.
- Produces: árvore visual final, UI de escolhas, documentação gerada e drift gate.

- [ ] **Step 1: Desenhar layout somente sobre IDs já estáveis**

A posição visual não muda ownership nem identidade.

- [ ] **Step 2: Representar Transmutation como raiz + três grupos**

UI deve comunicar claramente que é 1 escolha por grupo e que os três grupos coexistem.

- [ ] **Step 3: Representar especializações puras e híbridas**

Híbridas podem aparecer entre árvores visualmente, mas continuam com owner único no catálogo.

- [ ] **Step 4: Gerar wiki e validar drift**

Run: `python3 scripts/generate-wiki-catalog.py --check`

Expected: nenhum drift entre runtime e documentação gerada.

- [ ] **Step 5: Gate final**

Catálogo, runtime, layout e wiki devem concordar em IDs, nomes, quotas, requisitos e escolhas.

**Expected outcome:** sistema publicável e documentado.

---

## State Machine do conteúdo

Cada item novo passa por estados explícitos:

`CANDIDATE -> CONCEPT-APPROVED -> TECH-AUDITED -> IMPLEMENTED -> VALIDATED`.

Estados alternativos:
- `REJECTED` — não entra no catálogo.
- `FAIL-CLOSED` — conceito aprovado, mas integração indisponível até existir boundary seguro.
- `REQUIRES-DESIGN-CHANGE` — auditoria provou que a ideia precisa voltar à fase conceitual.

Nenhum item pode saltar diretamente de CANDIDATE para IMPLEMENTED.

---

## Unresolved Product Decisions

Essas decisões são deliberadamente adiadas porque dependem da execução das fases anteriores:

1. Valores finais de `N_standard_per_tree`, `N_transmutation_per_tree` e `N_specialization_per_tree`.
2. Se `K_specializations_per_tree` e `M_perks_per_specialization` também serão exatamente iguais ou se somente o total `N_specialization_per_tree` será obrigatório.
3. Rubrica e pesos usados para ranquear Abilities candidatas à Transmutation.
4. Lista concreta de Abilities selecionadas.
5. Roster concreto de especializações PURE/HYBRID.
6. Mecânica Assinatura concreta de cada especialização.
7. Matriz final de Affinities/Schools convertíveis.
8. Runtime schema final de P/T/S e bindings namespaced.
9. Topologia visual e posição física das especializações híbridas.

Nenhuma dessas decisões deve ser inventada antecipadamente por uma fase downstream.
