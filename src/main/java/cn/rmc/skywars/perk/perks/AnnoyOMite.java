package cn.rmc.skywars.perk.perks;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.event.GameStartEvent;
import cn.rmc.skywars.event.PlayerKillEvent;
import cn.rmc.skywars.listener.PlayerListener;
import cn.rmc.skywars.perk.PerkBasic;
import cn.rmc.skywars.perk.PerkType;
import cn.rmc.skywars.util.MathUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.UUID;

public class AnnoyOMite extends PerkBasic {

    public AnnoyOMite(UUID uuid,Integer innerLevel){
        super(uuid, PerkType.AnnoyOMite,innerLevel);
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
        for(Player player: PlayerListener.lastarrow.keySet()){

            if(PlayerListener.lastarrow.get(player).equals(e.getEntity().getUniqueId())){
                int i = 0;
                switch (getInnerLevel()){
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        i = 4+getInnerLevel();
                        break;
                    case 5:
                        i = 10;
                        break;
                }
                if(MathUtils.Chance(i)) {
                    Entity entity = player.getWorld().spawnEntity(player.getLocation(), EntityType.SILVERFISH);
                    entity.setCustomName(getPlayer().getName() + "'s SilverFish");
                    PlayerListener.entityby.put(entity, SkyWars.getInstance().getPlayerManager().get(getPlayer()));
                }
            }
            break;
        }
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
