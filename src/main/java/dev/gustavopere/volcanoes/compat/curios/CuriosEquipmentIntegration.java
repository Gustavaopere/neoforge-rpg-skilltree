package dev.gustavopere.volcanoes.compat.curios;

import dev.gustavopere.volcanoes.pressure.EquippedItemView;
import dev.gustavopere.volcanoes.pressure.PressureNeoForgeRuntime;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Exact Curios 9.5.1 bridge kept outside the loader-neutral Pressure core. */
final class CuriosEquipmentIntegration {
    private CuriosEquipmentIntegration() {
    }

    static void install() {
        PressureNeoForgeRuntime.registerHostEquipmentProvider(CuriosEquipmentIntegration::equippedCurios);
    }

    private static List<EquippedItemView> equippedCurios(ServerPlayer player) {
        ArrayList<EquippedItemView> equipped = new ArrayList<>();
        CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            IItemHandlerModifiable curios = handler.getEquippedCurios();
            for (int slot = 0; slot < curios.getSlots(); slot++) {
                ItemStack stack = curios.getStackInSlot(slot);
                if (stack.isEmpty()) {
                    continue;
                }
                Set<String> tags = stack.getTags()
                        .map(tag -> tag.location().toString())
                        .collect(Collectors.toUnmodifiableSet());
                equipped.add(new EquippedItemView(
                        "curios:" + slot,
                        BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(),
                        tags));
            }
        });
        return List.copyOf(equipped);
    }
}
