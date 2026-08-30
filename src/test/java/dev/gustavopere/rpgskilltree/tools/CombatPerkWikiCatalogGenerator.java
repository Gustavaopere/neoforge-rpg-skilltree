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
 * Build-time generator for the semantic A0001-A0100 combat-perk wiki catalog.
 *
 * <p>The acquisition facts come exclusively from {@link CombatPerkTreeModel}; names come from the
 * versioned Notion snapshot already used by the client; player-facing effect text is emitted only
 * when the perk has an approved entry in {@link CombatPerkPlayerTextCatalog}. Runtime policies are
 * never reverse-engineered into prose.</p>
 */
public final class CombatPerkWikiCatalogGenerator {
    public static final Path DEFAULT_PATH = Path.of("wiki/COMBAT_PERK_CATALOG.md");

    private CombatPerkWikiCatalogGenerator() {}

    public static String renderDocument() {
        List<CombatPerkTreeModel.Node> nodes = new ArrayList<>(CombatPerkTreeModel.all());
        nodes.sort(Comparator.comparing(CombatPerkTreeModel.Node::code));

        StringBuilder out = new StringBuilder();
        out.append("# Catálogo factual — Perks de Combate A0001–A0100\n\n");
        out.append("> Arquivo gerado a partir de `CombatPerkTreeModel`, `NotionCombatPerkCatalog` e do texto player-facing já auditado. ")
            .append("Não editar as linhas manualmente. Ausência de descrição significa que o texto ainda não foi fechado pelo processo canônico de auditoria.\n\n");
        out.append("| ID | Código | Nome | Descrição auditada | Ranks | Custo/rank | Requisitos |\n");
        out.append("| --- | --- | --- | --- | ---: | ---: | --- |\n");

        for (CombatPerkTreeModel.Node node : nodes) {
            String name = NotionCombatPerkCatalog.definition(node.code())
                .orElseThrow(() -> new IllegalStateException("missing canonical combat definition: " + node.code()))
                .name();
            String description = CombatPerkPlayerTextCatalog.entry(node.code())
                .map(CombatPerkPlayerTextCatalog.PlayerText::effect)
                .orElse("—");
            out.append("| `").append(cell(node.nodeId())).append("` | ")
                .append(cell(node.code())).append(" | ")
                .append(cell(name)).append(" | ")
                .append(cell(description)).append(" | ")
                .append(node.maxRank()).append(" | ")
                .append(node.costPerRank()).append(" | ")
                .append(cell(formatRequirements(node))).append(" |\n");
        }
        return out.toString();
    }

    public static void write(Path path) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(path, renderDocument(), StandardCharsets.UTF_8);
    }

    public static void check(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("semantic combat wiki catalog is missing: " + path);
        }
        String actual = Files.readString(path, StandardCharsets.UTF_8);
        String expected = renderDocument();
        if (!expected.equals(actual)) {
            throw new IllegalStateException("semantic combat wiki catalog is stale: " + path);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "--write".equals(args[0])) {
            Path path = args.length >= 2 ? Path.of(args[1]) : DEFAULT_PATH;
            write(path);
            System.out.println("Generated semantic combat wiki catalog: " + path);
            return;
        }
        if ("--check".equals(args[0])) {
            Path path = args.length >= 2 ? Path.of(args[1]) : DEFAULT_PATH;
            check(path);
            System.out.println("Semantic combat wiki catalog drift check: PASS");
            return;
        }
        if ("--print".equals(args[0])) {
            System.out.print(renderDocument());
            return;
        }
        throw new IllegalArgumentException("usage: --write [path] | --check [path] | --print");
    }

    private static String formatRequirements(CombatPerkTreeModel.Node node) {
        List<String> parts = new ArrayList<>();
        if (node.minCharacterLevel() > 1) {
            parts.add("Nível ≥ " + node.minCharacterLevel());
        }
        node.requiredMastery().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> parts.add("Mastery `" + entry.getKey() + "` ≥ " + entry.getValue()));
        node.requiredNodeRanks().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> parts.add("Nó `" + entry.getKey() + "` rank ≥ " + entry.getValue()));
        if (node.startingPoint()) {
            parts.add("Ponto inicial");
        }
        return parts.isEmpty() ? "—" : String.join("; ", parts);
    }

    private static String cell(String value) {
        return value.replace("|", "\\|").replace('\n', ' ');
    }
}
