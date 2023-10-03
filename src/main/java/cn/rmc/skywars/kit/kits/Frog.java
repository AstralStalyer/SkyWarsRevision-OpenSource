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

public class Frog extends KitBasic {

    public Frog(UUID uuid){
        super(uuid, KitType.Frog);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        getPlayer().getInventory().setHelmet(ItemUtil.getCustomSkull("ewogICJ0aW1lc3RhbXAiIDogMTU5NTMxOTUxNDgyMSwKICAicHJvZmlsZUlkIiA6ICI1NjY3NWIyMjMyZjA0ZWUwODkxNzllOWM5MjA2Y2ZlOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUaGVJbmRyYSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kMmIyMTJiNzNiYmEwNjRkNWYyZmUwNjFlNDZmZmZmNzMwZWQ1NWZiODM2N2NiZTEyZTcxYmRiMjdhYzAwZGU3IgogICAgfQogIH0KfQ=="));
        getPlayer().getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.GREEN).toItemStack());
        getPlayer().getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.GREEN).toItemStack());
        getPlayer().getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.GREEN).toItemStack());
        inv.setItem(0,ItemUtil.addPotionEffect(new Potion(PotionType.SPEED).splash().toItemStack(1),new PotionEffect(PotionEffectType.SPEED,20*20,1),
                new PotionEffect(PotionEffectType.JUMP,20*20,1)));
    }
}
