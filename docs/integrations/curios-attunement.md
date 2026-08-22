# Curios Attunement

## Goal
Allow special tree nodes to unlock an equipment surface represented inside the skill tree, without consuming the player's ordinary ring/charm/etc. slots.

## Preferred implementation
Create a dedicated Curios slot type (for example `rpgskilltree:attunement`) and grant capacity using permanent Curios slot modifiers. Store/equip real ItemStacks in those slots and expose them through the Passive Skill Tree UI.

This preserves Curios item callbacks and attribute/tick behavior, which is safer than copying only item attributes into a custom attachment.

## Rules
- Default capacity: 0.
- Nodes/keystones grant +1 or more Attunement capacity.
- Optional item/tag allowlist for balance.
- On respec, keep lowest slot indices and eject overflow to player inventory; drop only as last resort if inventory is full.
- Never silently delete an attuned item.
- Attunement items cannot recursively grant additional Attunement slots unless explicitly whitelisted.
