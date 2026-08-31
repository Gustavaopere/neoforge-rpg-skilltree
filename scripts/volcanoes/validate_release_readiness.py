#!/usr/bin/env python3
"""Fail-closed Stage 07 release-readiness evidence validator."""

from __future__ import annotations

from pathlib import Path
import re
import sys

ROOT = Path(__file__).resolve().parents[1]
ERRORS: list[str] = []
CHECKS = 0


def read(path: str) -> str:
    file = ROOT / path
    if not file.is_file():
        ERRORS.append(f"missing required release-readiness evidence file: {path}")
        return ""
    return file.read_text(encoding="utf-8")


def require_tokens(path: str, *tokens: str) -> None:
    global CHECKS
    CHECKS += 1
    text = read(path)
    for token in tokens:
        if token not in text:
            ERRORS.append(f"{path}: missing required release-readiness token: {token}")


def require_absent(path: str, *tokens: str) -> None:
    global CHECKS
    CHECKS += 1
    text = read(path)
    for token in tokens:
        if token in text:
            ERRORS.append(f"{path}: forbidden release-readiness token present: {token}")


def validate_runtime_dependency_boundary() -> None:
    global CHECKS
    CHECKS += 1
    toml = read("src/main/resources/META-INF/neoforge.mods.toml")
    blocks = toml.split("[[dependencies.${mod_id}]]")[1:]
    for block in blocks:
        mod_match = re.search(r'^modId="([^"]+)"', block, re.MULTILINE)
        mandatory_match = re.search(r'^mandatory=(true|false)', block, re.MULTILINE)
        if not mod_match:
            ERRORS.append("neoforge.mods.toml: dependency block has no modId")
            continue
        mod_id = mod_match.group(1)
        if "tfc" in mod_id.lower() or "terrafirma" in mod_id.lower():
            ERRORS.append(f"neoforge.mods.toml: TFC/TFC Registry runtime dependency declared: {mod_id}")
        if mandatory_match and mandatory_match.group(1) == "true" and mod_id not in {"neoforge", "minecraft"}:
            ERRORS.append(f"neoforge.mods.toml: optional compatibility host became mandatory: {mod_id}")

    build = read("build.gradle")
    for line in build.splitlines():
        stripped = line.strip()
        if stripped.startswith(("runtimeOnly", "localRuntime", "implementation")):
            lowered = stripped.lower()
            if "tfc" in lowered or "terrafirma" in lowered:
                ERRORS.append(f"build.gradle: TFC/TFC Registry runtime dependency found: {stripped}")


# RL-01 / RL-02 / RL-03: standard CI executes unit tests, build, JAR inspection and a real dedicated server.
require_tokens(
    ".github/workflows/build.yml",
    "run: gradle --no-daemon test",
    "run: gradle --no-daemon build",
    "jar tf \"$JAR\" | grep -Fx 'META-INF/neoforge.mods.toml'",
    "gradle --no-daemon runServer",
    "Dedicated-server smoke test: PASS",
)

# RL-04 / RL-05: release metadata contains no TFC runtime dependency and no optional compatibility host is required.
validate_runtime_dependency_boundary()
require_tokens(
    "build.gradle",
    "implementation \"net.neoforged:neoforge:${neo_version}\"",
    "compileOnly('curse.maven:minecolonies-245506:8621898')",
    "compileOnly('curse.maven:cold-sweat-506194:8302211')",
    "compileOnly('curse.maven:create-rns-1370563:8729955')",
    "compileOnly('curse.maven:sable-1312371:8673825')",
    "compileOnly('com.simibubi.create:create-1.21.1:6.0.10-280:slim')",
)

# RL-06: the exact full-pack host stack boots, loads a world, forces the deterministic volcanic region and persists site data.
require_tokens(
    ".github/workflows/full-pack-compatibility-acceptance.yml",
    ".github/scripts/install_full_pack_acceptance.sh",
    ".github/scripts/run_full_pack_smoke.sh",
    "runGameTestServer",
)
require_tokens(
    ".github/scripts/run_full_pack_smoke.sh",
    "gradle --no-daemon runServer",
    "rcon forceload add -1040 -15664 -993 -15617",
    'test -f "$RUN_DIR/world/data/volcanoes_sites.dat"',
    "Full-pack dedicated-server startup, save/reload and Volcanoes site persistence PASS",
)

# RL-07: every matrix case creates a clean new world twice and requires deterministic Volcanoes site data.
require_tokens(
    ".github/scripts/run_worldgen_matrix_case.sh",
    'rm -rf "$RUN_DIR/world"',
    'test -f "$RUN_DIR/world/data/volcanoes_sites.dat"',
    "run_once 1",
    "run_once 2",
    'cmp "$BUILD_DIR/round-1.digest" "$BUILD_DIR/round-2.digest"',
    "startup, bounded worldgen and same-case determinism PASS",
)

