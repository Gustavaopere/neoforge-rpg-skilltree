# 03 — Skill Tree & Perks

Estado: **EM ANDAMENTO**.

## Base atual
O datapack principal contém 474 nós JSON materializados, enquanto o design histórico descreve um blueprint de 512 nós.

## Objetivo
Manter árvores extensas, legíveis e data-driven, com validação forte de IDs, dependências e efeitos.

## Famílias auditadas
Arcane, Martial, Vitality, Agility, Engineering, Healing, Logistics, Mining, Occult, Summoning, Survival, Core, Keystones e Bridges.

## Critérios de aceite
- [ ] todo nó referencia efeitos conhecidos;
- [ ] requisitos formam grafo válido;
- [ ] nenhum ID duplicado;
- [ ] desbloqueio é server-authoritative;
- [ ] respec não deixa atributos/efeitos órfãos;
- [ ] catálogo da wiki pode ser regenerado a partir dos dados.