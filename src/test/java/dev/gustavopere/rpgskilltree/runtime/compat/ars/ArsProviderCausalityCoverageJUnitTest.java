package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

/**
 * Provider-present coverage for the thin Ars 5.13.1 event glue.
 *
 * <p>The ordinary project test lane intentionally keeps optional providers off its runtime classpath.
 * Sonar adds the exact Ars artifact through {@code gradle/ars-sonar-test-runtime.init.gradle}; when
 * that artifact is absent these tests abort instead of changing the provider-free runtime contract.
 */
final class ArsProviderCausalityCoverageJUnitTest {
    private static final String SPELL = "com.hollingsworth.arsnouveau.api.spell.Spell";
    private static final String CONTEXT = "com.hollingsworth.arsnouveau.api.spell.SpellContext";
    private static final String CAST_EVENT = "com.hollingsworth.arsnouveau.api.event.SpellCastEvent";
    private static final String RESOLVE_POST = "com.hollingsworth.arsnouveau.api.event.SpellResolveEvent$Post";
    private static final String ADAPTER = "dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsNouveauProgressionEvents";
    private static final ResourceLocation CAUSAL_AWARD_ID = ResourceLocation.fromNamespaceAndPath(
        "rpgskilltree",
        "ars_mastery_causal_award"
    );

