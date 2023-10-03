package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ChestUtils;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Disco extends KitBasic {

    public Disco(UUID uuid){
        super(uuid, KitType.Disco);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.GOLD_HELMET).addEnchant(Enchantment.PROTECTION_PROJECTILE,4).setLore("§a我大喊...").toItemStack());
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2).addEnchant(Enchantment.THORNS,3).setLeatherArmorColor(Color.PURPLE).setLore("嗨呀呀呀嗨").toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLore("§a我大喊 嘿!").setLeatherArmorColor(Color.GRAY).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLore("§a想知道, 未来去向何方?").setLeatherArmorColor(Color.GRAY).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.JUKEBOX).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.NOTE_BLOCK,12).toItemStack());
        List<Material> itemStackList = Arrays.asList(Material.RECORD_3,Material.RECORD_4,Material.RECORD_5,Material.RECORD_6, Material.RECORD_7,Material.RECORD_8,Material.RECORD_9,Material.RECORD_10,Material.RECORD_11
        ,Material.RECORD_12);
        Material result = itemStackList.get(ChestUtils.getrandomint(0,itemStackList.size()-1));
        inv.setItem(2,new ItemBuilder(result).toItemStack());
    }
}
