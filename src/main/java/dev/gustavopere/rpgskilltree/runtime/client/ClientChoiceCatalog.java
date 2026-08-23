package dev.gustavopere.rpgskilltree.runtime.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.gustavopere.rpgskilltree.core.ClassChoiceDefinition;
import dev.gustavopere.rpgskilltree.core.ClassChoicePolicy;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class ClientChoiceCatalog {
    private static final Gson GSON = new Gson();
    private static final String RESOURCE = "/assets/rpgskilltree/tree/class_choices.json";
    private static final List<Entry> ENTRIES = load();

    private ClientChoiceCatalog() {}

    public static List<View> visibleFor(ProgressionState state) {
        Objects.requireNonNull(state);
        List<View> views = new ArrayList<>();
        for (Entry entry : ENTRIES) {
            var definition = entry.definition();
            if (!state.classProgression().isUnlocked(definition.requiredClassId())) continue;
            boolean selected = state.classChoices()
                .selectedInGroup(definition.groupId())
                .contains(definition.choiceId());
            boolean canSelect = ClassChoicePolicy.canSelect(
                state.classChoices(),
                definition,
                state.classProgression().unlockedClassIds(),
                definition.defaultGroupCapacity()
            );
            views.add(new View(entry, selected, canSelect));
        }
        return views.stream()
            .sorted(Comparator.comparing(view -> view.entry().definition().choiceId()))
            .toList();
    }

    private static List<Entry> load() {
        try (var stream = ClientChoiceCatalog.class.getResourceAsStream(RESOURCE)) {
            if (stream == null) throw new IllegalStateException("missing client class choice catalog: " + RESOURCE);
            JsonObject root = GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class);
            List<Entry> result = new ArrayList<>();
            root.getAsJsonArray("choices").forEach(element -> {
                JsonObject object = element.getAsJsonObject();
                result.add(new Entry(
                    new ClassChoiceDefinition(
                        object.get("id").getAsString(),
                        object.get("requiredClassId").getAsString(),
                        object.get("groupId").getAsString(),
                        object.get("capacity").getAsInt()
                    ),
                    object.get("displayKey").getAsString()
                ));
            });
            return result.stream()
                .sorted(Comparator.comparing(entry -> entry.definition().choiceId()))
                .toList();
        } catch (Exception exception) {
            throw new IllegalStateException("failed to load client class choices", exception);
        }
    }

    public record Entry(ClassChoiceDefinition definition, String displayKey) {
        public Entry {
            Objects.requireNonNull(definition);
            Objects.requireNonNull(displayKey);
        }
    }

    public record View(Entry entry, boolean selected, boolean canSelect) {
        public View {
            Objects.requireNonNull(entry);
        }
    }
}
