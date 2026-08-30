# 13.07 — Adapter de renderização JourneyMap

## Objetivo

Projetar JourneyMap como renderer primário sem tornar sua API a autoridade do domínio e sem introduzir dependência client-side no dedicated server.

## Regra de integração

Na implementação, auditar a API pública exata do JourneyMap para NeoForge 1.21.1 usada no modpack antes de escolher classes/métodos. O plano não congela nomes de API especulativos.

O adapter deve consumir uma projeção segura do domínio:

```text
CartographyService
→ VisibleCartographyProjection(bodyId, dimension)
→ CartographyRenderer
→ JourneyMapAdapter
```

## Elementos visuais

Quando suportados pela API pública, projetar:

- labels de região;
- polígonos/frontiers/overlays de regiões descobertas;
- waypoints/markers de POIs localizados;
- círculos/polígonos de área aproximada;
- ícones por categoria;
- estados visuais de quest, visitado, concluído e estado físico conhecido.

Não exigir que JourneyMap suporte exatamente todos os primitives. O domínio fornece semântica; o adapter escolhe a melhor representação suportada.

## IDs e reconciliação

Cada decoração criada pelo RPG deve ter ID namespaced e estável, derivado de `regionId`/`poiId` + tipo de projeção. O adapter precisa:

- criar quando passa a ser visível;
- atualizar sem duplicar;
- remover quando deixa de ser permitido;
- limpar markers órfãos do namespace RPG;
- reconstruir após reconnect/client restart;
- reconciliar imediatamente na troca de corpo/dimensão.

## Segurança

Renderer recebe somente dados já filtrados. Não passar `PoiRecord` físico completo ao client adapter e esperar que ele esconda campos.

## Ausência de JourneyMap

- nenhum classloading de JourneyMap no servidor comum;
- integration module carregado somente quando mod/API compatível estiver presente;
- sem JourneyMap, quests/descoberta/persistência continuam funcionando;
- outro `CartographyRenderer` pode ser adicionado futuramente.

## Licença

Programar contra a API pública respeitando os termos do TeamJM. Não copiar nem embutir source/class files do JourneyMap API no projeto fora do explicitamente permitido. Registrar versão/API utilizada em `THIRD_PARTY_NOTICES.md` quando implementado.

MapFrontiers pode ser estudado para UX/algoritmos de frontier somente dentro da licença MIT; qualquer código adaptado exige proveniência por arquivo/commit.

## PT-BR

Todos os nomes/categorias/tooltips próprios são localizados pelo RPG. Não depender de strings inglesas internas do adapter.

## Acceptance

- dedicated server passa sem JourneyMap;
- cliente com JourneyMap cria/update/remove overlays idempotentemente;
- troca de corpo remove intel que o novo corpo não possui;
- nenhum marker secreto reaparece de cache após relog;
- ausência/versão incompatível falha de forma soft e diagnosticável.