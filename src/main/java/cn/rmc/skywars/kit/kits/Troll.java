package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Troll extends KitBasic {

    public Troll(UUID uuid){
        super(uuid, KitType.Troll);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.PURPLE).toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.PURPLE).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.PURPLE).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.PURPLE).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.WEB,16).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.FIREWORK,5).toItemStack());
    }
}
