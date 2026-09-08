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

`minecraft-imagegen` serve para concept art, briefs, pack icons, mockups e referências. Saída gerada não vira automaticamente modelo, UV ou textura final.

## Art direction, modelos e animação

Para qualquer asset visual project-owned:

1. `../../docs/art/VISUAL-STYLE-BIBLE.md` — gramática visual cross-system;
2. `library/minecraft-asset-art-direction/SKILL.md` — identidade e asset contract;
3. `templates/ASSET-BRIEF.md` / `MODEL-BRIEF.md` / `ANIMATION-BRIEF.md` conforme o caso;
4. `library/minecraft-blockbench-geckolib/SKILL.md` — `.bbmodel`, UV, rig, animação e GeckoLib 4;
5. `tools/blockbench/rpg-asset-toolkit/rpg_asset_toolkit.js` — validator estrutural/contratual preferido;
6. `library/minecraft-visual-qa/SKILL.md` + `standards/VISUAL-QA.md` — gate visual/in-game.

`minecraft-imagegen` é subordinada a este fluxo quando usada para concept/look-dev.

## VFX geral

Para VFX de mobs, bosses, ambiente, explosões, corruption, Shroud, hazards, tecnologia, armas ou impactos:

1. `library/minecraft-vfx-engineering/SKILL.md`;
2. `templates/VFX-BRIEF.md`;
3. `standards/VFX-QA.md`;
4. `library/minecraft-visual-qa/SKILL.md`.

Provider-native vem primeiro. Photon, AAA/Effekseer ou vanilla entram como backend comprovado; não são gameplay authority.

## Spells, VFX e áudio

Para spell/ability project-owned ou integração que precise de polish:

1. `library/minecraft-spell-production/SKILL.md` — coordena a produção ponta a ponta;
2. `templates/SPELL-BRIEF.md` — contrato de produção;
3. `library/minecraft-spell-vfx-engineering/SKILL.md` — lifecycle específico de spell;
4. `library/minecraft-vfx-engineering/SKILL.md` — composição/backend VFX geral;
5. `standards/SPELL-PRESENTATION-CONTRACT.md` e `standards/VFX-QA.md`;
6. `library/minecraft-audio-design/SKILL.md` + `templates/AUDIO-CUE-SHEET.md` + `standards/AUDIO-QA.md`;
7. `library/minecraft-visual-qa/SKILL.md` para aceitação final.

Quando um spell exige construct/projectile animado, combinar com asset-art-direction + Blockbench/GeckoLib.

## QA visual

Sempre que a pergunta for “está pronto/bonito/coerente/sem regressão?” para um asset visual, carregar `minecraft-visual-qa`. Compile/build/editor preview não é evidência visual suficiente.

## Fora do escopo padrão

Ative somente quando a tarefa realmente exigir:

- `minecraft-plugin-dev`
- `minecraft-server-admin`
- `minecraft-essentials-ops`
- `minecraft-multiloader`
- `minecraft-worldedit-ops`
