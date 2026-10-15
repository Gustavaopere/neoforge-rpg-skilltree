# Universal Material Chemistry — Acceptance Criteria

The implementation may be declared complete only when all applicable acceptance criteria are satisfied with evidence.

## Audit

- Every canonical modlist row has an audit disposition.
- Every material-provider mod has a candidate inventory or explicit blocked audit reason.
- Every discovered material candidate is classified or blocked.

## Scientific correctness

- No unsupported formulae or compositions exist.
- Minerals, alloys, mixtures, polymers, molecules, and elements use category-correct representation.
- Every supported real material carries scientific provenance.
- Composition ranges remain ranges unless a provider explicitly supplies an exact composition.

## Fictional correctness

- Fictional materials contain no scientific formula field unless they are actually real materials misclassified by the provider audit.
- Supported fictional materials have an arcane/runic signature.
- Hybrid materials keep real and fictional constituents separate.

## Destroy

- Destroy remains authoritative for native chemistry.
- Exact aliases do not duplicate species/reaction state.
- Full Destroy coverage is claimed only if exact installed-version enumeration is possible and tested.

## Runtime

- Resource reload is atomic and deterministic.
- No unbounded per-tick scanning is introduced.
- Client rendering does not load on dedicated server.
- Optional provider failures are isolated and fail closed.

## UI

- Real materials expose scientific information through supported inspection surfaces.
- Fictional materials expose visibly fictional arcane information.
- Large structures/charts do not make ordinary tooltips unbounded.

## Release

- Unit tests/GameTests applicable to the implementation are green.
- NeoForge build/check is green on Java 21.
- Full-pack/client/dedicated-server validation is green where supported by the repository.
- Required CI is green.
- Implementation PR is merged to `main`.
- Final `main` SHA and post-merge workflow state are confirmed.
