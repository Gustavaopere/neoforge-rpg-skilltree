package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies.economy;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.eventbus.events.colony.ColonyDeletedModEvent;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomyEvents;
import dev.gustavopere.rpgskilltree.runtime.economy.ColonyEconomySavedData;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Public-provider lifecycle hooks needed to keep native colony IDs from inheriting old money. */
public final class MineColoniesEconomyLifecycleEvents {
    private static final AtomicBoolean INSTALLED = new AtomicBoolean();
    private static final Logger LOGGER = LoggerFactory.getLogger(MineColoniesEconomyLifecycleEvents.class);

    private MineColoniesEconomyLifecycleEvents() {}

    public static void install() {
        if (!INSTALLED.compareAndSet(false, true)) {
            return;
        }
        try {
            IMinecoloniesAPI.getInstance().getEventBus().subscribe(
                ColonyDeletedModEvent.class,
                MineColoniesEconomyLifecycleEvents::onColonyDeleted
            );
            NeoForge.EVENT_BUS.register(ColonyEconomyEvents.class);
        } catch (RuntimeException | LinkageError failure) {
            INSTALLED.set(false);
            throw failure;
        }
    }

    /** True only after both provider deletion subscription and NeoForge settlement hooks register. */
    public static boolean isInstalled() {
        return INSTALLED.get();
    }

    private static void onColonyDeleted(ColonyDeletedModEvent event) {
        if (event == null) {
            return;
        }
        IColony colony = event.getColony();
        if (colony == null) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        try {
            ColonyEconomySavedData.get(server).archiveNativeBinding(
                colony.getDimension().location(),
                colony.getID()
            );
        } catch (RuntimeException | LinkageError failure) {
            // Do not block MineColonies deletion. Leaving the stale binding intact is fail-closed:
            // a recycled native id cannot inherit money because fingerprint validation will reject it.
            LOGGER.error(
                "Failed to archive MineColonies economy binding {}#{} after colony deletion; monetary identity remains fail-closed",
                colony.getDimension().location(),
                colony.getID(),
                failure
            );
        }
    }
}
