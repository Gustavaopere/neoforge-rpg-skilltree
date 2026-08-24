#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
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
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.NotionCombatPerkCatalogTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkNodeBindingTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkTreeModelTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkRuntimeStateTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkAttackPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkFinalizationPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkTransitionPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkLifecyclePolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkLifecycleBoundaryTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkPurchaseRespecReconcileTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.FrozenA0010FuryPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.FrozenPhysicalProtectionPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.FrozenA0046FocusPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.FrozenA0036RuntimeContractTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalTargetDebuffServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatWeaponFamilyPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatWeaponMasteryPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPositionPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkDefensePolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CombatPerkControlPolicyTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalActionLedgerTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalCriticalServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalFuryServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalFocusServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalStaminaServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.CanonicalActionCorrelationServiceTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ExactStaminaDebitCaptureTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ExactStaminaReceiptCorrelationTest
java -cp "$OUT" dev.gustavopere.rpgskilltree.core.ExactStaminaPerkIntegrationContractTest
