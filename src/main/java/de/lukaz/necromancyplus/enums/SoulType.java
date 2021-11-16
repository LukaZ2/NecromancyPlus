package de.lukaz.necromancyplus.enums;

public enum SoulType {

    ZOMBIE("Zombie", "Normal", "Just a regular zombie."),
    TRIBE_MEMBER("Kalhuiki Tribe Member", "Very slow"),
    TRIBE_MEMBER_YOUNG("Kalhuiki Youngling", "Normal"),
    MINOS_INQUISITOR("Minos Inquisitor", "Normal", "This mob has the most health of any mob in the game, being tied with Necron."),
    MINOS_CHAMPION("Minos Champion", "Normal", "Doesn't have increased damage ability."),
    MINOTAUR("Minotaur", "Normal", "Doesn't throw axe or have bleed effect."),
    AUTOMATON("Automaton", "Normal", "Doesn't shoot laser beam at enemies."),
    BOSS_CORLEONE("Boss Corleone", "Very slow", "Doesn't summon mobs."),
    GOON("Goon", "Normal", "No longer obtainable. Still exists in game."),
    GREAT_WHITE_SHARK("Great White Shark", "Normal", "Doesn't use ability."),
    YETI("Yeti", "Slow", "Doesn't use ability."),
    PHANTOM_FISHER("Phantom Fisher", "Normal", "Doesn't hold rod or cast it."),
    RAT("Rat", "Normal", "Deals no damage."),
    GRIM_REAPER("Grim Reaper", "Fast", "Doesn't use ability."),
    SPIRIT_BAT("Spirit Bat", "Very fast"),
    SPIRIT_BEAR("Spirit Bear", "Normal", "Holds bow, but doesn't shoot it."),
    TERRACOTTA("Terracotta", "Normal", "Does not use the Flower of Truth ability."),
    GOLEM("Golem", "Fast", "Currently the strongest necromancy mob."),
    GIANT("Giant", "Fast", "Doesn't use the abilities of any giant."),
    HYPIXEL("Hypixel", "Normal", "Holds a bone."),
    ZOMBIE_GRUNT("Zombie Grunt", "Normal", "Wears rotten armor."),
    TANK_ZOMBIE("Tank Zombie", "Slow", "Has a lot of defense."),
    SKELETON_GRUNT("Skeleton Grunt", "Normal", "Uses a bow."),
    CRYPT_DREADLORD("Crypt Dreadlord", "Normal", "Cannot use ability."),
    UNDEAD_SKELETON("Undead Skeleton", "Normal", "Uses a bow."),
    SKELETON_SOLDIER("Skeleton Soldier", "Normal", "Uses a bow."),
    CRYPT_LURKER("Crypt Lurker", "Normal", "Cannot throw the bone."),
    CRYPT_SOULEATER("Crypt Souleater", "Normal", "Holds bow, but doesn't shoot it."),
    SNIPER("Sniper", "Normal", "Uses a bow.");


    public String soulName;
    public String speed;
    public String description;

    SoulType(String soulName, String speed) {
        this.soulName = soulName;
        this.speed = speed;
    }

    SoulType(String soulName, String speed, String description) {
        this.soulName = soulName;
        this.speed = speed;
        this.description = description;
    }

}
