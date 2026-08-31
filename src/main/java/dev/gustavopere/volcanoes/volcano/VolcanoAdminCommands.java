package dev.gustavopere.volcanoes.volcano;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Optional;

/** Administrative hardening commands for explicit existing-world volcano metadata registration. */
public final class VolcanoAdminCommands {
    private static final long PREVIEW_TTL_TICKS = 1_200L;
    private static final ExistingWorldVolcanoAdminSession SESSION =
            new ExistingWorldVolcanoAdminSession(PREVIEW_TTL_TICKS);
    private static final VolcanoWorldgenResolver RESOLVER =
            VolcanoWorldgenResolver.createDefault(VolcanoWorldgenFeature.MAX_FOOTPRINT_RADIUS_BLOCKS);

    private VolcanoAdminCommands() {
    }

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("volcanoes")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("world_upgrade")
                        .then(Commands.literal("preview")
                                .then(Commands.argument("chunk_x", IntegerArgumentType.integer())
                                        .then(Commands.argument("chunk_z", IntegerArgumentType.integer())
                                                .executes(VolcanoAdminCommands::preview))))
                        .then(Commands.literal("apply")
                                .then(Commands.argument("token", StringArgumentType.word())
                                        .executes(VolcanoAdminCommands::apply)))));
    }

    private static int preview(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        if (!Level.OVERWORLD.equals(level.dimension())) {
            source.sendFailure(Component.literal(
                    "Volcano world-upgrade registration is only supported in the Overworld."));
            return 0;
        }

        int chunkX = IntegerArgumentType.getInteger(context, "chunk_x");
        int chunkZ = IntegerArgumentType.getInteger(context, "chunk_z");
        ChunkPos chunk = new ChunkPos(chunkX, chunkZ);
        Optional<VolcanoSite> candidate = RESOLVER.siteOwnedByChunk(
                level.getSeed(),
                chunk,
                VolcanoWorldgenTerrainHints.forLevel(level));
        if (candidate.isEmpty()) {
            source.sendFailure(Component.literal(
                    "No canonical Volcanoes site is owned by chunk " + chunkX + "," + chunkZ + ". No state changed."));
            return 0;
        }

        VolcanoSite site = candidate.orElseThrow();
        VolcanoSavedData data = VolcanoSavedData.get(level);
        VolcanoSite existing = data.get(site.persistenceId()).orElse(null);
        if (existing != null) {
            source.sendFailure(Component.literal(
                    "Site " + site.persistenceId() + " is already registered. No state changed."));
            return 0;
        }
        if (!data.nearby(site.center(), VolcanoWorldgenResolver.DEFAULT_PERSISTED_SPACING_BLOCKS).isEmpty()) {
            source.sendFailure(Component.literal(
                    "A persisted volcano already occupies the protected spacing radius. No state changed."));
            return 0;
        }

        ExistingWorldVolcanoAdminSession.Preview preview = SESSION.preview(
                level.dimension(),
                level.getSeed(),
                level.getGameTime(),
                site);
        source.sendSuccess(() -> Component.literal(
                "PREVIEW ONLY — no terrain or SavedData changed. Site=" + site.persistenceId()
                        + " type=" + site.type()
                        + " center=" + site.center().toShortString()
                        + ". To register metadata only, run /volcanoes world_upgrade apply " + preview.token()
                        + " before tick " + preview.expiresAtTick() + "."), false);
        return 1;
    }

    private static int apply(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        String token = StringArgumentType.getString(context, "token");
        Optional<ExistingWorldVolcanoAdminSession.Preview> pending = SESSION.pending(token);
        if (pending.isEmpty()) {
            source.sendFailure(Component.literal("No active preview exists for that token. No state changed."));
            return 0;
        }

        VolcanoSite previewedSite = pending.orElseThrow().site();
        ChunkPos ownerChunk = new ChunkPos(previewedSite.center());
        Optional<VolcanoSite> currentCandidate = Level.OVERWORLD.equals(level.dimension())
                ? RESOLVER.siteOwnedByChunk(
                        level.getSeed(),
                        ownerChunk,
                        VolcanoWorldgenTerrainHints.forLevel(level))
                : Optional.empty();
        if (currentCandidate.isEmpty()) {
            SESSION.cancel(token);
            source.sendFailure(Component.literal(
                    "The preview context no longer resolves to the same canonical site. Preview discarded; no state changed."));
            return 0;
        }

        ExistingWorldVolcanoAdminSession.ApplyResult result = SESSION.apply(
                token,
                level.dimension(),
                level.getSeed(),
                level.getGameTime(),
                currentCandidate.orElseThrow(),
                VolcanoSavedData.get(level));
        if (result == ExistingWorldVolcanoAdminSession.ApplyResult.REGISTERED) {
            VolcanoSite registered = currentCandidate.orElseThrow();
            source.sendSuccess(() -> Component.literal(
                    "Registered Volcanoes metadata for site " + registered.persistenceId()
                            + " at " + registered.center().toShortString()
                            + ". Existing terrain blocks were not modified."), true);
            return 1;
        }

        source.sendFailure(Component.literal(switch (result) {
            case ALREADY_REGISTERED -> "The site is already registered; no state changed.";
            case NO_PREVIEW -> "Preview token is missing or already consumed; no state changed.";
            case EXPIRED -> "Preview token expired; run preview again. No state changed.";
            case CONTEXT_CHANGED -> "World or candidate changed since preview; no state changed.";
            case SPACING_CONFLICT -> "Another persisted volcano now occupies the spacing radius; no state changed.";
            case CONFLICT -> "A conflicting persisted site exists for this identity; no state changed.";
            case REGISTERED -> throw new IllegalStateException("handled above");
        }));
        return 0;
    }
}
