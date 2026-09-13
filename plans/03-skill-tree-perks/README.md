# 03 — Skill Tree & Perks

Fechar a Árvore Principal como grafo data-driven íntegro, comprável, reembolsável e documentável.

Base atual: 512 nós materializados e 119 declarações auditadas de efeitos de atributo.

Ordem: schema/loaders → grafo/layout → compra/ranks → efeitos → respec → conteúdo e geração de wiki.

## Instruções operacionais canônicas

Os critérios obrigatórios, os quatro guias consolidados e os protocolos dos Chats 1–3 ficam na pasta raiz [`PROJECT-INSTRUCTIONS/`](../../PROJECT-INSTRUCTIONS/README.md).

Essa pasta é o ponto de entrada operacional do fluxo de perks. A árvore detalhada dos guias está em [`PROJECT-INSTRUCTIONS/guides/`](../../PROJECT-INSTRUCTIONS/guides/README.md); não manter uma segunda cópia ativa em `plans/03-skill-tree-perks/guides/`.

## Dossiês canônicos de perks

As especificações auditadas, evidências de implementação, contratos técnicos, testes e pendências de cada perk são mantidos individualmente em [`perks/`](./perks/README.md).

O **Notion continua sendo a fonte de verdade do design**. Os dossiês do repositório registram o snapshot auditado e o estado técnico real de `main`; divergências devem permanecer explícitas como pendências, nunca ser resolvidas silenciosamente alterando a identidade da perk.

A migração do antigo checklist agregado é feita em lotes auditados. O primeiro lote documentado individualmente é **A0001–A0010**.