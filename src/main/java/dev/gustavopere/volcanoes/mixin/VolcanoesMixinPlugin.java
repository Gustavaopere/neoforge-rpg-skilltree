package dev.gustavopere.volcanoes.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Early-startup gate for Volcanoes mixins targeting optional provider classes.
 *
 * <p>Mixin configuration is evaluated before normal NeoForge mod bootstrap, so provider presence
 * is detected from class resources rather than {@code ModList}. This prevents core-only runtimes
 * from attempting to resolve an absent optional target while still enabling the mixin whenever
 * the exact host class is present.</p>
 */
public final class VolcanoesMixinPlugin implements IMixinConfigPlugin {
    private static final String RNS_DEPOSIT_TARGET =
            "com.bmaster.createrns.content.deposit.info.CustomServerDepositLocation";
    private static final String RNS_DEPOSIT_TARGET_RESOURCE =
            RNS_DEPOSIT_TARGET.replace('.', '/') + ".class";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!RNS_DEPOSIT_TARGET.equals(targetClassName)) {
            return true;
        }
        return resourcePresent(Thread.currentThread().getContextClassLoader())
                || resourcePresent(VolcanoesMixinPlugin.class.getClassLoader());
    }

    private static boolean resourcePresent(ClassLoader loader) {
        return loader != null && loader.getResource(RNS_DEPOSIT_TARGET_RESOURCE) != null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

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
    ) {
    }

    @Override
    public void postApply(
            String targetClassName,
            ClassNode targetClass,
            String mixinClassName,
            IMixinInfo mixinInfo
    ) {
    }
}
