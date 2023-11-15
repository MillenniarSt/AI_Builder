package io.github.millenniarst.ai_builder.brain.area.world.house;

import java.util.ArrayList;

import io.github.millenniarst.ai_builder.brain.area.world.Floor;
import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.brain.area.PrismArea;
import io.github.millenniarst.ai_builder.brain.area.world.Wall;
import io.github.millenniarst.ai_builder.brain.map.MapArea;
import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;
import io.github.millenniarst.ai_builder.brain.building.Building;
import io.github.millenniarst.ai_builder.brain.building.house.House;
import io.github.millenniarst.ai_builder.brain.building.house.SmallHouse;
import io.github.millenniarst.ai_builder.config.component.DoorConfig;
import io.github.millenniarst.ai_builder.config.component.MapRoomConfig;
import io.github.millenniarst.ai_builder.config.component.WallConfig;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.brain.map.MapPosition;
import io.github.millenniarst.ai_builder.brain.map.house.MapRoom;

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

public class Room<M extends MapArea> extends PrismArea<M> {

	private final MapRoomConfig config;
	private final DoorConfig doorConfig;
	private int base;
	private int high;
	
	private ArrayList<Wall> walls;
	private Floor<M> floor;
	private Floor<M> ceiling;
	private Roof roof;
	
	public Room(MapRoom<M> map, int base, int high) {
		super(map.getArea());
		this.config = map.getConfig();
		this.doorConfig = map.getStyleDoor();
		this.base = base;
		this.high = high;
		this.walls = new ArrayList<>();
	}
	
	public void build(House<?> house) throws Exception {
		AI_Builder.print("Building room " + config.getId());

		generateFloor(house);

		if(house instanceof SmallHouse smallHouse)
			generateWall(smallHouse, getRoot(), null, smallHouse.getStyleBuild().getRandomWall(0, smallHouse.getStyleBuild().getFloorHigh() ,false));

		AI_Builder.print("Built room " + config.getId());
	}
	
	public void generateFloor(Building<?> building) throws AIObjectNotFoundException {
		AI_Builder.printDebug("Generating room's flooring and ceilings");

		this.floor = new Floor<M>(config.getRandomFlooring(false, true), getRoot(), base, base + high -1, true, false);
		this.ceiling = new Floor<M>(config.getRandomFlooring(true, true), getRoot(), base, base + high -1, true, true);
		
		AI_Builder.printDebug("Building room's flooring and ceilings");
		floor.build(building);
		ceiling.build(building);
		AI_Builder.printDebug("Finish to build room's flooring and ceilings");
	}
	
	public void generateWall(House<?> house, MapArea map, WallConfig wallInside, WallConfig wallOutside) throws AIObjectNotFoundException {
		AI_Builder.printDebug("Generating room's wall");
		boolean inside;
		boolean door;
		boolean skip;
		int x;
		int z;
		
		boolean checkInside;
		boolean checkDoor;
		boolean checkSkip;
		MapPosition checkOut;
		MapPosition checkWall;

		if(getRoot() instanceof MapRectangleArea rectangleArea) {
			AI_Builder.printDebug("Generating north walls");

			x = rectangleArea.getMinX();
			z = rectangleArea.getMaxZ() + 1;
			MapPosition posOut = map.getPos(x, z + house.getStyleBuild().getWallsize());
			if (posOut == null) {
				inside = false;
			} else {
				inside = posOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
			}
			door = map.getPos(x, z).getFilled() == MapPosition.MapPositionOpt.DOOR;
			skip = house.isWallPosUsed(x, z);
			for (int i = x +1; i <= rectangleArea.getMaxX() + house.getStyleBuild().getWallsize(); i++) {
				checkOut = map.getPos(i, z + house.getStyleBuild().getWallsize());
				checkWall = map.getPos(i, z);
				if (checkOut == null) {
					checkInside = false;
				} else {
					checkInside = checkOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
				}
				checkDoor = checkWall.getFilled() == MapPosition.MapPositionOpt.DOOR;
				checkSkip = house.isWallPosUsed(x, z);
				if (!skip) {
					if (checkSkip || (inside != checkInside && checkInside) || door != checkDoor) {
						Wall wall = new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(i - 1, z + house.getStyleBuild().getWallsize() - 1, base + high), inside, 0);
						if (door != checkDoor) {
							wall.setDoor(doorConfig);
							wall.setCorner(false);
						}
						walls.add(wall);
						x = i;
						inside = checkInside;
						door = checkDoor;
						skip = checkSkip;
					} else if (inside != checkInside) {
						walls.add(new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(i, z + house.getStyleBuild().getWallsize() - 1, base + high), inside, 0));
						x = i + 1;
						inside = checkInside;
					}
					house.useWallPos(i, z, z + house.getStyleBuild().getWallsize() -1);
				}
			}

