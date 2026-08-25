package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Exact binary contract for the P-0035 post-cancel/pre-stun-shield transaction domain. */
public final class EpicFightImpactStaminaJarContractTest {
    private static final String VANILLA_HOOKS = "yesman/epicfight/api/event/impl/VanillaEntityEventHooks";
    private static final String DAMAGE_SOURCE = "yesman/epicfight/world/damagesource/EpicFightDamageSource";
    private static final String HURTABLE = "yesman/epicfight/world/capabilities/entitypatch/HurtableEntityPatch";
    private static final String PLAYER_PATCH = "yesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch";
    private static final String STUN_TYPE = "yesman/epicfight/world/damagesource/StunType";
    private static final String STUNNED_EVENT = "yesman/epicfight/api/event/types/entity/StunnedEvent";
    private static final String EVENT_HOOKS_ENTITY = "yesman/epicfight/api/event/EpicFightEventHooks$Entity";
    private static final String EVENT_HOOK = "yesman/epicfight/api/event/EventHook";
    private static final String ENDURANCE = "yesman/epicfight/skill/passive/EnduranceSkill";
    private static final String SKILL = "yesman/epicfight/skill/Skill";
    private static final String RESOURCE = "yesman/epicfight/skill/Skill$Resource";
    private static final String COMPOUND_TAG = "net/minecraft/nbt/CompoundTag";

    private EpicFightImpactStaminaJarContractTest() {}

    public static void main(String[] args) throws Exception {
        require(args.length == 2, "usage: <epicfight.jar> <expectedSha256>");
        Path jarPath = Path.of(args[0]).toAbsolutePath().normalize();
        String expectedSha = args[1].trim().toLowerCase();
        require(Files.isRegularFile(jarPath), "missing Epic Fight JAR: " + jarPath);
        String actualSha = sha256(jarPath);
        require(actualSha.equals(expectedSha), "Epic Fight SHA-256 drift: expected=" + expectedSha + " actual=" + actualSha);

        try (JarFile jar = new JarFile(jarPath.toFile())) {
            Map<String, ClassNode> classes = readClasses(jar);
            proveImpactCommitWindow(classes);
            proveNativeStaminaPrimitive(classes);
            proveEnduranceDimensionalEquivalence(classes);
        }
        System.out.println("EpicFightImpactStaminaJarContractTest: PASS");
        System.out.println("EPICFIGHT_IMPACT_STAMINA_SHA256=" + actualSha);
    }

