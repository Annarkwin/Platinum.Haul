package com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.Annarkwin.Platinum.API.PlatinumCommand;
import com.gmail.Annarkwin.Platinum.API.PlatinumMainCommand;

public class CommandEnchant extends PlatinumMainCommand
{
	public CommandEnchant( String name, String permission, boolean player, String description, String usage )
	{

		super(name, permission, player, description, usage);

	}

	public boolean run( CommandSender sender, String cmdname, String[] args )
	{

		boolean isplayer = sender instanceof Player;

		if (args.length > 0 && isplayer)
		{

			for (PlatinumCommand command : getChildren())
			{

				if (command.getName().equalsIgnoreCase(args[0]) && (!command.isPlayerOnly() || isplayer))
				{

					if (sender.hasPermission(command.getPermissionHook()))
						command.run(sender, cmdname, args);
					else
						sender.sendMessage("§4[Error]:§f You don't have permission for that command");
					return true;

				}

			}

		}

		return false;

	}

}
