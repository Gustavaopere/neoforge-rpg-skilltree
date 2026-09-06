package dev.gustavopere.volcanoes.volcano;

import dev.gustavopere.volcanoes.VolcanoesMod;
import dev.gustavopere.volcanoes.tectonics.TectonicContext;
import dev.gustavopere.volcanoes.tectonics.TectonicService;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.EnumSet;
import java.util.UUID;

/** Dedicated-server acceptance tests for the detailed eruption lifecycle. */
@GameTestHolder(VolcanoesMod.MOD_ID)
@PrefixGameTestTemplate(false)
public final class EruptionGameTests {
    private static final UUID VOLCANO_ID = UUID.fromString("531357f4-1c67-4f13-934f-96a5de4cf250");

    private EruptionGameTests() {
    }

    @GameTest(template = "eruption_runtime_empty", timeoutTicks = 100)
    public static void eruptionResumesAfterPersistenceRoundTrip(GameTestHelper helper) {
        VolcanoSavedData data = new VolcanoSavedData();
        VolcanoSite site = new VolcanoSite(
                VOLCANO_ID,
                helper.absolutePos(new BlockPos(0, 1, 0)),
                VolcanoType.STRATOVOLCANO,
                VolcanoState.ERUPTING,
                TectonicContext.CONVERGENT,
                71L,
                72L,
                0.92);
        MagmaChamber chamber = new MagmaChamber(
                MagmaComposition.forType(VolcanoType.STRATOVOLCANO),
                9.0,
                330.0,
                0.22,
                1_240.0,
                0.35);
        data.register(site);
        data.updateLifecycle(VOLCANO_ID, VolcanoState.ERUPTING, chamber);

        EnumSet<EruptionPhase> observed = EnumSet.noneOf(EruptionPhase.class);
        EruptionSink sink = (signal, work) -> {
            if (VOLCANO_ID.equals(signal.volcanoId())) {
                observed.add(signal.phase());
            }
        };
        helper.assertTrue(VolcanoLifecycleRuntime.registerEruptionSink(sink),
                "eruption acceptance sink must register once");

        try {
            long worldSeed = helper.getLevel().getSeed();
            VolcanoLifecycleRuntime.RuntimeState beforeRestart = new VolcanoLifecycleRuntime.RuntimeState(
                    data,
                    new VolcanoManager(data, TectonicService.fallback()));
            beforeRestart.discoverSites(0L);
            beforeRestart.processDue(worldSeed, 200L);
            beforeRestart.processDue(worldSeed, 400L);

            EruptionEvent persistedBeforeRestart = data.eruption(VOLCANO_ID).orElseThrow();
            helper.assertTrue(persistedBeforeRestart.elapsedTicks() > 0L,
                    "eruption must make progress before persistence round-trip");
            helper.assertTrue(persistedBeforeRestart.phase() == EruptionPhase.PRECURSORS,
                    "restart fixture must persist a live precursor phase");

            VolcanoSavedData restored = VolcanoSavedData.fromTag(data.toTag());
            helper.assertTrue(site.equals(restored.get(VOLCANO_ID).orElseThrow()),
                    "volcano site identity and geological birth context must survive the SavedData round-trip");
            EruptionEvent restoredEvent = restored.eruption(VOLCANO_ID).orElseThrow();
            helper.assertTrue(restoredEvent.equals(persistedBeforeRestart),
                    "saved eruption must round-trip without phase/progress drift");

            VolcanoLifecycleRuntime.RuntimeState afterRestart = new VolcanoLifecycleRuntime.RuntimeState(
                    restored,
                    new VolcanoManager(restored, TectonicService.fallback()));
            afterRestart.discoverSites(400L);

            long tick = 600L;
            while (tick <= 30_000L && !restored.eruption(VOLCANO_ID).orElseThrow().isComplete()) {
                afterRestart.processDue(worldSeed, tick);
                tick += 200L;
            }

            EruptionEvent completed = restored.eruption(VOLCANO_ID).orElseThrow();
            helper.assertTrue(completed.isComplete(),
                    "detailed eruption must reach DORMANT after restart");
            helper.assertTrue(completed.startedTick() == persistedBeforeRestart.startedTick(),
                    "restart must resume the original eruption instead of starting another one");
            helper.assertTrue(observed.containsAll(EnumSet.allOf(EruptionPhase.class)),
                    "dedicated server must observe PRECURSORS, OPENING, SUSTAINED, WANING and DORMANT");
            helper.succeed();
        } finally {
            VolcanoLifecycleRuntime.unregisterEruptionSink(sink);
        }
    }
}
