package cn.rmc.skywars.enums;

public enum Rarity {
    COMMON("普通"),
    RARE("史诗"),
    LEGENDARY("传奇");
    String displayname;
    Rarity(String displayname){
        this.displayname = displayname;
    }
}
