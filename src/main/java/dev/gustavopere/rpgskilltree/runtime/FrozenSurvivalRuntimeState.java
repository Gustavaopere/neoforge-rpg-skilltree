package dev.gustavopere.rpgskilltree.runtime;

import dev.gustavopere.rpgskilltree.core.AcclimationLedger;
import dev.gustavopere.rpgskilltree.core.BodyCostResolver;
import dev.gustavopere.rpgskilltree.core.CanonicalMaintenanceService;
import dev.gustavopere.rpgskilltree.core.CastingStabilityService;
import dev.gustavopere.rpgskilltree.core.DamageMitigationResolver;
import dev.gustavopere.rpgskilltree.core.EmergencyGuardService;
import dev.gustavopere.rpgskilltree.core.EquipmentConservationService;
import dev.gustavopere.rpgskilltree.core.FieldReinforcementService;
import dev.gustavopere.rpgskilltree.core.FrozenDefensiveTradeoffPolicy;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.FrozenSurvivalPerkRanks;
import dev.gustavopere.rpgskilltree.core.MagicPowerResolver;
import dev.gustavopere.rpgskilltree.core.NutritionQualityResolver;
import dev.gustavopere.rpgskilltree.core.PhysiologicalRestService;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.core.ReactiveShellService;
import dev.gustavopere.rpgskilltree.core.ResourceDebitReceiptService;
import dev.gustavopere.rpgskilltree.core.SecondWindService;
import dev.gustavopere.rpgskilltree.core.SpellRecoveryService;
import dev.gustavopere.rpgskilltree.core.TfcExhaustionHydrationLedger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.util.FakePlayer;

/** Server owner for A0101-A0150 runtime state; transient clears retain cooldowns and claims. */
public final class FrozenSurvivalRuntimeState {
    private static final int MAX_PLAYERS = 256;
    private static final ResourceLocation A0105_ARMOR = id("a0105_reactive_armor");
    private static final ResourceLocation A0105_TOUGHNESS = id("a0105_reactive_toughness");
    private static final ResourceLocation A0108_MOVEMENT = id("a0108_stone_skin_movement_cost");
    private static final String ACCLIMATION_HOT = "rpgskilltree.a0101_a0150.acclimation_hot";
    private static final String ACCLIMATION_COLD = "rpgskilltree.a0101_a0150.acclimation_cold";
    private static final DamageMitigationResolver DAMAGE_MITIGATION = new DamageMitigationResolver(8_192);
    private static final SecondWindService SECOND_WIND = new SecondWindService(MAX_PLAYERS);
    private static final ReactiveShellService REACTIVE_SHELL = new ReactiveShellService(MAX_PLAYERS);
    private static final EmergencyGuardService EMERGENCY_GUARD = new EmergencyGuardService(MAX_PLAYERS);
    private static final EquipmentConservationService EQUIPMENT_CONSERVATION =
        new EquipmentConservationService(8_192);
    private static final CanonicalMaintenanceService MAINTENANCE = new CanonicalMaintenanceService(MAX_PLAYERS);
    private static final FieldReinforcementService FIELD_REINFORCEMENT = new FieldReinforcementService(8_192);
    private static final BodyCostResolver BODY_COST = new BodyCostResolver(8_192);
    private static final TfcExhaustionHydrationLedger TFC_HYDRATION =
        new TfcExhaustionHydrationLedger(8_192);
    private static final AcclimationLedger ACCLIMATION = new AcclimationLedger(MAX_PLAYERS);
    private static final PhysiologicalRestService PHYSIOLOGICAL_REST =
        new PhysiologicalRestService(MAX_PLAYERS);
    private static final NutritionQualityResolver NUTRITION = new NutritionQualityResolver();
    private static final MagicPowerResolver MAGIC_POWER = new MagicPowerResolver(8_192);
    private static final SpellRecoveryService SPELL_RECOVERY = new SpellRecoveryService(MAX_PLAYERS);
    private static final ResourceDebitReceiptService RESOURCE_RECEIPTS =
        new ResourceDebitReceiptService(8_192);
    private static final CastingStabilityService CASTING_STABILITY =
        new CastingStabilityService(MAX_PLAYERS, RESOURCE_RECEIPTS);
    private static final Set<String> ACCLIMATION_ACTIVE = new HashSet<>();

