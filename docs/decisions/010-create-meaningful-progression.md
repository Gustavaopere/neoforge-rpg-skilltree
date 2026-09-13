# ADR 010 — Create mastery rewards attributable outcomes, never machine ticks

Status: Accepted  
Date: 2026-09-12

## Context

Engineer/Technomancer and their future specialists need progression from Create without awarding XP simply because a shaft rotates or a machine exists. Passive tick-based grants are farmable, difficult to attribute and do not prove meaningful player engineering activity.

Exact hooks must still be validated against the installed Create/addon versions before implementation.

## Decision

Create-family mastery/progression may be awarded only from a **semantic outcome** that can be causally attributed to the player or to an ownership context deliberately associated with that player.

Eligible categories include, when a provider-native hook can prove them:

- completion of a qualifying processing/recipe outcome;
- completion/assembly/deployment of a qualifying contraption milestone;
- first or thresholded automation outcome for a canonical machine/line identity;
- qualified logistics/stock/request completion;
- artillery/aeronautics milestone from the owning specialist system;
- explicit engineering discovery/advancement.

Ineligible by default:

- rotational/tick presence;
- machine merely loaded/running;
- block placement spam;
- repeated transfer of the same item in a loop;
- observer event without authoritative completion;
- output that cannot be attributed safely.

Every adapter normalizes a provider event into the common semantic-action/causal pipeline and participates in dedupe/anti-farm limits.

## Consequences

Engineer/Technomancer mastery measures built/operated systems rather than time spent near machines. If the exact provider version lacks a safe completion/ownership hook for a proposed milestone, that milestone remains unavailable/fail-closed instead of falling back to ticks.

## Migration/compatibility impact

Legacy Create mastery already earned is preserved. New awards follow this semantic contract after the new adapter/milestone revision activates.

## Tests/verification required

- one qualifying outcome -> one award;
- tick/rotation alone -> zero award;
- same semantic result observed by two hooks -> deduped;
- looped item transfer -> anti-farm/no unbounded awards;
- ownership attribution multiplayer;
- provider absent/unsupported version -> no award and actionable diagnostic.
