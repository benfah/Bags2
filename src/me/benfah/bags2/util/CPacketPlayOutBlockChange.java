package me.benfah.bags2.util;

import java.io.IOException;
import java.lang.reflect.Field;

import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Blocks;
import net.minecraft.server.v1_12_R1.IBlockData;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.World;

public class CPacketPlayOutBlockChange extends PacketPlayOutBlockChange
{
	
	
	public CPacketPlayOutBlockChange(World arg0, BlockPosition arg1)
	{
		super(arg0, arg1);
		
	}
	
	@Override
	public void b(PacketDataSerializer packetdataserializer) throws IOException
	{
		
//		super.b(arg0);
		try {
		
			Field f = PacketPlayOutBlockChange.class.getDeclaredField("a");
			f.setAccessible(true);
			packetdataserializer.a((BlockPosition) f.get(this));
	        packetdataserializer.d(0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
//		arg0.d
	}
	
	
}
