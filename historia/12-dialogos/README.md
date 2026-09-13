# 12 — Diálogos

Esta pasta contém diálogos narrativos versionados quando o conteúdo for grande ou ramificado o bastante para merecer arquivo próprio.

## Regra de autoridade

Diálogo descreve fala, escolhas, precondições e intents. Não é storage de runtime e não altera o save diretamente.

## IDs

- diálogo: `DLG-####`

IDs não são reciclados depois de entrarem em `main`.

## Organização recomendada

- `npcs/` — conversas centradas em NPCs;
- `quests/` — diálogos ligados a oportunidades/quests;
- `ambientais/` — interações narrativas sem NPC central.

As subpastas só precisam existir quando houver conteúdo real.

## Formato

O scaffold genérico de diálogo pertence à Minecraft Mod Factory em `narrative/templates/TEMPLATE-DIALOGUE.md`; aplique `historia/narrative-authoring-profile.json` para as regras estruturais desta campanha. Um framework como Ink só substitui o formato editorial após PoC e adapter explícitos.
