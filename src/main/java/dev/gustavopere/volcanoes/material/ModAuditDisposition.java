package dev.gustavopere.volcanoes.material;

/** Fail-closed disposition assigned to every top-level modlist entry during material-provider audit. */
public enum ModAuditDisposition {
    MATERIAL_PROVIDER,
    MATERIAL_CONSUMER_ONLY,
    NO_ELIGIBLE_MATERIALS,
    BLOCKED_AUDIT
}
