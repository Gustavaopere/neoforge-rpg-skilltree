# 07.05 — Contrato funcional da Skill Tree Screen

## Fronteira

A apresentação visual foi separada para `../textura/01-skill-tree-ui-hud.md`.

Este arquivo permanece como contrato de engenharia da tela da árvore.

## Objetivo

Permitir que o cliente navegue pela árvore, consulte requisitos e envie intenções de compra/respec sem se tornar autoridade de progressão.

## Engenharia

- [ ] Expor read model suficiente para nome, descrição, rank, custo, requisitos, efeitos e estado funcional dos nós.
- [ ] Suportar pan/zoom e hit-testing de forma estável nas resoluções/escalas aceitas, sem acoplar a semântica do nó ao renderer.
- [ ] Expor confluências, bridges, gateways, capstones e requisitos faltantes como dados/estados, não inferi-los por aparência.
- [ ] Enviar compra/respec como intenção ao servidor.
- [ ] Exibir somente resultado confirmado e motivo de recusa presentation-safe retornado pelo contrato funcional.
- [ ] Manter o cliente incapaz de conceder unlock, alterar pontos, rank ou requisitos localmente.
- [ ] Preservar IDs estáveis entre layout/renderer e o grafo canônico.

## Handoff para Textura

A engenharia fornece os estados e campos presentation-safe. Layout, node styling, ícones, tooltips, badges, feedback visual e motion são definidos em `../textura/01-skill-tree-ui-hud.md`.

A ausência de um asset visual não muda eligibility, custo, unlock ou resultado da operação.

## Acceptance funcional

O cliente consegue descobrir programaticamente por que um nó está bloqueado, enviar uma intenção válida e refletir o resultado confirmado pelo servidor; nenhum caminho de UI concede progressão localmente.
