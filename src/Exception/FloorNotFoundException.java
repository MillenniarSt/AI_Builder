package Exception;

import Main.Main;

public class FloorNotFoundException extends AIObjectNotFoundException {
	private static final long serialVersionUID = 1L;

	private boolean ceiling; 
	private boolean inside;
	
	public FloorNotFoundException(boolean ceiling, boolean inside) {
		super("Floor", "Not found a floor from CustomMapRoom.flooring (config\\AI_Builder\\flooring) with data: ceiling = " 
				+ ceiling + " inside = " + inside);
		Main.printDebug("FloorNotFoundException: " + "Not found a floor from CustomMapRoom.flooring (config\\AI_Builder\\flooring) with data: ceiling = " 
				+ ceiling + " inside = " + inside);
		this.ceiling = ceiling;
		this.inside = inside;
	}
	
	public boolean isCeiling() {
		return ceiling;
	}
	public boolean isInside() {
		return inside;
	}
}
