package io.github.millenniarst.ai_builder.config.component;

import java.io.File;

import io.github.millenniarst.ai_builder.config.model.SingleModel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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

public class DoorConfig extends Component {

	private SingleModel model;
	private int distance;
	private int weigh;
	
	public DoorConfig(String id) {
		super(id);
		this.model = new SingleModel();
	}
	
	@Override
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		distance = getInt(file, "distance", 1);
		weigh = getInt(file, "weigh", 1);
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load(modelPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.RED + "Fail to load door model " + modelPath);
				this.setState(STATE_ERROR);
				return false;
			}
			this.setState(STATE_ENABLE);
			return true;
		} else {
			this.setState(STATE_ERROR);
			return false;
		}
	}

	public String getDirectoryPath() {
		return "doors\\";
	}
	
	public SingleModel getModel() {
		return model;
	}
	public void setModel(SingleModel model) {
		this.model = model;
	}
	public int getDistance() {
		return distance;
	}
	public int getWeigh() {
		return weigh;
	}
}
