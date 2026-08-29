# Stage 10.07 — Compêndio: loot, dieta, reprodução e ecologia — Design

**Status:** aprovado e implementado no PR #92.  
**Plataforma:** Minecraft 1.21.1, NeoForge 21.1, Java 21.

O Stage 10.07 enriquece páginas com relações de gameplay verificáveis sem executar gameplay para produzir documentação.

- Relações aceitam alvos tipados `ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK` e `BLOCK_TAG`; itens não viram `CompendiumEntryKind.ITEM`.
- `CURATED_EDITORIAL + EXACT` exige evidência explícita.
- Loot é analisado estruturalmente em `data/<namespace>/loot_table/entities/...`; não há roll de loot table, spawn, comando, function ou contexto sintético.
- Snapshot de loot é imutável e publicado atomicamente após staging/validação; falha mantém o snapshot anterior.
- Páginas ENTITY aplicam o snapshot corrente via `CompendiumLootEnricher`, expondo `DROPS` e facts de item/quantidade/chance somente quando demonstráveis.
- Food, temptation e breeding permanecem independentes.
- Taming separa capability da espécie do tame/owner/adult state contextual.
- Ecologia só publica relações com comportamento técnico ou fonte confiável; habitat permanece no 10.08.
- Integrações externas completas permanecem no 10.11; save/rede/cache global permanece no 10.13.
- Adapters opcionais degradam de forma fail-soft, sem reflection arbitrária ou NBT não documentado.

Acceptance exige Ecology, Flora, Entities, Discovery e RPG Skill Tree CI completos, incluindo NeoForge build, JAR e dedicated-server smoke, antes do merge.
