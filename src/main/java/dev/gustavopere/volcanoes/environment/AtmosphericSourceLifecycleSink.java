package dev.gustavopere.volcanoes.environment;

import java.util.UUID;

public interface AtmosphericSourceLifecycleSink {
    void upsert(AtmosphericSource source);

    void remove(UUID id);

    static AtmosphericSourceLifecycleSink none() {
        return new AtmosphericSourceLifecycleSink() {
            @Override
            public void upsert(AtmosphericSource source) {
            }

            @Override
            public void remove(UUID id) {
            }
        };
    }
}
