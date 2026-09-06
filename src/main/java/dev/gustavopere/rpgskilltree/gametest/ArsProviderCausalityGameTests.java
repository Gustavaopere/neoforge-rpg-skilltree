package dev.gustavopere.rpgskilltree.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.MasteryLaneCatalog;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.SpellAction;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

/** Loaded-provider acceptance coverage for the Ars Nouveau 5.13.1 causal Mastery boundary. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class ArsProviderCausalityGameTests {
    private static final String EXPECTED_ARS_VERSION = "5.13.1";
    private static final String SPELL = "com.hollingsworth.arsnouveau.api.spell.Spell";
    private static final String SPELL_CONTEXT = "com.hollingsworth.arsnouveau.api.spell.SpellContext";
    private static final String SPELL_RESOLVER = "com.hollingsworth.arsnouveau.api.spell.SpellResolver";
    private static final String SPELL_CAST_EVENT = "com.hollingsworth.arsnouveau.api.event.SpellCastEvent";
    private static final String SPELL_RESOLVE_POST = "com.hollingsworth.arsnouveau.api.event.SpellResolveEvent$Post";
    private static final String METHOD_PROJECTILE = "com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile";
    private static final String EFFECT_HARM = "com.hollingsworth.arsnouveau.common.spell.effect.EffectHarm";
    private static final String ADAPTER = "dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents";

    private ArsProviderCausalityGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void providerContextCarriesOneShotCausality(GameTestHelper helper) {
        if (!arsPresent()) {
            helper.succeed();
            return;
        }

        try {
            helper.assertTrue(
                EXPECTED_ARS_VERSION.equals(OptionalIntegrations.version(OptionalIntegrations.Provider.ARS_NOUVEAU)),
                "Ars provider GameTest must run against exact pack version 5.13.1"
            );

            Class<?> spellClass = Class.forName(SPELL);
            Class<?> contextClass = Class.forName(SPELL_CONTEXT);
            Class<?> adapterClass = Class.forName(ADAPTER);
            Object spell = projectileHarmSpell(spellClass);

            Method actionFor = declared(adapterClass, "actionFor", spellClass);
            Method arm = declared(adapterClass, "armCausalAward", contextClass, SpellAction.class);
            Method claim = declared(adapterClass, "claimResolved", contextClass);

            SpellAction action = (SpellAction) actionFor.invoke(null, spell);
            helper.assertTrue(action != null, "valid provider spell must create a semantic SpellAction");
            helper.assertTrue("ars".equals(action.provider()), "semantic action provider must remain ars");
            helper.assertTrue("composition".equals(action.discipline()), "Ars action discipline must remain composition");
            helper.assertTrue(action.tags().contains("projectile"), "projectile glyph must classify into the projectile Mastery lane");
            helper.assertTrue(action.spellId().contains("ars_nouveau:glyph_projectile"), "signature must retain projectile glyph identity");
            helper.assertTrue(action.spellId().contains("ars_nouveau:glyph_harm"), "signature must retain harm glyph identity");
            int providerCost = (int) spellClass.getMethod("getCost").invoke(spell);
            helper.assertTrue(action.resourceCost() == Math.max(0, providerCost), "semantic action must use provider-native spell cost");

            Object parent = contextClass.getMethod("dehydrated", spellClass).invoke(null, spell);
            Object child = contextClass.getMethod("dehydrated", spellClass).invoke(null, spell);
            contextClass.getMethod("withParent", contextClass).invoke(child, parent);
            arm.invoke(null, parent, action);
            helper.assertTrue(claim.invoke(null, child) == action, "child context must resolve the parent causal attachment");
            helper.assertTrue(claim.invoke(null, parent) == null, "shared causal claim must be one-shot across the context chain");

            Object cloneSource = contextClass.getMethod("dehydrated", spellClass).invoke(null, spell);
            arm.invoke(null, cloneSource, action);
            Object cloned = contextClass.getMethod("clone").invoke(cloneSource);
            helper.assertTrue(claim.invoke(null, cloned) == action, "Ars 5.13.1 clone must retain the shared causal attachment value");
            helper.assertTrue(claim.invoke(null, cloneSource) == null, "clone and source must share the same one-shot claim");

            Object unarmed = contextClass.getMethod("dehydrated", spellClass).invoke(null, spell);
            helper.assertTrue(claim.invoke(null, unarmed) == null, "missing causal state must fail closed");
            helper.assertTrue(actionFor.invoke(null, new Object[]{null}) == null, "null spell must fail closed");
            Object emptySpell = spellClass.getConstructor().newInstance();
            helper.assertTrue(actionFor.invoke(null, emptySpell) == null, "empty spell must fail closed");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Ars provider causal-context GameTest failed", failure);
        }
    }

    @GameTest(template = "foundation_empty")
    public static void castArmsAndResolvedEventAwardsExactlyOnce(GameTestHelper helper) {
        if (!arsPresent()) {
            helper.succeed();
            return;
        }

        try {
            Class<?> spellClass = Class.forName(SPELL);
            Class<?> contextClass = Class.forName(SPELL_CONTEXT);
            Class<?> castEventClass = Class.forName(SPELL_CAST_EVENT);
            Class<?> resolvePostClass = Class.forName(SPELL_RESOLVE_POST);
            Class<?> resolverClass = Class.forName(SPELL_RESOLVER);
            Class<?> adapterClass = Class.forName(ADAPTER);
            Object spell = projectileHarmSpell(spellClass);

            ServerPlayer player = new ServerPlayer(
                helper.getLevel().getServer(),
                helper.getLevel(),
                new GameProfile(UUID.randomUUID(), "ars_mastery_probe"),
                ClientInformation.createDefault()
            );

            Method fromEntity = contextClass.getMethod("fromEntity", spellClass, LivingEntity.class, ItemStack.class);
            Method claim = declared(adapterClass, "claimResolved", contextClass);
            Method onSpellCast = adapterClass.getMethod("onSpellCast", castEventClass);
            Method onSpellResolved = adapterClass.getMethod("onSpellResolved", resolvePostClass);
            Constructor<?> castCtor = castEventClass.getConstructor(spellClass, contextClass);

            Object canceledContext = fromEntity.invoke(null, spell, player, ItemStack.EMPTY);
            Object canceledCast = castCtor.newInstance(spell, canceledContext);
            castEventClass.getMethod("setCanceled", boolean.class).invoke(canceledCast, true);
            onSpellCast.invoke(null, canceledCast);
            helper.assertTrue(claim.invoke(null, canceledContext) == null, "canceled cast must not arm Mastery causality");

            Object context = fromEntity.invoke(null, spell, player, ItemStack.EMPTY);
            Object cast = castCtor.newInstance(spell, context);
            onSpellCast.invoke(null, cast);

            ProgressionState before = PlayerProgressionRuntime.get(player);
            int magicBefore = before.mastery().experience(MasteryLaneCatalog.MAGIC_CASTING);
            int arsBefore = before.mastery().experience(MasteryLaneCatalog.ARS_CASTING);
            int projectileBefore = before.mastery().experience(MasteryLaneCatalog.ars("projectile"));

            Constructor<?> resolveCtor = resolvePostClass.getConstructor(
                Level.class,
                LivingEntity.class,
                HitResult.class,
                spellClass,
                contextClass,
                resolverClass
            );
            Object resolved = resolveCtor.newInstance(helper.getLevel(), player, null, spell, context, null);
            onSpellResolved.invoke(null, resolved);

            ProgressionState after = PlayerProgressionRuntime.get(player);
            int magicAfter = after.mastery().experience(MasteryLaneCatalog.MAGIC_CASTING);
            int arsAfter = after.mastery().experience(MasteryLaneCatalog.ARS_CASTING);
            int projectileAfter = after.mastery().experience(MasteryLaneCatalog.ars("projectile"));
            helper.assertTrue(magicAfter > magicBefore, "resolved Ars cast must increase canonical magic:casting Mastery");
            helper.assertTrue(arsAfter > arsBefore, "resolved Ars cast must increase canonical ars:casting Mastery");
            helper.assertTrue(projectileAfter > projectileBefore, "projectile composition must increase canonical ars:projectile Mastery");

            onSpellResolved.invoke(null, resolved);
            ProgressionState repeated = PlayerProgressionRuntime.get(player);
            helper.assertTrue(repeated.mastery().experience(MasteryLaneCatalog.MAGIC_CASTING) == magicAfter,
                "replaying SpellResolveEvent.Post must not double-award magic:casting");
            helper.assertTrue(repeated.mastery().experience(MasteryLaneCatalog.ARS_CASTING) == arsAfter,
                "replaying SpellResolveEvent.Post must not double-award ars:casting");
            helper.assertTrue(repeated.mastery().experience(MasteryLaneCatalog.ars("projectile")) == projectileAfter,
                "replaying SpellResolveEvent.Post must not double-award ars:projectile");
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Ars provider cast/resolve GameTest failed", failure);
        }
    }

    private static Object projectileHarmSpell(Class<?> spellClass) throws ReflectiveOperationException {
        Field projectile = Class.forName(METHOD_PROJECTILE).getField("INSTANCE");
        Field harm = Class.forName(EFFECT_HARM).getField("INSTANCE");
        return spellClass.getConstructor(List.class).newInstance(List.of(projectile.get(null), harm.get(null)));
    }

    private static Method declared(Class<?> owner, String name, Class<?>... parameters) throws NoSuchMethodException {
        Method method = owner.getDeclaredMethod(name, parameters);
        method.setAccessible(true);
        return method;
    }

    private static boolean arsPresent() {
        return ModList.get().isLoaded(OptionalIntegrations.Provider.ARS_NOUVEAU.modId());
    }
}
