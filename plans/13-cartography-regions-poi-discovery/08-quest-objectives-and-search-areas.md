# 13.08 — Quests, objetivos cartográficos e áreas de busca

## Objetivo

Permitir que quests revelem informação geográfica de forma graduada, reutilizem regiões/POIs reais e avancem por exploração sem depender de coordenadas hardcoded.

## Tipos de objetivo

Fornecer contratos para objetivos como:

- entrar em uma `RegionType`;
- entrar em uma `regionId` específica;
- descobrir uma região;
- descobrir/localizar/visitar um POI por `poiId`;
- encontrar qualquer POI que satisfaça categoria/tags;
- interagir dentro de um POI;
- derrotar entidade/boss associada ao POI;
- concluir evento dentro de uma área de busca;
- voltar a um local já conhecido.

## Revelação por quest

Uma quest pode conceder:

- somente texto/rumor;
- direção aproximada;
- nome da região;
- área circular com erro configurável;
- polygon de busca;
- marcador exato;
- atualização incremental conforme etapas avançam.

Exemplo:

```text
Quest aceita
→ “Procure a Torre do Mago ao norte da Floresta de Valen”
→ AREA_APROXIMADA
→ jogador entra no raio correto
→ exploração detecta a estrutura
→ LOCALIZADO
→ visita/interação
→ VISITADO/CONCLUIDO
```

## Seleção dinâmica de alvo

Quests que não dependem de local fixo podem solicitar ao servidor um alvo por regras:

- categoria/tags;
- dimensão/região;
- distância mínima/máxima;
- ainda não visitado;
- compatibilidade com nível/território;
- disponibilidade/estado físico;
- seed determinística da quest.

A seleção deve ser persistida quando vinculada à instância da quest para não trocar de torre/dungeon após reload.

## Anti-cheat

Nunca implementar “área aproximada” enviando o ponto real escondido em metadata client-side. O servidor cria uma aproximação suficientemente independente do alvo real.

Não usar `/locate` ou exploração forçada de chunks repetidamente por tick para achar objetivo. A quest pode aguardar descoberta/indexação ou usar mecanismo bounded explicitamente autorizado.

## Falhas e invalidação

Se o POI alvo desaparecer/incompatibilizar após update:

- manter referência/diagnóstico;
- tentar regra de recuperação configurada somente se a quest permitir retarget;
- nunca trocar silenciosamente um objetivo narrativo único;
- fail-closed para objetivos exclusivos.

## Hooks

Expor eventos canônicos, por exemplo conceitualmente:

- `RegionEntered`;
- `RegionDiscovered`;
- `PoiDiscovered`;
- `PoiVisited`;
- `PoiStateChanged`.

Consumidores precisam de deduplicação/claim IDs.

## Acceptance

- quest com área aproximada não vaza posição real;
- alvo persistido sobrevive a reload;
- visitar local avança somente uma vez;
- objetivo genérico seleciona POI por tags sem hardcode de mod;
- remoção/invalidação possui política explícita e testada.