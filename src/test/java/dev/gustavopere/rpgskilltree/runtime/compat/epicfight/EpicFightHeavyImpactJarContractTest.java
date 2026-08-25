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
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

/** Binary contract for the exact Epic Fight provider path used by P-0002. */
public final class EpicFightHeavyImpactJarContractTest {
    private static final String EVENT_HOOKS_ENTITY = "yesman/epicfight/api/event/EpicFightEventHooks$Entity";
    private static final String EVENT_HOOK = "yesman/epicfight/api/event/EventHook";
    private static final String VANILLA_HOOKS = "yesman/epicfight/api/event/impl/VanillaEntityEventHooks";
    private static final String LIVING_PATCH = "yesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch";
    private static final String HURTABLE_PATCH = "yesman/epicfight/world/capabilities/entitypatch/HurtableEntityPatch";
    private static final String STUN_TYPE = "yesman/epicfight/world/damagesource/StunType";
    private static final String APPLY_STUN_EVENT = "yesman/epicfight/api/event/types/entity/ApplyStunEvent";
    private static final String TAKE_DAMAGE_PRE = "yesman/epicfight/api/event/types/entity/TakeDamageEvent$Pre";
    private static final String TAKE_DAMAGE_POST = "yesman/epicfight/api/event/types/entity/TakeDamageEvent$Post";

    private EpicFightHeavyImpactJarContractTest() {}

    public static void main(String[] args) throws Exception {
        require(args.length == 2, "usage: <epicfight.jar> <expectedSha256>");
        Path jarPath = Path.of(args[0]).toAbsolutePath().normalize();
        String expectedSha = args[1].trim().toLowerCase();
        require(Files.isRegularFile(jarPath), "missing Epic Fight JAR: " + jarPath);
        String actualSha = sha256(jarPath);
        require(actualSha.equals(expectedSha), "Epic Fight SHA-256 drift: expected=" + expectedSha + " actual=" + actualSha);

        try (JarFile jar = new JarFile(jarPath.toFile())) {
            Map<String, ClassNode> classes = readClasses(jar);
            proveServerEventHooks(classes);
            proveStunEnum(classes);
            provePreToApplyStunPath(classes);
            proveApplyStunPublishesFinalType(classes);
            provePostEventExistsInDamagePipeline(classes);
        }

        System.out.println("EpicFightHeavyImpactJarContractTest: PASS");
        System.out.println("EPICFIGHT_HEAVY_IMPACT_SHA256=" + actualSha);
    }

    private static void proveServerEventHooks(Map<String, ClassNode> classes) {
        ClassNode hooks = requireClass(classes, EVENT_HOOKS_ENTITY);
        requireField(hooks, "TAKE_DAMAGE_PRE", 'L' + EVENT_HOOK + ';');
        requireField(hooks, "APPLY_STUN", 'L' + EVENT_HOOK + ';');
        requireField(hooks, "TAKE_DAMAGE_POST", 'L' + EVENT_HOOK + ';');
    }

    private static void proveStunEnum(Map<String, ClassNode> classes) {
        ClassNode stun = requireClass(classes, STUN_TYPE);
        for (String name : new String[] {"NONE", "SHORT", "LONG", "HOLD", "KNOCKDOWN", "NEUTRALIZE", "FALL"}) {
            FieldNode field = stun.fields.stream().filter(f -> f.name.equals(name)).findFirst()
                .orElseThrow(() -> new AssertionError("missing StunType." + name));
            require((field.access & Opcodes.ACC_ENUM) != 0, "StunType." + name + " is no longer an enum constant");
        }
    }

    private static void provePreToApplyStunPath(Map<String, ClassNode> classes) {
        ClassNode hooks = requireClass(classes, VANILLA_HOOKS);
        MethodNode pre = method(hooks, "onCalculateDamagePre",
            "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;FLjava/util/function/Consumer;)V");
        require(countNew(pre, TAKE_DAMAGE_PRE) == 1, "TAKE_DAMAGE_PRE construction drifted");
        require(countFieldRead(pre, EVENT_HOOKS_ENTITY, "TAKE_DAMAGE_PRE") == 1,
            "TAKE_DAMAGE_PRE hook publication drifted");
        require(countCall(pre, HURTABLE_PATCH, "applyStun", "(L" + STUN_TYPE + ";F)Z") == 1,
            "final applyStun call drifted");
    }

