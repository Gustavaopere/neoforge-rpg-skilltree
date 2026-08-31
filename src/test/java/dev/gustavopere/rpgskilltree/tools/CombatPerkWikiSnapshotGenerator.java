package dev.gustavopere.rpgskilltree.tools;

import dev.gustavopere.rpgskilltree.core.CombatPerkPlayerTextCatalog;
import dev.gustavopere.rpgskilltree.core.CombatPerkTreeModel;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkCatalog;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Build-time snapshot generator for the semantic A0001-A0100 combat-perk tree.
 *
 * <p>Acquisition facts come only from {@link CombatPerkTreeModel}; names come from the versioned
 * Notion snapshot already used by the client; player-facing descriptions are emitted only for
 * perks present in {@link CombatPerkPlayerTextCatalog}. Runtime policy code is never translated
 * back into prose.</p>
 */
public final class CombatPerkWikiSnapshotGenerator {
    public static final Path DEFAULT_PATH = Path.of("build/generated-wiki/combat-perks.json");
    private static final String TREE_ID = "rpgskilltree:runtime/combat_perks";

    private CombatPerkWikiSnapshotGenerator() {}

    public static String renderJson() {
        List<CombatPerkTreeModel.Node> nodes = new ArrayList<>(CombatPerkTreeModel.all());
        nodes.sort(Comparator.comparing(CombatPerkTreeModel.Node::code));
        if (nodes.size() != 100) {
            throw new IllegalStateException("expected exactly 100 semantic combat perks, got " + nodes.size());
        }

        StringBuilder out = new StringBuilder();
        out.append("{\n");
        out.append("  \"schema\": 1,\n");
        out.append("  \"treeId\": ").append(json(TREE_ID)).append(",\n");
        out.append("  \"nodes\": [\n");

        for (int index = 0; index < nodes.size(); index++) {
            CombatPerkTreeModel.Node node = nodes.get(index);
            var definition = NotionCombatPerkCatalog.definition(node.code())
                .orElseThrow(() -> new IllegalStateException("missing canonical combat definition: " + node.code()));
            if (definition.maxRank() != node.maxRank() || definition.rankCost() != node.costPerRank()) {
                throw new IllegalStateException("combat catalog/tree rank-cost drift: " + node.code());
            }

            String description = CombatPerkPlayerTextCatalog.entry(node.code())
                .map(CombatPerkPlayerTextCatalog.PlayerText::effect)
                .orElse(null);

            out.append("    {\n");
            out.append("      \"id\": ").append(json(node.nodeId())).append(",\n");
            out.append("      \"code\": ").append(json(node.code())).append(",\n");
            out.append("      \"name\": ").append(json(definition.name())).append(",\n");
            out.append("      \"description\": ").append(description == null ? "null" : json(description)).append(",\n");
            out.append("      \"maxRank\": ").append(node.maxRank()).append(",\n");
            out.append("      \"costPerRank\": ").append(node.costPerRank()).append(",\n");
            out.append("      \"startingPoint\": ").append(node.startingPoint()).append(",\n");
            out.append("      \"minCharacterLevel\": ").append(node.minCharacterLevel()).append(",\n");
            out.append("      \"requiredMastery\": ");
            appendIntMap(out, node.requiredMastery(), 6);
            out.append(",\n");
            out.append("      \"requiredNodeRanks\": ");
            appendIntMap(out, node.requiredNodeRanks(), 6);
            out.append("\n");
            out.append("    }");
            if (index + 1 < nodes.size()) {
                out.append(',');
            }
            out.append('\n');
        }

        out.append("  ]\n");
        out.append("}\n");
        return out.toString();
    }

    public static void write(Path path) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(path, renderJson(), StandardCharsets.UTF_8);
    }

    public static void check(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("semantic combat wiki snapshot is missing: " + path);
        }
        String actual = Files.readString(path, StandardCharsets.UTF_8);
        String expected = renderJson();
        if (!expected.equals(actual)) {
            throw new IllegalStateException("semantic combat wiki snapshot is stale: " + path);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "--write".equals(args[0])) {
            Path path = args.length >= 2 ? Path.of(args[1]) : DEFAULT_PATH;
            write(path);
            System.out.println("Generated semantic combat wiki snapshot: " + path);
            return;
        }
        if ("--check".equals(args[0])) {
            Path path = args.length >= 2 ? Path.of(args[1]) : DEFAULT_PATH;
            check(path);
            System.out.println("Semantic combat wiki snapshot drift check: PASS");
            return;
        }
        if ("--print".equals(args[0])) {
            System.out.print(renderJson());
            return;
        }
        throw new IllegalArgumentException("usage: --write [path] | --check [path] | --print");
    }

    private static void appendIntMap(StringBuilder out, Map<String, Integer> values, int indent) {
        if (values.isEmpty()) {
            out.append("{}");
            return;
        }
        String prefix = " ".repeat(indent);
        String entryPrefix = " ".repeat(indent + 2);
        out.append("{\n");
        List<Map.Entry<String, Integer>> entries = values.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .toList();
        for (int index = 0; index < entries.size(); index++) {
            Map.Entry<String, Integer> entry = entries.get(index);
            out.append(entryPrefix).append(json(entry.getKey())).append(": ").append(entry.getValue());
            if (index + 1 < entries.size()) {
                out.append(',');
            }
            out.append('\n');
        }
        out.append(prefix).append('}');
    }

    private static String json(String value) {
        StringBuilder out = new StringBuilder(value.length() + 16);
        out.append('"');
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"' -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\b' -> out.append("\\b");
                case '\f' -> out.append("\\f");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if (character < 0x20) {
                        out.append("\\u%04x".formatted((int) character));
                    } else {
                        out.append(character);
                    }
                }
            }
        }
        return out.append('"').toString();
    }
}
