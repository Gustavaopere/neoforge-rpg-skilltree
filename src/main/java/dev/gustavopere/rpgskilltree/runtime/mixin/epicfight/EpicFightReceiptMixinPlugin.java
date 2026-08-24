package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightExactStaminaReceiptBridge;
import java.util.List;
import java.util.Set;
import net.neoforged.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/** Early exact-version gate for the required Epic Fight receipt mixin configuration. */
public final class EpicFightReceiptMixinPlugin implements IMixinConfigPlugin {
    private boolean apply;

    @Override
    public void onLoad(String mixinPackage) {
        this.apply = FMLLoader.getCurrent().getLoadingModList().getMods().stream()
            .anyMatch(mod -> "epicfight".equals(mod.getModId())
                && EpicFightExactStaminaReceiptBridge.SUPPORTED_EPIC_FIGHT_VERSION.equals(mod.getVersion().toString()));
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return apply;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(
        String targetClassName,
        ClassNode targetClass,
        String mixinClassName,
        IMixinInfo mixinInfo
    ) {}

    @Override
    public void postApply(
        String targetClassName,
        ClassNode targetClass,
        String mixinClassName,
        IMixinInfo mixinInfo
    ) {}
}
