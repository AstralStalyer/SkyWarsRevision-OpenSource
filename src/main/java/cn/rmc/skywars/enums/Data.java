package cn.rmc.skywars.enums;

import lombok.Getter;

public enum Data {

    PLAYERINFO("playerinfo"),
    JOB("kit"),
    PAJ(null),
    PERK("perk");
    @Getter
    String where;
    
    Data(String where) {
        this.where = where;
    }
    public enum field {
        UUID("UUID", null),
        CURRENTKIT("CurrentKit",PLAYERINFO),
        LEVEL("Level", Data.PLAYERINFO),
        EXPERIENCE("Experience",PLAYERINFO),
        COIN("Coins", PLAYERINFO),
        SOUL("Souls", PLAYERINFO),
        SOUL_ADDED("Souls_Added",PLAYERINFO),
        WIN("Wins", PAJ),
        LOSS("Losses", PAJ),
        KILL("Kills", PAJ),
        ASSIST("Assists", PAJ),
        DEATH("Deaths", PAJ),
        BLOCKS_BROKEN("Blocks_Broken", PLAYERINFO),
        BLOCKS_PLACED("Blocks_Placed", PLAYERINFO),
        SOUL_WELL_USE("Soul_Well_Uses", PLAYERINFO),
        SOUL_WELL_LEGEND("Soul_Well_Legendaries", PLAYERINFO),
        SOUL_WELL_RARE("Soul_Well_Rares", PLAYERINFO),
        SOUL_PAID("Souls_Paid", PLAYERINFO),
        SOUL_GATHERED("Souls_Gathered", PLAYERINFO),
        EGGS_THROWN("Eggs_Thrown", PLAYERINFO),
        ENDERPERLS_THROWN("Enderperls_Thrown", PLAYERINFO),
        BOW_SHOT("Bow_Shots", PAJ),
        BOW_HIT("Bow_Hits", PAJ),
        KitNAME("KitName", JOB),
        ISBOUGHT("isBought", JOB),
        INVENTORY("Inventory",JOB),
        TIME_PLAYED("Time_Played", JOB),
        FASTEST_WIN("Fastest_Win", JOB),
        MOST_KILLS_IN_A_GAME("Most_Kills_in_a_Game", JOB),
        MOBS_KILLED("Mobs_Killed", JOB),
        CHESTS_OPENED("Chests_Opened", PAJ),
        HEADS_GATHERED("Heads_Gathered", PAJ),
        MELEE_KILL("Melee_Kills", PAJ),
        BOW_KILL("Bow_Kills", PAJ),
        VOID_KILL("Void_Kills", PAJ),
        KILLS_BY_MOBS("Kills_by_Mobs", JOB),
        BOW_LONGEST_KILL("Bow_Longest_Kill", JOB),
        BOW_LONGEST_SHOT("Bow_Longest_Shot", JOB),
        PERKNAME("PerkName", PERK),
        PERKLEVEL("PerkLevel", PERK);

        @Getter
        String where;
        @Getter
        Data table;

        field(String where, Data table) {
            this.where = where;
            this.table = table;
        }
    }
    
    
}
