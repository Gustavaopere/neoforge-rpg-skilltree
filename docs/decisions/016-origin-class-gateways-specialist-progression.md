# ADR 016 — Origin Class, Class Gateways and Specialist Progression

Status: Accepted  
Date: 2026-09-12

## Context

The historical class model derived class identity primarily from investment/domain state. The approved Tree 2 redesign instead uses one connected Path-of-Exile-like graph organized around class neighborhoods. The player must have a meaningful starting identity without turning class into an exclusive lock, and cross-region perk investment must remain possible without making every nearby purchase silently grant a new formal class.

The same redesign separates character specializations from provider schools/disciplines. Iron's schools, Ars composition disciplines, Epic Fight weapon/combat disciplines and base Create lanes may be requirements/masteries, but they are not automatically Tree 3 character identities.

The physical modlist also removed providers referenced by historical specialization definitions, so save-safe provenance and explicit gateway semantics are required before implementation.

## Decision

### 1. Origin Class is an explicit initial character choice

A new character selects exactly one **Origin Class** from the origin-eligible class catalog.

Origin Class may define:

- initial Tree 2 start position;
- an audited signature capability/starting rule;
- origin affinity used by explicitly declared pricing rules.

Origin is not a permanent prohibition against other class regions.

The current readiness audit contains **23 formal class identities**, of which **16 are origin-eligible** and **7 are confluence-only**. Those counts are design data and must be validated from the effective catalog rather than duplicated as magic constants in runtime code.

### 2. Tree 2 is one connected graph

Class regions are semantic neighborhoods in a single canonical graph, not separate disconnected class trees.

A shared node has one canonical gameplay ID even when it belongs visually/semantically to more than one class neighborhood.

A player may path into any reachable region when all ordinary graph/cost/requirement rules are satisfied.

### 3. Cross-region investment does not automatically grant class identity

Buying perks associated with another class may provide those perks' valid effects without granting that formal class.

A formal additional class is granted only when the player satisfies and purchases/activates the corresponding **Class Gateway** under the server-authoritative rules snapshot.

A Class Gateway may require, when explicitly declared:

- graph reachability/investment;
- permanent Tree 1 base-attribute thresholds;
- Mastery thresholds;
- discoveries/milestones;
- provider availability/capability;
- prerequisite class identity;
- other namespaced rule facts accepted by the schema.

Temporary equipment/buffs never satisfy permanent base-attribute gateway requirements.

### 4. No hard class-count cap

There is no hardcoded maximum number of formal class identities.

The intended soft cap comes from:

- graph distance and path opportunity cost;
- progressive global Tree 2 purchase cost;
- permanent Tree 1 attribute requirements;
- Mastery/provider requirements;
- specialization/resource opportunity cost.

If later balance evidence proves a hard cap necessary, that is a new architecture decision and must not be introduced silently.

### 5. No hard global Tree 2 allocation budget

Tree 2 does not use a fixed global allocation budget that permanently forbids further investment after N points.

Character level may remain uncapped. Build identity is preserved by topology, progressive cost, requirements and opportunity cost rather than an arbitrary point ceiling.

Dynamic purchase pricing must persist the amount actually paid through the allocation-economics model; respec never recomputes historical refund from the player's current total investment.

### 6. Tree 3 is class-centric specialist progression

Tree 3 contains character-identity specializations attached to one or more audited class identities. Provider schools/disciplines remain reusable mastery/gate inputs unless an explicit specialization definition promotes a distinct identity.

The current readiness audit targets **60 class-centric specialization identities**. This is design/catalog state, not evidence that 60 runtime trees are implemented.

### 7. Specialist Points are a separate provenance-aware currency

Specialist Points are not Core Progression Points and are not automatically granted every Character Level.

They may be awarded only by explicit specialist sources such as:

- Mastery milestones;
- class/specialization milestones;
- thematic quests/advancements;
- explicit specialist gateway rewards;
- migration/admin provenance where documented.

SP acquisition and spending must retain provenance sufficient for migration/respec/reconciliation.

There is no global hard cap on the number of specializations. Mutually exclusive choices exist only through an explicit `choice_group` or equivalent canonical rule.

## Alternatives considered

### Fully classless/emergent identity

Rejected as the target model. It weakens starting identity and makes signature/origin placement ambiguous.

### Isolated per-class trees

Rejected. It prevents meaningful cross-class pathing and shared-node synergies.

### Buying any perk in a region automatically grants that class

Rejected. It makes multiclass identity incidental, difficult to reason about and too cheap to obtain.

### Hard limit of two or three classes

Rejected without balance evidence. Systemic opportunity cost is preferred first.

### Fixed Tree 2 point budget

Rejected for the uncapped-level architecture. Progressive pricing and topology preserve scarcity without making later levels useless to Tree 2 by rule.

### Treat provider schools as the Tree 3 catalog

Rejected. Provider-native schools/disciplines are reusable mechanical axes and do not map 1:1 to character specialization identity.

## Consequences

- Origin selection becomes an explicit persisted/migrated fact.
- Legacy emergent class state requires deliberate migration/reconciliation.
- Class Gateway ownership becomes a canonical server-side fact or derivation with explicit provenance.
- UI must distinguish region affinity, perk ownership and formal class ownership.
- The purchase ledger must support dynamic historical cost correctly.
- Specialist Point sources/spending require a separate ledger/provenance path.
- Provider absence can block only dependent gateways/specializations and must not corrupt unrelated class state.

## Migration/compatibility impact

- Existing class IDs should be preserved where semantic identity still matches.
- Historical provider-backed identities whose provider disappeared must fail closed or migrate through explicit aliases/rules; they are never silently mapped to a thematically similar provider.
- Legacy specialization IDs may remain compatibility inputs while the new class-centric catalog is introduced.
- Origin cannot be inferred from temporary equipment/buffs or fabricated from unavailable historical facts.

## Tests/verification required

- all origin-eligible classes can be selected; all confluence-only classes are rejected as origin;
- one origin selection is persisted/synced server-authoritatively;
- cross-region perk purchase does not grant formal class identity;
- Class Gateway grants exactly one intended namespaced class identity idempotently;
- multi-class progression has no hidden hard count cap;
- permanent attribute gate ignores gear/buffs;
- shared node remains one gameplay allocation/effect;
- global progressive pricing uses effective rules and persists paid cost;
- respec refunds historical paid cost;
- provider-required gateway becomes unavailable fail-closed when provider disappears;
- Specialist Points cannot be substituted by general Core Progression Points;
- SP provenance survives save/reload/migration;
- explicit specialization `choice_group` is enforced while unrelated specializations remain combinable;
- legacy class/specialization migration fixtures are deterministic and idempotent.
