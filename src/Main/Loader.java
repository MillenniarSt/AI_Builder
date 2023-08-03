package Main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/*
*           |\       /|                          __                 __    ___  __
*           | \     / |   ______    /\    |     |  \  |   | | |    |  \  |    |  \
*           |  \   /  |  /         /  \   |     |__/  |   | | |    |   | |___ |__/
*           |   \_/   | |         /----\  |     |   \ |   | | |    |   | |    |  \
*           |         |  \____   /      \ |     |___/  \_/  | |___ |__/  |___ |   \
*           |         |       \
*           |         |        |      AI Builder  ---   By Millenniar Studios
*           |         | ______/
*/

public interface Loader {

	public static final String CONFIG = Bukkit.getServer().getPluginManager().getPlugin(Main.NAME).getDataFolder().getPath();
	
	public static final String STATE_ENABLE = "enable";
	public static final String STATE_VOID = "void";
	public static final String STATE_DISABLE = "disable";
	public static final String STATE_ERROR = "error";
	
	boolean load(String path);
	
	public default int getInt(FileConfiguration file, String location, int min) {
		int value = file.getInt(location, min);
		if(value < min)
			value = min;
		return value;
	}
	public default int getInt(FileConfiguration file, String location, int min, int defaultValue) {
		int value = file.getInt(location, defaultValue);
		if(value < min)
			value = min;
		return value;
	}
}
