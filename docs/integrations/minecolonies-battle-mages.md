# MineColonies Battle Mages × Iron's Spells 'n Spellbooks

Status: **implemented on PR #288; awaiting final CI validation and merge**.

This document is the canonical design and validation contract for the Battle Mage integration. It was persisted after a documentation gap was identified during final PR validation; it records the contract already implemented and reviewed rather than introducing a redesign.

## Audited provider baseline

Target runtime: NeoForge 1.21.1 / Java 21.

Audited modpack baseline:
- MineColonies `1.1.1375-1.21.1-snapshot` (MineColonies runtime reports the base `1.1.1375` version for this audited snapshot).
- Iron's Spells 'n Spellbooks `1.21.1-3.16.3`.
- Epic Fight `21.17.3.1` when present.
- Epic Colonies `21.0.8` when present.
- EFIS compatibility layer when present in the pack.

Provider version gates are fail-closed. A provider that is absent or outside the explicitly audited contract must not silently activate an unverified path.

## Authority boundaries

### MineColonies owns citizen and colony authority

MineColonies remains authoritative for the citizen entity, colony membership, citizen lifecycle, employment/job state and protected-colony relationships. The RPG Skill Tree integration must not create a parallel citizen model or bypass MineColonies authority.

A Battle Mage is a MineColonies citizen using the Battle Mage job/AI integration. Registration and lifecycle hooks are attached to MineColonies-native surfaces and are active only when the audited provider contract is satisfied.

### Iron's owns spell semantics and casting

Iron's Spells 'n Spellbooks remains authoritative for registered spells, spell level/power semantics, spellbook contents, casting behavior, mana/cooldown behavior and provider effects.

The integration must not create a duplicate spell registry, duplicate mana pool, substitute spell implementation or generic replacement effect. Provider-native casting is mandatory and citizen casts use Iron's `CastSource.MOB` semantics.

## Loadout and spell candidates

The candidate set is derived from the citizen's actual equipped spellbook/item loadout. A configured profile does not grant a spell the citizen does not possess.

Spell identities are represented as Minecraft `ResourceLocation` values throughout the canonical runtime model. Raw strings may be parsed only at serialization/provider boundaries; the public profile contract must not expose a String-first spell identity API.

Direct scroll casting is intentionally **fail-closed** in the initial contract. A scroll item is not treated as a spellbook or converted into an alternate generic cast path unless a future audited design explicitly enables provider-native scroll semantics.

## Data-driven spell profiles

Battle Mage tactical metadata is data-driven under:

`data/rpgskilltree/battle_mage_spell_profiles/*.json`

A profile describes tactical properties required by the integration without redefining Iron's spell mechanics. Reload publication must be atomic: invalid reload data must not leave a partially published catalog.

Profile identity and catalog lookup use `ResourceLocation`.

When multiple valid candidates remain, deterministic ordering is:
1. higher configured tactical priority;
2. lower spellbook index (`bookIndex`);
3. lexical spell `ResourceLocation` identifier.

This order is part of the contract and must remain deterministic across equivalent inputs.

## Targeting, safety and friendly fire

The integration is server-authoritative. Before any hostile cast, the controller must validate the current citizen, target, MineColonies relationship and spell profile.

Protected allies and friendly MineColonies citizens must not be hit through the Battle Mage integration. Safety is fail-closed: if the integration cannot prove a hostile cast is safe, it does not cast.

For hostile area-of-effect spells, a known positive affected-area footprint is required unless the provider/profile semantics explicitly establish that allies cannot be harmed. Unknown, missing or non-positive hostile AoE radius/footprint therefore blocks the cast.

No fallback may convert an unsafe or unknown spell into a generic damage effect.

## Casting pipeline and causality

An approved cast goes through the Iron's citizen bridge and provider-native cast path with `CastSource.MOB`.

The integration tracks cast causality so one Battle Mage decision does not produce duplicate integration-side processing. Re-entrant/provider events must not be interpreted as an additional autonomous decision by the RPG Skill Tree runtime.

Citizen casts do **not** award autonomous player RPG/mastery XP. Player progression must remain tied to player-authoritative actions; a colony citizen cannot farm player progression merely by casting on the player's behalf.

Cooldown, mana availability, cast viability and provider rejection remain authoritative to Iron's/provider state. Integration bookkeeping must never override a provider rejection.

