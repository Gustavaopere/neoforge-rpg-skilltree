from pathlib import Path

root = Path(__file__).resolve().parents[2]
path = root / "src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityEcologyInspector.java"
if not path.exists():
    raise SystemExit("RuntimeEntityEcologyInspector.java is required")

text = path.read_text(encoding="utf-8")
required = [
    "public final class RuntimeEntityEcologyInspector",
    "inspect(Entity entity)",
    "instanceof AgeableMob",
    "instanceof TamableAnimal",
    "TamingFacts.instance",
]
for token in required:
    if token not in text:
        raise SystemExit(f"ecology runtime inspector missing required contract: {token}")

forbidden = [
    "net.minecraft.client",
    "EntityType.create",
    "addFreshEntity",
    "getPersistentData",
    "CompoundTag",
    "Class.forName",
    "java.lang.reflect",
    "static Entity ",
    "static Level ",
    "static ServerLevel ",
]
for token in forbidden:
    if token in text:
        raise SystemExit(f"ecology runtime inspector uses forbidden operation: {token}")

print("Compendium ecology runtime validation: PASS")
