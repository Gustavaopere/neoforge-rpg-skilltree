package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/** Pins audited Epic Fight-facing addon binaries and rejects transformations/listeners that invalidate P-0035 invariants. */
public final class EpicFightImpactAddonBinaryContractTest {
    private static final byte[] VANILLA_HOOKS =
        "yesman/epicfight/api/event/impl/VanillaEntityEventHooks".getBytes(StandardCharsets.UTF_8);
    private static final byte[] DAMAGE_STUN_SHIELD = "damageStunShield".getBytes(StandardCharsets.UTF_8);
    private static final byte[] ON_STUNNED = "ON_STUNNED".getBytes(StandardCharsets.UTF_8);

    private EpicFightImpactAddonBinaryContractTest() {}

    public static void main(String[] args) throws Exception {
        require(args.length > 0 && args.length % 4 == 0,
            "usage: (<modId> <version> <sha256> <jarPath>)+");
        for (int i = 0; i < args.length; i += 4) {
            verify(args[i], args[i + 1], args[i + 2].toLowerCase(), Path.of(args[i + 3]));
        }
        System.out.println("EpicFightImpactAddonBinaryContractTest: PASS (" + (args.length / 4) + " artifacts)");
    }

    private static void verify(String modId, String version, String expectedSha, Path path) throws Exception {
        require(Files.isRegularFile(path), "missing audited addon " + modId + ": " + path);
        String actualSha = sha256(path);
        require(expectedSha.equals(actualSha), modId + " SHA-256 drift: expected=" + expectedSha + " actual=" + actualSha);

        try (JarFile jar = new JarFile(path.toFile())) {
            JarEntry metadataEntry = jar.getJarEntry("META-INF/neoforge.mods.toml");
            require(metadataEntry != null, modId + " missing NeoForge metadata");
            String metadata;
            try (InputStream input = jar.getInputStream(metadataEntry)) {
                metadata = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            }
            require(containsTomlValue(metadata, "modId", modId), modId + " packaged modId drifted");
            require(containsTomlValue(metadata, "version", version), modId + " packaged version drifted from " + version);

            var entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
                byte[] bytes;
                try (InputStream input = jar.getInputStream(entry)) {
                    bytes = input.readAllBytes();
                }
                require(!contains(bytes, VANILLA_HOOKS),
                    modId + " now references/transforms VanillaEntityEventHooks in " + entry.getName());
                require(!contains(bytes, DAMAGE_STUN_SHIELD),
                    modId + " now references damageStunShield in " + entry.getName());
                require(!contains(bytes, ON_STUNNED),
                    modId + " now registers/references ON_STUNNED in " + entry.getName());
            }
        }
        System.out.println("AUDITED_ADDON=" + modId + '@' + version + " SHA256=" + actualSha);
    }

    private static boolean containsTomlValue(String text, String key, String value) {
        String compact = text.replace(" ", "").replace("\t", "");
        return compact.contains(key + "=\"" + value + "\"");
    }

    private static boolean contains(byte[] haystack, byte[] needle) {
        outer: for (int i = 0; i <= haystack.length - needle.length; i++) {
            for (int j = 0; j < needle.length; j++) if (haystack[i + j] != needle[j]) continue outer;
            return true;
        }
        return false;
    }

    private static String sha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(path)) {
            byte[] buffer = new byte[64 * 1024];
            for (int read; (read = input.read(buffer)) >= 0;) if (read > 0) digest.update(buffer, 0, read);
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
