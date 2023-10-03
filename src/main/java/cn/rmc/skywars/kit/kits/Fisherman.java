package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Fisherman extends KitBasic {

    public Fisherman(UUID uuid){
        super(uuid, KitType.Fisherman);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.FISHING_ROD).addEnchant(Enchantment.DURABILITY,10)
        .addEnchant(Enchantment.KNOCKBACK,1).addEnchant(Enchantment.LUCK,10).addEnchant(Enchantment.LURE,7).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.COOKED_FISH,16).toItemStack());
    }
}
