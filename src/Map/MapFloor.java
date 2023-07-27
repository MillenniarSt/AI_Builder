package Map;

import java.util.ArrayList;
import java.util.HashMap;

import Building.BuildingPalace;
import Exception.AIObjectNotFoundException;
import Exception.IdNotFoundException;
import Exception.OverlapMapException;
import Main.Main;
import Model.Position;
import Model.WallStyle;

public class MapFloor {

	private ArrayList<MapRoom> rooms;
	private HashMap<int[], MapPosition> mapPos;
	private boolean underground;
	private final int floor;
	
	private WallStyle wallInside;
	private WallStyle wallOutside;
	
	public MapFloor(int floor, boolean underground) {
		this.rooms = new ArrayList<>();
		this.mapPos = new HashMap<>();
		this.underground = underground;
		this.floor = floor;
		this.wallInside = null;
		this.wallOutside = null;
	}
	
	public void buildFloor(BuildingPalace building) throws OverlapMapException, NumberFormatException, IdNotFoundException, AIObjectNotFoundException {
		Main.printDebug("Starting to build a basic floor from building " + building.getName());
		if(building.getRooms() >= 1) {
			System.out.println("Starting to build basic floor " + this.getFloor());
			
			Main.printDebug("Starting to build center room");
			CustomMapRoom custom = building.getRandomRoom(this.getFloor());
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
			Main.printDebug("Generate center room " + center);
			this.getRooms().add(center);
			setPosRoom(center, building.getWallsize());
			System.out.println("Create center room " + center.getCustom().getId());
			
			int count = 1;
			while(count < building.getRooms()) {
				Main.printDebug("Starting to build other room");
				MapRoom room = null;
				CustomMapRoom newCustom = building.getRandomRoom(this.getFloor());
				
				MapRoom parent = building.getRandomParentRoom(this.getRooms());
				MapCorner anchor = parent.getCorner((int) (Math.random() * 4));
				Main.printDebug("Select corner parent with id " + anchor.getId());
				int div = (int) (Math.random() * 2);
				if(div == 0)
					div = -1;
				int corner = anchor.getId() + div;
				int idWall = -1;
				int posX = 0;
				int posZ = 0;
				if(anchor.getId() == 0 && corner == -1) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() + building.getWallsize() + 1;
					idWall = 0;
					corner = 3;
				} else if(anchor.getId() == 0 && corner == 1) {
					posX = anchor.getPos().getPosX() - building.getWallsize() - 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 3;
				} else if(anchor.getId() == 1 && corner == 0) {
					posX = anchor.getPos().getPosX() + building.getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 1;
				} else if(anchor.getId() == 1 && corner == 2) {
					posX = anchor.getPos().getPosX() + building.getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 0;
				} else if(anchor.getId() == 2 && corner == 1) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() - building.getWallsize() - 1;
					idWall = 2;
				} else if(anchor.getId() == 2 && corner == 3) {
					posX = anchor.getPos().getPosX() + building.getWallsize() + 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 1;
				} else if(anchor.getId() == 3 && corner == 2) {
					posX = anchor.getPos().getPosX() - building.getWallsize() - 1;
					posZ = anchor.getPos().getPosZ();
					idWall = 3;
				} else if(anchor.getId() == 3 && corner == 4) {
					posX = anchor.getPos().getPosX();
					posZ = anchor.getPos().getPosZ() - building.getWallsize() - 1;
					idWall = 2;
					corner = 0;
				}
				Main.printDebug("Select corner child with id " + corner);
				Main.printDebug("Set room position in " + posX + " - " + posZ);
				
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
					int[] changeSize = checkRoomSize(posX, posZ, posX + sizeX - 1, posZ - sizeZ + 1, building.getWallAnchor());
					sizeX = sizeX + changeSize[0];
					sizeZ = sizeZ + changeSize[1];
					
					room = new MapRoom(posX, posZ, sizeX, sizeZ, newCustom);
					Main.printDebug("Generate new room " + room);
					this.getRooms().add(room);
					setPosDoor(parent, room, idWall, building.getWallsize());
					parent.setDoor(parent.getDoor() + 1);
					System.out.println("Create room " + room.getCustom().getId());
				} catch(OverlapMapException exc) {
					room = null;
				}
				
