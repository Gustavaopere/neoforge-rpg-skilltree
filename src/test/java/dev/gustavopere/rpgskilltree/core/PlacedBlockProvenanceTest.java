package dev.gustavopere.rpgskilltree.core;

import java.util.List;
import java.util.Set;

public final class PlacedBlockProvenanceTest {
    public static void main(String[] args) {
        PlacedBlockProvenance provenance = new PlacedBlockProvenance();

        require(!provenance.consume(42L), "natural/untracked blocks must remain eligible");
        require(provenance.mark(42L), "first placement must be recorded");
        require(!provenance.mark(42L), "duplicate placement must not duplicate state");
        require(provenance.contains(42L), "marked position must be present");
        require(provenance.consume(42L), "placed block must be consumed once");
        require(!provenance.consume(42L), "consumed marker must not remain farmable");

        provenance.mark(10L);
        provenance.mark(20L);
        provenance.mark(30L);
        require(provenance.removeAll(List.of(20L, 30L)) == 2, "bulk destruction must remove matching markers");
        require(provenance.snapshot().equals(Set.of(10L)), "snapshot must preserve only live markers");

        System.out.println("PlacedBlockProvenanceTest PASS");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
