#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "src/main/java/dev/gustavopere/rpgskilltree"
EPIC = JAVA / "runtime/compat/epicfight/A0061A0080EpicFightHooks.java"
PROJECTILES = JAVA / "runtime/events/A0041A0060ProjectileEvents.java"
MOD = JAVA / "RpgSkillTreeMod.java"
EARLY = JAVA / "runtime/compat/epicfight/A0001A0020EpicFightHooks.java"
MID = JAVA / "runtime/compat/epicfight/A0021A0040EpicFightHooks.java"
LATE = JAVA / "runtime/compat/epicfight/A0041A0060EpicFightHooks.java"


def read(path: Path) -> str:
    if not path.is_file():
        raise SystemExit(f"ERROR: missing {path.relative_to(ROOT)}")
    return path.read_text(encoding="utf-8")


def require(text: str, needle: str, path: Path) -> None:
    if needle not in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: missing {needle!r}")


def forbid(text: str, needle: str, path: Path) -> None:
    if needle in text:
        raise SystemExit(f"ERROR: {path.relative_to(ROOT)}: forbidden {needle!r}")


epic = read(EPIC)
projectiles = read(PROJECTILES)
mod = read(MOD)
early = read(EARLY)
mid = read(MID)
late = read(LATE)

# General melee fundamentals must live on the provider-native Epic Fight damage path.
for needle in (
    "DELIVER_DAMAGE_PRE.registerEvent",
    "A0061A0080CombatPolicy.beforePhysicalHit(",
    "attachArmorNegationModifier",
    "attachImpactModifier",
    "LivingDamageEvent.Post",
    "ServerTickEvent.Post",
    "A0061A0080RuntimeState.stationary().sample(",
):
    require(epic, needle, EPIC)

# A0080 must remain fail-closed until a callback proves a hostile attack was avoided.
forbid(epic, "DodgeEvent", EPIC)
forbid(epic, "ON_DODGE", EPIC)
forbid(epic, "onConfirmedDodgeAvoidance(", EPIC)

# A0075 is all-or-nothing and has no safe Cold Sweat receipt in the current runtime.
forbid(epic, "recordMartialAction(", EPIC)

# A0063 reuses the canonical critical decision already made by each weapon pipeline.
require(early, "A0061A0080CombatPolicy.criticalDamageMultiplier", EARLY)
require(mid, "A0061A0080CombatPolicy.criticalDamageMultiplier", MID)
require(late, "A0061A0080CombatPolicy.criticalDamageMultiplier", LATE)
require(projectiles, "A0061A0080CombatPolicy.criticalDamageMultiplier", PROJECTILES)

# Dagger is processed by both the cumulative A0001 and A0021 Epic Fight adapters. A0063 must have
# exactly one owner for that overlap: A0001 owns dagger critical damage, while A0021 owns only its
# hammer/mace/scythe lanes. This prevents multiplying A0063 twice on one root action.
require(mid, "double a0063CriticalDamage = family.get() == WeaponFamily.DAGGER", MID)
require(mid, "damage *= a0063CriticalDamage;", MID)
forbid(mid, "damage *= dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy.criticalDamageMultiplier(ranks, root.critical);", MID)

# Ranged physical hits receive the general direct-physical layer, but no fabricated impact.
require(projectiles, "A0061A0080CombatPolicy.beforePhysicalHit(", PROJECTILES)
require(projectiles, "MartialTargetClassifier.classify(", PROJECTILES)

# Bootstrap and lifecycle ownership must be explicit.
require(mod, "A0061A0080EpicFightHooks.register();", MOD)
require(mod, "NeoForge.EVENT_BUS.register(A0061A0080EpicFightHooks.class);", MOD)
require(epic, "A0061A0080RuntimeState.clearAll();", EPIC)

print("A0061-A0080 runtime integration validation: PASS")
