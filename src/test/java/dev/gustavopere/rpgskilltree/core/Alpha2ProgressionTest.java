package dev.gustavopere.rpgskilltree.core;

import java.util.Map;

public final class Alpha2ProgressionTest {
    public static void main(String[] args) {
        characterLevelCurveHasDeterministicBoundaries();
        passivePointLedgerPreservesProvenanceAndSpendState();
        bossRewardsUseConfiguredFirstKillValues();
        finalTriadsGatePureAndHybridClassTrees();
        targetPlannerFindsMinimumPointRoute();
        morphAccessSeparatesDruidAndMetamorphForms();
        morphPermissionsComeFromLiveClassTreeInvestment();
        morphClassificationUsesOverridesBeforeSpawnCategoryFallback();
        apothicContractsModifyRealSocketsAndGemPower();
        progressionServiceConnectsXpLevelsAndBossPoints();
        postClassSpecializationsRequireClassMasteryAndGateway();
        warlockPactsUseExclusiveChoiceCapacityByDefault();
        classChoicesCanBeClearedForSafePactRespec();
        unlockingAClassPersistsItAndSpendsOnlyAbnormalBridgePoints();
        progressionSnapshotRoundTripsCompleteBuildState();
        bossRewardKeysUseStableBoundedIdentities();
        gameplayXpPolicyScalesCombatWithoutUnboundedBossXp();
        apothicBossRewardKeysStayFiniteAcrossRandomSpawns();
        finalTriadsPersistAndLegacySnapshotsMigrate();
        finalTriadInvestmentSpendsPointsTransactionally();
        classUnlockUsesPersistedFinalTriads();
        passiveNodePurchasesAreServerAuthoritativeAndRanked();
        passiveNodeRanksPersistAndV2SnapshotsMigrate();
        passiveNodeRespecCascadesOnlyOrphanedBranchesAndRefundsExactCosts();
        passiveFinalTriadNodesDrivePersistedTriadsAndClassEligibility();
        automaticClassesUnlockOnlyForZeroCostEligibleConfluences();
        nodeAttributeEffectsScaleWithPersistedRanks();
        treeDisplayProjectionMatchesAuthoritativePurchaseState();
        explorationDiscoveriesAwardXpOnlyOnceAndPersist();
        oreMiningXpRewardsTaggedOresWithoutExplodingProgression();
        automaticClassesReconcileWhenFinalTriadsAreRespecced();
        nodeAccessRequirementsAreEvaluatedFromAuthoritativeState();
        nodeAccessRequirementsCanRequireClassChoices();
        clearingClassChoiceInvalidatesChoiceGatedNodesAndRefunds();
        nodeGrantedSpecializationsTrackLiveGatewayRanks();
        treeDisplayProjectionRespectsAuthoritativeRequirements();
        invalidClassSubtreesAreRemovedAndRefunded();
        System.out.println("Alpha2ProgressionTest: PASS");
    }

    static void characterLevelCurveHasDeterministicBoundaries() {
        var curve = CharacterLevelCurve.defaultCurve();
        eq(1, curve.levelForTotalXp(0));
        eq(2, curve.levelForTotalXp(curve.xpRequiredForLevel(2)));
        eq(1, curve.levelForTotalXp(curve.xpRequiredForLevel(2) - 1));
        eq(100, curve.levelForTotalXp(Long.MAX_VALUE));
        eq(true, curve.xpRequiredForLevel(60) > curve.xpRequiredForLevel(20));
        eq(true, curve.xpRequiredForLevel(100) > curve.xpRequiredForLevel(60));

        var progress = CharacterProgress.fromTotalXp(curve, curve.xpRequiredForLevel(10));
        eq(10, progress.level());
        eq(9, progress.levelPointsEarned());
    }

    static void passivePointLedgerPreservesProvenanceAndSpendState() {
        var ledger = PassivePointLedger.empty()
            .award(PassivePointSource.LEVEL, 9)
            .award(PassivePointSource.BOSS, 5)
            .award(PassivePointSource.ADVANCEMENT, 2);
        eq(16, ledger.totalEarned());
        eq(9, ledger.earned(PassivePointSource.LEVEL));
        eq(5, ledger.earned(PassivePointSource.BOSS));
        eq(16, ledger.available());

        ledger = ledger.spend(11);
        eq(11, ledger.spent());
        eq(5, ledger.available());
        eq(Map.of(
            PassivePointSource.LEVEL, 9,
            PassivePointSource.BOSS, 5,
            PassivePointSource.ADVANCEMENT, 2
        ), ledger.earnedBySource());

        ledger = ledger.refund(4);
        eq(7, ledger.spent());
        eq(9, ledger.available());

        boolean overspendRejected = false;
        try { ledger.spend(10); } catch (IllegalArgumentException expected) { overspendRejected = true; }
        eq(true, overspendRejected);
    }

    static void bossRewardsUseConfiguredFirstKillValues() {
        var registry = BossRewardRegistry.defaults();
        eq(5, registry.resolveForNamespace("cataclysm").points());
        eq(2, registry.resolveForNamespace("apotheosis").points());
        eq(2, registry.resolveForNamespace("apothic_spawners").points());
        eq(3, registry.resolveForNamespace("minecraft").points());

        var progress = BossProgress.empty();
        var first = progress.creditFirstDefeat("cataclysm:ignis", registry.resolveForNamespace("cataclysm"));
        eq(5, first.pointsAwarded());
        eq(true, first.firstDefeat());
        progress = first.progress();

        var repeated = progress.creditFirstDefeat("cataclysm:ignis", registry.resolveForNamespace("cataclysm"));
        eq(0, repeated.pointsAwarded());
        eq(false, repeated.firstDefeat());

        var vanilla = repeated.progress().creditFirstDefeat("minecraft:ender_dragon", registry.resolveForNamespace("minecraft"));
        eq(3, vanilla.pointsAwarded());
    }

