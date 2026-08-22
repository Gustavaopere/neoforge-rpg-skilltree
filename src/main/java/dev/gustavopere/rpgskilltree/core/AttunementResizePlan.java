package dev.gustavopere.rpgskilltree.core;
import java.util.List;
public record AttunementResizePlan(List<AttunedItem> kept,List<AttunedItem> ejected){public AttunementResizePlan{kept=List.copyOf(kept);ejected=List.copyOf(ejected);}}
