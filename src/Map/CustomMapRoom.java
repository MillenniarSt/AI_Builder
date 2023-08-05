package Map;

import java.io.File;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Exception.AIObjectNotFoundException;
import Exception.FloorNotFoundException;
import Main.Main;
import Main.Random;
import Main.RandomCollection;
import Style.CustomStyle;
import Style.Door;
import Style.FloorStyle;
import Style.RoofStyle;

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

public class CustomMapRoom extends CustomStyle {
	
	private int minX;
	private int maxX;
	private int minZ;
	private int maxZ;
	
	private int minFloor;
	private int maxFloor;
	private int floor;
	
	private int prefDoor;
	private RandomCollection<Door> door;
	private RandomCollection<FloorStyle> flooring;
	private RandomCollection<RoofStyle> roofs;
	private String roofType;
	
	private boolean stairs;
	
	public CustomMapRoom(String id) {
		super(id);
	}
	public CustomMapRoom(int minX, int maxX, int minZ, int maxZ, int minFloor, int maxFloor, int floor, int prefDoor,
			RandomCollection<Door> door, RandomCollection<FloorStyle> flooring, String roofType, boolean stairs, RandomCollection<RoofStyle> roofs, String id) {
		super(id);
		this.minX = minX;
		this.maxX = maxX;
		this.minZ = minZ;
		this.maxZ = maxZ;
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
		this.floor = floor;
		this.prefDoor = prefDoor;
		this.door = door;
		this.flooring = flooring;
		this.roofs = roofs;
		this.roofType = roofType;
		this.stairs = stairs;
	}
	
	public FloorStyle getRandomFlooring(boolean ceiling, boolean inside) throws FloorNotFoundException, AIObjectNotFoundException {
		Main.printDebug("Searching a flooring for the room" + getId() + " by array: " + flooring);
		RandomCollection<FloorStyle> floors = new RandomCollection<>();
		for(Random<FloorStyle> floor : flooring.getCollection()) {
			if(ceiling == floor.getObject().isCeiling() && inside == floor.getObject().isInside()) {
				floors.add(floor);
			}
		}
		Main.printDebug("Searching a room by array: " + floors);
		if(floors.getCollection().isEmpty()) {
			throw new FloorNotFoundException(ceiling, inside);
		} else {
			FloorStyle floor = floors.getRandom();
			Main.printDebug("Return flooring " + floor);
			return floor;
		}
	}
	
	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		loadEnableStyle(file);
		
		minFloor = file.getInt("preferencies.minFloor", Integer.MIN_VALUE);
		maxFloor = file.getInt("preferencies.maxFloor", Integer.MAX_VALUE);
		minX = getInt(file, "preferencies.minX", 1, 4);
		maxX = getInt(file, "preferencies.maxX", 1, 8);
		minZ = getInt(file, "preferencies.minZ", 1, 4);
		maxZ = getInt(file, "preferencies.maxZ", 1, 8);
		prefDoor = getInt(file, "preferencies.prefDoor", 1);
		
		door.getCollection().clear();
		Set<String> doors = file.getConfigurationSection("door").getKeys(false);
		if(doors != null) {
			if(!doors.isEmpty()) {
				for(String key : doors) {
					Door dr = new Door(key);
					if(!dr.load("doors\\" + key)) {
						Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load door style " + key);
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
					FloorStyle flr = new FloorStyle(key);
					if(!flr.load("floors\\" + key)) {
						Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load floor style " + key);
						return false;
					}
					flooring.add(flr, file.getInt("flooring." + key));
				}
			}
		}
		
		return true;
	}
	
	public int getMinX() {
		return minX;
	}
	public void setMinX(int minX) {
		this.minX = minX;
	}
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}
	public int getMinZ() {
		return minZ;
	}
	public void setMinZ(int minZ) {
		this.minZ = minZ;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
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
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getPrefDoor() {
		return prefDoor;
	}
	public void setPrefDoor(int prefDoor) {
		this.prefDoor = prefDoor;
	}
	public boolean isStairs() {
		return stairs;
	}
	public void setStairs(boolean stairs) {
		this.stairs = stairs;
	}
	public RandomCollection<Door> getDoor() {
		return door;
	}
	public void setDoor(RandomCollection<Door> door) {
		this.door = door;
	}
	public RandomCollection<FloorStyle> getFlooring() {
		return flooring;
	}
	public void setFlooring(RandomCollection<FloorStyle> flooring) {
		this.flooring = flooring;
	}
	public RandomCollection<RoofStyle> getRoofs() {
		return roofs;
	}
	public void setRoofs(RandomCollection<RoofStyle> roofs) {
		this.roofs = roofs;
	}
	public String getRoofType() {
		return roofType;
	}
	public void setRoofType(String roofType) {
		this.roofType = roofType;
	}
}
