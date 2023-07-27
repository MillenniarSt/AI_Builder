package Map;

public class MapCorner {

	public static final int NORTHWEST = 0;
	public static final int NORTHEAST = 1;
	public static final int SOUTHEAST = 2;
	public static final int SOUTHWEST = 3;
	
	private MapPosition pos;
	private int id;
	
	protected MapCorner(MapPosition pos, int id) {
		this.pos = pos;
		this.id = id;
	}
	
	public MapPosition getPos() {
		return pos;
	}
	public void setPos(MapPosition pos) {
		this.pos = pos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
