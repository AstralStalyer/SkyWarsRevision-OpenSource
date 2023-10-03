package cn.rmc.skywars.task;

import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.util.ChestUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class ChestFillTask implements Runnable{

    Game g;
    ItemStack[] itemStack;

    public ChestFillTask(Game g){
        this.g = g;
    }
    public ChestFillTask(Game g,ItemStack... itemStack){
        this.itemStack = itemStack;
        this.g = g;
    }
    @Override
    public void run() {
        if(itemStack == null) {
            for (Location smallchest : g.getMap().smallchests) {
                if(smallchest.getBlock().getType() != Material.CHEST) smallchest.getBlock().setType(Material.CHEST);
                refill(smallchest, ChestUtils.getNormalSmallChest());
            }
            for (Location middlechest : g.getMap().middlechests) {
                if(middlechest.getBlock().getType() != Material.CHEST) middlechest.getBlock().setType(Material.CHEST);
                refill(middlechest, ChestUtils.getNormalMiddleChest());
            }
        }else{
            for (Location smallchest : g.getMap().smallchests) {
                if(smallchest.getBlock().getType() != Material.CHEST) smallchest.getBlock().setType(Material.CHEST);
                ArrayList<ItemStack> smallChest = ChestUtils.getNormalSmallChest();
                smallChest.addAll(Arrays.asList(itemStack));
                refill(smallchest, smallChest);
            }
            for (Location middlechest : g.getMap().middlechests) {
                if(middlechest.getBlock().getType() != Material.CHEST) middlechest.getBlock().setType(Material.CHEST);
                ArrayList<ItemStack> middleChest = ChestUtils.getNormalMiddleChest();
                middleChest.addAll(Arrays.asList(itemStack));
                refill(middlechest, middleChest);
            }
        }

    }
    public ChestFillTask(Location loc){
        refill(loc,ChestUtils.getNormalSmallChest());
    }

    public void refill(Location loc, ArrayList<ItemStack> items){
        Chest chest = (Chest) loc.getBlock().getState();
        Inventory inv = chest.getBlockInventory();
        int size = items.size();
        int item = 0;
        for(int i:ChestUtils.getrandomints(size,0,26)){
            inv.setItem(i,items.get(item));
            item++;
        }
        chest.update();
    }
}
