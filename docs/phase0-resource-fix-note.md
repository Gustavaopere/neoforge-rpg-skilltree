# Phase 0 — Minecraft 1.21.1 resource correctness

Temporary integration note for the Phase 0 resource-ID correction PR.

This branch fixes only strict Minecraft 1.21.1 resource/registry correctness:

- singular `tags/entity_type` path;
- optional Cataclysm entries (`required: false`);
- vanilla 1.21.1 attribute registry IDs using `minecraft:generic.*` / supported 1.21.1 namespaces;
- validator wired into the core CI runner.

No Axxxx perk balance/content behavior is changed.
