package Model;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;

public class Window extends CustomStyle {

	private Model model;
	private int high;
	private int prefDistance;
	
	public Window(String id) {
		super(id);
		this.model = new Model();
		this.high = 1;
		this.prefDistance = 1;
	}
	public Window(Model model, int high, int prefDistance, String id) {
		super(id);
		this.model = model;
		this.high = high;
		this.prefDistance = prefDistance;
	}
	
	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		int high = file.getInt("high", 1);
		if(high < 1)
			high = 1;
		this.high = high;
		int prefDistance = file.getInt("prefDistance", 1);
		if(prefDistance < 1)
			prefDistance = 1;
		this.prefDistance = prefDistance;
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			this.model.load("windows\\" + modelPath);
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
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getPrefDistance() {
		return prefDistance;
	}
	public void setPrefDistance(int prefDistance) {
		this.prefDistance = prefDistance;
	}
}
