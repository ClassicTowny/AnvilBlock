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
import me.virustotal.AnvilBlock.events.AnvilRenameClickEvent;

public class AnvilRenameClickListener implements Listener
{
	private AnvilBlock plugin;
	private AnvilConfig config;
	
	public AnvilRenameClickListener(AnvilBlock plugin) 
	{
		this.plugin = plugin;
		this.config = this.plugin.getAnvilConfig();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onRepair(AnvilRenameClickEvent e)
	{
		World world = e.getPlayer().getWorld();
		if(!config.enabledRenamesPluginWorlds.contains(world))
			return;
		if(config.renamesMethod.contains("renames")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				e.setResult(Event.Result.DENY);
				e.setCancelled(true);						
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.repairscancelMessage));
				return;
			}
		}
		
		if(config.renamesMethod.contains("lore")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				ItemStack item = inv.getItem(0);
				if(item.hasItemMeta())
				{
					ItemMeta meta = item.getItemMeta();
					if(meta.hasLore()){
						if(meta.getLore().size() > 0)
						{
							for(int i = 0; i < meta.getLore().size(); i++)
							{
								for(int j = 0;j < config.renamesLoreList.size();j++)
								{
									if(meta.getLore().get(i).contains(config.renamesLoreList.get(j)))
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
		
		if(config.renamesMethod.contains("customname")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				ItemStack item = inv.getItem(0);
				if(item.hasItemMeta())
				{
					ItemMeta meta = item.getItemMeta();
					if(meta.hasDisplayName())
					{
						for(int j = 0;j < config.renamesCustomNameList.size();j++)
						{
							if(meta.getDisplayName() == (config.renamesCustomNameList.get(j)))
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
		
		if(config.renamesMethod.contains("materialtype")){
			Player player = e.getPlayer();
			if(!(player.hasPermission("AnvilBlock.bypass"))){
				Inventory inv = e.getInventory();
				ItemStack item = inv.getItem(0);
				if(item.getType()!=null && item.getType()!=Material.AIR)
				{
					Material mat = item.getType();
					for(int j = 0;j < config.renamesMaterialList.size();j++)
					{
						if(mat == (config.renamesMaterialList.get(j)))
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