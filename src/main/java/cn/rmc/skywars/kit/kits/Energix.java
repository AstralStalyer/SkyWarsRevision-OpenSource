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

public class Energix extends KitBasic {

    public Energix(UUID uuid){
        super(uuid, KitType.Energix);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0, ItemUtil.addPotionEffect(new Potion(PotionType.STRENGTH).toItemStack(1),new PotionEffect(PotionEffectType.INCREASE_DAMAGE,5*20,0)));
    }
}
