# Third-Party Notices and Provenance

This public repository integrates with and has been informed by third-party Minecraft mods and libraries. This file records the known provenance/compliance status of those sources.

**Important:** listing a project here does not mean its code or assets are included in this repository. `REFERENCE_ONLY` means the project may inform behavior, architecture or compatibility while implementation remains independent. Exact file-level provenance must be added before any substantial copied/adapted material is merged.

Status vocabulary:

- `REFERENCE_ONLY` — no third-party code/assets are intended to be copied;
- `DEPENDENCY_API` — this project writes its own integration against an allowed dependency/API;
- `DERIVED_CODE` — source has been copied/adapted and requires exact provenance + license compliance;
- `DERIVED_ASSET` — asset has been copied/adapted and requires separate rights;
- `REVIEW_REQUIRED` — license/version/compatibility not sufficiently verified for copying;
- `PERMISSION_REQUIRED` — public copying requires additional permission.

## Verified / partially verified upstreams

| Upstream | Known license status | Current intended use | Compliance note |
| --- | --- | --- | --- |
| [NeoSync](https://github.com/breakinblocks/NeoSync) | MIT | `REFERENCE_ONLY`; Stage 12 may later use `DERIVED_CODE` | If code is adapted, record exact commit/files and preserve MIT copyright/license notice. |
| [MapFrontiers](https://github.com/alejandrocoria/MapFrontiers) | MIT | `REFERENCE_ONLY`; optional future `DERIVED_CODE` for Stage 13 | Derivation allowed only with required MIT notice and exact provenance. Prefer own implementation where practical. |
| [Compass to Map](https://github.com/KURONAMI333/compass-to-map) | All Rights Reserved / custom terms | `REFERENCE_ONLY` | Upstream terms consulted prohibit copying code or portions into a public project without explicit written permission. Any copying is `PERMISSION_REQUIRED`. |
| [JourneyMap API](https://github.com/TeamJM/journeymap-api) | Custom TeamJM terms | `DEPENDENCY_API` | Write against the supported public API. Do not copy/embed API source or class files beyond what upstream terms permit. |
| [Iron's Spells 'n Spellbooks](https://github.com/iron431/irons-spells-n-spellbooks) | All Rights Reserved / custom terms | `DEPENDENCY_API`, `REFERENCE_ONLY` | Terms consulted permit own addon/dependency code, but not public redistribution of a modified fork. Assets may not be reused. Do not copy source/assets without separate permission. |
| [Ars Nouveau](https://github.com/baileyholl/Ars-Nouveau) | Code: GNU LGPL v3. Assets: All Rights Reserved unless otherwise stated/permitted | Primarily `DEPENDENCY_API` | Any derived code must satisfy LGPL obligations. Do not copy/modify assets without explicit permission or a separately stated asset license. |
| [Epic Fight](https://github.com/Antikythera-Studios/epicfight) | GNU GPL v3 | Primarily integration/reference | Any copied/adapted code requires explicit copyleft compatibility review before merge/release. |
| [Create](https://github.com/Creators-of-Create/Create) | Code: MIT. `src/main/resources/assets/`: All Rights Reserved | Primarily `DEPENDENCY_API`/reference | MIT code may be adapted with notice; Create assets must not be copied/adapted without additional permission. |
| [Curios](https://github.com/TheIllusiveC4/Curios) | GNU LGPL v3 or later | `DEPENDENCY_API` | Any copied/adapted code requires LGPL compliance; normal API integration is preferred. |
| [Passive Skill Tree](https://github.com/Daripher/Passive-Skill-Tree) | Project metadata inspected says “GNU GENERAL PUBLIC LICENSE”; exact version/scope still requires audit | Historical code/reference source | `REVIEW_REQUIRED` for any copied/adapted source until exact license version, provenance and compatibility are resolved. |
| [Passive Skill Tree — NeoForge 1.21.1 community port](https://github.com/themarneilx/Passive-Skill-Tree/tree/1.21.1-neoforge) | Project metadata inspected says “GNU GENERAL PUBLIC LICENSE”; exact version/scope still requires audit | Historical code/reference source | `REVIEW_REQUIRED` for any copied/adapted source until exact license/provenance audit is complete. |

## User-supplied design/reference projects

The following projects/names were supplied historically as design/reference material:

- Iron's unofficial skill-tree addon;
- Iron's Dynamic Skill Tree;
- Skills Mastery Reimagined;
- Scion: Races and Skill Tree;
- Waifing Passive Skill Tree;
- Passive Skill Tree Additions.

Until canonical upstream URLs and licenses are verified, their status is **`REFERENCE_ONLY / REVIEW_REQUIRED`**. No code or assets from these sources should be copied into this public repository merely from screenshots, binaries or decompiled material.

## File-level derivation register

When substantial code or assets are actually copied/adapted, add a record in this section before merge:

```text
Local file(s):
Upstream project:
Upstream URL:
Upstream commit/tag:
Upstream file(s):
Use type: DERIVED_CODE | DERIVED_ASSET
License:
Copyright notice required:
Modification note/date:
Additional obligations:
Permission evidence (if applicable):
```

At the time this ledger was introduced, no new Stage 13 code was copied from MapFrontiers, Compass to Map or JourneyMap API; Stage 13 is planning only.

## Release policy

A public release must not contain copied/adapted third-party material whose status remains `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown. The engineering gate is specified in `plans/09-hardening-release/08-third-party-licenses-provenance.md`.

The absence of a license file in this repository itself must not be interpreted as permission to ignore third-party license obligations.