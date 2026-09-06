package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.battlemage;

import com.minecolonies.core.entity.citizen.EntityCitizen;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/** Server-side lifecycle owner for Battle Mage provider casts. */
public final class BattleMageLifecycleEvents {
    private BattleMageLifecycleEvents() {}

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof EntityCitizen citizen) || citizen.level().isClientSide) return;

        MagicData data = IronsCitizenMagicBridge.magicData(citizen);
        boolean battleMageJob = citizen.getCitizenJobHandler().getColonyJob() instanceof JobBattleMage;
        boolean tracked = BattleMageCastTracker.ownsCast(citizen);

        if (!battleMageJob) {
            if (tracked) cancelAndClear(citizen);
            return;
        }

        if (!citizen.isAlive()) {
            if (tracked || isKnownOrphanedBattleMageCast(data)) cancelAndClear(citizen);
            return;
        }

        if (data.isCasting()) {
            if (!tracked) {
                // A Battle Mage profile left casting in persistent Iron's synced data without the
                // ephemeral context that proves a live book still owns it. Fail closed.
                if (isKnownOrphanedBattleMageCast(data)) IronsCitizenMagicBridge.cancelCast(citizen);
            } else if (!BattleMageCastTracker.contextStillValid(citizen) || !providerContextStillSafe(citizen, data)) {
                cancelAndClear(citizen);
            } else {
                IronsCitizenMagicBridge.CastTickResult result = IronsCitizenMagicBridge.tickCast(citizen);
                if (result == IronsCitizenMagicBridge.CastTickResult.COMPLETED
                    || result == IronsCitizenMagicBridge.CastTickResult.CANCELLED) {
                    BattleMageCastTracker.clear(citizen);
                }
            }
        } else if (tracked) {
            BattleMageCastTracker.clear(citizen);
        }

        // The provider's player manager does not tick non-player attachments, so this bridge ticks
        // the same provider-owned mana/cooldown containers for an active Battle Mage citizen.
        IronsCitizenMagicBridge.tickResources(citizen);
    }

    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (!(event.getEntity() instanceof EntityCitizen citizen) || event.getLevel().isClientSide) return;
        if (BattleMageCastTracker.ownsCast(citizen)) cancelAndClear(citizen);
    }

    private static boolean providerContextStillSafe(EntityCitizen citizen, MagicData data) {
        SpellData spellData = data.getCastingSpell();
        if (spellData == null || spellData.getSpell() == null || spellData.getLevel() <= 0) return false;

        AbstractSpell spell = spellData.getSpell();
        BattleMageSpellProfile profile = BattleMageSpellProfileCatalog.find(spell.getSpellId()).orElse(null);
        if (!BattleMageCombatController.canContinueCast(citizen, citizen.getTarget(), profile)) return false;

        LivingEntity target = citizen.getTarget();
        return profile.targetMode() == BattleMageTargetMode.SELF
            || target == null
            || !spell.shouldAIStopCasting(spellData.getLevel(), citizen, target);
    }

    private static boolean isKnownOrphanedBattleMageCast(MagicData data) {
        if (data == null || !data.isCasting()) return false;
        SpellData casting = data.getCastingSpell();
        return casting != null
            && casting.getSpell() != null
            && BattleMageSpellProfileCatalog.find(casting.getSpell().getSpellId()).isPresent();
    }

    private static void cancelAndClear(EntityCitizen citizen) {
        IronsCitizenMagicBridge.cancelCast(citizen);
        BattleMageCastTracker.clear(citizen);
    }
}
