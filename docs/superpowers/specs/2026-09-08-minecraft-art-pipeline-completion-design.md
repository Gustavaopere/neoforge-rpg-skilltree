# Minecraft Art Pipeline Completion Design

## Objective

Close the gaps left after PRs #479/#480/#482 by adding the Visual Style Bible, separate general VFX/spell-production/visual-QA skills, reusable templates and a tested Blockbench RPG Asset Toolkit while preserving the existing 1.21.1 authority model.

## Scope

- `docs/art/VISUAL-STYLE-BIBLE.md` grounded in the current modpack visual stack;
- `VISUAL-STYLE-SOURCES.md` for auditability;
- seven templates promised by the original design;
- skills: `minecraft-vfx-engineering`, `minecraft-spell-production`, `minecraft-visual-qa`;
- Blockbench `rpg_asset_toolkit.js` + Node tests + docs;
- router/README/current art skills updated;
- validator expanded from 24 to 27 canonical skills;
- existing CI invokes Node syntax/tests through the repository skill validator.

## Safety boundaries

- Physical modlist/JAR remains version authority.
- Notion/resource packs inform visual language only.
- No third-party asset copying.
- No universal texture resolution/texel density invented without measured corpus.
- Blockbench toolkit is read-only: no destructive auto-fix.
- No GeckoLib/Photon/AAA Java signature frozen by analogy.
- Golden Samples are a separate follow-up PR so examples cannot weaken the infrastructure review.

## Visual foundation

Current catalog says Excalibur replaced Whimscape as base visual, with Fresh Animations/extensions, Refreshed family, Mandala dark UI and Complementary Reimagined. The Bible uses these as composition/compatibility context, not source assets.

## Toolkit architecture

Pure `validateProject(project, profile)` logic is exportable for Node tests; the same file registers a Blockbench plugin when Blockbench globals exist. A contract profile can require bones, animations and max bounds without hardcoding arbitrary global limits.

## Acceptance

- new behavior has RED→GREEN test evidence;
- exactly 27 canonical skills;
- all promised template files exist;
- Visual Style Bible/source audit are required by deterministic validator;
- toolkit JS passes syntax + behavioral tests;
- existing full repository CI/security/acceptance gates pass on exact reconciled head;
- main rechecked immediately before merge.
