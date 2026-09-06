package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;

/**
 * Server-side Iron's spell lifecycle adapter for a MineColonies citizen.
 *
 * <p>The citizen always uses the real Iron's {@link MagicData} attachment. This class does not
 * retain mana, cooldowns, spell levels, or a parallel casting state. Iron's exposes its ordinary
 * player charging helpers only for {@code ServerPlayer}; therefore the NPC bridge applies the same
 * provider mana/cooldown values to the provider-owned attachment while keeping the cast source
 * {@link CastSource#MOB}.</p>
 */
public final class IronsCitizenMagicBridge {
    public static final CastSource CAST_SOURCE = CastSource.MOB;

    private IronsCitizenMagicBridge() {
    }

    public enum CastTickResult {
        IDLE,
        CASTING,
        COMPLETED,
        CANCELLED
    }

    /** Returns the provider-owned attachment; never creates RPG-owned magic state. */
    public static MagicData magicData(LivingEntity caster) {
        return MagicData.getPlayerMagicData(Objects.requireNonNull(caster, "caster"));
    }

    /** Ticks Iron's cooldown state and provider-formula mana regeneration for the citizen. */
    public static void tickResources(LivingEntity caster) {
        if (caster == null || caster.level().isClientSide || !caster.isAlive()) {
            return;
        }

        MagicData data = magicData(caster);
        data.getPlayerCooldowns().tick(1);

        if (caster.tickCount % MagicManager.MANA_REGEN_TICKS != 0) {
            return;
        }

        float maxMana = Math.max(0.0f, (float) caster.getAttributeValue(AttributeRegistry.MAX_MANA));
        if (maxMana <= 0.0f) {
            data.setMana(0.0f);
            return;
        }

        float manaRegen = Math.max(0.0f, (float) caster.getAttributeValue(AttributeRegistry.MANA_REGEN));
        float increment = maxMana
            * manaRegen
            * 0.01f
            * ServerConfigs.MANA_REGEN_MULTIPLIER.get().floatValue();
        data.setMana(clampMana(data.getMana() + increment, maxMana));
    }

    /**
     * Begins a provider-native MOB cast after native spell conditions, mana, and cooldown checks.
     * Tactical/friendly-fire checks must already have succeeded before this method is called.
     */
    public static boolean beginCast(LivingEntity caster, SpellData spellData) {
        if (caster == null || spellData == null || caster.level().isClientSide || !caster.isAlive()) {
            return false;
        }

        AbstractSpell spell = spellData.getSpell();
        int spellLevel = spellData.getLevel();
        if (spell == null || spellLevel <= 0 || !spell.isEnabled()) {
            return false;
        }

        MagicData data = magicData(caster);
        // MagicData attachments for non-player entities lazily create SyncedSpellData. Force that
        // initialization before initiateCast(), which directly dereferences the provider field.
        data.getSyncedData();
        if (data.isCasting()
            || data.getPlayerCooldowns().isOnCooldown(spell)
            || !hasMana(data.getMana(), spell.getManaCost(spellLevel))) {
            return false;
        }

        if (!spell.checkPreCastConditions(caster.level(), spellLevel, caster, data)) {
            return false;
        }

        int duration = spell.getEffectiveCastTime(spellLevel, caster);
        data.initiateCast(spell, spellLevel, duration, CAST_SOURCE, SpellSelectionManager.MAINHAND);
        spell.onServerPreCast(caster.level(), spellLevel, caster, data);
        return true;
    }

    /** Advances one server tick of the provider cast lifecycle. */
    public static CastTickResult tickCast(LivingEntity caster) {
        if (caster == null || caster.level().isClientSide || !caster.isAlive()) {
            return CastTickResult.IDLE;
        }

        MagicData data = magicData(caster);
        if (!data.isCasting()) {
            return CastTickResult.IDLE;
        }

        SpellData spellData = data.getCastingSpell();
        AbstractSpell spell = spellData.getSpell();
        int spellLevel = spellData.getLevel();
        if (spell == null || spellLevel <= 0 || !spell.isEnabled()) {
            cancelCast(caster);
            return CastTickResult.CANCELLED;
        }

        if (spell.getCastType() == CastType.CONTINUOUS) {
            return tickContinuous(caster, data, spell, spellLevel);
        }

        data.handleCastDuration();
        if (data.isCasting()) {
            spell.onServerCastTick(caster.level(), spellLevel, caster, data);
        }

        if (data.getCastDurationRemaining() > 0) {
            return CastTickResult.CASTING;
        }

        if (!chargeAndCast(caster, data, spell, spellLevel)) {
            spell.onServerCastComplete(caster.level(), spellLevel, caster, data, true);
            return CastTickResult.CANCELLED;
        }

        addCooldown(caster, data, spell);
        spell.onServerCastComplete(caster.level(), spellLevel, caster, data, false);
        return CastTickResult.COMPLETED;
    }

