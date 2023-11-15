package io.github.millenniarst.ai_builder.brain.map.house;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.millenniarst.ai_builder.brain.map.MapArea;
import io.github.millenniarst.ai_builder.brain.map.MapPosition;
import io.github.millenniarst.ai_builder.brain.building.BuildingPalace;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.exception.IdNotFoundException;
import io.github.millenniarst.ai_builder.exception.OverlapMapException;
import io.github.millenniarst.ai_builder.exception.RoomNotFoundException;
import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.config.component.MapRoomConfig;
import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.config.component.WallConfig;

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

public class MapFloor extends MapArea {

	private ArrayList<MapRoom<?>> rooms;
	private boolean underground;
	private final int floor;
	
	private WallConfig wallInside;
	private WallConfig wallOutside;
	
	public MapFloor(int floor, boolean underground) {
		this.rooms = new ArrayList<>();
		this.underground = underground;
		this.floor = floor;
		this.wallInside = null;
		this.wallOutside = null;
	}
	
	public void buildFloor(BuildingPalace building) throws OverlapMapException, NumberFormatException, IdNotFoundException, AIObjectNotFoundException {
		AI_Builder.printDebug("Starting to build a basic floor from building " + building.getStyleBuild().getName());
		if(building.getRooms() >= 1) {
			System.out.println("Starting to build basic floor " + this.getFloor());
			
			AI_Builder.printDebug("Starting to build center room");
			MapRoomConfig custom = building.getStyleBuild().getRandomRoom(this.getFloor());
			int sizeXr;
			int sizeZr;
			if(((int) (Math.random() * 2)) == 0) {
				sizeXr = ((int) (Math.random() * (custom.getMaxX() - custom.getMinX() + 1))) + custom.getMinX();
				sizeZr = ((int) (Math.random() * (custom.getMaxZ() - custom.getMinZ() + 1))) + custom.getMinZ();
			} else {
				sizeZr = ((int) (Math.random() * (custom.getMaxX() - custom.getMinX() + 1))) + custom.getMinX();
				sizeXr = ((int) (Math.random() * (custom.getMaxZ() - custom.getMinZ() + 1))) + custom.getMinZ();
			}
			MapRoom center = new MapRoom(0, 0, sizeXr, sizeZr, custom);
			AI_Builder.printDebug("Generate center room " + center);
			this.getRooms().add(center);
			setPosRoom(center, building.getStyleBuild().getWallsize());
			System.out.println("Create center room " + center.getConfig().getId());
			
			int count = 1;
			while(count < building.getRooms()) {
				AI_Builder.printDebug("Starting to build other room");
				MapRoom room = null;
				MapRoomConfig newCustom = building.getStyleBuild().getRandomRoom(this.getFloor());
				
				MapRoom parent = getRandomParentRoom(this.getRooms());
				MapCorner anchor = parent.getCorner((int) (Math.random() * 4));
				AI_Builder.printDebug("Select corner parent with id " + anchor.getId());
				int div = (int) (Math.random() * 2);
				if(div == 0)
					div = -1;
				int corner = anchor.getId() + div;
				int idWall = -1;
				int posX = 0;
				int posZ = 0;
				if(anchor.getId() == 0 && corner == -1) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() + building.getStyleBuild().getWallsize() + 1;
					idWall = 0;
					corner = 3;
				} else if(anchor.getId() == 0 && corner == 1) {
					posX = anchor.getPos().getPosX() - building.getStyleBuild().getWallsize() - 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 3;
				} else if(anchor.getId() == 1 && corner == 0) {
					posX = anchor.getPos().getPosX() + building.getStyleBuild().getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 1;
				} else if(anchor.getId() == 1 && corner == 2) {
					posX = anchor.getPos().getPosX() + building.getStyleBuild().getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 0;
				} else if(anchor.getId() == 2 && corner == 1) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() - building.getStyleBuild().getWallsize() - 1;
					idWall = 2;
				} else if(anchor.getId() == 2 && corner == 3) {
					posX = anchor.getPos().getPosX() + building.getStyleBuild().getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 1;
				} else if(anchor.getId() == 3 && corner == 2) {
					posX = anchor.getPos().getPosX() - building.getStyleBuild().getWallsize() - 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 3;
				} else if(anchor.getId() == 3 && corner == 4) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() - building.getStyleBuild().getWallsize() - 1;
					idWall = 2;
					corner = 0;
				}
				AI_Builder.printDebug("Select corner child with id " + corner);
				AI_Builder.printDebug("Set room position in " + posX + " - " + posZ);
				
