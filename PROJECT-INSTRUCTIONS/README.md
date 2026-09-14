# PROJECT-INSTRUCTIONS

Diretório de instruções operacionais, documentação de referência e proveniência do **RPG Skill Tree** para Minecraft 1.21.1 / NeoForge / Java 21.

## Authority atual

Este repositório é a **authority do código e runtime do mod RPG**.

A infraestrutura comum para criação de mods foi migrada para [`Gustavaopere/minecraft-mod-factory`](https://github.com/Gustavaopere/minecraft-mod-factory), que abriga as authorities lógicas de **Mod Engineering / Integration Control Plane** e **Repo Textura / Visual & Asset Pipeline**.

Portanto, não iniciar aqui novas capabilities genéricas de scaffolding, validators, skills compartilhadas, Blockbench/asset tooling, catálogos, CI reutilizável ou automação cross-mod. Quando trabalho histórico deste repositório for útil à Factory, ele deve ser migrado/reconciliado e revalidado, não reimplementado do zero.

## Antes de alterar o runtime RPG

Leia nesta ordem:

1. [`../AGENT-WORKFLOW.md`](../AGENT-WORKFLOW.md);
2. [`../docs/MASTER_PLAN.md`](../docs/MASTER_PLAN.md);
3. [`../TESTING.md`](../TESTING.md);
4. a modlist física mais recente;
5. o estado atual do GitHub, incluindo `main`, branches e PRs concorrentes.

Para skills, tooling, catálogos ou contratos compartilhados, use os entrypoints canônicos da Factory:

- [`skills/ROUTER.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/ROUTER.md)
- [`skills/VERSION-AUTHORITY.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/VERSION-AUTHORITY.md)
- [`skills/USER-GUIDED-WORKFLOW.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/USER-GUIDED-WORKFLOW.md)

## Autoria narrativa

Para trabalho narrativo substantivo, leia `editorial/NARRATIVE-AUTHORING.md` antes de criar ou alterar material da campanha.

O conteúdo e o cânone versionado permanecem em `historia/`. A configuração específica de consumo da capability reutilizável fica em `historia/narrative-authoring-profile.json`. Validators, inventory, templates e a skill genérica de autoria narrativa pertencem a `Gustavaopere/minecraft-mod-factory/narrative/` e não devem ser duplicados neste repositório.

O contrato editorial local preserva as regras específicas desta campanha para authority Grimoire↔GitHub, IDs estáveis, knowledge/evidence, relações e memória, lifecycle, voz, geografia, epílogos, spoilers e custo zero.

## Autoridade operacional dos perks

Os oito arquivos abaixo continuam sendo o pacote consolidado para o fluxo de perks do RPG Skill Tree:

1. `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`
2. `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`
3. `GUIA-COMPLETO-MODS-DE-MAGIA.md`
4. `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`
5. `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`
6. `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`
7. `CHAT-2-IMPLEMENTACAO-PERKS-ANEXOS-PROJETO.md`
8. `CHAT-3-PENDENCIAS-TESTES-VALIDACAO-MERGE-PERKS-ANEXOS-PROJETO.md`

Para execução operacional de Chat 1/2/3, esses arquivos consolidados continuam sendo o ponto de entrada do conteúdo RPG. A modlist física mais recente continua sendo a autoridade de presença/JAR/runtime; documentação editorial deve ser reconciliada quando houver divergência.

## Estrutura de suporte

- Os contratos operacionais de engenharia do RPG ficam na raiz do repositório: `/ENGINEERING.md`, `/AGENT-WORKFLOW.md`, `/TESTING.md`, `/DIAGNOSTICS.md` e `/REPO-ROUTING.md`.
- `guides/` contém a árvore detalhada e particionada dos quatro guias para manutenção editorial, proveniência, matrizes e deltas técnicos do RPG.
- `modlist/` preserva material de auditoria/delta da modlist já consolidado no repositório.
- A antiga árvore `skills/`, o antigo catálogo/tooling I2 compartilhado e a antiga subárvore `engineering/` foram retirados da árvore ativa depois de migração/reconciliação ou realocação do conteúdo RPG necessário.

Material histórico removido da árvore ativa continua recuperável pelo histórico Git e pela matriz/status de migração da Factory. Ele não é authority para novas capabilities compartilhadas.

## Entrypoints com localização obrigatória

Alguns arquivos precisam permanecer fora deste diretório porque sua localização tem semântica operacional ou de descoberta:

- `/AGENTS.md` permanece na raiz para descoberta automática de agentes e aponta para o contrato runtime local e para os entrypoints compartilhados da Factory;
- `/ENGINEERING.md`, `/AGENT-WORKFLOW.md`, `/TESTING.md`, `/DIAGNOSTICS.md` e `/REPO-ROUTING.md` permanecem na raiz como contratos operacionais do RPG, sem recriar uma subárvore de control plane compartilhada;
- `.github/**` permanece sob `.github/` quando o GitHub exige o caminho para workflows, templates ou políticas da plataforma.

Documentação cujo papel principal seja código, configuração ou workflow executável deve permanecer no domínio correspondente. Não recriar uma árvore local de infraestrutura comum somente para conservar caminhos históricos.

## Migração dos guias

A árvore detalhada foi movida de `plans/03-skill-tree-perks/guides/` para `PROJECT-INSTRUCTIONS/guides/`; o delta de modlist correspondente está em `PROJECT-INSTRUCTIONS/modlist/`.

Referências operacionais dentro de `plans/03-skill-tree-perks/perks/` devem apontar para a nova localização. Documentos históricos em `docs/superpowers/` podem conservar caminhos antigos porque registram a arquitetura existente na data em que foram escritos.

Os comentários `ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/...` presentes dentro dos quatro guias consolidados são marcadores históricos de proveniência do snapshot consolidado, não caminhos vivos nem uma segunda authority.

## Regra de manutenção

Novas atualizações editoriais dos guias detalhados do RPG devem ocorrer em `PROJECT-INSTRUCTIONS/guides/`. Quando uma mudança precisar ser refletida nos quatro arquivos consolidados, ela deve ser reconciliada conscientemente e validada como novo snapshot; não recriar a árvore legada em `plans/03-skill-tree-perks/guides/`.

Novas skills, contracts, validators, templates, tooling, catálogos e automações **genéricos de produção de mods** pertencem à Minecraft Mod Factory. Este repositório só deve receber alterações desse tipo quando forem necessárias para o próprio runtime RPG ou para preservar/migrar trabalho histórico.
