package Model;

import java.io.File;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.CustomStyle;
import Main.RandomCollection;

public class WallStyle extends CustomStyle {

	private WallCorner corner;
	private Model modelBotton;
	private Model modelUp;
	private Model modelRepeat;
	private RandomCollection<Window> window;
	
	private int minFloor;
	private int maxFloor;
	private int minHigh;
	private int maxHigh;
	private boolean inside;
	
	public WallStyle(String id) {
		super(id);
		this.modelBotton = new Model();
		this.modelUp = new Model();
		this.modelRepeat = new Model();
	}
	public WallStyle(WallCorner corner, Model modelBotton, Model modelUp, Model modelRepeat, RandomCollection<Window> window, 
			int minFloor, int maxFloor, int minHigh, int maxHigh, boolean inside, String id) {
		super(id);
		this.corner = corner;
		this.modelBotton = modelBotton;
		this.modelUp = modelUp;
		this.modelRepeat = modelRepeat;
		this.window = window;
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
		this.minHigh = minHigh;
		this.maxHigh = maxHigh;
		this.inside = inside;
	}
	
	public WallStyle clone() {
		return new WallStyle(corner, modelBotton.clone(), modelUp.clone(), modelRepeat.clone(), window, minFloor, maxFloor, minHigh, maxHigh, inside, getId());
	}
	
	public String toString() {
		return "id=" + getId() + ",corner=[" + corner + "],modelBotton=" + modelBotton + ",modelUp=" + modelUp + ",modelRepeat=" + modelRepeat + 
				",minFloor=" + minFloor + ",maxFloor=" + maxFloor + ",minHigh=" + minHigh + ",maxHigh=" + maxHigh + ",inside=" + inside;
	}
	
	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		String bottomPath = file.getString("bottom", null);
		if(bottomPath != null)
			this.modelBotton.load("walls\\" + bottomPath);
		else
			this.modelBotton = null;
		String upPath = file.getString("up", null);
		if(upPath != null)
			this.modelUp.load("walls\\" + upPath);
		else
			this.modelUp = null;
		String repeatPath = file.getString("repeat", null);
		if(repeatPath != null)
			this.modelRepeat.load("walls\\" + repeatPath);
		else
			return false;
			
		String cornerPath = file.getString("corner", null);
		if(cornerPath != null)
			this.corner.load("walls\\" + cornerPath);
		else
			this.corner = null;
			
		window.getCollection().clear();
		Set<String> windows = file.getConfigurationSection("window").getKeys(false);
		if(!windows.isEmpty() && windows != null) {
			for(String key : windows) {
				Window wind = new Window(key);
				wind.load("windows\\" + key);
				window.add(wind, file.getInt("window." + key));
			}
		}
			
		minFloor = file.getInt("preferencies.minFloor", Integer.MIN_VALUE);
		maxFloor = file.getInt("preferencies.maxFloor", Integer.MAX_VALUE);
		minHigh = file.getInt("preferencies.minHigh", Integer.MIN_VALUE);
		maxHigh = file.getInt("preferencies.maxHigh", Integer.MAX_VALUE);
		inside = file.getBoolean("preferencies.inside", false);
		
		return true;
	}
	
	public WallCorner getCorner() {
		return corner;
	}
	public void setCorner(WallCorner corner) {
		this.corner = corner;
	}
	public int getMinFloor() {
		return minFloor;
	}
	public void setMinFloor(int minFloor) {
		this.minFloor = minFloor;
	}
	public int getMaxFloor() {
		return maxFloor;
	}
	public void setMaxFloor(int maxFloor) {
		this.maxFloor = maxFloor;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public Model getModelBotton() {
		return modelBotton;
	}
	public void setModelBotton(Model modelBotton) {
		this.modelBotton = modelBotton;
	}
	public Model getModelUp() {
		return modelUp;
	}
	public void setModelUp(Model modelUp) {
		this.modelUp = modelUp;
	}
	public Model getModelRepeat() {
		return modelRepeat;
	}
	public void setModelRepeat(Model modelRepeat) {
		this.modelRepeat = modelRepeat;
	}
	public int getMinHigh() {
		return minHigh;
	}
	public void setMinHigh(int minHigh) {
		this.minHigh = minHigh;
	}
	public int getMaxHigh() {
		return maxHigh;
	}
	public void setMaxHigh(int maxHigh) {
		this.maxHigh = maxHigh;
	}
	public RandomCollection<Window> getWindow() {
		return window;
	}
	public void setWindow(RandomCollection<Window> window) {
		this.window = window;
	}
}
