package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Default extends KitBasic {


    public Default(UUID uuid){
        super(uuid, KitType.Default);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.WOOD_PICKAXE).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.WOOD_AXE).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.WOOD_HOE).toItemStack());
    }
}
