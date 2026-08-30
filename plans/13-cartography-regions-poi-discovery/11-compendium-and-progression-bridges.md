# 13.11 — Pontes com Compêndio Natural e progressão

## Objetivo

Reutilizar o conhecimento canônico já produzido por outros estágios sem transformar a cartografia em uma segunda autoridade para biomas, estruturas, leveling ou recompensas.

## Stage 10 — Compêndio Natural

Reutilizar quando disponível:

- registry ID;
- nome localizado;
- mod de origem;
- categoria técnica;
- entradas de biome/estrutura/dimensão;
- provenance/coverage metadata.

A cartografia acrescenta:

- presença espacial;
- `regionId`/`poiId`;
- estado de descoberta;
- apresentação no mapa;
- vínculos de quest.

Permitir crosslink futuro:

```text
marker/region no mapa
→ “Abrir no Compêndio”
→ entrada correspondente
```

O inverso também pode existir para conteúdo já descoberto: Compêndio → destacar no mapa.

## Stage 02 — World scaling

Stage 13 pode fornecer contexto territorial legível, mas não assume autoridade de scaling.

Pontes permitidas:

- `regionId` como contexto espacial;
- traits de região aprovados como input data-driven;
- POI/boss-site como contexto de encounter.

A fórmula de level/dificuldade continua pertencendo ao Stage 02. Alterar nome/frontier de mapa não pode recalcular inimigos por acidente.

## Progressão e rewards

Descobertas podem publicar eventos para sistemas canônicos de XP/recompensa, mas:

- exatamente uma claim por descoberta elegível;
- reward rules data-driven;
- sem XP entregue pelo renderer;
- sem duplicar reward de quest e discovery quando ambos apontam para o mesmo claim, salvo design explícito.

## Stage 08 — Quests

A integração deve usar IDs estáveis e eventos, não referências de tela do JourneyMap.

## Stage 11 — Itemização

Mapas, cartas, instrumentos de exploração ou rewards podem ser itens do mundo, mas receber/usar um item cartográfico não deve rerrolar equipamentos ou acoplar itemização ao spatial index.

## Stage 12 — Corpos

Todo bridge deve propagar `bodyId`/escopo quando o conhecimento for corporal.

## Acceptance

- Compêndio e mapa compartilham registry identities sem bases duplicadas;
- world scaling funciona sem depender do renderer;
- discovery reward é idempotente;
- apagar marker visual não apaga entrada do Compêndio nem progresso;
- links respeitam conteúdo ainda não descoberto.