# Skills e instruções especializadas

Esta pasta é o ponto de entrada canônico para skills/instruções de Minecraft usadas pelos chats que trabalham neste repositório.

## Leia primeiro

1. `ROUTER.md` — escolhe a skill adequada.
2. `VERSION-AUTHORITY.md` — fixa a autoridade de Minecraft 1.21.1 / NeoForge 21.1.x / Java 21.
3. `USER-GUIDED-WORKFLOW.md` — obriga uma etapa manual verificável por vez quando o usuário precisa agir.
4. `SOURCE-AUDIT.md` — classifica as skills recebidas.
5. `SOURCE-MANIFEST.md` — registra SHA-256 dos 20 ZIPs recebidos.
6. `ART-PIPELINE-SOURCES.md`, `VISUAL-STYLE-SOURCES.md` e `SPELL-VFX-AUDIO-SOURCES.md` — evidência técnica/visual das skills project-authored.
7. `../../docs/art/VISUAL-STYLE-BIBLE.md` — gramática visual canônica para assets próprios.

## Estrutura

- `library/<skill>/SKILL.md` — skills canônicas deste repositório;
- `standards/` — contratos/checklists de aceitação;
- `templates/` — briefs reutilizáveis para assets/modelos/animação/VFX/spells/áudio/QA;
- `golden-samples/` — exemplos project-owned, executáveis quando possível, sempre subordinados às standards/templates e nunca authority de runtime/API;
- `tools/` — utilitários versionados que não substituem validação no runtime;
- `docs/art/VISUAL-STYLE-BIBLE.md` — direção visual cross-system.

Skills project-authored atuais:

- `minecraft-asset-art-direction` — identidade visual e asset contract;
- `minecraft-blockbench-geckolib` — Blockbench/GeckoLib 4 para modelagem, UV, rig e animação;
- `minecraft-vfx-engineering` — VFX geral para magia, mobs, bosses, ambiente, tecnologia, armas e impacts;
- `minecraft-spell-vfx-engineering` — presentation lifecycle específico de spells;
- `minecraft-spell-production` — produção ponta a ponta de spell/ability;
- `minecraft-audio-design` — event map, loops, mix, spatialização e provenance;
- `minecraft-visual-qa` — aceitação visual/in-game e regression evidence.

## Golden Samples

`golden-samples/README.md` contém referências preenchidas para modelo/animação e spell/VFX/áudio/QA. Use somente depois de carregar a skill, template e standard correspondentes.

Os Golden Samples são marcados `REFERENCE-ONLY`; IDs `golden_reference_*`, fixtures normalizadas e campos `UNRESOLVED` não são registros, recursos ou APIs do runtime. Antes de reutilizar um exemplo em implementação real, substitua sua identidade e prove todos os campos não resolvidos em código/JAR/documentação exata.

## Blockbench

`tools/blockbench/rpg-asset-toolkit/` é o caminho preferido. O antigo `tools/blockbench/minecraft_asset_validator.js` é mantido como validator legado/compatibilidade e não recebe novos requisitos de contract profile.

## Regra de segurança de versão

Os ZIPs originais foram auditados, mas exemplos multi-versão e arquivos auxiliares não são promovidos automaticamente a authority. Isso impede que exemplos de 1.21.4+, 1.21.8+, 1.21.11, Fabric, Forge legado, Paper ou multi-loader contaminem o runtime NeoForge 1.21.1.

Ter um `SKILL.md` neste repositório não o transforma em skill nativa do ChatGPT; é uma instrução versionada que o chat deve ler e seguir ao trabalhar neste repositório.
