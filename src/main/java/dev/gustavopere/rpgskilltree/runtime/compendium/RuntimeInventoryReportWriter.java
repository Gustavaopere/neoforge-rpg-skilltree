package dev.gustavopere.rpgskilltree.runtime.compendium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.compendium.catalog.RegistryInventoryEntry;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import net.minecraft.SharedConstants;
import net.neoforged.fml.ModList;

public final class RuntimeInventoryReportWriter {
    public static final Path DEFAULT_OUTPUT = Path.of("generated", "compendium", "runtime-registry-inventory.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private RuntimeInventoryReportWriter() {}

    public static Path write(List<RegistryInventoryEntry> entries) throws IOException {
        return write(entries, DEFAULT_OUTPUT);
    }

    public static Path write(List<RegistryInventoryEntry> entries, Path output) throws IOException {
        JsonObject root = new JsonObject();
        root.addProperty("schema", 1);
        root.addProperty("generated_at_utc", Instant.now().toString());
        root.addProperty("minecraft_version", SharedConstants.getCurrentVersion().getName());
        root.addProperty("loader", "neoforge");

        JsonArray mods = new JsonArray();
        ArrayList<String> fingerprintLines = new ArrayList<>();
        ModList.get().getMods().stream()
            .sorted(Comparator.comparing(info -> info.getModId()))
            .forEach(info -> {
                JsonObject mod = new JsonObject();
                mod.addProperty("mod_id", info.getModId());
                mod.addProperty("display_name", info.getDisplayName());
                mod.addProperty("runtime_version", info.getVersion().toString());
                mods.add(mod);
                fingerprintLines.add("mod|" + info.getModId() + "|" + info.getVersion());
            });
        root.add("loaded_mods", mods);

        JsonArray registry = new JsonArray();
        entries.stream().sorted(Comparator.comparing(RegistryInventoryEntry::key)).forEach(entry -> {
            JsonObject item = new JsonObject();
            item.addProperty("kind", entry.kind().name());
            item.addProperty("resource_location", entry.resourceLocation());
            item.addProperty("namespace", entry.namespace());
            item.addProperty("translation_key", entry.translationKey());
            item.addProperty("mod_display_name", entry.modDisplayName());
            item.addProperty("registry_source", entry.registrySource());
            item.addProperty("present_at_runtime", entry.presentAtRuntime());
            registry.add(item);
            fingerprintLines.add("entry|" + entry.key());
        });
        root.add("entries", registry);
        root.addProperty("entry_count", entries.size());
        root.addProperty("runtime_fingerprint_sha256", sha256(fingerprintLines));

        Path absolute = output.toAbsolutePath().normalize();
        Files.createDirectories(absolute.getParent());
        Files.writeString(absolute, GSON.toJson(root) + System.lineSeparator(), StandardCharsets.UTF_8);
        return absolute;
    }

    private static String sha256(List<String> lines) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            lines.stream().sorted().forEach(line -> {
                digest.update(line.getBytes(StandardCharsets.UTF_8));
                digest.update((byte) '\n');
            });
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException exc) {
            throw new IllegalStateException("SHA-256 unavailable", exc);
        }
    }
}
