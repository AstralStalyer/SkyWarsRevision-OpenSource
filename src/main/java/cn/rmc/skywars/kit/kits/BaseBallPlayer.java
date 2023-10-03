package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class BaseBallPlayer extends KitBasic {
    public BaseBallPlayer(UUID uuid){
        super(uuid, KitType.BaseBallPlayer);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,1).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.WOOD_SWORD).addEnchant(Enchantment.KNOCKBACK,1).toItemStack());
    }
}
