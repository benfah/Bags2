package me.benfah.bags2.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import me.benfah.bags2.item.BagBase;

public class CraftListener implements Listener
{
	@EventHandler
	public void onCraft(CraftItemEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		if(e.getRecipe() instanceof ShapedRecipe)
		{
			ShapedRecipe sr = (ShapedRecipe) e.getRecipe();
			if(Bukkit.getBukkitVersion().contains("1.11"))
			{
				for(BagBase  bb : Util.getBags())
				{
					if(((ShapedRecipe)bb.getRecipe()).getShape().equals(sr.getShape()))
					{
						if(!hasPermission(new Permission("bag.craft." + bb.getName(), PermissionDefault.TRUE), p))
						e.setCancelled(true);					
					}
				}
			}
			else
			if(sr.getKey().getNamespace().startsWith("bag_"))
			{
				if(!hasPermission(new Permission("bag.craft." + sr.getKey().getKey(), PermissionDefault.TRUE), p))
				e.setCancelled(true);
			}
		}
	}
	
	public boolean hasPermission(Permission perm, Player p)
	{
		if(p.hasPermission(perm))
		{
			return true;
		}
		else
		{
			p.sendMessage(Translation.get("not_allowed"));
			return false;
		}
	}
}
