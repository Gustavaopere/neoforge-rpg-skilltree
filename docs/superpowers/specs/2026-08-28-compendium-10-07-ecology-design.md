# Stage 10.07 — Compêndio: loot, dieta, reprodução e ecologia — Design

**Data:** 2026-08-28  
**Base:** `main@e474446bcc5d7ac92bb346ec2e4b71de178d9e26`  
**Plataforma:** Minecraft 1.21.1, NeoForge 21.1, Java 21  
**Escopo canônico:** `plans/10-compendio-natural/07-loot-dieta-reproducao-ecologia.md`

## 1. Objetivo

Enriquecer entradas do Compêndio Natural com relações de gameplay verificáveis — loot, alimentação, atração, reprodução, domesticação e ecologia — sem executar efeitos de gameplay para produzir documentação e sem converter inferências em fatos confirmados.

O Stage 10.07 deve trabalhar sobre o modelo canônico já fechado nos Stages 10.03–10.06. Ele não cria um recipe browser, não assume habitat/worldgen que pertence ao 10.08 e não absorve o wiring completo de APIs públicas de mods opcionais que pertence ao 10.11.

## 2. Decisão estrutural: alvo tipado de relação

O modelo atual possui relações como `DROPS`, `EATS`, `ATTRACTED_BY` e `BREEDS_WITH_ITEM`, porém `CompendiumRelation` aceita apenas `CompendiumEntryId` como alvo. Itens e tags de itens não são páginas canônicas do Compêndio e não devem ser transformados artificialmente em `CompendiumEntryKind.ITEM`.

Será introduzido `CompendiumRelationTarget` com kinds explícitos:

- `ENTRY` — outra entrada canônica do Compêndio;
- `ITEM` — `ResourceLocation` de item;
- `ITEM_TAG` — `ResourceLocation` de tag de item;
- `BLOCK` — `ResourceLocation` de bloco quando uma interação ecológica aponta para um bloco concreto;
- `BLOCK_TAG` — `ResourceLocation` de tag de bloco.

`CompendiumRelation` passará a armazenar esse alvo tipado. Um construtor de compatibilidade continuará aceitando `CompendiumEntryId` e o converterá para `ENTRY`. O alvo exporá uma serialização determinística usada por ordenação, diagnostics e schemas.

Não será criado `CompendiumEntryKind.ITEM` neste estágio.

## 3. Proveniência e confiança de relações

`FactSource` e `FactConfidence` continuam sendo a autoridade semântica para a relação, mas relações editoriais exatas precisam provar de onde vieram. `CompendiumRelation` ganhará um identificador opcional de evidência (`evidenceId`).

Regra de validação:

- `CURATED_EDITORIAL + EXACT` exige `evidenceId` não vazio;
- fontes técnicas como `REGISTRY`, `LOOT_TABLE`, `RUNTIME_ENTITY`, `DATAPACK` e `ADAPTER` podem omitir `evidenceId` quando a própria fonte técnica é reproduzível;
- `UNAVAILABLE` continua inválido para relações publicadas;
- relação sem evidência suficiente não é publicada como `EXACT`.

Essa regra fecha o caso obrigatório de editorial marcado como exato sem fonte.

## 4. Compatibilidade de schema de relações

O schema global continua em `schema_version: 1` neste estágio porque a extensão será compatível com documentos existentes.

Documentos legados permanecem válidos:

```json
{
  "schema_version": 1,
  "type": "BELONGS_TO_DIMENSION",
  "from": "ENTITY|minecraft:pig",
  "to": "DIMENSION|minecraft:overworld",
  "source": "REGISTRY"
}
```

Novos alvos não-entry usam campos tipados:

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

O validator aceitará exatamente um dos formatos de alvo: `to` legado para `ENTRY`, ou `target_kind + target`. Mistura dos dois formatos falha de forma determinística. `evidence_id` será aceito quando aplicável.

## 5. Loot: análise estrutural sem execução

Loot de entidade será derivado de recursos de datapack/reload, não de execução de `LootTable`, comandos, functions, spawning de entidades ou construção de contexto sintético.

Componentes previstos:

- `CompendiumLootProvider` — transforma um summary já resolvido em facts/relações;
- `LootSummary` — snapshot imutável da tabela de uma entidade;
- `LootEntrySummary` — item, quantidade resolvível e chance resolvível quando possível;
- `LootConditionSummary` — condições conhecidas/condicionais;
- `CompendiumLootResourceReloader` — lê recursos de loot no reload e publica um snapshot imutável somente depois de validação completa.

O parser suportará inicialmente os formatos que podem ser resumidos sem ambiguidade: item direto, `set_count` constante/uniforme, rolls constantes/uniformes simples e condições conhecidas relevantes a player kill/Looting quando matematicamente deriváveis. Qualquer função, entry type, number provider ou condição não suportada torna o aspecto correspondente `CONDICIONAL`; nunca será substituído por uma chance inventada.

Tabelas ausentes ou vazias resultam em ausência clara de loot documentável, não em erro de runtime.

## 6. XP de morte

O Compêndio distinguirá XP vanilla/base observável de recompensas do RPG. Valores calculados dinamicamente por instância não serão publicados como um inteiro exato global.

O modelo de summary poderá representar:

- valor exato quando tecnicamente estável;
- faixa/fórmula quando um adapter confiável fornece esse contrato;
- indisponível/contextual quando depende da instância.

