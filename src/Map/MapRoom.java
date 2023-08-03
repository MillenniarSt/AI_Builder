package Map;

import Exception.AIObjectNotFoundException;
import Model.Door;
import Model.RoofStyle;

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

public class MapRoom {

	private MapCorner northWest;
	private MapCorner northEast;
	private MapCorner southEast;
	private MapCorner southWest;
	
	private int lenghtX;
	private int lenghtZ;
	
	private Door styleDoor;
	private int door;
	private boolean stairs;
	
	private CustomMapRoom custom;
	
	protected MapRoom(MapCorner northWest, MapCorner northEast, MapCorner southWest, MapCorner southEast, 
			CustomMapRoom custom) throws AIObjectNotFoundException {
		this.custom = custom;
		
		this.northWest = northWest;
		this.northEast = northEast;
		this.southWest = southWest;
		this.southEast = southEast;
		
		this.door = 0;
		this.stairs = false;
		this.styleDoor = custom.getDoor().getRandom();
		
		this.reloadLenght();
	}
	
	protected MapRoom(int posX, int posZ, int sizeX, int sizeZ, CustomMapRoom custom) throws AIObjectNotFoundException {
		this.custom = custom;
		
		this.northWest = new MapCorner(new MapPosition(posX, posZ), MapCorner.NORTHWEST);
		this.northEast = new MapCorner(new MapPosition(posX + sizeX -1, posZ), MapCorner.NORTHEAST);
		this.southWest = new MapCorner(new MapPosition(posX, posZ - sizeZ +1), MapCorner.SOUTHWEST);
		this.southEast = new MapCorner(new MapPosition(posX + sizeX -1, posZ - sizeZ +1), MapCorner.SOUTHEAST);
		
		this.door = 0;
		this.stairs = false;
		this.styleDoor = custom.getDoor().getRandom();
		
		this.reloadLenght();
	}
	
	public void reloadLenght() {
		this.lenghtX = Math.abs(northWest.getPos().getPosX() - southEast.getPos().getPosX()) + 1;
		this.lenghtZ = Math.abs(northWest.getPos().getPosZ() - southEast.getPos().getPosZ()) + 1;
	}
	
	public boolean isContains(MapPosition pos) {
		if(pos.getPosX() >= this.getNorthWest().getPos().getPosX() && pos.getPosX() <= this.getSouthEast().getPos().getPosX() &&
				pos.getPosZ() >= this.getNorthWest().getPos().getPosZ() && pos.getPosZ() <= this.getSouthEast().getPos().getPosZ())
			return true;
		else
			return false;
	}
	
	public String toString() {
		return "Room " + this.custom.getId() + ": position from " + this.getNorthWest().getPos().getPosX() + " - " + this.getNorthWest().getPos().getPosZ() +
				" to " + this.getSouthEast().getPos().getPosX() + " - " + this.getSouthEast().getPos().getPosZ() + ", lenght " + this.lenghtX + " - " + this.lenghtZ +
				", data: [door=" + this.door + ",stairs=" + this.stairs + ",custom=[" + this.custom + "]]";
	}
	
	public MapCorner getCorner(int id) {
		if(id == MapCorner.NORTHWEST)
			return this.northWest;
		else if(id == MapCorner.NORTHEAST)
			return this.northEast;
		else if(id == MapCorner.SOUTHEAST)
			return this.northEast;
		else if(id == MapCorner.SOUTHWEST)
			return this.southWest;
		return null;
	}
	public MapCorner getNorthWest() {
		return northWest;
	}
	public void setNorthWest(MapCorner northWest) {
		this.northWest = northWest;
		this.reloadLenght();
	}
	public MapCorner getNorthEast() {
		return northEast;
	}
	public void setNorthEast(MapCorner northEast) {
		this.northEast = northEast;
		this.reloadLenght();
	}
	public MapCorner getSouthWest() {
		return southWest;
	}
	public void setSouthWest(MapCorner southWest) {
		this.southWest = southWest;
		this.reloadLenght();
	}
	public MapCorner getSouthEast() {
		return southEast;
	}
	public void setSouthEast(MapCorner southEast) {
		this.southEast = southEast;
		this.reloadLenght();
	}
	public int getLenghtX() {
		return lenghtX;
	}
	public int getLenghtZ() {
		return lenghtZ;
	}
	public CustomMapRoom getCustom() {
		return custom;
	}
	public void setCustom(CustomMapRoom custom) {
		this.custom = custom;
	}
	public int getDoor() {
		return door;
	}
	public void setDoor(int door) {
		this.door = door;
	}
	public boolean isStairs() {
		return stairs;
	}
	public void setStairs(boolean stairs) {
		this.stairs = stairs;
	}
	public Door getStyleDoor() {
		return styleDoor;
	}
	public void setStyleDoor(Door styleDoor) {
		this.styleDoor = styleDoor;
	}
}
