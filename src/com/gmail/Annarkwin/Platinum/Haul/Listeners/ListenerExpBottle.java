package com.gmail.Annarkwin.Platinum.Haul.Listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;

public class ListenerExpBottle implements Listener {
	
	@EventHandler
	public void bottleSmashEvent(ExpBottleEvent e) {	
		List<String> lore = e.getEntity().getItem().getItemMeta().getLore();
		
		if (e.getEntity().getItem().getItemMeta().hasLore() && lore.get(0).contains("experience"))
				e.setExperience(Integer.parseInt(lore.get(0).split("o")[1].split(" ")[0]));
	}
}
