package me.benfah.bags2.item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.benfah.bags2.main.Bags2;
import me.benfah.cu.api.CInventory;

public abstract class BagStorage extends BagBase
{
	public BagStorage(String name, String modelPath, String title, int size) {
		super(name, modelPath, title);
		this.size = size;
	}

	int size;
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		if(es.equals(EquipmentSlot.HAND))
		{	
		Player p = e.getPlayer();
		
		ItemStack stack = es.equals(EquipmentSlot.HAND) ? p.getInventory().getItemInMainHand() : p.getInventory().getItemInOffHand();
		ItemStack[] istack = loadInventory(getCompoundOfItemStack(stack), size);
		CInventory inv = Bags2.bagGUI.createInventory(stack.getItemMeta().getDisplayName(), istack, size);
//		inv.slot = p.getInventory().getHeldItemSlot();
//		p.openInventory(inv.getInternalInventory());
		
		inv.openInventory(p);
		}
		super.onInteract(e, es);
	}

	
	
	
}
