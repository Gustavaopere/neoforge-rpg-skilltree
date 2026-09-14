# 13.07 — Adapter de renderização JourneyMap — contrato técnico

## Fronteira

Ícones, overlays, labels, styling e apresentação de estados cartográficos foram separados para `../textura/06-cartografia-waypoints-bosses.md`.

Este arquivo permanece dono do adapter, projeção segura, IDs/reconciliação, classloading opcional e compatibilidade.

## Objetivo

Projetar JourneyMap como renderer opcional/primário quando disponível sem tornar sua API authority do domínio e sem introduzir dependência client-side no dedicated server.

## Regra de integração

Antes da implementação, auditar a API pública exata do JourneyMap para NeoForge 1.21.1 instalada fisicamente no modpack. Este plano não congela classes/métodos especulativos.

Pipeline conceitual:

```text
CartographyService
→ VisibleCartographyProjection(bodyId, dimension)
→ CartographyRenderer
→ JourneyMapAdapter
```

O domínio define semântica e visibilidade. O adapter escolhe somente a primitive técnica suportada pela API comprovada. O estilo visual dessa primitive pertence a Textura.

## Dados de apresentação suportáveis

Quando a API pública permitir, o adapter pode receber/projetar semanticamente:

- região descoberta;
- POI localizado;
- área aproximada;
- quest/visitado/concluído;
- estado físico conhecido;
- categoria/label localizada.

A escolha de ícone, borda, cor, pattern, typography e composição é definida em `../textura/06-cartografia-waypoints-bosses.md`.

## IDs e reconciliação

Cada decoração criada pelo RPG deve ter ID namespaced e estável derivado de `regionId`/`poiId` + tipo de projeção.

O adapter precisa:

- criar quando passa a ser visível;
- atualizar sem duplicar;
- remover quando deixa de ser permitido;
- limpar markers órfãos do namespace RPG;
- reconstruir após reconnect/client restart;
- reconciliar imediatamente em troca de corpo/dimensão.

## Segurança

Renderer recebe somente dados já filtrados. Não passar `PoiRecord` físico completo ao client adapter esperando que a UI esconda campos.

A camada de Textura não pode reconstruir precisão ou secrets ausentes do projection DTO.

## Ausência/incompatibilidade de JourneyMap

- nenhum classloading de JourneyMap no servidor comum;
- integration module somente quando mod/API compatível estiver presente;
- sem JourneyMap, quests/descoberta/persistência continuam funcionando;
- outro `CartographyRenderer` pode ser adicionado futuramente;
- fallback de apresentação sem mapa externo pertence a Textura, mas nunca cria coordenada inexistente.

## Licença/proveniência

Programar contra API pública respeitando termos do TeamJM. Não copiar/embutir source/class files fora do permitido. Registrar versão/API utilizada em `THIRD_PARTY_NOTICES.md` quando implementado.

MapFrontiers pode ser estudado apenas dentro da licença aplicável; qualquer código adaptado exige proveniência por arquivo/commit.

## Localização

O adapter recebe labels/categories próprios já localizados pelo RPG. Keys e semântica pertencem aos estágios funcionais; aparência do label/tooltip pertence a Textura.

## Acceptance técnico

- dedicated server passa sem JourneyMap;
- cliente com versão/API comprovada cria/update/remove overlays idempotentemente;
- troca de corpo remove intel que o novo corpo não possui;
- marker secreto não reaparece de cache após relog;
- ausência/versão incompatível falha soft e diagnosticável;
- apresentação pode mudar sem alterar IDs, visibilidade ou authority do domínio.
