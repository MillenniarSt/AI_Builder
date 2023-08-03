package Building;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Exception.AIObjectNotFoundException;
import Exception.RoomNotFoundException;
import Exception.WallNotFoundException;
import Main.Main;
import Main.Random;
import Main.RandomCollection;
import Map.CustomMapRoom;
import Model.WallStyle;

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

public class BuildingPalaceStyle extends BuildingStyle {

	private RandomCollection<CustomMapRoom> roomWeigh;
	private RandomCollection<WallStyle> wallWeigh;
	
	private HashMap<Integer, Integer> floorHigh;
	private int defaultFloorHigh;
	private int floors;
	private int undergroundFloors;
	private int rooms;
	private int wallsize;
	private int wallAnchor;
	private int stairsWeigh;
	
	public BuildingPalaceStyle(String id, String name) {
		super(id, name);
	}
	public BuildingPalaceStyle(String id, String name, RandomCollection<CustomMapRoom> roomWeigh, RandomCollection<WallStyle> wallWeigh, HashMap<Integer, Integer> floorHigh, 
			int defaultFloorHigh, int floors, int undergroundFloors, int rooms, int wallsize, int wallAnchor, int stairsWeigh) {
		super(id, name);
		this.roomWeigh = roomWeigh;
		this.wallWeigh = wallWeigh;
		this.floorHigh = floorHigh;
		this.defaultFloorHigh = defaultFloorHigh;
		this.floors = floors;
		this.undergroundFloors = undergroundFloors;
		this.rooms = rooms;
		this.wallsize = wallsize;
		this.wallAnchor = wallAnchor;
		this.stairsWeigh = stairsWeigh;
	}
	
	@Override
	public boolean load(String path) {
		setId(path.replaceAll("\\", "."));
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		
		this.setName(file.getString("name", getId()));
		
		roomWeigh.getCollection().clear();
		Set<String> rooms = file.getConfigurationSection("rooms").getKeys(false);
		if(rooms != null) {
			if(!rooms.isEmpty()) {
				for(String key : rooms) {
					CustomMapRoom room = new CustomMapRoom(key);
					if(!room.load("rooms\\" + key)) {
						Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load room style " + key);
						return false;
					}
					roomWeigh.add(room, getInt(file, "rooms." + key, 1));
				}
			}
		}
		wallWeigh.getCollection().clear();
		Set<String> walls = file.getConfigurationSection("walls").getKeys(false);
		if(walls != null) {
			if(!walls.isEmpty()) {
				for(String key : walls) {
					WallStyle wall = new WallStyle(key);
					if(!wall.load("walls\\" + key)) {
						Main.getConsole().sendMessage(ChatColor.YELLOW + "Fail to load wall style " + key);
						return false;
					}
					wallWeigh.add(wall, getInt(file, "walls." + key, 1));
				}
			}
		}
		try {
			floorHigh.clear();
			Set<String> highs = file.getConfigurationSection("floorHigh").getKeys(false);
			if(walls != null) {
				if(!walls.isEmpty()) {
					for(String high : highs) {
						floorHigh.put(Integer.parseInt(high), getInt(file, "floorHigh." + high, 1));
					}
				}
			}
		} catch(NumberFormatException exc) {
			System.out.println(exc);
			exc.printStackTrace();
			return false;
		}
		
		getInt(file, "defaultFloorHigh", 1, 5);
		getInt(file, "floors", 0, 2);
		getInt(file, "undergroundFloors", 0);
		getInt(file, "rooms", 1, 3);
		getInt(file, "wallsize", 1);
		getInt(file, "wallAnchor", 0, 3);
		getInt(file, "stairsWeigh", 0, 4);
		
		return true;
	}
	
