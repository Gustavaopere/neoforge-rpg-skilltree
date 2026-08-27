# Status canônico

Última auditoria documental: **2026-08-27**.

Base auditada: `main@60055e83e8db9a36646be8aaeaeaec62b1cb4b9d`.

## Estado observado

- Fundação de RPG server-authoritative: presente e em evolução.
- Level/progressão de jogador: infraestrutura presente.
- Level de área/território, entidade e cálculo de nível relevante: infraestrutura presente.
- Raridade/arquetipagem/escala de mobs: trabalho já integrado na base auditada.
- Skill tree data-driven: presente.
- Catálogo materializado em `data/rpgskilltree/skills/main`: **474 nós JSON** auditados.
- Blueprint histórico/conceitual: **512 nós**. Não confundir capacidade/meta de design com materialização atual.
- Integrações runtime confirmadas na auditoria: Epic Fight, Iron's Spellbooks, Ars Nouveau, Goety, Malum e Eidolon: Repraised. Identity2 possui contrato de identidade/progressão no projeto, mas detalhes devem continuar sendo verificados contra o código antes de documentar efeitos específicos.
- Create e Applied Energistics 2 possuem definições de progressão/especialização nos dados/especificações, mas adapter runtime dedicado não foi comprovado na auditoria desta revisão.

## Legenda

- `IMPLEMENTED`: código/dado operacional comprovado.
- `PARTIAL`: contrato existe, mas falta parte do comportamento, cobertura ou UX.
- `SPEC/DATA`: desenho ou definição data-driven existe sem prova de adapter/runtime completo.
- `PLANNED`: intenção futura registrada.
- `UNCONFIRMED`: documentação antiga menciona, porém a revisão atual não fornece evidência suficiente.

## Próximos checkpoints

1. Congelar contratos do RPG Core que alimentam perks, classes e scaling.
2. Gerar automaticamente documentação de cada nó a partir dos JSON para eliminar drift.
3. Fechar matriz de integração com testes por mod opcional.
4. Conectar quests/recompensas aos serviços canônicos, sem duplicar XP/level.
5. Fechar migração de SavedData/player data e matriz dedicated-server antes de release.