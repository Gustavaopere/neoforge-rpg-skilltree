# Router de skills do projeto

Antes de escolher uma skill, leia `VERSION-AUTHORITY.md`. Se o usuário precisar agir manualmente, aplique também `USER-GUIDED-WORKFLOW.md`.

## Engenharia NeoForge 1.21.1

Preferência:

1. `library/minecraft-neoforge-engineering/SKILL.md`
2. `library/minecraft-testing/SKILL.md` para testes
3. `library/minecraft-ci-release/SKILL.md` para CI/release
4. `library/minecraft-jar-reverse-engineering/SKILL.md` para investigar JAR/provider

`minecraft-mod-dev` é referência secundária porque o ZIP recebido aponta para três arquivos internos ausentes.

## Debug de modpack

- crash/hang/load: `minecraft-neoforge-modpack-debugging`
- isolamento de interação: `minecraft-modpack-bisect`
- dependências/bridges: `minecraft-dependency-compatibility-graph`
- redundância/inventário: `modpack-inventory-redundancy-audit`

## Conteúdo data-driven

Use com confirmação explícita de formatos/paths para 1.21.1:

- `minecraft-datapack`
- `minecraft-resource-pack`
- `minecraft-world-generation`
- `minecraft-commands-scripting`

## Imagem/conceito

`minecraft-imagegen` serve para concept art, briefs, pack icons, mockups e referências. Saída de image generation não vira automaticamente modelo, UV ou textura final.

## Fora do escopo padrão

Ative somente quando a tarefa realmente exigir:

- `minecraft-plugin-dev`
- `minecraft-server-admin`
- `minecraft-essentials-ops`
- `minecraft-multiloader`
- `minecraft-worldedit-ops`

Skills específicas de art direction, Blockbench/GeckoLib, VFX/spells, áudio e visual QA prevalecem sobre este fallback quando existirem.
