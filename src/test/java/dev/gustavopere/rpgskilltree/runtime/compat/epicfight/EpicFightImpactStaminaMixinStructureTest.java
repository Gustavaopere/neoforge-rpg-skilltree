package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.nio.file.Files;
import java.nio.file.Path;

public final class EpicFightImpactStaminaMixinStructureTest {
    private static final Path MIXIN = Path.of("src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/VanillaEntityImpactStaminaMixin.java");
    private static final Path PLUGIN = Path.of("src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/EpicFightImpactMixinPlugin.java");
    private static final Path BRIDGE = Path.of("src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightImpactStaminaBridge.java");
    private static final Path CONFIG = Path.of("src/main/resources/rpgskilltree-compat-epicfight-impact.mixins.json");
    private static final Path MODS = Path.of("src/main/resources/META-INF/neoforge.mods.toml");

    private EpicFightImpactStaminaMixinStructureTest() {}

    public static void main(String[] args) throws Exception {
        require(Files.isRegularFile(MIXIN), "missing restricted impact mixin");
        require(Files.isRegularFile(PLUGIN), "missing exact-version mixin plugin");
        require(Files.isRegularFile(BRIDGE), "missing generic bridge");
        require(Files.isRegularFile(CONFIG), "missing dedicated mixin config");

        String source = Files.readString(MIXIN);
        require(source.contains("@WrapMethod(method = PRE_METHOD)"), "scope must wrap original without copying body");
        require(count(source, "original.call(hitEntity, damageSource, amount, modifiedDamageApplier)") == 1,
            "scope wrapper must invoke provider method exactly once");
        require(source.contains("try (var ignored = ImpactStaminaInvocationGuard.open(damageSource, hitEntity))"),
            "scope wrapper must guarantee finally-style exception cleanup");
        require(source.contains("target = DAMAGE_STUN_SHIELD"), "only commit target may be damageStunShield");
        require(source.contains("shift = At.Shift.BEFORE"), "commit must be immediately before damageStunShield");
        require(source.contains("require = 1") && source.contains("expect = 1") && source.contains("allow = 1"),
            "commit target count must fail closed on drift");
        require(source.contains("@Local(name = \"impact\") LocalFloatRef impact"), "impact must be mutable provider local");
        require(source.contains("@Local(name = \"stunShield\") float stunShield"), "shield snapshot must come from provider frame");
        require(source.contains("@Local(name = \"stunType\") StunType stunType"), "effective stun type must come from provider frame");
        require(source.contains("@Local(name = \"hitEntityPatchAsHurtable\") HurtableEntityPatch<?> hitPatch"),
            "victim patch must come from same provider frame");
        require(count(source, "EpicFightImpactStaminaBridge.tryCommit(") == 1, "one commit callback only");
        require(!source.contains("calculateImpact"), "calculateImpact must never be the commit point");
        require(!source.contains("@Overwrite"), "must not overwrite onCalculateDamagePre");
        require(!source.contains("@Redirect"), "must not redirect provider pipeline");
        require(!source.contains("damageStunShield(finalDamage"), "must not reimplement provider stun-shield mutation");

        String config = Files.readString(CONFIG);
        require(config.contains("\"required\": true"), "gated impact mixin config must be required");
        require(config.contains("EpicFightImpactMixinPlugin"), "config must use compatibility gate");
        require(count(config, "VanillaEntityImpactStaminaMixin") == 1, "exactly one impact mixin expected");

        String mods = Files.readString(MODS);
        require(count(mods, "rpgskilltree-compat-epicfight-impact.mixins.json") == 1, "config must be registered once");

        String bridge = Files.readString(BRIDGE);
        String plugin = Files.readString(PLUGIN);
        require(!bridge.contains("A0107") && !source.contains("A0107") && !plugin.contains("A0107"),
            "infrastructure must not connect A0107");
        require(plugin.contains("getFilePath()") && plugin.contains("sha256(file)")
                && plugin.contains("isCertifiedArtifacts") && plugin.contains("getDependencies()"),
            "Mixin plugin must gate on loaded binary fingerprints and declared Epic Fight dependencies");
        require(bridge.contains("registerRequestSource"), "future consumer must register explicitly");
        require(bridge.contains("ImpactStaminaTransaction.tryDebitExactNativeStamina"), "bridge must use exact debit primitive");
        require(bridge.contains("victimPatch.getOriginal() != victim") && bridge.contains("victimPatch.isLogicalClient()"),
            "bridge must fail closed on victim-patch mismatch and logical client execution");
        System.out.println("EpicFightImpactStaminaMixinStructureTest: PASS");
    }

    private static int count(String source, String token) {
        int count = 0;
        for (int at = 0; (at = source.indexOf(token, at)) >= 0; at += token.length()) count++;
        return count;
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
