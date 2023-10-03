package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Enchanter extends KitBasic {

    public Enchanter(UUID uuid){
        super(uuid, KitType.Enchanter);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.ENCHANTMENT_TABLE).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.EXP_BOTTLE,64).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.BOOKSHELF,8).toItemStack());
    }
}
