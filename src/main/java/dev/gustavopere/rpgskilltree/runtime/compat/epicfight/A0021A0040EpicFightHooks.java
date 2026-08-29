package dev.gustavopere.rpgskilltree.runtime.compat.epicfight;

import dev.gustavopere.rpgskilltree.RpgSkillTreeMod;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy.BeforeResult;
import dev.gustavopere.rpgskilltree.core.A0021A0040CombatPolicy.HitFacts;
import dev.gustavopere.rpgskilltree.core.CombatPerkDefinition.WeaponFamily;
import dev.gustavopere.rpgskilltree.core.CombatPerkNodeBinding;
import dev.gustavopere.rpgskilltree.core.CombatPerkRanks;
import dev.gustavopere.rpgskilltree.core.EpicFightWeaponCategory;
import dev.gustavopere.rpgskilltree.core.NotionCombatPerkRules;
import dev.gustavopere.rpgskilltree.core.ProgressionState;
import dev.gustavopere.rpgskilltree.runtime.A0001A0020RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.A0021A0040RuntimeState;
import dev.gustavopere.rpgskilltree.runtime.PlayerProgressionRuntime;
import dev.gustavopere.rpgskilltree.runtime.client.ClientProgressionState;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.api.event.types.entity.DodgeEvent;
import yesman.epicfight.api.event.types.entity.ModifyAttackSpeedEvent;
import yesman.epicfight.api.event.types.player.SkillConsumeEvent;
import yesman.epicfight.api.event.types.player.TickPlayerEpicFightModeEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

