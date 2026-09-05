# 19.01 — State machine de crise climática

## Estados

Usar estados explícitos, por exemplo:

```text
NORMAL
COLD_WARNING
EXTREME_COLD
RECOVERY
```

Os nomes são internos/extensíveis; thresholds/durações vêm de policy data-driven e do adapter climático real.

## Entrada

O adapter pode observar estação, temperatura/forecast ou evento de mod compatível. Sem provider suficiente, a crise pode ser acionada por comando/admin/test fixture; não inventar dados meteorológicos.

## Transição

Histerese evita flicker quando temperatura oscila perto do limite. Transição registra crisisId, start/end, severity curve e source provenance.

## Frequência

Nenhum check global por tick. Amostrar provider em intervalo bounded ou consumir eventos. Heat demand usa severity snapshot publicado.

## Gameplay

`COLD_WARNING` informa preparação; `EXTREME_COLD` ativa demanda crítica; `RECOVERY` reduz demanda gradualmente conforme model.

## Testes

- entrada/saída thresholds;
- hysteresis;
- provider absent;
- restart durante crise;
- duplicate weather event;
- command fixture;
- multi-dimension policy.

## Acceptance

O inverno extremo começa/termina de forma determinística e nunca vira estado permanente por reload.