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

public class BagSmall extends BagStorage{
	
	Permission small_open = new Permission("bag.open." + name, PermissionDefault.TRUE);

	
	public BagSmall() {
		super("bag_small", "item/bag_small", Translation.get("bag_small"), Bags2.cfg.getInt("bag_small_size"));
	}

	@Override
	public ShapedRecipe getStandardRecipe()
	{
		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), getItem());
		sr.shape("ILI", "LSL", "LLL");
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('I', Material.IRON_INGOT);
		sr.setIngredient('S', Material.STRING);

		return sr;
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(hasPermission(small_open, e.getPlayer()))
		super.onInteract(e, es);
	}
	
}
