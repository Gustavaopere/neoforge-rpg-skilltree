# 21. Atualização da modlist tecnológica — 2026-09-07

[← Índice do guia](README.md)

> A authority de presença/JAR/runtime é [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md). Este capítulo registra apenas o efeito tecnológico do delta.

## Updates do stack tecnológico

- **Create: Bits 'n' Bobs**: `bits_n_bobs-2.3.0.jar → bits_n_bobs-2.3.1.jar`, runtime `2.3.1`.
- **Create: Copycats+**: `copycats-3.0.8+mc.1.21.1-neoforge.jar → copycats-3.0.9+mc.1.21.1-neoforge.jar`.
- **Create: Cyber Goggles**: `8.5.1 → 8.5.2`.
- **Lychee Tweaker**: `6.6.1 → 6.7.0`.
- **Petrolpark's Library**: `1.5.8 → 1.5.9`.

Esses updates alteram a identidade física corrente, mas não autorizam inferir mudança de API/hook sem inspeção do provider correspondente.

## Bits 'n' Bobs — decisão curatorial preservada

Apesar do update físico para `2.3.1`, a decisão de curadoria anterior **Tirar** permanece vigente. O JAR continua `Instalado` porque ainda aparece na modlist física. A atualização de versão não cancela a incompatibilidade/duplicidade de authority documentada com o stack atual.

## CERBON's API não é provider tecnológico

`CerbonsAPI-NeoForge-1.21-1.3.0.jar` entrou na modlist como dependência de Bosses of Mass Destruction. Embora seja infraestrutura de software, ela não representa capacidade tecnológica do gameplay e não deve entrar na árvore como provider de tecnologia.

## Bridges cross-domain

`Ironsable x Wind's Spellbooks` pertence ao domínio magia↔física/Sable e está documentado no guia de Magia. A existência de interação com blocos simulados não transforma a bridge em provider tecnológico independente.

## Regra operacional

Para os cinco mods atualizados acima, o delta de 07/09 substitui as linhas de presença/JAR/versão do snapshot de 06/09. Para os demais módulos tecnológicos, o snapshot anterior permanece válido até novo delta explícito.
