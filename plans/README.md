# RPG Skill Tree — Planos de implementação

`plans/` reúne planejamento de engenharia e apresentação do RPG Skill Tree. A documentação de jogador continua em `wiki/`.

A organização canônica é dividida em duas authorities de planejamento:

- **estágios numerados (`00-*` a `13-*`)** — semântica, gameplay, dados, server authority, rede, persistência, providers, hooks, segurança e contratos funcionais;
- **`textura/`** — UI, HUD, layout, ícones, texturas, modelos, animações, VFX, partículas, áudio e acessibilidade visual.

Regra normativa: **Engenharia produz semântica; Textura produz apresentação.** A apresentação nunca concede autoridade de gameplay e não substitui provider/hook funcional ausente.

Ver:

- `textura/README.md`;
- `textura/FRONTEIRA-ENGENHARIA-TEXTURA.md`;
- `textura/AUDITORIA-SEPARACAO-2026-09-13.md`.

## Formato dos estágios de engenharia

O formato canônico segue o padrão usado em Volcanoes:

```text
plans/
  03-skill-tree-perks/
    README.md
    01-data-schema-loaders.md
    02-graph-layout-validation.md
    03-purchase-ranks.md
    ...
```

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

A criação de companions em `textura/` não altera retroativamente o estado de implementação dos subplanos de engenharia. Quando uma entrega visual futura possuir seu próprio lifecycle, seu estado deve ser registrado explicitamente dentro de `textura/` sem falsificar `✅` de runtime.

## Ordem de autoridade

Para runtime e comportamento:

1. código e recursos em `src/`;
2. testes, validadores e CI;
3. `plans/DECISIONS.md` e os subplanos de engenharia;
4. `plans/STATUS.md`;
5. `wiki/` para informação ao jogador;
6. specs históricas em `docs/`.

Para apresentação ainda não materializada em recursos, `plans/textura/` é a authority de planejamento visual/audiovisual. Ela não pode contradizer semântica, authority, provider ou dados definidos pelos níveis acima.

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
| 07 | `07-data-network-ui/` | EM ANDAMENTO — contrato funcional da UI |
| 08 | `08-quests-progression-hooks/` | PLANEJADO |
| 09 | `09-hardening-release/` | EM ANDAMENTO contínuo |
| 10 | `10-compendio-natural/` | PLANEJADO — Dicionário Enciclopédico pt-BR |
| 11 | `11-itemization-equipment-progression/` | PLANEJADO — itemização universal e progressão de equipamentos |
| 12 | `12-bodies-clones-progression-identities/` | PLANEJADO — corpos, clones e identidades de progressão |
| 13 | `13-cartography-regions-poi-discovery/` | PLANEJADO — cartografia RPG, regiões, locais e descoberta |
| — | `textura/` | APRESENTAÇÃO — authority transversal UI/HUD/assets/animação/VFX/áudio |

O Stage 10 consolida fauna, flora, árvores, cultivos, biomas, estruturas, dimensões, descoberta e dados técnicos em um único **Compêndio Natural**. O inventário deve ser derivado da modlist/registries atuais e manter suporte automático a conteúdo modded desconhecido. Sua apresentação visual é companion de `textura/03-*` e `textura/06-*`.

O Stage 11 consolida a **Itemização e Progressão de Equipamentos**: identidade persistente, Rank, Poder do Item, 1–5 Prefixos, 1–5 Sufixos e 1–5 Infixos por equipamento, sem reroll, com geração universal em craft/loot/mobs/outputs modded, integração com Apotheosis/Apothic, Iron's Spellbooks, Ars Nouveau, Create/tech e Curios. Localização/semântica permanecem no Stage 11; tooltip e identidade visual ficam em `textura/04-*`.

O Stage 12 consolida **Corpos, Clones e Identidades de Progressão**: uma mesma conta pode manter múltiplos corpos persistentes com level, árvore, atributos, classes, masteries, inventário e integrações corporais independentes. A troca usa transação server-authoritative com rollback; o world scaling consulta a progressão do corpo ativo. A apresentação do seletor/construção/ritual fica em `textura/05-*`.

O Stage 13 consolida **Cartografia, Regiões, Locais e Descoberta**: regiões e POIs persistentes, descoberta gradual, integração opcional com JourneyMap e política anti-cheat. O Stage 13 continua dono dos dados/reconciliação; overlays, ícones, waypoints e apresentação de Bosses ficam em `textura/06-*`.

Antes de implementar qualquer subplano de engenharia, ler `STATUS.md`, `DECISIONS.md`, `PENDING.md`, o `README.md` do estágio e os arquivos anteriores na ordem numérica. Antes de produzir uma superfície visual/audiovisual, ler também `textura/FRONTEIRA-ENGENHARIA-TEXTURA.md` e o companion correspondente.
