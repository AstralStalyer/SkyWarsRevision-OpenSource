package cn.rmc.skywars.listener;

import cn.rmc.skywars.Inventory.KitSelector;
import cn.rmc.skywars.Inventory.SpecSelector;
import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.enums.PlayerState;
import cn.rmc.skywars.util.BungeeUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class PlayerInteractListener implements Listener {
    private HashMap<Player, BukkitTask> wait = new HashMap<>();

    @EventHandler
    public void onWaiting(PlayerInteractEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(e.getItem() == null) return;
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(pd.getState() != PlayerState.FIGHTING) return;
        Game g = pd.getGame();
        if(g.getState() == GameState.FIGHTING || g.getState() == GameState.ENDING) return;
        switch (e.getItem().getType()){
            case BOW:
                new KitSelector(pd).open();
                //
                break;
            case BED:
                if(wait.containsKey(p)){
                    p.sendMessage("§c§l传送取消了!");
                    wait.get(p).cancel();
                    wait.remove(p);
                }else{
                    p.sendMessage("§a§l3秒后将你传送到大厅...再次右键以取消传送!");
                    BukkitTask result = Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->{
                        BungeeUtil.sendPlayer(p,"Lobby");
                    },20*3L);
                    wait.put(p,result);
                }
                break;
        }
    }
    @EventHandler
    public void onSpec(PlayerInteractEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(e.getItem() == null) return;
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(pd.getState() != PlayerState.SPECING) return;
        switch (e.getItem().getType()){
            case COMPASS:
                new SpecSelector(pd).open();
                break;
            case REDSTONE_COMPARATOR:
                p.sendMessage("§c开发中");
                break;
            case BED:
                if(wait.containsKey(p)){
                    p.sendMessage("§c§l传送取消了!");
                    wait.get(p).cancel();
                    wait.remove(p);
                }else{
                    p.sendMessage("§a§l3秒后将你传送到大厅...再次右键以取消传送!");
                    BukkitTask result = Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->{
                        BungeeUtil.sendPlayer(p,"Lobby");
                    },20*3L);
                    wait.put(p,result);
                }
                break;
        }
    }
}
