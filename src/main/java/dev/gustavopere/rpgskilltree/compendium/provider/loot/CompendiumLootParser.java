package dev.gustavopere.rpgskilltree.compendium.provider.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class CompendiumLootParser {
    private CompendiumLootParser() {}

    public static LootSummary parse(String tableId, Map<String, Object> root) {
        if (root == null) throw new IllegalArgumentException("loot root must not be null");
        ArrayList<LootEntrySummary> summaries = new ArrayList<>();
        for (Object poolValue : list(root.get("pools"))) {
            Map<String, Object> pool = object(poolValue);
            if (pool == null) continue;
            List<Object> entries = list(pool.get("entries"));
            List<LootConditionSummary> poolConditions = parseConditions(pool.get("conditions"));
            boolean exactSingleRoll = isExactOne(pool.get("rolls")) && entries.size() == 1 && poolConditions.isEmpty();
            for (Object entryValue : entries) {
                Map<String, Object> entry = object(entryValue);
                if (entry == null || !"minecraft:item".equals(string(entry.get("type")))) continue;
                String itemId = string(entry.get("name"));
                if (itemId == null) continue;

                ArrayList<LootConditionSummary> conditions = new ArrayList<>(poolConditions);
                List<LootConditionSummary> entryConditions = parseConditions(entry.get("conditions"));
                conditions.addAll(entryConditions);

                LootNumberSummary count = LootNumberSummary.exact(1.0);
                for (Object functionValue : list(entry.get("functions"))) {
                    Map<String, Object> function = object(functionValue);
                    if (function == null) {
                        count = LootNumberSummary.conditional();
                        continue;
                    }
                    String functionId = string(function.get("function"));
                    if ("minecraft:set_count".equals(functionId)) {
                        count = parseCount(function.get("count"));
                    } else if ("minecraft:looting_enchant".equals(functionId)
                        || "minecraft:enchanted_count_increase".equals(functionId)) {
                        count = LootNumberSummary.conditional();
                        conditions.add(LootConditionSummary.looting());
                    } else {
                        count = LootNumberSummary.conditional();
                        conditions.add(LootConditionSummary.unsupported(functionId == null ? "unknown_function" : functionId));
                    }
                }

                boolean hasConditionalContext = !conditions.isEmpty();
                LootNumberSummary chance = exactSingleRoll && entryConditions.isEmpty() && !hasConditionalContext
                    ? LootNumberSummary.exact(1.0)
                    : LootNumberSummary.conditional();
                summaries.add(new LootEntrySummary(itemId, count, chance, conditions));
            }
        }
        return new LootSummary(tableId, summaries);
    }

    private static LootNumberSummary parseCount(Object value) {
        if (value instanceof Number number) {
            double numeric = number.doubleValue();
            return Double.isFinite(numeric) && numeric >= 0.0 ? LootNumberSummary.exact(numeric) : LootNumberSummary.conditional();
        }
        Map<String, Object> map = object(value);
        if (map == null) return LootNumberSummary.conditional();
        String type = string(map.get("type"));
        if ("minecraft:constant".equals(type)) {
            Object constant = map.containsKey("value") ? map.get("value") : map.get("min");
            if (constant instanceof Number number) {
                double numeric = number.doubleValue();
                if (Double.isFinite(numeric) && numeric >= 0.0) return LootNumberSummary.exact(numeric);
            }
            return LootNumberSummary.conditional();
        }
        if ("minecraft:uniform".equals(type)) {
            Double min = number(map.get("min"));
            Double max = number(map.get("max"));
            if (min != null && max != null && min >= 0.0 && max >= min) return LootNumberSummary.range(min, max);
        }
        return LootNumberSummary.conditional();
    }

    private static List<LootConditionSummary> parseConditions(Object raw) {
        ArrayList<LootConditionSummary> result = new ArrayList<>();
        for (Object value : list(raw)) {
            Map<String, Object> condition = object(value);
            String id = condition == null ? null : string(condition.get("condition"));
            if ("minecraft:killed_by_player".equals(id)) {
                result.add(LootConditionSummary.playerKill());
            } else {
                result.add(LootConditionSummary.unsupported(id == null ? "unknown_condition" : id));
            }
        }
        return List.copyOf(result);
    }

    private static boolean isExactOne(Object value) {
        if (value == null) return true;
        if (value instanceof Number number) return Double.compare(number.doubleValue(), 1.0) == 0;
        Map<String, Object> map = object(value);
        if (map == null || !"minecraft:constant".equals(string(map.get("type")))) return false;
        Double constant = number(map.get("value"));
        return constant != null && Double.compare(constant, 1.0) == 0;
    }

    private static Double number(Object value) {
        if (!(value instanceof Number number)) return null;
        double numeric = number.doubleValue();
        return Double.isFinite(numeric) ? numeric : null;
    }

    private static String string(Object value) {
        return value instanceof String text && !text.trim().isEmpty() ? text.trim() : null;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> object(Object value) {
        return value instanceof Map<?, ?> map ? (Map<String, Object>) map : null;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> list(Object value) {
        return value instanceof List<?> values ? (List<Object>) values : List.of();
    }
}
