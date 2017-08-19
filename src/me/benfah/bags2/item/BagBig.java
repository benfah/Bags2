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

public class BagBig extends BagStorage{
	
	Permission big_open = new Permission("bag.open." + name, PermissionDefault.TRUE);

	
	public BagBig() {
		super("bag_big", "item/bag_big", Translation.get("bag_big"), Bags2.cfg.getInt("bag_big_size"));
	}

	@Override
	public ShapedRecipe getStandardRecipe()
	{
		ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), getItem());
		sr.shape("ILI", "LSL", "LLL");
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('I', Material.GOLD_INGOT);
		sr.setIngredient('S', Material.STRING);

		return sr;
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(hasPermission(big_open, e.getPlayer()))
		super.onInteract(e, es);
	}
	
}
