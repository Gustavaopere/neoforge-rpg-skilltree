# Universal Material Chemistry — Risk Register

| Risk | Consequence | Planned control |
|---|---|---|
| Treating similar names as identical materials | false chemistry and broken provider semantics | canonical alias requires explicit equivalence evidence |
| Treating minerals/alloys as molecules | scientifically false UI | category-specific render models |
| Fabricating formulae for fictional resources | misleading science | arcane signature system; formula forbidden by validator |
| Destroy port API differs from upstream | classloading/runtime failures | freeze exact 0.4.1 port API before bridge implementation |
| Optional provider absent | startup crash | isolated optional adapters and fail-closed availability |
| Mod update adds materials silently | stale coverage | provider/catalog drift tests in CI |
| Destroy adds species silently | stale chemistry visualization | enumerable Destroy coverage gate when possible |
| Third-party Molecule Drawings code/data licensing unclear | release/legal risk | provenance review before reuse; independent renderer fallback |
| Huge tooltips | poor UX | compact tooltip plus encyclopedia/expanded view |
| Client renderer loaded on dedicated server | server crash | client-only packages/bootstrap + dedicated-server smoke |
| Full modlist audit becomes unreviewable | mistakes hidden in bulk | provider-family batches and coverage checkpoints |
| Composition range shown as exact percentage | false precision | range-aware representation, no arbitrary midpoint |
| Historical rune meaning accidentally asserted | lore/accuracy issue | project-original labels unless intentionally sourced |
