import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
SCAFFOLDER = ROOT / "PROJECT-INSTRUCTIONS/engineering/tooling/scaffolder/scaffold_mod.py"
MOD_SPEC = ROOT / "PROJECT-INSTRUCTIONS/engineering/tests/fixtures/i3-golden-mod-spec.json"
SCAFFOLD_CONFIG = ROOT / "PROJECT-INSTRUCTIONS/engineering/tests/fixtures/i3-golden-scaffold-config.json"
GOLDEN = ROOT / "PROJECT-INSTRUCTIONS/engineering/tests/golden/i3-golden-mod"
ROOT_WRAPPER_JAR = ROOT / "gradle/wrapper/gradle-wrapper.jar"


def load_scaffolder():
    spec = importlib.util.spec_from_file_location("i3_scaffold_mod", SCAFFOLDER)
    module = importlib.util.module_from_spec(spec)
    assert spec.loader is not None
    spec.loader.exec_module(module)
    return module


def file_map(root):
    return {
        path.relative_to(root).as_posix(): path.read_bytes()
        for path in root.rglob("*")
        if path.is_file()
    }


class I3ModScaffolderContractTest(unittest.TestCase):
    def setUp(self):
        self.mod_spec = json.loads(MOD_SPEC.read_text(encoding="utf-8"))
        self.config = json.loads(SCAFFOLD_CONFIG.read_text(encoding="utf-8"))

    def require_scaffolder(self):
        if not SCAFFOLDER.is_file():
            self.skipTest("production scaffolder is intentionally absent during I3 RED")
        return load_scaffolder()

    def generate(self, output):
        module = self.require_scaffolder()
        module.generate_project(MOD_SPEC, SCAFFOLD_CONFIG, output)
        return output

    def test_scaffolder_production_entrypoint_exists(self):
        self.assertTrue(
            SCAFFOLDER.is_file(),
            "I3 RED: production scaffolder PROJECT-INSTRUCTIONS/engineering/tooling/scaffolder/scaffold_mod.py is missing",
        )

    def test_generated_project_matches_checked_in_golden_bytes(self):
        with tempfile.TemporaryDirectory() as tmp:
            generated = self.generate(Path(tmp) / "generated")
            self.assertEqual(file_map(GOLDEN), file_map(generated))

    def test_generation_is_deterministic(self):
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            first = self.generate(root / "first")
            second = self.generate(root / "second")
            self.assertEqual(file_map(first), file_map(second))

    def test_non_empty_target_is_rejected_without_overwrite(self):
        module = self.require_scaffolder()
        with tempfile.TemporaryDirectory() as tmp:
            target = Path(tmp) / "existing"
            target.mkdir()
            marker = target / "keep.txt"
            marker.write_text("preserve", encoding="utf-8")
            with self.assertRaises(FileExistsError):
                module.generate_project(MOD_SPEC, SCAFFOLD_CONFIG, target)
            self.assertEqual("preserve", marker.read_text(encoding="utf-8"))

    def test_generated_project_uses_exact_audited_stack_and_no_optional_providers(self):
        with tempfile.TemporaryDirectory() as tmp:
            generated = self.generate(Path(tmp) / "generated")
            gradle_properties = (generated / "gradle.properties").read_text(encoding="utf-8")
            build_gradle = (generated / "build.gradle").read_text(encoding="utf-8")
            wrapper = (generated / "gradle/wrapper/gradle-wrapper.properties").read_text(encoding="utf-8")
            metadata = (generated / "src/main/resources/META-INF/neoforge.mods.toml").read_text(encoding="utf-8")

            self.assertIn("minecraft_version=1.21.1", gradle_properties)
            self.assertIn("neo_version=21.1.248", gradle_properties)
            self.assertIn("java_version=21", gradle_properties)
            self.assertIn("junit_version=5.14.3", gradle_properties)
            self.assertIn("net.neoforged.gradle.userdev' version '7.1.26", build_gradle)
            self.assertIn("JavaLanguageVersion.of(Integer.parseInt(java_version))", build_gradle)
            self.assertIn("gradle-8.14-bin.zip", wrapper)
            self.assertIn('modId="${mod_id}"', metadata)
            self.assertIn('modId="neoforge"', metadata)
            self.assertIn('modId="minecraft"', metadata)

            forbidden = ("create", "sable", "minecolonies", "ars_nouveau", "epicfight", "irons_spellbooks")
            lowered = "\n".join((build_gradle, metadata)).lower()
            for provider in forbidden:
                self.assertNotIn(provider, lowered)

    def test_common_and_client_entrypoints_are_physically_separated(self):
        with tempfile.TemporaryDirectory() as tmp:
            generated = self.generate(Path(tmp) / "generated")
            package_path = Path(*self.config["java_package"].split("."))
            common = generated / "src/main/java" / package_path / f'{self.config["main_class"]}.java'
            client = generated / "src/main/java" / package_path / "client" / f'{self.config["client_class"]}.java'

            common_text = common.read_text(encoding="utf-8")
            client_text = client.read_text(encoding="utf-8")
            self.assertIn("@Mod(I3GoldenMod.MOD_ID)", common_text)
            self.assertNotIn("net.minecraft.client", common_text)
            self.assertIn("@Mod(value = I3GoldenMod.MOD_ID, dist = Dist.CLIENT)", client_text)
            self.assertIn("package dev.example.i3golden.client;", client_text)

    def test_wrapper_jar_is_copied_from_repository_authority(self):
        with tempfile.TemporaryDirectory() as tmp:
            generated = self.generate(Path(tmp) / "generated")
            self.assertEqual(
                ROOT_WRAPPER_JAR.read_bytes(),
                (generated / "gradle/wrapper/gradle-wrapper.jar").read_bytes(),
            )

    def test_target_drift_is_rejected_instead_of_silently_retargeted(self):
        module = self.require_scaffolder()
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            drifted = json.loads(MOD_SPEC.read_text(encoding="utf-8"))
            drifted["identity"]["target"]["neoforge"] = "21.1.247"
            drifted_path = root / "drifted-mod-spec.json"
            drifted_path.write_text(json.dumps(drifted), encoding="utf-8")
            with self.assertRaises(ValueError):
                module.generate_project(drifted_path, SCAFFOLD_CONFIG, root / "output")

    def test_missing_explicit_java_identity_is_rejected_instead_of_guessed(self):
        module = self.require_scaffolder()
        with tempfile.TemporaryDirectory() as tmp:
            root = Path(tmp)
            incomplete = json.loads(SCAFFOLD_CONFIG.read_text(encoding="utf-8"))
            incomplete.pop("java_package")
            incomplete_path = root / "incomplete-scaffold-config.json"
            incomplete_path.write_text(json.dumps(incomplete), encoding="utf-8")
            with self.assertRaises(ValueError):
                module.generate_project(MOD_SPEC, incomplete_path, root / "output")

    def test_no_unresolved_template_tokens_remain(self):
        with tempfile.TemporaryDirectory() as tmp:
            generated = self.generate(Path(tmp) / "generated")
            unresolved = []
            for relative, data in file_map(generated).items():
                if relative.endswith(".jar"):
                    continue
                text = data.decode("utf-8")
                if "{{" in text or "}}" in text or "@@" in text:
                    unresolved.append(relative)
            self.assertEqual([], unresolved)


if __name__ == "__main__":
    unittest.main()
