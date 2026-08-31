package dev.gustavopere.volcanoes.pressure;

import java.util.List;

/** Integration extension point. Concrete Create/Destroy/Curios adapters belong to Stage 06. */
@FunctionalInterface
public interface EquipmentProtectionAdapter {
    List<ProtectionContribution> resolve(EquipmentProtectionContext context);
}
