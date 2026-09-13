# ADR 001 — Custom RPG Skill Tree UI and authoring are permanent authority consumers

Status: Accepted  
Date: 2026-09-12

## Context

The RPG Skill Tree already owns server-authoritative `ProgressionState`, purchase/respec, class resolution, rule validation and player sync. The target UI now requires one connected Tree 2 with 23 class regions, shared nodes, class gateways, origin-affinity pricing, attribute gates, class-centric Tree 3 and specialist glyph layouts.

Pufferfish's Skills is installed in the physical pack and remains useful for its own consumers, but it is a progression framework with its own points/XP/state/persistence/root/reward model. Making it the authority of this RPG progression would introduce a second progression authority or require the RPG core to project all economic/class rules into an external state model.

Mine and Slash provides useful engineering reference for large-tree authoring (`grid -> parser -> graph`) but its public source is treated as clean-room reference, not a permissive code dependency.

## Decision

The permanent direction is a **custom RPG Skill Tree UI and custom authoring pipeline** consuming the server-projected canonical rules snapshot.

- `ProgressionState`/server rules remain the only gameplay authority.
- The client UI never grants purchases, classes, SP, forms or specialist state locally.
- Pufferfish's Skills remains installed for unrelated/third-party consumers and is not an RPG Skill Tree authority.
- Mine and Slash may inspire authoring/layout architecture clean-room, but no runtime dependency is introduced.
- The custom authoring layer may use a compact grid/graph source format, but the compiled canonical graph remains validated server-side.

## Consequences

- The old D001 comparison gate is closed; implementation no longer waits for an external tree engine decision.
- The vertical slice is still mandatory, but now validates our custom implementation rather than choosing between engines.
- UI work must prove two class regions, a shared node, class gateway, specialist gateway, dynamic cost, base-attribute requirement, purchase/respec and multiplayer rule authority before scaling to the full graph.
- Pufferfish integration must not mirror or mutate RPG progression state unless a future explicit adapter is designed.

## Migration/compatibility impact

No save migration follows from this ADR by itself because the decision concerns presentation/authoring ownership. Existing server-side IDs and progression state remain authoritative.

## Tests/verification required

- client cannot forge cost/requirements;
- rule revision sync updates UI deterministically;
- purchase/respec round trip is server-confirmed;
- shared-node instance is not duplicated visually or economically;
- 512/750/1000+ node profiling before advanced spatial optimization;
- disconnect/reconnect clears stale client rule state.
