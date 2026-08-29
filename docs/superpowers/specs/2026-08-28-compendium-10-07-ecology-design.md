# Stage 10.07 — Compêndio: loot, dieta, reprodução e ecologia — Design

**Data:** 2026-08-28  
**Base de desenho:** `main@e474446bcc5d7ac92bb346ec2e4b71de178d9e26`  
**Plataforma:** Minecraft 1.21.1, NeoForge 21.1, Java 21  
**Escopo canônico:** `plans/10-compendio-natural/07-loot-dieta-reproducao-ecologia.md`

## Status do design

**Aprovado e congelado para esta implementação.** A execução preserva as fronteiras deste documento; evidências TDD/CI e o merge-ref final ficam no plano `docs/superpowers/plans/2026-08-28-compendium-10-07-ecology.md` e no PR de implementação.

## 1. Objetivo

Enriquecer entradas do Compêndio Natural com relações de gameplay verificáveis — loot, alimentação, atração, reprodução, domesticação e ecologia — sem executar efeitos de gameplay para produzir documentação e sem converter inferências em fatos confirmados.

O Stage 10.07 trabalha sobre o modelo canônico já fechado nos Stages 10.03–10.06. Ele não cria um recipe browser, não assume habitat/worldgen que pertence ao 10.08 e não absorve o wiring completo de APIs públicas de mods opcionais que pertence ao 10.11.

## 2. Decisão estrutural: alvo tipado de relação

O modelo possui relações como `DROPS`, `EATS`, `ATTRACTED_BY` e `BREEDS_WITH_ITEM`, mas itens e tags de itens não são páginas canônicas do Compêndio e não devem ser transformados artificialmente em `CompendiumEntryKind.ITEM`.

`CompendiumRelationTarget` usa kinds explícitos:

- `ENTRY` — outra entrada canônica do Compêndio;
- `ITEM` — `ResourceLocation` de item;
- `ITEM_TAG` — `ResourceLocation` de tag de item;
- `BLOCK` — `ResourceLocation` de bloco para interação ecológica concreta;
- `BLOCK_TAG` — `ResourceLocation` de tag de bloco.

`CompendiumRelation` armazena o alvo tipado e mantém construtor de compatibilidade com `CompendiumEntryId`. O alvo expõe serialização determinística usada por ordenação, diagnostics e schema.

Não existe `CompendiumEntryKind.ITEM` neste estágio.

## 3. Proveniência e confiança de relações

`FactSource` e `FactConfidence` continuam sendo a autoridade semântica. Relações editoriais exatas precisam provar de onde vieram através de `evidenceId`.

Regras:

- `CURATED_EDITORIAL + EXACT` exige `evidenceId` não vazio;
- fontes técnicas reproduzíveis como `REGISTRY`, `LOOT_TABLE`, `RUNTIME_ENTITY`, `DATAPACK` e `ADAPTER` podem omitir `evidenceId`;
- `UNAVAILABLE` é inválido para relações publicadas;
- relação sem evidência suficiente não é publicada como `EXACT`.

## 4. Compatibilidade de schema de relações

O schema global permanece em `schema_version: 1` porque a extensão é retrocompatível.

Formato legado válido:

```json
{
  "schema_version": 1,
  "type": "BELONGS_TO_DIMENSION",
  "from": "ENTITY|minecraft:pig",
  "to": "DIMENSION|minecraft:overworld",
  "source": "REGISTRY"
}
```

Novo alvo tipado:

```json
{
  "schema_version": 1,
  "type": "BREEDS_WITH_ITEM",
  "from": "ENTITY|minecraft:cow",
  "target_kind": "ITEM",
  "target": "minecraft:wheat",
  "source": "REGISTRY",
  "confidence": "EXACT"
}
```

O validator aceita exatamente um dos formatos: `to` legado para ENTRY, ou `target_kind + target`. Mistura dos formatos falha deterministically. `evidence_id` é opcional conforme a regra de proveniência.

## 5. Loot: análise estrutural sem execução

Loot de entidade é derivado dos recursos do datapack/reload. A implementação não executa `LootTable`, comandos, functions, spawning de entidade nem contexto sintético.

O parser suporta inicialmente o que pode ser resumido sem ambiguidade: item direto, `set_count` constante/uniforme, rolls constantes/uniformes simples e condições conhecidas relevantes a player kill/Looting quando deriváveis. Entry/function/number-provider/condition não suportado torna o aspecto `CONDICIONAL`; nunca vira um número inventado.

