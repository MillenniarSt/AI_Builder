package io.github.millenniarst.ai_builder.config.component;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.exception.FloorNotFoundException;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.util.Random;
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

public class MapRoomConfig extends Component {
	
	private int minX;
	private int maxX;
	private int minZ;
	private int maxZ;
	
	private int minFloor;
	private int maxFloor;
	private int floor;
	
	private int prefDoor;
	private RandomCollection<DoorConfig> door;
	private RandomCollection<FloorConfig> flooring;
	private RandomCollection<RoofStyle> roofs;
	private String roofType;
	
	private boolean stairs;
	
	public MapRoomConfig(String id) {
		super(id);
	}
	
	public FloorConfig getRandomFlooring(boolean ceiling, boolean inside) throws FloorNotFoundException, AIObjectNotFoundException {
		AI_Builder.printDebug("Searching a flooring for the room" + getId() + " by array: " + flooring);
		RandomCollection<FloorConfig> floors = new RandomCollection<>();
		for(Random<FloorConfig> floor : flooring.getCollection()) {
			if(ceiling == floor.getObject().isCeiling() && inside == floor.getObject().isInside()) {
				floors.add(floor);
			}
		}
		AI_Builder.printDebug("Searching a room by array: " + floors);
		if(floors.getCollection().isEmpty()) {
			throw new FloorNotFoundException(ceiling, inside);
		} else {
			FloorConfig floor = floors.getRandom();
			AI_Builder.printDebug("Return flooring " + floor);
			return floor;
		}
	}
	
	@Override
	public boolean load(String path) {
		path = getDirectoryPath() + path;
		super.load(path);
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		minFloor = file.getInt("preferences.minFloor", Integer.MIN_VALUE);
		maxFloor = file.getInt("preferences.maxFloor", Integer.MAX_VALUE);
		minX = getInt(file, "preferences.minX", 1, 4);
		maxX = getInt(file, "preferences.maxX", 1, 8);
		minZ = getInt(file, "preferences.minZ", 1, 4);
		maxZ = getInt(file, "preferences.maxZ", 1, 8);
		prefDoor = getInt(file, "preferences.prefDoor", 1);
		
		door.getCollection().clear();
		Set<String> doors = file.getConfigurationSection("door").getKeys(false);
		if(doors != null) {
			if(!doors.isEmpty()) {
				for(String key : doors) {
					DoorConfig dr = new DoorConfig(key);
					if(!dr.load("doors\\" + key)) {
						AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load door style " + key);
						return false;
					}
					door.add(dr, file.getInt("door." + key));
				}
			}
		}
		flooring.getCollection().clear();
		Set<String> floorings = file.getConfigurationSection("flooring").getKeys(false);
		if(floorings != null) {
			if(!floorings.isEmpty()) {
				for(String key : floorings) {
					FloorConfig flr = new FloorConfig(key);
					if(!flr.load("floors\\" + key)) {
						AI_Builder.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load floor style " + key);
						return false;
					}
					flooring.add(flr, file.getInt("flooring." + key));
				}
			}
		}

		return true;
	}

	public String getDirectoryPath() {
		return "rooms\\";
	}
	
	public int getMinX() {
		return minX;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMinZ() {
		return minZ;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public int getMinFloor() {
		return minFloor;
	}
	public int getMaxFloor() {
		return maxFloor;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getPrefDoor() {
		return prefDoor;
	}
	public boolean isStairs() {
		return stairs;
	}
	public RandomCollection<DoorConfig> getDoor() {
		return door;
	}
	public RandomCollection<FloorConfig> getFlooring() {
		return flooring;
	}
	public RandomCollection<RoofStyle> getRoofs() {
		return roofs;
	}
	public String getRoofType() {
		return roofType;
	}
}