## Lifecycle, AI and job integration

The Battle Mage integration consists of MineColonies registration/job surfaces, lifecycle hooks and a combat AI/controller. It must:
- activate only for valid MineColonies citizen/job state;
- stop acting after invalidation/removal or loss of authority;
- avoid duplicate AI/controller attachment;
- reevaluate provider/version readiness rather than assuming optional integrations are present;
- fail closed when required authority cannot be established.

The integration does not globally replace MineColonies combat AI for unrelated citizens.

## Epic Fight / Epic Colonies / EFIS compatibility

Epic combat integrations are optional compatibility providers, not hard dependencies of the core Battle Mage feature.

When Epic Fight, Epic Colonies and/or EFIS are present at audited versions, Battle Mage runtime validation must honor their supported linkage and must not bypass their combat/citizen compatibility surfaces.

Optional Epic linkage must not become a hard classloading dependency. Missing optional classes/providers must degrade by disabling only the optional bridge or by failing the affected path closed; they must not crash a provider-free server.

No compatibility fallback may silently change the Battle Mage's spell identity, targeting authority or Iron's casting semantics.

## Fail-closed rules

The runtime must refuse the affected action/integration when any required fact is not established, including:
- unsupported MineColonies/Iron's provider version;
- missing citizen/colony authority;
- missing or invalid actual spellbook candidate;
- unknown spell identity/profile boundary;
- unsafe friendly-fire relationship;
- unknown hostile AoE footprint when ally safety is not guaranteed;
- provider-native cast path unavailable or rejected;
- ambiguous optional integration linkage where continuing could change semantics.

Fail-closed means no generic bonus, synthetic projectile, vanilla-damage substitute, duplicate mana implementation or silent redesign.

## Deduplication and anti-abuse

The implementation must keep one authoritative decision/cast accounting path per Battle Mage action. Lifecycle attachment, provider callbacks and cast tracking must be idempotent where repeated events are possible.

The integration must not allow:
- repeated lifecycle hooks to attach duplicate combat logic;
- provider callbacks to double-count a cast;
- citizen casts to generate player mastery/RPG XP;
- missing safety metadata to become an implicit permissive default;
- malformed reload data to partially replace the active profile catalog.

## Required validation

Final acceptance requires fresh evidence on the PR head for all applicable gates:

### Unit / contract tests
- installation/provider version contract;
- MineColonies API surface and registration contract;
- Battle Mage registration/job/AI surfaces;
- actual spellbook loadout resolution;
- deterministic priority → `bookIndex` → ID ordering;
- `ResourceLocation`-only canonical spell identity;
- Iron's bridge behavior and provider rejection;
- cast causality/no autonomous player XP;
- profile parsing/reload publication and invalid-data handling.

### NeoForge GameTests
- provider-free runtime remains loadable and safe;
- provider-present MineColonies + Iron's Battle Mage runtime;
- hostile/defensive/offensive targeting behavior;
- protected-ally/friendly-fire rejection;
- unknown hostile AoE footprint rejection;
- lifecycle/reload/authority behavior;
- representative provider-native casts.

### Compatibility / build gates
- Battle Mage Epic compatibility workflow;
- optional-integrations validation;
- NeoForge build and JAR verification;
- dedicated-server smoke where configured;
- Sonar provider-free and provider-present transformed GameTest coverage;
- Sonar analysis and New Code Quality Gate.

A green test from an older commit is not sufficient after a behavior-affecting change or synchronization with `main`; final merge requires current-head evidence.

## Change-control boundary

Technical fixes may preserve this contract. A change that alters the feature's identity, provider authority, spell source, targeting semantics, casting authority, MineColonies job topology, essential gates or player-progression semantics is a redesign and must be reviewed explicitly rather than introduced silently.

Future support for direct scroll casting, additional provider-owned loadout systems or materially different combat authority therefore requires a new audited contract/update to this document before runtime implementation.

## Acceptance definition

The Battle Mage integration is complete only when:
1. the runtime matches this contract;
2. provider/version gates and fail-closed behavior are verified;
3. provider-free and provider-present tests pass on the final head;
4. applicable Epic compatibility checks pass;
5. NeoForge build/server validation passes;
6. Sonar analysis and required Quality Gate pass;
7. PR #288 is merged to `main`;
8. the resulting `main` SHA and the presence of this canonical document are confirmed after merge.
