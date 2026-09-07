<!-- Guia temático canônico versionado no GitHub | referência atual de presença/JAR/versão: modlist.txt reconciliada em 2026-09-06 -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **RECONCILIADO COM A MODLIST ATUAL — 2026-09-06:** a fonte canônica contém **607 entradas top-level** e o recorte tecnológico atual cobre **200 JARs/IDs únicos**. Os 190 IDs efetivamente cobertos pelo snapshot anterior continuam presentes; 10 módulos novos foram incorporados. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) registra JAR, mod ID, runtime name e runtime version exatos e prevalece sobre referências históricas.

> Este guia é um **catálogo descritivo dos mods de tecnologia** instalados no pack. O foco é explicar o que cada sistema acrescenta, como sua tecnologia funciona e como os addons se encaixam no ecossistema principal. Bibliotecas, UI e bridges são classificadas pelo papel técnico real e não tratadas como sistemas tecnológicos independentes sem contrato mecânico correspondente.

## Padrão de integração para perks

O guia separa **provider mecânico**, **bridge**, **biblioteca**, **scripting** e **presentation/client compat**. Uma integração só pode alterar o recurso/estado que a versão instalada realmente expõe.

Em especial:
- stress/rotação permanecem Create-owned;
- energia/máquinas Oritech permanecem Oritech-owned;
- requisições de colônia permanecem MineColonies-owned mesmo quando Create Colony Logistics transporta itens;
- KubeJS configura pipelines, mas não substitui a authority do mod configurado;
- EMF Compat: Create não é provider de gameplay;
- Sable/Create Aeronautics permanecem authority física para contraptions, massa e orientação contextual.

---