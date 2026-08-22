package dev.gustavopere.rpgskilltree.core;
import java.util.Objects;
public record ActionOrigin(String sourceId,int procDepth){
 public ActionOrigin{Objects.requireNonNull(sourceId);if(sourceId.isBlank()||procDepth<0)throw new IllegalArgumentException();}
 public ActionOrigin child(String newSourceId){return new ActionOrigin(newSourceId,procDepth+1);}
}
