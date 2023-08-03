package Exception;

import Map.MapPositionOpt;

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

public class OverlapMapException extends Exception {
	private static final long serialVersionUID = 1L;

	public OverlapMapException(MapPositionOpt base, MapPositionOpt overlap) {
		super("Overlap Map: impossible to set a \"" + overlap + "\" on a \"" + base + "\"");
	}
	public OverlapMapException(int x1, int z1, int x2, int z2, int value, int anchor) {
		super("Overlap Map Room: impossible to place a room with size in the position: from " + x1 + " - " + z1 + " to " + x2 + " - " + z2 +
				". The position has already filled with value " + value + " and room anchor " + anchor);
	}
}
