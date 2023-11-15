package io.github.millenniarst.ai_builder.config;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import io.github.millenniarst.ai_builder.config.building.BuildingStyle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.util.RandomCollection;

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
	private String currentColor;
	private RandomCollection<String> colors;
	private HashMap<String, StyleGroup> groups;

	private HashMap<String, BuildingStyle> buildings = new HashMap<>();
	
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
		
		Set<String> colorsName = file.getConfigurationSection("colors").getKeys(false);
		if(colorsName != null) {
			for(String color : colorsName) {
				colors.add(color, getInt(file, "colors." + color, 1));
			}
		} else {
			colors.add("white", 1);
		}
		
		Set<String> groups = file.getConfigurationSection("groups").getKeys(false);
		if(groups != null) {
			for(String group : groups) {
				
				StyleGroup styleGroup = new StyleGroup(group);
				this.groups.put(group, styleGroup);
				
				Set<String> random = file.getConfigurationSection("groups." + group).getKeys(false);
				if(random != null) {
					for(String child : random) {
						RandomCollection<String> collection = new RandomCollection<>();
						int weigh = getInt(file, "groups." + group, 1);
						styleGroup.getRandom().add(new UnderGroupStyle(), weigh);
						Set<String> values = file.getConfigurationSection("groups." + group + "." + child).getKeys(false);
						for(String value : values) {
							collection.add(value, file.getInt("groups." + group + "." + value, 1));
						}
					}
				} else {
					this.setState(STATE_ERROR);
					return false;
				}
			}
		} else {
			this.setState(STATE_ERROR);
			return false;
		}
		this.setState(STATE_ENABLE);
		return true;
	}
	
	public void changeIndexs() {
		try {
			for(String group : groups.keySet()) {
					groups.get(group).changeIndex();
			}
		} catch (AIObjectNotFoundException exc) {
			exc.printStackTrace();
		}
	}
	public void changeColorIndex() {
		try {
			this.currentColor = colors.getRandom();
		} catch (AIObjectNotFoundException exc) {
			exc.printStackTrace();
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
	public RandomCollection<String> getColors() {
		return colors;
	}
	public void setColors(RandomCollection<String> colors) {
		this.colors = colors;
	}
	public String getCurrentColor() {
		return currentColor;
	}
}
