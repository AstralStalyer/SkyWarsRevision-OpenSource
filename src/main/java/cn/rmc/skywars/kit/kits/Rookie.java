package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Rookie extends KitBasic {

    public Rookie(UUID uuid){
        super(uuid, KitType.Rookie);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.WOOD_SWORD).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.GLASS,16).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.COOKED_BEEF).toItemStack());
        
    }
}
