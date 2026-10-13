# Stage 03 — Plano Mestre de Reconstrução Implementation Plan

> **For agentic workers:** Use the host's available task-by-task implementation workflow. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** produzir todas as perks e especializações das 11 árvores com modlist/versões auditadas, custos e requisitos corretos, e um sistema de vagas T associado a perfis específicos de todas as Abilities elegíveis.

**Architecture:** quatro etapas editoriais 0→3. A auditoria de providers da **versão instalada** começa durante a autoria das P/S; a auditoria individual por Ability ocorre por último na etapa T. P modificam sistemas gerais; S aplicam regras a categorias de ações usando facts normalizadas com origem verificável; T usa vagas fixas na árvore e perfis de transformação separados por Ability. Os gates para implementação, topologia e wiki são posteriores.

**Tech Stack:** GitHub Markdown; Java 21/NeoForge 1.21.1; modlist, datapacks, Black Arcana; Python 3 validator; Gradle JUnit, dedicated-server GameTests posteriores.

## Global Constraints

- 11 owners: MARTIAL, AGILITY, VITALITY, HEALING, ARCANE, ENGINEERING, MINING, SURVIVAL, SUMMONING, OCCULT, LOGISTICS.
- Mesmo número **final** de P/S por árvore e de **vagas T** por árvore; o total de perfis Ability pode diferir por provider/domínio, porque um perfil não conta como vaga T. Nenhum filler.
- Atributos globais I0001–I0006 compartilhados; usar fonte de verdade do Core.
- S generalistas por categoria/tags/capability; T específico por Ability. Mesmo spell não recebe múltiplos perfis só porque pode aparecer em slots/tier diferentes.
- Vagas T reservadas inicialmente **não contêm Ability**; cada vaga tem arquivos estruturais dos 3 grupos, sem efeitos inventados.
- Escolha 1 LEFT (.11–.19), 1 RIGHT (.21–.29), 1 METAMORPHOSIS (.31–.39). Grupo tem mínimo 2/2/3 opções por perfil real, máximo 9 cada.
- Nenhuma integração pode ser declarada auditada sem mod id, versão/JAR instalado, evidência correspondente à release, fato mecânico nativo, contrato do hook/authority e fallback.
- Custo de perk (Passive Points) e requisito de atributo (ranks canônicos sem consumo) são coisas distintas.
- Ver [`CODEX-FOUR-STAGES.md`](./CODEX-FOUR-STAGES.md), [`PROVIDER-EVIDENCE-AND-COSTS.md`](./PROVIDER-EVIDENCE-AND-COSTS.md), [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md).
- A modlist/datapacks/Black Arcana e os critérios em `PROJECT-INSTRUCTIONS/orientacoes/ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` são fontes obrigatórias; itens antigos específicos de A/Notion/3 chats não determinam a nova estrutura.
- Topologia visual, ordem de desbloqueio concreta, implementation bindings e release somente após catálogo + testes e gates de runtime.

---

### Task 0 — concluir identidade dos atributos I e política de custo/requisitos

**Files:** `01-internal-attributes/README.md`, `I0001..I0006-*.md`, `PROVIDER-EVIDENCE-AND-COSTS.md`; futuramente `NodeAccessRequirement.java` / `NodeAccessResolver.java` e testes correspondentes.

**Interface:** ler `AttributeId`/`AttributeRanks`/`AttributeRankCostPolicy`; produzir seis dossiês I com fonte canônica e proposta de thresholds/gates. Não criar stat paralelo.

- [ ] Validar seis atributos, uncapped e custo de rank via Core.
- [ ] Anotar funções/escala verificadas e proposta de threshold por power tier, sem inventar fórmula como implementada.
- [ ] Revisar que atributo mínimo NÃO debita pontos na compra de perk.
- [ ] Registrar contrato posterior para verificar atributos na compra e após respec.
- [ ] **Gate:** seis dossiês completos, sem duplicação de custo e sem novas fórmulas não evidenciadas.

**Tests planned:** quantidade e IDs I exatos, políticas de ranks existentes; futuro teste de requisito em NodeAccessResolver incluindo abaixo/igual/acima do mínimo.

### Task 1 — escrever todas as Standard Perks P

**Files:** `02-standard-perks/<tree>/Pxxxx-<slug>.md`, `CAPABILITY-MATRIX.md` (proposto), `CATALOG-BUDGETS.md` (proposto); fonte de critério e modlist atuais.

**Interface:** consumir JAR/version/source de providers e facts; produzir quota P equilibrada com dossiês completos conceitualmente e status técnico distinguido.

- [ ] Conferir modlist/versão instalada, datapacks e interações entre mods por batch; pesquisar source/API da release instalada para as funções usadas.
- [ ] Propor orçamento P igual para todas árvores, antirrepetição e função estrutural/tier.
- [ ] Escrever P com: efeito, custo por rank, rank máximo, atributo mínimo, requisitos, scaling, mod/provider/versão/JAR/fato nativo/hook, sinergias e fail-closed.
- [ ] Revisar cada P contra os 9 eixos/18 critérios, inclusive PT-BR e antiabuso; não chamar `TECH-AUDITED` se falta boundary comprovado.
- [ ] **Gate:** mesmo total P por árvore, sem filler, versão de cada provider identificada, dossiês completos e bloqueios documentados.

**Tests planned:** unique P IDs, owner válido, contagem por árvore, campos de evidência por integração, gate de atributo não conflando custo.

### Task 2 — escrever especializações S e Mecânicas Assinatura

**Files:** `04-specialization-perks/<owner>/<specialization>/README.md`, `Sxxxx-<slug>.md`; `SPECIALIZATION-ROSTER.md` proposto; [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md).

