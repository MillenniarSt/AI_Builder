package Style;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
		path = "windows\\" + path;
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		high = getInt(file, "high", 1, 2);
		prefDistance = getInt(file, "prefDistance", 1, 2);
		
		String modelPath = file.getString("model", null);
		if(modelPath != null) {
			if(!this.model.load(modelPath)) {
				Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load window model " + modelPath);
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
