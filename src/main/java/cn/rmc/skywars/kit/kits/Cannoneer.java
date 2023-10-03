package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Cannoneer extends KitBasic {

    public Cannoneer(UUID uuid){
        super(uuid, KitType.Cannoneer);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).addEnchant(Enchantment.PROTECTION_FALL,3).addEnchant(Enchantment.PROTECTION_EXPLOSIONS,3).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.TNT,16).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.REDSTONE_BLOCK,4).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.WATER_BUCKET).toItemStack());
        inv.setItem(3,new ItemBuilder(Material.STONE_PLATE,4).toItemStack());

    }
}
