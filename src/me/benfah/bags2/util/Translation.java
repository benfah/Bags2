package me.benfah.bags2.util;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import me.benfah.bags2.main.Bags2;

public class Translation
{
	
	public static YamlConfiguration lang;
	public static File langFile;

	
	
	public static String get(String arg0)
	{
		if(lang == null)
		{
			langFile = new File(Bags2.instance.getDataFolder() + "/lang", Bags2.cfg.getString("lang") + ".yml");
			lang = YamlConfiguration.loadConfiguration(langFile);
		}
		return lang.getString(arg0) == null ? "NO TRANSLATION AVAILABLE" : lang.getString(arg0).replaceAll("&", "§");
	}
	
	public static void initLangFiles()
	{
		Bags2.instance.saveResource("lang/en.yml", false);
		Bags2.instance.saveResource("lang/de.yml", false);
		Bags2.instance.saveResource("lang/es.yml", false);
		Bags2.instance.saveResource("lang/jp.yml", false);
		Bags2.instance.saveResource("lang/nl.yml", false);
		Bags2.instance.saveResource("lang/sv.yml", false);
	}
}
