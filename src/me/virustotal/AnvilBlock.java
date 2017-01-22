package me.virustotal;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilBlock extends JavaPlugin{
	
	private File file = new File(this.getDataFolder().getPath(),"config.yml");
	public String cancelMessage;
	public List<String> loreList;
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
		if(!file.exists())
		this.saveDefaultConfig();
		else
		this.reloadConfig();
		loreList = this.getConfig().getStringList("lore");
		this.cancelMessage = this.getConfig().getString("message-cancelled");
	}

}