    private FrozenSurvivalRuntimeState() {}

    public static DamageMitigationResolver damageMitigation() { return DAMAGE_MITIGATION; }
    public static SecondWindService secondWind() { return SECOND_WIND; }
    public static ReactiveShellService reactiveShell() { return REACTIVE_SHELL; }
    public static EmergencyGuardService emergencyGuard() { return EMERGENCY_GUARD; }
    public static EquipmentConservationService equipmentConservation() { return EQUIPMENT_CONSERVATION; }
    public static CanonicalMaintenanceService maintenance() { return MAINTENANCE; }
    public static FieldReinforcementService fieldReinforcement() { return FIELD_REINFORCEMENT; }
    public static BodyCostResolver bodyCost() { return BODY_COST; }
    public static TfcExhaustionHydrationLedger tfcHydration() { return TFC_HYDRATION; }
    public static AcclimationLedger acclimation() { return ACCLIMATION; }
    public static PhysiologicalRestService physiologicalRest() { return PHYSIOLOGICAL_REST; }
    public static NutritionQualityResolver nutrition() { return NUTRITION; }
    public static MagicPowerResolver magicPower() { return MAGIC_POWER; }
    public static SpellRecoveryService spellRecovery() { return SPELL_RECOVERY; }
    public static ResourceDebitReceiptService resourceReceipts() { return RESOURCE_RECEIPTS; }
    public static CastingStabilityService castingStability() { return CASTING_STABILITY; }

    public static FrozenSurvivalPerkRanks ranks(ProgressionState progression) {
        Objects.requireNonNull(progression);
        return FrozenSurvivalPerkNodeBinding.ranks(progression.passiveNodes());
    }

    public static FrozenSurvivalPerkRanks ranks(ServerPlayer player) {
        return ranks(PlayerProgressionRuntime.get(player));
    }

    public static void revalidate(ServerPlayer player, ProgressionState progression) {
        Objects.requireNonNull(player);
        ensureAcclimationLoaded(player);
        FrozenSurvivalPerkRanks ranks = ranks(progression);
        String playerId = player.getUUID().toString();
        if (!ranks.learned("A0104")) SECOND_WIND.clearTransient(playerId);
        if (!ranks.learned("A0105")) REACTIVE_SHELL.clearTransient(playerId);
        if (!ranks.learned("A0106")) EMERGENCY_GUARD.clearTransient(playerId);
        refreshStoneSkin(player, ranks);
        refreshReactiveShell(player, ranks, player.level().getGameTime());
    }

    /** Initializes online-only clocks without granting offline acclimation or out-of-combat time. */
    public static void beginSession(ServerPlayer player) {
        Objects.requireNonNull(player);
        ensureAcclimationLoaded(player);
        long nowTick = player.level().getGameTime();
        String playerId = player.getUUID().toString();
        MAINTENANCE.recordHostileCombat(playerId, nowTick);
        PHYSIOLOGICAL_REST.invalidateLifecycle(playerId, nowTick);
    }

    public static void recordHostileCombat(ServerPlayer player) {
        Objects.requireNonNull(player);
        long nowTick = player.level().getGameTime();
        String playerId = player.getUUID().toString();
        MAINTENANCE.recordHostileCombat(playerId, nowTick);
        PHYSIOLOGICAL_REST.recordHostileCombat(playerId, nowTick);
    }

    public static void recordPhysicalBodyCost(ServerPlayer player) {
        Objects.requireNonNull(player);
        PHYSIOLOGICAL_REST.recordPhysicalBodyCost(
            player.getUUID().toString(), player.level().getGameTime());
    }

