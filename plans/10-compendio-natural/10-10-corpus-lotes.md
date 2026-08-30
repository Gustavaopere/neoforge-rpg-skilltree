# 10.10 — Registro de lotes do corpus editorial pt-BR

Este arquivo acompanha a produção incremental do corpus editorial real previsto em `10-ptbr-corpus-editorial.md`. Ele não substitui o plano canônico; registra somente lotes efetivamente escritos, revisados e validados.

## Regras de fechamento de lote

Um lote só pode ser tratado como concluído quando:

- todas as entradas usam `language: pt_br`;
- `entry_id`, `namespace`, `kind` e `availability` correspondem ao conteúdo real em runtime;
- todo resumo e toda seção textual possuem fonte explícita;
- textos não congelam em prosa valores mecânicos que podem mudar por configuração/runtime;
- não existem `TODO`, `TBD`, `FIXME` ou placeholders;
- as entradas estão marcadas como `REVIEWED` somente após revisão factual e linguística;
- o CI editorial e o CI agregado do mod passam no HEAD final.

## Lote 1 — Vanilla / criação e companhia

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:bee` — Abelha
2. `ENTITY:minecraft:cat` — Gato
3. `ENTITY:minecraft:chicken` — Galinha
4. `ENTITY:minecraft:cow` — Vaca
5. `ENTITY:minecraft:goat` — Cabra
6. `ENTITY:minecraft:horse` — Cavalo
7. `ENTITY:minecraft:pig` — Porco
8. `ENTITY:minecraft:rabbit` — Coelho
9. `ENTITY:minecraft:sheep` — Ovelha
10. `ENTITY:minecraft:wolf` — Lobo

## Lote 2 — Vanilla / fauna e montarias complementares

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch2.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:axolotl` — Axolote
2. `ENTITY:minecraft:camel` — Camelo
3. `ENTITY:minecraft:donkey` — Burro
4. `ENTITY:minecraft:fox` — Raposa
5. `ENTITY:minecraft:frog` — Sapo
6. `ENTITY:minecraft:llama` — Lhama
7. `ENTITY:minecraft:mooshroom` — Coguvaca
8. `ENTITY:minecraft:ocelot` — Jaguatirica
9. `ENTITY:minecraft:parrot` — Papagaio
10. `ENTITY:minecraft:turtle` — Tartaruga

### Critérios editoriais aplicados ao lote 2

- fontes primárias `RUNTIME` e `OFFICIAL_CODE` por entrada;
- sem probabilidades, dano, vida, velocidade, cooldown ou capacidades numéricas hardcoded em prosa;
- distinção explícita entre domesticação, confiança e simples uso de sela quando as mecânicas diferem;
- variantes descritas sem transformar regras de worldgen/configuração em texto universal;
- descrições redigidas em pt-BR e separadas dos fatos técnicos/providers do Compêndio.

## Lote 3 — Vanilla / fauna ambiente e aquática

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch3.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:armadillo` — Tatu
2. `ENTITY:minecraft:bat` — Morcego
3. `ENTITY:minecraft:cod` — Bacalhau
4. `ENTITY:minecraft:dolphin` — Golfinho
5. `ENTITY:minecraft:glow_squid` — Lula-brilhante
6. `ENTITY:minecraft:panda` — Panda
7. `ENTITY:minecraft:polar_bear` — Urso-polar
8. `ENTITY:minecraft:pufferfish` — Baiacu
9. `ENTITY:minecraft:salmon` — Salmão
10. `ENTITY:minecraft:squid` — Lula

### Critérios editoriais aplicados ao lote 3

- identidade de todas as dez entidades confirmada no registry de Minecraft 1.21.1;
- comportamento descrito a partir do runtime e das classes oficiais correspondentes;
- sem stats, probabilidades, durações, alcances, cooldowns ou limites de grupo hardcoded em prosa;
- heranças relevantes preservadas quando informativas, como `AbstractSchoolingFish`, `Bucketable` e `Squid` → `GlowSquid`;
- relações explícitas Lula ↔ Lula-brilhante permanecem referências editoriais, sem criar fatos técnicos paralelos;
- texto em pt-BR revisado para não confundir comportamento neutro, passivo, defesa, domesticação ou interação.

## Lote 4 — Vanilla / criaturas especiais e utilitárias

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch4.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:allay` — Allay
2. `ENTITY:minecraft:iron_golem` — Golem de Ferro
3. `ENTITY:minecraft:mule` — Mula
4. `ENTITY:minecraft:skeleton_horse` — Cavalo Esqueleto
5. `ENTITY:minecraft:sniffer` — Farejador
6. `ENTITY:minecraft:snow_golem` — Golem de Neve
7. `ENTITY:minecraft:strider` — Andarilho
8. `ENTITY:minecraft:tadpole` — Girino
9. `ENTITY:minecraft:trader_llama` — Lhama do Comerciante
10. `ENTITY:minecraft:tropical_fish` — Peixe Tropical

