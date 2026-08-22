# Create / Engineering integration

Verified base target: Create 6.0.x on NeoForge 1.21.1. The user's current modlist has a very large Create ecosystem, so this adapter must be capability/tag driven and conservative about machine ownership.

## Engineering domain
The main tree has a full Engineering region. Engineering mastery can be subdivided into:
- `create:kinetics`
- `create:automation`
- `create:logistics`
- `create:artillery`
- `create:aeronautics`
- `create:power`

Actions must be attributable to a player before they grant XP or receive player-specific effects. We should not globally increase arbitrary machines on a server just because one player has a node.

## Technomancer
Technomancer is an emergent ARCANE + ENGINEERING archetype. This is especially appropriate because the pack already contains three bridge ecosystems:
- Ars Creo: Ars Nouveau + Create.
- Ars Technica: Create-centered Ars addon; current 1.21.1 release includes Technomancer content such as Technomancer armor and Source Motor.
- Create: Wizardry: Create + Iron's Spells integration.

Technomancer nodes should therefore buff interactions and mechanics that are genuinely both magical and technological, not merely give independent `+magic` and `+engineering` numbers.

Examples:
- Source-assisted kinetic devices become more efficient for the player.
- Casting through/with compatible techno-magical items can gain extra effects.
- Engineering actions contribute to technomancy mastery; actual spellcasting contributes to the magic side.
- Hybrid keystones can require both `create:*` and `magic:casting` mastery thresholds.
