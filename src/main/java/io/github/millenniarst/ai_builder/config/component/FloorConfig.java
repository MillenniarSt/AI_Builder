package io.github.millenniarst.ai_builder.config.component;

import java.io.File;
import java.util.List;

import io.github.millenniarst.ai_builder.config.model.Model;
import io.github.millenniarst.ai_builder.config.model.Schematic;
import io.github.millenniarst.ai_builder.config.model.SingleModel;
import io.github.millenniarst.ai_builder.config.model.WallModel;
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

public class FloorConfig extends Component {

	private WallModel model;
	private SingleModel[] center;
	
	private boolean ceiling;
	private boolean inside;

	public FloorConfig(String id) {
		super(id);
		this.model = new Model();
		this.center = new SingleModel[4];
		this.inside = false;
		this.ceiling = false;
	}
	
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		String cornerPath = file.getString("model", null);
		if(cornerPath != null)
			if(!this.model.load(cornerPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load floor corner model " + cornerPath);
				this.setState(STATE_DISABLE);
				return false;
			}
		else
			this.model = new Model();
		List<String> centers = file.getStringList("center");
		if(centers != null) {
			if(!centers.isEmpty()) {
				for(int i = 0; i < 4; i++) {
					String centerPath = centers.get(i);
					if(centerPath != null) {
						center[i] = new SingleModel();
						if(!center[i].load(centerPath)) {
							AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load floor center model " + centerPath + " id:" + i);
							this.setState(STATE_ERROR);
							return false;
						}
					} else {
						this.setState(STATE_DISABLE);
						return false;
					}
				}
			}
		}
		
		ceiling = file.getBoolean("ceiling", false);
		inside = file.getBoolean("inside", false);
		
		this.setState(STATE_ENABLE);
		return true;
	}

	public String getDirectoryPath() {
		return "floors\\";
	}
	
	public String toString() {
		return "model=" + model + ",center=" + center + ",ceiling=" + ceiling +  ",inside=" + inside;
	}

	public WallModel getModel() {
		return model;
	}
	public SingleModel[] getCenter() {
		return center;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public boolean isCeiling() {
		return ceiling;
	}
}
