package com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.Annarkwin.Platinum.API.MainCommand;
import com.gmail.Annarkwin.Platinum.API.Subcommand;

public class CommandEnchant implements CommandExecutor , MainCommand
{

	private final Subcommand[] subcommands =
	{
			new EnchantHelp(this), new EnchantF(this), new EnchantN(this)
	};

	public Subcommand[] getSubcommands()
	{

		return subcommands;

	}

	@Override
	public boolean onCommand( CommandSender sender, Command cmd, String label, String[] args )
	{

		boolean isplayer = sender instanceof Player;

		if (args.length > 0 && isplayer)
		{

			for (Subcommand command : subcommands)
			{

				if (command.getName().equalsIgnoreCase(args[0]) && (!command.isPlayerOnly() || isplayer))
				{

					if (sender.hasPermission(command.getPermission()))
						command.run(sender, args);
					else
						sender.sendMessage("§4[Error]:§f You don't have permission for that command");
					return true;

				}

			}

		}

		return false;

	}

}
