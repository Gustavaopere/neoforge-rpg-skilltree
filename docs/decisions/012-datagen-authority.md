# ADR 012 — Python remains canonical for generated RPG gameplay data

Status: Accepted  
Date: 2026-09-12

## Context

The repository already uses deterministic Python generators/validators for large committed RPG data families, while NeoForge `runData` exists but does not own those same outputs. The class-oriented redesign will add large generated/validated catalogs and must not introduce two generators for the same resources.

## Decision

Until a future ADR explicitly migrates a resource family, **Python is the canonical generator for generated RPG gameplay data already owned by the Python pipeline**.

NeoForge datagen may generate only resource families that do not overlap with Python-owned outputs.

Rules:

- one output path/family has exactly one generator authority;
- migration of a family requires an atomic handoff: new generator produces byte/semantic-equivalent accepted output, old generator is removed for that family in the same change;
- CI drift checks compare generated committed data deterministically;
- manually authored design/catalog files remain manual unless explicitly enrolled in a generator.

The new class/specialist topology may use a Python authoring/compiler pipeline if its generated outputs are committed and validated. Client rendering is not generator authority.

## Consequences

The overhaul can add class-affinity/topology/specialist generated data without waiting for a wholesale NeoForge datagen rewrite. Future `GatherDataEvent` work remains possible through explicit family-by-family migration.

## Migration/compatibility impact

None for saves. Repository generation commands/documentation must state the owning generator for each generated family.

## Tests/verification required

- generator run is deterministic;
- CI detects committed-output drift;
- no file is emitted by both Python and NeoForge providers;
- generator failure cannot partially publish a canonical runtime snapshot.
