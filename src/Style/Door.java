package Style;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.Main;
import Model.Position;

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

public class Door extends CustomStyle {

	private Model model;
	private Position origin;
	private int distance;
	private int weigh;
	
	public Door(String id) {
		super(id);
		this.model = new Model();
	}
	
	@Override
	public boolean load(String path) {
		path = "doors\\" + path;
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		distance = getInt(file, "distance", 1);
		weigh = getInt(file, "weigh", 1);
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load(modelPath)) {
				Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load door model " + modelPath);
				return false;
			}
			this.setState(STATE_ENABLE);
			return true;
		} else {
			this.setState(STATE_ENABLE);
			return false;
		}
	}
	
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Position getOrigin() {
		return origin;
	}
	public void setOrigin(Position origin) {
		this.origin = origin;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getWeigh() {
		return weigh;
	}
	public void setWeigh(int weigh) {
		this.weigh = weigh;
	}
}
