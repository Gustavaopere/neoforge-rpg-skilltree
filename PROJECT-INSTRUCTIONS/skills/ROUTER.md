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

## Art direction, modelos e animação

Para qualquer asset visual project-owned que precise combinar com o jogo/modpack:

1. `library/minecraft-asset-art-direction/SKILL.md` — define identidade, asset contract e critérios de aceitação;
2. `library/minecraft-blockbench-geckolib/SKILL.md` — Blockbench, `.bbmodel`, UV, rig, animação e GeckoLib 4 para o alvo 1.21.1;
3. `standards/MODEL-ASSET-CONTRACT.md` — contrato canônico antes da produção final;
4. `standards/VISUAL-QA.md` — gate visual/in-game;
5. `tools/blockbench/minecraft_asset_validator.js` — auditor estrutural read-only para Blockbench.

`minecraft-imagegen` é subordinada a este fluxo quando usada para concept/look-dev. Um concept sheet não substitui o `.bbmodel`, UV, textura, rig ou validação in-game.

## Spells, VFX e áudio

Para spell/ability project-owned ou integração que precise de presentation polish:

1. `library/minecraft-spell-vfx-engineering/SKILL.md` — lifecycle de VFX, causalidade, backend, sync, cleanup e performance;
2. `standards/SPELL-PRESENTATION-CONTRACT.md` — contrato obrigatório entre gameplay authority e apresentação;
3. `standards/VFX-QA.md` — gate in-game/multiplayer/dedicated-server;
4. `library/minecraft-audio-design/SKILL.md` — event map, loops, mix, spatialização, provenance e multiplayer;
5. `standards/AUDIO-QA.md` — gate de áudio no modpack real;
6. `SPELL-VFX-AUDIO-SOURCES.md` — versões físicas e fontes técnicas auditadas.

Provider-native vem primeiro. Photon, AAA Particles/Effekseer ou vanilla só entram como backend de apresentação comprovado; nenhum deles substitui o owner real de gameplay/cost/cooldown/damage.

Quando um spell também precisa de modelo/construct/projectile animado, combine este fluxo com `minecraft-asset-art-direction` e `minecraft-blockbench-geckolib`.

## Fora do escopo padrão

Ative somente quando a tarefa realmente exigir:

- `minecraft-plugin-dev`
- `minecraft-server-admin`
- `minecraft-essentials-ops`
- `minecraft-multiloader`
- `minecraft-worldedit-ops`
