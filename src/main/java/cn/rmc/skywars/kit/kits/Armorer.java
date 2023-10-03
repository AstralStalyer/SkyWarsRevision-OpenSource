package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Armorer extends KitBasic {

    //装备师
    public Armorer(UUID uuid){
        super(uuid, KitType.Armorer);
    }
    @Override
    public void GiveItem() {
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.GOLD_CHESTPLATE).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.GOLD_LEGGINGS).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.GOLD_BOOTS).toItemStack());
    }
}
