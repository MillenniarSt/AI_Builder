package Model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Building.Building;
import Main.CommandExe;
import Main.Loader;
import Main.Random;
import Main.RandomCollection;

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

public class Model implements Loader {

	private HashMap<int[], Position> model;
	
	private int sizeX;
	private int sizeY;
	private int sizeZ;
	
	private int minX;
	private int minZ;
	private int minY;
	private int maxX;
	private int maxZ;
	private int maxY;
	
	private String state;
	
	public Model() {
		this.model = new HashMap<>();
		this.sizeX = 0;
		this.sizeZ = 0;
		this.sizeY = 0;
		this.minX = 0;
		this.minZ = 0;
		this.minY = 0;
		this.maxX = 0;
		this.maxZ = 0;
		this.maxY = 0;
		this.state = STATE_VOID;
	}
	public Model(HashMap<int[], Position> model) {
		this.model = model;
		refreshSize();
		this.state = STATE_ENABLE;
	}
	
	public void build(Building building, Position pos) {
		this.build(building, pos.getPosX(), pos.getPosZ(), pos.getPosY());
	}
	public void build(Building building, int x, int z, int y) {
		building.getStyle().changeIndexs();
		for(int ix = 0; ix <= this.getSizeX() -1; ix++) {
			for(int iz = 0; iz <= this.getSizeZ() -1; iz++) {
				for(int iy = 0; iy <= this.getSizeY() -1; iy++) {
					building.setPos(x + ix, z + iz, y + iy, this.getDataPos(ix, iz, iy));
				}
			}
		}
	}
	public void buildRepeatNorth(Position origin, Position end, int up) {
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + this.getMinZ();
		int endZ = origin.getPosZ() + this.getMaxZ();
		int originY = origin.getPosY() + this.getMinY();
		int endY = origin.getPosY() + this.getMaxY();
		int varY = 0;
		
		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iz = this.getMinZ();
			for(int z = originZ; z <= endZ; z++) {
				int iy = this.getMinY();
				for(int y = originY + varY; y <= endY + varY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, this.getDataPos(ix, iz, iy));
					iy++;
				}
				iz++;
			}
			ix++;
			if(ix > this.getMaxX()) {
				ix = this.getMinX();
				varY = varY + up;
			}
		}
	}
	public void buildRepeatEast(Position origin, Position end, int up) {
		Model repeat = this.cloneRotate(90);
		
		int originX = origin.getPosX() + repeat.getMinX();
		int endX = origin.getPosX() + repeat.getMaxX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY() + repeat.getMinY();
		int endY = origin.getPosY() + repeat.getMaxY();
		int varY = 0;
		
		int iz = repeat.getMaxZ();
		for(int z = originZ; z >= endZ; z--) {
			int ix = repeat.getMinX();
			for(int x = originX; x <= endX; x++) {
				int iy = repeat.getMinY();
				for(int y = originY + varY; y <= endY + varY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					iy++;
				}
				ix++;
			}
			iz--;
			if(iz < repeat.getMinZ()) {
				iz = repeat.getMaxZ();
				varY = varY + up;
			}
		}
	}
	public void buildRepeatSouth(Position origin, Position end, int up) {
		Model repeat = this.cloneRotate(180);
		
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + repeat.getMaxZ();
		int endZ = origin.getPosZ() + repeat.getMinZ();
		int originY = origin.getPosY() + repeat.getMinY();
		int endY = origin.getPosY() + repeat.getMaxY();
		int varY = 0;
		
		int ix = repeat.getMaxX();
		for(int x = originX; x >= endX; x--) {
			int iz = repeat.getMaxZ();
			for(int z = originZ; z >= endZ; z--) {
				int iy = repeat.getMinY();
				for(int y = originY + varY; y <= endY + varY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					iy++;
				}
				iz--;
			}
			ix--;
			if(ix < repeat.getMinX()) {
				ix = repeat.getMaxX();
				varY = varY + up;
			}
		}
	}
	public void buildRepeatWest(Position origin, Position end, int up) {
		Model repeat = this.cloneRotate(270);
		
		int originX = origin.getPosX() + repeat.getMaxX();
		int endX = origin.getPosX() + repeat.getMinX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY() + repeat.getMinY();
		int endY = origin.getPosY() + repeat.getMaxY();
		int varY = 0;
		
		int iz = repeat.getMinZ();
		for(int z = originZ; z <= endZ; z++) {
			int ix = repeat.getMaxX();
			for(int x = originX; x >= endX; x--) {
				int iy = repeat.getMinY();
				for(int y = originY + varY; y <= endY + varY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					iy++;
				}
				ix--;
			}
			iz++;
			if(iz > repeat.getMaxZ()) {
				iz = repeat.getMinZ();
				varY = varY + up;
			}
		}
	}
	public void buildRepeat2North(Position origin, Position end, int side) {
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + this.getMinZ();
		int endZ = origin.getPosZ() + this.getMaxZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varZ = 0;
		
		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iy = this.getMinY();
			for(int y = originY; y <= endY; y++) {
				int iz = this.getMinZ();
				for(int z = originZ + varZ; z <= endZ + varZ; z++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, this.getDataPos(ix, iz, iy));
					iz++;
				}
				iy++;
				if(iy > this.getMaxY()) {
					iy = this.getMinY();
					varZ = varZ + side;
				}
			}
			varZ = 0;
			ix++;
			if(ix > this.getMaxX()) {
				ix = this.getMinX();
			}
		}
	}
	public void buildRepeat2East(Position origin, Position end, int side) {
		Model repeat = this.cloneRotate(90);
		
		int originX = origin.getPosX() + repeat.getMinX();
		int endX = origin.getPosX() + repeat.getMaxX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varX = 0;
		
		int iz = repeat.getMaxZ();
		for(int z = originZ; z >= endX; z--) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int ix = repeat.getMinX();
				for(int x = originX + varX; x <= endX + varX; x++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					ix++;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varX = varX + side;
				}
			}
			varX = 0;
			iz--;
			if(iz < repeat.getMinZ()) {
				iz = repeat.getMaxZ();
			}
		}
	}
	public void buildRepeat2South(Position origin, Position end, int side) {
		Model repeat = repeat.cloneRotate(180);
		
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + repeat.getMaxZ();
		int endZ = origin.getPosZ() + repeat.getMinZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varZ = 0;
		
		int ix = repeat.getMaxX();
		for(int x = originX; x >= endX; x--) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int iz = repeat.getMaxZ();
				for(int z = originZ - varZ; z >= endZ - varZ; z--) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					iz--;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varZ = varZ + side;
				}
			}
			varZ = 0;
			ix--;
			if(ix < repeat.getMinX()) {
				ix = repeat.getMaxX();
			}
		}
	}
	public void buildRepeat2West(Position origin, Position end, int side) {
		Model repeat = this.cloneRotate(270);
		
		int originX = origin.getPosX() + repeat.getMaxX();
		int endX = origin.getPosX() + repeat.getMinX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varX = 0;
		
		int iz = repeat.getMinZ();
		for(int z = originZ; z <= endX; z++) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int ix = repeat.getMaxX();
				for(int x = originX - varX; x >= endX - varX; x--) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, repeat.getDataPos(ix, iz, iy));
					ix--;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varX = varX + side;
				}
			}
			varX = 0;
			iz++;
			if(iz > repeat.getMaxZ()) {
				iz = repeat.getMinZ();
			}
		}
	}
	
	public Model cloneRotate(int angle) {
		Model clone = this.clone();
		clone.rotate(angle);
		return clone;
	}
	public void rotate(int angle) {
		if(angle == 90) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosZ());
				pos.setPosZ(pos.getPosX() * -1);
				for(Random<AIBlockData> block : pos.getBlock().getCollection()) {
					if(block.getObject().getProperty("facing") != null) {
						switch(block.getObject().getProperty("facing")) {
						case "north":
							block.getObject().setProperty("facing", "east");
							break;
						case "east":
							block.getObject().setProperty("facing", "south");
							break;
						case "south":
							block.getObject().setProperty("facing", "west");
							break;
						case "west":
							block.getObject().setProperty("facing", "north");
							break;
						}
					}
					boolean north = false;
					boolean east = false;
					boolean south = false;
					boolean west = false;
					if(block.getObject().getProperty("north") != null) {
						north = false;
						east = true;
					}
					if(block.getObject().getProperty("east") != null) {
						east = false;
						south = true;
					}
					if(block.getObject().getProperty("south") != null) {
						south = false;
						west = true;
					}
					if(block.getObject().getProperty("west") != null) {
						west = false;
						north = true;
					}
					block.getObject().setProperty("north", north+"");
					block.getObject().setProperty("east", east+"");
					block.getObject().setProperty("south", south+"");
					block.getObject().setProperty("west", west+"");
				}
			}
		} else if(angle == 180 || angle == -180) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosZ() * -1);
				pos.setPosZ(pos.getPosX());
				for(Random<AIBlockData> block : pos.getBlock().getCollection()) {
					if(block.getObject().getProperty("facing") != null) {
						switch(block.getObject().getProperty("facing")) {
						case "north":
							block.getObject().setProperty("facing", "south");
							break;
						case "east":
							block.getObject().setProperty("facing", "west");
							break;
						case "south":
							block.getObject().setProperty("facing", "north");
							break;
						case "west":
							block.getObject().setProperty("facing", "east");
							break;
						}
					}
					boolean north = false;
					boolean east = false;
					boolean south = false;
					boolean west = false;
					if(block.getObject().getProperty("north") != null) {
						north = false;
						south = true;
					}
					if(block.getObject().getProperty("east") != null) {
						east = false;
						west = true;
					}
					if(block.getObject().getProperty("south") != null) {
						south = false;
						north = true;
					}
					if(block.getObject().getProperty("west") != null) {
						west = false;
						east = true;
					}
					block.getObject().setProperty("north", north+"");
					block.getObject().setProperty("east", east+"");
					block.getObject().setProperty("south", south+"");
					block.getObject().setProperty("west", west+"");
				}
			}
		} else if(angle == 270 || angle == -90) {
			for(int[] arg : model.keySet()) {
				Position pos = model.get(arg);
				pos.setPosX(pos.getPosX() * -1);
				pos.setPosZ(pos.getPosZ() * -1);
				for(Random<AIBlockData> block : pos.getBlock().getCollection()) {
					if(block.getObject().getProperty("facing") != null) {
						switch(block.getObject().getProperty("facing")) {
						case "north":
							block.getObject().setProperty("facing", "west");
							break;
						case "east":
							block.getObject().setProperty("facing", "north");
							break;
						case "south":
							block.getObject().setProperty("facing", "east");
							break;
						case "west":
							block.getObject().setProperty("facing", "south");
							break;
						}
					}
					boolean north = false;
					boolean east = false;
					boolean south = false;
					boolean west = false;
					if(block.getObject().getProperty("north") != null) {
						north = false;
						west = true;
					}
					if(block.getObject().getProperty("east") != null) {
						east = false;
						north = true;
					}
					if(block.getObject().getProperty("south") != null) {
						south = false;
						east = true;
					}
					if(block.getObject().getProperty("west") != null) {
						west = false;
						south = true;
					}
					block.getObject().setProperty("north", north+"");
					block.getObject().setProperty("east", east+"");
					block.getObject().setProperty("south", south+"");
					block.getObject().setProperty("west", west+"");
				}
			}
		} else if(angle != 0 && angle != 360) {
			throw new NumberFormatException("The rotate angle must be: -180, -90, 0, 90, 180, 270 or 360");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Model clone() {
		return new Model((HashMap<int[], Position>) this.model.clone());
	}
	
	public void refreshSize() {
		int minX = 0;
		int minZ = 0;
		int minY = 0;
		int maxX = 0;
		int maxZ = 0;
		int maxY = 0;
		for(int[] pos : model.keySet()) {
			if(pos[0] > maxX)
				maxX = pos[0];
			else if(pos[0] < minX)
				minX = pos[0];
			if(pos[1] > maxZ)
				maxZ = pos[1];
			else if(pos[1] < minZ)
				minZ = pos[1];
			if(pos[2] > maxY)
				maxY = pos[2];
			else if(pos[2] < minY)
				minY = pos[2];
		}
		this.sizeX = Math.abs(maxX - minX) + 1;
		this.sizeZ = Math.abs(maxZ - minZ) + 1;
		this.sizeY = Math.abs(maxY - minY) + 1;
		this.minX = minX;
		this.minZ = minZ;
		this.minY = minY;
		this.maxX = maxX;
		this.maxZ = maxZ;
		this.maxY = maxY;
	}
	
	@Override
	public boolean load(String path) {
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		model.clear();
		
		try {
			Set<String> grpX = file.getConfigurationSection("model").getKeys(false);
			for(String posX : grpX) {
				int x = Integer.parseInt(posX);
				Set<String> grpY = file.getConfigurationSection("model." + posX).getKeys(false);
				for(String posY : grpY) {
					int y = Integer.parseInt(posY);
					Set<String> grpZ = file.getConfigurationSection("model." + posX + "." + posY).getKeys(false);
					for(String posZ : grpZ) {
						int z = Integer.parseInt(posZ);
						String position = "model." + posX + "." + posY + "." + posZ + ".";
						
						boolean random = file.getBoolean(position + "random", false);
						if(random) {
							List<String> materials = file.getStringList(position + "material");
							List<String> types = file.getStringList(position + "type");
							List<String> groups = file.getStringList(position + "group");
							HashMap<String, List<String>> properties = null;
							Set<String> propertiesConf = file.getConfigurationSection(position + "properties").getKeys(false);
							if(!propertiesConf.isEmpty() && propertiesConf != null) {
								properties = new HashMap<>();
								for(String property : propertiesConf) {
									properties.put(property, file.getStringList(position + "properties." + property));
								}
							}
							addPos(x, z, y, AIBlockData.getCollection(materials, types, groups, properties));
						} else {
							String material = file.getString(position + "material", null);
							String type = file.getString(position + "type", null);
							String group = file.getString(position + "group", "default");
							HashMap<String, String> properties = new HashMap<>();
							Set<String> propertiesConf = file.getConfigurationSection(position + "properties").getKeys(false);
							if(!propertiesConf.isEmpty() && propertiesConf != null) {
								for(String property : propertiesConf) {
									properties.put(property, file.getString(position + "properties." + property));
								}
							}
							RandomCollection<AIBlockData> unique = new RandomCollection<>();
							unique.add(new AIBlockData(material, BlockType.getType(type), group, properties), 1);
							addPos(x, z, y, unique);
						}
					}
				}
			}
			refreshSize();
			this.state = STATE_ENABLE;
			return true;
		} catch(NumberFormatException exc) {
			exc.printStackTrace();
			model.clear();
			this.sizeX = 0;
			this.sizeZ = 0;
			this.sizeY = 0;
			this.state = STATE_ERROR;
		}
		return false;
	}
	
	public void addPos(Position pos) {
		int[] arg = {pos.getPosX(), pos.getPosZ(), pos.getPosY()};
		this.model.put(arg, pos);
	}
	public void addPos(int x, int z, int y, RandomCollection<AIBlockData> block) {
		int[] arg = {x, z, y};
		this.model.put(arg, new Position(x, z, y, block));
	}
	public void setPos(int x, int z, int y, RandomCollection<AIBlockData> block) {
		Position pos = getPos(x, z, y);
		if(pos == null) {
			addPos(x, z, y, block);
		} else {
			pos.setBlock(block);
		}
	}
	public Position getPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.model.get(arg);
	}
	public RandomCollection<AIBlockData> getDataPos(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.model.get(arg).getBlock();
	}
	
	public String toString() {
		return state;
	}
	
	public HashMap<int[], Position> getModel() {
		return model;
	}
	public void setModel(HashMap<int[], Position> model) {
		this.model = model;
	}
	public int getSizeX() {
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public int getSizeZ() {
		return sizeZ;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getMinX() {
		return minX;
	}
	public int getMinZ() {
		return minZ;
	}
	public int getMinY() {
		return minY;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public int getMaxY() {
		return maxY;
	}
}
