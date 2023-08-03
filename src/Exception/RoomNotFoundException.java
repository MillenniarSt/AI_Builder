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

public class RoomNotFoundException extends AIObjectNotFoundException {
	private static final long serialVersionUID = 1L;

	private int floor;
	private int lenghtX;
	private int lenghtZ;
	
	public RoomNotFoundException(int floor) {
		super("Room", "Not found a room from BuildingPalace.rooms (config\\AI_Builder\\rooms) for the floor " + floor);
		this.floor = floor;
	}
	public RoomNotFoundException(int floor, int lenghtX, int lenghtZ) {
		super("Room", "Not found a room from BuildingPalace.rooms (config\\AI_Builder\\rooms) for the floor " + floor + 
				" and size " + lenghtX + " x " + lenghtZ);
		this.floor = floor;
		this.lenghtX = lenghtX;
		this.lenghtZ = lenghtZ;
	}
	
	public int getFloor() {
		return floor;
	}
	public int getLenghtX() {
		return lenghtX;
	}
	public int getLenghtZ() {
		return lenghtZ;
	}
}
