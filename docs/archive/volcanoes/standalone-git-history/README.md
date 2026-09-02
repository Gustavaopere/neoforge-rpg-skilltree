# Volcanoes standalone — complete Git history archive

This directory preserves the complete Git history exported from the retired `Gustavaopere/Volcanoes` repository before that repository is deleted.

## Authority

The live implementation is **not** this bundle. Runtime authority remains `Gustavaopere/neoforge-rpg-skilltree`.

The bundle exists only for provenance, historical investigation, and disaster recovery.

## Preserved checkpoints

- standalone tombstone `main`: `298352973e941c2034c97465929dc67f6a0400e2`
- last complete standalone source checkpoint: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- immutable export commit used to build this archive: `97009d4c9a7e1c53b3680e845e8fb47d76643a87`
- bundle SHA-256: see `SHA256SUMS`

## Restore the retired repository locally

From this directory:

```bash
sha256sum -c SHA256SUMS
git bundle verify Volcanoes-full-history.bundle
git clone --branch main Volcanoes-full-history.bundle Volcanoes-restored
cd Volcanoes-restored
git cat-file -e eaddc3232dfc600780769f4a5e7e45ff1e50181c^{commit}
```

`BUNDLE_HEADS.txt` records exported branch/tag heads, while `STANDALONE_EXPORT_METADATA.txt` records the source refs and bundle size captured during export.

Do not restart independent development from this archive. Any future Volcanoes change belongs in the unified RPG repository.
