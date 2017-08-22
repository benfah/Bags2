package me.benfah.bags2.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import me.benfah.bags2.main.Bags2;
import me.benfah.bags2.util.Translation;
import me.benfah.bags2.util.Util;

public class BagAnvil extends BagBase{
	
	Permission anvil_open = new Permission("bag.open." + name, PermissionDefault.TRUE);
	
	public BagAnvil()
	{
		super("bag_anvil", "item/bag_anvil", Translation.get("bag_anvil"));
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(hasPermission(anvil_open, e.getPlayer()))
		{	
		Util.openAnvil(e.getPlayer());
		}
		super.onInteract(e, es);
	}
	
	@Override
	public ShapedRecipe getStandardRecipe()
	{
		ShapedRecipe sr;
		if(Bukkit.getBukkitVersion().contains("1.11"))
		{
			sr = new ShapedRecipe(getItem());
		}
		else
		sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), getItem());
		sr.shape("ILI", "LSL", "LAL");
		sr.setIngredient('I', Material.IRON_BLOCK);
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('S', Material.STRING);
		sr.setIngredient('A', Material.ANVIL);
		
		return sr;
	}
	
}
