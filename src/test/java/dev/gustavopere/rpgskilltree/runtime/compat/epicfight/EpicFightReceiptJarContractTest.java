package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Structural proof for the exact Epic Fight binary supported by the causal stamina receipt bridge.
 *
 * <p>This intentionally validates bytecode rather than source. Any unknown ResourceConsumer.consume
 * call site or change to the STAMINA consumer fails closed until explicitly audited.
 */
public final class EpicFightReceiptJarContractTest {
    private static final String PLAYER_PATCH =
        "yesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch";
    private static final String SERVER_PLAYER_PATCH =
        "yesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch";
    private static final String SKILL_CONTAINER = "yesman/epicfight/skill/SkillContainer";
    private static final String COMBO_ATTACKS = "yesman/epicfight/skill/common/ComboAttacks";
    private static final String SKILL = "yesman/epicfight/skill/Skill";
    private static final String RESOURCE = "yesman/epicfight/skill/Skill$Resource";
    private static final String RESOURCE_CONSUMER =
        "yesman/epicfight/skill/Skill$Resource$ResourceConsumer";

    private static final String CONSUME_FOR_SKILL_DESC =
        "(Lyesman/epicfight/skill/Skill;Lyesman/epicfight/skill/Skill$Resource;FZ" +
        "Lnet/minecraft/nbt/CompoundTag;)Z";
    private static final String REQUEST_HOLD_DESC =
        "(Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;" +
        "Lnet/minecraft/nbt/CompoundTag;)Z";
    private static final String EXECUTE_ON_SERVER_DESC =
        "(Lyesman/epicfight/skill/SkillContainer;Lnet/minecraft/nbt/CompoundTag;)V";
    private static final String CONSUMER_DESC =
        "(Lyesman/epicfight/skill/SkillContainer;" +
        "Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;F)V";

    private static final Map<CallSite, Integer> EXPECTED_CALL_SITES = Map.of(
        new CallSite(PLAYER_PATCH, "consumeForSkill", CONSUME_FOR_SKILL_DESC), 2,
        new CallSite(SKILL_CONTAINER, "requestHold", REQUEST_HOLD_DESC), 1,
        new CallSite(COMBO_ATTACKS, "executeOnServer", EXECUTE_ON_SERVER_DESC), 1
    );

    private EpicFightReceiptJarContractTest() {}

    public static void main(String[] args) throws Exception {
        require(args.length >= 3 && args.length <= 4,
            "usage: <epicfight.jar> <expectedVersion> <expectedVersionId> [expectedSha256]");
        Path jarPath = Path.of(args[0]).toAbsolutePath().normalize();
        String expectedVersion = requireText(args[1], "expectedVersion");
        String expectedVersionId = requireText(args[2], "expectedVersionId");
        String expectedSha256 = args.length == 4 ? args[3].trim().toLowerCase(Locale.ROOT) : "";

        require(Files.isRegularFile(jarPath), "Epic Fight artifact does not exist: " + jarPath);
        String sha256 = sha256(jarPath);
        if (!expectedSha256.isEmpty()) {
            require(sha256.equals(expectedSha256),
                "Epic Fight SHA-256 changed: expected=" + expectedSha256 + " actual=" + sha256);
        }

        try (JarFile jar = new JarFile(jarPath.toFile())) {
            provePackagedVersion(jar, expectedVersion);
            Map<String, ClassNode> classes = readClasses(jar);
            proveConsumeForSkillDescriptor(classes);
            proveAuditedResourceConsumerCallSites(classes);
            proveStaminaConsumer(classes);
        }

        System.out.println("EpicFightReceiptJarContractTest: PASS");
        System.out.println("EPICFIGHT_RECEIPT_ARTIFACT=" + jarPath.getFileName());
        System.out.println("EPICFIGHT_RECEIPT_VERSION=" + expectedVersion);
        System.out.println("EPICFIGHT_RECEIPT_VERSION_ID=" + expectedVersionId);
        System.out.println("EPICFIGHT_RECEIPT_SHA256=" + sha256);
    }

