# 13.06 — Máquina de estados de descoberta e inteligência

## Objetivo

Representar quanto cada corpo sabe sobre uma região/POI sem confundir existência física com conhecimento do jogador.

## Estados canônicos

Para POIs, suportar progressão equivalente a:

```text
DESCONHECIDO
→ RUMOR
→ REGIAO_CONHECIDA
→ AREA_APROXIMADA
→ LOCALIZADO
→ VISITADO
→ CONCLUIDO
```

Nem toda descoberta precisa atravessar todos os estados. Uma recompensa de quest pode ir diretamente a `LOCALIZADO`; exploração física pode promover `DESCONHECIDO → VISITADO`.

Para regiões, um fluxo mais simples pode usar:

```text
DESCONHECIDA → AVISTADA → EXPLORADA
```

Os nomes finais ficam em IDs internos + lang keys PT-BR.

## Precisão

Separar `state` de `precision`. Uma informação pode conter:

- nenhuma coordenada;
- direção/cardinalidade;
- região sem coordenada;
- centro aproximado + raio de erro;
- polygon/área de busca;
- posição exata;
- bounds exatos.

O servidor calcula e transmite apenas a projeção permitida.

## Fontes de intel

Registrar `DiscoverySource`/equivalente:

- exploração;
- entrada/proximidade de estrutura;
- quest;
- NPC;
- livro/mapa/item;
- Nature's Compass/Explorer's Compass quando integração autorizada;
- Compêndio;
- comando admin;
- script/datapack.

A fonte é útil para auditoria e regras de progressão, mas não deve alterar identidade física.

## Monotonicidade e exceções

Conhecimento normalmente é monotônico: descobrir posição exata não volta a rumor. Exceções de design como localização móvel ou informação falsa exigem um tipo explícito; não reutilizar POI estático para isso.

`DESTRUIDO`, `HOSTIL`, `SEGURO`, `DOMINADO` etc. são estados físicos/situação, não níveis de conhecimento.

## Claims idempotentes

Cada descoberta remunerável deve gerar claim estável para impedir:

- XP repetido ao cruzar borda;
- quest avançando várias vezes;
- spam de toast;
- exploit de logout/reload.

## Segurança

- cliente não recebe posição real de `DESCONHECIDO`/`RUMOR` quando ela não deveria ser conhecida;
- área aproximada deve ser materializada pelo servidor sem incluir metadata que permita reconstruir o centro real;
- debug/admin sensível só funciona com permissão adequada.

## Acceptance

- transições válidas e inválidas testadas;
- rewards/quest events são exactly-once/idempotentes;
- save/reload preserva estado;
- packet de rumor não contém coordenada secreta;
- corpo sem intel não herda informação de outro corpo por cache client-side.