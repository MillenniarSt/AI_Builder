package io.github.millenniarst.ai_builder.brain.area.world.house;

import io.github.millenniarst.ai_builder.AI_Builder;
import io.github.millenniarst.ai_builder.brain.area.Position;
import io.github.millenniarst.ai_builder.config.component.RoofStyle;

public class RoofDoubleSloping extends RoofOrientable {

	public static final String type = "double_sloping";
	
	private RoofSloping single;
	
	public RoofDoubleSloping(int rotation, RoofStyle style) {
		super(type, rotation, style);
	}
	
	@Override
	public RoofOrientable generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofDoubleSloping((int) (Math.random() * 2), style);
		else
			return new RoofDoubleSloping((int) (Math.random() * 2) + 2, style);
	}
	
	public void build() {
		AI_Builder.printDebug("Building sloping roof");
		if(getRotation() == NORTH) {
			if(getStyle().getSideBase() != null) {
				AI_Builder.printDebug("Building roof sides");
				
				getStyle().getSideBase().buildRepeatNorth(new Position(getOrigin().getPosX(), getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()),
						new Position(getEnd().getPosX(), getOrigin().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 0);
				getStyle().getSideBase().buildRepeatSouth(new Position(getOrigin().getPosX(), getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 
						new Position(getEnd().getPosX(), getEnd().getPosZ(), getOrigin().getPosY() + getStyle().getBase()), 0);
			}
			if(getStyle().getRepeat() != null) {
				AI_Builder.printDebug("Building roof repeat");
			}
		}
	}
	
	public void setSingle(RoofSloping single) {
		this.single = single;
	}
	public RoofSloping getSingle() {
		return single;
	}
}
