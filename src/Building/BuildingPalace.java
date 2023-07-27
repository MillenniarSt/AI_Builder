package Building;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.AIObjectNotFoundException;
import Exception.IdNotFoundException;
import Exception.OverlapMapException;
import Exception.RoomNotFoundException;
import Exception.WallNotFoundException;
import Main.Main;
import Main.Random;
import Main.RandomCollection;
import Map.CustomMapRoom;
import Map.MapFloor;
import Map.MapRoom;
import Model.Room;
import Model.WallStyle;

public class BuildingPalace extends Building {
	
	private RandomCollection<CustomMapRoom> roomWeigh;
	private RandomCollection<WallStyle> wallWeigh;
	private HashMap<Integer, MapFloor> map;
	private ArrayList<Room> inRooms;
	
	private int baseY;
	
	private HashMap<Integer, Integer> floorHigh;
	private int defaultFloorHigh;
	private int floors;
	private int undergroundFloors;
	private int rooms;
	private int wallsize;
	private int wallAnchor;
	private int stairsWeigh;
	
	protected BuildingPalace(String name, RandomCollection<CustomMapRoom> roomWeigh, RandomCollection<WallStyle> wallWeigh, int floors, int undergroundFloors, int rooms, 
			int wallsize, int wallAnchor, int stairsWeigh) {
		super(name);
		this.roomWeigh = roomWeigh;
		this.wallWeigh = wallWeigh;
		this.floors = floors;
		this.undergroundFloors = undergroundFloors;
		this.rooms = rooms;
		this.wallsize = wallsize;
		this.wallAnchor = wallAnchor;
		this.stairsWeigh = stairsWeigh;
		this.map = new HashMap<>();
		this.inRooms = new ArrayList<>();
	}
	
	public void generateMap() throws Exception {
		Main.printDebug("Build: " + this);
		Main.print("Starting to create build " + this.getName());
		try {
			Main.print("Creating build map " + this.getName());
			MapFloor basic = new MapFloor(0, false);
			basic.buildFloor(this);
			map.put(0, basic);
			for(int i = 1; i <= this.floors; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, map.get(i - 1), this);
			}
			for(int i = this.undergroundFloors * -1; i < 0; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, floor, this);
			}
			Main.print("Create map " + this.getName() + " with " + this.floors + " floors and " + this.undergroundFloors + " underground floors");
			
			Main.print("Building schematic...");
			Main.printDebug("Building floor");
			for(Integer floor: map.keySet()) {
				Main.print("Building floor " + floor);
				MapFloor mapFloor = map.get(floor);
				Integer high = floorHigh.get(floor);
				if(high == null)
					high = defaultFloorHigh;
				int base = baseY;
				if(floor > 0) {
					base--;
					for(int i = 0; i < floor; i++) {
						Integer highFloor = floorHigh.get(i);
						if(highFloor == null)
							highFloor = defaultFloorHigh;
						base = base + highFloor;
					}
				} else if(floor < 0) {
					for(int i = 0; i > floor; i--) {
						Integer highFloor = floorHigh.get(i);
						if(highFloor == null)
							highFloor = defaultFloorHigh;
						base = base - highFloor;
					}
				}
				mapFloor.setWallInside(getRandomWall(floor, high, true));
				mapFloor.setWallOutside(getRandomWall(floor, high, false));
				Main.printDebug("Built floor " + floor + " with base " + base + ", high " + high + " wallStyle: inside " + mapFloor.getWallInside() + " outside " + mapFloor.getWallOutside());
				for(MapRoom mapRoom : mapFloor.getRooms()) {
					Room room = new Room(mapRoom, base, high);
					room.build(this, mapFloor);
				}
				Main.print("Finish to build floor " + floor + "'s rooms");
			}
		} catch(NumberFormatException | AIObjectNotFoundException | OverlapMapException | IdNotFoundException exc) {
			Main.print("Fail to create build: please look to the console");
			System.out.println(exc);
			exc.printStackTrace();
		} catch(Exception exc) {
			Main.print("Fail to create build: please look to the console");
			System.out.println(exc);
			exc.printStackTrace();
		}
	}
	
	public MapRoom getRandomParentRoom(ArrayList<MapRoom> rooms) throws RoomNotFoundException {
		Main.printDebug("Searching a parent room by array: " + rooms);
		if(rooms.isEmpty()) {
			throw new ArrayIndexOutOfBoundsException("The arraylist rooms must not be empity to search a parent");
		} else {
			ArrayList<MapRoom> posRoom = new ArrayList<>();
			for(MapRoom room : rooms) {
				if(room.getCustom().getPrefDoor() - room.getDoor() > 0) {
					posRoom.add(room);
				}
			}
			Main.printDebug("Getting a random parent room by array: " + posRoom);
			if(posRoom.isEmpty()) {
				MapRoom selcted = rooms.get((int) (Math.random() * rooms.size()));
				Main.printDebug("Return " + selcted + " parent room");
				return selcted;
			} else {
				MapRoom selcted = posRoom.get((int) (Math.random() * posRoom.size()));
				Main.printDebug("Return " + selcted + " parent room");
				return selcted;
			}
		}
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
	public HashMap<Integer, MapFloor> getMap() {
		return map;
	}
	public void setMap(HashMap<Integer, MapFloor> map) {
		this.map = map;
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
	public ArrayList<Room> getInRooms() {
		return inRooms;
	}
	public void setInRooms(ArrayList<Room> inRooms) {
		this.inRooms = inRooms;
	}
	public HashMap<Integer, Integer> getFloorHigh() {
		return floorHigh;
	}
	public void setFloorHigh(HashMap<Integer, Integer> floorHigh) {
		this.floorHigh = floorHigh;
	}
	public int getBaseY() {
		return baseY;
	}
	public void setBaseY(int baseY) {
		this.baseY = baseY;
	}
}