    private static void provePackagedVersion(JarFile jar, String expectedVersion) throws IOException {
        JarEntry modsToml = jar.getJarEntry("META-INF/neoforge.mods.toml");
        require(modsToml != null, "Epic Fight JAR has no META-INF/neoforge.mods.toml");
        String text;
        try (InputStream input = jar.getInputStream(modsToml)) {
            text = new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        }
        require(text.contains("epicfight"), "neoforge.mods.toml does not identify Epic Fight");
        require(text.contains(expectedVersion),
            "neoforge.mods.toml does not contain expected version " + expectedVersion);
    }

    private static void proveConsumeForSkillDescriptor(Map<String, ClassNode> classes) {
        ClassNode playerPatch = requireClass(classes, PLAYER_PATCH);
        long exact = playerPatch.methods.stream()
            .filter(method -> method.name.equals("consumeForSkill") && method.desc.equals(CONSUME_FOR_SKILL_DESC))
            .count();
        require(exact == 1L,
            "expected exactly one PlayerPatch.consumeForSkill" + CONSUME_FOR_SKILL_DESC + ", found " + exact);
    }

    private static void proveAuditedResourceConsumerCallSites(Map<String, ClassNode> classes) {
        Map<CallSite, Integer> actual = new LinkedHashMap<>();
        for (ClassNode classNode : classes.values()) {
            for (MethodNode method : classNode.methods) {
                int invokes = 0;
                for (AbstractInsnNode insn : method.instructions) {
                    if (insn instanceof MethodInsnNode call
                        && call.getOpcode() == Opcodes.INVOKEINTERFACE
                        && call.owner.equals(RESOURCE_CONSUMER)
                        && call.name.equals("consume")
                        && call.desc.equals(CONSUMER_DESC)) {
                        invokes++;
                    }
                }
                if (invokes > 0) {
                    actual.put(new CallSite(classNode.name, method.name, method.desc), invokes);
                }
            }
        }

        require(actual.equals(EXPECTED_CALL_SITES),
            "ResourceConsumer.consume call-site drift. expected=" + EXPECTED_CALL_SITES + " actual=" + actual);
        int total = actual.values().stream().mapToInt(Integer::intValue).sum();
        require(total == 4, "expected exactly four global ResourceConsumer.consume invokes, found " + total);
    }

    private static void proveStaminaConsumer(Map<String, ClassNode> classes) {
        ClassNode resource = requireClass(classes, RESOURCE);
        MethodNode clinit = method(resource, "<clinit>", "()V");
        Handle staminaConsumerHandle = findStaminaConsumerHandle(clinit);
        require(staminaConsumerHandle != null, "could not resolve STAMINA ResourceConsumer lambda from <clinit>");
        require(staminaConsumerHandle.getOwner().equals(RESOURCE),
            "STAMINA consumer implementation moved outside Skill$Resource: " + staminaConsumerHandle);

        MethodNode consumer = method(resource, staminaConsumerHandle.getName(), staminaConsumerHandle.getDesc());
        List<AbstractInsnNode> meaningful = meaningful(consumer);
        int getIndex = indexOfCall(meaningful, SERVER_PLAYER_PATCH, "getStamina", "()F", 0);
        require(getIndex >= 0, "STAMINA consumer no longer reads ServerPlayerPatch.getStamina()");
        int subIndex = indexOfOpcode(meaningful, Opcodes.FSUB, getIndex + 1);
        require(subIndex > getIndex, "STAMINA consumer no longer subtracts amount with FSUB");
        int setIndex = indexOfCall(meaningful, SERVER_PLAYER_PATCH, "setStamina", "(F)V", subIndex + 1);
        require(setIndex > subIndex, "STAMINA consumer no longer writes ServerPlayerPatch.setStamina(float)");
    }

