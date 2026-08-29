# 10.07 — Loot, dieta, reprodução e ecologia

## Objetivo

Enriquecer entradas do Compêndio Natural com relações de gameplay verificáveis — loot, alimentação, atração, reprodução, domesticação e ecologia — sem executar efeitos de gameplay para produzir documentação e sem promover inferências a fatos confirmados.

## Contrato implementado

- `CompendiumRelationTarget` permite alvos tipados `ENTRY`, `ITEM`, `ITEM_TAG`, `BLOCK` e `BLOCK_TAG`, preservando compatibilidade com relações legadas entre entradas e sem criar `CompendiumEntryKind.ITEM`.
- `CompendiumRelation` preserva `FactSource`/`FactConfidence` e exige evidência explícita para `CURATED_EDITORIAL + EXACT`.
- O schema v1 de relações aceita exatamente um formato de alvo: `to` legado para entry ou `target_kind + target` para alvo tipado.
- Loot de entidades é analisado estruturalmente a partir de `data/<namespace>/loot_table/entities/...`; o Compêndio não rola `LootTable`, não cria entidades e não executa commands/functions para descobrir drops.
- `LootSummary`, `LootEntrySummary`, `LootNumberSummary` e `LootConditionSummary` distinguem valores exatos, ranges verificáveis e aspectos condicionais. Condição/função não suportada nunca vira chance inventada.
- `CompendiumLootProvider`/`CompendiumLootEnricher` produzem relações `DROPS` e seção de loot apenas com item, quantidade e chance que possam ser demonstrados pelo dado carregado.
- `FoodRelationProvider`, `TemptationRelationProvider` e `BreedingRelationProvider` mantêm alimento, atração e reprodução como semânticas independentes; `isFood` não promove automaticamente um item a tempt/breeding.
- `TamingFacts` separa capability de espécie de estado contextual de instância. `RuntimeEntityEcologyInspector` lê apenas uma entidade já existente e não mantém cache global de `Entity`/`Level`.
- `EcologyRelationProvider` publica relações ecológicas somente com fonte/confiança válidas; integrações opcionais degradam de forma fail-soft.
- `CompendiumLootSnapshot` é imutável. `CompendiumLootResourceReloader` faz staging completo no reload e `RuntimeCompendiumLootCatalog` publica atomicamente o snapshot novo; falha de staging preserva o snapshot válido anterior.
- `Compendium Ecology CI` executa os testes puros e validators de runtime/reload em paralelo aos gates já existentes.

## Checklist de fechamento

- [x] loot simples com item fixo é sumarizado sem executar a tabela;
- [x] quantidade fixa/range é representada somente quando resolvível;
- [x] contexto de player kill/Looting e condições não suportadas permanece explícito/condicional;
- [x] tabelas vazias/indisponíveis não geram drops inventados;
- [x] item/tag pode ser alvo de relação sem virar página artificial do Compêndio;
- [x] alimento, atração e reprodução permanecem distintos;
- [x] reprodução pode usar item/tag e requisitos verificáveis;
- [x] domesticação separa capability global de tame/owner/adult state contextual;
- [x] relações ecológicas carregam fonte/confiança e editorial `EXACT` sem evidência é rejeitado;
- [x] adapter ecológico opcional possui comportamento presença/ausência fail-soft;
- [x] reload altera o snapshot de loot atomicamente e falha mantém o anterior;
- [x] runtime não usa spawn, loot rolling, reflection arbitrária, NBT não documentado ou cache estático de entidade/world;
- [x] `Compendium Ecology CI` e o CI completo NeoForge passaram no head final e após o merge na `main`.

## Fronteiras deliberadas

Habitat, biome, estrutura e dimensão permanecem no 10.08. O wiring completo de APIs públicas de TFC, Animal Husbandry, Animal Wellness e outros mods opcionais permanece no 10.11, usando os contratos fail-soft definidos aqui. O gerenciador global unificado de save/rede/cache/reload permanece no 10.13. O 10.07 implementa somente o snapshot/cache de loot necessário ao próprio acceptance.

XP vanilla dinâmico não é apresentado como inteiro global exato quando depende da instância/contexto, e recompensas do RPG não são misturadas com esse valor.

## Evidência TDD

- Draft de desenvolvimento/TDD: PR #92.
- Ecology #73 / `33223780280`: RED esperado porque `CompendiumLootEnricher` ainda não existia.
- Ecology #79 / `33224214237`: RED esperado porque a página ENTITY ainda não aplicava o snapshot corrente de loot.
- Os gaps foram corrigidos sem enfraquecer os testes/validators.

## Evidência de integração

- PR funcional final: #99.
- Head pré-merge final: `7b117a3b5e8965ccc143c68b73eca4c558dcc5c6` contra `main@0fadd19eb0703a34c2ced396116b248f797b1f3e`.
- Gates pré-merge:
  - `33227491076` / Compendium Ecology #110 — GREEN;
  - `33227490917` / Compendium Flora #146 — GREEN;
  - `33227490925` / Compendium Entities #212 — GREEN;
  - `33227490903` / Compendium Discovery #289 — GREEN;
  - `33227490912` / RPG Skill Tree #1172 — GREEN completo, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.
- Merge funcional na `main`: `03403fc3f7934b0e2b2c9a5cd0a9e6606a2ba7d9`.
- Gates pós-merge:
  - `33228111273` / Compendium Ecology #111 — GREEN;
  - `33228111253` / Compendium Flora #149 — GREEN;
  - `33228111262` / Compendium Entities #215 — GREEN;
  - `33228111257` / Compendium Discovery #292 — GREEN;
  - `33228111266` / RPG Skill Tree #1175 — GREEN completo, incluindo Core/tests/validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final.

## Acceptance

**Acceptance: satisfied.** Loot, dieta, reprodução, domesticação e relações ecológicas enriquecem páginas através de dados/contratos verificáveis, sem execução perigosa de gameplay e sem apresentar inferências como fatos confirmados.