    private static void proveImpactCommitWindow(Map<String, ClassNode> classes) {
        MethodNode pre = method(requireClass(classes, VANILLA_HOOKS), "onCalculateDamagePre",
            "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;FLjava/util/function/Consumer;)V");

        LocalVariableNode hitPatch = local(pre, "hitEntityPatchAsHurtable", 'L' + HURTABLE + ';');
        LocalVariableNode stunType = local(pre, "stunType", 'L' + STUN_TYPE + ';');
        LocalVariableNode shield = local(pre, "stunShield", "F");
        LocalVariableNode impact = local(pre, "impact", "F");
        LocalVariableNode finalDamage = local(pre, "finalDamage", "F");

        MethodInsnNode calculate = uniqueCall(pre, DAMAGE_SOURCE, "calculateImpact", "()F");
        MethodInsnNode damageShield = uniqueCall(pre, HURTABLE, "damageStunShield", "(FF)V");
        MethodInsnNode isCanceled = uniqueCall(pre, STUNNED_EVENT, "isCanceled", "()Z");
        FieldInsnNode onStunned = uniqueFieldRead(pre, EVENT_HOOKS_ENTITY, "ON_STUNNED", 'L' + EVENT_HOOK + ';');
        MethodInsnNode post = firstCallBetween(pre, index(pre, onStunned), index(pre, isCanceled), EVENT_HOOK, "postWithListener");

        int calculateIndex = index(pre, calculate);
        int onStunnedIndex = index(pre, onStunned);
        int postIndex = index(pre, post);
        int canceledIndex = index(pre, isCanceled);
        int damageIndex = index(pre, damageShield);
        require(calculateIndex < onStunnedIndex && onStunnedIndex < postIndex && postIndex < canceledIndex && canceledIndex < damageIndex,
            "impact/cancel/damageStunShield ordering drifted");
        require(countCall(pre, DAMAGE_SOURCE, "calculateImpact", "()F") == 1, "calculateImpact must remain unique");
        require(countCall(pre, HURTABLE, "damageStunShield", "(FF)V") == 1, "damageStunShield must remain unique");

        AbstractInsnNode afterCalculate = nextReal(calculate);
        require(afterCalculate instanceof VarInsnNode store && store.getOpcode() == Opcodes.FSTORE && store.var == impact.index,
            "calculateImpact result must still store into LVT local impact");

        int compareIndex = findOpcodeBetween(pre, calculateIndex, onStunnedIndex, Opcodes.FCMPL);
        require(compareIndex > calculateIndex, "missing stunShield > impact comparison");
        AbstractInsnNode compare = pre.instructions.get(compareIndex);
        VarInsnNode compareImpact = requireVar(previousReal(compare), Opcodes.FLOAD, "comparison impact load");
        VarInsnNode compareShield = requireVar(previousReal(compareImpact), Opcodes.FLOAD, "comparison shield load");
        require(compareImpact.var == impact.index && compareShield.var == shield.index,
            "comparison no longer uses captured stunShield/impact locals");
        AbstractInsnNode compareJump = nextReal(compare);
        require(compareJump.getOpcode() == Opcodes.IFLE, "provider strict `stunShield > impact` branch drifted");

        FieldInsnNode shortField = uniqueFieldReadBetween(pre, compareIndex, onStunnedIndex, STUN_TYPE, "SHORT");
        FieldInsnNode longField = uniqueFieldReadBetween(pre, compareIndex, onStunnedIndex, STUN_TYPE, "LONG");
        FieldInsnNode noneField = uniqueFieldReadBetween(pre, compareIndex, onStunnedIndex, STUN_TYPE, "NONE");
        require(index(pre, shortField) < index(pre, longField) && index(pre, longField) < index(pre, noneField),
            "SHORT/LONG -> NONE comparison sequence drifted");
        require(nextReal(shortField).getOpcode() == Opcodes.IF_ACMPEQ, "SHORT decision branch drifted");
        require(nextReal(longField).getOpcode() == Opcodes.IF_ACMPNE, "LONG decision branch drifted");
        VarInsnNode stunStore = requireVar(nextReal(noneField), Opcodes.ASTORE, "effective stunType store");
        require(stunStore.var == stunType.index, "NONE result no longer overwrites same stunType local");

        AbstractInsnNode canceledJumpNode = nextReal(isCanceled);
        require(canceledJumpNode instanceof JumpInsnNode canceledJump && canceledJump.getOpcode() == Opcodes.IFEQ,
            "StunnedEvent cancellation branch drifted");
        require(nextReal(canceledJumpNode).getOpcode() == Opcodes.RETURN,
            "canceled ON_STUNNED must still return before shield mutation");
        int resumeIndex = index(pre, ((JumpInsnNode)canceledJumpNode).label);
        require(resumeIndex < damageIndex, "non-canceled branch no longer resumes before damageStunShield");

        for (AbstractInsnNode node = nextReal(((JumpInsnNode)canceledJumpNode).label);
             node != null && node != damageShield;
             node = nextReal(node)) {
            require(node.getOpcode() == Opcodes.ALOAD || node.getOpcode() == Opcodes.FLOAD,
                "new operation appeared inside approved commit window before damageStunShield: opcode=" + node.getOpcode());
        }

        VarInsnNode impactArg = requireVar(previousReal(damageShield), Opcodes.FLOAD, "damageStunShield impact argument");
        VarInsnNode damageArg = requireVar(previousReal(impactArg), Opcodes.FLOAD, "damageStunShield damage argument");
        VarInsnNode patchArg = requireVar(previousReal(damageArg), Opcodes.ALOAD, "damageStunShield patch receiver");
        require(impactArg.var == impact.index && damageArg.var == finalDamage.index && patchArg.var == hitPatch.index,
            "damageStunShield no longer consumes the captured frame locals");

        MethodInsnNode applyStun = uniqueCall(pre, HURTABLE, "applyStun", "(L" + STUN_TYPE + ";F)Z");
        int applyIndex = index(pre, applyStun);
        require(applyIndex > damageIndex, "stun resolution moved before shield mutation");
        require(findTableSwitchBetween(pre, damageIndex, applyIndex) != null, "stun/knockback switch missing after shield mutation");
        require(countVarLoadBetween(pre, damageIndex, applyIndex, Opcodes.FLOAD, impact.index) >= 1,
            "downstream stun/knockback no longer consumes the same impact local");
    }

