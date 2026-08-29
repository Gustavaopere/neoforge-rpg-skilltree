package dev.gustavopere.rpgskilltree.bootstrap;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * Early-startup Mixin gate for the optional Identity2 target.
 *
 * <p>This class deliberately references neither Minecraft nor Identity2 classes. Mixin plugins run
 * during transformer initialization, before normal mod bootstrap, so provider availability is
 * detected by class-resource presence instead of the NeoForge {@code ModList} runtime boundary.</p>
 */
public final class Identity2MixinPlugin implements IMixinConfigPlugin {
    private static final String IDENTITY_TARGET = "net.Gabou.identity2.identity.IdentityProgression";
    private static final String IDENTITY_TARGET_RESOURCE =
        IDENTITY_TARGET.replace('.', '/') + ".class";

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!IDENTITY_TARGET.equals(targetClassName)) return true;
        return resourcePresent(Thread.currentThread().getContextClassLoader())
            || resourcePresent(Identity2MixinPlugin.class.getClassLoader());
    }

    private static boolean resourcePresent(ClassLoader loader) {
        return loader != null && loader.getResource(IDENTITY_TARGET_RESOURCE) != null;
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
