package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Salmon extends KitBasic {

    public Salmon(UUID uuid){
        super(uuid, KitType.Salmon);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.DEPTH_STRIDER,3).toItemStack());
        for(int i = 0;i<3;i++){
            inv.setItem(i,new ItemBuilder(Material.WATER_BUCKET).toItemStack());
        }
    }
}
