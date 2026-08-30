# 20.08 — Espionagem e inteligência

## IntelState

Separar fato real de conhecimento do realm:

```text
UNKNOWN
RUMOR
ESTIMATED
VERIFIED
STALE
```

Dados possíveis: military strength band, treasury estimate, treaty knowledge, strategic POI, unrest, heat/fuel vulnerability.

## Fontes

- spymaster office/agent mission;
- diplomatic exchange;
- trade observation;
- map/quest discovery Stage 13;
- public events.

Nenhuma fonte pode revelar coordenada secreta que Stage 13 não autorizou.

## Missions

Possuem cost, duration, difficulty/risk e target. Resultado é seeded/persistido antes de apresentação para impedir reroll por reload.

## Counterintelligence

Target pode reduzir accuracy/detect mission. Detection gera incident factor diplomático, não guerra automática.

## UI

Mostrar confidence/source/age; não apresentar estimate como fato exato.

## Testes

- rumor→verified;
- stale intel;
- mission retry/reload;
- unauthorized POI data not leaked;
- diplomatic incident;
- spymaster missing.

## Acceptance

Realm AI/jogador toma decisões com informação parcial persistente, não omnisciente.