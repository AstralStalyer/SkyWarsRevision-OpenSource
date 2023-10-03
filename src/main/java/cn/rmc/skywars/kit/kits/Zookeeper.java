package cn.rmc.skywars.kit.kits;

import cn.rmc.skywars.kit.KitBasic;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Zookeeper extends KitBasic {

    public Zookeeper(UUID uuid){
        super(uuid, KitType.Zookeeper);
    }

    @Override
    public void GiveItem() {
        Inventory inv = getPlayer().getInventory();
        inv.clear();
        inv.setItem(0,new ItemBuilder(Material.MONSTER_EGG,3).setName("§d神秘之蛋").setLore("§7随机生成怪物").toItemStack());
    }
}
