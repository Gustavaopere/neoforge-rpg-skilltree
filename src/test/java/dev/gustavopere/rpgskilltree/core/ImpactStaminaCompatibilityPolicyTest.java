package dev.gustavopere.rpgskilltree.core;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class ImpactStaminaCompatibilityPolicyTest {
    private ImpactStaminaCompatibilityPolicyTest() {}

    public static void main(String[] args) throws Exception {
        exactProviderAndAuditedAddonsAreCertified();
        providerVersionDriftFailsClosed();
        auditedAddonVersionDriftFailsClosed();
        unknownEpicFightDependentFailsClosed();
        unrelatedModsDoNotInvalidateCertification();
        mceaVersionIsPinnedEvenWithoutDeclaredEpicFightDependency();
        exactBinaryFingerprintsAreCertified();
        sameVersionDifferentBinaryFailsClosed();
        System.out.println("ImpactStaminaCompatibilityPolicyTest: PASS");
    }

    private static void exactProviderAndAuditedAddonsAreCertified() throws Exception {
        Set<String> deps = Set.of("wom", "efiscompat", "epicparcool", "epic_api", "epic_colonies",
            "epicfightcompat", "epicfight_curios_compat", "punchy_epicfight_compat");
        require(isCertified(exactInstalled(), deps), "exact audited environment must be certified");
    }

    private static void providerVersionDriftFailsClosed() throws Exception {
        require(!isCertified(Map.of("epicfight", "21.17.4"), Set.of()), "provider drift must fail closed");
        require(!isCertified(Map.of(), Set.of()), "missing provider must fail closed");
    }

    private static void auditedAddonVersionDriftFailsClosed() throws Exception {
        Map<String, String> installed = new LinkedHashMap<>(exactInstalled());
        installed.put("wom", "2.0.177");
        require(!isCertified(installed, Set.of("wom")), "WoM drift must fail closed");
        installed = new LinkedHashMap<>(exactInstalled());
        installed.put("epicfight_curios_compat", "2.2");
        require(!isCertified(installed, Set.of("epicfight_curios_compat")),
            "runtime packaged Curios version is 1.4, not filename 2.2");
    }

    private static void unknownEpicFightDependentFailsClosed() throws Exception {
        Map<String, String> installed = new LinkedHashMap<>(exactInstalled());
        installed.put("future_epic_addon", "1.0.0");
        require(!isCertified(installed, Set.of("future_epic_addon")), "unknown Epic Fight dependent must fail closed");
    }

    private static void unrelatedModsDoNotInvalidateCertification() throws Exception {
        Map<String, String> installed = new LinkedHashMap<>();
        installed.put("epicfight", "21.17.3.1");
        installed.put("unrelated_mod", "99.0");
        require(isCertified(installed, Set.of()), "unrelated mods must not make gate globally unusable");
    }

    private static void mceaVersionIsPinnedEvenWithoutDeclaredEpicFightDependency() throws Exception {
        Map<String, String> installed = new LinkedHashMap<>();
        installed.put("epicfight", "21.17.3.1");
        installed.put("mcea", "21.17.0.2");
        require(!isCertified(installed, Set.of()), "audited MCEA drift must fail closed");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void exactBinaryFingerprintsAreCertified() throws Exception {
        Class<?> policy = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy");
        Class<?> fingerprint = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy$ArtifactFingerprint");
        var ctor = fingerprint.getConstructor(String.class, String.class);
        Map<String, Object> installed = new LinkedHashMap<>();
        installed.put("epicfight", ctor.newInstance("21.17.3.1", "8b882554cf10086398340fbdc741819ee72a801a3adce516c7f4768326a39526"));
        installed.put("wom", ctor.newInstance("2.0.176", "eea940044fd1216a4f66dc96d36f3bc4cb6f7a693b269d94d4153bdc651bc8c5"));
        Method method = policy.getMethod("isCertifiedArtifacts", Map.class, Set.class);
        boolean certified = (boolean) method.invoke(null, installed, Set.of("wom"));
        require(certified, "exact provider/addon binary fingerprints must be certified");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void sameVersionDifferentBinaryFailsClosed() throws Exception {
        Class<?> policy = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy");
        Class<?> fingerprint = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy$ArtifactFingerprint");
        var ctor = fingerprint.getConstructor(String.class, String.class);
        Map<String, Object> installed = new LinkedHashMap<>();
        installed.put("epicfight", ctor.newInstance("21.17.3.1", "deadbeef"));
        Method method = policy.getMethod("isCertifiedArtifacts", Map.class, Set.class);
        boolean certified = (boolean) method.invoke(null, installed, Set.of());
        require(!certified, "same provider version with different binary must fail closed");
    }

    private static Map<String, String> exactInstalled() {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("epicfight", "21.17.3.1");
        m.put("wom", "2.0.176");
        m.put("efiscompat", "3.1.0");
        m.put("mcea", "21.17.0.1");
        m.put("epicparcool", "21.0.0");
        m.put("epic_api", "21.3.1");
        m.put("epic_colonies", "21.0.8");
        m.put("epicfightcompat", "1.1.0");
        m.put("epicfight_curios_compat", "1.4");
        m.put("punchy_epicfight_compat", "1.0.0");
        return m;
    }

    private static boolean isCertified(Map<String, String> installed, Set<String> deps) throws Exception {
        Class<?> policy;
        try { policy = Class.forName("dev.gustavopere.rpgskilltree.core.ImpactStaminaCompatibilityPolicy"); }
        catch (ClassNotFoundException missing) { throw new AssertionError("missing compatibility policy", missing); }
        Method method = policy.getMethod("isCertified", Map.class, Set.class);
        return (boolean) method.invoke(null, installed, deps);
    }

    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
