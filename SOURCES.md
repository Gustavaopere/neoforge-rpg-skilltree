# Source provenance

This file is the public index of upstream projects used as dependencies, compatibility targets, architectural references or historical development references.

**A source link is not a license grant.** Before copying/adapting code or assets, consult `THIRD_PARTY_NOTICES.md` and `plans/09-hardening-release/08-third-party-licenses-provenance.md`. Any actual derivation must record exact upstream commit/tag and file-level provenance.

## Current compile/runtime compatibility targets

These providers are compiled against or explicitly integrated by the current project. Their presence here does **not** imply copied code.

| Source | Current artifact/version evidence | Known use classification | Compliance status |
| --- | --- | --- | --- |
| Iron's Spells 'n Spellbooks | Maven `1.21.1-3.16.3` | `DEPENDENCY_API` / `REFERENCE_ONLY` | observed custom/ARR terms; exact immutable source-license snapshot still required before any derivation |
| Ars Nouveau | Modrinth version `ugLa4qlw`, pack line 5.13.x | `DEPENDENCY_API` | observed LGPLv3 code / separately restricted assets; derived material remains gated pending exact source snapshot |
| Epic Fight | Modrinth version `8HHhJt6i`, `21.17.3.1-mc1.21.1-neoforge` | `DEPENDENCY_API` / reference | observed GPLv3; derived material requires copyleft review and exact source snapshot |
| Goety | CurseForge project 586095, file `8689429`, version `3.1.4` | `DEPENDENCY_API` / compatibility target | project metadata observed as MIT; no copied code declared |
| Malum | CurseForge project 484064, file `7307339`, version `1.8.2` | `DEPENDENCY_API` / compatibility target | project metadata observed as LGPLv3; no copied code declared |
| Eidolon: Repraised | CurseForge project 870250, file `8064602`, version `1.21.1-0.5.0.2` | `DEPENDENCY_API` / compatibility target | project metadata observed as LGPLv3; no copied code declared |
| Identity2 | CurseForge project 1238155, file `8439845`, version `2.2.1` | `DEPENDENCY_API` / compatibility target | All Rights Reserved; integration must remain independently implemented unless separate permission is obtained |

## Other primary code/API/reference sources

| Source | Immutable audit/source snapshot | Known use classification | Compliance status |
| --- | --- | --- | --- |
| [NeoSync](https://github.com/breakinblocks/NeoSync) | commit `131709b52f1cf25c85f2cd02a3b4a93cb08979d0` | Stage 12 `REFERENCE_ONLY`; possible future permitted derivation | MIT observed on this audited snapshot; derived code must preserve notice and file-level provenance |
| [MapFrontiers](https://github.com/alejandrocoria/MapFrontiers) | 1.21.1 branch commit `dea25ae7e85b0b12c43dee89062b4199f6d361a9` | Stage 13 `REFERENCE_ONLY`; optional future derivation | MIT observed for audited 1.21.1 source; preserve notice/provenance for derived code |
| [Compass to Map](https://github.com/KURONAMI333/compass-to-map) | commit `79d0aa8caeb025d2c8df3e4fb1dd87f2d3ab7d1e` | Stage 13 `REFERENCE_ONLY` | commit explicitly changes project to All Rights Reserved; public copying requires written permission |
| [JourneyMap API](https://github.com/TeamJM/journeymap-api) | 1.21.1 API branch commit `4a57dee370a0ae70660ae66d3dc5363e670fc1ee` | Stage 13 `DEPENDENCY_API` | custom TeamJM terms; integrate through supported API and do not copy/embed source/classes outside permitted terms |
| [Create](https://github.com/Creators-of-Create/Create) | exact source/license revision still pending | `DEPENDENCY_API` / reference | observed code MIT and assets restricted separately; `REVIEW_REQUIRED` before derived material |
| [Curios](https://github.com/TheIllusiveC4/Curios) | exact source/license revision still pending | `DEPENDENCY_API` | observed LGPLv3-or-later; `REVIEW_REQUIRED` before derived material |
| [Passive Skill Tree original](https://github.com/Daripher/Passive-Skill-Tree) | historical derivation revision unresolved | historical code/reference source | `REVIEW_REQUIRED` for copied/derived code until exact GPL version/scope and historical provenance are audited |
| [NeoForge 1.21.1 community port](https://github.com/themarneilx/Passive-Skill-Tree/tree/1.21.1-neoforge) | historical derivation revision unresolved | historical code/reference source | `REVIEW_REQUIRED` until exact source/license/provenance audit is complete |

## Design/reference projects supplied historically

- Iron's unofficial skill-tree addon;
- Iron's Dynamic Skill Tree;
- Skills Mastery Reimagined;
- Scion: Races and Skill Tree;
- Waifing Passive Skill Tree;
- Passive Skill Tree Additions.

These remain `REFERENCE_ONLY / REVIEW_REQUIRED` unless `THIRD_PARTY_NOTICES.md` records an immutable upstream revision and license decision. No source or asset may be copied merely from screenshots, binaries, datapacks or decompiled material.

## Detailed compliance ledger

See [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md) for immutable audit evidence already captured, asset restrictions, required notices and the file-level derivation register.