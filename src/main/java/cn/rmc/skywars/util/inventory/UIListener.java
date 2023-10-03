package cn.rmc.skywars.util.inventory;

import cn.rmc.skywars.Inventory.MenuBasic;
import cn.rmc.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class UIListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory() == null) {
			return;
		}
		if (!(event.getInventory().getHolder() instanceof InventoryUI.InventoryUIHolder)) {
			return;
		}
		if (event.getCurrentItem() == null) {
			return;
		}

		InventoryUI.InventoryUIHolder inventoryUIHolder = (InventoryUI.InventoryUIHolder) event.getInventory().getHolder();

		event.setCancelled(true);

		if (event.getClickedInventory() == null || !event.getInventory().equals(event.getClickedInventory())) {
			return;
		}
		InventoryUI ui = inventoryUIHolder.getInventoryUI();
		InventoryUI.ClickableItem item = ui.getCurrentUI().getItem(event.getSlot());

		if (item == null) {
			return;
		}
		item.onClick(event);
	}


}
