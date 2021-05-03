package com.gmail.Annarkwin.Platinum.Haul;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp.CommandBottleExp;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerCrafting;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerExpBottle;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerPokemon;

public class Haul extends JavaPlugin {
	
	@Override
	public void onEnable(){		
		//Load configuration serializable classes
		registerSerializables();
		
		//Retrieve file data
		
		//Enable plugin features
		enableListeners();
		enableCommands();
		//Initialize update event
	}
	
	@Override
	public void onDisable(){
		//Save data
		
	}
	
	private void registerSerializables() {
		
	}
	
	public void enableCommands(){
		getCommand("BottleExp").setExecutor(new CommandBottleExp());
	}
	
	public void enableListeners(){
		getServer().getPluginManager().registerEvents(new ListenerPokemon(), this);
		getServer().getPluginManager().registerEvents(new ListenerExpBottle(), this);
		getServer().getPluginManager().registerEvents(new ListenerCrafting(), this);
	}
}
