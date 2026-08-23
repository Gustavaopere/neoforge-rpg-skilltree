# Apotheosis / Apothic integration

Target pack versions at Alpha 2 planning time include Apotheosis 8.7.0, Apothic Attributes 2.10.1, Apothic Enchanting 1.6.1 and Apothic Spawners 1.4.0 on Minecraft 1.21.1.

## Design rule
The RPG tree must not recreate gems, affixes, rarities, sockets or their inventories. It modifies the real Apotheosis systems through public/event-facing integration points.

## Socket integration
The Apotheosis `1.21` source exposes `SocketHelper.getSockets(ItemStack)`, which posts `GetItemSocketsEvent`, and `SocketHelper.setSockets` clamps stored socket counts to 0..16. The planned runtime adapter should prefer the query event for skill-tree bonus sockets so respec can remove a virtual bonus without rewriting the item's stored sockets.

`GemSocketModifier` is the dependency-free core representation of that dynamic bonus. `ApothicIntegrationPolicy` enforces the same 16-socket ceiling.

## Gem integration
The tree may expose gem-focused notables/masteries, but actual gems remain Apotheosis gem instances and keep their category/purity validity rules. `GemPowerModifier` is a core contract for later adapter work; runtime implementation must verify a stable way to modify arbitrary gem bonuses before shipping a generic "gem power" node. If no safe generic hook exists, use category-specific tree effects derived from the real socketed gems instead of copying their bonuses.

## Attributes
Common Apothic Attributes are registered in the canonical stat catalog under their real `apothic_attributes:*` identifiers, including critical chance/damage, life steal, overheal, mining speed, projectile damage, healing received, armor/protection pierce/shred and dodge chance.

## Boss points
Apotheosis/Apothic bosses default to 2 first-credit passive points. The runtime adapter must use bounded stable reward keys (for example rarity/tier/archetype) rather than per-entity UUIDs, preventing random affix bosses from becoming an infinite passive-point farm.
