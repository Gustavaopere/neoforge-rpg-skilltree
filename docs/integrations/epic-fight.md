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
