package me.benfah.bags2.item;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import me.benfah.bags2.main.Bags2;
import me.benfah.bags2.util.Translation;

public class BagEnder extends BagBase{
	
	Permission ender_open = new Permission("bag.open." + name, PermissionDefault.TRUE);

	
	public BagEnder()
	{
		super("bag_ender", "item/bag_ender", Translation.get("bag_ender"));
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(hasPermission(ender_open, e.getPlayer()))
		{	
		e.getPlayer().openInventory(e.getPlayer().getEnderChest());
		}
		super.onInteract(e, es);
	}

	@Override
	public ShapedRecipe getStandardRecipe()
	{
		
		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), getItem());
		
		sr.shape("GDG", "LSL", "LEL");
		sr.setIngredient('G', Material.GOLD_INGOT);
		sr.setIngredient('D', Material.DIAMOND);
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('S', Material.STRING);
		sr.setIngredient('E', Material.ENDER_CHEST);

		return sr;
	}

}
