package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class A0061A0080EpicFightProviderBoundaryJUnitTest {
    private static final Path HOOKS = Path.of(
        "src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/A0061A0080EpicFightHooks.java"
    );

    @Test
    void physicalMeleeUsesProviderClassificationAndOnlyExactVanillaMaceFallback() throws IOException {
        String source = Files.readString(HOOKS);

        assertTrue(source.contains("EpicFightCapabilities.getItemStackCapability(stack)"));
        assertTrue(source.contains("return stack.is(Items.MACE);"));

        assertFalse(source.contains("TagKey<Item> HAMMERS"));
        assertFalse(source.contains("TagKey<Item> MACES"));
        assertFalse(source.contains("TagKey<Item> SCYTHES"));
        assertFalse(source.contains("stack.is(HAMMERS)"));
        assertFalse(source.contains("stack.is(MACES)"));
        assertFalse(source.contains("stack.is(SCYTHES)"));
    }
}
