# Narrative Authoring Contract — RPG Skill Tree

Status: **PROPOSED UNTIL MERGE. After acceptance into `main`, this becomes the repository-local working contract for AI-assisted campaign authoring.**

This is project-specific guidance, not a generic Minecraft Mod Factory skill.

## Authority by domain

- **Structured lore and Campaign Bible:** Grimoire/TTRPG.bot Campaign Bible and Foundations. This is the principal authority for structured lore, narrative causality, world facts, characters, organizations, places, relationships, knowledge, secrets and campaign state when those facts are registered there.
- **Versioned editorial source:** accepted `historia/` in `main`. This is the reviewed Git record for stable IDs, editorial documents, dialogues, evidence, lifecycle definitions and the versioned representation/history of campaign material.
- **Runtime/mechanics:** current RPG code/tests, latest physical modlist/JARs, exact provider evidence and current technical contracts.
- **Shared Minecraft engineering/art tooling:** `Gustavaopere/minecraft-mod-factory`.
- **Branches/PRs:** proposals until review/merge; newer draft content does not outrank established lore merely because it is newer.

Lore never proves mechanical capability. Mechanical capability never creates historical facts automatically.

When Grimoire and accepted `main` appear to differ, preserve the discrepancy and reconcile provenance/decision history explicitly. For structured lore, Grimoire is the principal domain authority, but that does not authorize blind overwrites of versioned files; inspect whether Grimoire, `main`, or both require an intentional update/retcon. Never synchronize automatically in either direction.

If Grimoire or another required source is unavailable and the missing fact is central to canon, keep the decision `BLOQUEADO/FAIL-CLOSED` or explicitly undecided. Do not invent missing lore merely to continue production.

## Required preflight

Before substantive narrative authoring:

1. read relevant accepted `historia/00-canone/` and existing records from `main`;
2. inspect open branches/PRs affecting the same narrative IDs so concurrent proposals are not mistaken for accepted material;
3. when available, run `python historia/tools/story_inventory.py --format json` to obtain a current editorial index of stable IDs, states and references before creating new entities;
4. treat that inventory as spoiler-bearing/editorial output: do not expose it in player-facing or public logs when it contains undiscovered material;
5. search stable IDs/names to avoid duplicate NPCs, factions, locations, quests or evidence;
6. consult Grimoire for central lore, Campaign Bible/Foundations and possible pre-existing entities when accessible; reconcile any difference with accepted `main` instead of selecting one silently;
7. consult relevant Stage 08 contracts for knowledge, chronology, relationships, opportunity lifecycle, consequences and death/return;
8. verify the latest modlist/provider evidence before assigning a real mechanic to a mod;
9. consult Compendium/runtime identities before binding a narrative place to a biome, structure or dimension.

Absence of information is a valid project state.

## Permanent authoring rules

- Keep provider systems semantically distinct; thematic similarity does not create a universal energy/resource.
- The player is not retroactively the reason the world existed and is not a chosen one unless canon explicitly changes.
- NPCs, factions, journals and quest markers are not omniscient.
- Player knowledge and character knowledge are distinct.
- Do not protect future plot by forcing NPC survival/availability without a causal system.
- Do not create named entities merely to fill templates or production quotas.
- Do not infer biome climate, resources, geology or settlement suitability from a translated biome name alone.
- Do not collapse multidimensional relationships into a single reputation/friendship score.
- New material on a branch remains a proposal until it is reconciled with the relevant authorities and deliberately accepted.

## Stable IDs and validation

Use the existing ID families under `historia/` (`HIST`, `ARC`, `NPC`, `QST`, `FAC`, `SET`, `LOC`, `EVT`, `EVD`, `END`, `DLG`, etc.). Reference entities by ID, not only by display name.

For a primary entity file whose filename starts with a concrete stable ID, that ID must match the concrete stable ID declared directly in the entity H1. A filename/H1 mismatch is a fatal structural error.

Auxiliary documents such as authoring sheets, asset briefs and lifecycle notes should reference an existing stable ID without redeclaring the entity in a top-level heading that starts directly with that ID. For example, prefer `# Ficha de autoria — NPC-0001 — ...`, not a second `# NPC-0001 — ...`.

When available in the branch, run:

`python historia/tools/validate_story.py historia`

Use `--strict-references` when validating a closed batch. For dialogue batches also run `python historia/tools/validate_dialogues.py`.

Both validators are spoiler-safe by default and should emit aggregate rule/count output rather than narrative IDs, file paths or editorial details. Use `--reveal` only for deliberate editorial debugging. Do not paste reveal-mode output into player-facing or public status reports unless the user explicitly requests those details.

Do not claim full-repository validation unless the command actually ran against the complete materialized tree. When only a subset was materialized, report the exact validation scope.

## Knowledge and evidence

Important facts need plausible acquisition paths. Distinguish knowledge, suspicion, rumor, witness, evidence, causation and inference.

