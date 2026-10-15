package dev.gustavopere.rpgskilltree.runtime.compat.ars.gametest;

import com.mojang.authlib.GameProfile;
import dev.gustavopere.rpgskilltree.core.MasteryLaneCatalog;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.compat.OptionalIntegrations;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Provider-present coexistence proof for Ars Nouveau 5.13.1 and Iron's 3.16.3. */
@GameTestHolder("rpgskilltree")
@PrefixGameTestTemplate(false)
public final class ArsIronsCoexistenceGameTests {
    private static final String SPELL = "com.hollingsworth.arsnouveau.api.spell.Spell";
    private static final String SPELL_CONTEXT = "com.hollingsworth.arsnouveau.api.spell.SpellContext";
    private static final String SPELL_RESOLVER = "com.hollingsworth.arsnouveau.api.spell.SpellResolver";
    private static final String SPELL_CAST_EVENT = "com.hollingsworth.arsnouveau.api.event.SpellCastEvent";
    private static final String SPELL_RESOLVE_POST = "com.hollingsworth.arsnouveau.api.event.SpellResolveEvent$Post";
    private static final String METHOD_PROJECTILE = "com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile";
    private static final String EFFECT_HARM = "com.hollingsworth.arsnouveau.common.spell.effect.EffectHarm";
    private static final String ADAPTER = "dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents";

    private ArsIronsCoexistenceGameTests() {}

    @GameTest(template = "foundation_empty")
    public static void arsResolutionDoesNotCreditIronsMastery(GameTestHelper helper) {
        if (!ModList.get().isLoaded(OptionalIntegrations.Provider.ARS_NOUVEAU.modId())
            || !ModList.get().isLoaded(OptionalIntegrations.Provider.IRONS_SPELLBOOKS.modId())) {
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
                new GameProfile(UUID.randomUUID(), "ars_irons_coexistence_probe"),
                ClientInformation.createDefault()
            );

            Method fromEntity = contextClass.getMethod("fromEntity", spellClass, LivingEntity.class, ItemStack.class);
            Method onSpellCast = adapterClass.getMethod("onSpellCast", castEventClass);
            Method onSpellResolved = adapterClass.getMethod("onSpellResolved", resolvePostClass);
            Constructor<?> castCtor = castEventClass.getConstructor(spellClass, contextClass);
            Constructor<?> resolveCtor = resolvePostClass.getConstructor(
                Level.class,
                LivingEntity.class,
                HitResult.class,
                spellClass,
                contextClass,
                resolverClass
            );

            ProgressionState before = PlayerProgressionRuntime.get(player);
            int magicBefore = before.mastery().experience(MasteryLaneCatalog.MAGIC_CASTING);
            int arsBefore = before.mastery().experience(MasteryLaneCatalog.ARS_CASTING);
            int ironsBefore = before.mastery().experience(MasteryLaneCatalog.IRONS_CASTING);

            Object context = fromEntity.invoke(null, spell, player, ItemStack.EMPTY);
            onSpellCast.invoke(null, castCtor.newInstance(spell, context));
            onSpellResolved.invoke(null, resolveCtor.newInstance(helper.getLevel(), player, null, spell, context, null));

            ProgressionState after = PlayerProgressionRuntime.get(player);
            helper.assertTrue(
                after.mastery().experience(MasteryLaneCatalog.MAGIC_CASTING) > magicBefore,
                "a resolved Ars cast must still credit the shared magic:casting lane"
            );
            helper.assertTrue(
                after.mastery().experience(MasteryLaneCatalog.ARS_CASTING) > arsBefore,
                "a resolved Ars cast must credit ars:casting when Iron's is also loaded"
            );
            helper.assertTrue(
                after.mastery().experience(MasteryLaneCatalog.IRONS_CASTING) == ironsBefore,
                "an Ars cast must never credit irons:casting merely because Iron's is present"
            );
            helper.succeed();
        } catch (ReflectiveOperationException | LinkageError failure) {
            throw new AssertionError("Ars + Iron's coexistence GameTest failed", failure);
        }
    }

    private static Object projectileHarmSpell(Class<?> spellClass) throws ReflectiveOperationException {
        Field projectile = Class.forName(METHOD_PROJECTILE).getField("INSTANCE");
        Field harm = Class.forName(EFFECT_HARM).getField("INSTANCE");
        return spellClass.getConstructor(List.class).newInstance(List.of(projectile.get(null), harm.get(null)));
    }
}
