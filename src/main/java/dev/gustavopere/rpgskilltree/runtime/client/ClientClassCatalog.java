package dev.gustavopere.rpgskilltree.runtime.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ClassUnlockDefinition;
import dev.gustavopere.rpgskilltree.core.ClassUnlockResolver;
import dev.gustavopere.rpgskilltree.core.ClassUnlockResult;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public final class ClientClassCatalog {
    private static final Gson GSON = new Gson();
    private static final String RESOURCE = "/assets/rpgskilltree/tree/paid_classes.json";
    private static final List<Entry> ENTRIES = load();

    private ClientClassCatalog() {}

    public static List<Entry> entries() {
        return ENTRIES;
    }

    public static List<View> visibleFor(ProgressionState state) {
        Objects.requireNonNull(state);
        return ENTRIES.stream()
            .filter(entry -> !state.classProgression().isUnlocked(entry.definition().classId()))
            .map(entry -> new View(
                entry,
                ClassUnlockResolver.evaluate(
                    state.finalTriads(),
                    entry.definition(),
                    state.passivePoints().available()
                )
            ))
            .sorted(Comparator.comparing(view -> view.entry().definition().classId()))
            .toList();
    }

    private static List<Entry> load() {
        try (var stream = ClientClassCatalog.class.getResourceAsStream(RESOURCE)) {
            if (stream == null) throw new IllegalStateException("missing client paid class catalog: " + RESOURCE);
            JsonObject root = GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
            JsonArray classes = root.getAsJsonArray("classes");
            List<Entry> result = new ArrayList<>();
            for (var element : classes) {
                JsonObject object = element.getAsJsonObject();
                String id = object.get("id").getAsString();
                EnumSet<ProgressionDomain> domains = EnumSet.noneOf(ProgressionDomain.class);
                object.getAsJsonArray("requiredCompletedDomains")
                    .forEach(value -> domains.add(ProgressionDomain.valueOf(value.getAsString())));
                int bridgeCost = object.get("bridgeCost").getAsInt();
                String displayKey = object.get("displayKey").getAsString();
                result.add(new Entry(
                    new ClassUnlockDefinition(id, domains, false, bridgeCost),
                    displayKey
                ));
            }
            return result.stream()
                .sorted(Comparator.comparing(entry -> entry.definition().classId()))
                .toList();
        } catch (Exception exception) {
            throw new IllegalStateException("failed to load paid class catalog", exception);
        }
    }

    public record Entry(ClassUnlockDefinition definition, String displayKey) {
        public Entry {
            Objects.requireNonNull(definition);
            Objects.requireNonNull(displayKey);
        }
    }

    public record View(Entry entry, ClassUnlockResult result) {
        public View {
            Objects.requireNonNull(entry);
            Objects.requireNonNull(result);
        }
    }
}
