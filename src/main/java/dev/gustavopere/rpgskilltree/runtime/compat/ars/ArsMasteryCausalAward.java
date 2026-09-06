package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import com.hollingsworth.arsnouveau.api.spell.IContextAttachment;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.io.Serial;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

/**
 * Ars-specific attachment that carries a one-shot Mastery claim across spell contexts.
 *
 * <p>Ars 5.13.1 shallow-copies attachment values when cloning a spell context, so descendants
 * share this attachment. The claim is transient: if Ars serializes a context, the award fails
 * closed after deserialization instead of reconstructing unverifiable causal state. The claim
 * is also bound to the original caster UUID, so another player cannot consume or receive it.
 */
final class ArsMasteryCausalAward implements IContextAttachment {
    @Serial
    private static final long serialVersionUID = 1L;

    static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(
        "rpgskilltree",
        "ars_mastery_causal_award"
    );

    private final transient ArsMasteryClaim claim;

    private ArsMasteryCausalAward(ArsMasteryClaim claim) {
        this.claim = claim;
    }

    static ArsMasteryCausalAward arm(UUID casterId, SpellAction action) {
        return new ArsMasteryCausalAward(ArsMasteryClaim.arm(casterId, action));
    }

    SpellAction claimResolved(UUID resolverId) {
        return claim == null ? null : claim.claimResolved(resolverId);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
