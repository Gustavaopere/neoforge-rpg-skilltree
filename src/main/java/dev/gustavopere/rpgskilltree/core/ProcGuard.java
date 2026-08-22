package dev.gustavopere.rpgskilltree.core;
public final class ProcGuard{private ProcGuard(){} public static boolean mayTriggerSecondaryEffect(ActionOrigin origin){return origin.procDepth()==0;}}
