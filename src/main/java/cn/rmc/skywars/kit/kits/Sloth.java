package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Sloth extends KitBasic {

    public Sloth(UUID uuid){
        super(uuid, KitType.Sloth);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.RED).toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.RED).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.RED).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.RED).toItemStack());
        inv.setItem(0, ItemUtil.renameItem(ItemUtil.addPotionEffect(new Potion(PotionType.SLOWNESS).splash().toItemStack(3),new PotionEffect(PotionEffectType.SLOW,20*5,1))
                ,"§7树懒药水"));
        inv.setItem(1,new ItemBuilder(Material.LOG,4).toItemStack());


    }
}