				int sizeX;
				int sizeZ;
				if(((int) Math.random() * 2) == 0) {
					sizeX = ((int) (Math.random() * (newCustom.getMaxX() - newCustom.getMinX() + 1))) + newCustom.getMinX();
					sizeZ = ((int) (Math.random() * (newCustom.getMaxZ() - newCustom.getMinZ() + 1))) + newCustom.getMinZ();
				} else {
					sizeZ = ((int) (Math.random() * (newCustom.getMaxX() - newCustom.getMinX() + 1))) + newCustom.getMinX();
					sizeX = ((int) (Math.random() * (newCustom.getMaxZ() - newCustom.getMinZ() + 1))) + newCustom.getMinZ();
				}
				
				try {
					int[] changeSize = checkRoomSize(posX, posZ, posX + sizeX - 1, posZ - sizeZ + 1, building.getStyleBuild().getWallAnchor());
					sizeX = sizeX + changeSize[0];
					sizeZ = sizeZ + changeSize[1];
					
					room = new MapRoom(posX, posZ, sizeX, sizeZ, newCustom);
					AI_Builder.printDebug("Generate new room " + room);
					this.getRooms().add(room);
					setPosDoor(parent, room, idWall, building.getStyleBuild().getWallsize());
					parent.setDoor(parent.getDoor() + 1);
					System.out.println("Create room " + room.getConfig().getId());
				} catch(OverlapMapException exc) {
					room = null;
				}
				
