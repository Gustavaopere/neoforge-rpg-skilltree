package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;

/** Loads explicit Battle Mage spell support from data/rpgskilltree/battle_mage_spell_profiles. */
public final class BattleMageSpellProfileReloader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public BattleMageSpellProfileReloader() {
        super(GSON, "battle_mage_spell_profiles");
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new BattleMageSpellProfileReloader());
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> resources,
        @NotNull ResourceManager manager,
        @NotNull ProfilerFiller profiler
    ) {
        LinkedHashMap<String, BattleMageSpellProfile> profiles = new LinkedHashMap<>();
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                BattleMageSpellProfile profile = parse(entry.getKey(), entry.getValue());
                if (profiles.putIfAbsent(profile.spellId(), profile) != null) {
                    throw new IllegalArgumentException(entry.getKey() + ": duplicate Battle Mage spell profile " + profile.spellId());
                }
            });
        BattleMageSpellProfileCatalog.replace(profiles);
    }

    static BattleMageSpellProfile parse(ResourceLocation resourceId, JsonElement element) {
        if (element == null || !element.isJsonObject()) {
            throw new IllegalArgumentException(resourceId + ": Battle Mage spell profile root must be an object");
        }
        JsonObject root = element.getAsJsonObject();
        String spell = requireString(root, "spell", resourceId);
        BattleMageTargetMode targetMode;
        try {
            targetMode = BattleMageTargetMode.valueOf(requireString(root, "target_mode", resourceId));
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(resourceId + ": invalid target_mode", exception);
        }

        return new BattleMageSpellProfile(
            spell,
            targetMode,
            requireInt(root, "priority", resourceId),
            requireDouble(root, "min_range", resourceId),
            requireDouble(root, "max_range", resourceId),
            requireDouble(root, "friendly_fire_radius", resourceId),
            optionalBoolean(root, "world_effect", false, resourceId),
            optionalBoolean(root, "ally_safe", false, resourceId)
        );
    }

    private static String requireString(JsonObject root, String key, ResourceLocation resourceId) {
        if (!root.has(key) || !root.get(key).isJsonPrimitive()) {
            throw new IllegalArgumentException(resourceId + ": missing/invalid " + key);
        }
        String value = root.get(key).getAsString();
        if (value.isBlank()) {
            throw new IllegalArgumentException(resourceId + ": blank " + key);
        }
        return value;
    }

    private static int requireInt(JsonObject root, String key, ResourceLocation resourceId) {
        try {
            if (!root.has(key)) throw new IllegalArgumentException("missing");
            return root.get(key).getAsInt();
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(resourceId + ": missing/invalid " + key, exception);
        }
    }

    private static double requireDouble(JsonObject root, String key, ResourceLocation resourceId) {
        try {
            if (!root.has(key)) throw new IllegalArgumentException("missing");
            return root.get(key).getAsDouble();
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(resourceId + ": missing/invalid " + key, exception);
        }
    }

    private static boolean optionalBoolean(
        JsonObject root,
        String key,
        boolean fallback,
        ResourceLocation resourceId
    ) {
        if (!root.has(key)) return fallback;
        try {
            return root.get(key).getAsBoolean();
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(resourceId + ": invalid " + key, exception);
        }
    }
}
