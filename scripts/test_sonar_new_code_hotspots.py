#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
PARSER = ROOT / "src/main/java/dev/gustavopere/rpgskilltree/runtime/data/SkillInvestmentMetadataParser.java"
LEGACY_TEST = ROOT / "src/test/java/dev/gustavopere/rpgskilltree/core/CanonicalInvestmentProjectionTest.java"
CONTRACT = ROOT / "src/test/java/dev/gustavopere/rpgskilltree/core/CanonicalInvestmentProjectionContract.java"
JUNIT = ROOT / "src/test/java/dev/gustavopere/rpgskilltree/core/CanonicalInvestmentProjectionJUnitTest.java"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def main() -> None:
    parser = PARSER.read_text(encoding="utf-8")
    junit = JUNIT.read_text(encoding="utf-8")

    require(
        "parseTags(" in parser and "parseDomainWeights(" in parser,
        "SkillInvestmentMetadataParser.parseOne must delegate tag parsing and domain projection to focused helpers.",
    )
    require(
        not LEGACY_TEST.exists(),
        "Legacy main-style CanonicalInvestmentProjectionTest must not remain named as a test class.",
    )
    require(
        CONTRACT.is_file(),
        "CanonicalInvestmentProjection legacy contract must be preserved under a non-test helper name.",
    )
    require(
        "assertDoesNotThrow" in junit and "CanonicalInvestmentProjectionContract.main" in junit,
        "JUnit wrapper must contain an explicit JUnit assertion around the legacy contract.",
    )

    print("Sonar New Code hotspot regression contract: PASS")


if __name__ == "__main__":
    main()
