package dev.gustavopere.rpgskilltree.runtime.compat.ars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.gustavopere.rpgskilltree.core.SpellAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

/**
 * Provider-present coverage for the thin Ars 5.13.1 causal Mastery glue.
 *
 * <p>The ordinary project test lane intentionally keeps optional providers off its runtime classpath.
 * Sonar adds the exact Ars artifact through {@code gradle/ars-sonar-test-runtime.init.gradle}; when
 * that artifact is absent these tests abort instead of changing the provider-free runtime contract.
 * The tests deliberately avoid constructing or mocking Minecraft entities: plain JUnit does not own
 * the vanilla server bootstrap, so entity-level authority remains covered by NeoForge/GameTest lanes.
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
    void exactProviderCausalApiSurfaceLoads() {
        assertNotNull(providerClass(SPELL));
        assertNotNull(providerClass(CONTEXT));
        assertNotNull(providerClass(CAST_EVENT));
        assertNotNull(providerClass(RESOLVE_POST));
        assertNotNull(providerClass(ADAPTER));
    }

    @Test
    void providerSpellBuildsCanonicalCausalAction() throws Exception {
        Class<?> spellClass = providerClass(SPELL);
        Class<?> adapterClass = providerClass(ADAPTER);
        Method actionFor = adapterClass.getDeclaredMethod("actionFor", spellClass);
        actionFor.setAccessible(true);

        assertNull(actionFor.invoke(null, new Object[] {null}));

        Object emptySpell = mock(spellClass);
        stub(spellClass.getMethod("isEmpty"), emptySpell, true);
        assertNull(actionFor.invoke(null, emptySpell));

        Object spell = mock(spellClass);
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

        SpellAction action = (SpellAction) actionFor.invoke(null, spell);
        assertNotNull(action);
        assertEquals("ars", action.provider());
        assertEquals("ars_nouveau:glyph_projectile>ars_nouveau:glyph_harm", action.spellId());
        assertEquals("composition", action.discipline());
        assertEquals(25, action.resourceCost());
        assertTrue(action.tags().contains("projectile"));
    }

    @Test
    void causalAttachmentTraversesChildContextAndClaimsExactlyOnce() throws Exception {
        Class<?> contextClass = providerClass(CONTEXT);
        Class<?> adapterClass = providerClass(ADAPTER);
        Method armCausalAward = adapterClass.getDeclaredMethod("armCausalAward", contextClass, SpellAction.class);
        Method claimResolved = adapterClass.getDeclaredMethod("claimResolved", contextClass);
        armCausalAward.setAccessible(true);
        claimResolved.setAccessible(true);

        SpellAction action = canonicalAction();
        Object rootContext = mock(contextClass);
        armCausalAward.invoke(null, rootContext, action);

        Invocation attachmentWrite = Mockito.mockingDetails(rootContext).getInvocations().stream()
            .filter(invocation -> invocation.getMethod().getName().equals("getOrCreateAttachment"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("causal action was not attached to the Ars SpellContext"));
        assertEquals(CAUSAL_AWARD_ID, attachmentWrite.getArgument(0));
        Object award = attachmentWrite.getArgument(1);
        assertNotNull(award);

        Method id = award.getClass().getMethod("id");
        assertEquals(CAUSAL_AWARD_ID, id.invoke(award));

        Object childContext = mock(contextClass);
        Method getAttachment = contextClass.getMethod("getAttachment", ResourceLocation.class);
        Method getPreviousContext = contextClass.getMethod("getPreviousContext");
        stub(getAttachment, childContext, null, CAUSAL_AWARD_ID);
        stub(getPreviousContext, childContext, rootContext);
        stub(getAttachment, rootContext, award, CAUSAL_AWARD_ID);

        assertSame(action, claimResolved.invoke(null, childContext));
        assertNull(claimResolved.invoke(null, childContext), "the same resolved cast must not award twice");
        assertNull(claimResolved.invoke(null, new Object[] {null}));
    }

    @Test
    void missingCausalAttachmentFailsClosed() throws Exception {
        Class<?> contextClass = providerClass(CONTEXT);
        Class<?> adapterClass = providerClass(ADAPTER);
        Method claimResolved = adapterClass.getDeclaredMethod("claimResolved", contextClass);
        claimResolved.setAccessible(true);

        Object contextWithoutAward = mock(contextClass);
        Method getAttachment = contextClass.getMethod("getAttachment", ResourceLocation.class);
        Method getPreviousContext = contextClass.getMethod("getPreviousContext");
        stub(getAttachment, contextWithoutAward, null, CAUSAL_AWARD_ID);
        stub(getPreviousContext, contextWithoutAward, null);

        assertNull(claimResolved.invoke(null, contextWithoutAward));
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

    private static SpellAction canonicalAction() {
        return new SpellAction(
            new dev.gustavopere.rpgskilltree.core.ActionOrigin("ars:spellcast", 0),
            "ars",
            "ars_nouveau:glyph_projectile>ars_nouveau:glyph_harm",
            "composition",
            java.util.Set.of("projectile"),
            25
        );
    }

    private static Class<?> providerClass(String name) {
        try {
            return Class.forName(name, false, ArsProviderCausalityCoverageJUnitTest.class.getClassLoader());
        } catch (ClassNotFoundException | NoClassDefFoundError missingProvider) {
            if (Boolean.getBoolean("rpgskilltree.arsProviderCoverageRequired")) {
                throw new AssertionError(
                    "Ars provider runtime is required in this coverage lane but could not load " + name,
                    missingProvider
                );
            }
            Assumptions.assumeTrue(false, "Ars provider runtime not present in this test lane: " + missingProvider.getMessage());
            throw new AssertionError("unreachable", missingProvider);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object mock(Class<?> type) {
        return Mockito.mock((Class) type);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void stub(Method method, Object receiver, Object value, Object... arguments) throws Exception {
        Object invocation = method.invoke(receiver, arguments);
        Mockito.when(invocation).thenReturn(value);
    }
}