**Interface:** provider skills/actions normalizadas para filtros de S; produzir perks que agem em categorias verificadas, não em um único spell.

- [ ] Roster PURE/HYBRID e owner único; mesma quota total S por árvore.
- [ ] Para cada especialização, definir Mecânica Assinatura sustentada pelos mods/vanilla existentes (afinidades, espíritos, summons, captura etc.).
- [ ] Definir predicados explícitos por tags de ação efetivamente comprovadas e normalizadas; exemplo `fire + projectile + single_target`.
- [ ] Escrever S completas: evento correto, efeitos, cooldown/limites, custo/ranks/atributos, mod version, causalidade, anti-recursão/friendly fire, fallback.
- [ ] Auditar mappings/hook do provider real e sobreposição com P e outros S/T; distinguir `CONCEPT-REVIEWED` de `TECH-AUDITED`.
- [ ] **Gate:** quota S e ownership equilibrados, tag predicates e evidência auditáveis, nenhum S definido exclusivamente por um spell individual.

**Tests planned:** classificações presentes/ausentes, não disparar com origem não compatível, evitar proc de proc, híbridos não contam duas vezes, budgets iguais.

### Task 3 — reservar vagas e depois auditar todos os perfis T por Ability

**Files:** `03-transmutation-perks/<tree>/Txxxx-reservado/`, `03-transmutation-perks/ability-profiles/<provider>/<ability>/` (proposto), `ABILITY-COVERAGE-MATRIX.md` (proposto).

**Interface:** vagas com tier progressivo + perfis Ability específicos + futura binding de jogador, não codificado nesta etapa.

- [ ] Com budget provisório comum, gerar **apenas slots T numerados vazios**, com raiz e os sete filhos editoriais mínimos .11/.12/.21/.22/.31/.32/.33, campos presentes/sem skill específica e `RESERVED-UNBOUND`.
- [ ] Registrar tier e critérios progressivos do slot separados de nível/raridade/rank máximo da Ability. Não classificar Legendary maxLevel 1 como inicial.
- [ ] Após catalogação, auditar **cada Ability** instalada/vanilla/própria e classificar ELIGIBLE, DESIGN-CANDIDATE, BLOCKED ou UNSUPPORTED com justificativa.
- [ ] Criar um perfil por Ability elegível (ID namespaced), opções específicas LEFT/RIGHT/META e versão de provider; compatibilidade e escolha 1+1+1. Um perfil pode ser apresentado em vagas compatíveis sem duplicar arquivos.
- [ ] Verificar requisito de conhecimento/desbloqueio real, runtime boundaries, tag semantics e interação com S. Para Shield, não prometer duração/mobilidade/reflexão sem verificar.
- [ ] **Gate:** vagas iguais por árvore, perfis únicos por Ability, todas as candidatas com disposição de cobertura, zero vagas vazias tratadas como conteúdo implementado.

**Tests planned:** 2/2/3 até 9 por grupo, perfil único por Ability, compatibilidade slot/nível normalizado, fonte de conhecimento de spell, provider ausente, save/reload multiplayer e exclusividade 1+1+1 quando implementado.

### Task 4 — validação automática, runtime, topologia e wiki (posterior)

**Files (propostos):** `scripts/validate-perk-catalog.py`, testes; classes de access requirement e abilities adapters, recursos JSON e UI definidos após auditoria.

**Interface:** converter catalogues validados em compras, efeitos, selections, persistence e wiki sem usar perk não comprovada.

- [ ] Validador com dois modos: editorial (permite vagas T vazias) e release (rejeita slots sem comportamento/runtime definido ou perfil indevidamente classificado).
- [ ] Testes antes da implementação para atributo mínimo, custo de perk, provider native handling, classificação/tags, assinatura, slot-binding, T groups, respec e optional provider.
- [ ] Implementar os recursos e adapters conforme as interfaces auditadas, sem clonar mana/cooldown, sem fabricar Ability nem duplo hit.
- [ ] Executar `./gradlew test`, `bash scripts/test-core.sh`, `python3 scripts/validate-data.py`, testes de servidor e multiplayer; registrar resultados reais.
- [ ] Somente então desenhar posição visual definitiva, camadas e ramificações de híbridas; gerar wiki e validar divergências.

**Gate final:** dossiês, ids, custos, gates, versões, runtime, validação e UI coerentes e nenhum slot placeholder publicado como perk funcional.

---

## Política de correções

Se uma P/S revelar ausência de hook seguro, marcar `TECH-BLOCKED` e revisar o conceito; não substituir automaticamente por +dano ou +mastery. Se a quota for inviável, propor redução **global** e documentá-la. Na etapa 3, perfis podem variar em número por provider, mas vagas T continuam com contagem estrutural igual por árvore.

## Decisões abertas que não podem ser inventadas

1. Quantidades finais de P/S/T e orçamento de custo por tier.
2. Se uma Ability pode ocupar duas vagas num mesmo personagem; política de troca de vínculo e refund, persistência.
3. Tiers normalizados entre providers e se ability nível 1 mas rara fica em faixa avançada.
4. Números de thresholds de atributos e efeitos de respec sobre perk adquirida.
5. Roster de especializações e classificação exata de Ability Facts por provider.
6. Perfis de transformação suportados pela release instalada de cada mod.
7. Tratamento editorial do piloto `T5000-chain-lightning/` existente: ele é **exemplo de perfil de Ability**, não deve continuar como binding obrigatório de uma vaga na arquitetura dinâmica. Migrar na Task 3, sem descartar conteúdo técnico válido nem contar duas vezes.
