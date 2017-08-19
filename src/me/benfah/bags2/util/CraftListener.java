package me.benfah.bags2.util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class CraftListener implements Listener
{
	@EventHandler
	public void onCraft(CraftItemEvent e)
	{
		Player p = (Player) e.getWhoClicked();
		if(e.getRecipe() instanceof ShapedRecipe)
		{
			ShapedRecipe sr = (ShapedRecipe) e.getRecipe();
			System.out.println(sr.getKey().getKey());
			if(sr.getKey().getNamespace().startsWith("bag_"))
			{
				if(!hasPermission(new Permission("bag.craft." + sr.getKey().getKey(), PermissionDefault.OP), p))
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
