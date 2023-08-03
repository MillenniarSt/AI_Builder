package Model;

import java.util.ArrayList;

import Building.BuildingPalace;
import Exception.AIObjectNotFoundException;
import Exception.FloorNotFoundException;
import Main.Main;
import Map.MapFloor;
import Map.MapPosition;
import Map.MapPositionOpt;
import Map.MapRoom;

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

public class Room {

	private MapRoom map;
	private int base;
	private int high;
	
	private ArrayList<Wall> walls;
	private Floor floor;
	private Floor ceiling;
	private Roof roof;
	
	public Room(MapRoom map, int base, int high) {
		this.map = map;
		this.base = base;
		this.high = high;
		this.walls = new ArrayList<>();
	}
	
	public void build(BuildingPalace building, MapFloor floor) throws Exception {
		Main.print("Building room " + map.getCustom().getId());
		generateFloor(building);
		generateWall(building, floor);
		Main.print("Built room " + map.getCustom().getId());
	}
	
	public void generateFloor(BuildingPalace building) throws FloorNotFoundException, AIObjectNotFoundException {
		Main.printDebug("Generating room's floorings and ceilings");
		this.floor = new Floor(map.getCustom().getRandomFlooring(false, true),
				new Position(map.getNorthWest().getPos().getPosX(), map.getNorthWest().getPos().getPosZ(), base),
				new Position(map.getSouthEast().getPos().getPosX(), map.getSouthEast().getPos().getPosZ(), base), true, false);
		this.ceiling = new Floor(map.getCustom().getRandomFlooring(true, true),
				new Position(map.getNorthWest().getPos().getPosX(), map.getNorthWest().getPos().getPosZ(), base + high -1),
				new Position(map.getSouthEast().getPos().getPosX(), map.getSouthEast().getPos().getPosZ(), base + high -1), true, true);
		
		Main.printDebug("Building room's floorings and ceilings");
		floor.build(building);
		ceiling.build(building);
		Main.printDebug("Finish to build room's floorings and ceilings");
	}
	
