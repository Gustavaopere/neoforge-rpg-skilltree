# PLANO — 03 Skill Tree & Perks

Estado: **EM ANDAMENTO**.

## Base atual

A Árvore Principal possui **512 nós JSON materializados**. Existem 119 declarações auditadas de efeitos de atributo, mas 512 nós não significam 512 bônus mecânicos exclusivos.

## Objetivo

Fechar a árvore como grafo data-driven íntegro, com compra, ranks, requisitos, efeitos, respec e documentação derivada sem drift.

## Dependências

01 RPG Core e contratos de efeitos de 05.

## Etapas de implementação

### 1 — Schema e loaders
- [ ] validar ID, custo, max rank, posição, requisitos, árvore e payload de efeito;
- [ ] rejeitar duplicidade e referências inexistentes;
- [ ] erros apontam arquivo e campo.

### 2 — Validação de grafo
- [ ] detectar ciclos inválidos/requisitos inalcançáveis;
- [ ] verificar roots, regiões, bridges e keystones;
- [ ] preservar orçamento/layout 512/512 enquanto ele for canônico.

### 3 — Compra e ranks
- [ ] servidor valida pontos, requisitos e rank atual;
- [ ] operação atômica: cobrar ponto somente se compra fechar;
- [ ] sync após confirmação.

### 4 — Efeitos
- [ ] resolver efeitos inline + packs externos sem duplicação;
- [ ] modifiers possuem IDs estáveis por jogador/nó/rank;
- [ ] integração opcional ausente não quebra a árvore.

### 5 — Respec
- [ ] validar dependentes antes de remover nó;
- [ ] desfazer modifiers e permissões órfãs;
- [ ] recomputar classe/mastery-derived state quando necessário;
- [ ] definir política de custo.

### 6 — Conteúdo
- [ ] revisar nós que permanecem apenas estruturais;
- [ ] completar nomes/descrições localizadas quando forem perks de gameplay;
- [ ] balancear final triads e bridges.

### 7 — Wiki derivada
- [ ] criar gerador que produza índice, nomes, descrições, custos, ranks, requisitos e stats a partir dos dados/localização;
- [ ] CI detecta drift de `wiki/`.

## Testes

- [ ] IDs e grafo;
- [ ] compra inválida/duplicada;
- [ ] ranks máximos;
- [ ] respec com dependentes;
- [ ] login/reload conserva efeitos exatamente uma vez.

## Definição de concluído

Grafo válido, conteúdo final definido para escopo de release, compra/respec seguros e wiki regenerável; então `PLANO-✅.md`.