    private static void proveNativeStaminaPrimitive(Map<String, ClassNode> classes) {
        ClassNode patch = requireClass(classes, PLAYER_PATCH);
        MethodNode has = method(patch, "hasStamina", "(F)Z");
        require(countCall(has, PLAYER_PATCH, "getStamina", "()F") == 1, "hasStamina no longer reads native stamina once");

        MethodNode set = method(patch, "setStamina", "(F)V");
        require(countCall(set, "net/minecraft/util/Mth", "clamp", "(FFF)F") == 1,
            "setStamina no longer clamps through native provider primitive");
        require(countCall(set, "yesman/epicfight/world/entity/data/ExpandedSyncedData", "set",
            "(Lnet/neoforged/neoforge/registries/DeferredHolder;Ljava/lang/Object;)V") == 1,
            "setStamina no longer writes provider synced stamina state directly");

        MethodNode reset = method(patch, "resetActionTick", "()V");
        require(reset.instructions.iterator().hasNext(), "resetActionTick unexpectedly empty");
    }

    private static void proveEnduranceDimensionalEquivalence(Map<String, ClassNode> classes) {
        ClassNode endurance = requireClass(classes, ENDURANCE);
        MethodNode listener = method(endurance, "lambda$onInitiate$0",
            "(Lyesman/epicfight/skill/SkillContainer;Lyesman/epicfight/api/event/types/entity/TakeDamageEvent$Pre;)V");
        LocalVariableNode consumption = local(listener, "staminaConsumption", "F");
        MethodInsnNode consume = uniqueCall(listener, PLAYER_PATCH, "consumeForSkill",
            "(L" + SKILL + ";L" + RESOURCE + ";F)Z");
        VarInsnNode consumeAmount = requireVar(previousReal(consume), Opcodes.FLOAD, "Endurance stamina amount");
        require(consumeAmount.var == consumption.index, "Endurance debit no longer uses staminaConsumption local");
        MethodInsnNode putFloat = uniqueCall(listener, COMPOUND_TAG, "putFloat", "(Ljava/lang/String;F)V");
        VarInsnNode taggedAmount = requireVar(previousReal(putFloat), Opcodes.FLOAD, "Endurance shield transfer amount");
        require(taggedAmount.var == consumption.index, "Endurance no longer transfers same nominal stamina amount");

        MethodNode execute = method(endurance, "executeOnServer",
            "(Lyesman/epicfight/skill/SkillContainer;Lnet/minecraft/nbt/CompoundTag;)V");
        LocalVariableNode staminaConsume = local(execute, "staminaConsume", "F");
        MethodInsnNode setMax = uniqueCall(execute, PLAYER_PATCH, "setMaxStunShield", "(F)V");
        MethodInsnNode setShield = uniqueCall(execute, PLAYER_PATCH, "setStunShield", "(F)V");
        require(requireVar(previousReal(setMax), Opcodes.FLOAD, "setMaxStunShield amount").var == staminaConsume.index,
            "max stun shield no longer receives exact transferred nominal amount");
        require(requireVar(previousReal(setShield), Opcodes.FLOAD, "setStunShield amount").var == staminaConsume.index,
            "stun shield no longer receives exact transferred nominal amount");
    }

    private static LocalVariableNode local(MethodNode method, String name, String desc) {
        require(method.localVariables != null, "missing LocalVariableTable for " + method.name);
        return method.localVariables.stream().filter(v -> v.name.equals(name) && v.desc.equals(desc)).findFirst()
            .orElseThrow(() -> new AssertionError("missing LVT local " + name + ':' + desc + " in " + method.name));
    }