    private static void proveApplyStunPublishesFinalType(Map<String, ClassNode> classes) {
        ClassNode patch = requireClass(classes, LIVING_PATCH);
        MethodNode apply = method(patch, "applyStun", "(L" + STUN_TYPE + ";F)Z");
        require(countNew(apply, APPLY_STUN_EVENT) == 1, "ApplyStunEvent construction drifted");
        require(countFieldRead(apply, EVENT_HOOKS_ENTITY, "APPLY_STUN") == 1,
            "APPLY_STUN publication drifted");
        require(countCall(apply, LIVING_PATCH, "playAnimationSynchronized",
            "(Lyesman/epicfight/api/asset/AssetAccessor;F)V") == 1,
            "applyStun no longer proceeds through synchronized stun animation");

        ClassNode event = requireClass(classes, APPLY_STUN_EVENT);
        require(event.methods.stream().anyMatch(m -> m.name.equals("getStunType") && m.desc.equals("()L" + STUN_TYPE + ";")),
            "ApplyStunEvent no longer exposes getStunType()");
        require(event.methods.stream().noneMatch(m -> m.name.toLowerCase().contains("setstuntype")),
            "ApplyStunEvent unexpectedly gained a stun-type mutator; re-audit final classification semantics");
    }

    private static void provePostEventExistsInDamagePipeline(Map<String, ClassNode> classes) {
        ClassNode hooks = requireClass(classes, VANILLA_HOOKS);
        method(hooks, "onCalculateDamagePost",
            "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;F)V");
        int newPost = 0;
        int postHookReads = 0;
        for (MethodNode method : hooks.methods) {
            newPost += countNew(method, TAKE_DAMAGE_POST);
            postHookReads += countFieldRead(method, EVENT_HOOKS_ENTITY, "TAKE_DAMAGE_POST");
        }
        require(newPost == 1, "expected exactly one TAKE_DAMAGE_POST construction in VanillaEntityEventHooks, found " + newPost);
        require(postHookReads == 1, "expected exactly one TAKE_DAMAGE_POST publication in VanillaEntityEventHooks, found " + postHookReads);
    }

    private static int countNew(MethodNode method, String owner) {
        int count = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof TypeInsnNode type && type.getOpcode() == Opcodes.NEW && type.desc.equals(owner)) count++;
        }
        return count;
    }

    private static int countFieldRead(MethodNode method, String owner, String name) {
        int count = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                && field.owner.equals(owner) && field.name.equals(name)) count++;
        }
        return count;
    }

    private static int countCall(MethodNode method, String owner, String name, String desc) {
        int count = 0;
        for (AbstractInsnNode insn : method.instructions) {
            if (insn instanceof MethodInsnNode call && call.owner.equals(owner)
                && call.name.equals(name) && call.desc.equals(desc)) count++;
        }
        return count;
    }

    private static MethodNode method(ClassNode owner, String name, String desc) {
        return owner.methods.stream().filter(m -> m.name.equals(name) && m.desc.equals(desc)).findFirst()
            .orElseThrow(() -> new AssertionError("missing method " + owner.name + '#' + name + desc));
    }

    private static void requireField(ClassNode owner, String name, String desc) {
        require(owner.fields.stream().anyMatch(f -> f.name.equals(name) && f.desc.equals(desc)),
            "missing field " + owner.name + '.' + name + ':' + desc);
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
                new ClassReader(input).accept(node, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                classes.put(node.name, node);
            }
        }
        return classes;
    }

    private static String sha256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream input = Files.newInputStream(path)) {
            byte[] buffer = new byte[64 * 1024];
            for (int read; (read = input.read(buffer)) >= 0;) {
                if (read > 0) digest.update(buffer, 0, read);
            }
        }
        return HexFormat.of().formatHex(digest.digest());
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
