package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy.PhysicalModifiers;
import dev.gustavopere.rpgskilltree.core.A0061A0080CombatPolicy.SpecialResult;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.runtime.A0061A0080RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier;
import dev.gustavopere.rpgskilltree.runtime.MartialTargetClassifier.TargetClass;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/**
 * Server-authoritative Epic Fight bridge for the general MARTIAL foundations A0061-A0080.
 *
 * <p>A0067, A0075 and A0080 deliberately remain fail-closed here: the current provider surface
 * does not prove, respectively, a safe offensive stun-armor window, the mandatory Cold Sweat
 * metabolic receipt, or that a dodge actually avoided an otherwise-hitting hostile attack.</p>
 */
public final class A0061A0080EpicFightHooks {
    public static final String SUPPORTED_VERSION_PREFIX = A0001A0020EpicFightHooks.SUPPORTED_VERSION_PREFIX;
    private static final String PRE_ID = "rpgskilltree:a0061_a0080/pre";
    private static final String POST_ID = "rpgskilltree:a0061_a0080/post";
    private static final TagKey<Item> HAMMERS = tag("hammers");
    private static final TagKey<Item> MACES = tag("maces");
    private static final TagKey<Item> SCYTHES = tag("scythes");
    private static final WeakHashMap<EpicFightDamageSource, Map<String, String>> ROOT_ACTIONS = new WeakHashMap<>();
    private static final AtomicLong ACTION_SEQUENCE = new AtomicLong();
    private static boolean registered;

    private A0061A0080EpicFightHooks() {}

    public static boolean supportsVersion(String version) {
        return version != null && version.startsWith(SUPPORTED_VERSION_PREFIX);
    }

    public static synchronized void register() {
        if (registered) return;
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(A0061A0080EpicFightHooks::onDamagePre, PRE_ID);
        EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(A0061A0080EpicFightHooks::onDamagePost, POST_ID);
        registered = true;
    }

