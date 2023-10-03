package cn.rmc.skywars.event;

import cn.rmc.skywars.game.PlayerData;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;

public class PlayerKillEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final PlayerData killer;
    @Getter
    private final PlayerData deather;
    @Getter
    private final ArrayList<PlayerData> assisters;
    @Getter
    private final EntityDamageEvent.DamageCause cause;

    public PlayerKillEvent(PlayerData killer, PlayerData deather, ArrayList<PlayerData> assisters, EntityDamageEvent.DamageCause cause){
        this.killer = killer;
        this.deather = deather;
        this.assisters = assisters;
        this.cause = cause;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
