package dev.gustavopere.rpgskilltree.runtime.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonParser;
import dev.gustavopere.rpgskilltree.core.ArchetypeDefinition;
import dev.gustavopere.rpgskilltree.core.MasteryInvestmentMetadata;
import dev.gustavopere.rpgskilltree.core.MasteryState;
import dev.gustavopere.rpgskilltree.core.ProgressionDomain;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.ClassResolutionRuntime;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

final class MasteryInvestmentMetadataRuntimeJUnitTest {
    @Test
    void reloaderPublishesExplicitMetadataSnapshot() {
        List<MasteryInvestmentMetadata> previous = MasteryInvestmentMetadataCatalog.current();
        try {
            new MasteryInvestmentMetadataReloader().apply(
                Map.of(
                    ResourceLocation.parse("rpgskilltree:mastery_investments/arcane_60"),
                    JsonParser.parseString("""
                        {
                          "lane": "magic:casting",
                          "minimum_experience": 60,
                          "domain_weights": {"ARCANE": 2},
                          "tags": ["rpgskilltree:mastery/arcane_practice"]
                        }
                        """)
                ),
                null,
                null
            );

            assertEquals(1, MasteryInvestmentMetadataCatalog.current().size());
            assertEquals("magic:casting", MasteryInvestmentMetadataCatalog.current().getFirst().laneId());
        } finally {
            MasteryInvestmentMetadataCatalog.replace(previous);
        }
    }

    @Test
    void canonicalClassResolutionConsumesPublishedMasteryMetadata() {
        List<MasteryInvestmentMetadata> previousMastery = MasteryInvestmentMetadataCatalog.current();
        List<ArchetypeDefinition> previousArchetypes = ArchetypeCatalog.definitions();
        ClassInvestmentMetadataCatalog.Snapshot previousClassMetadata = ClassInvestmentMetadataCatalog.current();
        try {
            long revision = SkillTreeDataCatalog.current().revision();
            ClassInvestmentMetadataCatalog.install(revision, Map.of());
            ArchetypeCatalog.replace(List.of(new ArchetypeDefinition(
                "rpgskilltree:mage",
                10,
                1,
                Map.of(ProgressionDomain.ARCANE, 2),
                Set.of(),
                Set.of()
            )));
            MasteryInvestmentMetadataCatalog.replace(List.of(new MasteryInvestmentMetadata(
                "magic:casting",
                60,
                Map.of(ProgressionDomain.ARCANE, 2),
                Set.of("rpgskilltree:mastery/arcane_practice")
            )));
            ProgressionState state = ProgressionState.empty().withMastery(
                MasteryState.of(Map.of("magic:casting", 60))
            );

            var projection = ClassResolutionRuntime.resolveCanonical(state);

            assertEquals(true, projection.complete());
            assertEquals("rpgskilltree:mage", projection.resolution().orElseThrow().primaryClassId().orElseThrow());
        } finally {
            MasteryInvestmentMetadataCatalog.replace(previousMastery);
            ArchetypeCatalog.replace(previousArchetypes);
            ClassInvestmentMetadataCatalog.install(
                previousClassMetadata.skillTreeRevision(),
                previousClassMetadata.nodeMetadata()
            );
        }
    }

    @Test
    void emptyCatalogNeverInfersContributionFromCanonicalLaneName() {
        List<MasteryInvestmentMetadata> previousMastery = MasteryInvestmentMetadataCatalog.current();
        List<ArchetypeDefinition> previousArchetypes = ArchetypeCatalog.definitions();
        ClassInvestmentMetadataCatalog.Snapshot previousClassMetadata = ClassInvestmentMetadataCatalog.current();
        try {
            long revision = SkillTreeDataCatalog.current().revision();
            ClassInvestmentMetadataCatalog.install(revision, Map.of());
            MasteryInvestmentMetadataCatalog.replace(List.of());
            ArchetypeCatalog.replace(List.of(new ArchetypeDefinition(
                "rpgskilltree:mage",
                10,
                1,
                Map.of(ProgressionDomain.ARCANE, 1),
                Set.of(),
                Set.of()
            )));
            ProgressionState state = ProgressionState.empty().withMastery(
                MasteryState.of(Map.of("magic:casting", 10_000))
            );

            var projection = ClassResolutionRuntime.resolveCanonical(state);

            assertEquals(true, projection.complete());
            assertEquals(Optional.empty(), projection.resolution().orElseThrow().primaryClassId());
            assertEquals(0, projection.investmentState().domainScore(ProgressionDomain.ARCANE));
        } finally {
            MasteryInvestmentMetadataCatalog.replace(previousMastery);
            ArchetypeCatalog.replace(previousArchetypes);
            ClassInvestmentMetadataCatalog.install(
                previousClassMetadata.skillTreeRevision(),
                previousClassMetadata.nodeMetadata()
            );
        }
    }

    @Test
    void publicRuntimeApiExposesOnlyCatalogBackedCanonicalResolution() {
        List<Method> publicCanonicalMethods = Arrays.stream(ClassResolutionRuntime.class.getMethods())
            .filter(method -> method.getName().equals("resolveCanonical"))
            .toList();

        assertEquals(1, publicCanonicalMethods.size());
        assertEquals(List.of(ProgressionState.class), List.of(publicCanonicalMethods.getFirst().getParameterTypes()));
    }
}
