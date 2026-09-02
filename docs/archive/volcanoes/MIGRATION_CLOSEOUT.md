# Volcanoes consolidation closeout

## Final authority

The active Volcanoes runtime is consolidated into this repository, `Gustavaopere/neoforge-rpg-skilltree`.

The former standalone repository `Gustavaopere/Volcanoes` is retired as an active runtime repository and now exists only as a migration tombstone plus Git history.

## Standalone historical checkpoint

The final complete standalone implementation is preserved at:

- repository: `Gustavaopere/Volcanoes`
- commit: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- tree: `c87fb2c5aede57d6eab69592e3377d76f3a3c232`

The standalone tombstone was merged by PR #98 in that repository. Its post-merge `main` is:

- commit: `298352973e941c2034c97465929dc67f6a0400e2`
- tree: `f21b6139a1431888030861e92bafb1b5e00c2491`

That tombstone contains only `README.md`, `MIGRATION.md`, and `LICENSE` at repository root. Runtime code, build scripts, acceptance workflows, plans, and operational documentation remain recoverable from the historical checkpoint above.

## Consolidation parity evidence

The consolidation contract is pinned to the standalone checkpoint above. The parity verifier confirms that all functional standalone paths required by the unified implementation are present in this repository, with byte-exact resource/test-resource preservation and explicit accounting for intentional Java/test changes made during integration.

The consolidated migration audit established 595/595 functional standalone paths represented in the unified repository, with no missing functional paths.

## Post-consolidation validation gate

Standalone retirement was authorized only after the consolidated repository passed the release-critical Volcanoes gates on the same validated `main` checkpoint:

- validated commit: `a675a753186a32dc600bfc3483a3d03b01fd716f`
- validated tree: `59dabded9ebffde94aaa34ef0be0c2f4194d360f`
- `Volcanoes Full Pack Compatibility Acceptance`: SUCCESS, run `33572889982`
- `SonarQube Cloud`: SUCCESS, run `33572889891`
- `Volcanoes Worldgen Compatibility Matrix`: SUCCESS, run `33572890045`

The Sonar workflow includes the explicit New Code guard that fails if OPEN or CONFIRMED New Code issues remain, so the successful run satisfies the post-consolidation Sonar release condition rather than only a generic Quality Gate.

The canonical `main` subsequently advanced through repository-maintenance changes; the validated checkpoint remains an ancestor and the authority transfer recorded here is unchanged.

## Ongoing maintenance rule

From this closeout forward:

- Volcanoes runtime fixes and features belong only in `Gustavaopere/neoforge-rpg-skilltree`;
- Volcanoes compatibility, worldgen, full-pack, release-readiness, provenance, and performance gates belong here;
- the standalone repository must not regain an independent runtime implementation;
- historical standalone investigation must use the pinned pre-tombstone checkpoint instead of the tombstone `main`;
- this `docs/archive/volcanoes/` tree remains the historical design and implementation record for the consolidated subsystem.
