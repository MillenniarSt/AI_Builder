package io.github.millenniarst.ai_builder.brain.area.world.house;

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.brain.area.Position;
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

public class RoofSloping extends RoofOrientable {

	public static final String type = "sloping";
	
	public RoofSloping(int rotation, RoofStyle style) {
		super(type, rotation, style);
	}
	
	@Override
	public RoofSloping generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofSloping((int) (Math.random() * 2), style);
		else
			return new RoofSloping((int) (Math.random() * 2) + 2, style);
	}
	
	public void build() {
		AI_Builder.printDebug("Building sloping roof");
		if(getRotation() == NORTH) {
			if(getStyle().getRepeat() != null) {
				AI_Builder.printDebug("Building roof repeat");
				
				getStyle().getRepeat().buildRepeat2Up(getOrigin(), getEnd(), getStyle().getRepeat().getMaxY() +1);
			}
			if(getStyle().getSideBase() != null) {
				AI_Builder.printDebug("Building roof base sides");
				
				getStyle().getSideBase().buildRepeatNorth(getOrigin(), getEnd(), 0);
			}
			if(getStyle().getSide() != null) {
				AI_Builder.printDebug("Building roof sides");
				
				getStyle().getSide().buildRepeatEast(new Position(getOrigin().getPosX() -1, getOrigin().getPosZ(), getOrigin().getPosY()),
						new Position(getOrigin().getPosX() -1, getEnd().getPosZ(), getOrigin().getPosY()), getStyle().getSide().getMaxY() +1);
				getStyle().getSide().cloneRotate(180).buildRepeatEast(new Position(getEnd().getPosX() +1, getOrigin().getPosZ(), getOrigin().getPosY()), 
						new Position(getEnd().getPosX() +1, getEnd().getPosZ(), getOrigin().getPosY()), getStyle().getSide().getMaxY() +1);
			}
			if(getStyle().getCorner() != null) {
				AI_Builder.printDebug("Building roof corners");
				
				getStyle().getCorner().build(new Position(getOrigin().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY()));
				getStyle().getCorner().build(new Position(getEnd().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY()));
			}
			if(getStyle().getCenter() != null) {
				AI_Builder.printDebug("Building roof center");
				
				getStyle().getCenter().buildRepeatNorth(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getEnd().getPosY()), 
						getEnd(), 0);
			}
			if(getStyle().getCornerHigh() != null) {
				AI_Builder.printDebug("Building roof high corners");
				
				getStyle().getCorner().build(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getEnd().getPosY()));
				getStyle().getCorner().cloneRotate(180).build(getEnd());
			}
		} else if(getRotation() == EAST) {
			if(getStyle().getRepeat() != null) {
				AI_Builder.printDebug("Building roof repeat");
				
				getStyle().getRepeat().buildRepeat2East(new Position(getOrigin().getPosX(), getOrigin().getPosZ(), getOrigin().getPosY()), 
						new Position(getEnd().getPosX(), getEnd().getPosZ(), getEnd().getPosY()), 
						(getStyle().getRepeat().getMaxZ() +1) * -1, getStyle().getRepeat().getMaxY() +1);
			}
			if(getStyle().getSideBase() != null) {
				AI_Builder.printDebug("Building roof base sides");
				
				getStyle().getSideBase().buildRepeatNorth(new Position(getOrigin().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getEnd().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY() + getStyle().getBase()), 0);
			}
			if(getStyle().getSide() != null) {
				AI_Builder.printDebug("Building roof sides");
				
				getStyle().getSide().buildRepeatEast(new Position(getOrigin().getPosX() -1, getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getOrigin().getPosX() -1, getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), getStyle().getSide().getMaxY() +1);
				getStyle().getSide().cloneRotate(90).buildRepeatEast(new Position(getEnd().getPosX() +1, getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getEnd().getPosX() +1, getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), getStyle().getSide().getMaxY() +1);
			}
			if(getStyle().getCorner() != null) {
				AI_Builder.printDebug("Building roof corners");
				
				getStyle().getCorner().build(new Position(getOrigin().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY() + getStyle().getBase()));
				getStyle().getCorner().build(new Position(getEnd().getPosX(), getOrigin().getPosZ() +1, getOrigin().getPosY() + getStyle().getBase()));
			}
			if(getStyle().getCenter() != null) {
				AI_Builder.printDebug("Building roof center");
				
				getStyle().getCenter().buildRepeatNorth(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getEnd().getPosY()), 
						getEnd(), 0);
			}
			if(getStyle().getCornerHigh() != null) {
				AI_Builder.printDebug("Building roof high corners");
				
				getStyle().getCorner().build(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getEnd().getPosY()));
				getStyle().getCorner().cloneRotate(180).build(getEnd());
			}
		}
	}
}