    private static void onDamagePre(DealDamageEvent.Pre event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player) || !eligible(player)) return;
        LivingEntity target = event.getTarget();
        EpicFightDamageSource source = event.getDamageSource();
        if (source.getDirectEntity() != player || !hostile(player, target) || !physicalMelee(source.getUsedItem())) return;

        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        if (!hasRuntimeEffect(ranks)) return;

        String actor = A0061A0080RuntimeState.actorId(player);
        String targetId = target.getUUID().toString();
        long now = now(player);
        String root = rootAction(source, targetId, now);
        TargetClass targetClass = MartialTargetClassifier.classify(target);
        double healthFraction = healthFraction(target);

        A0061A0080CombatPolicy.HitFacts facts = new A0061A0080CombatPolicy.HitFacts(
            actor,
            targetId,
            root,
            healthFraction,
            targetClass == TargetClass.BOSS,
            targetClass == TargetClass.ELITE,
            player.isSprinting(),
            A0061A0080RuntimeState.isStationary(player),
            false,
            true,
            true,
            now
        );
        PhysicalModifiers base = A0061A0080CombatPolicy.beforePhysicalHit(
            facts, ranks, A0061A0080RuntimeState.state()
        );
        SpecialResult execution = A0061A0080CombatPolicy.execution(
            actor, targetId, root, healthFraction, targetClass == TargetClass.BOSS,
            ranks, A0061A0080RuntimeState.state(), true, now
        );
        SpecialResult firstBlood = A0061A0080CombatPolicy.firstBlood(
            actor, targetId, root, healthFraction, ranks, A0061A0080RuntimeState.state(), true, now
        );
        double opportunity = A0061A0080CombatPolicy.consumeOpportunityDamageMultiplier(
            actor, root, ranks, A0061A0080RuntimeState.state(), now
        );

        double damage = base.damageMultiplier()
            * execution.damageMultiplier()
            * firstBlood.damageMultiplier()
            * opportunity;
        if (Double.compare(damage, 1.0D) != 0) {
            source.attachDamageModifier(ValueModifier.multiplier((float) damage));
        }
        if (base.penetrationFraction() > 0.0D) {
            source.attachArmorNegationModifier(ValueModifier.adder((float) (base.penetrationFraction() * 100.0D)));
        }
        double impact = base.impactMultiplier()
            * execution.impactMultiplier()
            * firstBlood.impactMultiplier();
        if (Double.compare(impact, 1.0D) != 0) {
            source.attachImpactModifier(ValueModifier.multiplier((float) impact));
        }

        // A0075 is intentionally not recorded here. Until STAMINA_REGEN, Cold Sweat metabolic
        // heat, and vanilla exhaustion are all proven operational on the same action, the Notion
        // contract requires all qualifiers and the benefit to remain inactive.
    }

    private static void onDamagePost(DealDamageEvent.Post event) {
        if (!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)) return;
        forget(event.getDamageSource(), event.getTarget().getUUID().toString());
    }

    /** A0072: only positive post-mitigation direct hostile damage opens/refreshes retaliation. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onDirectHostileDamageTaken(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
            || !eligible(player)
            || event.getNewDamage() <= 0.0F
            || !(event.getSource().getDirectEntity() instanceof LivingEntity attacker)
            || event.getSource().getEntity() != attacker
            || !hostile(player, attacker)) return;

        CombatPerkRanks ranks = A0061A0080RuntimeState.ranks(player);
        if (ranks.rank("A0072") <= 0) return;
        long now = now(player);
        String causal = "hurt/" + player.level().getGameTime() + "/" + ACTION_SEQUENCE.incrementAndGet();
        A0061A0080CombatPolicy.onDirectHostileDamageTaken(
            A0061A0080RuntimeState.actorId(player), causal, event.getNewDamage(), true,
            ranks, A0061A0080RuntimeState.state(), now
        );
    }

    /** A0079 canonical stationary sampling. */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            if (!eligible(player)) continue;
            A0061A0080RuntimeState.stationary().sample(
                A0061A0080RuntimeState.actorId(player),
                player.getX(), player.getY(), player.getZ(), false
            );
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) clearPlayer(player);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        A0061A0080RuntimeState.clearAll();
        synchronized (A0061A0080EpicFightHooks.class) {
            ROOT_ACTIONS.clear();
        }
    }

    private static boolean physicalMelee(ItemStack stack) {
        CapabilityItem capability = EpicFightCapabilities.getItemStackCapability(stack);
        if (capability != null && !capability.isEmpty()) {
            String category = EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());
            int slash = category.lastIndexOf('/');
            if (slash >= 0 && slash + 1 < category.length()) category = category.substring(slash + 1);
            if (switch (category) {
                case "sword", "longsword", "greatsword", "tachi", "uchigatana",
                     "axe", "spear", "dagger", "hammer", "mace", "scythe", "fist", "knuckle" -> true;
                default -> false;
            }) return true;
        }
        return stack.is(HAMMERS) || stack.is(MACES) || stack.is(SCYTHES);
    }

    private static boolean hasRuntimeEffect(CombatPerkRanks ranks) {
        for (int i = 61; i <= 80; i++) {
            if (ranks.rank("A%04d".formatted(i)) > 0) return true;
        }
        return false;
    }

    private static synchronized String rootAction(EpicFightDamageSource source, String targetId, long now) {
        Map<String, String> byTarget = ROOT_ACTIONS.computeIfAbsent(source, ignored -> new HashMap<>());
        return byTarget.computeIfAbsent(targetId,
            ignored -> "martial/" + now + "/" + ACTION_SEQUENCE.incrementAndGet());
    }

    private static synchronized void forget(EpicFightDamageSource source, String targetId) {
        Map<String, String> byTarget = ROOT_ACTIONS.get(source);
        if (byTarget == null) return;
        byTarget.remove(targetId);
        if (byTarget.isEmpty()) ROOT_ACTIONS.remove(source);
    }

    private static void clearPlayer(ServerPlayer player) {
        A0061A0080RuntimeState.clear(player);
    }

    private static double healthFraction(LivingEntity target) {
        return target.getMaxHealth() <= 0.0F
            ? 0.0D
            : Math.max(0.0D, Math.min(1.0D, target.getHealth() / target.getMaxHealth()));
    }

    private static boolean hostile(ServerPlayer player, LivingEntity target) {
        return target != player
            && target.isAlive()
            && !player.isAlliedTo(target)
            && !target.isInvulnerable()
            && (target instanceof Enemy || target instanceof Player);
    }

    private static boolean eligible(ServerPlayer player) {
        return !player.level().isClientSide()
            && !player.isCreative()
            && !player.isSpectator()
            && !(player instanceof FakePlayer);
    }

    private static long now(ServerPlayer player) {
        return Math.multiplyExact(player.level().getGameTime(), 50L);
    }

    private static TagKey<Item> tag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID, path));
    }
}
