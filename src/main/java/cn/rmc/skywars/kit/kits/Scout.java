package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Scout extends KitBasic {
    public Scout(UUID uuid){
        super(uuid, KitType.Scout);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,ItemUtil.addPotionEffect(new Potion(PotionType.SPEED).splash().toItemStack(3),new PotionEffect(PotionEffectType.SPEED,20*15,1)));
    }
}
