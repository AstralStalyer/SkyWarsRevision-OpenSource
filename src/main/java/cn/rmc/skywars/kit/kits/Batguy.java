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

public class Batguy extends KitBasic {

    public Batguy(UUID uuid){
        super(uuid, KitType.Batguy);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLACK).toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLACK).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.BLACK).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.BLACK).toItemStack());
        inv.setItem(0, ItemUtil.renameItem(ItemUtil.addPotionEffect(new Potion(PotionType.SLOWNESS).splash().toItemStack(1),new PotionEffect(PotionEffectType.SLOW,6*20,0)),"§a蝙蝠人药水 (8秒)"));
    }
}