/** Runtime bridge for the closed A0021-A0040 batch. Unsafe provider semantics stay fail-closed. */
public final class A0021A0040EpicFightHooks {
    public static final String SUPPORTED_VERSION_PREFIX = A0001A0020EpicFightHooks.SUPPORTED_VERSION_PREFIX;
    private static final String PRE_ID="rpgskilltree:a0021_a0040/pre",POST_ID="rpgskilltree:a0021_a0040/post",SPEED_ID="rpgskilltree:a0021_a0040/speed",DODGE_ID="rpgskilltree:a0021_a0040/dodge",SKILL_ID="rpgskilltree:a0021_a0040/skill",TICK_ID="rpgskilltree:a0021_a0040/tick";
    private static final TagKey<Item> HAMMERS=tag("hammers"),MACES=tag("maces"),SCYTHES=tag("scythes");
    private static final ResourceLocation ARMOR_SUNDER_ID=ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID,"a0035_armor_sunder");
    private static final long CRITICAL_CORRELATION_MILLIS=100L;
    private static final WeakHashMap<EpicFightDamageSource,Map<String,PendingHit>> PENDING=new WeakHashMap<>();
    private static final Map<String,RecentCritical> RECENT_CRITICAL=new HashMap<>();
    private static final Map<String,Boolean> REAR_STATE=new HashMap<>();
    private static final Map<String,PendingVanilla> VANILLA_PENDING=new HashMap<>();
    private static final Map<UUID,Long> ARMOR_SUNDER_EXPIRES=new HashMap<>();
    private static final AtomicLong ACTION_SEQUENCE=new AtomicLong();
    private static boolean registered;
    private A0021A0040EpicFightHooks(){}

    public static boolean supportsVersion(String version){return version!=null&&version.startsWith(SUPPORTED_VERSION_PREFIX);}
    public static synchronized void register(){if(registered)return;EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE.registerEvent(A0021A0040EpicFightHooks::onDamagePre,PRE_ID);EpicFightEventHooks.Entity.DELIVER_DAMAGE_POST.registerEvent(A0021A0040EpicFightHooks::onDamagePost,POST_ID);EpicFightEventHooks.Entity.MODIFY_ATTACK_SPEED.registerEvent(A0021A0040EpicFightHooks::onAttackSpeed,SPEED_ID);EpicFightEventHooks.Entity.ON_DODGE.registerEvent(A0021A0040EpicFightHooks::onDodge,DODGE_ID);EpicFightEventHooks.Player.CONSUME_SKILL.registerEvent(A0021A0040EpicFightHooks::onSkillConsume,SKILL_ID);EpicFightEventHooks.Player.TICK_EPICFIGHT_MODE.registerEvent(A0021A0040EpicFightHooks::onEpicFightTick,TICK_ID);registered=true;}

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void onCriticalHit(CriticalHitEvent event){
        if(!(event.getEntity() instanceof ServerPlayer player)||!eligible(player)||!(event.getTarget() instanceof LivingEntity target)||!hostile(player,target))return;
        Optional<WeaponFamily> family=family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()),player.getMainHandItem());if(family.isEmpty()||!isBatchFamily(family.get()))return;
        long now=now(player);String actor=actor(player),targetId=target.getUUID().toString();
        if(family.get()==WeaponFamily.DAGGER){rememberCritical(actor,targetId,"observed-dagger/"+now+"/"+ACTION_SEQUENCE.incrementAndGet(),event.isCriticalHit(),true,now);return;}
        CombatPerkRanks ranks=ranks(player);double bonus=NotionCombatPerkRules.criticalChanceBonus(family.get(),ranks);boolean providerCritical=event.isCriticalHit();
        String root="critical/"+now+"/"+ACTION_SEQUENCE.incrementAndGet();boolean critical=A0001A0020RuntimeState.critical().resolve(actor,root,providerCritical,bonus,now);
        if(critical&&!providerCritical)event.setDamageMultiplier(Math.max(1.5F,event.getDamageMultiplier()));event.setCriticalHit(critical);rememberCritical(actor,targetId,root,critical,true,now);
    }

    private static void onDamagePre(DealDamageEvent.Pre event){
        if(!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)||!eligible(player))return;LivingEntity target=event.getTarget();EpicFightDamageSource source=event.getDamageSource();if(source.getDirectEntity()!=player||!hostile(player,target))return;
        Optional<WeaponFamily> family=family(EpicFightCapabilities.getItemStackCapability(source.getUsedItem()),source.getUsedItem());if(family.isEmpty()||!isBatchFamily(family.get()))return;
        CombatPerkRanks ranks=ranks(player);if(ranks.ranks().isEmpty())return;long now=now(player);String targetId=target.getUUID().toString(),actor=actor(player);Root root=rootAction(source,actor,targetId,family.get(),ranks,now);
        boolean rear=family.get()==WeaponFamily.DAGGER&&rearHalfPlane(player,target);boolean reposition=family.get()==WeaponFamily.DAGGER&&A0021A0040RuntimeState.state().repositionActive(actor,now);boolean protectedTarget=target.getArmorValue()>0||target.isBlocking();boolean boss=target.getType().is(Tags.EntityTypes.BOSSES);
        HitFacts facts=facts(actor,targetId,root.id,family.get(),root.critical,reposition,rear,false,protectedTarget,false,true,true,true,healthFraction(target),boss,now);
        BeforeResult specialty=A0021A0040CombatPolicy.beforeHit(facts,ranks,A0021A0040RuntimeState.state(),mastery(player,family.get()));
        double damage=specialty.damageMultiplier();if(family.get()!=WeaponFamily.DAGGER)damage*=NotionCombatPerkRules.baseDamageMultiplier(family.get(),ranks);if(root.critical&&family.get()!=WeaponFamily.DAGGER&&!root.criticalMultiplierAlreadyApplied)damage*=1.5D;
        if(Double.compare(damage,1.0D)!=0)source.attachDamageModifier(ValueModifier.multiplier((float)damage));if(specialty.physicalPenetrationFraction()>0)source.attachArmorNegationModifier(ValueModifier.adder((float)(specialty.physicalPenetrationFraction()*100.0D)));if(Double.compare(specialty.impactMultiplier(),1.0D)!=0)source.attachImpactModifier(ValueModifier.multiplier((float)specialty.impactMultiplier()));
        remember(source,targetId,new PendingHit(root.id,family.get(),root.critical,reposition,rear,protectedTarget,boss,specialty));
    }

    private static void onDamagePost(DealDamageEvent.Post event){
        if(!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)||!eligible(player))return;String targetId=event.getTarget().getUUID().toString();PendingHit pending=forget(event.getDamageSource(),targetId);if(pending==null||event.getModifiedDamage()<=0||!hostile(player,event.getTarget()))return;long now=now(player);CombatPerkRanks ranks=ranks(player);
        HitFacts facts=facts(actor(player),targetId,pending.rootActionId,pending.family,pending.critical,pending.reposition,pending.rear,false,pending.protectedTarget,false,true,true,true,healthFraction(event.getTarget()),pending.boss,now);A0021A0040CombatPolicy.afterConfirmedHit(facts,ranks,A0021A0040RuntimeState.state());
        if(pending.specialty.applyArmorSunder())applyArmorSunder(event.getTarget(),pending.specialty.armorSunderFraction(),pending.specialty.armorSunderDurationMillis(),now);
    }

    private static void onAttackSpeed(ModifyAttackSpeedEvent event){
        if(!(event.getEntityPatch().getOriginal() instanceof Player player))return;ProgressionState progression;if(player instanceof ServerPlayer sp){if(!eligible(sp))return;progression=PlayerProgressionRuntime.get(sp);}else{if(!player.isLocalPlayer())return;progression=ClientProgressionState.get();}
        Optional<WeaponFamily> family=family(event.getItemCapability(),player.getMainHandItem());if(family.isEmpty()||family.get()==WeaponFamily.DAGGER||!isBatchFamily(family.get()))return;double bonus=NotionCombatPerkRules.rhythmBonus(family.get(),CombatPerkNodeBinding.ranks(progression.passiveNodes()));if(bonus>0)event.setAttackSpeed((float)(event.getAttackSpeed()*(1+bonus)));
    }
    private static void onDodge(DodgeEvent event){if(!(event.getEntityPatch().getOriginal() instanceof ServerPlayer player)||!eligible(player))return;if(family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()),player.getMainHandItem()).orElse(null)!=WeaponFamily.DAGGER)return;A0021A0040CombatPolicy.onConfirmedDodge(actor(player),ranks(player),A0021A0040RuntimeState.state(),now(player));}
    private static void onSkillConsume(SkillConsumeEvent event){if(!(event.getEntityPatch() instanceof PlayerPatch<?> patch)||!(patch.getOriginal() instanceof ServerPlayer player)||!eligible(player)||event.getSkill()==null||event.getResourceType()!=Skill.Resource.STAMINA)return;if(family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()),player.getMainHandItem()).orElse(null)!=WeaponFamily.DAGGER)return;String category=event.getSkill().getCategory().toString().toLowerCase(Locale.ROOT);if(!category.equals("dodge")&&!category.equals("mover"))return;long now=now(player);if(A0021A0040RuntimeState.state().consumeDanceMove(actor(player),now))event.setAmount(Math.max(0,event.getAmount())*.70F);}
    private static void onEpicFightTick(TickPlayerEpicFightModeEvent event){
        if(!(event.getPlayerPatch() instanceof ServerPlayerPatch patch))return;ServerPlayer player=patch.getOriginal();if(!eligible(player))return;Optional<WeaponFamily> family=family(EpicFightCapabilities.getItemStackCapability(player.getMainHandItem()),player.getMainHandItem());if(family.orElse(null)!=WeaponFamily.DAGGER)return;
        String actor=actor(player);long now=now(player);CombatPerkRanks ranks=ranks(player);double dx=player.getDeltaMovement().x,dz=player.getDeltaMovement().z;boolean moving=dx*dx+dz*dz>1.0E-8D;if(moving)A0021A0040RuntimeState.state().recordHorizontalMovement(actor,now);A0021A0040RuntimeState.state().tickFlow(actor,true,now);
        LivingEntity target=patch.getTarget();if(target==null||!hostile(player,target))return;String key=actor+'\0'+target.getUUID();boolean rear=rearHalfPlane(player,target);Boolean previous=REAR_STATE.put(key,rear);if(moving&&Boolean.FALSE.equals(previous)&&rear)A0021A0040RuntimeState.state().armDodgeReposition(actor,now);
    }

    /** NeoForge-only fallback for explicitly tagged weapons that Epic Fight did not classify. */
    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onVanillaIncoming(LivingIncomingDamageEvent event){
        if(!(event.getSource().getDirectEntity() instanceof ServerPlayer player)||!eligible(player)||!hostile(player,event.getEntity()))return;ItemStack stack=player.getMainHandItem();CapabilityItem capability=EpicFightCapabilities.getItemStackCapability(stack);if(categoryFamily(capability).isPresent())return;Optional<WeaponFamily> family=tagFamily(stack);if(family.isEmpty()||family.get()==WeaponFamily.DAGGER)return;
        long now=now(player);String targetId=event.getEntity().getUUID().toString(),actor=actor(player),rootId="vanilla/"+now+"/"+ACTION_SEQUENCE.incrementAndGet();RecentCritical recent=claimCritical(actor,targetId,now);boolean critical=recent!=null&&recent.critical;CombatPerkRanks ranks=ranks(player);boolean protectedTarget=event.getEntity().getArmorValue()>0||event.getEntity().isBlocking();boolean boss=event.getEntity().getType().is(Tags.EntityTypes.BOSSES);
        HitFacts facts=facts(actor,targetId,rootId,family.get(),critical,false,false,false,protectedTarget,false,false,false,true,healthFraction(event.getEntity()),boss,now);BeforeResult specialty=A0021A0040CombatPolicy.beforeHit(facts,ranks,A0021A0040RuntimeState.state(),mastery(player,family.get()));double multiplier=NotionCombatPerkRules.baseDamageMultiplier(family.get(),ranks)*specialty.damageMultiplier();if(Double.compare(multiplier,1.0D)!=0)event.setAmount((float)(event.getAmount()*multiplier));VANILLA_PENDING.put(vanillaKey(actor,targetId),new PendingVanilla(rootId,family.get(),critical,protectedTarget,boss,specialty));
    }

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public static void onLivingDamagePost(LivingDamageEvent.Post event){
        LivingEntity target=event.getEntity();long now=target.level().getGameTime()*50L;A0021A0040RuntimeState.state().updateReapingMaturityForTarget(target.getUUID().toString(),healthFraction(target),now);
        if(!(event.getSource().getDirectEntity() instanceof ServerPlayer player)||!eligible(player)||event.getHealthDamage()<=0)return;String actor=actor(player),targetId=target.getUUID().toString();PendingVanilla pending=VANILLA_PENDING.remove(vanillaKey(actor,targetId));if(pending==null)return;CombatPerkRanks ranks=ranks(player);HitFacts facts=facts(actor,targetId,pending.rootActionId,pending.family,pending.critical,false,false,false,pending.protectedTarget,false,false,false,true,healthFraction(target),pending.boss,now);A0021A0040CombatPolicy.afterConfirmedHit(facts,ranks,A0021A0040RuntimeState.state());if(pending.specialty.applyArmorSunder())applyArmorSunder(target,pending.specialty.armorSunderFraction(),pending.specialty.armorSunderDurationMillis(),now);
    }

    @SubscribeEvent public static void onDeath(LivingDeathEvent event){LivingEntity entity=event.getEntity();A0021A0040RuntimeState.clearTarget(entity.getUUID().toString());removeArmorSunder(entity);ARMOR_SUNDER_EXPIRES.remove(entity.getUUID());if(entity instanceof ServerPlayer player)clearPlayer(player);}
    @SubscribeEvent public static void onLogout(PlayerEvent.PlayerLoggedOutEvent event){if(event.getEntity() instanceof ServerPlayer player)clearPlayer(player);}
    @SubscribeEvent public static void onDimension(PlayerEvent.PlayerChangedDimensionEvent event){if(event.getEntity() instanceof ServerPlayer player)clearPlayer(player);}
    @SubscribeEvent public static void onRespawn(PlayerEvent.PlayerRespawnEvent event){if(event.getEntity() instanceof ServerPlayer player)clearPlayer(player);}
    @SubscribeEvent(priority=EventPriority.LOWEST) public static void onServerTickPost(ServerTickEvent.Post event){long now=event.getServer().overworld().getGameTime()*50L;ARMOR_SUNDER_EXPIRES.entrySet().removeIf(e->{if(e.getValue()>now)return false;LivingEntity target=findLiving(event.getServer(),e.getKey());if(target!=null)removeArmorSunder(target);return true;});}
    @SubscribeEvent public static void onServerStopped(ServerStoppedEvent event){A0021A0040RuntimeState.clearAll();synchronized(A0021A0040EpicFightHooks.class){PENDING.clear();RECENT_CRITICAL.clear();REAR_STATE.clear();VANILLA_PENDING.clear();ARMOR_SUNDER_EXPIRES.clear();}}

    private static void applyArmorSunder(LivingEntity target,double fraction,long duration,long now){if(fraction<=0||duration<=0)return;AttributeInstance armor=target.getAttribute(Attributes.ARMOR);if(armor==null)return;double amount=-fraction;AttributeModifier current=armor.getModifier(ARMOR_SUNDER_ID);if(current!=null)amount=Math.min(amount,current.amount());armor.addOrUpdateTransientModifier(new AttributeModifier(ARMOR_SUNDER_ID,amount,AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));ARMOR_SUNDER_EXPIRES.merge(target.getUUID(),Math.addExact(now,duration),Math::max);}
    private static void removeArmorSunder(LivingEntity target){AttributeInstance armor=target.getAttribute(Attributes.ARMOR);if(armor!=null)armor.removeModifier(ARMOR_SUNDER_ID);}
    private static LivingEntity findLiving(MinecraftServer server,UUID id){for(ServerLevel level:server.getAllLevels()){Entity e=level.getEntity(id);if(e instanceof LivingEntity living)return living;}return null;}

    private static HitFacts facts(String actor,String target,String root,WeaponFamily family,boolean critical,boolean reposition,boolean rear,boolean heavy,boolean protectedTarget,boolean pressure,boolean impact,boolean penetration,boolean armorDebuff,double hp,boolean boss,long now){return new HitFacts(actor,target,root,family,true,true,true,critical,reposition,rear,heavy,protectedTarget,pressure,impact,penetration,armorDebuff,hp,boss,now);}
    private static double healthFraction(LivingEntity target){return target.getMaxHealth()<=0?0:Math.max(0,Math.min(1,target.getHealth()/target.getMaxHealth()));}
    private static boolean rearHalfPlane(ServerPlayer player,LivingEntity target){double fx=target.getLookAngle().x,fz=target.getLookAngle().z,tx=player.getX()-target.getX(),tz=player.getZ()-target.getZ(),len=Math.sqrt(tx*tx+tz*tz),flen=Math.sqrt(fx*fx+fz*fz);if(len<1.0E-6||flen<1.0E-6)return false;return (fx/flen)*(tx/len)+(fz/flen)*(tz/len)<=0;}
    private static boolean hostile(ServerPlayer player,LivingEntity target){return target!=player&&!player.isAlliedTo(target)&&!target.isInvulnerable()&&(target instanceof Enemy||target instanceof Player);}
    private static boolean eligible(ServerPlayer player){return !player.level().isClientSide()&&!player.isSpectator()&&!(player instanceof FakePlayer);}
    private static String actor(ServerPlayer player){return player.getUUID().toString();}
    private static CombatPerkRanks ranks(ServerPlayer player){return A0021A0040RuntimeState.ranks(player);}
    private static int mastery(ServerPlayer player,WeaponFamily family){String key=switch(family){case DAGGER->"epicfight:dagger";case HAMMER->"epicfight:heavy";case MACE->"epicfight:mace";case SCYTHE->"epicfight:scythe";default->"";};return key.isEmpty()?0:PlayerProgressionRuntime.get(player).mastery().experience(key);}
    private static boolean isBatchFamily(WeaponFamily family){return family==WeaponFamily.DAGGER||family==WeaponFamily.HAMMER||family==WeaponFamily.MACE||family==WeaponFamily.SCYTHE;}
    private static TagKey<Item> tag(String path){return TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(RpgSkillTreeMod.MOD_ID,path));}
    private static Optional<WeaponFamily> family(CapabilityItem capability,ItemStack stack){Optional<WeaponFamily> provider=categoryFamily(capability);return provider.isPresent()?provider:tagFamily(stack);}
    private static Optional<WeaponFamily> categoryFamily(CapabilityItem capability){if(capability==null||capability.isEmpty())return Optional.empty();String category=EpicFightWeaponCategory.normalize(capability.getWeaponCategory().toString());int slash=category.lastIndexOf('/');if(slash>=0&&slash+1<category.length())category=category.substring(slash+1);return switch(category){case "dagger"->Optional.of(WeaponFamily.DAGGER);case "hammer"->Optional.of(WeaponFamily.HAMMER);case "mace"->Optional.of(WeaponFamily.MACE);case "scythe"->Optional.of(WeaponFamily.SCYTHE);default->Optional.empty();};}
    private static Optional<WeaponFamily> tagFamily(ItemStack stack){if(stack.is(HAMMERS))return Optional.of(WeaponFamily.HAMMER);if(stack.is(MACES))return Optional.of(WeaponFamily.MACE);if(stack.is(SCYTHES))return Optional.of(WeaponFamily.SCYTHE);return Optional.empty();}

    private static synchronized Root rootAction(EpicFightDamageSource source,String actor,String target,WeaponFamily family,CombatPerkRanks ranks,long now){Map<String,PendingHit> map=PENDING.computeIfAbsent(source,k->new HashMap<>());PendingHit p=map.get(target);if(p!=null)return new Root(p.rootActionId,p.critical,p.criticalMultiplierAlreadyApplied);RecentCritical recent=claimCritical(actor,target,now);if(recent!=null)return new Root(recent.rootActionId,recent.critical,recent.multiplierApplied);String id="epicfight-b/"+now+"/"+ACTION_SEQUENCE.incrementAndGet();if(family==WeaponFamily.DAGGER)return new Root(id,false,false);boolean critical=A0001A0020RuntimeState.critical().resolve(actor,id,false,NotionCombatPerkRules.criticalChanceBonus(family,ranks),now);return new Root(id,critical,false);}
    private static synchronized void remember(EpicFightDamageSource source,String target,PendingHit pending){PENDING.computeIfAbsent(source,k->new HashMap<>()).put(target,pending);}
    private static synchronized PendingHit forget(EpicFightDamageSource source,String target){Map<String,PendingHit> map=PENDING.get(source);if(map==null)return null;PendingHit p=map.remove(target);if(map.isEmpty())PENDING.remove(source);return p;}
    private static synchronized void rememberCritical(String actor,String target,String root,boolean critical,boolean multiplier,long now){pruneCritical(now);RECENT_CRITICAL.put(actor+'\0'+target,new RecentCritical(root,critical,multiplier,Math.addExact(now,CRITICAL_CORRELATION_MILLIS)));}
    private static synchronized RecentCritical claimCritical(String actor,String target,long now){pruneCritical(now);return RECENT_CRITICAL.remove(actor+'\0'+target);}
    private static void pruneCritical(long now){RECENT_CRITICAL.entrySet().removeIf(e->e.getValue().expiresAt<=now);}
    private static String vanillaKey(String actor,String target){return actor+'\0'+target;}
    private static void clearPlayer(ServerPlayer player){String actor=actor(player);A0021A0040RuntimeState.clear(player);RECENT_CRITICAL.keySet().removeIf(k->k.startsWith(actor+'\0'));REAR_STATE.keySet().removeIf(k->k.startsWith(actor+'\0'));VANILLA_PENDING.keySet().removeIf(k->k.startsWith(actor+'\0'));}
    private static long now(ServerPlayer player){return Math.multiplyExact(player.level().getGameTime(),50L);}

    private record Root(String id,boolean critical,boolean criticalMultiplierAlreadyApplied){}
    private record RecentCritical(String rootActionId,boolean critical,boolean multiplierApplied,long expiresAt){}
    private record PendingHit(String rootActionId,WeaponFamily family,boolean critical,boolean reposition,boolean rear,boolean protectedTarget,boolean boss,BeforeResult specialty){boolean criticalMultiplierAlreadyApplied(){return false;}}
    private record PendingVanilla(String rootActionId,WeaponFamily family,boolean critical,boolean protectedTarget,boolean boss,BeforeResult specialty){}
}