    static void finalTriadsGatePureAndHybridClassTrees() {
        var incomplete = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, java.util.List.of(3, 3, 2)
        ));
        eq(false, incomplete.complete(ProgressionDomain.ARCANE));

        var triads = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, java.util.List.of(3, 3, 3),
            ProgressionDomain.HEALING, java.util.List.of(3, 3, 3),
            ProgressionDomain.VITALITY, java.util.List.of(3, 3, 3),
            ProgressionDomain.MINING, java.util.List.of(3, 3, 3)
        ));
        eq(true, triads.complete(ProgressionDomain.ARCANE));
        eq(9, triads.pointsInFinalTriad(ProgressionDomain.ARCANE));

        var arcanist = new ClassUnlockDefinition("arcanist", java.util.Set.of(ProgressionDomain.ARCANE), true, 0);
        var paladin = new ClassUnlockDefinition("paladin", java.util.Set.of(ProgressionDomain.VITALITY, ProgressionDomain.HEALING), true, 0);
        var geomancer = new ClassUnlockDefinition("geomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING), false, 10);

        var pure = ClassUnlockResolver.evaluate(triads, arcanist, 0);
        eq(true, pure.unlockable());
        eq(0, pure.bridgeCost());

        var adjacent = ClassUnlockResolver.evaluate(triads, paladin, 0);
        eq(true, adjacent.unlockable());
        eq(0, adjacent.bridgeCost());

        var distantNoPoints = ClassUnlockResolver.evaluate(triads, geomancer, 9);
        eq(false, distantNoPoints.unlockable());
        eq(10, distantNoPoints.bridgeCost());
        eq(1, distantNoPoints.missingBridgePoints());

        var distant = ClassUnlockResolver.evaluate(triads, geomancer, 10);
        eq(true, distant.unlockable());
        eq(10, distant.bridgeCost());
    }

    static void targetPlannerFindsMinimumPointRoute() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("a", "b"),
            new SkillGraph.Edge("b", "c"),
            new SkillGraph.Edge("c", "target"),
            new SkillGraph.Edge("a", "x"),
            new SkillGraph.Edge("x", "y"),
            new SkillGraph.Edge("y", "target")
        ));
        var path = SkillPathPlanner.shortestPath(graph, java.util.Set.of("a", "b"), "target");
        eq(java.util.List.of("b", "c", "target"), path.nodeIds());
        eq(2, path.pointsRequired());

        var owned = SkillPathPlanner.shortestPath(graph, java.util.Set.of("a", "b", "c", "target"), "target");
        eq(java.util.List.of("target"), owned.nodeIds());
        eq(0, owned.pointsRequired());
    }

    static void morphAccessSeparatesDruidAndMetamorphForms() {
        var cow = new MorphFormDescriptor("minecraft:cow", MorphFormCategory.NATURAL_LAND, java.util.Set.of("animal"));
        var dolphin = new MorphFormDescriptor("minecraft:dolphin", MorphFormCategory.NATURAL_AQUATIC, java.util.Set.of("animal"));
        var zombie = new MorphFormDescriptor("minecraft:zombie", MorphFormCategory.MONSTER, java.util.Set.of("undead"));
        var dragon = new MorphFormDescriptor("minecraft:ender_dragon", MorphFormCategory.MONSTER, java.util.Set.of("boss", "rpgskilltree:morph_blacklist"));
        var arrow = new MorphFormDescriptor("minecraft:arrow", MorphFormCategory.TECHNICAL, java.util.Set.of());

        eq(true, MorphAccessPolicy.canUse(cow, java.util.Set.of(MorphPermission.DRUID_LAND)));
        eq(false, MorphAccessPolicy.canUse(dolphin, java.util.Set.of(MorphPermission.DRUID_LAND)));
        eq(true, MorphAccessPolicy.canUse(dolphin, java.util.Set.of(MorphPermission.DRUID_AQUATIC)));
        eq(false, MorphAccessPolicy.canUse(zombie, java.util.Set.of(MorphPermission.DRUID_LAND, MorphPermission.DRUID_AQUATIC)));
        eq(true, MorphAccessPolicy.canUse(zombie, java.util.Set.of(MorphPermission.METAMORPH_MONSTER)));
        eq(false, MorphAccessPolicy.canUse(dragon, java.util.Set.of(MorphPermission.METAMORPH_MONSTER)));
        eq(false, MorphAccessPolicy.canUse(arrow, java.util.Set.of(MorphPermission.METAMORPH_MONSTER)));
    }

    static void morphPermissionsComeFromLiveClassTreeInvestment() {
        var druid = ProgressionState.empty()
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("druid")));
        eq(java.util.Set.of(MorphPermission.DRUID_LAND), MorphPermissionResolver.resolve(druid));

        druid = druid.withPassiveNodes(PassiveNodeProgress.of(java.util.Map.of(
            "rpgskilltree:druid/aquatic_shape", 1,
            "rpgskilltree:druid/winged_shape", 1,
            "rpgskilltree:druid/primal_spirit", 1
        )));
        eq(java.util.Set.of(
            MorphPermission.DRUID_LAND,
            MorphPermission.DRUID_AQUATIC,
            MorphPermission.DRUID_FLYING,
            MorphPermission.DRUID_MAGICAL_NATURAL
        ), MorphPermissionResolver.resolve(druid));

        var metamorph = ProgressionState.empty()
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("metamorph")))
            .withPassiveNodes(PassiveNodeProgress.of(java.util.Map.of(
                "rpgskilltree:metamorph/monstrous_flesh", 1,
                "rpgskilltree:metamorph/aberrant_form", 1
            )));
        eq(java.util.Set.of(
            MorphPermission.METAMORPH_HUMANOID,
            MorphPermission.METAMORPH_MONSTER,
            MorphPermission.METAMORPH_ABERRATION
        ), MorphPermissionResolver.resolve(metamorph));
    }

    static void morphClassificationUsesOverridesBeforeSpawnCategoryFallback() {
        var defaults = java.util.Map.of(
            "minecraft:parrot", MorphFormCategory.NATURAL_FLYING,
            "minecraft:villager", MorphFormCategory.HUMANOID
        );
        var blacklist = java.util.Set.of("minecraft:ender_dragon");

        eq(MorphFormCategory.NATURAL_FLYING, MorphClassificationPolicy.classify("minecraft:parrot", "creature", defaults));
        eq(MorphFormCategory.HUMANOID, MorphClassificationPolicy.classify("minecraft:villager", "misc", defaults));
        eq(MorphFormCategory.NATURAL_LAND, MorphClassificationPolicy.classify("modded:deer", "creature", defaults));
        eq(MorphFormCategory.NATURAL_AQUATIC, MorphClassificationPolicy.classify("modded:fish", "water_ambient", defaults));
        eq(MorphFormCategory.MONSTER, MorphClassificationPolicy.classify("modded:horror", "monster", defaults));
        eq(MorphFormCategory.TECHNICAL, MorphClassificationPolicy.classify("modded:projectile", "misc", defaults));
        eq(true, MorphClassificationPolicy.describe("minecraft:ender_dragon", "monster", defaults, blacklist).explicitlyBlacklisted());
    }

    static void apothicContractsModifyRealSocketsAndGemPower() {
        var sockets = java.util.List.of(
            new GemSocketModifier("node:gem_socket_1", 1),
            new GemSocketModifier("node:gem_socket_2", 2)
        );
        eq(5, ApothicIntegrationPolicy.resolveSockets(2, sockets));
        eq(16, ApothicIntegrationPolicy.resolveSockets(15, sockets));

        var power = java.util.List.of(
            new GemPowerModifier("node:gem_mastery", 0.20),
            new GemPowerModifier("class:artificer", 0.10)
        );
        close(1.32, ApothicIntegrationPolicy.resolveGemPowerMultiplier(power));

        var catalog = CanonicalStatCatalog.defaults();
        eq("apothic_attributes:crit_chance", catalog.resolve("crit_chance").id());
        eq("apothic_attributes:mining_speed", catalog.resolve("apothic_attributes:mining_speed").id());
        eq("apotheosis:gem_power", catalog.resolve("gem_power").id());
    }

    static void progressionServiceConnectsXpLevelsAndBossPoints() {
        var curve = CharacterLevelCurve.defaultCurve();
        var state = ProgressionState.empty();
        var toLevelFour = new CharacterXpAward("test:quest", curve.xpRequiredForLevel(4), java.util.Set.of(ProgressionDomain.SURVIVAL));
        state = ProgressionService.applyXp(state, toLevelFour, curve);
        eq(4, state.characterProgress(curve).level());
        eq(3, state.passivePoints().earned(PassivePointSource.LEVEL));

        long delta = curve.xpRequiredForLevel(6) - state.totalCharacterXp();
        state = ProgressionService.applyXp(state, new CharacterXpAward("test:boss_xp", delta, java.util.Set.of(ProgressionDomain.MARTIAL)), curve);
        eq(6, state.characterProgress(curve).level());
        eq(5, state.passivePoints().earned(PassivePointSource.LEVEL));

        var bossDef = BossRewardRegistry.defaults().resolveForNamespace("cataclysm");
        var first = ProgressionService.creditBoss(state, "cataclysm:ignis", bossDef);
        eq(5, first.pointsAwarded());
        eq(5, first.state().passivePoints().earned(PassivePointSource.BOSS));
        var repeated = ProgressionService.creditBoss(first.state(), "cataclysm:ignis", bossDef);
        eq(0, repeated.pointsAwarded());
        eq(5, repeated.state().passivePoints().earned(PassivePointSource.BOSS));
    }

    static void postClassSpecializationsRequireClassMasteryAndGateway() {
        var fire = new SpecializationDefinition(
            "irons_fire",
            java.util.Set.of("arcanist", "spellblade", "technomancer"),
            java.util.Map.of("irons:fire", 100),
            java.util.Set.of("gateway:irons_fire")
        );
        var investment = InvestmentState.of(java.util.List.of(
            new NodeInvestment("fire_gateway", java.util.Map.of(ProgressionDomain.ARCANE, 3), java.util.Set.of("gateway:irons_fire"))
        ));

        var missingClass = SpecializationResolver.evaluate(java.util.Set.of("warrior"), MasteryState.of(java.util.Map.of("irons:fire", 100)), investment, fire);
        eq(false, missingClass.unlockable());
        eq(true, missingClass.missingEligibleClass());

        var missingMastery = SpecializationResolver.evaluate(java.util.Set.of("arcanist"), MasteryState.of(java.util.Map.of("irons:fire", 99)), investment, fire);
        eq(false, missingMastery.unlockable());
        eq(java.util.Map.of("irons:fire", 1), missingMastery.missingMasteryExperience());

        var unlocked = SpecializationResolver.evaluate(java.util.Set.of("arcanist"), MasteryState.of(java.util.Map.of("irons:fire", 100)), investment, fire);
        eq(true, unlocked.unlockable());
        eq(java.util.Set.of(), unlocked.missingTags());
    }

    static void warlockPactsUseExclusiveChoiceCapacityByDefault() {
        var blade = new ClassChoiceDefinition("warlock:pact_blade", "warlock", "warlock:pact", 1);
        var familiar = new ClassChoiceDefinition("warlock:pact_familiar", "warlock", "warlock:pact", 1);
        var state = ClassChoiceState.empty();

        eq(false, ClassChoicePolicy.canSelect(state, blade, java.util.Set.of("arcanist"), 1));
        eq(true, ClassChoicePolicy.canSelect(state, blade, java.util.Set.of("warlock"), 1));
        state = ClassChoicePolicy.select(state, blade, java.util.Set.of("warlock"), 1);
        eq(java.util.Set.of("warlock:pact_blade"), state.selectedInGroup("warlock:pact"));
        eq(false, ClassChoicePolicy.canSelect(state, familiar, java.util.Set.of("warlock"), 1));
        eq(true, ClassChoicePolicy.canSelect(state, familiar, java.util.Set.of("warlock"), 2));
        state = ClassChoicePolicy.select(state, familiar, java.util.Set.of("warlock"), 2);
        eq(java.util.Set.of("warlock:pact_blade", "warlock:pact_familiar"), state.selectedInGroup("warlock:pact"));
    }

    static void classChoicesCanBeClearedForSafePactRespec() {
        var state = ClassChoiceState.of(Map.of(
            "warlock:pact", java.util.Set.of("warlock:pact_blade"),
            "other:choice", java.util.Set.of("other:value")
        ));
        var cleared = state.withoutSelection("warlock:pact", "warlock:pact_blade");
        eq(java.util.Set.of(), cleared.selectedInGroup("warlock:pact"));
        eq(java.util.Set.of("other:value"), cleared.selectedInGroup("other:choice"));
    }

    static void unlockingAClassPersistsItAndSpendsOnlyAbnormalBridgePoints() {
        var triads = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, java.util.List.of(3, 3, 3),
            ProgressionDomain.MINING, java.util.List.of(3, 3, 3),
            ProgressionDomain.VITALITY, java.util.List.of(3, 3, 3),
            ProgressionDomain.HEALING, java.util.List.of(3, 3, 3)
        ));
        var state = ProgressionState.empty().withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 20));
        var geomancer = new ClassUnlockDefinition("geomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING), false, 10);
        var unlocked = ProgressionService.unlockClass(state, triads, geomancer);
        eq(true, unlocked.unlockedNow());
        eq(10, unlocked.bridgePointsSpent());
        eq(true, unlocked.state().classProgression().isUnlocked("geomancer"));
        eq(10, unlocked.state().passivePoints().available());

        var repeated = ProgressionService.unlockClass(unlocked.state(), triads, geomancer);
        eq(false, repeated.unlockedNow());
        eq(0, repeated.bridgePointsSpent());
        eq(10, repeated.state().passivePoints().available());

        var paladin = new ClassUnlockDefinition("paladin", java.util.Set.of(ProgressionDomain.VITALITY, ProgressionDomain.HEALING), true, 0);
        var natural = ProgressionService.unlockClass(repeated.state(), triads, paladin);
        eq(true, natural.unlockedNow());
        eq(0, natural.bridgePointsSpent());
        eq(10, natural.state().passivePoints().available());
    }

    static void progressionSnapshotRoundTripsCompleteBuildState() {
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty()
                .award(PassivePointSource.LEVEL, 12)
                .award(PassivePointSource.BOSS, 5)
                .spend(9))
            .withMastery(MasteryState.of(java.util.Map.of("irons:fire", 137, "ars:projectile", 42)))
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("arcanist", "warlock")))
            .withClassChoices(ClassChoiceState.of(java.util.Map.of("warlock:pact", java.util.Set.of("warlock:pact_grimoire"))))
            .withSpecializations(SpecializationProgressionState.of(java.util.Set.of("irons_fire")));
        state = ProgressionService.applyXp(state, new CharacterXpAward("test:persistence", 12345, java.util.Set.of(ProgressionDomain.ARCANE)), CharacterLevelCurve.defaultCurve());
        state = ProgressionService.creditBoss(state, "cataclysm:ignis", BossRewardRegistry.defaults().resolveForNamespace("cataclysm")).state();

        byte[] encoded = ProgressionStateCodec.encode(state);
        var decoded = ProgressionStateCodec.decode(encoded);

        eq(state.totalCharacterXp(), decoded.totalCharacterXp());
        eq(state.passivePoints().earnedBySource(), decoded.passivePoints().earnedBySource());
        eq(state.passivePoints().spent(), decoded.passivePoints().spent());
        eq(state.bossProgress().creditedRewardKeys(), decoded.bossProgress().creditedRewardKeys());
        eq(state.classProgression().unlockedClassIds(), decoded.classProgression().unlockedClassIds());
        eq(state.mastery().experience(), decoded.mastery().experience());
        eq(state.classChoices().selections(), decoded.classChoices().selections());
        eq(state.specializations().unlockedSpecializationIds(), decoded.specializations().unlockedSpecializationIds());
    }

    static void bossRewardKeysUseStableBoundedIdentities() {
        eq("cataclysm:ignis", BossRewardKeyPolicy.resolve(new BossIdentity("cataclysm:ignis", null)));
        eq("minecraft:ender_dragon", BossRewardKeyPolicy.resolve(new BossIdentity("minecraft:ender_dragon", "")));
        eq("apotheosis:boss_tier/rare", BossRewardKeyPolicy.resolve(new BossIdentity("minecraft:zombie", "apotheosis:boss_tier/rare")));

        boolean uuidRejected = false;
        try {
            BossRewardKeyPolicy.resolve(new BossIdentity("minecraft:zombie", "550e8400-e29b-41d4-a716-446655440000"));
        } catch (IllegalArgumentException expected) {
            uuidRejected = true;
        }
        eq(true, uuidRejected);
    }

    static void close(double expected, double actual) {
        if (Math.abs(expected - actual) > 1e-9) throw new AssertionError("expected=" + expected + " actual=" + actual);
    }

    static void gameplayXpPolicyScalesCombatWithoutUnboundedBossXp() {
        var zombie = GameplayXpPolicy.combatKill("minecraft:zombie", 20.0, false);
        eq(25L, zombie.amount());
        eq(java.util.Set.of(ProgressionDomain.MARTIAL), zombie.attributedDomains());

        var stronger = GameplayXpPolicy.combatKill("minecraft:ravager", 100.0, false);
        eq(true, stronger.amount() > zombie.amount());
        eq(true, stronger.amount() <= 150L);

        var boss = GameplayXpPolicy.combatKill("cataclysm:ignis", 9999.0, true);
        eq(2000L, boss.amount());
        eq(true, boss.sourceId().startsWith("combat:boss/"));

        boolean rejected = false;
        try { GameplayXpPolicy.combatKill("minecraft:zombie", 0.0, false); }
        catch (IllegalArgumentException expected) { rejected = true; }
        eq(true, rejected);
    }

    static void apothicBossRewardKeysStayFiniteAcrossRandomSpawns() {
        eq("apotheosis:elite/apotheosis/brutal",
            ApothicBossRewardKeyPolicy.elite("apotheosis:brutal", "minecraft:zombie"));
        eq("apotheosis:invader/apotheosis/mythic",
            ApothicBossRewardKeyPolicy.invader("apotheosis:mythic"));
        eq("apotheosis:elite_entity/minecraft/zombie",
            ApothicBossRewardKeyPolicy.elite(null, "minecraft:zombie"));

        boolean rejected = false;
        try { ApothicBossRewardKeyPolicy.invader("550e8400-e29b-41d4-a716-446655440000"); }
        catch (IllegalArgumentException expected) { rejected = true; }
        eq(true, rejected);
    }

    static void finalTriadsPersistAndLegacySnapshotsMigrate() {
        var triads = FinalTriadProgress.of(Map.of(
            ProgressionDomain.ARCANE, java.util.List.of(3, 2, 1),
            ProgressionDomain.HEALING, java.util.List.of(1, 1, 1)
        ));
        var state = ProgressionState.empty().withFinalTriads(triads);
        var decoded = ProgressionStateCodec.decode(ProgressionStateCodec.encode(state));
        eq(java.util.List.of(3, 2, 1), decoded.finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(4, ProgressionStateCodec.CURRENT_VERSION);

        try {
            var bytes = new java.io.ByteArrayOutputStream();
            try (var out = new java.io.DataOutputStream(bytes)) {
                out.writeInt(1); // legacy codec version
                out.writeLong(0L);
                out.writeInt(0); // ledger earned sources
                out.writeInt(0); // ledger spent
                out.writeInt(0); // bosses
                out.writeInt(0); // classes
                out.writeInt(0); // mastery
                out.writeInt(0); // choice groups
                out.writeInt(0); // specializations
            }
            var migrated = ProgressionStateCodec.decode(bytes.toByteArray());
            eq(java.util.List.of(0, 0, 0), migrated.finalTriads().ranks(ProgressionDomain.ARCANE));
        } catch (java.io.IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    static void finalTriadInvestmentSpendsPointsTransactionally() {
        var state = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 4));

        state = ProgressionService.investFinalTriadRank(state, ProgressionDomain.ARCANE, 0);
        state = ProgressionService.investFinalTriadRank(state, ProgressionDomain.ARCANE, 0);
        state = ProgressionService.investFinalTriadRank(state, ProgressionDomain.ARCANE, 0);
        eq(java.util.List.of(3, 0, 0), state.finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(1, state.passivePoints().available());

        boolean rankCapRejected = false;
        try { ProgressionService.investFinalTriadRank(state, ProgressionDomain.ARCANE, 0); }
        catch (IllegalArgumentException expected) { rankCapRejected = true; }
        eq(true, rankCapRejected);
        eq(1, state.passivePoints().available());

        boolean badSlotRejected = false;
        try { ProgressionService.investFinalTriadRank(state, ProgressionDomain.ARCANE, 3); }
        catch (IllegalArgumentException expected) { badSlotRejected = true; }
        eq(true, badSlotRejected);
    }

    static void classUnlockUsesPersistedFinalTriads() {
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 20))
            .withFinalTriads(FinalTriadProgress.of(Map.of(
                ProgressionDomain.ARCANE, java.util.List.of(3, 3, 3),
                ProgressionDomain.ENGINEERING, java.util.List.of(3, 3, 3)
            )));
        var technomancer = new ClassUnlockDefinition(
            "technomancer",
            java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.ENGINEERING),
            true,
            0
        );
        var result = ProgressionService.unlockClass(state, technomancer);
        eq(true, result.unlockedNow());
        eq(true, result.state().classProgression().isUnlocked("technomancer"));
        eq(20, result.state().passivePoints().available());
    }

    static void passiveNodePurchasesAreServerAuthoritativeAndRanked() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("start", "arcane_capacity"),
            new SkillGraph.Edge("arcane_capacity", "mana_flow")
        ));
        var state = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 6));

        var start = new NodePurchaseDefinition("start", 1, 1, true);
        var capacity = new NodePurchaseDefinition("arcane_capacity", 3, 1, false);
        var manaFlow = new NodePurchaseDefinition("mana_flow", 1, 2, false);

        state = ProgressionService.purchaseNode(state, graph, start, true);
        eq(1, state.passiveNodes().rank("start"));
        eq(5, state.passivePoints().available());

        state = ProgressionService.purchaseNode(state, graph, capacity, true);
        state = ProgressionService.purchaseNode(state, graph, capacity, true);
        state = ProgressionService.purchaseNode(state, graph, capacity, true);
        eq(3, state.passiveNodes().rank("arcane_capacity"));
        eq(2, state.passivePoints().available());

        boolean rankCapRejected = false;
        try { ProgressionService.purchaseNode(state, graph, capacity, true); }
        catch (IllegalArgumentException expected) { rankCapRejected = true; }
        eq(true, rankCapRejected);

        var disconnectedState = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 3));
        boolean disconnectedRejected = false;
        try { ProgressionService.purchaseNode(disconnectedState, graph, manaFlow, true); }
        catch (IllegalArgumentException expected) { disconnectedRejected = true; }
        eq(true, disconnectedRejected);

        boolean requirementRejected = false;
        try { ProgressionService.purchaseNode(state, graph, manaFlow, false); }
        catch (IllegalArgumentException expected) { requirementRejected = true; }
        eq(true, requirementRejected);

        state = ProgressionService.purchaseNode(state, graph, manaFlow, true);
        eq(1, state.passiveNodes().rank("mana_flow"));
        eq(0, state.passivePoints().available());
    }

    static void passiveNodeRanksPersistAndV2SnapshotsMigrate() {
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 5).spend(3))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "rpgskilltree:start", 1,
                "rpgskilltree:arcane_capacity", 2
            )));
        var decoded = ProgressionStateCodec.decode(ProgressionStateCodec.encode(state));
        eq(1, decoded.passiveNodes().rank("rpgskilltree:start"));
        eq(2, decoded.passiveNodes().rank("rpgskilltree:arcane_capacity"));
        eq(4, ProgressionStateCodec.CURRENT_VERSION);

        try {
            var bytes = new java.io.ByteArrayOutputStream();
            try (var out = new java.io.DataOutputStream(bytes)) {
                out.writeInt(2);
                out.writeLong(0L);
                out.writeInt(0); // ledger sources
                out.writeInt(0); // spent
                out.writeInt(0); // bosses
                out.writeInt(0); // classes
                out.writeInt(0); // mastery
                out.writeInt(0); // choices
                out.writeInt(0); // specializations
                out.writeInt(0); // final triad domains
            }
            var migrated = ProgressionStateCodec.decode(bytes.toByteArray());
            eq(0, migrated.passiveNodes().rank("rpgskilltree:start"));
        } catch (java.io.IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    static void passiveNodeRespecCascadesOnlyOrphanedBranchesAndRefundsExactCosts() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("start", "a"),
            new SkillGraph.Edge("a", "b"),
            new SkillGraph.Edge("b", "c"),
            new SkillGraph.Edge("a", "d"),
            new SkillGraph.Edge("d", "c")
        ));
        var definitions = Map.of(
            "start", new NodePurchaseDefinition("start", 1, 1, true),
            "a", new NodePurchaseDefinition("a", 2, 1, false),
            "b", new NodePurchaseDefinition("b", 1, 2, false),
            "c", new NodePurchaseDefinition("c", 1, 3, false),
            "d", new NodePurchaseDefinition("d", 1, 1, false)
        );
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 20).spend(9))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "start", 1,
                "a", 2,
                "b", 1,
                "c", 1,
                "d", 1
            )));

        var oneRank = ProgressionService.respecNode(state, graph, definitions, "a");
        eq(1, oneRank.state().passiveNodes().rank("a"));
        eq(1, oneRank.pointsRefunded());
        eq(Map.of("a", 1), oneRank.removedRanks());
        eq(true, oneRank.state().passiveNodes().learned("c"));

        var removeB = ProgressionService.respecNode(oneRank.state(), graph, definitions, "b");
        eq(false, removeB.state().passiveNodes().learned("b"));
        eq(true, removeB.state().passiveNodes().learned("c"));
        eq(2, removeB.pointsRefunded());
        eq(Map.of("b", 1), removeB.removedRanks());

        var removeD = ProgressionService.respecNode(removeB.state(), graph, definitions, "d");
        eq(false, removeD.state().passiveNodes().learned("d"));
        eq(false, removeD.state().passiveNodes().learned("c"));
        eq(4, removeD.pointsRefunded());
        eq(Map.of("d", 1, "c", 1), removeD.removedRanks());
        eq(18, removeD.state().passivePoints().available());

        boolean unknownDefinitionRejected = false;
        try {
            ProgressionService.respecNode(
                state, graph, Map.of("start", definitions.get("start")), "b");
        } catch (IllegalArgumentException expected) {
            unknownDefinitionRejected = true;
        }
        eq(true, unknownDefinitionRejected);
    }

    static void passiveFinalTriadNodesDrivePersistedTriadsAndClassEligibility() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("start", "arcane_final_0"),
            new SkillGraph.Edge("start", "arcane_final_1"),
            new SkillGraph.Edge("start", "arcane_final_2")
        ));
        var definitions = Map.of(
            "start", new NodePurchaseDefinition("start", 1, 1, true),
            "arcane_final_0", new NodePurchaseDefinition("arcane_final_0", 3, 1, false, ProgressionDomain.ARCANE, 0),
            "arcane_final_1", new NodePurchaseDefinition("arcane_final_1", 3, 1, false, ProgressionDomain.ARCANE, 1),
            "arcane_final_2", new NodePurchaseDefinition("arcane_final_2", 3, 1, false, ProgressionDomain.ARCANE, 2)
        );
        var state = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 20));
        state = ProgressionService.purchaseNode(state, graph, definitions.get("start"), true);
        for (int slot = 0; slot < 3; slot++) {
            var definition = definitions.get("arcane_final_" + slot);
            for (int rank = 0; rank < 3; rank++) {
                state = ProgressionService.purchaseNode(state, graph, definition, true);
            }
        }
        eq(java.util.List.of(3, 3, 3), state.finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(true, state.finalTriads().complete(ProgressionDomain.ARCANE));

        var arcanist = new ClassUnlockDefinition(
            "arcanist", java.util.Set.of(ProgressionDomain.ARCANE), true, 0);
        eq(true, ClassUnlockResolver.evaluate(state.finalTriads(), arcanist, state.passivePoints().available()).unlockable());

        var respec = ProgressionService.respecNode(state, graph, definitions, "arcane_final_1");
        eq(java.util.List.of(3, 2, 3), respec.state().finalTriads().ranks(ProgressionDomain.ARCANE));
        eq(false, respec.state().finalTriads().complete(ProgressionDomain.ARCANE));
        eq(false, ClassUnlockResolver.evaluate(
            respec.state().finalTriads(), arcanist, respec.state().passivePoints().available()).unlockable());
    }

    static void automaticClassesUnlockOnlyForZeroCostEligibleConfluences() {
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 40))
            .withFinalTriads(FinalTriadProgress.of(Map.of(
                ProgressionDomain.ARCANE, java.util.List.of(3, 3, 3),
                ProgressionDomain.ENGINEERING, java.util.List.of(3, 3, 3),
                ProgressionDomain.MINING, java.util.List.of(3, 3, 3)
            )));
        var arcanist = new ClassUnlockDefinition(
            "arcanist", java.util.Set.of(ProgressionDomain.ARCANE), true, 0);
        var technomancer = new ClassUnlockDefinition(
            "technomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.ENGINEERING), true, 0);
        var geomancer = new ClassUnlockDefinition(
            "geomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING), false, 10);

        var result = ProgressionService.unlockAutomaticClasses(
            state, java.util.List.of(geomancer, technomancer, arcanist));
        eq(java.util.Set.of("arcanist", "technomancer"), result.newlyUnlocked());
        eq(true, result.state().classProgression().isUnlocked("arcanist"));
        eq(true, result.state().classProgression().isUnlocked("technomancer"));
        eq(false, result.state().classProgression().isUnlocked("geomancer"));
        eq(40, result.state().passivePoints().available());

        var repeated = ProgressionService.unlockAutomaticClasses(
            result.state(), java.util.List.of(geomancer, technomancer, arcanist));
        eq(java.util.Set.of(), repeated.newlyUnlocked());
    }

    static void nodeAttributeEffectsScaleWithPersistedRanks() {
        var effects = java.util.List.of(
            new NodeAttributeEffect(
                "rpgskilltree:arcane_capacity",
                "rpgskilltree:arcane_capacity",
                "irons_spellbooks:max_mana",
                ModifierOperation.ADD_FLAT,
                25.0
            ),
            new NodeAttributeEffect(
                "rpgskilltree:arcane_power",
                "rpgskilltree:arcane_power",
                "irons_spellbooks:spell_power",
                ModifierOperation.ADD_PERCENT_BASE,
                0.04
            ),
            new NodeAttributeEffect(
                "rpgskilltree:unused",
                "rpgskilltree:unused",
                "minecraft:armor",
                ModifierOperation.ADD_FLAT,
                2.0
            )
        );
        var progress = PassiveNodeProgress.of(Map.of(
            "rpgskilltree:arcane_capacity", 3,
            "rpgskilltree:arcane_power", 2
        ));
        var resolved = NodeEffectResolver.resolveAttributes(progress, effects);
        eq(2, resolved.size());
        var byId = resolved.stream().collect(java.util.stream.Collectors.toMap(ResolvedNodeAttributeEffect::effectId, e -> e));
        eq(75.0, byId.get("rpgskilltree:arcane_capacity").amount());
        eq(ModifierOperation.ADD_FLAT, byId.get("rpgskilltree:arcane_capacity").operation());
        eq(0.08, byId.get("rpgskilltree:arcane_power").amount());
        eq("irons_spellbooks:spell_power", byId.get("rpgskilltree:arcane_power").attributeId());

        boolean overrideRejected = false;
        try {
            new NodeAttributeEffect(
                "rpgskilltree:bad", "rpgskilltree:bad", "minecraft:armor", ModifierOperation.OVERRIDE, 1.0);
        } catch (IllegalArgumentException expected) {
            overrideRejected = true;
        }
        eq(true, overrideRejected);
    }

    static void treeDisplayProjectionMatchesAuthoritativePurchaseState() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("start", "a"),
            new SkillGraph.Edge("a", "b")
        ));
        var definitions = Map.of(
            "start", new NodePurchaseDefinition("start", 1, 1, true),
            "a", new NodePurchaseDefinition("a", 3, 2, false),
            "b", new NodePurchaseDefinition("b", 1, 5, false)
        );
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 5))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("start", 1, "a", 2)));

        var projected = TreeDisplayProjector.project(state, graph, definitions);
        eq(5, projected.availablePoints());
        eq(1, projected.nodes().get("start").rank());
        eq(false, projected.nodes().get("start").canPurchase());
        eq(true, projected.nodes().get("start").canRespec());
        eq(2, projected.nodes().get("a").rank());
        eq(3, projected.nodes().get("a").maxRank());
        eq(true, projected.nodes().get("a").canPurchase());
        eq(true, projected.nodes().get("b").canPurchase());

        var poor = state.withPassivePoints(PassivePointLedger.of(Map.of(PassivePointSource.LEVEL, 5), 4));
        var poorProjected = TreeDisplayProjector.project(poor, graph, definitions);
        eq(1, poorProjected.availablePoints());
        eq(false, poorProjected.nodes().get("a").canPurchase());
        eq(false, poorProjected.nodes().get("b").canPurchase());

        var disconnected = ProgressionState.empty().withPassivePoints(
            PassivePointLedger.empty().award(PassivePointSource.LEVEL, 10));
        var disconnectedProjected = TreeDisplayProjector.project(disconnected, graph, definitions);
        eq(true, disconnectedProjected.nodes().get("start").canPurchase());
        eq(false, disconnectedProjected.nodes().get("a").canPurchase());
        eq(false, disconnectedProjected.nodes().get("b").canPurchase());
    }

    static void explorationDiscoveriesAwardXpOnlyOnceAndPersist() {
        var state = ProgressionState.empty();
        var plains = GameplayXpPolicy.biomeDiscovery("minecraft:plains");
        var first = ProgressionService.creditDiscovery(
            state, "biome:minecraft:plains", plains, CharacterLevelCurve.defaultCurve());
        eq(true, first.firstDiscovery());
        eq(25L, first.xpAwarded());
        eq(25L, first.state().totalCharacterXp());
        eq(true, first.state().discoveries().contains("biome:minecraft:plains"));

        var repeated = ProgressionService.creditDiscovery(
            first.state(), "biome:minecraft:plains", plains, CharacterLevelCurve.defaultCurve());
        eq(false, repeated.firstDiscovery());
        eq(0L, repeated.xpAwarded());
        eq(25L, repeated.state().totalCharacterXp());

        var nether = ProgressionService.creditDiscovery(
            repeated.state(),
            "dimension:minecraft:the_nether",
            GameplayXpPolicy.dimensionDiscovery("minecraft:the_nether"),
            CharacterLevelCurve.defaultCurve());
        eq(true, nether.firstDiscovery());
        eq(100L, nether.xpAwarded());
        eq(125L, nether.state().totalCharacterXp());

        var restored = ProgressionStateCodec.decode(ProgressionStateCodec.encode(nether.state()));
        eq(nether.state().discoveries(), restored.discoveries());
        eq(4, ProgressionStateCodec.CURRENT_VERSION);
    }

    static void oreMiningXpRewardsTaggedOresWithoutExplodingProgression() {
        var common = GameplayXpPolicy.oreMined("minecraft:iron_ore", false);
        eq(8L, common.amount());
        eq(java.util.Set.of(ProgressionDomain.MINING), common.attributedDomains());
        eq("mining:ore/minecraft:iron_ore", common.sourceId());

        var rare = GameplayXpPolicy.oreMined("minecraft:diamond_ore", true);
        eq(20L, rare.amount());
        eq(java.util.Set.of(ProgressionDomain.MINING), rare.attributedDomains());

        boolean rejected = false;
        try { GameplayXpPolicy.oreMined("", false); }
        catch (IllegalArgumentException expected) { rejected = true; }
        eq(true, rejected);
    }

    static void automaticClassesReconcileWhenFinalTriadsAreRespecced() {
        var arcanist = new ClassUnlockDefinition(
            "arcanist", java.util.Set.of(ProgressionDomain.ARCANE), true, 0);
        var technomancer = new ClassUnlockDefinition(
            "technomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.ENGINEERING), true, 0);
        var geomancer = new ClassUnlockDefinition(
            "geomancer", java.util.Set.of(ProgressionDomain.ARCANE, ProgressionDomain.MINING), false, 10);

        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 30))
            .withFinalTriads(FinalTriadProgress.of(Map.of(
                ProgressionDomain.ARCANE, java.util.List.of(3, 3, 3),
                ProgressionDomain.ENGINEERING, java.util.List.of(3, 3, 3)
            )))
            .withClassProgression(ClassProgressionState.of(java.util.Set.of(
                "arcanist", "technomancer", "geomancer"
            )));

        var stillEligible = ProgressionService.reconcileAutomaticClasses(
            state, java.util.List.of(arcanist, technomancer, geomancer));
        eq(java.util.Set.of(), stillEligible.removed());
        eq(true, stillEligible.state().classProgression().isUnlocked("arcanist"));
        eq(true, stillEligible.state().classProgression().isUnlocked("technomancer"));
        eq(true, stillEligible.state().classProgression().isUnlocked("geomancer"));

        var brokenArcane = stillEligible.state().withFinalTriads(
            stillEligible.state().finalTriads().decrease(ProgressionDomain.ARCANE, 2, 1));
        var reconciled = ProgressionService.reconcileAutomaticClasses(
            brokenArcane, java.util.List.of(arcanist, technomancer, geomancer));
        eq(java.util.Set.of("arcanist", "technomancer"), reconciled.removed());
        eq(false, reconciled.state().classProgression().isUnlocked("arcanist"));
        eq(false, reconciled.state().classProgression().isUnlocked("technomancer"));
        eq(true, reconciled.state().classProgression().isUnlocked("geomancer"));
    }

    static void nodeAccessRequirementsAreEvaluatedFromAuthoritativeState() {
        var curve = CharacterLevelCurve.defaultCurve();
        var state = ProgressionState.empty()
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("technomancer")))
            .withMastery(MasteryState.of(Map.of("create:engineering", 120)))
            .withSpecializations(SpecializationProgressionState.of(java.util.Set.of("arcane_machinist")));
        state = ProgressionService.applyXp(
            state,
            new CharacterXpAward("test:level", curve.xpRequiredForLevel(20), java.util.Set.of()),
            curve
        );

        var requirement = new NodeAccessRequirement(
            20,
            java.util.Set.of("technomancer"),
            Map.of("create:engineering", 100),
            java.util.Set.of("arcane_machinist")
        );
        eq(true, NodeAccessResolver.satisfied(state, requirement, curve));

        eq(false, NodeAccessResolver.satisfied(
            state, new NodeAccessRequirement(21, java.util.Set.of(), Map.of(), java.util.Set.of()), curve));
        eq(false, NodeAccessResolver.satisfied(
            state, new NodeAccessRequirement(1, java.util.Set.of("paladin"), Map.of(), java.util.Set.of()), curve));
        eq(false, NodeAccessResolver.satisfied(
            state, new NodeAccessRequirement(1, java.util.Set.of(), Map.of("create:engineering", 121), java.util.Set.of()), curve));
        eq(false, NodeAccessResolver.satisfied(
            state, new NodeAccessRequirement(1, java.util.Set.of(), Map.of(), java.util.Set.of("missing_spec")), curve));
        eq(true, NodeAccessResolver.satisfied(state, NodeAccessRequirement.none(), curve));
    }

    static void nodeAccessRequirementsCanRequireClassChoices() {
        var curve = CharacterLevelCurve.defaultCurve();
        var requirement = new NodeAccessRequirement(
            1,
            java.util.Set.of("warlock"),
            Map.of(),
            java.util.Set.of(),
            java.util.Set.of("warlock:pact_blade")
        );
        var noPact = ProgressionState.empty()
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("warlock")));
        eq(false, NodeAccessResolver.satisfied(noPact, requirement, curve));

        var blade = noPact.withClassChoices(ClassChoiceState.of(
            Map.of("warlock:pact", java.util.Set.of("warlock:pact_blade"))
        ));
        eq(true, NodeAccessResolver.satisfied(blade, requirement, curve));

        var grimoire = noPact.withClassChoices(ClassChoiceState.of(
            Map.of("warlock:pact", java.util.Set.of("warlock:pact_grimoire"))
        ));
        eq(false, NodeAccessResolver.satisfied(grimoire, requirement, curve));
    }

    static void clearingClassChoiceInvalidatesChoiceGatedNodesAndRefunds() {
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("root", "blade_1"),
            new SkillGraph.Edge("blade_1", "blade_2")
        ));
        var definitions = Map.of(
            "root", new NodePurchaseDefinition("root", 1, 1, true, null, -1),
            "blade_1", new NodePurchaseDefinition("blade_1", 1, 1, false, null, -1),
            "blade_2", new NodePurchaseDefinition("blade_2", 1, 1, false, null, -1)
        );
        var requirements = Map.of(
            "root", new NodeAccessRequirement(1, java.util.Set.of("warlock"), Map.of(), java.util.Set.of()),
            "blade_1", new NodeAccessRequirement(1, java.util.Set.of("warlock"), Map.of(), java.util.Set.of(), java.util.Set.of("warlock:pact_blade")),
            "blade_2", new NodeAccessRequirement(1, java.util.Set.of("warlock"), Map.of(), java.util.Set.of(), java.util.Set.of("warlock:pact_blade"))
        );
        var state = ProgressionState.empty()
            .withClassProgression(ClassProgressionState.of(java.util.Set.of("warlock")))
            .withClassChoices(ClassChoiceState.of(Map.of("warlock:pact", java.util.Set.of("warlock:pact_blade"))))
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 5).spend(3))
            .withPassiveNodes(PassiveNodeProgress.of(Map.of("root",1,"blade_1",1,"blade_2",1)));

        var cleared = state.withClassChoices(
            state.classChoices().withoutSelection("warlock:pact", "warlock:pact_blade")
        );
        var result = ProgressionService.reconcileInvalidNodes(
            cleared, graph, definitions, requirements, CharacterLevelCurve.defaultCurve());
        eq(java.util.Set.of("root"), result.state().passiveNodes().learnedNodeIds());
        eq(2, result.pointsRefunded());
        eq(4, result.state().passivePoints().available());
    }

    static void nodeGrantedSpecializationsTrackLiveGatewayRanks() {
        var state = ProgressionState.empty()
            .withPassiveNodes(PassiveNodeProgress.of(Map.of(
                "rpgskilltree:technomancer/create_gateway", 1
            )))
            .withSpecializations(SpecializationProgressionState.of(java.util.Set.of("stale_spec")));

        var grants = java.util.List.of(
            new NodeSpecializationGrant("rpgskilltree:technomancer/create_gateway", "create_kinetics", 1),
            new NodeSpecializationGrant("rpgskilltree:technomancer/ae2_gateway", "ae2_networks", 1)
        );

        var reconciled = ProgressionService.reconcileNodeSpecializations(state, grants);
        eq(java.util.Set.of("stale_spec", "create_kinetics"), reconciled.specializations().unlockedSpecializationIds());

        var removedGateway = reconciled.withPassiveNodes(
            reconciled.passiveNodes().without(java.util.Set.of("rpgskilltree:technomancer/create_gateway")));
        var afterRespec = ProgressionService.reconcileNodeSpecializations(removedGateway, grants);
        eq(java.util.Set.of("stale_spec"), afterRespec.specializations().unlockedSpecializationIds());

        boolean invalidRankRejected = false;
        try { new NodeSpecializationGrant("node", "spec", 0); }
        catch (IllegalArgumentException expected) { invalidRankRejected = true; }
        eq(true, invalidRankRejected);
    }

    static void treeDisplayProjectionRespectsAuthoritativeRequirements() {
        var curve = CharacterLevelCurve.defaultCurve();
        var state = ProgressionState.empty()
            .withPassivePoints(PassivePointLedger.empty().award(PassivePointSource.LEVEL, 5))
            .withPassiveNodes(PassiveNodeProgress.of(java.util.Map.of("root", 1)));
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("root", "locked")
        ));
        var definitions = java.util.Map.of(
            "root", new NodePurchaseDefinition("root", 1, 1, true),
            "locked", new NodePurchaseDefinition("locked", 1, 1, false)
        );
        var requirements = java.util.Map.of(
            "root", NodeAccessRequirement.none(),
            "locked", new NodeAccessRequirement(1, java.util.Set.of("technomancer"), java.util.Map.of(), java.util.Set.of())
        );

        var before = TreeDisplayProjector.project(state, graph, definitions, requirements, curve);
        eq(false, before.nodes().get("root").canPurchase());
        eq(false, before.nodes().get("locked").canPurchase());

        var technomancer = state.withClassProgression(ClassProgressionState.of(java.util.Set.of("technomancer")));
        var after = TreeDisplayProjector.project(technomancer, graph, definitions, requirements, curve);
        eq(true, after.nodes().get("locked").canPurchase());
    }

    static void invalidClassSubtreesAreRemovedAndRefunded() {
        var curve = CharacterLevelCurve.defaultCurve();
        var graph = SkillGraph.undirected(java.util.List.of(
            new SkillGraph.Edge("main_root", "main_child"),
            new SkillGraph.Edge("tech_root", "tech_child")
        ));
        var definitions = java.util.Map.of(
            "main_root", new NodePurchaseDefinition("main_root", 1, 1, true),
            "main_child", new NodePurchaseDefinition("main_child", 1, 1, false),
            "tech_root", new NodePurchaseDefinition("tech_root", 1, 1, true),
            "tech_child", new NodePurchaseDefinition("tech_child", 1, 1, false)
        );
        var requirements = java.util.Map.of(
            "main_root", NodeAccessRequirement.none(),
            "main_child", NodeAccessRequirement.none(),
            "tech_root", new NodeAccessRequirement(1, java.util.Set.of("technomancer"), java.util.Map.of(), java.util.Set.of()),
            "tech_child", new NodeAccessRequirement(1, java.util.Set.of("technomancer"), java.util.Map.of(), java.util.Set.of())
        );
        var ledger = PassivePointLedger.empty().award(PassivePointSource.LEVEL, 10).spend(4);
        var state = ProgressionState.empty()
            .withPassivePoints(ledger)
            .withPassiveNodes(PassiveNodeProgress.of(java.util.Map.of(
                "main_root", 1, "main_child", 1, "tech_root", 1, "tech_child", 1
            )));

        var result = ProgressionService.reconcileInvalidNodes(state, graph, definitions, requirements, curve);
        eq(java.util.Set.of("main_root", "main_child"), result.state().passiveNodes().learnedNodeIds());
        eq(2, result.pointsRefunded());
        eq(8, result.state().passivePoints().available());

        var valid = state.withClassProgression(ClassProgressionState.of(java.util.Set.of("technomancer")));
        var unchanged = ProgressionService.reconcileInvalidNodes(valid, graph, definitions, requirements, curve);
        eq(java.util.Set.of("main_root", "main_child", "tech_root", "tech_child"), unchanged.state().passiveNodes().learnedNodeIds());
        eq(0, unchanged.pointsRefunded());
    }

    static void eq(Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) throw new AssertionError("expected=" + expected + " actual=" + actual);
    }
}
