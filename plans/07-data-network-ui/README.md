# 07 — Data, Network & UI

Carregar dados com validação forte e expor ao cliente somente o estado necessário para operar a progressão sem transferir authority de gameplay para a interface.

Ordem funcional: schemas → reload/snapshots → protocolo → sync do jogador → contrato funcional da tela → localização/validação.

A apresentação da árvore — layout, node styling, ícones, HUD, tooltips, tipografia, motion e acessibilidade visual — pertence a:

- `../textura/01-skill-tree-ui-hud.md`;
- `../textura/02-localizacao-acessibilidade-visual.md`.

O Stage 07 continua dono de read models, input/hit-testing funcional, requests, respostas, localization keys e restrições client/server. A camada de Textura não concede unlock, compra, respec ou qualquer mutação localmente.
