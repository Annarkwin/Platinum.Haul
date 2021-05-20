package com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.Annarkwin.Platinum.API.CommandHelper;
import com.gmail.Annarkwin.Platinum.API.HelpCommand;
import com.gmail.Annarkwin.Platinum.API.MainCommand;
import com.gmail.Annarkwin.Platinum.API.Subcommand;

public class EnchantF implements Subcommand , HelpCommand
{

	private String description = "Force enchant an item";
	private MainCommand main;
	private String name = "f";
	private String permission = "platinum.enchant.force";
	private boolean playeronly = true;
	private String usage = "/enchant f <enchant> <level>";

	public EnchantF( MainCommand maincommand )
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

			Player p = (Player) sender;

			Enchantment e = Enchantment.getByKey(NamespacedKey.minecraft(args[1]));

			if (e != null)
			{

				if (args.length > 2 && CommandHelper.isPositiveInt(args[2]))
				{

					ItemStack item = p.getInventory().getItemInMainHand();
					item.addUnsafeEnchantment(e, CommandHelper.getInt(args[2]));
					sender.sendMessage("§2[Info]:§f Enchantment applied");

				}
				else
					sender.sendMessage("§4[Error]:§f Enchantment level incorrect");

			}
			else
				sender.sendMessage("§4[Error]:§f Enchantment name invalid");

		}
		else
			sender.sendMessage("§4[Error]:§f You must choose an enchantment followed by a level");

	}

}
