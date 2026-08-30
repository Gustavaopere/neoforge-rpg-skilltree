<!-- Atualização incremental de 2026-08-30. Fonte canônica no Notion: https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6 -->

[← Índice do guia](README.md)

# 14. Mobstein — fauna ressuscitada, experimentos, bosses e estruturas

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

**Mobstein** adiciona uma linha de gameplay centrada em ressurreição de mobs, animais utilitários, experimentos, necromancia corporal e encontros próprios. A release instalada é `5.4.4` para **NeoForge 1.21.1**, publicada em 04/05/2026. O projeto é client + server e requer **GeckoLib** na linha 1.21.1; o autor recomenda JEI para consultar receitas.

A progressão começa com o guidebook e a **Clinical Stretch**, usada para ressuscitar corpos durante a noite. Diferentes criaturas ressuscitadas têm utilidades próprias. Exemplos documentados pelo autor incluem:

- Axolotl ressuscitado: regeneration + night vision quando domesticado e próximo;
- Dolphin ressuscitado: montável, rápido na água e fornece water breathing;
- Silverfish ressuscitado: bônus voltado a mineração rápida em cavernas;
- Rabbit ressuscitado: jump boost;
- Warden ressuscitado e Frankenstein golem: guarda-costas estacionários/poderosos;
- outras criaturas ressuscitadas com comportamento, domesticação e utilidades próprias.

O mod também trabalha com **corpos, cabeças, órgãos, seringas, Surgery Stretch e Subject Assembly Machine**. Corpos podem fornecer partes e órgãos; experimentos e mannequins podem ser montados a partir de componentes. Há ainda criaturas especiais como Dr. Mobstenio e Igor.

## Estruturas e bosses

O conteúdo de exploração inclui:

- **Frankenstein Castle**, em swamp, como estrutura central de lore;
- **Witherstein Ruins**, em jungle;
- **Old Ruins**, também em jungle;
- **Witherstein**, boss ressuscitado por interação com seu esqueleto e Reviver Syringe, com três estágios documentados.

Isso coloca Mobstein simultaneamente nos eixos de fauna/pets, summons/ownership, bosses, estruturas, exploração e progressão discreta.

## "Perks" internas do Mobstein

O próprio Mobstein usa o termo `perk` para quatro componentes de criação na Surgery Stretch:

- `Attack`;
- `Health`;
- `Speed`;
- `Template`.

Essas **não são perks/nodes do RPG Skill Tree**. O Chat 1 deve manter essa distinção terminológica explícita para não interpretar dados do Mobstein como árvore RPG existente.

## Regra para perks do RPG Skill Tree

Mobstein passa a ser provider obrigatório de cobertura quando a fantasia da perk tocar:

- ressurreição de criaturas;
- corpos/cabeças/órgãos;
- criação de experimentos;
- allies/bodyguards ressuscitados;
- milestones de estrutura/boss;
- ownership de companion/summon quando houver hook real.

Não reduzir o provider a `+dano de summon` apenas porque seus mobs podem lutar. Antes de integrar, distinguir o tipo real de entidade e o evento causal disponível.

Mastery/reward não deve ser concedida por tick, por um ressuscitado apenas permanecer vivo/seguindo o jogador, ou por repetir indefinidamente a mesma ressurreição sem identidade deduplicável. Estruturas, boss kills, montagem ou ressurreições podem ser milestones apenas quando houver hook server-authoritative e identidade estável.

A necromancia do Mobstein é um domínio próprio. Não converter automaticamente seus corpos/ressuscitados em **Black Arcana Corruption**, **Goety Soul Energy**, **Malum spirits**, **Eidolon rituals** ou **Enshrouded Shroud**. Qualquer bridge futura precisa de contrato explícito e authority definida.

## Fonte auditada

- CurseForge oficial: `Mobstein : Revive animals and necromancy!`, projeto 1193873.
- Release NeoForge 1.21.1: `mobstein-5.4.4-neoforge-1.21.1.jar`, publicada em 04/05/2026.
