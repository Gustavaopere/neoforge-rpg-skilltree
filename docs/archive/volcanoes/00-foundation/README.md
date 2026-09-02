# 00 — Foundation

**Implementation branch:** `round-1-foundation`

**Status:** COMPLETE — PR #2 merged to `main` as `bee79bc77688118e78e73deeb0cb3c06f0b7288b` and post-merge CI is GREEN.

**Goal:** create a compiling NeoForge 1.21.1 project, CI, test harness, upstream-source inventory and stable domain contracts before feature code.

**Completed order:** `✅-01-scaffold-ci.md` → `✅-02-upstream-tfc-volcanoes-inventory.md` → `✅-03-domain-contracts.md`.

**Exit gate:** `gradle test` and `gradle build` green; dedicated server smoke test green; no TFC/TFC Registry API runtime dependency; source/provenance inventory committed; domain contracts covered by unit tests.

**Evidence:** branch push workflow `32901372365` GREEN; PR workflow `32902286035` GREEN; `main` workflow `32903071520` attempt 2 GREEN after attempt 1 hit a transient GitHub runner DNS failure before project steps executed.
