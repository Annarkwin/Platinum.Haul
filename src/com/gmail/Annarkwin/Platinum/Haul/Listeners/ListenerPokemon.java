package com.gmail.Annarkwin.Platinum.Haul.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.Annarkwin.Platinum.API.Events.PlayerRightClickBlockEvent;

public class ListenerPokemon implements Listener
{

	@EventHandler
	public void eggStrike( ProjectileHitEvent e )
	{

		// TODO Block for players not allowed
		if (e.getHitEntity() != null && e.getEntityType() == EntityType.EGG)
		{

			Entity target = e.getHitEntity();

			if (isEggable(target))
			{

				ItemStack drop = null;
				target.remove();
				drop = getEgg(target);
				target.getWorld().dropItemNaturally(target.getLocation(), drop);

			}

		}

	}

	// Stop players from using eggs to change spawners
	@EventHandler(ignoreCancelled = true)
	public void eggSpawner( PlayerRightClickBlockEvent e )
	{

		// TODO Block for players not allowed
		if (e.getItem() != null && isSpawnEgg(e.getItem().getType()) && e.getBlock().getType() == Material.SPAWNER)
		{

			if (!e.getPlayer().hasPermission("Platinum.haul.changespawner"))
			{

				e.getPlayer().sendMessage("§4[Error]:§f You don't have permission to change spawners");
				e.setCancelled(true);

			}

		}

	}

	// Don't spawn baby chickens from eggs
	@EventHandler(ignoreCancelled = true)
	public void eggSpawnChicken( CreatureSpawnEvent e )
	{

		// TODO Block for players not allowed
		if (e.getEntityType() == EntityType.CHICKEN && e.getSpawnReason() == SpawnReason.EGG)
		{

			e.setCancelled(true);

		}

	}

	// Mob Eggs spawn animals which have a reset breeding timer
	@EventHandler(ignoreCancelled = true)
	public void mobEggSpawn( CreatureSpawnEvent e )
	{

		// TODO Block for players not allowed
		if (e.getEntity() instanceof Ageable && e.getSpawnReason() == SpawnReason.SPAWNER_EGG)
		{

			Ageable mob = (Ageable) e.getEntity();
			mob.setBaby();

		}

	}

	public boolean isEggable( Entity e )
	{

		return (!(e instanceof Tameable) && !(e instanceof Hoglin) && (e instanceof Animals || e instanceof Fish));

	}

	public boolean isSpawnEgg( Material type )
	{

		return (type.toString().toLowerCase().contains("spawn"));

	}

	public ItemStack getEgg( Entity entity )
	{

		String eggKey = entity.getType().getKey() + "_spawn_egg";

		for (Material m : Material.values())
		{

			if (m.getKey().toString().equals(eggKey))
				return new ItemStack(m);

		}

		return null;

	}

}
