# Own Projects Perk Guide Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** documentar de forma canônica e rastreável os sistemas de RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana para que o Chat 1 possa integrá-los corretamente em perks sem inventar hooks nem confundir planejamento com runtime disponível.

**Architecture:** `plans/03-skill-tree-perks/guides/projects/` será a fonte única dos quatro projetos próprios e da matriz cruzada. Gameplay, Magic e Technology receberão apenas um apêndice final por eixo, e o Notion espelhará a mesma arquitetura editorial. O protocolo do Chat 1 passará a exigir a leitura integral da coleção de projetos próprios.

**Tech Stack:** Markdown, Notion, GitHub, NeoForge 1.21.1 / Java 21 como contexto técnico dos quatro projetos.

**Spec:** `docs/superpowers/specs/2026-08-30-own-projects-perk-guide-design.md`

## Global Constraints

- Usar `plans/` e estado real de `main` como fontes primárias; README raiz não é suficiente.
- Distinguir sempre `IMPLEMENTADO E CANÔNICO`, `IMPLEMENTADO PARCIALMENTE`, `PREPARATÓRIO / NÃO CANÔNICO`, `PLANEJADO`, `BLOQUEADO / FAIL-CLOSED` e `NÃO APLICÁVEL`.
- Não apresentar plano/protótipo como hook disponível.
- Não inventar integração entre os quatro projetos.
- Notion é a fonte editorial canônica; GitHub é o snapshot operacional versionado.
- Escritas no Notion exigem re-fetch de persistência.
- Alterações no GitHub devem terminar em PR, CI aplicável verde, merge e confirmação da `main`.

---

### Task 1: Auditoria factual dos quatro projetos

**Files:**
- Read: `plans/STATUS.md` e `plans/**` de `Gustavaopere/neoforge-rpg-skilltree`
- Read: `plans/STATUS.md` e `plans/**` de `Gustavaopere/Volcanoes`
- Read: `plans/STATUS.md` e `plans/**` de `Gustavaopere/Enshrouded`
- Read: `plans/STATUS.md` e `plans/**` de `Gustavaopere/Black-Arcana`

**Interfaces:**
- Consumes: estado atual de `main` dos quatro repositórios.
- Produces: inventário factual por subsistema, status, authority, boundaries, hooks confirmados, integrações e lacunas.

- [ ] Registrar SHA atual da `main` de cada repositório.
- [ ] Ler `STATUS.md` e inventariar todas as pastas de `plans/` relevantes.
- [ ] Abrir os planos de cada subsistema; não inferir conteúdo pelo nome da pasta.
- [ ] Onde um plano não prove disponibilidade de hook, verificar contrato/código correspondente.
- [ ] Classificar cada capacidade com um dos seis estados permitidos pela spec.
- [ ] Separar fatos confirmados, inferências de integração e propostas futuras.
- [ ] Conferir relações cruzadas RPG ↔ Black Arcana, RPG ↔ Enshrouded, RPG ↔ Volcanoes, Black Arcana ↔ Enshrouded e Volcanoes ↔ Enshrouded.

### Task 2: Criar a fonte canônica no Notion

**Files/Pages:**
- Create: `Guia Completo — Projetos Próprios do Modpack`
- Create child/reference pages: RPG Skill Tree, Volcanoes, Enshrouded, Black Arcana, Matriz de Integração Cruzada
- Modify: os três guias canônicos atuais no Notion, acrescentando seção final `Projetos próprios do modpack`

**Interfaces:**
- Consumes: inventário factual da Task 1.
- Produces: fonte editorial canônica e links dos três eixos para os quatro dossiês.

- [ ] Criar página canônica e quatro dossiês + matriz, preservando hierarquia se o conector permitir; caso contrário, usar links/IDs explícitos.
- [ ] Para cada projeto, escrever identidade, subsistemas, authority, status, boundaries/APIs, recursos úteis a perks, ownership, fallback/fail-closed, proibições, anti-abuse, testes/evidência, fontes e lacunas.
- [ ] Na matriz cruzada, registrar produtor, consumidor, recurso/evento, direção, status, boundary, fallback, fail-closed, impacto em perks e risco de dupla autoridade.
- [ ] Acrescentar ao final de Gameplay o recorte de sistemas próprios pertinente a gameplay.
- [ ] Acrescentar ao final de Magic o recorte de sistemas próprios pertinente a magia.
- [ ] Acrescentar ao final de Technology o recorte de sistemas próprios pertinente a tecnologia.
- [ ] Re-fetch de todas as páginas alteradas/criadas e comparar títulos, status, links e conteúdo crítico.