    /** Provider adapters call this only from an authoritative mapped thermal state. */
    public static synchronized AcclimationLedger.Snapshot observeThermalState(
        ServerPlayer player,
        AcclimationLedger.ThermalState state
    ) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(state);
        if (player instanceof FakePlayer || player.isCreative() || player.isSpectator()) {
            return ACCLIMATION.snapshot(player.getUUID().toString());
        }
        ensureAcclimationLoaded(player);
        AcclimationLedger.Snapshot snapshot = ACCLIMATION.observe(
            player.getUUID().toString(), state, player.level().getGameTime());
        saveAcclimation(player, snapshot);
        return snapshot;
    }

    public static boolean stoneSkinCostApplied(ServerPlayer player) {
        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        return movement != null && movement.getModifier(A0108_MOVEMENT) != null;
    }

    public static void refreshReactiveShell(
        ServerPlayer player,
        FrozenSurvivalPerkRanks ranks,
        long nowTick
    ) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        AttributeInstance toughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armor != null) armor.removeModifier(A0105_ARMOR);
        if (toughness != null) toughness.removeModifier(A0105_TOUGHNESS);
        if (!ranks.learned("A0105")) return;

        double baseArmor = armor == null ? 0.0D : armor.getValue();
        double baseToughness = toughness == null ? 0.0D : toughness.getValue();
        ReactiveShellService.Bonuses bonuses = REACTIVE_SHELL.bonuses(
            player.getUUID().toString(), baseArmor, baseToughness, nowTick);
        if (armor != null && bonuses.armor() > 0.0D) {
            armor.addOrUpdateTransientModifier(new AttributeModifier(
                A0105_ARMOR, bonuses.armor(), AttributeModifier.Operation.ADD_VALUE));
        }
        if (toughness != null && bonuses.toughness() > 0.0D) {
            toughness.addOrUpdateTransientModifier(new AttributeModifier(
                A0105_TOUGHNESS, bonuses.toughness(), AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void clearTransient(ServerPlayer player) {
        Objects.requireNonNull(player);
        String playerId = player.getUUID().toString();
        SECOND_WIND.clearTransient(playerId);
        REACTIVE_SHELL.clearTransient(playerId);
        EMERGENCY_GUARD.clearTransient(playerId);
        MAINTENANCE.clearTransient(playerId);
        FIELD_REINFORCEMENT.clearTransient(playerId);
        TFC_HYDRATION.clearTransient(playerId);
        SPELL_RECOVERY.clearTransient(playerId);
        RESOURCE_RECEIPTS.clearTransient(playerId);
        CASTING_STABILITY.clearTransient(playerId);
        suspendAcclimation(player);
        remove(player.getAttribute(Attributes.ARMOR), A0105_ARMOR);
        remove(player.getAttribute(Attributes.ARMOR_TOUGHNESS), A0105_TOUGHNESS);
        remove(player.getAttribute(Attributes.MOVEMENT_SPEED), A0108_MOVEMENT);
    }

    private static synchronized void ensureAcclimationLoaded(ServerPlayer player) {
        String playerId = player.getUUID().toString();
        if (!ACCLIMATION_ACTIVE.add(playerId)) return;
        int hot = clampCharges(player.getPersistentData().getInt(ACCLIMATION_HOT));
        int cold = clampCharges(player.getPersistentData().getInt(ACCLIMATION_COLD));
        ACCLIMATION.restore(playerId, new AcclimationLedger.Snapshot(hot, cold));
    }

    private static synchronized void suspendAcclimation(ServerPlayer player) {
        String playerId = player.getUUID().toString();
        if (!ACCLIMATION_ACTIVE.remove(playerId)) return;
        AcclimationLedger.Snapshot snapshot = ACCLIMATION.snapshot(playerId);
        saveAcclimation(player, snapshot);
        ACCLIMATION.suspend(playerId);
    }

    private static void saveAcclimation(ServerPlayer player, AcclimationLedger.Snapshot snapshot) {
        player.getPersistentData().putInt(ACCLIMATION_HOT, snapshot.hotCharges());
        player.getPersistentData().putInt(ACCLIMATION_COLD, snapshot.coldCharges());
    }

    private static int clampCharges(int value) {
        return Math.max(0, Math.min(AcclimationLedger.MAX_CHARGES, value));
    }

    private static void refreshStoneSkin(ServerPlayer player, FrozenSurvivalPerkRanks ranks) {
        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null) return;
        movement.removeModifier(A0108_MOVEMENT);
        FrozenDefensiveTradeoffPolicy.Tradeoff tradeoff = FrozenDefensiveTradeoffPolicy.stoneSkin(
            ranks.rank("A0108"), true, true);
        if (tradeoff.active()) {
            movement.addOrUpdateTransientModifier(new AttributeModifier(
                A0108_MOVEMENT,
                tradeoff.movementSpeedMultiplierDelta(),
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }
    }

    private static void remove(AttributeInstance attribute, ResourceLocation id) {
        if (attribute != null) attribute.removeModifier(id);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("rpgskilltree", path);
    }
}
