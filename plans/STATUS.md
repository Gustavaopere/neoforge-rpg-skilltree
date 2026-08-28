# Status canônico dos planos

Última reorganização estrutural: **2026-08-27**.

Base funcional auditada antes desta reorganização: `main@ea04511a065bc9d956f9654d96efab8d1a48db7d`. Esta reorganização altera somente documentação de planejamento.

## Estado observado

- RPG Core server-authoritative: base presente, ainda não formalmente encerrada.
- Level/XP de jogador: infraestrutura presente.
- Nível de área/território, nível de entidade e relevant player level: infraestrutura presente.
- Raridade/arquetipagem/scaling: infraestrutura já integrada.
- Skill tree data-driven: presente; Árvore Principal materializada em 512 nós.
- Efeitos de atributo: 119 declarações auditadas.
- Classes: 23 definições data-driven auditadas.
- Especializações: 25 definições data-driven auditadas.
- Integrações confirmadas na auditoria: Epic Fight, Iron's, Ars Nouveau, Goety, Malum e Eidolon.
- Create/AE2/Oritech possuem conteúdo data-driven, mas runtime completo de máquinas/redes não deve ser presumido.

## Convenção de acompanhamento

O status fino fica nos nomes dos arquivos dentro de cada estágio:

- `NN-nome.md` = aberto;
- `✅-NN-nome.md` = concluído.

Nenhum arquivo novo desta reorganização recebe check automaticamente. O check será aplicado durante a execução/auditoria específica de cada subplano.

## Estágios

| Estágio | Diretório | Estado geral |
| --- | --- | --- |
| 00 Foundation | `00-foundation/` | EM ANDAMENTO / base existente |
| 01 RPG Core | `01-rpg-core/` | EM ANDAMENTO / base existente |
| 02 Progression & World Scaling | `02-progression-world-scaling/` | EM ANDAMENTO |
| 03 Skill Tree & Perks | `03-skill-tree-perks/` | EM ANDAMENTO |
| 04 Classes, Masteries & Specializations | `04-classes-masteries-specializations/` | EM ANDAMENTO |
| 05 Combat & Magic Hooks | `05-combat-magic-hooks/` | EM ANDAMENTO |
| 06 Integrations | `06-integrations/` | EM ANDAMENTO |
| 07 Data, Network & UI | `07-data-network-ui/` | EM ANDAMENTO |
| 08 Quest & Progression Hooks | `08-quests-progression-hooks/` | PLANEJADO |
| 09 Hardening & Release | `09-hardening-release/` | EM ANDAMENTO contínuo |