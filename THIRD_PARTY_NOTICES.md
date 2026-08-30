# Third-Party Notices and Provenance

This public repository integrates with and has been informed by third-party Minecraft mods and libraries. This file records known provenance/compliance state without implying that third-party source or assets are bundled here.

`REFERENCE_ONLY` means behavior/architecture may be studied while implementation remains independent. `DEPENDENCY_API` means this project writes its own integration against a dependency/API. Substantial copied/adapted material is forbidden unless a file-level derivation record proves that its license permits the use and all obligations are met.

## Status vocabulary

- `REFERENCE_ONLY` — no third-party code/assets are intended to be copied;
- `DEPENDENCY_API` — own integration against a dependency/API;
- `DERIVED_CODE` — code copied/adapted; exact source revision + license obligations required;
- `DERIVED_ASSET` — asset copied/adapted; separate asset rights required;
- `REVIEW_REQUIRED` — evidence is not sufficient to authorize derivation;
- `PERMISSION_REQUIRED` — public copying requires additional permission.

## Reproducible audit evidence

Verification date for the entries below: **2026-08-30**. A row is not considered a reusable license grant merely because a license was observed. When the evidence column does not identify an immutable source revision containing the applicable terms, the status remains `REVIEW_REQUIRED` for any derivation.

| Upstream | Artifact/source evidence audited | License/terms observed | Intended use | Derivation status / obligation |
| --- | --- | --- | --- | --- |
| [NeoSync](https://github.com/breakinblocks/NeoSync) | commit `131709b52f1cf25c85f2cd02a3b4a93cb08979d0` | MIT | `REFERENCE_ONLY`; possible future Stage 12 derivation | `DERIVED_CODE` permitted only with exact file provenance and preserved MIT copyright/license notice. |
| [MapFrontiers](https://github.com/alejandrocoria/MapFrontiers) | 1.21.1 branch commit `dea25ae7e85b0b12c43dee89062b4199f6d361a9` | MIT | `REFERENCE_ONLY`; possible Stage 13 derivation | Preserve MIT notice and exact source-file/commit provenance. Prefer independent implementation where practical. |
| [Compass to Map](https://github.com/KURONAMI333/compass-to-map) | commit `79d0aa8caeb025d2c8df3e4fb1dd87f2d3ab7d1e` | All Rights Reserved/custom; this commit explicitly changes the project from MIT to ARR | `REFERENCE_ONLY` | `PERMISSION_REQUIRED` for copying/adapting code. Stage 13 must independently implement equivalent behavior unless written permission is recorded. |
| [JourneyMap API](https://github.com/TeamJM/journeymap-api) | 1.21.1 API branch commit `4a57dee370a0ae70660ae66d3dc5363e670fc1ee` | custom TeamJM terms | `DEPENDENCY_API` | Use the supported API. Do not copy/embed API source or class files outside the audited terms. |
| Goety | CurseForge project `586095`, immutable file `8689429`, version `3.1.4` | project metadata observed as MIT | `DEPENDENCY_API` / compatibility target | No copied code declared. Source-level derivation remains `REVIEW_REQUIRED` until the exact source revision corresponding to the artifact is recorded. |
| Malum | CurseForge project `484064`, immutable file `7307339`, version `1.8.2` | project metadata observed as LGPLv3 | `DEPENDENCY_API` / compatibility target | No copied code declared. `DERIVED_CODE` remains `REVIEW_REQUIRED` until exact source revision and LGPL obligations are mapped. |
| Eidolon: Repraised | CurseForge project `870250`, immutable file `8064602`, version `1.21.1-0.5.0.2` | project metadata observed as LGPLv3 | `DEPENDENCY_API` / compatibility target | No copied code declared. `DERIVED_CODE` remains `REVIEW_REQUIRED` until exact source revision is mapped. |
| Identity2 | CurseForge project `1238155`, immutable file `8439845`, version `2.2.1` | All Rights Reserved | `DEPENDENCY_API` / compatibility target | Independent integration only. Any copying/adaptation is `PERMISSION_REQUIRED`. |
| Iron's Spells 'n Spellbooks | Maven version `1.21.1-3.16.3` used by this build | custom/All Rights Reserved terms observed historically | `DEPENDENCY_API`, `REFERENCE_ONLY` | `REVIEW_REQUIRED` for derivation until immutable source/terms snapshot for this line is recorded; no source/assets may be copied on the basis of this row. |
| Ars Nouveau | Modrinth version id `ugLa4qlw`, pack line 5.13.x | LGPLv3 code / separately restricted assets observed historically | `DEPENDENCY_API` | `REVIEW_REQUIRED` for derivation until exact source revision is mapped; assets require their own rights review. |
| Epic Fight | Modrinth version id `8HHhJt6i`, `21.17.3.1-mc1.21.1-neoforge` | GPLv3 observed historically | `DEPENDENCY_API` / reference | `REVIEW_REQUIRED` for copied code until exact source revision and copyleft impact are documented. |
| Create | exact 1.21.1 source/license revision not yet captured here | code MIT / assets separately restricted observed historically | `DEPENDENCY_API` / reference | `REVIEW_REQUIRED` for derivation; never assume source license covers textures/models/audio. |
| Curios | exact source/license revision not yet captured here | LGPLv3-or-later observed historically | `DEPENDENCY_API` | `REVIEW_REQUIRED` for derivation until exact revision is captured. |
| Passive Skill Tree | historical source revision used by this project is not yet reconstructed | project metadata indicates GNU GPL, exact version/scope unresolved | historical code/reference source | `REVIEW_REQUIRED`; no new copying until historical file-level provenance and license compatibility are closed. |
| Passive Skill Tree — NeoForge 1.21.1 community port | historical source revision used by this project is not yet reconstructed | project metadata indicates GNU GPL, exact version/scope unresolved | historical code/reference source | `REVIEW_REQUIRED`; no new copying until exact port revision/provenance is closed. |

## Historical design/reference projects

The project documentation also names these as design or feature donors:

- Iron's unofficial skill-tree addon;
- Iron's Dynamic Skill Tree;
- Skills Mastery Reimagined;
- Scion: Races and Skill Tree;
- Waifing Passive Skill Tree;
- Passive Skill Tree Additions.

Until each canonical upstream, immutable revision and applicable license are captured, they remain **`REFERENCE_ONLY / REVIEW_REQUIRED`**. Screenshots, binaries, datapacks or decompiled classes are not permission to copy source/assets.

## Current compatibility-surface coverage

The license/provenance audit must include every provider declared by build/runtime integration metadata. As of the current build this explicitly includes Iron's, Ars Nouveau, Epic Fight, **Goety, Malum, Eidolon: Repraised and Identity2**, in addition to infrastructure/reference providers recorded above. Adding another compileOnly/runtime compatibility target requires adding it to this ledger in the same PR.

## File-level derivation register

Whenever substantial code or assets are copied/adapted, add a record **before merge**:

```text
Local file(s):
Upstream project:
Upstream URL:
Upstream immutable commit/tag/artifact:
Upstream file(s):
Use type: DERIVED_CODE | DERIVED_ASSET
License/terms snapshot:
Copyright notice required:
Modification note/date:
Additional obligations:
Permission evidence (if applicable):
```

At the introduction of this ledger, no new Stage 13 code was copied from MapFrontiers, Compass to Map or JourneyMap API; Stage 13 is planning only.

## Release policy

A public release must not contain copied/adapted third-party material whose derivation status remains `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown. Dependency/API use still requires compliance with the provider's distribution/API terms.

The engineering gate is specified in `plans/09-hardening-release/08-third-party-licenses-provenance.md`. The absence of a license file in this repository must never be interpreted as permission to ignore third-party obligations.