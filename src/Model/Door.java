package Model;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;

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
		
		int distance = file.getInt("distance", 1);
		if(distance < 1)
			distance = 1;
		this.distance = distance;
		int weigh = file.getInt("weigh", 1);
		if(weigh < 1)
			weigh = 1;
		this.weigh = weigh;
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			this.model.load("doors\\" + modelPath);
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
