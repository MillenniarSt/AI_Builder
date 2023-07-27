package Main;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Style implements Loader {

	private HashMap<String, StyleGroup> groups;
	
	public Style() {
		this.setGroups(new HashMap<>());
	}
	public Style(HashMap<String, StyleGroup> groups) {
		this.setGroups(groups);
	}
	
	@Override
	public boolean load(String path) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path));
		
		Set<String> groups = file.getConfigurationSection("groups").getKeys(false);
		for(String group : groups) {
			
			StyleGroup styleGroup = new StyleGroup(group);
			this.groups.put(group, styleGroup);
			
			Set<String> random = file.getConfigurationSection(group).getKeys(false);
			for(String value : random) {
				styleGroup.getRandom().add(value, file.getInt("groups." + group + "." + value));
			}
		}
		return true;
	}
	
	public HashMap<String, StyleGroup> getGroups() {
		return groups;
	}
	public void setGroups(HashMap<String, StyleGroup> groups) {
		this.groups = groups;
	}
}
