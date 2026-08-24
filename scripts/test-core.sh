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