			AI_Builder.printDebug("Generating east walls");
			x = rectangleArea.getMaxX() + 1;
			z = rectangleArea.getMaxZ();
			posOut = map.getPos(x + house.getStyleBuild().getWallsize(), z);
			if (posOut == null) {
				inside = false;
			} else {
				inside = posOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
			}
			door = map.getPos(x, z).getFilled() == MapPosition.MapPositionOpt.DOOR;
			skip = house.isWallPosUsed(x, z);
			for (int i = z -1; i >= rectangleArea.getMinZ() - house.getStyleBuild().getWallsize(); i--) {
				checkOut = map.getPos(x + house.getStyleBuild().getWallsize(), i);
				checkWall = map.getPos(i, z);
				if (checkOut == null) {
					checkInside = false;
				} else {
					checkInside = checkOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
				}
				checkDoor = checkWall.getFilled() == MapPosition.MapPositionOpt.DOOR;
				checkSkip = house.isWallPosUsed(x, i);
				if (!skip) {
					if (checkSkip || (inside != checkInside && checkInside) || door != checkDoor) {
						Wall wall = new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(x + house.getStyleBuild().getWallsize() - 1, i + 1, base + high), inside, 1);
						if (door != checkDoor) {
							wall.setDoor(doorConfig);
							wall.setCorner(false);
						}
						walls.add(wall);
						z = i;
						inside = checkInside;
						door = checkDoor;
						skip = checkSkip;
					} else if (inside != checkInside) {
						walls.add(new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(x + house.getStyleBuild().getWallsize() - 1, i, base + high), inside, 1));
						z = i - 1;
						inside = checkInside;
					}
					house.useWallPos(i, z, z + house.getStyleBuild().getWallsize() -1);
				}
			}

			AI_Builder.printDebug("Generating south walls");
			x = rectangleArea.getMaxX();
			z = rectangleArea.getMinZ() -1;
			posOut = map.getPos(x, z - house.getStyleBuild().getWallsize());
			if (posOut == null) {
				inside = false;
			} else {
				inside = posOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
			}
			door = map.getPos(x, z).getFilled() == MapPosition.MapPositionOpt.DOOR;
			skip = house.isWallPosUsed(x, z);
			for (int i = x -1; i >= rectangleArea.getMinX() - house.getStyleBuild().getWallsize(); i--) {
				checkOut = map.getPos(i, z - house.getStyleBuild().getWallsize());
				checkWall = map.getPos(i, z);
				if (checkOut == null) {
					checkInside = false;
				} else {
					checkInside = checkOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
				}
				checkDoor = checkWall.getFilled() == MapPosition.MapPositionOpt.DOOR;
				checkSkip = house.isWallPosUsed(i, z);
				if (!skip) {
					if (checkSkip || (inside != checkInside && checkInside) || door != checkDoor) {
						Wall wall = new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(i + 1, z - house.getStyleBuild().getWallsize() + 1, base + high), inside, 2);
						if (door != checkDoor) {
							wall.setDoor(doorConfig);
							wall.setCorner(false);
						}
						walls.add(wall);
						x = i;
						inside = checkInside;
						door = checkDoor;
						skip = checkSkip;
					} else if (inside != checkInside) {
						walls.add(new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(i, z - house.getStyleBuild().getWallsize() + 1, base + high), inside, 2));
						x = i - 1;
						inside = checkInside;
					}
					house.useWallPos(i, z, z + house.getStyleBuild().getWallsize() -1);
				}
			}

			AI_Builder.printDebug("Generating west walls");
			x = rectangleArea.getMinX() -1;
			z = rectangleArea.getMinZ();
			posOut = map.getPos(x - house.getStyleBuild().getWallsize(), z);
			if (posOut == null) {
				inside = false;
			} else {
				inside = posOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
			}
			door = map.getPos(x, z).getFilled() == MapPosition.MapPositionOpt.DOOR;
			skip = house.isWallPosUsed(x, z);
			for (int i = z +1; i <= rectangleArea.getMinX() + house.getStyleBuild().getWallsize(); i++) {
				checkOut = map.getPos(x - house.getStyleBuild().getWallsize(), i);
				checkWall = map.getPos(i, z);
				if (checkOut == null) {
					checkInside = false;
				} else {
					checkInside = checkOut.getFilled() != MapPosition.MapPositionOpt.OUTSIDE;
				}
				checkDoor = checkWall.getFilled() == MapPosition.MapPositionOpt.DOOR;
				checkSkip = house.isWallPosUsed(x, i);
				if (!skip) {
					if (checkSkip || (inside != checkInside && checkInside) || door != checkDoor) {
						Wall wall = new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(x - house.getStyleBuild().getWallsize() + 1, i - 1, base + high), inside, 3);
						if (door != checkDoor) {
							wall.setDoor(doorConfig);
							wall.setCorner(false);
						}
						walls.add(wall);
						z = i;
						inside = checkInside;
						door = checkDoor;
						skip = checkSkip;
					} else if (inside != checkInside) {
						walls.add(new Wall(inside ? wallInside : wallOutside, new Position(x, z, base), new Position(x - house.getStyleBuild().getWallsize() + 1, i, base + high), inside, 3));
						z = i + 1;
						inside = checkInside;
					}
					house.useWallPos(i, z, z + house.getStyleBuild().getWallsize() -1);
				}
			}

			AI_Builder.printDebug("Building walls by array " + walls);
			for (Wall wall : walls) {
				wall.build(house);
			}
			AI_Builder.printDebug("Finish to build walls");
		}
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
	public Floor<M> getFloor() {
		return floor;
	}
	public void setFloor(Floor<M> floor) {
		this.floor = floor;
	}
	public Floor<M> getCeiling() {
		return ceiling;
	}
	public void setCeiling(Floor<M> ceiling) {
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

	public DoorConfig getDoorConfig() {
		return doorConfig;
	}
}