### Task 3: Criar `guides/projects/` no GitHub

**Files:**
- Create: `plans/03-skill-tree-perks/guides/projects/README.md`
- Create: `plans/03-skill-tree-perks/guides/projects/01-rpg-skill-tree.md`
- Create: `plans/03-skill-tree-perks/guides/projects/02-volcanoes.md`
- Create: `plans/03-skill-tree-perks/guides/projects/03-enshrouded.md`
- Create: `plans/03-skill-tree-perks/guides/projects/04-black-arcana.md`
- Create: `plans/03-skill-tree-perks/guides/projects/05-cross-project-integration-matrix.md`
- Modify: `plans/03-skill-tree-perks/guides/README.md`

**Interfaces:**
- Consumes: Notion final e inventário factual.
- Produces: snapshot versionado da fonte canônica dos projetos próprios.

- [ ] Transcrever o conteúdo editorial persistido no Notion para Markdown sem mudar estado ou autoridade.
- [ ] Incluir metadados de origem e SHAs auditados dos quatro projetos.
- [ ] Garantir links relativos válidos entre README, quatro dossiês e matriz.
- [ ] Atualizar README raiz dos guias para tornar `projects/` leitura obrigatória do Chat 1.

### Task 4: Adicionar os apêndices finais aos três guias

**Files:**
- Create: próximo número disponível em `plans/03-skill-tree-perks/guides/gameplay/*-projetos-proprios.md`
- Create: próximo número disponível em `plans/03-skill-tree-perks/guides/magic/*-projetos-proprios.md`
- Create: próximo número disponível em `plans/03-skill-tree-perks/guides/technology/*-projetos-proprios.md`
- Modify: `README.md` de cada um dos três diretórios.

**Interfaces:**
- Consumes: `guides/projects/`.
- Produces: mapas temáticos para auditoria de perks por eixo.

- [ ] Calcular a numeração a partir do diretório real e usar o próximo número sem renumerar arquivos existentes.
- [ ] Gameplay: mapear progressão/RPG, world scaling, itemização, corpos, Shroud/Flame, hazards ambientais, combate e risco arcano relevantes.
- [ ] Magic: mapear Black Arcana, bridges mágicas do RPG, Flame/Lich/necromancia do Enshrouded e somente os boundaries ambientais legítimos de Volcanoes.
- [ ] Technology: mapear Volcanoes/Create/Sable/Aeronautics/pressão/geotermia, tecnologia/itemização/corpos do RPG e marcar ausência de integração tecnológica nativa para Black Arcana/Enshrouded quando não houver contrato.
- [ ] Em todos: proibir inferência de hook por nome, namespace ou plano futuro.

### Task 5: Atualizar o protocolo do Chat 1 e snapshots consolidados

**Files:**
- Modify/create documentação operacional do Chat 1 correspondente aos anexos do projeto.
- Produce local attachment snapshots: `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`, `GUIA-COMPLETO-MODS-DE-MAGIA.md`, `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`, `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.

**Interfaces:**
- Consumes: quatro coleções finais de guias.
- Produces: protocolo que exige a nova fonte e arquivos consolidados anexáveis à descrição do projeto.

- [ ] Exigir leitura integral de `guides/projects/` e da matriz antes do fechamento de perks.
- [ ] Registrar classificação obrigatória da relação de cada perk com os quatro projetos.
- [ ] Regenerar os três guias consolidados incluindo os novos apêndices.
- [ ] Gerar um quarto consolidado para `projects/`.
- [ ] Validar que cada consolidado contém todos os arquivos esperados e termina no novo apêndice/matriz.

### Task 6: Validação documental e fechamento GitHub

**Files:**
- Review: todos os arquivos adicionados/modificados nesta branch.

**Interfaces:**
- Consumes: Tasks 1–5.
- Produces: PR verde e `main` confirmada.

- [ ] Buscar termos de estado contraditórios e corrigir qualquer plano tratado como runtime disponível.
- [ ] Verificar ausência de integração inventada e de dupla autoridade.
- [ ] Verificar links relativos e referências de fontes.
- [ ] Comparar branch contra a `main` atual e sincronizar se necessário sem perder trabalho concorrente.
- [ ] Abrir PR com resumo de arquitetura, fontes auditadas e regra de status.
- [ ] Revisar comentários/CI e corrigir findings reais.
- [ ] Exigir CI aplicável verde no head final.
- [ ] Fazer merge na `main`.
- [ ] Confirmar SHA e estado da `main` pós-merge.
