package cn.rmc.skywars.perk.perks;

import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.MathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Robbery extends PerkBasic {

    public Robbery(UUID uuid,Integer innerLevel){
        super(uuid, PerkType.Robbery,innerLevel);
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
        if(getPlayer().getInventory().getItemInHand().getType() != Material.AIR) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if(MathUtils.Chance(getInnerLevel())){
            player.getWorld().dropItem(player.getLocation(),player.getInventory().getItemInHand());
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
        }

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
