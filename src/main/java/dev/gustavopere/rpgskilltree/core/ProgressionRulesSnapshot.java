package dev.gustavopere.rpgskilltree.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Immutable, versioned balance rules used to interpret character progression.
 *
 * <p>The fingerprint is content-derived so persisted progression can identify
 * the exact rule set under which XP and allocation decisions were interpreted.</p>
 */
public final class ProgressionRulesSnapshot {
    private static final Pattern NAMESPACED_ID = Pattern.compile("[a-z0-9_.-]+:[a-z0-9_./-]+");

    private final long version;
    private final String rulesId;
    private final List<LevelCurveBand> levelCurveBands;
    private final MainPerkBudget mainPerkBudget;
    private final LevelCorePointAwardPolicy levelCorePointAwardPolicy;
    private final SegmentedInfiniteLevelCurve levelCurve;
    private final String fingerprint;

    /**
     * Compatibility constructor for callers that have not yet supplied a level-point schedule.
     * Runtime rule loading must use the explicit constructor before level awards are enabled.
     */
    public ProgressionRulesSnapshot(
        long version,
        String rulesId,
        List<LevelCurveBand> levelCurveBands,
        MainPerkBudget mainPerkBudget
    ) {
        this(
            version,
            rulesId,
            levelCurveBands,
            mainPerkBudget,
            DisabledLevelCorePointAwardPolicy.INSTANCE
        );
    }

    public ProgressionRulesSnapshot(
        long version,
        String rulesId,
        List<LevelCurveBand> levelCurveBands,
        MainPerkBudget mainPerkBudget,
        LevelCorePointAwardPolicy levelCorePointAwardPolicy
    ) {
        if (version <= 0L) throw new IllegalArgumentException("rules version must be positive");
        Objects.requireNonNull(rulesId);
        Objects.requireNonNull(levelCurveBands);
        Objects.requireNonNull(mainPerkBudget);
        Objects.requireNonNull(levelCorePointAwardPolicy);
        if (!NAMESPACED_ID.matcher(rulesId).matches()) {
            throw new IllegalArgumentException("rulesId must be a lowercase namespaced id");
        }
        String pointPolicyCanonical = Objects.requireNonNull(levelCorePointAwardPolicy.canonicalForm());
        if (pointPolicyCanonical.isBlank()) {
            throw new IllegalArgumentException("level Core Point award policy canonical form must not be blank");
        }

        this.version = version;
        this.rulesId = rulesId;
        this.levelCurveBands = List.copyOf(levelCurveBands);
        this.mainPerkBudget = mainPerkBudget;
        this.levelCorePointAwardPolicy = levelCorePointAwardPolicy;
        this.levelCurve = new SegmentedInfiniteLevelCurve(this.levelCurveBands);
        this.fingerprint = fingerprintOf(
            version,
            rulesId,
            this.levelCurveBands,
            mainPerkBudget,
            pointPolicyCanonical
        );
    }

    public long version() {
        return version;
    }

    public String rulesId() {
        return rulesId;
    }

    public List<LevelCurveBand> levelCurveBands() {
        return levelCurveBands;
    }

    public MainPerkBudget mainPerkBudget() {
        return mainPerkBudget;
    }

    public LevelCorePointAwardPolicy levelCorePointAwardPolicy() {
        return levelCorePointAwardPolicy;
    }

    public InfiniteLevelCurve levelCurve() {
        return levelCurve;
    }

    public String fingerprint() {
        return fingerprint;
    }

    private static String fingerprintOf(
        long version,
        String rulesId,
        List<LevelCurveBand> bands,
        MainPerkBudget budget,
        String pointPolicyCanonical
    ) {
        StringBuilder canonical = new StringBuilder(160 + bands.size() * 48);
        canonical.append("progression-rules-v2\n");
        canonical.append("version=").append(version).append('\n');
        canonical.append("rulesId=").append(rulesId).append('\n');
        canonical.append("mainPerkBudget=").append(budget.total()).append('\n');
        canonical.append("levelCorePointAwardPolicy=").append(pointPolicyCanonical).append('\n');
        canonical.append("bands=").append(bands.size()).append('\n');
        for (LevelCurveBand band : bands) {
            canonical.append(band.startLevel()).append(',')
                .append(band.baseXp()).append(',')
                .append(band.growthPerLevel()).append('\n');
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(canonical.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 is unavailable", impossible);
        }
    }
}
