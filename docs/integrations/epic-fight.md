# Epic Fight integration

Verified against `Antikythera-Studios/epicfight` branch `1.21.1`.

## Native hooks we can use
`EpicFightEventHooks` exposes server-side damage delivery, kill, dodge, stun, attack speed/damage, combo and skill-cast/consume events.
`CapabilityItem` and `WeaponCategory` let bonuses target actual Epic Fight weapon categories instead of item-name lists.
The stock `SwordmasterSkill` demonstrates the intended pattern: test the weapon category and modify attack speed through an Epic Fight event listener.

## Progression model
Weapon mastery is earned from actual Epic Fight combat actions. Generic Martial/Agility nodes remain weapon-agnostic; deeper branches specialize by registered weapon category and Epic Fight skill behavior.

Candidate branches:
- Sword/longsword/tachi mastery
- Heavy weapons / impact
- Dual wield / combo
- Guard/parry
- Dodge/mobility
- Weapon-skill specialization

Addons that correctly register Epic Fight weapon capabilities/categories should be picked up without explicit item lists.

## Canonical A0001-A0050 services

Verified functional sources:

- Notion A0010/A0011/A0012: Fury is a shared resource capped at 100. A0010 specifies rank and target-switch multipliers but no base gain.
- Notion A0046/A0048: Focus is capped at 100; A0048 arms at 80 after 1.25 seconds and consumes 50 for the next fully drawn shot even when it misses. A0046 does not specify its time-based rate or distant-hit amount.
- [NeoForge `CriticalHitEvent`](https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/event/entity/player/CriticalHitEvent.java) is the mutable canonical melee decision in `Player.attack` and fires on both logical sides.
- [NeoForge `ArrowLooseEvent`](https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/event/entity/player/ArrowLooseEvent.java) proves bow release intent, but can still be cancelled and does not identify the spawned projectile.
- [NeoForge `EntityJoinLevelEvent`](https://github.com/neoforged/NeoForge/blob/1.21.1/src/main/java/net/neoforged/neoforge/event/entity/EntityJoinLevelEvent.java) supplies the actual projectile spawn used to finalize shot correlation before any damage event.
- [Epic Fight `SkillConsumeEvent`](https://github.com/Antikythera-Studios/epicfight/blob/1.21.1/src/main/java/yesman/epicfight/api/event/types/player/SkillConsumeEvent.java) is explicitly a cancellable resource-check event.
- [Epic Fight `PlayerPatch.consumeForSkill`](https://github.com/Antikythera-Studios/epicfight/blob/1.21.1/src/main/java/yesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch.java) posts `SkillConsumeEvent`, then checks the predicate, then invokes the resource consumer. There is no public post-consume receipt.

Implemented contracts:

- `CanonicalActionIdentity` keeps one actor/action identity across NeoForge, Epic Fight PRE/POST and projectile callbacks. Derived effects retain the action ID and increment `procDepth`.
- `CanonicalEventLedger` makes each root consumer idempotent and rejects proc-depth claims.
- `CanonicalCriticalService` retains one boolean and performs at most one bonus roll. NeoForge applies the decision; Epic Fight only reads it.
- `CanonicalFuryService` owns producer/consumer dedupe and uses `NotionCombatPerkState`'s 100-point cap. Undefined A0010 base production is explicitly disabled.
- `CanonicalFocusService` owns preparation, release-time cost, cooldown, projectile links and once-per-shot hit claims. Runtime consumes A0048 at the first confirmed projectile spawn, so a later miss cannot preserve the prepared shot.
- `CanonicalStaminaService` accepts only exact post-consume evidence. `EpicFightStaminaPolicy.exactCostSupport()` reports `UNSUPPORTED_PRE_CONSUME_ONLY`, so A0029/A0042 refunds are not fabricated.

Known public-API limits:

- A0046 Focus production remains disabled because neither production rate nor distant-hit amount is specified.
- Notion does not quantify a “sudden direction change” for stable bow aim. The runtime invalidates preparation on damage, sprint, item-use cancellation and bow-use loss; no arbitrary rotation threshold is invented.
- Public NeoForge bow events do not carry a shot ID. Correlation therefore requires the same eligible owner, a bow-backed `AbstractArrow`, and a two-tick spawn window. An addon that deliberately spawns an indistinguishable owner-tagged bow arrow inside that window cannot be distinguished without an internal mixin/provider receipt.
- Epic Fight has no public exact post-stamina-consumption event. A0029 recovery and the stamina-refund portion of A0042 remain unsupported; A0042 behavior itself is outside this infrastructure branch.
