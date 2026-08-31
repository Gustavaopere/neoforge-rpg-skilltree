package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

/** Persistent chunk-local attachments owned by Volcanoes. */
public final class VolcanoAttachments {
    public static final int MAX_DURABLE_HANDOFFS_PER_CHUNK = 8;

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, VolcanoesMod.MOD_ID);

    public static final Supplier<AttachmentType<List<GeothermalChunkHandoff>>> GEOTHERMAL_HANDOFFS =
            ATTACHMENT_TYPES.register(
                    "geothermal_handoffs",
                    () -> AttachmentType.builder(() -> List.<GeothermalChunkHandoff>of())
                            .serialize(GeothermalChunkHandoff.CODEC.listOf(0, MAX_DURABLE_HANDOFFS_PER_CHUNK))
                            .build());

    private VolcanoAttachments() {
    }

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
