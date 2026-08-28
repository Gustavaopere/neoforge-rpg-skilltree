package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.EntityScalingAttachmentData;
import dev.gustavopere.rpgskilltree.core.EntityScalingStateCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteArrayTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/** NeoForge attachment bridge for the strict entity-scaling codec. */
public enum EntityScalingAttachmentSerializer
    implements IAttachmentSerializer<ByteArrayTag, EntityScalingAttachmentData> {
    INSTANCE;

    @Override
    public EntityScalingAttachmentData read(
        IAttachmentHolder holder,
        ByteArrayTag tag,
        HolderLookup.Provider provider
    ) {
        return EntityScalingStateCodec.decode(tag.getAsByteArray());
    }

    @Override
    public ByteArrayTag write(EntityScalingAttachmentData attachment, HolderLookup.Provider provider) {
        return new ByteArrayTag(EntityScalingStateCodec.encode(attachment));
    }
}
