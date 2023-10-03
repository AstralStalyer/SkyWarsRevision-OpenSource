package cn.rmc.skywars.perk.perks;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.listener.PlayerListener;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.MathUtils;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.UUID;

public class Revenge extends PerkBasic {
    public Revenge(UUID uuid,Integer innerLevel){
        super(uuid, PerkType.Revenge,innerLevel);
    }

    @Override
    protected void onDamaged(EntityDamageEvent e) {

    }

    @Override
    protected void onBlockDamaged(EntityDamageByBlockEvent e) {

    }

    @Override
    protected void onEntityDamaged(EntityDamageByEntityEvent e) {

    }

    @Override
    protected void onDealDamage(EntityDamageByEntityEvent e) {

    }

    @Override
    protected void onDealProjectileDamage(EntityDamageByEntityEvent e) {

    }

    @Override
    protected void onFoodLevelChanged(FoodLevelChangeEvent e) {

    }

    @Override
    protected void onProjectileLaunch(ProjectileLaunchEvent e) {

    }

    @Override
    protected void onProjectileHit(ProjectileHitEvent e) {

    }

    @Override
    protected void onConsume(PlayerItemConsumeEvent e) {

    }

    @Override
    protected void onCraft(CraftItemEvent e) {

    }

    @Override
    protected void onInteract(PlayerInteractEvent e) {

    }

    @Override
    protected void onKill(PlayerKillEvent e) {

    }

    @Override
    protected void onDeath(PlayerKillEvent e) {
        if(MathUtils.Chance(5)){
            if(e.getKiller() == null) return;
            Entity entity = e.getDeather().getPlayer().getWorld().spawnEntity(e.getDeather().getDeathloc(), EntityType.SPIDER);
            entity.setCustomName(e.getDeather().getPlayer().getName()+"'s Spider");
            PlayerListener.entityby.put(entity, SkyWars.getInstance().getPlayerManager().get(e.getDeather().getPlayer()));
            Creature creature = (Creature) entity;
            creature.setTarget(e.getKiller().getPlayer());
        }
    }

    @Override
    protected void onKillEntity(EntityDeathEvent e) {

    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent e) {

    }

    @Override
    protected void onBlockBreak(BlockBreakEvent e) {

    }

    @Override
    protected void onStart(GameStartEvent e) {

    }
}
