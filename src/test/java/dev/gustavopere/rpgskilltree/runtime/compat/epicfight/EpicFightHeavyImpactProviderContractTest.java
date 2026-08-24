package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.core.HeavyImpactReceiptCorrelation;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import yesman.epicfight.api.event.types.entity.TakeDamageEvent;
import yesman.epicfight.world.damagesource.StunType;

/** Isolated provider contract for P-0002; intentionally does not require the general test suite. */
public final class EpicFightHeavyImpactProviderContractTest {
    private static final String HOOKS =
        "dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightCombatPerkHooks";
    private static final String BRIDGE =
        "dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightHeavyImpactReceiptBridge";
    private static final String POLICY =
        "dev/gustavopere/rpgskilltree/core/CombatPerkTransitionPolicy";

    public static void main(String[] args) throws Exception {
        Class<?> bridge = Class.forName(
            "dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightHeavyImpactReceiptBridge"
        );
        bridge.getMethod("register");
        bridge.getMethod("peekConfirmedHeavyImpact", TakeDamageEvent.Post.class);
        bridge.getMethod("claimConfirmedHeavyImpact", TakeDamageEvent.Post.class, String.class);
        bridge.getMethod("clearTransientState");

        Method normalize = bridge.getDeclaredMethod("normalizeStunType", StunType.class);
        normalize.setAccessible(true);
        require(normalize.invoke(null, StunType.LONG) == HeavyImpactReceiptCorrelation.ImpactKind.LONG_STUN,
            "LONG must normalize to LONG_STUN");
        require(normalize.invoke(null, StunType.KNOCKDOWN) == HeavyImpactReceiptCorrelation.ImpactKind.KNOCKDOWN,
            "KNOCKDOWN must remain heavy");
        require(normalize.invoke(null, StunType.NEUTRALIZE) == HeavyImpactReceiptCorrelation.ImpactKind.NEUTRALIZE,
            "NEUTRALIZE must remain heavy");
        require(normalize.invoke(null, StunType.SHORT) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "SHORT must fail closed");
        require(normalize.invoke(null, StunType.HOLD) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "raw HOLD must fail closed; provider normally resolves it to SHORT before APPLY_STUN");
        require(normalize.invoke(null, StunType.NONE) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "NONE must fail closed");
        require(normalize.invoke(null, StunType.FALL) == HeavyImpactReceiptCorrelation.ImpactKind.LIGHT,
            "FALL must not be treated as combat heavy impact");

        require(Optional.class.isAssignableFrom(
                bridge.getMethod("peekConfirmedHeavyImpact", TakeDamageEvent.Post.class).getReturnType()),
            "peek API must return Optional receipt evidence");
        require(Optional.class.isAssignableFrom(
                bridge.getMethod("claimConfirmedHeavyImpact", TakeDamageEvent.Post.class, String.class).getReturnType()),
            "claim API must return Optional receipt evidence");

        Class<?> lifecycle = Class.forName(
            "dev.gustavopere.rpgskilltree.runtime.compat.epicfight.EpicFightHeavyImpactReceiptLifecycleEvents"
        );
        lifecycle.getMethod("onLogin", PlayerEvent.PlayerLoggedInEvent.class);
        lifecycle.getMethod("onLogout", PlayerEvent.PlayerLoggedOutEvent.class);
        lifecycle.getMethod("onRespawn", PlayerEvent.PlayerRespawnEvent.class);
        lifecycle.getMethod("onDeath", LivingDeathEvent.class);
        lifecycle.getMethod("onServerTick", ServerTickEvent.Post.class);

        provePerkConsumerWiring();
        System.out.println("EpicFightHeavyImpactProviderContractTest: PASS");
    }

    private static void provePerkConsumerWiring() throws Exception {
        ClassNode hooks = readClass(HOOKS);
        MethodNode register = method(hooks, "register", "()V");
        boolean registeredOnTakeDamagePost = false;
        for (var insn : register.instructions) {
            if (insn instanceof FieldInsnNode field
                && field.getOpcode() == Opcodes.GETSTATIC
                && field.owner.equals("yesman/epicfight/api/event/EpicFightEventHooks$Entity")
                && field.name.equals("TAKE_DAMAGE_POST")) {
                registeredOnTakeDamagePost = true;
            }
        }
        require(registeredOnTakeDamagePost,
            "combat perk adapter must reuse TAKE_DAMAGE_POST rather than inventing a competing provider hook");

        MethodNode consumer = method(
            hooks,
            "onIncomingHeavyImpact",
            "(Lyesman/epicfight/api/event/types/entity/TakeDamageEvent$Post;)V"
        );
        Set<String> strings = new HashSet<>();
        int claimCalls = 0;
        Set<String> policyCalls = new HashSet<>();
        for (var insn : consumer.instructions) {
            if (insn instanceof LdcInsnNode ldc && ldc.cst instanceof String text) strings.add(text);
            if (insn instanceof MethodInsnNode call) {
                if (call.owner.equals(BRIDGE) && call.name.equals("claimConfirmedHeavyImpact")) claimCalls++;
                if (call.owner.equals(POLICY) && call.name.startsWith("applyA") && call.name.endsWith("ConfirmedHeavyImpact")) {
                    policyCalls.add(call.name);
                }
            }
        }

        require(claimCalls == 4, "exactly four mechanically independent perk consumers must claim the receipt");
        require(strings.containsAll(Set.of(
            "rpgskilltree:a0004_momentum_heavy",
            "rpgskilltree:a0016_distance_control_heavy",
            "rpgskilltree:a0022_flow_heavy",
            "rpgskilltree:a0046_focus_heavy_impact"
        )), "all four stable consumer ids must be embedded in the adapter");
        require(policyCalls.equals(Set.of(
            "applyA0004ConfirmedHeavyImpact",
            "applyA0016ConfirmedHeavyImpact",
            "applyA0022ConfirmedHeavyImpact",
            "applyA0046ConfirmedHeavyImpact"
        )), "each claimed receipt must dispatch to exactly its own perk consumer policy");
    }

    private static ClassNode readClass(String internalName) throws Exception {
        String resource = internalName + ".class";
        try (InputStream input = EpicFightHeavyImpactProviderContractTest.class.getClassLoader().getResourceAsStream(resource)) {
            require(input != null, "missing compiled class resource: " + resource);
            ClassNode node = new ClassNode();
            new ClassReader(input).accept(node, 0);
            return node;
        }
    }

    private static MethodNode method(ClassNode owner, String name, String descriptor) {
        return owner.methods.stream()
            .filter(method -> method.name.equals(name) && method.desc.equals(descriptor))
            .findFirst()
            .orElseThrow(() -> new AssertionError("missing method " + owner.name + "." + name + descriptor));
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
