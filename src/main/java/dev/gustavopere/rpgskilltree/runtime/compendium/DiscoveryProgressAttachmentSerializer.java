package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryProgress;
import dev.gustavopere.rpgskilltree.compendium.discovery.DiscoveryProgressCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteArrayTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/** NeoForge attachment serializer for persistent Compendium discovery progress. */
public enum DiscoveryProgressAttachmentSerializer
    implements IAttachmentSerializer<ByteArrayTag, DiscoveryProgress> {
    INSTANCE;

    @Override
    public DiscoveryProgress read(
        IAttachmentHolder holder,
        ByteArrayTag tag,
        HolderLookup.Provider provider
    ) {
        return DiscoveryProgressCodec.decode(tag.getAsByteArray());
    }

    @Override
    public ByteArrayTag write(DiscoveryProgress attachment, HolderLookup.Provider provider) {
        return new ByteArrayTag(DiscoveryProgressCodec.encode(attachment));
    }
}
