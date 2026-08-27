# RPG Skill Tree — Planos canônicos

Este diretório é a memória operacional do projeto. Ele complementa, sem substituir, `docs/MASTER_PLAN.md` e `docs/specs/`, que permanecem como histórico e especificações de design.

## Regra de autoridade

Para dizer o que **existe hoje**, a ordem de autoridade é:

1. código e recursos versionados em `src/`;
2. testes/CI e contratos executáveis;
3. este diretório `plans/`;
4. wiki em `docs/wiki/`;
5. especificações históricas em `docs/specs/` e `docs/MASTER_PLAN.md`.

Uma ideia descrita em documentação antiga não deve ser promovida a `IMPLEMENTED` sem evidência correspondente no runtime ou datapack atual.

## Ordem causal

| Estágio | Escopo | Estado |
| --- | --- | --- |
| 00 | Foundation | EM ANDAMENTO / base existente |
| 01 | RPG Core | EM ANDAMENTO / base existente |
| 02 | Progression & World Scaling | EM ANDAMENTO / base existente |
| 03 | Skill Tree & Perks | EM ANDAMENTO / catálogo data-driven existente |
| 04 | Classes, Masteries & Specializations | EM ANDAMENTO |
| 05 | Combat & Magic Hooks | EM ANDAMENTO |
| 06 | Integrations | EM ANDAMENTO |
| 07 | Data, Network & UI | EM ANDAMENTO |
| 08 | Quest & Progression Hooks | PLANEJADO |
| 09 | Hardening & Release | EM ANDAMENTO contínuo |

## Regras de trabalho

- NeoForge 1.21.1 e Java 21 são o alvo canônico.
- Servidor é autoridade para progressão, gating e efeitos que alterem gameplay.
- Conteúdo data-driven deve ser validado antes de entrar em runtime.
- Integrações opcionais não podem quebrar o carregamento quando o mod externo estiver ausente.
- Não inventar integração nominal: hooks genéricos devem ser documentados como genéricos.
- Mudanças em IDs persistidos exigem estratégia de migração.
- Cada estágio só pode ser marcado como concluído quando seus critérios de aceite estiverem cobertos por código/dados e validação adequada.

Consulte `STATUS.md`, `DECISIONS.md` e `PENDING.md` antes de iniciar trabalho novo.