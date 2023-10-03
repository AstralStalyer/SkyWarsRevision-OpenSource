package cn.rmc.skywars.perk;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.UUID;

public abstract class PerkBasic implements Listener {

    @Getter
    private Integer innerLevel;
    @Getter
    private Integer maxLevel;
    @Getter
    private PerkType type;
    @Getter
    private UUID owner;
    @Getter
    public Player player;


    public PerkBasic(UUID uuid,PerkType type,Integer innerLevel){
        Bukkit.getPluginManager().registerEvents(this,SkyWars.getInstance());
        this.owner = uuid;
        this.innerLevel = innerLevel;
        this.type = type;
        this.maxLevel = type.getMaxLevel();
        player = Bukkit.getPlayer(owner);

    }

    public void dispose(){
        HandlerList.unregisterAll(this);
    }



    @EventHandler
    public void onGameStart(GameStartEvent e) {
        if(e.getGame().getPlayers().contains(SkyWars.getInstance().getPlayerManager().get(getPlayer()))){
            onStart(e);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKillEvent(PlayerKillEvent e) {
        if (e.getKiller() == null) {
            return;
        }
        if (e.getKiller().getPlayer().equals(getPlayer())) {
            onKill(e);
        }
        if (e.getDeather().getPlayer().equals(getPlayer())) {
            onDeath(e);
        }
    }

    @EventHandler
    public void onKillEntityEvent(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) {
            return;
        }
        if (e.getEntity().getKiller().equals(getPlayer())) {
            onKillEntity(e);
        }
    }


    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) {
            if (e.getPlayer().equals(getPlayer())) {
                onInteract(e);
            }
        }
    }

    @EventHandler
    public void onMake(CraftItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getWhoClicked().equals(getPlayer())) {
            onCraft(e);
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().equals(getPlayer())) {
            onConsume(e);
        }
    }

    @EventHandler
    public void onProjectileHitE(ProjectileHitEvent e) {
        if(!(e.getEntity() instanceof Arrow)) return;
        if (e.getEntity().getShooter().equals(getPlayer())) {
            onProjectileHit(e);
        }
    }

    @EventHandler
    public void ProjectileLaunchEvent(ProjectileLaunchEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if(!(e.getEntity() instanceof Arrow)) return;
        if (e.getEntity().getShooter().equals(getPlayer())) {
            onProjectileLaunch(e);
        }
    }


    @EventHandler
    public void onDamagedE(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().equals(getPlayer())) {
            onDamaged(e);
        }
    }

    @EventHandler
    public void onEntityDamagedE(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().equals(getPlayer())) {
            onEntityDamaged(e);
        }
    }

    @EventHandler
    public void onBlockDamagedE(EntityDamageByBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().equals(getPlayer())) {
            onBlockDamaged(e);
        }
    }

    @EventHandler
    public void onDealDamagedE(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamager().equals(getPlayer())) {
            onDealDamage(e);
        }
    }

    @EventHandler
    public void onDealDamageProdE(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter().equals(getPlayer())) {
            onDealProjectileDamage(e);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity().equals(getPlayer())) {
            onFoodLevelChanged(e);
        }
    }
    @EventHandler
    public void onBlockPlaceE(BlockPlaceEvent e){
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().equals(getPlayer())) {
            onBlockPlace(e);
        }
    }

    @EventHandler
    public void onBlockBreakE(BlockBreakEvent e){
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().equals(getPlayer())) {
            onBlockBreak(e);
        }
    }


    //being damaged
    protected abstract void onDamaged(EntityDamageEvent e);

    protected abstract void onBlockDamaged(EntityDamageByBlockEvent e);

    protected abstract void onEntityDamaged(EntityDamageByEntityEvent e);

    //damaged others
    protected abstract void onDealDamage(EntityDamageByEntityEvent e);

    protected abstract void onDealProjectileDamage(EntityDamageByEntityEvent e);

    protected abstract void onFoodLevelChanged(FoodLevelChangeEvent e);

    protected abstract void onProjectileLaunch(ProjectileLaunchEvent e);

    protected abstract void onProjectileHit(ProjectileHitEvent e);

    protected abstract void onConsume(PlayerItemConsumeEvent e);

    protected abstract void onCraft(CraftItemEvent e);

    protected abstract void onInteract(PlayerInteractEvent e);

    protected abstract void onKill(PlayerKillEvent e);

    protected void onDeath(PlayerKillEvent e){};

    protected abstract void onKillEntity(EntityDeathEvent e);

    protected abstract void onBlockPlace(BlockPlaceEvent e);

    protected abstract void onBlockBreak(BlockBreakEvent e);

    protected abstract void onStart(GameStartEvent e);



}
