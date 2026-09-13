# 21. Atualização da modlist tecnológica — 2026-09-07

[← Índice do guia](README.md)

> A authority de presença/JAR/runtime é [`../MODLIST-DELTA-2026-09-07.md`](../MODLIST-DELTA-2026-09-07.md). Este capítulo registra apenas o efeito tecnológico do delta. Para decisões curatoriais explicitamente revistas em 07/09, prevalece [`../gameplay/CURATION-DECISIONS-2026-09-07.md`](../gameplay/CURATION-DECISIONS-2026-09-07.md).

## Updates do stack tecnológico

- **Create: Bits 'n' Bobs**: `bits_n_bobs-2.3.0.jar → bits_n_bobs-2.3.1.jar`, runtime `2.3.1`.
- **Create: Copycats+**: `copycats-3.0.8+mc.1.21.1-neoforge.jar → copycats-3.0.9+mc.1.21.1-neoforge.jar`.
- **Create: Cyber Goggles**: `8.5.1 → 8.5.2`.
- **Lychee Tweaker**: `6.6.1 → 6.7.0`.
- **Petrolpark's Library**: `1.5.8 → 1.5.9`.

Esses updates alteram a identidade física corrente, mas não autorizam inferir mudança de API/hook sem inspeção do provider correspondente.

## Bits 'n' Bobs — decisão curatorial revisada

Além do update físico para `2.3.1`, a decisão de curadoria foi **explicitamente revisada em 07/09/2026 para `Manter`**. A decisão `Tirar` de 06/09 permanece apenas como histórico e não deve ser usada para remover o JAR atual.

A incompatibilidade visual documentada com os Thermochemical Cogwheels de Create: Sulfuric Resonance 0.4.1 continua real. O pack aceita esse risco e mantém ambos; isso não significa que o conflito foi corrigido. Render-testar especificamente os Thermochemical Cogwheels após updates de Bits 'n' Bobs ou Sulfuric Resonance.

## CERBON's API não é provider tecnológico

`CerbonsAPI-NeoForge-1.21-1.3.0.jar` entrou na modlist como dependência de Bosses of Mass Destruction. Embora seja infraestrutura de software, ela não representa capacidade tecnológica do gameplay e não deve entrar na árvore como provider de tecnologia.

## Bridges cross-domain

`Ironsable x Wind's Spellbooks` pertence ao domínio magia↔física/Sable e está documentado no guia de Magia. A existência de interação com blocos simulados não transforma a bridge em provider tecnológico independente.

## Regra operacional

Para os cinco mods atualizados acima, o delta de 07/09 substitui as linhas de presença/JAR/versão do snapshot de 06/09. Para os demais módulos tecnológicos, o snapshot anterior permanece válido até novo delta explícito. Para Bits 'n' Bobs, a decisão vigente é `Manter` conforme a authority curatorial de 07/09.