	public void generateWall(BuildingPalace building, MapFloor floor) throws AIObjectNotFoundException {
		Main.printDebug("Generating room's wall");
		boolean inside;
		boolean door;
		boolean skip;
		int x;
		int z;
		MapPosition posOut;
		MapPosition posWall;
		
		boolean checkInside;
		boolean checkDoor;
		boolean checkSkip;
		MapPosition checkOut;
		MapPosition checkWall;
		
		Main.printDebug("Generating north walls");
		x = map.getNorthWest().getPos().getPosX() - building.getStyleBuild().getWallsize();
		z = map.getNorthWest().getPos().getPosZ() + 1;
		posOut = floor.getPos(x, z + building.getStyleBuild().getWallsize());
		posWall = floor.getPos(x, z);
		if(posOut == null) {
			inside = false;
		} else {
			inside = posOut.getFilled() != MapPositionOpt.OUTSIDE;
		}
		door = posWall.getFilled() == MapPositionOpt.DOOR;
		skip = posWall.isUsed();
		for(int i = map.getNorthWest().getPos().getPosX() - building.getStyleBuild().getWallsize(); i <= map.getNorthEast().getPos().getPosX(); i++) {
			checkOut = floor.getPos(i, z + building.getStyleBuild().getWallsize());
			checkWall = floor.getPos(i, z);
			if(checkOut == null) {
				checkInside = false;
			} else {
				checkInside = checkOut.getFilled() != MapPositionOpt.OUTSIDE;
			}
			checkDoor = checkWall.getFilled() == MapPositionOpt.DOOR;
			checkSkip = checkWall.isUsed();
			if(skip == false) {
				if(checkSkip == true || (inside != checkInside && checkInside) || door != checkDoor) {
					Wall wall = new Wall(floor.getWall(inside), new Position(x, z, base), new Position(i - 1, z + building.getStyleBuild().getWallsize() - 1, base + high), inside, 0);	
					if(door != checkDoor) {
						wall.setDoor(this.getMap().getStyleDoor());
						wall.getStyle().setCorner(null);
					}
					walls.add(wall);
					x = i;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				} else if(inside != checkInside) {
					walls.add(new Wall(floor.getWall(inside), new Position(x, z, base), new Position(i, z + building.getStyleBuild().getWallsize() - 1, base + high), inside, 0));
					x = i + 1;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				}
				floor.getPos(i, z + building.getStyleBuild().getWallsize() -1).setUsed(true);
			}
		}
		
		Main.printDebug("Generating east walls");
		x = map.getNorthEast().getPos().getPosX() + 1;
		z = map.getNorthEast().getPos().getPosZ() + building.getStyleBuild().getWallsize();
		posOut = floor.getPos(x + building.getStyleBuild().getWallsize(), z);
		posWall = floor.getPos(x, z);
		if(posOut == null) {
			inside = false;
		} else {
			inside = posOut.getFilled() != MapPositionOpt.OUTSIDE;
		}
		door = posWall.getFilled() == MapPositionOpt.DOOR;
		skip = posWall.isUsed();
		for(int i = map.getNorthEast().getPos().getPosZ() + building.getStyleBuild().getWallsize(); i >= map.getSouthEast().getPos().getPosZ(); i--) {
			checkOut = floor.getPos(x  + building.getStyleBuild().getWallsize(), i);
			checkWall = floor.getPos(i, z);
			if(checkOut == null) {
				checkInside = false;
			} else {
				checkInside = checkOut.getFilled() != MapPositionOpt.OUTSIDE;
			}
			checkDoor = checkWall.getFilled() == MapPositionOpt.DOOR;
			checkSkip = checkWall.isUsed();
			if(skip == false) {
				if(checkSkip == true || (inside != checkInside && checkInside) || door != checkDoor) {
					Wall wall = new Wall(floor.getWall(inside), new Position(x, z, base), new Position(x + building.getStyleBuild().getWallsize() - 1, i + 1, base + high), inside, 1);
					if(door != checkDoor) {
						wall.setDoor(this.getMap().getStyleDoor());
						wall.getStyle().setCorner(null);
					}
					walls.add(wall);
					z = i;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				} else if(inside != checkInside) {
					walls.add(new Wall(floor.getWall(inside), new Position(x, z, base), new Position(x + building.getStyleBuild().getWallsize() - 1, i, base + high), inside, 1));
					z = i - 1;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				}
				floor.getPos(x + building.getStyleBuild().getWallsize() -1, i).setUsed(true);
			}
		}
		
		Main.printDebug("Generating south walls");
		x = map.getSouthEast().getPos().getPosX() + building.getStyleBuild().getWallsize();
		z = map.getSouthEast().getPos().getPosZ() - 1;
		posOut = floor.getPos(x, z - building.getStyleBuild().getWallsize());
		posWall = floor.getPos(x, z);
		if(posOut == null) {
			inside = false;
		} else {
			inside = posOut.getFilled() != MapPositionOpt.OUTSIDE;
		}
		door = posWall.getFilled() == MapPositionOpt.DOOR;
		skip = posWall.isUsed();
		for(int i = map.getSouthEast().getPos().getPosX() + building.getStyleBuild().getWallsize(); i >= map.getSouthWest().getPos().getPosX(); i--) {
			checkOut = floor.getPos(i, z - building.getStyleBuild().getWallsize());
			checkWall = floor.getPos(i, z);
			if(checkOut == null) {
				checkInside = false;
			} else {
				checkInside = checkOut.getFilled() != MapPositionOpt.OUTSIDE;
			}
			checkDoor = checkWall.getFilled() == MapPositionOpt.DOOR;
			checkSkip = checkWall.isUsed();
			if(skip == false) {
				if(checkSkip == true || (inside != checkInside && checkInside) || door != checkDoor) {
					Wall wall = new Wall(floor.getWall(inside), new Position(x, z, base), new Position(i + 1, z - building.getStyleBuild().getWallsize() + 1, base + high), inside, 2);
					if(door != checkDoor) {
						wall.setDoor(this.getMap().getStyleDoor());
						wall.getStyle().setCorner(null);
					}
					walls.add(wall);
					x = i;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				} else if(inside != checkInside) {
					walls.add(new Wall(floor.getWall(inside), new Position(x, z, base), new Position(i, z - building.getStyleBuild().getWallsize() + 1, base + high), inside, 2));
					x = i - 1;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				}
				floor.getPos(i, z - building.getStyleBuild().getWallsize() +1).setUsed(true);
			}
		}
		
		Main.printDebug("Generating west walls");
		x = map.getSouthWest().getPos().getPosX() - 1;
		z = map.getSouthWest().getPos().getPosZ() - building.getStyleBuild().getWallsize();
		posOut = floor.getPos(x - building.getStyleBuild().getWallsize(), z);
		posWall = floor.getPos(x, z);
		if(posOut == null) {
			inside = false;
		} else {
			inside = posOut.getFilled() != MapPositionOpt.OUTSIDE;
		}
		door = posWall.getFilled() == MapPositionOpt.DOOR;
		skip = posWall.isUsed();
		for(int i = map.getSouthWest().getPos().getPosZ() - building.getStyleBuild().getWallsize(); i <= map.getNorthWest().getPos().getPosZ(); i++) {
			checkOut = floor.getPos(x - building.getStyleBuild().getWallsize(), i);
			checkWall = floor.getPos(i, z);
			if(checkOut == null) {
				checkInside = false;
			} else {
				checkInside = checkOut.getFilled() != MapPositionOpt.OUTSIDE;
			}
			checkDoor = checkWall.getFilled() == MapPositionOpt.DOOR;
			checkSkip = checkWall.isUsed();
			if(skip == false) {
				if(checkSkip == true || (inside != checkInside && checkInside) || door != checkDoor) {
					Wall wall = new Wall(floor.getWall(inside), new Position(x, z, base), new Position(x - building.getStyleBuild().getWallsize() + 1, i - 1, base + high), inside, 3);
					if(door != checkDoor) {
						wall.setDoor(this.getMap().getStyleDoor());
						wall.getStyle().setCorner(null);
					}
					walls.add(wall);
					z = i;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				} else if(inside != checkInside) {
					walls.add(new Wall(floor.getWall(inside), new Position(x, z, base), new Position(x - building.getStyleBuild().getWallsize() + 1, i, base + high), inside, 3));
					z = i + 1;
					inside = checkInside;
					door = checkDoor;
					skip = checkSkip;
				}
				floor.getPos(x - building.getStyleBuild().getWallsize() +1, i).setUsed(true);
			}
		}
		
		Main.printDebug("Building walls by array " + walls);
		for(Wall wall : walls) {
			wall.build(building);
		}
		Main.printDebug("Finish to build walls");
	}
	
	public MapRoom getMap() {
		return map;
	}
	public void setMap(MapRoom map) {
		this.map = map;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public ArrayList<Wall> getWalls() {
		return walls;
	}
	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	public Floor getCeiling() {
		return ceiling;
	}
	public void setCeiling(Floor ceiling) {
		this.ceiling = ceiling;
	}
	public int getBase() {
		return base;
	}
	public void setBase(int base) {
		this.base = base;
	}
	public Roof getRoof() {
		return roof;
	}
	public void setRoof(Roof roof) {
		this.roof = roof;
	}
}
