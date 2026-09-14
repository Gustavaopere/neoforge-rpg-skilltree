# 11.13 — Localização pt-BR e contrato de apresentação da itemização

## Fronteira

Tooltip, Rank visual, ícones, hierarquia e acessibilidade visual foram separados para `../textura/04-itemizacao-tooltips-identidade-visual.md`.

Este arquivo mantém localização, aliases semânticos, formatação e validators do Stage 11.

## Objetivo

Garantir que toda informação própria da itemização seja fornecida em português do Brasil sem persistir texto traduzido, sem alterar IDs técnicos e sem depender da qualidade de localização de mods externos.

## A — Chaves próprias

Todo Rank, família, modifier, efeito, erro, mensagem e label próprio do Stage 11 deve possuir key estável, por exemplo:

```text
rank.rpgskilltree.common
modifier.rpgskilltree.mana_regeneration
itemization.rpgskilltree.family.prefix
itemization.rpgskilltree.locked_roll
```

IDs técnicos permanecem estáveis; apresentação é localizada no momento de exibição.

## B — Aliases de integrações

Quando o RPG exibir atributos externos, usar aliases próprios somente para conceitos conhecidos e auditados, como Regeneração de Mana, Mana Máxima, Poder Mágico, Velocidade de Ataque, Redução de Recarga, Perfuração de Armadura ou Roubo de Vida quando a semântica correspondente estiver comprovada.

Não renomear internamente IDs externos nem inventar tradução para conceito desconhecido.

## C — Dados do tooltip

O Stage 11 fornece ao cliente, quando aplicável:

- Rank;
- nome base localizado pelo item/provider;
- Poder do Item;
- Prefixos/Sufixos/Infixos e seus textos/valores resolvidos;
- estado de identidade permanente/sem reroll;
- metadata avançada somente quando o modo correspondente estiver ativo.

A estrutura visual desses dados pertence a Textura.

## D — Nome do item

O runtime não deve concatenar todos os modifiers no nome persistido. Rank e modifiers continuam campos semânticos separados; a escolha de badge/cor/prefixo visual pertence ao companion de Textura.

## E — Formatação brasileira

- [ ] percentuais e números apresentados com locale adequado quando possível;
- [ ] duração/unidades consistentes;
- [ ] sinais positivo/negativo semanticamente corretos;
- [ ] IDs/keys não aparecem no modo normal produzido pelo RPG.

Textura define somente como esses valores já resolvidos são organizados.

## F — Debug avançado

Quando permitido, a engenharia pode fornecer:

- item ID;
- instance ID abreviado;
- Poder original;
- source;
- schema version;
- provider do modifier;
- IDs técnicos necessários ao diagnóstico.

Essa metadata não substitui o conteúdo localizado normal.

## G — Validator de localização

CI deve falhar se:

- key própria usada pelo Stage 11 não existir em `pt_br`;
- conteúdo próprio conhecido renderizar key crua;
- definitions/corpus declararem localization key inexistente;
- strings próprias conhecidas forem hardcoded fora do mecanismo aprovado.

Termos ingleses de UI externa fora do controle do RPG devem ser classificados separadamente, sem mascarar limitação de provider como falha própria corrigível.

## H — Handoff de acessibilidade

A engenharia fornece estado semântico e texto equivalente para ícones importantes. Contraste, wrapping, paginação/compactação visual e distinção de Rank sem somente cor pertencem a `../textura/04-itemizacao-tooltips-identidade-visual.md`.

## Testes funcionais previstos

- resolução pt-BR de todos os Ranks/famílias;
- modifier externo com alias auditado;
- modifier desconhecido com fallback legível sem tradução inventada;
- nenhuma key crua própria;
- locale/decimal/percentual;
- payload de tooltip com 5/5/5 modifiers permanece semanticamente completo.

A navegação/legibilidade visual de 5/5/5 é validada pelo companion de Textura.

## Acceptance funcional

O cliente recebe conteúdo localizado e semanticamente correto para Rank, Poder, Prefixos, Sufixos, Infixos, efeitos e recusas; o CI impede regressão de cobertura de localization e a camada visual não precisa inventar significado a partir de IDs.
