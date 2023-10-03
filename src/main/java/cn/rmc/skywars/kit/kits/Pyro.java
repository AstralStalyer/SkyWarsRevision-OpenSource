package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class Pyro extends KitBasic {

    public Pyro(UUID uuid){
        super(uuid, KitType.Pyro);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).toItemStack());
        inv.setItem(0,new ItemBuilder(Material.FLINT_AND_STEEL).addEnchant(Enchantment.DURABILITY,10).toItemStack());
        for(int i = 1;i<6;i++){
            inv.setItem(i,new ItemBuilder(Material.LAVA_BUCKET).toItemStack());
        }
        inv.setItem(6,ItemUtil.addPotionEffect(new Potion(PotionType.FIRE_RESISTANCE).splash().toItemStack(1),new PotionEffect(PotionEffectType.FIRE_RESISTANCE,99999*20,0)));
    }
}
