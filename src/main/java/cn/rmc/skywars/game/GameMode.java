package cn.rmc.skywars.game;

public enum GameMode {
    NORMAL("§a普通模式"),
    NORMALSOLO("§a普通模式"),
    NORMALTEAM("§a普通模式"),
    INSANE("§c疯狂模式"),
    INSANESOLO("§c疯狂模式"),
    INSANETEAM("§c疯狂模式"),
    RANKED("§6排位"),
    MEGA("§d超级");

    public String displayname;

    GameMode(String displayname){
        this.displayname = displayname;
    }

    public String getDisplayName() {
        return this.displayname;
    }
}
