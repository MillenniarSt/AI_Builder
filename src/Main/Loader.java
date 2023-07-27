package Main;

import org.bukkit.Bukkit;

public interface Loader {

	public static final String CONFIG = Bukkit.getServer().getPluginManager().getPlugin("AI_Builder").getDataFolder().getPath();
	
	boolean load(String path);
}