`CompendiumLootSnapshot` é imutável. `CompendiumLootEnricher` aplica o snapshot atual a uma página ENTITY sem reconstruir o catálogo-base, adicionando relações `DROPS` e seção `loot`. Item, quantidade/faixa e chance entram como facts somente quando resolvidos; condições/contextos permanecem `CONTEXTUAL`.

No Minecraft 1.21.1 os dados residem em `data/<namespace>/loot_table/...` (singular). O listener observa `loot_table/entities`, enquanto o ID lógico armazenado é `<namespace>:entities/<path>`.

## 6. XP de morte

O Compêndio distingue XP vanilla/base observável de recompensas do RPG. Valores dinâmicos de instância não são publicados como inteiro exato global. Adapter confiável pode posteriormente fornecer faixa/fórmula; indisponível/contextual permanece explicitamente assim.

## 7. Dieta, atração e reprodução

Esses conceitos são independentes mesmo quando usam o mesmo item.

- `FoodRelationProvider` — alimento verificável;
- `TemptationRelationProvider` — atração verificável;
- `BreedingRelationProvider` — capacidade/requisitos/reprodução verificável.

`Animal.isFood(ItemStack)` não prova por si só temptation nem reprodução. Nenhum provider auto-promove `EATS` para `ATTRACTED_BY` ou `BREEDS_WITH_ITEM`.

Gestação, sexo, genética e herança só entram por adapter confiável.

## 8. Domesticação e dados de instância

Domesticação separa capability estática da espécie do estado contextual da instância. Tame state, owner e adulto são inspecionados sobre entidade existente, não persistidos em cache global. Não há referência forte global a entity/world.

## 9. Ecologia de gameplay

`PREDATOR_OF`, `PREY_OF`, medo/evitação, polinização, hostilidade dirigida e interações com blocos/plantas só são publicadas com comportamento técnico ou fonte confiável. Habitat geográfico/bioma/estrutura/dimensão permanece no 10.08. Não há inferência por nome, translation key ou aparência.

## 10. Mods opcionais

O 10.07 fornece contrato fail-soft de enrichment opcional. Ausência/incompatibilidade do mod retorna ausência de contribution e preserva o catálogo base. O wiring completo de APIs externas — TFC, Animal Husbandry, Animal Wellness e equivalentes — permanece no 10.11, sem reflection arbitrária ou NBT não documentado.

## 11. Reload, cache e atomicidade

Summaries de loot são construídos em reload/snapshot, nunca por tick/frame.

Fluxo:

1. reload lê recursos para staging;
2. parser cria summaries/diagnostics;
3. validação conclui antes da publicação;
4. snapshot imutável novo substitui atomicamente o anterior;
5. falha mantém o snapshot anterior.

O Stage 10.13 continua responsável pelo gerenciador global de catálogo/rede/cache. Dados contextuais de instância não entram no cache global.

## 12. Erros e diagnostics

- loot malformado: staging falha antes da publicação;
- formato não suportado: aspecto condicional, sem execução;
- `CURATED_EDITORIAL + EXACT` sem evidência: rejeitado;
- target inválido: rejeitado;
- mod opcional ausente/incompatível: enrichment ausente;
- conflito de providers segue `ProviderMerger` determinístico.

## 13. Testes e gates

TDD cobre relation target/proveniência, loot simples/range/condicional, food/tempt/breeding separados, taming contextual, ecology/adapters opcionais, reload atômico e aplicação do snapshot atual à página.

O gate focal é `Compendium Ecology CI`. A integração exige também Flora, Entities, Discovery e `RPG Skill Tree CI`, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.

## 14. Critérios de aceite

O Stage 10.07 está pronto quando:

- loot é documentado por análise segura de dados;
- chance/quantidade só são exatas quando demonstráveis;
- item/tag pode ser alvo de relação sem virar entrada artificial;
- alimento, atração e reprodução são distintos;
- domesticação separa capability de estado contextual;
- ecologia possui fonte/confiança;
- adapters opcionais degradam com segurança;
- reload publica snapshot atomicamente;
- editorial `EXACT` sem evidência é rejeitado;
- gates focais e CI NeoForge completo estão verdes antes do merge.