				if(room != null) {
					setPosRoom(room, building.getWallsize());
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
		Main.printDebug("Starting to build floor from floor parent " + parent.getFloor() + ", downstairs " + downstairs.getFloor() + " building " + building.getName());
		System.out.println("Starting to build floor " + this.getFloor() + " from parent floor" + parent.getFloor());
		
		this.mapPos = (HashMap<int[], MapPosition>) parent.mapPos.clone();
		this.rooms = (ArrayList<MapRoom>) parent.rooms.clone();
		for(MapRoom room : this.rooms) {
			room.setCustom(building.getRandomRoom(this.getFloor(), room.getLenghtX(), room.getLenghtZ()));
			System.out.println("Set room " + room.getCustom().getId());
		}
		
		Main.printDebug("Starting to place " + (int) (downstairs.getRooms().size() / building.getStairsWeigh()) + " stairs");
		ArrayList<MapRoom> posStairs = new ArrayList<>();
		for(MapRoom room : downstairs.rooms) {
			if(room.getCustom().isStairs())
				posStairs.add(room);
		}
		int stairs = 0;
		if(posStairs.isEmpty())
			for(MapRoom room : downstairs.rooms) {
				posStairs.add(room);
				stairs = 1;
			}
		if(stairs != 1)
			stairs = (int) (downstairs.getRooms().size() / building.getStairsWeigh());
		Main.printDebug("Definitely number stairs set to " + stairs);
		if(stairs > posStairs.size())
			stairs = posStairs.size();
		for(int i = 1; i <= stairs; i++) {
			MapRoom room = posStairs.get((int) (Math.random() * posStairs.size()));
			room.setStairs(true);
			Main.printDebug("Place a stairs in the room " + room.getCustom().getId());
			posStairs.remove(room);
		}
		System.out.println("Place " + stairs + " stairs on floor " + downstairs.getFloor());
		System.out.println("Finish to build floor " + this.getFloor() + " with " + this.getRooms().size() + " rooms");
	}
	
	public MapPosition getPos(int x, int z) {
		int[] pos = {x, z};
		return mapPos.get(pos);
	}
	public void addPos(MapPosition pos) {
		int[] arg = {pos.getPosX(), pos.getPosZ()};
		mapPos.put(arg, pos);
	}
	public void setPosRoom(MapRoom room, int wallSize) throws OverlapMapException {
		setPosRoom(room.getCorner(0).getPos().getPosX(), room.getCorner(0).getPos().getPosZ(),
				room.getCorner(3).getPos().getPosX(), room.getCorner(3).getPos().getPosZ(), wallSize);
	}
	public void setPosRoom(int x1, int z1, int x2, int z2, int wallSize) throws OverlapMapException {
		Main.printDebug("Setting area room from " + x1 + " - " + z1 + " to " + x2 + " - " + z2 + ", with wall size " + wallSize);
		for(int i = x1; i <= x2; i++) {
			for(int j = z1; j <= z2; j++) {
				MapPosition pos = getPos(i, j);
				if(pos == null) {
					addPos(new MapPosition(i, j, MapPositionOpt.ROOM));
				} else if(pos.getFilled().equals(MapPositionOpt.NONE)){
					pos.setFilled(MapPositionOpt.ROOM);
				} else {
					throw new OverlapMapException(pos.getFilled(), MapPositionOpt.ROOM);
				}
			}
		}
		Main.printDebug("Set room area succefly");
		for(int i = x1 - wallSize; i <= x2 + wallSize; i++) {
			for(int j = z1 + wallSize; j <= z2 - wallSize; j++) {
				MapPosition pos = getPos(i, j);
				if(pos == null) {
					addPos(new MapPosition(i, j, MapPositionOpt.WALL));
				} else if(pos.getFilled().equals(MapPositionOpt.NONE) && !pos.getFilled().equals(MapPositionOpt.DOOR)){
					pos.setFilled(MapPositionOpt.WALL);
				} else if(pos.getFilled().equals(MapPositionOpt.ROOM)) {
					throw new OverlapMapException(pos.getFilled(), MapPositionOpt.WALL);
				}
			}
		}
		Main.printDebug("Set wall area succefly");
	}
	
