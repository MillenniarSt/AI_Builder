package io.github.millenniarst.ai_builder.brain.map.house;

import io.github.millenniarst.ai_builder.brain.map.MapArea;
import io.github.millenniarst.ai_builder.brain.map.MapPosition;
import io.github.millenniarst.ai_builder.brain.map.MapRectangleArea;
import io.github.millenniarst.ai_builder.exception.AIObjectNotFoundException;
import io.github.millenniarst.ai_builder.config.component.MapRoomConfig;
import io.github.millenniarst.ai_builder.config.component.DoorConfig;

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

public class MapRoom<A extends MapArea> {

	private A area;
	
	private DoorConfig styleDoorConfig;
	private int door;
	private boolean stairs;
	
	private MapRoomConfig config;

	public MapRoom(A area, MapRoomConfig config) throws AIObjectNotFoundException {
		this.config = config;
		
		this.area = area;
		
		this.door = 0;
		this.stairs = false;
		this.styleDoorConfig = config.getDoor().getRandom();
	}

	public static MapRoom<MapRectangleArea> createRectangleRoom(MapRoomConfig config, int x, int z) throws AIObjectNotFoundException {
		int sizeXr;
		int sizeZr;
		if(((int) (Math.random() * 2)) == 0) {
			sizeXr = ((int) (Math.random() * (config.getMaxX() - config.getMinX() +1))) + config.getMinX();
			sizeZr = ((int) (Math.random() * (config.getMaxZ() - config.getMinZ() +1))) + config.getMinZ();
		} else {
			sizeZr = ((int) (Math.random() * (config.getMaxX() - config.getMinX() +1))) + config.getMinX();
			sizeXr = ((int) (Math.random() * (config.getMaxZ() - config.getMinZ() +1))) + config.getMinZ();
		}
		return new MapRoom<>(new MapRectangleArea(x, z, x + sizeXr -1, z + sizeZr -1, MapPosition.MapPositionOpt.ROOM), config);
	}
	
	public String toString() {
		return "Room " + this.config.getId() + ": area: [" + this.area + "], data: [door=" + this.door + ",stairs=" + this.stairs + ",custom=[" + this.config + "]]";
	}

	public MapRoomConfig getConfig() {
		return config;
	}
	public void setConfig(MapRoomConfig config) {
		this.config = config;
	}
	public A getArea() {
		return area;
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
	public DoorConfig getStyleDoor() {
		return styleDoorConfig;
	}
	public void setStyleDoor(DoorConfig styleDoorConfig) {
		this.styleDoorConfig = styleDoorConfig;
	}
}
