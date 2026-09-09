# Router de skills do projeto

Antes de escolher uma skill, leia `VERSION-AUTHORITY.md`. Se o usuário precisar agir manualmente, aplique também `USER-GUIDED-WORKFLOW.md`.

## Regra de overlay

Skills recebidas do usuário preservam o payload-fonte em `library/<skill>/`. Quando existir `library/<skill>/PROJECT-OVERLAY.md`, leia o `SKILL.md` e depois o overlay.

O overlay pode **restringir** loader, versão, autoridade, escopo e roteamento para este projeto, mas não deve reescrever silenciosamente o conteúdo-fonte. Em conflito de versão/loader, o overlay + `VERSION-AUTHORITY.md` vencem para a execução deste projeto.

## Engenharia NeoForge 1.21.1

Preferência:

1. `library/minecraft-neoforge-engineering/SKILL.md`
2. `library/minecraft-testing/SKILL.md` para testes
3. `library/minecraft-ci-release/SKILL.md` para CI/release
4. `library/minecraft-jar-reverse-engineering/SKILL.md` para investigar JAR/provider

`minecraft-mod-dev` e `minecraft-modding` são referências gerais/secundárias. Seus exemplos Fabric, Forge legado, Paper ou de versões posteriores não substituem a autoridade NeoForge 1.21.1.

`minecraft-multiloader` fica desativada por padrão; só usar quando existir uma decisão explícita de manter mais de um loader.

## Debug de modpack

- crash/hang/load: `minecraft-neoforge-modpack-debugging`
- isolamento de interação: `minecraft-modpack-bisect`
- dependências/bridges: `minecraft-dependency-compatibility-graph`
- redundância/inventário: `modpack-inventory-redundancy-audit`

Sempre partir da modlist/JARs/logs físicos atuais. Bibliotecas com nomes Fabric/Architectury podem existir em um pack NeoForge e não mudam o loader-alvo por si só.

## Conteúdo data-driven

Use com confirmação explícita de formatos/paths para 1.21.1:

- `minecraft-datapack`
- `minecraft-resource-pack`
- `minecraft-world-generation`
- `minecraft-commands-scripting`

Exemplos de 1.21.2+ / 1.21.8+ / 1.21.11 permanecem referência conceitual até prova de compatibilidade com 1.21.1.

## Imagem/conceito

`minecraft-imagegen` serve para concept art, briefs, pack icons, mockups e referências. Saída gerada não vira automaticamente modelo, UV ou textura final. Repo Textura permanece a fonte artística de verdade para assets project-owned.

## Art direction, modelos e animação

Para qualquer asset visual project-owned:

1. `../../docs/art/VISUAL-STYLE-BIBLE.md` — gramática visual cross-system;
2. `library/minecraft-asset-art-direction/SKILL.md` — identidade e asset contract;
3. `templates/ASSET-BRIEF.md` / `MODEL-BRIEF.md` / `ANIMATION-BRIEF.md` conforme o caso;
4. `library/minecraft-blockbench-geckolib/SKILL.md` — `.bbmodel`, UV, rig, animação e GeckoLib 4;
5. `tools/blockbench/rpg-asset-toolkit/rpg_asset_toolkit.js` — validator estrutural/contratual preferido;
6. `library/minecraft-visual-qa/SKILL.md` + `standards/VISUAL-QA.md` — gate visual/in-game;
7. opcionalmente `golden-samples/model-asset/` — exemplo preenchido `REFERENCE-ONLY`, nunca authority de runtime/API.

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
7. `library/minecraft-visual-qa/SKILL.md` para aceitação final;
8. opcionalmente `golden-samples/spell/` — exemplo completo `REFERENCE-ONLY`, com runtime/provider fields mantidos `UNRESOLVED` até prova real.

Quando um spell exige construct/projectile animado, combinar com asset-art-direction + Blockbench/GeckoLib.

## Golden Samples — regra de uso

Golden Samples servem para mostrar forma, cobertura e estado de evidência. Eles entram **depois** das skills/templates/standards relevantes. Nunca copiar `golden_reference_*`, fixture normalizada, provider candidate ou `UNRESOLVED` como se fossem implementação existente.

## QA visual

Sempre que a pergunta for “está pronto/bonito/coerente/sem regressão?” para um asset visual, carregar `minecraft-visual-qa`. Compile/build/editor preview não é evidência visual suficiente.

## Fora do escopo padrão

Ative somente quando a tarefa realmente exigir e o ambiente correspondente estiver comprovado:

- `minecraft-plugin-dev` — Paper/Bukkit/Spigot separado do runtime NeoForge;
- `minecraft-server-admin` — default deste projeto é servidor dedicado NeoForge;
- `minecraft-essentials-ops` — EssentialsX não está provado pela modlist física atual;
- `minecraft-multiloader` — somente com decisão explícita multi-loader;
- `minecraft-worldedit-ops` — WorldEdit não está presente na modlist física fornecida.
