from pathlib import Path

root = Path(__file__).resolve().parents[2]
reloader = root / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumLootResourceReloader.java"
catalog = root / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumLootCatalog.java"
mod = root / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"

for path in (reloader, catalog):
    if not path.exists():
        raise SystemExit(f"{path.name} is required")

text = reloader.read_text(encoding="utf-8")
catalog_text = catalog.read_text(encoding="utf-8")
mod_text = mod.read_text(encoding="utf-8")

required_reloader = [
    '"loot_table/entities"',
    "CompendiumLootSnapshot.stage",
    "RuntimeCompendiumLootCatalog.publish",
    "AddReloadListenerEvent",
]
for token in required_reloader:
    if token not in text:
        raise SystemExit(f"loot reloader missing required contract: {token}")

if text.index("CompendiumLootSnapshot.stage") > text.index("RuntimeCompendiumLootCatalog.publish"):
    raise SystemExit("loot snapshot must be fully staged before publication")

for token in ["getRandomItems", ".fill(", "PlayerTickEvent", "ServerTickEvent", "EntityTick"]:
    if token in text or token in catalog_text:
        raise SystemExit(f"loot reload uses forbidden rolling/tick operation: {token}")

if "CompendiumLootResourceReloader.class" not in mod_text:
    raise SystemExit("RpgSkillTreeMod must register CompendiumLootResourceReloader")
if "volatile CompendiumLootSnapshot" not in catalog_text:
    raise SystemExit("RuntimeCompendiumLootCatalog must publish through a volatile snapshot")

print("Compendium ecology reload validation: PASS")
