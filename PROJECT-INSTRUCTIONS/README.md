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

Os cinco arquivos abaixo formam o pacote operacional estável para o fluxo de perks do RPG Skill Tree:

1. `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`
2. `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`
3. `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`
4. `CHAT-2-IMPLEMENTACAO-PERKS-ANEXOS-PROJETO.md`
5. `CHAT-3-PENDENCIAS-TESTES-VALIDACAO-MERGE-PERKS-ANEXOS-PROJETO.md`

Para providers externos, a autoridade operacional não é mais um guia temático consolidado: `modlist/modlist.md` fixa presença, posição, JAR e versão instalada, e o dossier individual atual em `modlist/<categorias>/✅-*.md` fixa mecânicas, ownership/authority, lifecycle, multiplayer, riscos, hooks e limites conhecidos. `GUIA-COMPLETO-PROJETOS-PROPRIOS.md` permanece obrigatório para RPG Skill Tree, Volcanoes, Enshrouded e Black Arcana.

## Estrutura de suporte

- Os contratos operacionais de engenharia do RPG ficam na raiz do repositório: `/ENGINEERING.md`, `/AGENT-WORKFLOW.md`, `/TESTING.md`, `/DIAGNOSTICS.md` e `/REPO-ROUTING.md`.
- `guides/` contém os contratos transversais de `projects/` e o relatório histórico da auditoria que reconciliou os antigos guias temáticos com os dossiers individuais.
- `modlist/` preserva material de auditoria/delta da modlist já consolidado no repositório.
- A antiga árvore `skills/`, o antigo catálogo/tooling I2 compartilhado e a antiga subárvore `engineering/` foram retirados da árvore ativa depois de migração/reconciliação ou realocação do conteúdo RPG necessário.

Material histórico removido da árvore ativa continua recuperável pelo histórico Git e pela matriz/status de migração da Factory. Ele não é authority para novas capabilities compartilhadas.

## Entrypoints com localização obrigatória

Alguns arquivos precisam permanecer fora deste diretório porque sua localização tem semântica operacional ou de descoberta:

- `/AGENTS.md` permanece na raiz para descoberta automática de agentes e aponta para o contrato runtime local e para os entrypoints compartilhados da Factory;
- `/ENGINEERING.md`, `/AGENT-WORKFLOW.md`, `/TESTING.md`, `/DIAGNOSTICS.md` e `/REPO-ROUTING.md` permanecem na raiz como contratos operacionais do RPG, sem recriar uma subárvore de control plane compartilhada;
- `.github/**` permanece sob `.github/` quando o GitHub exige o caminho para workflows, templates ou políticas da plataforma.

Documentação cujo papel principal seja código, configuração ou workflow executável deve permanecer no domínio correspondente. Não recriar uma árvore local de infraestrutura comum somente para conservar caminhos históricos.

## Migração e retirada dos guias temáticos

Os antigos guias temáticos de Gameplay, Magia e Tecnologia foram reconciliados contra os dossiers individuais na auditoria de 29/09/2026. As lacunas perk-relevantes encontradas foram migradas para os dossiers correspondentes. Depois dessa reconciliação, a camada temática agregada foi retirada da árvore ativa para evitar duplicação e drift.

O conteúdo removido continua recuperável pelo histórico Git, mas não é authority operacional. Não recriar `guides/gameplay/`, `guides/magic/`, `guides/technology/` nem os três antigos `GUIA-COMPLETO-*` temáticos como fontes paralelas.

## Regra de manutenção

Atualizações de provider externo devem ocorrer no dossier individual correspondente em `modlist/`. Contratos dos quatro projetos próprios e integrações transversais continuam em `guides/projects/` e no consolidado `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`.

Novas skills, contracts, validators, templates, tooling, catálogos e automações **genéricos de produção de mods** pertencem à Minecraft Mod Factory. Este repositório só deve receber alterações desse tipo quando forem necessárias para o próprio runtime RPG ou para preservar/migrar trabalho histórico.
