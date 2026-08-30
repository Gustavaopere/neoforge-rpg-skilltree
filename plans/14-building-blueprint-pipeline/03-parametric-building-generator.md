# 14.03 — Gerador paramétrico de construções

## Objetivo

Gerar edifícios reproduzíveis a partir de dimensões, módulos e regras, em vez de escrever milhares de coordenadas manualmente.

## BuildingSpec

Deve conter:

- ID namespaced;
- estágio/nível;
- footprint e limites de altura;
- estilo/paleta;
- módulos obrigatórios;
- portas, acessos e circulação;
- markers funcionais;
- requisitos de provider;
- seed determinística opcional;
- regras de upgrade.

## Módulos

Biblioteca própria de módulos: fundação, parede, coluna, janela, telhado, chaminé, torre, corredor, sala técnica, tanque, casa de máquinas, escritório, depósito e outros componentes necessários aos Stages 18/19.

Módulos retornam voxels e constraints. A composição deve detectar colisão em vez de sobrescrever conteúdo silenciosamente.

## Níveis 1–5

Para prédios evolutivos, cada nível é um VoxelModel completo derivado de uma sequência declarada. O diff `N -> N+1` é calculável e auditável; não depende de adivinhar o que mudou.

## Determinismo

Mesma spec + mesma paleta + mesma seed + mesmos registries = mesmo hash voxel.

## Testes

- footprints pares/ímpares;
- rotação 0/90/180/270;
- espelhamento;
- módulos incompatíveis;
- geração níveis 1–5;
- diff de upgrade;
- seed estável;
- volume máximo bounded.

## Acceptance

O pipeline consegue reproduzir exatamente a mesma construção e seus cinco níveis em execuções independentes.