# 10.02 — Inventário do modpack e cobertura de conteúdo

## Objetivo

Transformar a modlist atual e os registries do jogo em um inventário **exaustivo e reproduzível** de conteúdo que o Compêndio deve cobrir.

O objetivo não é manter manualmente uma lista frágil de centenas de mods. O projeto deve conseguir responder, para o pack realmente carregado:

> Quais entidades, plantas, árvores, cultivos, biomas, estruturas e dimensões existem, de qual namespace vêm e qual é o status enciclopédico de cada uma?

## Fonte de verdade

Snapshot conhecido na data deste plano:

- arquivo: `modlist agora atual.txt`;
- data conhecida: 2026-08-26;
- 553 entradas top-level incluindo o loader;
- entradas internas em `META-INF/jarjar/` e `META-INF/jars/` são dependências embarcadas, não mods independentes.

No começo da implementação, repetir a coleta. **A presença em runtime vence este snapshot.**

## Plano

### A — Extrair a modlist top-level

Criar ferramentas previstas:

```text
scripts/compendium/inventory_modlist.py
scripts/compendium/inventory_runtime_report.py
generated/compendium/modpack-inventory.json
generated/compendium/modpack-inventory.md
```

- [ ] ler a modlist canônica fornecida ao projeto;
- [ ] normalizar filename, mod id, versão publicada e versão runtime quando disponíveis;
- [ ] ignorar JAR-in-JAR como item top-level;
- [ ] registrar dependências sem promovê-las automaticamente a conteúdo editorial;
- [ ] produzir hash do snapshot para detectar drift.

### B — Enumerar registries relevantes em runtime

Criar um coletor de desenvolvimento/datagen que enumere pelo menos:

- `ENTITY_TYPE`;
- `BLOCK` e itens associados quando classificados como flora/cultivo;
- `BIOME`;
- `STRUCTURE`;
- dimensões/`LEVEL_STEM` ou contrato equivalente seguro para 1.21.1;
- configured/placed features somente quando necessárias para resolver árvores/flora sem depender de internals instáveis.

Saída mínima por registro:

```text
kind
resource_location
namespace
translation_key
mod_display_name
registry_source
present_at_runtime
```

### C — Classificar cobertura

Cada entrada deve receber exatamente um estado editorial:

- `AUTO` — página funcional derivada de registries/runtime;
- `CURATED` — página automática enriquecida por corpus pt-BR próprio;
- `ADAPTER` — exige provider específico para dados que o registry genérico não expõe;
- `IGNORED` — não faz sentido enciclopédico; exige motivo explícito;
- `ERROR` — deveria ser coberta, mas falhou; bloqueia gate de conteúdo.

Nenhuma entrada pode ficar sem estado.

### D — Grupos prioritários do pack

O scanner é a autoridade, mas o plano deve verificar explicitamente famílias de conteúdo já recorrentes no pack. A lista abaixo é uma **fila de auditoria**, não uma declaração eterna de presença; cada namespace deve ser confirmado no snapshot/runtime atual antes de receber suporte nominal.

#### Fauna, criaturas e bosses

- conteúdo vanilla;
- Alex's Mobs / ecossistema Alex quando presente;
- Alex's Caves;
- Aquaculture 2;
- Cataclysm;
- Illage and Spillage / Respillaged;
- TerraFirmaCraft e addons de fauna;
- conteúdos de dimensões como Aether, Blue Skies, BetterEnd/BetterNether, Deeper and Darker e equivalentes presentes;
- mobs de mods de worldgen/aventura presentes no pack;
- entidades utilitárias ou NPCs com valor enciclopédico, mantendo MineColonies/Structurize separados de fauna biológica.

#### Flora, árvores e cultivos

- vanilla;
- TerraFirmaCraft;
- Biomes O' Plenty;
- Nature's Spirit;
- Oh The Biomes We've Gone, se presente no snapshot atual;
- Dynamic Trees e addons compatíveis;
- Wilder Wild;
- Farmer's Delight e ecossistema agrícola;
- flora de dimensões e cavernas;
- árvores/plantas adicionadas por outros mods detectados pelo scanner.

#### Biomas, cavernas e worldgen

- biomas vanilla;
- YUNG's Cave Biomes — explicitamente conhecido no snapshot atual;
- TFC/worldgen compatível;
- Biomes O' Plenty/Nature's Spirit/BYG quando presentes;
- Alex's Caves;
- Tectonic e outros modificadores de terreno somente no que forem capazes de fornecer informação enciclopédica estável;
- biomas dimensionais.

#### Estruturas

- estruturas vanilla;
- YUNG's Better Mineshafts — explicitamente conhecido no snapshot atual;
- demais YUNG's presentes no snapshot efetivo;
- Integrated Structures/IDAS quando presentes;
- Structory;
- Stoneworks Structures;
- Stellarity e estruturas dimensionais;
- MineColonies/Structurize apenas quando uma estrutura for explorável/registrável de maneira semanticamente útil;
- estruturas de bosses/mods de aventura;
- qualquer `Structure` registrada por namespace não listado acima.

### E — Relatório de cobertura por mod

Gerar tabela semelhante a:

```text
namespace | mod | entities | flora | trees | crops | biomes | structures | dimensions | AUTO | CURATED | ADAPTER | IGNORED | ERROR
```

Também gerar listas detalhadas por tipo e por namespace.

### F — Drift do modpack

- [ ] detectar mod removido desde o último snapshot;
- [ ] detectar mod adicionado;
- [ ] detectar ID novo/removido dentro de namespace existente;
- [ ] não apagar silenciosamente progresso de jogador por causa de remoção temporária de mod;
- [ ] marcar conteúdo ausente como legado/orphan em save até a política de migração decidir seu destino.

## Testes previstos

```text
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/ModpackInventoryTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/CoverageClassifierTest.java
src/test/java/dev/gustavopere/rpgskilltree/compendium/catalog/RegistryInventoryTest.java
```

Casos obrigatórios:

- [ ] namespace vanilla é enumerado;
- [ ] mod opcional presente aparece;
- [ ] mod opcional ausente não quebra startup;
- [ ] JAR embarcado não é contado como top-level;
- [ ] entrada desconhecida recebe `AUTO`, `ADAPTER` ou `ERROR`, nunca desaparece;
- [ ] `IGNORED` sem motivo falha validação;
- [ ] drift entre snapshots é relatado.

## Acceptance

O subplano fecha quando uma execução reproduzível consegue gerar o inventário integral do pack carregado e produzir cobertura explícita para cada entrada enciclopédica suportada.
