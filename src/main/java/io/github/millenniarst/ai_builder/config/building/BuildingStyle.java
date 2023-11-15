package io.github.millenniarst.ai_builder.config.building;

import java.io.File;

import io.github.millenniarst.ai_builder.config.component.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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

public class BuildingStyle extends Component {
	
	private String name;
	
	public BuildingStyle(String id, String name) {
		super(id);
		this.name = name;
	}

	@Override
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		this.name = file.getString("name", getId());

		return super.load(path);
	}

	public String getDirectoryPath() {
		return "buildings\\";
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
