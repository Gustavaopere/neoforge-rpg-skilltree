#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MOD_MAIN = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java"
RUNTIME = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/CorePlayerProgressionRuntime.java"
CATALOG = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/AttributeRankCostPolicyCatalog.java"
NODE_EFFECT_RELOADER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectsReloader.java"
ATTRIBUTE_GAMETEST = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/gametest/AttributeModifierGameTests.java"
NETWORKING = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java"
PURCHASE_PAYLOAD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/PurchaseAttributeRanksPayload.java"
REFUND_PAYLOAD = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/network/RefundAttributeRanksPayload.java"
text = RUNTIME.read_text(encoding="utf-8")

required = [
    "AttributeRankCostPolicy",
    "AttributeRankMutationService",
    "public static CoreProgressionState purchaseAttributeRanks(",
    "AttributeRankMutationService.purchase(",
    "public static CoreProgressionState refundAttributeRanks(",
    "AttributeRankMutationService.refund(",
    "if (next != current)",
    "set(player, next, rules)",
    "AttributeRankCostPolicyCatalog.provider().requireCurrent()",
    "CoreProgressionRulesCatalog.provider().requireCurrent()",
]
for needle in required:
    if needle not in text:
        print(f"ERROR: {RUNTIME.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

if not CATALOG.is_file():
    print(f"ERROR: {CATALOG.relative_to(ROOT)}: required attribute cost policy catalog is missing")
    raise SystemExit(1)
catalog_text = CATALOG.read_text(encoding="utf-8")
for needle in [
    "InstallableAttributeRankCostPolicyProvider",
    "public static AttributeRankCostPolicyProvider provider()",
    "public static void install(AttributeRankCostPolicy policy)",
    "PROVIDER.install(policy)",
    "public static void clear()",
    "PROVIDER.clear()",
]:
    if needle not in catalog_text:
        print(f"ERROR: {CATALOG.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

# Replacing the authoritative node-effect catalog must immediately reconcile all
# online players. Otherwise modifiers removed or changed by /reload remain stale
# until a later login/progression mutation. Initial resource loads may happen before
# a MinecraftServer is available, so the server lookup must remain null-safe.
if not NODE_EFFECT_RELOADER.is_file():
    print(f"ERROR: {NODE_EFFECT_RELOADER.relative_to(ROOT)}: required node effect reloader is missing")
    raise SystemExit(1)
node_reloader_text = NODE_EFFECT_RELOADER.read_text(encoding="utf-8")
for needle in [
    "AttributeNodeEffectRuntime",
    "PlayerProgressionRuntime",
    "ServerLifecycleHooks",
    "NodeEffectCatalog.replace(effects)",
    "AttributeEffectDiagnostics.clear()",
    "ServerLifecycleHooks.getCurrentServer()",
    "server.getPlayerList().getPlayers()",
    "AttributeNodeEffectRuntime.refresh(player, PlayerProgressionRuntime.get(player))",
]:
    if needle not in node_reloader_text:
        print(f"ERROR: {NODE_EFFECT_RELOADER.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)
replace_pos = node_reloader_text.index("NodeEffectCatalog.replace(effects)")
diagnostics_pos = node_reloader_text.index("AttributeEffectDiagnostics.clear()")
server_pos = node_reloader_text.index("ServerLifecycleHooks.getCurrentServer()")
refresh_pos = node_reloader_text.index("AttributeNodeEffectRuntime.refresh(player, PlayerProgressionRuntime.get(player))")
if not (replace_pos < diagnostics_pos < server_pos < refresh_pos):
    print(
        f"ERROR: {NODE_EFFECT_RELOADER.relative_to(ROOT)}: reload reconciliation must run "
        "after catalog replacement and diagnostics reset"
    )
    raise SystemExit(1)
if "if (server != null)" not in node_reloader_text:
    print(f"ERROR: {NODE_EFFECT_RELOADER.relative_to(ROOT)}: initial reload must tolerate a null server")
    raise SystemExit(1)

# The plan's idempotency acceptance is a Minecraft-runtime contract, not only a
# pure resolver property. Keep one GameTest exercising a real ServerPlayer
# AttributeInstance through apply -> repeated apply -> remove -> reapply.
if not ATTRIBUTE_GAMETEST.is_file():
    print(f"ERROR: {ATTRIBUTE_GAMETEST.relative_to(ROOT)}: required attribute modifier GameTest is missing")
    raise SystemExit(1)
gametest_text = ATTRIBUTE_GAMETEST.read_text(encoding="utf-8")
for needle in [
    "@GameTest",
    "makeMockServerPlayerInLevel",
    "NodeEffectCatalog.replace",
    "AttributeNodeEffectRuntime.refresh",
    "ProgressionState.empty().withPassiveNodes",
    "PassiveNodeProgress.empty()",
    "instance.getValue()",
    "helper.succeed()",
]:
    if needle not in gametest_text:
        print(f"ERROR: {ATTRIBUTE_GAMETEST.relative_to(ROOT)}: missing runtime acceptance marker {needle!r}")
        raise SystemExit(1)
if gametest_text.count("AttributeNodeEffectRuntime.refresh") < 4:
    print(
        f"ERROR: {ATTRIBUTE_GAMETEST.relative_to(ROOT)}: GameTest must exercise apply, repeated apply, removal and reapply"
    )
    raise SystemExit(1)

# The canonical 1 CPP = 1 rank policy is a server bootstrap decision. The network
# never installs or supplies a policy, and the catalog must not remain empty in a
# normally constructed mod instance.
mod_main_text = MOD_MAIN.read_text(encoding="utf-8")
for needle in [
    "UnitAttributeRankCostPolicy",
    "AttributeRankCostPolicyCatalog",
    "AttributeRankCostPolicyCatalog.install(UnitAttributeRankCostPolicy.INSTANCE)",
]:
    if needle not in mod_main_text:
        print(f"ERROR: {MOD_MAIN.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

# Client -> server attribute mutations are request-only. The client may identify an
# attribute, a bounded number of ranks, and an idempotency key; price, rules, and
# economic source remain server-authoritative and must never be packet fields.
for path in [PURCHASE_PAYLOAD, REFUND_PAYLOAD]:
    if not path.is_file():
        print(f"ERROR: {path.relative_to(ROOT)}: required attribute mutation payload is missing")
        raise SystemExit(1)

purchase_text = PURCHASE_PAYLOAD.read_text(encoding="utf-8")
refund_text = REFUND_PAYLOAD.read_text(encoding="utf-8")
for payload_text, path, record_name, operation, source_id in [
    (
        purchase_text,
        PURCHASE_PAYLOAD,
        "PurchaseAttributeRanksPayload",
        "purchaseAttributeRanks",
        "network:attribute_purchase",
    ),
    (
        refund_text,
        REFUND_PAYLOAD,
        "RefundAttributeRanksPayload",
        "refundAttributeRanks",
        "network:attribute_refund",
    ),
]:
    location = str(path.relative_to(ROOT))
    for needle in [
        f"public record {record_name}(AttributeId attribute, long rankCount, String transactionId)",
        "MAX_RANKS_PER_REQUEST",
        "MAX_TRANSACTION_ID_LENGTH",
        "ByteBufCodecs.VAR_LONG",
        "ByteBufCodecs.stringUtf8(MAX_TRANSACTION_ID_LENGTH)",
        "StreamCodec.composite(",
        f"CorePlayerProgressionRuntime.{operation}(",
        source_id,
        "context.enqueueWork",
        "context.player() instanceof ServerPlayer player",
    ]:
        if needle not in payload_text:
            print(f"ERROR: {location}: missing {needle!r}")
            raise SystemExit(1)
    for forbidden in [
        "ProgressionRulesSnapshot",
        "AttributeRankCostPolicy",
        "sourceId)",
        "long cost",
        "price",
    ]:
        if forbidden in payload_text:
            print(f"ERROR: {location}: client payload exposes forbidden authority field {forbidden!r}")
            raise SystemExit(1)

networking_text = NETWORKING.read_text(encoding="utf-8")
for needle in [
    'private static final String NETWORK_VERSION = "3";',
    "PurchaseAttributeRanksPayload.TYPE",
    "PurchaseAttributeRanksPayload.STREAM_CODEC",
    "PurchaseAttributeRanksPayload::handle",
    "RefundAttributeRanksPayload.TYPE",
    "RefundAttributeRanksPayload.STREAM_CODEC",
    "RefundAttributeRanksPayload::handle",
]:
    if needle not in networking_text:
        print(f"ERROR: {NETWORKING.relative_to(ROOT)}: missing {needle!r}")
        raise SystemExit(1)

print("Attribute runtime validation: PASS")
