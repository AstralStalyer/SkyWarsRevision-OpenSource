package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Princess extends KitBasic {

    public Princess(UUID uuid){
        super(uuid, KitType.Princess);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.GOLD_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.BOW).setDurability((short)14).addEnchant(Enchantment.ARROW_FIRE,0).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.ARROW).toItemStack());
    }
}
