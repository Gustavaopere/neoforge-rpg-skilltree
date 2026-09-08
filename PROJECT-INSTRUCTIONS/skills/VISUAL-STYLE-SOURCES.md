# Visual Style Sources — 2026-09-08

Este arquivo registra as fontes usadas pela Visual Style Bible. Ele não transforma resource packs, shaders ou mods visuais em authority de gameplay.

## Fontes de inventário

1. modlist física `modlist(2).txt` — autoridade para JARs/mod IDs/versões;
2. Notion `Auditoria Mestre da Modlist — NeoForge 1.21.1` — catálogo atual de resource packs, shaders e contexto visual;
3. `PROJECT-INSTRUCTIONS/skills/ART-PIPELINE-SOURCES.md` — Blockbench/GeckoLib;
4. `PROJECT-INSTRUCTIONS/skills/SPELL-VFX-AUDIO-SOURCES.md` — Photon/AAA/Effekseer/Iron's.

## Contexto visual confirmado no Notion

- Excalibur: base visual atual; substitui Whimscape;
- Fresh Animations 1.10.4;
- Fresh Animations: Extensions 1.8.1;
- Fresh Animations: Player Extension 1.1;
- Excalibur | Fresh Animations Patch;
- Mobs Refreshed 2.2 e compatibilidades Fresh Animations;
- Mandala's GUI — Dark mode 3.1 + add-ons/compat;
- Complementary Shaders — Reimagined r5.9.

Essas entradas são referências de composição/compatibilidade. Não autorizam copiar assets.

## Stack física relevante

Snapshot físico 2026-09-07/08:

- GeckoLib 4.9.2;
- Photon 2.2.6.a;
- AAA Particles 2.2.3;
- AAA Particles: World 2.0.0;
- Iron's Spells 'n Spellbooks 1.21.1-3.16.3;
- Player Animator 2.0.4+1.21.1.

Se Notion divergir do JAR físico, o JAR físico vence.

## Limite de evidência

Os binários dos resource packs/shaders listados acima não estão versionados no repositório como corpus de medição. Portanto:

- nenhuma resolução universal de textura é congelada aqui;
- nenhuma texel density universal é inferida;
- nenhuma cor exata de terceiro vira paleta project-owned por simples amostragem;
- o procedimento canônico é comparar o asset project-owned com referências disponíveis no ambiente real e registrar a decisão no contrato.
