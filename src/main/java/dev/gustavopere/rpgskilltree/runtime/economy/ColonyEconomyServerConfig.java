package dev.gustavopere.rpgskilltree.runtime.economy;

import dev.gustavopere.rpgskilltree.core.economy.EconomyParameters;
import net.neoforged.neoforge.common.ModConfigSpec;

/** World-specific server configuration for MineColonies Economy operational policy. */
public final class ColonyEconomyServerConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue ENABLED;
    private static final ModConfigSpec.LongValue SETTLEMENT_INTERVAL_TICKS;
    private static final ModConfigSpec.LongValue MAX_MUTATION_AMOUNT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("minecoloniesEconomy");
        ENABLED = builder
            .comment("Enable MineColonies Economy settlement and administrative monetary mutations.")
            .define("enabled", true);
        SETTLEMENT_INTERVAL_TICKS = builder
            .comment("Server ticks between bounded colony-economy settlement passes.")
            .defineInRange("settlementIntervalTicks", 1_200L, 1L, Long.MAX_VALUE);
        MAX_MUTATION_AMOUNT = builder
            .comment(
                "Maximum amount accepted by one MINT or RETIRE intent. This is monetary policy and is applied after the independent packet ceiling."
            )
            .defineInRange("maxMutationAmount", Long.MAX_VALUE, 1L, Long.MAX_VALUE);
        builder.pop();
        SPEC = builder.build();
    }

    private ColonyEconomyServerConfig() {}

    public static ColonyEconomyConfigSnapshot snapshot() {
        return snapshot(ENABLED.get(), SETTLEMENT_INTERVAL_TICKS.get(), MAX_MUTATION_AMOUNT.get());
    }

    static ColonyEconomyConfigSnapshot snapshot(boolean enabled, long settlementIntervalTicks, long maxMutationAmount) {
        return new ColonyEconomyConfigSnapshot(
            enabled,
            settlementIntervalTicks,
            maxMutationAmount,
            EconomyParameters.defaults()
        );
    }
}
