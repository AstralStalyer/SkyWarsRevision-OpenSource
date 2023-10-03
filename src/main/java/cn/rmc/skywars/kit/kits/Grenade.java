package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Grenade extends KitBasic {

    public Grenade(UUID uuid){
        super(uuid, KitType.Grenade);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.MONSTER_EGG,1,(byte)50).setName("§a苦力怕蛋").toItemStack());
    }
}
