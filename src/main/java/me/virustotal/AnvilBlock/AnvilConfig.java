package me.virustotal.AnvilBlock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class AnvilConfig
{
	private AnvilBlock plugin;
	private File file;
	
	public List<String> repairsMethod;
	public List<String> repairsCustomNameList;
	public List<String> repairsLoreList;
	public List<Material> repairsMaterialList;
	public String repairscancelMessage;
	public List<World> enabledRepairsPluginWorlds;
	
	public List<String> renamesMethod;
	public List<String> renamesCustomNameList;
	public List<String> renamesLoreList;
	public List<Material> renamesMaterialList;
	public String renamescancelMessage;
	public List<World> enabledRenamesPluginWorlds;
	
	public AnvilConfig(AnvilBlock plugin){
		this.plugin = plugin;
		file = new File(this.plugin.getDataFolder().getPath(),"config.yml");
		this.repairsMethod = new ArrayList<String>();
		this.repairsCustomNameList = new ArrayList<String>();
		this.repairsLoreList = new ArrayList<String>();;
		this.repairsMaterialList = new ArrayList<Material>();;
		this.repairscancelMessage = "";
		this.enabledRepairsPluginWorlds = new ArrayList<World>();
		
		this.renamesMethod = new ArrayList<String>();
		this.renamesCustomNameList = new ArrayList<String>();
		this.renamesLoreList = new ArrayList<String>();;
		this.renamesMaterialList = new ArrayList<Material>();;
		this.renamescancelMessage = "";
		this.enabledRenamesPluginWorlds = new ArrayList<World>();
	}
	
	public void loadConfig()
	{
		if(!file.exists()){
			this.plugin.saveDefaultConfig();
		}else{
			this.plugin.reloadConfig();
		}
		loadRepairsConfig();
		loadRenamesConfig();
	}
	
	private void loadRepairsConfig(){
		List<String> repairMethodtemp = (this.plugin.getConfig().getStringList("repairs.methods"));
		if(repairMethodtemp!=null && !(repairMethodtemp.isEmpty())){
			for(String s : repairMethodtemp){
				repairsMethod.add(s.toLowerCase());
			}
		}
		repairsCustomNameList = this.plugin.getConfig().getStringList("repairs.methodtypes.customname");
		repairsLoreList = this.plugin.getConfig().getStringList("repairs.methodtypes.lore");
		List<String> tempRepairMaterialList = this.plugin.getConfig().getStringList("repairs.methodtypes.materialtype");
		if(tempRepairMaterialList!=null && !(tempRepairMaterialList.isEmpty())){
			for(String s : tempRepairMaterialList){
				s = s.toUpperCase();
				repairsMaterialList.add(Material.getMaterial(s));
			}
		}
		repairscancelMessage = this.plugin.getConfig().getString("repairs.messages.message-cancelled");
		List<String> tempRepairWorldList = this.plugin.getConfig().getStringList("repairs.enabledInWorlds");
		if(tempRepairWorldList!=null && !(tempRepairWorldList.isEmpty())){
			for(String s : tempRepairWorldList){
				if(Bukkit.getServer().getWorld(s)!=null){
					enabledRepairsPluginWorlds.add(Bukkit.getServer().getWorld(s));
				}else if(s.equalsIgnoreCase("all")){
					for(World world : Bukkit.getServer().getWorlds()){
						enabledRepairsPluginWorlds.add(world);
					}
				}
			}
		}
	}
	
	private void loadRenamesConfig(){
		List<String> renamesMethodtemp = (this.plugin.getConfig().getStringList("renames.methods"));
		if(renamesMethodtemp!=null && !(renamesMethodtemp.isEmpty())){
			for(String s : renamesMethodtemp){
				renamesMethod.add(s.toLowerCase());
			}
		}
		renamesCustomNameList = this.plugin.getConfig().getStringList("renames.methodtypes.customname");
		renamesLoreList = this.plugin.getConfig().getStringList("renames.methodtypes.lore");
		List<String> tempRenameMaterialList = this.plugin.getConfig().getStringList("renames.methodtypes.materialtype");
		if(tempRenameMaterialList!=null && !(tempRenameMaterialList.isEmpty())){
			for(String s : tempRenameMaterialList){
				s = s.toUpperCase();
				if(!(Material.getMaterial(s).equals(null))){
				    renamesMaterialList.add(Material.getMaterial(s));
				}
			}
		}
		renamescancelMessage = this.plugin.getConfig().getString("renames.messages.message-cancelled");
		List<String> tempRenameWorldList = this.plugin.getConfig().getStringList("renames.enabledInWorlds");
		if(tempRenameWorldList!=null && !(tempRenameWorldList.isEmpty())){
			for(String s : tempRenameWorldList){
				if(Bukkit.getServer().getWorld(s)!=null){
					enabledRenamesPluginWorlds.add(Bukkit.getServer().getWorld(s));
				}else if(s.equalsIgnoreCase("all")){
					for(World world : Bukkit.getServer().getWorlds()){
						enabledRenamesPluginWorlds.add(world);
					}
				}
			}
		}
	}
}