package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.A0081A0100CombatPolicy;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.CombatRecoveryService;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Shared post-mitigation resolver for the approved A0081-A0087 sustain contracts. */
public final class A0081A0090SustainRuntime {
    private static final TagKey<Item> SIMPLYCATACLYSM_IGNITIUM_GEAR = TagKey.create(
        Registries.ITEM,
        ResourceLocation.fromNamespaceAndPath("simplycataclysm", "ignitium_gear")
    );

    private A0081A0090SustainRuntime() {}

    /**
     * Resolves one confirmed direct physical weapon root. Provider-native lifesteal sources whose
     * final heal cannot be intercepted are marked AMBIGUOUS so the Skill Tree contributes zero.
     */
    public static void resolvePhysicalWeaponHit(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        double postMitigationDamage,
        boolean directMelee,
        ItemStack weaponStack
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(rootActionId, "rootActionId");
        Objects.requireNonNull(weaponStack, "weaponStack");
        if (!eligible(player) || rootActionId.isBlank() || postMitigationDamage <= 0.0D) return;

        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        String actor = A0081A0100RuntimeState.actorId(player);
        long nowTick = player.level().getGameTime();
        long nowMillis = Math.multiplyExact(nowTick, 50L);

        if (directMelee && ranks.rank("A0081") > 0) {
            boolean rhythmActive = A0061A0080RuntimeState.state().sustainedRhythmActive(actor, nowMillis);
            A0081A0100RuntimeState.recovery().recordDamage(
                new CombatRecoveryService.DamageRequest(
                    actor,
                    rootActionId,
                    true,
                    true,
                    true,
                    true,
                    rhythmActive,
                    player.getMaxHealth(),
                    postMitigationDamage,
                    targetHealthBefore,
                    ranks.rank("A0081")
                ),
                nowMillis
            );
        }

        double canonical = A0081A0100CombatPolicy.sustainCoefficient(
            ranks,
            true,
            false,
            false,
            false
        );
        List<Double> candidates = new ArrayList<>();
        if (canonical > 0.0D) candidates.add(canonical);

        // A0087 remains structurally unavailable in this batch. Keeping this branch behind
        // effectiveRanks means no partial Blood Thirst benefit can leak while BodyProvider and
        // general healing-received authority are absent.
        if (ranks.rank("A0087") > 0) {
            double bloodMinimum = A0081A0100RuntimeState.bloodThirst().weaponMinimumCoefficient(actor, nowTick);
            if (bloodMinimum > 0.0D) candidates.add(bloodMinimum);
        }
        if (candidates.isEmpty()) return;

        SustainResolver.NativeCorrelation nativeCorrelation = hasAmbiguousNativeWeaponLifesteal(weaponStack)
            ? SustainResolver.NativeCorrelation.AMBIGUOUS
            : SustainResolver.NativeCorrelation.NONE;

        resolveSustain(
            player,
            rootActionId,
            targetHealthBefore,
            postMitigationDamage,
            nativeCorrelation,
            candidates,
            nowTick
        );
    }

    /** Resolves a provider-proven DIRECT_MAGIC root (currently Iron's 3.16.3 only). */
    public static void resolveDirectMagicHit(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        double postMitigationDamage,
        boolean nativeLifestealAmbiguous
    ) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(rootActionId, "rootActionId");
        if (!eligible(player) || rootActionId.isBlank() || postMitigationDamage <= 0.0D) return;

        CombatPerkRanks ranks = A0081A0100RuntimeState.ranks(player);
        double canonical = A0081A0100CombatPolicy.sustainCoefficient(
            ranks,
            false,
            true,
            false,
            false
        );
        if (canonical <= 0.0D) return;

        resolveSustain(
            player,
            rootActionId,
            targetHealthBefore,
            postMitigationDamage,
            nativeLifestealAmbiguous
                ? SustainResolver.NativeCorrelation.AMBIGUOUS
                : SustainResolver.NativeCorrelation.NONE,
            List.of(canonical),
            player.level().getGameTime()
        );
    }

    /**
     * SimplyCataclysm 1.0.2 exposes a provider-owned Ignitium gear tag and heals directly inside
     * its hit callback. Without a final native-heal receipt, the entire tagged source is closed for
     * the Skill Tree portion rather than risking a second heal on the same root.
     */
    public static boolean hasAmbiguousNativeWeaponLifesteal(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.is(SIMPLYCATACLYSM_IGNITIUM_GEAR);
    }

    private static void resolveSustain(
        ServerPlayer player,
        String rootActionId,
        double targetHealthBefore,
        double postMitigationDamage,
        SustainResolver.NativeCorrelation nativeCorrelation,
        List<Double> candidates,
        long nowTick
    ) {
        SustainResolver.Resolution resolution = A0081A0100RuntimeState.sustain().resolve(
            new SustainResolver.Request(
                A0081A0100RuntimeState.actorId(player),
                rootActionId,
                true,
                true,
                true,
                postMitigationDamage,
                Math.max(0.0D, targetHealthBefore),
                player.getMaxHealth(),
                Math.max(0.0D, player.getMaxHealth() - player.getHealth()),
                1.0D,
                nativeCorrelation,
                0.0D,
                candidates
            ),
            nowTick
        );
        if (resolution.skillTreeHealing() > 0.0D) {
            player.heal((float) resolution.skillTreeHealing());
        }
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && player.isAlive()
            && !player.isCreative()
            && !player.isSpectator();
    }
}