    private static Handle findStaminaConsumerHandle(MethodNode clinit) {
        List<AbstractInsnNode> instructions = meaningful(clinit);
        int putStamina = -1;
        for (int i = 0; i < instructions.size(); i++) {
            AbstractInsnNode insn = instructions.get(i);
            if (insn instanceof FieldInsnNode field
                && field.getOpcode() == Opcodes.PUTSTATIC
                && field.owner.equals(RESOURCE)
                && field.name.equals("STAMINA")
                && field.desc.equals('L' + RESOURCE + ';')) {
                putStamina = i;
                break;
            }
        }
        require(putStamina >= 0, "Skill$Resource.<clinit> no longer initializes STAMINA");

        int previousResourcePut = -1;
        for (int i = putStamina - 1; i >= 0; i--) {
            AbstractInsnNode insn = instructions.get(i);
            if (insn instanceof FieldInsnNode field
                && field.getOpcode() == Opcodes.PUTSTATIC
                && field.owner.equals(RESOURCE)
                && field.desc.equals('L' + RESOURCE + ';')) {
                previousResourcePut = i;
                break;
            }
        }

        Handle found = null;
        for (int i = previousResourcePut + 1; i < putStamina; i++) {
            AbstractInsnNode insn = instructions.get(i);
            if (!(insn instanceof InvokeDynamicInsnNode indy)) continue;
            if (!Type.getReturnType(indy.desc).getInternalName().equals(RESOURCE_CONSUMER)) continue;
            for (Object arg : indy.bsmArgs) {
                if (arg instanceof Handle handle && handle.getDesc().equals(CONSUMER_DESC)) {
                    require(found == null, "multiple ResourceConsumer implementation handles found for STAMINA");
                    found = handle;
                }
            }
        }
        return found;
    }

    private static Map<String, ClassNode> readClasses(JarFile jar) throws IOException {
        Map<String, ClassNode> classes = new HashMap<>();
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
            try (InputStream input = jar.getInputStream(entry)) {
                ClassNode node = new ClassNode();
                new ClassReader(input).accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                classes.put(node.name, node);
            }
        }
        return classes;
    }

    private static MethodNode method(ClassNode owner, String name, String desc) {
        return owner.methods.stream()
            .filter(method -> method.name.equals(name) && method.desc.equals(desc))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing method " + owner.name + '#' + name + desc));
    }

    private static ClassNode requireClass(Map<String, ClassNode> classes, String name) {
        ClassNode result = classes.get(name);
        if (result == null) throw new AssertionError("missing class " + name);
        return result;
    }

    private static List<AbstractInsnNode> meaningful(MethodNode method) {
        List<AbstractInsnNode> result = new ArrayList<>();
        for (AbstractInsnNode insn : method.instructions) {
            if (insn.getOpcode() >= 0) result.add(insn);
        }
        return result;
    }

    private static int indexOfCall(
        List<AbstractInsnNode> instructions,
        String owner,
        String name,
        String desc,
        int from
    ) {
        for (int i = Math.max(0, from); i < instructions.size(); i++) {
            AbstractInsnNode insn = instructions.get(i);
            if (insn instanceof MethodInsnNode call
                && call.owner.equals(owner)
                && call.name.equals(name)
                && call.desc.equals(desc)) return i;
        }
        return -1;
    }

    private static int indexOfOpcode(List<AbstractInsnNode> instructions, int opcode, int from) {
        for (int i = Math.max(0, from); i < instructions.size(); i++) {
            if (instructions.get(i).getOpcode() == opcode) return i;
        }
        return -1;
    }

    private static String sha256(Path file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(file)) {
            byte[] buffer = new byte[64 * 1024];
            for (int read; (read = input.read(buffer)) >= 0;) {
                if (read > 0) digest.update(buffer, 0, read);
            }
        }
        return java.util.HexFormat.of().formatHex(digest.digest());
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return value;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private record CallSite(String owner, String name, String desc) {}
}
