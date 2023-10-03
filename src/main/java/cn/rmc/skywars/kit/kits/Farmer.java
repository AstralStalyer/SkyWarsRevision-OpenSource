package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Farmer extends KitBasic {

    public Farmer(UUID uuid){
        super(uuid, KitType.Farmer);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_PROJECTILE,3).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.EGG,64).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.GOLDEN_APPLE).toItemStack());
    }
}
