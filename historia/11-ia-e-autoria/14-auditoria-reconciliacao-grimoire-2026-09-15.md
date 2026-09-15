# Auditoria de reconciliação Grimoire ↔ GitHub — 2026-09-15

## Estado
AUDITORIA OPERACIONAL / NÃO CRIA CÂNONE NOVO.

## Nota de superação posterior
Este documento preserva o snapshot operacional **anterior** à decisão editorial que resolveu o item Severin ↔ Aren. Ainda em 2026-09-15, uma decisão posterior preservou o ID estável `NPC-0001` para Aren e aposentou Severin como entidade ativa, sem declarar alias ou identidade in-universe e sem transferir voz, aparência, relações ou cenas por herança.

Para o estado corrente desse item, usar `15-migracao-npc-0001-severin-aren-2026-09-15.md`, `historia/STATUS.md`, `13-backlog-editorial-e-bloqueios.md` e `NPC-0001-aren.md`. As afirmações abaixo de que o conflito ainda estava aberto permanecem somente como registro histórico desta auditoria e não representam o estado editorial atual.

## Objetivo
Consolidar, a partir das fontes versionadas disponíveis, quais pendências de reconciliação ainda existem entre `historia/` e a Campaign Bible do Grimoire/TTRPG.bot, quais evidências já foram recuperadas e quais decisões continuam fail-closed.

Este documento não substitui `STATUS.md`, `12-grimoire-github-sync.md`, `13-backlog-editorial-e-bloqueios.md`, os dossiês de entidade nem a própria Campaign Bible.

## Authorities consultadas nesta auditoria
- `historia/STATUS.md` em `main`;
- `historia/11-ia-e-autoria/12-grimoire-github-sync.md`;
- `historia/11-ia-e-autoria/13-backlog-editorial-e-bloqueios.md`;
- `historia/03-npcs/principais/NPC-0001-severin.md`;
- `historia/03-npcs/principais/NPC-0002-elias.md`;
- histórico Git do arquivo `NPC-0002-elias.md`;
- histórico editorial registrado nas PRs #546, #549 e #556.

Nesta sessão, nenhum conector compatível com Grimoire/TTRPG.bot foi encontrado no diretório de plugins disponível. Portanto, nenhum item dependente de consulta nova à Campaign Bible foi promovido, tipado ou resolvido por inferência.

## Resultado executivo
Existem dez pendências editoriais relevantes ainda abertas:

1. um conflito de identidade (`NPC-0001` Severin ↔ Aren);
2. um NPC source-blocked (`NPC-0002` Elias);
3. uma entidade já localizada anteriormente no Grimoire, mas ainda sem gate completo de tipagem/ID (`Pátio da Passagem`);
4. uma parent location ainda não reconciliada;
5. dois membros de `FAC-0002` ainda não reconciliados;
6. quatro membros de `FAC-0003` ainda não reconciliados.

Nenhum ID editorial novo foi reservado nesta auditoria.

## Matriz de pendências

| Alvo | Referência externa | Estado após auditoria | Evidência já disponível | Próxima ação legítima |
| --- | --- | --- | --- | --- |
| `NPC-0001` Severin ↔ Aren | Aren `e9a83c1b-4bd9-49c8-8b79-ad41011383cd` | `CONFLITO DE IDENTIDADE` | o dossiê versionado registra que o Grimoire não possuía `Severin` na reconciliação de 2026-09-14 e possuía Aren ativo com sobreposição material de papel | decisão editorial explícita: alias/mesma entidade, entidades distintas ou retcon/substituição; depois reconciliar os dois lados |
| `NPC-0002` Elias | nome histórico sem UUID recuperado | `BLOQUEADO — GRIMOIRE` | o arquivo entrou no Git em 2026-08-31 já como `RASCUNHO / CONSOLIDAÇÃO PENDENTE`; o único update posterior apenas mudou referência de template | localizar Campaign Bible/registro anterior, chat-fonte ou outra provenance primária; não inferir identidade pelo nome |
| Pátio da Passagem | `14e39b79-a0de-4bf0-9bbe-9c11ae5b95a9` | `PENDENTE DE DECISÃO EDITORIAL` | backlog registra que a entidade já foi localizada anteriormente no Grimoire, mas não passou pelo gate completo de tipagem/ID | recuperar novamente a entidade completa, auditar duplicatas e só então decidir `SET/LOC/outro` e eventual ID |
| parent location de `SET-0003` / `LOC-0002` | `e05ef102-46c3-4f31-8043-ee2eda6d99af` | `BLOQUEADO — GRIMOIRE` | UUID e provenance relacional preservados | consultar entidade-fonte; validar tipo/nome/parentesco e duplicatas antes de materializar |
| membro de `FAC-0002` | `6ffab968-5ff5-4563-9aa9-3552b5e97310` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |
| membro de `FAC-0002` | `74a40012-22e6-44aa-a479-75345011519d` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |
| membro de `FAC-0003` | `29552385-a06a-4258-bcb7-a27c2ce8e6bb` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |
| membro de `FAC-0003` | `32a3237d-e699-4384-bf14-a12f8d620965` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |
| membro de `FAC-0003` | `55f7bdb0-a0a3-46c2-8d10-a69b76cbe6fd` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |
| membro de `FAC-0003` | `8ed41a1d-1692-4df2-9366-7a2153545e74` | `BLOQUEADO — GRIMOIRE` | UUID preservado em `key_member_ids` | consultar entidade-fonte e auditar duplicata/ID existente |

