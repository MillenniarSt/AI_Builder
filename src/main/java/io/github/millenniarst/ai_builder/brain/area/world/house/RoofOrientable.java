package io.github.millenniarst.ai_builder.brain.area.world.house;

import io.github.millenniarst.ai_builder.config.component.RoofStyle;

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

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private int rotation;
	
	public RoofOrientable(String type, int rotation, RoofStyle style) {
		super(type, style);
		this.setRotation(rotation);
	}
	
	public RoofOrientable generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofOrientable("rotable", (int) (Math.random() * 2), style);
		else
			return new RoofOrientable("rotable", (int) (Math.random() * 2) + 2, style);
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	public int getRotation() {
		return rotation;
	}
}
