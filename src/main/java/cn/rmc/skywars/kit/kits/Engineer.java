package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Engineer extends KitBasic {

    public Engineer(UUID uuid){
        super(uuid, KitType.Engineer);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.TRIPWIRE_HOOK,2).toItemStack());
        inv.setItem(1,new ItemBuilder(Material.WEB,8).toItemStack());
        inv.setItem(2,new ItemBuilder(Material.PISTON_BASE,4).toItemStack());
        inv.setItem(3,new ItemBuilder(Material.SLIME_BALL,4).toItemStack());
        inv.setItem(4,new ItemBuilder(Material.REDSTONE,16).toItemStack());
        inv.setItem(5,new ItemBuilder(Material.LEVER).toItemStack());
        inv.setItem(6,new ItemBuilder(Material.DISPENSER,2).toItemStack());
    }
}
