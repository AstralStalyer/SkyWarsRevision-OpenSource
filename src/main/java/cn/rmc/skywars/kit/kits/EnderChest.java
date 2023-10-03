package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class EnderChest extends KitBasic {

    public EnderChest(UUID uuid){
        super(uuid, KitType.EnderChest);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.GOLDEN_APPLE).toItemStack());
    }
}
