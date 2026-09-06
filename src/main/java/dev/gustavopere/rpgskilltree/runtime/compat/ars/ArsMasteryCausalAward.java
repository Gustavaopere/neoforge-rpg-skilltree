package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import com.hollingsworth.arsnouveau.api.spell.IContextAttachment;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.io.Serial;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.resources.ResourceLocation;

/**
 * One-shot causal token carried by Ars {@code SpellContext} attachments.
 *
 * <p>Ars 5.13.1 shallow-copies attachment values when cloning a spell context, so descendants
 * share this token and can claim the originating cast at most once. If Ars serializes a context,
 * the transient action is intentionally lost and the award fails closed after deserialization.
 */
final class ArsMasteryCausalAward implements IContextAttachment {
    @Serial
    private static final long serialVersionUID = 1L;

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(
        "rpgskilltree",
        "ars_mastery_causal_award"
    );

    private final transient SpellAction action;
    private final AtomicBoolean claimed = new AtomicBoolean(false);

    private ArsMasteryCausalAward(SpellAction action) {
        this.action = Objects.requireNonNull(action, "action");
    }

    static ArsMasteryCausalAward arm(SpellAction action) {
        return new ArsMasteryCausalAward(action);
    }

    SpellAction claimResolved() {
        if (action == null) return null;
        return claimed.compareAndSet(false, true) ? action : null;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
