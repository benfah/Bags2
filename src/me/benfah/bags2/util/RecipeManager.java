package me.benfah.bags2.util;

import java.io.IOException;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import me.benfah.bags2.item.BagBase;
import me.benfah.bags2.main.Bags2;
import me.benfah.cu.api.CustomItem;
import me.benfah.cu.api.CustomRegistry;

public class RecipeManager 
{
	public static Recipe getRecipe(String name)
	{
		System.out.println("EIGHBÜGBÜGBPEIUVDEPBDU");
		CustomItem ci = CustomRegistry.getCustomItemByName(name);
		if(ci instanceof BagBase)
		{
			BagBase bb = (BagBase) ci;
			
			String prefix = "recipes." + name;
			
			ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Bags2.instance, name), bb.getItem());
			sr.shape("ABC", "DEF", "GHI");
			sr.setIngredient('A', Material.getMaterial(Bags2.recipes.getString(prefix + ".1")));
			sr.setIngredient('B', Material.getMaterial(Bags2.recipes.getString(prefix + ".2")));
			sr.setIngredient('C', Material.getMaterial(Bags2.recipes.getString(prefix + ".3")));
			sr.setIngredient('D', Material.getMaterial(Bags2.recipes.getString(prefix + ".4")));
			sr.setIngredient('E', Material.getMaterial(Bags2.recipes.getString(prefix + ".5")));
			sr.setIngredient('F', Material.getMaterial(Bags2.recipes.getString(prefix + ".6")));
			sr.setIngredient('G', Material.getMaterial(Bags2.recipes.getString(prefix + ".7")));
			sr.setIngredient('H', Material.getMaterial(Bags2.recipes.getString(prefix + ".8")));
			sr.setIngredient('I', Material.getMaterial(Bags2.recipes.getString(prefix + ".9")));
			return sr;
		}
		return null;
	}
	
	public static void initRecipes()
	{
		for(BagBase bb : Util.getBags())
		{
			String prefix = "recipes." + bb.getName();
			
			ShapedRecipe sr = bb.getStandardRecipe();
			Map<Character, ItemStack> map = sr.getIngredientMap();
			Material[] matArray = new Material[9];
			
			setIf(prefix + ".1", map.get(sr.getShape()[0].charAt(0)).getType().toString());
			setIf(prefix + ".2", map.get(sr.getShape()[0].charAt(1)).getType().toString());
			setIf(prefix + ".3", map.get(sr.getShape()[0].charAt(2)).getType().toString());
			setIf(prefix + ".4", map.get(sr.getShape()[1].charAt(0)).getType().toString());
			setIf(prefix + ".5", map.get(sr.getShape()[1].charAt(1)).getType().toString());
			setIf(prefix + ".6", map.get(sr.getShape()[1].charAt(2)).getType().toString());
			setIf(prefix + ".7", map.get(sr.getShape()[2].charAt(0)).getType().toString());
			setIf(prefix + ".8", map.get(sr.getShape()[2].charAt(1)).getType().toString());
			setIf(prefix + ".9", map.get(sr.getShape()[2].charAt(2)).getType().toString());

			
		}
		try {
			Bags2.recipes.save(Bags2.recipesFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void setIf(String key, Object value)
	{
		if(!Bags2.recipes.contains(key))
		Bags2.recipes.set(key, value);
	}
	
}
