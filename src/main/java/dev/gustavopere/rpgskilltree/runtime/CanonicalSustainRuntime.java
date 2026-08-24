package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.CanonicalActionIdentity;
import dev.gustavopere.rpgskilltree.core.FrozenCombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.FrozenPeriodicProviderPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenSustainPolicy;
import dev.gustavopere.rpgskilltree.core.SustainResolver;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

/** Runtime adapter for one capped healing pipeline; provider-specific classification stays outside it. */
public final class CanonicalSustainRuntime {
    private static final ThreadLocal<Deque<HealEnvelope>> HEAL_ENVELOPES =
        ThreadLocal.withInitial(ArrayDeque::new);
    private static final Set<DamageSource> PROVIDER_CLASSIFIED_NON_WEAPON =
        java.util.Collections.newSetFromMap(new WeakHashMap<>());
    private static final Set<DamageSource> AMBIGUOUS_NATIVE_HEALING =
        java.util.Collections.newSetFromMap(new WeakHashMap<>());

    private CanonicalSustainRuntime() {}

    public static synchronized void markProviderClassifiedNonWeapon(DamageSource source) {
        PROVIDER_CLASSIFIED_NON_WEAPON.add(Objects.requireNonNull(source));
    }

    public static synchronized boolean isProviderClassifiedNonWeapon(DamageSource source) {
        return PROVIDER_CLASSIFIED_NON_WEAPON.contains(Objects.requireNonNull(source));
    }

    public static synchronized void markAmbiguousNativeHealing(DamageSource source) {
        AMBIGUOUS_NATIVE_HEALING.add(Objects.requireNonNull(source));
    }

    public static synchronized boolean hasAmbiguousNativeHealing(DamageSource source) {
        return AMBIGUOUS_NATIVE_HEALING.contains(Objects.requireNonNull(source));
    }

    /** Resolves only exact providers or an explicitly configured tag with a persistent direct origin. */
    public static Optional<PeriodicSource> periodicSource(
        DamageSource source,
        boolean configuredPeriodicTag,
        ServerPlayer owner
    ) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(owner);
        Entity direct = source.getDirectEntity();
        String spellClass = ironSpellClass(source);
        FrozenPeriodicProviderPolicy.Classification classification =
            FrozenPeriodicProviderPolicy.classify(
                source.getClass().getName(),
                direct == null ? "" : direct.getClass().getName(),
                spellClass,
                configuredPeriodicTag,
                direct != null
            );
        if (classification == FrozenPeriodicProviderPolicy.Classification.NONE) return Optional.empty();

        boolean exactOwnedOrigin = switch (classification) {
            case IRONS_RAY_OF_SIPHONING -> direct == owner && source.getEntity() == owner;
            case GOETY_ACID_POOL -> source.getEntity() == owner;
            case CONFIGURED_TAG -> direct == owner
                || direct instanceof Projectile projectile && projectile.getOwner() == owner;
            case NONE -> false;
        };
        if (!exactOwnedOrigin) return Optional.empty();
        return Optional.of(new PeriodicSource(
            classification.providerId(), direct.getUUID().toString()));
    }

    public static boolean hasEligibleCandidate(ServerPlayer player, Classification classification) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(classification);
        return coefficient(player, classification) > 0.0D;
    }

    public static SustainResolver.Resolution resolve(
        ServerPlayer player,
        CanonicalActionIdentity action,
        Classification classification,
        double postMitigationDamage,
        double targetHealthBefore,
        SustainResolver.NativeCorrelation nativeCorrelation,
        double interceptedNativeHealing
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(action);
        Objects.requireNonNull(classification);
        Objects.requireNonNull(nativeCorrelation);
        if (!Double.isFinite(interceptedNativeHealing) || interceptedNativeHealing < 0.0D) {
            throw new IllegalArgumentException("interceptedNativeHealing");
        }

        long nowTick = player.level().getGameTime();
        String actorId = player.getUUID().toString();
        double healingMultiplier = FrozenCombatRuntimeState.bloodThirst()
            .healingMultiplier(actorId, nowTick);
        double nativeFinalHealing = interceptedNativeHealing * healingMultiplier;
        double currentMissingHealth = Math.max(0.0D, player.getMaxHealth() - player.getHealth());
        double missingAfterNative = Math.max(0.0D, currentMissingHealth - nativeFinalHealing);
        double selected = coefficient(player, classification);
        SustainResolver.Resolution resolution = FrozenCombatRuntimeState.sustain().resolve(
            new SustainResolver.Request(
                action,
                true,
                true,
                classification.directOwnerProven(),
                postMitigationDamage,
                targetHealthBefore,
                player.getMaxHealth(),
                missingAfterNative,
                healingMultiplier,
                nativeCorrelation,
                nativeFinalHealing,
                List.of(new SustainResolver.Candidate("frozen:a0082-a0086", selected, selected > 0.0D))
            ),
            nowTick
        );
        if (resolution.status() != SustainResolver.Status.AUTHORIZED) return resolution;

        double attempted = resolution.nativeHealingCounted() + resolution.skillTreeHealing();
        if (attempted <= 0.0D) {
            FrozenCombatRuntimeState.sustain().confirmFinalHealing(resolution, 0.0D);
            return resolution;
        }
        double before = player.getHealth();
        Deque<HealEnvelope> envelopes = HEAL_ENVELOPES.get();
        envelopes.push(new HealEnvelope(player, resolution.maximumFinalHealing()));
        try {
            player.heal((float) attempted);
        } finally {
            envelopes.pop();
            if (envelopes.isEmpty()) HEAL_ENVELOPES.remove();
        }
        double actual = Math.max(0.0D, player.getHealth() - before);
        FrozenCombatRuntimeState.sustain().confirmFinalHealing(resolution, actual);
        return resolution;
    }

    /** Invoked at LOWEST so all provider healing modifiers are capped on their final amount. */
    public static void clampHealing(LivingHealEvent event) {
        Deque<HealEnvelope> envelopes = HEAL_ENVELOPES.get();
        if (envelopes.isEmpty()) return;
        HealEnvelope envelope = envelopes.peek();
        if (event.getEntity() != envelope.player()) return;
        event.setAmount((float) Math.min(event.getAmount(), envelope.maximumFinalHealing()));
    }

    private static double coefficient(ServerPlayer player, Classification classification) {
        FrozenCombatPerkRanks ranks = FrozenCombatRuntimeState.ranks(player);
        double selected = FrozenSustainPolicy.coefficientFor(
            ranks,
            classification.weapon(),
            classification.magic(),
            classification.elemental(),
            classification.periodic()
        );
        if (classification.weapon()) {
            selected = Math.max(selected, FrozenCombatRuntimeState.bloodThirst().weaponMinimumCoefficient(
                player.getUUID().toString(), player.level().getGameTime()));
        }
        return selected;
    }

    private static String ironSpellClass(DamageSource source) {
        if (!source.getClass().getName()
            .equals("io.redspace.ironsspellbooks.damage.SpellDamageSource")) return "";
        try {
            Object spell = source.getClass().getMethod("spell").invoke(source);
            return spell == null ? "" : spell.getClass().getName();
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return "";
        }
    }

    public record Classification(
        boolean weapon,
        boolean magic,
        boolean elemental,
        boolean periodic,
        boolean directOwnerProven
    ) {}

    public record PeriodicSource(String providerId, String persistentOriginId) {
        public PeriodicSource {
            Objects.requireNonNull(providerId);
            Objects.requireNonNull(persistentOriginId);
        }
    }

    private record HealEnvelope(ServerPlayer player, double maximumFinalHealing) {}
}
