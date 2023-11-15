package io.github.millenniarst.ai_builder.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.millenniarst.ai_builder.AI_Builder;

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

	String CONFIG = Bukkit.getServer().getPluginManager().getPlugin(AI_Builder.NAME).getDataFolder().getPath();
	
	String STATE_ENABLE = "enable";
	String STATE_VOID = "void";
	String STATE_DISABLE = "disable";
	String STATE_ERROR = "error";
	
	boolean load(String path);
	
	String getState();
	void setState(String state);

	String getDirectoryPath();
	
	default int getInt(FileConfiguration file, String location, int min) {
		int value = file.getInt(location, min);
		if(value < min)
			value = min;
		return value;
	}
	default int getInt(FileConfiguration file, String location, int min, int defaultValue) {
		int value = file.getInt(location, defaultValue);
		if(value < min)
			value = min;
		return value;
	}
}
