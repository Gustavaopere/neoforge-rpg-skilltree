# 07.06 — Localização e contrato funcional de acessibilidade

## Fronteira

Legibilidade, contraste, tipografia, ícones e redundância visual foram separados para `../textura/02-localizacao-acessibilidade-visual.md`.

Este arquivo mantém localização e dados semânticos necessários à acessibilidade.

## Objetivo

Garantir que todo conteúdo player-facing próprio possua representação localizada e que a camada de apresentação receba sinais semânticos suficientes para não depender de IDs crus ou de inferência visual.

## Engenharia

- [ ] Cobrir nomes, descrições, erros, gateways, classes e escolhas com localization keys estáveis.
- [ ] Fornecer textos/labels alternativos quando um estado não puder ser comunicado somente por um ícone.
- [ ] Expor estado semântico explícito para que a UI não precise inferir significado por cor.
- [ ] Usar localização como fonte factual para geração da wiki quando apropriado.
- [ ] Validar chaves ausentes e placeholders inconsistentes.
- [ ] Impedir que IDs técnicos, enums ou erros crus sejam o único texto disponível no fluxo normal.

## Handoff para Textura

Textura recebe keys resolvidas/argumentos e estados funcionais. Ela define contraste, wrapping, escala, hierarquia tipográfica e redundância visual em `../textura/02-localizacao-acessibilidade-visual.md`.

## Acceptance funcional

Nenhuma feature do escopo final exige ler ID cru para ser entendida pelo jogador, e a camada de apresentação recebe estado semântico suficiente para representar diferenças sem inventá-las.
