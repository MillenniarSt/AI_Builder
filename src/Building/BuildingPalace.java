package Building;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;

import Exception.AIObjectNotFoundException;
import Exception.IdNotFoundException;
import Exception.OverlapMapException;
import Main.Main;
import Map.MapFloor;
import Map.MapRoom;
import Model.Room;
import Style.BuildingPalaceStyle;
import Style.Style;

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

public class BuildingPalace extends Building {
	
	private BuildingPalaceStyle styleBuild;
	
	private HashMap<Integer, MapFloor> map;
	private ArrayList<Room> inRooms;
	
	private int rooms;
	private int floors;
	
	public BuildingPalace(Style style, BuildingPalaceStyle styleBuild) {
		super(style);
		this.styleBuild = styleBuild;
		this.map = new HashMap<>();
		this.inRooms = new ArrayList<>();
		this.floors = styleBuild.getFloors();
		this.rooms = styleBuild.getRooms();
	}
	
	public void build() {
		Main.printDebug("Build: " + this);
		Main.print("Starting to create build " + this.styleBuild.getName());
		Main.setExecute(true);
		try {
			Main.print("Creating build map " + this.styleBuild.getName());
			MapFloor basic = new MapFloor(0, false);
			basic.buildFloor(this);
			map.put(0, basic);
			for(int i = 1; i <= this.floors; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, map.get(i - 1), this);
			}
			for(int i = this.styleBuild.getUndergroundFloors() * -1; i < 0; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, floor, this);
			}
			Main.print("Create map " + this.styleBuild.getName() + " with " + this.floors + " floors and " + this.styleBuild.getUndergroundFloors() + " underground floors");
			
			Main.print("Building schematic...");
			Main.printDebug("Building floor");
			for(Integer floor: map.keySet()) {
				Main.print("Building floor " + floor);
				MapFloor mapFloor = map.get(floor);
				Integer high = styleBuild.getFloorHigh().get(floor);
				if(high == null)
					high = styleBuild.getDefaultFloorHigh();
				int base = getPosition().getBlockY();
				if(floor > 0) {
					base--;
					for(int i = 0; i < floor; i++) {
						Integer highFloor = styleBuild.getFloorHigh().get(i);
						if(highFloor == null)
							highFloor = styleBuild.getDefaultFloorHigh();
						base = base + highFloor;
					}
				} else if(floor < 0) {
					for(int i = 0; i > floor; i--) {
						Integer highFloor = styleBuild.getFloorHigh().get(i);
						if(highFloor == null)
							highFloor = styleBuild.getDefaultFloorHigh();
						base = base - highFloor;
					}
				}
				mapFloor.setWallInside(styleBuild.getRandomWall(floor, high, true));
				mapFloor.setWallOutside(styleBuild.getRandomWall(floor, high, false));
				Main.printDebug("Built floor " + floor + " with base " + base + ", high " + high + " wallStyle: inside " + mapFloor.getWallInside() + " outside " + mapFloor.getWallOutside());
				for(MapRoom mapRoom : mapFloor.getRooms()) {
					Room room = new Room(mapRoom, base, high);
					room.build(this, mapFloor);
				}
				Main.print("Finish to build floor " + floor + "'s rooms");
			}
		} catch(NumberFormatException | AIObjectNotFoundException | OverlapMapException | IdNotFoundException exc) {
			Main.print("Fail to create build: please look to the console", ChatColor.RED);
			Main.printConsole(exc.getMessage(), ChatColor.RED);
		} catch(Exception exc) {
			Main.print("Fail to create build: please look to the console", ChatColor.RED);
			Main.printConsole(exc.getMessage(), ChatColor.RED);
		}
		Main.setExecute(false);
	}
	
	public String toString() {
		return this.styleBuild.toString();
	}
	
	public HashMap<Integer, MapFloor> getMap() {
		return map;
	}
	public void setMap(HashMap<Integer, MapFloor> map) {
		this.map = map;
	}
	public ArrayList<Room> getInRooms() {
		return inRooms;
	}
	public void setInRooms(ArrayList<Room> inRooms) {
		this.inRooms = inRooms;
	}
	public BuildingPalaceStyle getStyleBuild() {
		return styleBuild;
	}
	public void setStyleBuild(BuildingPalaceStyle styleBuild) {
		this.styleBuild = styleBuild;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public int getFloors() {
		return floors;
	}
	public void setFloors(int floors) {
		this.floors = floors;
	}
}
