# 11.10 — Integração com Iron's Spellbooks e Ars Nouveau

## Objetivo

Integrar equipamentos e atributos mágicos sem duplicar os sistemas próprios de magia e mantendo toda apresentação do Stage 11 em português do Brasil.

## Passo a passo

### A — Auditoria fresca de contratos

Antes de implementar, confirmar nas versões instaladas:

- IDs e APIs de mana/mana máxima/regeneração;
- poder mágico geral e por escola/elemento;
- cooldown/cast speed quando expostos de forma estável;
- categorias de staff/spellbook/armor/Curios;
- bridges já fornecidas por Iron's Apothic/Apothic Compats;
- quais valores são atributos reais e quais são apenas propriedades internas.

Não inventar adapter para API inexistente.

### B — Iron's Spellbooks

Pools possíveis, sujeitos à API real:

- Prefixos: Poder Mágico, poder por escola, velocidade/eficiência ofensiva;
- Sufixos: Mana Máxima, Regeneração de Mana, redução bounded de recarga, defesa mágica;
- Infixos: efeitos condicionais ao conjurar, matar com magia, alternar escola ou cumprir gatilho suportado.

Staffs e spellbooks devem possuir classificação própria e também categorias genéricas aplicáveis.

### C — Ars Nouveau

- [ ] integrar mana/regen e capacidades realmente expostas;
- [ ] classificar armaduras, equipamentos e Curios relevantes;
- [ ] manter Threads como sistema Ars separado;
- [ ] mudança de Thread não rerrola o item;
- [ ] não fabricar equivalência falsa entre glyph/spell config e modifier RPG.

### D — Convivência Iron's + Ars

Consumir o contrato conjunto de magic stats do Stage 05/06 quando ele estiver fechado. Se ainda houver ambiguidade entre stats homônimas, falhar fechado naquela integração em vez de somar dois atributos como se fossem equivalentes.

### E — PT-BR

Toda linha gerada pelo RPG usa chaves próprias/aliases localizados:

- `Regeneração de Mana`;
- `Mana Máxima`;
- `Poder Mágico`;
- escolas/elementos com nome pt-BR quando exibidos pelo RPG;
- descrições completas de Infixos.

IDs técnicos externos nunca são traduzidos/persistidos como identidade.

### F — Ausência independente

Testar quatro estados:

1. nenhum dos dois;
2. apenas Iron's;
3. apenas Ars;
4. ambos.

## Testes previstos

- item mágico recebe apenas modifiers válidos;
- modifier altera o atributo real quando adapter existir;
- Thread/gem/enchant não rerrola;
- ambos instalados não duplicam mana/poder;
- matriz de classloading opcional;
- tooltip Stage 11 sem inglês/chave crua em `pt_br`.

## Acceptance

Iron's e Ars participam da itemização por adapters verificáveis, seus sistemas próprios permanecem independentes e nenhum atributo mágico é duplicado ou traduzido de forma inconsistente pelo RPG.
