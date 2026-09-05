package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.gametest;

import java.lang.reflect.Method;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present coverage for Battle Mage bridge guards that cannot run in plain JVM JUnit. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class BattleMageBridgeGuardProviderGameTests {
    private static final String BRIDGE =
        "dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage.IronsCitizenMagicBridge";
    private static final String SPELL_DATA = "io.redspace.ironsspellbooks.api.spells.SpellData";

    private BattleMageBridgeGuardProviderGameTests() {
    }

    @GameTest(template = "foundation_empty", timeoutTicks = 100)
    public static void bridgeGuardsAndNumericPoliciesFailClosed(GameTestHelper helper) {
        if (!ModList.get().isLoaded("irons_spellbooks")) {
            helper.succeed();
            return;
        }

        LivingEntity caster = EntityType.ZOMBIE.create(helper.getLevel());
        if (caster == null) {
            throw new AssertionError("vanilla zombie EntityType failed to create bridge guard caster");
        }
        caster.moveTo(helper.absolutePos(net.minecraft.core.BlockPos.ZERO), 0.0f, 0.0f);
        if (!helper.getLevel().addFreshEntity(caster)) {
            throw new AssertionError("failed to add bridge guard caster");
        }

        try {
            Class<?> bridge = Class.forName(BRIDGE);
            Object magicData = bridge.getMethod("magicData", LivingEntity.class).invoke(null, caster);
            helper.assertTrue(magicData != null,
                "Battle Mage bridge must resolve the provider-owned MagicData attachment");

            Method beginCast = bridge.getMethod("beginCast", LivingEntity.class, Class.forName(SPELL_DATA));
            helper.assertTrue(!(boolean) beginCast.invoke(null, caster, null),
                "beginCast must fail closed when no provider SpellData is supplied");
            helper.assertTrue(!(boolean) beginCast.invoke(null, null, null),
                "beginCast must fail closed for a null caster");

            Method tickCast = bridge.getMethod("tickCast", LivingEntity.class);
            Object idle = tickCast.invoke(null, caster);
            helper.assertTrue(idle instanceof Enum<?> && "IDLE".equals(((Enum<?>) idle).name()),
                "tickCast must remain IDLE when the provider attachment is not casting");
            Object nullIdle = tickCast.invoke(null, (Object) null);
            helper.assertTrue(nullIdle instanceof Enum<?> && "IDLE".equals(((Enum<?>) nullIdle).name()),
                "tickCast must remain IDLE for a null caster");

            Method cancelCast = bridge.getMethod("cancelCast", LivingEntity.class);
            helper.assertTrue(!(boolean) cancelCast.invoke(null, caster),
                "cancelCast must not fabricate provider cleanup for an idle caster");
            helper.assertTrue(!(boolean) cancelCast.invoke(null, (Object) null),
                "cancelCast must fail closed for a null caster");

            Method tickResources = bridge.getMethod("tickResources", LivingEntity.class);
            caster.tickCount = 1;
            tickResources.invoke(null, caster);
            tickResources.invoke(null, (Object) null);
            caster.discard();
            tickResources.invoke(null, caster);

            Method clampMana = bridge.getDeclaredMethod("clampMana", float.class, float.class);
            clampMana.setAccessible(true);
            helper.assertTrue((float) clampMana.invoke(null, Float.NaN, 100.0f) == 0.0f,
                "non-finite mana must clamp to zero");
            helper.assertTrue((float) clampMana.invoke(null, 50.0f, 0.0f) == 0.0f,
                "non-positive max mana must clamp to zero");
            helper.assertTrue((float) clampMana.invoke(null, -5.0f, 100.0f) == 0.0f,
                "negative mana must clamp to zero");
            helper.assertTrue((float) clampMana.invoke(null, 150.0f, 100.0f) == 100.0f,
                "mana above provider max must clamp to max mana");
            helper.assertTrue((float) clampMana.invoke(null, 40.0f, 100.0f) == 40.0f,
                "finite in-range mana must be preserved");

            Method hasMana = bridge.getDeclaredMethod("hasMana", float.class, int.class);
            hasMana.setAccessible(true);
            helper.assertTrue(!(boolean) hasMana.invoke(null, Float.NaN, 1),
                "non-finite mana must never satisfy a cast cost");
            helper.assertTrue(!(boolean) hasMana.invoke(null, 100.0f, -1),
                "negative provider mana cost must fail closed");
            helper.assertTrue(!(boolean) hasMana.invoke(null, 9.0f, 10),
                "insufficient mana must fail the provider cost check");
            helper.assertTrue((boolean) hasMana.invoke(null, 10.0f, 10),
                "exact provider mana cost must be accepted");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Battle Mage provider bridge guard probe failed", failure);
        } finally {
            if (!caster.isRemoved()) {
                caster.discard();
            }
        }
    }
}
