package dev.gustavopere.rpgskilltree.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Exact version and binary gate for the provider/addons audited for P-0035. */
public final class ImpactStaminaCompatibilityPolicy {
    public static final String SUPPORTED_EPIC_FIGHT_VERSION = "21.17.3.1";
    public static final String SUPPORTED_EPIC_FIGHT_SHA256 =
        "8b882554cf10086398340fbdc741819ee72a801a3adce516c7f4768326a39526";

    private static final Map<String, ArtifactFingerprint> CERTIFIED_ARTIFACTS = Map.ofEntries(
        Map.entry("epicfight", new ArtifactFingerprint(SUPPORTED_EPIC_FIGHT_VERSION, SUPPORTED_EPIC_FIGHT_SHA256)),
        Map.entry("wom", new ArtifactFingerprint("2.0.176", "eea940044fd1216a4f66dc96d36f3bc4cb6f7a693b269d94d4153bdc651bc8c5")),
        Map.entry("efiscompat", new ArtifactFingerprint("3.1.0", "5d6232d7468b005e9dbcaf9c63ad00fde40b54955dc1aa3938daebdddb1a15c5")),
        Map.entry("mcea", new ArtifactFingerprint("21.17.0.1", "dbfe733ae03f64fe9812916023a8aa75f8f019acabda6328cb9f2c32e515c73a")),
        Map.entry("epicparcool", new ArtifactFingerprint("21.0.0", "cb8cd4f6d95f2a0b6e82beab117e36aa1933bdc4693d686395ccd4640e02929d")),
        Map.entry("epic_api", new ArtifactFingerprint("21.3.1", "be644e8365287bb90d399e441d8aa6972328c3b95c573ad41e541b65fcb25dd2")),
        Map.entry("epic_colonies", new ArtifactFingerprint("21.0.8", "bcab85c7c0b46217acdbc6240796079eb77326c75835074a9760d8abb07aeecc")),
        Map.entry("epicfightcompat", new ArtifactFingerprint("1.1.0", "df4df816cf8b8faab48bd0e5ca74b5943a8d00d48e8869136626d3e6598b74fa")),
        Map.entry("epicfight_curios_compat", new ArtifactFingerprint("1.4", "6228e8761342cce733e3d3c0d8ca836e9150d8e6487e6ac780c461dc30a18588")),
        Map.entry("punchy_epicfight_compat", new ArtifactFingerprint("1.0.0", "5d1c33ded9b1f64a8aa75f1cdb05e163bb4a6199e575e602e4a7e08fd0c367e6"))
    );

    public static final Map<String, String> AUDITED_OPTIONAL_ADDONS;
    static {
        Map<String, String> versions = new LinkedHashMap<>();
        CERTIFIED_ARTIFACTS.forEach((id, artifact) -> {
            if (!"epicfight".equals(id)) versions.put(id, artifact.version());
        });
        AUDITED_OPTIONAL_ADDONS = Map.copyOf(versions);
    }

    private ImpactStaminaCompatibilityPolicy() {}

    public record ArtifactFingerprint(String version, String sha256) {
        public ArtifactFingerprint {
            Objects.requireNonNull(version);
            Objects.requireNonNull(sha256);
            sha256 = sha256.toLowerCase(java.util.Locale.ROOT);
        }
    }

    public static boolean isCertified(Map<String, String> installedVersions, Set<String> declaredEpicFightDependents) {
        Objects.requireNonNull(installedVersions);
        Objects.requireNonNull(declaredEpicFightDependents);
        if (!SUPPORTED_EPIC_FIGHT_VERSION.equals(installedVersions.get("epicfight"))) return false;
        for (var audited : AUDITED_OPTIONAL_ADDONS.entrySet()) {
            String installed = installedVersions.get(audited.getKey());
            if (installed != null && !audited.getValue().equals(installed)) return false;
        }
        for (String dependent : declaredEpicFightDependents) {
            String expected = AUDITED_OPTIONAL_ADDONS.get(dependent);
            if (expected == null || !expected.equals(installedVersions.get(dependent))) return false;
        }
        return true;
    }

    public static boolean isCertifiedArtifacts(
        Map<String, ArtifactFingerprint> installedArtifacts,
        Set<String> declaredEpicFightDependents
    ) {
        Objects.requireNonNull(installedArtifacts);
        Objects.requireNonNull(declaredEpicFightDependents);

        if (!matches("epicfight", installedArtifacts.get("epicfight"))) return false;
        for (String id : AUDITED_OPTIONAL_ADDONS.keySet()) {
            ArtifactFingerprint installed = installedArtifacts.get(id);
            if (installed != null && !matches(id, installed)) return false;
        }
        for (String dependent : declaredEpicFightDependents) {
            if (!AUDITED_OPTIONAL_ADDONS.containsKey(dependent) || !matches(dependent, installedArtifacts.get(dependent))) {
                return false;
            }
        }
        return true;
    }

    private static boolean matches(String modId, ArtifactFingerprint installed) {
        ArtifactFingerprint expected = CERTIFIED_ARTIFACTS.get(modId);
        return expected != null && expected.equals(installed);
    }
}
