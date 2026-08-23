package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ProgressionStateCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ByteArrayTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

public enum ProgressionAttachmentSerializer implements IAttachmentSerializer<ByteArrayTag, ProgressionState> {
    INSTANCE;

    @Override
    public ProgressionState read(IAttachmentHolder holder, ByteArrayTag tag, HolderLookup.Provider provider) {
        return ProgressionStateCodec.decode(tag.getAsByteArray());
    }

    @Override
    public ByteArrayTag write(ProgressionState attachment, HolderLookup.Provider provider) {
        return new ByteArrayTag(ProgressionStateCodec.encode(attachment));
    }
}