	public CustomMapRoom getRandomRoom(int floor) throws AIObjectNotFoundException {
		Main.printDebug("Searching a room in the floor " + floor + " by array: " + this.getRoomWeigh());
		RandomCollection<CustomMapRoom> rooms = new RandomCollection<>();
		for(Random<CustomMapRoom> room : this.getRoomWeigh().getCollection()) {
			if(floor >= room.getObject().getMinFloor() && floor <= room.getObject().getMaxFloor()) {
				rooms.add(room);
			}
		}
		Main.printDebug("Searching a room by array: " + rooms);
		if(rooms.getCollection().isEmpty()) {
			throw new RoomNotFoundException(floor);
		} else {
			CustomMapRoom room = rooms.getRandom();
			Main.printDebug("Return room " + room);
			return room;
		}
	}
	public CustomMapRoom getRandomRoom(int floor, int lenghtX, int lenghtZ) throws AIObjectNotFoundException {
		Main.printDebug("Searching a room in the floor " + floor + ", lenght " + lenghtX + " - " + lenghtZ + " by array: " + this.getRoomWeigh());
		RandomCollection<CustomMapRoom> rooms = new RandomCollection<>();
		for(Random<CustomMapRoom> random : this.getRoomWeigh().getCollection()) {
			CustomMapRoom room = random.getObject();
			if(floor >= room.getMinFloor() && floor <= room.getMaxFloor() && (
					(lenghtX >= room.getMinX() && lenghtX <= room.getMaxX() && lenghtZ >= room.getMinZ() && lenghtZ <= room.getMaxZ()) ||
					(lenghtZ >= room.getMinX() && lenghtZ <= room.getMaxX() && lenghtX >= room.getMinZ() && lenghtX <= room.getMaxZ()))) {
				rooms.add(random);
			}
		}
		Main.printDebug("Searching a room by array: " + rooms);
		if(rooms.getCollection().isEmpty()) {
			throw new RoomNotFoundException(floor, lenghtX, lenghtZ);
		} else {
			CustomMapRoom room = rooms.getRandom();
			Main.printDebug("Return room " + room);
			return room;
		}
	}
	
	public WallStyle getRandomWall(int floor, int high, boolean inside) throws WallNotFoundException, AIObjectNotFoundException {
		Main.printDebug("Searching a wall in the floor " + floor + " with inside = " + inside + " by array: " + this.getWallWeigh());
		RandomCollection<WallStyle> walls = new RandomCollection<>();
		for(Random<WallStyle> wall : this.getWallWeigh().getCollection()) {
			if(floor >= wall.getObject().getMinFloor() && floor <= wall.getObject().getMaxFloor() &&
					high >= wall.getObject().getMinHigh() && high <= wall.getObject().getMinHigh() &&
					inside == wall.getObject().isInside()) {
				walls.add(wall);
			}
		}
		Main.printDebug("Searching a wall by array: " + walls);
		if(walls.getCollection().isEmpty()) {
			throw new WallNotFoundException(floor, high, inside);
		} else {
			WallStyle wall = walls.getRandom();
			Main.printDebug("Return wall " + wall);
			return wall;
		}
	}
	
	public String toString() {
		return this.getName() + ": floors " + this.floors + " - " + this.undergroundFloors + ", wall size " + this.wallsize + " anchor " + this.wallAnchor +
				", rooms " + this.rooms + ", stairs weigh " + this.stairsWeigh;
	}
	
	public int getFloors() {
		return floors;
	}
	public void setFloors(int floors) {
		this.floors = floors;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public int getWallsize() {
		return wallsize;
	}
	public void setWallsize(int wallsize) {
		this.wallsize = wallsize;
	}
	public int getWallAnchor() {
		return wallAnchor;
	}
	public void setWallAnchor(int wallAnchor) {
		this.wallAnchor = wallAnchor;
	}
	public RandomCollection<CustomMapRoom> getRoomWeigh() {
		return roomWeigh;
	}
	public void setRoomWeigh(RandomCollection<CustomMapRoom> roomWeigh) {
		this.roomWeigh = roomWeigh;
	}
	public HashMap<Integer, Integer> getFloorHigh() {
		return floorHigh;
	}
	public void setFloorHigh(HashMap<Integer, Integer> floorHigh) {
		this.floorHigh = floorHigh;
	}
	public int getStairsWeigh() {
		return stairsWeigh;
	}
	public void setStairsWeigh(int stairsWeigh) {
		this.stairsWeigh = stairsWeigh;
	}

	public int getUndergroundFloors() {
		return undergroundFloors;
	}
	public void setUndergroundFloors(int undergroundFloors) {
		this.undergroundFloors = undergroundFloors;
	}
	public RandomCollection<WallStyle> getWallWeigh() {
		return wallWeigh;
	}
	public void setWallWeigh(RandomCollection<WallStyle> wallWeigh) {
		this.wallWeigh = wallWeigh;
	}
	public int getDefaultFloorHigh() {
		return defaultFloorHigh;
	}
	public void setDefaultFloorHigh(int defaultFloorHigh) {
		this.defaultFloorHigh = defaultFloorHigh;
	}
}
