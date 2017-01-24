package me.virustotal.AnvilBlock.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import me.virustotal.AnvilBlock.AnvilBlock;
import me.virustotal.AnvilBlock.AnvilConfig;
import me.virustotal.AnvilBlock.events.AnvilRenameClickEvent;
import me.virustotal.AnvilBlock.events.AnvilRepairClickEvent;

public class InventoryListener implements Listener{

	private AnvilBlock plugin;
	private AnvilConfig config;
	
	public InventoryListener(AnvilBlock plugin) 
	{
		this.plugin = plugin;
		this.config = this.plugin.getAnvilConfig();
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onRepair(InventoryClickEvent e)
	{
		if(this.plugin.isDoRepairs()){
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
							if(inv.getItem(0)!= null && inv.getItem(1) != null){
								if(inv.getItem(0).getType() == inv.getItem(1).getType()){
									AnvilRepairClickEvent event = new AnvilRepairClickEvent(e.getView(), e.getSlotType(), e.getRawSlot(), e.getClick(), e.getAction(), player);
									Bukkit.getServer().getPluginManager().callEvent(event);
									if(event.isCancelled()){
										float level = player.getExp();
										e.setResult(Event.Result.DENY);
										e.setCancelled(true);
										player.setExp(level);
										return;
									}
								}
							}
							
						}
					}
				}
			}
		}	
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onRename(InventoryClickEvent e)
	{
		if(this.plugin.isDoRenames()){
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
							if(inv.getItem(0) != null && inv.getItem(0).getType() != Material.AIR){
								if(inv.getItem(1) == null || inv.getItem(1).getType() == Material.AIR){
									AnvilRenameClickEvent event = new AnvilRenameClickEvent(e.getView(), e.getSlotType(), e.getRawSlot(), e.getClick(), e.getAction(), player);
									Bukkit.getServer().getPluginManager().callEvent(event);
									if(event.isCancelled()){
										float level = player.getExp();
										e.setResult(Event.Result.DENY);
										e.setCancelled(true);
										player.setExp(level);
										return;
									}
								}
							}
						}
					}
				}
			}
		}		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAnvilOpen(InventoryOpenEvent e){
		if(this.config.renamesMethod.contains("all") || this.config.repairsMethod.contains("all")){
			Inventory inv = e.getInventory();
			if(inv instanceof AnvilInventory){
				if(e.getPlayer() instanceof Player){
					Player player = (Player) e.getPlayer();
					if(!(player.hasPermission("AnvilBlock.bypass"))){
						e.setCancelled(true);
						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.config.repairscancelMessage));
					}					
				}				
			}
		}
	}
	
}
