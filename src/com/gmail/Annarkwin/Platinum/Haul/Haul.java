package com.gmail.Annarkwin.Platinum.Haul;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.Annarkwin.Platinum.API.PlatinumMainCommand;
import com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp.BottleExpFill;
import com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp.BottleExpHelp;
import com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp.CommandBottleExp;
import com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant.EnchantF;
import com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant.EnchantHelp;
import com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant.EnchantN;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerCrafting;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerExpBottle;
import com.gmail.Annarkwin.Platinum.Haul.Listeners.ListenerPokemon;

public class Haul extends JavaPlugin
{

	@Override
	public void onEnable()
	{

		// Load configuration serializable classes
		registerSerializables();

		// Retrieve file data

		// Enable plugin features
		enableListeners();
		enableCommands();

		// Initialize update event
	}

	@Override
	public void onDisable()
	{
		// Save data

	}

	private void registerSerializables()
	{

	}

	public void enableCommands()
	{

		PlatinumMainCommand bottleexp = new CommandBottleExp("BottleEXP", "platinum.command.bottleexp", true,
				"Bottle your exp", "/bxp help (page)");
		bottleexp.addChildCommand(new BottleExpHelp("help", "platinum.command.bottleexp.help", true,
				"Get bottleexp help", "/bxp help (page)"));
		bottleexp.addChildCommand(new BottleExpFill("fill", "platinum.command.bottleexp.fill", true,
				"Fill bottles with exp", "/bxp fill <xp>"));
		getCommand("BottleExp").setExecutor(bottleexp);

		PlatinumMainCommand enchant = new CommandBottleExp("Enchant", "platinum.command.enchant", true, "Enchant items",
				"/enchant help (page)");
		enchant.addChildCommand(new EnchantHelp("help", "platinum.command.enchant.help", true, "Get enchant help",
				"/enchant help (page)"));
		enchant.addChildCommand(new EnchantF("f", "platinum.command.enchant.f", true, "Force enchant an item",
				"/enchant f <enchant> <level>"));
		enchant.addChildCommand(new EnchantN("n", "platinum.command.enchant.n", true, "Normal enchant an item",
				"/enchant n <enchant> <level>"));
		getCommand("Enchant").setExecutor(enchant);

	}

	public void enableListeners()
	{

		getServer().getPluginManager().registerEvents(new ListenerPokemon(), this);
		getServer().getPluginManager().registerEvents(new ListenerExpBottle(), this);
		getServer().getPluginManager().registerEvents(new ListenerCrafting(), this);

	}

}
