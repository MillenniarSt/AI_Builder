package Model;

import Main.Main;
import Style.RoofStyle;

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

public class RoofSloping extends RoofOrientable {

	public static final String type = "sloping";
	
	public RoofSloping(boolean rotation, RoofStyle style) {
		super(type, rotation, style);
	}
	
	@Override
	public RoofSloping generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofSloping(true, style);
		else
			return new RoofSloping(false, style);
	}
	
	public void build() {
		Main.printDebug("Building sloping roof");
		if(isRotation()) {
			if(getStyle().getSideBase() != null) {
				Main.printDebug("Building roof sides");
				
				getStyle().getSideBase().buildRepeatNorth(new Position(getOrigin().getPosX(), getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getEnd().getPosX(), getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 0);
				getStyle().getSideBase().buildRepeatSouth(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getEnd().getPosX(), getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 0);
			}
			if(getStyle().getRepeat() != null) {
				Main.printDebug("Building roof repeat");
			}
		}
	}
}
