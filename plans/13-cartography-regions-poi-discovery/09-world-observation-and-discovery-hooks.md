# 13.09 — Observação do mundo e hooks de descoberta

## Objetivo

Detectar regiões e locais conforme o mundo realmente é explorado, com baixo custo e sem transformar o sistema em scanner global de worldgen.

## Fontes de observação

Avaliar hooks server-side adequados para NeoForge 1.21.1 e integrações presentes, incluindo:

- chunk load/generation quando já ocorrer naturalmente;
- movimento do jogador entre células/regiões;
- proximidade/entrada de bounds de estrutura;
- StructureStart/placement quando houver API/evento estável;
- interação com blocos/entidades registrados como POI provider;
- quest/intel explicitamente concedida;
- comandos admin e migrações bounded.

A implementação deve auditar os eventos reais disponíveis antes de escolher hooks; não adicionar mixin frágil se API/evento público resolver.

## Regras de descoberta

Descobrir o chunk não significa automaticamente revelar todos os segredos nele. Cada região/POI pode definir requisitos:

- entrar na região;
- aproximar-se a X blocos;
- obter linha de visão quando tecnicamente viável;
- atravessar bounds;
- interagir com elemento-chave;
- receber intel de quest;
- cumprir requisito especial.

## Spatial index

Manter índice espacial bounded para responder:

- quais regiões/POIs estão próximos;
- qual região contém a posição;
- quais POIs podem ser candidatos de quest;
- quais sujeitos precisam ser reconciliados após movimento.

Não pesquisar todos os registros em cada `PlayerTickEvent`.

## Frequência

Preferir transições de chunk/célula, eventos e caches. Para verificações de proximidade inevitáveis:

- cadence reduzida/configurável;
- consulta espacial local;
- budget por jogador/tick;
- nenhum force-load.

## Discovery providers

Criar porta para fontes opcionais como:

- mapas/itens do próprio RPG;
- Nature's Compass;
- Explorer's Compass;
- NPCs/quests;
- estruturas de mods com APIs próprias.

Integrações só podem promover intel através do `MapIntelService` server-authoritative.

## Telemetria/diagnóstico

Expor métricas debug:

- células indexadas;
- regiões materializadas;
- POIs registrados;
- consultas espaciais por período;
- jobs pendentes;
- tempo máximo/médio de processamento;
- chunks force-loaded pelo Stage 13, que deve permanecer **zero** em fluxo normal.

## Acceptance

- caminhar pelo mundo materializa apenas área observada;
- não há varredura global em startup/reload;
- repetição de eventos não duplica POIs nem discovery rewards;
- consultas de proximidade permanecem locais/bounded;
- testes confirmam zero force-load cartográfico em fluxo normal.