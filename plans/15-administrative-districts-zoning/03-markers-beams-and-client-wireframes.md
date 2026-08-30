# 15.03 — Markers, beams e wireframes client-side

## Objetivo

Dar feedback espacial sem spawnar entidades persistentes nem sobrecarregar servidor.

## Render

- vértices: marker temporário;
- segmentos: linha/beam;
- polígono confirmado: contorno e preenchimento translúcido opcional;
- distrito selecionado: destaque;
- conflitos: sinalização local distinta.

## Regras

Render é client-only e nunca é autoridade. O servidor sincroniza apenas districts autorizados e revisions necessárias. Desconectar limpa caches.

LOD/culling:

- não renderizar geometria além do alcance configurado na visão 3D;
- mapa pode usar versão simplificada;
- polígonos grandes usam triangulação/cache client-side por revision, não por frame.

## Acessibilidade

Não depender somente de cor: usar padrões/espessura/ícones para estados. Textos e tooltips em pt-BR.

## Testes

- dedicated server sem classes client;
- cache invalida quando revision muda;
- logout limpa overlay;
- polígono côncavo renderiza sem preencher exterior;
- muitos distritos respeitam culling.

## Acceptance

O traçado é legível e responsivo, mas remover o renderer não afeta nenhuma fronteira persistida.