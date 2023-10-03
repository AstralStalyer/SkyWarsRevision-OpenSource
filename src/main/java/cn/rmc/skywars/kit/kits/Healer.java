package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Healer extends KitBasic {

    public Healer(UUID uuid){
        super(uuid, KitType.Healer);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0, ItemUtil.addPotionEffect(new Potion(PotionType.INSTANT_HEAL).splash().toItemStack(3),new PotionEffect(PotionEffectType.HEAL,1,0)));
        inv.setItem(1, ItemUtil.addPotionEffect(new Potion(PotionType.FIRE_RESISTANCE).splash().toItemStack(2),new PotionEffect(PotionEffectType.REGENERATION,20*33,0)));
    }
}
