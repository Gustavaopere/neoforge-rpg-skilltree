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
require("player.level().isClientSide()" in runtime,
        "discovery mutation boundary must reject client-side execution")

reward_bridge = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryRewardBridge.java")
require("DiscoveryRewardKind.CHARACTER_XP" in reward_bridge,
        "discovery reward bridge must explicitly support CHARACTER_XP")
require("ProgressionReward.characterXp" in reward_bridge,
        "discovery CHARACTER_XP must map to typed Core progression reward")
require("CorePlayerProgressionRuntime.applyProgressionReward" in reward_bridge,
        "discovery rewards must use the canonical server-authoritative reward runtime")
require("UnsupportedOperationException" in reward_bridge,
        "unsupported discovery reward kinds must fail explicitly")

events = text("src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryEvents.java")
require("LivingDeathEvent" in events,
        "discovery events must consume trusted LivingDeathEvent")
require("event.getSource().getEntity() instanceof ServerPlayer" in events,
        "defeat discovery must derive the actor from the server damage source")
require("BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType())" in events,
        "defeat discovery must derive target identity from the dead entity registry type")
require("PlayerInteractEvent.EntityInteract" in events,
        "discovery events must consume trusted entity interaction events")
require("event.getTarget() instanceof LivingEntity" in events,
        "entity interaction discovery must require a server-observed living target")
require("BuiltInRegistries.ENTITY_TYPE.getKey(target.getType())" in events,
        "interaction discovery must derive identity from the actual event target")
require("PlayerEvent.PlayerChangedDimensionEvent" in events,
        "dimension discovery must use the server dimension-change event")
require("player.level().dimension().location()" in events,
        "dimension identity must come from the server player's current level")
require("PlayerTickEvent.Post" in events,
        "biome discovery must use a bounded server player tick hook")
require("BIOME_SAMPLE_INTERVAL_TICKS" in events and "player.tickCount % BIOME_SAMPLE_INTERVAL_TICKS" in events,
        "biome observation must be throttled")
require("player.level().getBiome(player.blockPosition()).unwrapKey()" in events,
        "biome identity must come from the server player's current biome holder")
require("CompendiumDiscoveryRuntime.progress(player).record(entryId)" in events,
        "repeated biome observations must short-circuit from persisted progress")
require("CompendiumDiscoveryRuntime.apply" in events,
        "trusted event feeds must enter the canonical discovery runtime")
require("new DiscoveryOrigin(" in events,
        "trusted event feeds must derive first origin server-side")
require("getEntities" not in events and "getAllEntities" not in events,
        "Stage 10.04 must not scan nearby entities each tick")

mod = text("src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java")
require("CompendiumDiscoveryEvents" in mod,
        "RpgSkillTreeMod must import CompendiumDiscoveryEvents")
require("NeoForge.EVENT_BUS.register(CompendiumDiscoveryEvents.class)" in mod,
        "RpgSkillTreeMod must register CompendiumDiscoveryEvents")

print("Compendium discovery runtime validation: PASS")
