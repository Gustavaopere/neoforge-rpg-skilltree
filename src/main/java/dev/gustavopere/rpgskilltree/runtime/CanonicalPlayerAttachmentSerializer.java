package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentData;
import dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentDataCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteArrayTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/** NeoForge serializer for the single canonical persisted player RPG envelope. */
public enum CanonicalPlayerAttachmentSerializer
    implements IAttachmentSerializer<ByteArrayTag, CanonicalPlayerAttachmentData> {
    INSTANCE;

    @Override
    public CanonicalPlayerAttachmentData read(
        IAttachmentHolder holder,
        ByteArrayTag tag,
        HolderLookup.Provider provider
    ) {
        return CanonicalPlayerAttachmentDataCodec.decode(tag.getAsByteArray());
    }

    @Override
    public ByteArrayTag write(
        CanonicalPlayerAttachmentData attachment,
        HolderLookup.Provider provider
    ) {
        return new ByteArrayTag(CanonicalPlayerAttachmentDataCodec.encode(attachment));
    }
}
