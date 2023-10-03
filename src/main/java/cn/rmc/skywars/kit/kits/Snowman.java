package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Snowman extends KitBasic {

    public Snowman(UUID uuid){
        super(uuid, KitType.Snowman);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.SNOW_BALL,16).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.SNOW_BLOCK,2).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.IRON_SPADE).toItemStack());
        inv.setItem(3,new ItemBuilder(Material.PUMPKIN).toItemStack());
    }
}
