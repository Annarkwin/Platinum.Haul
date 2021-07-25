package com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.gmail.Annarkwin.Platinum.API.CommandHelper;
import com.gmail.Annarkwin.Platinum.API.HelpCommand;
import com.gmail.Annarkwin.Platinum.API.PlatinumCommand;

public class EnchantHelp  extends PlatinumCommand implements HelpCommand
{

	public EnchantHelp( String name, String permission, boolean player, String description, String usage )
	{

		super(name, permission, player, description, usage);

	}

	@Override
	public String getHelpString( PlatinumCommand command )
	{

		return " §5" + command.getUsage() + " §6- " + command.getDescription();

	}

	@Override
	public String[] getHelpEntries( CommandSender sender, PlatinumCommand command )
	{

		ArrayList<String> entries = new ArrayList<String>();

		for (PlatinumCommand sc : command.getChildren())
		{

			if (sender.hasPermission(sc.getPermissionHook()))
				entries.add(getHelpString(sc));

		}

		return ((String[]) entries.toArray(new String[0]));

	}

	@Override
	public boolean run( CommandSender sender, String cmdname, String[] args )
	{

		String[] entries = getHelpEntries(sender, getParent());

		if (args.length > 1)
		{

			if (CommandHelper.isPositiveInt(args[1]))
			{

				CommandHelper.sendHelp(sender, entries, "BottleExp", CommandHelper.getInt(args[1]));

			}
			else
			{

				sender.sendMessage("§4[Error]:§f Enter a positive number");

			}

		}
		else
		{

			CommandHelper.sendHelp(sender, entries, "BottleExp", 1);

		}
		return true;

	}

}
