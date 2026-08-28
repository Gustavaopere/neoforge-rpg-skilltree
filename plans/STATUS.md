# Status canônico dos planos

Última reorganização documental: **2026-08-27**.

Base funcional auditada antes desta reorganização: `main@6ab1c7a56bc15856999786c6be9971205a1a3359`. Os commits posteriores desta tarefa reorganizam documentação e não alteram gameplay.

## Estado observado

- RPG Core server-authoritative: base presente, ainda não formalmente encerrada.
- Level/XP de jogador: infraestrutura presente.
- Nível de área/território, nível de entidade e relevant player level: infraestrutura presente.
- Raridade/arquetipagem/scaling: infraestrutura já integrada.
- Skill tree data-driven: presente; Árvore Principal materializada em **512 nós**.
- Efeitos de atributo: **119 declarações** auditadas em `node_effects/*.json`.
- Classes: 23 definições data-driven auditadas.
- Especializações: 25 definições data-driven auditadas.
- Integrações runtime confirmadas: Epic Fight, Iron's, Ars Nouveau, Goety, Malum e Eidolon; morphs de Druid/Metamorph possuem contratos runtime descritos nas classes.
- Create/AE2/Oritech possuem conteúdo data-driven, mas runtime completo de máquinas/redes não deve ser presumido.

## Estágios

| Estágio | Estado | Arquivo |
| --- | --- | --- |
| 00 Foundation | EM ANDAMENTO / base existente | `00-foundation/PLANO.md` |
| 01 RPG Core | EM ANDAMENTO / base existente | `01-rpg-core/PLANO.md` |
| 02 Progression & World Scaling | EM ANDAMENTO | `02-progression-world-scaling/PLANO.md` |
| 03 Skill Tree & Perks | EM ANDAMENTO | `03-skill-tree-perks/PLANO.md` |
| 04 Classes, Masteries & Specializations | EM ANDAMENTO | `04-classes-masteries-specializations/PLANO.md` |
| 05 Combat & Magic Hooks | EM ANDAMENTO | `05-combat-magic-hooks/PLANO.md` |
| 06 Integrations | EM ANDAMENTO | `06-integrations/PLANO.md` |
| 07 Data, Network & UI | EM ANDAMENTO | `07-data-network-ui/PLANO.md` |
| 08 Quest & Progression Hooks | PLANEJADO | `08-quests-progression-hooks/PLANO.md` |
| 09 Hardening & Release | EM ANDAMENTO contínuo | `09-hardening-release/PLANO.md` |

Nenhum estágio possui `PLANO-✅.md` nesta revisão porque ainda há critérios obrigatórios abertos.