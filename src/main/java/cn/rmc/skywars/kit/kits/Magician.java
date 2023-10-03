package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Magician extends KitBasic {

    public Magician(UUID uuid){
        super(uuid, KitType.Magician);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLACK).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.MONSTER_EGG,1,(byte)101).toItemStack());
        inv.setItem(1, ItemUtil.renameItem(ItemUtil.addPotionEffect(new Potion(PotionType.INVISIBILITY).splash().toItemStack(2),new PotionEffect(PotionEffectType.INVISIBILITY,20*8,0))
                ,"§7魔术师药水"));
        inv.setItem(2,new ItemBuilder(Material.STICK).addEnchant(Enchantment.DAMAGE_ALL,4).setName("§7魔术师法杖").toItemStack());

    }
}
