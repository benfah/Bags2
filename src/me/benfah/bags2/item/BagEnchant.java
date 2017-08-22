package me.benfah.bags2.item;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import me.benfah.bags2.main.Bags2;
import me.benfah.bags2.util.CustomContainerEnchantTable;
import me.benfah.bags2.util.Translation;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;

public class BagEnchant extends BagBase
{
	Permission enchant_open = new Permission("bag.open." + name, PermissionDefault.TRUE);
	
	public BagEnchant()
	{
		super("bag_enchant", "item/bag_enchant", Translation.get("bag_enchant"));
		
	}
	
	CustomContainerEnchantTable enchant;
	PacketPlayOutBlockChange ppobc;
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		Player p = e.getPlayer();
		if(hasPermission(enchant_open, p))
		{	
		
		Location loc = new Location(p.getWorld(), 10000.0D, 255.0D, 10000.0D);
		//Can't be handled differently :(
		Block im5 = loc.getBlock();
        if(im5.getType() != Material.ENCHANTMENT_TABLE)
        {
           im5.setType(Material.ENCHANTMENT_TABLE);
        }
		
        p.openEnchanting(loc, true);
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
		sr.shape("GLG", "LSL", "LEL");
		sr.setIngredient('G', Material.GOLD_INGOT);
		sr.setIngredient('L', Material.LEATHER);
		sr.setIngredient('S', Material.STRING);
		sr.setIngredient('E', Material.ENCHANTMENT_TABLE);

		
		return sr;
	}
	
	
}
