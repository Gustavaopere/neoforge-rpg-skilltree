# 13.01 — Domínio, invariantes e autoridade cartográfica

## Objetivo

Definir o modelo canônico antes de qualquer integração visual. O sistema deve continuar correto mesmo sem JourneyMap instalado.

## Serviços canônicos

Implementar contratos equivalentes a:

- `CartographyService`: fachada server-authoritative para regiões, POIs e conhecimento;
- `RegionRegistryService`: identidade/geometria persistente de regiões;
- `PoiRegistryService`: identidade física de locais;
- `MapIntelService`: conhecimento por owner/body;
- `CartographyRenderer`: porta client-side opcional;
- `DiscoveryEventSink`: publicação idempotente de descobertas para quests/Compêndio/progressão.

Os nomes finais podem variar, mas essas responsabilidades não podem ser misturadas em um único adapter de JourneyMap.

## Modelo mínimo

### RegionRecord

- `regionId` estável;
- dimensão;
- `RegionType` semântico;
- células/chunks pertencentes ou representação de boundary;
- nome persistido como identidade/seed + chave de localização, nunca texto técnico congelado sem necessidade;
- `schemaVersion`;
- metadados de origem/classificador;
- timestamps/version counters somente quando necessários à reconciliação.

### PoiRecord

- `poiId` estável;
- dimensão;
- posição/anchor e bounds quando conhecidos;
- `PoiCategory`;
- registry key da estrutura/origem;
- mod de origem;
- estado físico opcional;
- tags semânticas e tags de quest;
- `schemaVersion`.

### MapIntelRecord

- subject (`regionId` ou `poiId`);
- owner UUID;
- `bodyId` quando `BODY_LOCAL`;
- nível de conhecimento;
- origem da informação;
- precisão permitida;
- timestamps/event claims necessários;
- quest links opcionais.

## Regras

1. dados físicos nunca dependem de arquivos do JourneyMap;
2. renderer nunca decide descoberta;
3. cliente nunca promove `MapIntelState` sozinho;
4. `ResourceLocation` e IDs estáveis são armazenados; strings PT-BR são renderizadas por lang keys;
5. toda mutação é idempotente e repetível após save/reload;
6. descoberta não dá reward diretamente: publica claim/evento consumido pelo serviço canônico apropriado;
7. nenhum `tick` pode iterar todos os POIs/regiões do mundo;
8. nenhuma API client-only pode ser carregada em dedicated server.

## Escopos

Todo dado deve declarar um dos escopos:

- `WORLD_GLOBAL`: geografia e local físico;
- `BODY_LOCAL`: intel do Stage 12;
- `ACCOUNT_GLOBAL`: somente quando explicitamente configurado/desenhado;
- `TRANSIENT_CLIENT`: projeção visual reconstruível.

## Acceptance

- testes unitários impedem renderer de ser autoridade;
- dedicated server inicializa sem JourneyMap;
- serialização round-trip preserva IDs e escopo;
- não existe caminho cliente → descoberta sem validação server-side.