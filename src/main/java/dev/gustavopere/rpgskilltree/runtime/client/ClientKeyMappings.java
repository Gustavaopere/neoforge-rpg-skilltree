package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.runtime.network.MartialStanceIntentPayload;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientKeyMappings {
    private static final KeyMapping OPEN_TREE = new KeyMapping(
        "key.rpgskilltree.open_tree",
        GLFW.GLFW_KEY_K,
        "key.categories.rpgskilltree"
    );
    private static final KeyMapping OPEN_COMPENDIUM = new KeyMapping(
        "key.rpgskilltree.open_compendium",
        GLFW.GLFW_KEY_J,
        "key.categories.rpgskilltree"
    );
    private static final KeyMapping TOGGLE_MARTIAL_STANCE = new KeyMapping(
        "Alternar Postura Marcial",
        GLFW.GLFW_KEY_G,
        "key.categories.rpgskilltree"
    );

    private ClientKeyMappings() {}

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_TREE);
        event.register(OPEN_COMPENDIUM);
        event.register(TOGGLE_MARTIAL_STANCE);
    }

    @EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, value = Dist.CLIENT)
    private static final class KeyEvents {
        private KeyEvents() {}

        @SubscribeEvent
        public static void clientTick(ClientTickEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();

            while (OPEN_TREE.consumeClick()) {
                if (minecraft.screen == null && minecraft.player != null) {
                    minecraft.setScreen(new RpgSkillTreeScreen());
                }
            }

            while (OPEN_COMPENDIUM.consumeClick()) {
                if (minecraft.screen == null && minecraft.player != null) {
                    minecraft.setScreen(new CompendiumScreen(ClientCompendiumState.get()));
                }
            }

            while (TOGGLE_MARTIAL_STANCE.consumeClick()) {
                if (minecraft.screen == null && minecraft.player != null) {
                    PacketDistributor.sendToServer(new MartialStanceIntentPayload(true));
                }
            }
        }
    }
}
