package Building;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;

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

public class BuildingStyle extends CustomStyle {

	public static HashMap<String, BuildingStyle> enabledBuildings = new HashMap<>();
	
	private String name;
	
	public BuildingStyle(String id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		this.name = file.getString("name", getId());
		return true;
	}
	
	public String toString() {
		return this.name + "[id:" + getId() + "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
