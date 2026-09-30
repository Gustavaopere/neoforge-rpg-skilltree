<!-- Guia temático canônico versionado no GitHub | snapshot histórico de presença/JAR/versão: 2026-09-06; authority atual: ../../modlist/modlist.md + dossier individual -->

[← Índice do guia](README.md)

# Visão geral e escopo

> **SNAPSHOT HISTÓRICO — 2026-09-06:** naquele checkpoint havia **607 entradas top-level** e o recorte tecnológico cobria **200 JARs/IDs únicos**. [`CURRENT-MODLIST.md`](CURRENT-MODLIST.md) preserva esse snapshot; presença/JAR/runtime atuais vêm de `../../modlist/modlist.md`, e o dossier individual atual prevalece para mecânicas/hooks do provider.

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