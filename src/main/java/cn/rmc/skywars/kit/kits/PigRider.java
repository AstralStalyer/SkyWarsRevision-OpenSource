package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class PigRider extends KitBasic {

    public PigRider(UUID uuid){
        super(uuid, KitType.PigRider);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.GOLD_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.SADDLE).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.MONSTER_EGG,1,(byte) 90).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.CARROT_STICK).toItemStack());
    }
}
