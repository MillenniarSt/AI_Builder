package io.github.millenniarst.ai_builder.brain.map;

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

public class MapPosition {

	private final int posX;
	private final int posZ;
	private MapPositionOpt filled;

	public MapPosition(int posX, int posZ) {
		this.posX = posX;
		this.posZ = posZ;
		this.filled = MapPositionOpt.NONE;
	}
	
	public MapPosition(int posX, int posZ, MapPositionOpt filled) {
		this.posX = posX;
		this.posZ = posZ;
		this.filled = filled;
	}
	
	public boolean equals(MapPosition pos) {
		if(this.getPosX() == pos.getPosX() && this.getPosZ() == pos.getPosZ())
			return true;
		return false;
	}
	
	public int getPosX() {
		return posX;
	}
	public int getPosZ() {
		return posZ;
	}
	public MapPositionOpt getFilled() {
		return filled;
	}
	public void setFilled(MapPositionOpt filled) {
		this.filled = filled;
	}

	public enum MapPositionOpt {
		NONE, ROOM, WALL, DOOR, OUTSIDE;
	}
}
