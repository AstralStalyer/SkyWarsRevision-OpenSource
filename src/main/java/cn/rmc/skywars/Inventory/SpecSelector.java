package cn.rmc.skywars.Inventory;

import cn.rmc.skywars.SkyWars;
import cn.rmc.skywars.game.Game;
import cn.rmc.skywars.game.PlayerData;
import cn.rmc.skywars.util.ActionBarUtils;
import cn.rmc.skywars.util.ItemBuilder;
import cn.rmc.skywars.util.ItemUtil;
import cn.rmc.skywars.util.LuckPerms;
import cn.rmc.skywars.util.database.KeyValue;
import cn.rmc.skywars.util.inventory.InventoryUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class SpecSelector extends MenuBasic{
    PlayerData pd;
    public SpecSelector(PlayerData pd){
        super(pd.getPlayer(),"旁观者选择",2);
        this.pd = pd;

    }
    public void Setup(){
        Game g = pd.getSpecGame();
        ArrayList<String> str = new ArrayList<>();
        g.getAliveplayers().forEach(playerData -> str.add(playerData.getPlayer().getName()));
        Collections.sort(str);
        ArrayList<Player> players = new ArrayList<>();
        str.forEach(player -> players.add(Bukkit.getPlayer(player)));
        for(int i = 0;i<=17;i++){
            if(i+1 <= players.size()){
                Player p = players.get(i);
                ItemBuilder ib = new ItemBuilder(Material.SKULL_ITEM,1,(byte)3);
                ib.setSkullOwner(p.getName());
                ib.setName(LuckPerms.getPrefix(p).replace("&","§")+p.getName());
                ib.addLoreLine("§7血量: §f"+p.getHealth()*5+"%");
                ib.addLoreLine("§7饱和度: §f"+p.getFoodLevel()*5+"%");
                ib.addLoreLine("");
                ib.addLoreLine("§7左键点击旁观!");
                if(ib == null) continue;
                inventoryUI.setItem(i, new InventoryUI.AbstractClickableItem(ib.toItemStack()) {
                    @Override
                    public void onClick(InventoryClickEvent e) {
                        if(e.getClick() == ClickType.LEFT){
                            if(g.getAliveplayers().contains(SkyWars.getInstance().getPlayerManager().get(p))){
                                e.getWhoClicked().teleport(p.getLocation());
                            }else{
                                pd.getPlayer().closeInventory();
                                ActionBarUtils.sendActionBar(pd.getPlayer(),"§c目标丢失!");
                            }
                        }
                    }
                });
            }else{
                inventoryUI.setItem(i, new InventoryUI.EmptyClickableItem(new ItemStack(Material.AIR)));
            }
        }
    }


}
