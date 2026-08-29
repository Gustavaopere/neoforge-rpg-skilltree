package dev.gustavopere.rpgskilltree.compendium.client;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryId;
import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntryKind;
import dev.gustavopere.rpgskilltree.compendium.catalog.CoverageState;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class CompendiumSearchIndexTest {
    public static void main(String[] args) {
        pesquisaIgnoraAcentosEEncontraNomeAliasModEIdTecnico();
        System.out.println("CompendiumSearchIndexTest: PASS");
    }

    private static void pesquisaIgnoraAcentosEEncontraNomeAliasModEIdTecnico() {
        CompendiumClientEntry lobo = entry(
            "alexsmobs:arctic_wolf",
            "Lobo Ártico",
            "alexsmobs",
            Set.of("lobo do gelo", "canideo polar")
        );
        CompendiumClientEntry urso = entry(
            "minecraft:polar_bear",
            "Urso-polar",
            "minecraft",
            Set.of("urso branco")
        );
        CompendiumSearchIndex index = new CompendiumSearchIndex(List.of(urso, lobo));

        eq(List.of(lobo), index.search("lobo artico", 20));
        eq(List.of(lobo), index.search("canídeo polar", 20));
        eq(List.of(lobo), index.search("alexsmobs", 20));
        eq(List.of(lobo), index.search("arctic_wolf", 20));
        eq("Lobo Ártico", index.search("lobo artico", 20).getFirst().displayName());
    }

    private static CompendiumClientEntry entry(
        String resourceLocation,
        String displayName,
        String sourceMod,
        Set<String> aliases
    ) {
        return new CompendiumClientEntry(
            CompendiumEntryId.of(CompendiumEntryKind.ENTITY, resourceLocation),
            displayName,
            sourceMod,
            aliases,
            Set.of("fauna"),
            Set.of("minecraft:overworld"),
            Set.of("minecraft:snowy_plains"),
            true,
            true,
            false,
            true,
            false,
            CoverageState.AUTO
        );
    }

    private static void eq(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError(expected + " != " + actual);
    }
}
