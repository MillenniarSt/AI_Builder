package io.github.millenniarst.ai_builder.config.component;

import java.io.File;
import java.util.Set;

import io.github.millenniarst.ai_builder.config.model.Model;
import io.github.millenniarst.ai_builder.config.model.WallModel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.util.RandomCollection;

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

public class WallConfig extends Component {

	private WallCornerConfig corner;
	private WallModel model;
	private RandomCollection<Window> window;
	
	private int minFloor;
	private int maxFloor;
	private int minHigh;
	private int maxHigh;
	private boolean inside;
	
	public WallConfig(String id) {
		super(id);
		this.model = new Model();
		this.corner = new WallCornerConfig(null);
	}
	
	public String toString() {
		return "id=" + getId() + ",corner=[" + corner + "],model=" + model + ",minFloor=" + minFloor + ",maxFloor=" + maxFloor + ",minHigh=" + minHigh + ",maxHigh=" + maxHigh + ",inside=" + inside;
	}
	
	@Override
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		String modelPath = getDirectoryPath() + file.getString("bottom", null);
		if(modelPath != null)
			if(!this.model.load(modelPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load wall bottom model " + modelPath);
				this.setState(STATE_DISABLE);
				return false;
			}
		else
			this.model = new Model();
			
		String cornerPath = getDirectoryPath() + file.getString("corner", null);
		if(cornerPath != null)
			if(!this.corner.load(cornerPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load wall corner " + cornerPath);
				this.setState(STATE_DISABLE);
				return false;
			}
		else
			this.corner = null;
			
		window.getCollection().clear();
		Set<String> windows = file.getConfigurationSection("window").getKeys(false);
		if(!windows.isEmpty() && windows != null) {
			for(String key : windows) {
				Window wind = new Window(key);
				if(!wind.load(getDirectoryPath() + key)) {
					AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load window " + key);
					this.setState(STATE_DISABLE);
					return false;
				}
				window.add(wind, file.getInt("window." + key, 1));
			}
		}
			
		minFloor = file.getInt("preferences.minFloor", Integer.MIN_VALUE);
		maxFloor = file.getInt("preferences.maxFloor", Integer.MAX_VALUE);
		minHigh = file.getInt("preferences.minHigh", Integer.MIN_VALUE);
		maxHigh = file.getInt("preferences.maxHigh", Integer.MAX_VALUE);
		inside = file.getBoolean("preferences.inside", false);
		
		this.setState(STATE_ENABLE);
		return true;
	}

	public String getDirectoryPath() {
		return "walls\\";
	}
	
	public WallCornerConfig getCorner() {
		return corner;
	}
	public int getMinFloor() {
		return minFloor;
	}
	public int getMaxFloor() {
		return maxFloor;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public WallModel getModel() {
		return model;
	}
	public int getMinHigh() {
		return minHigh;
	}
	public RandomCollection<Window> getWindow() {
		return window;
	}
}
