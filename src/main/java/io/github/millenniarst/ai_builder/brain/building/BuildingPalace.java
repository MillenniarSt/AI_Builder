package io.github.millenniarst.ai_builder.brain.building;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;

import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.exception.IdNotFoundException;
import io.github.millenniarst.ai_builder.exception.OverlapMapException;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.brain.map.house.MapFloor;
import io.github.millenniarst.ai_builder.brain.map.house.MapRoom;
import io.github.millenniarst.ai_builder.brain.area.world.house.Room;
import io.github.millenniarst.ai_builder.config.building.BuildingPalaceStyle;
import io.github.millenniarst.ai_builder.config.Style;

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
	
	private HashMap<Integer, MapFloor> map;
	private ArrayList<Room> inRooms;
	
	private int rooms;
	private int floors;
	
	public BuildingPalace(Style style, BuildingPalaceStyle styleBuild) {
		super(style, styleBuild);
		this.map = new HashMap<>();
		this.inRooms = new ArrayList<>();
		this.floors = styleBuild.getFloors();
		this.rooms = styleBuild.getRooms();
	}
	
	public void build() {
		AI_Builder.printDebug("Build: " + this);
		AI_Builder.print("Starting to create build " + this.getStyleBuild().getName());
		AI_Builder.setExecute(true);
		try {
			AI_Builder.print("Creating build map " + this.getStyleBuild().getName());
			MapFloor basic = new MapFloor(0, false);
			basic.buildFloor(this);
			map.put(0, basic);
			for(int i = 1; i <= this.floors; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, map.get(i - 1), this);
			}
			for(int i = this.getStyleBuild().getUndergroundFloors() * -1; i < 0; i++) {
				MapFloor floor = new MapFloor(i, false);
				floor.buildFloor(basic, floor, this);
			}
			AI_Builder.print("Create map " + this.getStyleBuild().getName() + " with " + this.floors + " floors and " + this.getStyleBuild().getUndergroundFloors() + " underground floors");
			
			AI_Builder.print("Building schematic...");
			AI_Builder.printDebug("Building floor");
			for(Integer floor: map.keySet()) {
				AI_Builder.print("Building floor " + floor);
				MapFloor mapFloor = map.get(floor);
				Integer high = getStyleBuild().getFloorHigh().get(floor);
				if(high == null)
					high = getStyleBuild().getDefaultFloorHigh();
				int base = getPosition().getBlockY();
				if(floor > 0) {
					base--;
					for(int i = 0; i < floor; i++) {
						Integer highFloor = getStyleBuild().getFloorHigh().get(i);
						if(highFloor == null)
							highFloor = getStyleBuild().getDefaultFloorHigh();
						base = base + highFloor;
					}
				} else if(floor < 0) {
					for(int i = 0; i > floor; i--) {
						Integer highFloor = getStyleBuild().getFloorHigh().get(i);
						if(highFloor == null)
							highFloor = getStyleBuild().getDefaultFloorHigh();
						base = base - highFloor;
					}
				}
				mapFloor.setWallInside(getStyleBuild().getRandomWall(floor, high, true));
				mapFloor.setWallOutside(getStyleBuild().getRandomWall(floor, high, false));
				AI_Builder.printDebug("Built floor " + floor + " with base " + base + ", high " + high + " wallStyle: inside " + mapFloor.getWallInside() + " outside " + mapFloor.getWallOutside());
				for(MapRoom mapRoom : mapFloor.getRooms()) {
					Room room = new Room(mapRoom, base, high);
					room.build(this, mapFloor);
				}
				AI_Builder.print("Finish to build floor " + floor + "'s rooms");
			}
		} catch(NumberFormatException | AIObjectNotFoundException | OverlapMapException | IdNotFoundException exc) {
			AI_Builder.print("Fail to create build: please look to the console", ChatColor.RED);
			AI_Builder.printConsole(exc.getMessage(), ChatColor.RED);
		} catch(Exception exc) {
			AI_Builder.print("Fail to create build: please look to the console", ChatColor.RED);
			AI_Builder.printConsole(exc.getMessage(), ChatColor.RED);
		}
		AI_Builder.setExecute(false);
	}
	
	public String toString() {
		return this.getStyleBuild().toString();
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
		return (BuildingPalaceStyle) super.getStyleBuild();
	}
	public void setStyleBuild(BuildingPalaceStyle styleBuild) {
		super.setStyleBuild(styleBuild);
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
