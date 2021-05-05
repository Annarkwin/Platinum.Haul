package com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.gmail.Annarkwin.Platinum.API.CommandHelper;
import com.gmail.Annarkwin.Platinum.API.HelpCommand;
import com.gmail.Annarkwin.Platinum.API.MainCommand;
import com.gmail.Annarkwin.Platinum.API.Subcommand;

public class BottleExpHelp implements Subcommand , HelpCommand
{

	private String description = "Show bottleexp command help";
	private MainCommand main;
	private String name = "help";
	private String permission = "platinum.bottleexp.help";
	private boolean playeronly = true;
	private String usage = "/bottlexp help";

	public BottleExpHelp( MainCommand maincommand )
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

		String[] entries = getHelpEntries(sender, main);

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

	}

}
