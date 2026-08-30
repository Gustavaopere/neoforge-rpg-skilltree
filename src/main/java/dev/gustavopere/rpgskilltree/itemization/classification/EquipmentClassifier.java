package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public final class EquipmentClassifier {
    private static final ResourceLocation STRUCTURAL_PROVIDER = id("structural");
    private static final ResourceLocation FALLBACK_PROVIDER = id("fallback");
    private static final ResourceLocation OVERRIDE_PROVIDER = id("override");

    private static final Comparator<EquipmentClassificationAdapter> ADAPTER_ORDER = Comparator
        .comparingInt(EquipmentClassificationAdapter::priority)
        .thenComparing(adapter -> adapter.providerId().toString());

    private final EquipmentOverrideCatalog overrides;
    private final List<EquipmentClassificationAdapter> adapters;

    public EquipmentClassifier(EquipmentOverrideCatalog overrides, List<EquipmentClassificationAdapter> adapters) {
        this.overrides = Objects.requireNonNull(overrides, "overrides");
        ArrayList<EquipmentClassificationAdapter> sorted = new ArrayList<>(Objects.requireNonNull(adapters, "adapters"));
        sorted.sort(ADAPTER_ORDER);
        this.adapters = List.copyOf(sorted);
    }

    public EquipmentClassification classify(EquipmentProbe probe) {
        Objects.requireNonNull(probe, "probe");

        EnumSet<EquipmentCategory> categories = probe.structuralCategories().isEmpty()
            ? EnumSet.noneOf(EquipmentCategory.class)
            : EnumSet.copyOf(probe.structuralCategories());
        boolean eligible = baseEligibility(probe);
        ResourceLocation provider = STRUCTURAL_PROVIDER;

        for (EquipmentClassificationAdapter adapter : adapters) {
            Optional<EquipmentAdapterContribution> contribution = Objects.requireNonNull(
                adapter.classify(probe),
                "adapter contribution optional"
            );
            if (contribution.isEmpty()) {
                continue;
            }
            EquipmentAdapterContribution resolved = contribution.get();
            categories.addAll(resolved.categories());
            eligible = applyEligibility(eligible, resolved.eligibility());
            provider = Objects.requireNonNull(adapter.providerId(), "adapter providerId");
        }

        List<EquipmentClassificationRule> matched = overrides.matchingRules(probe);
        List<ResourceLocation> matchedIds = matched.stream().map(EquipmentClassificationRule::id).toList();
        if (!matched.isEmpty()) {
            for (EquipmentClassificationRule rule : matched) {
                if (rule.replaceCategories()) {
                    categories.clear();
                }
                categories.addAll(rule.addCategories());
                categories.removeAll(rule.removeCategories());
                eligible = applyEligibility(eligible, rule.eligibility());
            }
            provider = OVERRIDE_PROVIDER;
        }

        if (!eligible) {
            return new EquipmentClassification(false, Set.of(), provider, false, matchedIds);
        }

        boolean fallback = categories.isEmpty();
        if (fallback) {
            categories.add(EquipmentCategory.GENERIC_EQUIPMENT);
            if (matched.isEmpty() && provider.equals(STRUCTURAL_PROVIDER)) {
                provider = FALLBACK_PROVIDER;
            }
        }

        return new EquipmentClassification(true, categories, provider, fallback, matchedIds);
    }

    private static boolean baseEligibility(EquipmentProbe probe) {
        if (probe.explicitEquipmentSignal()) {
            return true;
        }
        return probe.durable() && !probe.blockItem() && !probe.consumable();
    }

    private static boolean applyEligibility(boolean current, EligibilityOverride override) {
        return switch (override) {
            case INHERIT -> current;
            case WHITELIST -> true;
            case BLACKLIST -> false;
        };
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath("rpgskilltree", path);
    }
}