# RL-08: existing-world administration is metadata-only and cannot synthesize terrain/lifecycle state.
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/volcano/ExistingWorldVolcanoAdminSessionTest.java",
    "applyingMetadataNeverInvokesTerrainShaping",
    "existing-world registration must only add site metadata, not synthesize lifecycle/terrain state",
)

# RL-09: MineColonies owns protected-area authority and compatibility drift fails closed.
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/compat/minecolonies/MineColoniesCompatTest.java",
    "wrongVersionFailsClosedWithoutResolvingFactory",
    "exactVersionConstructionMismatchDoesNotEstablishMutationAuthority",
    "assertFalse(service.mayMutate(Level.OVERWORLD, BlockPos.ZERO))",
)

# RL-10: Destroy projection is explicit and each emission component mutates the host at most once.
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/compat/destroy/DestroyPollutionProjectionContractTest.java",
    "exactFourDestroyChannelsAreMappedWithoutInventingParticulateSupport",
    "particulateOnlyLoadIsExplicitlyUnsupportedRatherThanFoldedIntoSmog",
)
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/compat/destroy/DestroyPollutionWriterContractTest.java",
    "successfulComponentMutationIsAppliedOnlyOnce",
    "failedComponentMutationIsNotReservedAndCanRetry",
)

# RL-11: Create oxygen debit is once-per-native cadence; Sable pressure is a bounded exact-host adapter.
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/compat/create/CreateRespirationDecisionContractTest.java",
    "safeAtmosphereDoesNotSpendBacktankAir",
    "debitUsesCreateTwentyTickCadenceExactlyOnce",
)
require_tokens(
    "src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java",
    "preventing native Create and the shared protection transaction from both debiting air",
    "EventPriority.LOWEST, AtmosphereRuntime::onLivingBreathe",
)
require_tokens(
    "src/test/java/dev/gustavopere/volcanoes/compat/sable/SablePressureCompatContractTest.java",
    "absentOrMismatchedSableNeverInstallsAdapter",
    "exactSableInstallsAndOptionalFailureFailsClosed",
)

# RL-12: Task 02 profiled hot paths and the stored benchmark remain executable with bounded defaults.
require_tokens(
    ".github/workflows/performance-acceptance.yml",
    "PerformanceProfilerTest",
    "VolcanoPerformanceBenchmarkTest",
    "VolcanoTickSchedulerHardeningTest",
    "AtmosphericSourceIndexTest",
    "BoundedConnectedWaterDepthLookupTest",
    "VolcanicLavaControllerTest",
    "build/performance/volcano-sites.csv",
)
require_tokens(
    "src/main/java/dev/gustavopere/volcanoes/performance/PerformanceConfig.java",
    "DEFAULT_ASH_DEPOSITION_BLOCKS_PER_TICK = 64",
    "DEFAULT_LAVA_SPECIALIZATION_BLOCKS_PER_TICK = 32",
    "DEFAULT_ERUPTION_TERRAIN_MUTATIONS_PER_TICK = 8",
)

# RL-13..RL-18: legal/provenance state is current, fail-closed and packaged into the release JAR.
require_tokens("LICENSE", "BSD 2-Clause")
require_tokens(
    "SOURCES.md",
    "The Stage 07 inventory contains no declared `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship",
    "Any future `DERIVED_CODE` / `DERIVED_ASSET` entry must name the exact upstream revision/artifact",
)
require_tokens(
    "THIRD_PARTY_NOTICES.md",
    "No TFC Volcanoes source unit or asset is currently declared as derived material",
    "REVIEW_REQUIRED",
    "DERIVED_CODE",
    "DERIVED_ASSET",
)
require_absent("THIRD_PARTY_NOTICES.md", "At the Stage 07 audit candidate state")
require_tokens(
    "scripts/validate_provenance.py",
    '"REVIEW_REQUIRED" in relationships',
    '"local_paths": derivation.get("local_paths")',
    '"upstream_paths": derivation.get("upstream_paths")',
    '"permission_evidence": derivation.get("permission_evidence")',
    '"required_notice": derivation.get("required_notice")',
    "derived material lacks exact upstream source/revision",
)
require_tokens(
    ".github/workflows/third-party-provenance-audit.yml",
    "TFCVolcanoes-1.21.1-2.2.1.jar",
    "Build release JAR and verify legal payload",
)
require_tokens(
    "docs/provenance/THIRD_PARTY_AUDIT.md",
    "Status: **GREEN and canonical on `main`",
    "41 direct project/tool records",
    "43 Gradle/settings/acceptance/CI tokens",
    "no `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship",
)
require_tokens(
    "docs/upstream/NOTICE.md",
    "BSD 2-Clause",
    "Copyright (c) 2026, Verph",
)
require_tokens(
    "build.gradle",
    "from('LICENSE')",
    "from('THIRD_PARTY_NOTICES.md')",
)

