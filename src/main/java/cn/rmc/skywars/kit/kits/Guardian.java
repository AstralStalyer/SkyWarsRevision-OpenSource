package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Guardian extends KitBasic {

    public Guardian(UUID uuid){
        super(uuid, KitType.Guardian);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.OBSIDIAN,6).toItemStack());
        inv.setItem(1, ItemUtil.addPotionEffect(new Potion(PotionType.INSTANT_HEAL).splash().toItemStack(1),new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*10,0)));
        inv.setItem(2,new ItemBuilder(Material.MONSTER_EGG,2,(byte)51).toItemStack());
        inv.setItem(3,new ItemBuilder(Material.MONSTER_EGG,2,(byte)54).toItemStack());
    }
}
