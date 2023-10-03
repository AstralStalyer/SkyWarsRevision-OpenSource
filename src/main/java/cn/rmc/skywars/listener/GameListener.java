package cn.rmc.skywars.listener;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.enums.GameEvent;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.event.GameEndEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.util.DataUtils;
import cn.rmc.skywars.util.ParticleBuilder;
import cn.rmc.skywars.util.TitleUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GameListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onKill(PlayerKillEvent e){
        for(Entity entity :PlayerListener.entityby.keySet()){
            if(e.getDeather().equals(PlayerListener.entityby.get(entity))){
                entity.remove();
            }
        }
        if(e.getKiller() != null){
            e.getKiller().getPlayer().playSound(e.getKiller().getPlayer().getLocation(), Sound.ORB_PICKUP,1,1);
            e.getKiller().getPlayer().sendMessage("§6+20硬币! 击杀");
            e.getKiller().setCoins(e.getKiller().getCoins()+20);
            int addsoul = 1;
            if(Integer.parseInt(DataUtils.get(Data.field.SOUL_ADDED,e.getKiller()) == null ? "0":DataUtils.get(Data.field.SOUL_ADDED,e.getKiller())) +100 - Integer.parseInt(DataUtils.get(Data.field.SOUL,e.getKiller()) == null ? "0":DataUtils.get(Data.field.SOUL,e.getKiller())) < 1){
                addsoul = 0;
            }
            e.getKiller().getPlayer().sendMessage("§b+"+addsoul+"个灵魂");
            e.getKiller().setSoul(e.getKiller().getSoul()+addsoul);
            e.getKiller().setExpenience(e.getKiller().getExpenience() + 1);
            e.getKiller().getPlayer().sendMessage("§d+1点空岛战争经验!");
        }
        Player deather = e.getDeather().getPlayer();
        Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->
                TitleUtils.sendFullTitle(deather,0,60,0,"§c§l你死了!","§7你现在是一名旁观者!"),20L);
        e.getDeather().setTimeplayed(System.currentTimeMillis()-e.getDeather().getGame().getStarttimestamp());
    }

    @EventHandler
    public void onStat(PlayerKillEvent e){
        EntityDamageEvent.DamageCause cause = PlayerListener.lastcause.get(e.getDeather().getPlayer());
        PlayerData kill = e.getKiller();
        PlayerData death = e.getDeather();
        death.setDeaths(death.getDeaths()+1);
        e.getAssisters().forEach(playerData -> {
            if(playerData != e.getKiller()) {
                playerData.setAssists(playerData.getAssists() + 1);
            }
        });
        if(kill != null){
            kill.setKills(kill.getKills()+1);
            if(cause == null){
                cause = EntityDamageEvent.DamageCause.VOID;
            }
            switch (cause){
                case VOID:
                    kill.setVoid_kills(kill.getVoid_kills()+1);
                    break;
                case PROJECTILE:
                    kill.setBow_kills(kill.getBow_kills()+1);
                    if(e.getKiller().getBow_longest_kill() < e.getKiller().getPlayer().getLocation().distance(e.getDeather().getPlayer().getLocation())){
                        e.getKiller().setBow_longest_kill(e.getKiller().getPlayer().getLocation().distance(e.getDeather().getPlayer().getLocation()));
                    }
                    break;
                case CUSTOM:
                    //mob
                    kill.setKills_by_mobs(kill.getKills_by_mobs()+1);
                    break;
                default:
                    //melee
                    kill.setMelee_kills(kill.getMelee_kills()+1);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(SkyWars.getInstance(),()->{
           PlayerData pd = SkyWars.getInstance().getPlayerManager().get(e.getPlayer());
           if(!e.isCancelled()){
               pd.setBlocks_palced(pd.getBlocks_palced()+1);
           }
        });
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(SkyWars.getInstance(),()->{
            PlayerData pd = SkyWars.getInstance().getPlayerManager().get(e.getPlayer());
            if(!e.isCancelled()){
                pd.setBlocks_broken(pd.getBlocks_broken()+1);
            }
            if (pd.getGame().getState() == GameState.FIGHTING) {
                new ParticleBuilder(e.getBlock().getLocation().add(0.5,1,0.5), EnumParticle.CLOUD).play();
            }
        });
    }
    @EventHandler
    public void onEDeath(EntityDeathEvent e){
        if(e.getEntity() instanceof Player) return;
        if(EntityListener.lastdamage.containsKey(e.getEntity())){
            PlayerData pd = EntityListener.lastdamage.get(e.getEntity());
            pd.setMobs_killed(pd.getMobs_killed()+1);
        }

    }
    @EventHandler
    public void onInter(PlayerInteractEvent e){
        if(e.isCancelled()) return;
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) return;
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(e.getPlayer());
        switch (e.getClickedBlock().getType()){
            case CHEST:
                if(!pd.getGame().getMap().isopened.get(e.getClickedBlock().getLocation())) {
                    pd.getGame().getMap().isopened.put(e.getClickedBlock().getLocation(), true);
                    pd.setChests_opened(pd.getChests_opened()+1);
                }
                break;
        }
    }
    @EventHandler
    public void onE(ProjectileLaunchEvent e){
        if(e.isCancelled()) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        switch (e.getEntityType()){
            case EGG:
                pd.setEggs_thrown(pd.getEggs_thrown()+1);
                break;
            case ENDER_PEARL:
                pd.setEnderperls_thrown(pd.getEnderperls_thrown()+1);
                break;
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGameEnd(GameEndEvent e){
        PlayerData winner = e.getGame().getAliveplayers().get(0);
        winner.setWin(true);
        winner.setCoins(winner.getCoins()+120);
        winner.getPlayer().sendMessage("§6+120硬币! 胜利");
        winner.setExpenience(winner.getExpenience() + 10);
        winner.getPlayer().sendMessage("§d+10点空岛战争经验!");
        winner.setTimeplayed(System.currentTimeMillis()-e.getGame().getStarttimestamp());
    }
    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e){
        if(!(e.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) e.getEntity();
        if(!(arrow.getShooter()instanceof Player)) return;
        Player p = (Player) arrow.getShooter();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        pd.setBow_hits(pd.getBow_hits()+1);
    }
    @EventHandler
    public void onHit(ProjectileHitEvent e){
        if(!(e.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) e.getEntity();
        if(!(arrow.getShooter()instanceof Player)) return;
        Player p = (Player) arrow.getShooter();
        Player hiter = p;
        for(Player pp:PlayerListener.lastarrow.keySet()){
            if(PlayerListener.lastarrow.get(pp).equals(e.getEntity().getUniqueId())){
                hiter = pp;
                break;
            }
        }
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if(pd.getBow_longest_shot() < p.getLocation().distance(hiter.getLocation())){
            pd.setBow_longest_shot(p.getLocation().distance(hiter.getLocation()));
        }
    }
    @EventHandler
    public void onEnd(GameEndEvent e){
        Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->{
            e.getGame().getPlayerlist().forEach(PlayerData::savedata);
        },20L);

    }
}