# Human evidence map is mandatory and maps 1:1 to the canonical 19-line checklist.
release_doc = read("docs/release/RELEASE_READINESS.md")
CHECKS += 1
for idx in range(1, 20):
    marker = f"RL-{idx:02d}"
    if marker not in release_doc:
        ERRORS.append(f"docs/release/RELEASE_READINESS.md: missing evidence id {marker}")
for workflow_name in (
    "Volcanoes CI",
    "Cold Sweat Heat Acceptance",
    "Performance Hardening Acceptance",
    "MineColonies Claim Acceptance",
    "Create Sable Acceptance",
    "RNS Hydrothermal Acceptance",
    "Full Pack Compatibility Acceptance",
    "Third-Party Provenance Audit",
    "Worldgen Compatibility Matrix",
    "Release Readiness",
):
    if workflow_name not in release_doc:
        ERRORS.append(f"docs/release/RELEASE_READINESS.md: missing canonical workflow {workflow_name}")
if "all canonical workflows must be GREEN on the same exact PR head" not in release_doc:
    ERRORS.append("release evidence must require all canonical workflows GREEN on the same exact PR head")

# RL-19 is intentionally post-merge: candidate PRs can prove RL-01..RL-18, but only closeout may claim final GREEN.
final_task = ROOT / "plans" / "07-hardening" / "✅-04-release-checklist.md"
open_task = ROOT / "plans" / "07-hardening" / "04-release-checklist.md"
if final_task.is_file():
    CHECKS += 1
    if "RELEASE_READINESS_STATUS: GREEN" not in release_doc:
        ERRORS.append("final Task 04 state requires RELEASE_READINESS_STATUS: GREEN")
    checked = final_task.read_text(encoding="utf-8")
    expected_items = (
        "`gradle test` green.",
        "`gradle build` green.",
        "dedicated-server smoke green.",
        "no TFC/TFC Registry runtime dependency.",
        "no required optional-mod dependency.",
        "full-pack startup and world-load test green.",
        "new-world volcano generation inspected.",
        "existing-world no-retrogen safety inspected.",
        "MineColonies protected-area tests green.",
        "eruption + atmosphere + Destroy integration has no double damage/pollution.",
        "pressure + Create/Sable protection consumes resources once.",
        "performance budgets verified.",
        "`LICENSE`, `SOURCES.md` and `THIRD_PARTY_NOTICES.md` are current.",
        "built JAR contains the Volcanoes `LICENSE` and `THIRD_PARTY_NOTICES.md`.",
        "the Stage 07 third-party provenance audit is green for every dependency/reference/compatibility target relevant to release.",
        "every actual `DERIVED_CODE`/`DERIVED_ASSET` entry has exact upstream revision/file provenance and all required notices/permissions.",
        "no release contains actual derived material whose status is `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown.",
        "TFC Volcanoes-derived material, if any, satisfies the BSD notice and root/file-level provenance rules.",
        "completed plan files are renamed with `✅-` prefix and `STATUS.md` records the final merge SHA.",
    )
    for item in expected_items:
        if f"- [x] {item}" not in checked:
            ERRORS.append(f"final Task 04 checklist item is not checked: {item}")
    status = read("plans/STATUS.md")
    if "Task 04" not in status or "complete and canonical" not in status:
        ERRORS.append("final Task 04 state requires STATUS.md to record Task 04 as complete and canonical")
    if "RL-19 — COMPLETE" not in release_doc:
        ERRORS.append("final Task 04 state requires RL-19 — COMPLETE")
else:
    CHECKS += 1
    if not open_task.is_file():
        ERRORS.append("candidate Task 04 state requires plans/07-hardening/04-release-checklist.md")
    if "RELEASE_READINESS_STATUS: CANDIDATE_GREEN" not in release_doc:
        ERRORS.append("candidate Task 04 state requires RELEASE_READINESS_STATUS: CANDIDATE_GREEN")
    if "RL-19 — PENDING_CLOSEOUT" not in release_doc:
        ERRORS.append("candidate Task 04 state must leave RL-19 pending until merge/status closeout")

print(f"RELEASE_READINESS_SUMMARY errors={len(ERRORS)} checks={CHECKS}")
for error in ERRORS:
    print(f"ERROR: {error}")

if ERRORS:
    sys.exit(1)

if final_task.is_file():
    print("RELEASE_READINESS_STATUS: GREEN")
else:
    print("RELEASE_READINESS_STATUS: CANDIDATE_GREEN")
