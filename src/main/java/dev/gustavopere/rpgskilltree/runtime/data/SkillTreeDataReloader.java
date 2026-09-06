package dev.gustavopere.rpgskilltree.runtime.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.effects.AttributeEffectDiagnostics;
import dev.gustavopere.rpgskilltree.runtime.effects.NodeEffectRuntime;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Owns the authoritative skill-tree datapack transaction. The complete candidate is prepared
 * and validated before apply publishes one revision, so a failed reload leaves the prior state intact.
 */
public final class SkillTreeDataReloader extends SimplePreparableReloadListener<PreparedSkillTreeReload> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillTreeDataReloader.class);

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new SkillTreeDataReloader());
    }

    @Override
    protected @NotNull PreparedSkillTreeReload prepare(
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        try {
            Map<ResourceLocation, JsonElement> skillResources = readJsonResources(resourceManager, "skills");
            PreparedSkillTreeData skillTreeData = SkillTreeDataLoader.prepare(
                readJsonResources(resourceManager, "node_rules"),
                readJsonResources(resourceManager, "node_effects"),
                skillResources,
                SkillTreeDataLoader.closedCombatRules()
            );
            return new PreparedSkillTreeReload(
                skillTreeData,
                SkillInvestmentMetadataParser.parse(skillResources)
            );
        } catch (RuntimeException failure) {
            LOGGER.error("Authoritative skill-tree datapack preparation failed; preserving previous revision", failure);
            throw failure;
        }
    }

    @Override
    protected void apply(
        @NotNull PreparedSkillTreeReload prepared,
        @NotNull ResourceManager resourceManager,
        @NotNull ProfilerFiller profiler
    ) {
        SkillTreeDataCatalog.publish(prepared.skillTreeData());
        ClassInvestmentMetadataCatalog.install(
            SkillTreeDataCatalog.current().revision(),
            prepared.classInvestmentMetadata()
        );
        AttributeEffectDiagnostics.clear();

        var server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().getPlayers().forEach(player ->
                NodeEffectRuntime.refresh(player, PlayerProgressionRuntime.get(player))
            );
        }
        LOGGER.info(
            "Published authoritative skill-tree revision {} with {} nodes, {} class-investment metadata entries, {} attribute effects and {} behavior effects",
            SkillTreeDataCatalog.current().revision(),
            SkillTreeDataCatalog.current().definitions().size(),
            ClassInvestmentMetadataCatalog.current().nodeMetadata().size(),
            SkillTreeDataCatalog.current().attributeEffects().size(),
            SkillTreeDataCatalog.current().behaviorEffects().size()
        );
    }

    private static Map<ResourceLocation, JsonElement> readJsonResources(ResourceManager manager, String path) {
        Map<ResourceLocation, JsonElement> result = new LinkedHashMap<>();
        Map<ResourceLocation, Resource> resources = manager.listResources(
            path,
            id -> id.getPath().endsWith(".json")
        );
        resources.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(java.util.Comparator.comparing(ResourceLocation::toString)))
            .forEach(entry -> {
                ResourceLocation id = entry.getKey();
                try (var reader = entry.getValue().openAsReader()) {
                    result.put(id, JsonParser.parseReader(reader));
                } catch (IOException | RuntimeException failure) {
                    if (failure instanceof SkillTreeDataValidationException validation) throw validation;
                    throw new SkillTreeDataValidationException(
                        id,
                        null,
                        "resource",
                        "could not read/parse JSON resource",
                        failure
                    );
                }
            });
        return Map.copyOf(result);
    }
}
