# Alpha 2 progression runtime boundaries

The core is intentionally independent from NeoForge and all optional mods. Runtime adapters convert real game events into the following contracts.

## Character XP
`CharacterXpAward` carries the XP amount, source id and attributed progression domains. `ProgressionService.applyXp` converts level gains into ledger-backed LEVEL passive points. Vanilla XP is never consumed.

Candidate runtime feeds:
- combat / Epic Fight: credited kills and meaningful combat milestones;
- Iron's / Ars / Goety: real player casts and progression actions, never synthetic proc echoes;
- mining / TFC / Oritech / Create: resource extraction and meaningful processing milestones with anti-cheese keys;
- survival / exploration: first discovery and environmental milestones;
- healing / support: effective healing, not overheal spam;
- engineering / logistics: achievements or bounded first-time systems rather than per-tick machine output.

## Bosses
`BossRewardRegistry` chooses point values while adapters choose safe reward keys. Vanilla defaults to 3, Cataclysm to 5 and Apotheosis/Apothic to 2. Random Apothic bosses must use bounded tier/archetype keys rather than UUIDs.

## Classes
Main-tree Final Triads are persistent investments. `ProgressionService.unlockClass` persists the class and spends an abnormal bridge surcharge exactly once. Natural adjacent confluences have no extra bridge surcharge.

## Specializations
`SpecializationResolver` sits after classes and consumes actual mastery lanes/tags. Iron's uses real school ids; Ars uses spell composition lanes; Epic Fight uses weapon/combat lanes; Create/Oritech/AE2 use engineering lanes.

## Optional integrations
Optional adapters must fail closed for mechanics they own but must not prevent the core mod from loading when that provider mod is absent.
