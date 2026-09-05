# 19.09 — Integração funcional com Create

## Objetivo

Usar Create onde ele agrega mecanismo/visual real, sem transformar Create em autoridade da contabilidade térmica.

## Possíveis componentes

Quando a versão instalada comprovar comportamento/API adequado:

- shafts/cogwheels/gearboxes para transmissão;
- pumps/fluid pipes/tanks para circuito de fluido;
- gauges/displays/control mechanisms;
- mechanical inputs/automation;
- casings e componentes estruturais da usina.

A lista final usa registry IDs reais do modpack e adapter versionado.

## Authority boundary

`Generator Core` + HeatNetwork calculam supply/allocation. Create prova que a maquinaria declarada está montada/operando segundo contrato; Stage 19 não converte qualquer RPM em calor infinito.

## Stage 14

FunctionalGraph valida conectividade/orientation. Provider-present test em mundo confirma que layout exportado realmente funciona.

## Fail-soft

Core heating pode possuir implementação mínima própria; style/plant que declara Create é indisponível quando Create falta. Não substituir por vanilla sem aviso.

## Testes

- classloading absence;
- real registry IDs;
- shaft/pump path valid;
- machine stopped = adapter status changes;
- blueprint rotation preserves orientation;
- Create version mismatch diagnostic.

## Acceptance

Blocos Create da Central Térmica são parte funcional/validada onde rotulados como tal, não cenário enganoso.