# 13.15 — Testes, performance e hardening

## Objetivo

Provar que cartografia, descoberta, quests e renderização são determinísticos, seguros e baratos o suficiente para o modpack real.

## Testes de domínio

Cobrir no mínimo:

- codec/save round-trip de regiões, POIs e intel;
- IDs estáveis após save/reload;
- classificação de biome por override/tag/fallback;
- classificação de estrutura por override/tag/fallback;
- segmentação determinística;
- merge de regiões + aliases;
- estados/transições de discovery válidos e inválidos;
- claims exactly-once;
- nomes persistentes sem reroll.

## Segurança de informação

Testes obrigatórios devem inspecionar payloads/projeções e provar que:

- `DESCONHECIDO` não transmite posição;
- `RUMOR` não transmite posição escondida;
- `AREA_APROXIMADA` não contém o centro real em metadata não usada;
- admin/debug não fica acessível sem permissão;
- cliente não promove discovery.

## Stage 12

GameTests/integration tests:

```text
Corpo A descobre POI
→ marker aparece
→ troca para B sem intel
→ marker some
→ relog/restart em B
→ marker continua ausente
→ volta para A
→ marker retorna
```

Também testar política opcional `ACCOUNT_GLOBAL` separadamente.

## JourneyMap

Matriz:

- JourneyMap presente e versão suportada;
- JourneyMap ausente;
- adapter incompatível/desabilitado;
- dedicated server sem classes client-side;
- reconciliação após reconnect/dimension/body switch;
- criação/atualização/remoção sem markers duplicados.

## Quests

- alvo dinâmico persiste após reload;
- área aproximada funciona;
- descobrir/visitar completa apenas uma vez;
- POI removido segue recovery policy;
- objetivo exclusivo nunca retargeta silenciosamente.

## Performance

Benchmarks/cenários de stress:

- milhares de POIs persistidos;
- centenas/milhares de regiões observadas;
- região muito grande;
- exploração rápida/elytra/veículos;
- troca de dimensão;
- troca de corpo com muitos markers;
- reload de datapack;
- vários providers opcionais ativos.

Métricas:

- tempo de processamento por chunk/célula;
- custo de consulta espacial;
- memória por RegionRecord/PoiRecord/MapIntelRecord;
- tamanho de save;
- bytes de rede por sync/delta;
- tempo de reconciliação do renderer;
- `forceLoadedChunksByCartography = 0` no fluxo normal.

## Regressões

CI deve impedir:

- uso client-only em server common code;
- full-world scan;
- fallback inglês em chaves player-facing do Stage 13;
- ausência de provenance quando código third-party derivado for adicionado;
- dependência rígida em JourneyMap/compasses opcionais.

## Dedicated server smoke

O smoke precisa inicializar o mod sem renderer client, carregar/migrar storage cartográfico e executar operações server-side básicas sem classloading indevido.

## Gate de conclusão

Nenhum arquivo do Stage 13 recebe `✅-` apenas porque o plano existe. Cada subplano só fecha após implementação real, testes aplicáveis, validação de licença/proveniência e integração na branch canônica.