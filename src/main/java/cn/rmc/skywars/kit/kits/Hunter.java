package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Hunter extends KitBasic {

    public Hunter(UUID uuid){
        super(uuid, KitType.Hunter);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE,1).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.ARROW,16).toItemStack());

    }
}
