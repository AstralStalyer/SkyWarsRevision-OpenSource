package cn.rmc.skywars.perk.perks;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Bridger extends PerkBasic {

    public Bridger(UUID uuid,Integer innerLevel){
        super(uuid, PerkType.Bridger,innerLevel);
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
    protected void onKillEntity(EntityDeathEvent e) {

    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent e) {
        Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()->{
            if(MathUtils.Chance(getInnerLevel()*5)){
                getPlayer().getInventory().addItem(new ItemStack(new ItemStack(e.getPlayer().getItemInHand().getType(), 1)));
            }
        },1);
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent e) {

    }

    @Override
    protected void onStart(GameStartEvent e) {

    }
}
