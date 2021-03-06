package com.gmail.Annarkwin.Platinum.Haul.Commands.Enchant;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.Annarkwin.Platinum.API.CommandHelper;
import com.gmail.Annarkwin.Platinum.API.PlatinumCommand;

public class EnchantF extends PlatinumCommand
{

	public EnchantF( String name, String permission, boolean player, String description, String usage )
	{

		super(name, permission, player, description, usage);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean run( CommandSender sender, String cmdname, String[] args )
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
					sender.sendMessage("?2[Info]:?f Enchantment applied");

				}
				else
					sender.sendMessage("?4[Error]:?f Enchantment level incorrect");

			}
			else
				sender.sendMessage("?4[Error]:?f Enchantment name invalid");

		}
		else
			sender.sendMessage("?4[Error]:?f You must choose an enchantment followed by a level");

		return true;

	}

}
