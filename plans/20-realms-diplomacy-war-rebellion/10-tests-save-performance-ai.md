# 20.10 — Testes, save, performance e AI

## Pure simulation

- realm graph/titles;
- treaty lifecycle;
- tribute/accounting;
- war state/outcomes;
- NPC decision determinism;
- offscreen economy/military;
- intel confidence;
- discontent escalation/hysteresis.

## Runtime/provider

- MineColonies member/guard/raider bridges;
- protection during siege;
- materialized combat dedupe;
- Stage 16 transactions;
- Stage 13 intel redaction;
- Stage 17 authority/governance transition.

## Save/migration

Unknown realm/title/treaty IDs permanecem preservados/quarantined. Mid-war/mid-transition save deve retomar exatamente; não rerollar battle/espionage result.

## Performance

Stress com muitos realms/settlements. AI roda em períodos escalonados, com budget por tick/server period. Catch-up bounded. Nenhuma varredura de todos os citizens de todos os realms offscreen.

## Fail-soft

Sem MineColonies, abstract realm simulation continua testável; materialized colony features ficam indisponíveis. Sem JourneyMap, diplomacy/realm state continua.

## Gate

Core/JUnit, GameTests provider-present, long-run deterministic simulation, build/JAR, dedicated-server smoke e profiling de stress.

## Acceptance

Muitos reinos simulados não tornam o servidor um simulador de colônias permanentemente carregadas e save/reload não muda decisões já resolvidas.