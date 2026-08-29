#!/usr/bin/env python3
from pathlib import Path
import zipfile

ROOT = Path(__file__).resolve().parents[1]
REQUIRED = [
    ROOT / "gradlew",
    ROOT / "gradlew.bat",
    ROOT / "gradle" / "wrapper" / "gradle-wrapper.jar",
    ROOT / "gradle" / "wrapper" / "gradle-wrapper.properties",
]

for path in REQUIRED:
    if not path.is_file():
        raise SystemExit(f"Gradle wrapper contract: missing {path.relative_to(ROOT)}")

properties = (ROOT / "gradle" / "wrapper" / "gradle-wrapper.properties").read_text(encoding="utf-8")
expected_distribution = "distributionUrl=https\\://services.gradle.org/distributions/gradle-8.14-bin.zip"
if expected_distribution not in properties:
    raise SystemExit("Gradle wrapper contract: distributionUrl must pin Gradle 8.14 bin")

with zipfile.ZipFile(ROOT / "gradle" / "wrapper" / "gradle-wrapper.jar") as jar:
    if "org/gradle/wrapper/GradleWrapperMain.class" not in jar.namelist():
        raise SystemExit("Gradle wrapper contract: wrapper JAR does not contain GradleWrapperMain")

workflow = (ROOT / ".github" / "workflows" / "alpha2-build.yml").read_text(encoding="utf-8")
for command in ("./gradlew --no-daemon build", "./gradlew --no-daemon runServer"):
    if command not in workflow:
        raise SystemExit(f"Gradle wrapper contract: CI must use {command}")
if "run: gradle --no-daemon" in workflow:
    raise SystemExit("Gradle wrapper contract: CI still invokes system Gradle directly")

print("Gradle wrapper contract: PASS")
