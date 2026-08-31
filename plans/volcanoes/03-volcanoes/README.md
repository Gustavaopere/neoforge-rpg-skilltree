# 03 — Volcanoes

Volcano implementation is intentionally split across dependent subtask branches so each merge remains reviewable and every task can close with its own CI evidence.

- `feat/03-volcano-sites`: `✅-01-volcano-sites.md`
- `feat/03-magma-lifecycle`: `✅-02-magma-lifecycle.md`
- `feat/03-lava`: `✅-03-lava.md`
- `feat/03-eruptions`: `✅-04-eruptions.md`
- `feat/03-ash-pyroclastics`: `✅-05-ash-pyroclastics.md`
- `feat/03-geothermal-hot-springs`: `✅-06-geothermal-hot-springs.md`

All six Stage 03 Volcanoes tasks are canonical and complete. Any future changes to volcano behavior must be tracked as new hardening/integration work rather than reopening a completed task implicitly.

Behavior from TFC Volcanoes should be reused/adapted where it improves fidelity, with all TFC-specific queries routed through our own geology/tectonics contracts.
