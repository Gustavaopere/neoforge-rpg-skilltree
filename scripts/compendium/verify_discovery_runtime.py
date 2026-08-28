#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def text(path: str) -> str:
    target = ROOT / path
    require(target.is_file(), f"missing required discovery runtime file: {path}")
    return target.read_text(encoding="utf-8")


attachments = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java")
require("COMPENDIUM_DISCOVERY" in attachments,
        "ModAttachments must register COMPENDIUM_DISCOVERY")
require('"compendium_discovery"' in attachments,
        "discovery attachment id must be compendium_discovery")
require("AttachmentType.builder(DiscoveryProgress::empty)" in attachments,
        "discovery attachment must default to DiscoveryProgress.empty")
require("DiscoveryProgressAttachmentSerializer.INSTANCE" in attachments,
        "discovery attachment must use DiscoveryProgressAttachmentSerializer")
require(".copyOnDeath()" in attachments,
        "discovery attachment must survive player death")

serializer = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/DiscoveryProgressAttachmentSerializer.java")
require("IAttachmentSerializer<ByteArrayTag, DiscoveryProgress>" in serializer,
        "discovery serializer must bind ByteArrayTag to DiscoveryProgress")
require("DiscoveryProgressCodec.decode(tag.getAsByteArray())" in serializer,
        "discovery serializer must decode with DiscoveryProgressCodec")
require("DiscoveryProgressCodec.encode(attachment)" in serializer,
        "discovery serializer must encode with DiscoveryProgressCodec")

runtime = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryRuntime.java")
require("ServerPlayer" in runtime,
        "discovery runtime boundary must require ServerPlayer")
require("ModAttachments.COMPENDIUM_DISCOVERY" in runtime,
        "discovery runtime must read/write the discovery attachment")
require("DiscoveryRuntime.apply" in runtime,
        "discovery runtime bridge must delegate transition logic to DiscoveryRuntime")
require("CompendiumDiscoveryRewardBridge.apply" in runtime,
        "discovery runtime must execute newly emitted rewards")
require("transition.newRewards()" in runtime,
        "discovery runtime must execute only newly emitted rewards")
require("player.setData(ModAttachments.COMPENDIUM_DISCOVERY" in runtime,
        "changed discovery progress must be persisted to the player attachment")

reward_bridge = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryRewardBridge.java")
require("DiscoveryRewardKind.CHARACTER_XP" in reward_bridge,
        "discovery reward bridge must explicitly support CHARACTER_XP")
require("ProgressionReward.characterXp" in reward_bridge,
        "discovery CHARACTER_XP must map to typed Core progression reward")
require("CorePlayerProgressionRuntime.applyProgressionReward" in reward_bridge,
        "discovery rewards must use the canonical server-authoritative reward runtime")
require("UnsupportedOperationException" in reward_bridge,
        "unsupported discovery reward kinds must fail explicitly")

print("Compendium discovery runtime validation: PASS")
