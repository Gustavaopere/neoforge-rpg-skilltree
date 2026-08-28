# 10.06 — Flora, árvores, fungos e cultivos

## Objetivo

Cobrir vegetação do modpack com uma camada técnica server-safe e fail-soft, incluindo flora simples, fungos, árvores, cultivos, plantas aquáticas e pontos de extensão para ecossistemas especializados, sem classificar blocos decorativos por aparência ou duplicar uma mesma espécie arbórea em várias páginas desconexas.

## Contrato implementado

- `FloraClassifier` classifica evidência estável coletada por adapters/runtime. A regra primária usa classes/tags/overrides explícitos; nome de arquivo e translation key não são heurística de classificação.
- `FloraKind` representa `FLORA`, `TREE_COMPONENT`, `CROP`, `FUNGUS`, `AQUATIC_FLORA` e `BLOCK_FEATURE` na camada editorial. No modelo persistido, fungo e flora aquática permanecem categorias editoriais de `CompendiumEntryKind.FLORA`, sem criar novos kinds canônicos.
- `FloraClassificationEvidence` e `FloraClassification` preservam diagnósticos, explicit override/ignore e estado ambíguo. Evidência decorativa insuficiente resulta em `UNKNOWN`, não em uma planta inventada.
- `FloraSpeciesFacts`, `FloraRegistryProvider` e `CropProvider` produzem páginas base de flora/cultivo somente com fatos verificáveis. Estágio máximo, seed, produce e tempo determinístico são registrados quando disponíveis; tempo de crescimento aleatório/desconhecido é omitido.
- `TreeSpeciesDescriptor`, `TreeComponent`, `TreeComponentRole` e `TreeProvider` representam uma árvore como uma única entrada `TREE` com componentes associados. Sapling, log, wood, stripped log e leaves não viram espécies independentes por padrão.
- Componentes idênticos são deduplicados de forma determinística; o mesmo ID atribuído a papéis incompatíveis é rejeitado em vez de escolhido silenciosamente.
- `FloraCatalogCoverage` separa cobertura esperada, IDs inesperados e entradas ambíguas. Ambiguidade é diagnóstico explícito e não é convertida artificialmente em cobertura válida.
- `RuntimeFloraCatalogCollector` enumera `BuiltInRegistries.BLOCK` e usa classes vanilla estáveis — incluindo `CropBlock`, `SaplingBlock`, `MushroomBlock`, `FungusBlock`, `KelpBlock`/famílias botânicas aplicáveis — e evidência de tags. O coletor não coloca blocos no mundo, não executa `randomTick`, não importa classes client-only e não usa translation key como classificador primário.
- Para saplings que não oferecem um agrupamento especializado, o fallback gera uma única espécie baseada no ID upstream do próprio sapling; ele não inventa relações com logs/leaves por sufixo de nome.
- `TfcFloraAdapter` e `DynamicTreesFloraAdapter` estabelecem contratos opcionais e fail-soft, sem hard dependency de classes externas. Ausência do mod ou metadata estável insuficiente resulta em `Optional.empty()`.
- `RuntimeCompendiumFloraCatalog` possui storage separado do catálogo `ENTITY`, valida o candidato antes de publicar e mantém publicação atômica. `CompendiumFloraCatalogEvents` publica no `ServerStartedEvent`, sem scan periódico por tick.
- `Compendium Flora CI` é o gate focal do subplano e roda em paralelo aos gates de Entities, Discovery e ao CI NeoForge completo.

## Checklist de fechamento