    private static MethodInsnNode uniqueCall(MethodNode method, String owner, String name, String desc) {
        MethodInsnNode found = null;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof MethodInsnNode call && call.owner.equals(owner) && call.name.equals(name) && call.desc.equals(desc)) {
                require(found == null, "duplicate call " + owner + '#' + name + desc + " in " + method.name);
                found = call;
            }
        }
        if (found == null) throw new AssertionError("missing call " + owner + '#' + name + desc + " in " + method.name);
        return found;
    }

    private static int countCall(MethodNode method, String owner, String name, String desc) {
        int count = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof MethodInsnNode call && call.owner.equals(owner) && call.name.equals(name) && call.desc.equals(desc)) count++;
        }
        return count;
    }

    private static FieldInsnNode uniqueFieldRead(MethodNode method, String owner, String name, String desc) {
        FieldInsnNode found = null;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                && field.owner.equals(owner) && field.name.equals(name) && field.desc.equals(desc)) {
                require(found == null, "duplicate field read " + owner + '.' + name);
                found = field;
            }
        }
        if (found == null) throw new AssertionError("missing field read " + owner + '.' + name);
        return found;
    }

    private static FieldInsnNode uniqueFieldReadBetween(MethodNode method, int from, int to, String owner, String name) {
        FieldInsnNode found = null;
        for (int i = from + 1; i < to; i++) {
            AbstractInsnNode insn = method.instructions.get(i);
            if (insn instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                && field.owner.equals(owner) && field.name.equals(name)) {
                require(found == null, "duplicate field read " + owner + '.' + name + " in comparison region");
                found = field;
            }
        }
        if (found == null) throw new AssertionError("missing comparison field " + owner + '.' + name);
        return found;
    }

    private static MethodInsnNode firstCallBetween(MethodNode method, int from, int to, String owner, String name) {
        for (int i = from + 1; i < to; i++) {
            AbstractInsnNode insn = method.instructions.get(i);
            if (insn instanceof MethodInsnNode call && call.owner.equals(owner) && call.name.equals(name)) return call;
        }
        throw new AssertionError("missing call " + owner + '#' + name + " in required region");
    }

    private static int findOpcodeBetween(MethodNode method, int from, int to, int opcode) {
        for (int i = from + 1; i < to; i++) if (method.instructions.get(i).getOpcode() == opcode) return i;
        return -1;
    }

    private static TableSwitchInsnNode findTableSwitchBetween(MethodNode method, int from, int to) {
        for (int i = from + 1; i < to; i++) if (method.instructions.get(i) instanceof TableSwitchInsnNode table) return table;
        return null;
    }

    private static int countVarLoadBetween(MethodNode method, int from, int to, int opcode, int var) {
        int count = 0;
        for (int i = from + 1; i < to; i++) {
            if (method.instructions.get(i) instanceof VarInsnNode node && node.getOpcode() == opcode && node.var == var) count++;
        }
        return count;
    }

    private static VarInsnNode requireVar(AbstractInsnNode node, int opcode, String label) {
        require(node instanceof VarInsnNode var && var.getOpcode() == opcode, label + " drifted");
        return (VarInsnNode) node;
    }

    private static AbstractInsnNode nextReal(AbstractInsnNode node) {
        for (AbstractInsnNode n = node == null ? null : node.getNext(); n != null; n = n.getNext()) {
            if (n.getOpcode() >= 0) return n;
        }
        return null;
    }

    private static AbstractInsnNode previousReal(AbstractInsnNode node) {
        for (AbstractInsnNode n = node == null ? null : node.getPrevious(); n != null; n = n.getPrevious()) {
            if (n.getOpcode() >= 0) return n;
        }
        return null;
    }

    private static int index(MethodNode method, AbstractInsnNode node) {
        int index = method.instructions.indexOf(node);
        require(index >= 0, "instruction not in method " + method.name);
        return index;
    }

    private static MethodNode method(ClassNode owner, String name, String desc) {
        return owner.methods.stream().filter(m -> m.name.equals(name) && m.desc.equals(desc)).findFirst()
            .orElseThrow(() -> new AssertionError("missing method " + owner.name + '#' + name + desc));
    }

    private static ClassNode requireClass(Map<String, ClassNode> classes, String name) {
        ClassNode node = classes.get(name);
        if (node == null) throw new AssertionError("missing class " + name);
        return node;
    }

    private static Map<String, ClassNode> readClasses(JarFile jar) throws Exception {
        Map<String, ClassNode> classes = new HashMap<>();
        var entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
            try (InputStream input = jar.getInputStream(entry)) {
                ClassNode node = new ClassNode();
                new ClassReader(input).accept(node, ClassReader.SKIP_FRAMES);
                classes.put(node.name, node);
            }
        }
        return classes;
    }

    private static String sha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(path)) {
            byte[] buffer = new byte[64 * 1024];
            for (int read; (read = input.read(buffer)) >= 0;) if (read > 0) digest.update(buffer, 0, read);
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
