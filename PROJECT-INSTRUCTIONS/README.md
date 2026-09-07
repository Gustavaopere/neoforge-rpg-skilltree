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

- `guides/` preserva a árvore detalhada e particionada dos guias para manutenção editorial, proveniência e compatibilidade com referências antigas.
- `modlist/` preserva material de auditoria/delta da modlist já consolidado no repositório.

A existência da árvore particionada não cria uma segunda autoridade operacional: para os protocolos de perks, os arquivos consolidados desta pasta raiz são o ponto de entrada canônico.

## Migração

Os caminhos antigos permanecem temporariamente preservados para evitar quebrar links, scripts ou branches concorrentes. A limpeza dos caminhos antigos deve ocorrer somente depois de revisar referências e atualizar os índices do repositório.
