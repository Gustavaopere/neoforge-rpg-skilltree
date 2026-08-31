package dev.gustavopere.rpgskilltree.core;

import java.util.Set;
import java.util.regex.Pattern;

/** Canonical identifiers emitted by the mastery policy layer. */
public final class MasteryLaneCatalog {
    private static final Pattern MEMBER = Pattern.compile("[a-z0-9_]+(?:-[a-z0-9_]+)*");

    private static final Set<String> ARS_MEMBERS = Set.of(
        "projectile", "amplification", "aoe", "duration", "summoning", "control"
    );
    private static final Set<String> GOETY_MEMBERS = Set.of(
        "necromancy", "nether", "ill", "frost", "geomancy", "wind", "storm",
        "abyss", "wild", "void", "summoning"
    );
    private static final Set<String> CREATE_MEMBERS = Set.of(
        "kinetics", "logistics", "artillery", "aeronautics", "power", "automation"
    );

    public static final String MAGIC_CASTING = "magic:casting";
    public static final String IRONS_CASTING = "irons:casting";
    public static final String ARS_CASTING = "ars:casting";
    public static final String OCCULT_PRACTICE = "occult:practice";
    public static final String GOETY_CASTING = "goety:casting";
    public static final String GOETY_SOUL_SPENDING = "goety:soul_spending";
    public static final String GOETY_SERVANTS = "goety:servants";
    public static final String SUMMONING_PRACTICE = "summoning:practice";
    public static final String GOETY_PACT_SERVANTS = "goety:pact_servants";
    public static final String GOETY_COMMANDING = "goety:commanding";
    public static final String MALUM_SPIRIT_ARCANA = "malum:spirit_arcana";
    public static final String MALUM_REAPING = "malum:reaping";
    public static final String MALUM_COLLECTION = "malum:collection";
    public static final String EIDOLON_RITUAL = "eidolon:ritual";
    public static final String HEALING_PRACTICE = "healing:practice";
    public static final String EIDOLON_ALCHEMY = "eidolon:alchemy";
    public static final String CREATE_ENGINEERING = "create:engineering";
    public static final String EPICFIGHT_WEAPON = "epicfight:weapon";
    public static final String EPICFIGHT_PRACTICE = "epicfight:practice";
    public static final String EPICFIGHT_SKILL = "epicfight:skill";
    public static final String EPICFIGHT_STAMINA = "epicfight:stamina";
    public static final String EPICFIGHT_GUARD = "epicfight:guard";
    public static final String EPICFIGHT_DODGE = "epicfight:dodge";
    public static final String EPICFIGHT_MOBILITY = "epicfight:mobility";
    public static final String EPICFIGHT_WEAPON_INNATE = "epicfight:weapon_innate";
    public static final String AGILITY_PRACTICE = "agility:practice";

    private static final Set<String> FIXED = Set.of(
        MAGIC_CASTING,
        IRONS_CASTING,
        ARS_CASTING,
        OCCULT_PRACTICE,
        GOETY_CASTING,
        GOETY_SOUL_SPENDING,
        GOETY_SERVANTS,
        SUMMONING_PRACTICE,
        GOETY_PACT_SERVANTS,
        GOETY_COMMANDING,
        MALUM_SPIRIT_ARCANA,
        MALUM_REAPING,
        MALUM_COLLECTION,
        EIDOLON_RITUAL,
        HEALING_PRACTICE,
        EIDOLON_ALCHEMY,
        CREATE_ENGINEERING,
        EPICFIGHT_WEAPON,
        EPICFIGHT_PRACTICE,
        EPICFIGHT_SKILL,
        EPICFIGHT_STAMINA,
        EPICFIGHT_GUARD,
        EPICFIGHT_DODGE,
        EPICFIGHT_MOBILITY,
        EPICFIGHT_WEAPON_INNATE,
        AGILITY_PRACTICE
    );

    private MasteryLaneCatalog() {}

    public static String ironsDiscipline(String discipline) {
        return "irons:" + dynamicMember(discipline, "Iron's discipline");
    }

    public static String ars(String member) {
        return bounded("ars", member, ARS_MEMBERS);
    }

    public static String goety(String member) {
        return bounded("goety", member, GOETY_MEMBERS);
    }

    public static String malumSpirit(String affinity) {
        return "malum:spirit/" + dynamicMember(affinity, "Malum spirit affinity");
    }

    public static String create(String member) {
        return bounded("create", member, CREATE_MEMBERS);
    }

    public static String epicFightWeaponCategory(String category) {
        return "epicfight:" + dynamicMember(category, "Epic Fight weapon category");
    }

    public static boolean isCanonical(String laneId) {
        if (laneId == null || laneId.isBlank()) return false;
        if (FIXED.contains(laneId)) return true;
        if (matchesDynamic(laneId, "irons:")) return true;
        if (matchesDynamic(laneId, "malum:spirit/")) return true;
        if (matchesDynamic(laneId, "epicfight:")) return true;
        return matchesBounded(laneId, "ars:", ARS_MEMBERS)
            || matchesBounded(laneId, "goety:", GOETY_MEMBERS)
            || matchesBounded(laneId, "create:", CREATE_MEMBERS);
    }

    private static String bounded(String namespace, String member, Set<String> allowed) {
        String checked = dynamicMember(member, namespace + " mastery member");
        if (!allowed.contains(checked)) {
            throw new IllegalArgumentException("unsupported " + namespace + " mastery member: " + checked);
        }
        return namespace + ":" + checked;
    }

    private static String dynamicMember(String value, String label) {
        if (value == null || !MEMBER.matcher(value).matches()) {
            throw new IllegalArgumentException(label + " must be a canonical lowercase token");
        }
        return value;
    }

    private static boolean matchesDynamic(String laneId, String prefix) {
        if (!laneId.startsWith(prefix)) return false;
        String member = laneId.substring(prefix.length());
        return MEMBER.matcher(member).matches();
    }

    private static boolean matchesBounded(String laneId, String prefix, Set<String> allowed) {
        if (!laneId.startsWith(prefix)) return false;
        return allowed.contains(laneId.substring(prefix.length()));
    }
}
