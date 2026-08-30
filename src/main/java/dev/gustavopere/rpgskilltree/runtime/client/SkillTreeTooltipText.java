package dev.gustavopere.rpgskilltree.runtime.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Pure presentation model for localized skill-tree tooltip lines. */
public final class SkillTreeTooltipText {
    private SkillTreeTooltipText() {}

    public enum PurchaseState {
        PURCHASABLE,
        PURCHASED,
        LOCKED
    }

    public record Line(String translationKey, List<String> arguments) {
        public Line {
            if (translationKey == null || translationKey.isBlank()) {
                throw new IllegalArgumentException("translationKey must not be blank");
            }
            arguments = List.copyOf(Objects.requireNonNull(arguments, "arguments"));
        }
    }

    public static List<Line> lines(
        String groupLabel,
        int rank,
        int maxRank,
        int costPerRank,
        Optional<String> effect,
        Optional<String> gate,
        PurchaseState purchaseState,
        boolean canRespec
    ) {
        Objects.requireNonNull(groupLabel, "groupLabel");
        Objects.requireNonNull(effect, "effect");
        Objects.requireNonNull(gate, "gate");
        Objects.requireNonNull(purchaseState, "purchaseState");

        List<Line> lines = new ArrayList<>();
        lines.add(new Line(
            "screen.rpgskilltree.tooltip.meta",
            List.of(groupLabel, Integer.toString(rank), Integer.toString(maxRank), Integer.toString(costPerRank))
        ));
        effect.filter(value -> !value.isBlank()).ifPresent(value ->
            lines.add(new Line("screen.rpgskilltree.tooltip.effect", List.of(plainPlayerText(value))));
        );
        gate.filter(value -> !value.isBlank()).ifPresent(value ->
            lines.add(new Line("screen.rpgskilltree.tooltip.requirement", List.of(plainPlayerText(value))));
        );
        lines.add(new Line(switch (purchaseState) {
            case PURCHASABLE -> "screen.rpgskilltree.tooltip.purchase";
            case PURCHASED -> "screen.rpgskilltree.tooltip.purchased";
            case LOCKED -> "screen.rpgskilltree.tooltip.locked";
        }, List.of()));
        if (canRespec) {
            lines.add(new Line("screen.rpgskilltree.tooltip.respec", List.of()));
        }
        return List.copyOf(lines);
    }

    private static String plainPlayerText(String text) {
        return text.replace("`", "");
    }
}
