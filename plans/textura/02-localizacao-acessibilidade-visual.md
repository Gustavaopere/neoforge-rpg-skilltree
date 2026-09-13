# 02 — Localização e Acessibilidade Visual

## Origem

Apresentação extraída principalmente de:

- `plans/07-data-network-ui/06-localization-accessibility.md`;
- a cláusula player-facing de `plans/08-quests-progression-hooks/23-network-ui-authoring-diagnostics.md`;
- requisitos visuais recorrentes dos Stages 10, 11 e 12.

Keys, placeholders, aliases semânticos, validação e filtragem de dados continuam nos planos de engenharia.

## Objetivo

Garantir que toda superfície própria do RPG apresente conteúdo localizado de forma legível e que estado importante não dependa de uma única pista visual.

## Princípios

- pt-BR continua sendo o locale canônico para conteúdo próprio quando assim definido pelo estágio de origem;
- IDs técnicos não são copy de interface normal;
- tradução não pode mudar a identidade técnica nem inventar semântica de provider desconhecido;
- contraste, tipografia, ícones e layout devem sobreviver às escalas de GUI suportadas;
- cor nunca deve ser o único canal para estado crítico;
- ícones importantes devem possuir texto, tooltip ou outra redundância semântica;
- mensagens de erro/recusa devem priorizar a causa compreensível ao jogador, não stack trace, enum interno ou classe Java.

## Tipografia e densidade

Textura é responsável por decidir, quando o design for materializado:

- hierarquia de título/subtítulo/body/metadata;
- largura e wrapping;
- truncamento somente quando houver acesso ao conteúdo completo;
- tratamento de números, porcentagens e unidades já formatados semanticamente pela engenharia;
- contraste de texto e fundos;
- comportamento de labels em telas compactas/ultrawide.

## Estados redundantes

Quando um estado tiver consequência funcional relevante, a apresentação deve combinar sinais quando necessário, por exemplo:

- cor + ícone;
- cor + padrão/borda;
- badge + texto;
- estado visual + tooltip.

A escolha estética concreta fica para o design visual; esta migração não fixa paleta.

## UIs narrativas futuras

O Stage 08 continua dono de snapshots, secrets, visibility filtering, explain tools e diagnóstico de authoring. Qualquer UI player-facing futura deve trazer para esta pasta a especificação de legibilidade e apresentação, sem transferir secrets ou authority ao cliente.

## Testes/validação visual

- chaves resolvidas não devem aparecer visualmente como IDs crus;
- strings longas precisam ser verificadas em escalas de GUI relevantes;
- contraste e distinção de estados precisam de validação visual;
- foco/hover/seleção devem ser perceptíveis quando o componente funcional oferece esses estados;
- client smoke deve detectar clipping severo, missing textures/fonts e recursos ausentes.

Os validators de existência de key/placeholders continuam sob engenharia e CI dos respectivos estágios.
