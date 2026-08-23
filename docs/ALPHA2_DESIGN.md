# Alpha 2 Design Specification

## Goal
Turn the Alpha 1 architecture into the progression foundation that the playable NeoForge runtime will consume.

## Character progression
- Character Level is independent from vanilla Minecraft experience levels.
- Default maximum Character Level is 100.
- Each Character Level gained grants 1 passive point through a source-aware point ledger.
- XP is awarded by meaningful gameplay and later adapters may feed combat, magic, mining, survival, crafting and engineering actions into the same Character XP system.
- Vanilla XP remains untouched for enchanting/anvils/mod compatibility.

## Passive point ledger
Point totals retain provenance. Initial sources are LEVEL, BOSS, ADVANCEMENT, ADMIN and MIGRATION.
Respec spends/refunds the aggregated point pool without losing provenance of earned points.

## Boss rewards
Boss rewards are granted only on the first credited defeat of a reward key per character.
Default reward priorities for Alpha 2:
- `cataclysm:*` bosses: 5 passive points.
- Apothic/Apotheosis bosses: 2 passive points.
- Vanilla Minecraft bosses: 3 passive points.
- Additional bosses are datapack-driven and can define exact values.
Repeated kills can still grant Character XP but not first-kill passive points.

## Main regions and class unlocking
The domain vocabulary expands beyond Alpha 1 to support HEALING, MINING, OCCULT and LOGISTICS while preserving all Alpha 1 domains.
Each major region ends in a Final Triad of three capstones, each with ranks 0..3. A region is complete only at 3/3 + 3/3 + 3/3 = 9 final-capstone points.

Class trees live beyond the ends of main-tree regions:
- A pure class may require one completed Final Triad.
- A hybrid class normally requires the completed Final Triads of both required regions.
- Adjacent regions use a natural confluence with no abnormal bridge surcharge.
- Non-adjacent region combinations are allowed but require an abnormal bridge investment; default cost is 10 passive points.
- Class definitions are data-driven so not every mathematical pair must become a class.
- Specialized systems such as Iron's schools are unlocked after class progression rather than being treated as classes themselves.

## Tree planner
The graph model must support selecting a target node and calculating a shortest legal route from the player's currently allocated nodes. Later UI will render that route as a planned/highlighted path.

## Morph progression
Identity 2 / Ars Morph runtime integration will be optional and adapter-based.
The core policy separates form categories:
- DRUID: natural/animal forms, with progression tiers for land/aquatic/flying/magical-natural forms.
- METAMORPH: humanoid/monster/aberrant forms and broader LivingEntity transformations.
- Technical entities and explicitly blacklisted forms are never eligible.
The runtime adapter must deny morph activation when the required class/node permission is missing.

## Apothic integration
Apotheosis/Apothic is a first-class integration target.
The skill tree does not clone its gem or affix system. Instead it modifies real Apothic mechanics through adapters:
- gem socket capacity/opportunities,
- gem effectiveness/power modifiers,
- affix-related bonuses where API/hooks allow,
- canonical Apothic Attributes integration,
- boss rewards for Apothic bosses.
Attunement Curios sockets and Apothic gem sockets remain distinct mechanics.

## Runtime target
NeoForge 1.21.1. The pure Java core remains dependency-free and testable with Java 21; NeoForge and mod-specific adapters sit outside that core.
