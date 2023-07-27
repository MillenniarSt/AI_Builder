package Map;

import java.io.File;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Exception.AIObjectNotFoundException;
import Exception.FloorNotFoundException;
import Main.CustomStyle;
import Main.Main;
import Main.Random;
import Main.RandomCollection;
import Model.Door;
import Model.FloorStyle;

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
	
	private boolean stairs;
	
	public CustomMapRoom(int minX, int maxX, int minZ, int maxZ, int minFloor, int maxFloor, int floor, int prefDoor,
			RandomCollection<Door> door, RandomCollection<FloorStyle> flooring, boolean stairs, String id) {
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
		
		minFloor = file.getInt("preferencies.minFloor", Integer.MIN_VALUE);
		maxFloor = file.getInt("preferencies.maxFloor", Integer.MAX_VALUE);
		int minX = file.getInt("preferencies.minX", 1);
		if(minX < 1)
			minX = 1;
		this.minX = minX;
		int maxX = file.getInt("preferencies.maxX", 8);
		if(maxX < 1)
			maxX = 8;
		this.maxX = maxX;
		int minZ = file.getInt("preferencies.minZ", 1);
		if(minZ < 1)
			minZ = 1;
		this.minZ = minZ;
		int maxZ = file.getInt("preferencies.maxZ", 8);
		if(maxZ < 1)
			maxZ = 8;
		this.maxZ = maxZ;
		int prefDoor = file.getInt("preferencies.prefDoor", 1);
		if(prefDoor < 1)
			prefDoor = 1;
		this.prefDoor = prefDoor;
		
		door.getCollection().clear();
		Set<String> doors = file.getConfigurationSection("door").getKeys(false);
		if(doors != null) {
			if(!doors.isEmpty()) {
				for(String key : doors) {
					Door dr = new Door(key);
					dr.load("doors\\" + key);
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
					flr.load("floors\\" + key);
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
}
