# Status canônico

Última auditoria documental: **2026-08-27**.

Base de código/dados auditada nesta revisão: `main@0904c060e5bde58529e30b919ff27d20fcac74d8`.

## Estado observado

- Fundação de RPG server-authoritative: presente e em evolução.
- Level/progressão de jogador: infraestrutura presente.
- Level de área/território, entidade e cálculo de nível relevante: infraestrutura presente.
- Raridade/arquetipagem/escala de mobs: trabalho já integrado na base auditada.
- Skill tree data-driven: presente.
- Árvore principal materializada: **512 nós JSON** em `data/rpgskilltree/skills/main`.
- Layout gerado: `target_node_count = 512` e `actual_node_count = 512`, com `node_budget_satisfied = true`.
- Efeitos de atributo declarados nos packs `node_effects/*.json`: **119 entradas** na revisão auditada (66 `main` + 53 distribuídas entre Druid, Epic Fight, Malum, Metamorph, Technomancer e Warlock).
- Nem todo nó da árvore principal possui hoje um efeito mecânico distinto: vários JSONs são nós estruturais/esqueleto com `bonuses: []`; efeitos também podem vir de packs de node effects e handlers runtime.
- Integrações runtime confirmadas na auditoria: Epic Fight, Iron's Spellbooks, Ars Nouveau, Goety, Malum e Eidolon: Repraised.
- Create e Applied Energistics 2 possuem definições de progressão/especialização, mas adapter runtime dedicado a eventos/máquinas não foi comprovado nesta revisão.

## Legenda

- `IMPLEMENTED`: código/dado operacional comprovado.
- `PARTIAL`: contrato existe, mas falta parte do comportamento, cobertura ou UX.
- `SPEC/DATA`: desenho ou definição data-driven existe sem prova de adapter/runtime completo.
- `PLANNED`: intenção futura registrada.
- `UNCONFIRMED`: documentação antiga menciona, porém a revisão atual não fornece evidência suficiente.

## Próximos checkpoints

1. Congelar contratos do RPG Core que alimentam perks, classes e scaling.
2. Automatizar a geração da wiki a partir de `skills/main`, `node_effects` e registries/runtime para eliminar drift.
3. Fechar matriz de integração com testes por mod opcional.
4. Conectar quests/recompensas aos serviços canônicos, sem duplicar XP/level.
5. Fechar migração de SavedData/player data e matriz dedicated-server antes de release.