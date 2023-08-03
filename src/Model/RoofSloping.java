package Model;

import Main.CommandExe;

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
		if(isRotation()) {
			int ix = 0;
			for(int x = getOrigin().getPosX(); x <= getEnd().getPosX(); x++) {
				int iz = 0;
				for(int z = getOrigin().getPosZ() + getStyle().getVarSide(); z <= getOrigin().getPosZ() + getStyle().getVarSide() + getStyle().getSideBase().getMaxZ(); z++) {
					int iy = getStyle().getSideBase().getMinY();
					for(int y = getOrigin().getPosY() + getStyle().getBase() + getStyle().getSideBase().getMinY(); y <= getOrigin().getPosY() + getStyle().getBase() + getStyle().getSideBase().getMaxY(); y++) {
						CommandExe.getCurrentBuilding().setPos(x, z, y, getStyle().getSideBase().getDataPos(ix, iz, iy));
						iy++;
					}
					iz++;
				}
				ix++;
				if(ix > getStyle().getSideBase().getMaxX())
					ix = 0;
			}
		}
	}
}
