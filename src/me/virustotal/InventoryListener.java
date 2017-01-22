package me.virustotal;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener{

	private AnvilBlock plugin;
	public InventoryListener(AnvilBlock plugin) 
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void inventoryListener(InventoryClickEvent e)
	{
		if(!e.isCancelled()){

			if(e.getWhoClicked() instanceof Player){
				Player player = (Player)e.getWhoClicked();
				Inventory inv = e.getInventory();

				// see if the event is about an anvil
				if(inv instanceof AnvilInventory){
					InventoryView view = e.getView();
					int rawSlot = e.getRawSlot();

					// compare the raw slot with the inventory view to make sure we are talking about the upper inventory
					if(rawSlot == view.convertSlot(rawSlot)){
						if(rawSlot == 2){

							ItemStack item = inv.getItem(rawSlot);
							if(item.hasItemMeta())
							{
								if(item.getItemMeta().getLore().size() > 0)
								{
									for(int i = 0; i < item.getItemMeta().getLore().size(); i++)
									{
										for(int j = 0;j < plugin.loreList.size();j++)
										{
											if(item.getItemMeta().getLore().get(i).contains(plugin.loreList.get(j)))
											{
												float level = player.getExp();
												e.setCancelled(true);
												player.setExp(level);
												player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.cancelMessage));
											}
										}
											
									}
								}
							}
							
						}
					}
				}
			}
		}
	}
}
