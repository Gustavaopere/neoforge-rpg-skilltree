# Dependências do perfil narrativo — NeoForge 1.21.1

Data de verificação pública: **2026-08-30**.

Este arquivo define os mods externos escolhidos para o perfil completo de campanha narrativa. Eles são dependências do **modpack/perfil de conteúdo**, não hard dependencies do RPG Core: o mod deve continuar carregando sem eles e os adapters devem ser opcionais/fail-soft.

## Stack escolhida

| Mod | Versão/referência atual verificada | Papel | Política |
|---|---|---|---|
| Easy NPC Bundle | `easy_npc_bundle-neoforge-1.21.1-7.9.0.jar` | NPCs especiais, diálogos, escolhas, ações e comerciantes | **BAIXAR / REQUERIDO PARA A CAMPANHA COMPLETA** |
| FTB Quests | `2101.1.34` para NeoForge 1.21.1 | diário, capítulos, objetivos e acompanhamento visual | **BAIXAR / REQUERIDO PARA A CAMPANHA COMPLETA** |
| KubeJS | `2101.7.2-build.374` para NeoForge 1.21.1 | scripting, prototipagem, authoring e glue temporário entre eventos | **BAIXAR / REQUERIDO PARA AUTHORING/PROTÓTIPOS** |
| MineColonies | manter a versão 1.21.1 auditada da modlist; release pública estável observada `1.1.1368`, snapshots posteriores existem | cidade física, cidadãos, empregos, happiness, mortes, raids e infraestrutura | **MANTER / REQUERIDO PARA CAMPANHA DE COLÔNIA** |

### URLs de referência

- Easy NPC: `https://www.curseforge.com/minecraft/mc-mods/easy-npc`
- FTB Quests: `https://www.curseforge.com/minecraft/mc-mods/ftb-quests-forge`
- KubeJS: `https://www.curseforge.com/minecraft/mc-mods/kubejs`
- MineColonies: `https://www.curseforge.com/minecraft/mc-mods/minecolonies`

## Dependências transitivas

Não hardcodear bibliotecas transitivas neste plano. Preferir instalação pelo launcher/gerenciador do modpack para resolver as dependências oficiais de cada projeto. Antes da implementação dos adapters, capturar a modlist real e congelar os JARs/mod IDs efetivamente carregados.

## Mods deliberadamente não escolhidos

- **RPG Dialogue:** interessante como referência/API, mas não será instalado junto com Easy NPC enquanto ocupar a mesma camada de diálogo. Evitar dois renderers/authorities concorrentes.
- **ViScriptTeam:** facções/reputação serão autoridade do Narrative & Society Core; não terceirizar o formato de save a um provider beta sem necessidade.
- **MCA Reborn:** não adicionar apenas para relações sociais; conflitaria conceitualmente com a população/civilização já representada por MineColonies.
- **ViScriptQuests/Mebahel's RPG e outros quest frameworks:** não adicionar enquanto FTB Quests já ocupa o papel de journal/UI.

## Regra de autoridade

- **RPG Skill Tree / Narrative & Society Core:** autoridade de estado narrativo, cronologia, conhecimento, segredos, relações, facções, leis, opinião pública e consequências.
- **Easy NPC:** renderer/interação de NPC, nunca autoridade do estado narrativo.
- **FTB Quests:** diário e apresentação, nunca autoridade única da campanha.
- **MineColonies:** autoridade de população/colônia física no que sua API realmente expõe; o RPG não duplica jobs, buildings, citizens, raids ou happiness nativos.
- **KubeJS:** authoring/prototipagem e automação declarativa; não deve virar banco de dados canônico da campanha.

## Critério para build.gradle/mod metadata

Quando os adapters forem implementados:

1. preferir `compileOnly`/runtime de desenvolvimento ou mecanismo equivalente para providers opcionais;
2. proteger classloading com o registry de integrações opcionais já existente;
3. não exigir Easy NPC/FTB Quests/KubeJS/MineColonies para o jar base inicializar;
4. o perfil oficial do modpack pode declará-los como obrigatórios independentemente de o jar base tratá-los como optional;
5. qualquer API instável deve falhar fechado apenas para a feature dependente, sem converter o comportamento em bônus genérico.
