# 03 — Skill Tree & Perks

Fechar a Árvore Principal como grafo data-driven íntegro, comprável, reembolsável e documentável.

Base atual: 512 nós materializados e 119 declarações auditadas de efeitos de atributo.

Ordem: schema/loaders → grafo/layout → compra/ranks → efeitos → respec → conteúdo e geração de wiki.

## Instruções operacionais

Os critérios de aprovação, protocolos dos Chats 1/2/3, quatro guias completos e governança de modlist foram centralizados em [`../../PROJECT-INSTRUCTIONS/`](../../PROJECT-INSTRUCTIONS/README.md).

A antiga árvore `plans/03-skill-tree-perks/guides/` foi movida para `PROJECT-INSTRUCTIONS/guides/` para evitar instruções operacionais espalhadas pelo repositório.

## Dossiês canônicos de perks

As especificações auditadas, evidências de implementação, contratos técnicos, testes e pendências de cada perk continuam mantidas individualmente em [`perks/`](./perks/README.md).

O **Notion continua sendo a fonte de verdade do design**. Os dossiês do repositório registram o snapshot auditado e o estado técnico real de `main`; divergências devem permanecer explícitas como pendências, nunca ser resolvidas silenciosamente alterando a identidade da perk.

A migração do antigo checklist agregado é feita em lotes auditados. O primeiro lote documentado individualmente é **A0001–A0010**.