				if(room != null) {
					setPosRoom(room, building.getStyleBuild().getWallsize());
					count++;
				}
			}
			System.out.println("Finish to build basic floor " + this.getFloor() + " with " + this.getRooms().size() + " rooms");
		} else {
			throw new NumberFormatException("The number of rooms must be 1+");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void buildFloor(MapFloor parent, MapFloor downstairs, BuildingPalace building) throws AIObjectNotFoundException {
		AI_Builder.printDebug("Starting to build floor from floor parent " + parent.getFloor() + ", downstairs " + downstairs.getFloor() + " building " + building.getStyleBuild().getName());
		System.out.println("Starting to build floor " + this.getFloor() + " from parent floor" + parent.getFloor());
		
		this.mapPos = (HashMap<int[], MapPosition>) parent.mapPos.clone();
		this.rooms = (ArrayList<MapRoom>) parent.rooms.clone();
		for(MapRoom room : this.rooms) {
			room.setConfig(building.getStyleBuild().getRandomRoom(this.getFloor(), room.getLenghtX(), room.getLenghtZ()));
			System.out.println("Set room " + room.getConfig().getId());
		}
		
		AI_Builder.printDebug("Starting to place " + (int) (downstairs.getRooms().size() / building.getStyleBuild().getStairsWeigh()) + " stairs");
		ArrayList<MapRoom> posStairs = new ArrayList<>();
		for(MapRoom room : downstairs.rooms) {
			if(room.getConfig().isStairs())
				posStairs.add(room);
		}
		int stairs = 0;
		if(posStairs.isEmpty())
			for(MapRoom room : downstairs.rooms) {
				posStairs.add(room);
				stairs = 1;
			}
		if(stairs != 1)
			stairs = (int) (downstairs.getRooms().size() / building.getStyleBuild().getStairsWeigh());
		AI_Builder.printDebug("Definitely number stairs set to " + stairs);
		if(stairs > posStairs.size())
			stairs = posStairs.size();
		for(int i = 1; i <= stairs; i++) {
			MapRoom room = posStairs.get((int) (Math.random() * posStairs.size()));
			room.setStairs(true);
			AI_Builder.printDebug("Place a stairs in the room " + room.getConfig().getId());
			posStairs.remove(room);
		}
		System.out.println("Place " + stairs + " stairs on floor " + downstairs.getFloor());
		System.out.println("Finish to build floor " + this.getFloor() + " with " + this.getRooms().size() + " rooms");
	}

	public void setPosRoom(MapRoom room, int wallSize) throws OverlapMapException {
		setPosRoom(room.getCorner(0).getPos().getPosX(), room.getCorner(0).getPos().getPosZ(),
				room.getCorner(3).getPos().getPosX(), room.getCorner(3).getPos().getPosZ(), wallSize);
	}

	public void setPosRoom(int x1, int z1, int x2, int z2, int wallSize) throws OverlapMapException {
		AI_Builder.printDebug("Setting area room from " + x1 + " - " + z1 + " to " + x2 + " - " + z2 + ", with wall size " + wallSize);
		for(int i = x1; i <= x2; i++) {
			for(int j = z1; j <= z2; j++) {
				MapPosition pos = getPos(i, j);
				if(pos == null) {
					addPos(new MapPosition(i, j, MapPosition.MapPositionOpt.ROOM));
				} else if(pos.getFilled().equals(MapPosition.MapPositionOpt.NONE)){
					pos.setFilled(MapPosition.MapPositionOpt.ROOM);
				} else {
					throw new OverlapMapException(pos.getFilled(), MapPosition.MapPositionOpt.ROOM);
				}
			}
		}
		AI_Builder.printDebug("Set room area succefly");
		for(int i = x1 - wallSize; i <= x2 + wallSize; i++) {
			for(int j = z1 + wallSize; j <= z2 - wallSize; j++) {
				MapPosition pos = getPos(i, j);
				if(pos == null) {
					addPos(new MapPosition(i, j, MapPosition.MapPositionOpt.WALL));
				} else if(pos.getFilled().equals(MapPosition.MapPositionOpt.NONE) && !pos.getFilled().equals(MapPosition.MapPositionOpt.DOOR)){
					pos.setFilled(MapPosition.MapPositionOpt.WALL);
				} else if(pos.getFilled().equals(MapPosition.MapPositionOpt.ROOM)) {
					throw new OverlapMapException(pos.getFilled(), MapPosition.MapPositionOpt.WALL);
				}
			}
		}
		AI_Builder.printDebug("Set wall area succefly");
	}
	
	public int[] checkRoomSize(int x1, int z1, int x2, int z2, int anchor) throws OverlapMapException {
		AI_Builder.printDebug("Checking room size: area from " + x1 + " - " + z1 + " to " + x2 + " - " + z2 + ", with anchor value " + anchor);
		int[] output = {0, 0};
		for(int i = x1; i <= x2; i++) {
			z:
			for(int j = z1; j <= z2; j++) {
				MapPosition pos = getPos(i, j);
				if(pos != null) {
					if(!pos.getFilled().equals(MapPosition.MapPositionOpt.NONE)) {
						output[0]--;
						break z;
					}
				}
			}
		}
		AI_Builder.printDebug("Firstly X area filled = " + output[0]);
		if(output[0] < anchor * -1) {
			throw new OverlapMapException(x1, z1, x2, z2, Math.abs(output[0]), anchor);
		} else if(output[0] == 0 && anchor > 0) {
			for(int i = x2 + 1; i <= x2 + anchor; i++) {
				z:
				for(int j = z1; j <= z2; j++) {
					MapPosition pos = getPos(i, j);
					if(pos != null) {
						if(!pos.getFilled().equals(MapPosition.MapPositionOpt.NONE)) {
							output[0]++;
							break z;
						}
					}
				}
			}
		}
		AI_Builder.printDebug("X area filled = " + output[0]);
		
		for(int j = z1; j <= z2; j++) {
			x:
			for(int i = x1; i <= x2; i++) {
				MapPosition pos = getPos(i, j);
				if(pos != null) {
					if(!pos.getFilled().equals(MapPosition.MapPositionOpt.NONE)) {
						output[1]--;
						break x;
					}
				}
			}
		}
		AI_Builder.printDebug("Firstly Z area filled = " + output[1]);
		if(output[1] < anchor * -1) {
			throw new OverlapMapException(x1, z1, x2, z2, Math.abs(output[1]), anchor);
		} else if(output[0] == 0 && anchor > 0) {
			for(int j = z1; j <= z2; j++) {
				x:
				for(int i = x2 + 1; i <= x2 + anchor; i++) {
					MapPosition pos = getPos(i, j);
					if(pos != null) {
						if(!pos.getFilled().equals(MapPosition.MapPositionOpt.NONE)) {
							output[1]++;
							break x;
						}
					}
				}
			}
		}
		AI_Builder.printDebug("Z area filled = " + output[1]);
		
		return output;
	}
	
	public void setPosDoor(MapRoom parent, MapRoom child, int idWall, int wallSize) throws IdNotFoundException, NumberFormatException {
		AI_Builder.printDebug("Setting door position from room " + parent + " to " + child + " with id wall " + idWall + " and wall size " + wallSize);
		if(idWall < 0 || idWall > 3)
			throw new IdNotFoundException(idWall, "the id wall must be from 0 to 3");
		if(wallSize <= 0)
			throw new NumberFormatException("The wall size must be 1+");
		
		int sizeX;
		int distance;
		if(parent.getStyleDoor().getWeigh() >= child.getStyleDoor().getWeigh()) {
			sizeX = parent.getStyleDoor().getModel().getBase().getSizeX();
			distance = parent.getStyleDoor().getDistance();
		} else {
			sizeX = child.getStyleDoor().getModel().getBase().getSizeX();
			distance = child.getStyleDoor().getDistance();
		}
		AI_Builder.printDebug("Door size: X/Z = " + sizeX);
		ArrayList<MapPosition> posDoor = new ArrayList<>();
		if(idWall == 0) {
			for(int i = parent.getNorthWest().getPos().getPosX() + distance; i <= parent.getNorthEast().getPos().getPosX() - sizeX - distance +1; i++) {
				MapPosition pos = getPos(i, parent.getNorthWest().getPos().getPosZ() + wallSize);
				if(pos != null) {
					if(child.isContains(new MapPosition(pos.getPosX(), pos.getPosZ() + 1))) {
						posDoor.add(pos);
					}
				}
			}
		} else if(idWall == 1) {
			for(int i = parent.getNorthEast().getPos().getPosZ() - distance; i >= parent.getSouthEast().getPos().getPosZ() + sizeX + distance -1; i--) {
				MapPosition pos = getPos(parent.getNorthWest().getPos().getPosX() + wallSize, i);
				if(pos != null) {
					if(child.isContains(new MapPosition(pos.getPosX() + 1, pos.getPosZ()))) {
						posDoor.add(pos);
					}
				}
			}
		} else if(idWall == 2) {
			for(int i = parent.getSouthEast().getPos().getPosX() - distance; i >= parent.getSouthWest().getPos().getPosX() + sizeX + distance -1; i--) {
				MapPosition pos = getPos(i, parent.getNorthWest().getPos().getPosZ() - wallSize);
				if(pos != null) {
					if(child.isContains(new MapPosition(pos.getPosX(), pos.getPosZ() - 1))) {
						posDoor.add(pos);
					}
				}
			}
		} else if(idWall == 3) {
			for(int i = parent.getSouthWest().getPos().getPosZ() + distance; i <= parent.getNorthWest().getPos().getPosZ() - sizeX - distance +1; i++) {
				MapPosition pos = getPos(parent.getSouthWest().getPos().getPosX() - wallSize, i);
				if(pos != null) {
					if(child.isContains(new MapPosition(pos.getPosX() - 1, pos.getPosZ()))) {
						posDoor.add(pos);
					}
				}
			}
		}
		
		MapPosition origin = posDoor.get((int) (Math.random() * posDoor.size()));
		parent.getStyleDoor().setOrigin(new Position(origin.getPosX(), origin.getPosZ(), -1));
		AI_Builder.printDebug("Door origin set: x = " + origin.getPosX() + " z = " + origin.getPosZ());
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < wallSize; j++) {
				MapPosition pos = null;
				if(idWall == 0) {
					pos = getPos(origin.getPosX() + i, origin.getPosZ() - j);
					if(pos == null) {
						pos = new MapPosition(origin.getPosX() + i, origin.getPosZ() - j);
						addPos(pos);
					}
				} else if(idWall == 1) {
					pos = getPos(origin.getPosX() - j, origin.getPosZ() - i);
					if(pos == null) {
						pos = getPos(origin.getPosX() - j, origin.getPosZ() - i);
						addPos(pos);
					}
				} else if(idWall == 2) {
					pos = getPos(origin.getPosX() - i, origin.getPosZ() + j);
					if(pos == null) {
						pos = new MapPosition(origin.getPosX() - i, origin.getPosZ() + j);
						addPos(pos);
					}
				} else if(idWall == 3) {
					pos = getPos(origin.getPosX() + j, origin.getPosZ() + i);
					if(pos == null) {
						pos = getPos(origin.getPosX() + j, origin.getPosZ() + i);
						addPos(pos);
					}
				}
				pos.setFilled(MapPosition.MapPositionOpt.DOOR);
			}
		}
		AI_Builder.printDebug("Door position set succefly");
	}

	public static MapRoom getRandomParentRoom(ArrayList<MapRoom> rooms) throws RoomNotFoundException {
		AI_Builder.printDebug("Searching a parent room by array: " + rooms);
		if(rooms.isEmpty()) {
			throw new ArrayIndexOutOfBoundsException("The arraylist rooms must not be empity to search a parent");
		} else {
			ArrayList<MapRoom> posRoom = new ArrayList<>();
			for(MapRoom room : rooms) {
				if(room.getConfig().getPrefDoor() - room.getDoor() > 0) {
					posRoom.add(room);
				}
			}
			AI_Builder.printDebug("Getting a random parent room by array: " + posRoom);
			if(posRoom.isEmpty()) {
				MapRoom selcted = rooms.get((int) (Math.random() * rooms.size()));
				AI_Builder.printDebug("Return " + selcted + " parent room");
				return selcted;
			} else {
				MapRoom selcted = posRoom.get((int) (Math.random() * posRoom.size()));
				AI_Builder.printDebug("Return " + selcted + " parent room");
				return selcted;
			}
		}
	}
	
	public ArrayList<MapRoom> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<MapRoom> rooms) {
		this.rooms = rooms;
	}
	public int getFloor() {
		return floor;
	}
	public boolean isUnderground() {
		return underground;
	}
	public void setUnderground(boolean underground) {
		this.underground = underground;
	}
	public WallConfig getWallInside() {
		return wallInside;
	}
	public void setWallInside(WallConfig wallInside) {
		this.wallInside = wallInside;
	}
	public WallConfig getWallOutside() {
		return wallOutside;
	}
	public void setWallOutside(WallConfig wallOutside) {
		this.wallOutside = wallOutside;
	}
	public WallConfig getWall(boolean inside) {
		if(inside)
			return wallInside;
		else
			return wallOutside;
	}
}