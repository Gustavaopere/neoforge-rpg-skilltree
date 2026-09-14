# Runtime evidence archive — run 34848925658

## Provenance
- workflow: `Volcanoes Full Pack Compatibility Acceptance`;
- run: `34848925658`;
- source head SHA: `4ae1bf04796d457d51c552ea58fa8fa85ded6675`;
- artifact: `volcanoes-full-pack-compatibility-9a4840cd4516e401d0a21cab0fd1f043902bebba`;
- artifact id: `10349785686`;
- artifact digest: `sha256:58a9b69ae5b952e3172dc166243e9f577bfee8e3845964c2b001f54fa4e1e10c`;
- artifact expiry recorded by GitHub: `2026-09-28T13:29:35Z`.

## Durable copies
The two files below preserve the exact bytes extracted from the artifact, encoded as deterministic gzip (`gzip -n -9`) followed by base64 so the evidence remains reconstructable after Actions artifact expiry:

- `runtime-registry-inventory.json.gz.base64`
  - decoded/original SHA-256: `14b6bbe0864422aa366a7504aa673a90a2099e0eb26a1e91169f903e018b7a7a`;
  - original size: `298902` bytes;
  - encoded-file SHA-256: `007537600db3ae3700209e1293c745d10852bdbed3351a0d100410010235ea9d`.
- `compendium-inventory-scope.json.gz.base64`
  - decoded/original SHA-256: `b6598d3633e26a2cb64f7915ad1fc933f0df9494b778c06251924e039432cd8f`;
  - original size: `695` bytes;
  - encoded-file SHA-256: `7b8cd99e6001d3f5f7a947b7ce5b66e1a9edf7adca5d6eac1f2fc4f1f55869c6`.

Reconstruction example:

```bash
base64 -d runtime-registry-inventory.json.gz.base64 | gzip -dc > runtime-registry-inventory.json
base64 -d compendium-inventory-scope.json.gz.base64 | gzip -dc > compendium-inventory-scope.json
sha256sum runtime-registry-inventory.json compendium-inventory-scope.json
```

## Authority boundary
The scope file declares `scope_id=volcanoes_compatibility_acceptance_stack` and `complete_modpack_inventory=false`.

This archive proves registry-entry presence within that executed bounded runtime. It does **not** prove completeness of the canonical modpack, absence from the canonical modpack, canonical lore/geography, or provider ownership/version for arbitrary registry entries. Provider attribution must be verified independently against the physical modlist/JAR and provider documentation/source.
