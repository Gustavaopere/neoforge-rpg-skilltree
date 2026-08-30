# 15.05 — Tipos de zoneamento

## Objetivo

Classificar uso pretendido do solo sem forçar uma única atividade por distrito.

IDs data-driven planejados:

- `residential`;
- `commercial`;
- `industrial`;
- `civic`;
- `religious`;
- `military`;
- `agricultural`;
- `logistics`;
- `technology`;
- `arcane`;
- `heating_infrastructure`;
- `mixed`.

Um distrito pode ter zone principal e tags secundárias. Regras econômicas/governamentais consomem IDs; não dependem de enum Java fechado.

## Efeito

Zoneamento não teleporta prédio nem cancela arbitrariamente MineColonies. Ele fornece contexto para:

- permissão/incentivo de novos prédios;
- impostos/subsídios;
- preço/valor de propriedade;
- prioridade de serviços;
- restrições legais;
- analytics.

Violações são resolvidas pelo Stage 17 e podem ser permitidas com penalidade dependendo do regime/lei.

## Auto-observação

Prédios existentes podem sugerir uso predominante, mas nunca reescrevem zoning confirmado sem decisão administrativa.

## Testes

- IDs desconhecidos preservados/quarentenados;
- reload data-driven;
- mixed-use;
- integração com building tags do Stage 18;
- provider ausente não remove zone.

## Acceptance

Zoneamento é extensível, persistente e útil a múltiplos sistemas sem virar hard lock técnico.