package me.benfah.bags2.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.benfah.bags2.item.BagBase;
import me.benfah.bags2.main.Bags2;
import me.benfah.cu.api.CustomRegistry;
import me.benfah.cu.util.ReflectionUtils;


public class Util
{
	public static boolean isBag(ItemStack stack)
	{
		if(CustomRegistry.getCustomItem(stack).getName().startsWith("bag_"))
		return true;
		
		
		return false;
	}
	
	public static List<BagBase> getBags()
	{
		return new ArrayList<BagBase>(){{add(Bags2.smallBag);add(Bags2.bigBag);add(Bags2.enchantBag);add(Bags2.anvilBag);add(Bags2.craftingBag);add(Bags2.enderBag);}};
	}
	
	public static void openAnvil(Player p)
	{
		Class<?> anvilContainerClass = ReflectionUtils.getRefClass("{nms}.ContainerAnvil");
		Class<?> craftPlayerClass = ReflectionUtils.getRefClass("{cb}.entity.CraftPlayer");
		Class<?> entityHumanClass = ReflectionUtils.getRefClass("{nms}.EntityHuman");
		Class<?> blockPositionClass = ReflectionUtils.getRefClass("{nms}.BlockPosition");
		Class<?> packetPlayOutOpenWindowClass = ReflectionUtils.getRefClass("{nms}.PacketPlayOutOpenWindow");
		Class<?> packetClass = ReflectionUtils.getRefClass("{nms}.Packet");
		Class<?> worldClass = ReflectionUtils.getRefClass("{nms}.World");
		Class<?> chatMessageClass = ReflectionUtils.getRefClass("{nms}.ChatMessage");
		Class<?> iChatBaseClass = ReflectionUtils.getRefClass("{nms}.IChatBaseComponent");
		
		try {
		Object handle = craftPlayerClass.getMethod("getHandle").invoke(p);
		Object blockPositionInstance = blockPositionClass.getConstructor(double.class, double.class, double.class).newInstance(entityHumanClass.getField("locX").get(handle), entityHumanClass.getField("locY").get(handle), entityHumanClass.getField("locZ").get(handle));
		Object anvilInstance = anvilContainerClass.getConstructor(entityHumanClass.getField("inventory").get(handle).getClass(), worldClass, blockPositionInstance.getClass(), entityHumanClass).newInstance(entityHumanClass.getField("inventory").get(handle), entityHumanClass.getField("world").get(handle), blockPositionInstance, handle);
		Field check = anvilInstance.getClass().getField("checkReachable");
		check.setAccessible(true);
		check.setBoolean(anvilInstance, false);
		int cID = ((Integer)handle.getClass().getMethod("nextContainerCounter").invoke(handle)).intValue();
		Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
		Object chatMessageInstance = chatMessageClass.getConstructor(String.class, Object[].class).newInstance("Anvil", new Object[0]);
		Object packetPlayOutWindowInstance = packetPlayOutOpenWindowClass.getConstructor(int.class, String.class, iChatBaseClass, int.class).newInstance(cID, "minecraft:anvil", chatMessageInstance, 0);
		playerConnection.getClass().getMethod("sendPacket", packetClass).invoke(playerConnection, packetPlayOutWindowInstance);
		}
		catch (ReflectiveOperationException e)
		{
			e.printStackTrace();
		}
	}
	
}
