package dev.gustavopere.rpgskilltree.compendium.provider;

import dev.gustavopere.rpgskilltree.compendium.api.CompendiumEntry;

public interface CompendiumProvider {
    String providerId();

    int priority();

    ProviderContribution contribute(ProviderContext context, CompendiumEntry baseEntry);
}
