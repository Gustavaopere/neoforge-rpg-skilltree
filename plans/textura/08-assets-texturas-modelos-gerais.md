# 08 — Assets, texturas e modelos gerais

## Estado desta auditoria

A auditoria de 2026-09-13 encontrou requisitos de apresentação ligados a superfícies específicas, mas não um inventário normativo geral de texturas/modelos do RPG Skill Tree.

Este arquivo cria o local canônico para esse inventário futuro sem fabricar assets, estilo ou requisitos que não existem nos planos atuais.

## Escopo

Quando aprovados, pertencem aqui ou a subplanos derivados:

- atlases e sprites gerais do RPG;
- ícones compartilhados;
- texturas de UI/HUD reutilizáveis;
- texturas de itens/blocos/entidades próprios quando forem assets do RPG;
- modelos próprios de itens/blocos/entidades;
- rigs/anchors compartilhados com animação;
- placeholders/fallbacks visuais;
- materiais/shaders próprios quando houver contrato técnico comprovado;
- convenções de namespace, resolução e exportação;
- provenance/licença de qualquer asset derivado de terceiro.

## O que não entra automaticamente

Assets pertencentes a mods externos não se tornam assets do RPG por aparecerem em uma integração. Reuso, derivação ou distribuição exige licença/proveniência apropriada.

Um item/entity de provider pode ser apresentado pelo renderer/asset do próprio provider sem que este projeto copie o arquivo.

## Handoff técnico

Antes de produzir um asset dependente de runtime, a engenharia deve fornecer, conforme aplicável:

- registry/resource ID;
- namespace e caminho esperado;
- tipo de renderer/model pipeline;
- dimensões/UV/anchors tecnicamente exigidos;
- estados funcionais que precisam de representação;
- provider e versão quando houver contrato externo;
- fallback se o asset faltar.

Textura não inventa registry ID, model loader ou signature de API.

## Inventário

O inventário concreto deve ser criado incrementalmente à medida que os designs forem aprovados. Cada entrada deverá distinguir ao menos:

- `OWNED` — asset original do RPG;
- `PROVIDER` — apresentação fornecida pelo mod/provider;
- `DERIVED_APPROVED` — derivação permitida com proveniência/licença registrada;
- `PLACEHOLDER` — asset temporário explicitamente identificado;
- `PENDING` — design ainda não materializado.

Esses estados são de produção de asset, não estados de gameplay.

## Validação futura

Quando houver assets concretos, adicionar gates para:

- missing resources;
- caminhos/namespaces inválidos;
- dimensões/formatos incompatíveis com o pipeline comprovado;
- provenance ausente quando necessária;
- clipping/UV/preview visual;
- consistência entre estados exigidos e assets entregues.

Nenhum gate numérico de tamanho/performance é congelado sem baseline real.
