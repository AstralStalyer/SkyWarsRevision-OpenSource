package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Pharaoh extends KitBasic {

    public Pharaoh(UUID uuid){
        super(uuid, KitType.Pharaoh);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.GOLD_HELMET).toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.WHITE).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.WHITE).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.BEACON).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.EMERALD_BLOCK,42).toItemStack());
    }
}
