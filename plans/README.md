# RPG Skill Tree — Planos de implementação

`plans/` contém **somente planejamento de programação e engenharia**. Informação destinada ao jogador fica em `wiki/`. Specs históricas e documentação técnica complementar permanecem em `docs/`.

## Convenção obrigatória de cada estágio

Cada subpasta numerada contém um único arquivo operacional:

- `PLANO.md` — estágio aberto, planejado ou em implementação;
- `PLANO-✅.md` — estágio concluído e validado.

O check no nome é um marco de engenharia, não editorial. Um plano só recebe `✅` depois que todos os critérios de aceite relevantes estiverem satisfeitos no código/dados, a validação aplicável estiver verde e o estado tiver sido integrado à branch canônica.

Se um estágio concluído voltar a ter requisito obrigatório aberto por regressão ou mudança arquitetural, o arquivo deve voltar para `PLANO.md` até o fechamento.

## Ordem de autoridade

Para decidir o que existe no runtime:

1. código e recursos versionados em `src/`;
2. testes, validadores e CI executáveis;
3. contratos/decisões em `plans/`;
4. wiki de jogador em `wiki/`;
5. specs históricas em `docs/specs/` e `docs/MASTER_PLAN.md`.

## Ordem causal

| Estágio | Diretório | Estado |
| --- | --- | --- |
| 00 | `00-foundation/PLANO.md` | EM ANDAMENTO / base existente |
| 01 | `01-rpg-core/PLANO.md` | EM ANDAMENTO / base existente |
| 02 | `02-progression-world-scaling/PLANO.md` | EM ANDAMENTO |
| 03 | `03-skill-tree-perks/PLANO.md` | EM ANDAMENTO |
| 04 | `04-classes-masteries-specializations/PLANO.md` | EM ANDAMENTO |
| 05 | `05-combat-magic-hooks/PLANO.md` | EM ANDAMENTO |
| 06 | `06-integrations/PLANO.md` | EM ANDAMENTO |
| 07 | `07-data-network-ui/PLANO.md` | EM ANDAMENTO |
| 08 | `08-quests-progression-hooks/PLANO.md` | PLANEJADO |
| 09 | `09-hardening-release/PLANO.md` | EM ANDAMENTO contínuo |

## Regras gerais de execução

- alvo canônico: Minecraft 1.21.1, NeoForge e Java 21;
- servidor é autoridade de level, XP, mastery, unlocks e efeitos de gameplay;
- IDs persistidos são API de save e não podem ser renomeados sem migração;
- conteúdo data-driven deve ser validado antes de chegar ao runtime;
- integração opcional não pode virar dependência dura acidental;
- um evento semântico concede progressão uma vez;
- nenhum adapter deve duplicar o fallback vanilla;
- antes de implementar um estágio, ler `STATUS.md`, `DECISIONS.md`, `PENDING.md` e seu `PLANO.md`;
- ao fechar um estágio, atualizar `STATUS.md` e renomear atomicamente `PLANO.md` para `PLANO-✅.md`.