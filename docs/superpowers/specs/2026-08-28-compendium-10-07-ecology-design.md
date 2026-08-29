# Stage 10.07 — Compêndio: loot, dieta, reprodução e ecologia — Design

**Data:** 2026-08-28  
**Base de desenho:** `main@e474446bcc5d7ac92bb346ec2e4b71de178d9e26`  
**Plataforma:** Minecraft 1.21.1, NeoForge 21.1, Java 21  
**Escopo canônico:** `plans/10-compendio-natural/07-loot-dieta-reproducao-ecologia.md`

## Status

**Aprovado e congelado para a implementação do Stage 10.07.** Evidências TDD/CI ficam no plano de execução e no PR; este arquivo registra apenas as decisões arquiteturais.

## Arquitetura

O Compêndio enriquece páginas com loot, alimentação, atração, reprodução, domesticação e ecologia sem executar gameplay para produzir documentação e sem transformar inferências em fatos confirmados.

Itens não viram `CompendiumEntryKind.ITEM`. Relações usam `CompendiumRelationTarget` tipado: `ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK` ou `BLOCK_TAG`. O construtor legado com `CompendiumEntryId` permanece compatível.

Relações `CURATED_EDITORIAL + EXACT` exigem `evidenceId`. Fontes técnicas reproduzíveis podem omiti-lo. O schema continua v1 e aceita exatamente `to` legado ou `target_kind + target`.

## Loot

Loot é analisado estruturalmente em recursos de datapack/reload; não há roll de `LootTable`, spawn, comandos, functions ou contexto sintético. Formatos matematicamente resolvíveis produzem item/quantidade/faixa/chance; o restante vira `CONDICIONAL`.

Em Minecraft 1.21.1 o diretório é `data/<namespace>/loot_table/...` (singular). O listener observa `loot_table/entities`, produz `CompendiumLootSnapshot` imutável e publica somente depois de staging/validação completa. Falha mantém o snapshot anterior.

`CompendiumLootEnricher` aplica o snapshot corrente a uma página ENTITY sem reconstruir o catálogo-base, adicionando relações `DROPS` e seção `loot`. Números só entram como `EXACT` quando demonstráveis.

## Ecologia

Food, temptation e breeding são semânticas independentes; `Animal.isFood` não promove automaticamente as outras duas. Domesticação separa capability estática de tame/owner/adult state da instância. Dados contextuais nunca entram em cache global de entity/world.

Predação, medo, polinização, hostilidade dirigida e interação com blocos/plantas exigem comportamento técnico ou fonte confiável. Habitat geográfico pertence ao 10.08.

Adapters opcionais são fail-soft e não resolvem classes externas quando o mod está ausente. Wiring completo de TFC/Animal Husbandry/Animal Wellness e equivalentes pertence ao 10.11; sem reflection arbitrária ou NBT não documentado.

## Fronteiras

- 10.08: habitat, bioma, estrutura e dimensão;
- 10.11: wiring completo de APIs externas;
- 10.13: save/rede/cache/reload global do Compêndio.

## Gates

TDD cobre relation targets/evidência, loot simples/range/condicional, food/tempt/breeding, taming contextual, ecology/adapters opcionais, reload atômico e enriquecimento pelo snapshot atual.

A integração requer `Compendium Ecology CI`, Flora, Entities, Discovery e `RPG Skill Tree CI` completos, incluindo NeoForge build, JAR e dedicated-server smoke.

## Acceptance

O Stage 10.07 fecha quando loot é seguro/source-aware, quantidade/chance só são exatas quando demonstráveis, relation targets não criam páginas ITEM artificiais, food/tempt/breeding continuam distintos, taming separa capability de estado, ecologia tem fonte/confiança, optional adapters são fail-soft, reload é atômico, editorial EXACT sem evidência é rejeitado e todos os gates passam antes do merge.
