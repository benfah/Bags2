package me.benfah.bags2.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import me.benfah.bags2.main.Bags2;
import me.benfah.bags2.util.Translation;

public class BagCrafting extends BagBase{
	
	Permission crafting_open = new Permission("bag.open." + name, PermissionDefault.TRUE);

	
	public BagCrafting()
	{
		super("bag_crafting", "item/bag_crafting", Translation.get("bag_crafting"));
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(hasPermission(crafting_open, e.getPlayer()))
		{	
		e.getPlayer().openInventory(Bukkit.createInventory(e.getPlayer(), InventoryType.WORKBENCH, "Crafting Bag"));
		}
		super.onInteract(e, es);
	}

	@Override
	public ShapedRecipe getStandardRecipe() {
		
		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), getItem());
		sr.shape("ILI", "LSL", "LCL");
		sr.setIngredient('I', Material.IRON_INGOT);
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('S', Material.STRING);
		sr.setIngredient('C', Material.WORKBENCH);

		return sr;
	}
	
}
