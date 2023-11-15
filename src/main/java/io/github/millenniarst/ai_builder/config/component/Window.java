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

public class Window extends Component {

	private SingleModel model;
	private int high;
	private int prefDistance;
	
	public Window(String id) {
		super(id);
		this.model = new SingleModel();
		this.high = 1;
		this.prefDistance = 1;
	}
	
	@Override
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		high = getInt(file, "high", 1, 2);
		prefDistance = getInt(file, "pref-distance", 1, 2);
		
		String modelPath = getDirectoryPath() + file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load(modelPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load window model " + modelPath);
				this.setState(STATE_DISABLE);
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
		return "windows\\";
	}
	
	public SingleModel getModel() {
		return model;
	}
	public int getHigh() {
		return high;
	}
	public int getPrefDistance() {
		return prefDistance;
	}
}