    @Test
    void resolvedProviderEventClaimsParentCastExactlyOnce() throws Exception {
        Class<?> spellClass = providerClass(SPELL);
        Class<?> contextClass = providerClass(CONTEXT);
        Class<?> castEventClass = providerClass(CAST_EVENT);
        Class<?> resolvePostClass = providerClass(RESOLVE_POST);
        Class<?> adapterClass = providerClass(ADAPTER);

        ServerPlayer player = Mockito.mock(ServerPlayer.class);
        Object spell = mock(spellClass);
        Object rootContext = mock(contextClass);
        Object castEvent = mock(castEventClass);

        setPublicField(castEventClass, castEvent, "spell", spell);
        setPublicField(castEventClass, castEvent, "context", rootContext);
        stub(castEventClass.getMethod("getEntity"), castEvent, player);
        stub(spellClass.getMethod("isEmpty"), spell, false);
        stub(
            spellClass.getMethod("serializeRecipe"),
            spell,
            List.of(
                ResourceLocation.fromNamespaceAndPath("ars_nouveau", "glyph_projectile"),
                ResourceLocation.fromNamespaceAndPath("ars_nouveau", "glyph_harm")
            )
        );
        stub(spellClass.getMethod("getCost"), spell, 25);

        Method onSpellCast = adapterClass.getMethod("onSpellCast", castEventClass);
        onSpellCast.invoke(null, castEvent);

        Invocation attachmentWrite = Mockito.mockingDetails(rootContext).getInvocations().stream()
            .filter(invocation -> invocation.getMethod().getName().equals("getOrCreateAttachment"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("SpellCastEvent did not arm the Ars causal attachment"));
        Object award = attachmentWrite.getArgument(1);
        assertNotNull(award);

        Method idMethod = award.getClass().getMethod("id");
        assertEquals(CAUSAL_AWARD_ID, idMethod.invoke(award));

        Object childContext = mock(contextClass);
        Method getAttachment = contextClass.getMethod("getAttachment", ResourceLocation.class);
        Method getPreviousContext = contextClass.getMethod("getPreviousContext");
        stub(getPreviousContext, childContext, rootContext);
        stub(getAttachment, rootContext, award);

        Object resolveEvent = mock(resolvePostClass);
        setPublicField(resolvePostClass, resolveEvent, "shooter", player);
        setPublicField(resolvePostClass, resolveEvent, "context", childContext);

        Method onSpellResolved = adapterClass.getMethod("onSpellResolved", resolvePostClass);
        try (MockedStatic<PlayerProgressionRuntime> progression = Mockito.mockStatic(PlayerProgressionRuntime.class)) {
            onSpellResolved.invoke(null, resolveEvent);
            progression.verify(() -> PlayerProgressionRuntime.awardMastery(Mockito.eq(player), Mockito.anyCollection()));
        }

        Method claimResolved = award.getClass().getDeclaredMethod("claimResolved");
        claimResolved.setAccessible(true);
        assertNull(claimResolved.invoke(award), "the same resolved cast must not award twice");
    }

    @Test
    void castArmingFailsClosedForMissingOrEmptyProviderContext() throws Exception {
        Class<?> spellClass = providerClass(SPELL);
        Class<?> contextClass = providerClass(CONTEXT);
        Class<?> castEventClass = providerClass(CAST_EVENT);
        Class<?> adapterClass = providerClass(ADAPTER);
        Method onSpellCast = adapterClass.getMethod("onSpellCast", castEventClass);
        ServerPlayer player = Mockito.mock(ServerPlayer.class);

        Object noContextEvent = mock(castEventClass);
        Object nonEmptySpell = mock(spellClass);
        setPublicField(castEventClass, noContextEvent, "spell", nonEmptySpell);
        stub(castEventClass.getMethod("getEntity"), noContextEvent, player);
        stub(spellClass.getMethod("isEmpty"), nonEmptySpell, false);
        onSpellCast.invoke(null, noContextEvent);

        Object nullSpellContext = mock(contextClass);
        Object nullSpellEvent = mock(castEventClass);
        setPublicField(castEventClass, nullSpellEvent, "context", nullSpellContext);
        stub(castEventClass.getMethod("getEntity"), nullSpellEvent, player);
        onSpellCast.invoke(null, nullSpellEvent);

        Object emptySpellContext = mock(contextClass);
        Object emptySpell = mock(spellClass);
        Object emptySpellEvent = mock(castEventClass);
        setPublicField(castEventClass, emptySpellEvent, "spell", emptySpell);
        setPublicField(castEventClass, emptySpellEvent, "context", emptySpellContext);
        stub(castEventClass.getMethod("getEntity"), emptySpellEvent, player);
        stub(spellClass.getMethod("isEmpty"), emptySpell, true);
        onSpellCast.invoke(null, emptySpellEvent);

        assertEquals(0, Mockito.mockingDetails(nullSpellContext).getInvocations().stream()
            .filter(invocation -> invocation.getMethod().getName().equals("getOrCreateAttachment"))
            .count());
        assertEquals(0, Mockito.mockingDetails(emptySpellContext).getInvocations().stream()
            .filter(invocation -> invocation.getMethod().getName().equals("getOrCreateAttachment"))
            .count());
    }

    @Test
    void resolutionFailsClosedWithoutServerShooterContextOrCausalAttachment() throws Exception {
        Class<?> contextClass = providerClass(CONTEXT);
        Class<?> resolvePostClass = providerClass(RESOLVE_POST);
        Class<?> adapterClass = providerClass(ADAPTER);
        Method onSpellResolved = adapterClass.getMethod("onSpellResolved", resolvePostClass);

        Object nonServerEvent = mock(resolvePostClass);
        setPublicField(resolvePostClass, nonServerEvent, "shooter", Mockito.mock(LivingEntity.class));
        onSpellResolved.invoke(null, nonServerEvent);

        Object noContextEvent = mock(resolvePostClass);
        setPublicField(resolvePostClass, noContextEvent, "shooter", Mockito.mock(ServerPlayer.class));
        onSpellResolved.invoke(null, noContextEvent);

        Object contextWithoutAward = mock(contextClass);
        Object noAwardEvent = mock(resolvePostClass);
        setPublicField(resolvePostClass, noAwardEvent, "shooter", Mockito.mock(ServerPlayer.class));
        setPublicField(resolvePostClass, noAwardEvent, "context", contextWithoutAward);
        onSpellResolved.invoke(null, noAwardEvent);
    }

    @Test
    void deserializedAttachmentWithoutTransientClaimFailsClosed() throws Exception {
        Class<?> awardClass = providerClass("dev.gustavopere.rpgskilltree.runtime.compat.ars.ArsMasteryCausalAward");
        Constructor<?> constructor = awardClass.getDeclaredConstructor(ArsMasteryClaim.class);
        constructor.setAccessible(true);
        Object awardWithoutClaim = constructor.newInstance(new Object[] {null});

        Method claimResolved = awardClass.getDeclaredMethod("claimResolved");
        claimResolved.setAccessible(true);
        assertNull(claimResolved.invoke(awardWithoutClaim));

        Method id = awardClass.getMethod("id");
        assertEquals(CAUSAL_AWARD_ID, id.invoke(awardWithoutClaim));
    }

    private static Class<?> providerClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException | NoClassDefFoundError missingProvider) {
            Assumptions.assumeTrue(false, "Ars provider runtime not present in this test lane: " + missingProvider.getMessage());
            throw new AssertionError("unreachable", missingProvider);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object mock(Class<?> type) {
        return Mockito.mock((Class) type);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void stub(Method method, Object receiver, Object value) throws Exception {
        Object invocation = method.invoke(receiver);
        Mockito.when(invocation).thenReturn(value);
    }

    private static void setPublicField(Class<?> type, Object receiver, String name, Object value) throws Exception {
        Field field = type.getField(name);
        field.set(receiver, value);
    }
}
