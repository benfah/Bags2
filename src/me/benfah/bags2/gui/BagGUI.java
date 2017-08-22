package me.benfah.bags2.gui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.inventivetalent.nbt.CompoundTag;

import me.benfah.bags2.item.BagStorage;
import me.benfah.bags2.util.Util;
import me.benfah.cu.api.CInventory;
import me.benfah.cu.api.CustomGUI;
import me.benfah.cu.api.CustomRegistry;
import me.benfah.cu.api.IInstanceProvider;

public class BagGUI extends CustomGUI
{

	public BagGUI() {
		super("bag_gui", null, null, 36);
	}
	
	
	
	public CInventory createInventory(String name, ItemStack[] itemList, int size)
	{
		final int prevSize = this.size;
		this.size = size;
		CInventory inv = super.createInventory(name);
		inv.setContents(itemList);
		this.size = prevSize;
		return inv;
	}
	
	@Override
	public void onClose(InventoryCloseEvent e)
	{
		int i = Util.isBag(e.getPlayer().getInventory().getItemInMainHand()) ? e.getPlayer().getInventory().getHeldItemSlot() : 40;
		ItemStack stack = e.getPlayer().getInventory().getItem(i);
		CompoundTag ct = BagStorage.getCompoundOfItemStack(stack);
		ct = BagStorage.saveInventory(ct, Arrays.asList(e.getInventory().getContents()), false, size);
		e.getPlayer().getInventory().setItem(i, BagStorage.setCompoundToItemStack(stack, ct));
		super.onClose(e);
	}
	@Override
	public void onClick(InventoryClickEvent e)
	{
		if(e.getCurrentItem() != null)
		{
			if(CustomRegistry.isCustomItem(e.getCurrentItem()))
			{
				if(CustomRegistry.getCustomItem(e.getCurrentItem()).getName().startsWith("bag_"))
				e.setCancelled(true);
				
			}
			if(e.getCurrentItem().getType().toString().endsWith("SHULKER_BOX"))
			e.setCancelled(true);	
		}
		super.onClick(e);
	}

	
}