	public int[] checkRoomSize(int x1, int z1, int x2, int z2, int anchor) throws OverlapMapException {
		Main.printDebug("Checking room size: area from " + x1 + " - " + z1 + " to " + x2 + " - " + z2 + ", with anchor value " + anchor);
		int[] output = {0, 0};
		for(int i = x1; i <= x2; i++) {
			z:
			for(int j = z1; j <= z2; j++) {
				MapPosition pos = getPos(i, j);
				if(pos != null) {
					if(!pos.getFilled().equals(MapPositionOpt.NONE)) {
						output[0]--;
						break z;
					}
				}
			}
		}
		Main.printDebug("Firstly X area filled = " + output[0]);
		if(output[0] < anchor * -1) {
			throw new OverlapMapException(x1, z1, x2, z2, Math.abs(output[0]), anchor);
		} else if(output[0] == 0 && anchor > 0) {
			for(int i = x2 + 1; i <= x2 + anchor; i++) {
				z:
				for(int j = z1; j <= z2; j++) {
					MapPosition pos = getPos(i, j);
					if(pos != null) {
						if(!pos.getFilled().equals(MapPositionOpt.NONE)) {
							output[0]++;
							break z;
						}
					}
				}
			}
		}
		Main.printDebug("X area filled = " + output[0]);
		
		for(int j = z1; j <= z2; j++) {
			x:
			for(int i = x1; i <= x2; i++) {
				MapPosition pos = getPos(i, j);
				if(pos != null) {
					if(!pos.getFilled().equals(MapPositionOpt.NONE)) {
						output[1]--;
						break x;
					}
				}
			}
		}
		Main.printDebug("Firstly Z area filled = " + output[1]);
		if(output[1] < anchor * -1) {
			throw new OverlapMapException(x1, z1, x2, z2, Math.abs(output[1]), anchor);
		} else if(output[0] == 0 && anchor > 0) {
			for(int j = z1; j <= z2; j++) {
				x:
				for(int i = x2 + 1; i <= x2 + anchor; i++) {
					MapPosition pos = getPos(i, j);
					if(pos != null) {
						if(!pos.getFilled().equals(MapPositionOpt.NONE)) {
							output[1]++;
							break x;
						}
					}
				}
			}
		}
		Main.printDebug("Z area filled = " + output[1]);
		
		return output;
	}
	
	public void setPosDoor(MapRoom parent, MapRoom child, int idWall, int wallSize) throws IdNotFoundException, NumberFormatException {
		Main.printDebug("Setting door position from room " + parent + " to " + child + " with id wall " + idWall + " and wall size " + wallSize);
		if(idWall < 0 || idWall > 3)
			throw new IdNotFoundException(idWall, "the id wall must be from 0 to 3");
		if(wallSize <= 0)
			throw new NumberFormatException("The wall size must be 1+");
		
		int sizeX;
		int distance;
		if(parent.getStyleDoor().getWeigh() >= child.getStyleDoor().getWeigh()) {
			sizeX = parent.getStyleDoor().getModel().getSizeX();
			distance = parent.getStyleDoor().getDistance();
		} else {
			sizeX = child.getStyleDoor().getModel().getSizeX();
			distance = child.getStyleDoor().getDistance();
		}
		Main.printDebug("Door size: X/Z = " + sizeX);
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
		Main.printDebug("Door origin set: x = " + origin.getPosX() + " z = " + origin.getPosZ());
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
				pos.setFilled(MapPositionOpt.DOOR);
			}
		}
		Main.printDebug("Door position set succefly");
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
	public WallStyle getWallInside() {
		return wallInside;
	}
	public void setWallInside(WallStyle wallInside) {
		this.wallInside = wallInside;
	}
	public WallStyle getWallOutside() {
		return wallOutside;
	}
	public void setWallOutside(WallStyle wallOutside) {
		this.wallOutside = wallOutside;
	}
	public WallStyle getWall(boolean inside) {
		if(inside)
			return wallInside;
		else
			return wallOutside;
	}
}
