package me.benfah.bags2.item;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.inventivetalent.nbt.CompoundTag;
import org.inventivetalent.nbt.ListTag;

import me.benfah.bags2.util.RecipeManager;
import me.benfah.bags2.util.Translation;
import me.benfah.cu.api.CustomItem;
import me.benfah.cu.util.ReflectionUtils;

public abstract class BagBase extends CustomItem
{
	
//	@Override
//	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
//	{
//		ItemStack bag = e.getPlayer().getInventory().getItemInMainHand();
//		
//		Inventory inv = Bukkit.createInventory(null, InventoryType.ANVIL, "Bag");
//		if(ni.hasKey("contents"))
//		CompoundTag ct = saveInventory(getCompoundOfItemStack(bag), new ArrayList<ItemStack>() {{add(new ItemStack(Material.ACACIA_DOOR_ITEM));}}, true);
//		ItemStack nBag = setCompoundToItemStack(bag, ct);
//		e.getPlayer().getInventory().setItemInMainHand(nBag);
//		super.onInteract(e, es);
//	}

	public BagBase(String name, String modelPath, String title, List<String> lore) {
		super(name, modelPath, title, lore);
	}

	
	
	public BagBase(String name, String modelPath, String title) {
		super(name, modelPath, title);
	}

	public static CompoundTag saveInventory(CompoundTag nbttagcompound, List<ItemStack> nonnulllist, boolean flag, int size)
	{
        ListTag<CompoundTag> nbttaglist = new ListTag<>();
        nbttaglist.setTagType(10);
        try
        {
	        for (int i = 0; i < nonnulllist.size(); ++i) {
	            ItemStack itemstack = (ItemStack) nonnulllist.get(i);
	            Object nmsStack = ReflectionUtils.getRefClass("{cb}.inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemstack);
	            
	            if (itemstack != null) {
	            	CompoundTag nbttagcompound1 = new CompoundTag();
	                nbttagcompound1.set("Slot", (byte) i);

	            	Object nmstagcompound1 = nbttagcompound1.toNMS();
	//                itemstack.save(nbttagcompound1);
	            	nmstagcompound1 = nmsStack.getClass().getMethod("save", nmstagcompound1.getClass()).invoke(nmsStack, nmstagcompound1);
	                nbttagcompound1.fromNMS(nmstagcompound1);
	            	nbttaglist.add(nbttagcompound1);
	            }
	        }
        }
        catch (ReflectiveOperationException e)
        {
        	e.printStackTrace();
		}
        
            nbttagcompound.set("Items", nbttaglist);
        
            System.out.println(nbttaglist.getValue());

        return nbttagcompound;
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
	
	
	public static ItemStack[] loadInventory(CompoundTag nbttagcompound, int size) {
		try
		{
	        ListTag<CompoundTag> nbttaglist = nbttagcompound.has("Items") ? nbttagcompound.getList("Items") : new ListTag<CompoundTag>();
	        ItemStack[] result = new ItemStack[size];
	        for (int i = 0; i < size; ++i) {
	        	System.out.println(nbttaglist.size());
	        	if(nbttaglist.size() > i)
	        	{	
	            CompoundTag nbttagcompound1 = nbttaglist.get(i);
	            int j = nbttagcompound1.get("Slot").getAsByte() & 255;
	            Object nmsStack = ReflectionUtils.getRefClass("{nms}.ItemStack").getConstructor(nbttagcompound1.toNMS().getClass()).newInstance(nbttagcompound1.toNMS());
	            ItemStack stack = (ItemStack) ReflectionUtils.getRefClass("{cb}.inventory.CraftItemStack").getMethod("asCraftMirror", nmsStack.getClass()).invoke(null, nmsStack);
	            System.out.println(stack.getType());
	            result[j] = stack;
	        	}
	        }
	        return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
    }
	
	
	public static CompoundTag getCompoundOfItemStack(ItemStack stack)
	{
        try
        {
			Object nmsStack = ReflectionUtils.getRefClass("{cb}.inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, stack);
			CompoundTag ct = new CompoundTag();
			ct.fromNMS(nmsStack.getClass().getMethod("getTag").invoke(nmsStack));
			return ct;
        } catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null; 

	}
	
	public static ItemStack setCompoundToItemStack(ItemStack stack, CompoundTag compoundTag)
	{
//		ItemMeta im = stack.getItemMeta();
		try
        {
			
			Object nmsStack = ReflectionUtils.getRefClass("{cb}.inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, stack);
			nmsStack.getClass().getMethod("setTag", compoundTag.toNMS().getClass()).invoke(nmsStack, compoundTag.toNMS());
			ItemStack result = (ItemStack) ReflectionUtils.getRefClass("{cb}.inventory.CraftItemStack").getMethod("asCraftMirror", nmsStack.getClass()).invoke(null, nmsStack);
//			result.setItemMeta(im);
			return result;
        } catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Recipe getRecipe() {
		return RecipeManager.getRecipe(name);
	}
	
	@Override
	public ItemStack getItem()
	{
		if(!title.equals(Translation.get(name)))
		title = Translation.get(name);
		
		return super.getItem();
	}
	
	@Override
	public void onInteract(PlayerInteractEvent e, EquipmentSlot es)
	{
		ItemStack stack = es.equals(EquipmentSlot.HAND) ? e.getPlayer().getInventory().getItemInMainHand() : e.getPlayer().getInventory().getItemInOffHand();
		if(stack.hasItemMeta() ? !stack.getItemMeta().getDisplayName().equals(Translation.get(name)) : false)
		{
			if(!stack.getItemMeta().getDisplayName().startsWith(ChatColor.ITALIC.toString()))
			{
				
				ItemMeta im = stack.getItemMeta();
				im.setDisplayName(ChatColor.RESET + Translation.get(name));
				stack.setItemMeta(im);
				if(es.equals(EquipmentSlot.HAND))
				e.getPlayer().getInventory().setItemInMainHand(stack);
				else
				e.getPlayer().getInventory().setItemInOffHand(stack);
			}
		}
		super.onInteract(e, es);
	}
	
	public abstract ShapedRecipe getStandardRecipe();
	
	
	
}
