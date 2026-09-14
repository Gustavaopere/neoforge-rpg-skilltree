# Auditoria de separação — Textura/Apresentação — 2026-09-13

## Base auditada

- Repositório: `Gustavaopere/neoforge-rpg-skilltree`.
- `main` considerada para abertura da branch: `b94fee3095d3379b6be2ce4eedadabbc82f97466`.
- Minecraft: 1.21.1.
- NeoForge físico vigente: 21.1.248.
- Java: 21.
- Modlist física: 595 mods.
- Notion consultado: `Auditoria Mestre da Modlist — NeoForge 1.21.1`.

A modlist/JAR continua sendo autoridade de presença e versão; esta auditoria não altera contratos de provider nem versões.

## Objetivo

Separar de `plans/` o conteúdo de apresentação que estava misturado com engenharia, criando `plans/textura/` como autoridade canônica para UI, HUD, texturas, modelos, animações, VFX, partículas, áudio e acessibilidade visual.

## Resultado por plano auditado

| Plano de origem | Classificação | Destino/ação |
| --- | --- | --- |
| `07-data-network-ui/05-skill-tree-ui.md` | misto | engenharia funcional permanece; apresentação vai para `textura/01-skill-tree-ui-hud.md` |
| `07-data-network-ui/06-localization-accessibility.md` | misto | keys/validator permanecem; legibilidade visual vai para `textura/02-localizacao-acessibilidade-visual.md` |
| `08-quests-progression-hooks/23-network-ui-authoring-diagnostics.md` | predominantemente engenharia | permanece; futura apresentação player-facing segue `textura/02-*` e a fronteira normativa |
| `10-compendio-natural/09-ui-modelo3d-notas.md` | misto | segurança/read model/protocolo permanecem; UI/previews/modelos vão para `textura/03-compendio-ui-modelos-previews.md` |
| `10-compendio-natural/15-bosses-rastreamento-waypoints.md` | misto | tracking/anti-cheat/persistência permanecem; Boss Checklist/waypoints/HUD vão para `textura/06-cartografia-waypoints-bosses.md` |
| `11-itemization-equipment-progression/13-ptbr-localization-ui.md` | misto | localização/validator permanecem; tooltip/Rank/identidade visual vão para `textura/04-itemizacao-tooltips-identidade-visual.md` |
| `12-bodies-clones-progression-identities/12-ui-selector-ptbr.md` | misto | read model/request/security/localization permanecem; apresentação vai para `textura/05-corpos-seletor-construcao-ritual.md` |
| `13-cartography-regions-poi-discovery/07-journeymap-renderer-adapter.md` | misto | adapter/reconciliação/classloading permanecem; overlays/ícones/estilos vão para `textura/06-cartografia-waypoints-bosses.md` |
| `06-integrations/11-minecolonies-economy-ui-audit-1.1.1375.md` | engenharia de seam/API | permanece integralmente em integração; não é direção artística |
| `06-integrations/✅-02-epicfight.md` | runtime | permanece; o próprio plano declara `Estética: N/A` |
| `05-combat-magic-hooks/02-melee-epicfight.md` | runtime | permanece |
| `06-integrations/06-identity-morphs.md` | runtime/permissão | permanece |

## Conteúdo não fabricado

Não foi encontrada, no conjunto ativo de planos de apresentação acima, uma especificação normativa dedicada de:

- clips/rigs de animação;
- VFX/partículas com identidade visual definida;
- soundscape/SFX/cues com assets definidos;
- biblioteca geral de texturas/modelos fora das superfícies já descritas.

A ausência foi registrada em `07-animacao-vfx-audio.md` e `08-assets-texturas-modelos-gerais.md`. Esses arquivos definem ownership e handoff, mas não inventam paleta, clip, som, modelo, textura ou timing funcional.

## Tratamento de dossiers e planos de gameplay

Dossiês de perks e planos de gameplay continuam donos da identidade semântica do efeito. Uma frase como “estado deve ser perceptível” ou um nome temático não transforma o dossiê em authority de asset.

Quando um perk precisar de ícone, animação, VFX ou som próprios, a materialização deve ser registrada em `plans/textura/` e consumir o evento/estado funcional comprovado. Não se deve mover para Textura cooldown, coeficiente, damage type, provider, hook, gate, anti-abuso ou causalidade.

## Critério de fechamento desta reorganização

- `plans/textura/` existe e é descobrível pelo README raiz de planos;
- a fronteira de ownership está explícita;
- os planos mistos auditados apontam para companions de apresentação;
- os companions não redefinem authority funcional;
- animação/VFX/áudio/assets sem especificação real permanecem explicitamente não definidos;
- nenhum código runtime, resource asset ou provider é alterado por esta reorganização.
