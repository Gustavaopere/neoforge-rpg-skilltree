# Projetos Próprios do Modpack — Fonte Canônica para Perks

Esta pasta documenta os quatro projetos próprios que precisam ser tratados como providers/sistemas de primeira classe durante a auditoria de perks:

1. [RPG Skill Tree](01-rpg-skill-tree.md)
2. [Volcanoes](02-volcanoes.md)
3. [Enshrouded](03-enshrouded.md)
4. [Black Arcana](04-black-arcana.md)
5. [Matriz de integração cruzada](05-cross-project-integration-matrix.md)
6. [Reconciliação dos snapshots](06-snapshot-reconciliation.md)

**Fonte editorial canônica no Notion:** https://app.notion.com/p/3cc69db9f0db81b09939eaca7c446fa2

## Por que esta pasta existe

Os três guias históricos descrevem muito bem os mods externos do pack, mas os quatro projetos próprios evoluem em repositórios separados e contêm sistemas que não podem ser inferidos por README, nome de classe ou intenção futura. O Chat 1 deve saber exatamente:

- quais subsistemas já são autoridade de gameplay em `main`;
- quais existem parcialmente;
- quais são preparatórios ou somente planejados;
- quais bridges são reais;
- quais relações são proibidas ou fail-closed;
- qual provider conserva a autoridade em integrações híbridas.

## Taxonomia obrigatória de estado

- **IMPLEMENTADO E CANÔNICO:** presente em `main`, com contrato/runtime fechado e evidência suficiente.
- **IMPLEMENTADO PARCIALMENTE:** código útil presente em `main`, mas o estágio/contrato ainda possui fechamento pendente.
- **PREPARATÓRIO / NÃO CANÔNICO:** existe em branch/protótipo/trabalho downstream, mas não é autoridade da `main`.
- **PLANEJADO:** especificação futura; não pode ser usada como hook implementável pelo Chat 2.
- **BLOQUEADO / FAIL-CLOSED:** integração intencionalmente inativa até existir API/evidência segura.
- **NÃO APLICÁVEL:** não existe relação semântica legítima no estado auditado.

A presença de um arquivo em `plans/` **não** prova disponibilidade de runtime.

## Snapshots auditados

A revisão de 2026-08-30 foi feita prioritariamente contra `plans/`, `plans/STATUS.md` e, quando necessário, contratos/código da `main`:

| Projeto | Base da análise extensa | Reconciliação antes do fechamento |
|---|---|---|
| RPG Skill Tree | `e49a1fa651abecfe096adb03c822482fcf9c3e7b` | `55463a195f8c3a87436399f71db19f29c8e85488`; 03.06 ganhou apenas o gate canônico de drift do catálogo/wiki, não fechamento integral |
| Volcanoes | `1d0da7ae7f19e06f60390fdeb0835720e2e40f1b` | mesma `main` auditada |
| Enshrouded | `de145be720f7f500f55e060982693312ed7f7bc3` | mesma `main` auditada |
| Black Arcana | `07263ae9bad12eba6ed500992991faa36ad598b2` | `STATUS.md` estava atrás da `main`; equipment set bonus foi classificado apenas como componente parcial de 05A.06 |

Os SHAs registram a evidência usada para este snapshot. Quando a `main` avança durante a própria auditoria, a diferença é registrada em [`06-snapshot-reconciliation.md`](06-snapshot-reconciliation.md). Cada novo lote de perks ainda deve fazer fetch fresco quando a integração depender de comportamento que possa ter mudado.

## Regra para o Chat 1

Antes de fechar qualquer perk, o Chat 1 deve cruzá-la com os quatro dossiês e a matriz. Para cada projeto próprio, classificar a relação como uma destas opções:

- provider direto;
- bridge/consumer secundário;
- coberto por sistema universal;
- progressão nativa autoritativa;
- planejado, ainda sem hook implementável;
- bloqueado/fail-closed;
- não aplicável.

Se uma perk toca mais de um projeto, o dossiê individual precisa declarar um **pipeline principal**, os providers/consumers secundários, a identidade de deduplicação, o fallback e o comportamento fail-closed.

## Regra de autoridade

Integração temática não cria um hook. Não converter automaticamente:

- Black Arcana Corruption em Enshrouded Shroud;
- Volcanoes Atmosphere em Shroud;
- pressão/temperatura/gases em Arcane Resistance;
- Flame Passage em resistência arcana;
- estado de veículos em encumbrance do jogador;
- dados client-side/HUD em autoridade de gameplay.

Quando um provider não expõe um hook seguro, a parte dependente permanece inativa/pending em vez de receber um bônus genérico substituto.

## Relação com os três guias

Os três guias continuam obrigatórios e recebem um apêndice final com o recorte pertinente destes quatro projetos:

- [Gameplay e Sistemas](../gameplay/13-projetos-proprios-do-modpack.md)
- [Mods de Magia](../magic/17-projetos-proprios-do-modpack.md)
- [Mods de Tecnologia](../technology/19-projetos-proprios-do-modpack.md)

Os apêndices são mapas temáticos. Para decidir provider, hook, status ou fail-closed, estes dossiês e a evidência fresca da `main` prevalecem.
