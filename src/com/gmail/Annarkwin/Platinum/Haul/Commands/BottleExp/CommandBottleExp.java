package com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.Annarkwin.Platinum.API.ExperienceManager;
import com.gmail.Annarkwin.Platinum.API.PlatinumCommand;
import com.gmail.Annarkwin.Platinum.API.PlatinumMainCommand;

public class CommandBottleExp extends PlatinumMainCommand
{

	
	public CommandBottleExp( String name, String permission, boolean player, String description, String usage )
	{

		super(name, permission, player, description, usage);
		

	}

	@Override
	public boolean run( CommandSender sender, String cmdname, String[] cmdargs )
	{


		boolean isplayer = sender instanceof Player;

		if (cmdargs.length > 0 && isplayer)
		{

			for (PlatinumCommand command : getChildren())
			{

				if (command.getName().equalsIgnoreCase(cmdargs[0]) && (!command.isPlayerOnly() || isplayer))
				{

					if (sender.hasPermission(command.getPermissionHook()))
						command.run(sender, getName(), cmdargs);
					else
						sender.sendMessage("§4[Error]:§f You don't have permission for that command");
					return true;

				}

			}

		}
		else if (isplayer)
		{

			sender.sendMessage(
					"§2[Info]:§f You have " + ExperienceManager.getTotalExperience((Player) sender) + " experience");
			return true;

		}

		return false;

	}

	// Clear player's exp and gives them exp (effectively setting player's exp
	// amount
	public static void setTotalExperience( Player p, int amount )
	{

		p.setExp(0);
		p.setLevel(0);
		p.giveExp(amount);

	}
}
