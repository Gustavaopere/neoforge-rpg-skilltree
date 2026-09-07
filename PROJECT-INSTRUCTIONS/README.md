# PROJECT-INSTRUCTIONS

Diretório canônico de instruções operacionais e documentação de referência do projeto Minecraft / NeoForge 1.21.1 / Java 21.

## Autoridade operacional

Os oito arquivos abaixo são o pacote canônico consolidado para o fluxo de perks do RPG Skill Tree:

1. `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`
2. `GUIA-COMPLETO-GAMEPLAY-E-SISTEMAS.md`
3. `GUIA-COMPLETO-MODS-DE-MAGIA.md`
4. `GUIA-COMPLETO-MODS-DE-TECNOLOGIA.md`
5. `GUIA-COMPLETO-PROJETOS-PROPRIOS.md`
6. `CHAT-1-AUDITORIA-DESIGN-PERKS-ANEXOS-PROJETO.md`
7. `CHAT-2-IMPLEMENTACAO-PERKS-ANEXOS-PROJETO.md`
8. `CHAT-3-PENDENCIAS-TESTES-VALIDACAO-MERGE-PERKS-ANEXOS-PROJETO.md`

Para execução operacional de Chat 1/2/3, estes arquivos consolidados devem ser preferidos. A modlist física mais recente continua sendo a autoridade de presença/JAR/runtime; a Auditoria Mestre da Modlist no Notion deve ser reconciliada quando houver divergência.

## Estrutura de suporte

- `guides/` contém a árvore detalhada e particionada dos quatro guias para manutenção editorial, proveniência, matrizes e deltas técnicos.
- `modlist/` preserva material de auditoria/delta da modlist já consolidado no repositório.
- `skills/` contém as skills/instruções especializadas usadas por chats e agentes, incluindo router, autoridade de versão, protocolo de interação manual e a biblioteca auditada das skills fornecidas pelo usuário.

A existência das árvores de suporte não cria uma segunda autoridade operacional: para os protocolos de perks, os oito arquivos consolidados desta pasta raiz continuam sendo o ponto de entrada canônico. Para trabalho técnico especializado, `skills/ROUTER.md` escolhe a instrução adequada sem substituir código, JAR, build ou documentação comprovada da versão alvo.

## Migração dos guias

A árvore detalhada foi movida de `plans/03-skill-tree-perks/guides/` para `PROJECT-INSTRUCTIONS/guides/`; o delta de modlist correspondente está em `PROJECT-INSTRUCTIONS/modlist/`.

Referências operacionais dentro de `plans/03-skill-tree-perks/perks/` devem apontar para a nova localização. Documentos históricos em `docs/superpowers/` podem conservar caminhos antigos porque registram a arquitetura existente na data em que foram escritos.

Os comentários `ARQUIVO-FONTE: plans/03-skill-tree-perks/guides/...` presentes dentro dos quatro guias consolidados são **marcadores históricos de proveniência do snapshot consolidado**, não caminhos vivos nem uma segunda autoridade. Eles são preservados para manter os oito arquivos canônicos byte-identical ao pacote validado de 2026-09-07.

## Regra de manutenção

Novas atualizações editoriais dos guias detalhados devem ocorrer em `PROJECT-INSTRUCTIONS/guides/`. Quando uma mudança precisar ser refletida nos quatro arquivos consolidados, ela deve ser reconciliada conscientemente e validada como novo snapshot; não recriar a árvore legada em `plans/03-skill-tree-perks/guides/`.

Skills específicas do projeto devem ser adicionadas sob `PROJECT-INSTRUCTIONS/skills/`, preservando a autoridade definida em `skills/VERSION-AUTHORITY.md`. Não corrigir silenciosamente uma skill de origem multi-versão para fazê-la parecer compatível; a adaptação deve ser explícita e auditável.