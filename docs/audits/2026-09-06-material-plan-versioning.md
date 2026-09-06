# Universal Material Chemistry — Versioning Policy

The material audit/catalog is tied to an explicit canonical modlist snapshot.

When the modlist changes:

1. record the new snapshot identity/count/date;
2. run mod audit drift detection;
3. re-audit added/removed/updated material providers;
4. re-run provider candidate enumeration;
5. re-run Destroy coverage against the installed version;
6. update catalog/provenance only where evidence changed;
7. keep historical audit reports rather than silently overwriting prior baselines when the change is materially significant.

A mod version update is not assumed chemically equivalent to the prior version. Provider adapters and mappings must be revalidated when the API or material set changes.
