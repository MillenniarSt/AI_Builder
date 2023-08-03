package Map;

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

public class MapPosition {

	private int posX; 
	private int posZ;
	private MapPositionOpt filled;
	
	private boolean used;
	
	protected MapPosition(int posX, int posZ) {
		this.posX = posX;
		this.posZ = posZ;
		this.filled = MapPositionOpt.NONE;
		this.used = false;
	}
	
	protected MapPosition(int posX, int posZ, MapPositionOpt filled) {
		this.posX = posX;
		this.posZ = posZ;
		this.filled = filled;
		this.used = false;
	}
	
	public boolean equals(MapPosition pos) {
		if(this.getPosX() == pos.getPosX() && this.getPosZ() == pos.getPosZ())
			return true;
		return false;
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosZ() {
		return posZ;
	}
	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
	public MapPositionOpt getFilled() {
		return filled;
	}
	public void setFilled(MapPositionOpt filled) {
		this.filled = filled;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
}