Recompensas do RPG não serão misturadas com o XP vanilla da entidade.

## 7. Dieta, atração e reprodução

Esses conceitos permanecem separados mesmo quando usam o mesmo item.

Providers previstos:

- `FoodRelationProvider` — itens/tags aceitos como alimento quando esse contrato é verificável;
- `TemptationRelationProvider` — itens/tags que realmente dirigem comportamento de atração;
- `BreedingRelationProvider` — capacidade, requisitos, item/tag, adulto/cooldown e resultado quando verificáveis.

`Animal.isFood(ItemStack)` é evidência de alimento, mas não prova sozinho que o item é um tempt item nem que inicia reprodução em todos os casos. Nenhum provider promoverá automaticamente `EATS` para `ATTRACTED_BY` ou `BREEDS_WITH_ITEM`.

Dados de gestação, sexo, genética, herança e sistemas complexos só entram por adapter confiável. O contrato genérico deve degradar para os dados vanilla/estáveis disponíveis.

## 8. Domesticação e dados de instância

Domesticação terá duas camadas:

- capability estática da espécie: domesticável ou não, método/item quando verificável;
- inspeção contextual de instância: tame state, owner e estados atuais permitidos.

A camada global nunca armazenará referência forte a entidade/world. Dados de owner ou estado atual são produzidos apenas sob inspeção de instância e não entram no snapshot global de catálogo.

## 9. Ecologia de gameplay

Relações ecológicas elegíveis neste estágio incluem `PREDATOR_OF`, `PREY_OF`, medo/evitação, polinização, hostilidade dirigida, interação com plantas/blocos e associação a ninho/colmeia quando há comportamento técnico ou fonte confiável.

Habitat geográfico, bioma, estrutura e dimensão permanecem no 10.08. Relações ecológicas não serão inferidas por nome da entidade, nome do item, translation key ou aparência.

## 10. Mods opcionais

O 10.07 define contratos fail-soft para sistemas de husbandry/agro presentes no pack, mas o wiring completo de APIs públicas externas permanece no 10.11.

Neste estágio haverá pelo menos um contrato de adapter ecológico opcional com testes de presença/ausência. A ausência do mod nunca pode resolver classes externas. Versão/API não reconhecida retorna ausência de enrichment e mantém o provider genérico.

TFC, Animal Husbandry, Animal Wellness e sistemas equivalentes só recebem dados especializados quando uma API pública/estável ou um contrato documentado permitir. Nenhuma integração será simulada por reflection arbitrária ou leitura de NBT não documentado.

## 11. Reload, cache e atomicidade

Summaries de loot são construídos em reload/snapshot, nunca por tick/frame.

Fluxo:

1. o reload lê recursos para staging;
2. parser gera summaries e diagnostics;
3. validação garante que nenhum snapshot parcial será publicado;
4. um novo snapshot imutável substitui atomicamente o anterior;
5. falha mantém o snapshot válido anterior.

O Stage 10.07 implementa somente o cache específico de loot/ecologia necessário ao seu acceptance. O gerenciador global unificado de catálogo/rede/cache continua pertencendo ao 10.13.

Dados contextuais de instância nunca entram nesse cache global.

## 12. Erros e diagnostics

O sistema é fail-closed/fail-soft conforme o caso:

- recurso de loot malformado: diagnóstico com resource id e caminho do campo; snapshot anterior permanece;
- formato de loot não suportado: summary parcial marcado como condicional, sem executar o conteúdo;
- relação editorial `EXACT` sem evidência: rejeitada;
- target de relação inválido: rejeitado antes da publicação;
- mod opcional ausente/incompatível: enrichment ausente, catálogo base preservado;
- conflito de providers continua seguindo `ProviderMerger` e sua precedência determinística.

## 13. Testes e gates

TDD será obrigatório. Os ciclos mínimos serão:

1. relation target + proveniência editorial;
2. loot summary simples/range/condicional;
3. food/tempt/breeding separados;
4. domesticação + dados contextuais sem cache global;
5. ecology relations + optional husbandry presence matrix;
6. reload/atomic snapshot.

Casos obrigatórios do plano serão cobertos explicitamente: item fixo, range, Looting/player kill, condição não suportada, animal vanilla com alimento/reprodução, entidade domesticável, mod opcional presente/ausente, reload alterando loot e editorial `EXACT` sem fonte.

Será criado gate focal `Compendium Ecology CI`, e a entrega só poderá ser integrada após esse gate e o `RPG Skill Tree CI` completo passarem, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.

## 14. Critérios de aceite

O Stage 10.07 está pronto para fechamento quando:

- loot é documentado por análise segura de dados, sem executar efeitos;
- chance/quantidade só são exatas quando matematicamente demonstráveis;
- itens/tags podem ser alvos de relações sem virar entradas artificiais do Compêndio;
- alimento, atração e reprodução permanecem semanticamente distintos;
- domesticação separa capability global de estado contextual da instância;
- relações ecológicas possuem fonte/confiança e não dependem de heurísticas frágeis;
- adapters opcionais degradam com segurança;
- reload substitui snapshots atomicamente e invalida os dados dependentes;
- editorial `EXACT` sem evidência é rejeitado;
- testes focais e CI NeoForge completo estão verdes antes do merge.