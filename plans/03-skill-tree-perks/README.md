# 03 — Skill Tree & Perks

Este Stage define o novo catálogo I/P/S/T. O legado A#### ainda existe no runtime, mas **não** governa o novo design; código e save antigos só devem ser migrados numa etapa própria com testes.

## Ordem do Codex

**[CODEX-FOUR-STAGES.md](./CODEX-FOUR-STAGES.md):**

0. **Atributos I:** seis atributos do Core, progressão e política de requisitos.
1. **Perks P:** escrever perks normais completas como design após consultar modlist, fontes da versão instalada, hooks/contratos e custos.
2. **Especializações S:** criar ramificações e Mecânicas Assinatura, com perks generalistas aplicadas por **categoria/tag verificada**, não por ID exclusivo de um spell.
3. **Transmutation T:** criar **vagas numeradas vazias** por tier; depois catalogar **cada Ability individualmente** e desenvolver seu perfil de transformação; inclui skills não mágicas.

As 11 árvores devem ter quotas iguais por família comparável. O desenho visual vem depois.

## Documentos de execução

- [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md): entregáveis, gates e testes.
- [`PROVIDER-EVIDENCE-AND-COSTS.md`](./PROVIDER-EVIDENCE-AND-COSTS.md): como provar mod/versão, custo de perk e requisito de atributo.
- [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md): distinguir posição T, perfil de Ability e tags de especialização.
- [`CODEX-TWO-PASS-BUILD.md`](./CODEX-TWO-PASS-BUILD.md): duas passagens dentro da etapa de Transmutation.
- [`CATALOG-CONTRACT.md`](./CATALOG-CONTRACT.md): paridade e ownership.
- [`NUMBERING.md`](./NUMBERING.md): identificadores I/P/S/T.
- [`DOSSIER-CONTRACT.md`](./DOSSIER-CONTRACT.md): campos obrigatórios para todos os dossiês.
- [`SOURCES-AND-SELECTION-POLICY.md`](./SOURCES-AND-SELECTION-POLICY.md): modlist, datapacks, Black Arcana, vanilla, projetos próprios.
- [`SPECIALIZATION-CONTRACT.md`](./SPECIALIZATION-CONTRACT.md): especializações e Mecânica Assinatura.
- [`00-EXECUTION-ROADMAP.md`](./00-EXECUTION-ROADMAP.md): visão global.
- [`06-content-wiki-generation.md`](./06-content-wiki-generation.md): fechamento do catálogo, runtime e wiki.

## Pastas de conteúdo

- `01-internal-attributes/`: I0001..I0006 comuns.
- `02-standard-perks/<tree>/`: Pxxxx.
- `03-transmutation-perks/<tree>/`: **slots** Txxxx vazios; perfis individuais por Ability em `ability-profiles/` **serão criados na etapa 3**.
- `04-specialization-perks/<owner-tree>/<specialization>/`: Sxxxx e Mecânicas Assinatura.

## O que ainda não é implementação

O mecanismo atual de requisitos de compra **não contém atributo mínimo**, e a vinculação de uma Ability a um slot T no runtime ainda é uma proposta. Não atribuir status de código pronto a documentação editorial. Não alegar que um mod suporta uma alteração com base numa versão antiga.
