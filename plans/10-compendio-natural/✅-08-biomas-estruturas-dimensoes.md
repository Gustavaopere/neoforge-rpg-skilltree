# 10.08 — Biomas, estruturas e dimensões

## Objetivo

Criar a camada geográfica server-authoritative do Compêndio, permitindo navegar entre biomas, estruturas e dimensões a partir de identidades reais do runtime e relações que possam ser comprovadas pelo estado do servidor.

## Contrato implementado

- `BiomeDescriptor`, `BiomeClimateFacts` e `BiomeProvider` materializam entradas `BIOME` por registry ID canônico, preservando namespace/mod e valores de clima vanilla como fatos de clima vanilla, sem reinterpretá-los como temperatura física/TFC.
- `StructureDescriptor`, `StructurePlacementSummary` e `StructureProvider` materializam entradas `STRUCTURE` por registry ID canônico; placement/frequência permanecem ausentes quando não são estruturalmente verificáveis.
- `DimensionDescriptor` e `DimensionProvider` materializam entradas `DIMENSION` a partir das dimensões realmente carregadas no servidor.
- `RuntimeWorldCatalogCollector` lê BIOME e STRUCTURE dos dynamic registries do servidor, dimensões dos `ServerLevel` reais e os biomas possíveis de cada dimensão pelo `BiomeSource` efetivo.
- As relações `Dimension ↔ Biome` vêm do `BiomeSource.possibleBiomes()`; `Structure ↔ Biome` vem de `Structure.biomes()`; `Structure ↔ Dimension` só é derivada quando a interseção de biomas prova participação possível naquela dimensão.
- `WorldCatalogSnapshot` é imutável e `WorldCatalogCoverage` impede a publicação de candidato incompleto; `RuntimeCompendiumWorldCatalog` substitui o snapshot somente depois de validar cobertura completa.
- `CompendiumWorldCatalogReloader` recompõe o catálogo após datapack reload usando o mesmo caminho de publicação validada.
- Descoberta de dimensão ocorre por estado observado no login/mudança de dimensão; descoberta de bioma é server-observed no login, mudança de dimensão e amostragem periódica.
- Descoberta de estrutura usa `StructureManager` do servidor para confirmar `StructureStart`/piece na posição do jogador antes de criar o sinal; não aceita identidade de estrutura fornecida pelo cliente.
- A amostragem de estrutura é limitada a uma consulta a cada 100 ticks por jogador, sem varrer o registry inteiro a cada tick.
- Suites opcionais de worldgen não são hard dependencies. Conteúdo de YUNG, Integrated Structures/IDAS, Structory, Cataclysm, MineColonies/Structurize ou outros namespaces carregados entra pelo contrato genérico de registry; adapters específicos só permanecem necessários quando fatos editoriais futuros não forem deriváveis genericamente.
- Substituição/override sob o mesmo registry ID continua sendo uma única identidade do Compêndio; o runtime não inventa página paralela baseada em nome de mod ou tradução.
- Coordenadas, chance exata, spacing/separation interpretados editorialmente e receitas/rituais de portal não são inferidos quando a fonte runtime não prova esses fatos.

## Checklist de fechamento

- [x] biomas vanilla e modded são catalogados por registry ID canônico;
- [x] clima vanilla é mantido semanticamente separado de sistemas físicos/modded como TFC;
- [x] estruturas vanilla e modded são catalogadas sem hard dependency de suites opcionais;
- [x] override sob o mesmo registry ID não cria identidade duplicada;
- [x] dimensões presentes são obtidas dos níveis reais do servidor;
- [x] `Dimension ↔ Biome` e `Structure ↔ Biome` são derivados de fontes runtime verificáveis;
- [x] `Structure ↔ Dimension` só é emitido quando há evidência por interseção de biomas;
- [x] snapshot geográfico é imutável, validado por cobertura e publicado atomicamente;
- [x] datapack reload recompõe o catálogo antes de publicar o novo snapshot;
- [x] biome e dimension discovery são server-observed e monotônicos pelo runtime de descoberta já existente;
- [x] structure discovery exige confirmação do servidor na posição do jogador;
- [x] estrutura forjada/solicitada sem confirmação não é aceita pela policy;
- [x] verificação de estrutura é bounded e não faz full-registry scan por tick;
- [x] World CI, Discovery, Entities, Ecology, Flora, Foundation e CI integral passaram no HEAD final mergeável;
- [x] implementação foi integrada na `main` pelo PR #110 com SHA de merge `c980f7835a01ef038e34d1ea0fab66d33e8bb03c`;
- [x] todos os workflows pós-merge do push de `main@c980f7835a01ef038e34d1ea0fab66d33e8bb03c` fecharam GREEN.

## Limites deliberados deste fechamento

Tempo mínimo configurável para teleporte transitório e granularidade configurável da localização da primeira descoberta não foram transformados em requisito do contrato 10.08. O runtime atual registra origem por dimensão e chunk e mantém discovery monotônico. Enriquecimento editorial específico de mods, relações de acesso/portal e adapters especiais continuam pertencendo aos estágios posteriores do Compêndio quando dados genéricos não bastarem.

## Evidência de implementação

Plano de execução: `docs/superpowers/plans/2026-08-28-compendium-10-08-world.md`.

HEAD final da implementação antes do merge: `25aa717ccff6e31dbb774b40f701b642871b2808`.

Gates finais pré-merge desse HEAD:

- Compendium Discovery CI `33229975397` / run #348 — GREEN;
- Compendium Entities CI `33229975446` / run #271 — GREEN;
- Compendium Ecology CI `33229975376` / run #232 — GREEN;
- Compendium World CI `33229975417` / run #15 — GREEN;
- Compendium Flora CI `33229975374` / run #205 — GREEN;
- Foundation Bootstrap Contract `33229975477` / run #9 — GREEN;
- RPG Skill Tree CI `33229975377` / run #1231 — GREEN, incluindo Core, JUnit 5, NeoForge GameTests, validators, NeoForge build, verificação do JAR e dedicated-server smoke.

## Evidência de integração

O draft original #107 preservava exatamente o mesmo HEAD de implementação, mas a operação de transição `Ready for review` do conector falhou por incompatibilidade GraphQL com o campo obsoleto `Repository.fullDatabaseId`. O #107 foi fechado sem merge e o mesmo branch/HEAD foi reaberto como PR não-draft #110; nenhum código foi descartado ou alterado nesse contorno operacional.

PR #110 foi mergeado em `main` como `c980f7835a01ef038e34d1ea0fab66d33e8bb03c`.

Gates pós-merge do push de `main@c980f7835a01ef038e34d1ea0fab66d33e8bb03c`:

- Compendium Flora CI `33230100328` / run #215 — GREEN;
- Compendium Entities CI `33230100330` / run #281 — GREEN;
- Foundation Bootstrap Contract `33230100337` / run #19 — GREEN;
- Compendium Ecology CI `33230100355` / run #254 — GREEN;
- Compendium Discovery CI `33230100371` / run #358 — GREEN;
- Compendium World CI `33230100386` / run #16 — GREEN;
- RPG Skill Tree CI `33230100358` / run #1241 — GREEN, incluindo NeoForge GameTests, build, JAR e dedicated-server smoke.

## Acceptance

**Acceptance: satisfied.** Biomas, estruturas e dimensões registrados produzem entradas do Compêndio com identidade canônica e relações geográficas verificáveis a partir do runtime do servidor; discovery geográfica relevante é server-authoritative; reload preserva publicação validada; e nenhuma suite opcional de worldgen é hard dependency.