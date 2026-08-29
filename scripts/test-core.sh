#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
python3 "$ROOT/scripts/verify-ci-generator-drift.py"
OUT="$ROOT/build/core-test-classes"; rm -rf "$OUT"; mkdir -p "$OUT"
mapfile -t SOURCES < <(find "$ROOT/src/main/java/dev/gustavopere/rpgskilltree/core" "$ROOT/src/test/java/dev/gustavopere/rpgskilltree/core" -name '*.java' -print | sort)
javac --release 21 -d "$OUT" "${SOURCES[@]}"
javac --release 21 -cp "$OUT" -d "$OUT" \
  "$ROOT/src/main/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectCatalog.java" \
  "$ROOT/src/test/java/dev/gustavopere/rpgskilltree/runtime/data/NodeEffectCatalogReloadTest.java"
for test in \
 CoreProgressionTest Alpha2ProgressionTest MasteryRuntimeCoreTest MasteryAwardIdempotencyTest PlacedBlockProvenanceTest ArsCompositionClassifierTest EpicFightWeaponCategoryTest ArcaneAccessPolicyTest ProviderIdentityClassTest GoetyMasteryCoreTest MalumMasteryCoreTest IronStudyPolicyTest ArsNativeProgressionPolicyTest EpicFightDepthPolicyTest GoetySoulPolicyTest GoetyCommandPolicyTest EidolonRitualPolicyTest EidolonAlchemyPolicyTest NodeAccessRequirementTest SystemFoundationTest ArchetypeSpecificityScoreTest MorphEcologyPolicyTest ProgressionStateMigrationMatrixTest ProgressionStateV5AllocationFoundationTest PersistedNodeAllocationsCodecTest LegacyNodeAllocationMigrationTest SpecializationReconciliationTest InfiniteProgressionFoundationTest CharacterXpRollbackTest CorePointEconomyTest ProgressionRulesSnapshotTest LegacyProgressionMigrationTest CorePointLedgerCheckpointTest CoreProgressionStateCodecTest CoreProgressionBootstrapTest CoreProgressionSyncStateTest CoreProgressionMutationServiceTest SemanticXpPipelineTest SemanticProgressionServiceTest GameplaySemanticXpPolicyTest ProgressionRulesProviderTest InstallableProgressionRulesProviderTest ProgressionRulesTransitionPolicyTest AttributeRanksTest AttributeRanksPersistenceTest AttributeRankMutationServiceTest AttributeRankCostPolicyProviderTest UnitAttributeRankCostPolicyTest LevelCorePointAwardPolicyTest MainPerkBudgetProgressionTest ProgressionRewardServiceTest CoreProgressionQueryServiceTest QuestProgressionHooksFoundationTest CanonicalPlayerStateFoundationTest CanonicalPlayerAttachmentDataTest CanonicalPlayerQueryServiceTest CanonicalEffectiveStatsFoundationTest WorldEntityLevelFoundationTest TerritoryGridTest NativeAreaThreatCompositionTest RelevantPlayerLevelFoundationTest EntityArchetypeStatScalingTest WorldEntityScalingPipelineTest MobRarityFoundationTest EntityScalingPersistenceTest EntityScalingLifecycleStateTest EntityScalingInitializationServiceTest MobAffixFoundationTest MobAffixPersistenceTest EntityBehaviorFoundationTest EntitySelectionLifecyclePersistenceTest EntityScalingDecisionServiceTest CanonicalProviderBindingFoundationTest CanonicalProviderBindingCatalogTest ProgressionSyncCoalescerTest A0001A0020NotionContractTest A0001A0020CombatPolicyTest A0001A0020CriticalServiceTest A0018RangeCrossingTest A0021A0040NotionContractTest A0021A0040CombatPolicyTest; do
 java -cp "$OUT" "dev.gustavopere.rpgskilltree.core.$test"
done
java -cp "$OUT" dev.gustavopere.rpgskilltree.runtime.data.NodeEffectCatalogReloadTest
python3 "$ROOT/scripts/verify-1211-resources.py"
python3 "$ROOT/scripts/verify-node-effect-diagnostics.py"
python3 "$ROOT/scripts/verify-quest-runtime.py"
python3 "$ROOT/scripts/verify-core-xp-adapters.py"
python3 "$ROOT/scripts/verify-world-scaling-runtime.py"
python3 "$ROOT/scripts/verify-entity-scaling-events.py"
python3 "$ROOT/scripts/verify-canonical-player-runtime.py"
python3 "$ROOT/scripts/verify-dimension-sync.py"
python3 "$ROOT/scripts/verify-progression-services-runtime.py"
python3 "$ROOT/scripts/verify-progression-sync-coalescing.py"
