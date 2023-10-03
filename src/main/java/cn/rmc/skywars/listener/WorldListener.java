package cn.rmc.skywars.listener;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.enums.GameEvent;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.enums.PlayerState;
import cn.rmc.skywars.util.TimeUtils;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import me.arasple.mc.trhologram.hologram.Hologram;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.Collections;

public class WorldListener implements Listener {


    @EventHandler
    public void onWeather(WeatherChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        switch (pd.getState()){
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case WAITING:
                    case COUNTING:
                        e.setCancelled(true);
                        break;
                    case ENDING:
                        e.setCancelled(true);
                        break;
                }
                break;
            case SPECING:
                e.setCancelled(true);
                break;
        }
    }
    @EventHandler
    public void onBreak(BlockPlaceEvent e){
        Player p = e.getPlayer();
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        switch (pd.getState()){
            case FIGHTING:
                Game g = pd.getGame();
                switch (g.getState()){
                    case WAITING:
                    case COUNTING:
                        e.setCancelled(true);
                        break;
                    case ENDING:
                        e.setCancelled(true);
                        break;
                }
                break;
            case SPECING:
                e.setCancelled(true);
                break;
        }
    }
    @EventHandler
    public void onIntChest(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.isCancelled()) return;
        PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (pd.getState() != PlayerState.FIGHTING && p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            return;
        }
        Game g = pd.getGame();
        if (g.getState() != GameState.FIGHTING && p.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            return;
        }
        Block target = e.getClickedBlock();
        if (target.getType() != Material.CHEST) return;
        if (g.getEvent() != GameEvent.Refill) return;
        Chest chest = (Chest) target.getState();
        Location loc = chest.getLocation();
        Location hololoc = loc.clone();
        hololoc.setX(loc.getX() + 0.5);
        hololoc.setY(loc.getY() + 0.3);
        hololoc.setZ(loc.getZ() + 0.5);
        if (!g.getMap().openchests.containsKey(loc)) {
            e.setCancelled(true);
            return;
        }
        if (g.getMap().openchests.get(loc) == null) {
            Hologram holo = TrHologramAPI.createHologram(SkyWars.getInstance(), hololoc.toString(), hololoc, "", "§a" + TimeUtils.formatIntoMMSS(g.getEventLeftTime()));
            g.getMap().openchests.put(loc, holo);
            //Bukkit.getScheduler().runTaskLater(SkyWars.getInstance(),()-> ChestUtils.playChestAction(chest,true),1L);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(e.getBlock().getType() != Material.PUMPKIN) return;
        Location loc = e.getBlock().getLocation();
        Location l1 = loc.clone();
        l1.setY(loc.getY()-1);
        Location l2 = loc.clone();
        l2.setY(loc.getY()-2);
        if(l1.getBlock().getType() == Material.SNOW_BLOCK && l2.getBlock().getType() == Material.SNOW_BLOCK){
            loc.getBlock().setType(Material.AIR);
            l1.getBlock().setType(Material.AIR);
            l2.getBlock().setType(Material.AIR);
            Entity entity = e.getPlayer().getWorld().spawnEntity(l1, EntityType.SNOWMAN);
            PlayerListener.entityby.put(entity,SkyWars.getInstance().getPlayerManager().get(e.getPlayer()));
        }
    }
    private ItemStack lapis;

    public WorldListener() {
        final Dye d = new Dye();
        d.setColor(DyeColor.BLUE);
        (this.lapis = d.toItemStack()).setAmount(3);
    }

    @EventHandler
    public void openInventoryEvent(final InventoryOpenEvent e) {
        if (e.getInventory() instanceof EnchantingInventory) {
            e.getInventory().setItem(1, this.lapis);
        }
    }

    @EventHandler
    public void closeInventoryEvent(final InventoryCloseEvent e) {
        if (e.getInventory() instanceof EnchantingInventory) {
            e.getInventory().setItem(1, new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void inventoryClickEvent(final InventoryClickEvent e) {
        if (e.getClickedInventory() instanceof EnchantingInventory && e.getSlot() == 1) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void enchantItemEvent(final EnchantItemEvent e) {
            e.getInventory().setItem(1, this.lapis);
    }
}
