package cn.rmc.skywars.kit;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class KitBasic{
    @Getter
    private String name;
    @Getter
    private KitType type;
    @Getter
    private UUID owner;

    public KitBasic(UUID uuid, KitType type){
        this.owner = uuid;
        this.type = type;
        this.name = type.getDBName();

    }

    public Player getPlayer() {
        return Bukkit.getPlayer(owner);
    }

    public String getPlayerName() {
        return getPlayer().getName();
    }

    public abstract void GiveItem();



}
