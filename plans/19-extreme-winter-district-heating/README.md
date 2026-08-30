# 19 — Inverno Extremo e Aquecimento Distrital

## Objetivo

Criar crises sazonais de frio no estilo de gestão térmica discutido: durante eventos extremos, casas e serviços precisam de uma rede de calor real, combustível, capacidade e prioridades. O mundo **não fica em inverno permanente**.

## Autoridade

- provider de clima/estações: sinal meteorológico disponível;
- Cold Sweat: temperatura corporal do jogador quando presente;
- Volcanoes: geologia/atmosfera/calor ambiental onde seus contratos canônicos se aplicarem;
- Stage 19: infraestrutura térmica urbana, building heat state, heat network, combustível e citizen exposure;
- Stage 17: leis de racionamento/prioridade;
- Stage 14: blueprints da Central Térmica;
- Create: maquinaria física auxiliar quando APIs/mecânicas forem comprovadas.

Stage 19 não cria uma segunda barra de temperatura corporal concorrente com Cold Sweat.

## Modelo

```text
ClimateCrisis
→ HeatDemand por building/district
→ HeatNetwork
   ├── Generator Core / Thermal Plant
   ├── network segments
   ├── substations
   └── endpoints/radiators
→ allocation por capacidade/prioridade
→ thermal state
→ produtividade/saúde/exposição
```

## Central Térmica

Terá cinco níveis físicos reais. Cada nível aumenta infraestrutura/capacidade/controle e usa Stage 14 para garantir que preview, schematic e blueprint sejam a mesma construção. Paletas avançadas devem usar Create de forma visível e funcional onde certificado.

## Invariantes

1. frio extremo é evento/estado, não clima permanente;
2. combustível é consumido de estoque real;
3. capacidade da rede é limitada;
4. prioridade não cria calor;
5. prédio desconectado não recebe heat por proximidade mágica;
6. jogador usa provider corporal canônico;
7. cidadão MineColonies usa exposição própria bounded, sem tick pesado;
8. Create ausente não quebra core, mas blueprint/integração dependente fica indisponível;
9. pt-BR first.

## Ordem

1. `01-climate-crisis-state-machine.md`
2. `02-building-thermal-model.md`
3. `03-heat-network-and-districts.md`
4. `04-fuels-boilers-and-generator-core.md`
5. `05-central-thermal-plant-levels-1-5.md`
6. `06-substations-radiators-and-priorities.md`
7. `07-cold-health-productivity-and-death.md`
8. `08-emergency-laws-and-rationing.md`
9. `09-create-machinery-integration.md`
10. `10-tests-performance-failsoft.md`

## Definition of Done

Durante uma crise extrema, uma colônia com combustível e rede adequada mantém prédios aquecidos; rede sobrecarregada exige prioridades; falta prolongada de calor reduz produtividade e ameaça cidadãos; ao terminar a crise o sistema volta ao regime normal sem apagar infraestrutura.