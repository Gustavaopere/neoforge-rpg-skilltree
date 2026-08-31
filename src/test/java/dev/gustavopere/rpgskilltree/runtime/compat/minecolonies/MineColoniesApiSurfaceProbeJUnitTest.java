package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

final class MineColoniesApiSurfaceProbeJUnitTest {
    @Test
    void reportsExactSnapshotRegistrationSurface() {
        String report = "\nExact MineColonies class surface:\n" + classSurface(
                "com.minecolonies.api.IMinecoloniesAPI",
                "com.minecolonies.api.colony.jobs.registry.JobEntry",
                "com.minecolonies.api.colony.jobs.registry.JobRegistry",
                "com.minecolonies.api.colony.requestsystem.locationguard.GuardType",
                "com.minecolonies.api.colony.guardtype.GuardType",
                "com.minecolonies.api.colony.guard.GuardType",
                "com.minecolonies.core.colony.jobs.AbstractJobGuard",
                "com.minecolonies.core.colony.jobs.JobKnight",
                "com.minecolonies.core.colony.jobs.JobRanger",
                "com.minecolonies.core.colony.jobs.JobDruid",
                "com.minecolonies.core.colony.jobs.registry.ModJobs",
                "com.minecolonies.core.colony.requestsystem.locationguard.ModGuardTypes"
        );
        fail(report);
    }

    private static String classSurface(String... names) {
        return Arrays.stream(names)
                .map(MineColoniesApiSurfaceProbeJUnitTest::describe)
                .collect(Collectors.joining("\n\n"));
    }

    private static String describe(String name) {
        try {
            Class<?> type = Class.forName(name, false, MineColoniesApiSurfaceProbeJUnitTest.class.getClassLoader());
            return name + " = PRESENT"
                    + "\nconstructors:\n" + constructors(type)
                    + "\npublic static methods:\n" + staticMethods(type);
        } catch (Throwable throwable) {
            return name + " = ABSENT (" + throwable.getClass().getSimpleName() + ")";
        }
    }

    private static String constructors(Class<?> type) {
        return Arrays.stream(type.getDeclaredConstructors())
                .sorted(Comparator.comparing(Constructor::toString))
                .map(Constructor::toString)
                .collect(Collectors.joining("\n"));
    }

    private static String staticMethods(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()))
                .sorted(Comparator.comparing(Method::toString))
                .map(Method::toString)
                .collect(Collectors.joining("\n"));
    }
}
