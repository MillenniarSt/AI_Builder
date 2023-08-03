package Model;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;
import Main.Main;

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
	public Door(Model model, int distance, int weigh, String id) {
		super(id);
		this.model = model.clone();
		this.origin = null;
		this.distance = distance;
		this.weigh = weigh;
	}
	public Door(Model model, Position origin, int distance, int weigh, String id) {
		super(id);
		this.model = model.clone();
		this.origin = origin;
		this.distance = distance;
		this.weigh = weigh;
	}
	
	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		distance = getInt(file, "distance", 1);
		weigh = getInt(file, "weigh", 1);
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load("doors\\" + modelPath)) {
				Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load door model " + modelPath);
				return false;
			}
			return true;
		} else {
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
