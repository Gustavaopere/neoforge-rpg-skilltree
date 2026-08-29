package dev.gustavopere.rpgskilltree.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class FoundationGameTests {
    private FoundationGameTests() {
    }

    @GameTest(template = "foundation_empty")
    public static void dedicatedServerGameTestBoots(GameTestHelper helper) {
        helper.succeed();
    }
}
