# Changelog

This file is the single chronological development record for the project. Older standalone `RELEASE_NOTES_*`, temporary sync/checkpoint notes and obsolete progress files were consolidated here on 2026-08-23; their full original contents remain available in Git history.

## 1.0.0-alpha.5.4.1-dev — Unreleased

Current NeoForge 1.21.1 development line. The latest pre-consolidation runtime checkpoint (`117c5cbe73daa4b2c60b167b4a05dbb89a0b0c60`) reported `rpgskilltree/full-build = success`.

### Unified progression/runtime foundation
- Independent Character Level 1–100 and source-aware passive-point ledger.
- First-credit boss rewards with datapack configuration; defaults remain Cataclysm 5, vanilla Minecraft 3 and Apotheosis/Apothic 2.
- Main progression vocabulary across Martial, Agility, Vitality, Healing, Arcane, Engineering, Mining, Survival, Summoning, Occult and Logistics.
- 3/3/3 final-domain triads, persistent emergent classes, abnormal bridge costs for distant hybrids, class trees, specializations, Warlock pacts, morph policy and server-authoritative purchase/respec flow.
- 512-node main-tree blueprint, path planning, persistence/network synchronization, runtime data loading and tree viewer foundations.
- Mining/exploration/combat progression protections, including player-placed-ore tracking and reward anti-farm/backpressure infrastructure.
- Apotheosis/Apothic attribute, gem/socket and attunement foundations.
- Emergent-class resolution now exposes a dynamic Primary Class plus ordered Secondary Classes; class identity remains derived from progression rather than an irreversible player selection.
- Specializations may be classless when their definition has no eligible-class gate.
- `Industrialist`, `Logistician` and `Prospector` are reclassified from classes to specializations. Existing v4 saves are migrated semantically during decode: those unlocked identities move into specialization progress while mastery, discoveries, passive points and purchased nodes are preserved. The migration is idempotent and does not require a binary save-format bump.

### Alpha 1.x — Iron's Spells 'n Spellbooks
- Intentional Iron casts feed provider-wide and school mastery while automatic/proc-only origins are excluded.
- Arcane Awakening is the shared entry gate for player-driven spellcasting.
- Permanent spell inscription now requires real provider/school practice; tier 3+ learned magic requires the emergent Mage identity.
- Mastery intensity scales from bounded real spell resource expenditure.
- School/content addons that use Iron's normal spell/school APIs inherit the common path; bespoke native systems remain explicit compatibility work.

### Alpha 2.x — Ars Nouveau
- Ars composition and provider mastery use real casts and glyph semantics.
- Arcane investment and emergent Sorcerer modify Ars native max mana and regeneration.
- Familiar binding is tied to entry into the unified Summoning branch.
- Cast mastery scales from bounded real mana cost.
- Glyph-learning is deliberately not pseudo-gated through fragile generic interaction hooks while no stable dedicated unlock event is available.

### Alpha 3.x — Epic Fight
- Weapon-category progression is integrated without replacing Epic Fight's own skill slots, skillbooks, combo engine or animation state machine.
- Martial/Agility investment modifies native stamina/impact-facing progression and bounded real stamina costs.
- Skill-resource use and successful dodge state feed dedicated mastery lanes.
- Fake-player, creative/spectator and derived-proc farming paths are rejected.

### Alpha 4.x — Goety / Goety Iron / Goety Cataclysm
- Goety progression uses its real Soul Energy economy instead of introducing a parallel mana pool.
- Occult/Warlock investment modifies Soul gain and spell cost; Necromancer receives summon-specific efficiency.
- Soul-backed spell mastery scales from actual adjusted cost.
- Hostile servant kills are attributed through Goety ownership and feed servant/Summoning plus eligible class lanes.
- Bridge-friendly deduplication prevents the same underlying action from being rewarded twice.

### Alpha 5 — Malum / Gaze / Vestis
- Malum Spirit Reaping and collection feed typed provider and per-spirit mastery.
- Dynamic spirit affinities are identified by registry identity rather than a brittle fixed list.
- Malum-native Soul Ward/Arcane Resonance/Geas-facing progression is exposed through canonical tree effects.
- Soul rupture and scythe kill outcomes feed typed Malum mastery where the provider exposes reliable outcome state.
- Gaze extends shared Malum registries/systems; cosmetic-only Vestis receives no artificial progression lane.

### Current verification boundary
- Green CI/full-build is required for every runtime checkpoint.
- Dedicated-server startup smoke exists in the pipeline and has been used on prior verified checkpoints.
- A green build does not by itself promote the project to Beta; Beta begins only after a functional GitHub Release JAR and the project shifts primarily to correctness, balance, compatibility hardening and UX/release polish.
- Full modpack/player-flow testing remains a separate quality gate from isolated build/server smoke.

## Historical consolidated milestones

### 0.2.0-alpha.2 — runtime foundation
- Character progression, boss rewards, 24 seed classes, 25 specialization definitions, class/pact/morph contracts and the expanded 512-node tree were established.
- NeoForge persistence, networking, adapters and build/runtime validation were subsequently implemented on the same development lineage, so the old Alpha 2 draft/status files are superseded by the current track above.

### 0.1.0-alpha.1 — 2026-08-22
- Initial architecture/core milestone: canonical RPG stats, deterministic modifier resolution, emergent hybrid classes, specialized-tree gates, normalized spell/combat/engineering actions, proc recursion guard, Curios attunement planning and the original 420-node/664-edge tree blueprint.
- This was a source architecture milestone rather than an installable gameplay release.

## Roadmap source
`ALPHA_TRACK.md` is the current provider-by-provider implementation roadmap. Design documents under `docs/` remain authoritative for architecture and integration contracts; temporary release/checkpoint/synchronization marker files are intentionally not maintained as separate status sources.
