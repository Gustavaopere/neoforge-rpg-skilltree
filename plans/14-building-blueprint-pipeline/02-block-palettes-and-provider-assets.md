# 14.02 — Paletas de blocos e assets de providers

## Objetivo

Permitir estilos ricos sem hardcode eterno e sem degradar uma construção Create para vanilla.

## Modelo

`BuildingPalette` resolve papéis semânticos para block states:

```text
STRUCTURE_PRIMARY
STRUCTURE_SECONDARY
FLOOR
ROOF
WINDOW
PIPE
SHAFT
GEARBOX
TANK
BOILER_SHELL
CONTROL_PANEL
LIGHT
DECORATION
```

Cada papel aceita variantes ponderadas e constraints de posição/orientação.

## Resolução

1. override específico do BuildingSpec;
2. paleta data-driven selecionada;
3. adapter/provider para estados complexos;
4. fallback somente se o papel declarar fallback permitido;
5. erro diagnosticável se o design exige provider ausente.

## Create

Quando a construção requer Create, shafts, cogwheels, fluid pipes, tanks, casings, gauges e demais componentes usados no desenho devem ser IDs reais da versão instalada. O gerador não inventa IDs e não substitui mecanicamente por blocos vanilla.

## Modpack

Paletas podem reutilizar blocos de TFC, MineColonies, Create e addons compatíveis, desde que o registry ID exista e a dependência esteja declarada como requisito da paleta. Paleta base continua disponível para testes core-only.

## Testes

- provider presente resolve todos os papéis obrigatórios;
- provider ausente falha apenas para paleta que o requer;
- fallback permitido funciona;
- ID inexistente é rejeitado;
- orientações de block state são válidas;
- reload de paletas é transacional.

## Acceptance

Uma especificação que pede Create produz uma construção visual e estruturalmente Create; nenhum fallback silencioso descaracteriza o resultado.