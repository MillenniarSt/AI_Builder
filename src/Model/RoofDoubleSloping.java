package Model;

import Style.RoofStyle;

public class RoofDoubleSloping extends RoofOrientable {

	public static final String type = "double_sloping";
	
	private RoofSloping single;
	
	public RoofDoubleSloping(boolean rotation, RoofStyle style) {
		super(type, rotation, style);
	}
	
	@Override
	public RoofDoubleSloping generate(Room room, RoofStyle style) {
		if(room.getMap().getLenghtX() > room.getMap().getLenghtZ())
			return new RoofDoubleSloping(true, style);
		else
			return new RoofDoubleSloping(false, style);
	}
	
	public void setSingle(RoofSloping single) {
		this.single = single;
	}
	public RoofSloping getSingle() {
		return single;
	}
}
