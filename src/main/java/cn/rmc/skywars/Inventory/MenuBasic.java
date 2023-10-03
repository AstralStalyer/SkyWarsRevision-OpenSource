package cn.rmc.skywars.Inventory;

import cn.rmc.skywars.util.inventory.InventoryUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MenuBasic implements Listener {
    public static HashMap<Player,ArrayList<MenuBasic>> uis = new HashMap<>();
    protected InventoryUI inventoryUI;
    protected Player player;
    protected MenuBasic mb;
    public MenuBasic(Player p, String title, Integer rows){
        this.player = p;
        inventoryUI = new InventoryUI(title,rows);
        mb = this;
        if(!uis.containsKey(uis)){
            uis.put(p,new ArrayList<>());
        }
        ArrayList<MenuBasic> al = uis.get(p);
        al.add(this);
        uis.put(p,al);
    }
    public MenuBasic(String title, Integer rows){
        inventoryUI = new InventoryUI(title,rows);
        mb = this;
    }
    protected abstract void Setup();
    public void open(){
        Setup();
        player.openInventory(inventoryUI.getCurrentPage());
    }
    public void destery(){
        inventoryUI = null;
        mb = null;
    }
}
