package Model;

public enum BlockType {
	BLOCK(""), LOG("log"), WOOD("wood"), PLANKS("planks"), STAIRS("stairs"), SLAB("slab"), FENCE("fence"), FENCE_GATE("fence_gate"), SIGN("sign"),
	WALL("wall"), TRAPDOOR("trapdoor"), DOOR("door"), BUTTON("button"), PRESSURE_PLATE("pressure_plate"), CRACKED("cracked"), MOSSY("mossy");
	
	private String id;
	
	private BlockType(String id) {
		this.id = id;
	}
	
	public static String change(String root, BlockType type) {
		if(root.contains("bricks"))
			root = root.substring(0, root.length() - 2);
		if(type == CRACKED || type == MOSSY)
			return type.id + "_" + root;
		if(type != BLOCK)
			return root + "_" + type.id;
		return root;
	}
	
	public static BlockType getType(String type) {
		switch(type) {
		case "":
			return BLOCK;
		case "block":
			return BLOCK;
		case "log":
			return LOG;
		case "wood":
			return WOOD;
		case "planks":
			return PLANKS;
		case "stairs":
			return STAIRS;
		case "slab":
			return SLAB;
		case "fence":
			return FENCE;
		case "fence_gate":
			return FENCE_GATE;
		case "sign":
			return SIGN;
		case "wall":
			return WALL;
		case "trapdoor":
			return TRAPDOOR;
		case "door":
			return DOOR;
		case "button":
			return BUTTON;
		case "pressure_plate":
			return PRESSURE_PLATE;
		case "cracked":
			return CRACKED;
		case "mossy":
			return MOSSY;
		}
		return null;
	}
	
	public String toString() {
		return id;
	}
	
	public String getId() {
		return id;
	}
}
