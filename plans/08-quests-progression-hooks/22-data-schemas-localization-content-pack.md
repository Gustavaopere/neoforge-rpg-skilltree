# 08.22 — Data Schemas, Localization & Narrative Content Pack

## Goal
Separar engine de conteúdo para permitir centenas de quests/arcs sem recompilar Java.

## Data types previstos
- narrative events/facts registry;
- actor profiles;
- factions;
- ideologies/stances;
- institutions;
- settlement profiles;
- laws/policies;
- relationship rules;
- knowledge/evidence definitions;
- consequence definitions;
- beat/choice definitions;
- campaign arcs/eras;
- epilogue fragments;
- provider narrative mappings.

## Entregas
- [ ] codecs/schemas versionados e namespaced;
- [ ] cross-reference validation;
- [ ] cycle detection onde rotas/dependências puderem ciclar ilegalmente;
- [ ] duplicate ID detection;
- [ ] unreachable route diagnostics;
- [ ] missing fallback diagnostics para NPC crítico quando marcado `requires_fallback`;
- [ ] localization keys obrigatórias para player-facing content;
- [ ] PT-BR como pacote oficial principal, preservando IDs técnicos em inglês;
- [ ] reload atômico: inválido mantém snapshot anterior;
- [ ] export/diagnostics de graph para authoring;
- [ ] exemplo completo do arco Severin.

## Regra
Texto de diálogo não deve guardar IDs/estado semanticamente importante em parsing de string. Conteúdo referencia IDs estruturados; renderers recebem texto localizado.

## Acceptance
Um novo pequeno arco narrativo, incluindo choices, laws conditions, relationship changes e epilogue fragment, pode ser criado apenas por dados/localização usando primitives já suportadas.