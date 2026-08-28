package dev.gustavopere.rpgskilltree.compendium.provider;

import java.util.List;

public record ProviderDiagnostic(String code, String message, List<String> providerIds) {
    public ProviderDiagnostic {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("diagnostic code must not be blank");
        if (message == null || message.trim().isEmpty()) throw new IllegalArgumentException("diagnostic message must not be blank");
        code = code.trim();
        message = message.trim();
        providerIds = List.copyOf(providerIds == null ? List.of() : providerIds);
    }
}
