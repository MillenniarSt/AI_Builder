package Exception;

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

public class WallNotFoundException extends AIObjectNotFoundException {
	private static final long serialVersionUID = 1L;

	private int floor;
	private int high;
	private boolean inside;
	
	public WallNotFoundException(int floor, int high, boolean inside) {
		super("WallStyle", "Not Found a wall style from BuildingPalace.wallHeight for the floor " + floor + " with high " + high + ", inside = " + inside);
	}

	public int getFloor() {
		return floor;
	}
	public int getHigh() {
		return high;
	}
	public boolean isInside() {
		return inside;
	}
}