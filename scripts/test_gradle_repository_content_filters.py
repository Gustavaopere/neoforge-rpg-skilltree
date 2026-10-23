#!/usr/bin/env python3
from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]
BUILD_GRADLE = ROOT / "build.gradle"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise SystemExit(message)


def project_maven_blocks(text: str) -> list[str]:
    """Return top-level project maven blocks from the repositories section.

    The repository declarations in this build use four-space indentation. Stop at
    the next sibling repository declaration so nested content blocks remain part
    of the owning repository block.
    """
    repositories_match = re.search(r"(?ms)^repositories\s*\{\n(?P<body>.*?)(?=^}\n\njava\.)", text)
    require(repositories_match is not None, "Could not locate project repositories block in build.gradle.")
    body = repositories_match.group("body")
    starts = [match.start() for match in re.finditer(r"(?m)^    maven\s*\{", body)]
    blocks: list[str] = []
    for index, start in enumerate(starts):
        end = starts[index + 1] if index + 1 < len(starts) else len(body)
        blocks.append(body[start:end])
    return blocks


def main() -> None:
    build = BUILD_GRADLE.read_text(encoding="utf-8")
    blocks = project_maven_blocks(build)
    parchment_blocks = [block for block in blocks if "https://maven.parchmentmc.org" in block]

    require(
        len(parchment_blocks) == 1,
        f"Expected exactly one project Parchment Maven repository; found {len(parchment_blocks)}.",
    )
    parchment = parchment_blocks[0]
    require(
        re.search(r"content\s*\{\s*includeGroup\s+['\"]org\.parchmentmc\.data['\"]\s*}", parchment) is not None,
        "Parchment Maven must be content-filtered to org.parchmentmc.data so unrelated dependencies are not queried there.",
    )
    require(
        "includeGroup 'curse.maven'" not in parchment and 'includeGroup "curse.maven"' not in parchment,
        "Parchment Maven must never claim CurseMaven coordinates.",
    )

    curse_blocks = [block for block in blocks if "https://www.cursemaven.com" in block]
    require(len(curse_blocks) == 1, f"Expected exactly one CurseMaven repository; found {len(curse_blocks)}.")
    require(
        re.search(r"content\s*\{\s*includeGroup\s+['\"]curse\.maven['\"]\s*}", curse_blocks[0]) is not None,
        "CurseMaven must remain the repository responsible for curse.maven coordinates.",
    )

    print("Gradle repository content-filter contract: PASS")


if __name__ == "__main__":
    main()
