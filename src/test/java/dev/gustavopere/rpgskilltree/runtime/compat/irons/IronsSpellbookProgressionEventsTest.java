package dev.gustavopere.rpgskilltree.runtime.compat.irons;

/** Ownership regression for externally-hosted spells using Iron's presentation layer. */
public final class IronsSpellbookProgressionEventsTest {
    public static void main(String[] args) {
        require(
            IronsSpellbookProgressionEvents.externallyOwnsProgression("black_arcana:irons_integration_probe"),
            "Black Arcana namespace must keep progression ownership"
        );
        require(
            !IronsSpellbookProgressionEvents.externallyOwnsProgression("irons_spellbooks:firebolt"),
            "native Iron's spells must keep native progression"
        );
        require(
            !IronsSpellbookProgressionEvents.externallyOwnsProgression(null),
            "null spell ids must fail safely"
        );
        System.out.println("IronsSpellbookProgressionEventsTest PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
