package me.virustotal.AnvilBlock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryView;

public class AnvilRenameClickEvent extends InventoryClickEvent implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	
	public AnvilRenameClickEvent(InventoryView view, SlotType type, int slot, ClickType click, InventoryAction action, Player player) {
		super(view, type, slot, click, action);
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}