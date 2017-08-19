package me.benfah.bags2.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import me.benfah.bags2.gui.BagGUI;
import me.benfah.bags2.item.BagAnvil;
import me.benfah.bags2.item.BagBig;
import me.benfah.bags2.item.BagCrafting;
import me.benfah.bags2.item.BagEnchant;
import me.benfah.bags2.item.BagEnder;
import me.benfah.bags2.item.BagSmall;
import me.benfah.bags2.item.BagStorage;
import me.benfah.bags2.util.CraftListener;
import me.benfah.bags2.util.RecipeManager;
import me.benfah.bags2.util.Translation;
import me.benfah.cu.api.CustomRegistry;

public class Bags2 extends JavaPlugin
{
	public static BagStorage smallBag;
	public static BagStorage bigBag;
	public static BagCrafting craftingBag;
	public static BagEnder enderBag;
	public static BagEnchant enchantBag;
	public static BagAnvil anvilBag;

	public static BagGUI bagGUI = new BagGUI();
	
	public static Bags2 instance;
	
	public static YamlConfiguration recipes;
	public static File recipesFile;
	
	public static YamlConfiguration cfg;
	public static File cfgFile;
	
	@Override
	public void onEnable()
	{
		instance = this;
		getDataFolder().mkdirs();
		
		cfgFile = new File(getDataFolder(), "cfg.yml");
		cfg = YamlConfiguration.loadConfiguration(cfgFile);
		
		
		
		setIf("lang", "en");
		setIf("bag_small_size", 27);
		setIf("bag_big_size", 54);
		try
		{
			cfg.save(cfgFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Translation.initLangFiles();

		
		smallBag = new BagSmall();
		
		bigBag = new BagBig();
		
		craftingBag = new BagCrafting();
		enderBag = new BagEnder();
		enchantBag = new BagEnchant();
		anvilBag = new BagAnvil();
		
		
		recipesFile = new File(getDataFolder(), "recipes.yml");
		recipes = YamlConfiguration.loadConfiguration(recipesFile);
		
		
		
		Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
		RecipeManager.initRecipes();

		
		CustomRegistry.registerItem(smallBag, this);
		CustomRegistry.registerItem(bigBag, this);
		CustomRegistry.registerItem(craftingBag, this);
		CustomRegistry.registerItem(enderBag, this);
		CustomRegistry.registerItem(enchantBag, this);
		CustomRegistry.registerItem(anvilBag, this);
		
		
		CustomRegistry.registerGUI(bagGUI, this);
		
	}
	
	public static void setIf(String key, Object value)
	{
		if(!cfg.contains(key))
		cfg.set(key, value);
	}
	
	
}
