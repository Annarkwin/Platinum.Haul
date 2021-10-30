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
import com.gmail.Annarkwin.Platinum.API.PlatinumCommand;

public class BottleExpFill extends PlatinumCommand
{

	public BottleExpFill( String name, String permission, boolean player, String description, String usage )
	{

		super(name, permission, player, description, usage);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean run( CommandSender sender, String cmdname, String[] args )
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

		return true;

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
