# Chat 1 — Regra obrigatória para projetos próprios

Este arquivo complementa o protocolo permanente do Chat 1 para garantir que os quatro projetos próprios do modpack participem da auditoria de perks.

Antes de fechar qualquer perk, o Chat 1 deve ler integralmente:

- `../guides/projects/README.md`;
- `../guides/projects/01-rpg-skill-tree.md`;
- `../guides/projects/02-volcanoes.md`;
- `../guides/projects/03-enshrouded.md`;
- `../guides/projects/04-black-arcana.md`;
- `../guides/projects/05-cross-project-integration-matrix.md`.

Para cada perk, o Chat 1 deve classificar a relação com cada projeto próprio pertinente como uma destas categorias:

- provider principal;
- provider/consumer secundário;
- bridge;
- gate/requisito;
- recurso;
- Mastery/progressão;
- hazard/ambiente;
- equipamento/itemização;
- read-only/query;
- não aplicável;
- bloqueado/fail-closed.

O Chat 1 não pode tratar `PLANEJADO`, `PREPARATÓRIO / NÃO CANÔNICO` ou `BLOQUEADO / FAIL-CLOSED` como runtime disponível. Um subcomponente comprovado em `main` não promove automaticamente o Stage inteiro a concluído.

README raiz dos quatro projetos não é evidência suficiente. A autoridade operacional é o estado real de `plans/STATUS.md`, planos individuais e, quando necessário, código/testes/CI na `main` do projeto correspondente.

Integração temática não cria bridge. Em especial:

- Black Arcana Corruption não é Enshrouded Shroud/Exposure;
- Black Arcana Arcane Resistance não é generic magic resistance nem Enshrouded MagicResistanceService;
- Volcanoes Atmosphere/pressão/temperatura não viram Shroud ou Arcane Resistance por inferência;
- o RPG Skill Tree não deve escrever diretamente estado autoritativo de Flame/Shroud, Volcanoes ou Black Arcana sem provider/API explícito.

Se uma perk toca dois ou mais projetos próprios, o dossiê deve declarar um pipeline principal, a direção da bridge, authority de cada domínio, identidade de deduplicação, fallback e fail-closed.

Esta regra é cumulativa com `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md` e não reduz nenhuma exigência existente.
