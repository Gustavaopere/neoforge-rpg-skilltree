package dev.gustavopere.rpgskilltree.compendium.client;

import java.util.List;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class CompendiumScreenJUnitTest {
    @Test
    void screenUsesCompendiumTitleAndDoesNotPauseGameplay() {
        CompendiumScreen screen = new CompendiumScreen(emptySnapshot());

        assertEquals(Component.translatable("screen.rpgskilltree.compendium.title"), screen.getTitle());
        assertFalse(screen.isPauseScreen());
    }

    @Test
    void screenRejectsNullSnapshot() {
        assertThrows(NullPointerException.class, () -> new CompendiumScreen(null));
    }

    private static CompendiumClientSnapshot emptySnapshot() {
        return new CompendiumClientSnapshot(List.of(), List.of());
    }
}
