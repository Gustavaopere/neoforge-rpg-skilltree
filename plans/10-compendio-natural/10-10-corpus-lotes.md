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

Estado: `REVIEWED` no conteúdo; fechamento definitivo depende do CI do PR do lote.

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

## Estado acumulado

- entidades vanilla reais no corpus: **20**;
- lotes documentados: **2**;
- namespace atual em produção: `minecraft`;
- o Stage 10.10 permanece aberto até a cobertura editorial exigida pela modlist e os demais gates do plano canônico serem concluídos.
