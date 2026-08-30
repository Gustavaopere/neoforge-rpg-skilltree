# 13.02 — Taxonomia de biomas e tipos de região

## Objetivo

Converter centenas de registry IDs de biomas vanilla/modded em uma taxonomia semântica estável adequada a mapa, quests e Compêndio, sem hardcode exaustivo por mod.

## RegionType inicial

O datapack deve poder definir famílias como:

- `FLORESTA`;
- `FLORESTA_ESCURA`;
- `SELVA`;
- `DESERTO`;
- `SAVANA`;
- `PLANICIE`;
- `PANTANO`;
- `TUNDRA`;
- `TAIGA`;
- `MONTANHA`;
- `COSTA`;
- `OCEANO`;
- `RIO`;
- `BADLANDS`;
- `SUBTERRANEO`;
- `VULCANICO`;
- `CORROMPIDO`;
- `ARCANO`;
- `NETHER`;
- `END`;
- `OUTRO`.

A lista é extensível por dados e não deve virar enum fechado impossível de ampliar por datapack.

## Classificação

Ordem de evidência recomendada:

1. override explícito por registry ID;
2. tags de biome próprias/canônicas;
3. tags vanilla/NeoForge/modded confiáveis;
4. propriedades ambientais/climáticas quando úteis;
5. namespace/padrões conhecidos somente como heurística de baixa prioridade;
6. fallback `OUTRO`/categoria genérica sem crash.

Cada regra pode declarar prioridade, `include`, `exclude` e motivo/proveniência.

## Integração com Stage 10

O Stage 10 — Compêndio Natural deve ser a fonte preferencial para nomes, registry IDs conhecidos, mod de origem e metadados já catalogados. Stage 13 adiciona a camada espacial/semântica e não duplica o catálogo.

Se Stage 10 estiver incompleto, Stage 13 continua funcionando com registries/tags runtime e reconcilia depois.

## Fronteiras semânticas

Dois biomas diferentes podem pertencer à mesma região quando sua família semântica for compatível e forem espacialmente contíguos. Exemplo:

```text
minecraft:forest
mod:maple_forest
mod:old_growth_forest
→ família FLORESTA
→ mesma RegionInstance quando conectados e sem separador forte
```

Também deve ser possível marcar tipos incompatíveis/fortes que quebram região: grande rio, oceano, cordilheira, fronteira dimensional ou regra data-driven.

## Dados

Definir formato reloadable para:

- region types;
- regras de classificação;
- compatibilidade de merge entre tipos;
- peso de subtipos dominantes;
- nomes/localization keys;
- overrides de modpack.

`/reload` pode mudar a classificação de chunks ainda não materializados. Regiões persistidas exigem política de migração do 13.14; nunca devem trocar nome/ID silenciosamente no meio do save.

## PT-BR

Nenhum registry ID cru deve ser a apresentação normal ao jogador. Fallbacks devem usar rótulos localizados como `Região desconhecida` e conservar o ID técnico apenas no modo avançado/admin.

## Acceptance

- biomas vanilla recebem categorias previsíveis;
- bioma modded desconhecido não causa erro;
- overrides vencem heurísticas;
- reload é determinístico;
- não há dependência obrigatória em um mod específico de worldgen;
- validação detecta regras impossíveis, duplicadas ou prioridades ambíguas.