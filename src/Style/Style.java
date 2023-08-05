package Style;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Main.RandomCollection;

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

public class Style implements Loader {

	public static HashMap<String, Style> enabledStyles = new HashMap<>();
	
	private String name;
	private String state;
	private HashMap<String, StyleGroup> groups;
	
	public Style(String name) {
		this.name = name;
		this.setGroups(new HashMap<>());
		this.state = STATE_VOID;
	}
	public Style(String name, HashMap<String, StyleGroup> groups) {
		this.name = name;
		this.setGroups(groups);
		this.state = STATE_ENABLE;
	}
	
	@Override
	public boolean load(String path) {
		path = "styles\\" + path;
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path));
		
		this.name = file.getString("name", path);
		Set<String> groups = file.getConfigurationSection("groups").getKeys(false);
		for(String group : groups) {
			
			StyleGroup styleGroup = new StyleGroup(group);
			this.groups.put(group, styleGroup);
			
			Set<String> random = file.getConfigurationSection("groups." + group).getKeys(false);
			for(String child : random) {
				RandomCollection<String> collection = new RandomCollection<>();
				styleGroup.getRandom().add(collection);
				Set<String> values = file.getConfigurationSection("groups." + group + "." + child).getKeys(false);
				for(String value : values) {
					collection.add(value, file.getInt("groups." + group + "." + value, 1));
				}
			}
		}
		this.setState(STATE_ENABLE);
		return true;
	}
	
	public void changeIndexs() {
		for(String group : groups.keySet()) {
			groups.get(group).changeIndex();
		}
	}
	
	@Override
	public String getState() {
		return state;
	}
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	public HashMap<String, StyleGroup> getGroups() {
		return groups;
	}
	public void setGroups(HashMap<String, StyleGroup> groups) {
		this.groups = groups;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
