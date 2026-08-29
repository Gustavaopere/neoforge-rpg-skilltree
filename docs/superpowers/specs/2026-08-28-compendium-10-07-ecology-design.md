# Stage 10.07 — Compêndio: loot, dieta, reprodução e ecologia — Design

**Data:** 2026-08-28  
**Base de desenho:** `main@e474446bcc5d7ac92bb346ec2e4b71de178d9e26`  
**Plataforma:** Minecraft 1.21.1, NeoForge 21.1, Java 21  
**Escopo canônico:** `plans/10-compendio-natural/07-loot-dieta-reproducao-ecologia.md`

## Status do design

**Aprovado e congelado para esta implementação.** Evidências TDD/CI e o merge-ref final ficam no plano `docs/superpowers/plans/2026-08-28-compendium-10-07-ecology.md` e no PR de implementação.

## 1. Objetivo

Enriquecer entradas do Compêndio Natural com relações de gameplay verificáveis — loot, alimentação, atração, reprodução, domesticação e ecologia — sem executar efeitos de gameplay para produzir documentação e sem converter inferências em fatos confirmados.

O Stage 10.07 trabalha sobre o modelo canônico já fechado nos Stages 10.03–10.06. Ele não cria um recipe browser, não assume habitat/worldgen que pertence ao 10.08 e não absorve o wiring completo de APIs públicas de mods opcionais que pertence ao 10.11.

## 2. Alvo tipado de relação

Itens e tags de itens não são páginas canônicas do Compêndio e não viram `CompendiumEntryKind.ITEM`.

`CompendiumRelationTarget` usa kinds explícitos `ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK` e `BLOCK_TAG`. `CompendiumRelation` mantém compatibilidade com `CompendiumEntryId` e o alvo expõe serialização determinística para ordenação, diagnostics e schema.

## 3. Proveniência e confiança

`CURATED_EDITORIAL + EXACT` exige `evidenceId` não vazio. Fontes técnicas reproduzíveis podem omiti-lo. `UNAVAILABLE` não é relação publicável.

## 4. Schema

`schema_version: 1` permanece compatível. Relação usa exatamente um formato: `to` legado para ENTRY ou `target_kind + target`. Misturar os dois falha deterministicamente.

## 5. Loot seguro

Loot é derivado dos recursos de datapack/reload, nunca de execução de `LootTable`, comandos, functions, spawn ou contexto sintético.

O parser resolve apenas estruturas matematicamente demonstráveis. Formato/função/condição não suportado vira `CONDICIONAL`, nunca número inventado.

`CompendiumLootSnapshot` é imutável. `CompendiumLootEnricher` aplica o snapshot atual a uma página ENTITY sem reconstruir o catálogo-base, adicionando `DROPS` e seção `loot`; item, quantidade/faixa e chance entram como facts apenas quando resolvidos.

Minecraft 1.21.1 usa `data/<namespace>/loot_table/...` (singular). O listener observa `loot_table/entities`; o ID lógico permanece `<namespace>:entities/<path>`.

## 6. XP de morte

XP vanilla/base e recompensas do RPG permanecem semanticamente separados. Valor dinâmico de instância não vira inteiro exato global.

## 7. Dieta, atração e reprodução

Food, temptation e breeding são independentes. `Animal.isFood(ItemStack)` não prova temptation nem reprodução. Gestação, sexo, genética e herança só entram por adapter confiável.

## 8. Domesticação

Capability estática da espécie é separada do tame/owner/adult state contextual. Inspeção usa entidade existente e não mantém cache global de entity/world.

## 9. Ecologia

Predação, medo, polinização, hostilidade dirigida e interação com blocos/plantas só entram com comportamento técnico ou fonte confiável. Habitat geográfico permanece no 10.08. Não há heurística por nome/translation key/aparência.

## 10. Mods opcionais

Enrichment opcional é fail-soft. Ausência/incompatibilidade preserva o catálogo base. Wiring completo de APIs externas permanece no 10.11; sem reflection arbitrária ou NBT não documentado.

## 11. Reload e atomicidade

Reload constrói staging, parseia/valida e só então troca o snapshot imutável. Falha mantém o snapshot anterior. Não há rebuild por tick/frame. Orquestração global de cache/rede continua no 10.13.

## 12. Erros

Loot malformado falha antes da publicação; conteúdo não suportado fica contextual; editorial EXACT sem evidência e target inválido são rejeitados; optional mod ausente degrada sem corromper catálogo.

## 13. Testes e gates

TDD cobre relation target/proveniência, loot simples/range/condicional, food/tempt/breeding, taming contextual, ecology/adapters opcionais, reload atômico e enriquecimento da página pelo snapshot atual.

Gate focal: `Compendium Ecology CI`. Integração exige também Flora, Entities, Discovery e `RPG Skill Tree CI`, incluindo NeoForge build, JAR e dedicated-server smoke.

## 14. Acceptance

O Stage 10.07 está pronto quando loot é seguro e source-aware; quantidade/chance só são exatas quando demonstráveis; relation targets não criam páginas ITEM artificiais; food/tempt/breeding continuam distintos; taming separa capability de estado; ecologia tem fonte/confiança; optional adapters são fail-soft; reload é atômico; editorial EXACT sem evidência é rejeitado; e todos os gates estão verdes antes do merge.
