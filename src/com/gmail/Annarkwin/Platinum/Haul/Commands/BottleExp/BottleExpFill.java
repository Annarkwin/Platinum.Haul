package com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.Annarkwin.Platinum.API.CommandHelper;
import com.gmail.Annarkwin.Platinum.API.ExperienceManager;
import com.gmail.Annarkwin.Platinum.API.HelpCommand;
import com.gmail.Annarkwin.Platinum.API.MainCommand;
import com.gmail.Annarkwin.Platinum.API.Subcommand;

public class BottleExpFill implements Subcommand , HelpCommand
{

	private String description = "Fill as many empty bottles with given amount";
	private MainCommand main;
	private String name = "fill";
	private String permission = "platinum.bottleexp.fill";
	private boolean playeronly = true;
	private String usage = "/bottlexp fill";

	public BottleExpFill( MainCommand maincommand )
	{

		main = maincommand;

	}

	@Override
	public String getDescription()
	{

		return description;

	}

	@Override
	public String getHelpString( Subcommand command )
	{

		return " §5" + command.getUsage() + " §6- " + command.getDescription();

	}

	@Override
	public String[] getHelpEntries( CommandSender sender, MainCommand command )
	{

		ArrayList<String> entries = new ArrayList<String>();

		for (Subcommand sc : command.getSubcommands())
		{

			if (sender.hasPermission(sc.getPermission()))
				entries.add(getHelpString(sc));

		}

		return ((String[]) entries.toArray(new String[0]));

	}

	@Override
	public MainCommand getMainCommand()
	{

		return main;

	}

	@Override
	public String getName()
	{

		return name;

	}

	@Override
	public String getPermission()
	{

		return permission;

	}

	@Override
	public String getUsage()
	{

		return usage;

	}

	@Override
	public boolean isPlayerOnly()
	{

		return playeronly;

	}

	@Override
	public void run( CommandSender sender, String[] args )
	{

		if (args.length > 1)
		{

			if (CommandHelper.isPositiveInt(args[1]))
			{

				if (((Player) sender).getInventory().getItemInMainHand().getType() == Material.GLASS_BOTTLE)
				{

					exec(sender, args);

				}
				else
					sender.sendMessage("§4[Error]:§f You must be holding glass bottles to fill");

			}
			else
				sender.sendMessage("§4[Error]:§f You can't fill a bottle with no exp");

		}
		else
			sender.sendMessage("§4[Error]:§f You must specify how much xp to fill each bottle");

	}

	public void exec( CommandSender sender, String[] args )
	{

		Player p = (Player) sender;
		int exp = (int) ExperienceManager.getTotalExperience(p);
		int fillamt = Integer.parseInt(args[1]);
		int bottles = p.getInventory().getItemInMainHand().getAmount();
		int remainder;

		// Player has enough exp to fill each bottle with specified amount
		if (fillamt * bottles <= exp && fillamt > 0)
		{

			remainder = exp - fillamt * bottles;

			// Create new exp bottle stack and replace the glass bottles in the player's
			// hand
			ItemStack expbottles = new ItemStack(Material.EXPERIENCE_BOTTLE, bottles);
			ItemMeta meta = expbottles.getItemMeta();
			meta.setLore(getExpLore(fillamt));
			expbottles.setItemMeta(meta);
			p.getInventory().setItemInMainHand(expbottles);

			// To avoid float remainder exploits, set player exp to integer
			CommandBottleExp.setTotalExperience(p, remainder);
			p.sendMessage("§2[Info]:§f You filled " + bottles + " with " + fillamt + " experience each");

		}
		// Player has enough exp to fill at least one bottle with specified amount, uses
		// extra inventory space
		else if (fillamt <= exp && p.getInventory().firstEmpty() > -1 && fillamt > 0)
		{

			int usedbottles = exp / fillamt;
			remainder = exp - usedbottles * fillamt;

			// Create new exp bottle stack and add it to the player's inventory. Reduce used
			// glass bottles
			ItemStack expbottles = new ItemStack(Material.EXPERIENCE_BOTTLE, usedbottles);
			ItemMeta meta = expbottles.getItemMeta();
			meta.setLore(getExpLore(fillamt));
			expbottles.setItemMeta(meta);
			p.getInventory().setItem(p.getInventory().firstEmpty(), expbottles);
			p.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE, bottles - usedbottles));

			CommandBottleExp.setTotalExperience(p, remainder);

			p.sendMessage("§2[Info]:§f You filled " + usedbottles + " with " + fillamt + " experience each");

		}
		// player does not have enough exp to fill any bottles with specified amount
		else
		{

			p.sendMessage(
					"§4[Error]:§f You don't have enough xp to fill bottles with that amount or need more inventory space");

		}

	}

	// Get formatted string with exp amount
	public List<String> getExpLore( int amount )
	{

		ArrayList<String> lore = new ArrayList<String>();
		lore.add("  §5§o" + String.valueOf(amount) + " experience");

		return lore;

	}

}
