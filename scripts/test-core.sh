#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
python3 "$ROOT/scripts/verify-ci-generator-drift.py"
OUT="$ROOT/build/core-test-classes"; rm -rf "$OUT"; mkdir -p "$OUT"
mapfile -t SOURCES < <(find "$ROOT/src/main/java/dev/gustavopere/rpgskilltree/core" "$ROOT/src/test/java/dev/gustavopere/rpgskilltree/core" -name '*.java' -print | sort)
javac --release 21 -d "$OUT" "${SOURCES[@]}"
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.Alpha2ProgressionTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MasteryRuntimeCoreTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.PlacedBlockProvenanceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ArsCompositionClassifierTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategoryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ArcaneAccessPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProviderIdentityClassTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.GoetyMasteryCoreTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MalumMasteryCoreTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.IronStudyPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ArsNativeProgressionPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EpicFightDepthPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.GoetySoulPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.GoetyCommandPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EidolonRitualPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EidolonAlchemyPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.NodeAccessRequirementTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.SystemFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ArchetypeSpecificityScoreTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MorphEcologyPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProgressionStateMigrationMatrixTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.SpecializationReconciliationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.InfiniteProgressionFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CorePointEconomyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProgressionRulesSnapshotTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.LegacyProgressionMigrationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CorePointLedgerCheckpointTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionStateCodecTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionBootstrapTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionSyncStateTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionMutationServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.SemanticXpPipelineTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.SemanticProgressionServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.GameplaySemanticXpPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProgressionRulesProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.InstallableProgressionRulesProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProgressionRulesTransitionPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.AttributeRanksTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.AttributeRanksPersistenceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.AttributeRankMutationServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.AttributeRankCostPolicyProviderTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.UnitAttributeRankCostPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.LevelCorePointAwardPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MainPerkBudgetProgressionTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ProgressionRewardServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CoreProgressionQueryServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalPlayerStateFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalPlayerAttachmentDataTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalEffectiveStatsFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.WorldEntityLevelFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.TerritoryGridTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.NativeAreaThreatCompositionTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.RelevantPlayerLevelFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EntityArchetypeStatScalingTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MobRarityFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EntityScalingPersistenceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EntityScalingLifecycleStateTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EntityScalingInitializationServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.MobAffixFoundationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.EntityBehaviorFoundationTest
python3 "$ROOT/scripts/verify-1211-resources.py"
python3 "$ROOT/scripts/verify-quest-runtime.py"
python3 "$ROOT/scripts/verify-core-xp-adapters.py"
python3 "$ROOT/scripts/verify-world-scaling-runtime.py"
python3 "$ROOT/scripts/verify-entity-scaling-events.py"
python3 "$ROOT/scripts/verify-canonical-player-runtime.py"
