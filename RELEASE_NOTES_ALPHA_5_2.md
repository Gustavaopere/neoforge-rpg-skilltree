# Alpha 5.2 — Ars Nouveau depth pass

This chronological checkpoint revisits the Alpha 2 provider after the verified Malum and Iron depth work. Provider maturity is tracked as the Ars 2.x retrospective even though the project version is `alpha.5.2-dev`.

## Native Ars mana economy
Ars Nouveau 5.13.0 exposes public `MaxManaCalcEvent` and `ManaRegenCalcEvent` boundaries after its own gear, glyph and spellbook-tier calculations. The RPG tree now participates at those native boundaries instead of pretending Ars uses Iron's attributes.

Shared Arcane progression maps into Ars as follows:
- `rpgskilltree:arcane_000` (Arcane Awakening): +20 Ars max mana.
- `rpgskilltree:arcane_002`: +3% Ars native mana regeneration.
- `rpgskilltree:arcane_037`: +35 Ars max mana.

The emergent Sorcerer identity now has a provider-native mechanical identity:
- +10% Ars max mana after the flat shared-Arcane bonuses;
- +5 percentage points to Ars native mana regeneration.

The calculation uses the server progression state and the already synchronized client progression snapshot, so the authoritative value and HUD-side calculation follow the same policy.

## Familiar summoning is a tree mechanic
Ars exposes a cancellable `FamiliarSummonEvent` immediately before the familiar entity is added to the level. Familiar summoning now requires entering the unified SUMMONING branch through `rpgskilltree:summoning_000`.

This deliberately does **not** require finishing the entire Summoner class. A player can begin using familiars when they commit a point to Summoning, then naturally emerge as Summoner later; Sorcerer/Summoner hybrids remain possible. Creative players bypass the progression gate and fake players are ignored.

No repeatable mastery is awarded from toggling a familiar, avoiding a trivial summon/dismiss XP farm.

## Composition mastery now respects real mana commitment
Ars casts previously granted the same provider/composition mastery regardless of whether the recipe cost almost nothing or consumed a large mana budget. The same bounded intensity model introduced during the Iron review now uses `Spell.getCost()`:
- low-cost casts preserve the existing 3 XP provider/composition floor;
- expensive compositions can raise provider and every actually used semantic lane up to 7 XP;
- the intensity is capped, preventing extreme addon recipes from creating unbounded mastery;
- shared `magic:casting` remains fixed at 2 so cross-provider class identity is not biased by spell cost.

## Why glyph learning is not intercepted yet
Ars 5.13.0 exposes glyph ownership through `IPlayerCap`, but glyph learning itself is performed inside the Glyph item without a dedicated public unlock event. A generic right-click interception would have target-dependent bypass/interaction edge cases. The project therefore does not add a fragile pseudo-gate merely to claim coverage. Glyph-tier study remains an Alpha 2.x follow-up if a robust hook can be implemented without breaking normal item/block/entity interaction.

## Next retrospective provider
Epic Fight: stamina, dodge/guard/parry, skills and combat state beyond successful weapon-hit mastery.
