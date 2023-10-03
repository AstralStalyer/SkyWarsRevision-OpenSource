package cn.rmc.skywars.enums;

import jdk.nashorn.internal.objects.annotations.Getter;

public enum GameEvent {
    Refill("箱子重置"),
    EnderDragon("死亡竞赛"),
    Trial("审判"),
    End("游戏结束");

    public String displayname;

    GameEvent(String displayname){
        this.displayname = displayname;
    }
}
