package dev.gustavopere.rpgskilltree.runtime.mixin.epicfight;

import dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy;
import dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy.ArtifactFingerprint;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforgespi.language.IModInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/** Early exact-version and exact-binary fail-closed gate for the restricted impact Mixin. */
public final class EpicFightImpactMixinPlugin implements IMixinConfigPlugin {
    private boolean apply;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            LoadingModList loading = LoadingModList.get();
            if (loading == null) {
                this.apply = false;
                return;
            }

            Map<String, ArtifactFingerprint> installed = new HashMap<>();
            Set<String> epicFightDependents = new HashSet<>();
            Set<String> fingerprintIds = new HashSet<>(ImpactStaminaCompatibilityPolicy.AUDITED_OPTIONAL_ADDONS.keySet());
            fingerprintIds.add("epicfight");

            for (IModInfo mod : loading.getMods()) {
                String modId = mod.getModId();
                if (fingerprintIds.contains(modId)) {
                    var file = mod.getOwningFile().getFile().getFilePath();
                    if (!Files.isRegularFile(file)) {
                        this.apply = false;
                        return;
                    }
                    installed.put(modId, new ArtifactFingerprint(mod.getVersion().toString(), sha256(file)));
                }

                if (!"rpgskilltree".equals(modId) && mod.getDependencies().stream().anyMatch(dep ->
                    "epicfight".equals(dep.getModId())
                        && (dep.getType() == IModInfo.DependencyType.REQUIRED
                            || dep.getType() == IModInfo.DependencyType.OPTIONAL))) {
                    epicFightDependents.add(modId);
                }
            }
            this.apply = ImpactStaminaCompatibilityPolicy.isCertifiedArtifacts(installed, epicFightDependents);
        } catch (RuntimeException | java.io.IOException failure) {
            this.apply = false;
        }
    }

    private static String sha256(java.nio.file.Path path) throws java.io.IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream input = Files.newInputStream(path)) {
                byte[] buffer = new byte[64 * 1024];
                for (int read; (read = input.read(buffer)) >= 0;) {
                    if (read > 0) digest.update(buffer, 0, read);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (java.security.NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 unavailable", impossible);
        }
    }

    @Override public String getRefMapperConfig() { return null; }
    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return apply; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
