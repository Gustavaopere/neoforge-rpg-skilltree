package dev.gustavopere.rpgskilltree.runtime.compendium;

import dev.gustavopere.rpgskilltree.compendium.provider.ecology.TamingFacts;
import java.util.Optional;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;

/** Projects whitelisted ecology state from an already-existing entity without retaining world/entity references. */
public final class RuntimeEntityEcologyInspector {
    private RuntimeEntityEcologyInspector() {}

    public static Optional<Snapshot> inspect(Entity entity) {
        if (entity == null) return Optional.empty();

        Boolean adult = entity instanceof AgeableMob ageable ? ageable.getAge() >= 0 : null;
        Boolean breedReady = entity instanceof Animal animal ? animal.isInLove() : null;
        TamingFacts taming = null;
        if (entity instanceof TamableAnimal tamable) {
            String ownerId = tamable.getOwnerUUID() == null ? null : tamable.getOwnerUUID().toString();
            taming = TamingFacts.instance(tamable.isTame(), ownerId);
        }
        return Optional.of(new Snapshot(adult, breedReady, taming));
    }

    public record Snapshot(Boolean adult, Boolean breedReady, TamingFacts taming) {}
}
