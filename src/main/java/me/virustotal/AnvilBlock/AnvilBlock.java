package me.virustotal.AnvilBlock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.virustotal.AnvilBlock.listeners.AnvilRenameClickListener;
import me.virustotal.AnvilBlock.listeners.AnvilRepairClickListener;
import me.virustotal.AnvilBlock.listeners.InventoryListener;
import net.md_5.bungee.api.ChatColor;

public class AnvilBlock extends JavaPlugin{
	
	private AnvilConfig config;
	private boolean doRepairs = false;
	private boolean doRenames = false;
	
	public void onEnable()
	{
		config = new AnvilConfig(this);
		config.loadConfig();
		if((config.repairsMethod==null || config.repairsMethod.isEmpty())
		&& (config.renamesMethod==null || config.renamesMethod.isEmpty())){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RESET 
					  + " You haven't enabled any methods in your config");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RED + " Disabling...");
			this.setEnabled(false);
		} 
		if((config.enabledRenamesPluginWorlds==null || config.enabledRenamesPluginWorlds.isEmpty())
		&& (config.enabledRepairsPluginWorlds==null || config.enabledRepairsPluginWorlds.isEmpty())){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RESET 
					  + " You haven't enabled any worlds in your config");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RED + " Disabling...");
			this.setEnabled(false);			
		}
		if(this.isEnabled()){
			registerEvents();
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RESET 
					  + " loaded " + ChatColor.GREEN + "successfully");
		}	
	}
	
	private void registerEvents()
	{
		Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
		if((config.repairsMethod!=null && !(config.repairsMethod.isEmpty()))){
			if(config.enabledRepairsPluginWorlds!=null && !(config.enabledRepairsPluginWorlds.isEmpty())){
				doRepairs = true;
				Bukkit.getPluginManager().registerEvents(new AnvilRepairClickListener(this), this);
			}
		}
		if(!doRepairs){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RESET 
					  + " You haven't enabled any repair methods in your config or"
					  + " you haven't enabled any repair worlds in your config");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.YELLOW + " Disabling Repair Listener for save Resources...");
		}
		if((config.renamesMethod!=null && !(config.renamesMethod.isEmpty()))){
			if(config.enabledRenamesPluginWorlds!=null && !(config.enabledRenamesPluginWorlds.isEmpty())){
				doRenames = true;
				Bukkit.getPluginManager().registerEvents(new AnvilRenameClickListener(this), this);
			}
		}
		if(!doRenames){
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.RESET 
					  + " You haven't enabled any rename methods in your config or"
					  + " you haven't enabled any rename worlds in your config");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "["
					  + ChatColor.GRAY +"AnvilBlock" + ChatColor.AQUA + "]" + ChatColor.YELLOW + " Disabling Rename Listener for save Resources...");
		}
	}
	
	public AnvilConfig getAnvilConfig()
	{
		return config;
	}

	public boolean isDoRepairs() {
		return doRepairs;
	}

	public boolean isDoRenames() {
		return doRenames;
	}

}
