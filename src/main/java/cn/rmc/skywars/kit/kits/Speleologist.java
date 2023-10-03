package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Speleologist extends KitBasic {

    public Speleologist(UUID uuid){
        super(uuid, KitType.Speleologist);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.IRON_PICKAXE).addEnchant(Enchantment.DIG_SPEED,3).addEnchant(Enchantment.DAMAGE_ALL,1).addEnchant(Enchantment.DURABILITY,3).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.LOG,16).toItemStack());

    }
}
