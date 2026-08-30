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
- opportunity/discovery lifecycle definitions;
- discovery channels e retrospective discovery hooks;
- autonomous progression/invalidation policies;
- death/return definitions;
- identity continuity profiles/reconciliation rules;
- campaign arcs/eras;
- epilogue fragments;
- provider narrative mappings.

## Entregas
- [ ] codecs/schemas versionados e namespaced;
- [ ] cross-reference validation;
- [ ] cycle detection onde rotas/dependências puderem ciclar ilegalmente;
- [ ] duplicate ID detection;
- [ ] unreachable route diagnostics;
- [ ] hidden-content leak diagnostics: beat `UNKNOWN` não pode ser exposto por journal/renderer sem discovery rule;
- [ ] diagnostics para oferta sem discovery/eligibility coerentes;
- [ ] diagnostics para autonomous progression sem idempotency/invalidation policy;
- [ ] missing fallback diagnostics para NPC crítico quando marcado `requires_fallback`;
- [ ] validation de gates que confundem `alive` com continuidade de memória/identidade quando o arco usa retorno pós-morte;
- [ ] localization keys obrigatórias para player-facing content;
- [ ] PT-BR como pacote oficial principal, preservando IDs técnicos em inglês;
- [ ] reload atômico: inválido mantém snapshot anterior;
- [ ] export/diagnostics de graph para authoring;
- [ ] exemplo completo do arco Severin cobrindo conteúdo nunca descoberto;
- [ ] exemplo de NPC morto e retornado por Mobstein com continuidade parcial.

## Regra
Texto de diálogo não deve guardar IDs/estado semanticamente importante em parsing de string. Conteúdo referencia IDs estruturados; renderers recebem texto localizado.

O schema deve manter separados availability, discovery, engagement e resolution. Também deve manter separados death/return e as dimensões de Identity Continuity; não inferir “mesma pessoa intacta” apenas porque uma entidade voltou a existir.

## Acceptance
Um novo pequeno arco narrativo, incluindo choices, laws conditions, relationship changes, conteúdo potencialmente nunca descoberto, retorno pós-morte quando aplicável e epilogue fragment, pode ser criado apenas por dados/localização usando primitives já suportadas.
