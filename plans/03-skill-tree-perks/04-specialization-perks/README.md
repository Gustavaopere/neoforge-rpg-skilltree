# 04 — Specialization Perks

Esta pasta está intencionalmente sem especializações individuais neste momento.

As 25 pastas herdadas do catálogo/runtime antigo foram removidas do novo desenho. Elas não são candidatas automaticamente e não reservam nomes, providers ou quotas.

## Conceito

Especializações são ramificações temáticas das 11 árvores principais.

Exemplo conceitual:

```
ARCANE
├── árvore principal
├── Piromante
├── Criomante
└── ...
```

Uma especialização pode integrar múltiplos mods e vanilla. Ela não deve ser criada simplesmente porque um mod, escola ou addon existe.

## Quando serão criadas

Somente após:

1. modlist física reconciliada;
2. matriz de capacidades concluída;
3. capacidades distribuídas nas 11 árvores;
4. fantasias de build comparadas entre todos os domínios.

Depois serão congelados globalmente:

- `K_specializations_per_tree`;
- `M_perks_per_specialization`.

Todas as 11 árvores terão os mesmos valores. Se uma árvore não sustentar a quota sem filler, reduzimos a quota global.
