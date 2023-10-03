package cn.rmc.skywars.task;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.enums.GameEvent;
import cn.rmc.skywars.enums.GameState;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.TimeUtils;
import me.arasple.mc.trhologram.TrHologram;
import me.arasple.mc.trhologram.api.TrHologramAPI;
import me.arasple.mc.trhologram.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class GameEventTask implements Runnable {

    Game g;
    int refill = 180;
    Boolean isrefilledtwice = false;
    Boolean isrefilled = false;
    int endergragon = 360;
    Boolean isSummoned = false;
    int trial = 600;
    public GameEventTask(Game g){
        this.g = g;
    }
    @Override
    public void run() {
        if(g.getState() == GameState.ENDING){
            g.setEvent(GameEvent.End);
            g.getEventtask().cancel();
        }
        if(refill >= 0){
            g.setEventLeftTime(refill);
            g.setEvent(GameEvent.Refill);
            for(Location loc : g.getMap().openchests.keySet()){
                if(g.getMap().openchests.get(loc) == null && loc.getBlock().getType() != Material.CHEST){
                    Location hololoc = loc.clone();
                    hololoc.setX(loc.getX()+0.5);
                    hololoc.setY(loc.getY()+0.3);
                    hololoc.setZ(loc.getZ()+0.5);
                    Hologram holo = TrHologramAPI.createHologram(SkyWars.getInstance(),hololoc.toString(),hololoc,"","§a"+ TimeUtils.formatIntoMMSS(g.getEventLeftTime()));
                    g.getMap().openchests.put(loc,holo);
                }
                if(g.getMap().openchests.get(loc) == null) continue;
                if(loc.getBlock().getType() == Material.CHEST) {
                    Chest chest = (Chest) loc.getBlock().getState();
                    Hologram hologram = g.getMap().openchests.get(loc);
                    Boolean isempty = true;
                    for (ItemStack item : chest.getInventory().getContents()) {
                        if (item != null) {
                            isempty = false;
                        }
                    }
                    if (isempty) {
                        hologram.updateLines(Arrays.asList("§a" +TimeUtils.formatIntoMMSS(g.getEventLeftTime()),"§c空的!"));
                    } else {
                        hologram.updateLines(Arrays.asList("","§a" + TimeUtils.formatIntoMMSS(g.getEventLeftTime())));
                    }
                }else{
                    g.getMap().openchests.get(loc).updateLines(Arrays.asList("","§a" + TimeUtils.formatIntoMMSS(g.getEventLeftTime())));
                }
            }
            refill--;
            endergragon--;
            trial--;
            return;
        }
        if(!isrefilled) {
            isrefilled = true;
            for(Location loc : g.getMap().openchests.keySet()){
                if(g.getMap().openchests.get(loc) == null) continue;
                Hologram hologram = g.getMap().openchests.get(loc);
                hologram.delete();
            }
            refill = 120;
            Bukkit.getScheduler().runTask(SkyWars.getInstance(),
                    new ChestFillTask(g, new ItemBuilder(Material.COMPASS).toItemStack()));
            for(Location loc : g.getMap().isopened.keySet()){
                g.getMap().openchests.put(loc,null);
                g.getMap().isopened.put(loc,false);
            }
            return;
        }
        if(!isrefilledtwice) {
            isrefilledtwice = true;
            for (Location loc : g.getMap().openchests.keySet()) {
                if (g.getMap().openchests.get(loc) == null) continue;
                Hologram hologram = g.getMap().openchests.get(loc);
                hologram.delete();
            }
            Bukkit.getScheduler().runTask(SkyWars.getInstance(), new ChestFillTask(g, new ItemBuilder(Material.COMPASS).toItemStack()));
            for (Location loc : g.getMap().isopened.keySet()) {
                g.getMap().isopened.put(loc, false);
            }
            return;
        }
        if (endergragon >= 0) {
            g.setEventLeftTime(endergragon);
            g.setEvent(GameEvent.EnderDragon);
            endergragon--;
            trial--;
            return;
        }
        if (!isSummoned) {
            isSummoned = true;
            for (int i = 1; i <= g.getAliveplayers().size(); i++) {
                g.getMiddle().getWorld().spawnEntity(g.getMiddle(), EntityType.ENDER_DRAGON);
            }
            return;
        }
        if (trial >= 0) {
            g.setEventLeftTime(trial);
            g.setEvent(GameEvent.Trial);
            trial--;
            return;
        }
        g.getEventtask().cancel();
        g.forceend();
    }
}
