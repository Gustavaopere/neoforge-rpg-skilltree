package dev.gustavopere.rpgskilltree.core;
import java.util.Objects;
public record AttunedItem(String itemId,int slotIndex){public AttunedItem{Objects.requireNonNull(itemId);if(itemId.isBlank()||slotIndex<0)throw new IllegalArgumentException();}}
