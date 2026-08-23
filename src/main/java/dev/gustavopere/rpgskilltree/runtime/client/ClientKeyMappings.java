package dev.gustavopere.rpgskilltree.runtime.client;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientKeyMappings {
    private static final KeyMapping OPEN_TREE = new KeyMapping(
        "key.rpgskilltree.open_tree",
        GLFW.GLFW_KEY_K,
        "key.categories.rpgskilltree"
    );

    private ClientKeyMappings() {}

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(OPEN_TREE);
    }

    @EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, value = Dist.CLIENT)
    private static final class KeyEvents {
        private KeyEvents() {}

        @SubscribeEvent
        public static void keyPressed(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();
            if (event.getAction() != GLFW.GLFW_PRESS) return;
            if (minecraft.screen != null || minecraft.player == null) return;
            if (event.getKey() == OPEN_TREE.getKey().getValue()) {
                minecraft.setScreen(new RpgSkillTreeScreen());
            }
        }
    }
}
