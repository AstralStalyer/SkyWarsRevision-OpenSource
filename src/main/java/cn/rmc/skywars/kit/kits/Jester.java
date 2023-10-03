package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ChestUtils;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Jester extends KitBasic {

    public Jester(UUID uuid){
        super(uuid, KitType.Jester);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        List<Material> itemStackList = Arrays.asList(Material.DIAMOND_SWORD,Material.GOLD_SWORD,Material.IRON_SWORD,Material.STONE_SWORD,
                Material.STONE_SWORD,Material.WOOD_SWORD);
        inv.setItem(0,new ItemBuilder(itemStackList.get(ChestUtils.getrandomint(0,itemStackList.size()-1))).toItemStack());
        inv.setItem(1,ItemUtil.addPotionEffect(new Potion(PotionType.INSTANT_HEAL).splash().toItemStack(1),new PotionEffect(PotionEffectType.REGENERATION,20*10,1)));
    }
}
