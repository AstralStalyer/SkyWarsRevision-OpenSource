package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Ecologist extends KitBasic {

    public Ecologist(UUID uuid){
        super(uuid, KitType.Ecologist);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.IRON_AXE).addEnchant(Enchantment.DIG_SPEED,1).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.LOG,16).toItemStack());

    }
}
