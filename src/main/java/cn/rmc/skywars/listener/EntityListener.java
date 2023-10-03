package cn.rmc.skywars.listener;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.PlayerState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class EntityListener implements Listener {
    public static HashMap<Entity,PlayerData> lastdamage = new HashMap<>();
    @EventHandler
    public void onTarget(EntityTargetEvent e){
        HashMap<Entity,PlayerData> hashMap = PlayerListener.entityby;
        if(!hashMap.containsKey(e.getEntity())) return;
        if(e.getReason() == EntityTargetEvent.TargetReason.FORGOT_TARGET) return;
        if(hashMap.containsKey(e.getTarget()) && hashMap.get(e.getEntity()) == hashMap.get(e.getTarget())){
            e.setCancelled(true);
        }
        if(!(e.getTarget() instanceof Player))return;
        PlayerData p = hashMap.get(e.getEntity());
        PlayerData target = SkyWars.getInstance().getPlayerManager().get((Player) e.getTarget());
        if(target.getState() == PlayerState.SPECING){
            e.setCancelled(true);
        }
        if(e.getTarget().getUniqueId().equals(p.getUuid())){
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        if(e.getEntity() instanceof Player) return;
        Player damager = (Player) e.getDamager();
        PlayerData damagerpd = SkyWars.getInstance().getPlayerManager().get(damager);
        if(PlayerListener.entityby.containsKey(e.getEntity())){
            if(PlayerListener.entityby.get(e.getEntity()).equals(damagerpd)){
                e.setCancelled(true);
            }else{
                lastdamage.put(e.getEntity(),damagerpd);
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        for (Entity entity : PlayerListener.entityby.keySet()) {
            PlayerData result = null;
            double lastdis = Double.MAX_VALUE;
            for (PlayerData playerData : PlayerListener.entityby.get(entity).getGame().getAliveplayers()) {
                if(PlayerListener.entityby.get(entity) == playerData) continue;
                double dis = entity.getLocation().distance(playerData.getPlayer().getLocation());
                if(dis < lastdis){
                    lastdis = dis;
                    result = playerData;
                }
            }
            Creature creature = (Creature) entity;
            if(result != null) {
                creature.setTarget(result.getPlayer());
            }
        }
    }
    @EventHandler
    public void onburn(EntityCombustEvent e){
        if(!(e.getEntity() instanceof Blaze)) {
            e.setCancelled(true);
        }
    }
}
