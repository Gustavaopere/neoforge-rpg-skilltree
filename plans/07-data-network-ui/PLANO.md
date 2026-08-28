# PLANO — 07 Data, Network & UI

Estado: **EM ANDAMENTO**.

## Objetivo

Carregar dados com validação forte e apresentar árvore/progressão no cliente sem transferir autoridade de gameplay para a UI.

## Dependências

01–06 para os estados que serão exibidos.

## Etapas de implementação

### 1 — Schemas de datapack
- [ ] classes, skills, effects, specializations e progression validados;
- [ ] referências cruzadas verificadas após reload;
- [ ] mensagens de erro com path/ID/campo.

### 2 — Reload
- [ ] montar snapshot novo antes de publicar;
- [ ] falha não deixa estado parcialmente trocado;
- [ ] recomputar efeitos dependentes quando necessário.

### 3 — Protocolo de rede
- [ ] packets mínimos e versionáveis;
- [ ] bounds em listas, strings, IDs e índices;
- [ ] requests de compra/respec são intenção, nunca autorização.

### 4 — Snapshot do jogador
- [ ] level/XP/pontos/classes/masteries necessários à UI;
- [ ] atualização em login, compra, respec e mudanças relevantes;
- [ ] sem sync por tick desnecessário.

### 5 — Tela da árvore
- [ ] pan/zoom robustos;
- [ ] compra/respec com feedback imediato após confirmação;
- [ ] requisitos, rank, custo e descrição legíveis;
- [ ] confluências/gateways mostram motivo do bloqueio.

### 6 — Localização e acessibilidade
- [ ] todos os elementos player-facing possuem chave localizada;
- [ ] tooltip não depende apenas de cor;
- [ ] nomes usados em `wiki/` podem ser derivados da localização.

## Testes

- [ ] reload inválido;
- [ ] payload malformado/fora de faixa;
- [ ] sync em multiplayer;
- [ ] cliente sem estado stale após respec;
- [ ] dedicated server não carrega UI.

## Definição de concluído

Dados validados, protocolo limitado e UI fiel ao servidor; então `PLANO-✅.md`.