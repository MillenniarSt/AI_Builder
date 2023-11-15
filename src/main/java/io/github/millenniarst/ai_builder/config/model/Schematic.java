package io.github.millenniarst.ai_builder.config.model;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import io.github.millenniarst.ai_builder.config.Loader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.millenniarst.ai_builder.core.command.CommandExe;
import io.github.millenniarst.ai_builder.brain.area.AIBlockData;
import io.github.millenniarst.ai_builder.brain.area.BlockType;
import io.github.millenniarst.ai_builder.brain.area.Position;

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

public class Schematic implements Loader {

	private HashMap<int[], AIBlockData> schematic;
	
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
	
	public Schematic() {
		this.schematic = new HashMap<>();
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
	public Schematic(HashMap<int[], AIBlockData> schematic) {
		this.schematic = schematic;
		refreshSize();
		this.state = STATE_ENABLE;
	}
	
	public void build(Position pos) {
		this.build(pos.getPosX(), pos.getPosZ(), pos.getPosY());
	}
	public void build(int x, int z, int y) {
		CommandExe.getCurrentBuilding().getStyle().changeIndexs();
		for(int ix = 0; ix <= this.getSizeX() -1; ix++) {
			for(int iz = 0; iz <= this.getSizeZ() -1; iz++) {
				for(int iy = 0; iy <= this.getSizeY() -1; iy++) {
					CommandExe.getCurrentBuilding().setPos(x + ix, z + iz, y + iy, this.getBlock(ix, iz, iy));
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
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y + varY, this.getBlock(ix, iz, iy));
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
		Schematic repeat = this.rotateY(90);
		
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
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y + varY, repeat.getBlock(ix, iz, iy));
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
		Schematic repeat = this.rotateY(180);
		
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
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y + varY, repeat.getBlock(ix, iz, iy));
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
		Schematic repeat = this.rotateY(270);
		
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
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y + varY, repeat.getBlock(ix, iz, iy));
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
	public void buildRepeatUp(Position origin, Position end) {
		int originX = origin.getPosX() + this.getMinX();
		int endX = end.getPosX() + this.getMaxX();
		int originZ = origin.getPosZ() + this.getMinZ();
		int endZ = origin.getPosZ() + this.getMaxZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();

		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iz = this.getMinZ();
			for(int z = originZ; z <= endZ; z++) {
				int iy = this.getMinY();
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, this.getBlock(ix, iz, iy));
					iy++;
					if(iy > this.getMaxY()) {
						iy = this.getMinY();
					}
				}
				iz++;
			}
			ix++;
		}
	}
	
	public void buildRepeat2Up(Position origin, Position end, int high) {
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY() + this.getMinY();
		int endY = origin.getPosY() + this.getMaxY();
		
		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iz = this.getMinZ();
			int varY = 0;
			for(int z = originZ; z <= endZ; z++) {
				int iy = this.getMinY();
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y + varY, this.getBlock(ix, iz, iy));
					iy++;
				}
				iz++;
				if(iz > this.getMaxZ()) {
					iz = this.getMinZ();
					varY = varY + high;
				}
			}
			ix++;
			if(ix > this.getMaxX()) {
				ix = this.getMinX();
			}
		}
	}
	public void buildRepeat2Down(Position origin, Position end, int high) {
		Schematic repeat = this.clone();
		repeat.rotateMirrorXZ();

		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY() + repeat.getMinY();
		int endY = origin.getPosY() + repeat.getMaxY();

		int ix = repeat.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iz = repeat.getMinZ();
			int varY = 0;
			for(int z = originZ; z <= endZ; z++) {
				int iy = repeat.getMinY();
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y - varY, this.getBlock(ix, iz, iy));
					iy++;
				}
				iz++;
				if(iz > repeat.getMaxZ()) {
					iz = repeat.getMinZ();
					varY = varY - high;
				}
			}
			ix++;
			if(ix > repeat.getMaxX()) {
				ix = repeat.getMinX();
			}
		}
	}
	public void buildRepeat2North(Position origin, Position end, int side, int high) {
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + this.getMinZ();
		int endZ = origin.getPosZ() + this.getMaxZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varZ = 0;
		int varY = 0;
		
		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iy = this.getMinY();
			for(int y = originY; y <= endY; y++) {
				int iz = this.getMinZ();
				for(int z = originZ; z <= endZ; z++) {
					CommandExe.getCurrentBuilding().setPos(x, z + varZ, y + varY, this.getBlock(ix, iz, iy));
					iz++;
				}
				iy++;
				if(iy > this.getMaxY()) {
					iy = this.getMinY();
					varZ = varZ + side;
					varY = varY + high;
				}
			}
			varZ = 0;
			varY = 0;
			ix++;
			if(ix > this.getMaxX()) {
				ix = this.getMinX();
			}
		}
	}
	public void buildRepeat2East(Position origin, Position end, int side, int high) {
		Schematic repeat = this.rotateY(90);
		
		int originX = origin.getPosX() + repeat.getMinX();
		int endX = origin.getPosX() + repeat.getMaxX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varX = 0;
		int varY = 0;
		
		int iz = repeat.getMaxZ();
		for(int z = originZ; z >= endZ; z--) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int ix = repeat.getMinX();
				for(int x = originX; x <= endX; x++) {
					CommandExe.getCurrentBuilding().setPos(x + varX, z, y + varY, repeat.getBlock(ix, iz, iy));
					ix++;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varX = varX + side;
					varY = varY + high;
				}
			}
			varX = 0;
			varY = 0;
			iz--;
			if(iz < repeat.getMinZ()) {
				iz = repeat.getMaxZ();
			}
		}
	}
	public void buildRepeat2South(Position origin, Position end, int side, int high) {
		Schematic repeat = this.rotateY(180);
		
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ() + repeat.getMaxZ();
		int endZ = origin.getPosZ() + repeat.getMinZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varZ = 0;
		int varY = 0;
		
		int ix = repeat.getMaxX();
		for(int x = originX; x >= endX; x--) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int iz = repeat.getMaxZ();
				for(int z = originZ; z >= endZ; z--) {
					CommandExe.getCurrentBuilding().setPos(x, z - varZ, y + varY, repeat.getBlock(ix, iz, iy));
					iz--;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varZ = varZ + side;
					varY = varY + high;
				}
			}
			varZ = 0;
			varY = 0;
			ix--;
			if(ix < repeat.getMinX()) {
				ix = repeat.getMaxX();
			}
		}
	}
	public void buildRepeat2West(Position origin, Position end, int side, int high) {
		Schematic repeat = this.rotateY(270);
		
		int originX = origin.getPosX() + repeat.getMaxX();
		int endX = origin.getPosX() + repeat.getMinX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();
		int varX = 0;
		int varY = 0;
		
		int iz = repeat.getMinZ();
		for(int z = originZ; z <= endZ; z++) {
			int iy = repeat.getMinY();
			for(int y = originY; y <= endY; y++) {
				int ix = repeat.getMaxX();
				for(int x = originX; x >= endX; x--) {
					CommandExe.getCurrentBuilding().setPos(x - varX, z, y + varY, repeat.getBlock(ix, iz, iy));
					ix--;
				}
				iy++;
				if(iy > repeat.getMaxY()) {
					iy = repeat.getMinY();
					varX = varX + side;
					varY = varY + high;
				}
			}
			varX = 0;
			varY = 0;
			iz++;
			if(iz > repeat.getMaxZ()) {
				iz = repeat.getMinZ();
			}
		}
	}

	public void buildRepeat3(Position origin, Position end) {
		int originX = origin.getPosX();
		int endX = end.getPosX();
		int originZ = origin.getPosZ();
		int endZ = end.getPosZ();
		int originY = origin.getPosY();
		int endY = end.getPosY();

		int ix = this.getMinX();
		for(int x = originX; x <= endX; x++) {
			int iz = this.getMinZ();
			for(int z = endZ; z <= originZ; z++) {
				int iy = this.getMinY();
				for(int y = originY; y <= endY; y++) {
					CommandExe.getCurrentBuilding().setPos(x, z, y, this.getBlock(ix, iz, iy));
					iy++;
					if(iy > this.getMaxY()) {
						iy = this.getMinY();
					}
				}
				iz++;
				if(iz > this.getMaxZ()) {
					iz = this.getMinZ();
				}
			}
			ix++;
			if(ix > this.getMaxX()) {
				ix = this.getMinX();
			}
		}
	}

	public Schematic clone() {
		HashMap<int[], AIBlockData> schem = new HashMap<>();
		for(int[] arg : schematic.keySet()) {
			schem.put(arg, schematic.get(arg));
		}
		Schematic clone = new Schematic(schem);
		clone.setState(getState());
		return clone;
	}

	public Schematic rotateY(int angle) {
		Schematic clone = new Schematic();

		if(angle / 90 % 4 == 1) {
			for(int[] arg : schematic.keySet()) {
				AIBlockData block = schematic.get(arg);
				if(block.getProperty("facing") != null) {
					switch(block.getProperty("facing")) {
						case "north":
							block.setProperty("facing", "east");
							break;
						case "east":
							block.setProperty("facing", "south");
							break;
						case "south":
							block.setProperty("facing", "west");
							break;
						case "west":
							block.setProperty("facing", "north");
							break;
						}
					}
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				if(block.getProperty("north") != null) {
					north = false;
					east = true;
				}
				if(block.getProperty("east") != null) {
					east = false;
					south = true;
				}
				if(block.getProperty("south") != null) {
					south = false;
					west = true;
				}
				if(block.getProperty("west") != null) {
					west = false;
					north = true;
				}
				block.setProperty("north", north+"");
				block.setProperty("east", east+"");
				block.setProperty("south", south+"");
				block.setProperty("west", west+"");

				clone.getSchematic().put(new int[]{arg[1], arg[0] * -1, arg[2]}, block);
			}
		} else if(angle / 90 % 4 == 2) {
			for(int[] arg : schematic.keySet()) {
				AIBlockData block = schematic.get(arg);
				if(block.getProperty("facing") != null) {
					switch(block.getProperty("facing")) {
						case "north":
							block.setProperty("facing", "south");
							break;
						case "east":
							block.setProperty("facing", "west");
							break;
						case "south":
							block.setProperty("facing", "north");
							break;
						case "west":
							block.setProperty("facing", "east");
							break;
						}
				}
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				if(block.getProperty("north") != null) {
					north = false;
					south = true;
					}
				if(block.getProperty("east") != null) {
					east = false;
					west = true;
				}
				if(block.getProperty("south") != null) {
					south = false;
					north = true;
				}
				if(block.getProperty("west") != null) {
					west = false;
					east = true;
				}
				block.setProperty("north", north+"");
				block.setProperty("east", east+"");
				block.setProperty("south", south+"");
				block.setProperty("west", west+"");

				clone.getSchematic().put(new int[]{arg[1] * -1, arg[0], arg[2]}, block);
			}
		} else if(angle / 90 % 4 == 3) {
			for(int[] arg : schematic.keySet()) {
				AIBlockData block = schematic.get(arg);
				if(block.getProperty("facing") != null) {
					switch(block.getProperty("facing")) {
						case "north":
							block.setProperty("facing", "west");
							break;
						case "east":
							block.setProperty("facing", "north");
							break;
						case "south":
							block.setProperty("facing", "east");
							break;
						case "west":
							block.setProperty("facing", "south");
							break;
						}
					}
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				if(block.getProperty("north") != null) {
					north = false;
					west = true;
				}
				if(block.getProperty("east") != null) {
					east = false;
					north = true;
				}
				if(block.getProperty("south") != null) {
					south = false;
					east = true;
				}
				if(block.getProperty("west") != null) {
					west = false;
					south = true;
				}
				block.setProperty("north", north+"");
				block.setProperty("east", east+"");
				block.setProperty("south", south+"");
				block.setProperty("west", west+"");

				clone.getSchematic().put(new int[]{arg[0] * -1, arg[1] * -1, arg[2]}, block);
			}
		}
		if(angle % 90 != 0) {
			throw new NumberFormatException("The rotate angle must be: -180, -90, 0, 90, 180, 270 or 360");
		}
		clone.setState(getState());
		clone.refreshSize();
		return clone;
	}

	public Schematic rotateMirrorXZ() {
		Schematic clone = new Schematic();

		for(int[] arg : schematic.keySet()) {
			AIBlockData block = schematic.get(arg);
			if(block.getProperty("facing") != null) {
				switch(block.getProperty("facing")) {
					case "down":
						block.setProperty("facing", "up");
						break;
					case "up":
						block.setProperty("facing", "down");
						break;
				}
			}
			boolean down = false;
			boolean up = false;
			if(block.getProperty("down") != null) {
				down = false;
				up = true;
			}
			if(block.getProperty("up") != null) {
				up = false;
				down = true;
			}
			block.setProperty("north", down+"");
			block.setProperty("east", up+"");
			if(block.getProperty("half") != null) {
				switch(block.getProperty("half")) {
					case "top":
						block.setProperty("half", "bottom");
						break;
					case "bottom":
						block.setProperty("half", "top");
						break;
				}
			}

			clone.getSchematic().put(new int[]{arg[0] * -1, arg[1] * -1, arg[2]}, block);
		}

		clone.setState(getState());
		clone.refreshSize();
		return clone;
	}
	
	public void refreshSize() {
		for(int[] pos : schematic.keySet()) {
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
	}
	
	@Override
	public boolean load(String path) {
		path = "models\\schematics\\" + path;
		FileConfiguration file = YamlConfiguration.loadConfiguration(new File(CONFIG + "\\" + path + ".yml"));
		schematic.clear();
		
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
						setPos(x, z, y, new AIBlockData(material, BlockType.getType(type), group, properties));
					}
				}
			}
			refreshSize();
			this.state = STATE_ENABLE;
			return true;
		} catch(NumberFormatException exc) {
			exc.printStackTrace();
			schematic.clear();
			this.sizeX = 0;
			this.sizeZ = 0;
			this.sizeY = 0;
			this.state = STATE_ERROR;
		}
		return false;
	}
	
	public void setPos(Position pos) {
		setPos(pos.getPosX(), pos.getPosZ(), pos.getPosY(), pos.getBlock());
	}
	public void setPos(int x, int z, int y, AIBlockData block) {
		int[] arg = {x, z, y};
		this.schematic.put(arg, block);
	}
	public AIBlockData getBlock(int x, int z, int y) {
		int[] arg = {x, z, y};
		return this.schematic.get(arg);
	}
	
	public String toString() {
		return state;
	}
	
	public HashMap<int[], AIBlockData> getSchematic() {
		return schematic;
	}
	public void setSchematic(HashMap<int[], AIBlockData> schematic) {
		this.schematic = schematic;
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

	@Override
	public String getDirectoryPath() {
		return null;
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
