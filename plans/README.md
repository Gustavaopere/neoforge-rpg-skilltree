# RPG Skill Tree — Planos de implementação

`plans/` contém somente planejamento de programação e engenharia. A documentação de jogador fica em `wiki/`.

O formato canônico segue o mesmo padrão usado em Volcanoes:

```text
plans/
  03-skill-tree-perks/
    README.md
    01-data-schema-loaders.md
    02-graph-layout-validation.md
    03-purchase-ranks.md
    ...
```

## Convenção de arquivos

Cada estágio possui um `README.md` curto, que explica objetivo, dependências e ordem causal. O trabalho real é dividido em arquivos numerados, um por subproblema implementável.

- `01-nome.md` = plano ainda aberto.
- `✅-01-nome.md` = plano concluído, validado e integrado.

O check pertence ao **subplano**, não ao estágio inteiro. Um estágio pode ter dois arquivos concluídos e três ainda abertos.

Um arquivo só recebe `✅-` quando:

1. todos os itens obrigatórios daquele arquivo foram implementados;
2. testes/validadores aplicáveis passaram;
3. nenhuma pendência necessária para o escopo ficou escondida;
4. a implementação foi integrada na branch canônica.

Se uma regressão ou mudança arquitetural reabrir o trabalho, o arquivo volta ao nome sem `✅-`.

## Ordem de autoridade

1. código e recursos em `src/`;
2. testes, validadores e CI;
3. `plans/DECISIONS.md` e os subplanos;
4. `plans/STATUS.md`;
5. `wiki/` para informação ao jogador;
6. specs históricas em `docs/`.

## Estágios

| Estágio | Diretório | Estado geral |
| --- | --- | --- |
| 00 | `00-foundation/` | EM ANDAMENTO / base existente |
| 01 | `01-rpg-core/` | EM ANDAMENTO / base existente |
| 02 | `02-progression-world-scaling/` | EM ANDAMENTO |
| 03 | `03-skill-tree-perks/` | EM ANDAMENTO |
| 04 | `04-classes-masteries-specializations/` | EM ANDAMENTO |
| 05 | `05-combat-magic-hooks/` | EM ANDAMENTO |
| 06 | `06-integrations/` | EM ANDAMENTO |
| 07 | `07-data-network-ui/` | EM ANDAMENTO |
| 08 | `08-quests-progression-hooks/` | PLANEJADO |
| 09 | `09-hardening-release/` | EM ANDAMENTO contínuo |
| 10 | `10-compendio-natural/` | PLANEJADO — Dicionário Enciclopédico pt-BR |
| 11 | `11-itemization-equipment-progression/` | PLANEJADO — itemização universal e progressão de equipamentos |
| 12 | `12-bodies-clones-progression-identities/` | PLANEJADO — corpos, clones e identidades de progressão |

O Stage 10 consolida fauna, flora, árvores, cultivos, biomas, estruturas, dimensões, descoberta e dados técnicos em um único **Compêndio Natural**. O inventário deve ser derivado da modlist/registries atuais e manter suporte automático a conteúdo modded desconhecido.

O Stage 11 consolida a **Itemização e Progressão de Equipamentos**: identidade persistente, Rank, Poder do Item, 1–5 Prefixos, 1–5 Sufixos e 1–5 Infixos por equipamento, sem reroll, com geração universal em craft/loot/mobs/outputs modded, integração com Apotheosis/Apothic, Iron's Spellbooks, Ars Nouveau, Create/tech e Curios, além de apresentação própria integral em português do Brasil.

O Stage 12 consolida **Corpos, Clones e Identidades de Progressão**: uma mesma conta pode manter múltiplos corpos persistentes com level, árvore, atributos, classes, masteries, inventário e integrações corporais independentes. A troca usa transação server-authoritative com rollback; o world scaling passa a consultar a progressão do corpo ativo sem remover baselines naturais de dimensões/territórios. O acesso pode ocorrer por construção tecnológica ou transmigração mística, incluindo integração opcional e não destrutiva com caixões do Vampirism, e toda apresentação própria deve ser integralmente localizada em português do Brasil.

Antes de implementar qualquer subplano, ler `STATUS.md`, `DECISIONS.md`, `PENDING.md`, o `README.md` do estágio e os arquivos anteriores na ordem numérica.
