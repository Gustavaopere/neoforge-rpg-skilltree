package dev.gustavopere.rpgskilltree.itemization.classification;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class EquipmentOverrideCatalog {
    private static final Comparator<EquipmentClassificationRule> ORDER = Comparator
        .comparingInt(EquipmentClassificationRule::priority)
        .thenComparing(rule -> rule.id().toString());

    private final List<EquipmentClassificationRule> rules;

    public EquipmentOverrideCatalog(List<EquipmentClassificationRule> rules) {
        ArrayList<EquipmentClassificationRule> sorted = new ArrayList<>(Objects.requireNonNull(rules, "rules"));
        sorted.sort(ORDER);
        this.rules = List.copyOf(sorted);
    }

    public static EquipmentOverrideCatalog empty() {
        return new EquipmentOverrideCatalog(List.of());
    }

    public List<EquipmentClassificationRule> matchingRules(EquipmentProbe probe) {
        return rules.stream().filter(rule -> rule.matches(probe)).toList();
    }

    public List<EquipmentClassificationRule> rules() {
        return rules;
    }
}