`324ff53b-c599-4892-b263-0f86a8e8f8b9`, também citado em `FAC-0003.key_member_ids`, já está reconciliado como `NPC-0005` Oren e não é pendência.

## Auditoria específica — Severin ↔ Aren
O conflito continua real e não deve ser resolvido por similaridade temática.

Evidência versionada suficiente para manter o bloqueio:
- `NPC-0001` já possui ID estável e papel editorial de necromante/pesquisador;
- a reconciliação registrada em 2026-09-14 não encontrou `Severin` no Grimoire;
- a mesma reconciliação encontrou Aren ativo, com UUID próprio e papel sobreposto envolvendo Goety/necromancia e controvérsia política;
- PR #546 formalizou que essa sobreposição não prova alias nem prova duplicidade;
- PRs posteriores preservaram deliberadamente o conflito sem decisão automática.

Conclusão operacional: nenhuma alteração de nome, ID, provider, aparência, motivação, relação, asset final ou vínculo de quest deve ser propagada entre Aren e Severin antes de decisão editorial explícita.

## Auditoria específica — Elias
O histórico Git do próprio arquivo elimina uma hipótese importante: não há, nesse caminho versionado, uma versão antiga rica do dossiê que tenha sido apagada posteriormente.

O arquivo foi introduzido em `f9a9e8b5a63d1546cee0a350ccbabb77aa4660ae` em 2026-08-31 já contendo apenas:
- nome `Elias`;
- estado `RASCUNHO / CONSOLIDAÇÃO PENDENTE`;
- declaração de que planejamento anterior citava o personagem, mas sem informação canônica suficiente;
- regra explícita de não preencher lacunas por suposição.

O update posterior em `ba0f76606f4032505a0e73410603cfb5a31d9e6d` apenas redirecionou o scaffold para a Minecraft Mod Factory e não adicionou lore.

Conclusão operacional: a recuperação de Elias não deve procurar uma versão antiga perdida desse arquivo. A fonte precisa vir de Campaign Bible/registro anterior, histórico de conversa/documentação externa ou decisão editorial deliberada de redesenho, preservando `NPC-0002`.

## Sequência de reconciliação quando o Grimoire estiver acessível
1. consultar Aren pelo UUID `e9a83c1b-4bd9-49c8-8b79-ad41011383cd` e capturar apenas campos estruturados relevantes à decisão de identidade;
2. pesquisar `Elias` por nome/aliases e provenance anterior, sem assumir que qualquer homônimo é `NPC-0002`;
3. consultar `14e39b79-a0de-4bf0-9bbe-9c11ae5b95a9` e aplicar gate de tipagem/duplicata para Pátio da Passagem;
4. consultar `e05ef102-46c3-4f31-8043-ee2eda6d99af` e validar sua relação real com `SET-0003`/`LOC-0002`;
5. consultar individualmente os dois UUIDs de `FAC-0002` e os quatro UUIDs de `FAC-0003`;
6. para cada entidade recuperada, pesquisar `main`, branches/PRs relevantes, nomes, aliases e relações antes de reservar ID;
7. materializar somente entidades cuja source e tipagem estejam claras; manter ambiguidade como bloqueio;
8. atualizar crosswalk, backlog e dossiês apenas depois da reconciliação individual.

## Gate de saída
Uma pendência só pode ser marcada `CONCLUÍDO` quando houver:
- entidade-fonte ou decisão editorial autoritativa recuperada;
- ausência de duplicata não intencional confirmada;
- tipo editorial decidido com base na identidade da entidade, não no schema genérico da ferramenta;
- ID estável preservado ou atribuído deliberadamente;
- provenance registrada;
- representação GitHub validada;
- atualização do Grimoire quando a decisão legítima alterar a própria Campaign Bible.

## Resultado desta auditoria
- nenhum fato de lore novo criado;
- nenhum UUID reinterpretado por inferência;
- nenhum ID reservado;
- Severin↔Aren permanece conflito explícito;
- Elias permanece source-blocked, agora com histórico Git verificado;
- oito referências relacionadas permanecem rastreadas e fail-closed conforme sua classificação atual;
- próxima etapa depende de consulta real ao Grimoire/TTRPG.bot ou de decisão editorial explícita do usuário para o conflito Severin↔Aren/redesenho de Elias.