### Critérios editoriais aplicados ao lote 4

- identidade das dez entidades confirmada para Minecraft 1.21.1 e cada ficha ancorada em `RUNTIME` e `OFFICIAL_CODE`;
- comportamentos especiais descritos pela implementação real, incluindo coleta do Allay, escavação do Farejador, estados dos golems, armadilha do Cavalo Esqueleto e vínculo da Lhama do Comerciante;
- heranças e contratos relevantes preservados sem duplicar fatos técnicos, incluindo `AbstractHorse`, `AbstractChestedHorse`, `AbstractSchoolingFish`, `Bucketable`, `Saddleable`, `ItemSteerable`, `Shearable` e `RangedAttackMob`;
- nenhuma duração, chance, dano, cura, velocidade, capacidade de inventário ou outro parâmetro mutável foi congelado em prosa;
- diferenças entre criatura amigável, animal, construto, montaria e comportamento defensivo foram mantidas sem inferir classificação pelo nome do ID;
- conteúdo validado pelo CI editorial e pelo CI agregado, incluindo carregamento do pacote, JUnit, GameTests, build, JAR e dedicated-server smoke.

## Lote 5 — Vanilla / hostis comuns e variantes

Arquivo: `src/main/resources/data/rpgskilltree/compendium/editorial/pt_br/minecraft/entities-batch5.json`

Estado: `REVIEWED`

Entradas:

1. `ENTITY:minecraft:bogged` — Pantanoso
2. `ENTITY:minecraft:cave_spider` — Aranha das Cavernas
3. `ENTITY:minecraft:creeper` — Creeper
4. `ENTITY:minecraft:drowned` — Afogado
5. `ENTITY:minecraft:husk` — Zumbi-Múmia
6. `ENTITY:minecraft:skeleton` — Esqueleto
7. `ENTITY:minecraft:spider` — Aranha
8. `ENTITY:minecraft:stray` — Errante
9. `ENTITY:minecraft:witch` — Bruxa
10. `ENTITY:minecraft:zombie` — Zumbi

### Critérios editoriais aplicados ao lote 5

- identidade das dez entidades confirmada no registry de Minecraft 1.21.1 e ancorada em `RUNTIME` e `OFFICIAL_CODE`;
- relações de herança relevantes preservadas, incluindo `Bogged`/`Skeleton`/`Stray` via `AbstractSkeleton`, `CaveSpider` via `Spider` e `Drowned`/`Husk` via `Zombie`;
- comportamentos próprios descritos a partir das classes vanilla, incluindo explosão/carga do Creeper, mobilidade aquática do Afogado, poções da Bruxa, veneno da Aranha das Cavernas e corte do Pantanoso;
- nenhuma chance, dano, duração de efeito, alcance, cadência, raio de explosão ou outro parâmetro mecânico mutável foi congelado em prosa;
- variantes com registry ID próprio permanecem entradas independentes, sem duplicar a ficha da classe-base;
- o pacote passou pelo CI editorial e pelo CI agregado antes deste registro, incluindo JUnit, GameTests, validadores do Compêndio, build, JAR e dedicated-server smoke.

## Estado acumulado

- entidades vanilla reais no corpus: **50**;
- lotes documentados: **5**;
- namespace atual em produção: `minecraft`;
- o Stage 10.10 permanece aberto até a cobertura editorial exigida pela modlist e os demais gates do plano canônico serem concluídos.
