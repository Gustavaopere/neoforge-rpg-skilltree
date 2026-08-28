#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
runtime = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumFloraCatalog.java"
events = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumFloraCatalogEvents.java"
mod = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"

for path in (runtime, events, mod):
    if not path.exists():
        raise SystemExit(f"missing flora publication contract: {path.name}")

runtime_text = runtime.read_text(encoding="utf-8")
event_text = events.read_text(encoding="utf-8")
mod_text = mod.read_text(encoding="utf-8")

for token in [
    "private static final CompendiumCatalog CATALOG",
    "RuntimeFloraCatalogCollector.collect()",
    "CompendiumCatalogSnapshot candidate = builder.build()",
    "FloraCatalogCoverage.compare",
    "return CATALOG.publish(builder)",
]:
    if token not in runtime_text:
        raise SystemExit(f"flora runtime catalog missing: {token}")

if runtime_text.index("builder.build()") > runtime_text.index("CATALOG.publish(builder)"):
    raise SystemExit("flora catalog must build/validate candidate before publication")

for token in ["ServerStartedEvent", "RuntimeCompendiumFloraCatalog.publishFromRegistries()", "Compendium flora catalog published"]:
    if token not in event_text:
        raise SystemExit(f"flora startup event missing: {token}")

if "CompendiumFloraCatalogEvents.class" not in mod_text:
    raise SystemExit("RpgSkillTreeMod must register CompendiumFloraCatalogEvents")

combined = runtime_text + event_text
for forbidden in ["PlayerTickEvent", "ServerTickEvent", "ClientTickEvent", "net.minecraft.client"]:
    if forbidden in combined:
        raise SystemExit(f"flora catalog publication contains forbidden tick/client surface: {forbidden}")

print("Compendium flora catalog publication validation: PASS")