    private static CastTickResult tickContinuous(
        LivingEntity caster,
        MagicData data,
        AbstractSpell spell,
        int spellLevel
    ) {
        int remainingBeforeTick = data.getCastDurationRemaining();
        int manaCost = spell.getManaCost(spellLevel);
        boolean interval = remainingBeforeTick % MagicManager.CONTINUOUS_CAST_TICK_INTERVAL == 0;
        boolean finish = remainingBeforeTick <= 0;

        if (interval) {
            boolean providerWouldStopForMana = data.getMana() - manaCost * 2.0f < 0.0f;
            if (!chargeAndCast(caster, data, spell, spellLevel)) {
                spell.onServerCastComplete(caster.level(), spellLevel, caster, data, true);
                return CastTickResult.CANCELLED;
            }
            if (finish || providerWouldStopForMana) {
                addCooldown(caster, data, spell);
                spell.onServerCastComplete(caster.level(), spellLevel, caster, data, false);
                return CastTickResult.COMPLETED;
            }
        }

        data.handleCastDuration();
        if (data.isCasting()) {
            spell.onServerCastTick(caster.level(), spellLevel, caster, data);
        }
        return CastTickResult.CASTING;
    }

    /** Cancels without charging mana/cooldown and invokes provider cleanup. */
    public static boolean cancelCast(LivingEntity caster) {
        if (caster == null || caster.level().isClientSide) {
            return false;
        }

        MagicData data = magicData(caster);
        if (!data.isCasting()) {
            return false;
        }

        SpellData spellData = data.getCastingSpell();
        AbstractSpell spell = spellData.getSpell();
        int spellLevel = spellData.getLevel();
        if (spell != null && spellLevel > 0) {
            spell.onServerCastComplete(caster.level(), spellLevel, caster, data, true);
        } else {
            data.resetCastingState();
        }
        return true;
    }

    private static boolean chargeAndCast(
        LivingEntity caster,
        MagicData data,
        AbstractSpell spell,
        int spellLevel
    ) {
        int manaCost = spell.getManaCost(spellLevel);
        if (!hasMana(data.getMana(), manaCost)) {
            return false;
        }

        data.setMana(clampMana(
            data.getMana() - manaCost,
            (float) caster.getAttributeValue(AttributeRegistry.MAX_MANA)
        ));
        spell.onCast(caster.level(), spellLevel, caster, CAST_SOURCE, data);
        return true;
    }

    private static void addCooldown(LivingEntity caster, MagicData data, AbstractSpell spell) {
        int cooldown = effectiveCooldownTicks(spell, caster);
        if (cooldown > 0) {
            data.getPlayerCooldowns().addCooldown(spell, cooldown);
        }
    }

    /** Iron's 3.16.x cooldown curve applied to the provider-owned cooldown collection. */
    static int effectiveCooldownTicks(AbstractSpell spell, LivingEntity caster) {
        Objects.requireNonNull(spell, "spell");
        Objects.requireNonNull(caster, "caster");
        double cooldownReduction = caster.getAttributeValue(AttributeRegistry.COOLDOWN_REDUCTION);
        return Math.max(0, (int) (spell.getSpellCooldown() * (2.0 - Utils.softCapFormula(cooldownReduction))));
    }

    static float clampMana(float mana, float maxMana) {
        if (!Float.isFinite(mana) || !Float.isFinite(maxMana) || maxMana <= 0.0f) {
            return 0.0f;
        }
        return Math.max(0.0f, Math.min(mana, maxMana));
    }

    static boolean hasMana(float mana, int manaCost) {
        return Float.isFinite(mana) && manaCost >= 0 && mana >= manaCost;
    }
}
