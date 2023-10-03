package cn.rmc.skywars.Inventory;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.enums.Data;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.kit.KitType;
import cn.rmc.skywars.util.DataUtils;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.inventory.InventoryUI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class KitSelector extends MenuBasic {

    PlayerData pd;
    public KitSelector(PlayerData pd){
        super(pd.getPlayer(),"职业选择",6);
        this.pd = pd;
    }
    public void Setup() {
        int i = 0;
        ArrayList<KitType> kitTypes = new ArrayList<>();
        Collections.addAll(kitTypes, KitType.values());
        ArrayList<KitType> needdelete = new ArrayList<>();
        for (KitType kit : kitTypes) {
            if (DataUtils.get(pd.getPlayer(), Data.field.ISBOUGHT, pd.getGame().getTotalmode(), kit) != null || kit == KitType.Default) {
                if (kit == pd.getKit().getType()) {
                    inventoryUI.setItem(i, new InventoryUI.EmptyClickableItem(kit.getSelectedItem()));
                } else {
                    inventoryUI.setItem(i, new InventoryUI.AbstractClickableItem(kit.getItem()) {
                        @Override
                        public void onClick(InventoryClickEvent e) {
                            Player p = (Player) e.getWhoClicked();
                            PlayerData pd = SkyWars.getInstance().getPlayerManager().get(p);
                            pd.setKit(kit, false);
                            new KitSelector(pd).open();
                            p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1, 1);
                        }
                    });

                }
                needdelete.add(kit);
                i++;
            }

        }
        kitTypes.removeAll(needdelete);
        for (KitType kit : kitTypes) {
            inventoryUI.setItem(i, new InventoryUI.AbstractClickableItem(kit.getunlockedItem()) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    Player p = (Player) e.getWhoClicked();
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                    p.sendMessage("§c你尚未拥有该职业");
                }
            });
            i++;
        }
        inventoryUI.setItem(49, new InventoryUI.AbstractClickableItem(new ItemBuilder(Material.BARRIER).setName("§c关闭").toItemStack()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                e.getWhoClicked().closeInventory();
            }
        });
        for (; i < 54; i++) {
            if (inventoryUI.getItem(i) == null) {
                inventoryUI.setItem(i, new InventoryUI.EmptyClickableItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName("§r").toItemStack()));
            }
        }
    }
    public void open(){
        Setup();
        pd.getPlayer().openInventory(inventoryUI.getCurrentPage());
    }
}
