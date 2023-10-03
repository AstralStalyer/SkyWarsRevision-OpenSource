package cn.rmc.skywars.listener;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.enums.PlayerState;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.task.ChestFillTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KitListener implements Listener {

    @EventHandler
    public void EnderChest(GameStartEvent e){
        for (PlayerData pd: e.getGame().getAliveplayers()){
            if(pd.getKit().getType() == KitType.EnderChest){
                Location locb = pd.getPlayer().getLocation().clone();
                for(int i = 1;;i++){
                    Location loca = new Location(locb.getWorld(),(int)locb.getX(),(int)locb.getY()-2-i, (int)locb.getZ());
                    if(loca.getBlock().getType() != Material.AIR){
                        Location chest = loca.clone();
                        chest.setY(loca.getY()+1);
                        chest.getBlock().setType(Material.CHEST);
                        e.getGame().getMap().smallchests.add(chest);
                        e.getGame().getMap().isopened.put(chest,false);
                        e.getGame().getMap().openchests.put(chest,null);
                        new ChestFillTask(chest);
                        break;
                    }
                }
            }
        }
    }
    @EventHandler
    public void Enderman(PlayerInteractEvent e){
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(e.getPlayer());
        if(pd.getState() != PlayerState.FIGHTING) return;
        if(pd.getKit().getType() != KitType.Enderman) return;
        if(pd.getGame().getState() != GameState.FIGHTING) return;
        Game g = pd.getGame();
        if(e.getItem() == null) return;
        if(e.getItem().getItemMeta() == null) return;
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(e.getItem().getItemMeta().getDisplayName().equals("§d损坏的珍珠")){
            if(System.currentTimeMillis() - g.getStarttimestamp() < 30000){
                int left = (int) (30000 -System.currentTimeMillis() + g.getStarttimestamp());
                e.getPlayer().sendMessage("§c你的珍珠正在冷却中,请等待"+left/1000+"秒");
                e.setCancelled(true);
                e.getPlayer().updateInventory();
            }
        }
    }
    @EventHandler
    public void Zooper(GameStartEvent e){
        for (PlayerData pd: e.getGame().getAliveplayers()){
            if(pd.getKit().getType() == KitType.EnderChest) {
                pd.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,99999,1));
            }
        }
    }
}