Every important `EVD-####` should declare provenance, possible knowers, what it proves, what it merely suggests, what it does not prove, uncertainty/reliability and how it may be altered, lost or transmitted.

Repeated rumor does not automatically become independent corroboration.

## Quest/opportunity lifecycle

Follow Stage 08. Separate availability, discovery, engagement and resolution when applicable. Support unknown-but-eligible, rumored, declined, ignored, pre-resolved, resolved-by-others, transformed, obsolete and retrospective discovery when causally valid.

A player who never knew an opportunity existed must not receive a fictitious retroactive failure. Autonomous progression requires an explicit actor/cause; player inactivity alone is not causal justification.

## Relationships, memory and identity continuity

Follow `historia/00-canone/relacoes-memoria-e-identidade.md` together with Stage 08.

For persistent actors, keep at least these dimensions conceptually distinct when relevant: `affection`, `trust`, `respect`, `fear`, `dependency` and `ideological_alignment`.

Relationship is directional (`source -> target`). Do not mirror it automatically. Grievance, debt and favor remain separate ledgers/facts rather than being hidden inside a generic score.

Important relationship changes need causal provenance. Memory and knowledge are not equivalent: an actor can remember false information, know a fact through evidence without witnessing it, or lose memory without erasing the historical event.

Death/return never restores relationship, memory or identity by blind snapshot. Reconcile through the Stage 08 Identity Continuity contract.

## Dialogue and NPC voice

For recurring NPCs, establish a voice profile before writing large dialogue volumes: formality, rhythm, sentence length, vocabulary, humor, directness/evasion, address forms, avoided subjects and incompatible speech patterns.

Calibration lines are non-canonical unless attached to an actual event. Dialogue may reveal only knowledge justified by state/provenance.

## Visual authoring

Separate textual visual brief, concept/portrait, real Minecraft skin texture and 3D/in-game validation. Reuse the Minecraft Mod Factory visual/Blockbench pipeline instead of recreating visual tooling here.

A generated portrait is not itself a valid Minecraft skin UV.

## Geography

Narrative `SET-####` and `LOC-####` may exist before a physical worldgen binding is fixed. Bind later to verified `BIOME`, `STRUCTURE` or `DIMENSION` identities from the current Compendium/runtime. Keep provider-specific physical claims unbound until proven.

## Epilogues

Use the existing `END-####` family and the editorial epilogue composition contract when available. Epilogue fragments must derive from persistent/derivable Narrative Core state rather than a second parallel ending-state system.

Conflict/suppression between fragments must be explicit and ordering deterministic. Do not hardcode a universal morality score or require a single final A/B choice. Player knowledge and objective world state remain distinct when selecting or wording fragments.

## Spoiler policy

Unless the user explicitly asks for narrative details, reports should expose IDs/types only when safe and necessary, plus counts, editorial state, validation results, blockers and PR/CI state. Do not reveal hidden motives, mystery solutions, betrayals, secret conditions, alternate endings or undiscovered consequences by default.

Prefer validator default output. Reveal-mode validator output and `story_inventory.py` output are editorial surfaces and may contain spoiler-bearing identifiers/titles/paths.

## Cost policy

Narrative authoring must remain usable with zero required incremental spend. Paid APIs/subscriptions may only be optional experiments with a free/open alternative. Trials are not permanent dependencies. Auxiliary external story tools never become canon authority merely by producing content.

## Completion gate

Before calling an authoring batch complete, confirm:

- Grimoire was consulted for central structured lore when accessible, or the dependency was explicitly left fail-closed;
- accepted `main`, relevant branches/PRs and other applicable authorities were checked;
- Grimoire↔GitHub discrepancies were reconciled or explicitly blocked rather than silently overwritten;
- current inventory/search used to reduce duplicate-entity risk;
- inventory/reveal-mode output kept editorial unless explicitly requested;
- no known duplicate stable entity introduced;
- primary filename and direct entity H1 IDs agree;
- editorial states explicit;
- knowledge/provenance preserved;
- relationship changes preserve direction, dimensions and causal provenance when applicable;
- provider/worldgen claims proved or left unbound;
- lifecycle supports legitimate alternate participation states;
- voice consistency checked where applicable;
- spoiler-safe reporting preserved;
- story/dialogue validation run when applicable and scope reported truthfully;
- no paid service became mandatory;
- tooling/contracts and substantive story content are separated in PRs when practical.

## Related documents

- `historia/README.md`
- `historia/00-canone/`
- `historia/00-canone/relacoes-memoria-e-identidade.md`
- `historia/10-finais-e-epilogos/`
- `historia/11-ia-e-autoria/`
- `historia/11-ia-e-autoria/12-grimoire-github-sync.md`
- `historia/templates/`
- `historia/tools/`
- `plans/08-quests-progression-hooks/`
- `plans/10-compendio-natural/`
- `docs/compendium/`
- `PROJECT-INSTRUCTIONS/engineering/AGENT-WORKFLOW.md`