- [x] classificação botânica não depende de nome/translation key como regra principal;
- [x] bloco visualmente semelhante a planta permanece fail-closed sem evidência estável;
- [x] flor, fungo, flora aquática, cultivo e `BLOCK_FEATURE` possuem representação técnica sem criar kinds persistidos indevidos;
- [x] árvore é agrupada por espécie e seus componentes não viram espécies independentes por padrão;
- [x] árvore modded sem adapter especializado mantém fallback conservador, sem inferir componentes pelo nome;
- [x] cultivos expõem estágio/seed/produto/tempo somente quando tecnicamente verificáveis;
- [x] tempo de crescimento não determinístico ou desconhecido não é inventado;
- [x] classificação ambígua aparece separadamente no relatório de cobertura;
- [x] coletor runtime usa registry/classes/tags e não executa placement, random tick ou código client-only;
- [x] catálogo flora é separado do catálogo de entidades e publicado atomicamente no startup;
- [x] contratos TFC e Dynamic Trees degradam com segurança quando o mod ou metadata confiável estão ausentes;
- [x] presença/ausência de TFC e Dynamic Trees possui testes unitários fail-soft;
- [x] regressões possuem gate focal `Compendium Flora CI` e passam também no CI completo NeoForge/dedicated server.

## Fronteira deliberada com 10.11-F

O 10.06 fecha a cobertura genérica de flora e os **contratos opcionais** necessários para ecossistemas especializados. Ele não declara concluído o wiring direto das APIs públicas de Dynamic Trees ou TerraFirmaCraft.

A integração especializada de runtime permanece explicitamente em `10.11 — Integrações, adapters e extensibilidade`, seção **F — Integrações prioritárias**, onde deverá existir a matriz completa de presença/ausência/versões e, quando houver API pública estável, o mapeamento real de famílias/espécies Dynamic Trees e clima/agricultura/flora especializada do TFC. Até lá, versões não reconhecidas degradam para o catálogo genérico em vez de usar reflection, NBT arbitrário ou heurísticas de nome.

Biomas/habitat/worldgen, loot/dieta/reprodução/ecologia, apresentação 3D/UI e corpus editorial completo permanecem respectivamente nos subplanos 10.08, 10.07, 10.09 e 10.10. O 10.06 não transforma o Compêndio em recipe browser.

## Evidência TDD

- Draft de implementação/TDD: PR **#83**; head funcional final `fc066cb2b620873a52232582be0d31b63e77028e`.
- RED do classificador: `33218641706` / Compendium Flora #1 — falha esperada por tipos de produção ausentes.
- RED de flora/cultivo: `33218832373` / Compendium Flora #8 — falha esperada por providers/modelos ainda ausentes.
- RED de agrupamento de árvores: `33219016065` / Compendium Flora #14 — falha esperada por contratos de árvore ausentes.
- RED de cobertura: `33219270508` / Compendium Flora #20 — falha esperada por `FloraCatalogCoverage` ausente.
- RED dos adapters opcionais: `33219497700` / Compendium Flora #26 — falha esperada por contratos TFC/Dynamic Trees ausentes.
- RED de publicação: `33219634046` / Compendium Flora #30 — falha esperada antes do catálogo/evento runtime.

## Evidência de integração

- PR final não-draft: **#88**, criado no mesmo head funcional após o wrapper do conector falhar ao promover o draft #83 para ready-for-review.
- CI pré-merge do PR #88:
  - `33220770426` / Compendium Flora #34 — GREEN;
  - `33220770435` / Compendium Entities #98 — GREEN;
  - `33220770551` / Compendium Discovery #175 — GREEN;
  - `33220770442` / RPG Skill Tree #1058 — GREEN completo, incluindo NeoForge build, verificação do JAR e dedicated-server smoke.
- Merge funcional na `main`: `68f694e98c068f3274cd1ecb6bd7588951833fb5`.
- CI pós-merge na `main@68f694e98c068f3274cd1ecb6bd7588951833fb5`:
  - `33220942187` / Compendium Flora #35 — GREEN;
  - `33220942179` / Compendium Entities #101 — GREEN;
  - `33220942213` / Compendium Discovery #178 — GREEN;
  - `33220942238` / RPG Skill Tree #1061 — GREEN completo, incluindo todos os validators, NeoForge build, verificação do JAR, dedicated-server smoke, upload do JAR e publicação do status final do commit.

## Acceptance

**Acceptance: satisfied.** O catálogo representa flora, cultivos e árvores sem duplicação estrutural grosseira; classificação insuficiente ou ambígua permanece fail-closed/diagnosticada; o runtime é registry-first e server-safe; e ecossistemas especializados possuem contratos opcionais que degradam para o catálogo genérico até o wiring público específico do 10.11-F.