package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.UUID;

public class Armorsmith extends KitBasic {

    public Armorsmith(UUID uuid){
        super(uuid, KitType.Armorsmith);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.ANVIL).toItemStack());
        ItemStack enchant = new ItemBuilder(Material.ENCHANTED_BOOK).toItemStack();
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchant.getItemMeta();
        meta.addStoredEnchant(Enchantment.DAMAGE_ALL,1,true);
        meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,3,true);
        enchant.setItemMeta(meta);
        inv.setItem(1,enchant);
        inv.setItem(2,new ItemBuilder(Material.EXP_BOTTLE,64).toItemStack());
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).toItemStack());
    }
}
