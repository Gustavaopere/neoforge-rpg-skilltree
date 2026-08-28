package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryProgress;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.EntityScalingAttachmentData;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.compendium.DiscoveryProgressAttachmentSerializer;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModAttachments {
    private ModAttachments() {}

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RpgSkillTreeMod.MOD_ID);

    /** Legacy persisted input retained only so pre-canonical saves can be decoded and migrated. */
    public static final Supplier<AttachmentType<ProgressionState>> PROGRESSION = ATTACHMENTS.register(
        "progression",
        () -> AttachmentType.builder(ProgressionState::empty)
            .serialize(ProgressionAttachmentSerializer.INSTANCE)
            .copyOnDeath()
            .build()
    );

    /** Legacy Core persisted input retained only so pre-canonical saves can be decoded and migrated. */
    public static final Supplier<AttachmentType<CoreProgressionAttachmentData>> CORE_PROGRESSION = ATTACHMENTS.register(
        "core_progression",
        () -> AttachmentType.builder(CoreProgressionAttachmentData::uninitialized)
            .serialize(CoreProgressionAttachmentSerializer.INSTANCE)
            .copyOnDeath()
            .build()
    );

    /** Single authoritative persisted player RPG envelope for all normal runtime writes. */
    public static final Supplier<AttachmentType<CanonicalPlayerAttachmentData>> CANONICAL_PLAYER = ATTACHMENTS.register(
        "canonical_player",
        () -> AttachmentType.builder(CanonicalPlayerAttachmentData::empty)
            .serialize(CanonicalPlayerAttachmentSerializer.INSTANCE)
            .copyOnDeath()
            .build()
    );

    /** Persistent per-player natural Compendium discovery progress. */
    public static final Supplier<AttachmentType<DiscoveryProgress>> COMPENDIUM_DISCOVERY = ATTACHMENTS.register(
        "compendium_discovery",
        () -> AttachmentType.builder(DiscoveryProgress::empty)
            .serialize(DiscoveryProgressAttachmentSerializer.INSTANCE)
            .copyOnDeath()
            .build()
    );

    public static final Supplier<AttachmentType<EntityScalingAttachmentData>> ENTITY_SCALING = ATTACHMENTS.register(
        "entity_scaling",
        () -> AttachmentType.builder(EntityScalingAttachmentData::uninitialized)
            .serialize(EntityScalingAttachmentSerializer.INSTANCE)
            .build()
    );

    public static void register(IEventBus modBus) {
        ATTACHMENTS.register(modBus);
    }
}
