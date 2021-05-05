package com.gmail.Annarkwin.Platinum.Haul.Commands.BottleExp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.Annarkwin.Platinum.API.MainCommand;
import com.gmail.Annarkwin.Platinum.API.Subcommand;

public class CommandBottleExp implements CommandExecutor , MainCommand
{

	private final Subcommand[] subcommands =
	{
			new BottleExpHelp(this), new BottleExpFill(this)
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
		else if (isplayer)
		{

			sender.sendMessage("§2[Info]:§f You have " + getTotalExperience((Player) sender) + " experience");
			return true;

		}

		return false;

	}

	// Get total experience player has based on leveling formulas
	public static int getTotalExperience( Player p )
	{

		int level = p.getLevel();
		int exp = (int) (p.getExp() * p.getExpToLevel());
		if (level <= 16)
			return level * level + 6 * level + exp;
		else if (level <= 31)
			return (int) (2.5 * level * level - 40.5 * level + 360) + exp;
		else
			return (int) (4.5 * level * level - 162.5 * level + 2220) + exp;

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
