package io.github.millenniarst.ai_builder.config.component;

import java.io.File;

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.config.model.ColumnModel;
import org.bukkit.ChatColor;
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

public class WallCornerConfig extends Component {
	
	private ColumnModel model;
	
	private int relX;
	private int relZ;
	
	public WallCornerConfig(String path) {
		super(path);
	}
	
	@Override
	public boolean load(String path) {
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));

		String modelPath = getDirectoryPath() + file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load(modelPath)) {
				AI_Builder.getConsole().sendMessage(ChatColor.RED + "Fail to load wall corner model " + modelPath);
				this.setState(STATE_ERROR);
				return false;
			}
		} else {
			this.setState(STATE_ERROR);
			return false;
		}

		int relX = file.getInt("relX", 1);
		if(relX < 1)
			relX = 1;
		this.relX = relX;
		int relZ = file.getInt("relZ", 1);
		if(relZ < 1)
			relZ = 1;
		this.relZ = relZ;
		
		this.setState(STATE_ENABLE);
		return true;
	}
	
	public String toString() {
		return "materials=" + model + ",relX=" + relX + ",relZ=" + relZ;
	}
	
	public ColumnModel getModel() {
		return model;
	}
	public void setModel(ColumnModel model) {
		this.model = model;
	}
	public int getRelX() {
		return relX;
	}
	public void setRelX(int relX) {
		this.relX = relX;
	}
	public int getRelZ() {
		return relZ;
	}
	public void setRelZ(int relZ) {
		this.relZ = relZ;
	}
}
