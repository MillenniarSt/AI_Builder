package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

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

public class CustomStyle implements Loader {

	private String id;
	private ArrayList<String> enableStyles;

	public CustomStyle(String id) {
		this.id = id.replaceAll("\\", ".");
		this.enableStyles = new ArrayList<>();
	}
	public CustomStyle(String id, ArrayList<String> enableStyles) {
		this.id = id.replaceAll("\\", ".");
		this.enableStyles = enableStyles;
	}

	@Override
	public boolean load(String path) {
		id = path.replaceAll("\\", ".");
		return true;
	}
	
	public boolean loadEnableStyle(FileConfiguration file) {
		enableStyles.clear();
		List<String> setEnableStyles = file.getStringList("styles");
		if(setEnableStyles == null) {
			enableStyles = new ArrayList<>();
		} else if(setEnableStyles.isEmpty()) {
			enableStyles = new ArrayList<>();
		} else {
			for(String style : setEnableStyles)
				enableStyles.add(style);
		}
		return true;
	}
	
	public String toString() {
		return id;
	}
	
	protected void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public ArrayList<String> getEnableStyles() {
		return enableStyles;
	}
	public void setEnableStyles(ArrayList<String> enableStyles) {
		this.enableStyles = enableStyles;
	}
}
