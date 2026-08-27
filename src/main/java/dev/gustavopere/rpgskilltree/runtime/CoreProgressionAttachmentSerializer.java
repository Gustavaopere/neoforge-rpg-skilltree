package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentData;
import dev.gustavopere.rpgskilltree.core.CoreProgressionAttachmentDataCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteArrayTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/** NeoForge attachment serializer for the parallel uncapped Core progression envelope. */
public enum CoreProgressionAttachmentSerializer
    implements IAttachmentSerializer<ByteArrayTag, CoreProgressionAttachmentData> {
    INSTANCE;

    @Override
    public CoreProgressionAttachmentData read(
        IAttachmentHolder holder,
        ByteArrayTag tag,
        HolderLookup.Provider provider
    ) {
        return CoreProgressionAttachmentDataCodec.decode(tag.getAsByteArray());
    }

    @Override
    public ByteArrayTag write(
        CoreProgressionAttachmentData attachment,
        HolderLookup.Provider provider
    ) {
        return new ByteArrayTag(CoreProgressionAttachmentDataCodec.encode(attachment));
    }
}
