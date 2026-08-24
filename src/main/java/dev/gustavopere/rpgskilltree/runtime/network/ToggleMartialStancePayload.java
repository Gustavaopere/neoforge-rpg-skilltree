package dev.gustavopere.rpgskilltree.runtime.network;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenMartialTacticsService.Stance;
import dev.gustavopere.rpgskilltree.runtime.FrozenCombatRuntimeState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/** Server-authoritative posture toggle. The node id is revalidated against current progression. */
public record ToggleMartialStancePayload(ResourceLocation nodeId) implements CustomPacketPayload {
    public static final Type<ToggleMartialStancePayload> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, "toggle_martial_stance")
    );
    public static final StreamCodec<ByteBuf, ToggleMartialStancePayload> STREAM_CODEC =
        ResourceLocation.STREAM_CODEC.map(ToggleMartialStancePayload::new, ToggleMartialStancePayload::nodeId);

    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(ToggleMartialStancePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            String code = FrozenCombatPerkNodeBinding.catalogCode(payload.nodeId().toString()).orElse("");
            if (code.equals("A0076")) FrozenCombatRuntimeState.toggleStance(player, Stance.AGGRESSIVE);
            else if (code.equals("A0077")) FrozenCombatRuntimeState.toggleStance(player, Stance.CAUTIOUS);
        });
    }
}
