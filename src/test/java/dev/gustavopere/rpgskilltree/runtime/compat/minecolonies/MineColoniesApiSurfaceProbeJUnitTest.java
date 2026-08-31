package dev.gustavopere.rpgskilltree.runtime.compat.minecolonies;

import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.colony.requestsystem.locationguard.GuardType;
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
        String report = "\nJobEntry constructors:\n" + constructors(JobEntry.class)
                + "\nJobEntry public static methods:\n" + staticMethods(JobEntry.class)
                + "\nGuardType constructors:\n" + constructors(GuardType.class)
                + "\nGuardType public static methods:\n" + staticMethods(GuardType.class)
                + "\nKnown classes:\n" + classPresence(
                "com.minecolonies.api.IMinecoloniesAPI",
                "com.minecolonies.api.colony.jobs.registry.JobRegistry",
                "com.minecolonies.core.colony.jobs.AbstractJobGuard",
                "com.minecolonies.core.colony.jobs.JobKnight",
                "com.minecolonies.core.colony.jobs.JobRanger",
                "com.minecolonies.core.colony.jobs.JobDruid",
                "com.minecolonies.core.colony.jobs.registry.ModJobs",
                "com.minecolonies.core.colony.requestsystem.locationguard.ModGuardTypes"
        );
        fail(report);
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

    private static String classPresence(String... names) {
        return Arrays.stream(names)
                .map(name -> name + " = " + present(name))
                .collect(Collectors.joining("\n"));
    }

    private static boolean present(String name) {
        try {
            Class.forName(name, false, MineColoniesApiSurfaceProbeJUnitTest.class.getClassLoader());
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
