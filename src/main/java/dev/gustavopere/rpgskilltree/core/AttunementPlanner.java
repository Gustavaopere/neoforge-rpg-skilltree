package dev.gustavopere.rpgskilltree.core;
import java.util.*;
public final class AttunementPlanner{private AttunementPlanner(){} public static AttunementResizePlan resize(Collection<AttunedItem> current,int capacity){if(capacity<0)throw new IllegalArgumentException("capacity");List<AttunedItem> sorted=current.stream().sorted(Comparator.comparingInt(AttunedItem::slotIndex).thenComparing(AttunedItem::itemId)).toList();return new AttunementResizePlan(sorted.stream().limit(capacity).toList(),sorted.stream().skip(capacity).toList());}}
