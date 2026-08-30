# 15.08 — Mapa e bridge com Stage 13

## Separação

Stage 13 continua dono de `RegionRecord`, `PoiRecord` e descoberta. Stage 15 adiciona `DistrictRecord`. Uma Floresta de Valen pode conter dois distritos e um distrito pode cruzar regiões naturais; nenhuma identidade substitui a outra.

## Renderer

Expor adapter de overlay para JourneyMap/renderer compatível:

- contorno;
- nome;
- zoning;
- status de política/serviço quando autorizado;
- filtros independentes de Region/POI.

JourneyMap não persiste a fronteira.

## Bridges

- POI/prédio pode referenciar `districtId` derivado pela posição;
- quest pode solicitar criar/visitar distrito sem revelar POIs secretos;
- Compêndio não vira cadastro administrativo;
- mudanças em regions não redesenham districts.

## Testes

- JourneyMap ausente;
- overlays independentes;
- region/district overlap legítimo;
- mudança de district revision atualiza marker sem duplicar;
- troca de dimensão não vaza overlays.

## Acceptance

O jogador vê as duas camadas — geografia descoberta e administração — sem confundir suas autoridades.