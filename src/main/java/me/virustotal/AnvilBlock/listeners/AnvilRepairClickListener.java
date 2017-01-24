package me.virustotal.AnvilBlock.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.virustotal.AnvilBlock.AnvilBlock;
import me.virustotal.AnvilBlock.AnvilConfig;
import me.virustotal.AnvilBlock.events.AnvilRepairClickEvent;

public class AnvilRepairClickListener implements Listener
{
	private AnvilBlock plugin;
	private AnvilConfig config;
	
	public AnvilRepairClickListener(AnvilBlock plugin) 
	{
		this.plugin = plugin;
		this.config = this.plugin.getAnvilConfig();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onRepair(AnvilRepairClickEvent e)
	{
		World world = e.getPlayer().getWorld();
		if(!config.enabledRepairsPluginWorlds.contains(world))
			return;
		if(config.repairsMethod.contains("repairs")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				e.setResult(Event.Result.DENY);
				e.setCancelled(true);						
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.repairscancelMessage));
				return;
			}
		}
		
		if(config.repairsMethod.contains("lore")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				int rawSlot = e.getRawSlot();
				ItemStack item = inv.getItem(rawSlot);
				if(item.hasItemMeta())
				{
					ItemMeta meta = item.getItemMeta();
					if(meta.hasLore()){
						if(meta.getLore().size() > 0)
						{
							for(int i = 0; i < meta.getLore().size(); i++)
							{
								for(int j = 0;j < config.repairsLoreList.size();j++)
								{
									if(meta.getLore().get(i).contains(config.repairsLoreList.get(j)))
									{
										e.setResult(Event.Result.DENY);
										e.setCancelled(true);						
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.repairscancelMessage));
										return;
									}
								}										
							}
						}
					}							
				}	
			}
		}
		
		if(config.repairsMethod.contains("customname")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				int rawSlot = e.getRawSlot();
				ItemStack item = inv.getItem(rawSlot);
				if(item.hasItemMeta())
				{
					ItemMeta meta = item.getItemMeta();
					if(meta.hasDisplayName())
					{
						for(int j = 0;j < config.repairsCustomNameList.size();j++)
						{
							if(meta.getDisplayName() == (config.repairsCustomNameList.get(j)))
							{
								e.setResult(Event.Result.DENY);
								e.setCancelled(true);						
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.repairscancelMessage));
								return;
							}
						}
					}
				}
			}
		}
		
		if(config.repairsMethod.contains("materialtype")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				int rawSlot = e.getRawSlot();
				ItemStack item = inv.getItem(rawSlot);
				if(item.getType()!=null && item.getType()!=Material.AIR)
				{
					Material mat = item.getType();
					for(int j = 0;j < config.repairsMaterialList.size();j++)
					{
						if(mat == (config.repairsMaterialList.get(j)))
						{
							e.setResult(Event.Result.DENY);
							e.setCancelled(true);						
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.repairscancelMessage));
							return;
						}
					}
				}
			}
		}
	}
}