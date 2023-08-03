package Model;

import java.util.ArrayList;

import Map.MapFloor;
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

public class RoofOrientable extends Roof {

	private boolean rotation;
	
	public RoofOrientable(String type, boolean rotation, RoofStyle style) {
		super(type, style);
		this.setRotation(rotation);
	}
	
	public RoofOrientable generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofOrientable("abstract", true, style);
		else
			return new RoofOrientable("abstract", false, style);
	}

	public void setRotation(boolean rotation) {
		this.rotation = rotation;
	}
	public boolean isRotation() {
		return rotation;
	}
}
